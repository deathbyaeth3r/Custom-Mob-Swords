package com.deathbyaether.custommobswords.objects.items;

import java.util.List;

import com.deathbyaether.custommobswords.util.enums.ModItemTier;
import net.minecraft.block.Blocks;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.SwordItem;
import net.minecraft.potion.EffectInstance;
import net.minecraft.potion.Effects;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Hand;
import net.minecraft.util.math.BlockRayTraceResult;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.RayTraceResult;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.StringTextComponent;
import net.minecraft.world.World;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

public class SpiderSwordItem extends SwordItem {
	public SpiderSwordItem(ModItemTier tier, int attackDamage, float attackSpeed, Item.Properties properties) {
		super(tier, attackDamage, attackSpeed, properties);
	}

	@Override
	@OnlyIn(Dist.CLIENT)
	public void appendHoverText(ItemStack stack, World world, List<ITextComponent> tooltip, ITooltipFlag flag) {
		super.appendHoverText(stack, world, tooltip, flag);
		tooltip.add(new StringTextComponent(Screen.hasShiftDown()
				? "Right-click to snare an enemy or place a temporary web. Crouch against a wall to climb."
				: "Right-click: web snare. Crouch against walls to climb."));
	}

	@Override
	public ActionResult<ItemStack> use(World world, PlayerEntity player, Hand hand) {
		ItemStack stack = player.getItemInHand(hand);
		if (!world.isClientSide) {
			RayTraceResult hit = player.pick(12.0D, 0.0F, false);
			if (hit.getType() == RayTraceResult.Type.ENTITY && ((net.minecraft.util.math.EntityRayTraceResult) hit).getEntity() instanceof LivingEntity) {
				((LivingEntity) ((net.minecraft.util.math.EntityRayTraceResult) hit).getEntity())
						.addEffect(new EffectInstance(Effects.MOVEMENT_SLOWDOWN, 100, 2));
			} else if (hit instanceof BlockRayTraceResult) {
				BlockRayTraceResult blockHit = (BlockRayTraceResult) hit;
				BlockPos place = blockHit.getBlockPos().relative(blockHit.getDirection());
				if (world.isEmptyBlock(place)) world.setBlock(place, Blocks.COBWEB.defaultBlockState(), 3);
			}
			player.getCooldowns().addCooldown(this, 40);
		}
		return ActionResult.success(stack);
	}

	@Override
	public boolean onLeftClickEntity(ItemStack stack, PlayerEntity player, Entity entity) {
		if (!player.level.isClientSide && entity instanceof LivingEntity) {
			((LivingEntity) entity).addEffect(new EffectInstance(Effects.MOVEMENT_SLOWDOWN, 60, 0));
		}
		return super.onLeftClickEntity(stack, player, entity);
	}
}
