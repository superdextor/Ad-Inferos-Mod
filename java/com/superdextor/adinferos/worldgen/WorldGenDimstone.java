package com.superdextor.adinferos.worldgen;

import java.util.Random;

import com.superdextor.adinferos.init.NetherBlocks;
import com.superdextor.thinkbigcore.worldgen.IOverridable;

import net.minecraft.block.material.Material;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraft.world.gen.feature.WorldGenerator;

public class WorldGenDimstone extends WorldGenerator implements IOverridable
{
	public boolean doOverride = false;
	
	public void doOverride() {
		this.doOverride = true;
	}

    public boolean generate(World worldIn, Random rand, BlockPos pos)
    {
        if (!this.doOverride && !worldIn.isAirBlock(pos))
        {
            return false;
        }
        else if (!this.doOverride && worldIn.getBlockState(pos.up()).getBlock() != NetherBlocks.DARKSTONE)
        {
            return false;
        }
        else
        {
            worldIn.setBlockState(pos, NetherBlocks.DIMSTONE.getDefaultState(), 2);

            for (int i = 0; i < 1500; ++i)
            {
                BlockPos blockpos1 = pos.add(rand.nextInt(8) - rand.nextInt(8), -rand.nextInt(12), rand.nextInt(8) - rand.nextInt(8));

                if (worldIn.getBlockState(blockpos1).getMaterial() == Material.AIR)
                {
                    int j = 0;
                    EnumFacing[] aenumfacing = EnumFacing.values();
                    int k = aenumfacing.length;

                    for (int l = 0; l < k; ++l)
                    {
                        EnumFacing enumfacing = aenumfacing[l];

                        if (worldIn.getBlockState(blockpos1.offset(enumfacing)).getBlock() == NetherBlocks.DIMSTONE)
                        {
                            ++j;
                        }

                        if (j > 1)
                        {
                            break;
                        }
                    }

                    if (j == 1)
                    {
                        worldIn.setBlockState(blockpos1, NetherBlocks.DIMSTONE.getDefaultState(), 2);
                    }
                }
            }
            
            this.doOverride = false;

            return true;
        }
    }
}