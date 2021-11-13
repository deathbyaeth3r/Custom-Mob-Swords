package com.deathbyaether.custommobswords.objects.items;

import java.util.List;

import org.lwjgl.glfw.GLFW;

import com.deathbyaether.custommobswords.util.enums.ModItemTier;

import net.minecraft.client.Minecraft;
import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.client.util.InputMappings;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.projectile.WitherSkullEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.SwordItem;
import net.minecraft.item.Item.Properties;
import net.minecraft.potion.EffectInstance;
import net.minecraft.potion.Effects;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Hand;
import net.minecraft.util.math.Vec3d;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.StringTextComponent;
import net.minecraft.util.text.TranslationTextComponent;
import net.minecraft.world.World;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

public class WitherSwordItem extends SwordItem {

	public WitherSwordItem(ModItemTier swordGem, int i, int j, Properties properties) {
		super(swordGem, j, i, properties);
		
	}
	
	@Override
	@OnlyIn(Dist.CLIENT)
	public void addInformation(ItemStack stack, World worldIn, List<ITextComponent> tooltip, ITooltipFlag flagIn) {
		// TODO Auto-generated method stub
		super.addInformation(stack, worldIn, tooltip, flagIn);
		if (InputMappings.isKeyDown(Minecraft.getInstance().getMainWindow().getHandle(), GLFW.GLFW_KEY_LEFT_SHIFT)) {
			tooltip.add(new StringTextComponent("right click for projectile"));
		} else {
			
			// "Hold " + "\u00A7e" + "Shift" + "\u00A77" + " for More Information"
			tooltip.add(new TranslationTextComponent("tooltip.mobswords.withersword_item.hold_shift"));
			
		}
		
	}
	
	
	
	@Override
	public boolean onLeftClickEntity(ItemStack stack, PlayerEntity player, Entity entity) {
		((LivingEntity) entity).addPotionEffect(new EffectInstance(Effects.WITHER, 100, 2));
		
		
		
		return super.onLeftClickEntity(stack, player, entity);
	}
	
	
	
	public ActionResult<ItemStack> onItemRightClick (World worldIn, PlayerEntity playerIn, Hand handIn) {
		
		ItemStack stack = playerIn.getHeldItem(handIn);
		Vec3d vec3d = playerIn.getLookVec();
		double d0 = vec3d.y;
		double d1 = vec3d.x;
		double d2 = vec3d.z;
		double p0 = playerIn.getPosY();
		
		
		
		
		
		if(!worldIn.isRemote) {
			
			WitherSkullEntity witherskullentity = new WitherSkullEntity(worldIn, playerIn, d1, d0, d2);
		      
			worldIn.addEntity(witherskullentity);
			playerIn.getCooldownTracker().setCooldown(this, 100);
			
			
			
		}
	
		
		if(!playerIn.abilities.isCreativeMode) {
			
		}
		
		
		return ActionResult.resultSuccess(stack);
	}
	
	

	
}
