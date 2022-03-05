package com.deathbyaether.custommobswords.util;

import com.deathbyaether.custommobswords.Main;

import net.minecraft.client.gui.ScreenManager;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.RenderTypeLookup;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.client.registry.ClientRegistry;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.Mod.EventBusSubscriber.Bus;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;


    @Mod.EventBusSubscriber(modid = Main.MOD_ID, bus = Bus.MOD, value = Dist.CLIENT)
	public class ClientEventBusSubscriber {

		@SubscribeEvent
		public static void clientSetup(FMLClientSetupEvent event) {
			
			
		}
	}

