/**package com.deathbyaether.custommobswords.list;

import java.awt.event.KeyEvent;

import javax.swing.text.JTextComponent.KeyBinding;

import com.deathbyaether.custommobswords.Main;

import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.fml.client.registry.ClientRegistry;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;

@OnlyIn(Dist.CLIENT)
public class KeybindsList {
	public static KeyBinding specialKey;
	
	public static void register(final FMLClientSetupEvent event) {
		specialKey = create("example_key", KeyEvent.VK_G);
		
		ClientRegistry.registerKeyBinding(specialKey);
	}
	
	private static KeyBinding create(String name, int key) {
		return new KeyBinding("key." + Main.MOD_ID + "." + name, key, "key.category." + Main.MOD_ID);
	}
}
**/