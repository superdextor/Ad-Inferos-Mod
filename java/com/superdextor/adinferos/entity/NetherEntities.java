package com.superdextor.adinferos.entity;

import java.util.ArrayList;

import com.superdextor.adinferos.AdInferosCore;
import com.superdextor.adinferos.AdInferosReference;
import com.superdextor.adinferos.AdInferosTab;
import com.superdextor.adinferos.config.NetherConfig;
import com.superdextor.adinferos.entity.monster.EntityBlackWidow;
import com.superdextor.adinferos.entity.monster.EntityCurse;
import com.superdextor.adinferos.entity.monster.EntityDimstoneSkeleton;
import com.superdextor.adinferos.entity.monster.EntityGhost;
import com.superdextor.adinferos.entity.monster.EntityGlowstoneSkeleton;
import com.superdextor.adinferos.entity.monster.EntityHerobrine;
import com.superdextor.adinferos.entity.monster.EntityHerobrineClone;
import com.superdextor.adinferos.entity.monster.EntityInfernumAvis;
import com.superdextor.adinferos.entity.monster.EntityObsidianSheepman;
import com.superdextor.adinferos.entity.monster.EntityPhantom;
import com.superdextor.adinferos.entity.monster.EntityReaper;
import com.superdextor.adinferos.entity.monster.EntitySkeletonHorse;
import com.superdextor.adinferos.entity.monster.EntitySummoner;
import com.superdextor.adinferos.entity.other.EntityInfernalChicken;
import com.superdextor.adinferos.entity.other.EntityNetherArrow;
import com.superdextor.adinferos.init.NetherItems;
import com.superdextor.thinkbigcore.helpers.EntityHelper;
import com.superdextor.thinkbigcore.helpers.RegistryHelper;
import com.superdextor.thinkbigcore.items.ItemCustomSpawnEgg;
import com.superdextor.thinkbigcore.items.ItemCustomSpawnEgg.CustomEggEntry;

import net.minecraft.entity.EnumCreatureType;
import net.minecraft.init.Biomes;
import net.minecraft.world.biome.Biome;

public class NetherEntities {
	
	private static final Biome[] hellSpawns = new Biome[] {Biomes.HELL};
	
