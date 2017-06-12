package com.superdextor.adinferos.config;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.superdextor.adinferos.AdInferosReference;

import net.minecraftforge.common.config.Configuration;
import net.minecraftforge.common.config.Property;

public class NetherConfig {
	
	//General
	public static boolean debugMode = true;
	private static final Logger debugLogger = LogManager.getLogger("Ad Inferos [debug]");
	
	//Items
	
	  //Recipes
	
	    public static boolean allowCrafting = true;
	    public static boolean allowSmelting = true;
	    public static boolean allowAltar = true;
	    public static boolean allowExtractor = true;
	    public static boolean recipesNetherWood = true;
	    public static boolean recipesNetherrack = true;
	    public static boolean recipesQuartz = true;
	    public static boolean recipesGlowstone = true;
	    public static boolean recipesObsidian = true;
	    public static boolean recipesWither = true;
	    public static boolean recipesNetherite = true;
	    public static boolean recipesOther = true;

	  //Materials
	
	  //Enchantments
	
	    //Berserk
	    public static int enchIdBerserk = -1;
	    public static int enchBerserkMax = 1;
	    
	    //Curse Protection
	    public static int enchIdCurseProtection = -1;
	    public static int enchCurseProtectionMax = 1;
	
	  //Other
	    public static boolean amuletEffects = true;
	    public static boolean wormholeTravel = true;
	    
	//Blocks
	
	//World
	
	  //Ids
	    
	    public static int dimensionIdAbyss = -1;
	    public static int biomeIDAbyss = -1;
	    public static int biomeIDNether = -1;
	
	  //Generation
	    
	    public static boolean safeWorldGen = true;
	    public static boolean allowOverworldNether = true;
	    public static int netherBiomeWeight = 10;
	    public static float genPeatOreChance = 0.0F;
	    public static int genPeatOreMinSize = 0;
	    public static int genPeatOreMaxSize = 0;
	    public static float genGoldOreChance = 0.0F;
	    public static int genGoldOreMinSize = 0;
	    public static int genGoldOreMaxSize = 0;
	    public static float genSoulOreChance = 0.0F;
	    public static int genSoulOreMinSize = 0;
	    public static int genSoulOreMaxSize = 0;
	    public static float genWitherOreChance = 0.0F;
	    public static int genWitherOreMinSize = 0;
	    public static int genWitherOreMaxSize = 0;
	    public static float genNetheriteOreChance = 0.0F;
	    public static int genNetheriteOreMinSize = 0;
	    public static int genNetheriteOreMaxSize = 0;
	    public static float genDimensionalOreChance = 0.0F;
	    public static int genDimensionalOreMinSize = 0;
	    public static int genDimensionalOreMaxSize = 0;
	    public static float genDungeonsChance = 0.0F;
	    public static float genInfernoTreesChance = 0.0F;
	    public static float genMagmaTreesChance = 0.0F;
	    public static float genPhantomTreesChance = 0.0F;
	    public static float genAshTreesChance = 0.0F;
	    public static float genAcidChance = 0.0F;
	    public static float genPurpleMushroomChance = 0.0F;
	    public static float genLargePurpleMushroomChance = 0.0F;
	    public static float genPiggyHouseChance = 0.0F;
	    public static float genDarkfireChance = 0.0F;
	    public static float genObsidianPyramideChance = 0.0F;
	    public static float genObsidianBeachChance = 0.0F;
	    public static float genSpikesChance = 0.0F;
	    public static float genDimstoneChance = 0.0F;
	    
	  //Other
	    public static boolean abyssPortalTravel = true;
	    public static boolean abyssPortalAnywhere = true;
	    public static boolean abyssPortalBuild = true;
	    public static boolean buildSummoner = true;
	
	//Entities
	
	  //BlackWidow
	    public static int blackWidowSpawnWeight = 0;
	    public static String blackWidowBiomes = "";
	    public static float blackWidowHealth = 0.0F;
	    public static float blackWidowDamage = 0.0F;
	    public static float blackWidowSpeed = 0.0F;
	    public static int blackWidowMaxsize = 0;
	    public static int blackWidowEXP = 8;
	    public static boolean blackWidowDrops = true;
	    public static boolean blackWidowDebuffs = true;
	    public static boolean blackWidowFireResistant = true;
	    public static boolean blackWidowDarkfireResistant = true;
	    public static boolean blackWidowCurseResistant = true;
	    public static boolean blackWidowWitherResistant = true;
	    public static boolean blackWidowAcidResistant = true;
	
	  //Curse
	    public static int curseSpawnWeight = 0;
	    public static String curseBiomes = "";
	    public static float curseHealth = 0.0F;
	    public static float curseDamage = 0.0F;
	    public static float curseSpeed = 0.0F;
	    public static int curseEXP = 8;
	    public static boolean curseDrops = true;
	    public static boolean curseTrail = true;
	    public static boolean curseDebuffs = true;
	    public static boolean curseFireResistant = true;
	    public static boolean curseDarkfireResistant = true;
	    public static boolean curseCurseResistant = true;
	    public static boolean curseWitherResistant = true;
	    public static boolean curseAcidResistant = true;
	
