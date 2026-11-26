package com.deathbyaether.custommobswords.objects.items;

import java.util.List;

import org.lwjgl.glfw.GLFW;

import com.deathbyaether.custommobswords.objects.entities.CarrotProjectileEntity;
import com.deathbyaether.custommobswords.util.enums.ModItemTier;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.client.util.InputMappings;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.inventory.InventoryHelper;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.item.SwordItem;
import net.minecraft.potion.EffectInstance;
import net.minecraft.potion.Effects;
import net.minecraft.util.ActionResult;
import net.minecraft.util.DamageSource;
import net.minecraft.util.Hand;
import net.minecraft.util.SoundEvents;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.StringTextComponent;
import net.minecraft.world.World;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.event.entity.living.LivingAttackEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;

public class DonkeySwordItem extends SwordItem {

    public DonkeySwordItem(ModItemTier swordGem, int attackDamage, float attackSpeed, Properties properties) {
        super(swordGem, attackDamage, attackSpeed, properties);
        net.minecraftforge.common.MinecraftForge.EVENT_BUS.register(this);
    }

    @Override
    @OnlyIn(Dist.CLIENT)
    public void addInformation(ItemStack stack, World worldIn, List<ITextComponent> tooltip, ITooltipFlag flagIn) {
        super.addInformation(stack, worldIn, tooltip, flagIn);
        if (Screen.hasShiftDown()) {
            tooltip.add(new StringTextComponent("Right-Click: Opens extra inventory (Cargo Mode)"));
            tooltip.add(new StringTextComponent("Shift + Right-Click: Grants Speed and Jump Boost"));
            tooltip.add(new StringTextComponent("Left-Click: Knocks back enemies behind you (Donkey Kick)"));
        } else {
            tooltip.add(new StringTextComponent("Hold" + "�e" + " Shift " + "�7" + "for abilities!"));
        }
    }
    @Override
    public ActionResult<ItemStack> onItemRightClick(World world, PlayerEntity player, Hand hand) {
        ItemStack stack = player.getHeldItem(hand);

        if (!world.isRemote) {
            if (player.isSneaking()) {
                // Speed Boost
                player.addPotionEffect(new EffectInstance(Effects.SPEED, 200, 1));
                player.addPotionEffect(new EffectInstance(Effects.JUMP_BOOST, 200, 1));
                player.playSound(SoundEvents.ENTITY_HORSE_GALLOP, 1.0F, 1.0F);
                return ActionResult.resultSuccess(stack);
            }

            if (!player.isSneaking()) {
                // Cargo Mode (Drops Random Items for Now)
                ItemStack loot = new ItemStack(Items.CHEST, 1);
                InventoryHelper.spawnItemStack(world, player.getPosX(), player.getPosY(), player.getPosZ(), loot);
                player.playSound(SoundEvents.ENTITY_DONKEY_CHEST, 1.0F, 1.0F);
                return ActionResult.resultSuccess(stack);
            }

            // Shoots Carrot Projectile (only if not in Cargo Mode or Speed Boost)
            CarrotProjectileEntity projectile = new CarrotProjectileEntity(player, world);
            projectile.setPosition(player.getPosX(), player.getPosYEye(), player.getPosZ());
            projectile.shoot(player, player.rotationPitch, player.rotationYaw, 0.0F, 1.5F, 1.0F);
            world.addEntity(projectile);
            player.playSound(SoundEvents.ENTITY_DONKEY_CHEST, 1.0F, 1.0F);
            return ActionResult.resultSuccess(stack);
            }
        return ActionResult.resultSuccess(stack);
        }
    
    @Override
    public boolean hitEntity(ItemStack stack, LivingEntity target, LivingEntity attacker) {
        if (!attacker.world.isRemote) {
            System.out.println("Left click detected: Applying knockback");

            // Apply knockback effect
            double xRatio = attacker.getPosX() - target.getPosX();
            double zRatio = attacker.getPosZ() - target.getPosZ();
            double magnitude = Math.sqrt(xRatio * xRatio + zRatio * zRatio);

            if (magnitude != 0) {
                target.setMotion(xRatio / magnitude * 2.5, 0.5, zRatio / magnitude * 2.5);
            }

            // Apply damage
            target.attackEntityFrom(DamageSource.causePlayerDamage((PlayerEntity) attacker), 4.0F);

            System.out.println("Knockback applied!");

            // Spawn Carrot Projectile
            CarrotProjectileEntity projectile = new CarrotProjectileEntity(attacker, attacker.world);
            projectile.setPosition(attacker.getPosX(), attacker.getPosYEye(), attacker.getPosZ());
            projectile.shoot(attacker, attacker.rotationPitch, attacker.rotationYaw, 0.0F, 1.5F, 1.0F);
            attacker.world.addEntity(projectile);

            System.out.println("CarrotProjectileEntity spawned!");
       }
        return super.hitEntity(stack, target, attacker);
   }
    
}
