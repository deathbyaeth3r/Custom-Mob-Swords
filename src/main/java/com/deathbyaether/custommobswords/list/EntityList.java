package com.deathbyaether.custommobswords.list;

import com.deathbyaether.custommobswords.Main;
import com.deathbyaether.custommobswords.objects.entities.CreeperProjectileEntity;

import net.minecraft.entity.EntityClassification;
import net.minecraft.entity.EntityType;
import net.minecraftforge.fml.RegistryObject;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;

public class EntityList {

	
	public static final DeferredRegister<EntityType<?>> ENTITIES = new DeferredRegister<>(ForgeRegistries.ENTITIES, Main.MOD_ID);
	
	public static final RegistryObject<EntityType<CreeperProjectileEntity>> CREEPER_PROJETILE = ENTITIES.register("creeper_projectile", 
			() -> EntityType.Builder.<CreeperProjectileEntity>create(CreeperProjectileEntity::new, EntityClassification.MISC).size(0.5F, 0.5F).build("creeper_projectile"));
}
