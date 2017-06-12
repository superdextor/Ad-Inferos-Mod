package com.superdextor.adinferos.blocks;

import java.util.Random;

import com.superdextor.adinferos.init.NetherBlocks;
import com.superdextor.adinferos.init.NetherItems;
import com.superdextor.thinkbigcore.config.TBCConfig;

import net.minecraft.block.BlockDoor;
import net.minecraft.block.SoundType;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class BlockHellDoor extends BlockDoor {

	@SideOnly(Side.CLIENT)
	private boolean shownTip = false;
	
	public BlockHellDoor(Material materialIn) {
		super(materialIn);
		this.setSoundType(SoundType.WOOD);
	}
	
    @SideOnly(Side.CLIENT)
    public AxisAlignedBB getSelectedBoundingBox(IBlockState state, World worldIn, BlockPos pos)
    {
    	if(!shownTip) {
    		TBCConfig.displayTip("Fire Resistant Door");
    		shownTip = true;
    	}
    	
    	return super.getSelectedBoundingBox(state, worldIn, pos);
    }
	
    @SideOnly(Side.CLIENT)
    public ItemStack getItem(World worldIn, BlockPos pos, IBlockState state)
    {
        return new ItemStack(this.getItem());
    }
    
    public Item getItemDropped(IBlockState state, Random rand, int fortune)
    {
        return state.getValue(HALF) == BlockDoor.EnumDoorHalf.UPPER ? null : this.getItem();
    }
	
    private Item getItem()
    {
        return this.equals(NetherBlocks.INFERNO_DOOR) ? NetherItems.INFERNO_DOOR : this.equals(NetherBlocks.MAGMA_DOOR) ? NetherItems.MAGMA_DOOR : this.equals(NetherBlocks.PHANTOM_DOOR) ? NetherItems.PHANTOM_DOOR : NetherItems.ASH_DOOR;
    }

}
