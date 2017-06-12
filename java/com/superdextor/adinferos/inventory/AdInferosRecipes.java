package com.superdextor.adinferos.inventory;

import com.superdextor.adinferos.config.NetherConfig;
import com.superdextor.adinferos.init.NetherBlocks;
import com.superdextor.adinferos.init.NetherItems;
import com.superdextor.thinkbigcore.helpers.RecipeHelper;

import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;
import net.minecraftforge.fml.common.registry.GameRegistry;

public class AdInferosRecipes {

    public static void register() {
    		
    	boolean crafting = NetherConfig.allowCrafting;
    	boolean smelting = NetherConfig.allowSmelting;
    	
    	if(NetherConfig.recipesNetherWood)
    	{
    		 initNetherWoodRecipes(crafting, smelting);
    	}
    		
    	if(NetherConfig.recipesNetherrack)
    	{
    		initNetherrackRecipes(crafting, smelting);
    	}
    		
    	if(NetherConfig.recipesQuartz)
    	{
    		initQuartzRecipes(crafting, smelting);
    	}
    	
    	if(NetherConfig.recipesGlowstone)
    	{
    		initGlowstoneRecipes(crafting, smelting);
    	}
    		
    	if(NetherConfig.recipesObsidian)
    	{
    		initObsidianRecipes(crafting, smelting);
    	}
    		
    	if(NetherConfig.recipesWither)
    	{
    		initWitherRecipes(crafting, smelting);
    	}
    		
    	if(NetherConfig.recipesNetherite)
    	{
    		initNetheriteRecipes(crafting, smelting);
    	}
    		
    	if(NetherConfig.recipesOther)
    	{
    		initMiscRecipes(crafting, smelting);
    	}
    	
		NetherConfig.printDebugInfo("Registered Recipes");
    }
	
