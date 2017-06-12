package com.superdextor.adinferos.blocks;

import java.util.Random;

import com.superdextor.adinferos.init.NetherItems;

import net.minecraft.block.BlockGlowstone;
import net.minecraft.block.SoundType;
import net.minecraft.block.material.MapColor;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.item.Item;

public class BlockDimstone extends BlockGlowstone
{

    public BlockDimstone(Material materialIn)
    {
        super(materialIn);
        this.setSoundType(SoundType.GLASS);
    }
    
    public Item getItemDropped(IBlockState state, Random rand, int fortune)
    {
        return NetherItems.DIMSTONE_DUST;
    }

    public MapColor getMapColor(IBlockState state)
    {
        return MapColor.BLACK;
    }
}