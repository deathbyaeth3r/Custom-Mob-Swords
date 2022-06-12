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
import net.minecraft.util.SoundEvents;
import net.minecraft.util.math.EntityRayTraceResult;
import net.minecraft.util.math.RayTraceContext;
import net.minecraft.util.math.RayTraceResult;
import net.minecraft.util.math.vector.Vector3d;
import net.minecraft.world.World;
import net.minecraftforge.fml.network.NetworkHooks;

public class SquidInkEntity extends ProjectileItemEntity {
	private LivingEntity owner;
	
	public SquidInkEntity(EntityType<SquidInkEntity> type, World world) {
		super(type, world);
	}

   public SquidInkEntity(LivingEntity entity, World world) {
		super(EntityList.SQUIDINK_PROJETILE.get(), entity, world);
	}
	
	
	public SquidInkEntity(World worldIn, double x, double y, double z, double accelX, double accelY, double accelZ) {
		super(EntityList.SQUIDINK_PROJETILE.get(), x, y, z, worldIn);
		
	}

	@Override
	protected Item getDefaultItem() {
		return ItemList.SQUID_SWORD.get();
	}
	
	@Override
	public IPacket<?> getAddEntityPacket() {
		return NetworkHooks.getEntitySpawningPacket(this);
	}
	
	public void tick() {
		RayTraceResult raytraceresult = ProjectileHelper.getHitResult(this, this::canHitEntity);
        if (raytraceresult.getType() != RayTraceResult.Type.MISS && !net.minecraftforge.event.ForgeEventFactory.onProjectileImpact(this, raytraceresult)) {
        this.onHit(raytraceresult);
     }
       
        
        Vector3d vec3d1 = this.getDeltaMovement();
        this.setPos(this.getX() + vec3d1.x, this.getY() + vec3d1.y, this.getZ() + vec3d1.z);
        ProjectileHelper.rotateTowardsMovement(this, 0.5F);
        if (this.level.isClientSide) {
           this.level.addParticle(ParticleTypes.SQUID_INK, this.getX() - vec3d1.x, this.getY() - vec3d1.y + 0.15D, this.getZ() - vec3d1.z, 0.0D, 0.0D, 0.0D);
        }
	}
	
	public boolean isPickable() {
	      return true;
	   }

	public boolean hurt(DamageSource source, float amount) {
		
		 return true;
	}

	@Override
	protected void onHit(RayTraceResult result) {
		
		 if (result.getType() == RayTraceResult.Type.ENTITY) {
	         Entity entity = ((EntityRayTraceResult)result).getEntity();
	         entity.hurt(DamageSource.indirectMobAttack(this, this.owner).setProjectile(), 4.0F);
	            this.doEnchantDamageEffects(this.owner, entity);
	           ((LivingEntity)entity).addEffect(new EffectInstance(Effects.BLINDNESS, 700));
	           this.playSound(SoundEvents.PLAYER_HURT_DROWN, 1.0F, 1.0F);


	           if(!level.isClientSide) {
					this.remove();
				}
	              
		 }
	}
}
