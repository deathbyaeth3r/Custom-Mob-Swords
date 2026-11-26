package com.deathbyaether.custommobswords.objects.items;

import java.util.List;

import com.deathbyaether.custommobswords.util.enums.ModItemTier;

import net.minecraft.client.gui.screen.Screen;
import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item.Properties;
import net.minecraft.item.ItemStack;
import net.minecraft.item.SwordItem;
import net.minecraft.potion.EffectInstance;
import net.minecraft.potion.Effects;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Hand;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.StringTextComponent;
import net.minecraft.world.World;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

public class BatSwordItem extends SwordItem {

	public BatSwordItem(ModItemTier tier, int attackDamage, float attackSpeed, Properties properties) {
		super(tier, attackDamage, attackSpeed, properties);
	}

	@Override
	@OnlyIn(Dist.CLIENT)
	public void appendHoverText(ItemStack stack, World world, List<ITextComponent> tooltip, ITooltipFlag flag) {
		super.appendHoverText(stack, world, tooltip, flag);
		tooltip.add(new StringTextComponent("Right-click: gain Speed and Slow Falling for 30 seconds"));
	}

	@Override
	public ActionResult<ItemStack> use(World world, PlayerEntity player, Hand hand) {
		ItemStack stack = player.getItemInHand(hand);
		if (!world.isClientSide) {
			player.addEffect(new EffectInstance(Effects.SLOW_FALLING, 600));
			player.addEffect(new EffectInstance(Effects.MOVEMENT_SPEED, 600, 2));
			player.getCooldowns().addCooldown(this, 600);
		}
		return ActionResult.success(stack);
	}
}
