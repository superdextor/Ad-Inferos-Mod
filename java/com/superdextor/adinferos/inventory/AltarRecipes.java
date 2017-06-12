package com.superdextor.adinferos.inventory;

import java.util.ArrayList;

import com.superdextor.adinferos.config.NetherConfig;
import com.superdextor.adinferos.init.NetherBlocks;
import com.superdextor.adinferos.init.NetherItems;

import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.NonNullList;

public class AltarRecipes
{
    public static final ArrayList<AltarRecipes.AltarRecipe> RECIPES = new ArrayList<AltarRecipes.AltarRecipe>();
		
    public static void register()
    {
        addRecipe(Items.NETHERBRICK, NetherItems.WITHER_DUST, Item.getItemFromBlock(Blocks.OBSIDIAN), NetherItems.WITHER_DUST, NetherItems.WITHER_DUST, Item.getItemFromBlock(Blocks.OBSIDIAN), NetherItems.WITHER_DUST, NetherItems.DARK_BRICK, 0, 1.0F);
        addRecipe(new ItemStack(Blocks.NETHER_BRICK), new ItemStack(NetherBlocks.WITHER_BLOCK), new ItemStack(Blocks.OBSIDIAN), new ItemStack(NetherBlocks.WITHER_BLOCK), new ItemStack(NetherBlocks.WITHER_BLOCK), new ItemStack(Blocks.OBSIDIAN), new ItemStack(NetherBlocks.WITHER_BLOCK), new ItemStack(NetherBlocks.DARK_BRICKS), 0, 0.4F);
        addRecipe(new ItemStack(Items.GLOWSTONE_DUST), null, new ItemStack(NetherItems.ECTOPLASM), null, null, new ItemStack(NetherItems.ECTOPLASM), null, new ItemStack(NetherBlocks.HIDDEN_LIGHT, 4), 400, 0.9F);
        addRecipe(new ItemStack(Blocks.NETHERRACK), null, new ItemStack(NetherItems.ECTOPLASM), null, null, new ItemStack(NetherItems.ECTOPLASM), null, new ItemStack(NetherBlocks.HIDDEN_BLOCK, 4), 400, 0.9F);
        addRecipe(new ItemStack(Blocks.STONE), null, new ItemStack(NetherItems.ECTOPLASM), null, null, new ItemStack(NetherItems.ECTOPLASM), null, new ItemStack(NetherBlocks.HIDDEN_BLOCK, 4), 400, 0.9F);
        addRecipe(Items.ENDER_PEARL, NetherItems.DIMENSIONAL_DUST, NetherItems.NETHERITE_INGOT, NetherItems.DIMENSIONAL_DUST, NetherItems.DIMENSIONAL_DUST, NetherItems.NETHERITE_INGOT, NetherItems.DIMENSIONAL_DUST, NetherItems.POCKET_WORMHOLE, 2000, 0.2F);
        addRecipe(Items.DIAMOND, NetherItems.WITHER_DUST, NetherItems.WITHER_DUST, NetherItems.WITHER_DUST, NetherItems.WITHER_DUST, NetherItems.WITHER_DUST, NetherItems.WITHER_DUST, NetherItems.WITHER_GEM, 300, 0.6F);
        addRecipe(Items.APPLE, NetherItems.WITHER_DUST, NetherItems.WITHER_DUST, NetherItems.WITHER_DUST, NetherItems.WITHER_DUST, NetherItems.WITHER_DUST, NetherItems.WITHER_DUST, NetherItems.WITHER_APPLE, 120, 0.5F);
        addRecipe(new ItemStack(NetherItems.GOLDEN_BUCKET_LAVA), new ItemStack(NetherItems.DIMENSIONAL_DUST), new ItemStack(NetherItems.NETHERITE_INGOT), new ItemStack(NetherItems.DIMENSIONAL_DUST), new ItemStack(NetherItems.DIMENSIONAL_DUST), new ItemStack(NetherItems.NETHERITE_INGOT), new ItemStack(NetherItems.DIMENSIONAL_DUST), new ItemStack(NetherItems.AMULET, 1, 0), 1200, 0.2F);
        addRecipe(new ItemStack(NetherItems.WITHER_GEM), new ItemStack(NetherItems.DIMENSIONAL_DUST), new ItemStack(NetherItems.NETHERITE_INGOT), new ItemStack(NetherItems.DIMENSIONAL_DUST), new ItemStack(NetherItems.DIMENSIONAL_DUST), new ItemStack(NetherItems.NETHERITE_INGOT), new ItemStack(NetherItems.DIMENSIONAL_DUST), new ItemStack(NetherItems.AMULET, 1, 1), 1200, 0.2F);
        addRecipe(new ItemStack(NetherItems.QUARTZ_BOOTS), new ItemStack(NetherItems.DIMENSIONAL_DUST), new ItemStack(NetherItems.NETHERITE_INGOT), new ItemStack(NetherItems.DIMENSIONAL_DUST), new ItemStack(NetherItems.DIMENSIONAL_DUST), new ItemStack(NetherItems.NETHERITE_INGOT), new ItemStack(NetherItems.DIMENSIONAL_DUST), new ItemStack(NetherItems.AMULET, 1, 2), 1200, 0.2F);
        addRecipe(new ItemStack(Items.GOLDEN_APPLE), new ItemStack(NetherItems.DIMENSIONAL_DUST), new ItemStack(NetherItems.NETHERITE_INGOT), new ItemStack(NetherItems.DIMENSIONAL_DUST), new ItemStack(NetherItems.DIMENSIONAL_DUST), new ItemStack(NetherItems.NETHERITE_INGOT), new ItemStack(NetherItems.DIMENSIONAL_DUST), new ItemStack(NetherItems.AMULET, 1, 3), 1200, 0.2F);
        addRecipe(new ItemStack(NetherItems.GOLDEN_BUCKET_ACID), new ItemStack(NetherItems.DIMENSIONAL_DUST), new ItemStack(NetherItems.NETHERITE_INGOT), new ItemStack(NetherItems.DIMENSIONAL_DUST), new ItemStack(NetherItems.DIMENSIONAL_DUST), new ItemStack(NetherItems.NETHERITE_INGOT), new ItemStack(NetherItems.DIMENSIONAL_DUST), new ItemStack(NetherItems.AMULET, 1, 4), 1200, 0.2F);
        addRecipe(Items.FIRE_CHARGE, NetherItems.OBSIDIAN_INGOT, Items.BLAZE_POWDER, NetherItems.NETHERITE_INGOT, NetherItems.OBSIDIAN_INGOT, Items.BLAZE_POWDER, NetherItems.NETHERITE_INGOT, NetherItems.INFERNO_STAFF, 2000, 0.2F);
        addRecipe(new ItemStack(Items.SKULL, 1, 1), new ItemStack(NetherItems.WITHER_GEM), new ItemStack(NetherItems.DIMENSIONAL_DUST), new ItemStack(NetherItems.NETHERITE_INGOT), new ItemStack(NetherItems.WITHER_GEM), new ItemStack(NetherItems.DIMENSIONAL_DUST), new ItemStack(NetherItems.NETHERITE_INGOT), new ItemStack(NetherItems.WITHER_STAFF), 2000, 0.2F);
        addRecipe(new ItemStack(NetherItems.SPAWNER_UPGRADE, 1, 0), new ItemStack(NetherItems.NETHERITE_NUGGET), new ItemStack(Items.REDSTONE), new ItemStack(NetherItems.NETHERITE_INGOT), new ItemStack(NetherItems.NETHERITE_NUGGET), new ItemStack(Items.REDSTONE), new ItemStack(NetherItems.NETHERITE_NUGGET), new ItemStack(NetherItems.SPAWNER_UPGRADE, 1, 1), 800, 0.4F);
        addRecipe(new ItemStack(NetherItems.SPAWNER_UPGRADE, 1, 1), new ItemStack(NetherItems.ECTOPLASM), new ItemStack(NetherItems.DIMENSIONAL_DUST), new ItemStack(NetherItems.ECTOPLASM), new ItemStack(NetherItems.ECTOPLASM), new ItemStack(NetherItems.DIMENSIONAL_DUST), new ItemStack(NetherItems.ECTOPLASM), new ItemStack(NetherItems.SPAWNER_UPGRADE, 1, 2), 900, 0.3F);
        addRecipe(new ItemStack(NetherItems.SPAWNER_UPGRADE, 1, 2), new ItemStack(NetherItems.ECTOPLASM), new ItemStack(NetherItems.DIMENSIONAL_DUST), new ItemStack(NetherItems.ECTOPLASM), new ItemStack(NetherItems.ECTOPLASM), new ItemStack(NetherItems.DIMENSIONAL_DUST), new ItemStack(NetherItems.ECTOPLASM), new ItemStack(NetherItems.SPAWNER_UPGRADE, 1, 3), 1000, 0.2F);
        addRecipe(new ItemStack(NetherItems.SPAWNER_UPGRADE, 1, 0), new ItemStack(NetherItems.NETHERITE_NUGGET), new ItemStack(Items.GLOWSTONE_DUST), new ItemStack(NetherItems.NETHERITE_INGOT), new ItemStack(NetherItems.NETHERITE_NUGGET), new ItemStack(Items.GLOWSTONE_DUST), new ItemStack(NetherItems.NETHERITE_NUGGET), new ItemStack(NetherItems.SPAWNER_UPGRADE, 1, 4), 800, 0.4F);
        addRecipe(new ItemStack(NetherItems.SPAWNER_UPGRADE, 1, 4), new ItemStack(NetherItems.ECTOPLASM), new ItemStack(NetherItems.DIMENSIONAL_DUST), new ItemStack(NetherItems.ECTOPLASM), new ItemStack(NetherItems.ECTOPLASM), new ItemStack(NetherItems.DIMENSIONAL_DUST), new ItemStack(NetherItems.ECTOPLASM), new ItemStack(NetherItems.SPAWNER_UPGRADE, 1, 5), 900, 0.3F);
        addRecipe(new ItemStack(NetherItems.SPAWNER_UPGRADE, 1, 5), new ItemStack(NetherItems.ECTOPLASM), new ItemStack(NetherItems.DIMENSIONAL_DUST), new ItemStack(NetherItems.ECTOPLASM), new ItemStack(NetherItems.ECTOPLASM), new ItemStack(NetherItems.DIMENSIONAL_DUST), new ItemStack(NetherItems.ECTOPLASM), new ItemStack(NetherItems.SPAWNER_UPGRADE, 1, 6), 1000, 0.2F);
        addRecipe(new ItemStack(NetherItems.SPAWNER_UPGRADE, 1, 6), new ItemStack(NetherItems.ECTOPLASM), new ItemStack(NetherItems.DIMENSIONAL_DUST), new ItemStack(NetherItems.ECTOPLASM), new ItemStack(NetherItems.ECTOPLASM), new ItemStack(NetherItems.DIMENSIONAL_DUST), new ItemStack(NetherItems.ECTOPLASM), new ItemStack(NetherItems.SPAWNER_UPGRADE, 1, 7), 1200, 0.2F);
        addRecipe(new ItemStack(NetherItems.SPAWNER_UPGRADE, 1, 7), new ItemStack(NetherItems.ECTOPLASM), new ItemStack(NetherItems.DIMENSIONAL_DUST), new ItemStack(NetherItems.ECTOPLASM), new ItemStack(NetherItems.ECTOPLASM), new ItemStack(NetherItems.DIMENSIONAL_DUST), new ItemStack(NetherItems.ECTOPLASM), new ItemStack(NetherItems.SPAWNER_UPGRADE, 1, 8), 1400, 0.1F);
        addRecipe(new ItemStack(NetherItems.SPAWNER_UPGRADE, 1, 0), new ItemStack(NetherItems.NETHERITE_NUGGET), new ItemStack(Items.BED), new ItemStack(NetherItems.NETHERITE_INGOT), new ItemStack(NetherItems.NETHERITE_NUGGET), new ItemStack(Items.BED), new ItemStack(NetherItems.NETHERITE_NUGGET), new ItemStack(NetherItems.SPAWNER_UPGRADE, 1, 9), 1200, 0.2F);
        addRecipe(new ItemStack(NetherItems.SPAWNER_UPGRADE, 1, 0), new ItemStack(NetherItems.NETHERITE_NUGGET), new ItemStack(NetherItems.GOLDEN_BUCKET), new ItemStack(NetherItems.NETHERITE_INGOT), new ItemStack(NetherItems.NETHERITE_NUGGET), new ItemStack(NetherItems.GOLDEN_BUCKET), new ItemStack(NetherItems.NETHERITE_NUGGET), new ItemStack(NetherItems.SPAWNER_UPGRADE, 1, 10), 800, 0.4F);
        addRecipe(new ItemStack(NetherItems.SPAWNER_UPGRADE, 1, 10), new ItemStack(NetherItems.ECTOPLASM), new ItemStack(NetherItems.DIMENSIONAL_DUST), new ItemStack(NetherItems.ECTOPLASM), new ItemStack(NetherItems.ECTOPLASM), new ItemStack(NetherItems.DIMENSIONAL_DUST), new ItemStack(NetherItems.ECTOPLASM), new ItemStack(NetherItems.SPAWNER_UPGRADE, 1, 11), 900, 0.3F);
        addRecipe(new ItemStack(NetherItems.SPAWNER_UPGRADE, 1, 11), new ItemStack(NetherItems.ECTOPLASM), new ItemStack(NetherItems.DIMENSIONAL_DUST), new ItemStack(NetherItems.ECTOPLASM), new ItemStack(NetherItems.ECTOPLASM), new ItemStack(NetherItems.DIMENSIONAL_DUST), new ItemStack(NetherItems.ECTOPLASM), new ItemStack(NetherItems.SPAWNER_UPGRADE, 1, 12), 1000, 0.2F);
        addRecipe(new ItemStack(NetherItems.SPAWNER_UPGRADE, 1, 12), new ItemStack(NetherItems.ECTOPLASM), new ItemStack(NetherItems.DIMENSIONAL_DUST), new ItemStack(NetherItems.ECTOPLASM), new ItemStack(NetherItems.ECTOPLASM), new ItemStack(NetherItems.DIMENSIONAL_DUST), new ItemStack(NetherItems.ECTOPLASM), new ItemStack(NetherItems.SPAWNER_UPGRADE, 1, 13), 1200, 0.2F);
        addRecipe(new ItemStack(NetherItems.SPAWNER_UPGRADE, 1, 13), new ItemStack(NetherItems.ECTOPLASM), new ItemStack(NetherItems.DIMENSIONAL_DUST), new ItemStack(NetherItems.ECTOPLASM), new ItemStack(NetherItems.ECTOPLASM), new ItemStack(NetherItems.DIMENSIONAL_DUST), new ItemStack(NetherItems.ECTOPLASM), new ItemStack(NetherItems.SPAWNER_UPGRADE, 1, 14), 1400, 0.1F);
        addRecipe(new ItemStack(NetherItems.SPAWNER_UPGRADE, 1, 0), new ItemStack(NetherItems.NETHERITE_NUGGET), new ItemStack(Items.NETHER_WART), new ItemStack(NetherItems.NETHERITE_INGOT), new ItemStack(NetherItems.NETHERITE_NUGGET), new ItemStack(Items.NETHER_WART), new ItemStack(NetherItems.NETHERITE_NUGGET), new ItemStack(NetherItems.SPAWNER_UPGRADE, 1, 15), 800, 0.4F);
        addRecipe(new ItemStack(NetherItems.SPAWNER_UPGRADE, 1, 15), new ItemStack(NetherItems.ECTOPLASM), new ItemStack(NetherItems.DIMENSIONAL_DUST), new ItemStack(NetherItems.ECTOPLASM), new ItemStack(NetherItems.ECTOPLASM), new ItemStack(NetherItems.DIMENSIONAL_DUST), new ItemStack(NetherItems.ECTOPLASM), new ItemStack(NetherItems.SPAWNER_UPGRADE, 1, 16), 900, 0.3F);
        addRecipe(new ItemStack(NetherItems.SPAWNER_UPGRADE, 1, 16), new ItemStack(NetherItems.ECTOPLASM), new ItemStack(NetherItems.DIMENSIONAL_DUST), new ItemStack(NetherItems.ECTOPLASM), new ItemStack(NetherItems.ECTOPLASM), new ItemStack(NetherItems.DIMENSIONAL_DUST), new ItemStack(NetherItems.ECTOPLASM), new ItemStack(NetherItems.SPAWNER_UPGRADE, 1, 17), 1000, 0.2F);
        addRecipe(new ItemStack(NetherItems.SPAWNER_UPGRADE, 1, 0), new ItemStack(NetherItems.NETHERITE_NUGGET), new ItemStack(Items.FERMENTED_SPIDER_EYE), new ItemStack(NetherItems.NETHERITE_INGOT), new ItemStack(NetherItems.NETHERITE_NUGGET), new ItemStack(Items.FERMENTED_SPIDER_EYE), new ItemStack(NetherItems.NETHERITE_NUGGET), new ItemStack(NetherItems.SPAWNER_UPGRADE, 1, 18), 800, 0.4F);
        addRecipe(new ItemStack(NetherItems.SPAWNER_UPGRADE, 1, 18), new ItemStack(NetherItems.ECTOPLASM), new ItemStack(NetherItems.DIMENSIONAL_DUST), new ItemStack(NetherItems.ECTOPLASM), new ItemStack(NetherItems.ECTOPLASM), new ItemStack(NetherItems.DIMENSIONAL_DUST), new ItemStack(NetherItems.ECTOPLASM), new ItemStack(NetherItems.SPAWNER_UPGRADE, 1, 19), 900, 0.3F);
        addRecipe(new ItemStack(NetherItems.SPAWNER_UPGRADE, 1, 19), new ItemStack(NetherItems.ECTOPLASM), new ItemStack(NetherItems.DIMENSIONAL_DUST), new ItemStack(NetherItems.ECTOPLASM), new ItemStack(NetherItems.ECTOPLASM), new ItemStack(NetherItems.DIMENSIONAL_DUST), new ItemStack(NetherItems.ECTOPLASM), new ItemStack(NetherItems.SPAWNER_UPGRADE, 1, 20), 1000, 0.2F);
        addRecipe(NetherItems.NETHERITE_CHESTPLATE, null, NetherItems.AVIS_WING, null, null, NetherItems.AVIS_WING, null, Items.ELYTRA, 3000, 0.2F);
        NetherConfig.printDebugInfo("Registered AltarRecipes");
	}
        
