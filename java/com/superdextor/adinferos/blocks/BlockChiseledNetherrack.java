package com.superdextor.adinferos.blocks;

import com.superdextor.thinkbigcore.blocks.BlockCustom;

import net.minecraft.block.SoundType;
import net.minecraft.block.material.MapColor;
import net.minecraft.block.material.Material;
import net.minecraft.block.properties.IProperty;
import net.minecraft.block.properties.PropertyBool;
import net.minecraft.block.state.BlockStateContainer;
import net.minecraft.block.state.IBlockState;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockAccess;

public class BlockChiseledNetherrack extends BlockCustom {

    public static final PropertyBool GLOWING = PropertyBool.create("glowing");
	
	public BlockChiseledNetherrack() {
		super(Material.ROCK, SoundType.STONE, MapColor.NETHERRACK);
        this.setDefaultState(this.blockState.getBaseState().withProperty(GLOWING, Boolean.valueOf(false)));
	}
	
	public int getLightValue(IBlockState state, IBlockAccess world, BlockPos pos) {
		
        if (((Boolean)state.getValue(GLOWING)).booleanValue())
        {
        	return 7;
        }
		
		return super.getLightValue(state, world, pos);
	}
	
    public IBlockState getStateFromMeta(int meta)
    {
        return this.getDefaultState().withProperty(GLOWING, Boolean.valueOf((meta & 1) > 0));
    }

    public int getMetaFromState(IBlockState state)
    {
        return ((Boolean)state.getValue(GLOWING)).booleanValue() ? 1 : 0;
    }

    protected BlockStateContainer createBlockState()
    {
        return new BlockStateContainer(this, new IProperty[] {GLOWING});
    }

}
