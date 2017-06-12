//Copyrighted 2015 Think big Corp, superdextor, obsidiancow (All Rights Reserved)

package com.superdextor.adinferos.proxy;

import com.google.common.base.Predicate;
import com.superdextor.adinferos.AdInferosCore;
import com.superdextor.adinferos.AdInferosSounds;
import com.superdextor.adinferos.config.NetherConfig;
import com.superdextor.adinferos.entity.monster.EntityBlackWidow;
import com.superdextor.adinferos.entity.monster.EntityCurse;
import com.superdextor.adinferos.entity.monster.EntityGhost;
import com.superdextor.adinferos.entity.monster.EntityHerobrine;
import com.superdextor.adinferos.entity.monster.EntityHerobrineClone;
import com.superdextor.adinferos.entity.monster.EntityInfernumAvis;
import com.superdextor.adinferos.entity.monster.EntityNetherSkeleton;
import com.superdextor.adinferos.entity.monster.EntityObsidianSheepman;
import com.superdextor.adinferos.entity.monster.EntityPhantom;
import com.superdextor.adinferos.entity.monster.EntityReaper;
import com.superdextor.adinferos.entity.monster.EntitySkeletonHorse;
import com.superdextor.adinferos.entity.monster.EntitySummoner;
import com.superdextor.adinferos.entity.other.EntityInfernalChicken;
import com.superdextor.adinferos.entity.other.EntityNetherArrow;
import com.superdextor.adinferos.init.NetherBlocks;
import com.superdextor.adinferos.init.NetherFluids;
import com.superdextor.adinferos.init.NetherItems;
import com.superdextor.adinferos.render.RenderBlackWidow;
import com.superdextor.adinferos.render.RenderCurse;
import com.superdextor.adinferos.render.RenderGhost;
import com.superdextor.adinferos.render.RenderHerobrine;
import com.superdextor.adinferos.render.RenderHerobrineClone;
import com.superdextor.adinferos.render.RenderInfernalChicken;
import com.superdextor.adinferos.render.RenderInfernumAvis;
import com.superdextor.adinferos.render.RenderNetherArrow;
import com.superdextor.adinferos.render.RenderNetherSkeleton;
import com.superdextor.adinferos.render.RenderObsidianSheepman;
import com.superdextor.adinferos.render.RenderPhantom;
import com.superdextor.adinferos.render.RenderReaper;
import com.superdextor.adinferos.render.RenderSkeletonHorse;
import com.superdextor.adinferos.render.RenderSummoner;
import com.superdextor.adinferos.render.layer.LayerDemonPlayer;
import com.superdextor.adinferos.world.WorldProviderAbyss;
import com.superdextor.thinkbigcore.ThinkBigCore;
import com.superdextor.thinkbigcore.proxy.CustomMusicTicker;

import net.minecraft.block.Block;
import net.minecraft.block.state.IBlockState;
import net.minecraft.client.Minecraft;
import net.minecraft.client.audio.PositionedSoundRecord;
import net.minecraft.client.renderer.ItemMeshDefinition;
import net.minecraft.client.renderer.block.model.ModelBakery;
import net.minecraft.client.renderer.block.model.ModelResourceLocation;
import net.minecraft.client.renderer.block.statemap.IStateMapper;
import net.minecraft.client.renderer.block.statemap.StateMapperBase;
import net.minecraft.client.renderer.color.IItemColor;
import net.minecraft.client.renderer.entity.Render;
import net.minecraft.client.renderer.entity.RenderManager;
import net.minecraft.client.renderer.entity.RenderPlayer;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;
import net.minecraft.world.World;
import net.minecraftforge.client.model.ModelLoader;
import net.minecraftforge.fluids.Fluid;
import net.minecraftforge.fml.client.registry.IRenderFactory;
import net.minecraftforge.fml.client.registry.RenderingRegistry;


public class ClientProxy extends CommonProxy {
	
