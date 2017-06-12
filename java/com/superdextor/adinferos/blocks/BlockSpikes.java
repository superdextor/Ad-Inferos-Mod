package com.superdextor.adinferos.blocks;

import java.util.Random;

import javax.annotation.Nullable;

import com.superdextor.adinferos.NetherDamageSource;
import com.superdextor.adinferos.init.NetherBlocks;
import com.superdextor.thinkbigcore.config.TBCConfig;

import net.minecraft.block.Block;
import net.minecraft.block.SoundType;
import net.minecraft.block.material.MapColor;
import net.minecraft.block.material.Material;
import net.minecraft.block.properties.IProperty;
import net.minecraft.block.properties.PropertyEnum;
import net.minecraft.block.state.BlockStateContainer;
import net.minecraft.block.state.IBlockState;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.BlockRenderLayer;
import net.minecraft.util.NonNullList;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.RayTraceResult;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class BlockSpikes extends Block {

	public static final PropertyEnum<BlockHellOre.EnumType> VARIANT = PropertyEnum.<BlockHellOre.EnumType>create("variant", BlockHellOre.EnumType.class);

	@SideOnly(Side.CLIENT)
	private boolean shownTip = false;
	
    public BlockSpikes(Material material)
    {
        super(material);
        this.setSoundType(SoundType.STONE);
        this.setDefaultState(this.blockState.getBaseState().withProperty(VARIANT, BlockHellOre.EnumType.DEFAULT));
    }
    
    @SideOnly(Side.CLIENT)
    public AxisAlignedBB getSelectedBoundingBox(IBlockState state, World worldIn, BlockPos pos)
    {
    	if(!shownTip) {
    		TBCConfig.displayTip("Pointy");
    		shownTip = true;
    	}
    	
    	return super.getSelectedBoundingBox(state, worldIn, pos);
    }

    public boolean isOpaqueCube(IBlockState state)
    {
        return false;
    }

    public boolean isFullCube(IBlockState state)
    {
        return false;
    }
    
    @Nullable
    public AxisAlignedBB getCollisionBoundingBox(IBlockState blockState, IBlockAccess worldIn, BlockPos pos) {
        return NULL_AABB;
    }

    @SideOnly(Side.CLIENT)
    public BlockRenderLayer getBlockLayer()
    {
        return BlockRenderLayer.CUTOUT;
    }
    
    public void onEntityCollidedWithBlock(World worldIn, BlockPos pos, IBlockState state, Entity entity)
    {
    	entity.attackEntityFrom(NetherDamageSource.SPIKES, 3.0F);
    }
    
    public boolean canPlaceBlockAt(World worldIn, BlockPos pos)
    {
        return worldIn.getBlockState(pos.down()).isFullyOpaque();
    }
    
    public Item getItemDropped(IBlockState state, Random rand, int fortune)
    {
        return rand.nextBoolean() ? (state.getValue(VARIANT).getMetadata() == 1 ? Item.getItemFromBlock(NetherBlocks.DARKSTONE) : Item.getItemFromBlock(Blocks.NETHERRACK)) : null;
    }
    
    protected boolean canSilkHarvest()
    {
        return true;
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
    
    public int getMetaFromState(IBlockState state)
    {
        return ((BlockHellOre.EnumType)state.getValue(VARIANT)).getMetadata();
    }

    public MapColor getMapColor(IBlockState state)
    {
        return ((BlockHellOre.EnumType)state.getValue(VARIANT)).getMapColor();
    }

    protected BlockStateContainer createBlockState()
    {
        return new BlockStateContainer(this, new IProperty[] {VARIANT});
    }
}