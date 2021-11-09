package com.deathbyaether.custommobswords.list;

import com.deathbyaether.custommobswords.Main;
import com.deathbyaether.custommobswords.util.enums.ModItemTier;

import net.minecraft.item.Item;
import net.minecraft.item.ItemGroup;
import net.minecraft.item.SwordItem;
import net.minecraftforge.fml.RegistryObject;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;

public class ItemList
{
	public static final ItemGroup SwordTab = Main.SWORD_TAB;
	public static final DeferredRegister<Item> ITEMS = new DeferredRegister<Item>(ForgeRegistries.ITEMS, Main.MOD_ID);

	
	
	public static final RegistryObject<Item> SWORD_GEMSTONE = ITEMS.register("sword_gemstone", () -> new Item(new Item.Properties().group(SwordTab)));
	
	//passive + neutral mobs 
	public static final RegistryObject<SwordItem> OCELOT_SWORD = ITEMS.register("ocelot_sword", () -> new SwordItem(ModItemTier.SWORD_GEM, 0, 0, new Item.Properties().group(SwordTab)));
	public static final RegistryObject<SwordItem> CHICKEN_SWORD = ITEMS.register("chicken_sword", () -> new SwordItem(ModItemTier.SWORD_GEM, 0, 0, new Item.Properties().group(SwordTab)));
	public static final RegistryObject<SwordItem> PIG_SWORD = ITEMS.register("pig_sword", () -> new SwordItem(ModItemTier.SWORD_GEM, 0, 0, new Item.Properties().group(SwordTab)));
	public static final RegistryObject<SwordItem> SQUID_SWORD = ITEMS.register("squid_sword", () -> new SwordItem(ModItemTier.SWORD_GEM, 0, 0, new Item.Properties().group(SwordTab)));
	public static final RegistryObject<SwordItem> PUFFERFISH_SWORD = ITEMS.register("pufferfish_sword", () -> new SwordItem(ModItemTier.SWORD_GEM, 0, 0, new Item.Properties().group(SwordTab)));
	public static final RegistryObject<SwordItem> RABBIT_SWORD = ITEMS.register("rabbit_sword", () -> new SwordItem(ModItemTier.SWORD_GEM, 0, 0, new Item.Properties().group(SwordTab)));
	
	//aggressive mobs
	public static final RegistryObject<SwordItem> GHAST_SWORD = ITEMS.register("ghast_sword", () -> new SwordItem(ModItemTier.SWORD_GEM, 0, 0, new Item.Properties().group(SwordTab)));
	public static final RegistryObject<SwordItem> ZOMBIE_SWORD = ITEMS.register("zombie_sword", () -> new SwordItem(ModItemTier.SWORD_GEM, 0, 0, new Item.Properties().group(SwordTab)));
	public static final RegistryObject<SwordItem> SKELETON_SWORD = ITEMS.register("skeleton_sword", () -> new SwordItem(ModItemTier.SWORD_GEM, 0, 0, new Item.Properties().group(SwordTab)));
	public static final RegistryObject<SwordItem> SHULKER_SWORD = ITEMS.register("shulker_sword", () -> new SwordItem(ModItemTier.SWORD_GEM, 0, 0, new Item.Properties().group(SwordTab)));
	public static final RegistryObject<SwordItem> CREEPER_SWORD = ITEMS.register("creeper_sword", () -> new SwordItem(ModItemTier.SWORD_GEM, 0, 0, new Item.Properties().group(SwordTab)));
	public static final RegistryObject<SwordItem> ENDERMAN_SWORD = ITEMS.register("enderman_sword", () -> new SwordItem(ModItemTier.SWORD_GEM, 0, 0, new Item.Properties().group(SwordTab)));
	

	//boss mobs
	public static final RegistryObject<SwordItem> WITHER_SWORD = ITEMS.register("wither_sword", () -> new SwordItem(ModItemTier.SWORD_GEM, 0, 0, new Item.Properties().group(SwordTab)));
	public static final RegistryObject<SwordItem> ENDERDRAGON_SWORD = ITEMS.register("enderdragon_sword", () -> new SwordItem(ModItemTier.SWORD_GEM, 0, 0, new Item.Properties().group(SwordTab)));
	
	//special mobs
	public static final RegistryObject<SwordItem> BEE_SWORD = ITEMS.register("bee_sword", () -> new SwordItem(ModItemTier.SWORD_GEM, 0, 0, new Item.Properties().group(SwordTab)));
	public static final RegistryObject<SwordItem> DIAMONDCHICKEN_SWORD = ITEMS.register("diamondchicken_sword", () -> new SwordItem(ModItemTier.SWORD_GEM, 0, 0, new Item.Properties().group(SwordTab)));
	
	
	
	
}