package com.deathbyaether.custommobswords.objects.items;

import java.util.List;

import org.lwjgl.glfw.GLFW;

import com.deathbyaether.custommobswords.util.enums.ModItemTier;

import net.minecraft.client.Minecraft;
import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.client.util.InputMappings;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.projectile.AbstractArrowEntity;
import net.minecraft.item.ArrowItem;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.item.SwordItem;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Hand;
import net.minecraft.util.SoundEvents;
import net.minecraft.util.math.vector.Vector3d;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.StringTextComponent;
import net.minecraft.world.World;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

import net.minecraft.item.Item.Properties;

public class SkeletonSwordItem extends SwordItem {

	public SkeletonSwordItem(ModItemTier swordGem, int i, int j, Properties properties) {
		super(swordGem, j, i, properties);
		
	}
	
	@Override
	@OnlyIn(Dist.CLIENT)
	public void appendHoverText(ItemStack stack, World worldIn, List<ITextComponent> tooltip, ITooltipFlag flagIn) {
		// TODO Auto-generated method stub
		super.appendHoverText(stack, worldIn, tooltip, flagIn);
		if (InputMappings.isKeyDown(Minecraft.getInstance().getWindow().getWindow(), GLFW.GLFW_KEY_LEFT_SHIFT)) {
			tooltip.add(new StringTextComponent("Rapidly fires arrows on right click"));
		} else {
			
			tooltip.add(new StringTextComponent("Hold" + "\u00A7e" + " Shift " + "\u00A77" + "for projectile!"));
		}
		super.appendHoverText(stack, worldIn, tooltip, flagIn);
	}
	
public boolean onLeftClickEntity(ItemStack stack, PlayerEntity playerIn, Entity entity) {
		
		playerIn.playSound(SoundEvents.SKELETON_AMBIENT, 5F, 0.8F + random.nextFloat() * 0.3F);
		
	                        
	return super.onLeftClickEntity(stack, playerIn, entity);
}
 
	
	
		public ActionResult<ItemStack> use (World worldIn, PlayerEntity playerIn, Hand handIn) {
			
			ItemStack stack = playerIn.getItemInHand(handIn);
			if(!worldIn.isClientSide) {
				
				
				Vector3d vec3d = playerIn.getLookAngle();
				double dX = vec3d.x;
				double dY = vec3d.y;
				double dZ = vec3d.z;
				double pX = playerIn.getX(); 
				double pY = playerIn.getEyeY(); 
				double pZ = playerIn.getZ();
			
				ArrowItem arrowitem = (ArrowItem)(stack.getItem() instanceof ArrowItem ? stack.getItem() : Items.ARROW);
                AbstractArrowEntity abstractarrowentity = arrowitem.createArrow(worldIn, stack, playerIn);
                abstractarrowentity.pickup = AbstractArrowEntity.PickupStatus.CREATIVE_ONLY;
                abstractarrowentity = customeArrow(abstractarrowentity);
                abstractarrowentity.shootFromRotation(playerIn, playerIn.xRot, playerIn.yRot,  0.0F, 2F, 1.0F);
                worldIn.addFreshEntity(abstractarrowentity);
              
					
			}
		
			if(!playerIn.abilities.instabuild) {
				
			}
			
			return ActionResult.success(stack);
		}
		
		public AbstractArrowEntity customeArrow(AbstractArrowEntity arrow) {
		      return arrow;
		   }


}
