package com.deathbyaether.custommobswords.objects.entities;

import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.projectile.ProjectileHelper;
import net.minecraft.entity.projectile.ProjectileItemEntity;
import net.minecraft.item.Item;
import net.minecraft.item.Items;
import net.minecraft.network.IPacket;
import net.minecraft.particles.ParticleTypes;
import net.minecraft.util.DamageSource;
import net.minecraft.util.SoundEvents;
import net.minecraft.util.math.EntityRayTraceResult;
import net.minecraft.util.math.RayTraceContext;
import net.minecraft.util.math.RayTraceResult;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;
import net.minecraftforge.fml.network.NetworkHooks;

public class ChickenEggProjectileEntity extends ProjectileItemEntity {
    
    public ChickenEggProjectileEntity(EntityType<ChickenEggProjectileEntity> type, World world) {
        super(type, world);
    }

    public ChickenEggProjectileEntity(LivingEntity entity, World world) {
        super(EntityType.EGG, entity, world);
    }

    public ChickenEggProjectileEntity(double x, double y, double z, World world) {
        super(EntityType.EGG, x, y, z, world);
    }

    @Override
    protected Item getDefaultItem() {
        return Items.EGG;
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
           this.world.addParticle(ParticleTypes.CLOUD, this.getPosX() - vec3d1.x, this.getPosY() - vec3d1.y + 0.15D, this.getPosZ() - vec3d1.z, 0.0D, 0.0D, 0.0D);
        }
	}

    @Override
    protected void onImpact(RayTraceResult result) {
        if (result.getType() == RayTraceResult.Type.ENTITY) {
            Entity entity = ((EntityRayTraceResult) result).getEntity();
            entity.attackEntityFrom(DamageSource.causeThrownDamage(this, this.getThrower()), 2.0F);
            
            // Launch entity into the air
            entity.setMotion(entity.getMotion().x, 2, entity.getMotion().z);
            entity.velocityChanged = true;
            
            this.playSound(SoundEvents.ENTITY_CHICKEN_AMBIENT, 1.0F, 1.0F);
        }
        if (!world.isRemote) {
            this.remove();
        }
    }
}
