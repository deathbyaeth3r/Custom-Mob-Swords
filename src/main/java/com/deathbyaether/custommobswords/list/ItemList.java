
package com.deathbyaether.custommobswords.list;

import com.deathbyaether.custommobswords.Main;
import com.deathbyaether.custommobswords.objects.items.BatSwordItem;
import com.deathbyaether.custommobswords.objects.items.BeeSwordItem;
import com.deathbyaether.custommobswords.objects.items.BlazeSwordItem;
import com.deathbyaether.custommobswords.objects.items.CaveSpiderSwordItem;
import com.deathbyaether.custommobswords.objects.items.ChickenSwordItem;
import com.deathbyaether.custommobswords.objects.items.CowSwordItem;
import com.deathbyaether.custommobswords.objects.items.CreeperSwordItem;
import com.deathbyaether.custommobswords.objects.items.DonkeySwordItem;
import com.deathbyaether.custommobswords.objects.items.ElderGuardianSwordItem;
import com.deathbyaether.custommobswords.objects.items.EnderdragonSwordItem;
import com.deathbyaether.custommobswords.objects.items.EndermanSwordItem;
import com.deathbyaether.custommobswords.objects.items.EndermiteSwordItem;
import com.deathbyaether.custommobswords.objects.items.GhastSwordItem;
import com.deathbyaether.custommobswords.objects.items.GuardianSwordItem;
import com.deathbyaether.custommobswords.objects.items.HorseSwordItem;
import com.deathbyaether.custommobswords.objects.items.HuskSwordItem;
import com.deathbyaether.custommobswords.objects.items.IronGolemSwordItem;
import com.deathbyaether.custommobswords.objects.items.MagmaCubeSwordItem;
import com.deathbyaether.custommobswords.objects.items.MooshroomSwordItem;
import com.deathbyaether.custommobswords.objects.items.MuleSwordItem;
import com.deathbyaether.custommobswords.objects.items.OcelotSwordItem;
import com.deathbyaether.custommobswords.objects.items.PigSwordItem;
import com.deathbyaether.custommobswords.objects.items.PolarBearSwordItem;
import com.deathbyaether.custommobswords.objects.items.PufferfishSwordItem;
import com.deathbyaether.custommobswords.objects.items.RabbitSwordItem;
import com.deathbyaether.custommobswords.objects.items.SheepSwordItem;
import com.deathbyaether.custommobswords.objects.items.ShulkerSwordItem;
import com.deathbyaether.custommobswords.objects.items.SilverFishSwordItem;
import com.deathbyaether.custommobswords.objects.items.SkeletonSwordItem;
import com.deathbyaether.custommobswords.objects.items.SnowGolemSwordItem;
import com.deathbyaether.custommobswords.objects.items.SpiderSwordItem;
import com.deathbyaether.custommobswords.objects.items.SquidSwordItem;
import com.deathbyaether.custommobswords.objects.items.StraySwordItem;
import com.deathbyaether.custommobswords.objects.items.VillagerSwordItem;
import com.deathbyaether.custommobswords.objects.items.WitchSwordItem;
import com.deathbyaether.custommobswords.objects.items.WitherSkeletonSwordItem;
import com.deathbyaether.custommobswords.objects.items.WitherSwordItem;
import com.deathbyaether.custommobswords.objects.items.WolfSwordItem;
import com.deathbyaether.custommobswords.objects.items.ZombieSwordItem;
import com.deathbyaether.custommobswords.objects.items.ZombieVillagerSwordItem;
import com.deathbyaether.custommobswords.util.enums.ModItemTier;

import net.minecraft.item.Item;
import net.minecraft.item.ItemGroup;
import net.minecraftforge.fml.RegistryObject;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;

public class ItemList 
{
	public static final ItemGroup SwordTab = Main.SWORD_TAB;
	public static final DeferredRegister<Item> ITEMS = DeferredRegister.create(ForgeRegistries.ITEMS, Main.MOD_ID);

	
	
	public static final RegistryObject<Item> SWORD_GEMSTONE = ITEMS.register("sword_gemstone", () -> new Item(new Item.Properties().tab(SwordTab)));
	
	//passive + neutral mobs 
	
