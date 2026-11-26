package com.deathbyaether.custommobswords.objects.items;

import java.util.List;

import com.deathbyaether.custommobswords.util.enums.ModItemTier;

import net.minecraft.client.gui.screen.Screen;
import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item.Properties;
import net.minecraft.item.ItemStack;
import net.minecraft.item.SwordItem;
import net.minecraft.potion.EffectInstance;
import net.minecraft.potion.Effects;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Hand;
import net.minecraft.util.math.vector.Vector3d;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.StringTextComponent;
import net.minecraft.world.World;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

public class DonkeySwordItem extends SwordItem {

	public DonkeySwordItem(ModItemTier tier, int attackDamage, float attackSpeed, Properties properties) {
		super(tier, attackDamage, attackSpeed, properties);
	}

	@Override
	@OnlyIn(Dist.CLIENT)
	public void appendHoverText(ItemStack stack, World world, List<ITextComponent> tooltip, ITooltipFlag flag) {
		super.appendHoverText(stack, world, tooltip, flag);
		tooltip.add(new StringTextComponent(Screen.hasShiftDown()
				? "Right-click: Speed. Crouch + right-click: Speed and Jump Boost. Hitting a living target kicks it back."
				: "Right-click: Speed. Crouch + right-click: Speed and Jump Boost."));
	}

	@Override
	public ActionResult<ItemStack> use(World world, PlayerEntity player, Hand hand) {
		ItemStack stack = player.getItemInHand(hand);
		if (!world.isClientSide) {
			player.addEffect(new EffectInstance(Effects.MOVEMENT_SPEED, 200, 1));
			if (player.isShiftKeyDown()) {
				player.addEffect(new EffectInstance(Effects.JUMP, 200, 1));
			}
			player.getCooldowns().addCooldown(this, 100);
		}
		return ActionResult.success(stack);
	}

	@Override
	public boolean onLeftClickEntity(ItemStack stack, PlayerEntity player, Entity entity) {
		if (!player.level.isClientSide && entity instanceof LivingEntity) {
			Vector3d away = entity.position().subtract(player.position()).normalize();
			entity.setDeltaMovement(away.x * 1.7D, 0.5D, away.z * 1.7D);
		}
		return super.onLeftClickEntity(stack, player, entity);
	}
}
