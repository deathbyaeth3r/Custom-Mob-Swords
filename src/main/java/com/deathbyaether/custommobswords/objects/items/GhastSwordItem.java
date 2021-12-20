package com.deathbyaether.custommobswords.objects.items;

import java.util.List;

import org.lwjgl.glfw.GLFW;

import com.deathbyaether.custommobswords.util.enums.ModItemTier;

import net.minecraft.client.Minecraft;
import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.client.util.InputMappings;
import net.minecraft.entity.Pose;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.projectile.FireballEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.item.SwordItem;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Hand;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.Vec3d;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.StringTextComponent;
import net.minecraft.util.text.TranslationTextComponent;
import net.minecraft.world.World;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

public class GhastSwordItem extends SwordItem {

	

	public GhastSwordItem(ModItemTier swordGem, int i, int j, Properties properties) {
		super(swordGem, j, i, properties);
		
		
	}
	
	@Override
	@OnlyIn(Dist.CLIENT)
	public void addInformation(ItemStack stack, World worldIn, List<ITextComponent> tooltip, ITooltipFlag flagIn) {
		// TODO Auto-generated method stub
		super.addInformation(stack, worldIn, tooltip, flagIn);
		if (InputMappings.isKeyDown(Minecraft.getInstance().getMainWindow().getHandle(), GLFW.GLFW_KEY_LEFT_SHIFT)) {
			tooltip.add(new StringTextComponent("Fires a fireball on right click"));
		} else {
			
			tooltip.add(new StringTextComponent("Hold" + "\u00A7e" + " Shift " + "\u00A77" + "for projectile!"));
		}
		super.addInformation(stack, worldIn, tooltip, flagIn);
	}
	
	
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
				
		
				FireballEntity fireballentity = new FireballEntity(worldIn, pX, pY, pZ, dX, dY, dZ);
				
				worldIn.addEntity(fireballentity);
				
				playerIn.getCooldownTracker().setCooldown(this, 100);
					
			}
		
			if(!playerIn.abilities.isCreativeMode) {
				
			}
			
			return ActionResult.resultSuccess(stack);
		}

		

}
