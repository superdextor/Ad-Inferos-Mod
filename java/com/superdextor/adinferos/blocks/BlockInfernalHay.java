package com.superdextor.adinferos.blocks;

import net.minecraft.block.BlockHay;
import net.minecraft.block.SoundType;
import net.minecraft.block.material.MapColor;
import net.minecraft.block.state.IBlockState;

public class BlockInfernalHay extends BlockHay {
	
	public BlockInfernalHay() {
		this.setSoundType(SoundType.PLANT);
	}
	
    public MapColor getMapColor(IBlockState state)
    {
        return MapColor.RED;
    }
}
