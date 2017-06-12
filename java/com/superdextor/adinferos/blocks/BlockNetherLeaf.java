package com.superdextor.adinferos.blocks;

import java.util.List;
import java.util.Random;

import com.google.common.base.Predicate;
import com.superdextor.adinferos.init.NetherBlocks;
import com.superdextor.adinferos.init.NetherItems;

import net.minecraft.block.BlockLeaves;
import net.minecraft.block.BlockPlanks;
import net.minecraft.block.properties.IProperty;
import net.minecraft.block.properties.PropertyEnum;
import net.minecraft.block.state.BlockStateContainer;
import net.minecraft.block.state.IBlockState;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.item.ItemShears;
import net.minecraft.item.ItemStack;
import net.minecraft.stats.StatList;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.NonNullList;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class BlockNetherLeaf extends BlockLeaves
{
    public static final PropertyEnum<BlockNetherPlanks.EnumType> VARIANT = PropertyEnum.<BlockNetherPlanks.EnumType>create("variant", BlockNetherPlanks.EnumType.class, new Predicate<BlockNetherPlanks.EnumType>()
    {
        public boolean apply(BlockNetherPlanks.EnumType p_apply_1_)
        {
            return p_apply_1_.getMetadata() < 4;
        }
    });

    public BlockNetherLeaf()
    {
    	this.leavesFancy = true;
        this.setDefaultState(this.blockState.getBaseState().withProperty(VARIANT, BlockNetherPlanks.EnumType.INFERNO).withProperty(CHECK_DECAY, Boolean.valueOf(true)).withProperty(DECAYABLE, Boolean.valueOf(true)));
    }

    protected void dropApple(World worldIn, BlockPos pos, IBlockState state, int chance)
    {
    	if(worldIn.rand.nextInt(chance) == 0) {
    		
    		switch(state.getValue(VARIANT)) {
	    		
	    		case MAGMA: {
	    			if(worldIn.rand.nextBoolean()) {
		                spawnAsEntity(worldIn, pos, new ItemStack(Items.BLAZE_POWDER, 1 + worldIn.rand.nextInt(3)));
	    			}
	    			else {
		                spawnAsEntity(worldIn, pos, new ItemStack(Items.MAGMA_CREAM));
	    			}
	                break;
	    		}
	    		
	    		case PHANTOM: {
	    			if(worldIn.rand.nextBoolean()) {
		                spawnAsEntity(worldIn, pos, new ItemStack(Items.GUNPOWDER, 3 + worldIn.rand.nextInt(4)));
	    			}else {
		                spawnAsEntity(worldIn, pos, new ItemStack(Items.GHAST_TEAR));
	    			}
	                break;
	    		}
	    		
	    		case ASH: {
	    			if(worldIn.rand.nextBoolean()) {
		                spawnAsEntity(worldIn, pos, new ItemStack(NetherItems.WITHER_DUST, 4 + worldIn.rand.nextInt(9)));
	    			}
	    			else {
		                spawnAsEntity(worldIn, pos, new ItemStack(NetherItems.WITHER_APPLE));
	    			}
	                break;
	    		}
	    		
	    		default: {
	    			if(worldIn.rand.nextBoolean()) {
		                spawnAsEntity(worldIn, pos, new ItemStack(Items.REDSTONE, 4 + worldIn.rand.nextInt(9)));
	    			}
	    			else {
		                spawnAsEntity(worldIn, pos, new ItemStack(Items.APPLE));
	    			}
	                break;
	    		}
    		}
    	}
    }
    
    protected int getSaplingDropChance(IBlockState state) {
        return 40;
    }
    
    public Item getItemDropped(IBlockState state, Random rand, int fortune)
    {
        return Item.getItemFromBlock(NetherBlocks.SAPLING);
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

    protected ItemStack createStackedBlock(IBlockState state)
    {
        return new ItemStack(Item.getItemFromBlock(this), 1, ((BlockNetherPlanks.EnumType)state.getValue(VARIANT)).getMetadata());
    }

    /**
     * Convert the given metadata into a BlockState for this Block
     */
    public IBlockState getStateFromMeta(int meta)
    {
        return this.getDefaultState().withProperty(VARIANT, this.getTrueWoodType(meta)).withProperty(DECAYABLE, Boolean.valueOf((meta & 4) == 0)).withProperty(CHECK_DECAY, Boolean.valueOf((meta & 8) > 0));
    }

    /**
     * Convert the BlockState into the correct metadata value
     */
    public int getMetaFromState(IBlockState state)
    {
        int i = 0;
        i = i | ((BlockNetherPlanks.EnumType)state.getValue(VARIANT)).getMetadata();

        if (!((Boolean)state.getValue(DECAYABLE)).booleanValue())
        {
            i |= 4;
        }

        if (((Boolean)state.getValue(CHECK_DECAY)).booleanValue())
        {
            i |= 8;
        }

        return i;
    }

    @Deprecated //USE getTrueWoodType instead
    public BlockPlanks.EnumType getWoodType(int meta)
    {
        return BlockPlanks.EnumType.OAK;
    }
    
    public BlockNetherPlanks.EnumType getTrueWoodType(int meta)
    {
        return BlockNetherPlanks.EnumType.byMetadata((meta & 3) % 4);
    }

    protected BlockStateContainer createBlockState()
    {
        return new BlockStateContainer(this, new IProperty[] {VARIANT, CHECK_DECAY, DECAYABLE});
    }

    /**
     * Gets the metadata of the item this Block can drop. This method is called when the block gets destroyed. It
     * returns the metadata of the dropped item based on the old metadata of the block.
     */
    public int damageDropped(IBlockState state)
    {
        return ((BlockNetherPlanks.EnumType)state.getValue(VARIANT)).getMetadata();
    }

    public void harvestBlock(World worldIn, EntityPlayer player, BlockPos pos, IBlockState state, TileEntity te, ItemStack stack)
    {
        if (!worldIn.isRemote && stack != null && stack.getItem() instanceof ItemShears)
        {
            player.addStat(StatList.getBlockStats(this));
        }
        else
        {
            super.harvestBlock(worldIn, player, pos, state, te, stack);
        }
    }

    @Override
    public List<ItemStack> onSheared(ItemStack item, net.minecraft.world.IBlockAccess world, BlockPos pos, int fortune)
    {
        return java.util.Arrays.asList(new ItemStack(this, 1, world.getBlockState(pos).getValue(VARIANT).getMetadata()));
    }
}