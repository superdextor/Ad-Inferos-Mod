package com.superdextor.adinferos.world;

import java.util.Random;

import com.superdextor.adinferos.config.NetherConfig;
import com.superdextor.adinferos.entity.monster.EntityBlackWidow;
import com.superdextor.adinferos.entity.monster.EntityCurse;
import com.superdextor.adinferos.entity.monster.EntityGhost;
import com.superdextor.adinferos.entity.monster.EntityGlowstoneSkeleton;
import com.superdextor.adinferos.entity.monster.EntityHerobrine;
import com.superdextor.adinferos.entity.monster.EntityInfernumAvis;
import com.superdextor.adinferos.entity.monster.EntityObsidianSheepman;
import com.superdextor.adinferos.entity.monster.EntityPhantom;
import com.superdextor.adinferos.entity.monster.EntityReaper;
import com.superdextor.adinferos.entity.monster.EntitySkeletonHorse;
import com.superdextor.adinferos.entity.other.EntityInfernalChicken;
import com.superdextor.adinferos.worldgen.WorldGenBigPurpleMushroom;
import com.superdextor.adinferos.worldgen.WorldGenInfernoTree;
import com.superdextor.adinferos.worldgen.WorldGenPiggyHouse;
import com.superdextor.adinferos.worldgen.WorldGenPurpleMushrooms;

