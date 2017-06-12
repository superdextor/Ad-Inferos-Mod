package com.superdextor.adinferos.world;

import java.util.List;
import java.util.Random;

import com.superdextor.adinferos.config.NetherConfig;
import com.superdextor.adinferos.init.NetherBlocks;
import com.superdextor.adinferos.worldgen.WorldGenAbyssAcid;
import com.superdextor.adinferos.worldgen.WorldGenAcidLakes;
import com.superdextor.adinferos.worldgen.WorldGenAshTree;
import com.superdextor.adinferos.worldgen.WorldGenDarkFire;
import com.superdextor.adinferos.worldgen.WorldGenDimstone;
import com.superdextor.adinferos.worldgen.WorldGenObsidianPyramide;
import com.superdextor.adinferos.worldgen.WorldGenPhantomTree;

import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.block.state.pattern.BlockMatcher;
import net.minecraft.entity.EnumCreatureType;
import net.minecraft.init.Blocks;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.world.World;
import net.minecraft.world.biome.Biome;
import net.minecraft.world.chunk.Chunk;
import net.minecraft.world.chunk.ChunkPrimer;
import net.minecraft.world.chunk.IChunkGenerator;
import net.minecraft.world.gen.MapGenBase;
import net.minecraft.world.gen.MapGenCavesHell;
import net.minecraft.world.gen.NoiseGeneratorOctaves;
import net.minecraft.world.gen.feature.WorldGenMinable;

public class ChunkProviderAbyss implements IChunkGenerator
{
	
    protected static final IBlockState air = Blocks.AIR.getDefaultState();
    protected static final IBlockState darkstone = NetherBlocks.DARKSTONE.getDefaultState();
    protected static final IBlockState bedrock = Blocks.BEDROCK.getDefaultState();
    protected static final IBlockState darksand = NetherBlocks.DARK_SAND.getDefaultState();
	
    /** Is the world that the nether is getting generated. */
    private final World world;
    private final Random rand;
    /** Holds the noise used to determine whether slowsand can be generated at a location */
    private final MapGenBase genCaves;
    
    private double[] slowsandNoise = new double[256];
    private double[] gravelNoise = new double[256];
    private double[] depthBuffer = new double[256];
    private double[] buffer;
    private NoiseGeneratorOctaves lperlinNoise1;
    private NoiseGeneratorOctaves lperlinNoise2;
    private NoiseGeneratorOctaves perlinNoise1;
    /** Determines whether slowsand or gravel can be generated at a location */
    private NoiseGeneratorOctaves slowsandGravelNoiseGen;
    /** Determines whether something other than nettherack can be generated at a location */
    private NoiseGeneratorOctaves netherrackExculsivityNoiseGen;
    public NoiseGeneratorOctaves scaleNoise;
    public NoiseGeneratorOctaves depthNoise;
    private final WorldGenAbyssAcid acidTrapGen = new WorldGenAbyssAcid(true);
    private final WorldGenAbyssAcid abyssSpringGen = new WorldGenAbyssAcid(false);
    private final WorldGenAcidLakes acidLakesGen = new WorldGenAcidLakes();
    private final WorldGenDarkFire darkfireFeature = new WorldGenDarkFire();
    private final WorldGenDimstone dimGemGen = new WorldGenDimstone();
    private final WorldGenPhantomTree phantomTreeGen = new WorldGenPhantomTree(false);
    private final WorldGenAshTree ashTreeGen = new WorldGenAshTree(false);
    double[] pnr;
    double[] ar;
    double[] br;
    double[] noiseData4;
    double[] dr;

    public ChunkProviderAbyss(World worldIn, long seed)
    {
        this.world = worldIn;
        this.rand = new Random(seed);
        this.genCaves = new MapGenCavesHell();
        
        this.lperlinNoise1 = new NoiseGeneratorOctaves(this.rand, 16);
        this.lperlinNoise2 = new NoiseGeneratorOctaves(this.rand, 16);
        this.perlinNoise1 = new NoiseGeneratorOctaves(this.rand, 8);
        this.slowsandGravelNoiseGen = new NoiseGeneratorOctaves(this.rand, 4);
        this.netherrackExculsivityNoiseGen = new NoiseGeneratorOctaves(this.rand, 4);
        this.scaleNoise = new NoiseGeneratorOctaves(this.rand, 10);
        this.depthNoise = new NoiseGeneratorOctaves(this.rand, 16);
        worldIn.setSeaLevel(63);
    }
    