    private static void initNetherWoodRecipes(boolean crafting, boolean smelting){
    	
    	if(crafting)
    	{
            GameRegistry.addShapelessRecipe(new ItemStack (NetherBlocks.PLANKS, 4, 0), new ItemStack(NetherBlocks.LOG, 1, 0));
            GameRegistry.addShapelessRecipe(new ItemStack (NetherBlocks.PLANKS, 4, 1), new ItemStack(NetherBlocks.LOG, 1, 1));
            GameRegistry.addShapelessRecipe(new ItemStack (NetherBlocks.PLANKS, 4, 2), new ItemStack(NetherBlocks.LOG, 1, 2));
            GameRegistry.addShapelessRecipe(new ItemStack (NetherBlocks.PLANKS, 4, 3), new ItemStack(NetherBlocks.LOG, 1, 3));
            
        	GameRegistry.addRecipe(new ItemStack (NetherBlocks.WOODEN_SLAB, 6, 0),
            		"WWW",
            		'W' , new ItemStack(NetherBlocks.PLANKS, 1, 0));
        	GameRegistry.addRecipe(new ItemStack (NetherBlocks.WOODEN_SLAB, 6, 1),
            		"WWW",
            		'W' , new ItemStack(NetherBlocks.PLANKS, 1, 1));
        	GameRegistry.addRecipe(new ItemStack (NetherBlocks.WOODEN_SLAB, 6, 2),
            		"WWW",
            		'W' , new ItemStack(NetherBlocks.PLANKS, 1, 2));
        	GameRegistry.addRecipe(new ItemStack (NetherBlocks.WOODEN_SLAB, 6, 3),
            		"WWW",
            		'W' , new ItemStack(NetherBlocks.PLANKS, 1, 3));
        	
        	GameRegistry.addRecipe(new ItemStack (NetherBlocks.INFERNO_STAIRS, 4),
            		"W  ",
            		"WW ",
            		"WWW",
            		'W' , new ItemStack(NetherBlocks.PLANKS, 1, 0));
        	GameRegistry.addRecipe(new ItemStack (NetherBlocks.MAGMA_STAIRS, 4),
            		"W  ",
            		"WW ",
            		"WWW",
            		'W' , new ItemStack(NetherBlocks.PLANKS, 1, 1));
        	GameRegistry.addRecipe(new ItemStack (NetherBlocks.PHANTOM_STAIRS, 4),
            		"W  ",
            		"WW ",
            		"WWW",
            		'W' , new ItemStack(NetherBlocks.PLANKS, 1, 2));
        	GameRegistry.addRecipe(new ItemStack (NetherBlocks.ASH_STAIRS, 4),
            		"W  ",
            		"WW ",
            		"WWW",
            		'W' , new ItemStack(NetherBlocks.PLANKS, 1, 3));
        	GameRegistry.addRecipe(new ItemStack (NetherBlocks.CRAFTING_TABLE, 1, 0),
            		"II",
            		"II",
            		'I' , new ItemStack(NetherBlocks.PLANKS, 1, 0));
        	GameRegistry.addRecipe(new ItemStack (NetherBlocks.CRAFTING_TABLE, 1, 1),
            		"II",
            		"II",
            		'I' , new ItemStack(NetherBlocks.PLANKS, 1, 1));
        	GameRegistry.addRecipe(new ItemStack (NetherBlocks.CRAFTING_TABLE, 1, 2),
            		"II",
            		"II",
            		'I' , new ItemStack(NetherBlocks.PLANKS, 1, 2));
        	GameRegistry.addRecipe(new ItemStack (NetherBlocks.CRAFTING_TABLE, 1, 3),
            		"II",
            		"II",
            		'I' , new ItemStack(NetherBlocks.PLANKS, 1, 3));
        	GameRegistry.addRecipe(new ItemStack (NetherBlocks.INFERNO_PRESSURE_PLATE),
            		"II",
            		'I' , new ItemStack(NetherBlocks.PLANKS, 1, 0));
        	GameRegistry.addRecipe(new ItemStack (NetherBlocks.MAGMA_PRESSURE_PLATE),
            		"II",
            		'I' , new ItemStack(NetherBlocks.PLANKS, 1, 1));
        	GameRegistry.addRecipe(new ItemStack (NetherBlocks.PHANTOM_PRESSURE_PLATE),
            		"II",
            		'I' , new ItemStack(NetherBlocks.PLANKS, 1, 2));
        	GameRegistry.addRecipe(new ItemStack (NetherBlocks.ASH_PRESSURE_PLATE),
            		"II",
            		'I' , new ItemStack(NetherBlocks.PLANKS, 1, 3));
        	GameRegistry.addRecipe(new ItemStack (NetherBlocks.INFERNO_TRAPDOOR, 2),
            		"III",
            		"III",
            		'I' , new ItemStack(NetherBlocks.PLANKS, 1, 0));
        	GameRegistry.addRecipe(new ItemStack (NetherBlocks.MAGMA_TRAPDOOR, 2),
            		"III",
            		"III",
            		'I' , new ItemStack(NetherBlocks.PLANKS, 1, 1));
        	GameRegistry.addRecipe(new ItemStack (NetherBlocks.PHANTOM_TRAPDOOR, 2),
            		"III",
            		"III",
            		'I' , new ItemStack(NetherBlocks.PLANKS, 1, 1));
        	GameRegistry.addRecipe(new ItemStack (NetherBlocks.ASH_TRAPDOOR, 2),
            		"III",
            		"III",
            		'I' , new ItemStack(NetherBlocks.PLANKS, 1, 2));
        	GameRegistry.addRecipe(new ItemStack (NetherBlocks.INFERNO_FENCE, 3),
            		"IZI",
            		"IZI",
            		'Z' , Items.STICK,
            		'I' , new ItemStack(NetherBlocks.PLANKS, 1, 0));
        	GameRegistry.addRecipe(new ItemStack (NetherBlocks.MAGMA_FENCE, 3),
            		"IZI",
            		"IZI",
            		'Z' , Items.STICK,
            		'I' , new ItemStack(NetherBlocks.PLANKS, 1, 1));
        	GameRegistry.addRecipe(new ItemStack (NetherBlocks.PHANTOM_FENCE, 3),
            		"IZI",
            		"IZI",
            		'Z' , Items.STICK,
            		'I' , new ItemStack(NetherBlocks.PLANKS, 1, 2));
        	GameRegistry.addRecipe(new ItemStack (NetherBlocks.ASH_FENCE, 3),
            		"IZI",
            		"IZI",
            		'Z' , Items.STICK,
            		'I' , new ItemStack(NetherBlocks.PLANKS, 1, 3));
        	GameRegistry.addRecipe(new ItemStack (NetherBlocks.INFERNO_FENCE_GATE),
            		"ZIZ",
            		"ZIZ",
            		'Z' , Items.STICK,
            		'I' , new ItemStack(NetherBlocks.PLANKS, 1, 0));
        	GameRegistry.addRecipe(new ItemStack (NetherBlocks.MAGMA_FENCE_GATE),
            		"ZIZ",
            		"ZIZ",
            		'Z' , Items.STICK,
            		'I' , new ItemStack(NetherBlocks.PLANKS, 1, 1));
        	GameRegistry.addRecipe(new ItemStack (NetherBlocks.PHANTOM_FENCE_GATE),
            		"ZIZ",
            		"ZIZ",
            		'Z' , Items.STICK,
            		'I' , new ItemStack(NetherBlocks.PLANKS, 1, 2));
        	GameRegistry.addRecipe(new ItemStack (NetherBlocks.ASH_FENCE_GATE),
            		"ZIZ",
            		"ZIZ",
            		'Z' , Items.STICK,
            		'I' , new ItemStack(NetherBlocks.PLANKS, 1, 3));
        	GameRegistry.addRecipe(new ItemStack (NetherItems.INFERNO_DOOR, 3),
            		"II",
            		"II",
            		"II",
            		'I' , new ItemStack(NetherBlocks.PLANKS, 1, 0));
        	GameRegistry.addRecipe(new ItemStack (NetherItems.MAGMA_DOOR, 3),
            		"II",
            		"II",
            		"II",
            		'I' , new ItemStack(NetherBlocks.PLANKS, 1, 1));
        	GameRegistry.addRecipe(new ItemStack (NetherItems.PHANTOM_DOOR, 3),
            		"II",
            		"II",
            		"II",
            		'I' , new ItemStack(NetherBlocks.PLANKS, 1, 2));
        	GameRegistry.addRecipe(new ItemStack (NetherItems.ASH_DOOR, 3),
            		"II",
            		"II",
            		"II",
            		'I' , new ItemStack(NetherBlocks.PLANKS, 1, 3));
    	}
    	
    	if(smelting)
    	{
            GameRegistry.addSmelting(NetherBlocks.LOG, new ItemStack(NetherItems.PEAT, 1), 0.4F);
            GameRegistry.addSmelting(NetherBlocks.INFERNO_TRAPDOOR, new ItemStack(NetherBlocks.INFERNO_TRAPDOOR_HIDDEN, 1), 0.1F);
            GameRegistry.addSmelting(NetherBlocks.MAGMA_TRAPDOOR, new ItemStack(NetherBlocks.MAGMA_TRAPDOOR_HIDDEN, 1), 0.1F);
            GameRegistry.addSmelting(NetherBlocks.PHANTOM_TRAPDOOR, new ItemStack(NetherBlocks.PHANTOM_TRAPDOOR_HIDDEN, 1), 0.1F);
            GameRegistry.addSmelting(NetherBlocks.ASH_TRAPDOOR, new ItemStack(NetherBlocks.ASH_TRAPDOOR_HIDDEN, 1), 0.1F);
    	}
	}
    
