package com.superdextor.adinferos.blocks;

import javax.annotation.Nullable;

import com.superdextor.thinkbigcore.config.TBCConfig;

import net.minecraft.block.BlockBreakable;
import net.minecraft.block.SoundType;
import net.minecraft.block.material.MapColor;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.Entity;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class BlockDarkSand extends BlockBreakable
{

	@SideOnly(Side.CLIENT)
	private boolean shownTip = false;
	
    public BlockDarkSand()
    {
        super(Material.SAND, true);
        this.setSoundType(SoundType.SAND);
    }
    
    @SideOnly(Side.CLIENT)
    public AxisAlignedBB getSelectedBoundingBox(IBlockState state, World worldIn, BlockPos pos)
    {
    	if(!shownTip) {
    		TBCConfig.displayTip("Can, and will Eat you");
    		shownTip = true;
    	}
    	
    	return super.getSelectedBoundingBox(state, worldIn, pos);
    }
    
    @Nullable
    public AxisAlignedBB getCollisionBoundingBox(IBlockState blockState, IBlockAccess worldIn, BlockPos pos) {
        return NULL_AABB;
    }

    /**
     * Called When an Entity Collided with the Block
     */
    public void onEntityCollidedWithBlock(World worldIn, BlockPos pos, IBlockState state, Entity entityIn)
    {
        entityIn.motionX *= 0.2D;
        entityIn.motionZ *= 0.2D;
        entityIn.motionY = -0.01D;
    }
    
    public MapColor getMapColor(IBlockState state)
    {
        return MapColor.GRAY;
    }
    
    public boolean isPassable(IBlockAccess worldIn, BlockPos pos)
    {
        return true;
    }
    
    public boolean isFullBlock(IBlockState state) {
    	return false;
    }
    
    public boolean isOpaqueCube(IBlockState state) {
    	return false;
    }
}