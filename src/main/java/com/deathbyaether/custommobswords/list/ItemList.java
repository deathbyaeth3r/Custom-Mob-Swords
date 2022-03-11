package com.deathbyaether.custommobswords.list;

import com.deathbyaether.custommobswords.Main;
import com.deathbyaether.custommobswords.objects.items.BeeSwordItem;
import com.deathbyaether.custommobswords.objects.items.CreeperSwordItem;
import com.deathbyaether.custommobswords.objects.items.EnderdragonSwordItem;
import com.deathbyaether.custommobswords.objects.items.EndermanSwordItem;
import com.deathbyaether.custommobswords.objects.items.GhastSwordItem;
import com.deathbyaether.custommobswords.objects.items.PufferfishSwordItem;
import com.deathbyaether.custommobswords.objects.items.SkeletonSwordItem;
import com.deathbyaether.custommobswords.objects.items.SquidSwordItem;
import com.deathbyaether.custommobswords.objects.items.WitherSwordItem;
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
	public static final RegistryObject<SquidSwordItem> SQUID_SWORD = ITEMS.register("squid_sword", () -> new SquidSwordItem(ModItemTier.SWORD_GEM, 0, 0, new Item.Properties().group(SwordTab)));
	public static final RegistryObject<PufferfishSwordItem> PUFFERFISH_SWORD = ITEMS.register("pufferfish_sword", () -> new PufferfishSwordItem(ModItemTier.SWORD_GEM, 0, 0, new Item.Properties().group(SwordTab)));
	
	
	//aggressive mobs
	public static final RegistryObject<GhastSwordItem> GHAST_SWORD = ITEMS.register("ghast_sword", () -> new GhastSwordItem(ModItemTier.SWORD_GEM, 0, 0, new Item.Properties().group(SwordTab)));
	public static final RegistryObject<SkeletonSwordItem> SKELETON_SWORD = ITEMS.register("skeleton_sword", () -> new SkeletonSwordItem(ModItemTier.SWORD_GEM, 0, 0, new Item.Properties().group(SwordTab)));
	//public static final RegistryObject<ShulkerSwordItem> SHULKER_SWORD = ITEMS.register("shulker_sword", () -> new ShulkerSwordItem(ModItemTier.SWORD_GEM, 0, 0, new Item.Properties().group(SwordTab)));
	public static final RegistryObject<CreeperSwordItem> CREEPER_SWORD = ITEMS.register("creeper_sword", () -> new CreeperSwordItem(ModItemTier.SWORD_GEM, 0, 0, new Item.Properties().group(SwordTab)));
	public static final RegistryObject<EndermanSwordItem> ENDERMAN_SWORD = ITEMS.register("enderman_sword", () -> new EndermanSwordItem(ModItemTier.SWORD_GEM, 0, 0, new Item.Properties().group(SwordTab)));
	

	//boss mobs
	public static final RegistryObject<WitherSwordItem> WITHER_SWORD = ITEMS.register("wither_sword", () -> new WitherSwordItem(ModItemTier.SWORD_GEM, 0, 0, new Item.Properties().group(SwordTab)));
	public static final RegistryObject<EnderdragonSwordItem> ENDERDRAGON_SWORD = ITEMS.register("enderdragon_sword", () -> new EnderdragonSwordItem(ModItemTier.SWORD_GEM, 0, 0, new Item.Properties().group(SwordTab)));
	
	//special mobs
	public static final RegistryObject<BeeSwordItem> BEE_SWORD = ITEMS.register("bee_sword", () -> new BeeSwordItem(ModItemTier.SWORD_GEM, 0, 0, new Item.Properties().group(SwordTab)));
	
	
	
	
}