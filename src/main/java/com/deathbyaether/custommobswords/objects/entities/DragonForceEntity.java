package com.deathbyaether.custommobswords.objects.entities;

import java.util.List;

import javax.annotation.Nullable;

import com.deathbyaether.custommobswords.list.EntityList;
import com.deathbyaether.custommobswords.list.ItemList;

import net.minecraft.entity.AreaEffectCloudEntity;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.projectile.ProjectileHelper;
import net.minecraft.entity.projectile.ProjectileItemEntity;
import net.minecraft.item.Item;
import net.minecraft.network.IPacket;
import net.minecraft.particles.ParticleTypes;
import net.minecraft.potion.EffectInstance;
import net.minecraft.potion.Effects;
import net.minecraft.util.DamageSource;
import net.minecraft.util.Direction;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.SoundEvents;
import net.minecraft.util.math.EntityRayTraceResult;
import net.minecraft.util.math.RayTraceContext;
import net.minecraft.util.math.RayTraceResult;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;
import net.minecraft.world.server.ServerWorld;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.fml.network.NetworkHooks;

public class DragonForceEntity extends ProjectileItemEntity {
	
	private Entity target;
	@Nullable
	private Direction direction;
	private int steps;

	public DragonForceEntity(EntityType<DragonForceEntity> type, World world) {
		super(type, world);
	}
	
	public DragonForceEntity(LivingEntity entity, World world) {
		super(EntityList.DRAGONFORCE_PROJETILE.get(), entity, world);
	}
	
	 @OnlyIn(Dist.CLIENT)
	public DragonForceEntity(World worldIn, double x, double y, double z, double accelX, double accelY, double accelZ) {
		super(EntityList.DRAGONFORCE_PROJETILE.get(), x, y, z, worldIn);
	}

	@Override
	protected Item getDefaultItem() {
		// TODO Auto-generated method stub
		return ItemList.ENDERDRAGON_SWORD.get().asItem();
	}
	
	@Override
	public IPacket<?> createSpawnPacket() {
		return NetworkHooks.getEntitySpawningPacket(this);
	}

	@Override
	protected void onImpact(RayTraceResult result) {
		
		if(result.getType() == RayTraceResult.Type.ENTITY) {
			
			Entity entity = ((EntityRayTraceResult)result).getEntity();
			
		
		}

		if(result.getType() == RayTraceResult.Type.MISS) {
			
			if (!this.world.isRemote) {
	            List<LivingEntity> list = this.world.getEntitiesWithinAABB(LivingEntity.class, this.getBoundingBox().grow(4.0D, 2.0D, 4.0D));
	            AreaEffectCloudEntity areaeffectcloudentity = new AreaEffectCloudEntity(this.world, this.getPosX(), this.getPosY(), this.getPosZ());
	            areaeffectcloudentity.setParticleData(ParticleTypes.DRAGON_BREATH);
	            areaeffectcloudentity.setRadius(3.0F);
	            areaeffectcloudentity.setDuration(600);
	            areaeffectcloudentity.setRadiusPerTick((7.0F - areaeffectcloudentity.getRadius()) / (float)areaeffectcloudentity.getDuration());
	            areaeffectcloudentity.addEffect(new EffectInstance(Effects.INSTANT_DAMAGE, 1, 1));
	            if (!list.isEmpty()) {
	               for(LivingEntity livingentity : list) {
	                  double d0 = this.getDistanceSq(livingentity);
	                  if (d0 < 16.0D) {
	                     areaeffectcloudentity.setPosition(livingentity.getPosX(), livingentity.getPosY(), livingentity.getPosZ());
	                     break;
	                  }
	               }
	            }
			}
				if(!world.isRemote) {
					this.remove();
				}
				
				
			}
		if(!world.isRemote) {
			this.remove();
		}
	}
	

	public void tick() {
		
		RayTraceResult raytraceresult = ProjectileHelper.rayTrace(this, true, false, this.owner, RayTraceContext.BlockMode.COLLIDER);
        if (raytraceresult.getType() != RayTraceResult.Type.MISS && !net.minecraftforge.event.ForgeEventFactory.onProjectileImpact(this, raytraceresult)) {
        this.bulletHit(raytraceresult);
     }
        
        Vec3d vec3d1 = this.getMotion();
        this.setPosition(this.getPosX() + vec3d1.x, this.getPosY() + vec3d1.y, this.getPosZ() + vec3d1.z);
        ProjectileHelper.rotateTowardsMovement(this, 0.5F);
        if (this.world.isRemote) {
           this.world.addParticle(ParticleTypes.EXPLOSION, this.getPosX() - vec3d1.x, this.getPosY() - vec3d1.y + 0.15D, this.getPosZ() - vec3d1.z, 0.0D, 0.0D, 0.0D);
        }
	}
	
	protected void bulletHit(RayTraceResult result) {
	      if (result.getType() == RayTraceResult.Type.ENTITY) {
	         Entity entity = ((EntityRayTraceResult)result).getEntity();
	         boolean flag = entity.attackEntityFrom(DamageSource.causeIndirectDamage(this, this.owner).setProjectile(), 4.0F);
	         if (flag) {
	            this.applyEnchantments(this.owner, entity);
	            if (entity instanceof LivingEntity) {
	               ((LivingEntity)entity).addPotionEffect(new EffectInstance(Effects.LEVITATION, 200));
	               ((ServerWorld)this.world).spawnParticle(ParticleTypes.DRAGON_BREATH, this.getPosX(), this.getPosY(), this.getPosZ(), 2, 0.2D, 0.2D, 0.2D, 0.0D);
	            }
	         }
	      } else {
	         ((ServerWorld)this.world).spawnParticle(ParticleTypes.EXPLOSION, this.getPosX(), this.getPosY(), this.getPosZ(), 2, 0.2D, 0.2D, 0.2D, 0.0D);
	         
	      }
	      this.remove();
	}
	
	public boolean canBeCollidedWith() {
	      return true;
	   }

	public boolean attackEntityFrom(DamageSource source, float amount) {
		
		 return true;
	}


}




