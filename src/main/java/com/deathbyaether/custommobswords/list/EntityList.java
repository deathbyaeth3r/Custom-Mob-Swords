package com.deathbyaether.custommobswords.list;

import com.deathbyaether.custommobswords.Main;
import com.deathbyaether.custommobswords.objects.entities.BatSwordProjectileEntity;
import com.deathbyaether.custommobswords.objects.entities.BeeStingProjectileEntity;
import com.deathbyaether.custommobswords.objects.entities.CarrotProjectileEntity;
import com.deathbyaether.custommobswords.objects.entities.ChickenEggProjectileEntity;
import com.deathbyaether.custommobswords.objects.entities.CreeperProjectileEntity;
import com.deathbyaether.custommobswords.objects.entities.DragonForceEntity;
import com.deathbyaether.custommobswords.objects.entities.MilkSplashProjectileEntity;
import com.deathbyaether.custommobswords.objects.entities.SpiderWebProjectileEntity;
import com.deathbyaether.custommobswords.objects.entities.SquidInkEntity;

import net.minecraft.entity.EntityClassification;
import net.minecraft.entity.EntityType;
import net.minecraftforge.fml.RegistryObject;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;

public class EntityList {

	
	public static final DeferredRegister<EntityType<?>> ENTITIES = new DeferredRegister<>(ForgeRegistries.ENTITIES, Main.MOD_ID);
	
	public static final RegistryObject<EntityType<CreeperProjectileEntity>> CREEPER_PROJETILE = ENTITIES.register("creeper_projectile", 
			() -> EntityType.Builder.<CreeperProjectileEntity>create(CreeperProjectileEntity::new, EntityClassification.MISC).size(0.5F, 0.5F).build("creeper_projectile"));
	
	public static final RegistryObject<EntityType<DragonForceEntity>> DRAGONFORCE_PROJETILE = ENTITIES.register("dragonforce_projectile", 
			() -> EntityType.Builder.<DragonForceEntity>create(DragonForceEntity::new, EntityClassification.MISC).size(0.5F, 0.5F).build("dragonforce_projectile"));
	
	public static final RegistryObject<EntityType<SquidInkEntity>> SQUIDINK_PROJETILE = ENTITIES.register("squidink_projectile", 
			() -> EntityType.Builder.<SquidInkEntity>create(SquidInkEntity::new, EntityClassification.MISC).size(0.5F, 0.5F).build("squidink_projectile"));
	
	public static final RegistryObject<EntityType<BeeStingProjectileEntity>> BEESTING_PROJETILE = ENTITIES.register("beesting_projectile", 
			() -> EntityType.Builder.<BeeStingProjectileEntity>create(BeeStingProjectileEntity::new, EntityClassification.MISC).size(0.5F, 0.5F).build("beesting_projectile"));
	
	public static final RegistryObject<EntityType<SpiderWebProjectileEntity>> SPIDERWEB_PROJETILE = ENTITIES.register("spiderweb_projectile", 
			() -> EntityType.Builder.<SpiderWebProjectileEntity>create(SpiderWebProjectileEntity::new, EntityClassification.MISC).size(0.5F, 0.5F).build("spiderweb_projectile"));

	public static final RegistryObject<EntityType<BatSwordProjectileEntity>> BAT_SWORD_PROJECTILE = ENTITIES.register("batsword_projectile", 
			() -> EntityType.Builder.<BatSwordProjectileEntity>create(BatSwordProjectileEntity::new, EntityClassification.MISC).size(0.5F, 0.5F).build("batsword_projectile"));
	
	public static final RegistryObject<EntityType<ChickenEggProjectileEntity>> CHICKENEGG_PROJECTILE = ENTITIES.register("chickenegg_projectile", 
			() -> EntityType.Builder.<ChickenEggProjectileEntity>create(ChickenEggProjectileEntity::new, EntityClassification.MISC).size(0.5F, 0.5F).build("chickenegg_projectile"));
	
	public static final RegistryObject<EntityType<MilkSplashProjectileEntity>> MILK_SPLASH_PROJECTILE = ENTITIES.register("milk_projectile", 
			() -> EntityType.Builder.<MilkSplashProjectileEntity>create(MilkSplashProjectileEntity::new, EntityClassification.MISC).size(0.5F, 0.5F).build("milk_projectile"));
	
	public static final RegistryObject<EntityType<CarrotProjectileEntity>> CARROT_PROJECTILE = ENTITIES.register("carrot_projectile", 
			() -> EntityType.Builder.<CarrotProjectileEntity>create(CarrotProjectileEntity::new, EntityClassification.MISC).size(0.5F, 0.5F).build("carrot_projectile"));
	
	
}