	public boolean isFightingAvis = false;
	public boolean isFightingHerobrine = false;
	private boolean hasUpdatedModel = false;
	
	@Override
	public void preInit() {
		super.preInit();
    	NetherModelManager.INSTANCE.registerAllModels();
		RenderingRegistry.registerEntityRenderingHandler(EntityBlackWidow.class, new BlackWidowRenderer());
		RenderingRegistry.registerEntityRenderingHandler(EntityReaper.class, new ReaperRenderer());
		RenderingRegistry.registerEntityRenderingHandler(EntityObsidianSheepman.class, new ObsidianSheepmanRenderer());
		RenderingRegistry.registerEntityRenderingHandler(EntityCurse.class, new CurseRenderer());
		RenderingRegistry.registerEntityRenderingHandler(EntityNetherSkeleton.class, new NetherSkeletonRenderer());
		RenderingRegistry.registerEntityRenderingHandler(EntitySkeletonHorse.class, new SkeletonHorseRenderer());
		RenderingRegistry.registerEntityRenderingHandler(EntityPhantom.class, new PhantomRenderer());
		RenderingRegistry.registerEntityRenderingHandler(EntityNetherArrow.class, new NetherArrowRenderer());
		RenderingRegistry.registerEntityRenderingHandler(EntityInfernumAvis.class, new InfernumAvisRenderer());
		RenderingRegistry.registerEntityRenderingHandler(EntityHerobrine.class, new HerobrineRenderer());
		RenderingRegistry.registerEntityRenderingHandler(EntityHerobrineClone.class, new HerobrineCloneRenderer());
		RenderingRegistry.registerEntityRenderingHandler(EntitySummoner.class, new SummonerRenderer());
		RenderingRegistry.registerEntityRenderingHandler(EntityInfernalChicken.class, new InfernalChinkenRenderer());
		RenderingRegistry.registerEntityRenderingHandler(EntityGhost.class, new GhostRenderer());
		NetherConfig.printDebugInfo("Registered Renderers");
		this.registerFluidModels(NetherFluids.fluidAcid);
	}
	
	@Override
    public void init() {
		super.init();
	}
	
	public void postInit() {
		super.postInit();
		
		this.registerBlockVariants();
		this.registerItemVariants();
		
		Minecraft.getMinecraft().getItemColors().registerItemColorHandler(new IItemColor()
        {
            public int getColorFromItemstack(ItemStack stack, int tintIndex)
            {
                return (tintIndex == 0 ? -1 : NetherItems.TRIBE_HEADBAND.getColor(stack));
            }
        }, new Item[] {NetherItems.TRIBE_HEADBAND});
		
		Minecraft.getMinecraft().getItemColors().registerItemColorHandler(new IItemColor()
        {
            public int getColorFromItemstack(ItemStack stack, int tintIndex)
            {
                return (tintIndex == 0 ? -1 : NetherItems.AMULET.getColor(stack));
            }
        }, new Item[] {NetherItems.AMULET});
		
		CustomMusicTicker.register(new CustomMusicTicker.MusicEntry(AdInferosSounds.MUSIC_INFERNUM_AVIS, 20, 20) {
			
			public boolean shouldSelect(Minecraft mc) {
				return ThinkBigCore.proxy.getClientPlayer().isEntityAlive() && AdInferosCore.proxy.isFightingAvis();
			}
		});
		
		CustomMusicTicker.register(new CustomMusicTicker.MusicEntry(AdInferosSounds.MUSIC_HEROBRINE, 20, 20) {
			
			public boolean shouldSelect(Minecraft mc) {
				return ThinkBigCore.proxy.getClientPlayer().isEntityAlive() && AdInferosCore.proxy.isFightingHerobrine();
			}
		});
		
		CustomMusicTicker.register(new CustomMusicTicker.MusicEntry(AdInferosSounds.MUSIC_ABYSS, 1200, 3600) {
			
			public boolean shouldSelect(Minecraft mc) {
				return mc.theWorld.provider instanceof WorldProviderAbyss;
			}
		});
    }
	
