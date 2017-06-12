package com.superdextor.adinferos.worldgen;

import java.util.Random;

import com.superdextor.adinferos.config.NetherConfig;
import com.superdextor.adinferos.init.NetherBlocks;
import com.superdextor.thinkbigcore.worldgen.IOverridable;

import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraft.world.gen.feature.WorldGenerator;

public class WorldGenDarkFire extends WorldGenerator implements IOverridable
{
	public boolean doOverride = false;
	
	public void doOverride() {
		this.doOverride = true;
	}
	

    public boolean generate(World worldIn, Random rand, BlockPos pos)
    {
    	
    	if(NetherConfig.safeWorldGen && !worldIn.isAreaLoaded(pos.add(-8, -4, -8), pos.add(8, 4, 8))) {
    		return false;
    	}
    	
        for (int i = 0; i < 64; ++i)
        {
            BlockPos blockpos1 = pos.add(rand.nextInt(8) - rand.nextInt(8), rand.nextInt(4) - rand.nextInt(4), rand.nextInt(8) - rand.nextInt(8));

            if (worldIn.isAirBlock(blockpos1) && (worldIn.getBlockState(blockpos1.down()).getBlock() == NetherBlocks.DARKSTONE || (this.doOverride && worldIn.getBlockState(blockpos1.down()).isOpaqueCube())))
            {
                worldIn.setBlockState(blockpos1, NetherBlocks.DARK_FIRE.getDefaultState(), 2);
            }
        }

        this.doOverride = false;
        
        return true;
    }
}