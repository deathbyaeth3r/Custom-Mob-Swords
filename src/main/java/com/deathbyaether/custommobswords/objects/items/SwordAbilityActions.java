package com.deathbyaether.custommobswords.objects.items;

import com.deathbyaether.custommobswords.objects.entities.CarrotProjectileEntity;
import com.deathbyaether.custommobswords.objects.entities.CaveSpiderProjectileEntity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.projectile.ArrowEntity;
import net.minecraft.entity.projectile.PotionEntity;
import net.minecraft.entity.projectile.SnowballEntity;
import net.minecraft.entity.projectile.SmallFireballEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.item.SwordItem;
import net.minecraft.potion.Effect;
import net.minecraft.potion.EffectInstance;
import net.minecraft.potion.Effects;
import net.minecraft.potion.PotionUtils;
import net.minecraft.potion.Potions;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Hand;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.world.World;

final class SwordAbilityActions {
	private SwordAbilityActions() {}

	static ActionResult<ItemStack> activate(String id, SwordItem sword, World world, PlayerEntity player, Hand hand) {
		ItemStack stack = player.getItemInHand(hand);
		if (!world.isClientSide && !player.getCooldowns().isOnCooldown(sword)) {
			switch (id) {
			case "endermite_sword": shootVenom(player); pulse(player, Effects.POISON, 100, 0, 5); break;
			case "guardian_sword": self(player, Effects.WATER_BREATHING, 600, 0); self(player, Effects.DOLPHINS_GRACE, 240, 0); break;
			case "horse_sword": self(player, Effects.MOVEMENT_SPEED, 240, 1); self(player, Effects.JUMP, 240, 1); break;
			case "husk_sword": pulse(player, Effects.HUNGER, 200, 0, 5); pulse(player, Effects.MOVEMENT_SLOWDOWN, 100, 0, 5); break;
			case "irongolem_sword": self(player, Effects.DAMAGE_RESISTANCE, 160, 1); self(player, Effects.DAMAGE_BOOST, 160, 0); break;
			case "magmacube_sword": self(player, Effects.FIRE_RESISTANCE, 400, 0); self(player, Effects.DAMAGE_BOOST, 160, 0); shootMagmaVolley(player); break;
			case "mooshroom_sword": self(player, Effects.REGENERATION, 160, 1); self(player, Effects.SATURATION, 1, 0); break;
			case "mule_sword": self(player, Effects.MOVEMENT_SPEED, 300, 0); self(player, Effects.DAMAGE_RESISTANCE, 100, 0); break;
			case "ocelot_sword": self(player, Effects.MOVEMENT_SPEED, 240, 1); self(player, Effects.INVISIBILITY, 100, 0); break;
			case "pig_sword": self(player, Effects.JUMP, 240, 1); self(player, Effects.MOVEMENT_SPEED, 160, 0); shootCarrot(player); break;
			case "polarbear_sword": self(player, Effects.DAMAGE_BOOST, 160, 1); self(player, Effects.DAMAGE_RESISTANCE, 160, 0); break;
			case "rabbit_sword": self(player, Effects.JUMP, 300, 2); self(player, Effects.MOVEMENT_SPEED, 200, 1); break;
			case "sheep_sword": self(player, Effects.REGENERATION, 200, 0); self(player, Effects.ABSORPTION, 300, 0); break;
			case "silverfish_sword": pulse(player, Effects.MOVEMENT_SLOWDOWN, 160, 1, 5); pulse(player, Effects.WEAKNESS, 160, 0, 5); break;
			case "snowgolem_sword":
				for (int i = 0; i < 5; i++) { SnowballEntity snowball = new SnowballEntity(world, player); snowball.shootFromRotation(player, player.xRot, player.yRot, -10.0F, 1.4F, 12.0F); world.addFreshEntity(snowball); }
				break;
			case "stray_sword": ArrowEntity arrow = new ArrowEntity(world, player); arrow.setBaseDamage(3.0D); arrow.shootFromRotation(player, player.xRot, player.yRot, 0.0F, 2.2F, 1.0F); world.addFreshEntity(arrow); break;
			case "villager_sword": self(player, Effects.REGENERATION, 120, 0); self(player, Effects.LUCK, 400, 0); break;
			case "witch_sword": self(player, Effects.REGENERATION, 160, 0); self(player, Effects.DAMAGE_RESISTANCE, 160, 0); throwHarmingPotion(player); break;
			case "witherskeleton_sword": self(player, Effects.DAMAGE_BOOST, 200, 1); pulse(player, Effects.WITHER, 100, 0, 4); break;
			case "wolf_sword": self(player, Effects.MOVEMENT_SPEED, 240, 1); self(player, Effects.DAMAGE_BOOST, 160, 0); break;
			case "zombie_sword": pulse(player, Effects.WEAKNESS, 160, 0, 5); pulse(player, Effects.HUNGER, 160, 0, 5); break;
			case "zombievillager_sword": cleanse(player); self(player, Effects.REGENERATION, 100, 0); break;
			default: return ActionResult.pass(stack);
			}
			player.getCooldowns().addCooldown(sword, 200);
		}
		return ActionResult.success(stack);
	}

	private static void self(PlayerEntity player, Effect effect, int duration, int amplifier) { player.addEffect(new EffectInstance(effect, duration, amplifier)); }
	private static void pulse(PlayerEntity player, Effect effect, int duration, int amplifier, double radius) {
		for (LivingEntity target : player.level.getEntitiesOfClass(LivingEntity.class, player.getBoundingBox().inflate(radius)))
			if (target != player && target.isAlive()) target.addEffect(new EffectInstance(effect, duration, amplifier));
	}
	private static void cleanse(PlayerEntity player) {
		for (EffectInstance effect : player.getActiveEffects()) if (!effect.getEffect().isBeneficial()) player.removeEffect(effect.getEffect());
	}

	private static void shootVenom(PlayerEntity player) {
		CaveSpiderProjectileEntity bolt = new CaveSpiderProjectileEntity(player, player.level);
		bolt.shootFromRotation(player, player.xRot, player.yRot, 0.0F, 1.7F, 1.0F);
		player.level.addFreshEntity(bolt);
	}

	private static void shootCarrot(PlayerEntity player) {
		CarrotProjectileEntity carrot = new CarrotProjectileEntity(player, player.level);
		carrot.shootFromRotation(player, player.xRot, player.yRot, 0.0F, 1.6F, 1.0F);
		player.level.addFreshEntity(carrot);
	}

	private static void shootMagmaVolley(PlayerEntity player) {
		net.minecraft.util.math.vector.Vector3d look = player.getLookAngle();
		for (int i = -1; i <= 1; i++) {
			SmallFireballEntity fireball = new SmallFireballEntity(player.level, player.getX() + i * 0.35D,
					player.getEyeY(), player.getZ(), look.x, look.y, look.z);
			player.level.addFreshEntity(fireball);
		}
	}

	private static void throwHarmingPotion(PlayerEntity player) {
		PotionEntity potion = new PotionEntity(player.level, player);
		potion.setItem(PotionUtils.setPotion(new ItemStack(Items.SPLASH_POTION), Potions.HARMING));
		potion.shootFromRotation(player, player.xRot, player.yRot, -20.0F, 0.8F, 1.0F);
		player.level.addFreshEntity(potion);
	}
}
