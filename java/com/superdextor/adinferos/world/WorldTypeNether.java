package com.superdextor.adinferos.world;

import net.minecraft.world.World;
import net.minecraft.world.WorldType;
import net.minecraft.world.biome.BiomeProvider;
import net.minecraft.world.chunk.IChunkGenerator;

public class WorldTypeNether extends WorldType {

	public WorldTypeNether() {
		super("adinferos");
	}
	
    public BiomeProvider getBiomeProvider(World world)
    {
    	return new BiomeProvider(world.getWorldInfo());
    }
    
    public IChunkGenerator getChunkGenerator(World world, String generatorOptions)
    {
        return new ChunkProviderNetherSurvival(world, world.getSeed(), world.getWorldInfo().isMapFeaturesEnabled(), generatorOptions);
    }
}
