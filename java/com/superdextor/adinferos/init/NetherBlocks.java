//Copyrighted 2015 Think big Corp, superdextor, obsidiancow (All Rights Reserved)

package com.superdextor.adinferos.init;

import java.util.List;

import com.superdextor.adinferos.AdInferosReference;
import com.superdextor.adinferos.AdInferosTab;
import com.superdextor.adinferos.blocks.BlockAbyssPortal;
import com.superdextor.adinferos.blocks.BlockAltar;
import com.superdextor.adinferos.blocks.BlockBloodHolder;
import com.superdextor.adinferos.blocks.BlockChiseledNetherrack;
import com.superdextor.adinferos.blocks.BlockCurse;
import com.superdextor.adinferos.blocks.BlockDarkFire;
import com.superdextor.adinferos.blocks.BlockDarkSand;
import com.superdextor.adinferos.blocks.BlockDimstone;
import com.superdextor.adinferos.blocks.BlockExtractor;
import com.superdextor.adinferos.blocks.BlockGhost;
import com.superdextor.adinferos.blocks.BlockGlowstoneTorch;
import com.superdextor.adinferos.blocks.BlockHardenedLava;
import com.superdextor.adinferos.blocks.BlockHellCrops;
import com.superdextor.adinferos.blocks.BlockHellDoor;
import com.superdextor.adinferos.blocks.BlockHellFenceGate;
import com.superdextor.adinferos.blocks.BlockHellOre;
import com.superdextor.adinferos.blocks.BlockHugePurpleMushroom;
import com.superdextor.adinferos.blocks.BlockInfernalHay;
import com.superdextor.adinferos.blocks.BlockNetherCraftingTable;
import com.superdextor.adinferos.blocks.BlockNetherFarmland;
import com.superdextor.adinferos.blocks.BlockNetherFurnace;
import com.superdextor.adinferos.blocks.BlockNetherLeaf;
import com.superdextor.adinferos.blocks.BlockNetherLog;
import com.superdextor.adinferos.blocks.BlockNetherPlanks;
import com.superdextor.adinferos.blocks.BlockNetherSapling;
import com.superdextor.adinferos.blocks.BlockNetherSlab;
import com.superdextor.adinferos.blocks.BlockNetherSlab2;
import com.superdextor.adinferos.blocks.BlockNetherSpawn;
import com.superdextor.adinferos.blocks.BlockPurpleMushroom;
import com.superdextor.adinferos.blocks.BlockSoulGlass;
import com.superdextor.adinferos.blocks.BlockSoulOre;
import com.superdextor.adinferos.blocks.BlockSoulPane;
import com.superdextor.adinferos.blocks.BlockSoulTNT;
import com.superdextor.adinferos.blocks.BlockSpawner;
import com.superdextor.adinferos.blocks.BlockSpikes;
import com.superdextor.adinferos.blocks.BlockWitherBlock;
import com.superdextor.adinferos.config.NetherConfig;
import com.superdextor.adinferos.items.ItemTwoBlock;
import com.superdextor.adinferos.items.ItemWoodBlock;
import com.superdextor.thinkbigcore.blocks.BlockCustom;
import com.superdextor.thinkbigcore.blocks.BlockCustomFence;
import com.superdextor.thinkbigcore.blocks.BlockCustomOre;
import com.superdextor.thinkbigcore.blocks.BlockCustomPane;
import com.superdextor.thinkbigcore.blocks.BlockCustomPressurePlate;
import com.superdextor.thinkbigcore.blocks.BlockCustomStairs;
import com.superdextor.thinkbigcore.blocks.BlockCustomTrapDoor;
import com.superdextor.thinkbigcore.helpers.RegistryHelper;

import net.minecraft.block.Block;
import net.minecraft.block.BlockSlab;
import net.minecraft.block.SoundType;
import net.minecraft.block.material.MapColor;
import net.minecraft.block.material.Material;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.ItemSlab;
import net.minecraft.item.ItemStack;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import net.minecraftforge.oredict.OreDictionary;

public class NetherBlocks {
	
