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
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;
import net.minecraftforge.fml.network.NetworkHooks;

//215963270

public class BatSwordProjectileEntity extends ProjectileItemEntity {

    public BatSwordProjectileEntity(EntityType<BatSwordProjectileEntity> type, World world) {
        super(type, world);
    }

    public BatSwordProjectileEntity(LivingEntity entity, World world) {
        super(EntityList.BAT_SWORD_PROJECTILE.get(), entity, world);
    }

    public BatSwordProjectileEntity(double x, double y, double z, World world) {
        super(EntityList.BAT_SWORD_PROJECTILE.get(), x, y, z, world);
    }

    @Override
    protected Item getDefaultItem() {
        return ItemList.BAT_SWORD.get();
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

        Vec3d motion = this.getMotion();
        this.setPosition(this.getPosX() + motion.x, this.getPosY() + motion.y, this.getPosZ() + motion.z);
        ProjectileHelper.rotateTowardsMovement(this, 0.5F);
        if (this.world.isRemote) {
            this.world.addParticle(ParticleTypes.END_ROD, this.getPosX() - motion.x, this.getPosY() - motion.y + 0.15D, this.getPosZ() - motion.z, 0.0D, 0.0D, 0.0D);
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
            Entity entity = ((EntityRayTraceResult) result).getEntity();
            entity.attackEntityFrom(DamageSource.causeIndirectDamage(this, this.owner).setProjectile(), 4.0F);
            ((LivingEntity) entity).addPotionEffect(new EffectInstance(Effects.GLOWING, 200)); // Glowing effect for 10 seconds
            this.playSound(SoundEvents.ENTITY_BAT_AMBIENT, 1.0F, 1.0F);
        }
        if (!world.isRemote) {
            this.remove();
        }
    }
}