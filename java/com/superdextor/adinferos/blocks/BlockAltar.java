package com.superdextor.adinferos.blocks;

import javax.annotation.Nullable;

import com.superdextor.adinferos.inventory.AdInferosGuis;
import com.superdextor.adinferos.inventory.TileEntityAltar;
import com.superdextor.thinkbigcore.ThinkBigCore;

import net.minecraft.block.BlockContainer;
import net.minecraft.block.SoundType;
import net.minecraft.block.material.Material;
import net.minecraft.block.properties.IProperty;
import net.minecraft.block.properties.PropertyBool;
import net.minecraft.block.state.BlockStateContainer;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.InventoryHelper;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumBlockRenderType;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.Explosion;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;

public class BlockAltar extends BlockContainer
{
    public static final PropertyBool OBSIDIAN = PropertyBool.create("obsidian");
    protected static final AxisAlignedBB table_AABB = new AxisAlignedBB(0.0D, 0.0D, 0.0D, 1.0D, 0.75D, 1.0D);

    public BlockAltar()
    {
        super(Material.ROCK);
        this.setDefaultState(this.blockState.getBaseState().withProperty(OBSIDIAN, Boolean.valueOf(false)));
        this.setLightOpacity(0);
        this.setSoundType(SoundType.METAL);
    }
    
    public AxisAlignedBB getBoundingBox(IBlockState state, IBlockAccess source, BlockPos pos)
    {
        return table_AABB;
    }

    public boolean isFullCube(IBlockState state)
    {
        return false;
    }

    public boolean isOpaqueCube(IBlockState state)
    {
        return false;
    }

    /**
     * The type of render function that is called for this block
     */
    public EnumBlockRenderType getRenderType(IBlockState state)
    {
        return EnumBlockRenderType.MODEL;
    }

    /**
     * Returns a new instance of a block's tile entity class. Called on placing the block.
     */
    @Nullable
    public TileEntity createNewTileEntity(World worldIn, int meta)
    {
    	TileEntityAltar altar = new TileEntityAltar();
    	if(meta == 1) {
    		altar.setObsidian();
    	}
        return altar;
    }

    public boolean onBlockActivated(World worldIn, BlockPos pos, IBlockState state, EntityPlayer playerIn,
    		EnumHand hand, EnumFacing side, float hitX, float hitY, float hitZ) 
    {
        if (worldIn.isRemote)
        {
            return true;
        }
        else
        {
            playerIn.openGui(ThinkBigCore.modInstance, AdInferosGuis.altar.ID, worldIn, pos.getX(), pos.getY(), pos.getZ());

            return true;
        }
    }

    public void onBlockPlacedBy(World worldIn, BlockPos pos, IBlockState state, EntityLivingBase placer, ItemStack stack)
    {
        if (stack.hasDisplayName())
        {
            TileEntity tileentity = worldIn.getTileEntity(pos);

            if (tileentity instanceof TileEntityAltar)
            {
                ((TileEntityAltar)tileentity).setCustomInventoryName(stack.getDisplayName());
            }
        }
    }
    
    public void breakBlock(World worldIn, BlockPos pos, IBlockState state)
    {
        TileEntity tileentity = worldIn.getTileEntity(pos);

        if (tileentity instanceof TileEntityAltar)
        {
            InventoryHelper.dropInventoryItems(worldIn, pos, (TileEntityAltar)tileentity);
            ((TileEntityAltar) tileentity).onBreak();
            worldIn.updateComparatorOutputLevel(pos, this);
        }
        
        super.breakBlock(worldIn, pos, state);
    }
    
    public IBlockState getStateFromMeta(int meta)
    {
        return this.getDefaultState().withProperty(OBSIDIAN, Boolean.valueOf((meta & 1) > 0));
    }

    public int getMetaFromState(IBlockState state)
    {
        return ((Boolean)state.getValue(OBSIDIAN)).booleanValue() ? 1 : 0;
    }

    protected BlockStateContainer createBlockState()
    {
        return new BlockStateContainer(this, new IProperty[] {OBSIDIAN});
    }
    
    public float getBlockHardness(IBlockState blockState, World worldIn, BlockPos pos) {
    	
    	if(blockState.getValue(OBSIDIAN)) {
    		return -1.0F;
    	}
    	
    	return super.getBlockHardness(blockState, worldIn, pos);
    }
    
    public float getExplosionResistance(World world, BlockPos pos, Entity exploder, Explosion explosion) {
    	
    	if(world.getBlockState(pos).getValue(OBSIDIAN)) {
    		return 10000.0F;
    	}
    	
    	return super.getExplosionResistance(world, pos, exploder, explosion);
    }
}