	public static final Block INFERNO_DOOR = new BlockHellDoor(Material.WOOD).setHardness(3.5F).setResistance(8.0F).setUnlocalizedName("inferno_door");
	public static final Block MAGMA_DOOR = new BlockHellDoor(Material.WOOD).setHardness(3.5F).setResistance(8.0F).setUnlocalizedName("magma_door");
	public static final Block PHANTOM_DOOR = new BlockHellDoor(Material.WOOD).setHardness(3.5F).setResistance(8.0F).setUnlocalizedName("phantom_door");
	public static final Block ASH_DOOR = new BlockHellDoor(Material.WOOD).setHardness(3.5F).setResistance(8.0F).setUnlocalizedName("ash_door");
    public static final Block NETHERITE_ORE = new BlockHellOre().setHarvestLevel(3).setFortuneBase(3).setHardness(3.0F).setResistance(5.0F).setUnlocalizedName("netherite_ore").setCreativeTab(AdInferosTab.AI_BUILDING);
    public static final Block NETHERITE_BLOCK = new BlockCustom(Material.IRON, SoundType.METAL, MapColor.IRON).setBeaconBlock().setHardness(6.0F).setResistance(20.0F).setLightLevel(0.9F).setUnlocalizedName("netherite_block").setCreativeTab(AdInferosTab.AI_BUILDING);
    public static final Block GLOWSTONE_TORCH = new BlockGlowstoneTorch().setUnlocalizedName("glowstone_torch").setCreativeTab(AdInferosTab.AI_TOOLS);
    public static final BlockCurse CURSE = (BlockCurse) new BlockCurse(Material.CIRCUITS).setUnlocalizedName("curse");
	public static final Block WITHER_ORE = new BlockHellOre(NetherItems.WITHER_DUST, 3, 6, 3).setHarvestLevel(2).setHardness(3.0F).setResistance(5.0F).setUnlocalizedName("wither_ore").setCreativeTab(AdInferosTab.AI_BUILDING);
	public static final Block WITHER_BLOCK = new BlockWitherBlock(Material.IRON, 0).setUnlocalizedName("wither_block").setCreativeTab(AdInferosTab.AI_BUILDING);
	public static final Block GOLD_ORE = new BlockCustomOre(Items.GOLD_NUGGET, 7, 13, 2).setHarvestLevel(1).setMapColor(MapColor.NETHERRACK).setHardness(3.0F).setResistance(5.0F).setUnlocalizedName("gold_ore").setCreativeTab(AdInferosTab.AI_BUILDING);
	public static final Block SOUL_TNT = new BlockSoulTNT().setUnlocalizedName("soul_tnt").setCreativeTab(AdInferosTab.AI_COMBAT);
	public static final Block LOG = new BlockNetherLog().setHardness(3.0F).setResistance(6.0F).setUnlocalizedName("log").setCreativeTab(AdInferosTab.AI_BUILDING);
	public static final Block PLANKS = new BlockNetherPlanks().setHardness(3.0F).setResistance(6.0F).setUnlocalizedName("planks").setCreativeTab(AdInferosTab.AI_BUILDING);
	public static final Block INFERNO_STAIRS = new BlockCustomStairs(PLANKS.getDefaultState()).setUnlocalizedName("inferno_stairs").setCreativeTab(AdInferosTab.AI_BUILDING);
	public static final Block MAGMA_STAIRS = new BlockCustomStairs(PLANKS.getStateFromMeta(1)).setUnlocalizedName("magma_stairs").setCreativeTab(AdInferosTab.AI_BUILDING);
	public static final Block PHANTOM_STAIRS = new BlockCustomStairs(PLANKS.getStateFromMeta(2)).setUnlocalizedName("phantom_stairs").setCreativeTab(AdInferosTab.AI_BUILDING);
	public static final Block ASH_STAIRS = new BlockCustomStairs(PLANKS.getStateFromMeta(3)).setUnlocalizedName("ash_stairs").setCreativeTab(AdInferosTab.AI_BUILDING);
	public static final BlockSlab DOUBLE_WOODEN_SLAB = (BlockSlab) new BlockNetherSlab().setHardness(3.0F).setResistance(6.0F).setUnlocalizedName("double_wooden_slab");
	public static final BlockSlab WOODEN_SLAB = (BlockSlab) new BlockNetherSlab().setHardness(3.0F).setResistance(6.0F).setUnlocalizedName("wooden_slab").setCreativeTab(AdInferosTab.AI_BUILDING);
	public static final Block LEAVES = new BlockNetherLeaf().setUnlocalizedName("leaves").setCreativeTab(AdInferosTab.AI_BUILDING);
	public static final Block SAPLING = new BlockNetherSapling().setUnlocalizedName("sapling").setCreativeTab(AdInferosTab.AI_BUILDING);
	public static final Block SMOOTH_NETHERRACK = new BlockCustom(Material.ROCK, SoundType.STONE, MapColor.NETHERRACK).setFireBlock().setHardness(1.5F).setResistance(10.0F).setUnlocalizedName("smooth_netherrack").setCreativeTab(AdInferosTab.AI_BUILDING);
	public static final Block PURPLE_MUSHROOM = new BlockPurpleMushroom().setUnlocalizedName("purple_mushroom").setCreativeTab(AdInferosTab.AI_FOOD);
	public static final Block PURPLE_MUSHROOM_BLOCK = new BlockHugePurpleMushroom(Material.WOOD, MapColor.PURPLE, PURPLE_MUSHROOM).setHardness(0.2F).setLightLevel(0.3F).setUnlocalizedName("purple_mushroom_block");
	public static final BlockDarkFire DARK_FIRE = new BlockDarkFire();
	public static final BlockAbyssPortal ABYSS_PORTAL = new BlockAbyssPortal();
	public static final Block DARKSTONE = new BlockCustom(Material.ROCK, SoundType.METAL, MapColor.BLACK).setCustomHarvestLevel("pickaxe", 2).setHardness(5.0F).setResistance(20.0F).setUnlocalizedName("darkstone").setCreativeTab(AdInferosTab.AI_BUILDING);
	public static final Block DIMSTONE = new BlockDimstone(Material.GLASS).setHardness(0.3F).setLightLevel(0.2F).setUnlocalizedName("dimstone").setCreativeTab(AdInferosTab.AI_BUILDING);
	public static final Block DIMENSIONAL_ORE = new BlockCustomOre(NetherItems.DIMENSIONAL_DUST, 3, 6, 5).setHarvestLevel(3).setMapColor(MapColor.BLACK).setHardness(6.0F).setResistance(20.0F).setUnlocalizedName("dimensional_ore").setCreativeTab(AdInferosTab.AI_BUILDING);
	public static final Block SMOOTH_NETHERRACK_STAIRS = new BlockCustomStairs(SMOOTH_NETHERRACK).setUnlocalizedName("smooth_netherrack_stairs").setCreativeTab(AdInferosTab.AI_BUILDING);
	public static final BlockSlab DOUBLE_STONE_SLAB = (BlockSlab) new BlockNetherSlab2().setUnlocalizedName("double_stone_slab");
	public static final BlockSlab STONE_SLAB = (BlockSlab) new BlockNetherSlab2().setUnlocalizedName("stone_slab").setCreativeTab(AdInferosTab.AI_BUILDING);
	public static final Block OBSIDIAN_STAIRS = new BlockCustomStairs(Blocks.OBSIDIAN).setUnlocalizedName("obsidian_stairs").setCreativeTab(AdInferosTab.AI_BUILDING);
	public static final Block OBSIDIAN_FENCE = new BlockCustomFence(Material.ROCK, SoundType.STONE, MapColor.OBSIDIAN).setHardness(50.0F).setResistance(2000.0F).setUnlocalizedName("obsidian_fence").setCreativeTab(AdInferosTab.AI_BUILDING);
	public static final Block SOUL_GLASS = new BlockSoulGlass(Material.GLASS).setHardness(0.4F).setUnlocalizedName("soul_glass").setCreativeTab(AdInferosTab.AI_BUILDING);
	public static final Block SOUL_GLASS_PANE = new BlockSoulPane(Material.GLASS).setHardness(0.4F).setUnlocalizedName("soul_glass_pane").setCreativeTab(AdInferosTab.AI_BUILDING);
	public static final Block OBSIDIAN_FENCE_GATE = new BlockHellFenceGate(Material.ROCK, SoundType.STONE, MapColor.OBSIDIAN).setHardness(50.0F).setResistance(2000.0F).setUnlocalizedName("obsidian_fence_gate").setCreativeTab(AdInferosTab.AI_BUILDING);
	public static final Block INFERNO_FENCE = new BlockCustomFence(Material.WOOD, SoundType.WOOD, MapColor.RED).setHardness(3.0F).setResistance(6.0F).setUnlocalizedName("inferno_fence").setCreativeTab(AdInferosTab.AI_BUILDING);
	public static final Block MAGMA_FENCE = new BlockCustomFence(Material.WOOD, SoundType.WOOD, MapColor.TNT).setHardness(3.0F).setResistance(6.0F).setUnlocalizedName("magma_fence").setCreativeTab(AdInferosTab.AI_BUILDING);
	public static final Block PHANTOM_FENCE = new BlockCustomFence(Material.WOOD, SoundType.WOOD, MapColor.AIR).setHardness(3.0F).setResistance(6.0F).setUnlocalizedName("phantom_fence").setCreativeTab(AdInferosTab.AI_BUILDING);
	public static final Block ASH_FENCE = new BlockCustomFence(Material.WOOD, SoundType.WOOD, MapColor.GRAY).setHardness(3.0F).setResistance(6.0F).setUnlocalizedName("ash_fence").setCreativeTab(AdInferosTab.AI_BUILDING);
	public static final Block INFERNO_FENCE_GATE = new BlockHellFenceGate(Material.WOOD, SoundType.WOOD, MapColor.RED).setHardness(3.0F).setResistance(6.0F).setUnlocalizedName("inferno_fence_gate").setCreativeTab(AdInferosTab.AI_BUILDING);
	public static final Block MAGMA_FENCE_GATE = new BlockHellFenceGate(Material.WOOD, SoundType.WOOD, MapColor.TNT).setHardness(3.0F).setResistance(6.0F).setUnlocalizedName("magma_fence_gate").setCreativeTab(AdInferosTab.AI_BUILDING);
	public static final Block PHANTOM_FENCE_GATE = new BlockHellFenceGate(Material.WOOD, SoundType.WOOD, MapColor.AIR).setHardness(3.0F).setResistance(6.0F).setUnlocalizedName("phantom_fence_gate").setCreativeTab(AdInferosTab.AI_BUILDING);
	public static final Block ASH_FENCE_GATE = new BlockHellFenceGate(Material.WOOD, SoundType.WOOD, MapColor.GRAY).setHardness(3.0F).setResistance(6.0F).setUnlocalizedName("ash_fence_gate").setCreativeTab(AdInferosTab.AI_BUILDING);
	public static final Block NETHER_BRICK_FENCE_GATE = new BlockHellFenceGate(Material.ROCK, SoundType.STONE, MapColor.NETHERRACK).setHardness(2.0F).setResistance(10.0F).setUnlocalizedName("nether_brick_fence_gate").setCreativeTab(AdInferosTab.AI_BUILDING);
	public static final Block INFERNO_TRAPDOOR = new BlockCustomTrapDoor(Material.WOOD).setStepSound(SoundType.WOOD).setHardness(3.0F).setResistance(6.0F).setUnlocalizedName("inferno_trapdoor").setCreativeTab(AdInferosTab.AI_BUILDING);
	public static final Block MAGMA_TRAPDOOR = new BlockCustomTrapDoor(Material.WOOD).setStepSound(SoundType.WOOD).setHardness(3.0F).setResistance(6.0F).setUnlocalizedName("magma_trapdoor").setCreativeTab(AdInferosTab.AI_BUILDING);
	public static final Block PHANTOM_TRAPDOOR = new BlockCustomTrapDoor(Material.WOOD).setStepSound(SoundType.WOOD).setHardness(3.0F).setResistance(6.0F).setUnlocalizedName("phantom_trapdoor").setCreativeTab(AdInferosTab.AI_BUILDING);
	public static final Block ASH_TRAPDOOR = new BlockCustomTrapDoor(Material.WOOD).setStepSound(SoundType.WOOD).setHardness(3.0F).setResistance(6.0F).setUnlocalizedName("ash_trapdoor").setCreativeTab(AdInferosTab.AI_BUILDING);
	public static final Block INFERNO_TRAPDOOR_HIDDEN = new BlockCustomTrapDoor(Material.WOOD).setStepSound(SoundType.WOOD).setHardness(3.0F).setResistance(6.0F).setUnlocalizedName("inferno_trapdoor_hidden").setCreativeTab(AdInferosTab.AI_BUILDING);
	public static final Block MAGMA_TRAPDOOR_HIDDEN = new BlockCustomTrapDoor(Material.WOOD).setStepSound(SoundType.WOOD).setHardness(3.0F).setResistance(6.0F).setUnlocalizedName("magma_trapdoor_hidden").setCreativeTab(AdInferosTab.AI_BUILDING);
	public static final Block PHANTOM_TRAPDOOR_HIDDEN = new BlockCustomTrapDoor(Material.WOOD).setStepSound(SoundType.WOOD).setHardness(3.0F).setResistance(6.0F).setUnlocalizedName("phantom_trapdoor_hidden").setCreativeTab(AdInferosTab.AI_BUILDING);
	public static final Block ASH_TRAPDOOR_HIDDEN = new BlockCustomTrapDoor(Material.WOOD).setStepSound(SoundType.WOOD).setHardness(3.0F).setResistance(6.0F).setUnlocalizedName("ash_trapdoor_hidden").setCreativeTab(AdInferosTab.AI_BUILDING);
	public static final Block CHISELED_NETHERRACK = new BlockChiseledNetherrack().setHardness(1.5F).setResistance(10.0F).setUnlocalizedName("chiseled_netherrack").setCreativeTab(AdInferosTab.AI_BUILDING);
	public static final Block INFERNO_PRESSURE_PLATE = new BlockCustomPressurePlate(Material.WOOD, BlockCustomPressurePlate.Sensitivity.EVERYTHING).setStepSound(SoundType.WOOD).setHardness(0.5F).setUnlocalizedName("inferno_pressure_plate").setCreativeTab(AdInferosTab.AI_BUILDING);
	public static final Block MAGMA_PRESSURE_PLATE = new BlockCustomPressurePlate(Material.WOOD, BlockCustomPressurePlate.Sensitivity.EVERYTHING).setStepSound(SoundType.WOOD).setHardness(0.5F).setUnlocalizedName("magma_pressure_plate").setCreativeTab(AdInferosTab.AI_BUILDING);
	public static final Block PHANTOM_PRESSURE_PLATE = new BlockCustomPressurePlate(Material.WOOD, BlockCustomPressurePlate.Sensitivity.EVERYTHING).setStepSound(SoundType.WOOD).setHardness(0.5F).setUnlocalizedName("phantom_pressure_plate").setCreativeTab(AdInferosTab.AI_BUILDING);
	public static final Block ASH_PRESSURE_PLATE = new BlockCustomPressurePlate(Material.WOOD, BlockCustomPressurePlate.Sensitivity.EVERYTHING).setStepSound(SoundType.WOOD).setHardness(0.5F).setUnlocalizedName("ash_pressure_plate").setCreativeTab(AdInferosTab.AI_BUILDING);
	public static final Block HIDDEN_LIGHT = new BlockGhost(Material.CIRCUITS, true).setLightLevel(1.0F).setUnlocalizedName("hidden_light");
	public static final Block HIDDEN_BLOCK = new BlockGhost(Material.ROCK, false).setUnlocalizedName("hidden_block");
	public static final Block SPIKES = new BlockSpikes(Material.ROCK).setUnlocalizedName("spikes").setCreativeTab(AdInferosTab.AI_COMBAT).setHardness(0.3F);
	public static final Block SMOOTH_OBSIDIAN = new BlockCustom(Material.ROCK, SoundType.STONE, MapColor.OBSIDIAN).setHardness(50.0F).setResistance(2000.0F).setUnlocalizedName("smooth_obsidian").setCreativeTab(AdInferosTab.AI_BUILDING);
	public static final Block CHISELED_OBSIDIAN = new BlockCustom(Material.ROCK, SoundType.STONE, MapColor.OBSIDIAN).setHardness(50.0F).setResistance(2000.0F).setUnlocalizedName("chiseled_obsidian").setCreativeTab(AdInferosTab.AI_BUILDING);
	public static final Block DARK_BRICKS = new BlockCustom(Material.ROCK, SoundType.METAL, MapColor.OBSIDIAN).setHardness(56.0F).setResistance(2000.0F).setUnlocalizedName("dark_bricks").setCreativeTab(AdInferosTab.AI_BUILDING);
	public static final Block CRAFTING_TABLE = new BlockNetherCraftingTable().setUnlocalizedName("crafting_table").setHardness(3.0F).setResistance(10.0F).setCreativeTab(AdInferosTab.AI_TOOLS);
	public static final Block ALTAR = new BlockAltar().setUnlocalizedName("altar").setHardness(8.0F).setResistance(2000.0F).setCreativeTab(AdInferosTab.AI_TOOLS);
	public static final Block DARK_SAND = new BlockDarkSand().setHardness(0.6F).setResistance(4.0F).setUnlocalizedName("dark_sand").setCreativeTab(AdInferosTab.AI_BUILDING);
    public static final Block OBSIDIAN_BARS = new BlockCustomPane(Material.IRON, true).setStepSound(SoundType.METAL).setHardness(40.0F).setResistance(2000.0F).setUnlocalizedName("obsidian_bars").setCreativeTab(AdInferosTab.AI_BUILDING);
	public static final Block NETHERRACK_FURNACE = new BlockNetherFurnace(false).setUnlocalizedName("netherrack_furnace").setHardness(3.5F).setCreativeTab(AdInferosTab.AI_TOOLS);
	public static final Block LIT_NETHERRACK_FURNACE = new BlockNetherFurnace(true).setUnlocalizedName("lit_netherrack_furnace").setHardness(3.5F);
	public static final Block BLOOD_HOLDER = new BlockBloodHolder().setUnlocalizedName("blood_holder");
	public static final Block HARDENED_LAVA = new BlockHardenedLava().setUnlocalizedName("hardened_lava").setHardness(34.0F).setResistance(2000.0F).setLightLevel(1.0F).setCreativeTab(AdInferosTab.AI_BUILDING);
	public static final Block SOUL_ORE = new BlockSoulOre(NetherItems.SOUL_FRAGMENT, 2, 5, 2).setStepSound(SoundType.SAND).setHarvestLevelAndType("shovel", 2).setMapColor(MapColor.BROWN).setHardness(3.0F).setResistance(5.0F).setUnlocalizedName("soul_ore").setCreativeTab(AdInferosTab.AI_BUILDING);
    public static final Block EXTRACTOR = new BlockExtractor().setHardness(3.5F).setUnlocalizedName("extractor").setCreativeTab(AdInferosTab.AI_TOOLS);
	public static final Block SPAWNER = new BlockSpawner().setHardness(5.0F).setUnlocalizedName("spawner").setCreativeTab(AdInferosTab.AI_TOOLS);
    public static final Block NETHER_SPAWN = new BlockNetherSpawn().setHardness(5.0F).setUnlocalizedName("nether_spawn").setCreativeTab(AdInferosTab.AI_TOOLS);
	public static final Block PEAT_ORE = new BlockCustomOre(NetherItems.PEAT, 1, 1, 2).setHardness(3.0F).setResistance(5.0F).setUnlocalizedName("peat_ore").setCreativeTab(AdInferosTab.AI_BUILDING);
    public static final Block PEAT_BLOCK = new BlockCustom(Material.IRON, SoundType.METAL, MapColor.BROWN).setHardness(6.0F).setResistance(20.0F).setUnlocalizedName("peat_block").setCreativeTab(AdInferosTab.AI_BUILDING);
	public static final Block INFERNAL_WHEAT = new BlockHellCrops().setUnlocalizedName("infernal_wheat");
	public static final Block NETHER_FARMLAND = new BlockNetherFarmland().setHardness(0.4F).setUnlocalizedName("nether_farmland");
    public static final Block INFERNAL_HAY = new BlockInfernalHay() {
  	    @SideOnly(Side.CLIENT)
  		public void addInformation(ItemStack stack, EntityPlayer playerIn, List<String> tooltip, boolean advanced) {
  	    	tooltip.add("Gluten Free!");
  		}
  	}.setHardness(0.5F).setUnlocalizedName("infernal_hay").setCreativeTab(AdInferosTab.AI_FOOD);
	
