package com.superdextor.adinferos.blocks;

import java.util.Random;

import javax.annotation.Nullable;

import com.superdextor.adinferos.entity.NetherMob;
import com.superdextor.adinferos.init.NetherItems;
import com.superdextor.thinkbigcore.blocks.BlockCustom;

import net.minecraft.block.SoundType;
import net.minecraft.block.material.MapColor;
import net.minecraft.block.material.Material;
import net.minecraft.block.properties.IProperty;
import net.minecraft.block.properties.PropertyEnum;
import net.minecraft.block.state.BlockStateContainer;
import net.minecraft.block.state.IBlockState;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.MobEffects;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.potion.PotionEffect;
import net.minecraft.util.DamageSource;
import net.minecraft.util.IStringSerializable;
import net.minecraft.util.NonNullList;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.RayTraceResult;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;


public class BlockWitherBlock extends BlockCustom {
	
    protected static final AxisAlignedBB WITHER_BLOCK_AABB = new AxisAlignedBB(0.01D, 0.01D, 0.01D, 0.99D, 0.99D, 0.99D);
    public static final PropertyEnum<BlockWitherBlock.EnumType> VARIANT = PropertyEnum.<BlockWitherBlock.EnumType>create("variant", BlockWitherBlock.EnumType.class);

	public BlockWitherBlock(Material materialIn, int type) {
		super(materialIn);
		this.setBeaconBlock();
        this.setDefaultState(this.blockState.getBaseState().withProperty(VARIANT, BlockWitherBlock.EnumType.DUST));
		this.setHardness(10.0F);
		this.setResistance(20.0F);
		this.setStepSound(SoundType.METAL);
	}
	
    public Item getItemDropped(IBlockState state, Random rand, int fortune)
    {
        return ((BlockWitherBlock.EnumType)state.getValue(VARIANT)).getMetadata() == 1 ? Item.getItemFromBlock(this) : NetherItems.WITHER_DUST;
    }
    
    public int quantityDropped(IBlockState state, int fortune, Random random) {
    	
    	if(((BlockWitherBlock.EnumType)state.getValue(VARIANT)).getMetadata() == 1) {
    		return 1;
    	}
    	
    	return 9;
    }
    
    public int damageDropped(IBlockState state) {
    	return ((BlockWitherBlock.EnumType)state.getValue(VARIANT)).getMetadata() == 1 ? 1 : 0;
    }

    public void onEntityCollidedWithBlock(World worldIn, BlockPos pos, IBlockState state, Entity entity)
    {
    	boolean flag = entity instanceof EntityLivingBase;
		
    	if(entity instanceof NetherMob) {
    		NetherMob nethermob = (NetherMob) entity;
    		if(nethermob.isWitherResistant())
    			flag = false;
    	}
    	
    	if(flag) {
    		int i = ((BlockWitherBlock.EnumType)state.getValue(VARIANT)).getMetadata();
    	    ((EntityLivingBase) entity).addPotionEffect(new PotionEffect(MobEffects.WITHER, 120, i));
    	    entity.attackEntityFrom(DamageSource.wither, 2 + i);
    	}
    }
    
    @Nullable
    public AxisAlignedBB getCollisionBoundingBox(IBlockState blockState, IBlockAccess worldIn, BlockPos pos)
    {
        return WITHER_BLOCK_AABB;
    }
    
    public MapColor getMapColor(IBlockState state)
    {
        return MapColor.GRAY;
    }
    
    public boolean isBeaconBase(IBlockAccess worldObj, BlockPos pos, BlockPos beacon)
    {
        return true;
    }
	
	public ItemStack getPickBlock(IBlockState state, RayTraceResult target, World world, BlockPos pos, EntityPlayer player) {
		return new ItemStack(this, 1, ((BlockWitherBlock.EnumType)state.getValue(VARIANT)).getMetadata());
	}
	
    @SideOnly(Side.CLIENT)
    public void getSubBlocks(Item itemIn, CreativeTabs tab, NonNullList<ItemStack> list)
    {
        list.add(new ItemStack(itemIn, 1, 0));
        list.add(new ItemStack(itemIn, 1, 1));
    }
	
    public IBlockState getStateFromMeta(int meta)
    {
        return this.getDefaultState().withProperty(VARIANT, BlockWitherBlock.EnumType.byMetadata(meta));
    }

    public int getMetaFromState(IBlockState state)
    {
        return ((BlockWitherBlock.EnumType)state.getValue(VARIANT)).getMetadata();
    }

    protected BlockStateContainer createBlockState()
    {
        return new BlockStateContainer(this, new IProperty[] {VARIANT});
    }
	
    public static enum EnumType implements IStringSerializable
    {
        DUST(0, "dust"),
        GEM(1, "gem");

        private static final BlockWitherBlock.EnumType[] META_LOOKUP = new BlockWitherBlock.EnumType[values().length];
        private final int meta;
        private final String name;

        private EnumType(int metaIn, String nameIn)
        {
            this.meta = metaIn;
            this.name = nameIn;
        }

        public int getMetadata()
        {
            return this.meta;
        }

        public String toString()
        {
            return this.name;
        }

        public static BlockWitherBlock.EnumType byMetadata(int meta)
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
            for (BlockWitherBlock.EnumType BlockWitherBlock$enumtype : values())
            {
                META_LOOKUP[BlockWitherBlock$enumtype.getMetadata()] = BlockWitherBlock$enumtype;
            }
        }
    }
	
}