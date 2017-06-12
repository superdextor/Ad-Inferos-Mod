package com.superdextor.adinferos.blocks;

import com.superdextor.thinkbigcore.blocks.BlockCustomOre;

import net.minecraft.block.material.MapColor;
import net.minecraft.block.properties.IProperty;
import net.minecraft.block.properties.PropertyEnum;
import net.minecraft.block.state.BlockStateContainer;
import net.minecraft.block.state.IBlockState;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.IStringSerializable;
import net.minecraft.util.NonNullList;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.RayTraceResult;
import net.minecraft.world.World;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class BlockHellOre extends BlockCustomOre {
	
    public static final PropertyEnum<BlockHellOre.EnumType> VARIANT = PropertyEnum.<BlockHellOre.EnumType>create("variant", BlockHellOre.EnumType.class);
	
    public BlockHellOre() {
    	super();
        this.setDefaultState(this.blockState.getBaseState().withProperty(VARIANT, BlockHellOre.EnumType.DEFAULT));
    }
    
	public BlockHellOre(Item drop, int min, int max, int xp) {
    	super(drop, min, max, xp);
        this.setDefaultState(this.blockState.getBaseState().withProperty(VARIANT, BlockHellOre.EnumType.DEFAULT));
	}
	
	public ItemStack getPickBlock(IBlockState state, RayTraceResult target, World world, BlockPos pos, EntityPlayer player)
    {
        return new ItemStack(this, 1, ((BlockHellOre.EnumType)state.getValue(VARIANT)).getMetadata());
    }
	
    @SideOnly(Side.CLIENT)
    public void getSubBlocks(Item itemIn, CreativeTabs tab, NonNullList<ItemStack> list)
    {
        for (BlockHellOre.EnumType BlockHellOre$enumtype : BlockHellOre.EnumType.values())
        {
            list.add(new ItemStack(itemIn, 1, BlockHellOre$enumtype.getMetadata()));
        }
    }
	
    public IBlockState getStateFromMeta(int meta)
    {
        return this.getDefaultState().withProperty(VARIANT, BlockHellOre.EnumType.byMetadata(meta));
    }

    public MapColor getMapColor(IBlockState state)
    {
        return ((BlockHellOre.EnumType)state.getValue(VARIANT)).getMapColor();
    }

    public int getMetaFromState(IBlockState state)
    {
        return ((BlockHellOre.EnumType)state.getValue(VARIANT)).getMetadata();
    }

    protected BlockStateContainer createBlockState()
    {
        return new BlockStateContainer(this, new IProperty[] {VARIANT});
    }
	
    public static enum EnumType implements IStringSerializable
    {
        DEFAULT(0, "default", MapColor.NETHERRACK),
        DAKRSTONE(1, "darkstone", MapColor.BLACK);

        private static final BlockHellOre.EnumType[] META_LOOKUP = new BlockHellOre.EnumType[values().length];
        private final int meta;
        private final String name;
        private final MapColor mapColor;

        private EnumType(int metaIn, String nameIn, MapColor mapColorIn)
        {
            this.meta = metaIn;
            this.name = nameIn;
            this.mapColor = mapColorIn;
        }

        public int getMetadata()
        {
            return this.meta;
        }

        /**
         * The color which represents this entry on a map.
         */
        public MapColor getMapColor()
        {
            return this.mapColor;
        }

        public String toString()
        {
            return this.name;
        }

        public static BlockHellOre.EnumType byMetadata(int meta)
        {
            if (meta < 0 || meta >= META_LOOKUP.length)
            {
                meta = 0;
            }

            return META_LOOKUP[meta];
        }

        public String getName()
        {
            return this.name;
        }

        static
        {
            for (BlockHellOre.EnumType BlockHellOre$enumtype : values())
            {
                META_LOOKUP[BlockHellOre$enumtype.getMetadata()] = BlockHellOre$enumtype;
            }
        }
    }

}
