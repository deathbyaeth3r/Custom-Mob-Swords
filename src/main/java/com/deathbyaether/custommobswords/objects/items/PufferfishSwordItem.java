package com.deathbyaether.custommobswords.objects.items;

import java.util.List;

import org.lwjgl.glfw.GLFW;

import com.deathbyaether.custommobswords.util.enums.ModItemTier;

import net.minecraft.client.Minecraft;
import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.client.util.InputMappings;
import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.item.SwordItem;
import net.minecraft.potion.EffectInstance;
import net.minecraft.potion.Effects;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Hand;
import net.minecraft.util.SoundEvents;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.StringTextComponent;
import net.minecraft.world.World;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

public class PufferfishSwordItem extends SwordItem {

	public PufferfishSwordItem(ModItemTier swordGem, int i, int j, Properties properties) {
		super(swordGem, j, i, properties);
		
		
	}
	public boolean onLeftClickEntity(ItemStack stack, PlayerEntity playerIn, Entity entity) {
		
		playerIn.playSound(SoundEvents.ENTITY_PUFFER_FISH_STING, 5F, 0.8F + random.nextFloat() * 0.3F);
		((LivingEntity) entity).addPotionEffect(new EffectInstance(Effects.POISON, 390, 2));
		playerIn.addPotionEffect(new EffectInstance(Effects.RESISTANCE, 1300, 2));
		
	                        
	return super.onLeftClickEntity(stack, playerIn, entity);
	}
	
 
}