    private static void initNetherrackRecipes(boolean crafting, boolean smelting)
    {
    	if(crafting)
    	{
        	GameRegistry.addRecipe(new ItemStack (NetherBlocks.CHISELED_NETHERRACK),
            		"I",
            		"I",
            		'I' , new ItemStack(NetherBlocks.STONE_SLAB, 1, 0));
        	GameRegistry.addRecipe(new ItemStack (NetherBlocks.SMOOTH_NETHERRACK, 4),
            		"NN",
            		"NN",
            		'N' , Blocks.NETHERRACK);
        	GameRegistry.addRecipe(new ItemStack (NetherBlocks.STONE_SLAB, 6, 0),
            		"WWW",
            		'W' , NetherBlocks.SMOOTH_NETHERRACK);
        	GameRegistry.addRecipe(new ItemStack (NetherBlocks.SMOOTH_NETHERRACK_STAIRS, 4),
            		"W  ",
            		"WW ",
            		"WWW",
            		'W' , NetherBlocks.SMOOTH_NETHERRACK);
        	GameRegistry.addRecipe(new ItemStack (NetherBlocks.SMOOTH_NETHERRACK_STAIRS, 4),
            		"W  ",
            		"WW ",
            		"WWW",
            		'W' , NetherBlocks.SMOOTH_NETHERRACK);
        	GameRegistry.addRecipe(new ItemStack (NetherBlocks.NETHERRACK_FURNACE),
            		"XXX",
            		"X X",
            		"XXX",
            		'X' , Blocks.NETHERRACK);
    	}
    }
    