    public static void register() {
    	String modid = AdInferosReference.MOD_ID;
    	EntityHelper.createEntity(EntityBlackWidow.class, 0, modid, "black_widow", AdInferosCore.modInstance);
    	EntityHelper.createEntity(EntityReaper.class, 1, modid, "reaper", AdInferosCore.modInstance);
    	EntityHelper.createEntity(EntityObsidianSheepman.class, 2, modid, "obsidian_sheepman", AdInferosCore.modInstance);
    	EntityHelper.createEntity(EntityCurse.class, 3, modid, "curse", AdInferosCore.modInstance);
    	EntityHelper.createEntity(EntityGlowstoneSkeleton.class, 4, modid, "glowstone_skeleton", AdInferosCore.modInstance);
    	EntityHelper.createEntity(EntityDimstoneSkeleton.class, 5, modid, "dimstone_skeleton", AdInferosCore.modInstance);
    	EntityHelper.createEntity(EntitySkeletonHorse.class, 6, modid, "skeleton_horse", AdInferosCore.modInstance);
    	EntityHelper.createEntity(EntityPhantom.class, 7, modid, "phantom", AdInferosCore.modInstance);
    	EntityHelper.createEntity(EntityNetherArrow.class, 8, modid, "arrow", AdInferosCore.modInstance, 60, 1, true);
    	EntityHelper.createEntity(EntityInfernumAvis.class, 9, modid, "infernum_avis", AdInferosCore.modInstance);
    	EntityHelper.createEntity(EntityHerobrine.class, 10, modid, "herobrine", AdInferosCore.modInstance);
    	EntityHelper.createEntity(EntityHerobrineClone.class, 11, modid, "herobrine_clone", AdInferosCore.modInstance);
    	EntityHelper.createEntity(EntitySummoner.class, 12, modid, "summoner", AdInferosCore.modInstance);
    	EntityHelper.createEntity(EntityInfernalChicken.class, 13, modid, "infernal_chicken", AdInferosCore.modInstance);
    	EntityHelper.createEntity(EntityGhost.class, 14, modid, "ghost", AdInferosCore.modInstance);
    	if(NetherConfig.blackWidowSpawnWeight > 0 && NetherConfig.blackWidowBiomes.contains("NETHER")) {
        	EntityHelper.addSpawn(EntityBlackWidow.class, EnumCreatureType.MONSTER, hellSpawns, NetherConfig.blackWidowSpawnWeight, 1, 3);
    	}
    	if(NetherConfig.curseSpawnWeight > 0 && NetherConfig.curseBiomes.contains("NETHER")) {
        	EntityHelper.addSpawn(EntityCurse.class, EnumCreatureType.MONSTER, hellSpawns, NetherConfig.curseSpawnWeight, 1, 3);
    	}
    	if(NetherConfig.ghostSpawnWeight > 0 && NetherConfig.ghostBiomes.contains("NETHER")) {
        	EntityHelper.addSpawn(EntityGhost.class, EnumCreatureType.MONSTER, hellSpawns, NetherConfig.ghostSpawnWeight, 1, 3);
    	}
    	if(NetherConfig.glowstoneSkeletonSpawnWeight > 0 && NetherConfig.glowstoneSkeletonBiomes.contains("NETHER")) {
        	EntityHelper.addSpawn(EntityGlowstoneSkeleton.class, EnumCreatureType.MONSTER, hellSpawns, NetherConfig.glowstoneSkeletonSpawnWeight, 1, 5);
    	}
    	if(NetherConfig.herobrineSpawnWeight > 0 && NetherConfig.herobrineBiomes.contains("NETHER")) {
        	EntityHelper.addSpawn(EntityHerobrine.class, EnumCreatureType.MONSTER, hellSpawns, NetherConfig.herobrineSpawnWeight, 1, 1);
    	}
    	if(NetherConfig.infernumAvisSpawnWeight > 0 && NetherConfig.infernumAvisBiomes.contains("NETHER")) {
        	EntityHelper.addSpawn(EntityInfernumAvis.class, EnumCreatureType.MONSTER, hellSpawns, NetherConfig.infernumAvisSpawnWeight, 1, 1);
    	}
    	if(NetherConfig.infernalChickenSpawnWeight > 0 && NetherConfig.infernalChickenBiomes.contains("NETHER")) {
        	EntityHelper.addSpawn(EntityInfernalChicken.class, EnumCreatureType.CREATURE, hellSpawns, NetherConfig.infernalChickenSpawnWeight, 1, 4);
    	}
    	if(NetherConfig.phantomSpawnWeight > 0 && NetherConfig.phantomBiomes.contains("NETHER")) {
        	EntityHelper.addSpawn(EntityPhantom.class, EnumCreatureType.CREATURE, hellSpawns, NetherConfig.phantomSpawnWeight, 1, 3);
    	}
    	if(NetherConfig.reaperSpawnWeight > 0 && NetherConfig.reaperBiomes.contains("NETHER")) {
        	EntityHelper.addSpawn(EntityReaper.class, EnumCreatureType.CREATURE, hellSpawns, NetherConfig.reaperSpawnWeight, 1, 3);
    	}
    	if(NetherConfig.sheepmanSpawnWeight > 0 && NetherConfig.sheepmanBiomes.contains("NETHER")) {
        	EntityHelper.addSpawn(EntityObsidianSheepman.class, EnumCreatureType.CREATURE, hellSpawns, NetherConfig.sheepmanSpawnWeight, 8, 18);
    	}
    	if(NetherConfig.skeletonHorseSpawnWeight > 0 && NetherConfig.skeletonHorseBiomes.contains("NETHER")) {
        	EntityHelper.addSpawn(EntitySkeletonHorse.class, EnumCreatureType.CREATURE, hellSpawns, NetherConfig.skeletonHorseSpawnWeight, 1, 3);
    	}
    	
		NetherConfig.printDebugInfo("Registered Entities");
    }
    
    public static void initSpawnEgg() {
    	NetherItems.SPAWN_EGG = new ItemCustomSpawnEgg(buildSpawnEgg()).setUnlocalizedName("spawn_egg").setCreativeTab(AdInferosTab.AI_MOBS);
    	RegistryHelper.registerItem(NetherItems.SPAWN_EGG, AdInferosReference.MOD_ID);
    }
    
	private static ArrayList<CustomEggEntry>  buildSpawnEgg() {
		ArrayList<CustomEggEntry> entries = new ArrayList<CustomEggEntry>();
		entries.add(new CustomEggEntry(EntityBlackWidow.class, 0x4A2012, 0x25282E));
		entries.add(new CustomEggEntry(EntityReaper.class, 0x0, 0x6D6D55));
		entries.add(new CustomEggEntry(EntityObsidianSheepman.class, 0x1D0C38, 0xFC8217));
		entries.add(new CustomEggEntry(EntityCurse.class, 0xC53FEB, 0x731133));
		entries.add(new CustomEggEntry(EntityGlowstoneSkeleton.class, 0xFFF45C, 0xF5C731));
		entries.add(new CustomEggEntry(EntityDimstoneSkeleton.class, 0x0F0F0F, 0x212121));
		entries.add(new CustomEggEntry(EntitySkeletonHorse.class, 0xDEDEDE, 0x969696));
		entries.add(new CustomEggEntry(EntityPhantom.class, 0x121212, 0xC41616));
		entries.add(new CustomEggEntry(EntityInfernumAvis.class, 0x8C3C89, 0xC29A55));
		entries.add(new CustomEggEntry(EntityInfernalChicken.class, 0xDA3636, 0xFF4800));
		entries.add(new CustomEggEntry(EntityGhost.class, 0xf7f6ed, 0xffffff));
		return entries;
	}
}
