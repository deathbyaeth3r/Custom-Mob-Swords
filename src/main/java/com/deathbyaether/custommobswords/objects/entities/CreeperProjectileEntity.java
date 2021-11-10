package com.deathbyaether.custommobswords.objects.entities;

import javax.annotation.Nullable;

import com.deathbyaether.custommobswords.list.EntityList;
import com.deathbyaether.custommobswords.list.ItemList;
import com.deathbyaether.custommobswords.objects.items.CreeperSwordItem;

import net.minecraft.block.Blocks;
import net.minecraft.client.renderer.Vector3d;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.monster.CreeperEntity;
import net.minecraft.entity.passive.CowEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.projectile.ProjectileItemEntity;
import net.minecraft.item.Item;
import net.minecraft.network.IPacket;
import net.minecraft.util.DamageSource;
import net.minecraft.util.Direction;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.SoundEvents;
import net.minecraft.util.math.BlockRayTraceResult;
import net.minecraft.util.math.EntityRayTraceResult;
import net.minecraft.util.math.RayTraceResult;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.Explosion;
import net.minecraft.world.World;
import net.minecraftforge.fml.RegistryObject;
import net.minecraftforge.fml.network.FMLPlayMessages.SpawnEntity;
import net.minecraftforge.fml.network.NetworkHooks;



public class CreeperProjectileEntity extends ProjectileItemEntity {

	
	public CreeperProjectileEntity(EntityType<CreeperProjectileEntity> type, World world) {
		super(type, world);
	}
	
	public CreeperProjectileEntity(LivingEntity entity, World world) {
		super(EntityList.CREEPER_PROJETILE.get(), entity, world);
	}
	
	public CreeperProjectileEntity(double x, double y, double z, World world) {
		super(EntityList.CREEPER_PROJETILE.get(), x, y, z, world);
	}
	
	
	@Override
	protected Item getDefaultItem() {
		return ItemList.CREEPER_SWORD.get().asItem();
	}
	
	
	@Override
	public IPacket<?> createSpawnPacket() {
		return NetworkHooks.getEntitySpawningPacket(this);
	}
	
	
	@Override
	protected void onImpact(RayTraceResult result) {
		
		if(result.getType() == RayTraceResult.Type.ENTITY) {
			
			Entity entity = ((EntityRayTraceResult)result).getEntity();
			CreeperEntity entityId = new CreeperEntity(EntityType.CREEPER, world);
			
			int damage;
			
		
			if(entity instanceof CreeperEntity) {
				
				damage = 0;
			} else {
				
				damage = 2;
				
				entityId.setPosition(entity.getPosX(), entity.getPosY(), entity.getPosZ());
				world.addEntity(entityId);
				
			}
			
			entity.attackEntityFrom(DamageSource.causeThrownDamage(this, this.getThrower()), (float)damage);
			
			
			if(!world.isRemote) {
				
				this.remove();
				
				
			}
		}
		
		if(result.getType() == RayTraceResult.Type.MISS) {
			
			world.playSound((PlayerEntity)this.getThrower(), this.getPosition(), SoundEvents.ENTITY_GENERIC_EXPLODE, SoundCategory.AMBIENT, 1.0F, 1.0F);
			if(!world.isRemote) {
				this.remove();
			}
		}
		
		if(!world.isRemote) {
			this.remove();
		}
		
	}
	
	
}