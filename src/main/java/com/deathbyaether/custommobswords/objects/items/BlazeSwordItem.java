package com.deathbyaether.custommobswords.objects.items;

import java.util.List;

import org.lwjgl.glfw.GLFW;

import com.deathbyaether.custommobswords.util.enums.ModItemTier;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.client.util.InputMappings;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.projectile.FireballEntity;
import net.minecraft.entity.projectile.SmallFireballEntity;
import net.minecraft.item.IItemTier;
import net.minecraft.item.ItemStack;
import net.minecraft.item.SwordItem;
import net.minecraft.item.Item.Properties;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Hand;
import net.minecraft.util.math.vector.Vector3d;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.StringTextComponent;
import net.minecraft.world.World;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

public class BlazeSwordItem extends SwordItem{

	public BlazeSwordItem(ModItemTier swordGem, int i, int j, Properties properties) {
		super(swordGem, j, i, properties);
		
	}
	
	@Override
	@OnlyIn(Dist.CLIENT)
	public void appendHoverText(ItemStack stack, World worldIn, List<ITextComponent> tooltip, ITooltipFlag flagIn) {
		super.appendHoverText(stack, worldIn, tooltip, flagIn);
		if (Screen.hasShiftDown()) {
			tooltip.add(new StringTextComponent("Right-click fires a spread of three fireballs."));
		} else {
			
			tooltip.add(new StringTextComponent("Hold" + "\u00A7e" + " Shift " + "\u00A77" + "for Info!"));
		}
		 
		
	}


	@Override
	public ActionResult<ItemStack> use (World worldIn, PlayerEntity playerIn, Hand handIn) {
		
		ItemStack stack = playerIn.getItemInHand(handIn);
		
		Vector3d vec3d = playerIn.getLookAngle();
		
		double dX = vec3d.x;
		double dY = vec3d.y;
		double dZ = vec3d.z;
		double pX = playerIn.getX(); 
		double pY = playerIn.getEyeY(); 
		double pZ = playerIn.getZ();
		
		
		
		if(!worldIn.isClientSide) {
			
	
			SmallFireballEntity smallfireballentity0 = new SmallFireballEntity(worldIn, pX, pY, pZ, dX, dY, dZ);
			SmallFireballEntity smallfireballentity1 = new SmallFireballEntity(worldIn, pX + 0.5, pY, pZ, dX, dY, dZ);
			SmallFireballEntity smallfireballentity2 = new SmallFireballEntity(worldIn, pX - 0.5, pY, pZ, dX, dY, dZ);
			
			worldIn.addFreshEntity(smallfireballentity0);
			worldIn.addFreshEntity(smallfireballentity1);
			worldIn.addFreshEntity(smallfireballentity2);
			
			playerIn.getCooldowns().addCooldown(this, 100);
				
		}
	
		if(!playerIn.abilities.instabuild) {
			
		}
		
		return ActionResult.success(stack);
	}
	
	
	

}