    public void prepareHeights(int p_185936_1_, int p_185936_2_, ChunkPrimer primer)
    {
        int i = 4;
        int j = this.world.getSeaLevel() / 2 + 1;
        int k = i + 1;
        int l = 17;
        int i1 = i + 1;
        this.buffer = this.getHeights(this.buffer, p_185936_1_ * i, 0, p_185936_2_ * i, k, l, i1);

        for (int j1 = 0; j1 < i; ++j1)
        {
            for (int k1 = 0; k1 < i; ++k1)
            {
                for (int l1 = 0; l1 < 16; ++l1)
                {
                    double d0 = 0.125D;
                    double d1 = this.buffer[((j1 + 0) * i1 + k1 + 0) * l + l1 + 0];
                    double d2 = this.buffer[((j1 + 0) * i1 + k1 + 1) * l + l1 + 0];
                    double d3 = this.buffer[((j1 + 1) * i1 + k1 + 0) * l + l1 + 0];
                    double d4 = this.buffer[((j1 + 1) * i1 + k1 + 1) * l + l1 + 0];
                    double d5 = (this.buffer[((j1 + 0) * i1 + k1 + 0) * l + l1 + 1] - d1) * d0;
                    double d6 = (this.buffer[((j1 + 0) * i1 + k1 + 1) * l + l1 + 1] - d2) * d0;
                    double d7 = (this.buffer[((j1 + 1) * i1 + k1 + 0) * l + l1 + 1] - d3) * d0;
                    double d8 = (this.buffer[((j1 + 1) * i1 + k1 + 1) * l + l1 + 1] - d4) * d0;

                    for (int i2 = 0; i2 < 8; ++i2)
                    {
                        double d9 = 0.25D;
                        double d10 = d1;
                        double d11 = d2;
                        double d12 = (d3 - d1) * d9;
                        double d13 = (d4 - d2) * d9;

                        for (int j2 = 0; j2 < 4; ++j2)
                        {
                            double d14 = 0.25D;
                            double d15 = d10;
                            double d16 = (d11 - d10) * d14;

                            for (int k2 = 0; k2 < 4; ++k2)
                            {
                                IBlockState iblockstate = null;

                                if (l1 * 8 + i2 < j)
                                {
                                    iblockstate = air;
                                }

                                if (d15 > 0.0D)
                                {
                                    iblockstate = darkstone;
                                }

                                int l2 = j2 + j1 * 4;
                                int i3 = i2 + l1 * 8;
                                int j3 = k2 + k1 * 4;
                                primer.setBlockState(l2, i3, j3, iblockstate);
                                d15 += d16;
                            }

                            d10 += d12;
                            d11 += d13;
                        }

                        d1 += d5;
                        d2 += d6;
                        d3 += d7;
                        d4 += d8;
                    }
                }
            }
        }
    }