	public static final RegistryObject<SquidSwordItem> SQUID_SWORD = ITEMS.register("squid_sword", () -> new SquidSwordItem(ModItemTier.SWORD_GEM, 0, 0, new Item.Properties().tab(SwordTab)));
	public static final RegistryObject<PufferfishSwordItem> PUFFERFISH_SWORD = ITEMS.register("pufferfish_sword", () -> new PufferfishSwordItem(ModItemTier.SWORD_GEM, 0, 0, new Item.Properties().tab(SwordTab)));
	//public static final RegistryObject<SheepSwordItem> SHEEP_SWORD = ITEMS.register("sheep_sword", () -> new SheepSwordItem(ModItemTier.SWORD_GEM, 0, 0, new Item.Properties().tab(SwordTab)));
	//public static final RegistryObject<CowSwordItem> COW_SWORD = ITEMS.register("cow_sword", () -> new CowSwordItem(ModItemTier.SWORD_GEM, 0, 0, new Item.Properties().tab(SwordTab)));
	//public static final RegistryObject<MooshroomSwordItem> MOOSHROOM_SWORD = ITEMS.register("mooshroom_sword", () -> new MooshroomSwordItem(ModItemTier.SWORD_GEM, 0, 0, new Item.Properties().tab(SwordTab)));
	//public static final RegistryObject<WolfSwordItem> WOLF_SWORD = ITEMS.register("wolf_sword", () -> new WolfSwordItem(ModItemTier.SWORD_GEM, 0, 0, new Item.Properties().tab(SwordTab)));
	//public static final RegistryObject<ChickenSwordItem> CHICKEN_SWORD = ITEMS.register("chicken_sword", () -> new ChickenSwordItem(ModItemTier.SWORD_GEM, 0, 0, new Item.Properties().tab(SwordTab)));
	//public static final RegistryObject<SnowGolemSwordItem> SNOWGOLEM_SWORD = ITEMS.register("snowgolem_sword", () -> new SnowGolemSwordItem(ModItemTier.SWORD_GEM, 0, 0, new Item.Properties().tab(SwordTab)));
	public static final RegistryObject<BeeSwordItem> BEE_SWORD = ITEMS.register("bee_sword", () -> new BeeSwordItem(ModItemTier.SWORD_GEM, 0, 0, new Item.Properties().tab(SwordTab)));
	//public static final RegistryObject<OcelotSwordItem> OCELOT_SWORD = ITEMS.register("ocelot_sword", () -> new OcelotSwordItem(ModItemTier.SWORD_GEM, 0, 0, new Item.Properties().tab(SwordTab)));
	//public static final RegistryObject<IronGolemSwordItem> IRONGOLEM_SWORD = ITEMS.register("irongolem_sword", () -> new IronGolemSwordItem(ModItemTier.SWORD_GEM, 0, 0, new Item.Properties().tab(SwordTab)));
	//public static final RegistryObject<BatSwordItem> BAT_SWORD = ITEMS.register("bat_sword", () -> new BatSwordItem(ModItemTier.SWORD_GEM, 0, 0, new Item.Properties().tab(SwordTab)));
	//public static final RegistryObject<DonkeySwordItem> DONKEY_SWORD = ITEMS.register("donkey_sword", () -> new DonkeySwordItem(ModItemTier.SWORD_GEM, 0, 0, new Item.Properties().tab(SwordTab)));
	//public static final RegistryObject<HorseSwordItem> HORSE_SWORD = ITEMS.register("horse_sword", () -> new HorseSwordItem(ModItemTier.SWORD_GEM, 0, 0, new Item.Properties().tab(SwordTab)));
	//public static final RegistryObject<MuleSwordItem> MULE_SWORD = ITEMS.register("mule_sword", () -> new MuleSwordItem(ModItemTier.SWORD_GEM, 0, 0, new Item.Properties().tab(SwordTab)));
	//public static final RegistryObject<RabbitSwordItem> RABBIT_SWORD = ITEMS.register("rabbit_sword", () -> new RabbitSwordItem(ModItemTier.SWORD_GEM, 0, 0, new Item.Properties().tab(SwordTab)));
	//public static final RegistryObject<PolarBearSwordItem> POLARBEAR_SWORD = ITEMS.register("polarbear_sword", () -> new PolarBearSwordItem(ModItemTier.SWORD_GEM, 0, 0, new Item.Properties().tab(SwordTab)));
	
	
	
	//aggressive mobs
	