    private static void initQuartzRecipes(boolean crafting, boolean smelting)
    {
    	if(crafting)
    	{
        	RecipeHelper.addMaterialSetRecipe(new ItemStack(NetherItems.QUARTZ_CHUNK), NetherItems.QUARTZ_STICK, NetherItems.QUARTZ_SWORD, NetherItems.QUARTZ_AXE, NetherItems.QUARTZ_PICKAXE, NetherItems.QUARTZ_SHOVEL, NetherItems.QUARTZ_HOE, NetherItems.QUARTZ_HELMET, NetherItems.QUARTZ_CHESTPLATE, NetherItems.QUARTZ_LEGGINGS, NetherItems.QUARTZ_BOOTS);
        	GameRegistry.addRecipe(new ItemStack(NetherItems.QUARTZ_STICK, 4),
            		"X",
            		"X",
            		'X' , Items.QUARTZ);
        	GameRegistry.addRecipe(new ItemStack(NetherItems.QUARTZ_STICK, 4),
            		"X",
            		"X",
            		'X' , NetherItems.QUARTZ_CHUNK);
        	GameRegistry.addRecipe(new ItemStack (NetherItems.QUARTZ_BOW),
            		"FQ ",
            		"F Q",
            		"FQ ",
            		'F' , NetherItems.INFERNAL_STRING,
            		'Q' , NetherItems.QUARTZ_CHUNK);
        	GameRegistry.addRecipe(new ItemStack (NetherItems.QUARTZ_ARROW, 12),
            		"B",
            		"Q",
            		"F",
            		'B' , NetherItems.QUARTZ_CHUNK,
            		'Q' , NetherItems.QUARTZ_STICK,
            		'F' , NetherItems.INFERNAL_FEATHER);
    	}
    	
    	if(smelting)
    	{
            GameRegistry.addSmelting(Items.QUARTZ, new ItemStack(NetherItems.QUARTZ_CHUNK, 1), 0.3F);
    	}
    }
    