    public void buildSurfaces(int p_185937_1_, int p_185937_2_, ChunkPrimer primer)
    {
        if (!net.minecraftforge.event.ForgeEventFactory.onReplaceBiomeBlocks(this, p_185937_1_, p_185937_2_, primer, this.world)) return;
        int i = this.world.getSeaLevel() + 1;
        double d0 = 0.03125D;
        this.slowsandNoise = this.slowsandGravelNoiseGen.generateNoiseOctaves(this.slowsandNoise, p_185937_1_ * 16, p_185937_2_ * 16, 0, 16, 16, 1, d0, d0, 1.0D);
        this.gravelNoise = this.slowsandGravelNoiseGen.generateNoiseOctaves(this.gravelNoise, p_185937_1_ * 16, 109, p_185937_2_ * 16, 16, 1, 16, d0, 1.0D, d0);
        this.depthBuffer = this.netherrackExculsivityNoiseGen.generateNoiseOctaves(this.depthBuffer, p_185937_1_ * 16, p_185937_2_ * 16, 0, 16, 16, 1, d0 * 2.0D, d0 * 2.0D, d0 * 2.0D);

        for (int j = 0; j < 16; ++j)
        {
            for (int k = 0; k < 16; ++k)
            {
                boolean flag = this.slowsandNoise[j + k * 16] + this.rand.nextDouble() * 0.2D > 0.0D;
                boolean flag1 = this.gravelNoise[j + k * 16] + this.rand.nextDouble() * 0.2D > 0.0D;
                int l = (int)(this.depthBuffer[j + k * 16] / 3.0D + 3.0D + this.rand.nextDouble() * 0.25D);
                int i1 = -1;
                IBlockState iblockstate = darkstone;
                IBlockState iblockstate1 = darkstone;

                for (int j1 = 127; j1 >= 0; --j1)
                {
                    if (j1 < 127 - this.rand.nextInt(5) && j1 > this.rand.nextInt(5))
                    {
                        IBlockState iblockstate2 = primer.getBlockState(k, j1, j);

                        if (iblockstate2.getBlock() != null && iblockstate2.getMaterial() != Material.AIR)
                        {
                            if (iblockstate2.getBlock() == NetherBlocks.DARKSTONE)
                            {
                                if (i1 == -1)
                                {
                                    if (l <= 0)
                                    {
                                        iblockstate = air;
                                        iblockstate1 = darkstone;
                                    }
                                    else if (j1 >= i - 4 && j1 <= i + 1)
                                    {
                                        iblockstate = darkstone;
                                        iblockstate1 = darkstone;

                                        if (flag1)
                                        {
                                            iblockstate = darkstone;
                                            iblockstate1 = darkstone;
                                        }

                                        if (flag)
                                        {
                                            iblockstate = darksand;
                                            iblockstate1 = darksand;
                                        }
                                    }

                                    i1 = l;

                                    if (j1 >= i - 1)
                                    {
                                        primer.setBlockState(k, j1, j, iblockstate);
                                    }
                                    else
                                    {
                                        primer.setBlockState(k, j1, j, iblockstate1);
                                    }
                                }
                                else if (i1 > 0)
                                {
                                    --i1;
                                    primer.setBlockState(k, j1, j, iblockstate1);
                                }
                            }
                        }
                        else
                        {
                            i1 = -1;
                        }
                    }
                    else
                    {
                        primer.setBlockState(k, j1, j, bedrock);
                    }
                }
            }
        }
    }

    public Chunk provideChunk(int x, int z)
    {
        this.rand.setSeed((long)x * 341873128712L + (long)z * 132897987541L);
        ChunkPrimer chunkprimer = new ChunkPrimer();
        this.prepareHeights(x, z, chunkprimer);
        this.buildSurfaces(x, z, chunkprimer);
        this.genCaves.generate(this.world, x, z, chunkprimer);

        Chunk chunk = new Chunk(this.world, chunkprimer, x, z);
        Biome[] abiome = this.world.getBiomeProvider().getBiomes((Biome[])null, x * 16, z * 16, 16, 16);
        byte[] abyte = chunk.getBiomeArray();

        for (int i = 0; i < abyte.length; ++i)
        {
            abyte[i] = (byte)Biome.getIdForBiome(abiome[i]);
        }

        chunk.resetRelightChecks();
        return chunk;
    }