	  //Ghost
	    public static int ghostSpawnWeight = 0;
	    public static String ghostBiomes = "";
	    public static float ghostHealth = 0.0F;
	    public static float ghostDamage = 0.0F;
	    public static float ghostSpeed = 0.0F;
	    public static int ghostEXP = 8;
	    public static boolean ghostDrops = true;
	    public static boolean ghostSpawnFromPlayers = true;
	    public static boolean ghostSpawnFromEntities = true;
	    public static boolean ghostFireResistant = true;
	    public static boolean ghostDarkfireResistant = true;
	    public static boolean ghostCurseResistant = true;
	    public static boolean ghostWitherResistant = true;
	    public static boolean ghostAcidResistant = true;
	
	  //GlowstoneSkeleton
	    public static int glowstoneSkeletonSpawnWeight = 0;
	    public static String glowstoneSkeletonBiomes = "";
	    public static float glowstoneSkeletonHealth = 0.0F;
	    public static float glowstoneSkeletonDamage = 0.0F;
	    public static float glowstoneSkeletonSpeed = 0.0F;
	    public static float glowstoneSkeletonBaby = 0;
	    public static int glowstoneSkeletonEXP = 8;
	    public static boolean glowstoneSkeletonDrops = true;
	    public static boolean glowstoneSkeletonFireResistant = true;
	    public static boolean glowstoneSkeletonDarkfireResistant = true;
	    public static boolean glowstoneSkeletonCurseResistant = true;
	    public static boolean glowstoneSkeletonWitherResistant = true;
	    public static boolean glowstoneSkeletonAcidResistant = true;
	
	  //Herobrine
	    public static int herobrineSpawnWeight = 0;
	    public static String herobrineBiomes = "";
	    
	  //InfernumAvis
	    public static int infernumAvisSpawnWeight = 0;
	    public static String infernumAvisBiomes = "";
	
	  //Sheepman
	    public static int sheepmanSpawnWeight = 0;
	    public static String sheepmanBiomes = "";
	
	  //Phantom
	    public static int phantomSpawnWeight = 0;
	    public static String phantomBiomes = "";
	
	  //Reaper
	    public static int reaperSpawnWeight = 0;
	    public static String reaperBiomes = "";
	
	  //SkeletonHorse
	    public static int skeletonHorseSpawnWeight = 0;
	    public static String skeletonHorseBiomes = "";
	    
	  //Infernal Chicken
	    public static int infernalChickenSpawnWeight = 0;
	    public static String infernalChickenBiomes = "";
	