    private static void initGlowstoneRecipes(boolean crafting, boolean smelting)
    {
    	if(crafting)
    	{
        	RecipeHelper.addMaterialSetRecipe(new ItemStack(NetherItems.GLOWSTONE_CRYSTAL), NetherItems.QUARTZ_STICK, NetherItems.GLOWSTONE_SWORD, NetherItems.GLOWSTONE_AXE, NetherItems.GLOWSTONE_PICKAXE, NetherItems.GLOWSTONE_SHOVEL, NetherItems.GLOWSTONE_HOE, NetherItems.GLOWSTONE_HELMET, NetherItems.GLOWSTONE_CHESTPLATE, NetherItems.GLOWSTONE_LEGGINGS, NetherItems.GLOWSTONE_BOOTS);
        	GameRegistry.addRecipe(new ItemStack (NetherItems.GLOWSTONE_CRYSTAL),
            		"NDN",
            		"DND",
            		"NDN",
            		'N' , Items.GOLD_NUGGET,
            		'D' , Items.GLOWSTONE_DUST);
        	GameRegistry.addRecipe(new ItemStack (NetherBlocks.GLOWSTONE_TORCH, 4),
            		"G",
            		"Q",
            		'G' , Items.GLOWSTONE_DUST,
    		        'Q' , NetherItems.QUARTZ_STICK);
        	GameRegistry.addRecipe(new ItemStack (NetherItems.GLOWSTONE_BOW),
            		"FQ ",
            		"F Q",
            		"FQ ",
            		'F' , NetherItems.INFERNAL_STRING,
            		'Q' , NetherItems.GLOWSTONE_CRYSTAL);
        	GameRegistry.addRecipe(new ItemStack (NetherItems.GLOWSTONE_ARROW, 12),
            		"B",
            		"Q",
            		"F",
            		'B' , NetherItems.GLOWSTONE_CRYSTAL,
            		'Q' , NetherItems.QUARTZ_STICK,
            		'F' , NetherItems.INFERNAL_FEATHER);
    	}
    }
    
    private static void initObsidianRecipes(boolean crafting, boolean smelting)
    {
    	if(crafting)
    	{
        	RecipeHelper.addMaterialSetRecipe(new ItemStack(NetherItems.OBSIDIAN_INGOT), NetherItems.QUARTZ_STICK, NetherItems.OBSIDIAN_SWORD, NetherItems.OBSIDIAN_AXE, NetherItems.OBSIDIAN_PICKAXE, NetherItems.OBSIDIAN_SHOVEL, NetherItems.OBSIDIAN_HOE, NetherItems.OBSIDIAN_HELMET, NetherItems.OBSIDIAN_CHESTPLATE, NetherItems.OBSIDIAN_LEGGINGS, NetherItems.OBSIDIAN_BOOTS);
        	GameRegistry.addRecipe(new ItemStack (NetherItems.OBSIDIAN_INGOT),
            		"XXX",
            		"XXX",
            		"XXX",
            		'X' , NetherItems.OBSIDIAN_NUGGET);
            GameRegistry.addShapelessRecipe(new ItemStack (NetherItems.OBSIDIAN_NUGGET, 9), NetherItems.OBSIDIAN_INGOT);
        	GameRegistry.addRecipe(new ItemStack (NetherItems.OBSIDIAN_BOW),
            		"FQ ",
            		"F Q",
            		"FQ ",
            		'F' , NetherItems.INFERNAL_STRING,
            		'Q' , NetherItems.OBSIDIAN_INGOT);
        	GameRegistry.addRecipe(new ItemStack (NetherItems.OBSIDIAN_ARROW, 12),
            		"B",
            		"Q",
            		"F",
            		'B' , NetherItems.OBSIDIAN_INGOT,
            		'Q' , NetherItems.QUARTZ_STICK,
            		'F' , NetherItems.INFERNAL_FEATHER);
        	GameRegistry.addRecipe(new ItemStack (NetherBlocks.STONE_SLAB, 6, 1),
            		"WWW",
            		'W' , Blocks.OBSIDIAN);
        	GameRegistry.addRecipe(new ItemStack (NetherBlocks.OBSIDIAN_STAIRS, 4),
            		"W  ",
            		"WW ",
            		"WWW",
            		'W' , Blocks.OBSIDIAN);
        	GameRegistry.addRecipe(new ItemStack (NetherBlocks.OBSIDIAN_FENCE_GATE),
            		"ZIZ",
            		"ZIZ",
            		'Z' , NetherItems.QUARTZ_STICK,
            		'I' , Blocks.OBSIDIAN);
        	GameRegistry.addRecipe(new ItemStack (NetherBlocks.OBSIDIAN_FENCE, 3),
            		"IZI",
            		"IZI",
            		'Z' , NetherItems.QUARTZ_STICK,
            		'I' , Blocks.OBSIDIAN);
        	GameRegistry.addRecipe(new ItemStack (NetherBlocks.CHISELED_OBSIDIAN),
            		"I",
            		"I",
            		'I' , new ItemStack(NetherBlocks.STONE_SLAB, 1, 1));
        	GameRegistry.addRecipe(new ItemStack (NetherBlocks.SMOOTH_OBSIDIAN, 4),
            		"II",
            		"II",
            		'I' , Blocks.OBSIDIAN);
        	GameRegistry.addRecipe(new ItemStack (NetherBlocks.OBSIDIAN_BARS, 16),
            		"II",
            		"II",
            		'I' , NetherItems.OBSIDIAN_INGOT);
    	}
    	
    	if(smelting) {
            GameRegistry.addSmelting(Blocks.OBSIDIAN, new ItemStack(NetherItems.OBSIDIAN_NUGGET, 6), 0.9F);
    	}
    }
    
