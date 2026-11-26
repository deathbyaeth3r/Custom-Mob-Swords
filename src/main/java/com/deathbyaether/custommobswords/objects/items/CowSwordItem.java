package com.deathbyaether.custommobswords.objects.items;

import java.util.ArrayList;
import java.util.List;

import com.deathbyaether.custommobswords.util.enums.ModItemTier;

import net.minecraft.client.gui.screen.Screen;
import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item.Properties;
import net.minecraft.item.ItemStack;
import net.minecraft.item.SwordItem;
import net.minecraft.potion.EffectInstance;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Hand;
import net.minecraft.util.math.vector.Vector3d;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.StringTextComponent;
import net.minecraft.world.World;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

public class CowSwordItem extends SwordItem {

	public CowSwordItem(ModItemTier tier, int attackDamage, float attackSpeed, Properties properties) {
		super(tier, attackDamage, attackSpeed, properties);
	}

	@Override
	@OnlyIn(Dist.CLIENT)
	public void appendHoverText(ItemStack stack, World world, List<ITextComponent> tooltip, ITooltipFlag flag) {
		super.appendHoverText(stack, world, tooltip, flag);
		tooltip.add(new StringTextComponent(Screen.hasShiftDown()
				? "Right-click cleanses harmful effects; crouch + right-click charges. Landing with the sword held stomps nearby enemies."
				: "Right-click: cleanse. Crouch + right-click: charge. Hold in your main hand to stomp on landing."));
	}

	@Override
	public ActionResult<ItemStack> use(World world, PlayerEntity player, Hand hand) {
		ItemStack stack = player.getItemInHand(hand);
		if (!world.isClientSide) {
			if (player.isShiftKeyDown()) {
				Vector3d look = player.getLookAngle();
				player.setDeltaMovement(look.x * 2.0D, 0.5D, look.z * 2.0D);
			} else {
				for (EffectInstance effect : new ArrayList<>(player.getActiveEffects())) {
					if (!effect.getEffect().isBeneficial()) {
						player.removeEffect(effect.getEffect());
					}
				}
			}
			player.getCooldowns().addCooldown(this, 60);
		}
		return ActionResult.success(stack);
	}
}
