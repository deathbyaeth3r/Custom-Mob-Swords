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

public class CarrotProjectileEntity extends ProjectileItemEntity {
    
    public CarrotProjectileEntity(EntityType<CarrotProjectileEntity> type, World world) {
        super(type, world);
    }

    public CarrotProjectileEntity(LivingEntity entity, World world) {
        super(EntityList.CARROT_PROJECTILE.get(), entity, world);
    }

    public CarrotProjectileEntity(double x, double y, double z, World world) {
        super(EntityList.CARROT_PROJECTILE.get(), x, y, z, world);
    }

    @Override
    public IPacket<?> createSpawnPacket() {
        return NetworkHooks.getEntitySpawningPacket(this);
    }

    @Override
    protected Item getDefaultItem() {
        return Items.CARROT;
    }

    @Override
    protected void onImpact(RayTraceResult result) {
        if (!world.isRemote) {
            if (result.getType() == RayTraceResult.Type.ENTITY) {
                EntityRayTraceResult entityResult = (EntityRayTraceResult) result;
                LivingEntity target = (LivingEntity) entityResult.getEntity();
                target.attackEntityFrom(DamageSource.causeThrownDamage(this, this.getThrower()), 3.0F);
                target.addPotionEffect(new EffectInstance(Effects.SLOWNESS, 100, 1)); // Apply Slowness
                this.playSound(SoundEvents.ENTITY_GENERIC_EAT, 1.0F, 1.0F);
            }
            this.remove();
        }
    }
}