	public boolean isFightingAvis() {
		return this.isFightingAvis;
	}
	
	public boolean isFightingHerobrine() {
		return this.isFightingHerobrine;
	}
	
	public void doUpdate() {
		EntityPlayer playerIn = ThinkBigCore.proxy.getClientPlayer();
		World worldIn = playerIn.worldObj;
		
		if(!playerIn.isEntityAlive() && (this.isFightingAvis || this.isFightingHerobrine)) {
			Minecraft.getMinecraft().getSoundHandler().playSound(PositionedSoundRecord.getMusicRecord(AdInferosSounds.MUSIC_DEATH));
			this.isFightingAvis = false;
			this.isFightingHerobrine = false;
			return;
		}
		
        for (EntityHerobrine herobrine : worldIn.getEntities(EntityHerobrine.class, new Predicate<EntityHerobrine>()
    {
        public boolean apply(EntityHerobrine herobrine)
            {
                return herobrine.isAttacking() && herobrine.isEntityAlive();
            }
        }))
        {
    		this.isFightingAvis = false;
    		this.isFightingHerobrine = true;
    		return;
        }
		
        for (EntityInfernumAvis infernumAvis : worldIn.getEntities(EntityInfernumAvis.class, new Predicate<EntityInfernumAvis>()
    {
        public boolean apply(EntityInfernumAvis infernumAvis)
            {
                return !infernumAvis.isSleeping() && infernumAvis.isEntityAlive();
            }
        }))
        {
    		if(infernumAvis.getDistanceSqToEntity(playerIn) < 800) {
    	        this.isFightingHerobrine = false;
    			this.isFightingAvis = true;
    			return;
    		}
        }
        
        this.isFightingAvis = false;
        this.isFightingHerobrine = false;
	}
	
	public void updatePlayerModel() {
		
		if(this.hasUpdatedModel) {
			return;
		}
		
		this.hasUpdatedModel = true;
		
		RenderManager manager = Minecraft.getMinecraft().getRenderManager();
		Render render = null;
		
		try {
			render = manager.getEntityRenderObject(Minecraft.getMinecraft().thePlayer);
		}
		catch(Exception exception) {
			logger.warn("[pre] Failed to get the Player Renderer");
			return;
		}
		
		if(render instanceof RenderPlayer) {
			RenderPlayer renderPlayer = (RenderPlayer) render;
			renderPlayer.addLayer(new LayerDemonPlayer(renderPlayer));
			logger.info("[init] Player Model was updated");
		}
		else {
			logger.warn("[post] Failed to get the Player Renderer");
		}
	}
	
