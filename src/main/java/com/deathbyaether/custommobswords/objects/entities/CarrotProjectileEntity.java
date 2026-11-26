package com.deathbyaether.custommobswords.objects.entities;

import com.deathbyaether.custommobswords.list.EntityList;
import net.minecraft.entity.Entity;
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
	public CarrotProjectileEntity(EntityType<CarrotProjectileEntity> type, World world) { super(type, world); }
	public CarrotProjectileEntity(LivingEntity shooter, World world) { super(EntityList.CARROT_PROJECTILE.get(), shooter, world); }

	@Override protected Item getDefaultItem() { return Items.CARROT; }
	@Override public IPacket<?> getAddEntityPacket() { return NetworkHooks.getEntitySpawningPacket(this); }

	@Override
	protected void onHit(RayTraceResult result) {
		super.onHit(result);
		if (!this.level.isClientSide) {
			if (result.getType() == RayTraceResult.Type.ENTITY) {
				Entity hit = ((EntityRayTraceResult) result).getEntity();
				if (hit instanceof LivingEntity) {
					LivingEntity target = (LivingEntity) hit;
					target.hurt(DamageSource.thrown(this, this.getOwner()), 3.0F);
					target.addEffect(new EffectInstance(Effects.MOVEMENT_SLOWDOWN, 100, 1));
				}
				this.playSound(SoundEvents.GENERIC_EAT, 1.0F, 1.0F);
			}
			this.remove();
		}
	}
}
