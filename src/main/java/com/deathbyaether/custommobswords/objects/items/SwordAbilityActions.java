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
import net.minecraft.world.World;

final class SwordAbilityActions {
	private SwordAbilityActions() {}

	static ActionResult<ItemStack> activate(String id, SwordItem sword, World world, PlayerEntity player, Hand hand) {
		ItemStack stack = player.getHeldItem(hand);
		if (!world.isRemote && !player.getCooldownTracker().hasCooldown(sword)) {
			switch (id) {
			case "endermite_sword": shootVenom(player); pulse(player, Effects.POISON, 100, 0, 5); break;
			case "guardian_sword": self(player, Effects.WATER_BREATHING, 600, 0); self(player, Effects.DOLPHINS_GRACE, 240, 0); break;
			case "horse_sword": self(player, Effects.SPEED, 240, 1); self(player, Effects.JUMP_BOOST, 240, 1); break;
			case "husk_sword": pulse(player, Effects.HUNGER, 200, 0, 5); pulse(player, Effects.SLOWNESS, 100, 0, 5); break;
			case "irongolem_sword": self(player, Effects.RESISTANCE, 160, 1); self(player, Effects.STRENGTH, 160, 0); break;
			case "magmacube_sword": self(player, Effects.FIRE_RESISTANCE, 400, 0); self(player, Effects.STRENGTH, 160, 0); shootMagmaVolley(player); break;
			case "mooshroom_sword": self(player, Effects.REGENERATION, 160, 1); self(player, Effects.SATURATION, 1, 0); break;
			case "mule_sword": self(player, Effects.SPEED, 300, 0); self(player, Effects.RESISTANCE, 100, 0); break;
			case "ocelot_sword": self(player, Effects.SPEED, 240, 1); self(player, Effects.INVISIBILITY, 100, 0); break;
			case "pig_sword": self(player, Effects.JUMP_BOOST, 240, 1); self(player, Effects.SPEED, 160, 0); shootCarrot(player); break;
			case "polarbear_sword": self(player, Effects.STRENGTH, 160, 1); self(player, Effects.RESISTANCE, 160, 0); break;
			case "rabbit_sword": self(player, Effects.JUMP_BOOST, 300, 2); self(player, Effects.SPEED, 200, 1); break;
			case "sheep_sword": self(player, Effects.REGENERATION, 200, 0); self(player, Effects.ABSORPTION, 300, 0); break;
			case "silverfish_sword": pulse(player, Effects.SLOWNESS, 160, 1, 5); pulse(player, Effects.WEAKNESS, 160, 0, 5); break;
			case "snowgolem_sword":
				for (int i = 0; i < 5; i++) { SnowballEntity snowball = new SnowballEntity(world, player); snowball.shoot(player, player.rotationPitch, player.rotationYaw, -10.0F, 1.4F, 12.0F); world.addEntity(snowball); }
				break;
			case "stray_sword": ArrowEntity arrow = new ArrowEntity(world, player); arrow.setDamage(3.0D); arrow.shoot(player, player.rotationPitch, player.rotationYaw, 0.0F, 2.2F, 1.0F); world.addEntity(arrow); break;
			case "villager_sword": self(player, Effects.REGENERATION, 120, 0); self(player, Effects.LUCK, 400, 0); break;
			case "witch_sword": self(player, Effects.REGENERATION, 160, 0); self(player, Effects.RESISTANCE, 160, 0); throwHarmingPotion(player); break;
			case "witherskeleton_sword": self(player, Effects.STRENGTH, 200, 1); pulse(player, Effects.WITHER, 100, 0, 4); break;
			case "wolf_sword": self(player, Effects.SPEED, 240, 1); self(player, Effects.STRENGTH, 160, 0); break;
			case "zombie_sword": pulse(player, Effects.WEAKNESS, 160, 0, 5); pulse(player, Effects.HUNGER, 160, 0, 5); break;
			case "zombievillager_sword": cleanse(player); self(player, Effects.REGENERATION, 100, 0); break;
			default: return ActionResult.resultPass(stack);
			}
			player.getCooldownTracker().setCooldown(sword, 200);
		}
		return ActionResult.resultSuccess(stack);
	}

	private static void self(PlayerEntity player, Effect effect, int duration, int amplifier) { player.addPotionEffect(new EffectInstance(effect, duration, amplifier)); }
	private static void pulse(PlayerEntity player, Effect effect, int duration, int amplifier, double radius) {
		for (LivingEntity target : player.world.getEntitiesWithinAABB(LivingEntity.class, player.getBoundingBox().grow(radius)))
			if (target != player && target.isAlive()) target.addPotionEffect(new EffectInstance(effect, duration, amplifier));
	}
	private static void cleanse(PlayerEntity player) {
		for (EffectInstance effect : player.getActivePotionEffects()) if (!effect.getPotion().isBeneficial()) player.removePotionEffect(effect.getPotion());
	}

	private static void shootVenom(PlayerEntity player) {
		CaveSpiderProjectileEntity bolt = new CaveSpiderProjectileEntity(player, player.world);
		bolt.shoot(player, player.rotationPitch, player.rotationYaw, 0.0F, 1.7F, 1.0F);
		player.world.addEntity(bolt);
	}

	private static void shootCarrot(PlayerEntity player) {
		CarrotProjectileEntity carrot = new CarrotProjectileEntity(player, player.world);
		carrot.shoot(player, player.rotationPitch, player.rotationYaw, 0.0F, 1.6F, 1.0F);
		player.world.addEntity(carrot);
	}

	private static void shootMagmaVolley(PlayerEntity player) {
		net.minecraft.util.math.Vec3d look = player.getLookVec();
		for (int i = -1; i <= 1; i++) {
			SmallFireballEntity fireball = new SmallFireballEntity(player.world, player.getPosX() + i * 0.35D,
					player.getPosYEye(), player.getPosZ(), look.x, look.y, look.z);
			player.world.addEntity(fireball);
		}
	}

	private static void throwHarmingPotion(PlayerEntity player) {
		PotionEntity potion = new PotionEntity(player.world, player);
		potion.setItem(PotionUtils.addPotionToItemStack(new ItemStack(Items.SPLASH_POTION), Potions.HARMING));
		potion.shoot(player, player.rotationPitch, player.rotationYaw, -20.0F, 0.8F, 1.0F);
		player.world.addEntity(potion);
	}
}