	private void registerBlockVariants() {
        ResourceLocation[] netheriteTypes = new ResourceLocation[2];
        ResourceLocation[] witherTypes = new ResourceLocation[2];
        ResourceLocation[] witherBlockTypes = new ResourceLocation[2];
        ResourceLocation[] logTypes = new ResourceLocation[4];
        ResourceLocation[] plankTypes = new ResourceLocation[4];
        ResourceLocation[] slabTypes = new ResourceLocation[4];
        ResourceLocation[] leaveTypes = new ResourceLocation[4];
        ResourceLocation[] saplingTypes = new ResourceLocation[4];
        ResourceLocation[] stoneSlabTypes = new ResourceLocation[2];
        ResourceLocation[] craftingTableTypes = new ResourceLocation[4];
        ResourceLocation[] spikeTypes = new ResourceLocation[2];
        String[] oreValues = {"", "_darkstone"};
        String[] blockValues = {"", "_gem"};
        String[] woodValues = {"", "magma_", "phantom_", "ash_"};
        String[] stoneSlabValues = {"", "_obsidian"};

        for (int j = 0; j < 2; ++j)
        {
        	netheriteTypes[j] = new ResourceLocation("adinferos", "netherite_ore" + oreValues[j]);
        	witherTypes[j] = new ResourceLocation("adinferos", "wither_ore" + oreValues[j]);
        	spikeTypes[j] = new ResourceLocation("adinferos", "spikes" + oreValues[j]);
        	witherBlockTypes[j] = new ResourceLocation("adinferos", "wither_block" + blockValues[j]);
        	stoneSlabTypes[j] = new ResourceLocation("adinferos", "stone_slab" + stoneSlabValues[j]);
    	   	Minecraft.getMinecraft().getRenderItem().getItemModelMesher().register(Item.getItemFromBlock(NetherBlocks.NETHERITE_ORE), j, new ModelResourceLocation("adinferos:netherite_ore" + oreValues[j], "inventory"));
    	   	Minecraft.getMinecraft().getRenderItem().getItemModelMesher().register(Item.getItemFromBlock(NetherBlocks.WITHER_ORE), j, new ModelResourceLocation("adinferos:wither_ore" + oreValues[j], "inventory"));
    	   	Minecraft.getMinecraft().getRenderItem().getItemModelMesher().register(Item.getItemFromBlock(NetherBlocks.SPIKES), j, new ModelResourceLocation("adinferos:spikes" + oreValues[j], "inventory"));
    	   	Minecraft.getMinecraft().getRenderItem().getItemModelMesher().register(Item.getItemFromBlock(NetherBlocks.WITHER_BLOCK), j, new ModelResourceLocation("adinferos:wither_block" + blockValues[j], "inventory"));
    	   	Minecraft.getMinecraft().getRenderItem().getItemModelMesher().register(Item.getItemFromBlock(NetherBlocks.STONE_SLAB), j, new ModelResourceLocation("adinferos:stone_slab" + stoneSlabValues[j], "inventory"));
        }
        
        for(int j = 0; j < 4; j++) {
        	logTypes[j] = new ResourceLocation("adinferos", woodValues[j] + "log");
        	plankTypes[j] = new ResourceLocation("adinferos", woodValues[j] + "planks");
        	slabTypes[j] = new ResourceLocation("adinferos", woodValues[j] + "wooden_slab");
        	leaveTypes[j] = new ResourceLocation("adinferos", woodValues[j] + "leaves");
        	saplingTypes[j] = new ResourceLocation("adinferos", woodValues[j] + "sapling");
        	craftingTableTypes[j] = new ResourceLocation("adinferos", woodValues[j] + "crafting_table");
    	   	Minecraft.getMinecraft().getRenderItem().getItemModelMesher().register(Item.getItemFromBlock(NetherBlocks.LOG), j, new ModelResourceLocation("adinferos:" + woodValues[j] + "log", "inventory"));
    	   	Minecraft.getMinecraft().getRenderItem().getItemModelMesher().register(Item.getItemFromBlock(NetherBlocks.PLANKS), j, new ModelResourceLocation("adinferos:" +  woodValues[j] + "planks", "inventory"));
    	   	Minecraft.getMinecraft().getRenderItem().getItemModelMesher().register(Item.getItemFromBlock(NetherBlocks.WOODEN_SLAB), j, new ModelResourceLocation("adinferos:" +  woodValues[j] + "wooden_slab", "inventory"));
    	   	Minecraft.getMinecraft().getRenderItem().getItemModelMesher().register(Item.getItemFromBlock(NetherBlocks.LEAVES), j, new ModelResourceLocation("adinferos:" +  woodValues[j] + "leaves", "inventory"));
    	   	Minecraft.getMinecraft().getRenderItem().getItemModelMesher().register(Item.getItemFromBlock(NetherBlocks.SAPLING), j, new ModelResourceLocation("adinferos:" +  woodValues[j] + "sapling", "inventory"));
    	   	Minecraft.getMinecraft().getRenderItem().getItemModelMesher().register(Item.getItemFromBlock(NetherBlocks.CRAFTING_TABLE), j, new ModelResourceLocation("adinferos:" +  woodValues[j] + "crafting_table", "inventory"));
        }
        
        ModelBakery.registerItemVariants(Item.getItemFromBlock(NetherBlocks.NETHERITE_ORE), netheriteTypes);
        ModelBakery.registerItemVariants(Item.getItemFromBlock(NetherBlocks.WITHER_ORE), witherTypes);
        ModelBakery.registerItemVariants(Item.getItemFromBlock(NetherBlocks.SPIKES), spikeTypes);
        ModelBakery.registerItemVariants(Item.getItemFromBlock(NetherBlocks.WITHER_BLOCK), witherBlockTypes);
        ModelBakery.registerItemVariants(Item.getItemFromBlock(NetherBlocks.LOG), logTypes);
        ModelBakery.registerItemVariants(Item.getItemFromBlock(NetherBlocks.PLANKS), plankTypes);
        ModelBakery.registerItemVariants(Item.getItemFromBlock(NetherBlocks.WOODEN_SLAB), slabTypes);
        ModelBakery.registerItemVariants(Item.getItemFromBlock(NetherBlocks.LEAVES), leaveTypes);
        ModelBakery.registerItemVariants(Item.getItemFromBlock(NetherBlocks.SAPLING), saplingTypes);
        ModelBakery.registerItemVariants(Item.getItemFromBlock(NetherBlocks.STONE_SLAB), stoneSlabTypes);
        ModelBakery.registerItemVariants(Item.getItemFromBlock(NetherBlocks.CRAFTING_TABLE), craftingTableTypes);
	}
	
