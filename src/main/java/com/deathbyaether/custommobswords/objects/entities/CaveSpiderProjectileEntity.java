package com.deathbyaether.custommobswords.objects.entities;

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
import net.minecraft.util.SoundEvents;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.EntityRayTraceResult;
import net.minecraft.util.math.RayTraceContext;
import net.minecraft.util.math.RayTraceResult;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;
import net.minecraftforge.fml.network.NetworkHooks;

public class CaveSpiderProjectileEntity extends ProjectileItemEntity {
    
    public CaveSpiderProjectileEntity(EntityType<SpiderWebProjectileEntity> type, World world) {
        super(type, world);
    }
    
    public CaveSpiderProjectileEntity(LivingEntity entity, World world) {
        super(EntityList.SPIDERWEB_PROJETILE.get(), entity, world);
    }
    
    public CaveSpiderProjectileEntity(double x, double y, double z, World world) {
        super(EntityList.SPIDERWEB_PROJETILE.get(), x, y, z, world);
    }
    
    @Override
    protected Item getDefaultItem() {
        return ItemList.CAVESPIDER_SWORD.get();
    }
    
    @Override
    public IPacket<?> createSpawnPacket() {
        return NetworkHooks.getEntitySpawningPacket(this);
    }

    @Override
    public void tick() {
        RayTraceResult raytraceresult = ProjectileHelper.rayTrace(this, true, false, this.owner, RayTraceContext.BlockMode.COLLIDER);
        if (raytraceresult.getType() != RayTraceResult.Type.MISS && !net.minecraftforge.event.ForgeEventFactory.onProjectileImpact(this, raytraceresult)) {
            this.onImpact(raytraceresult);
        }
        
        Vec3d vec3d1 = this.getMotion();
        this.setPosition(this.getPosX() + vec3d1.x, this.getPosY() + vec3d1.y, this.getPosZ() + vec3d1.z);
        ProjectileHelper.rotateTowardsMovement(this, 0.5F);
        
        if (this.world.isRemote) {
            this.world.addParticle(ParticleTypes.SPIT, this.getPosX() - vec3d1.x, this.getPosY() - vec3d1.y + 0.15D, this.getPosZ() - vec3d1.z, 0.0D, 0.0D, 0.0D);
        }
    }

    @Override
    protected void onImpact(RayTraceResult result) {
        if (!this.world.isRemote) { // Only process on the server-side
            BlockPos hitPos = null;

            if (result.getType() == RayTraceResult.Type.BLOCK) {
                // Hitting a block
                hitPos = new BlockPos(result.getHitVec());
            } else if (result.getType() == RayTraceResult.Type.ENTITY) {
                // Hitting an entity
                Entity entity = ((EntityRayTraceResult) result).getEntity();
                hitPos = entity.getPosition();

                // Apply slowness effect (simulating being stuck in a web)
                if (entity instanceof LivingEntity) {
                    ((LivingEntity) entity).addPotionEffect(new EffectInstance(Effects.SLOWNESS, 390, 1));
                    ((LivingEntity) entity).addPotionEffect(new EffectInstance(Effects.POISON, 390, 1));
                    ((LivingEntity) entity).addPotionEffect(new EffectInstance(Effects.BLINDNESS, 390, 1));
                }
                
                // Damage entity
                entity.attackEntityFrom(DamageSource.causeIndirectDamage(this, this.owner).setProjectile(), 4.0F);
            }

            if (hitPos != null) {
                // Ensure the block at the position is air before placing cobweb
                if (world.isAirBlock(hitPos)) {
                    world.setBlockState(hitPos, Blocks.COBWEB.getDefaultState()); // Spawns permanent cobweb
                }
            }

            // Play spider sound effect on impact
            this.playSound(SoundEvents.ENTITY_SPIDER_HURT, 1.0F, 1.0F);
            
            // Remove the projectile
            this.remove();
        }
    }
    
    @Override
    public boolean canBeCollidedWith() {
        return true;
    }

    @Override
    public boolean attackEntityFrom(DamageSource source, float amount) {
        return true;
    }
}
