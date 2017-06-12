package com.superdextor.adinferos.worldgen;

import java.util.Random;

import com.superdextor.adinferos.config.NetherConfig;
import com.superdextor.adinferos.init.NetherFluids;
import com.superdextor.thinkbigcore.worldgen.IOverridable;

import net.minecraft.block.material.Material;
import net.minecraft.init.Blocks;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraft.world.gen.feature.WorldGenerator;

public class WorldGenAcidLakes extends WorldGenerator implements IOverridable
{
	public boolean doOverride = false;
	
    public WorldGenAcidLakes() {}
    
    public void doOverride() {
    	this.doOverride = true;
    }

    public boolean generate(World worldIn, Random random, BlockPos blockpos)
    {
        for (blockpos = blockpos.add(-8, 0, -8); blockpos.getY() > 5 && (NetherConfig.safeWorldGen ? worldIn.isAreaLoaded(blockpos, 1) : true) && worldIn.isAirBlock(blockpos); blockpos = blockpos.down())
        {
            ;
        }

        if (!this.doOverride && blockpos.getY() <= 4)
        {
            return false;
        }
        else
        {
        	blockpos = blockpos.down(4);
            boolean[] aboolean = new boolean[2048];
            int i = random.nextInt(4) + 4;
            int j;

            for (j = 0; j < i; ++j)
            {
                double d0 = random.nextDouble() * 6.0D + 3.0D;
                double d1 = random.nextDouble() * 4.0D + 2.0D;
                double d2 = random.nextDouble() * 6.0D + 3.0D;
                double d3 = random.nextDouble() * (16.0D - d0 - 2.0D) + 1.0D + d0 / 2.0D;
                double d4 = random.nextDouble() * (8.0D - d1 - 4.0D) + 2.0D + d1 / 2.0D;
                double d5 = random.nextDouble() * (16.0D - d2 - 2.0D) + 1.0D + d2 / 2.0D;

                for (int l = 1; l < 15; ++l)
                {
                    for (int i1 = 1; i1 < 15; ++i1)
                    {
                        for (int j1 = 1; j1 < 7; ++j1)
                        {
                            double d6 = ((double)l - d3) / (d0 / 2.0D);
                            double d7 = ((double)j1 - d4) / (d1 / 2.0D);
                            double d8 = ((double)i1 - d5) / (d2 / 2.0D);
                            double d9 = d6 * d6 + d7 * d7 + d8 * d8;

                            if (d9 < 1.0D)
                            {
                                aboolean[(l * 16 + i1) * 8 + j1] = true;
                            }
                        }
                    }
                }
            }

            int k;
            int k1;
            boolean flag;

            for (j = 0; j < 16; ++j)
            {
                for (k1 = 0; k1 < 16; ++k1)
                {
                    for (k = 0; k < 8; ++k)
                    {
                        flag = !aboolean[(j * 16 + k1) * 8 + k] && (j < 15 && aboolean[((j + 1) * 16 + k1) * 8 + k] || j > 0 && aboolean[((j - 1) * 16 + k1) * 8 + k] || k1 < 15 && aboolean[(j * 16 + k1 + 1) * 8 + k] || k1 > 0 && aboolean[(j * 16 + (k1 - 1)) * 8 + k] || k < 7 && aboolean[(j * 16 + k1) * 8 + k + 1] || k > 0 && aboolean[(j * 16 + k1) * 8 + (k - 1)]);

                        if (flag)
                        {
                        	
                        	if(NetherConfig.safeWorldGen && !worldIn.isAreaLoaded(blockpos.add(j, k, k1), 1)) {
                        		return false;
                        	}
                        	
                            Material material = worldIn.getBlockState(blockpos.add(j, k, k1)).getMaterial();

                            if (!this.doOverride && k >= 4 && material.isLiquid())
                            {
                                return false;
                            }

                            if (!this.doOverride && k < 4 && !material.isSolid() && worldIn.getBlockState(blockpos.add(j, k, k1)).getBlock() != NetherFluids.blockAcid)
                            {
                                return false;
                            }
                        }
                    }
                }
            }

            for (j = 0; j < 16; ++j)
            {
                for (k1 = 0; k1 < 16; ++k1)
                {
                    for (k = 0; k < 8; ++k)
                    {
                        if (aboolean[(j * 16 + k1) * 8 + k])
                        {
                            worldIn.setBlockState(blockpos.add(j, k, k1), k >= 4 ? Blocks.AIR.getDefaultState() : NetherFluids.blockAcid.getDefaultState(), 2);
                        }
                    }
                }
            }

            this.doOverride = false;
            
            return true;
        }
    }
}