package com.deathbyaether.custommobswords.objects.items;

import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

import org.lwjgl.glfw.GLFW;

import com.deathbyaether.custommobswords.objects.entities.BatSwordProjectileEntity;
import com.deathbyaether.custommobswords.util.enums.ModItemTier;

import net.minecraft.client.Minecraft;
import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.client.util.InputMappings;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.inventory.EquipmentSlotType;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.item.SwordItem;
import net.minecraft.potion.EffectInstance;
import net.minecraft.potion.Effects;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Hand;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.StringTextComponent;
import net.minecraft.world.World;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

public class BatSwordItem extends SwordItem {
	
	   private static final int MAX_FLAPS = 5;
	   private int wingFlapCount = 0;
	   public static boolean batFlightActive = false; 
	
	   public BatSwordItem(ModItemTier swordGem, int attackDamage, float attackSpeed, Properties properties) {
	        super(swordGem, attackDamage, attackSpeed, properties);
	       
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
	
	@Override
	    public ActionResult<ItemStack> onItemRightClick(World world, PlayerEntity player, Hand hand) {
	        if (!world.isRemote) {
	            // Shoot projectile
	            BatSwordProjectileEntity projectile = new BatSwordProjectileEntity(player, world);
	            projectile.shoot(player, player.rotationPitch, player.rotationYaw, 0.0F, 1.5F, 1.0F);
	            world.addEntity(projectile);
	            
	            // Apply slow falling and speed effects
	            //player.addPotionEffect(new EffectInstance(Effects.SLOW_FALLING, 600));
	            player.addPotionEffect(new EffectInstance(Effects.SPEED, 600, 2));
	            
	            // Equip temporary Elytra
	            ItemStack batElytra = new ItemStack(Items.ELYTRA);
	            player.setItemStackToSlot(EquipmentSlotType.CHEST, batElytra);
	            player.sendMessage(new StringTextComponent("You feel as light as a bat!"));
	            
	            batFlightActive = true;  
	            
	            // Remove Elytra after 30 seconds
	            new Timer().schedule(new TimerTask() {
	                @Override
	                public void run() {
	                    if (player.getItemStackFromSlot(EquipmentSlotType.CHEST).getItem() == Items.ELYTRA) {
	                        player.setItemStackToSlot(EquipmentSlotType.CHEST, ItemStack.EMPTY);
	                        player.sendMessage(new StringTextComponent("Your wings disappear..."));
	                    }
	                }
	            }, 30000);
	        }
	        return ActionResult.resultSuccess(player.getHeldItem(hand));
	        
	  }
	    
}



