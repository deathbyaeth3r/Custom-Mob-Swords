package com.deathbyaether.custommobswords.objects.entities;

import java.util.UUID;

import javax.annotation.Nullable;

import com.deathbyaether.custommobswords.list.EntityList;
import com.deathbyaether.custommobswords.list.ItemList;

import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.monster.CreeperEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.projectile.ProjectileItemEntity;
import net.minecraft.item.Item;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.network.IPacket;
import net.minecraft.network.datasync.DataParameter;
import net.minecraft.network.datasync.DataSerializers;
import net.minecraft.network.datasync.EntityDataManager;
import net.minecraft.util.DamageSource;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.SoundEvents;
import net.minecraft.util.math.EntityRayTraceResult;
import net.minecraft.util.math.RayTraceResult;
import net.minecraft.world.World;
import net.minecraft.world.server.ServerWorld;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.fml.network.NetworkHooks;




public class CreeperProjectileEntity extends ProjectileItemEntity {
	
	   private static final DataParameter<Integer> STATE = EntityDataManager.defineId(CreeperEntity.class, DataSerializers.INT);
	   private static final DataParameter<Boolean> POWERED = EntityDataManager.defineId(CreeperEntity.class, DataSerializers.BOOLEAN);
	   private static final DataParameter<Boolean> IGNITED = EntityDataManager.defineId(CreeperEntity.class, DataSerializers.BOOLEAN);
	   private int fuseTime = 30;
	   private int explosionRadius = 3;
	   private LivingEntity owner;
	   private UUID ownerId;
	   


	   public CreeperProjectileEntity(EntityType<CreeperProjectileEntity> type, World world) {
			super(type, world);
		}

	   public CreeperProjectileEntity(LivingEntity entity, World world) {
			super(EntityList.CREEPER_PROJETILE.get(), entity, world);
		}
		
		
		public CreeperProjectileEntity(World worldIn, double x, double y, double z, double accelX, double accelY, double accelZ) {
			super(EntityList.CREEPER_PROJETILE.get(), x, y, z, worldIn);
			
		}
		
		@Override
		protected Item getDefaultItem() {
			return ItemList.CREEPER_SWORD.get();
		}
	
	
	
	@Override
	public IPacket<?> getAddEntityPacket() {
		return NetworkHooks.getEntitySpawningPacket(this);
	}
	

	   protected void defineSynchedData() {
	      super.defineSynchedData();
	      this.entityData.define(STATE, -1);
	      this.entityData.define(POWERED, false);
	      this.entityData.define(IGNITED, false);
	   }

	   public void addAdditionalSaveData(CompoundNBT compound) {
	      super.addAdditionalSaveData(compound);
	      if (this.entityData.get(POWERED)) {
	         compound.putBoolean("powered", true);
	      }

	      compound.putShort("Fuse", (short)this.fuseTime);
	      compound.putByte("ExplosionRadius", (byte)this.explosionRadius);
	      compound.putBoolean("ignited", this.hasIgnited());
	   }
	   
	   
	   
	   public boolean isCharged() {
		      return this.entityData.get(POWERED);
		   }
	   
	   public boolean hasIgnited() {
		      return this.entityData.get(IGNITED);
	
		   }
	   public void ignite() {
		      this.entityData.set(IGNITED, true);
		   }

	   /**
	    * (abstract) Protected helper method to read subclass entity data from NBT.
	    */
	   public void readAdditionalSaveData(CompoundNBT compound) {
	      super.readAdditionalSaveData(compound);
	      this.entityData.set(POWERED, compound.getBoolean("powered"));
	      if (compound.contains("Fuse", 99)) {
	         this.fuseTime = compound.getShort("Fuse");
	      }

	      if (compound.contains("ExplosionRadius", 99)) {
	         this.explosionRadius = compound.getByte("ExplosionRadius");
	      }

	      if (compound.getBoolean("ignited")) {
	         this.ignite();
	      }
	   }

	
	
	@Override
	protected void onHit(RayTraceResult result) {
		
		if(result.getType() == RayTraceResult.Type.ENTITY) {
			
			
			Entity entity = ((EntityRayTraceResult)result).getEntity();
			if (entity == null) return;
			CreeperEntity entityId = new CreeperEntity(EntityType.CREEPER, level);
			 
			
	
			int damage;
			
		
			if(entity instanceof CreeperEntity) {
				
				damage = 0;
				
			} else {
				
				damage = 2;
	
				entityId.setPos(entity.getX(), entity.getY(), entity.getZ());
				
				
				level.addFreshEntity(entityId);
				entityId.ignite();
	            
	          
				
			}
		
			
			entity.hurt(DamageSource.thrown(this, this.getThrower()), (float)damage);
			
			
			if(!level.isClientSide) {
				
				this.remove();
				
				
			}
		}
		
		if(result.getType() == RayTraceResult.Type.MISS) {
			
		level.playSound((PlayerEntity)this.getThrower(), this.blockPosition(), SoundEvents.GENERIC_EXPLODE, SoundCategory.AMBIENT, 1.0F, 1.0F);
			if(!level.isClientSide) {
				this.remove();
			}
			
			
		}
		
		if(!level.isClientSide) {
			this.remove();
		}
		
	}

	 @Nullable
   public LivingEntity getThrower() {
      if ((this.owner == null || this.owner.removed) && this.ownerId != null && this.level instanceof ServerWorld) {
         Entity entity = ((ServerWorld)this.level).getEntity(this.ownerId);
         if (entity instanceof LivingEntity) {
            this.owner = (LivingEntity)entity;
         } else {
            this.owner = null;
         }
      }

      return this.owner;
   }
	
	
}