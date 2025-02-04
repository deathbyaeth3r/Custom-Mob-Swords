
package com.deathbyaether.custommobswords.events;
 

import com.deathbyaether.custommobswords.Main;
import com.deathbyaether.custommobswords.list.KeybindsList;
import com.deathbyaether.custommobswords.objects.items.BatSwordItem;

import net.minecraft.client.Minecraft;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.client.event.InputEvent;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.entity.living.LivingEvent;
import net.minecraftforge.event.entity.living.LivingFallEvent;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod.EventBusSubscriber;
import net.minecraftforge.fml.common.Mod.EventBusSubscriber.Bus;

@EventBusSubscriber(modid = Main.MOD_ID, bus = Bus.FORGE, value = Dist.CLIENT)
public class InputEvents {
	
	private static final int MAX_FLAPS = 5;
    private static int wingFlapCount = 0;
    
	  public static void register(IEventBus modEventBus) {
	        MinecraftForge.EVENT_BUS.register(new InputEvents());
	    }
	
	@SubscribeEvent
	public static void onKeyPress(InputEvent.KeyInputEvent event) {
		Minecraft mc = Minecraft.getInstance();
		if (mc.world == null) return;
		onInput(mc, event.getKey(), event.getAction());
	}
	
	@SubscribeEvent
	public static void onMouseClick(InputEvent.MouseInputEvent event) {
		Minecraft mc = Minecraft.getInstance();
		if (mc.world == null) return;
		onInput(mc, event.getButton(), event.getAction());
	}
	
	@SubscribeEvent
	public void onJump(LivingEvent.LivingJumpEvent event) {
	    if (!(event.getEntityLiving() instanceof PlayerEntity)) return;

	    PlayerEntity player = (PlayerEntity) event.getEntityLiving();

	    // Ensure jump triggers only when on the ground
	    if (BatSwordItem.batFlightActive && player.onGround && wingFlapCount < MAX_FLAPS) {
	        System.out.println("Wing Flap Activated!");  // Debug message

	        // Apply motion (jump boost)
	        player.setMotion(player.getMotion().x, 3.0, player.getMotion().z);
	        
	        // Force movement update
	        player.velocityChanged = true;

	        wingFlapCount++;
	    }
	}

    private static void onInput(Minecraft mc, int key, int action) {
        if (mc == null || mc.world == null) return;  // Prevent crashes due to null values

        if (mc.currentScreen == null && KeybindsList.specialKey != null && KeybindsList.specialKey.isPressed()) {
            //keybinding logic 
        }
    }
}
