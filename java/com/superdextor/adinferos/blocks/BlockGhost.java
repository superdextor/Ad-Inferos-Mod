package com.superdextor.adinferos.blocks;

import java.util.Random;

import net.minecraft.block.BlockBreakable;
import net.minecraft.block.SoundType;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.BlockRenderLayer;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;


public class BlockGhost extends BlockBreakable {
	
	private final boolean IsHollow;
    protected static final AxisAlignedBB GHOST_AABB = new AxisAlignedBB(0.0D, 0.0D, 0.0D, 0.0D, 0.0D, 0.0D);
	
	public BlockGhost(Material materialIn, boolean IsHollow) {
		super(materialIn, false);
		this.setLightOpacity(0);
		this.setSoundType(SoundType.SAND);
        this.IsHollow = IsHollow;
	}
	
    @SideOnly(Side.CLIENT)
    public AxisAlignedBB getSelectedBoundingBox(IBlockState state, World worldIn, BlockPos pos)
    {
        return GHOST_AABB.offset(pos);
    }
    
    public boolean isFullCube(IBlockState state)
    {
        return false;
    }
    
    public int quantityDropped(Random random)
    {
        return 0;
    }
    
    @SideOnly(Side.CLIENT)
    public BlockRenderLayer getBlockLayer()
    {
        return BlockRenderLayer.CUTOUT;
    }
    
    public ItemStack getItem(World worldIn, BlockPos pos, IBlockState state) {
    	return ItemStack.field_190927_a;
    }
    
    public boolean isReplaceable(IBlockAccess worldIn, BlockPos pos) {
    	
        return IsHollow;
    }

}
