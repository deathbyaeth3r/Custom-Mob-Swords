package com.deathbyaether.custommobswords.objects.items;

import java.util.List;

import com.deathbyaether.custommobswords.util.enums.ModItemTier;

import net.minecraft.client.gui.screen.Screen;
import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.projectile.ShulkerBulletEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.item.SwordItem;
import net.minecraft.potion.EffectInstance;
import net.minecraft.potion.Effects;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Hand;
import net.minecraft.util.SoundEvents;
import net.minecraft.util.math.vector.Vector3d;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.StringTextComponent;
import net.minecraft.world.World;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

public class ShulkerSwordItem extends SwordItem {
	public ShulkerSwordItem(ModItemTier swordGem, int i, int j, Properties properties) {
		super(swordGem, j, i, properties);
		
	}
	
	@OnlyIn(Dist.CLIENT)
	public void addInformation(ItemStack stack, World worldIn, List<ITextComponent> tooltip, ITooltipFlag flagIn) {
		// TODO Auto-generated method stub
		super.appendHoverText(stack, worldIn, tooltip, flagIn);
		if (Screen.hasShiftDown()) {
			tooltip.add(new StringTextComponent("Fires a bee stinger on right click"));
		} else {
			
			tooltip.add(new StringTextComponent("Hold" + "\u00A7e" + " Shift " + "\u00A77" + "for projectile!"));
		}
		super.appendHoverText(stack, worldIn, tooltip, flagIn);
	}
	
	@Override
	public boolean onLeftClickEntity(ItemStack stack, PlayerEntity player, Entity entity) {
		((LivingEntity) entity).addEffect(new EffectInstance(Effects.LEVITATION, 390, 2));
		player.playSound(SoundEvents.SHULKER_SHOOT, 5F, 0.8F + random.nextFloat() * 0.3F);
		
		
		return super.onLeftClickEntity(stack, player, entity);
	}
	
	public ActionResult<ItemStack> onItemRightClick (World worldIn, PlayerEntity playerIn, Hand handIn) {
		
		ItemStack stack = playerIn.getItemInHand(handIn);
		
		Vector3d vec3d = playerIn.getLookAngle();
		double dX = vec3d.x;
		double dY = vec3d.y;
		double dZ = vec3d.z;
		double pX = playerIn.getX(); 
		double pY = playerIn.getEyeY(); 
		double pZ = playerIn.getZ();
	
		
		
		if(!worldIn.isClientSide) {
			
			ShulkerBulletEntity shulkerbulletentity = new ShulkerBulletEntity(worldIn, pX, pY, pZ, dX, dY, dZ);
			
			
			worldIn.addFreshEntity(shulkerbulletentity);
			playerIn.getCooldowns().addCooldown(this, 100);
				
		}
	
		if(!playerIn.abilities.instabuild) {
			
		}
		
		return ActionResult.success(stack);
	}
	
		
}





