package com.superdextor.adinferos.world;

import com.superdextor.adinferos.AdInferosCore;
import com.superdextor.adinferos.NetherGeneration;

import net.minecraft.util.math.Vec3d;
import net.minecraft.world.DimensionType;
import net.minecraft.world.WorldProvider;
import net.minecraft.world.biome.BiomeProviderSingle;
import net.minecraft.world.border.WorldBorder;
import net.minecraft.world.chunk.IChunkGenerator;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class WorldProviderAbyss extends WorldProvider
{
    public void createBiomeProvider()
    {
        this.biomeProvider = new BiomeProviderSingle(NetherGeneration.abyss);
        this.isHellWorld = true;
        this.hasNoSky = true;
    }

    @SideOnly(Side.CLIENT)
    public Vec3d getFogColor(float f, float f2)
    {
        return new Vec3d(0.006D, 0.0D, 0.01D);
    }
    
    /**
     * Returns a new chunk provider which generates chunks for this world
     */
    public IChunkGenerator createChunkGenerator()
    {
        return new ChunkProviderAbyss(this.worldObj, this.worldObj.getSeed());
    }

    /**
     * True if the player can respawn in this dimension (true = overworld, false = nether).
     */
    public boolean canRespawnHere()
    {
        return false;
    }

    /**
     * Returns 'true' if in the "main surface world", but 'false' if in the Nether or End dimensions.
     */
    public boolean isSurfaceWorld()
    {
        return false;
    }

    /**
     * Will check if the x, z position specified is alright to be set as the map spawn point
     */
    public boolean canCoordinateBeSpawn(int x, int z)
    {
        return false;
    }

    public float calculateCelestialAngle(long p_76563_1_, float p_76563_3_)
    {
        return 0.5F;
    }

    /**
     * Returns true if the given X,Z coordinate should show environmental fog.
     */
    @SideOnly(Side.CLIENT)
    public boolean doesXZShowFog(int x, int z)
    {
        return true;
    }

    public DimensionType getDimensionType()
    {
        return AdInferosCore.abyssDimension;
    }
    
    public WorldBorder createWorldBorder()
    {
        return new WorldBorder()
        {
            public double getCenterX()
            {
                return super.getCenterX() / 8.0D;
            }
            public double getCenterZ()
            {
                return super.getCenterZ() / 8.0D;
            }
        };
    }
}