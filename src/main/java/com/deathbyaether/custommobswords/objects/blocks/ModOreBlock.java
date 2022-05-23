package com.deathbyaether.custommobswords.objects.blocks;

import java.util.Random;

import net.minecraft.block.BlockState;
import net.minecraft.block.OreBlock;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.world.World;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

import net.minecraft.block.AbstractBlock.Properties;

public class ModOreBlock extends OreBlock {
	
	public ModOreBlock(Properties properties) {
		super(properties);
	}
	
	protected int xpOnDrop(Random rand) {
		return MathHelper.nextInt(RANDOM, 2, 10);
	}
	
	@OnlyIn(Dist.CLIENT)
	   public void animateTick(BlockState stateIn, World worldIn, BlockPos pos, Random rand) {
	      double d0 = (double)pos.getX() + 0.5D;
	      double d1 = (double)pos.getY() + 1.5D;
	      double d2 = (double)pos.getZ() + 0.5D;
	      
	   }
	
}