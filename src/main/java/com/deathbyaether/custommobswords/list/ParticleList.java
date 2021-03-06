package com.deathbyaether.custommobswords.list;

import com.deathbyaether.custommobswords.Main;

import net.minecraft.particles.BasicParticleType;
import net.minecraft.particles.ParticleType;
import net.minecraftforge.fml.RegistryObject;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;

public class ParticleList {
	
	
	@SuppressWarnings("deprecation")
	public static final DeferredRegister<ParticleType<?>> PARTICLES = new DeferredRegister<>(ForgeRegistries.PARTICLE_TYPES, Main.MOD_ID);
	
	public static final RegistryObject<BasicParticleType> DRAGONFIRE_PARTICLE = PARTICLES.register("dragonfire_particle", () -> new BasicParticleType(true));
	
	
}