	private void registerItemVariants() {
		
		Minecraft.getMinecraft().getRenderItem().getItemModelMesher().register(NetherItems.AMULET, 1, new ModelResourceLocation("adinferos:amulet", "inventory"));
		Minecraft.getMinecraft().getRenderItem().getItemModelMesher().register(NetherItems.AMULET, 2, new ModelResourceLocation("adinferos:amulet", "inventory"));
		Minecraft.getMinecraft().getRenderItem().getItemModelMesher().register(NetherItems.AMULET, 3, new ModelResourceLocation("adinferos:amulet", "inventory"));
		Minecraft.getMinecraft().getRenderItem().getItemModelMesher().register(NetherItems.AMULET, 4, new ModelResourceLocation("adinferos:amulet", "inventory"));
		
        ResourceLocation[] bloodBucketTypes = new ResourceLocation[2];
        ResourceLocation[] upgradeTypes = new ResourceLocation[21];
        String[] bucketValues = {"", "_ghost"};
        String[] upgradeValues = {"", "_radius", "_radius", "_radius", "_speed", "_speed", "_speed", "_speed", "_speed", "_rules", "_blood", "_blood", "_blood", "_blood", "_blood", "_count", "_count", "_count", "_maxcount", "_maxcount", "_maxcount"};
        
        for(int i = 0; i < 2; i++) {
        	bloodBucketTypes[i] = new ResourceLocation("adinferos", "golden_bucket_blood" + bucketValues[i]);
    	   	Minecraft.getMinecraft().getRenderItem().getItemModelMesher().register(NetherItems.GOLDEN_BUCKET_BLOOD, i, new ModelResourceLocation("adinferos:golden_bucket_blood" + bucketValues[i], "inventory"));
        }
        
        for(int i = 0; i < 21; i++) {
        	upgradeTypes[i] = new ResourceLocation("adinferos", "spawner_upgrade" + upgradeValues[i]);
    	   	Minecraft.getMinecraft().getRenderItem().getItemModelMesher().register(NetherItems.SPAWNER_UPGRADE, i, new ModelResourceLocation("adinferos:spawner_upgrade" + upgradeValues[i], "inventory"));
        }
        
        ModelBakery.registerItemVariants(NetherItems.GOLDEN_BUCKET_BLOOD, bloodBucketTypes);
        ModelBakery.registerItemVariants(NetherItems.SPAWNER_UPGRADE, upgradeTypes);
	}
	
