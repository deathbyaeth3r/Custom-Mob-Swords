package com.deathbyaether.custommobswords.objects.items;

import java.util.List;

import org.lwjgl.glfw.GLFW;

import com.deathbyaether.custommobswords.objects.entities.MilkSplashProjectileEntity;
import com.deathbyaether.custommobswords.util.enums.ModItemTier;

import net.minecraft.client.Minecraft;
import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.client.util.InputMappings;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.item.SwordItem;
import net.minecraft.util.ActionResult;
import net.minecraft.util.DamageSource;
import net.minecraft.util.Hand;
import net.minecraft.util.SoundEvents;
import net.minecraft.util.math.Vec3d;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.StringTextComponent;
import net.minecraft.world.World;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.event.TickEvent;
import net.minecraftforge.event.entity.living.LivingFallEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;

public class CowSwordItem extends SwordItem {

    public CowSwordItem(ModItemTier swordGem, int attackDamage, float attackSpeed, Properties properties) {
        super(swordGem, attackDamage, attackSpeed, properties);
        net.minecraftforge.common.MinecraftForge.EVENT_BUS.register(this);
    }

    @Override
    @OnlyIn(Dist.CLIENT)
    public void addInformation(ItemStack stack, World worldIn, List<ITextComponent> tooltip, ITooltipFlag flagIn) {
        super.addInformation(stack, worldIn, tooltip, flagIn);
        if (InputMappings.isKeyDown(Minecraft.getInstance().getMainWindow().getHandle(), GLFW.GLFW_KEY_LEFT_SHIFT)) {
            tooltip.add(new StringTextComponent("Right-Click: Removes negative effects (Milk Shield)"));
            tooltip.add(new StringTextComponent("Shift + Right-Click: Charge forward and knock enemies back"));
            tooltip.add(new StringTextComponent("Landing: Stomps the ground, damaging nearby enemies"));
        } else {
            tooltip.add(new StringTextComponent("Hold" + "§e" + " Shift " + "§7" + "for abilities!"));
        }
    }

    @Override
    public ActionResult<ItemStack> onItemRightClick(World world, PlayerEntity player, Hand hand) {
        ItemStack stack = player.getHeldItem(hand);

        if (!world.isRemote) {
            if (player.isSneaking()) {
                // Charge Attack
                Vec3d look = player.getLookVec();
                player.setMotion(look.x * 2.0, 0.5, look.z * 2.0);
                player.velocityChanged = true;
                player.playSound(SoundEvents.ENTITY_COW_AMBIENT, 1.0F, 1.0F);
            } else {
                // Milk Shield: Removes negative effects
                player.clearActivePotions();
              
             // Spawn Milk Splash Projectile
                MilkSplashProjectileEntity projectile = new MilkSplashProjectileEntity(player, world);
                projectile.setPosition(player.getPosX(), player.getPosYEye(), player.getPosZ());
                projectile.shoot(player, player.rotationPitch, player.rotationYaw, 0.0F, 1.5F, 1.0F);
                world.addEntity(projectile);
                player.playSound(SoundEvents.ENTITY_COW_MILK, 1.0F, 1.0F);
            }
        }
        return ActionResult.resultSuccess(stack);
    }

    @SubscribeEvent
    public void onPlayerTick(TickEvent.PlayerTickEvent event) {
        PlayerEntity player = event.player;
        if (!player.world.isRemote && player.fallDistance > 1 && player.onGround && player.getHeldItemMainhand().getItem() instanceof CowSwordItem) {
            // Cow Stomp Effect
            player.world.createExplosion(null, player.getPosX(), player.getPosY(), player.getPosZ(), 0.0F, false, net.minecraft.world.Explosion.Mode.NONE);
            player.world.getEntitiesWithinAABB(LivingEntity.class, player.getBoundingBox().grow(3)).forEach(entity -> {
                if (entity != player) {
                    Vec3d knockback = entity.getPositionVec().subtract(player.getPositionVec()).normalize().scale(1.5);
                    entity.setMotion(knockback.x, 0.5, knockback.z);
                    entity.velocityChanged = true;
                    entity.attackEntityFrom(DamageSource.causePlayerDamage(player), 8.0F);
                }
            });
            player.world.playSound(null, player.getPosition(), SoundEvents.ENTITY_COW_HURT, player.getSoundCategory(), 1.0F, 1.0F);
            player.fallDistance = 0; // Prevent normal fall damage
        }
    } 
}