    private double[] getHeights(double[] p_185938_1_, int p_185938_2_, int p_185938_3_, int p_185938_4_, int p_185938_5_, int p_185938_6_, int p_185938_7_)
    {
        if (p_185938_1_ == null)
        {
            p_185938_1_ = new double[p_185938_5_ * p_185938_6_ * p_185938_7_];
        }

        double d0 = 684.412D;
        double d1 = 2053.236D;
        this.noiseData4 = this.scaleNoise.generateNoiseOctaves(this.noiseData4, p_185938_2_, p_185938_3_, p_185938_4_, p_185938_5_, 1, p_185938_7_, 1.0D, 0.0D, 1.0D);
        this.dr = this.depthNoise.generateNoiseOctaves(this.dr, p_185938_2_, p_185938_3_, p_185938_4_, p_185938_5_, 1, p_185938_7_, 100.0D, 0.0D, 100.0D);
        this.pnr = this.perlinNoise1.generateNoiseOctaves(this.pnr, p_185938_2_, p_185938_3_, p_185938_4_, p_185938_5_, p_185938_6_, p_185938_7_, d0 / 80.0D, d1 / 60.0D, d0 / 80.0D);
        this.ar = this.lperlinNoise1.generateNoiseOctaves(this.ar, p_185938_2_, p_185938_3_, p_185938_4_, p_185938_5_, p_185938_6_, p_185938_7_, d0, d1, d0);
        this.br = this.lperlinNoise2.generateNoiseOctaves(this.br, p_185938_2_, p_185938_3_, p_185938_4_, p_185938_5_, p_185938_6_, p_185938_7_, d0, d1, d0);
        int i = 0;
        double[] adouble = new double[p_185938_6_];

        for (int j = 0; j < p_185938_6_; ++j)
        {
            adouble[j] = Math.cos((double)j * Math.PI * 6.0D / (double)p_185938_6_) * 2.0D;
            double d2 = (double)j;

            if (j > p_185938_6_ / 2)
            {
                d2 = (double)(p_185938_6_ - 1 - j);
            }

            if (d2 < 4.0D)
            {
                d2 = 4.0D - d2;
                adouble[j] -= d2 * d2 * d2 * 10.0D;
            }
        }

        for (int l = 0; l < p_185938_5_; ++l)
        {
            for (int i1 = 0; i1 < p_185938_7_; ++i1)
            {
                double d3 = 0.0D;

                for (int k = 0; k < p_185938_6_; ++k)
                {
                    double d4 = 0.0D;
                    double d5 = adouble[k];
                    double d6 = this.ar[i] / 512.0D;
                    double d7 = this.br[i] / 512.0D;
                    double d8 = (this.pnr[i] / 10.0D + 1.0D) / 2.0D;

                    if (d8 < 0.0D)
                    {
                        d4 = d6;
                    }
                    else if (d8 > 1.0D)
                    {
                        d4 = d7;
                    }
                    else
                    {
                        d4 = d6 + (d7 - d6) * d8;
                    }

                    d4 = d4 - d5;

                    if (k > p_185938_6_ - 4)
                    {
                        double d9 = (double)((float)(k - (p_185938_6_ - 4)) / 3.0F);
                        d4 = d4 * (1.0D - d9) + -10.0D * d9;
                    }

                    if ((double)k < d3)
                    {
                        double d10 = (d3 - (double)k) / 4.0D;
                        d10 = MathHelper.clamp_double(d10, 0.0D, 1.0D);
                        d4 = d4 * (1.0D - d10) + -10.0D * d10;
                    }

                    p_185938_1_[i] = d4;
                    ++i;
                }
            }
        }

        return p_185938_1_;
    }
    
