   package com.superdextor.adinferos.worldgen;

import java.util.Random;

import com.superdextor.adinferos.init.NetherBlocks;
import com.superdextor.thinkbigcore.worldgen.IOverridable;

import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.init.Blocks;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraft.world.gen.feature.WorldGenerator;

public class WorldGenObsidianBeach extends WorldGenerator implements IOverridable
{
	public boolean doOverride = false;
	
    public WorldGenObsidianBeach() {}
    
    public void doOverride() {
    	this.doOverride = true;
    }

    public boolean generate(World worldIn, Random random, BlockPos pos)
    {
        if (!this.doOverride && worldIn.getBlockState(pos).getMaterial() != Material.LAVA)
        {
            return false;
        }
        else
        {
            int i = random.nextInt(30) + 2;
            byte b0 = 2;

            for (int j = pos.getX() - i; j <= pos.getX() + i; ++j)
            {
                for (int k = pos.getZ() - i; k <= pos.getZ() + i; ++k)
                {
                    int l = j - pos.getX();
                    int i1 = k - pos.getZ();

                    if (l * l + i1 * i1 <= i * i)
                    {
                        for (int j1 = pos.getY() - b0; j1 <= pos.getY() + b0; ++j1)
                        {
                            BlockPos blockpos1 = new BlockPos(j, j1, k);
                            
                            if(worldIn.isBlockFullCube(blockpos1)) {
                                worldIn.setBlockState(blockpos1, random.nextInt(4) == 0 ? Blocks.OBSIDIAN.getDefaultState() : NetherBlocks.HARDENED_LAVA.getDefaultState(), 2);
                            }
                        }
                    }
                }
            }

            this.doOverride = false;
            return true;
        }
    }
}