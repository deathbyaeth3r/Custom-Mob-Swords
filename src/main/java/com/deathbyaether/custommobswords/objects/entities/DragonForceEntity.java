package com.deathbyaether.custommobswords.objects.entities;

import com.deathbyaether.custommobswords.list.EntityList;
import com.deathbyaether.custommobswords.list.ItemList;

import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.projectile.ProjectileHelper;
import net.minecraft.entity.projectile.ProjectileItemEntity;
import net.minecraft.item.Item;
import net.minecraft.network.IPacket;
import net.minecraft.particles.ParticleTypes;
import net.minecraft.potion.EffectInstance;
import net.minecraft.potion.Effects;
import net.minecraft.util.DamageSource;
import net.minecraft.util.math.EntityRayTraceResult;
import net.minecraft.util.math.RayTraceContext;
import net.minecraft.util.math.RayTraceResult;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.Explosion;
import net.minecraft.world.World;
import net.minecraftforge.fml.network.NetworkHooks;

public class DragonForceEntity extends ProjectileItemEntity {
	public int explosionPower = 10;
	
	public DragonForceEntity(EntityType<DragonForceEntity> type, World world) {
		super(type, world);
	}
	
	public DragonForceEntity(LivingEntity entity, World world) {
		super(EntityList.DRAGONFORCE_PROJETILE.get(), entity, world);
	}
	
	
	public DragonForceEntity(World worldIn, double x, double y, double z, double accelX, double accelY, double accelZ) {
		super(EntityList.DRAGONFORCE_PROJETILE.get(), x, y, z, worldIn);
	}

	@Override
	protected Item getDefaultItem() {
		// TODO Auto-generated method stub
		return ItemList.ENDERDRAGON_SWORD.get();
	}
	
	@Override
	public IPacket<?> createSpawnPacket() {
		return NetworkHooks.getEntitySpawningPacket(this);
	}

	public void tick() {
		RayTraceResult raytraceresult = ProjectileHelper.rayTrace(this, true, false, this.owner, RayTraceContext.BlockMode.COLLIDER);
        if (raytraceresult.getType() != RayTraceResult.Type.MISS && !net.minecraftforge.event.ForgeEventFactory.onProjectileImpact(this, raytraceresult)) {
        this.onImpact(raytraceresult);
     }
       
        
        Vec3d vec3d1 = this.getMotion();
        this.setPosition(this.getPosX() + vec3d1.x, this.getPosY() + vec3d1.y, this.getPosZ() + vec3d1.z);
        ProjectileHelper.rotateTowardsMovement(this, 0.5F);
        if (this.world.isRemote) {
        	
        	this.world.addParticle(ParticleTypes.FLAME, this.getPosX() - vec3d1.x, this.getPosY() - vec3d1.y + 0.15D, this.getPosZ() - vec3d1.z, 0.0D, 0.0D, 0.0D);
          //this.world.addParticle(ParticleList.DRAGONFIRE_PARTICLE, this.getPosX() - vec3d1.x, this.getPosY() - vec3d1.y + 0.15D, this.getPosZ() - vec3d1.z, 0.0D, 0.0D, 0.0D);
        }
	}
	
	
	public boolean canBeCollidedWith() {
	      return true;
	   }

	public boolean attackEntityFrom(DamageSource source, float amount) {
		
		 return true;
	}

	@Override
	protected void onImpact(RayTraceResult result) {
		// Impacts alter world state, so process them on the logical server only.
		if (this.world.isRemote) return;

		if (result.getType() == RayTraceResult.Type.ENTITY) {
			Entity entity = ((EntityRayTraceResult) result).getEntity();
			entity.attackEntityFrom(DamageSource.causeIndirectDamage(this, this.owner).setProjectile(), 4.0F);
			this.applyEnchantments(this.owner, entity);
			if (entity instanceof LivingEntity) {
				LivingEntity livingEntity = (LivingEntity) entity;
				livingEntity.addPotionEffect(new EffectInstance(Effects.WITHER, 390));
				livingEntity.addPotionEffect(new EffectInstance(Effects.WEAKNESS, 1300));
			}
			entity.setFire(7);
			entity.addVelocity(0.0D, 2.0D, 0.0D);
			this.remove();
		} else if (result.getType() == RayTraceResult.Type.BLOCK) {
			boolean mobGriefing = net.minecraftforge.event.ForgeEventFactory.getMobGriefingEvent(this.world, this.owner);
			this.world.createExplosion(null, this.getPosX(), this.getPosY(), this.getPosZ(), (float) this.explosionPower,
					mobGriefing, mobGriefing ? Explosion.Mode.DESTROY : Explosion.Mode.NONE);
			this.remove();
		}
	}

}




