
package com.deathbyaether.custommobswords.events;

import com.deathbyaether.custommobswords.Main;

import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.event.entity.living.LivingEvent;
import net.minecraft.entity.player.PlayerEntity;
import com.deathbyaether.custommobswords.objects.items.BatSwordItem;
import net.minecraftforge.fml.common.Mod.EventBusSubscriber;
import net.minecraftforge.fml.common.Mod.EventBusSubscriber.Bus;

@EventBusSubscriber(modid = Main.MOD_ID, bus = Bus.FORGE)
public class PlayerEvents {
	private static final int MAX_FLAPS = 5;
	private static int wingFlapCount = 0;

	 public static void register(IEventBus modEventBus) {
	        MinecraftForge.EVENT_BUS.register(new PlayerEvents());
	    }

	@SubscribeEvent
	public void onJump(LivingEvent.LivingJumpEvent event) {
		if (!(event.getEntityLiving() instanceof PlayerEntity)) return;
		PlayerEntity player = (PlayerEntity) event.getEntityLiving();
		if (BatSwordItem.batFlightActive && player.onGround && wingFlapCount < MAX_FLAPS) {
			player.setMotion(player.getMotion().x, 3.0D, player.getMotion().z);
			player.velocityChanged = true;
			wingFlapCount++;
		}
	}
	
}

		
	
