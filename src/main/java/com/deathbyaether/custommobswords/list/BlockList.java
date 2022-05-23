package com.deathbyaether.custommobswords.list;

import com.deathbyaether.custommobswords.Main;
import com.deathbyaether.custommobswords.objects.blocks.ModOreBlock;

import net.minecraft.block.Block;
import net.minecraft.block.Blocks;
import net.minecraft.block.OreBlock;
import net.minecraft.block.SoundType;
import net.minecraft.block.material.Material;
import net.minecraftforge.fml.RegistryObject;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;

import net.minecraft.block.AbstractBlock;

public class BlockList
{

	public static final DeferredRegister<Block> BLOCKS =  DeferredRegister.create(ForgeRegistries.BLOCKS, Main.MOD_ID);
	
	public static final RegistryObject<Block> GEMSTONE_ORE = BLOCKS.register("gemstone_ore", () -> new ModOreBlock(AbstractBlock.Properties.from(Blocks.DIAMOND_ORE)));
	
	public static final DeferredRegister<Block> NO_ITEM_BLOCK = DeferredRegister.create(ForgeRegistries.BLOCKS, Main.MOD_ID);
	
	
}