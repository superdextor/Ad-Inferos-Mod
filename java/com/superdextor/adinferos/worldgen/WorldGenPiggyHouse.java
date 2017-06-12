package com.superdextor.adinferos.worldgen;

import java.util.Random;

import com.superdextor.adinferos.init.NetherBlocks;
import com.superdextor.adinferos.init.NetherItems;
import com.superdextor.thinkbigcore.helpers.InventoryHelper;
import com.superdextor.thinkbigcore.helpers.InventoryHelper.LootEntry;
import com.superdextor.thinkbigcore.worldgen.IOverridable;
import com.superdextor.thinkbigcore.worldgen.WorldUtilities;

import net.minecraft.block.BlockSlab;
import net.minecraft.entity.EntityList;
import net.minecraft.entity.monster.EntityPigZombie;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.ItemDoor;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.tileentity.TileEntityChest;
import net.minecraft.tileentity.TileEntityMobSpawner;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraft.world.gen.feature.WorldGenerator;

public class WorldGenPiggyHouse extends WorldGenerator implements IOverridable {
	
	public boolean doOverride = false;
    
    public WorldGenPiggyHouse() {}
    
    public void doOverride() {
    	this.doOverride = true;
    }
     
    public boolean generate(World worldIn, Random random, BlockPos pos)
    {
        if (this.doOverride || worldIn.isAirBlock(pos.add(-2, 1, -2)) && worldIn.isAirBlock(pos.add(-2, 8, -2)) && worldIn.getBlockState(pos.add(-2, -1, -2)).isOpaqueCube() &&
        		worldIn.isAirBlock(pos.add(6, 1, -2)) && worldIn.isAirBlock(pos.add(6, 8, -2)) && worldIn.getBlockState(pos.add(6, -1, -2)).isOpaqueCube() &&
        		worldIn.isAirBlock(pos.add(6, 1, 8)) && worldIn.isAirBlock(pos.add(6, 8, 8)) && worldIn.getBlockState(pos.add(6, -1, 8)).isOpaqueCube() && 
        		worldIn.isAirBlock(pos.add(-2, 1, 8)) && worldIn.isAirBlock(pos.add(-2, 8, 8)) && worldIn.getBlockState(pos.add(-2, -1, 8)).isOpaqueCube())
        {
            
        	WorldUtilities.fill(worldIn, pos.add(0, 0, 0), NetherBlocks.SMOOTH_NETHERRACK, 7, 6, 9);
        	WorldUtilities.fill(worldIn, pos.add(1, 4, 1), NetherBlocks.STONE_SLAB.getDefaultState().withProperty(BlockSlab.HALF, BlockSlab.EnumBlockHalf.TOP), 5, 1, 7);
        	WorldUtilities.fill(worldIn, pos.add(1, 6, 0), NetherBlocks.SMOOTH_NETHERRACK, 5, 1, 9);
        	WorldUtilities.fill(worldIn, pos.add(1, 1, 1), Blocks.AIR, 5, 3, 7);
        	WorldUtilities.fill(worldIn, pos.add(1, 0, 1), NetherBlocks.PLANKS, 5, 1, 7);
        	WorldUtilities.fill(worldIn, pos.add(0, 0, 0), NetherBlocks.LOG, 1, 5, 1);
        	WorldUtilities.fill(worldIn, pos.add(6, 0, 0), NetherBlocks.LOG, 1, 5, 1);
        	WorldUtilities.fill(worldIn, pos.add(0, 0, 8), NetherBlocks.LOG, 1, 5, 1);
        	WorldUtilities.fill(worldIn, pos.add(6, 0, 8), NetherBlocks.LOG, 1, 5, 1);
        	WorldUtilities.fill(worldIn, pos.add(2, 2, 0), NetherBlocks.SOUL_GLASS_PANE, 3, 1, 1);
        	WorldUtilities.fill(worldIn, pos.add(0, 2, 2), NetherBlocks.SOUL_GLASS_PANE, 1, 1, 5);
        	WorldUtilities.fill(worldIn, pos.add(6, 2, 2), NetherBlocks.SOUL_GLASS_PANE, 1, 1, 3);
        	WorldUtilities.fill(worldIn, pos.add(2, 2, 8), NetherBlocks.SOUL_GLASS_PANE, 3, 1, 1);
        	WorldUtilities.fill(worldIn, pos.add(-1, 4, -1), NetherBlocks.INFERNO_STAIRS.getStateFromMeta(0), 1, 1, 11);
        	WorldUtilities.fill(worldIn, pos.add(0, 5, -1), NetherBlocks.INFERNO_STAIRS.getStateFromMeta(0), 1, 1, 11);
        	WorldUtilities.fill(worldIn, pos.add(1, 6, -1), NetherBlocks.INFERNO_STAIRS.getStateFromMeta(0), 1, 1, 11);
        	WorldUtilities.fill(worldIn, pos.add(2, 7, -1), NetherBlocks.WOODEN_SLAB, 3, 1, 11);
        	WorldUtilities.fill(worldIn, pos.add(7, 4, -1), NetherBlocks.INFERNO_STAIRS.getStateFromMeta(1), 1, 1, 11);
        	WorldUtilities.fill(worldIn, pos.add(6, 5, -1), NetherBlocks.INFERNO_STAIRS.getStateFromMeta(1), 1, 1, 11);
        	WorldUtilities.fill(worldIn, pos.add(5, 6, -1), NetherBlocks.INFERNO_STAIRS.getStateFromMeta(1), 1, 1, 11);
        	this.setBlockAndNotifyAdequately(worldIn, pos.add(0, 4, -1), NetherBlocks.INFERNO_STAIRS.getStateFromMeta(5));
        	this.setBlockAndNotifyAdequately(worldIn, pos.add(1, 5, -1), NetherBlocks.INFERNO_STAIRS.getStateFromMeta(5));
        	this.setBlockAndNotifyAdequately(worldIn, pos.add(2, 6, -1), NetherBlocks.INFERNO_STAIRS.getStateFromMeta(5));
        	this.setBlockAndNotifyAdequately(worldIn, pos.add(0, 4, 9), NetherBlocks.INFERNO_STAIRS.getStateFromMeta(5));
        	this.setBlockAndNotifyAdequately(worldIn, pos.add(1, 5, 9), NetherBlocks.INFERNO_STAIRS.getStateFromMeta(5));
        	this.setBlockAndNotifyAdequately(worldIn, pos.add(2, 6, 9), NetherBlocks.INFERNO_STAIRS.getStateFromMeta(5));
        	this.setBlockAndNotifyAdequately(worldIn, pos.add(3, 6, -1), NetherBlocks.WOODEN_SLAB.getDefaultState().withProperty(BlockSlab.HALF, BlockSlab.EnumBlockHalf.TOP));
        	this.setBlockAndNotifyAdequately(worldIn, pos.add(3, 6, 9), NetherBlocks.WOODEN_SLAB.getDefaultState().withProperty(BlockSlab.HALF, BlockSlab.EnumBlockHalf.TOP));
        	this.setBlockAndNotifyAdequately(worldIn, pos.add(6, 4, -1), NetherBlocks.INFERNO_STAIRS.getStateFromMeta(4));
        	this.setBlockAndNotifyAdequately(worldIn, pos.add(5, 5, -1), NetherBlocks.INFERNO_STAIRS.getStateFromMeta(4));
        	this.setBlockAndNotifyAdequately(worldIn, pos.add(4, 6, -1), NetherBlocks.INFERNO_STAIRS.getStateFromMeta(4));
        	this.setBlockAndNotifyAdequately(worldIn, pos.add(6, 4, 9), NetherBlocks.INFERNO_STAIRS.getStateFromMeta(4));
        	this.setBlockAndNotifyAdequately(worldIn, pos.add(5, 5, 9), NetherBlocks.INFERNO_STAIRS.getStateFromMeta(4));
        	this.setBlockAndNotifyAdequately(worldIn, pos.add(4, 6, 9), NetherBlocks.INFERNO_STAIRS.getStateFromMeta(4));
        	
        	this.setBlockAndNotifyAdequately(worldIn, pos.add(6, 0, 6), NetherBlocks.PLANKS.getDefaultState());
        	this.setBlockAndNotifyAdequately(worldIn, pos.add(6, 1, 6), Blocks.AIR.getDefaultState());
        	this.setBlockAndNotifyAdequately(worldIn, pos.add(6, 2, 6), Blocks.AIR.getDefaultState());
        	
            if(worldIn.getBlockState(pos.add(7, 0, 5)).getBlock() == Blocks.AIR) {
                this.setBlockAndNotifyAdequately(worldIn, pos.add(7, 0, 5), NetherBlocks.INFERNO_STAIRS.getStateFromMeta(2));
            }
            if(worldIn.getBlockState(pos.add(7, 0, 6)).getBlock() == Blocks.AIR) {
            	this.setBlockAndNotifyAdequately(worldIn, pos.add(7, 0, 6), NetherBlocks.INFERNO_STAIRS.getStateFromMeta(1));
            }
            if(worldIn.getBlockState(pos.add(7, 0, 7)).getBlock() == Blocks.AIR) {
            	this.setBlockAndNotifyAdequately(worldIn, pos.add(7, 0, 7), NetherBlocks.INFERNO_STAIRS.getStateFromMeta(3));
            }
            
            ItemDoor.placeDoor(worldIn, pos.add(6, 1, 6), EnumFacing.WEST, NetherBlocks.INFERNO_DOOR, false);
            worldIn.setBlockState(pos.add(7, 2, 7), NetherBlocks.GLOWSTONE_TORCH.getStateFromMeta(1));
            
            WorldUtilities.fill(worldIn, pos.add(1, 1, 1), Blocks.SOUL_SAND.getDefaultState(), 2, 1, 3);
            worldIn.setBlockState(pos.add(1, 2, 1), Blocks.STONE_SLAB.getStateFromMeta(7));
            worldIn.setBlockState(pos.add(2, 2, 1), Blocks.STONE_SLAB.getStateFromMeta(7));
            
            worldIn.setBlockState(pos.add(3, 1, 7), NetherBlocks.MAGMA_FENCE.getDefaultState());
            worldIn.setBlockState(pos.add(3, 2, 7), Blocks.CARPET.getStateFromMeta(1));
            worldIn.setBlockState(pos.add(4, 1, 7), NetherBlocks.MAGMA_STAIRS.getStateFromMeta(0));
            worldIn.setBlockState(pos.add(2, 1, 7), NetherBlocks.MAGMA_STAIRS.getStateFromMeta(1));
            
            worldIn.setBlockState(pos.add(1, 2, -1), NetherBlocks.INFERNO_TRAPDOOR_HIDDEN.getStateFromMeta(4));
            worldIn.setBlockState(pos.add(5, 2, -1), NetherBlocks.INFERNO_TRAPDOOR_HIDDEN.getStateFromMeta(4));
            worldIn.setBlockState(pos.add(1, 2, 9), NetherBlocks.INFERNO_TRAPDOOR_HIDDEN.getStateFromMeta(5));
            worldIn.setBlockState(pos.add(5, 2, 9), NetherBlocks.INFERNO_TRAPDOOR_HIDDEN.getStateFromMeta(5));
            worldIn.setBlockState(pos.add(-1, 2, 1), NetherBlocks.INFERNO_TRAPDOOR_HIDDEN.getStateFromMeta(6));
            worldIn.setBlockState(pos.add(-1, 2, 7), NetherBlocks.INFERNO_TRAPDOOR_HIDDEN.getStateFromMeta(6));
            worldIn.setBlockState(pos.add(7, 2, 1), NetherBlocks.INFERNO_TRAPDOOR_HIDDEN.getStateFromMeta(7));
            worldIn.setBlockState(pos.add(7, 2, 5), NetherBlocks.INFERNO_TRAPDOOR_HIDDEN.getStateFromMeta(7));
            
        	BlockPos chestpos = pos.add(5, -1, 1);
            
            worldIn.setBlockState(chestpos, Blocks.CHEST.correctFacing(worldIn, chestpos, Blocks.CHEST.getDefaultState()), 2);
            TileEntity tileentity1 = worldIn.getTileEntity(chestpos);

            if (tileentity1 instanceof TileEntityChest)
            {
            	TileEntityChest chest = (TileEntityChest) tileentity1;
            	LootEntry[] loot = {new LootEntry(Items.GOLD_NUGGET, 5, 9, 0.6F), new LootEntry(Items.GOLD_INGOT, 2, 5, 0.4F), new LootEntry(Items.GOLDEN_APPLE, 1, 1, 0.7F), new LootEntry(Items.CARROT, 2, 3, 0.7F), new LootEntry(NetherItems.GOLDEN_BUCKET_BLOOD, 1, 1, 0.3F), new LootEntry(Items.QUARTZ, 3, 7, 0.5F)};
            	LootEntry[] junk = {new LootEntry(Items.BONE, 3, 8, 0.8F), new LootEntry(Items.GUNPOWDER, 2, 5, 0.4F), new LootEntry(Items.ROTTEN_FLESH, 3, 9, 0.5F)};
            	InventoryHelper.addLootToChest(random, chest, 1 + random.nextInt(5), loot);
            	InventoryHelper.addLootToChest(random, chest, 3, junk);
            }
            
        	BlockPos spawnerpos = pos.add(2, 0, 3);
            worldIn.setBlockState(spawnerpos, Blocks.MOB_SPAWNER.getDefaultState());
            TileEntity tileentityspawner = worldIn.getTileEntity(spawnerpos);

            if (tileentityspawner instanceof TileEntityMobSpawner)
            {
                ((TileEntityMobSpawner)tileentityspawner).getSpawnerBaseLogic().func_190894_a(EntityList.func_191306_a(EntityPigZombie.class));
            }
        	
            this.doOverride = false;
            return true;
        }
        else
        {
            return false;
        }
    }

}
