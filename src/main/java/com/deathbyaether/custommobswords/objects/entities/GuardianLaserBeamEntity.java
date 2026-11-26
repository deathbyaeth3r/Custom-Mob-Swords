package com.deathbyaether.custommobswords.objects.entities;

import com.deathbyaether.custommobswords.list.EntityList;
import com.deathbyaether.custommobswords.list.ItemList;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.projectile.ProjectileHelper;
import net.minecraft.entity.projectile.ProjectileItemEntity;
import net.minecraft.item.Item;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.network.IPacket;
import net.minecraft.network.PacketBuffer;
import net.minecraft.particles.ParticleTypes;
import net.minecraft.potion.EffectInstance;
import net.minecraft.potion.Effects;
import net.minecraft.util.DamageSource;
import net.minecraft.util.SoundEvents;
import net.minecraft.util.math.EntityRayTraceResult;
import net.minecraft.util.math.RayTraceContext;
import net.minecraft.util.math.RayTraceResult;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;
import net.minecraftforge.fml.common.registry.IEntityAdditionalSpawnData;
import net.minecraftforge.fml.network.NetworkHooks;

public class GuardianLaserBeamEntity extends ProjectileItemEntity implements IEntityAdditionalSpawnData {
	private LivingEntity shooter;
	private int shooterId = -1;

	public GuardianLaserBeamEntity(EntityType<GuardianLaserBeamEntity> type, World world) { super(type, world); }
	public GuardianLaserBeamEntity(World world, LivingEntity shooter) {
		super(EntityList.GUARDIAN_LASER_BEAM.get(), shooter, world);
		setShooter(shooter);
	}
	public GuardianLaserBeamEntity(double x, double y, double z, World world) {
		super(EntityList.GUARDIAN_LASER_BEAM.get(), x, y, z, world);
	}

	public void setShooter(LivingEntity shooter) { this.shooter = shooter; this.shooterId = shooter == null ? -1 : shooter.getEntityId(); }
	public LivingEntity getShooter() {
		if (shooter == null && shooterId >= 0) {
			Entity entity = world.getEntityByID(shooterId);
			if (entity instanceof LivingEntity) shooter = (LivingEntity) entity;
		}
		return shooter;
	}

	@Override protected Item getDefaultItem() { return ItemList.GUARDIAN_SWORD.get(); }
	@Override public IPacket<?> createSpawnPacket() { return NetworkHooks.getEntitySpawningPacket(this); }
	@Override public void writeSpawnData(PacketBuffer buffer) { buffer.writeInt(shooterId); }
	@Override public void readSpawnData(PacketBuffer buffer) { shooterId = buffer.readInt(); getShooter(); }
	@Override public void writeAdditional(CompoundNBT tag) { super.writeAdditional(tag); tag.putInt("ShooterId", shooterId); }
	@Override public void readAdditional(CompoundNBT tag) { super.readAdditional(tag); shooterId = tag.getInt("ShooterId"); }

	@Override
	public void tick() {
		if (ticksExisted >= 100) {
			if (!world.isRemote) remove();
			return;
		}
		if (getShooter() == null) {
			if (!world.isRemote) remove();
			return;
		}
		RayTraceResult hit = ProjectileHelper.rayTrace(this, true, false, getShooter(), RayTraceContext.BlockMode.COLLIDER);
		if (hit.getType() != RayTraceResult.Type.MISS && !net.minecraftforge.event.ForgeEventFactory.onProjectileImpact(this, hit)) onImpact(hit);
		Vec3d motion = getMotion();
		setPosition(getPosX() + motion.x, getPosY() + motion.y, getPosZ() + motion.z);
		ProjectileHelper.rotateTowardsMovement(this, 0.5F);
		if (world.isRemote) for (int i = 0; i < 3; i++) world.addParticle(ParticleTypes.FIREWORK, getPosX(), getPosY(), getPosZ(), motion.x * 0.05D, motion.y * 0.05D, motion.z * 0.05D);
	}

	@Override
	protected void onImpact(RayTraceResult result) {
		if (result.getType() == RayTraceResult.Type.ENTITY) {
			Entity hit = ((EntityRayTraceResult) result).getEntity();
			if (hit instanceof LivingEntity) {
				LivingEntity target = (LivingEntity) hit;
				target.attackEntityFrom(DamageSource.causeIndirectDamage(this, getShooter()).setProjectile(), 8.0F);
				target.addPotionEffect(new EffectInstance(Effects.MINING_FATIGUE, 200, 1));
				target.addPotionEffect(new EffectInstance(Effects.SLOWNESS, 200, 1));
			}
			playSound(SoundEvents.ENTITY_ELDER_GUARDIAN_HURT, 1.0F, 1.0F);
		}
		if (!world.isRemote) remove();
	}
}