    public void registerFluidModels(Fluid fluid) {
        if (fluid == null) {
            return;
        }
        Block block = fluid.getBlock();
        if (block != null) {
            Item item = Item.getItemFromBlock((Block)block);
            FluidStateMapper mapper = new FluidStateMapper(fluid);
            if (item != null) {
                ModelLoader.registerItemVariants((Item)item, (ResourceLocation[])new ResourceLocation[0]);
                ModelLoader.setCustomMeshDefinition((Item)item, (ItemMeshDefinition)mapper);
            }
            ModelLoader.setCustomStateMapper((Block)block, (IStateMapper)mapper);
        }
    }
    
    public static class FluidStateMapper extends StateMapperBase
    implements ItemMeshDefinition {
        public final Fluid fluid;
        public final ModelResourceLocation location;

        public FluidStateMapper(Fluid fluid) {
            this.fluid = fluid;
            this.location = new ModelResourceLocation(new ResourceLocation("adinferos", "block_acid"), fluid.getName());
        }

        protected ModelResourceLocation getModelResourceLocation(IBlockState state) {
            return this.location;
        }

        public ModelResourceLocation getModelLocation(ItemStack stack) {
            return this.location;
        }
    }
    
	private class BlackWidowRenderer implements IRenderFactory<EntityBlackWidow> {

		public Render createRenderFor(RenderManager manager) {
			return new RenderBlackWidow(manager);
		}
	}
	
	private class ReaperRenderer implements IRenderFactory<EntityReaper> {

		public Render createRenderFor(RenderManager manager) {
			return new RenderReaper(manager);
		}
	}
	
	private class ObsidianSheepmanRenderer implements IRenderFactory<EntityObsidianSheepman> {

		public Render createRenderFor(RenderManager manager) {
			return new RenderObsidianSheepman(manager);
		}
	}
	
	private class CurseRenderer implements IRenderFactory<EntityCurse> {

		public Render createRenderFor(RenderManager manager) {
			return new RenderCurse(manager);
		}
	}
	
	private class NetherSkeletonRenderer implements IRenderFactory<EntityNetherSkeleton> {

		public Render createRenderFor(RenderManager manager) {
			return new RenderNetherSkeleton(manager);
		}
	}
	
	private class SkeletonHorseRenderer implements IRenderFactory<EntitySkeletonHorse> {

		public Render createRenderFor(RenderManager manager) {
			return new RenderSkeletonHorse(manager);
		}
	}
	
	private class PhantomRenderer implements IRenderFactory<EntityPhantom> {

		public Render createRenderFor(RenderManager manager) {
			return new RenderPhantom(manager);
		}
	}
	
	private class NetherArrowRenderer implements IRenderFactory<EntityNetherArrow> {

		public Render createRenderFor(RenderManager manager) {
			return new RenderNetherArrow(manager);
		}
	}
	
	private class InfernumAvisRenderer implements IRenderFactory<EntityInfernumAvis> {

		public Render createRenderFor(RenderManager manager) {
			return new RenderInfernumAvis(manager);
		}
	}
	
	private class HerobrineRenderer implements IRenderFactory<EntityHerobrine> {

		public Render createRenderFor(RenderManager manager) {
			return new RenderHerobrine(manager);
		}
	}
	
	private class HerobrineCloneRenderer implements IRenderFactory<EntityHerobrineClone> {

		public Render createRenderFor(RenderManager manager) {
			return new RenderHerobrineClone(manager);
		}
	}
	
	private class SummonerRenderer implements IRenderFactory<EntitySummoner> {

		public Render createRenderFor(RenderManager manager) {
			return new RenderSummoner(manager);
		}
	}
	
	private class InfernalChinkenRenderer implements IRenderFactory<EntityInfernalChicken> {

		public Render createRenderFor(RenderManager manager) {
			return new RenderInfernalChicken(manager);
		}
	}
	
	private class GhostRenderer implements IRenderFactory<EntityGhost> {

		public Render createRenderFor(RenderManager manager) {
			return new RenderGhost(manager);
		}
	}
}
