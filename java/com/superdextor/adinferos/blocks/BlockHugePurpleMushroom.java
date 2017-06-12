package com.superdextor.adinferos.blocks;

import net.minecraft.block.Block;
import net.minecraft.block.BlockHugeMushroom;
import net.minecraft.block.SoundType;
import net.minecraft.block.material.MapColor;
import net.minecraft.block.material.Material;

public class BlockHugePurpleMushroom extends BlockHugeMushroom {
	
	public BlockHugePurpleMushroom(Material material, MapColor color, Block mushroom) {
		super(material, color, mushroom);
		this.setSoundType(SoundType.WOOD);
	}

}
