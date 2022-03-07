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
import net.minecraft.util.math.Vec3d;
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
		
		 if (result.getType() == RayTraceResult.Type.ENTITY) {
	         Entity entity = ((EntityRayTraceResult)result).getEntity();
	         entity.attackEntityFrom(DamageSource.causeIndirectDamage(this, this.owner).setProjectile(), 4.0F);
	            this.applyEnchantments(this.owner, entity);
	           ((LivingEntity)entity).addPotionEffect(new EffectInstance(Effects.WITHER, 390));
	           ((LivingEntity)entity).addPotionEffect(new EffectInstance(Effects.WEAKNESS, 1300));
	           entity.setFire(7);
	           entity.addVelocity(0, 2, 0);
	           if(!world.isRemote) {
					this.remove();
				}
	              
		 }
	            if (result.getType() == RayTraceResult.Type.BLOCK) {
	            	
	            	BlockRayTraceResult blockRTR = (BlockRayTraceResult)result;
	            	if(blockRTR.getFace() == Direction.UP) {
	            	boolean flag = net.minecraftforge.event.ForgeEventFactory.getMobGriefingEvent(this.world, this.owner);	
	    			world.createExplosion((Entity)null, this.getPosX(), this.getPosY(), this.getPosZ(), (float)this.explosionPower, flag, flag ? Explosion.Mode.DESTROY : Explosion.Mode.NONE);
	    		
	             if(!world.isRemote) {
	    			this.remove();
	    				}
	            	 }
	              
	            if(!world.isRemote) {
	    					this.remove();
	    				}
	            }
	      }
	
		


}




