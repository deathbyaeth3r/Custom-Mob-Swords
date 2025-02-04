package com.deathbyaether.custommobswords.objects.items;

import java.util.List;
import java.util.Random;

import org.lwjgl.glfw.GLFW;

import com.deathbyaether.custommobswords.objects.entities.ChickenEggProjectileEntity;
import com.deathbyaether.custommobswords.objects.entities.ChickenHatchingEggEntity;
import com.deathbyaether.custommobswords.util.enums.ModItemTier;

import net.minecraft.client.Minecraft;
import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.client.util.InputMappings;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.item.SwordItem;
import net.minecraft.potion.EffectInstance;
import net.minecraft.potion.Effects;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Hand;
import net.minecraft.util.SoundEvents;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.StringTextComponent;
import net.minecraft.world.World;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.event.TickEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;

public class ChickenSwordItem extends SwordItem {

    public ChickenSwordItem(ModItemTier swordGem, int attackDamage, float attackSpeed, Properties properties) {
        super(swordGem, attackDamage, attackSpeed, properties);
        net.minecraftforge.common.MinecraftForge.EVENT_BUS.register(this);
    }

    @Override
    @OnlyIn(Dist.CLIENT)
    public void addInformation(ItemStack stack, World worldIn, List<ITextComponent> tooltip, ITooltipFlag flagIn) {
        super.addInformation(stack, worldIn, tooltip, flagIn);
        if (InputMappings.isKeyDown(Minecraft.getInstance().getMainWindow().getHandle(), GLFW.GLFW_KEY_LEFT_SHIFT)) {
            tooltip.add(new StringTextComponent("Shoots an egg that launches enemies on right-click"));
            tooltip.add(new StringTextComponent("Spawns multiple hatching eggs on shift + right-click"));
        } else {
            tooltip.add(new StringTextComponent("Hold" + "§e" + " Shift " + "§7" + "for abilities!"));
        }
    }

    @SubscribeEvent
    public void onPlayerTick(TickEvent.PlayerTickEvent event) {
        PlayerEntity player = event.player;
        if (player.getHeldItemMainhand().getItem() instanceof ChickenSwordItem) {
            player.addPotionEffect(new EffectInstance(Effects.SLOW_FALLING, 10, 0, false, false));
        }
    }

    @Override
    public boolean onLeftClickEntity(ItemStack stack, PlayerEntity playerIn, Entity entity) {
        playerIn.playSound(SoundEvents.ENTITY_CHICKEN_AMBIENT, 5F, 0.8F + random.nextFloat() * 0.3F);
        return super.onLeftClickEntity(stack, playerIn, entity);
    }

    @Override
    public ActionResult<ItemStack> onItemRightClick(World world, PlayerEntity player, Hand hand) {
        Random rand = new Random();
        ItemStack stack = player.getHeldItem(hand);

        if (!world.isRemote && player.isSneaking()) {
            for (int i = 0; i < 5; i++) {
                ChickenHatchingEggEntity hatchingEgg = new ChickenHatchingEggEntity(player, world);
                hatchingEgg.setPosition(player.getPosX(), player.getPosY() + 5.5, player.getPosZ()); // Spawns higher above player
                
                // Shoots downward at a 45-degree angle
                float angle = (float) (rand.nextFloat() * Math.PI * 2);
                double motionX = MathHelper.cos(angle) * 1.0;
                double motionZ = MathHelper.sin(angle) * 1.0;
                hatchingEgg.setMotion(motionX, -1.2, motionZ);
                
                world.addEntity(hatchingEgg);
            }
            
            return ActionResult.resultSuccess(stack);
        }
        
        if (!world.isRemote) {
            ChickenEggProjectileEntity projectile = new ChickenEggProjectileEntity(player, world);
            projectile.setPosition(player.getPosX(), player.getPosY() + 1.5, player.getPosZ());
            projectile.shoot(player, player.rotationPitch, player.rotationYaw, 0.00F, 1.5F, 1.0F);
            world.addEntity(projectile);
        }
        return ActionResult.resultSuccess(stack);
    }
}
