package com.superdextor.adinferos.blocks;

import java.util.List;
import java.util.Random;

import javax.annotation.Nullable;

import com.superdextor.adinferos.init.NetherBlocks;

import net.minecraft.block.Block;
import net.minecraft.block.BlockPane;
import net.minecraft.block.SoundType;
import net.minecraft.block.material.Material;
import net.minecraft.block.properties.IProperty;
import net.minecraft.block.properties.PropertyBool;
import net.minecraft.block.state.BlockStateContainer;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.Entity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.BlockRenderLayer;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class BlockSoulPane extends BlockPane {

    public static final PropertyBool GHOST = PropertyBool.create("ghost");
    
	public BlockSoulPane(Material materialIn) {
		super(materialIn, false);
        this.setDefaultState(this.blockState.getBaseState().withProperty(GHOST, Boolean.valueOf(false)));
        this.setCreativeTab(null);
        this.setSoundType(SoundType.GLASS);
	}
	
    @SideOnly(Side.CLIENT)
    public BlockRenderLayer getBlockLayer()
    {
        return BlockRenderLayer.TRANSLUCENT;
    }

    public void addCollisionBoxToList(IBlockState state, World worldIn, BlockPos pos, AxisAlignedBB p_185477_4_, List<AxisAlignedBB> p_185477_5_, Entity p_185477_6_)
    {
    	if(state.getValue(GHOST).booleanValue()) {
            addCollisionBoxToList(pos, p_185477_4_, p_185477_5_, state.getSelectedBoundingBox(worldIn, pos));
    	}
    	else {
    		super.addCollisionBoxToList(state, worldIn, pos, p_185477_4_, p_185477_5_, p_185477_6_);
    	}
    }
    
    @Nullable
    public AxisAlignedBB getCollisionBoundingBox(IBlockState blockState, World source, BlockPos pos)
    {
    	return blockState.getValue(GHOST).booleanValue() ? NULL_AABB : super.getCollisionBoundingBox(blockState, source, pos);
    }
    
    public boolean isPassable(IBlockAccess worldIn, BlockPos pos) {
    	return worldIn.getBlockState(pos).getValue(GHOST).booleanValue() ? true : super.isPassable(worldIn, pos);
    }
    
    public boolean isFullBlock(IBlockState state) {
    	return state.getValue(GHOST).booleanValue() ? false : super.isFullBlock(state);
    }
    
    public void onBlockAdded(World worldIn, BlockPos pos, IBlockState state)
    {
        if (!worldIn.isRemote)
        {
            if (!state.getValue(GHOST).booleanValue() && worldIn.isBlockPowered(pos))
            {
                worldIn.setBlockState(pos, NetherBlocks.SOUL_GLASS_PANE.getStateFromMeta(1), 2);
            }
        }
    }
    
    public void neighborChanged(IBlockState state, World worldIn, BlockPos pos, Block blockIn, BlockPos value)
    {
        if (!worldIn.isRemote)
        {
        	boolean isOn = state.getValue(GHOST).booleanValue();
        	
            if (isOn && !worldIn.isBlockPowered(pos))
            {
                worldIn.setBlockState(pos, NetherBlocks.SOUL_GLASS_PANE.getStateFromMeta(0), 2);
            }
            else if (!isOn && worldIn.isBlockPowered(pos))
            {
                worldIn.setBlockState(pos, NetherBlocks.SOUL_GLASS_PANE.getStateFromMeta(1), 2);
            }
        }
    }
    
    public Item getItemDropped(IBlockState state, Random rand, int fortune)
    {
        return Item.getItemFromBlock(NetherBlocks.SOUL_GLASS_PANE);
    }

    @SideOnly(Side.CLIENT)
    public ItemStack getItem(World worldIn, BlockPos pos, IBlockState state)
    {
        return new ItemStack(Item.getItemFromBlock(NetherBlocks.SOUL_GLASS_PANE));
    }

    protected ItemStack createStackedBlock(IBlockState state)
    {
        return new ItemStack(NetherBlocks.SOUL_GLASS_PANE);
    }
    
    public IBlockState getStateFromMeta(int meta)
    {
        return this.getDefaultState().withProperty(GHOST, Boolean.valueOf((meta & 1) > 0));
    }

    public int getMetaFromState(IBlockState state)
    {
        return ((Boolean)state.getValue(GHOST)).booleanValue() ? 1 : 0;
    }

    protected BlockStateContainer createBlockState()
    {
        return new BlockStateContainer(this, new IProperty[] {NORTH, EAST, WEST, SOUTH, GHOST});
    }
    
    public boolean canPaneConnectTo(IBlockAccess world, BlockPos pos, EnumFacing dir)
    {
        IBlockState state = world.getBlockState(pos.offset(dir));
    	return state.getBlock() == NetherBlocks.SOUL_GLASS || super.canPaneConnectTo(world, pos, dir);
    }

}