import net.minecraft.entity.monster.EntityGhast;
import net.minecraft.entity.monster.EntityMagmaCube;
import net.minecraft.entity.monster.EntityPigZombie;
import net.minecraft.init.Blocks;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraft.world.biome.Biome;
import net.minecraft.world.gen.feature.WorldGenFire;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class BiomeNether extends Biome
{

    public BiomeNether()
    {
        super(new Biome.BiomeProperties("Overworld Nether").setRainDisabled().setWaterColor(0xFF007F));
        this.topBlock = Blocks.NETHERRACK.getDefaultState();
        this.fillerBlock = Blocks.NETHERRACK.getDefaultState();
        this.spawnableMonsterList.clear();
        this.spawnableCreatureList.clear();
        this.spawnableWaterCreatureList.clear();
        this.spawnableCaveCreatureList.clear();
        this.theBiomeDecorator.generateLakes = false;
        this.theBiomeDecorator.treesPerChunk = -999;
        this.theBiomeDecorator.flowersPerChunk = -999;
        this.theBiomeDecorator.grassPerChunk = -999;
        this.theBiomeDecorator.sandPerChunk = -999;
        this.theBiomeDecorator.sandPerChunk2 = -999;
        this.theBiomeDecorator.clayPerChunk = -999;
        this.spawnableMonsterList.add(new Biome.SpawnListEntry(EntityGhast.class, 10, 4, 4));
        this.spawnableMonsterList.add(new Biome.SpawnListEntry(EntityPigZombie.class, 50, 4, 4));
        this.spawnableMonsterList.add(new Biome.SpawnListEntry(EntityMagmaCube.class, 10, 4, 4));
    	if(NetherConfig.blackWidowSpawnWeight > 0 && NetherConfig.blackWidowBiomes.contains("OVERWORLDN")) {
            this.spawnableMonsterList.add(new Biome.SpawnListEntry(EntityBlackWidow.class, NetherConfig.blackWidowSpawnWeight, 1, 3));
    	}
    	if(NetherConfig.curseSpawnWeight > 0 && NetherConfig.curseBiomes.contains("OVERWORLDN")) {
    		this.spawnableMonsterList.add(new Biome.SpawnListEntry(EntityCurse.class, NetherConfig.curseSpawnWeight, 1, 2));
    	}
    	if(NetherConfig.ghostSpawnWeight > 0 && NetherConfig.ghostBiomes.contains("OVERWORLDN")) {
    		this.spawnableMonsterList.add(new Biome.SpawnListEntry(EntityGhost.class, NetherConfig.ghostSpawnWeight, 1, 3));
    	}
    	if(NetherConfig.glowstoneSkeletonSpawnWeight > 0 && NetherConfig.glowstoneSkeletonBiomes.contains("OVERWORLDN")) {
    		this.spawnableMonsterList.add(new Biome.SpawnListEntry(EntityGlowstoneSkeleton.class, NetherConfig.glowstoneSkeletonSpawnWeight, 4, 4));
    	}
    	if(NetherConfig.herobrineSpawnWeight > 0 && NetherConfig.herobrineBiomes.contains("OVERWORLDN")) {
    		this.spawnableMonsterList.add(new Biome.SpawnListEntry(EntityHerobrine.class, NetherConfig.herobrineSpawnWeight, 1, 1));
    	}
    	if(NetherConfig.infernumAvisSpawnWeight > 0 && NetherConfig.infernumAvisBiomes.contains("OVERWORLDN")) {
    		this.spawnableMonsterList.add(new Biome.SpawnListEntry(EntityInfernumAvis.class, NetherConfig.infernumAvisSpawnWeight, 1, 1));
    	}
    	if(NetherConfig.infernalChickenSpawnWeight > 0 && NetherConfig.infernalChickenBiomes.contains("OVERWORLDN")) {
    		this.spawnableMonsterList.add(new Biome.SpawnListEntry(EntityInfernalChicken.class, NetherConfig.infernalChickenSpawnWeight, 1, 4));
    	}
    	if(NetherConfig.phantomSpawnWeight > 0 && NetherConfig.phantomBiomes.contains("OVERWORLDN")) {
    		this.spawnableMonsterList.add(new Biome.SpawnListEntry(EntityPhantom.class, NetherConfig.phantomSpawnWeight, 6, 10));
    	}
    	if(NetherConfig.reaperSpawnWeight > 0 && NetherConfig.reaperBiomes.contains("OVERWORLDN")) {
    		this.spawnableMonsterList.add(new Biome.SpawnListEntry(EntityReaper.class, NetherConfig.reaperSpawnWeight, 1, 1));
    	}
    	if(NetherConfig.sheepmanSpawnWeight > 0 && NetherConfig.sheepmanBiomes.contains("OVERWORLDN")) {
    		this.spawnableMonsterList.add(new Biome.SpawnListEntry(EntityObsidianSheepman.class, NetherConfig.sheepmanSpawnWeight, 6, 16));
    	}
    	if(NetherConfig.skeletonHorseSpawnWeight > 0 && NetherConfig.skeletonHorseBiomes.contains("OVERWORLDN")) {
    		this.spawnableMonsterList.add(new Biome.SpawnListEntry(EntitySkeletonHorse.class, NetherConfig.skeletonHorseSpawnWeight, 4, 7));
    	}
    }

    public void decorate(World world, Random rand, BlockPos pos)
    {
		NetherConfig.printDebugInfo("Overworld Nether: Providing Chuck: " + pos.getX() / 16 + "/" + pos.getZ() / 16);
		
        if(NetherConfig.genInfernoTreesChance > 0.0F) {
        	int k = (int)(10 * NetherConfig.genInfernoTreesChance);
        	for(int i = 0; i < k; i++) {
            	new WorldGenInfernoTree(false).generate(world, rand, pos.add(rand.nextInt(16), 40 + rand.nextInt(30), rand.nextInt(16)));
        	}
        }
        
        if(rand.nextFloat() < NetherConfig.genPurpleMushroomChance) {
        	new WorldGenPurpleMushrooms().generate(world, rand, pos.add(rand.nextInt(16), 40 + rand.nextInt(30), rand.nextInt(16)));
        }
        
        if(NetherConfig.genLargePurpleMushroomChance > 0.0F) {
        	int k = (int)(10 * NetherConfig.genLargePurpleMushroomChance);
        	for(int i = 0; i < k; i++) {
            	new WorldGenBigPurpleMushroom().generate(world, rand, pos.add(rand.nextInt(16), 40 + rand.nextInt(30), rand.nextInt(16)));
        	}
        }
        
        if(NetherConfig.genPiggyHouseChance > 0.0F) {
        	int k = (int)(10 * NetherConfig.genPiggyHouseChance);
        	for(int i = 0; i < k; i++) {
            	new WorldGenPiggyHouse().generate(world, rand, pos.add(rand.nextInt(16), 40 + rand.nextInt(30), rand.nextInt(16)));
        	}
        }
    	
        int i;
        int j;
        int k = pos.getX();
        int l = pos.getZ();

            for (i = 0; i < 4; ++i)
            {
                for (j = 0; j < 4; ++j)
                {
                    if (rand.nextFloat() < 0.02F) {
                        k = i * 4 + 1 + 8 + rand.nextInt(3);
                        l = j * 4 + 1 + 8 + rand.nextInt(3);
                        BlockPos blockpos1 = world.getHeight(pos.add(k, 0, l));
                    	WorldGenFire tree = new WorldGenFire();
                        tree.generate(world, rand, blockpos1);
                }
            }
        }
    }
    
    @SideOnly(Side.CLIENT)
    public int getGrassColorAtPos(BlockPos p_180627_1_)
    {
        return 0x7F2B0F;
    }

    @SideOnly(Side.CLIENT)
    public int getFoliageColorAtPos(BlockPos p_180625_1_)
    {
        return 0x7F2B0F;
    }
    
    public int getWaterColorMultiplier()
    {
        return 0xFF007F;
    }

    public int getModdedBiomeGrassColor(int original)
    {
        return 0x7F2B0F;
    }

    public int getModdedBiomeFoliageColor(int original)
    {
        return 0x7F2B0F;
    }
}