    private static void initWitherRecipes(boolean crafting, boolean smelting)
    {
    	if(crafting)
    	{
        	RecipeHelper.addMaterialSetRecipe(new ItemStack(NetherItems.WITHER_GEM), NetherItems.QUARTZ_STICK, NetherItems.WITHER_SWORD, NetherItems.WITHER_AXE, NetherItems.WITHER_PICKAXE, NetherItems.WITHER_SHOVEL, NetherItems.WITHER_HOE, NetherItems.WITHER_HELMET, NetherItems.WITHER_CHESTPLATE, NetherItems.WITHER_LEGGINGS, NetherItems.WITHER_BOOTS);
        	GameRegistry.addRecipe(new ItemStack (NetherBlocks.WITHER_BLOCK, 1, 1),
            		"III",
            		"III",
            		"III",
            		'I' , NetherItems.WITHER_GEM);
            GameRegistry.addShapelessRecipe(new ItemStack (NetherItems.WITHER_GEM, 9), new ItemStack(NetherBlocks.WITHER_BLOCK, 1, 1));
        	GameRegistry.addRecipe(new ItemStack (NetherBlocks.WITHER_BLOCK),
            		"III",
            		"III",
            		"III",
            		'I' , NetherItems.WITHER_DUST);
            GameRegistry.addShapelessRecipe(new ItemStack (NetherItems.WITHER_DUST, 9), NetherBlocks.WITHER_BLOCK);
        	GameRegistry.addRecipe(new ItemStack (NetherItems.WITHER_BOW),
            		"FQ ",
            		"F Q",
            		"FQ ",
            		'F' , NetherItems.INFERNAL_STRING,
            		'Q' , NetherItems.WITHER_GEM);
        	GameRegistry.addRecipe(new ItemStack (NetherItems.WITHER_ARROW, 12),
            		"B",
            		"Q",
            		"F",
            		'B' , NetherItems.WITHER_GEM,
            		'Q' , Items.BLAZE_ROD,
            		'F' , NetherItems.INFERNAL_FEATHER);
    	}
    }
    
    private static void initNetheriteRecipes(boolean crafting, boolean smelting)
    {
    	if(crafting)
    	{
        	RecipeHelper.addMaterialSetRecipe(new ItemStack(NetherItems.NETHERITE_INGOT), NetherBlocks.NETHERITE_BLOCK, Items.BLAZE_ROD, NetherItems.NETHERITE_SWORD, NetherItems.NETHERITE_AXE, NetherItems.NETHERITE_PICKAXE, NetherItems.NETHERITE_SHOVEL, NetherItems.NETHERITE_HOE, NetherItems.NETHERITE_HELMET, NetherItems.NETHERITE_CHESTPLATE, NetherItems.NETHERITE_LEGGINGS, NetherItems.NETHERITE_BOOTS);
        	GameRegistry.addRecipe(new ItemStack(NetherItems.NETHERITE_INGOT),
            		"XXX",
            		"XXX",
            		"XXX",
            		'X' , NetherItems.NETHERITE_NUGGET);
            GameRegistry.addShapelessRecipe(new ItemStack(NetherItems.NETHERITE_NUGGET, 9), NetherItems.NETHERITE_INGOT);
        	GameRegistry.addRecipe(new ItemStack (NetherItems.NETHERITE_BOW),
            		"FQ ",
            		"F Q",
            		"FQ ",
            		'F' , NetherItems.INFERNAL_STRING,
            		'Q' , NetherItems.NETHERITE_INGOT);
        	GameRegistry.addRecipe(new ItemStack (NetherItems.NETHERITE_ARROW, 12),
            		"B",
            		"Q",
            		"F",
            		'B' , NetherItems.NETHERITE_INGOT,
            		'Q' , Items.BLAZE_ROD,
            		'F' , NetherItems.INFERNAL_FEATHER);
    	}
    	
    	if(smelting)
    	{
            GameRegistry.addSmelting(NetherBlocks.NETHERITE_ORE, new ItemStack(NetherItems.NETHERITE_INGOT), 1.0F);
    	}
    }
    
