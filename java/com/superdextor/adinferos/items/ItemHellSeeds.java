package com.superdextor.adinferos.items;

import com.superdextor.adinferos.AdInferosTab;
import com.superdextor.adinferos.init.NetherBlocks;

import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemSeeds;
import net.minecraft.item.ItemStack;
import net.minecraft.util.EnumActionResult;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import net.minecraftforge.common.EnumPlantType;

public class ItemHellSeeds extends ItemSeeds {

	private final int type; //USE when more than one type of Crop is added
	
	public ItemHellSeeds(int type) {
		super(null, NetherBlocks.NETHER_FARMLAND);
        this.setCreativeTab(AdInferosTab.AI_FOOD);
		this.type = type;
	}
	
    public EnumActionResult onItemUse(EntityPlayer playerIn, World worldIn, BlockPos pos, EnumHand hand, EnumFacing facing, float hitX, float hitY, float hitZ)
    {
    	ItemStack stack = playerIn.getHeldItem(hand);
        IBlockState state = worldIn.getBlockState(pos);
        if (facing == EnumFacing.UP && playerIn.canPlayerEdit(pos.offset(facing), facing, stack) && state.getBlock().canSustainPlant(state, worldIn, pos, EnumFacing.UP, this) && worldIn.isAirBlock(pos.up()))
        {
            worldIn.setBlockState(pos.up(), this.getPlant(worldIn, pos));
            stack.func_190918_g(1);
            return EnumActionResult.SUCCESS;
        }
        else
        {
            return EnumActionResult.FAIL;
        }
    }

    @Override
    public EnumPlantType getPlantType(net.minecraft.world.IBlockAccess world, BlockPos pos)
    {
        return EnumPlantType.Nether;
    }
    
    @Override
    public IBlockState getPlant(IBlockAccess world, BlockPos pos)
    {
        return NetherBlocks.INFERNAL_WHEAT.getDefaultState();
    }
}
