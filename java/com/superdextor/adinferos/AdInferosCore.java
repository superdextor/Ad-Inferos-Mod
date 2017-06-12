package com.superdextor.adinferos;

import java.io.File;

import com.superdextor.adinferos.config.NetherConfig;
import com.superdextor.adinferos.enchantments.AdInferosEnchantments;
import com.superdextor.adinferos.entity.NetherEntities;
import com.superdextor.adinferos.init.NetherBlocks;
import com.superdextor.adinferos.init.NetherFluids;
import com.superdextor.adinferos.init.NetherItems;
import com.superdextor.adinferos.inventory.AdInferosGuis;
import com.superdextor.adinferos.inventory.AdInferosRecipes;
import com.superdextor.adinferos.inventory.AltarRecipes;
import com.superdextor.adinferos.inventory.ExtractorRecipes;
import com.superdextor.adinferos.inventory.NetherFuels;
import com.superdextor.adinferos.inventory.TileEntityAltar;
import com.superdextor.adinferos.inventory.TileEntityExtractor;
import com.superdextor.adinferos.inventory.TileEntityNetherFurnace;
import com.superdextor.adinferos.inventory.TileEntitySpawner;
import com.superdextor.adinferos.network.CMessageUpdateDarkNether;
import com.superdextor.adinferos.network.CMessageUpdateNetherSurvival;
import com.superdextor.adinferos.network.SMessageHellSwordAttack;
import com.superdextor.adinferos.proxy.CommonProxy;
import com.superdextor.adinferos.world.ChunkProviderNetherSurvival;
import com.superdextor.adinferos.world.WorldProviderAbyss;
import com.superdextor.adinferos.world.WorldTypeNether;
import com.superdextor.thinkbigcore.config.TBCConfig;
import com.superdextor.thinkbigcore.network.TBCNetwork;

import net.minecraft.world.DimensionType;
import net.minecraft.world.World;
import net.minecraft.world.WorldType;
import net.minecraft.world.gen.ChunkProviderServer;
import net.minecraftforge.common.BiomeManager;
import net.minecraftforge.common.BiomeManager.BiomeEntry;
import net.minecraftforge.common.BiomeManager.BiomeType;
import net.minecraftforge.common.DimensionManager;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.common.config.Configuration;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.Mod.EventHandler;
import net.minecraftforge.fml.common.Mod.Instance;
import net.minecraftforge.fml.common.SidedProxy;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPostInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import net.minecraftforge.fml.common.registry.GameRegistry;
import net.minecraftforge.fml.relauncher.Side;

@Mod(modid = AdInferosReference.MOD_ID, name = AdInferosReference.MOD_NAME, version = AdInferosReference.VERSION, dependencies = "required-after:thinkbigcore@[1.5.9,]", guiFactory = "com.superdextor.adinferos.config.GuiFactoryAdInferos")
public class AdInferosCore {
	
	@Instance(AdInferosReference.MOD_ID)
	public static AdInferosCore modInstance;
	
	@SidedProxy(clientSide = AdInferosReference.CLIENT_PROXY_CLASS, serverSide = AdInferosReference.SERVER_PROXY_CLASS)
	public static CommonProxy proxy;
	
	public static DimensionType abyssDimension;
	
    public static final WorldType NETHER = (new WorldTypeNether());
	
	@EventHandler
	public void preInit(FMLPreInitializationEvent event)
	{
		AdInferosReference.config = new Configuration(new File(TBCConfig.getConfigFilePath(event), AdInferosReference.MOD_NAME + ".cfg"));
		NetherConfig.initConfig();
		AdInferosSounds.register();
		NetherFluids.registerFluids();
	    NetherBlocks.register();
	    NetherItems.register();
	    AdInferosRecipes.register();
        NetherEntities.register();
        NetherEntities.initSpawnEgg();
        NetherGeneration.register();
        abyssDimension = DimensionType.register("Abyss", "_abyss", NetherConfig.dimensionIdAbyss, WorldProviderAbyss.class, false);
    	GameRegistry.registerWorldGenerator(new NetherGeneration(), 1);
    	DimensionManager.registerDimension(NetherConfig.dimensionIdAbyss, abyssDimension);
		NetherConfig.printDebugInfo("Registered Abyss Dimension");
    	AdInferosAchievements.register();
    	AdInferosEnchantments.register();
    	GameRegistry.registerTileEntity(TileEntityAltar.class, "Altar");
    	GameRegistry.registerTileEntity(TileEntityNetherFurnace.class, "NetherrackFurnace");
    	GameRegistry.registerTileEntity(TileEntityExtractor.class, "Extractor");
    	GameRegistry.registerTileEntity(TileEntitySpawner.class, "AFSpawner");
		NetherConfig.printDebugInfo("Registered TileEntities");
		
		proxy.preInit();
		NetherConfig.printDebugInfo("preInit completed");
    }	
    @EventHandler
	public void Init(FMLInitializationEvent event)
	{
    	AdInferosGuis.register();
        MinecraftForge.EVENT_BUS.register(new NetherEvents());
        
        if(NetherConfig.allowOverworldNether) {
        	BiomeManager.addBiome(BiomeType.WARM, new BiomeEntry(NetherGeneration.nether, NetherConfig.netherBiomeWeight));
    		NetherConfig.printDebugInfo("Added Overworld-Nether Biome to BiomeManager");
        }
        
        if(NetherConfig.allowSmelting)
        GameRegistry.registerFuelHandler(new NetherFuels());
        
		proxy.init();
		NetherConfig.printDebugInfo("Init completed");
        
	}
	@EventHandler
	public void postInit(FMLPostInitializationEvent event)
	{
		AltarRecipes.register();
		ExtractorRecipes.register();
		TBCNetwork.registerMessage(SMessageHellSwordAttack.Handler.class, SMessageHellSwordAttack.class, Side.SERVER, 101);
		TBCNetwork.registerMessage(CMessageUpdateNetherSurvival.Handler.class, CMessageUpdateNetherSurvival.class, Side.CLIENT, 102);
		TBCNetwork.registerMessage(CMessageUpdateDarkNether.Handler.class, CMessageUpdateDarkNether.class, Side.CLIENT, 103);
		NetherConfig.printDebugInfo("Registered Packets");
		proxy.postInit();
		NetherConfig.printDebugInfo("postInit completed");
	}
	
	//ONLY USE ON SERVER SIDE
	public static boolean isNetherSurvival(World world) {
		
		if(!world.isRemote && world.getChunkProvider() instanceof ChunkProviderServer) {
			ChunkProviderServer providerServer = (ChunkProviderServer) world.getChunkProvider();
			if(providerServer.chunkGenerator instanceof ChunkProviderNetherSurvival) {
				return true;
			}
		}
		
		return false;
	}
}
