package com.superdextor.adinferos.worldgen;

import java.util.Random;

import com.superdextor.adinferos.blocks.BlockNetherLeaf;
import com.superdextor.adinferos.blocks.BlockNetherLog;
import com.superdextor.adinferos.blocks.BlockNetherPlanks;
import com.superdextor.adinferos.init.NetherBlocks;
import com.superdextor.thinkbigcore.worldgen.IOverridable;

import net.minecraft.block.Block;
import net.minecraft.block.BlockLeaves;
import net.minecraft.block.BlockLog;
import net.minecraft.block.state.IBlockState;
import net.minecraft.init.Blocks;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraft.world.gen.feature.WorldGenAbstractTree;

public class WorldGenAshTree extends WorldGenAbstractTree implements IOverridable
{
    private static final IBlockState log = NetherBlocks.LOG.getDefaultState().withProperty(BlockNetherLog.VARIANT, BlockNetherPlanks.EnumType.ASH);
    private static final IBlockState leaves = NetherBlocks.LEAVES.getDefaultState().withProperty(BlockNetherLeaf.VARIANT, BlockNetherPlanks.EnumType.ASH).withProperty(BlockLeaves.CHECK_DECAY, Boolean.valueOf(false));
    private int hasLeaves = -1;
    public boolean doOverride = false;

    public WorldGenAshTree(boolean notify, boolean hasLeaves)
    {
        super(notify);
        this.hasLeaves = hasLeaves ? 1 : 0;
    }
    
    public WorldGenAshTree(boolean notify)
    {
        super(notify);
        this.hasLeaves = -1;
    }
    
    public void doOverride() {
    	this.doOverride = true;
    }

    public boolean generate(World worldIn, Random rand, BlockPos position)
    {
        int i = rand.nextInt(3) + 5;
        int hasLeaves = this.hasLeaves;

        if(hasLeaves == -1) {
        	hasLeaves = rand.nextInt(2);
        }

        boolean flag = true;

        if (!this.doOverride || position.getY() >= 1 && position.getY() + i + 1 <= 256)
        {
            for (int j = position.getY(); j <= position.getY() + 1 + i; ++j)
            {
                int k = 1;

                if (j == position.getY())
                {
                    k = 0;
                }

                if (j >= position.getY() + 1 + i - 2)
                {
                    k = 2;
                }

                BlockPos.MutableBlockPos blockpos$mutableblockpos = new BlockPos.MutableBlockPos();

                for (int l = position.getX() - k; l <= position.getX() + k && flag; ++l)
                {
                    for (int i1 = position.getZ() - k; i1 <= position.getZ() + k && flag; ++i1)
                    {
                        if (j >= 0 && j < worldIn.getHeight())
                        {
                            if (!this.isReplaceable(worldIn, blockpos$mutableblockpos.setPos(l, j, i1)))
                            {
                                flag = false;
                            }
                        }
                        else
                        {
                            flag = false;
                        }
                    }
                }
            }

            if (!this.doOverride && !flag)
            {
                return false;
            }
            else
            {
                BlockPos down = position.down();
                IBlockState state = worldIn.getBlockState(down);
                Block block = state.getBlock();

                if (!this.doOverride && block != Blocks.NETHERRACK && block != Blocks.SOUL_SAND && block != NetherBlocks.DARKSTONE && block != NetherBlocks.DARK_SAND && block != NetherBlocks.GOLD_ORE && block != NetherBlocks.DIMENSIONAL_ORE && block != NetherBlocks.NETHERITE_ORE && block != NetherBlocks.WITHER_ORE && block != Blocks.QUARTZ_ORE)
                {
                    return false;
                }

                if (this.doOverride || position.getY() < worldIn.getHeight() - i - 1)
                {
                    state.getBlock().onPlantGrow(state, worldIn, down, position);

                    if(hasLeaves == 1) {
                        for (int i2 = position.getY() - 3 + i; i2 <= position.getY() + i; ++i2)
                        {
                            int k2 = i2 - (position.getY() + i);
                            int l2 = 1 - k2 / 2;

                            for (int i3 = position.getX() - l2; i3 <= position.getX() + l2; ++i3)
                            {
                                int j1 = i3 - position.getX();

                                for (int k1 = position.getZ() - l2; k1 <= position.getZ() + l2; ++k1)
                                {
                                    int l1 = k1 - position.getZ();

                                    if (Math.abs(j1) != l2 || Math.abs(l1) != l2 || rand.nextInt(2) != 0 && k2 != 0)
                                    {
                                        BlockPos blockpos = new BlockPos(i3, i2, k1);
                                        IBlockState state2 = worldIn.getBlockState(blockpos);

                                        if (state2.getBlock().isAir(state2, worldIn, blockpos) || state2.getBlock().isAir(state2, worldIn, blockpos))
                                        {
                                            this.setBlockAndNotifyAdequately(worldIn, blockpos, leaves);
                                        }
                                    }
                                }
                            }
                        }
                    }
                    else {
                    	for(int s = rand.nextInt(4); s > 0; s--) {
                        	IBlockState sideLog;
                        	int h = i - rand.nextInt(3) - 1;
                        	
                        	if(rand.nextBoolean()) {
                        		sideLog = log.withProperty(BlockLog.LOG_AXIS, BlockLog.EnumAxis.Z);
                        		this.setBlockAndNotifyAdequately(worldIn, position.add(0, h, rand.nextBoolean() ? 1 : -1), sideLog);
                        	}
                        	else {
                        		sideLog = log.withProperty(BlockLog.LOG_AXIS, BlockLog.EnumAxis.X);
                        		this.setBlockAndNotifyAdequately(worldIn, position.add(rand.nextBoolean() ? 1 : -1, h, 0), sideLog);
                        	}
                    	}
                    }

                    for (int j2 = 0; j2 < i; ++j2)
                    {
                        BlockPos upN = position.up(j2);
                        IBlockState state2 = worldIn.getBlockState(upN);

                        if (state2.getBlock().isAir(state2, worldIn, upN) || state2.getBlock().isLeaves(state2, worldIn, upN))
                        {
                            this.setBlockAndNotifyAdequately(worldIn, position.up(j2), log);
                        }
                    }

                    this.doOverride = false;
                    
                    return true;
                }
                else
                {
                    return false;
                }
            }
        }
        else
        {
            return false;
        }
    }
}