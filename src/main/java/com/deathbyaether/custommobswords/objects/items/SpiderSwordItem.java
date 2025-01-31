package com.deathbyaether.custommobswords.objects.items;

import java.util.List;

import org.lwjgl.glfw.GLFW;

import com.deathbyaether.custommobswords.objects.entities.SpiderWebProjectileEntity;
import com.deathbyaether.custommobswords.util.enums.ModItemTier;

import net.minecraft.client.Minecraft;
import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.client.util.InputMappings;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.item.SwordItem;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Direction;
import net.minecraft.util.Hand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.StringTextComponent;
import net.minecraft.world.World;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.event.TickEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

@Mod.EventBusSubscriber
public class SpiderSwordItem extends SwordItem {

    public SpiderSwordItem(ModItemTier swordGem, int i, int j, Properties properties) {
        super(swordGem, j, i, properties);
    }

    @Override
    @OnlyIn(Dist.CLIENT)
    public void addInformation(ItemStack stack, World worldIn, List<ITextComponent> tooltip, ITooltipFlag flagIn) {
        super.addInformation(stack, worldIn, tooltip, flagIn);
        if (InputMappings.isKeyDown(Minecraft.getInstance().getMainWindow().getHandle(), GLFW.GLFW_KEY_LEFT_SHIFT)) {
            tooltip.add(new StringTextComponent("\u00A7eCrouch near a wall to climb!"));
            tooltip.add(new StringTextComponent("\u00A7eRight click to shoot cobwebs!"));
        } else {
            tooltip.add(new StringTextComponent("Hold" + "\u00A7e Shift " + "\u00A77 for Info!"));
        }
    }

    public ActionResult<ItemStack> onItemRightClick(World worldIn, PlayerEntity playerIn, Hand handIn) {
    	
    	ItemStack stack = playerIn.getHeldItem(handIn);
		
		if(!worldIn.isRemote) {
			
			SpiderWebProjectileEntity spiderweb_projectile = new SpiderWebProjectileEntity(playerIn, worldIn);
			
			spiderweb_projectile.setItem(stack);
			spiderweb_projectile.shoot(playerIn, playerIn.rotationPitch, playerIn.rotationYaw, 0.0F, 2F, 1.0F);
			worldIn.addEntity(spiderweb_projectile);
			
			//playerIn.getCooldownTracker().setCooldown(this, 100);
			
			
		}
	
		
		if(!playerIn.abilities.isCreativeMode) {
			
		}
		
		
        return ActionResult.resultSuccess(playerIn.getHeldItem(handIn));
    }

    @SubscribeEvent
    public static void onPlayerTick(TickEvent.PlayerTickEvent event) {
        PlayerEntity player = event.player;
        World world = player.world;

        if (!world.isRemote && player.isCrouching() && player.getHeldItemMainhand().getItem() instanceof SpiderSwordItem) {
            BlockPos pos = player.getPosition();
            Direction facing = player.getHorizontalFacing();

            // Check if the player is touching a solid block (wall)
            BlockPos wallPos = pos.offset(facing);
            if (world.getBlockState(wallPos).isSolid()) {
                // Apply climbing effect
                player.setMotion(player.getMotion().x, 0.3, player.getMotion().z);
                player.velocityChanged = true; // Force update so motion takes effect
                player.fallDistance = 0; // Prevent fall damage
            }
        }
    }
}