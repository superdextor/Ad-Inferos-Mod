package com.superdextor.adinferos;

import java.util.Random;

import com.superdextor.adinferos.config.NetherConfig;
import com.superdextor.adinferos.init.NetherBlocks;
import com.superdextor.adinferos.world.BiomeAbyss;
import com.superdextor.adinferos.world.BiomeNether;
import com.superdextor.adinferos.worldgen.WorldGenAcidLakes;
import com.superdextor.adinferos.worldgen.WorldGenAshTree;
import com.superdextor.adinferos.worldgen.WorldGenBigPurpleMushroom;
import com.superdextor.adinferos.worldgen.WorldGenDarkFire;
import com.superdextor.adinferos.worldgen.WorldGenDimstone;
import com.superdextor.adinferos.worldgen.WorldGenInfernoTree;
import com.superdextor.adinferos.worldgen.WorldGenMagmaTree;
import com.superdextor.adinferos.worldgen.WorldGenNetherDungeon;
import com.superdextor.adinferos.worldgen.WorldGenObsidianBeach;
import com.superdextor.adinferos.worldgen.WorldGenObsidianPyramide;
import com.superdextor.adinferos.worldgen.WorldGenPiggyHouse;
import com.superdextor.adinferos.worldgen.WorldGenPurpleMushrooms;
import com.superdextor.adinferos.worldgen.WorldGenSpikes;
import com.superdextor.thinkbigcore.worldgen.WorldUtilities;

import net.minecraft.block.Block;
import net.minecraft.block.state.pattern.BlockMatcher;
import net.minecraft.init.Blocks;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraft.world.biome.Biome;
import net.minecraft.world.chunk.IChunkGenerator;
import net.minecraft.world.chunk.IChunkProvider;
import net.minecraft.world.gen.feature.WorldGenMinable;
import net.minecraftforge.fml.common.IWorldGenerator;

public class NetherGeneration implements IWorldGenerator {
	
    public static final Biome abyss = new BiomeAbyss();
    public static final Biome nether = new BiomeNether();
	
	public NetherGeneration() {
		
		String modId = AdInferosReference.MOD_ID;
		WorldUtilities.register(modId, "netherrack_spikes", new WorldGenSpikes());
		WorldUtilities.register(modId, "dark_fire", new WorldGenDarkFire());
		WorldUtilities.register(modId, "acid_lakes", new WorldGenAcidLakes());
		WorldUtilities.register(modId, "large_purple_mushroom", new WorldGenBigPurpleMushroom());
		WorldUtilities.register(modId, "dimstone", new WorldGenDimstone());
		WorldUtilities.register(modId, "nether_dungeon", new WorldGenNetherDungeon());
		WorldUtilities.register(modId, "inferno_tree", new WorldGenInfernoTree(true));
		WorldUtilities.register(modId, "magma_tree", new WorldGenMagmaTree(true, true));
		WorldUtilities.register(modId, "ash_tree", new WorldGenAshTree(true));
		WorldUtilities.register(modId, "obsidian_beach", new WorldGenObsidianBeach());
		WorldUtilities.register(modId, "piggy_house", new WorldGenPiggyHouse());
		WorldUtilities.register(modId, "obsidian_pyramide", new WorldGenObsidianPyramide());
		WorldUtilities.register(modId, "purple_mushrooms", new WorldGenPurpleMushrooms());
		
		NetherConfig.printDebugInfo("Registered World Generator");
	}
	
	public static void register() {
		Biome.registerBiome(NetherConfig.biomeIDNether, "overworld_nether", nether);
		Biome.registerBiome(NetherConfig.biomeIDAbyss, "abyss", abyss);
		NetherConfig.printDebugInfo("Registered Biomes");
	}
    
	@Override
	public void generate(Random random, int chunkX, int chunkZ, World world,
			IChunkGenerator chunkGenerator, IChunkProvider chunkProvider) {
		
		if(world.provider.getDimension() == -1) {
			NetherConfig.printDebugInfo("Nether Generation: Providing Chuck: " + chunkX + "/" + chunkZ);
			this.generateNether(world, random, new BlockPos(chunkX * 16, 16, chunkZ * 16));
		}
	}

	private void genOre(Block block, World world, Random random,
			BlockPos pos, int maxVeinSize,
			int chanceToSpawn, Block generateIn) {
		WorldGenMinable mine = new WorldGenMinable(block.getDefaultState(),
				maxVeinSize, BlockMatcher.forBlock(generateIn));
		for (int i = 0; i < chanceToSpawn; i++) {
			int x = pos.getX() + random.nextInt(16);
			int y = random.nextInt(120);
			int z = pos.getZ() + random.nextInt(16);
			mine.generate(world, random, new BlockPos(x, y, z));
		}
	}

