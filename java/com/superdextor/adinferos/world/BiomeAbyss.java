package com.superdextor.adinferos.world;

import com.superdextor.adinferos.config.NetherConfig;
import com.superdextor.adinferos.entity.monster.EntityBlackWidow;
import com.superdextor.adinferos.entity.monster.EntityCurse;
import com.superdextor.adinferos.entity.monster.EntityDimstoneSkeleton;
import com.superdextor.adinferos.entity.monster.EntityGhost;
import com.superdextor.adinferos.entity.monster.EntityHerobrine;
import com.superdextor.adinferos.entity.monster.EntityHerobrineClone;
import com.superdextor.adinferos.entity.monster.EntityInfernumAvis;
import com.superdextor.adinferos.entity.monster.EntityObsidianSheepman;
import com.superdextor.adinferos.entity.monster.EntityPhantom;
import com.superdextor.adinferos.entity.monster.EntityReaper;
import com.superdextor.adinferos.entity.monster.EntitySkeletonHorse;
import com.superdextor.adinferos.entity.other.EntityInfernalChicken;

import net.minecraft.util.math.BlockPos;
import net.minecraft.world.biome.Biome;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class BiomeAbyss extends Biome
{

    public BiomeAbyss()
    {
        super(new Biome.BiomeProperties("Abyss").setRainDisabled().setWaterColor(0x820000));
        this.spawnableMonsterList.clear();
        this.spawnableCreatureList.clear();
        this.spawnableWaterCreatureList.clear();
        this.spawnableCaveCreatureList.clear();
    	if(NetherConfig.blackWidowSpawnWeight > 0 && NetherConfig.blackWidowBiomes.contains("ABYSS")) {
            this.addSpawn(new Biome.SpawnListEntry(EntityBlackWidow.class, NetherConfig.blackWidowSpawnWeight, 1, 3));
    	}
    	if(NetherConfig.curseSpawnWeight > 0 && NetherConfig.curseBiomes.contains("ABYSS")) {
            this.addSpawn(new Biome.SpawnListEntry(EntityCurse.class, NetherConfig.curseSpawnWeight, 2, 4));
    	}
    	if(NetherConfig.ghostSpawnWeight > 0 && NetherConfig.ghostBiomes.contains("ABYSS")) {
            this.addSpawn(new Biome.SpawnListEntry(EntityGhost.class, NetherConfig.ghostSpawnWeight, 1, 3));
    	}
    	if(NetherConfig.glowstoneSkeletonSpawnWeight > 0 && NetherConfig.glowstoneSkeletonBiomes.contains("ABYSS")) {
            this.addSpawn(new Biome.SpawnListEntry(EntityDimstoneSkeleton.class, NetherConfig.glowstoneSkeletonSpawnWeight, 4, 4));
    	}
    	if(NetherConfig.herobrineBiomes.contains("ABYSS")) {
            this.addSpawn(new Biome.SpawnListEntry(EntityHerobrineClone.class, NetherConfig.herobrineSpawnWeight * 2 + 1, 1, 1));
    		
            if(NetherConfig.herobrineSpawnWeight > 0) {
                this.addSpawn(new Biome.SpawnListEntry(EntityHerobrine.class, NetherConfig.herobrineSpawnWeight, 1, 1));
    		}
    	}
    	if(NetherConfig.infernumAvisSpawnWeight > 0 && NetherConfig.infernumAvisBiomes.contains("ABYSS")) {
            this.addSpawn(new Biome.SpawnListEntry(EntityInfernumAvis.class, NetherConfig.infernumAvisSpawnWeight, 1, 1));
    	}
    	if(NetherConfig.infernalChickenSpawnWeight > 0 && NetherConfig.infernalChickenBiomes.contains("ABYSS")) {
            this.addSpawn(new Biome.SpawnListEntry(EntityInfernalChicken.class, NetherConfig.infernalChickenSpawnWeight, 1, 4));
    	}
    	if(NetherConfig.phantomSpawnWeight > 0 && NetherConfig.phantomBiomes.contains("ABYSS")) {
            this.addSpawn(new Biome.SpawnListEntry(EntityPhantom.class, NetherConfig.phantomSpawnWeight, 6, 10));
    	}
    	if(NetherConfig.reaperSpawnWeight > 0 && NetherConfig.reaperBiomes.contains("ABYSS")) {
            this.addSpawn(new Biome.SpawnListEntry(EntityReaper.class, NetherConfig.reaperSpawnWeight, 1, 1));
    	}
    	if(NetherConfig.sheepmanSpawnWeight > 0 && NetherConfig.sheepmanBiomes.contains("ABYSS")) {
            this.addSpawn(new Biome.SpawnListEntry(EntityObsidianSheepman.class, NetherConfig.sheepmanSpawnWeight, 6, 16));
    	}
    	if(NetherConfig.skeletonHorseSpawnWeight > 0 && NetherConfig.skeletonHorseBiomes.contains("ABYSS")) {
            this.addSpawn(new Biome.SpawnListEntry(EntitySkeletonHorse.class, NetherConfig.skeletonHorseSpawnWeight, 4, 7));
    	}
    }
    
    public void addDefaultFlowers() {}
    
    private void addSpawn(Biome.SpawnListEntry spawn) {
    	spawnableMonsterList.add(spawn);
    	spawnableCreatureList.add(spawn);
    }
    
    public float getSpawningChance() {
    	return 1.0F;
    }
    
    @SideOnly(Side.CLIENT)
    public int getGrassColorAtPos(BlockPos p_180627_1_)
    {
        return 0x404040;
    }

    @SideOnly(Side.CLIENT)
    public int getFoliageColorAtPos(BlockPos p_180625_1_)
    {
        return 0x404040;
    }
    
    public int getWaterColorMultiplier()
    {
        return 0x820000;
    }

    public int getModdedBiomeGrassColor(int original)
    {
        return 0x404040;
    }

    public int getModdedBiomeFoliageColor(int original)
    {
        return 0x404040;
    }
}