    public static void initConfig() {
    	
    	final Configuration config = AdInferosReference.config;
    	
		try
		{
			
			config.load();
			updateValues();
 
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
		finally
		{
			config.save();
		}
    }
    
    public static void updateValues() throws Exception {
    	
    	final Configuration config = AdInferosReference.config;
    	
		final String General = config.CATEGORY_GENERAL + config.CATEGORY_SPLITTER + "General";
		final String Items = config.CATEGORY_GENERAL + config.CATEGORY_SPLITTER + "Items";
		final String Blocks = config.CATEGORY_GENERAL + config.CATEGORY_SPLITTER + "Blocks";
		final String World = config.CATEGORY_GENERAL + config.CATEGORY_SPLITTER + "World";
		final String Entities = config.CATEGORY_GENERAL + config.CATEGORY_SPLITTER + "Entities";
		
		config.addCustomCategoryComment(General, "Generic Properties");
		config.addCustomCategoryComment(Items, "Properties related to Items: recipes, materials, ect..");
		config.addCustomCategoryComment(Blocks, "Properties related to Blocks: Hardness, Drops, ect..");
		config.setCategoryRequiresMcRestart(Blocks, true);
		config.addCustomCategoryComment(World, "Properties related to the World: Generation, Ids, ect..");
		config.addCustomCategoryComment(Entities, "Properties related to the Entities: Health, Spawns, ect..");
		
		//General
		
		debugMode = config.getBoolean("1. Debug Mode", General, false, "When enabled: Prints extra log info");
		
		
		//ITEMS
		
		  final String Recipes = Items + config.CATEGORY_SPLITTER + "Recipes";
		  final String Materials = Items + config.CATEGORY_SPLITTER + "Materials";
		  final String Enchantments = Items + config.CATEGORY_SPLITTER + "Enchantments";
		
		  config.addCustomCategoryComment(Recipes, "Enable and Disable Recipes from Ad Inferos");
		  config.addCustomCategoryComment(Materials, "Change Tool/Armor Materials from Ad Inferos");
		  config.addCustomCategoryComment(Enchantments, "Change Enchantments from Ad Inferos");
		
		  config.setCategoryRequiresMcRestart(Recipes, true);
		  config.setCategoryRequiresMcRestart(Materials, true);
		  
		  //Recipes
		  
			allowCrafting = config.getBoolean("1. Mod Crafting", Recipes, true, "All Crafting Recipes from Ad Inferos");
			allowSmelting = config.getBoolean("2. Mod Smelting", Recipes, true, "All Smelting Recipes from Ad Inferos");
			allowAltar = config.getBoolean("3. Altar Usage", Recipes, true, "All Altar Recipes from Ad Inferos");
			allowExtractor = config.getBoolean("4. Extractor Usage", Recipes, true, "All Extractor Recipes from Ad Inferos");
			recipesNetherWood = config.getBoolean("5. Nether-Wood Recipes", Recipes, true, "Recipes using NetherWood");
			recipesNetherrack = config.getBoolean("6. Netherrack Recipes", Recipes, true, "Recipes using Netherrack");
			recipesQuartz = config.getBoolean("7. Quartz Recipes", Recipes, true, "Recipes using Quartz");
			recipesGlowstone = config.getBoolean("8. Glowstone Recipes", Recipes, true, "Recipes using Glowstone");
			recipesObsidian = config.getBoolean("9. Obsidian Recipes", Recipes, true, "Recipes using Obsidian");
			recipesWither = config.getBoolean("A. Wither Recipes", Recipes, true, "Recipes using Wither Gem/Dust");
			recipesNetherite = config.getBoolean("B. Netherite Recipes", Recipes, true, "Recipes using Netherite");
			recipesOther = config.getBoolean("C. Miscellaneous Recipes", Recipes, true, "Other Recipes");
		  
		  //Materials
		  
		  //Enchantments
		  
		    //Berserk
		      final String Berserk = Enchantments + config.CATEGORY_SPLITTER + "Berserk";
			  config.addCustomCategoryComment(Berserk, "Configure: Enchantment Berserk");
			
			  config.get(Berserk, "1. ID", 65).setRequiresMcRestart(true);
			  enchIdBerserk = config.getInt("1. ID", Berserk, 65, 63, 255, "Enchantment Id for Berserk");
			  enchBerserkMax = config.getInt("2. Max Level", Berserk, 3, 1, 10, "Maximum Level for Berserk");
			  
			//CurseProtection
		      final String CurseProtection = Enchantments + config.CATEGORY_SPLITTER + "CurseProtection";
			  config.addCustomCategoryComment(CurseProtection, "Configure: Enchantment Berserk");
			
			  config.get(CurseProtection, "1. ID", 65).setRequiresMcRestart(true);
			  enchIdCurseProtection = config.getInt("1. ID", CurseProtection, 65, 63, 255, "Enchantment Id for CurseProtection");
			  enchCurseProtectionMax = config.getInt("2. Max Level", CurseProtection, 4, 1, 10, "Maximum Level for Berserk");
		
		  //Other
			amuletEffects = config.getBoolean("Amulet Effects", Items, true, "Usage of Amulets");
			wormholeTravel = config.getBoolean("Pocket-Wormhole Travel", Items, true, "Usage of Pocket-Wormholes");
			  
		//BLOCKS
		  
		//WORLD
		  
		  final String WorldIds = World + config.CATEGORY_SPLITTER + "Ids";
		  final String WorldGen = World + config.CATEGORY_SPLITTER + "Generation";
		
		  config.addCustomCategoryComment(WorldIds, "Ids for the World: Biomes, Dimension, ect..");
		  config.addCustomCategoryComment(WorldGen, "Random Features Generated in the World: Trees, Dungeons, ect..");
		
		  config.setCategoryRequiresMcRestart(WorldIds, true);
		  
		  //Ids
		  
			dimensionIdAbyss = config.getInt("1. Abyss Dimension", WorldIds, 10, 2, 99, "Dimension Id for the Abyss");
			biomeIDAbyss = config.getInt("2. Abyss Biome", WorldIds, 93, 40, 255, "Abyss Biome Id");
			biomeIDNether = config.getInt("3. Nether Biome", WorldIds, 94, 40, 255, "Overworld Nether Biome Id");
		  
		  //Generation
			
			config.get(WorldGen, "0. Safe World Generation", true).setRequiresMcRestart(true);
			safeWorldGen = config.getBoolean("0. Safe World Generation", WorldGen, true, "When enabled: Tests for null chucks when Generating (seems to be required for 1.9)");
			config.get(WorldGen, "1. Overworld Nether Biome", true).setRequiresMcRestart(true);
			allowOverworldNether = config.getBoolean("1. Overworld Nether Biome", WorldGen, true, "Generation of the Nether Biome in the Overworld");
			config.get(WorldGen, "2. Nether Biome Weight", 15).setRequiresMcRestart(true);
			netherBiomeWeight = config.getInt("2. Nether Biome Weight", WorldGen, 15, 1, 100, "Biome Weight. Higher number = more likely");
			genGoldOreChance = config.getFloat("3. Gold Ore Chance", WorldGen, 0.05F, 0.0F, 1.0F, "Generation Chance. Higher number = more likely : 0 to disable");
			genGoldOreMinSize = config.getInt("4. Gold Ore Minimum Size", WorldGen, 6, 1, 100, "Minimum Vein Size (amount of blocks)");
			genGoldOreMaxSize = config.getInt("5. Gold Ore Maximum Size", WorldGen, 10, 2, 100, "Maximum Vein Size (amount of blocks)");
			genSoulOreChance = config.getFloat("6. Soul Ore Chance", WorldGen, 0.20F, 0.0F, 1.0F, "Generation Chance. Higher number = more likely : 0 to disable");
			genSoulOreMinSize = config.getInt("7. Soul Ore Minimum Size", WorldGen, 4, 1, 100, "Minimum Vein Size (amount of blocks)");
			genSoulOreMaxSize = config.getInt("8. Soul Ore Maximum Size", WorldGen, 8, 2, 100, "Maximum Vein Size (amount of blocks)");
			genWitherOreChance = config.getFloat("9. Wither Ore Chance", WorldGen, 0.04F, 0.0F, 1.0F, "Generation Chance. Higher number = more likely : 0 to disable");
			genWitherOreMinSize = config.getInt("A. Wither Ore Minimum Size", WorldGen, 4, 1, 100, "Minimum Vein Size (amount of blocks)");
			genWitherOreMaxSize = config.getInt("B. Wither Ore Maximum Size", WorldGen, 8, 2, 100, "Maximum Vein Size (amount of blocks)");
			genNetheriteOreChance = config.getFloat("C. Netherite Ore Chance", WorldGen, 0.02F, 0.0F, 1.0F, "Generation Chance. Higher number = more likely : 0 to disable");
			genNetheriteOreMinSize = config.getInt("D. Netherite Ore Minimum Size", WorldGen, 4, 1, 100, "Minimum Vein Size (amount of blocks)");
			genNetheriteOreMaxSize = config.getInt("E. Netherite Ore Maximum Size", WorldGen, 8, 2, 100, "Maximum Vein Size (amount of blocks)");
			genDimensionalOreChance = config.getFloat("F. Dimensional Ore Chance", WorldGen, 0.03F, 0.0F, 1.0F, "Generation Chance. Higher number = more likely : 0 to disable");
			genDimensionalOreMinSize = config.getInt("G. Dimensional Ore Minimum Size", WorldGen, 4, 1, 100, "Minimum Vein Size (amount of blocks)");
			genDimensionalOreMaxSize = config.getInt("H. Dimensional Ore Maximum Size", WorldGen, 8, 2, 100, "Maximum Vein Size (amount of blocks)");
			genDungeonsChance = config.getFloat("I. Nether Dungeons Chance", WorldGen, 0.2F, 0.0F, 1.0F, "Generation Chance. Higher number = more likely : 0 to disable");
			genInfernoTreesChance = config.getFloat("J. Inferno Trees Chance", WorldGen, 0.4F, 0.0F, 1.0F, "Generation Chance. Higher number = more likely : 0 to disable");
			genMagmaTreesChance = config.getFloat("K. Magma Trees Chance", WorldGen, 0.5F, 0.0F, 1.0F, "Generation Chance. Higher number = more likely : 0 to disable");
			genPhantomTreesChance = config.getFloat("L. Phantom Trees Chance", WorldGen, 0.2F, 0.0F, 1.0F, "Generation Chance. Higher number = more likely : 0 to disable");
			genAshTreesChance = config.getFloat("M. Ash Trees Chance", WorldGen, 0.3F, 0.0F, 1.0F, "Generation Chance. Higher number = more likely : 0 to disable");
			genAcidChance = config.getFloat("N. Acid Lakes Chance", WorldGen, 0.4F, 0.0F, 1.0F, "Generation Chance. Higher number = more likely : 0 to disable");
			genPurpleMushroomChance = config.getFloat("O. Purple Mushrooms Chance", WorldGen, 0.4F, 0.0F, 1.0F, "Generation Chance. Higher number = more likely : 0 to disable");
			genLargePurpleMushroomChance = config.getFloat("P. Large Purple Mushrooms Chance", WorldGen, 0.1F, 0.0F, 1.0F, "Generation Chance. Higher number = more likely : 0 to disable");
			genPiggyHouseChance = config.getFloat("Q. Pigman House Chance", WorldGen, 0.2F, 0.0F, 1.0F, "Generation Chance. Higher number = more likely : 0 to disable");
			genDarkfireChance = config.getFloat("R. Darkfire Chance", WorldGen, 0.6F, 0.0F, 1.0F, "Generation Chance. Higher number = more likely : 0 to disable");
			genObsidianPyramideChance = config.getFloat("S. Obsidian Pyramides Chance", WorldGen, 0.1F, 0.0F, 1.0F, "Generation Chance. Higher number = more likely : 0 to disable");
			genObsidianBeachChance = config.getFloat("T. Obsidian Beaches Chance", WorldGen, 0.3F, 0.0F, 1.0F, "Generation Chance. Higher number = more likely : 0 to disable");
			genSpikesChance = config.getFloat("U. Netherrack Spikes Chance", WorldGen, 0.6F, 0.0F, 1.0F, "Generation Chance. Higher number = more likely : 0 to disable");
			genDimstoneChance = config.getFloat("V. Dimstone Chance", WorldGen, 0.12F, 0.0F, 1.0F, "Generation Chance. Higher number = more likely : 0 to disable");
			genPeatOreChance = config.getFloat("W. Peat Ore Chance", WorldGen, 0.08F, 0.0F, 1.0F, "Generation Chance. Higher number = more likely : 0 to disable");
			genPeatOreMinSize = config.getInt("X. Peat Ore Minimum Size", WorldGen, 8, 1, 100, "Minimum Vein Size (amount of blocks)");
			genPeatOreMaxSize = config.getInt("Y. Peat Ore Maximum Size", WorldGen, 16, 2, 100, "Maximum Vein Size (amount of blocks)");
			
		  //Other
			
			abyssPortalTravel = config.getBoolean("1. Abyss Portal Travel", World, true, "Usage of the Abyss Portal to enter the Abyss");
			abyssPortalAnywhere = config.getBoolean("2. Abyss Portals Anywhere", World, false, "When enabled, the Portal won't remove it's self if it isn't within a Frame");
			abyssPortalBuild = config.getBoolean("3. Build Abyss Portal", World, true, "When Disabled: Witherdust cannot be used to open the Abyss Portal");
			buildSummoner = config.getBoolean("4. Build Summoner", World, true, "When disabled: The Summoner cannot be created via Gold/Obsidian/Fire/Dust");
			
		//ENTITIES
		  
		  final String EntityBlackWidow = Entities + config.CATEGORY_SPLITTER + "BlackWidow";
		  final String EntityCurse = Entities + config.CATEGORY_SPLITTER + "Curse";
		  final String EntityGhost = Entities + config.CATEGORY_SPLITTER + "Ghost";
		  final String EntityGlowstoneSkeleton = Entities + config.CATEGORY_SPLITTER + "GlowstoneSkeleton";
		  final String EntityHerobrine = Entities + config.CATEGORY_SPLITTER + "Herobrine";
		  final String EntityInfernumAvis = Entities + config.CATEGORY_SPLITTER + "InfernumAvis";
		  final String EntitySheepman = Entities + config.CATEGORY_SPLITTER + "Sheepman";
		  final String EntityPhantom = Entities + config.CATEGORY_SPLITTER + "Phantom";
		  final String EntityReaper = Entities + config.CATEGORY_SPLITTER + "Reaper";
		  final String EntitySkeletonHorse = Entities + config.CATEGORY_SPLITTER + "SkeletonHorse";
		  final String EntityInfernalChicken = Entities + config.CATEGORY_SPLITTER + "InfernalChicken";
		  
		  config.addCustomCategoryComment(EntityBlackWidow, "Change Stats for the BlackWidow");
		  config.addCustomCategoryComment(EntityCurse, "Change Stats for the Curse");
		  config.addCustomCategoryComment(EntityGhost, "Change Stats for the Ghost");
		  config.addCustomCategoryComment(EntityGlowstoneSkeleton, "Change Stats for the GlowstoneSkeleton");
		  config.addCustomCategoryComment(EntityHerobrine, "Change Stats for the Herobrine");
		  config.addCustomCategoryComment(EntityInfernumAvis, "Change Stats for the InfernumAvis");
		  config.addCustomCategoryComment(EntitySheepman, "Change Stats for the Sheepman");
		  config.addCustomCategoryComment(EntityPhantom, "Change Stats for the Phantom");
		  config.addCustomCategoryComment(EntityReaper, "Change Stats for the Reaper");
		  config.addCustomCategoryComment(EntitySkeletonHorse, "Change Stats for the SkeletonHorse");
		  config.addCustomCategoryComment(EntityInfernalChicken, "Change Stats for the InfernalChicken");
		  
		  //BLACK WIDOW
			config.get(EntityBlackWidow, "1. Spawn Weight", 50).setRequiresMcRestart(true);
			config.get(EntityBlackWidow, "2. Spawnable Areas", "NETHER ABYSS OVERWORLDN").setRequiresMcRestart(true);
			blackWidowSpawnWeight = config.getInt("1. Spawn Weight", EntityBlackWidow, 50, 0, 500, "Spawn Weight: Chance of this Entity be selected when the game is looking for a creature to Spawn. Higher = more likely : 0 to disable");
			blackWidowBiomes = config.getString("2. Spawnable Areas", EntityBlackWidow, "NETHER ABYSS OVERWORLDN", "Spawn Areas: A list of locations the Entity can spawn. Keys: NETHER, ABYSS, OVERWORLDN");
			blackWidowHealth = config.getFloat("3. MaxHealth", EntityBlackWidow, 20.0F, 0.5F, 1024.0F, "MaxHealth: the about of Health the entity spawns with, can change via Variants");
			blackWidowDamage = config.getFloat("4. AttackDamage", EntityBlackWidow, 5.0F, 0.0F, 2048.0F, "AttackDamage: the raw Damage a entity deals on Attack, can change via Variants");
			blackWidowSpeed = config.getFloat("5. MovementSpeed", EntityBlackWidow, 0.30000001192092896F, 0.0F, 1024.0F, "MovementSpeed: the Speed of the entity's movement, can change via Variants");
			blackWidowMaxsize = config.getInt("6. Max Size", EntityBlackWidow, 2, 0, 25, "Max Size: the natural maximum size for the BlackWidow (how big the spider is)");
			blackWidowEXP = config.getInt("7. EXP", EntityBlackWidow, 7, 0, 255, "EXP: about of experience dropped by a entity on Death");
			blackWidowDrops = config.getBoolean("8. Drops", EntityBlackWidow, true, "Drops: when disabled: The Entity won't drop Items on Death");
			blackWidowDebuffs = config.getBoolean("9. Debuffs", EntityBlackWidow, true, "Debuffs: when enabled: Black Widow will deal Debuffs to their Attack Targets");
			blackWidowFireResistant = config.getBoolean("A. FireResistant", EntityBlackWidow, true, "FireResistant: when enabled: the entity isn't damaged from Fire/Lava");
			blackWidowDarkfireResistant = config.getBoolean("B. DarkfireResistant", EntityBlackWidow, true, "DarkfireResistant: when enabled: the entity isn't damaged from Darkfire");
			blackWidowCurseResistant = config.getBoolean("C. CurseResistant", EntityBlackWidow, true, "CurseResistant: when enabled: the entity isn't damaged from Curses");
			blackWidowWitherResistant = config.getBoolean("D. WitherResistant", EntityBlackWidow, true, "WitherResistant: when enabled: the entity isn't damaged from Wither");
			blackWidowAcidResistant = config.getBoolean("E. AcidResistant", EntityBlackWidow, false, "AcidResistant: when enabled: the entity isn't damaged from Acid");
			
		  //CURSE
			config.get(EntityCurse, "1. Spawn Weight", 15).setRequiresMcRestart(true);
			config.get(EntityCurse, "2. Spawnable Areas", "NETHER ABYSS OVERWORLDN").setRequiresMcRestart(true);
			curseSpawnWeight = config.getInt("1. Spawn Weight", EntityCurse, 15, 0, 500, "Spawn Weight: Chance of this Entity be selected when the game is looking for a creature to Spawn. Higher = more likely : 0 to disable");
			curseBiomes = config.getString("2. Spawnable Areas", EntityCurse, "NETHER ABYSS OVERWORLDN", "Spawn Areas: A list of locations the Entity can spawn. Keys: NETHER, ABYSS, OVERWORLDN");
			curseHealth = config.getFloat("3. MaxHealth", EntityCurse, 18.0F, 0.5F, 1024.0F, "MaxHealth: the about of Health the entity spawns with, can change via Variants");
			curseDamage = config.getFloat("4. AttackDamage", EntityCurse, 4.0F, 0.0F, 2048.0F, "AttackDamage: the raw Damage a entity deals on Attack, can change via Variants");
			curseSpeed = config.getFloat("5. MovementSpeed", EntityCurse, 0.3F, 0.0F, 1024.0F, "MovementSpeed: the Speed of the entity's movement, can change via Variants");
			curseEXP = config.getInt("6. EXP", EntityCurse, 8, 0, 255, "EXP: about of experience dropped by a entity on Death");
			curseDrops = config.getBoolean("7. Drops", EntityCurse, true, "Drops: when disabled: The Entity won't drop Items on Death");
			curseTrail = config.getBoolean("8. Trail", EntityCurse, true, "Trail: when enabled: Curses will leave behind Curse Blocks");
			curseDebuffs = config.getBoolean("9. Debuffs", EntityCurse, true, "Debuffs: when enabled: Curse will deal Debuffs to their Attack Targets");
			curseFireResistant = config.getBoolean("A. FireResistant", EntityCurse, true, "FireResistant: when enabled: the entity isn't damaged from Fire/Lava");
			curseDarkfireResistant = config.getBoolean("B. DarkfireResistant", EntityCurse, false, "DarkfireResistant: when enabled: the entity isn't damaged from Darkfire");
			curseCurseResistant = config.getBoolean("C. CurseResistant", EntityCurse, true, "CurseResistant: when enabled: the entity isn't damaged from Curses");
			curseWitherResistant = config.getBoolean("D. WitherResistant", EntityCurse, false, "WitherResistant: when enabled: the entity isn't damaged from Wither");
			curseAcidResistant = config.getBoolean("E. AcidResistant", EntityCurse, false, "AcidResistant: when enabled: the entity isn't damaged from Acid");
			
		  //GHOST
			config.get(EntityGhost, "1. Spawn Weight", 0).setRequiresMcRestart(true);
			config.get(EntityGhost, "2. Spawnable Areas", "").setRequiresMcRestart(true);
			ghostSpawnWeight = config.getInt("1. Spawn Weight", EntityGhost, 0, 0, 500, "Spawn Weight: Chance of this Entity be selected when the game is looking for a creature to Spawn. Higher = more likely : 0 to disable");
			ghostBiomes = config.getString("2. Spawnable Areas", EntityGhost, "", "Spawn Areas: A list of locations the Entity can spawn. Keys: NETHER, ABYSS, OVERWORLDN");
			ghostHealth = config.getFloat("3. MaxHealth", EntityGhost, 20.0F, 0.5F, 1024.0F, "MaxHealth: the about of Health the entity spawns with, can change via Variants");
			ghostDamage = config.getFloat("4. AttackDamage", EntityGhost, 5.0F, 0.0F, 2048.0F, "AttackDamage: the raw Damage a entity deals on Attack, can change via Variants");
			ghostSpeed = config.getFloat("5. MovementSpeed", EntityGhost, 0.33500000417232513F, 0.0F, 1024.0F, "MovementSpeed: the Speed of the entity's movement, can change via Variants");
			ghostEXP = config.getInt("6. EXP", EntityGhost, 11, 0, 255, "EXP: about of experience dropped by a entity on Death");
			ghostDrops = config.getBoolean("7. Drops", EntityGhost, true, "Drops: when disabled: The Entity won't drop Items on Death");
			ghostSpawnFromPlayers = config.getBoolean("8. Spawn from Players", EntityGhost, true, "Spawn from Players: when enabled: A Ghost can spawn when a Player Dies");
			ghostSpawnFromEntities = config.getBoolean("9. Spawn from Entities", EntityGhost, true, "Spawn from Entities: when enabled: A Ghost has a chance to Spawn when a Entity Dies");
			ghostFireResistant = config.getBoolean("A. FireResistant", EntityGhost, true, "FireResistant: when enabled: the entity isn't damaged from Fire/Lava");
			ghostDarkfireResistant = config.getBoolean("B. DarkfireResistant", EntityGhost, true, "DarkfireResistant: when enabled: the entity isn't damaged from Darkfire");
			ghostCurseResistant = config.getBoolean("C. CurseResistant", EntityGhost, true, "CurseResistant: when enabled: the entity isn't damaged from Curses");
			ghostWitherResistant = config.getBoolean("D. WitherResistant", EntityGhost, true, "WitherResistant: when enabled: the entity isn't damaged from Wither");
			ghostAcidResistant = config.getBoolean("E. AcidResistant", EntityGhost, true, "AcidResistant: when enabled: the entity isn't damaged from Acid");
			
		  //GLOWSTONE SKELETON
			config.get(EntityGlowstoneSkeleton, "1. Spawn Weight", 30).setRequiresMcRestart(true);
			config.get(EntityGlowstoneSkeleton, "2. Spawnable Areas", "NETHER ABYSS OVERWORLDN").setRequiresMcRestart(true);
			glowstoneSkeletonSpawnWeight = config.getInt("1. Spawn Weight", EntityGlowstoneSkeleton, 30, 0, 500, "Spawn Weight: Chance of this Entity be selected when the game is looking for a creature to Spawn. Higher = more likely : 0 to disable");
		    glowstoneSkeletonBiomes = config.getString("2. Spawnable Areas", EntityGlowstoneSkeleton, "NETHER ABYSS OVERWORLDN", "Spawn Areas: A list of locations the Entity can spawn. Keys: NETHER, ABYSS, OVERWORLDN");
		    glowstoneSkeletonHealth = config.getFloat("3. MaxHealth", EntityGlowstoneSkeleton, 20.0F, 0.5F, 1024.0F, "MaxHealth: the about of Health the entity spawns with, can change via Variants");
		    glowstoneSkeletonDamage = config.getFloat("4. AttackDamage", EntityGlowstoneSkeleton, 4.0F, 0.0F, 2048.0F, "AttackDamage: the raw Damage a entity deals on Attack, can change via Variants");
		    glowstoneSkeletonSpeed = config.getFloat("5. MovementSpeed", EntityGlowstoneSkeleton, 0.25F, 0.0F, 1024.0F, "MovementSpeed: the Speed of the entity's movement, can change via Variants");
		    glowstoneSkeletonBaby = config.getFloat("6. Baby Chance", EntityGlowstoneSkeleton, 0.05F, 0.0F, 1.0F, "Baby Chance: The chance of the Entity spawning as a Baby : 1 = always, 0 = never");
		    glowstoneSkeletonEXP = config.getInt("7. EXP", EntityGlowstoneSkeleton, 10, 0, 255, "EXP: about of experience dropped by a entity on Death");
		    glowstoneSkeletonDrops = config.getBoolean("8. Drops", EntityGlowstoneSkeleton, true, "Drops: when disabled: The Entity won't drop Items on Death");
			glowstoneSkeletonFireResistant = config.getBoolean("9. FireResistant", EntityGlowstoneSkeleton, true, "FireResistant: when enabled: the entity isn't damaged from Fire/Lava");
			glowstoneSkeletonDarkfireResistant = config.getBoolean("A. DarkfireResistant", EntityGlowstoneSkeleton, false, "DarkfireResistant: when enabled: the entity isn't damaged from Darkfire");
			glowstoneSkeletonCurseResistant = config.getBoolean("B. CurseResistant", EntityGlowstoneSkeleton, false, "CurseResistant: when enabled: the entity isn't damaged from Curses");
			glowstoneSkeletonWitherResistant = config.getBoolean("C. WitherResistant", EntityGlowstoneSkeleton, false, "WitherResistant: when enabled: the entity isn't damaged from Wither");
			glowstoneSkeletonAcidResistant = config.getBoolean("D. AcidResistant", EntityGlowstoneSkeleton, false, "AcidResistant: when enabled: the entity isn't damaged from Acid");
		    
		  //HEROBRINE
			config.get(EntityHerobrine, "1. Spawn Weight", 0).setRequiresMcRestart(true);
			config.get(EntityHerobrine, "2. Spawnable Areas", "").setRequiresMcRestart(true);
			herobrineSpawnWeight = config.getInt("1. Spawn Weight", EntityHerobrine, 0, 0, 500, "Spawn Weight: Chance of this Entity be selected when the game is looking for a creature to Spawn. Higher = more likely : 0 to disable");
			herobrineBiomes = config.getString("2. Spawnable Areas", EntityHerobrine, "", "Spawn Areas: A list of locations the Entity can spawn. Keys: NETHER, ABYSS, OVERWORLDN");
			
		  //AVIS
			config.get(EntityInfernumAvis, "1. Spawn Weight", 0).setRequiresMcRestart(true);
			config.get(EntityInfernumAvis, "2. Spawnable Areas", "").setRequiresMcRestart(true);
			infernumAvisSpawnWeight = config.getInt("1. Spawn Weight", EntityInfernumAvis, 0, 0, 500, "Spawn Weight: Chance of this Entity be selected when the game is looking for a creature to Spawn. Higher = more likely : 0 to disable");
			infernumAvisBiomes = config.getString("2. Spawnable Areas", EntityInfernumAvis, "", "Spawn Areas: A list of locations the Entity can spawn. Keys: NETHER, ABYSS, OVERWORLDN");
			
		  //SHEEPMAN
			config.get(EntitySheepman, "1. Spawn Weight", 30).setRequiresMcRestart(true);
			config.get(EntitySheepman, "2. Spawnable Areas", "NETHER").setRequiresMcRestart(true);
			sheepmanSpawnWeight = config.getInt("1. Spawn Weight", EntitySheepman, 30, 0, 500, "Spawn Weight: Chance of this Entity be selected when the game is looking for a creature to Spawn. Higher = more likely : 0 to disable");
			sheepmanBiomes = config.getString("2. Spawnable Areas", EntitySheepman, "NETHER", "Spawn Areas: A list of locations the Entity can spawn. Keys: NETHER, ABYSS, OVERWORLDN");
			
		  //PHANTOM
			config.get(EntityPhantom, "1. Spawn Weight", 50).setRequiresMcRestart(true);
			config.get(EntityPhantom, "2. Spawnable Areas", "ABYSS").setRequiresMcRestart(true);
			phantomSpawnWeight = config.getInt("1. Spawn Weight", EntityPhantom, 50, 0, 500, "Spawn Weight: Chance of this Entity be selected when the game is looking for a creature to Spawn. Higher = more likely : 0 to disable");
			phantomBiomes = config.getString("2. Spawnable Areas", EntityPhantom, "ABYSS", "Spawn Areas: A list of locations the Entity can spawn. Keys: NETHER, ABYSS, OVERWORLDN");
			
		  //REAPER
			config.get(EntityReaper, "1. Spawn Weight", 6).setRequiresMcRestart(true);
			config.get(EntityReaper, "2. Spawnable Areas", "NETHER ABYSS").setRequiresMcRestart(true);
			reaperSpawnWeight = config.getInt("1. Spawn Weight", EntityReaper, 6, 0, 500, "Spawn Weight: Chance of this Entity be selected when the game is looking for a creature to Spawn. Higher = more likely : 0 to disable");
			reaperBiomes = config.getString("2. Spawnable Areas", EntityReaper, "NETHER ABYSS", "Spawn Areas: A list of locations the Entity can spawn. Keys: NETHER, ABYSS, OVERWORLDN");
			
		  //SKELETONHORSE
			config.get(EntitySkeletonHorse, "1. Spawn Weight", 30).setRequiresMcRestart(true);
			config.get(EntitySkeletonHorse, "2. Spawnable Areas", "NETHER ABYSS").setRequiresMcRestart(true);
			skeletonHorseSpawnWeight = config.getInt("1. Spawn Weight", EntitySkeletonHorse, 30, 0, 500, "Spawn Weight: Chance of this Entity be selected when the game is looking for a creature to Spawn. Higher = more likely : 0 to disable");
			skeletonHorseBiomes = config.getString("2. Spawnable Areas", EntitySkeletonHorse, "NETHER ABYSS", "Spawn Areas: A list of locations the Entity can spawn. Keys: NETHER, ABYSS, OVERWORLDN");
			
		  //INFERNAL CHICKEN
			config.get(EntityInfernalChicken, "1. Spawn Weight", 40).setRequiresMcRestart(true);
			config.get(EntityInfernalChicken, "2. Spawnable Areas", "NETHER").setRequiresMcRestart(true);
			infernalChickenSpawnWeight = config.getInt("1. Spawn Weight", EntityInfernalChicken, 40, 0, 500, "Spawn Weight: Chance of this Entity be selected when the game is looking for a creature to Spawn. Higher = more likely : 0 to disable");
			infernalChickenBiomes = config.getString("2. Spawnable Areas", EntityInfernalChicken, "NETHER", "Spawn Areas: A list of locations the Entity can spawn. Keys: NETHER, ABYSS, OVERWORLDN");
    }
    
    public static void printDebugInfo(String info) {
    	if(debugMode) {
        	debugLogger.info(info);
    	}
    }
}
