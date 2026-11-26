package com.deathbyaether.custommobswords.objects.items;

import java.util.List;

import com.deathbyaether.custommobswords.Main;
import com.deathbyaether.custommobswords.objects.entities.GuardianLaserBeamEntity;
import com.deathbyaether.custommobswords.util.enums.ModItemTier;

import net.minecraft.client.gui.screen.Screen;
import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item.Properties;
import net.minecraft.item.ItemStack;
import net.minecraft.item.SwordItem;
import net.minecraft.potion.EffectInstance;
import net.minecraft.potion.Effects;
import net.minecraft.particles.ParticleTypes;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Hand;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.SoundEvents;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.StringTextComponent;
import net.minecraft.world.World;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.event.TickEvent;
import net.minecraftforge.event.entity.living.LivingAttackEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod.EventBusSubscriber;
import net.minecraftforge.fml.common.Mod.EventBusSubscriber.Bus;

@EventBusSubscriber(modid = Main.MOD_ID, bus = Bus.FORGE)
public class ElderGuardianSwordItem extends SwordItem {

	public ElderGuardianSwordItem(ModItemTier tier, int attackDamage, float attackSpeed, Properties properties) {
		super(tier, attackDamage, attackSpeed, properties);
	}

	@Override
	@OnlyIn(Dist.CLIENT)
	public void appendHoverText(ItemStack stack, World world, List<ITextComponent> tooltip, ITooltipFlag flag) {
		super.appendHoverText(stack, world, tooltip, flag);
		tooltip.add(new StringTextComponent(Screen.hasShiftDown()
				? "Right-click sends a guardian pulse; crouch and right-click to fire the Elder Guardian's tracking beam. Water grants Dolphin's Grace."
				: "Right-click: pulse. Crouch + right-click: guardian beam."));
	}

	@Override
	public ActionResult<ItemStack> use(World world, PlayerEntity player, Hand hand) {
		ItemStack stack = player.getItemInHand(hand);
		if (world.isClientSide) {
			if (!player.isShiftKeyDown() && !player.getCooldowns().isOnCooldown(this)) {
				spawnPulseEffect(world, player);
			}
		} else {
			if (player.isShiftKeyDown()) {
				GuardianLaserBeamEntity beam = new GuardianLaserBeamEntity(world, player);
				beam.setPos(player.getX(), player.getEyeY(), player.getZ());
				net.minecraft.util.math.vector.Vector3d look = player.getLookAngle();
				beam.shoot(look.x, look.y, look.z, 1.8F, 1.0F);
				world.addFreshEntity(beam);
				player.playSound(net.minecraft.util.SoundEvents.ELDER_GUARDIAN_CURSE, 1.0F, 1.0F);
			} else {
				for (LivingEntity target : world.getEntitiesOfClass(LivingEntity.class,
						player.getBoundingBox().inflate(6.0D))) {
					if (target != player) {
						target.addEffect(new EffectInstance(Effects.MOVEMENT_SLOWDOWN, 100, 1));
						target.addEffect(new EffectInstance(Effects.DIG_SLOWDOWN, 100, 1));
					}
				}
				world.playSound(null, player.blockPosition(), SoundEvents.ELDER_GUARDIAN_CURSE,
						SoundCategory.PLAYERS, 1.0F, 1.0F);
			}
			player.getCooldowns().addCooldown(this, 100);
		}
		return ActionResult.success(stack);
	}

	/** Draws three rings of bubbles and guardian magic around the player. */
	private static void spawnPulseEffect(World world, PlayerEntity player) {
		for (int ring = 0; ring < 3; ring++) {
			double radius = 1.0D + ring * 1.7D;
			double speed = 0.04D + ring * 0.025D;
			for (int i = 0; i < 24; i++) {
				double angle = Math.PI * 2.0D * i / 24.0D;
				double dx = Math.cos(angle);
				double dz = Math.sin(angle);
				double x = player.getX() + dx * radius;
				double y = player.getY() + 0.15D + ring * 0.08D;
				double z = player.getZ() + dz * radius;
				world.addParticle(i % 2 == 0 ? ParticleTypes.BUBBLE : ParticleTypes.ENCHANT,
						x, y, z, dx * speed, 0.025D, dz * speed);
			}
		}
	}

	@Override
	public boolean onLeftClickEntity(ItemStack stack, PlayerEntity player, Entity entity) {
		if (!player.level.isClientSide && entity instanceof LivingEntity) {
			((LivingEntity) entity).addEffect(new EffectInstance(Effects.MOVEMENT_SLOWDOWN, 100, 0));
		}
		return super.onLeftClickEntity(stack, player, entity);
	}

	@SubscribeEvent
	public static void onPlayerInWater(TickEvent.PlayerTickEvent event) {
		PlayerEntity player = event.player;
		if (event.phase == TickEvent.Phase.END && !player.level.isClientSide && player.isInWater()
				&& player.getMainHandItem().getItem() instanceof ElderGuardianSwordItem) {
			player.addEffect(new EffectInstance(Effects.DOLPHINS_GRACE, 100, 0));
		}
	}

	@SubscribeEvent
	public static void retaliateWhenHit(LivingAttackEvent event) {
		if (event.getEntityLiving() instanceof PlayerEntity && !event.getEntityLiving().level.isClientSide
				&& event.getSource().getEntity() instanceof LivingEntity) {
			PlayerEntity player = (PlayerEntity) event.getEntityLiving();
			if (player.getMainHandItem().getItem() instanceof ElderGuardianSwordItem) {
				((LivingEntity) event.getSource().getEntity()).addEffect(new EffectInstance(Effects.MOVEMENT_SLOWDOWN, 60, 0));
			}
		}
	}
}
