package com.deathbyaether.custommobswords.util;

import com.deathbyaether.custommobswords.Main;
import com.deathbyaether.custommobswords.list.EntityList;
import com.deathbyaether.custommobswords.objects.entities.renderer.BatSwordProjectileRenderer;
import com.deathbyaether.custommobswords.objects.entities.renderer.BeeStingProjectileRenderer;
import com.deathbyaether.custommobswords.objects.entities.renderer.CarrotProjectileRenderer;
import com.deathbyaether.custommobswords.objects.entities.renderer.ChickenEggProjectileRenderer;
import com.deathbyaether.custommobswords.objects.entities.renderer.CreeperProjectileRenderer;
import com.deathbyaether.custommobswords.objects.entities.renderer.DragonForceRenderer;
import com.deathbyaether.custommobswords.objects.entities.renderer.MilkSplashProjectileRenderer;
import com.deathbyaether.custommobswords.objects.entities.renderer.SpiderWebProjectileRenderer;
import com.deathbyaether.custommobswords.objects.entities.renderer.SquidInkRenderer;

import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.Mod.EventBusSubscriber.Bus;
import net.minecraftforge.fml.client.registry.RenderingRegistry;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;


    @Mod.EventBusSubscriber(modid = Main.MOD_ID, bus = Bus.MOD, value = Dist.CLIENT)
	public class ClientEventBusSubscriber {

		@SubscribeEvent
		public static void clientSetup(FMLClientSetupEvent event) {
			RenderingRegistry.registerEntityRenderingHandler(EntityList.CREEPER_PROJETILE.get(), CreeperProjectileRenderer::new);
			RenderingRegistry.registerEntityRenderingHandler(EntityList.DRAGONFORCE_PROJETILE.get(), DragonForceRenderer::new);
			RenderingRegistry.registerEntityRenderingHandler(EntityList.SQUIDINK_PROJETILE.get(), SquidInkRenderer::new);
			RenderingRegistry.registerEntityRenderingHandler(EntityList.BEESTING_PROJETILE.get(), BeeStingProjectileRenderer::new);
			RenderingRegistry.registerEntityRenderingHandler(EntityList.SPIDERWEB_PROJETILE.get(), SpiderWebProjectileRenderer::new);
			RenderingRegistry.registerEntityRenderingHandler(EntityList.BAT_SWORD_PROJECTILE.get(), BatSwordProjectileRenderer::new);
			RenderingRegistry.registerEntityRenderingHandler(EntityList.CHICKENEGG_PROJECTILE.get(), ChickenEggProjectileRenderer::new);
			RenderingRegistry.registerEntityRenderingHandler(EntityList.MILK_SPLASH_PROJECTILE.get(), MilkSplashProjectileRenderer::new);
			RenderingRegistry.registerEntityRenderingHandler(EntityList.CARROT_PROJECTILE.get(), CarrotProjectileRenderer::new);
		}
	}

