package com.superdextor.adinferos.blocks;

import java.util.Random;

import com.superdextor.adinferos.NetherDamageSource;
import com.superdextor.adinferos.enchantments.AdInferosEnchantments;
import com.superdextor.adinferos.entity.NetherMob;
import com.superdextor.thinkbigcore.config.TBCConfig;

import net.minecraft.block.Block;
import net.minecraft.block.BlockBreakable;
import net.minecraft.block.SoundType;
import net.minecraft.block.material.MapColor;
import net.minecraft.block.material.Material;
import net.minecraft.block.properties.IProperty;
import net.minecraft.block.properties.PropertyEnum;
import net.minecraft.block.state.BlockStateContainer;
import net.minecraft.block.state.IBlockState;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.init.Blocks;
import net.minecraft.init.MobEffects;
import net.minecraft.item.ItemStack;
import net.minecraft.potion.PotionEffect;
import net.minecraft.util.BlockRenderLayer;
import net.minecraft.util.EnumParticleTypes;
import net.minecraft.util.IStringSerializable;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class BlockCurse extends BlockBreakable {
	
    public static final PropertyEnum<BlockCurse.EnumType> VARIANT = PropertyEnum.<BlockCurse.EnumType>create("variant", BlockCurse.EnumType.class);
    protected static final AxisAlignedBB CURSE_AABB = new AxisAlignedBB(0.0D, 0.0D, 0.0D, 1.0D, 0.0825D, 1.0D);

	@SideOnly(Side.CLIENT)
	private boolean shownTip = false;
    
	public BlockCurse(Material material) {
        super(material, false);
        this.setDefaultState(this.blockState.getBaseState().withProperty(VARIANT, BlockCurse.EnumType.CONFUSION));
        this.setTickRandomly(true);
		this.setSoundType(SoundType.SAND);
	}
	
    @SideOnly(Side.CLIENT)
    public AxisAlignedBB getSelectedBoundingBox(IBlockState state, World worldIn, BlockPos pos)
    {
    	if(!shownTip) {
    		TBCConfig.displayTip("Woah don't step on it! This block is cursed, It may try to curse you next!");
    		shownTip = true;
    	}
    	
    	return super.getSelectedBoundingBox(state, worldIn, pos);
    }
    
    @SideOnly(Side.CLIENT)
    public BlockRenderLayer getBlockLayer()
    {
        return BlockRenderLayer.TRANSLUCENT;
    }
	
	public boolean isOpaqueCube(IBlockState state) {
		return false;
	}
	
    public boolean isFullCube(IBlockState state)
    {
        return false;
    }
   
    public int quantityDropped(Random random)
    {
        return 0;
    }

    public AxisAlignedBB getBoundingBox(IBlockState state, IBlockAccess source, BlockPos pos)
    {
        return CURSE_AABB;
    }

    public boolean canPlaceBlockAt(World worldIn, BlockPos pos)
    {
        return super.canPlaceBlockAt(worldIn, pos) && this.canBlockStay(worldIn, pos);
    }
    
    public void neighborChanged(IBlockState state, World worldIn, BlockPos pos, Block blockIn)
    {
    	this.checkForDrop(worldIn, pos, state);
    }

    private boolean checkForDrop(World worldIn, BlockPos pos, IBlockState state)
    {
        if (!this.canBlockStay(worldIn, pos))
        {
            worldIn.setBlockToAir(pos);
            return false;
        }
        else
        {
            return true;
        }
    }

    private boolean canBlockStay(World worldIn, BlockPos pos)
    {
    	IBlockState state = worldIn.getBlockState(pos.down());
        return state.getBlock() == Blocks.SOUL_SAND || state.isOpaqueCube();
    }
    
    public void onEntityCollidedWithBlock(World worldIn, BlockPos pos, IBlockState state, Entity entity)
    {
    	if(!(entity instanceof EntityLivingBase)) {
    		return;
    	}
    	
    	EntityLivingBase entitylivingbase = (EntityLivingBase) entity;
	    		
    	if(entity instanceof NetherMob && ((NetherMob) entity).isCurseResistant()) {
    		return;
    	}
	        	
    	ItemStack stack = EnchantmentHelper.getEnchantedItem(AdInferosEnchantments.curseProtection, entitylivingbase);
    	if(stack != null && worldIn.rand.nextInt(1 + EnchantmentHelper.getEnchantmentLevel(AdInferosEnchantments.curseProtection, stack)) > 1) {
    		return;
    	}
    	
    	switch(state.getBlock().getMetaFromState(state)) {
	    	case 1: {
	    		entity.setFire(4);
	    		break;
	    	}
				    	
	    	case 2: {
	    		entitylivingbase.addPotionEffect(new PotionEffect(MobEffects.WITHER, 120, 0));
	    		break;
	    	}
				    	
	    	case 3: {
	    		entitylivingbase.addPotionEffect(new PotionEffect(MobEffects.POISON, 100, 0));
	    		break;
	    	}
				    	
	    	default: {
	    		entitylivingbase.addPotionEffect(new PotionEffect(MobEffects.NAUSEA, 100, 0));
	    		break;
	    	}
    	}
		    
    	if (entity.ticksExisted % 8 == 0) {
    		entity.attackEntityFrom(NetherDamageSource.CURSE, 1);
    	}
    }
    
    @SideOnly(Side.CLIENT)
    public void randomDisplayTick(IBlockState state, World worldIn, BlockPos pos, Random rand)
    {
        double d0 = (double)((float)pos.getX() + rand.nextFloat());
        double d1 = (double)((float)pos.getY() + 0.1F);
        double d2 = (double)((float)pos.getZ() + rand.nextFloat());
    	
    	switch(state.getBlock().getMetaFromState(state)) {
    	
    		case 1: {
    	        worldIn.spawnParticle(EnumParticleTypes.FLAME, d0, d1, d2, 0.0D, 0.0D, 0.0D, new int[0]);
    	        break;
    		}
    		
    		case 2: {
    	        worldIn.spawnParticle(EnumParticleTypes.SUSPENDED_DEPTH, d0, d1, d2, 0.0D, 0.0D, 0.0D, new int[0]);
    	        break;
    		}
    		
    		case 3: {
    	        worldIn.spawnParticle(EnumParticleTypes.REDSTONE, d0, d1, d2, 0.1D, 0.5D, 0.0D, new int[0]);
    	        break;
    		}
    		
    		default: {
    	        worldIn.spawnParticle(EnumParticleTypes.REDSTONE, d0, d1, d2, 0.5D, 0.0D, 1.0D, new int[0]);
    	        break;
    		}
    	}
    }

    public void updateTick(World worldIn, BlockPos pos, IBlockState state, Random rand)
    {
         worldIn.setBlockToAir(pos);
    }

    /**
     * Convert the given metadata into a BlockState for this Block
     */
    public IBlockState getStateFromMeta(int meta)
    {
        return this.getDefaultState().withProperty(VARIANT, BlockCurse.EnumType.byMetadata(meta));
    }

    /**
     * Get the MapColor for this Block and the given BlockState
     */
    public MapColor getMapColor(IBlockState state)
    {
        return ((BlockCurse.EnumType)state.getValue(VARIANT)).getMapColor();
    }

    /**
     * Convert the BlockState into the correct metadata value
     */
    public int getMetaFromState(IBlockState state)
    {
        return ((BlockCurse.EnumType)state.getValue(VARIANT)).getMetadata();
    }

    protected BlockStateContainer createBlockState()
    {
        return new BlockStateContainer(this, new IProperty[] {VARIANT});
    }
	
    public static enum EnumType implements IStringSerializable
    {
        CONFUSION(0, "confusion", MapColor.PURPLE),
        FIRE(1, "fire", MapColor.TNT),
        WITHER(2, "wither", MapColor.GRAY),
        POISON(3, "poison", MapColor.GREEN);

        private static final BlockCurse.EnumType[] META_LOOKUP = new BlockCurse.EnumType[values().length];
        private final int meta;
        private final String name;
        private final MapColor color;

        private EnumType(int metaIn, String nameIn, MapColor colorIn)
        {
        	this.meta = metaIn;
        	this.name = nameIn;
        	this.color = colorIn;
        }

        public int getMetadata()
        {
            return this.meta;
        }

        public String toString()
        {
            return this.name;
        }
        
        public MapColor getMapColor() {
        	return this.color;
        }

        public static BlockCurse.EnumType byMetadata(int meta)
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
            for (BlockCurse.EnumType blockplanks$enumtype : values())
            {
                META_LOOKUP[blockplanks$enumtype.getMetadata()] = blockplanks$enumtype;
            }
        }
    }
}