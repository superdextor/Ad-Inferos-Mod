package com.superdextor.adinferos.blocks;

import java.util.Random;
import java.util.Set;

import com.superdextor.adinferos.init.NetherItems;
import com.superdextor.adinferos.inventory.AdInferosGuis;
import com.superdextor.adinferos.inventory.TileEntitySpawner;
import com.superdextor.adinferos.items.ItemBloodBucket;
import com.superdextor.thinkbigcore.ThinkBigCore;

import net.minecraft.block.BlockMobSpawner;
import net.minecraft.block.SoundType;
import net.minecraft.block.properties.IProperty;
import net.minecraft.block.properties.PropertyBool;
import net.minecraft.block.state.BlockStateContainer;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.InventoryHelper;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.RayTraceResult;
import net.minecraft.world.World;

public class BlockSpawner extends BlockMobSpawner {
	
    public static final PropertyBool CUSTOMIZABLE = PropertyBool.create("customizable");
	
	public BlockSpawner() {
        this.setDefaultState(this.blockState.getBaseState().withProperty(CUSTOMIZABLE, Boolean.valueOf(true)));
		this.setSoundType(SoundType.METAL);
	}
	
	public static boolean isSpawnerEntity(Entity entityIn) {
		
		if(entityIn == null) {
			return false;
		}
		
		Set<String> tags = entityIn.getTags();
		
		if(!tags.isEmpty() && tags.contains("AdSpawnerEntity")) {
			return true;
		}
		
		return false;
	}
	
    public boolean onBlockActivated(World worldIn, BlockPos pos, IBlockState state, EntityPlayer playerIn,
    		EnumHand hand, EnumFacing side, float hitX, float hitY, float hitZ) 
    {
        if (worldIn.isRemote)
        {
            return true;
        }
        else
        {
        	TileEntitySpawner tileentityfurnace = (TileEntitySpawner)worldIn.getTileEntity(pos);

            if (tileentityfurnace != null)
            {
                playerIn.openGui(ThinkBigCore.modInstance, AdInferosGuis.spawner.ID, worldIn, pos.getX(), pos.getY(), pos.getZ());
            }

            return true;
        }
    }
    
    public void breakBlock(World worldIn, BlockPos pos, IBlockState state)
    {
    	TileEntity tileentity = worldIn.getTileEntity(pos);

    	if (tileentity instanceof TileEntitySpawner)
    	{
    		TileEntitySpawner extractor = (TileEntitySpawner) tileentity;
    		InventoryHelper.dropInventoryItems(worldIn, pos, extractor);
    		
    		if(extractor.getField(0) > 0) {
    			ItemStack stack = new ItemStack(NetherItems.GOLDEN_BUCKET_BLOOD, 1, 1);
    			ItemBloodBucket.setBlood(stack, extractor.getField(0));
    			InventoryHelper.spawnItemStack(worldIn, extractor.getPos().getX(), extractor.getPos().getY(), extractor.getPos().getZ(), stack);
    		}
    		
    		worldIn.updateComparatorOutputLevel(pos, this);
    	}

        super.breakBlock(worldIn, pos, state);
    }
	
    public TileEntity createNewTileEntity(World worldIn, int meta)
    {
        return new TileEntitySpawner();
    }
    
    public Item getItemDropped(IBlockState state, Random rand, int fortune) {
    	return Item.getItemFromBlock(this);
    }
    
    public ItemStack getPickBlock(IBlockState state, RayTraceResult target, World world, BlockPos pos, EntityPlayer player)
    {
        return new ItemStack(this);
    }
    
    public int quantityDropped(Random random)
    {
        return 1;
    }
    
    public int getExpDrop(IBlockState state, net.minecraft.world.IBlockAccess world, BlockPos pos, int fortune)
    {
        return 0;
    }
    
    public IBlockState getStateFromMeta(int meta)
    {
        return this.getDefaultState().withProperty(CUSTOMIZABLE, Boolean.valueOf((meta & 1) == 0));
    }
    
    public int getMetaFromState(IBlockState state)
    {
        return ((Boolean)state.getValue(CUSTOMIZABLE)).booleanValue() ? 0 : 1;
    }

    protected BlockStateContainer createBlockState()
    {
        return new BlockStateContainer(this, new IProperty[] {CUSTOMIZABLE});
    }
}
