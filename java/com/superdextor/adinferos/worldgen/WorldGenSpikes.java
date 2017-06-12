package com.superdextor.adinferos.worldgen;

import java.util.Random;

import com.superdextor.adinferos.config.NetherConfig;
import com.superdextor.adinferos.init.NetherBlocks;

import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraft.world.gen.feature.WorldGenerator;

public class WorldGenSpikes extends WorldGenerator
{

    public boolean generate(World worldIn, Random rand, BlockPos position)
    {
    	
    	if(NetherConfig.safeWorldGen && !worldIn.isAreaLoaded(position.add(-8, -4, -8), position.add(8, 4, 8))) {
    		return false;
    	}
    	
        for (int i = 0; i < 64; ++i)
        {
            BlockPos blockpos = position.add(rand.nextInt(8) - rand.nextInt(8), rand.nextInt(4) - rand.nextInt(4), rand.nextInt(8) - rand.nextInt(8));

            if (worldIn.isAirBlock(blockpos) && blockpos.getY() < 255 && worldIn.getBlockState(blockpos.down()).isFullBlock())
            {
                worldIn.setBlockState(blockpos, NetherBlocks.SPIKES.getDefaultState(), 2);
            }
        }

        return true;
    }
}