	private void generateNether(World world, Random random, BlockPos pos) {
		
		
		if(NetherConfig.genPeatOreChance > 0.0F) {
			genOre(NetherBlocks.PEAT_ORE, world, random, pos, NetherConfig.genPeatOreMinSize + random.nextInt(NetherConfig.genPeatOreMaxSize - NetherConfig.genPeatOreMinSize), (int)(100 * NetherConfig.genPeatOreChance), Blocks.NETHERRACK);
		}
		
		if(NetherConfig.genGoldOreChance > 0.0F) {
			genOre(NetherBlocks.GOLD_ORE, world, random, pos, NetherConfig.genGoldOreMinSize + random.nextInt(NetherConfig.genGoldOreMaxSize - NetherConfig.genGoldOreMinSize), (int)(100 * NetherConfig.genGoldOreChance), Blocks.NETHERRACK);
		}
		
		if(NetherConfig.genSoulOreChance > 0.0F) {
			genOre(NetherBlocks.SOUL_ORE, world, random, pos, NetherConfig.genSoulOreMinSize + random.nextInt(NetherConfig.genSoulOreMaxSize - NetherConfig.genSoulOreMinSize), (int)(100 * NetherConfig.genSoulOreChance), Blocks.SOUL_SAND);
		}
		
		if(NetherConfig.genWitherOreChance > 0.0F) {
			genOre(NetherBlocks.WITHER_ORE, world, random, pos, NetherConfig.genWitherOreMinSize + random.nextInt(NetherConfig.genWitherOreMaxSize - NetherConfig.genWitherOreMinSize), (int)(100 * NetherConfig.genWitherOreChance), Blocks.NETHERRACK);
		}
		
		if(NetherConfig.genNetheriteOreChance > 0.0F) {
			genOre(NetherBlocks.NETHERITE_ORE, world, random, pos, NetherConfig.genNetheriteOreMinSize + random.nextInt(NetherConfig.genNetheriteOreMaxSize - NetherConfig.genNetheriteOreMinSize), (int)(100 * NetherConfig.genNetheriteOreChance), Blocks.NETHERRACK);
		}
		
        if(random.nextFloat() < NetherConfig.genPurpleMushroomChance) {
        	new WorldGenPurpleMushrooms().generate(world, random, pos.add(random.nextInt(16), random.nextInt(96), random.nextInt(16)));
        }
        
        if(NetherConfig.genLargePurpleMushroomChance > 0.0F) {
        	int k = (int)(10 * NetherConfig.genLargePurpleMushroomChance);
        	for(int i = 0; i < k; i++) {
            	new WorldGenBigPurpleMushroom().generate(world, random, pos.add(random.nextInt(16), random.nextInt(96), random.nextInt(16)));
        	}
        }
        
        if(NetherConfig.genInfernoTreesChance > 0.0F) {
        	int k = (int)(10 * NetherConfig.genInfernoTreesChance);
        	for(int i = 0; i < k; i++) {
            	new WorldGenInfernoTree(false).generate(world, random, pos.add(random.nextInt(16), random.nextInt(96), random.nextInt(16)));
        	}
        }
        
        if(NetherConfig.genMagmaTreesChance > 0.0F) {
        	int k = (int)(10 * NetherConfig.genMagmaTreesChance);
        	for(int i = 0; i < k; i++) {
            	new WorldGenMagmaTree(false, false).generate(world, random, pos.add(random.nextInt(16), random.nextInt(96), random.nextInt(16)));
        	}
        }
        
        if(NetherConfig.genDungeonsChance > 0.0F) {
        	int k = (int)(10 * NetherConfig.genDungeonsChance);
        	for(int i = 0; i < k; i++) {
            	new WorldGenNetherDungeon().generate(world, random, pos.add(random.nextInt(16), random.nextInt(96), random.nextInt(16)));
        	}
        }
        
        if(random.nextFloat() < NetherConfig.genAcidChance) {
        	new WorldGenAcidLakes().generate(world, random, pos.add(random.nextInt(16), random.nextInt(96), random.nextInt(16)));
        }
        
        if(NetherConfig.genPiggyHouseChance > 0.0F) {
        	int k = (int)(10 * NetherConfig.genPiggyHouseChance);
        	for(int i = 0; i < k; i++) {
            	new WorldGenPiggyHouse().generate(world, random, pos.add(random.nextInt(16), random.nextInt(96), random.nextInt(16)));
        	}
        }
        
        if(NetherConfig.genObsidianPyramideChance > 0.0F) {
        	int k = (int)(10 * NetherConfig.genObsidianPyramideChance);
        	for(int i = 0; i < k; i++) {
            	new WorldGenObsidianPyramide().generate(world, random, pos.add(random.nextInt(16), random.nextInt(96), random.nextInt(16)));
        	}
        }
        
        if(NetherConfig.genObsidianBeachChance > 0.0F) {
        	int k = (int)(10 * NetherConfig.genObsidianBeachChance);
        	for(int i = 0; i < k; i++) {
            	new WorldGenObsidianBeach().generate(world, random, pos.add(random.nextInt(16), random.nextInt(96), random.nextInt(16)));
        	}
        }
        
        if(random.nextFloat() < NetherConfig.genSpikesChance) {
        	new WorldGenSpikes().generate(world, random, pos.add(random.nextInt(16), random.nextInt(96), random.nextInt(16)));
        }
	}
}