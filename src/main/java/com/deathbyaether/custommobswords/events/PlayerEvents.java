
package com.deathbyaether.custommobswords.events;

import com.deathbyaether.custommobswords.Main;

import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.fml.common.Mod.EventBusSubscriber;
import net.minecraftforge.fml.common.Mod.EventBusSubscriber.Bus;

@EventBusSubscriber(modid = Main.MOD_ID, bus = Bus.FORGE)
public class PlayerEvents {

	 public static void register(IEventBus modEventBus) {
	        MinecraftForge.EVENT_BUS.register(new PlayerEvents());
	    }
	
}

		
	