    public void populate(int x, int z)
    {
		NetherConfig.printDebugInfo("Chuck Provider Abyss: Providing Chuck: " + x + "/" + z);

        BlockPos blockpos = new BlockPos(x * 16, 0, z * 16);
        
        if(NetherConfig.genDarkfireChance > 0.0F) {
        	int i = (int)(5 * NetherConfig.genDarkfireChance);
        	for(int k = 0; k < i; k++) {
            	this.darkfireFeature.generate(this.world, this.rand, blockpos.add(this.rand.nextInt(16) + 8, this.rand.nextInt(120) + 4, this.rand.nextInt(16) + 8));
        	}
        }
        
        if(NetherConfig.genDimstoneChance > 0.0F) {
        	int i = (int)(100 * NetherConfig.genDimstoneChance);
        	
            for (int k = 0; k < i; ++k)
            {
                this.dimGemGen.generate(this.world, this.rand, blockpos.add(this.rand.nextInt(16) + 8, this.rand.nextInt(120) + 4, this.rand.nextInt(16) + 8));
            }

            for (int k = 0; k < i; ++k)
            {
            	this.dimGemGen.generate(this.world, this.rand, blockpos.add(this.rand.nextInt(16) + 8, this.rand.nextInt(128), this.rand.nextInt(16) + 8));
            }
        }
        
        if(NetherConfig.genAcidChance > 0.0F) {
        	int i = (int)(20 * NetherConfig.genAcidChance);
        	
            for (int k = 0; k < i; ++k)
            {
                this.acidTrapGen.generate(this.world, this.rand, blockpos.add(this.rand.nextInt(16), this.rand.nextInt(108) + 10, this.rand.nextInt(16)));
            }
            
            for (int k = 0; k < i / 2; ++k)
            {
                this.abyssSpringGen.generate(this.world, this.rand, blockpos.add(this.rand.nextInt(16) + 8, this.rand.nextInt(120) + 4, this.rand.nextInt(16) + 8));
            }
            
            if(this.rand.nextFloat() < NetherConfig.genAcidChance) {
                this.acidLakesGen.generate(this.world, this.rand, blockpos.add(this.rand.nextInt(16) + 8, this.rand.nextInt(120) + 4, this.rand.nextInt(16) + 8));
            }
        }
        
		if(NetherConfig.genWitherOreChance > 0.0F) {
			int i = (int)(100 * NetherConfig.genWitherOreChance) * 2;
			for(int k = 0; k < i; k++) {
				new WorldGenMinable(NetherBlocks.WITHER_ORE.getStateFromMeta(1), NetherConfig.genWitherOreMinSize + this.rand.nextInt(NetherConfig.genWitherOreMaxSize - NetherConfig.genWitherOreMinSize), BlockMatcher.forBlock(NetherBlocks.DARKSTONE)).generate(this.world, this.rand, blockpos.add(this.rand.nextInt(16) + 8, this.rand.nextInt(120) + 4, this.rand.nextInt(16) + 8));
			}
		}
		
		if(NetherConfig.genNetheriteOreChance > 0.0F) {
			int i = (int)(100 * NetherConfig.genNetheriteOreChance) * 2;
			for(int k = 0; k < i; k++) {
				new WorldGenMinable(NetherBlocks.NETHERITE_ORE.getStateFromMeta(1), NetherConfig.genNetheriteOreMinSize + this.rand.nextInt(NetherConfig.genNetheriteOreMaxSize - NetherConfig.genNetheriteOreMinSize), BlockMatcher.forBlock(NetherBlocks.DARKSTONE)).generate(this.world, this.rand, blockpos.add(this.rand.nextInt(16) + 8, this.rand.nextInt(120) + 4, this.rand.nextInt(16) + 8));
			}
		}
		
		if(NetherConfig.genDimensionalOreChance > 0.0F) {
			int i = (int)(100 * NetherConfig.genDimensionalOreChance) * 2;
			for(int k = 0; k < i; k++) {
				new WorldGenMinable(NetherBlocks.DIMENSIONAL_ORE.getDefaultState(), NetherConfig.genDimensionalOreMinSize + this.rand.nextInt(NetherConfig.genDimensionalOreMaxSize - NetherConfig.genDimensionalOreMinSize), BlockMatcher.forBlock(NetherBlocks.DARKSTONE)).generate(this.world, this.rand, blockpos.add(this.rand.nextInt(16) + 8, this.rand.nextInt(120) + 4, this.rand.nextInt(16) + 8));
			}
		}
        
        if(NetherConfig.genPhantomTreesChance > 0.0F) {
        	int i = (int)(10 * NetherConfig.genPhantomTreesChance);
        	for(int k = 0; k < i; k++) {
                this.phantomTreeGen.generate(this.world, this.rand, blockpos.add(this.rand.nextInt(16) + 8, this.rand.nextInt(120) + 4, this.rand.nextInt(16) + 8));
        	}
        }
        
        if(NetherConfig.genAshTreesChance > 0.0F) {
        	int i = (int)(10 * NetherConfig.genAshTreesChance);
        	for(int k = 0; k < i; k++) {
                this.ashTreeGen.generate(this.world, this.rand, blockpos.add(this.rand.nextInt(16) + 8, this.rand.nextInt(120) + 4, this.rand.nextInt(16) + 8));
        	}
        }
        
        if(NetherConfig.genObsidianPyramideChance > 0.0F) {
        	int i = (int)(20 * NetherConfig.genObsidianPyramideChance);
        	for(int k = 0; k < i; k++) {
                new WorldGenObsidianPyramide().generate(this.world, this.rand, blockpos.add(this.rand.nextInt(16) + 8, this.rand.nextInt(120) + 4, this.rand.nextInt(16) + 8));
        	}
        }
    }
    
    public boolean generateStructures(Chunk chunkIn, int x, int z)
    {
        return false;
    }
    
    public List<Biome.SpawnListEntry> getPossibleCreatures(EnumCreatureType creatureType, BlockPos pos)
    {
        Biome biome = this.world.getBiome(pos);
        return biome.getSpawnableList(creatureType);
    }
    
    public BlockPos getStrongholdGen(World worldIn, String structureName, BlockPos position,  boolean p_180513_4)
    {
        return null;
    }

    public void recreateStructures(Chunk p_180514_1_, int p_180514_2_, int p_180514_3_) {}
}