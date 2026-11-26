package com.deathbyaether.custommobswords.objects.items;

import java.util.List;

import com.deathbyaether.custommobswords.util.enums.ModItemTier;

import net.minecraft.client.gui.screen.Screen;
import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.projectile.EggEntity;
import net.minecraft.item.Item.Properties;
import net.minecraft.item.ItemStack;
import net.minecraft.item.SwordItem;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Hand;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.StringTextComponent;
import net.minecraft.world.World;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

public class ChickenSwordItem extends SwordItem {

	public ChickenSwordItem(ModItemTier tier, int attackDamage, float attackSpeed, Properties properties) {
		super(tier, attackDamage, attackSpeed, properties);
	}

	@Override
	@OnlyIn(Dist.CLIENT)
	public void appendHoverText(ItemStack stack, World world, List<ITextComponent> tooltip, ITooltipFlag flag) {
		super.appendHoverText(stack, world, tooltip, flag);
		tooltip.add(new StringTextComponent("Hold to fall slowly while this sword is in your main hand."));
		tooltip.add(new StringTextComponent(Screen.hasShiftDown()
				? "Right-click throws eggs; crouch and right-click throws a burst."
				: "Right-click: egg. Crouch + right-click: egg burst."));
	}

	@Override
	public ActionResult<ItemStack> use(World world, PlayerEntity player, Hand hand) {
		ItemStack stack = player.getItemInHand(hand);
		if (!world.isClientSide) {
			int count = player.isShiftKeyDown() ? 5 : 1;
			for (int i = 0; i < count; i++) {
				EggEntity egg = new EggEntity(world, player);
				egg.shootFromRotation(player, player.xRot, player.yRot, 0.0F,
						player.isShiftKeyDown() ? 1.1F : 1.5F, 1.0F);
				world.addFreshEntity(egg);
			}
			player.getCooldowns().addCooldown(this, 20);
		}
		return ActionResult.success(stack);
	}
}
