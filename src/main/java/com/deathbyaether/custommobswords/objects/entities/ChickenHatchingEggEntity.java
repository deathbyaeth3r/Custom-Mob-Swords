package com.deathbyaether.custommobswords.objects.entities;

import com.deathbyaether.custommobswords.list.EntityList;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.passive.ChickenEntity;
import net.minecraft.entity.projectile.ProjectileItemEntity;
import net.minecraft.item.Item;
import net.minecraft.item.Items;
import net.minecraft.network.IPacket;
import net.minecraft.util.SoundEvents;
import net.minecraft.util.math.RayTraceResult;
import net.minecraft.world.World;
import net.minecraftforge.fml.network.NetworkHooks;

public class ChickenHatchingEggEntity extends ProjectileItemEntity {
    
    public ChickenHatchingEggEntity(EntityType<ChickenHatchingEggEntity> type, World world) {
        super(type, world);
    }

    public ChickenHatchingEggEntity(LivingEntity entity, World world) {
        super(EntityList.CHICKENEGG_PROJECTILE.get(), entity, world);
    }

    public ChickenHatchingEggEntity(double x, double y, double z, World world) {
        super(EntityList.CHICKENEGG_PROJECTILE.get(), x, y, z, world);
    }

    @Override
    public IPacket<?> createSpawnPacket() {
        return NetworkHooks.getEntitySpawningPacket(this);
    }

    @Override
    protected Item getDefaultItem() {
        return Items.EGG;
    }

    @Override
    protected void onImpact(RayTraceResult result) {
        if (!world.isRemote) {
            spawnChicken();
        }
    }

    private void spawnChicken() {
        ChickenEntity chicken = new ChickenEntity(EntityType.CHICKEN, world);
        chicken.setPosition(this.getPosX(), this.getPosY(), this.getPosZ());
        world.addEntity(chicken);
        this.playSound(SoundEvents.ENTITY_CHICKEN_AMBIENT, 1.0F, 1.0F);
        this.remove();
    }
}
