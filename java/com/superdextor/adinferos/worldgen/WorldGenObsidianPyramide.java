package com.superdextor.adinferos.worldgen;

import java.util.Random;

import com.superdextor.adinferos.config.NetherConfig;
import com.superdextor.adinferos.init.NetherBlocks;
import com.superdextor.adinferos.init.NetherItems;
import com.superdextor.thinkbigcore.helpers.InventoryHelper;
import com.superdextor.thinkbigcore.helpers.InventoryHelper.LootEntry;
import com.superdextor.thinkbigcore.worldgen.IOverridable;
import com.superdextor.thinkbigcore.worldgen.WorldUtilities;

import net.minecraft.block.state.IBlockState;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.tileentity.TileEntityChest;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraft.world.gen.feature.WorldGenerator;

public class WorldGenObsidianPyramide extends WorldGenerator implements IOverridable {
	
	public boolean doOverride = false;
    
    public WorldGenObsidianPyramide() {}

    public void doOverride() {
    	this.doOverride = true;
    }
    
	public boolean generate(World worldIn, Random random, BlockPos pos)
    {
        if (this.doOverride || (NetherConfig.safeWorldGen ? worldIn.isAreaLoaded(pos, pos.add(23, 0, 23)) : true) && worldIn.getBlockState(pos).isFullBlock() && worldIn.getBlockState(pos.add(23, 0, 0)).isFullBlock() && worldIn.getBlockState(pos.add(23, 0, 23)).isFullBlock() && worldIn.getBlockState(pos.add(0, 0, 23)).isFullBlock() &&
        		worldIn.getBlockState(pos.add(6, 7, 6)).isFullBlock() && worldIn.getBlockState(pos.add(6, 7, 15)).isFullBlock() && worldIn.getBlockState(pos.add(15, 7, 15)).isFullBlock() && worldIn.getBlockState(pos.add(15, 6, 7)).isFullBlock() &&
        		(worldIn.isAirBlock(pos.add(11, 11, 11)) || worldIn.isAirBlock(pos.add(11, 7, 15))))
        {
            
        	//Creates the Basic Pyramide
        	WorldUtilities.fill(worldIn, pos.add(0, 0, 0), Blocks.OBSIDIAN, 23, 1, 23);
        	WorldUtilities.fill(worldIn, pos.add(0, 1, 0), Blocks.OBSIDIAN, 23, 1, 23);
        	WorldUtilities.fill(worldIn, pos.add(1, 2, 1), Blocks.OBSIDIAN, 21, 1, 21);
        	WorldUtilities.fill(worldIn, pos.add(2, 3, 2), Blocks.OBSIDIAN, 19, 1, 19);
        	WorldUtilities.fill(worldIn, pos.add(3, 4, 3), Blocks.OBSIDIAN, 17, 1, 17);
        	WorldUtilities.fill(worldIn, pos.add(4, 5, 4), Blocks.OBSIDIAN, 15, 1, 15);
        	WorldUtilities.fill(worldIn, pos.add(5, 6, 5), Blocks.OBSIDIAN, 13, 1, 13);
        	WorldUtilities.fill(worldIn, pos.add(6, 7, 6), Blocks.OBSIDIAN, 11, 1, 11);
        	WorldUtilities.fill(worldIn, pos.add(7, 8, 7), Blocks.OBSIDIAN, 9, 1, 9);
        	WorldUtilities.fill(worldIn, pos.add(8, 9, 8), Blocks.OBSIDIAN, 7, 1, 7);
        	WorldUtilities.fill(worldIn, pos.add(9, 10, 9), Blocks.OBSIDIAN, 5, 1, 5);
        	WorldUtilities.fill(worldIn, pos.add(10, 11, 10), Blocks.OBSIDIAN, 3, 1, 3);
        	
        	//Hollows out the Pyramide
        	WorldUtilities.fill(worldIn, pos.add(1, 1, 1), Blocks.AIR, 21, 1, 21);
        	WorldUtilities.fill(worldIn, pos.add(2, 2, 2), Blocks.AIR, 19, 1, 19);
        	WorldUtilities.fill(worldIn, pos.add(3, 3, 3), Blocks.AIR, 17, 1, 17);
        	WorldUtilities.fill(worldIn, pos.add(4, 4, 4), Blocks.AIR, 15, 1, 15);
        	WorldUtilities.fill(worldIn, pos.add(5, 5, 5), Blocks.AIR, 13, 1, 13);
        	WorldUtilities.fill(worldIn, pos.add(10, 6, 10), Blocks.AIR, 3, 1, 3);
        	WorldUtilities.fill(worldIn, pos.add(7, 7, 7), Blocks.AIR, 9, 1, 9);
        	WorldUtilities.fill(worldIn, pos.add(8, 8, 8), Blocks.AIR, 7, 1, 7);
        	WorldUtilities.fill(worldIn, pos.add(9, 9, 9), Blocks.AIR, 5, 1, 5);
        	WorldUtilities.fill(worldIn, pos.add(10, 10, 10), Blocks.AIR, 3, 1, 3);
        	this.setBlockAndNotifyAdequately(worldIn, pos.add(11, 11, 11), Blocks.AIR.getDefaultState());
        	
        	IBlockState piller = NetherBlocks.SMOOTH_OBSIDIAN.getDefaultState();
        	
        	WorldUtilities.fill(worldIn, pos.add(9, 1, 9), piller, 1, 5, 1);
        	WorldUtilities.fill(worldIn, pos.add(9, 1, 13), piller, 1, 5, 1);
        	WorldUtilities.fill(worldIn, pos.add(13, 1, 13), piller, 1, 5, 1);
        	WorldUtilities.fill(worldIn, pos.add(13, 1, 9), piller, 1, 5, 1);
        	
        	WorldUtilities.fill(worldIn, pos.add(3, 1, 3), piller, 1, 3, 1);
        	WorldUtilities.fill(worldIn, pos.add(5, 1, 3), piller, 1, 3, 1);
        	WorldUtilities.fill(worldIn, pos.add(7, 1, 3), piller, 1, 3, 1);
        	WorldUtilities.fill(worldIn, pos.add(9, 1, 3), piller, 1, 3, 1);
        	WorldUtilities.fill(worldIn, pos.add(11, 1, 3), piller, 1, 3, 1);
        	WorldUtilities.fill(worldIn, pos.add(13, 1, 3), piller, 1, 3, 1);
        	WorldUtilities.fill(worldIn, pos.add(15, 1, 3), piller, 1, 3, 1);
        	WorldUtilities.fill(worldIn, pos.add(17, 1, 3), piller, 1, 3, 1);
        	WorldUtilities.fill(worldIn, pos.add(19, 1, 3), piller, 1, 3, 1);
        	
        	WorldUtilities.fill(worldIn, pos.add(3, 1, 19), piller, 1, 3, 1);
        	WorldUtilities.fill(worldIn, pos.add(5, 1, 19), piller, 1, 3, 1);
        	WorldUtilities.fill(worldIn, pos.add(7, 1, 19), piller, 1, 3, 1);
        	WorldUtilities.fill(worldIn, pos.add(9, 1, 19), piller, 1, 3, 1);
        	WorldUtilities.fill(worldIn, pos.add(11, 1, 19), piller, 1, 3, 1);
        	WorldUtilities.fill(worldIn, pos.add(13, 1, 19), piller, 1, 3, 1);
        	WorldUtilities.fill(worldIn, pos.add(15, 1, 19), piller, 1, 3, 1);
        	WorldUtilities.fill(worldIn, pos.add(17, 1, 19), piller, 1, 3, 1);
        	WorldUtilities.fill(worldIn, pos.add(19, 1, 19), piller, 1, 3, 1);
        	
        	WorldUtilities.fill(worldIn, pos.add(3, 1, 3), piller, 1, 3, 1);
        	WorldUtilities.fill(worldIn, pos.add(3, 1, 5), piller, 1, 3, 1);
        	WorldUtilities.fill(worldIn, pos.add(3, 1, 7), piller, 1, 3, 1);
        	WorldUtilities.fill(worldIn, pos.add(3, 1, 9), piller, 1, 3, 1);
        	WorldUtilities.fill(worldIn, pos.add(3, 1, 11), piller, 1, 3, 1);
        	WorldUtilities.fill(worldIn, pos.add(3, 1, 13), piller, 1, 3, 1);
        	WorldUtilities.fill(worldIn, pos.add(3, 1, 15), piller, 1, 3, 1);
        	WorldUtilities.fill(worldIn, pos.add(3, 1, 17), piller, 1, 3, 1);
        	WorldUtilities.fill(worldIn, pos.add(3, 1, 19), piller, 1, 3, 1);
        	
        	WorldUtilities.fill(worldIn, pos.add(19, 1, 3), piller, 1, 3, 1);
        	WorldUtilities.fill(worldIn, pos.add(19, 1, 5), piller, 1, 3, 1);
        	WorldUtilities.fill(worldIn, pos.add(19, 1, 7), piller, 1, 3, 1);
        	WorldUtilities.fill(worldIn, pos.add(19, 1, 9), piller, 1, 3, 1);
        	WorldUtilities.fill(worldIn, pos.add(19, 1, 11), piller, 1, 3, 1);
        	WorldUtilities.fill(worldIn, pos.add(19, 1, 13), piller, 1, 3, 1);
        	WorldUtilities.fill(worldIn, pos.add(19, 1, 15), piller, 1, 3, 1);
        	WorldUtilities.fill(worldIn, pos.add(19, 1, 17), piller, 1, 3, 1);
        	WorldUtilities.fill(worldIn, pos.add(19, 1, 19), piller, 1, 3, 1);
        	
        	//Makes the entrance
        	WorldUtilities.fill(worldIn, pos.add(6, 7, 10), Blocks.OBSIDIAN, 2, 3, 3);
        	WorldUtilities.fill(worldIn, pos.add(6, 7, 11), Blocks.AIR, 2, 2, 1);
        	
        	//Adds random chest in corner in main room.
        	BlockPos chestPos = pos.add(2, -1, 2);
        	
        	switch(random.nextInt(4)) {
	        	case 1: {
	        		chestPos = pos.add(20, -1, 2);
	        	}
	        	
	        	case 2: {
	        		chestPos = pos.add(20, -1, 20);
	        		break;
	        	}
	        	
	        	case 3: {
	        		chestPos = pos.add(2, -1, 20);
	        		break;
	        	}
	        	
	        	default: {
	        		break;
	        	}
        	}
        	
        	worldIn.setBlockState(chestPos.up(), NetherBlocks.ASH_TRAPDOOR_HIDDEN.getStateFromMeta(8));
            worldIn.setBlockState(chestPos, Blocks.CHEST.correctFacing(worldIn, chestPos, Blocks.CHEST.getDefaultState()), 2);
            TileEntity tileentity1 = worldIn.getTileEntity(chestPos);

            if (tileentity1 instanceof TileEntityChest)
            {
            	TileEntityChest chest = (TileEntityChest) tileentity1;
            	LootEntry[] rareLoot = {new LootEntry(Items.GOLDEN_APPLE, 1, 1, 0.5F), new LootEntry(new ItemStack(Items.GOLDEN_APPLE, 1, 1), 1, 1, 0.2F), new LootEntry(NetherItems.RECORD_AWAKENED, 1, 1, 0.4F), new LootEntry(NetherItems.GOLDEN_BUCKET_BLOOD, 1, 1, 0.6F), new LootEntry(NetherItems.NETHERITE_NUGGET, 3, 8, 0.3F)};
            	LootEntry[] loot = {new LootEntry(NetherItems.OBSIDIAN_INGOT, 2, 5, 0.6F), new LootEntry(Items.GOLD_INGOT, 3, 7, 0.5F), new LootEntry(NetherItems.COOKED_FLESH, 3, 9, 0.7F), new LootEntry(NetherItems.WITHER_DUST, 3, 9, 0.7F), new LootEntry(NetherItems.GOLDEN_BUCKET, 1, 1, 0.3F), new LootEntry(Blocks.OBSIDIAN, 3, 7, 0.5F)};
            	LootEntry[] junk = {new LootEntry(Items.BONE, 3, 8, 0.8F), new LootEntry(Items.GUNPOWDER, 2, 5, 0.4F), new LootEntry(Items.ROTTEN_FLESH, 3, 9, 0.5F), new LootEntry(NetherItems.INFERNAL_STRING, 4, 7, 0.5F)};
            	InventoryHelper.addLootToChest(random, chest, 2 + random.nextInt(2), rareLoot);
            	InventoryHelper.addLootToChest(random, chest, 1 + random.nextInt(5), loot);
            	InventoryHelper.addLootToChest(random, chest, 3, junk);
            }
            
            this.setBlockAndNotifyAdequately(worldIn, pos.add(11, 1, 11), NetherBlocks.ALTAR.getStateFromMeta(1));
            
            this.setBlockAndNotifyAdequately(worldIn, pos.add(11, 0, 9), Blocks.SOUL_SAND.getDefaultState());
            this.setBlockAndNotifyAdequately(worldIn, pos.add(9, 0, 11), Blocks.SOUL_SAND.getDefaultState());
            this.setBlockAndNotifyAdequately(worldIn, pos.add(13, 0, 11), Blocks.SOUL_SAND.getDefaultState());
            this.setBlockAndNotifyAdequately(worldIn, pos.add(11, 0, 13), Blocks.SOUL_SAND.getDefaultState());
        	
        	this.doOverride = false;
        	
            return true;
        }
        else
        {
            return false;
        }
    }

}