    public static void addRecipe(Item input0, Item input1, Item input2, Item input3, Item input4, Item input5, Item input6, Item output, int blood, float duration) {
    	addRecipe(new ItemStack(input0), new ItemStack(input1), new ItemStack(input2), new ItemStack(input3), new ItemStack(input4), new ItemStack(input5), new ItemStack(input6), new ItemStack(output), blood, duration);
    }

	public static void addRecipe(ItemStack input0, ItemStack input1, ItemStack input2, ItemStack input3, ItemStack input4, ItemStack input5, ItemStack input6, ItemStack output, int blood, float duration)
    {
	    if(NetherConfig.allowAltar) {
	    	AltarRecipe recipe = new AltarRecipe(input0, input1, input2, input3, input4, input5, input6, output, blood, duration);
	    	RECIPES.add(recipe); 
		}
    }
		
	public static AltarRecipe getRecipe(NonNullList<ItemStack> stacks) {
		for(AltarRecipe recipe : RECIPES) {
			if(doesMatch(recipe.input0, stacks.get(0)) && doesMatch(recipe.input1, stacks.get(1)) &&
				doesMatch(recipe.input2, stacks.get(2)) && doesMatch(recipe.input3, stacks.get(3)) &&
				doesMatch(recipe.input4, stacks.get(4)) && doesMatch(recipe.input5, stacks.get(5)) && 
				doesMatch(recipe.input6, stacks.get(6)))
			return recipe;
		}
		return null;
	}
	
	private static boolean doesMatch(ItemStack recipe, ItemStack input) {
		if(recipe == null && input != ItemStack.field_190927_a) {
			return false;
		}
		else {
			return ItemStack.areItemsEqual(recipe, input);
		}
	}
        
    public static class AltarRecipe {
        	
        public final ItemStack input0;
        public final ItemStack input1;
        public final ItemStack input2;
        public final ItemStack input3;
        public final ItemStack input4;
        public final ItemStack input5;
        public final ItemStack input6;
        public final ItemStack output;
        public final float duration;
        public final int blood;
        	
        public AltarRecipe(ItemStack input0, ItemStack input1, ItemStack input2, ItemStack input3, ItemStack input4, ItemStack input5, ItemStack input6, ItemStack output, int blood, float duration) {
        		
        	this.input0 = input0;
        	this.input1 = input1;
        	this.input2 = input2;
        	this.input3 = input3;
        	this.input4 = input4;
        	this.input5 = input5;
        	this.input6 = input6;
        	this.output = output;
        	this.blood = blood;
        	this.duration = duration;
		}
        	
        public String toString() {
        	return "[" + input0 + "," + input1 + "," + input2 + "," + input3 + "," + input4 + "," + input5 + "," + input6 + "]";
        }
    }
}