package com.deathbyaether.custommobswords.objects.items;

import java.util.List;

import org.lwjgl.glfw.GLFW;

import com.deathbyaether.custommobswords.util.enums.ModItemTier;

import net.minecraft.client.Minecraft;
import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.client.util.InputMappings;
import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.projectile.ShulkerBulletEntity;
import net.minecraft.entity.projectile.WitherSkullEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.item.SwordItem;
import net.minecraft.potion.EffectInstance;
import net.minecraft.potion.Effects;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Hand;
import net.minecraft.util.SoundEvents;
import net.minecraft.util.math.Vec3d;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.StringTextComponent;
import net.minecraft.world.World;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

public class ShulkerSwordItem extends SwordItem {
	public ShulkerSwordItem(ModItemTier swordGem, int i, int j, Properties properties) {
		super(swordGem, j, i, properties);
		
	}
	
	@Override
	@OnlyIn(Dist.CLIENT)
	public void addInformation(ItemStack stack, World worldIn, List<ITextComponent> tooltip, ITooltipFlag flagIn) {
		// TODO Auto-generated method stub
		super.addInformation(stack, worldIn, tooltip, flagIn);
		if (InputMappings.isKeyDown(Minecraft.getInstance().getMainWindow().getHandle(), GLFW.GLFW_KEY_LEFT_SHIFT)) {
			tooltip.add(new StringTextComponent(""));
		} else {
			
			tooltip.add(new StringTextComponent("Hold" + "\u00A7e" + " Shift " + "\u00A77" + "for Info!"));
		}
		super.addInformation(stack, worldIn, tooltip, flagIn);
		 
		
	}
	
	@Override
	public boolean onLeftClickEntity(ItemStack stack, PlayerEntity player, Entity entity) {
		((LivingEntity) entity).addPotionEffect(new EffectInstance(Effects.LEVITATION, 390, 2));
		player.playSound(SoundEvents.ENTITY_SHULKER_SHOOT, 5F, 0.8F + random.nextFloat() * 0.3F);
		
		
		return super.onLeftClickEntity(stack, player, entity);
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
			
			ShulkerBulletEntity shulkerbulletentity = new ShulkerBulletEntity(worldIn, pX, pY, pZ, dX, dY, dZ);
			
			
			worldIn.addEntity(shulkerbulletentity);
			playerIn.getCooldownTracker().setCooldown(this, 100);
				
		}
	
		if(!playerIn.abilities.isCreativeMode) {
			
		}
		
		return ActionResult.resultSuccess(stack);
	}
	
		
}


