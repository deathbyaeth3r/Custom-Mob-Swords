package com.deathbyaether.custommobswords.objects.items;

import java.util.List;

import org.lwjgl.glfw.GLFW;

import com.deathbyaether.custommobswords.objects.entities.BeeStingProjectileEntity;
import com.deathbyaether.custommobswords.util.enums.ModItemTier;

import net.minecraft.client.Minecraft;
import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.client.util.InputMappings;
import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.item.SwordItem;
import net.minecraft.potion.EffectInstance;
import net.minecraft.potion.Effects;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Hand;
import net.minecraft.util.SoundEvents;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.StringTextComponent;
import net.minecraft.world.World;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

public class BeeSwordItem extends SwordItem {

	public BeeSwordItem(ModItemTier swordGem, int i, int j, Properties properties) {
		super(swordGem, j, i, properties);
		
		
	}
	
	@Override
	@OnlyIn(Dist.CLIENT)
	public void addInformation(ItemStack stack, World worldIn, List<ITextComponent> tooltip, ITooltipFlag flagIn) {
		// TODO Auto-generated method stub
		super.addInformation(stack, worldIn, tooltip, flagIn);
		if (InputMappings.isKeyDown(Minecraft.getInstance().getMainWindow().getHandle(), GLFW.GLFW_KEY_LEFT_SHIFT)) {
			tooltip.add(new StringTextComponent("Fires a bee stinger on right click"));
		} else {
			
			tooltip.add(new StringTextComponent("Hold" + "\u00A7e" + " Shift " + "\u00A77" + "for projectile!"));
		}
		super.addInformation(stack, worldIn, tooltip, flagIn);
	}
	
	public boolean onLeftClickEntity(ItemStack stack, PlayerEntity playerIn, Entity entity) {
		
		playerIn.playSound(SoundEvents.ENTITY_BEE_STING, 5F, 0.8F + random.nextFloat() * 0.3F);
		((LivingEntity) entity).addPotionEffect(new EffectInstance(Effects.POISON, 390, 2));
	                        
	return super.onLeftClickEntity(stack, playerIn, entity);
	}
	
public ActionResult<ItemStack> onItemRightClick (World worldIn, PlayerEntity playerIn, Hand handIn) {
		
		ItemStack stack = playerIn.getHeldItem(handIn);
		
		
		
		if(!worldIn.isRemote) {
			
			BeeStingProjectileEntity beesting_projectile = new BeeStingProjectileEntity(playerIn, worldIn);
			
			beesting_projectile.setItem(stack);
			beesting_projectile.shoot(playerIn, playerIn.rotationPitch, playerIn.rotationYaw, 0.0F, 2F, 1.0F);
			worldIn.addEntity(beesting_projectile);
			
			playerIn.addPotionEffect(new EffectInstance(Effects.REGENERATION, 1300, 7) );
			playerIn.addPotionEffect(new EffectInstance(Effects.STRENGTH, 1300, 7) );
			playerIn.addPotionEffect(new EffectInstance(Effects.RESISTANCE, 1300, 7) );
			
			playerIn.getCooldownTracker().setCooldown(this, 100);
				
		}
	
		if(!playerIn.abilities.isCreativeMode) {
			
		}
		
		return ActionResult.resultSuccess(stack);
	}


}
