package com.deathbyaether.custommobswords.util;

import com.deathbyaether.custommobswords.Main;

import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.Mod.EventBusSubscriber.Bus;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;


    @Mod.EventBusSubscriber(modid = Main.MOD_ID, bus = Bus.MOD, value = Dist.CLIENT)
	public class ClientEventBusSubscriber {

		@SubscribeEvent
		public static void clientSetup(FMLClientSetupEvent event) {
			
			
			
		}
	}

