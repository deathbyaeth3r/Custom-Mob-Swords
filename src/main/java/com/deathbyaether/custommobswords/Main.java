package com.deathbyaether.custommobswords;

import java.util.function.Supplier;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.deathbyaether.custommobswords.list.BlockList;
import com.deathbyaether.custommobswords.list.EntityList;
import com.deathbyaether.custommobswords.list.ItemList;
import com.deathbyaether.custommobswords.list.ParticleList;
import com.deathbyaether.custommobswords.objects.entities.renderer.BeeStingProjectileRenderer;
import com.deathbyaether.custommobswords.objects.entities.renderer.CreeperProjectileRenderer;
import com.deathbyaether.custommobswords.objects.entities.renderer.DragonForceRenderer;
import com.deathbyaether.custommobswords.objects.entities.renderer.SquidInkRenderer;
import com.deathbyaether.custommobswords.world.gen.GemstoneGeneration;

import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.ItemRenderer;
import net.minecraft.item.BlockItem;
import net.minecraft.item.Item;
import net.minecraft.item.ItemGroup;
import net.minecraft.item.ItemStack;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.DeferredWorkQueue;
import net.minecraftforge.fml.RegistryObject;
import net.minecraftforge.fml.client.registry.RenderingRegistry;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.Mod.EventBusSubscriber;
import net.minecraftforge.fml.common.Mod.EventBusSubscriber.Bus;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import net.minecraftforge.registries.IForgeRegistry;

@SuppressWarnings("deprecation")
@Mod(Main.MOD_ID)
@EventBusSubscriber(modid = Main.MOD_ID, bus = Bus.MOD)
public class Main
{
	
	public static Main instance;
	public static final String MOD_ID = "mobswords";
	public static final Logger LOGGER = LogManager.getLogger();
	public static final ItemGroup SWORD_TAB = new Main.ModItemGroup("swords_group");
	
	public Main()
	{
		instance = this;
		
		final IEventBus modEventBus = FMLJavaModLoadingContext.get().getModEventBus();
		
		
		modEventBus.addListener(this::setup);
		modEventBus.addListener(this::clientSetup);
		
		/* Register all of our deferred registries from our list/init classes, which get added to the IEventBus */
		ParticleList.PARTICLES.register(modEventBus);
		ItemList.ITEMS.register(modEventBus);
		BlockList.BLOCKS.register(modEventBus);
		BlockList.NO_ITEM_BLOCK.register(modEventBus);
		EntityList.ENTITIES.register(modEventBus);
	}
	
	
	
	@SubscribeEvent
	public static void createBlockItems(final RegistryEvent.Register<Item> event) {
		final IForgeRegistry<Item> registry = event.getRegistry();
		
		BlockList.BLOCKS.getEntries().stream().map(RegistryObject::get).forEach(block -> {
			final Item.Properties properties = new Item.Properties().group(SWORD_TAB);
			final BlockItem blockItem = new BlockItem(block, properties);
			blockItem.setRegistryName(block.getRegistryName());
			registry.register(blockItem);
		});
		
	}
	
	
	/* The FMLCommonSetupEvent (FML - Forge Mod Loader) */
	private void setup(final FMLCommonSetupEvent event)
	{
		
		DeferredWorkQueue.runLater(GemstoneGeneration::generate);
		
	}
	
	
	private void clientSetup(final FMLClientSetupEvent event)
	{	
		
		registerEntityModels(event.getMinecraftSupplier());	
		
		
	}
	
	private void registerEntityModels(Supplier<Minecraft> minecraft) {
		
		ItemRenderer renderer = minecraft.get().getItemRenderer();
		
		RenderingRegistry.registerEntityRenderingHandler(EntityList.CREEPER_PROJETILE.get(), CreeperProjectileRenderer::new);
		RenderingRegistry.registerEntityRenderingHandler(EntityList.DRAGONFORCE_PROJETILE.get(), DragonForceRenderer::new);
		RenderingRegistry.registerEntityRenderingHandler(EntityList.SQUIDINK_PROJETILE.get(), SquidInkRenderer::new);
		RenderingRegistry.registerEntityRenderingHandler(EntityList.BEESTING_PROJETILE.get(), BeeStingProjectileRenderer::new);
	}
	
	
	public static class ModItemGroup extends ItemGroup {

		public ModItemGroup(String name) {
			super(name);
		}

		@Override
		public ItemStack createIcon() {
			return new ItemStack(ItemList.BEE_SWORD.get());
		}
		
	}
	
	
}