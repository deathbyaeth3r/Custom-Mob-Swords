package com.deathbyaether.custommobswords.list;

import com.deathbyaether.custommobswords.Main;

import net.minecraft.item.Item;
import net.minecraftforge.fml.RegistryObject;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;

public class ItemList
{
	
	public static final DeferredRegister<Item> ITEMS = new DeferredRegister<Item>(ForgeRegistries.ITEMS, Main.MOD_ID);

	//passive + neutral mobs 
	public static final RegistryObject<Item> OCELOT_SWORD = ITEMS.register("ocelot_sword", () -> new Item(new Item.Properties().group(Main.SWORD_TAB)));
	public static final RegistryObject<Item> CHICKEN_SWORD = ITEMS.register("chicken_sword", () -> new Item(new Item.Properties().group(Main.SWORD_TAB)));
	public static final RegistryObject<Item> PIG_SWORD = ITEMS.register("pig_sword", () -> new Item(new Item.Properties().group(Main.SWORD_TAB)));
	public static final RegistryObject<Item> SQUID_SWORD = ITEMS.register("squid_sword", () -> new Item(new Item.Properties().group(Main.SWORD_TAB)));
	public static final RegistryObject<Item> PUFFERFISH_SWORD = ITEMS.register("pufferfish_sword", () -> new Item(new Item.Properties().group(Main.SWORD_TAB)));
	
	
	//aggressive mobs
	public static final RegistryObject<Item> GHAST_SWORD = ITEMS.register("ghast_sword", () -> new Item(new Item.Properties().group(Main.SWORD_TAB)));
	public static final RegistryObject<Item> ZOMBIE_SWORD = ITEMS.register("zombie_sword", () -> new Item(new Item.Properties().group(Main.SWORD_TAB)));
	public static final RegistryObject<Item> SKELETON_SWORD = ITEMS.register("skeleton_sword", () -> new Item(new Item.Properties().group(Main.SWORD_TAB)));
	public static final RegistryObject<Item> SHULKER_SWORD = ITEMS.register("shulker_sword", () -> new Item(new Item.Properties().group(Main.SWORD_TAB)));
	public static final RegistryObject<Item> CREEPER_SWORD = ITEMS.register("creeper_sword", () -> new Item(new Item.Properties().group(Main.SWORD_TAB)));
	public static final RegistryObject<Item> ENDERMAN_SWORD = ITEMS.register("enderman_sword", () -> new Item(new Item.Properties().group(Main.SWORD_TAB)));
	

	//boss mobs
	public static final RegistryObject<Item> WITHER_SWORD = ITEMS.register("wither_sword", () -> new Item(new Item.Properties().group(Main.SWORD_TAB)));
	public static final RegistryObject<Item> ENDERDRAGON_SWORD = ITEMS.register("enderdragon_sword", () -> new Item(new Item.Properties().group(Main.SWORD_TAB)));
	
	//special mobs
	public static final RegistryObject<Item> BEE_SWORD = ITEMS.register("bee_sword", () -> new Item(new Item.Properties().group(Main.SWORD_TAB)));
	public static final RegistryObject<Item> DIAMONDCHICKEN_SWORD = ITEMS.register("diamondchicken_sword", () -> new Item(new Item.Properties().group(Main.SWORD_TAB)));
	
}