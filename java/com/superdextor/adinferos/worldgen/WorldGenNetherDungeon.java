package com.superdextor.adinferos.worldgen;

import java.util.Random;

import com.superdextor.adinferos.config.NetherConfig;
import com.superdextor.adinferos.entity.monster.EntityBlackWidow;
import com.superdextor.adinferos.entity.monster.EntityGlowstoneSkeleton;
import com.superdextor.adinferos.init.NetherBlocks;
import com.superdextor.adinferos.init.NetherItems;
import com.superdextor.thinkbigcore.helpers.InventoryHelper;
import com.superdextor.thinkbigcore.helpers.InventoryHelper.LootEntry;
import com.superdextor.thinkbigcore.worldgen.IOverridable;

import net.minecraft.block.material.Material;
import net.minecraft.entity.EntityList;
import net.minecraft.entity.monster.EntityBlaze;
import net.minecraft.entity.monster.EntityPigZombie;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.tileentity.TileEntityChest;
import net.minecraft.tileentity.TileEntityMobSpawner;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraft.world.gen.feature.WorldGenerator;

public class WorldGenNetherDungeon extends WorldGenerator implements IOverridable
{
	public boolean doOverride = false;
	
	public void doOverride() {
		this.doOverride = true;
	}
	
    public boolean generate(World worldIn, Random rand, BlockPos position)
    {
        int i = 3;
        int j = rand.nextInt(2) + 2;
        int k = -j - 1;
        int l = j + 1;
        int i1 = -1;
        int j1 = 4;
        int k1 = rand.nextInt(2) + 2;
        int l1 = -k1 - 1;
        int i2 = k1 + 1;
        int j2 = 0;
        
        if(NetherConfig.safeWorldGen && !worldIn.isAreaLoaded(position.add(k - 1, -1, l1 - 1), position.add(l + 1, 6, i2 + 1))) {
        	return false;
        }

        for (int k2 = k; k2 <= l; ++k2)
        {
            for (int l2 = -1; l2 <= 4; ++l2)
            {
                for (int i3 = l1; i3 <= i2; ++i3)
                {
                    BlockPos blockpos = position.add(k2, l2, i3);
                    Material material = worldIn.getBlockState(blockpos).getMaterial();
                    boolean flag = material.isSolid();

                    if (!this.doOverride && l2 == -1 && !flag)
                    {
                        return false;
                    }

                    if (!this.doOverride && l2 == 4 && !flag)
                    {
                        return false;
                    }

                    if ((k2 == k || k2 == l || i3 == l1 || i3 == i2) && l2 == 0 && worldIn.isAirBlock(blockpos) && worldIn.isAirBlock(blockpos.up()))
                    {
                        ++j2;
                    }
                }
            }
        }

        if (this.doOverride || j2 >= 1)
        {
            for (int k3 = k; k3 <= l; ++k3)
            {
                for (int i4 = 3; i4 >= -1; --i4)
                {
                    for (int k4 = l1; k4 <= i2; ++k4)
                    {
                        BlockPos blockpos1 = position.add(k3, i4, k4);

                        if (k3 != k && i4 != -1 && k4 != l1 && k3 != l && i4 != 4 && k4 != i2)
                        {
                            if (worldIn.getBlockState(blockpos1).getBlock() != Blocks.CHEST)
                            {
                                worldIn.setBlockToAir(blockpos1);
                            }
                        }
                        else if (blockpos1.getY() >= 0 && !worldIn.getBlockState(blockpos1.down()).getMaterial().isSolid())
                        {
                            worldIn.setBlockToAir(blockpos1);
                        }
                        else if (worldIn.getBlockState(blockpos1).getMaterial().isSolid() && worldIn.getBlockState(blockpos1).getBlock() != Blocks.CHEST)
                        {
                            if (i4 == -1 && rand.nextInt(4) != 0)
                            {
                                worldIn.setBlockState(blockpos1, NetherBlocks.SMOOTH_NETHERRACK.getDefaultState(), 2);
                            }
                            else
                            {
                                worldIn.setBlockState(blockpos1, NetherBlocks.SMOOTH_NETHERRACK.getDefaultState(), 2);
                            }
                        }
                    }
                }
            }

            for (int l3 = 0; l3 < 2; ++l3)
            {
                for (int j4 = 0; j4 < 3; ++j4)
                {
                    int l4 = position.getX() + rand.nextInt(j * 2 + 1) - j;
                    int i5 = position.getY();
                    int j5 = position.getZ() + rand.nextInt(k1 * 2 + 1) - k1;
                    BlockPos blockpos2 = new BlockPos(l4, i5, j5);

                    if (worldIn.isAirBlock(blockpos2))
                    {
                        int j3 = 0;

                        for (EnumFacing enumfacing : EnumFacing.Plane.HORIZONTAL)
                        {
                            if (worldIn.getBlockState(blockpos2.offset(enumfacing)).getMaterial().isSolid())
                            {
                                ++j3;
                            }
                        }

                        if (j3 == 1)
                        {
                            worldIn.setBlockState(blockpos2, Blocks.CHEST.correctFacing(worldIn, blockpos2, Blocks.CHEST.getDefaultState()), 2);
                            TileEntity tileentity1 = worldIn.getTileEntity(blockpos2);

                            if (tileentity1 instanceof TileEntityChest)
                            {
                            	TileEntityChest chest = (TileEntityChest) tileentity1;
                            	LootEntry[] rareLoot = {new LootEntry(Items.GOLDEN_APPLE, 1, 3, 0.4F), new LootEntry(NetherItems.WITHER_APPLE, 1, 3, 0.3F), new LootEntry(NetherItems.RECORD_FLAMES, 1, 1, 0.5F), new LootEntry(Items.NAME_TAG, 1, 1, 0.2F)};
                            	LootEntry[] loot = {new LootEntry(Items.GOLD_INGOT, 1, 3, 0.6F), new LootEntry(NetherItems.OBSIDIAN_NUGGET, 3, 7, 0.3F), new LootEntry(NetherItems.COOKED_FLESH, 3, 9, 0.7F), new LootEntry(NetherItems.WITHER_DUST, 3, 9, 0.7F), new LootEntry(NetherItems.GOLDEN_BUCKET, 1, 1, 0.3F), new LootEntry(Items.QUARTZ, 3, 7, 0.5F)};
                            	LootEntry[] junk = {new LootEntry(Items.BONE, 3, 8, 0.8F), new LootEntry(Items.GUNPOWDER, 2, 5, 0.4F), new LootEntry(NetherItems.INFERNAL_FEATHER, 3, 9, 0.5F), new LootEntry(NetherItems.INFERNAL_STRING, 4, 7, 0.5F)};
                            	InventoryHelper.addLootToChest(rand, chest, 2 + rand.nextInt(2), rareLoot);
                            	InventoryHelper.addLootToChest(rand, chest, 1 + rand.nextInt(5), loot);
                            	InventoryHelper.addLootToChest(rand, chest, 3, junk);
                            }

                            break;
                        }
                    }
                }
            }

            worldIn.setBlockState(position, Blocks.MOB_SPAWNER.getDefaultState(), 2);
            TileEntity tileentity = worldIn.getTileEntity(position);

            if (tileentity instanceof TileEntityMobSpawner)
            {
                ((TileEntityMobSpawner)tileentity).getSpawnerBaseLogic().func_190894_a(this.pickMobSpawner(rand));
            }

            this.doOverride = false;
            return true;
        }
        else
        {
            return false;
        }
    }

    private ResourceLocation pickMobSpawner(Random rand)
    {
    	switch(rand.nextInt(4)) {
    		default: {
    			return EntityList.func_191306_a(EntityBlackWidow.class);
    		}
    		
    		case 1: {
    			return EntityList.func_191306_a(EntityGlowstoneSkeleton.class);
    		}
    		
    		case 2: {
    			return EntityList.func_191306_a(EntityBlaze.class);
    		}
    		
    		case 3: {
    			return EntityList.func_191306_a(EntityPigZombie.class);
    		}
    	}
    }
}