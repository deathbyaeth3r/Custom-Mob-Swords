package com.deathbyaether.custommobswords.objects.items;

import java.util.List;

import org.lwjgl.glfw.GLFW;

import com.deathbyaether.custommobswords.objects.entities.DragonForceEntity;
import com.deathbyaether.custommobswords.util.enums.ModItemTier;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.client.util.InputMappings;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.item.SwordItem;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Hand;
import net.minecraft.util.SoundEvents;
import net.minecraft.util.math.Vec3d;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.StringTextComponent;
import net.minecraft.world.World;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

public class EnderdragonSwordItem extends SwordItem {
	
	 

	public EnderdragonSwordItem(ModItemTier swordGem, int i, int j, Properties properties) {
		super(swordGem, j, i, properties);
		
	}
	
	@Override
	@OnlyIn(Dist.CLIENT)
	public void addInformation(ItemStack stack, World worldIn, List<ITextComponent> tooltip, ITooltipFlag flagIn) {
		super.addInformation(stack, worldIn, tooltip, flagIn);
		if (Screen.hasShiftDown()) {
			tooltip.add(new StringTextComponent("Fires a combustive dragon charge on right click"));
		} else {
			
			tooltip.add(new StringTextComponent("Hold" + "\u00A7e" + " Shift " + "\u00A77" + "for Info!"));
		}
		 
		
	}
	
	
	
	public boolean onLeftClickEntity(ItemStack stack, PlayerEntity playerIn, Entity entity) {
		
			playerIn.playSound(SoundEvents.ENTITY_ENDER_DRAGON_GROWL, 5F, 0.8F + random.nextFloat() * 0.3F);
			
		                        
		return super.onLeftClickEntity(stack, playerIn, entity);
	}
	 

	
	@Override
	public ActionResult<ItemStack> onItemRightClick (World worldIn, PlayerEntity playerIn, Hand handIn) {
		
		ItemStack stack = playerIn.getHeldItem(handIn);
		
		Vec3d vec3d = playerIn.getLookVec();
		
		double dX = vec3d.x;
		double dY = vec3d.y;
		double dZ = vec3d.z;
		double pX = playerIn.getPosX(); 
		double pY = playerIn.getPosYEye(); 
		double pZ = playerIn.getPosZ();
		
		
		
		
		if(!worldIn.isRemote) {
			
	
			DragonForceEntity dragonforceentity = new DragonForceEntity(worldIn, pX, pY, pZ, dX, dY, dZ);
			dragonforceentity.setVelocity(dX, dY, dZ);
			worldIn.addEntity(dragonforceentity);

			playerIn.getCooldownTracker().setCooldown(this, 100);
				
		}
	
		if(!playerIn.abilities.isCreativeMode) {
			
		}
		
		return ActionResult.resultSuccess(stack);
	}

	

	

}