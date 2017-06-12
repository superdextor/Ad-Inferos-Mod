package com.superdextor.adinferos.blocks;

import java.util.Random;

import com.superdextor.adinferos.init.NetherBlocks;
import com.superdextor.adinferos.init.NetherItems;
import com.superdextor.thinkbigcore.config.TBCConfig;

import net.minecraft.block.BlockCrops;
import net.minecraft.block.state.IBlockState;
import net.minecraft.item.Item;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class BlockHellCrops extends BlockCrops {
	
	@SideOnly(Side.CLIENT)
	private boolean shownTip = false;
	
    @SideOnly(Side.CLIENT)
    public AxisAlignedBB getSelectedBoundingBox(IBlockState state, World worldIn, BlockPos pos)
    {
    	if(!shownTip) {
    		TBCConfig.displayTip("Gluten Free");
    		shownTip = true;
    	}
    	
    	return super.getSelectedBoundingBox(state, worldIn, pos);
    }
	
    public void updateTick(World worldIn, BlockPos pos, IBlockState state, Random rand)
    {
        this.checkAndDropBlock(worldIn, pos, state);

        if (worldIn.provider.doesWaterVaporize() || worldIn.getLightFromNeighbors(pos.up()) >= 9)
        {
            int i = this.getAge(state);

            if (i < this.getMaxAge())
            {
                float f = getGrowthChance(this, worldIn, pos);

                if (rand.nextInt((int)(25.0F / f) + 1) == 0)
                {
                    worldIn.setBlockState(pos, this.withAge(i + 1), 2);
                }
            }
        }
    }
    
    public boolean canBlockStay(World worldIn, BlockPos pos, IBlockState state)
    {
        IBlockState soil = worldIn.getBlockState(pos.down());
        return (worldIn.provider.doesWaterVaporize() || worldIn.getLight(pos) >= 8 || worldIn.canSeeSky(pos)) && soil.getBlock().canSustainPlant(soil, worldIn, pos.down(), net.minecraft.util.EnumFacing.UP, this);
    }
	
    protected boolean canSustainBush(IBlockState state)
    {
        return state.getBlock() == NetherBlocks.NETHER_FARMLAND;
    }
    
    protected int getBonemealAgeIncrease(World worldIn)
    {
        return super.getBonemealAgeIncrease(worldIn) / 2;
    }
    
    protected Item getSeed()
    {
        return NetherItems.INFERNAL_SEEDS;
    }

    protected Item getCrop()
    {
        return NetherItems.INFERNAL_WHEAT;
    }

}