    private static void initMiscRecipes(boolean crafting, boolean smelting)
    {
    	if(crafting)
    	{
        	GameRegistry.addRecipe(new ItemStack (NetherBlocks.SOUL_TNT),
            		"GSG",
            		"SGS",
            		"GSG",
            		'S' , Blocks.SOUL_SAND,
            		'G' , Items.GUNPOWDER);
        	GameRegistry.addRecipe(new ItemStack (NetherBlocks.SOUL_GLASS_PANE, 16),
            		"WWW",
            		"WWW",
            		'W' , NetherBlocks.SOUL_GLASS);
        	GameRegistry.addRecipe(new ItemStack (NetherBlocks.NETHER_BRICK_FENCE_GATE),
            		"ZIZ",
            		"ZIZ",
            		'Z' , NetherItems.QUARTZ_STICK,
            		'I' , Blocks.NETHER_BRICK);
        	GameRegistry.addRecipe(new ItemStack (NetherBlocks.DARK_BRICKS),
            		"II",
            		"II",
            		'I' , NetherItems.DARK_BRICK);
        	GameRegistry.addRecipe(new ItemStack (NetherBlocks.ALTAR),
            		" G ",
            		"WZW",
            		"III",
            		'Z' , NetherItems.DARKCORE,
            		'W' , NetherItems.AVIS_WING,
            		'G' , NetherItems.WITHER_DUST,
            		'I' , NetherItems.NETHERITE_INGOT);
        	GameRegistry.addRecipe(new ItemStack (NetherBlocks.DIMSTONE),
            		"II",
            		"II",
            		'I' , NetherItems.DIMSTONE_DUST);
            GameRegistry.addSmelting(Items.ROTTEN_FLESH, new ItemStack(NetherItems.COOKED_FLESH, 1), 0.2F);
        	GameRegistry.addRecipe(new ItemStack (NetherItems.GOLDEN_BUCKET),
            		"G G",
            		" G ",
            		'G' , Items.GOLD_INGOT);
        	GameRegistry.addRecipe(new ItemStack (NetherItems.GOLDEN_SHEARS),
            		" I",
            		"I ",
            		'I' , Items.GOLD_INGOT);
        	GameRegistry.addRecipe(new ItemStack (NetherItems.SPAWNER_UPGRADE, 3),
            		"NNN",
            		"NWN",
            		"NNN",
            		'W' , NetherItems.AVIS_WING,
            		'N' , NetherItems.NETHERITE_NUGGET);
            GameRegistry.addShapelessRecipe(new ItemStack(Items.FLINT_AND_STEEL), Items.FLINT, NetherItems.NETHERITE_INGOT);
        	GameRegistry.addRecipe(new ItemStack (NetherBlocks.EXTRACTOR),
            		"ESE",
            		"NFN",
            		"ENE",
            		'F' , NetherBlocks.NETHERRACK_FURNACE,
            		'N' , NetherItems.NETHERITE_INGOT,
            		'E' , NetherItems.ECTOPLASM,
            		'S' , NetherItems.SOUL_FRAGMENT);
        	GameRegistry.addRecipe(new ItemStack (NetherBlocks.SPAWNER),
            		"NNN",
            		"NSN",
            		"NNN",
            		'N' , NetherItems.NETHERITE_INGOT,
            		'S' , NetherItems.SOUL_FRAGMENT);
        	GameRegistry.addRecipe(new ItemStack (NetherBlocks.NETHER_SPAWN),
            		"NSN",
            		"SBS",
            		"NSN",
            		'B' , NetherBlocks.SMOOTH_NETHERRACK,
            		'N' , NetherItems.NETHERITE_INGOT,
            		'S' , NetherItems.SOUL_FRAGMENT);
            GameRegistry.addShapelessRecipe(new ItemStack (NetherItems.PURPLE_MUSHROOM_SOUP), Items.BOWL, NetherBlocks.PURPLE_MUSHROOM, NetherBlocks.PURPLE_MUSHROOM, NetherBlocks.PURPLE_MUSHROOM, NetherBlocks.PURPLE_MUSHROOM);
        	GameRegistry.addRecipe(new ItemStack (NetherBlocks.PEAT_BLOCK),
            		"XXX",
            		"XXX",
            		"XXX",
            		'X' , NetherItems.PEAT);
            GameRegistry.addShapelessRecipe(new ItemStack (NetherItems.PEAT, 9), NetherBlocks.PEAT_BLOCK);
        	GameRegistry.addRecipe(new ItemStack (Blocks.TORCH, 4),
            		"P",
            		"S",
            		'P' , NetherItems.PEAT,
            		'S' , Items.STICK);
            GameRegistry.addShapelessRecipe(new ItemStack (Items.FIRE_CHARGE, 3), Items.GUNPOWDER, Items.BLAZE_POWDER, NetherItems.PEAT);
            GameRegistry.addShapelessRecipe(new ItemStack (Items.DYE, 2, 15), NetherItems.PEAT);
        	GameRegistry.addRecipe(new ItemStack (NetherBlocks.SPIKES, 8),
            		" X ",
            		"XXX",
            		'X' , Blocks.NETHERRACK);
        	GameRegistry.addRecipe(new ItemStack(NetherBlocks.SPIKES, 8, 1),
            		" X ",
            		"XXX",
            		'X' , NetherBlocks.DARKSTONE);
        	GameRegistry.addRecipe(new ItemStack(NetherItems.INFERNAL_BREAD),
            		"XXX",
            		'X' , NetherItems.INFERNAL_WHEAT);
        	GameRegistry.addRecipe(new ItemStack(NetherBlocks.INFERNAL_HAY),
            		"XXX",
            		"XXX",
            		"XXX",
            		'X' , NetherItems.INFERNAL_WHEAT);
            GameRegistry.addShapelessRecipe(new ItemStack (NetherItems.INFERNAL_WHEAT, 9), NetherBlocks.INFERNAL_HAY);
    	}
    	
    	if(smelting)
    	{
            GameRegistry.addSmelting(NetherItems.INFERNAL_CHICKEN, new ItemStack(NetherItems.COOKED_INFERNAL_CHICKEN, 1), 0.5F);
            GameRegistry.addSmelting(NetherBlocks.PURPLE_MUSHROOM, new ItemStack(NetherItems.COOKED_PURPLE_MUSHROOM, 1), 0.4F);
            GameRegistry.addSmelting(Blocks.SOUL_SAND, new ItemStack(NetherBlocks.SOUL_GLASS, 1), 0.1F);
            GameRegistry.addSmelting(NetherBlocks.GOLD_ORE, new ItemStack(Items.GOLD_INGOT, 2), 0.7F);
            GameRegistry.addSmelting(NetherBlocks.DIMENSIONAL_ORE, new ItemStack(NetherItems.DIMENSIONAL_DUST, 1), 1.0F);
            GameRegistry.addSmelting(NetherBlocks.SOUL_ORE, new ItemStack(NetherItems.SOUL_FRAGMENT, 2), 0.7F);
            GameRegistry.addSmelting(NetherBlocks.PEAT_ORE, new ItemStack(NetherItems.PEAT), 0.5F);
    	}
    }
}

