package com.superdextor.adinferos.items;

import net.minecraft.block.Block;
import net.minecraft.item.ItemMultiTexture;
import net.minecraft.item.ItemStack;

public class ItemWoodBlock extends ItemMultiTexture
{
    public ItemWoodBlock(Block block)
    {
        super(block, block, new String[] {"inferno", "magma", "phantom", "ash"});
    }
}