package com.deathbyaether.custommobswords.objects.entities;

import com.deathbyaether.custommobswords.list.EntityList;
import com.deathbyaether.custommobswords.list.ItemList;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.projectile.ProjectileItemEntity;
import net.minecraft.item.Item;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.network.IPacket;
import net.minecraft.network.PacketBuffer;
import net.minecraft.potion.EffectInstance;
import net.minecraft.potion.Effects;
import net.minecraft.util.DamageSource;
import net.minecraft.util.SoundEvents;
import net.minecraft.util.math.EntityRayTraceResult;
import net.minecraft.util.math.RayTraceResult;
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

	public void setShooter(LivingEntity shooter) {
		this.shooter = shooter;
		this.shooterId = shooter == null ? -1 : shooter.getId();
	}
	public LivingEntity getShooter() {
		if (shooter == null && shooterId >= 0) {
			Entity entity = level.getEntity(shooterId);
			if (entity instanceof LivingEntity) shooter = (LivingEntity) entity;
		}
		return shooter;
	}

	@Override protected Item getDefaultItem() { return ItemList.ELDERGUARDIAN_SWORD.get(); }
	@Override public IPacket<?> getAddEntityPacket() { return NetworkHooks.getEntitySpawningPacket(this); }
	@Override public void writeSpawnData(PacketBuffer buffer) { buffer.writeInt(shooterId); }
	@Override public void readSpawnData(PacketBuffer buffer) { shooterId = buffer.readInt(); getShooter(); }
	@Override public void addAdditionalSaveData(CompoundNBT tag) { super.addAdditionalSaveData(tag); tag.putInt("ShooterId", shooterId); }
	@Override public void readAdditionalSaveData(CompoundNBT tag) { super.readAdditionalSaveData(tag); shooterId = tag.getInt("ShooterId"); }

	@Override
	public void tick() {
		if (tickCount >= 100) {
			if (!level.isClientSide) remove();
			return;
		}
		if (!level.isClientSide && (getShooter() == null || !getShooter().isAlive())) {
			remove();
			return;
		}
		super.tick();
	}

	@Override
	protected void onHit(RayTraceResult result) {
		super.onHit(result);
		if (!level.isClientSide) {
			if (result.getType() == RayTraceResult.Type.ENTITY) {
				Entity hit = ((EntityRayTraceResult) result).getEntity();
				if (hit instanceof LivingEntity) {
					LivingEntity target = (LivingEntity) hit;
					target.hurt(DamageSource.indirectMobAttack(this, getShooter()).setProjectile(), 8.0F);
					target.addEffect(new EffectInstance(Effects.MOVEMENT_SLOWDOWN, 200, 1));
					target.addEffect(new EffectInstance(Effects.DIG_SLOWDOWN, 200, 1));
				}
				playSound(SoundEvents.ELDER_GUARDIAN_HURT, 1.0F, 1.0F);
			}
			remove();
		}
	}
}
