package com.superdextor.adinferos.blocks;

import java.util.Random;

import javax.annotation.Nullable;

import com.superdextor.adinferos.init.NetherBlocks;
import com.superdextor.adinferos.inventory.AdInferosGuis;
import com.superdextor.adinferos.inventory.TileEntityNetherFurnace;
import com.superdextor.thinkbigcore.ThinkBigCore;
import com.superdextor.thinkbigcore.config.TBCConfig;

import net.minecraft.block.BlockContainer;
import net.minecraft.block.BlockFurnace;
import net.minecraft.block.SoundType;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.InventoryHelper;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.tileentity.TileEntityFurnace;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class BlockNetherFurnace extends BlockFurnace
{
    private static boolean keepInventory;
    
	@SideOnly(Side.CLIENT)
	private boolean shownTip = false;
	
    public BlockNetherFurnace(boolean isBurning)
    {
        super(isBurning);
        this.setDefaultState(this.blockState.getBaseState().withProperty(FACING, EnumFacing.NORTH));
        this.setSoundType(SoundType.STONE);
    }
    
    @SideOnly(Side.CLIENT)
    public AxisAlignedBB getSelectedBoundingBox(IBlockState state, World worldIn, BlockPos pos)
    {
    	if(!shownTip) {
    		TBCConfig.displayTip("Put Fire on top for free Fuel");
    		shownTip = true;
    	}
    	
    	return super.getSelectedBoundingBox(state, worldIn, pos);
    }
    
    public boolean isFireSource(World world, BlockPos pos, EnumFacing side) {
    	return true;
    }

    public Item getItemDropped(IBlockState state, Random rand, int fortune)
    {
        return Item.getItemFromBlock(NetherBlocks.NETHERRACK_FURNACE);
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
        	TileEntityNetherFurnace tileentityfurnace = (TileEntityNetherFurnace)worldIn.getTileEntity(pos);

            if (tileentityfurnace != null)
            {
                playerIn.openGui(ThinkBigCore.modInstance, AdInferosGuis.netherrackFurnace.ID, worldIn, pos.getX(), pos.getY(), pos.getZ());
            }

            return true;
        }
    }

    @Nullable
    public TileEntity createNewTileEntity(World worldIn, int meta)
    {
        return new TileEntityNetherFurnace();
    }

    public ItemStack getItem(World worldIn, BlockPos pos, IBlockState state)
    {
        return new ItemStack(NetherBlocks.NETHERRACK_FURNACE);
    }
    
    public static void setState(boolean active, World worldIn, BlockPos pos)
    {
        IBlockState iblockstate = worldIn.getBlockState(pos);
        TileEntity tileentity = worldIn.getTileEntity(pos);
        keepInventory = true;

        if (active)
        {
            worldIn.setBlockState(pos, NetherBlocks.LIT_NETHERRACK_FURNACE.getDefaultState().withProperty(FACING, iblockstate.getValue(FACING)), 3);
            worldIn.setBlockState(pos, NetherBlocks.LIT_NETHERRACK_FURNACE.getDefaultState().withProperty(FACING, iblockstate.getValue(FACING)), 3);
        }
        else
        {
            worldIn.setBlockState(pos, NetherBlocks.NETHERRACK_FURNACE.getDefaultState().withProperty(FACING, iblockstate.getValue(FACING)), 3);
            worldIn.setBlockState(pos, NetherBlocks.NETHERRACK_FURNACE.getDefaultState().withProperty(FACING, iblockstate.getValue(FACING)), 3);
        }

        keepInventory = false;

        if (tileentity != null)
        {
            tileentity.validate();
            worldIn.setTileEntity(pos, tileentity);
        }
    }
    
    public void breakBlock(World worldIn, BlockPos pos, IBlockState state)
    {
        if (!keepInventory)
        {
            TileEntity tileentity = worldIn.getTileEntity(pos);

            if (tileentity instanceof TileEntityFurnace)
            {
                InventoryHelper.dropInventoryItems(worldIn, pos, (TileEntityFurnace)tileentity);
                worldIn.updateComparatorOutputLevel(pos, this);
            }
        }

        worldIn.removeTileEntity(pos);
        
        if (hasTileEntity(state) && !(this instanceof BlockContainer))
        {
            worldIn.removeTileEntity(pos);
        }
    }
    
    
}