    public static void register() {
    	
    	OBSIDIAN_STAIRS.setHarvestLevel("pickaxe", 3);
    	OBSIDIAN_FENCE.setHarvestLevel("pickaxe", 3);
    	OBSIDIAN_FENCE_GATE.setHarvestLevel("pickaxe", 3);
    	CHISELED_OBSIDIAN.setHarvestLevel("pickaxe", 3);
    	SMOOTH_OBSIDIAN.setHarvestLevel("pickaxe", 3);
    	OBSIDIAN_BARS.setHarvestLevel("pickaxe", 3);
    	NETHERITE_BLOCK.setHarvestLevel("pickaxe", 2);
    	WITHER_BLOCK.setHarvestLevel("pickaxe", 2);
    	DARK_BRICKS.setHarvestLevel("pickaxe", 2);
    	
    	String id = AdInferosReference.MOD_ID;
    	
    	//TAB BLOCKS
    	RegistryHelper.registerBlock(GOLD_ORE, id);
    	RegistryHelper.registerBlock(WITHER_ORE, new ItemTwoBlock(WITHER_ORE), id);
    	RegistryHelper.registerBlock(NETHERITE_ORE, new ItemTwoBlock(NETHERITE_ORE), id);
    	RegistryHelper.registerBlock(SOUL_ORE, id);
    	RegistryHelper.registerBlock(HARDENED_LAVA, id);
    	RegistryHelper.registerBlock(DIMENSIONAL_ORE, id);
    	RegistryHelper.registerBlock(DIMSTONE, id);
    	RegistryHelper.registerBlock(NETHERITE_BLOCK, id);
    	RegistryHelper.registerBlock(WITHER_BLOCK, new ItemTwoBlock(WITHER_BLOCK), id);
    	RegistryHelper.registerBlock(DARK_BRICKS, id);
    	RegistryHelper.registerBlock(DARKSTONE, id);
    	RegistryHelper.registerBlock(DARK_SAND, id);
    	RegistryHelper.registerBlock(SMOOTH_NETHERRACK, id);
    	RegistryHelper.registerBlock(CHISELED_NETHERRACK, id);
    	RegistryHelper.registerBlock(SMOOTH_NETHERRACK_STAIRS, id);
    	RegistryHelper.registerBlock(SOUL_GLASS, id);
    	RegistryHelper.registerBlock(SOUL_GLASS_PANE, id);
    	RegistryHelper.registerBlock(NETHER_BRICK_FENCE_GATE, id);
    	RegistryHelper.registerBlock(SMOOTH_OBSIDIAN, id);
    	RegistryHelper.registerBlock(CHISELED_OBSIDIAN, id);
    	RegistryHelper.registerBlock(OBSIDIAN_STAIRS, id);
    	RegistryHelper.registerBlock(OBSIDIAN_BARS, id);
    	RegistryHelper.registerBlock(OBSIDIAN_FENCE, id);
    	RegistryHelper.registerBlock(OBSIDIAN_FENCE_GATE, id);
    	RegistryHelper.registerBlock(SAPLING, new ItemWoodBlock(SAPLING), id);
    	RegistryHelper.registerBlock(LEAVES, new ItemWoodBlock(LEAVES), id);
    	RegistryHelper.registerBlock(INFERNO_PRESSURE_PLATE, id);
    	RegistryHelper.registerBlock(LOG, new ItemWoodBlock(LOG), id);
    	RegistryHelper.registerBlock(PLANKS, new ItemWoodBlock(PLANKS), id);
    	RegistryHelper.registerBlock(MAGMA_PRESSURE_PLATE, id);
    	RegistryHelper.registerBlock(INFERNO_FENCE, id);
    	RegistryHelper.registerBlock(MAGMA_FENCE, id);
    	RegistryHelper.registerBlock(PHANTOM_FENCE, id);
    	RegistryHelper.registerBlock(ASH_FENCE, id);
    	RegistryHelper.registerBlock(INFERNO_FENCE_GATE, id);
    	RegistryHelper.registerBlock(MAGMA_FENCE_GATE, id);
    	RegistryHelper.registerBlock(PHANTOM_FENCE_GATE, id);
    	RegistryHelper.registerBlock(ASH_FENCE_GATE, id);
    	RegistryHelper.registerBlock(PHANTOM_PRESSURE_PLATE, id);
    	RegistryHelper.registerBlock(INFERNO_TRAPDOOR, id);
    	RegistryHelper.registerBlock(MAGMA_TRAPDOOR, id);
    	RegistryHelper.registerBlock(PHANTOM_TRAPDOOR, id);
    	RegistryHelper.registerBlock(ASH_TRAPDOOR, id);
    	RegistryHelper.registerBlock(INFERNO_TRAPDOOR_HIDDEN, id);
    	RegistryHelper.registerBlock(MAGMA_TRAPDOOR_HIDDEN, id);
    	RegistryHelper.registerBlock(PHANTOM_TRAPDOOR_HIDDEN, id);
    	RegistryHelper.registerBlock(ASH_TRAPDOOR_HIDDEN, id);
    	RegistryHelper.registerBlock(ASH_PRESSURE_PLATE, id);
    	RegistryHelper.registerBlock(INFERNO_STAIRS, id);
    	RegistryHelper.registerBlock(MAGMA_STAIRS, id);
    	RegistryHelper.registerBlock(PHANTOM_STAIRS, id);
    	RegistryHelper.registerBlock(ASH_STAIRS, id);
    	RegistryHelper.registerBlock(DOUBLE_WOODEN_SLAB, null, id);
    	RegistryHelper.registerBlock(WOODEN_SLAB, new ItemSlab(WOODEN_SLAB, WOODEN_SLAB, DOUBLE_WOODEN_SLAB), id);
    	RegistryHelper.registerBlock(DOUBLE_STONE_SLAB, null, id);
    	RegistryHelper.registerBlock(STONE_SLAB, new ItemSlab(STONE_SLAB, STONE_SLAB, DOUBLE_STONE_SLAB), id);
    	RegistryHelper.registerBlock(INFERNO_DOOR, null, id);
    	RegistryHelper.registerBlock(MAGMA_DOOR, null, id);
    	RegistryHelper.registerBlock(PHANTOM_DOOR, null, id);
    	RegistryHelper.registerBlock(ASH_DOOR, null, id);
    	RegistryHelper.registerBlock(PEAT_ORE, id);
    	RegistryHelper.registerBlock(PEAT_BLOCK, id);
    	
    	//TAB TOOLS
    	RegistryHelper.registerBlock(GLOWSTONE_TORCH, id);
    	RegistryHelper.registerBlock(CRAFTING_TABLE, new ItemWoodBlock(CRAFTING_TABLE), id);
    	RegistryHelper.registerBlock(NETHERRACK_FURNACE, id);
    	RegistryHelper.registerBlock(LIT_NETHERRACK_FURNACE, null, id);
    	RegistryHelper.registerBlock(NETHER_SPAWN, id);
    	RegistryHelper.registerBlock(ALTAR, id);
    	RegistryHelper.registerBlock(EXTRACTOR, id);
    	RegistryHelper.registerBlock(SPAWNER, id);
    	
    	//TAB COMBAT
    	RegistryHelper.registerBlock(SOUL_TNT, id);
    	RegistryHelper.registerBlock(SPIKES, new ItemTwoBlock(SPIKES), id);
    	
    	//TAB FOOD
    	RegistryHelper.registerBlock(INFERNAL_HAY, id);
    	RegistryHelper.registerBlock(PURPLE_MUSHROOM, id);
    	
    	//TAB OTHER/UNCLASSED
    	RegistryHelper.registerBlock(ABYSS_PORTAL, id);
    	RegistryHelper.registerBlock(HIDDEN_LIGHT, id);
    	RegistryHelper.registerBlock(HIDDEN_BLOCK, id);
    	RegistryHelper.registerBlock(DARK_FIRE, null, id);
    	RegistryHelper.registerBlock(CURSE, null, id);
    	RegistryHelper.registerBlock(BLOOD_HOLDER, null, id);
    	RegistryHelper.registerBlock(PURPLE_MUSHROOM_BLOCK, id);
    	RegistryHelper.registerBlock(NETHER_FARMLAND, id);
    	RegistryHelper.registerBlock(INFERNAL_WHEAT, null, id);
    	
    	BlockDarkFire.initDarkfire();
    	
        OreDictionary.registerOre("orePeat", PEAT_ORE);
        OreDictionary.registerOre("oreGold", GOLD_ORE);
        OreDictionary.registerOre("oreWither", WITHER_ORE);
        OreDictionary.registerOre("oreNetherite", NETHERITE_ORE);
        OreDictionary.registerOre("oreDimensional", DIMENSIONAL_ORE);
        OreDictionary.registerOre("oreSoul", SOUL_ORE);
        OreDictionary.registerOre("blockGlass", SOUL_GLASS);
        OreDictionary.registerOre("paneGlass", SOUL_GLASS_PANE);
        OreDictionary.registerOre("treeSapling", new ItemStack(NetherBlocks.SAPLING, 1, OreDictionary.WILDCARD_VALUE));
        OreDictionary.registerOre("treeLeaves", new ItemStack(NetherBlocks.LEAVES, 1, OreDictionary.WILDCARD_VALUE));
        OreDictionary.registerOre("logWood", new ItemStack(NetherBlocks.LOG, 1, OreDictionary.WILDCARD_VALUE));
        OreDictionary.registerOre("plankWood", new ItemStack(NetherBlocks.PLANKS, 1, OreDictionary.WILDCARD_VALUE));
        OreDictionary.registerOre("stairWood", INFERNO_STAIRS);
        OreDictionary.registerOre("stairWood", MAGMA_STAIRS);
        OreDictionary.registerOre("stairWood", PHANTOM_STAIRS);
        OreDictionary.registerOre("stairWood", ASH_STAIRS);
        OreDictionary.registerOre("slabWood", new ItemStack(NetherBlocks.WOODEN_SLAB, 1, OreDictionary.WILDCARD_VALUE));
        OreDictionary.registerOre("workbench", new ItemStack(NetherBlocks.CRAFTING_TABLE, 1, OreDictionary.WILDCARD_VALUE));
        OreDictionary.registerOre("torch", GLOWSTONE_TORCH);
        OreDictionary.registerOre("blockNetherite", NETHERITE_BLOCK);
        OreDictionary.registerOre("blockWither", new ItemStack(NetherBlocks.WITHER_BLOCK, 1, OreDictionary.WILDCARD_VALUE));
        OreDictionary.registerOre("darkstone", DARKSTONE);
		NetherConfig.printDebugInfo("Registered Blocks");
    }
}
