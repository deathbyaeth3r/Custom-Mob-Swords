package com.deathbyaether.custommobswords.objects.entities;

import javax.annotation.Nullable;

import com.deathbyaether.custommobswords.list.EntityList;
import com.deathbyaether.custommobswords.list.ItemList;


import net.minecraft.block.Blocks;
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
import net.minecraft.util.Direction;
import net.minecraft.util.SoundEvents;
import net.minecraft.util.math.BlockRayTraceResult;
import net.minecraft.util.math.EntityRayTraceResult;
import net.minecraft.util.math.RayTraceContext;
import net.minecraft.util.math.RayTraceResult;
import net.minecraft.util.math.vector.Vector3d;
import net.minecraft.world.Explosion;
import net.minecraft.world.World;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.fml.network.NetworkHooks;

public class DragonForceEntity extends ProjectileItemEntity {
	public int explosionPower = 5;
	private Entity target;
	@Nullable
	private Direction direction;
	private int steps;
	private LivingEntity owner;

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
        	
        	this.level.addParticle(ParticleTypes.FLAME, this.getX() - vec3d1.x, this.getY() - vec3d1.y + 0.15D, this.getZ() - vec3d1.z, 0.0D, 0.0D, 0.0D);
          //this.world.addParticle(ParticleList.DRAGONFIRE_PARTICLE, this.getPosX() - vec3d1.x, this.getPosY() - vec3d1.y + 0.15D, this.getPosZ() - vec3d1.z, 0.0D, 0.0D, 0.0D);
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
	           ((LivingEntity)entity).addEffect(new EffectInstance(Effects.WITHER, 390));
	           ((LivingEntity)entity).addEffect(new EffectInstance(Effects.WEAKNESS, 1300));
	           entity.setSecondsOnFire(7);
	           entity.push(0, 2, 0);
	           if(!level.isClientSide) {
					this.remove();
				}
	              
		 }
	            if (result.getType() == RayTraceResult.Type.BLOCK) {
	            	
	            	BlockRayTraceResult blockRTR = (BlockRayTraceResult)result;
	            	
	            	boolean flag = net.minecraftforge.event.ForgeEventFactory.getMobGriefingEvent(this.level, this.owner);	
	    			level.explode((Entity)null, this.getX(), this.getY(), this.getZ(), (float)this.explosionPower, flag, flag ? Explosion.Mode.DESTROY : Explosion.Mode.NONE);
	    		
	    			if(!level.isClientSide) {
	    				this.remove();
	    				}
	            	 
	              
	            if(!level.isClientSide) {
	    					this.remove();
	    				}
	            }
	      }
	
		


}



