package com.superdextor.adinferos.items;

import com.superdextor.adinferos.init.NetherBlocks;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.SoundEvents;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.EnumActionResult;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

public class ItemWitherDust extends Item
{
    public EnumActionResult onItemUse(EntityPlayer playerIn, World worldIn, BlockPos pos,
    		EnumHand hand, EnumFacing facing, float hitX, float hitY, float hitZ) 
    {
    	ItemStack stack = playerIn.getHeldItem(hand);
        pos = pos.offset(facing);

        if (!playerIn.canPlayerEdit(pos, facing, stack) || !worldIn.isAirBlock(pos) || !NetherBlocks.DARK_FIRE.canPlaceBlockAt(worldIn, pos))
        {
            return EnumActionResult.FAIL;
        }
        else
        {
            if (worldIn.isAirBlock(pos))
            {
            	playerIn.playSound(SoundEvents.ENTITY_WITHER_SHOOT, 1.4F, itemRand.nextFloat() * 0.4F + 0.8F);
                worldIn.setBlockState(pos, NetherBlocks.DARK_FIRE.getDefaultState()); 
            }

            if (!playerIn.capabilities.isCreativeMode)
            {
                stack.func_190918_g(1);
            }
            
            return EnumActionResult.SUCCESS;
        }
    }

}
