package com.deathbyaether.custommobswords.world.gen;

import com.deathbyaether.custommobswords.list.BlockList;

import net.minecraft.world.biome.Biome;
import net.minecraft.world.gen.GenerationStage.Decoration;
import net.minecraft.world.gen.feature.Feature;
import net.minecraft.world.gen.feature.OreFeatureConfig;
import net.minecraft.world.gen.placement.CountRangeConfig;
import net.minecraft.world.gen.placement.Placement;
import net.minecraftforge.registries.ForgeRegistries;

public class GemstoneGeneration {

	private static void setupOreGeneration() {
		for (Biome biome : ForgeRegistries.BIOMES) {
			biome.addFeature(Decoration.UNDERGROUND_ORES,
					Feature.ORE
							.withConfiguration(new OreFeatureConfig(OreFeatureConfig.FillerBlockType.NATURAL_STONE,
									BlockList.GEMSTONE_ORE.get().getDefaultState(), 10))
							.withPlacement(Placement.COUNT_RANGE.configure(new CountRangeConfig(7, 16, 8, 70))));
		}
	}
	
		
	public static void generate() {
		setupOreGeneration();
		
	}

}