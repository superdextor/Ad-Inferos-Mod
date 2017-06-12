package com.superdextor.adinferos.items;

import net.minecraft.block.Block;
import net.minecraft.item.ItemMultiTexture;
import net.minecraft.item.ItemStack;

public class ItemTwoBlock extends ItemMultiTexture
{
    public ItemTwoBlock(Block block)
    {
        super(block, block, new String[] {"", ""});
    }
    
    public String getUnlocalizedName(ItemStack stack)
    {
        return super.getUnlocalizedName();
    }
}