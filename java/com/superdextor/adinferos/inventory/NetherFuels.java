package com.superdextor.adinferos.inventory;

import com.superdextor.adinferos.config.NetherConfig;
import com.superdextor.adinferos.init.NetherBlocks;
import com.superdextor.adinferos.init.NetherItems;

import net.minecraft.block.Block;
import net.minecraft.init.Blocks;
import net.minecraft.item.Item;
import net.minecraft.item.ItemBlock;
import net.minecraft.item.ItemStack;
import net.minecraftforge.fml.common.IFuelHandler;

public class NetherFuels implements IFuelHandler {
	
	public NetherFuels() {
		NetherConfig.printDebugInfo("Registered NetherFuels");
	}

	public int getBurnTime(ItemStack stack) {
		
		if(stack.func_190926_b()) {
			return 0;
		}
		else {
            Item item = stack.getItem();
            
            if (item instanceof ItemBlock && Block.getBlockFromItem(item) != Blocks.AIR)
            {
                Block block = Block.getBlockFromItem(item);
                
                if (block == NetherBlocks.LOG) return 400;
                if (block == NetherBlocks.PLANKS) return 400;
                if (block == NetherBlocks.INFERNO_STAIRS) return 400;
                if (block == NetherBlocks.MAGMA_STAIRS) return 400;
                if (block == NetherBlocks.PHANTOM_STAIRS) return 400;
                if (block == NetherBlocks.ASH_STAIRS) return 400;
                if (block == NetherBlocks.WOODEN_SLAB) return 150;
                if (block == NetherBlocks.SAPLING) return 150;
                if (block == NetherBlocks.INFERNO_FENCE) return 400;
                if (block == NetherBlocks.MAGMA_FENCE) return 400;
                if (block == NetherBlocks.PHANTOM_FENCE) return 400;
                if (block == NetherBlocks.ASH_FENCE) return 400;
                if (block == NetherBlocks.INFERNO_FENCE_GATE) return 400;
                if (block == NetherBlocks.MAGMA_FENCE_GATE) return 400;
                if (block == NetherBlocks.PHANTOM_FENCE_GATE) return 400;
                if (block == NetherBlocks.ASH_FENCE_GATE) return 400;
                if (block == NetherBlocks.INFERNO_TRAPDOOR) return 400;
                if (block == NetherBlocks.MAGMA_TRAPDOOR) return 400;
                if (block == NetherBlocks.PHANTOM_TRAPDOOR) return 400;
                if (block == NetherBlocks.ASH_TRAPDOOR) return 400;
                if (block == NetherBlocks.INFERNO_TRAPDOOR_HIDDEN) return 400;
                if (block == NetherBlocks.MAGMA_TRAPDOOR_HIDDEN) return 400;
                if (block == NetherBlocks.PHANTOM_TRAPDOOR_HIDDEN) return 400;
                if (block == NetherBlocks.ASH_TRAPDOOR_HIDDEN) return 400;
                if (block == NetherBlocks.INFERNO_PRESSURE_PLATE) return 300;
                if (block == NetherBlocks.MAGMA_PRESSURE_PLATE) return 300;
                if (block == NetherBlocks.PHANTOM_PRESSURE_PLATE) return 300;
                if (block == NetherBlocks.ASH_PRESSURE_PLATE) return 300;
                if (block == NetherBlocks.CRAFTING_TABLE) return 400;
                if (block == NetherBlocks.PEAT_BLOCK) return 16000;
            }
            
            if (item == NetherItems.GOLDEN_BUCKET_LAVA) return 20000;
            if (item == NetherItems.INFERNO_DOOR) return 800;
            if (item == NetherItems.MAGMA_DOOR) return 800;
            if (item == NetherItems.PHANTOM_DOOR) return 800;
            if (item == NetherItems.ASH_DOOR) return 800;
            if (item == NetherItems.INFERNAL_FEATHER) return 800;
            if (item == NetherItems.INFERNAL_STRING) return 800;
            if (item == NetherItems.PEAT) return 1600;
            
		}
		
		return 0;
		
	}

}
