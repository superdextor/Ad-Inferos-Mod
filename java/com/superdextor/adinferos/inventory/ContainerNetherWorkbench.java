package com.superdextor.adinferos.inventory;

import com.superdextor.adinferos.init.NetherBlocks;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.inventory.ContainerWorkbench;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

public class ContainerNetherWorkbench extends ContainerWorkbench
{
	private final BlockPos pos;
	private final World worldObj;
	
    public ContainerNetherWorkbench(InventoryPlayer playerInventory, World worldIn, BlockPos posIn)
    {
    	super(playerInventory, worldIn, posIn);
    	this.pos = posIn;
    	this.worldObj = worldIn;
    }

    public boolean canInteractWith(EntityPlayer playerIn)
    {
        return this.worldObj.getBlockState(this.pos).getBlock() != NetherBlocks.CRAFTING_TABLE ? false : playerIn.getDistanceSq((double)this.pos.getX() + 0.5D, (double)this.pos.getY() + 0.5D, (double)this.pos.getZ() + 0.5D) <= 64.0D;
    }
}