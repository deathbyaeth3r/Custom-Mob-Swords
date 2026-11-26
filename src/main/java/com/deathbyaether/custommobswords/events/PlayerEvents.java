package com.deathbyaether.custommobswords.events;

import com.deathbyaether.custommobswords.Main;
import com.deathbyaether.custommobswords.objects.items.ChickenSwordItem;
import com.deathbyaether.custommobswords.objects.items.CowSwordItem;
import com.deathbyaether.custommobswords.objects.items.SpiderSwordItem;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.vector.Vector3d;
import net.minecraftforge.event.TickEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod.EventBusSubscriber;
import net.minecraftforge.fml.common.Mod.EventBusSubscriber.Bus;

@EventBusSubscriber(modid = Main.MOD_ID, bus = Bus.FORGE)
public class PlayerEvents {

	@SubscribeEvent
	public static void onPlayerTick(TickEvent.PlayerTickEvent event) {
		PlayerEntity player = event.player;
		if (event.phase != TickEvent.Phase.END || player.level.isClientSide || player.isSpectator()) return;

		if (player.getMainHandItem().getItem() instanceof ChickenSwordItem) {
			player.addEffect(new net.minecraft.potion.EffectInstance(net.minecraft.potion.Effects.SLOW_FALLING, 40, 0, true, false));
		}

		if (player.getMainHandItem().getItem() instanceof CowSwordItem && player.isOnGround() && player.fallDistance > 1.0F) {
			float impact = Math.min(player.fallDistance, 8.0F);
			for (LivingEntity target : player.level.getEntitiesOfClass(LivingEntity.class, player.getBoundingBox().inflate(3.0D))) {
				if (target != player) {
					target.hurt(net.minecraft.util.DamageSource.FALL, impact * 1.5F);
					Vector3d away = target.position().subtract(player.position()).normalize();
					target.push(away.x * 0.8D, 0.35D, away.z * 0.8D);
				}
			}
			player.fallDistance = 0.0F;
		}

		if (player.getMainHandItem().getItem() instanceof SpiderSwordItem && player.isCrouching()) {
			BlockPos wall = player.blockPosition().relative(player.getDirection());
			if (!player.level.getBlockState(wall).isAir() && !player.isOnGround()) {
				Vector3d motion = player.getDeltaMovement();
				player.setDeltaMovement(motion.x, Math.max(motion.y, 0.12D), motion.z);
				player.fallDistance = 0.0F;
			}
		}
	}
}