	public static final RegistryObject<GhastSwordItem> GHAST_SWORD = ITEMS.register("ghast_sword", () -> new GhastSwordItem(ModItemTier.SWORD_GEM, 0, 0, new Item.Properties().tab(SwordTab)));
	public static final RegistryObject<SkeletonSwordItem> SKELETON_SWORD = ITEMS.register("skeleton_sword", () -> new SkeletonSwordItem(ModItemTier.SWORD_GEM, 0, 0, new Item.Properties().tab(SwordTab)));
	public static final RegistryObject<ShulkerSwordItem> SHULKER_SWORD = ITEMS.register("shulker_sword", () -> new ShulkerSwordItem(ModItemTier.SWORD_GEM, 0, 0, new Item.Properties().tab(SwordTab)));
	public static final RegistryObject<CreeperSwordItem> CREEPER_SWORD = ITEMS.register("creeper_sword", () -> new CreeperSwordItem(ModItemTier.SWORD_GEM, 0, 0, new Item.Properties().tab(SwordTab)));
	public static final RegistryObject<EndermanSwordItem> ENDERMAN_SWORD = ITEMS.register("enderman_sword", () -> new EndermanSwordItem(ModItemTier.SWORD_GEM, 0, 0, new Item.Properties().tab(SwordTab)));
	//public static final RegistryObject<BlazeSwordItem> BLAZE_SWORD = ITEMS.register("blaze_sword", () -> new BlazeSwordItem(ModItemTier.SWORD_GEM, 0, 0, new Item.Properties().tab(SwordTab)));
	//public static final RegistryObject<CaveSpiderSwordItem> CAVESPIDER_SWORD = ITEMS.register("cavespider_sword", () -> new CaveSpiderSwordItem(ModItemTier.SWORD_GEM, 0, 0, new Item.Properties().tab(SwordTab)));
	//public static final RegistryObject<MagmaCubeSwordItem> MAGMACUBE_SWORD = ITEMS.register("magmacube_sword", () -> new MagmaCubeSwordItem(ModItemTier.SWORD_GEM, 0, 0, new Item.Properties().tab(SwordTab)));
	//public static final RegistryObject<SilverFishSwordItem> SILVERFISH_SWORD = ITEMS.register("silverfish_sword", () -> new SilverFishSwordItem(ModItemTier.SWORD_GEM, 0, 0, new Item.Properties().tab(SwordTab)));
	//public static final RegistryObject<SpiderSwordItem> SPIDER_SWORD = ITEMS.register("spider_sword", () -> new SpiderSwordItem(ModItemTier.SWORD_GEM, 0, 0, new Item.Properties().tab(SwordTab)));
	//public static final RegistryObject<ZombieSwordItem> ZOMBIE_SWORD = ITEMS.register("zombie_sword", () -> new ZombieSwordItem(ModItemTier.SWORD_GEM, 0, 0, new Item.Properties().tab(SwordTab)));
	//public static final RegistryObject<WitchSwordItem> WITCH_SWORD = ITEMS.register("witch_sword", () -> new WitchSwordItem(ModItemTier.SWORD_GEM, 0, 0, new Item.Properties().tab(SwordTab)));
	//public static final RegistryObject<WitherSkeletonSwordItem> WITHERSKELETON_SWORD = ITEMS.register("witherskeleton_sword", () -> new WitherSkeletonSwordItem(ModItemTier.SWORD_GEM, 0, 0, new Item.Properties().tab(SwordTab)));
	//public static final RegistryObject<EndermiteSwordItem> ENDERMITE_SWORD = ITEMS.register("endermite_sword", () -> new EndermiteSwordItem(ModItemTier.SWORD_GEM, 0, 0, new Item.Properties().tab(SwordTab)));
	//public static final RegistryObject<GuardianSwordItem> GUARDIAN_SWORD = ITEMS.register("guardian_sword", () -> new GuardianSwordItem(ModItemTier.SWORD_GEM, 0, 0, new Item.Properties().tab(SwordTab)));
	//public static final RegistryObject<ElderGuardianSwordItem> ELDERGUARDIAN_SWORD = ITEMS.register("elderguardian_sword", () -> new ElderGuardianSwordItem(ModItemTier.SWORD_GEM, 0, 0, new Item.Properties().tab(SwordTab)));
	//public static final RegistryObject<HuskSwordItem> HUSK_SWORD = ITEMS.register("husk_sword", () -> new HuskSwordItem(ModItemTier.SWORD_GEM, 0, 0, new Item.Properties().tab(SwordTab)));
	//public static final RegistryObject<StraySwordItem> STRAY_SWORD = ITEMS.register("stray_sword", () -> new StraySwordItem(ModItemTier.SWORD_GEM, 0, 0, new Item.Properties().tab(SwordTab)));
	
	
	//boss mobs
	
	public static final RegistryObject<WitherSwordItem> WITHER_SWORD = ITEMS.register("wither_sword", () -> new WitherSwordItem(ModItemTier.SWORD_GEM, 0, 0, new Item.Properties().tab(SwordTab)));
	public static final RegistryObject<EnderdragonSwordItem> ENDERDRAGON_SWORD = ITEMS.register("enderdragon_sword", () -> new EnderdragonSwordItem(ModItemTier.SWORD_GEM, 0, 0, new Item.Properties().tab(SwordTab)));
	
	//special mobs
	
	//public static final RegistryObject<VillagerSwordItem> VILLAGER_SWORD = ITEMS.register("villager_sword", () -> new VillagerSwordItem(ModItemTier.SWORD_GEM, 0, 0, new Item.Properties().tab(SwordTab)));
	//public static final RegistryObject<PigSwordItem> PIG_SWORD = ITEMS.register("pig_sword", () -> new PigSwordItem(ModItemTier.SWORD_GEM, 0, 0, new Item.Properties().tab(SwordTab)));
	//public static final RegistryObject<ZombieVillagerSwordItem> ZOMBIEVILLAGER_SWORD = ITEMS.register("zombievillager_sword", () -> new ZombieVillagerSwordItem(ModItemTier.SWORD_GEM, 0, 0, new Item.Properties().tab(SwordTab)));
	
}