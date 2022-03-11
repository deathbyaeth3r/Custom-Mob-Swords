package com.deathbyaether.custommobswords.util;

import com.deathbyaether.custommobswords.Main;
import com.deathbyaether.custommobswords.list.EntityList;
import com.deathbyaether.custommobswords.objects.entities.renderer.BeeStingProjectileRenderer;

import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.client.registry.RenderingRegistry;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.Mod.EventBusSubscriber.Bus;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;


    @Mod.EventBusSubscriber(modid = Main.MOD_ID, bus = Bus.MOD, value = Dist.CLIENT)
	public class ClientEventBusSubscriber {

		@SubscribeEvent
		public static void clientSetup(FMLClientSetupEvent event) {
			
			
			
		}
	}

