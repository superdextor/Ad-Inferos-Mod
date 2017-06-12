package com.superdextor.adinferos.worldgen;

import java.util.Random;

import com.superdextor.adinferos.config.NetherConfig;
import com.superdextor.adinferos.init.NetherBlocks;
import com.superdextor.adinferos.init.NetherFluids;

import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraft.world.gen.feature.WorldGenerator;

public class WorldGenAbyssAcid extends WorldGenerator
{
    private final boolean insideRock;

    public WorldGenAbyssAcid(boolean insideRock)
    {
        this.insideRock = insideRock;
    }

    public boolean generate(World worldIn, Random rand, BlockPos position)
    {
        if (worldIn.getBlockState(position.up()).getBlock() != NetherBlocks.DARKSTONE)
        {
            return false;
        }
        else if (NetherConfig.safeWorldGen ? worldIn.isAreaLoaded(position, 1) : true && worldIn.getBlockState(position).getMaterial() != Material.AIR && worldIn.getBlockState(position).getBlock() != NetherBlocks.DARKSTONE)
        {
            return false;
        }
        else
        {
            int i = 0;

            if (worldIn.getBlockState(position.west()).getBlock() == NetherBlocks.DARKSTONE)
            {
                ++i;
            }

            if (worldIn.getBlockState(position.east()).getBlock() == NetherBlocks.DARKSTONE)
            {
                ++i;
            }

            if (worldIn.getBlockState(position.north()).getBlock() == NetherBlocks.DARKSTONE)
            {
                ++i;
            }

            if (worldIn.getBlockState(position.south()).getBlock() == NetherBlocks.DARKSTONE)
            {
                ++i;
            }

            if (worldIn.getBlockState(position.down()).getBlock() == NetherBlocks.DARKSTONE)
            {
                ++i;
            }

            int j = 0;

            if (worldIn.isAirBlock(position.west()))
            {
                ++j;
            }

            if (worldIn.isAirBlock(position.east()))
            {
                ++j;
            }

            if (worldIn.isAirBlock(position.north()))
            {
                ++j;
            }

            if (worldIn.isAirBlock(position.south()))
            {
                ++j;
            }

            if (worldIn.isAirBlock(position.down()))
            {
                ++j;
            }

            if (!this.insideRock && i == 4 && j == 1 || i == 5)
            {
                IBlockState iblockstate = NetherFluids.blockAcid.getDefaultState();
                worldIn.setBlockState(position, iblockstate, 2);
                worldIn.immediateBlockTick(position, iblockstate, rand);
            }

            return true;
        }
    }
}