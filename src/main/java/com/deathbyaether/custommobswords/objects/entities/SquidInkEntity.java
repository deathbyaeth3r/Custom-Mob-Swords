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
	
	public SquidInkEntity(double x, double y, double z, World world) {
		super(EntityList.SQUIDINK_PROJETILE.get(), x, y, z, world);
	}

	@Override
	protected Item getDefaultItem() {
		return ItemList.SQUID_SWORD.get();
	}
	
	@Override
	public IPacket<?> createSpawnPacket() {
		return NetworkHooks.getEntitySpawningPacket(this);
	}
	
	public void tick() {
		RayTraceResult raytraceresult = ProjectileHelper.func_234618_a_(this, this::func_230298_a_);
        if (raytraceresult.getType() != RayTraceResult.Type.MISS && !net.minecraftforge.event.ForgeEventFactory.onProjectileImpact(this, raytraceresult)) {
        this.onImpact(raytraceresult);
     }
       
        
        Vector3d vec3d1 = this.getMotion();
        this.setPosition(this.getPosX() + vec3d1.x, this.getPosY() + vec3d1.y, this.getPosZ() + vec3d1.z);
        ProjectileHelper.rotateTowardsMovement(this, 0.5F);
        if (this.world.isRemote) {
           this.world.addParticle(ParticleTypes.SQUID_INK, this.getPosX() - vec3d1.x, this.getPosY() - vec3d1.y + 0.15D, this.getPosZ() - vec3d1.z, 0.0D, 0.0D, 0.0D);
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
	           ((LivingEntity)entity).addPotionEffect(new EffectInstance(Effects.BLINDNESS, 700));
	           this.playSound(SoundEvents.ENTITY_PLAYER_HURT_DROWN, 1.0F, 1.0F);


	           if(!world.isRemote) {
					this.remove();
				}
	              
		 }
	}
}
