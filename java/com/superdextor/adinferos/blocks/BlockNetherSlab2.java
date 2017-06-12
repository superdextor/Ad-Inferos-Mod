package com.superdextor.adinferos.blocks;

import java.util.Random;

import com.superdextor.adinferos.init.NetherBlocks;

import net.minecraft.block.BlockSlab;
import net.minecraft.block.SoundType;
import net.minecraft.block.material.MapColor;
import net.minecraft.block.material.Material;
import net.minecraft.block.properties.IProperty;
import net.minecraft.block.properties.PropertyEnum;
import net.minecraft.block.state.BlockStateContainer;
import net.minecraft.block.state.IBlockState;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.Entity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.IStringSerializable;
import net.minecraft.util.NonNullList;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.Explosion;
import net.minecraft.world.World;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class BlockNetherSlab2 extends BlockSlab
{
    public static final PropertyEnum<BlockNetherSlab2.EnumType> VARIANT = PropertyEnum.<BlockNetherSlab2.EnumType>create("variant", BlockNetherSlab2.EnumType.class);

    public BlockNetherSlab2()
    {
        super(Material.WOOD);
        this.setSoundType(SoundType.STONE);
        this.useNeighborBrightness = true;
        IBlockState iblockstate = this.blockState.getBaseState();

        if (!this.isDouble())
        {
            iblockstate = iblockstate.withProperty(HALF, BlockSlab.EnumBlockHalf.BOTTOM);
        }

        this.setDefaultState(iblockstate.withProperty(VARIANT, BlockNetherSlab2.EnumType.SMOOTH_NETHERRACK));
        this.setCreativeTab(CreativeTabs.BUILDING_BLOCKS);
        this.setHarvestLevel("pickaxe", 0, this.getDefaultState());
        this.setHarvestLevel("pickaxe", 3, this.getStateFromMeta(1));
    }

    /**
     * Get the MapColor for this Block and the given BlockState
     */
    public MapColor getMapColor(IBlockState state)
    {
        return ((BlockNetherSlab2.EnumType)state.getValue(VARIANT)).getMapColor();
    }

    /**
     * Get the Item that this Block should drop when harvested.
     */
    public Item getItemDropped(IBlockState state, Random rand, int fortune)
    {
        return Item.getItemFromBlock(NetherBlocks.STONE_SLAB);
    }

    public ItemStack getItem(World worldIn, BlockPos pos, IBlockState state)
    {
        return new ItemStack(NetherBlocks.STONE_SLAB, 1, ((BlockNetherSlab2.EnumType)state.getValue(VARIANT)).getMetadata());
    }

    /**
     * Returns the slab block name with the type associated with it
     */
    public String getUnlocalizedName(int meta)
    {
        return super.getUnlocalizedName() + "." + BlockNetherSlab2.EnumType.byMetadata(meta).getName();
    }

    public IProperty<?> getVariantProperty()
    {
        return VARIANT;
    }

    public Comparable<?> getTypeForItem(ItemStack stack)
    {
        return BlockNetherSlab2.EnumType.byMetadata(stack.getMetadata() & 7);
    }

    /**
     * returns a list of blocks with the same ID, but different meta (eg: wood returns 4 blocks)
     */
    @SideOnly(Side.CLIENT)
    public void getSubBlocks(Item itemIn, CreativeTabs tab, NonNullList<ItemStack> list)
    {
        if (itemIn != Item.getItemFromBlock(NetherBlocks.DOUBLE_STONE_SLAB))
        {
            for (BlockNetherSlab2.EnumType BlockNetherPlanks$enumtype : BlockNetherSlab2.EnumType.values())
            {
                list.add(new ItemStack(itemIn, 1, BlockNetherPlanks$enumtype.getMetadata()));
            }
        }
    }

    /**
     * Convert the given metadata into a BlockState for this Block
     */
    public IBlockState getStateFromMeta(int meta)
    {
        IBlockState iblockstate = this.getDefaultState().withProperty(VARIANT, BlockNetherSlab2.EnumType.byMetadata(meta & 7));

        if (!this.isDouble())
        {
            iblockstate = iblockstate.withProperty(HALF, (meta & 8) == 0 ? BlockSlab.EnumBlockHalf.BOTTOM : BlockSlab.EnumBlockHalf.TOP);
        }

        return iblockstate;
    }

    /**
     * Convert the BlockState into the correct metadata value
     */
    public int getMetaFromState(IBlockState state)
    {
        int i = 0;
        i = i | ((BlockNetherSlab2.EnumType)state.getValue(VARIANT)).getMetadata();

        if (!this.isDouble() && state.getValue(HALF) == BlockSlab.EnumBlockHalf.TOP)
        {
            i |= 8;
        }

        return i;
    }

    protected BlockStateContainer createBlockState()
    {
        return this.isDouble() ? new BlockStateContainer(this, new IProperty[] {VARIANT}): new BlockStateContainer(this, new IProperty[] {HALF, VARIANT});
    }

    /**
     * Gets the metadata of the item this Block can drop. This method is called when the block gets destroyed. It
     * returns the metadata of the dropped item based on the old metadata of the block.
     */
    public int damageDropped(IBlockState state)
    {
        return state.getValue(VARIANT).getMetadata();
    }
    
    public float getBlockHardness(IBlockState blockState, World worldIn, BlockPos pos) {
        return blockState.getValue(VARIANT).getHardness();
    }
    
    public float getExplosionResistance(World world, BlockPos pos, Entity exploder, Explosion explosion) {
        return world.getBlockState(pos).getValue(VARIANT).getExplosionResistance();
    }
    
    public boolean isDouble() {
    	return this == NetherBlocks.DOUBLE_STONE_SLAB;
    }
    
    public static enum EnumType implements IStringSerializable
    {
    	SMOOTH_NETHERRACK(0, "smooth_netherrack", MapColor.NETHERRACK, 1.5F, 10.0F),
        OBSIDIAN(1, "obsidian", MapColor.OBSIDIAN, 50.0F, 2000.0F);

        private static final BlockNetherSlab2.EnumType[] META_LOOKUP = new BlockNetherSlab2.EnumType[values().length];
        private final int meta;
        private final String name;
        private final MapColor mapColor;
        private final float hardness;
        private final float explosionResistance;

        private EnumType(int metaIn, String nameIn, MapColor mapColorIn, float hardness, float explosionResistance)
        {
            this.meta = metaIn;
            this.name = nameIn;
            this.mapColor = mapColorIn;
            this.hardness = hardness;
            this.explosionResistance = explosionResistance;
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
        
        public float getHardness() {
        	return this.hardness;
        }
        
        public float getExplosionResistance() {
        	return this.explosionResistance;
        }

        public static BlockNetherSlab2.EnumType byMetadata(int meta)
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
            for (BlockNetherSlab2.EnumType BlockNetherPlanks$enumtype : values())
            {
                META_LOOKUP[BlockNetherPlanks$enumtype.getMetadata()] = BlockNetherPlanks$enumtype;
            }
        }
    }
}