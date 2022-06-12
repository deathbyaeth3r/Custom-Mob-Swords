package com.deathbyaether.custommobswords.list;

import com.deathbyaether.custommobswords.Main;
import com.deathbyaether.custommobswords.objects.entities.BeeStingProjectileEntity;
import com.deathbyaether.custommobswords.objects.entities.CreeperProjectileEntity;
import com.deathbyaether.custommobswords.objects.entities.DragonForceEntity;
import com.deathbyaether.custommobswords.objects.entities.SquidInkEntity;

import net.minecraft.entity.EntityClassification;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.projectile.DragonFireballEntity;
import net.minecraftforge.fml.RegistryObject;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;

public class EntityList {

	
	public static final DeferredRegister<EntityType<?>> ENTITIES = DeferredRegister.create(ForgeRegistries.ENTITIES, Main.MOD_ID);
	
	public static final RegistryObject<EntityType<CreeperProjectileEntity>> CREEPER_PROJETILE = ENTITIES.register("creeper_projectile", 
			() -> EntityType.Builder.<CreeperProjectileEntity>of(CreeperProjectileEntity::new, EntityClassification.MISC).sized(0.5F, 0.5F).build("creeper_projectile"));
	
	public static final RegistryObject<EntityType<DragonForceEntity>> DRAGONFORCE_PROJETILE = ENTITIES.register("dragonforce_projectile", 
			() -> EntityType.Builder.<DragonForceEntity>of(DragonForceEntity::new, EntityClassification.MISC).sized(0.5F, 0.5F).build("dragonforce_projectile"));
	
	public static final RegistryObject<EntityType<SquidInkEntity>> SQUIDINK_PROJETILE = ENTITIES.register("squidink_projectile", 
			() -> EntityType.Builder.<SquidInkEntity>of(SquidInkEntity::new, EntityClassification.MISC).sized(0.5F, 0.5F).build("squidink_projectile"));
	
	public static final RegistryObject<EntityType<BeeStingProjectileEntity>> BEESTING_PROJETILE = ENTITIES.register("beesting_projectile", 
			() -> EntityType.Builder.<BeeStingProjectileEntity>of(BeeStingProjectileEntity::new, EntityClassification.MISC).sized(0.5F, 0.5F).build("beesting_projectile"));
	
	
}
