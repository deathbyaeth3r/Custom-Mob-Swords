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
import net.minecraftforge.fml.network.NetworkHooks;



public class CreeperProjectileEntity extends ProjectileItemEntity {
	
	   private static final DataParameter<Integer> STATE = EntityDataManager.createKey(CreeperEntity.class, DataSerializers.VARINT);
	   private static final DataParameter<Boolean> POWERED = EntityDataManager.createKey(CreeperEntity.class, DataSerializers.BOOLEAN);
	   private static final DataParameter<Boolean> IGNITED = EntityDataManager.createKey(CreeperEntity.class, DataSerializers.BOOLEAN);
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
	
	public CreeperProjectileEntity(double x, double y, double z, World world) {
		super(EntityList.CREEPER_PROJETILE.get(), x, y, z, world);
	}
	
	
	@Override
	protected Item getDefaultItem() {
		return ItemList.CREEPER_SWORD.get();
	}
	
	
	
	@Override
	public IPacket<?> createSpawnPacket() {
		return NetworkHooks.getEntitySpawningPacket(this);
	}
	

	   protected void registerData() {
	      super.registerData();
	      this.dataManager.register(STATE, -1);
	      this.dataManager.register(POWERED, false);
	      this.dataManager.register(IGNITED, false);
	   }

	   public void writeAdditional(CompoundNBT compound) {
	      super.writeAdditional(compound);
	      if (this.dataManager.get(POWERED)) {
	         compound.putBoolean("powered", true);
	      }

	      compound.putShort("Fuse", (short)this.fuseTime);
	      compound.putByte("ExplosionRadius", (byte)this.explosionRadius);
	      compound.putBoolean("ignited", this.hasIgnited());
	   }
	   
	   public boolean isCharged() {
		      return this.dataManager.get(POWERED);
		   }
	   
	   public boolean hasIgnited() {
		      return this.dataManager.get(IGNITED);
	
		   }
	   public void ignite() {
		      this.dataManager.set(IGNITED, true);
		   }

	   /**
	    * (abstract) Protected helper method to read subclass entity data from NBT.
	    */
	   public void readAdditional(CompoundNBT compound) {
	      super.readAdditional(compound);
	      this.dataManager.set(POWERED, compound.getBoolean("powered"));
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
	protected void onImpact(RayTraceResult result) {
		
		if(result.getType() == RayTraceResult.Type.ENTITY) {
			
			
			Entity entity = ((EntityRayTraceResult)result).getEntity();
			if (entity == null) return;
			CreeperEntity entityId = new CreeperEntity(EntityType.CREEPER, world);
			 
			
	
			int damage;
			
		
			if(entity instanceof CreeperEntity) {
				
				damage = 0;
				
			} else {
				
				damage = 2;
	
				entityId.setPosition(entity.getPosX(), entity.getPosY(), entity.getPosZ());
				
				
				world.addEntity(entityId);
				entityId.ignite();
	            
	          
				
			}
		
			
			entity.attackEntityFrom(DamageSource.causeThrownDamage(this, this.getThrower()), (float)damage);
			
			
			if(!world.isRemote) {
				
				this.remove();
				
				
			}
		}
		
		if(result.getType() == RayTraceResult.Type.MISS) {
			
		world.playSound((PlayerEntity)this.getThrower(), this.getPosition(), SoundEvents.ENTITY_GENERIC_EXPLODE, SoundCategory.AMBIENT, 1.0F, 1.0F);
			if(!world.isRemote) {
				this.remove();
			}
			
			
		}
		
		
		if(!world.isRemote) {
			this.remove();
		}
		
	}

	@Nullable
	   public LivingEntity getThrower() {
	      if ((this.owner == null || this.owner.removed) && this.ownerId != null && this.world instanceof ServerWorld) {
	         Entity entity = ((ServerWorld)this.world).getEntityByUuid(this.ownerId);
	         if (entity instanceof LivingEntity) {
	            this.owner = (LivingEntity)entity;
	         } else {
	            this.owner = null;
	         }
	      }

	      return this.owner;
	   }

	
	
}