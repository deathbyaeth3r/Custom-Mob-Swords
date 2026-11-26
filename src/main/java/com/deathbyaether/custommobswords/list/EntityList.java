package com.deathbyaether.custommobswords.list;

import com.deathbyaether.custommobswords.Main;
import com.deathbyaether.custommobswords.objects.entities.BeeStingProjectileEntity;
import com.deathbyaether.custommobswords.objects.entities.CarrotProjectileEntity;
import com.deathbyaether.custommobswords.objects.entities.CaveSpiderProjectileEntity;
import com.deathbyaether.custommobswords.objects.entities.CreeperProjectileEntity;
import com.deathbyaether.custommobswords.objects.entities.DragonForceEntity;
import com.deathbyaether.custommobswords.objects.entities.GuardianLaserBeamEntity;
import com.deathbyaether.custommobswords.objects.entities.SquidInkEntity;

import net.minecraft.entity.EntityClassification;
import net.minecraft.entity.EntityType;
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
	
	public static final RegistryObject<EntityType<CaveSpiderProjectileEntity>> CAVESPIDER_PROJETILE = ENTITIES.register("cavespider_projectile", 
			() -> EntityType.Builder.<CaveSpiderProjectileEntity>of(CaveSpiderProjectileEntity::new, EntityClassification.MISC).sized(0.5F, 0.5F).build("cavespider_projectile"));

	public static final RegistryObject<EntityType<CarrotProjectileEntity>> CARROT_PROJECTILE = ENTITIES.register("carrot_projectile",
			() -> EntityType.Builder.<CarrotProjectileEntity>of(CarrotProjectileEntity::new, EntityClassification.MISC).sized(0.25F, 0.25F).clientTrackingRange(64).updateInterval(1).build("carrot_projectile"));

	public static final RegistryObject<EntityType<GuardianLaserBeamEntity>> GUARDIAN_LASER_BEAM = ENTITIES.register("guardian_laser_beam",
			() -> EntityType.Builder.<GuardianLaserBeamEntity>of(GuardianLaserBeamEntity::new, EntityClassification.MISC).sized(0.25F, 0.25F).clientTrackingRange(64).updateInterval(1).build("guardian_laser_beam"));
}
