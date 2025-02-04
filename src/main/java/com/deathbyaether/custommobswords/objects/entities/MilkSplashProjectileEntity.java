package com.deathbyaether.custommobswords.objects.entities;

import com.deathbyaether.custommobswords.list.EntityList;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.projectile.ProjectileItemEntity;
import net.minecraft.item.Item;
import net.minecraft.item.Items;
import net.minecraft.network.IPacket;
import net.minecraft.potion.EffectInstance;
import net.minecraft.potion.Effects;
import net.minecraft.util.DamageSource;
import net.minecraft.util.SoundEvents;
import net.minecraft.util.math.EntityRayTraceResult;
import net.minecraft.util.math.RayTraceResult;
import net.minecraft.world.World;
import net.minecraftforge.fml.network.NetworkHooks;

public class MilkSplashProjectileEntity extends ProjectileItemEntity {
    
    public MilkSplashProjectileEntity(EntityType<MilkSplashProjectileEntity> type, World world) {
        super(type, world);
    }

    public MilkSplashProjectileEntity(LivingEntity entity, World world) {
        super(EntityList.MILK_SPLASH_PROJECTILE.get(), entity, world);
    }

    public MilkSplashProjectileEntity(double x, double y, double z, World world) {
        super(EntityList.MILK_SPLASH_PROJECTILE.get(), x, y, z, world);
    }

    @Override
    public IPacket<?> createSpawnPacket() {
        return NetworkHooks.getEntitySpawningPacket(this);
    }

    @Override
    protected Item getDefaultItem() {
        return Items.MILK_BUCKET;
    }

    @Override
    protected void onImpact(RayTraceResult result) {
        if (!world.isRemote) {
            if (result.getType() == RayTraceResult.Type.ENTITY) {
                EntityRayTraceResult entityResult = (EntityRayTraceResult) result;
                LivingEntity target = (LivingEntity) entityResult.getEntity();
                target.attackEntityFrom(DamageSource.causeThrownDamage(this, this.getThrower()), 2.0F);
                target.addPotionEffect(new EffectInstance(Effects.SLOWNESS, 100, 1)); // Apply slowness
                this.playSound(SoundEvents.ENTITY_COW_MILK, 1.0F, 1.0F);
            }
            this.remove();
        }
    }
}
