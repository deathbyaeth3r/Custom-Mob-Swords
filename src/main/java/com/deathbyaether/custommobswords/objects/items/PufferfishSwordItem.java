package com.deathbyaether.custommobswords.objects.items;

import com.deathbyaether.custommobswords.util.enums.ModItemTier;

import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.item.SwordItem;
import net.minecraft.potion.EffectInstance;
import net.minecraft.potion.Effects;
import net.minecraft.util.SoundEvents;

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
