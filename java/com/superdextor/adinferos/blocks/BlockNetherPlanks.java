package com.superdextor.adinferos.blocks;

import com.superdextor.thinkbigcore.blocks.BlockCustom;

import net.minecraft.block.SoundType;
import net.minecraft.block.material.MapColor;
import net.minecraft.block.material.Material;
import net.minecraft.block.properties.IProperty;
import net.minecraft.block.properties.PropertyEnum;
import net.minecraft.block.state.BlockStateContainer;
import net.minecraft.block.state.IBlockState;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.IStringSerializable;
import net.minecraft.util.NonNullList;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class BlockNetherPlanks extends BlockCustom
{
    public static final PropertyEnum<BlockNetherPlanks.EnumType> VARIANT = PropertyEnum.<BlockNetherPlanks.EnumType>create("variant", BlockNetherPlanks.EnumType.class);

    public BlockNetherPlanks()
    {
        super(Material.WOOD);
        this.setDefaultState(this.blockState.getBaseState().withProperty(VARIANT, BlockNetherPlanks.EnumType.INFERNO));
        this.setCreativeTab(CreativeTabs.BUILDING_BLOCKS);
        this.setStepSound(SoundType.WOOD);
    }

    /**
     * Gets the metadata of the item this Block can drop. This method is called when the block gets destroyed. It
     * returns the metadata of the dropped item based on the old metadata of the block.
     */
    public int damageDropped(IBlockState state)
    {
        return ((BlockNetherPlanks.EnumType)state.getValue(VARIANT)).getMetadata();
    }

    /**
     * returns a list of blocks with the same ID, but different meta (eg: wood returns 4 blocks)
     */
    @SideOnly(Side.CLIENT)
    public void getSubBlocks(Item itemIn, CreativeTabs tab, NonNullList<ItemStack> list)
    {
        for (BlockNetherPlanks.EnumType BlockNetherPlanks$enumtype : BlockNetherPlanks.EnumType.values())
        {
            list.add(new ItemStack(itemIn, 1, BlockNetherPlanks$enumtype.getMetadata()));
        }
    }

    /**
     * Convert the given metadata into a BlockState for this Block
     */
    public IBlockState getStateFromMeta(int meta)
    {
        return this.getDefaultState().withProperty(VARIANT, BlockNetherPlanks.EnumType.byMetadata(meta));
    }

    /**
     * Get the MapColor for this Block and the given BlockState
     */
    public MapColor getMapColor(IBlockState state)
    {
        return ((BlockNetherPlanks.EnumType)state.getValue(VARIANT)).getMapColor();
    }

    /**
     * Convert the BlockState into the correct metadata value
     */
    public int getMetaFromState(IBlockState state)
    {
        return ((BlockNetherPlanks.EnumType)state.getValue(VARIANT)).getMetadata();
    }

    protected BlockStateContainer createBlockState()
    {
        return new BlockStateContainer(this, new IProperty[] {VARIANT});
    }

    public static enum EnumType implements IStringSerializable
    {
    	INFERNO(0, "inferno", MapColor.RED),
        MAGMA(1, "magma", MapColor.TNT),
        PHANTOM(2, "phantom", MapColor.AIR),
        ASH(3, "ash", MapColor.GRAY);

        private static final BlockNetherPlanks.EnumType[] META_LOOKUP = new BlockNetherPlanks.EnumType[values().length];
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

        public static BlockNetherPlanks.EnumType byMetadata(int meta)
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
            for (BlockNetherPlanks.EnumType BlockNetherPlanks$enumtype : values())
            {
                META_LOOKUP[BlockNetherPlanks$enumtype.getMetadata()] = BlockNetherPlanks$enumtype;
            }
        }
    }
}