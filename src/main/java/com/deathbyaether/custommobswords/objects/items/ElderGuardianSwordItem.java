package com.deathbyaether.custommobswords.objects.items;

import java.util.List;

import com.deathbyaether.custommobswords.objects.entities.GuardianLaserBeamEntity;

import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.item.IItemTier;
import net.minecraft.item.ItemStack;
import net.minecraft.item.SwordItem;
import net.minecraft.particles.ParticleTypes;
import net.minecraft.potion.EffectInstance;
import net.minecraft.potion.Effects;
import net.minecraft.util.ActionResult;
import net.minecraft.util.DamageSource;
import net.minecraft.util.Hand;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.SoundEvents;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.Vec3d;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.StringTextComponent;
import net.minecraft.world.World;
import net.minecraftforge.event.TickEvent;
import net.minecraftforge.event.entity.living.LivingAttackEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod.EventBusSubscriber;
import net.minecraftforge.fml.common.Mod.EventBusSubscriber.Bus;

@EventBusSubscriber(modid = com.deathbyaether.custommobswords.Main.MOD_ID, bus = Bus.FORGE)
public class ElderGuardianSwordItem extends SwordItem {
    private static final int WAVE_RADIUS = 6;
    private static final int LASER_DAMAGE = 3;
    
    public ElderGuardianSwordItem(IItemTier tier, int attackDamage, float attackSpeed, Properties properties) {
        super(tier, attackDamage, attackSpeed, properties);
    }

    @Override
    @net.minecraftforge.api.distmarker.OnlyIn(net.minecraftforge.api.distmarker.Dist.CLIENT)
    public void addInformation(ItemStack stack, World world, List<ITextComponent> tooltip, ITooltipFlag flag) {
        super.addInformation(stack, world, tooltip, flag);
        tooltip.add(new StringTextComponent(Screen.hasShiftDown()
                ? "Right-click sends a guardian pulse; crouch and right-click to fire the Elder Guardian's beam. Water grants Dolphin's Grace."
                : "Hold Shift for abilities."));
    }

    private LivingEntity shooter;

    public void setShooter(LivingEntity shooter) {
        this.shooter = shooter;
    }

    public LivingEntity getShooter() {
        return this.shooter;
    }
    
    @Override
    public ActionResult<ItemStack> onItemRightClick(World world, PlayerEntity player, Hand hand) {
        if (world.isRemote) {
            if (!player.isSneaking()) spawnPulseEffect(world, player);
        } else {
            if (player.isSneaking()) {
                // Fires Guardian Laser Beam when sneaking
                GuardianLaserBeamEntity laserBeam = new GuardianLaserBeamEntity(world, player);
                laserBeam.setShooter(player);
                Vec3d lookVec = player.getLookVec();
                laserBeam.setPosition(player.getPosX(), player.getPosYEye(), player.getPosZ());
                laserBeam.setMotion(lookVec.x * 1.5, lookVec.y * 1.5, lookVec.z * 1.5);
                player.world.addEntity(laserBeam);
                player.playSound(SoundEvents.ENTITY_ELDER_GUARDIAN_CURSE, 1.0F, 1.0F);
            } else {
                // Applies mining fatigue and slowness to nearby enemies
                AxisAlignedBB area = player.getBoundingBox().grow(WAVE_RADIUS);
                List<LivingEntity> entities = world.getEntitiesWithinAABB(LivingEntity.class, area);
                for (LivingEntity entity : entities) {
                    if (entity != player) {
                        entity.addPotionEffect(new EffectInstance(Effects.MINING_FATIGUE, 100, 1));
                        entity.addPotionEffect(new EffectInstance(Effects.SLOWNESS, 100, 1));
                    }
                }
                player.world.playSound(null, player.getPosition(), SoundEvents.ENTITY_ELDER_GUARDIAN_CURSE, SoundCategory.PLAYERS, 1.0F, 1.0F);
            }
        }
        return ActionResult.resultSuccess(player.getHeldItem(hand));
    }

    /** Draws three expanding-looking rings of bubbles and guardian magic for the pulse. */
    private static void spawnPulseEffect(World world, PlayerEntity player) {
        for (int ring = 0; ring < 3; ring++) {
            double radius = 1.0D + ring * 1.7D;
            double speed = 0.04D + ring * 0.025D;
            for (int i = 0; i < 24; i++) {
                double angle = Math.PI * 2.0D * i / 24.0D;
                double dx = Math.cos(angle);
                double dz = Math.sin(angle);
                double x = player.getPosX() + dx * radius;
                double y = player.getPosY() + 0.15D + ring * 0.08D;
                double z = player.getPosZ() + dz * radius;
                world.addParticle(i % 2 == 0 ? ParticleTypes.BUBBLE : ParticleTypes.ENCHANT,
                        x, y, z, dx * speed, 0.025D, dz * speed);
            }
        }
    }
    
    @Override
    public boolean hitEntity(ItemStack stack, LivingEntity target, LivingEntity attacker) {
        if (!attacker.world.isRemote) {
            GuardianLaserBeamEntity beam = new GuardianLaserBeamEntity(attacker.world, attacker);
            beam.shoot(attacker, attacker.rotationPitch, attacker.rotationYaw, 0.0F, 1.0F, 0.0F);
            attacker.world.addEntity(beam);
            attacker.playSound(SoundEvents.ENTITY_ELDER_GUARDIAN_HURT, 1.0F, 1.0F);

            // Applies Mining Fatigue & Slowness on hit
            target.addPotionEffect(new EffectInstance(Effects.MINING_FATIGUE, 200, 1));
            target.addPotionEffect(new EffectInstance(Effects.SLOWNESS, 200, 1));
        }
        return super.hitEntity(stack, target, attacker);
    }
    
    @SubscribeEvent
    public static void onPlayerDamaged(LivingAttackEvent event) {
        if (event.getEntityLiving().world.isRemote || event.getSource().getTrueSource() == null) {
            return;
        }
        if (event.getEntityLiving() instanceof PlayerEntity) {
            PlayerEntity player = (PlayerEntity) event.getEntityLiving();
            if (player.getHeldItemMainhand().getItem() instanceof ElderGuardianSwordItem) {
                event.getSource().getTrueSource().attackEntityFrom(DamageSource.causeThornsDamage(player), 2.0F);
            }
        }
    }
    
    @SubscribeEvent
    public static void onPlayerInWater(TickEvent.PlayerTickEvent event) {
        PlayerEntity player = event.player;
        if (event.phase == TickEvent.Phase.END && !player.world.isRemote && player.isInWater()
                && player.getHeldItemMainhand().getItem() instanceof ElderGuardianSwordItem) {
            player.addPotionEffect(new EffectInstance(Effects.DOLPHINS_GRACE, 100, 1));
        }
    }
}
