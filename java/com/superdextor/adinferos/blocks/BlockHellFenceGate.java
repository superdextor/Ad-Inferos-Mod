package com.superdextor.adinferos.blocks;

import net.minecraft.block.BlockFenceGate;
import net.minecraft.block.BlockPlanks;
import net.minecraft.block.SoundType;
import net.minecraft.block.material.MapColor;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;

public class BlockHellFenceGate extends BlockFenceGate
{
	private final Material material;
	private final MapColor color;
	
    public BlockHellFenceGate(Material material, SoundType sound, MapColor color)
    {
        super(BlockPlanks.EnumType.OAK);
        this.material = material;
        this.setSoundType(sound);
        this.color = color;
    }
    
    public Material getMaterial(IBlockState state)
    {
        return this.material;
    }
    
    public MapColor getMapColor(IBlockState state) {
    	return this.color;
    }
}