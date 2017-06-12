package com.superdextor.adinferos.blocks;

import java.util.Random;

import com.superdextor.adinferos.init.NetherBlocks;
import com.superdextor.adinferos.worldgen.WorldGenBigPurpleMushroom;
import com.superdextor.thinkbigcore.config.TBCConfig;

import net.minecraft.block.Block;
import net.minecraft.block.BlockMushroom;
import net.minecraft.block.SoundType;
import net.minecraft.block.state.IBlockState;
import net.minecraft.init.Blocks;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class BlockPurpleMushroom extends BlockMushroom
{
	
	@SideOnly(Side.CLIENT)
	private boolean shownTip = false;
	
    public BlockPurpleMushroom()
    {
    	this.setSoundType(SoundType.PLANT);
    }
    
    @SideOnly(Side.CLIENT)
    public AxisAlignedBB getSelectedBoundingBox(IBlockState state, World worldIn, BlockPos pos)
    {
    	if(!shownTip) {
    		TBCConfig.displayTip("Can be cooked and Eaten");
    		shownTip = true;
    	}
    	
    	return super.getSelectedBoundingBox(state, worldIn, pos);
    }

    public boolean canBlockStay(World worldIn, BlockPos pos, IBlockState state)
    {
        if (pos.getY() >= 0 && pos.getY() < 256)
        {
            Block block = worldIn.getBlockState(pos.down()).getBlock();
            return block == Blocks.NETHERRACK || block == Blocks.SOUL_SAND || block == Blocks.QUARTZ_ORE || block == NetherBlocks.NETHERITE_ORE || block == NetherBlocks.WITHER_ORE || block == NetherBlocks.GOLD_ORE || 
            		block == NetherBlocks.DARKSTONE || block == NetherBlocks.DARK_SAND;
        }
        else
        {
            return false;
        }
    }

    public boolean generateBigMushroom(World worldIn, BlockPos pos, IBlockState state, Random rand)
    {
        worldIn.setBlockToAir(pos);
        WorldGenBigPurpleMushroom worldgenerator = new WorldGenBigPurpleMushroom(1);

        if (worldgenerator.generate(worldIn, rand, pos))
        {
            return true;
        }
        else
        {
            worldIn.setBlockState(pos, state, 3);
            return false;
        }
    }
    
    public boolean canUseBonemeal(World worldIn, Random rand, BlockPos pos, IBlockState state)
    {
        return (double)rand.nextFloat() < 0.2D;
    }
}