package com.deathbyaether.custommobswords.objects.items;

import java.util.List;

import org.lwjgl.glfw.GLFW;

import com.deathbyaether.custommobswords.util.enums.ModItemTier;

import net.minecraft.client.Minecraft;
import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.client.util.InputMappings;
import net.minecraft.entity.monster.CreeperEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Hand;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.StringTextComponent;
import net.minecraft.util.text.TranslationTextComponent;
import net.minecraft.world.World;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

public class CreeperSwordItem extends Item {

	public CreeperSwordItem(ModItemTier swordGem, int i, int j, Properties properties) {
		super(properties);
		
		
	}
	
	

	@Override
	@OnlyIn(Dist.CLIENT)
	public void addInformation(ItemStack stack, World worldIn, List<ITextComponent> tooltip, ITooltipFlag flagIn) {
		
		super.addInformation(stack, worldIn, tooltip, flagIn);
		if (InputMappings.isKeyDown(Minecraft.getInstance().getMainWindow().getHandle(), GLFW.GLFW_KEY_LEFT_SHIFT)) {
			tooltip.add(new StringTextComponent("right click for projectile"));
		} else {
			
			// "Hold " + "\u00A7e" + "Shift" + "\u00A77" + " for More Information"
			tooltip.add(new TranslationTextComponent("tooltip.mobswords.creepersword_item.hold_shift"));
			
		}
	}
	
	public ActionResult<ItemStack> onItemRightClick(World worldIn, PlayerEntity playerIn, Hand handIn) {
		if(!playerIn.getCooldownTracker().hasCooldown(this)) {
			
			CreeperEntity entity = new CreeperEntity(worldIn);
			entity.setPosition(playerIn.getPosX(), playerIn.getPosY(), playerIn.getPosZ());
			worldIn.addEntity(entity);
			playerIn.getCooldownTracker().setCooldown(this, 1000);
			return ActionResult.resultSuccess(playerIn.getHeldItem(handIn));
			
		}
		
		return ActionResult.resultFail(playerIn.getHeldItem(handIn));
		
	}
}
