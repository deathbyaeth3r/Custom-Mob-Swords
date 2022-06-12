package com.deathbyaether.custommobswords.objects.items;

import java.util.List;

import org.lwjgl.glfw.GLFW;

import com.deathbyaether.custommobswords.util.enums.ModItemTier;

import net.minecraft.client.Minecraft;
import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.client.util.InputMappings;
import net.minecraft.entity.Entity;
import net.minecraft.entity.item.EnderPearlEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.item.SwordItem;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Hand;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.SoundEvents;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.StringTextComponent;
import net.minecraft.world.World;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

import net.minecraft.item.Item.Properties;

public class EndermanSwordItem extends SwordItem {

	public EndermanSwordItem(ModItemTier swordGem, int i, int j, Properties properties) {
		super(swordGem, j, i, properties);
		
	}
	
	@Override
	@OnlyIn(Dist.CLIENT)
	public void appendHoverText(ItemStack stack, World worldIn, List<ITextComponent> tooltip, ITooltipFlag flagIn) {
		// TODO Auto-generated method stub
		super.appendHoverText(stack, worldIn, tooltip, flagIn);
		if (InputMappings.isKeyDown(Minecraft.getInstance().getWindow().getWindow(), GLFW.GLFW_KEY_LEFT_SHIFT)) {
			tooltip.add(new StringTextComponent("Fires a ender pearl on right click"));
		} else {
			
			tooltip.add(new StringTextComponent("Hold" + "\u00A7e" + " Shift " + "\u00A77" + "for Info!"));
		}
		super.appendHoverText(stack, worldIn, tooltip, flagIn);
	}
	
	public boolean onLeftClickEntity(ItemStack stack, PlayerEntity playerIn, Entity entity) {
		
		playerIn.playSound(SoundEvents.ENDERMAN_SCREAM, 5F, 0.8F + random.nextFloat() * 0.3F);
		
	                        
	return super.onLeftClickEntity(stack, playerIn, entity);
}
	
public ActionResult<ItemStack> use(World worldIn, PlayerEntity playerIn, Hand handIn) {
    ItemStack stack = playerIn.getItemInHand(handIn);
    worldIn.playSound((PlayerEntity)null, playerIn.getX(), playerIn.getY(), playerIn.getZ(), SoundEvents.ENDER_PEARL_THROW, SoundCategory.NEUTRAL, 0.5F, 0.4F / (random.nextFloat() * 0.4F + 0.8F));
    playerIn.getCooldowns().addCooldown(this, 20);
    
    if (!worldIn.isClientSide) {
       EnderPearlEntity enderpearlentity = new EnderPearlEntity(worldIn, playerIn);
       enderpearlentity.setItem(stack);
       enderpearlentity.shootFromRotation(playerIn, playerIn.xRot, playerIn.yRot, 0.0F, 1.5F, 1.0F);
       worldIn.addFreshEntity(enderpearlentity);
       
       playerIn.getCooldowns().addCooldown(this, 100);
    }
	
		if(!playerIn.abilities.instabuild) {
			
		}
		
		return ActionResult.success(stack);
	}

	

	


}
