/**
package com.deathbyaether.custommobswords.util;

import com.deathbyaether.custommobswords.Main;
import com.deathbyaether.custommobswords.list.ParticleList;
import com.deathbyaether.custommobswords.objects.particles.DragonFireParticle;

import net.minecraft.client.Minecraft;
import net.minecraftforge.client.event.ParticleFactoryRegisterEvent;
import net.minecraftforge.eventbus.api.EventPriority;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod.EventBusSubscriber;
import net.minecraftforge.fml.common.Mod.EventBusSubscriber.Bus;

@EventBusSubscriber(modid = Main.MOD_ID, bus = Bus.MOD)
public class ParticleUtil {
	
	
	@SuppressWarnings("resource")
	@SubscribeEvent(priority = EventPriority.LOWEST)
	public static void registerParticles(ParticleFactoryRegisterEvent event) {
		Minecraft.getInstance().particles.registerFactory(ParticleList.DRAGONFIRE_PARTICLE.get(), DragonFireParticle.Factory::new);
	}

}
**/