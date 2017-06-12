package com.superdextor.adinferos.init;

import java.util.ArrayList;
import java.util.List;

import com.superdextor.adinferos.AdInferosReference;
import com.superdextor.adinferos.AdInferosSounds;
import com.superdextor.adinferos.AdInferosTab;
import com.superdextor.adinferos.config.NetherConfig;
import com.superdextor.adinferos.entity.monster.EntityBlackWidow;
import com.superdextor.adinferos.entity.monster.EntityCurse;
import com.superdextor.adinferos.entity.monster.EntityGhost;
import com.superdextor.adinferos.entity.monster.EntityGlowstoneSkeleton;
import com.superdextor.adinferos.entity.monster.EntityInfernumAvis;
import com.superdextor.adinferos.entity.monster.EntityObsidianSheepman;
import com.superdextor.adinferos.entity.monster.EntityPhantom;
import com.superdextor.adinferos.entity.monster.EntityReaper;
import com.superdextor.adinferos.entity.monster.EntitySkeletonHorse;
import com.superdextor.adinferos.entity.other.EntityInfernalChicken;
import com.superdextor.adinferos.items.ItemAmulet;
import com.superdextor.adinferos.items.ItemBloodBucket;
import com.superdextor.adinferos.items.ItemGlowstoneArmor;
import com.superdextor.adinferos.items.ItemGoldenBucket;
import com.superdextor.adinferos.items.ItemHellHoe;
import com.superdextor.adinferos.items.ItemHellSeeds;
import com.superdextor.adinferos.items.ItemHellSword;
import com.superdextor.adinferos.items.ItemNetherBow;
import com.superdextor.adinferos.items.ItemNetheriteArmor;
import com.superdextor.adinferos.items.ItemObsidianArmor;
import com.superdextor.adinferos.items.ItemScythe;
import com.superdextor.adinferos.items.ItemSoul;
import com.superdextor.adinferos.items.ItemSpawnerUpgrade;
import com.superdextor.adinferos.items.ItemStaff;
import com.superdextor.adinferos.items.ItemTribeHeadband;
import com.superdextor.adinferos.items.ItemWitherApple;
import com.superdextor.adinferos.items.ItemWitherArmor;
import com.superdextor.adinferos.items.ItemWitherDust;
import com.superdextor.adinferos.items.ItemWormHole;
import com.superdextor.thinkbigcore.helpers.InventoryHelper;
import com.superdextor.thinkbigcore.helpers.RegistryHelper;
import com.superdextor.thinkbigcore.items.ItemCustomArmor;
import com.superdextor.thinkbigcore.items.ItemCustomAxe;
import com.superdextor.thinkbigcore.items.ItemCustomPickaxe;
import com.superdextor.thinkbigcore.items.ItemCustomRecord;
import com.superdextor.thinkbigcore.items.ItemCustomShovel;
import com.superdextor.thinkbigcore.items.ItemCustomSpawnEgg;
import com.superdextor.thinkbigcore.items.ItemCustomSpawnEgg.CustomEggEntry;

import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.init.MobEffects;
import net.minecraft.init.SoundEvents;
import net.minecraft.inventory.EntityEquipmentSlot;
import net.minecraft.item.Item;
import net.minecraft.item.ItemArmor;
import net.minecraft.item.ItemDoor;
import net.minecraft.item.ItemFood;
import net.minecraft.item.ItemShears;
import net.minecraft.item.ItemStack;
import net.minecraft.potion.PotionEffect;
import net.minecraft.world.World;
import net.minecraftforge.common.util.EnumHelper;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import net.minecraftforge.oredict.OreDictionary;

public class NetherItems {
	
  	public static final Item QUARTZ_CHUNK = new Item().setUnlocalizedName("quartz_chunk").setCreativeTab(AdInferosTab.AI_OTHER);
  	public static final Item GLOWSTONE_CRYSTAL = new Item().setUnlocalizedName("glowstone_crystal").setCreativeTab(AdInferosTab.AI_OTHER);
  	public static final Item OBSIDIAN_INGOT = new Item().setUnlocalizedName("obsidian_ingot").setCreativeTab(AdInferosTab.AI_OTHER);
  	public static final Item NETHERITE_INGOT = new Item().setUnlocalizedName("netherite_ingot").setCreativeTab(AdInferosTab.AI_OTHER);
  	public static final Item WITHER_DUST = new ItemWitherDust().setUnlocalizedName("wither_dust").setCreativeTab(AdInferosTab.AI_OTHER);
  	public static final Item WITHER_GEM = new Item().setUnlocalizedName("wither_gem").setCreativeTab(AdInferosTab.AI_OTHER);
  	
	public static final Item.ToolMaterial QuartzToolMaterial = EnumHelper.addToolMaterial("QuartzToolMaterial", 1, 168, 6.0F, 1.0F, 20);
	public static final ItemArmor.ArmorMaterial QuartzArmorMaterial = InventoryHelper.createArmorMaterial("QuartzArmorMaterial", "adinferos:textures/models/armor/" , 12, new int[]{1,3,5,2}, 12, 0.0F, SoundEvents.ITEM_ARMOR_EQUIP_IRON, new ItemStack(NetherItems.QUARTZ_CHUNK));
	public static final Item.ToolMaterial GlowstoneToolMaterial = EnumHelper.addToolMaterial("GlowstoneToolMaterial", 2, 250, 10.0F, 2.0F, 20);
	public static final ItemArmor.ArmorMaterial GlowstoneArmorMaterial = InventoryHelper.createArmorMaterial("GlowstoneArmorMaterial", "adinferos:textures/models/armor/" , 17, new int[]{2,3,6,3}, 18, 0.0F, SoundEvents.ITEM_ARMOR_EQUIP_GOLD, new ItemStack(NetherItems.GLOWSTONE_CRYSTAL));
	public static final Item.ToolMaterial ObsidianToolMaterial = EnumHelper.addToolMaterial("ObsidianToolMaterial", 3, 1875, 6.5F, 3.0F, 7);
	public static final ItemArmor.ArmorMaterial ObsidianArmorMaterial = InventoryHelper.createArmorMaterial("ObsidianArmorMaterial" , "adinferos:textures/models/armor/" , 35, new int[]{3, 6, 8, 3}, 16, 3.0F, SoundEvents.ITEM_ARMOR_EQUIP_DIAMOND, new ItemStack(NetherItems.OBSIDIAN_INGOT));
	public static final Item.ToolMaterial NetheriteToolMaterial = EnumHelper.addToolMaterial("NetheriteToolMaterial", 3, 1754, 10.0F, 4.0F, 20);
	public static final ItemArmor.ArmorMaterial NetheriteArmorMaterial = InventoryHelper.createArmorMaterial("NetheriteArmorMaterial" , "adinferos:textures/models/armor/" , 39, new int[]{3,6,8,4}, 24, 3.0F, SoundEvents.ITEM_ARMOR_EQUIP_DIAMOND, new ItemStack(NetherItems.NETHERITE_INGOT));
	public static final Item.ToolMaterial WitherToolMaterial = EnumHelper.addToolMaterial("WitherToolMaterial", 3, 2456, 12.0F, 5.0F, 26);
	public static final ItemArmor.ArmorMaterial WitherArmorMaterial = InventoryHelper.createArmorMaterial("WitherArmorMaterial", "adinferos:textures/models/armor/" , 36, new int[]{3,6,8,4}, 32, 4.0F, SoundEvents.ITEM_ARMOR_EQUIP_IRON, new ItemStack(NetherItems.WITHER_GEM));
	
	public static final Item NETHERITE_SWORD = new ItemHellSword(NetheriteToolMaterial).setUnlocalizedName("netherite_sword").setCreativeTab(AdInferosTab.AI_COMBAT);
	public static final Item NETHERITE_AXE = new ItemCustomAxe(NetheriteToolMaterial).setUnlocalizedName("netherite_axe").setCreativeTab(AdInferosTab.AI_TOOLS);
	public static final Item NETHERITE_PICKAXE = new ItemCustomPickaxe(NetheriteToolMaterial).setUnlocalizedName("netherite_pickaxe").setCreativeTab(AdInferosTab.AI_TOOLS);
	public static final Item NETHERITE_SHOVEL = new ItemCustomShovel(NetheriteToolMaterial).setUnlocalizedName("netherite_shovel").setCreativeTab(AdInferosTab.AI_TOOLS);
	public static final Item NETHERITE_HOE = new ItemHellHoe(NetheriteToolMaterial).setUnlocalizedName("netherite_hoe").setCreativeTab(AdInferosTab.AI_TOOLS);
	public static final Item OBSIDIAN_SWORD = new ItemHellSword(ObsidianToolMaterial).setUnlocalizedName("obsidian_sword").setCreativeTab(AdInferosTab.AI_COMBAT);
	public static final Item OBSIDIAN_AXE = new ItemCustomAxe(ObsidianToolMaterial).setUnlocalizedName("obsidian_axe").setCreativeTab(AdInferosTab.AI_TOOLS);
	public static final Item OBSIDIAN_PICKAXE = new ItemCustomPickaxe(ObsidianToolMaterial).setUnlocalizedName("obsidian_pickaxe").setCreativeTab(AdInferosTab.AI_TOOLS);
	public static final Item OBSIDIAN_SHOVEL = new ItemCustomShovel(ObsidianToolMaterial).setUnlocalizedName("obsidian_shovel").setCreativeTab(AdInferosTab.AI_TOOLS);
	public static final Item OBSIDIAN_HOE = new ItemHellHoe(ObsidianToolMaterial).setUnlocalizedName("obsidian_hoe").setCreativeTab(AdInferosTab.AI_TOOLS);
	public static final Item WITHER_SWORD = new ItemHellSword(WitherToolMaterial, new PotionEffect(MobEffects.WITHER, 80, 1)).setUnlocalizedName("wither_sword").setCreativeTab(AdInferosTab.AI_COMBAT);
	public static final Item WITHER_AXE = new ItemCustomAxe(WitherToolMaterial, new PotionEffect(MobEffects.WITHER, 60, 1)).setUnlocalizedName("wither_axe").setCreativeTab(AdInferosTab.AI_TOOLS);
	public static final Item WITHER_PICKAXE = new ItemCustomPickaxe(WitherToolMaterial, new PotionEffect(MobEffects.WITHER, 40, 1)).setUnlocalizedName("wither_pickaxe").setCreativeTab(AdInferosTab.AI_TOOLS);
	public static final Item WITHER_SHOVEL = new ItemCustomShovel(WitherToolMaterial, new PotionEffect(MobEffects.WITHER, 20, 1)).setUnlocalizedName("wither_shovel").setCreativeTab(AdInferosTab.AI_TOOLS);
	public static final Item WITHER_HOE = new ItemHellHoe(WitherToolMaterial).setUnlocalizedName("wither_hoe").setCreativeTab(AdInferosTab.AI_TOOLS);
	public static final Item GLOWSTONE_SWORD = new ItemHellSword(GlowstoneToolMaterial).setUnlocalizedName("glowstone_sword").setCreativeTab(AdInferosTab.AI_COMBAT);
	public static final Item GLOWSTONE_AXE = new ItemCustomAxe(GlowstoneToolMaterial).setUnlocalizedName("glowstone_axe").setCreativeTab(AdInferosTab.AI_TOOLS);
	public static final Item GLOWSTONE_PICKAXE = new ItemCustomPickaxe(GlowstoneToolMaterial).setUnlocalizedName("glowstone_pickaxe").setCreativeTab(AdInferosTab.AI_TOOLS);
	public static final Item GLOWSTONE_SHOVEL = new ItemCustomShovel(GlowstoneToolMaterial).setUnlocalizedName("glowstone_shovel").setCreativeTab(AdInferosTab.AI_TOOLS);
	public static final Item GLOWSTONE_HOE = new ItemHellHoe(GlowstoneToolMaterial).setUnlocalizedName("glowstone_hoe").setCreativeTab(AdInferosTab.AI_TOOLS);
	public static final Item QUARTZ_SWORD = new ItemHellSword(QuartzToolMaterial).setUnlocalizedName("quartz_sword").setCreativeTab(AdInferosTab.AI_COMBAT);
	public static final Item QUARTZ_AXE = new ItemCustomAxe(QuartzToolMaterial).setUnlocalizedName("quartz_axe").setCreativeTab(AdInferosTab.AI_TOOLS);
	public static final Item QUARTZ_PICKAXE = new ItemCustomPickaxe(QuartzToolMaterial).setUnlocalizedName("quartz_pickaxe").setCreativeTab(AdInferosTab.AI_TOOLS);
	public static final Item QUARTZ_SHOVEL = new ItemCustomShovel(QuartzToolMaterial).setUnlocalizedName("quartz_shovel").setCreativeTab(AdInferosTab.AI_TOOLS);
	public static final Item QUARTZ_HOE = new ItemHellHoe(QuartzToolMaterial).setUnlocalizedName("quartz_hoe").setCreativeTab(AdInferosTab.AI_TOOLS);
	
	public static final Item INFERNO_STAFF = new ItemStaff(0).setUnlocalizedName("inferno_staff").setCreativeTab(AdInferosTab.AI_COMBAT);
	public static final Item SCYTHE = new ItemScythe().setUnlocalizedName("scythe").setCreativeTab(AdInferosTab.AI_COMBAT);
	public static final Item WITHER_STAFF = new ItemStaff(1).setUnlocalizedName("wither_staff").setCreativeTab(AdInferosTab.AI_COMBAT);
  	public static final Item QUARTZ_ARROW = new Item().setUnlocalizedName("quartz_arrow").setCreativeTab(AdInferosTab.AI_COMBAT);
	public static final Item QUARTZ_BOW = new ItemNetherBow("adinferos:quartz_bow", 168, NetherItems.QUARTZ_ARROW, 6, 0.7, 1.1F, 12, 1.0F, 0).setUnlocalizedName("quartz_bow").setCreativeTab(AdInferosTab.AI_COMBAT);
  	public static final Item GLOWSTONE_ARROW = new Item().setUnlocalizedName("glowstone_arrow").setCreativeTab(AdInferosTab.AI_COMBAT);
	public static final Item GLOWSTONE_BOW = new ItemNetherBow("adinferos:glowstone_bow", 250, NetherItems.GLOWSTONE_ARROW, -1, 1.1, 0.8F, 21, 0.8F, 1).setUnlocalizedName("glowstone_bow").setCreativeTab(AdInferosTab.AI_COMBAT);
  	public static final Item OBSIDIAN_ARROW = new Item().setUnlocalizedName("obsidian_arrow").setCreativeTab(AdInferosTab.AI_COMBAT);
	public static final Item OBSIDIAN_BOW = new ItemNetherBow("adinferos:obsidian_bow", 1875, NetherItems.OBSIDIAN_ARROW, -12, 4.0, 0.6F, 40, 0.6F, 2).setUnlocalizedName("obsidian_bow").setCreativeTab(AdInferosTab.AI_COMBAT);
  	public static final Item WITHER_ARROW = new Item().setUnlocalizedName("wither_arrow").setCreativeTab(AdInferosTab.AI_COMBAT);
	public static final Item WITHER_BOW = new ItemNetherBow("adinferos:wither_bow", 2456, NetherItems.WITHER_ARROW, 0, 1.5, 1.2F, 20, 1.2F, 3).setUnlocalizedName("wither_bow").setCreativeTab(AdInferosTab.AI_COMBAT);
  	public static final Item NETHERITE_ARROW = new Item().setUnlocalizedName("netherite_arrow").setCreativeTab(AdInferosTab.AI_COMBAT);
	public static final Item NETHERITE_BOW = new ItemNetherBow("adinferos:netherite_bow", 1754, NetherItems.NETHERITE_ARROW, 4, 1.3, 1.0F, 16, 1.4F, 4).setUnlocalizedName("netherite_bow").setCreativeTab(AdInferosTab.AI_COMBAT);
	
	public static final Item NETHERITE_HELMET = new ItemNetheriteArmor(NetheriteArmorMaterial, 0, EntityEquipmentSlot.HEAD).setUnlocalizedName("netherite_helmet").setCreativeTab(AdInferosTab.AI_COMBAT);
	public static final Item NETHERITE_CHESTPLATE = new ItemNetheriteArmor(NetheriteArmorMaterial, 0, EntityEquipmentSlot.CHEST).setUnlocalizedName("netherite_chestplate").setCreativeTab(AdInferosTab.AI_COMBAT);
	public static final Item NETHERITE_LEGGINGS = new ItemNetheriteArmor(NetheriteArmorMaterial, 0, EntityEquipmentSlot.LEGS).setUnlocalizedName("netherite_leggings").setCreativeTab(AdInferosTab.AI_COMBAT);
	public static final Item NETHERITE_BOOTS = new ItemNetheriteArmor(NetheriteArmorMaterial, 0, EntityEquipmentSlot.FEET).setUnlocalizedName("netherite_boots").setCreativeTab(AdInferosTab.AI_COMBAT);
	public static final Item OBSIDIAN_HELMET = new ItemObsidianArmor(ObsidianArmorMaterial, 0, EntityEquipmentSlot.HEAD).setUnlocalizedName("obsidian_helmet").setCreativeTab(AdInferosTab.AI_COMBAT);
	public static final Item OBSIDIAN_CHESTPLATE = new ItemObsidianArmor(ObsidianArmorMaterial, 0, EntityEquipmentSlot.CHEST).setUnlocalizedName("obsidian_chestplate").setCreativeTab(AdInferosTab.AI_COMBAT);
	public static final Item OBSIDIAN_LEGGINGS = new ItemObsidianArmor(ObsidianArmorMaterial, 0, EntityEquipmentSlot.LEGS).setUnlocalizedName("obsidian_leggings").setCreativeTab(AdInferosTab.AI_COMBAT);
	public static final Item OBSIDIAN_BOOTS = new ItemObsidianArmor(ObsidianArmorMaterial, 0, EntityEquipmentSlot.FEET).setUnlocalizedName("obsidian_boots").setCreativeTab(AdInferosTab.AI_COMBAT);
	public static final Item WITHER_HELMET = new ItemWitherArmor(WitherArmorMaterial, 0, EntityEquipmentSlot.HEAD).setUnlocalizedName("wither_helmet").setCreativeTab(AdInferosTab.AI_COMBAT);
	public static final Item WITHER_CHESTPLATE = new ItemWitherArmor(WitherArmorMaterial, 0, EntityEquipmentSlot.CHEST).setUnlocalizedName("wither_chestplate").setCreativeTab(AdInferosTab.AI_COMBAT);
	public static final Item WITHER_LEGGINGS = new ItemWitherArmor(WitherArmorMaterial, 0, EntityEquipmentSlot.LEGS).setUnlocalizedName("wither_leggings").setCreativeTab(AdInferosTab.AI_COMBAT);
	public static final Item WITHER_BOOTS = new ItemWitherArmor(WitherArmorMaterial, 0, EntityEquipmentSlot.FEET).setUnlocalizedName("wither_boots").setCreativeTab(AdInferosTab.AI_COMBAT);
	public static final Item GLOWSTONE_HELMET = new ItemGlowstoneArmor(GlowstoneArmorMaterial, 0, EntityEquipmentSlot.HEAD).setUnlocalizedName("glowstone_helmet").setCreativeTab(AdInferosTab.AI_COMBAT);
	public static final Item GLOWSTONE_CHESTPLATE = new ItemGlowstoneArmor(GlowstoneArmorMaterial, 0, EntityEquipmentSlot.CHEST).setUnlocalizedName("glowstone_chestplate").setCreativeTab(AdInferosTab.AI_COMBAT);
	public static final Item GLOWSTONE_LEGGINGS = new ItemGlowstoneArmor(GlowstoneArmorMaterial, 0, EntityEquipmentSlot.LEGS).setUnlocalizedName("glowstone_leggings").setCreativeTab(AdInferosTab.AI_COMBAT);
	public static final Item GLOWSTONE_BOOTS = new ItemGlowstoneArmor(GlowstoneArmorMaterial, 0, EntityEquipmentSlot.FEET).setUnlocalizedName("glowstone_boots").setCreativeTab(AdInferosTab.AI_COMBAT);
	public static final Item QUARTZ_HELMET = new ItemCustomArmor(QuartzArmorMaterial, 0, EntityEquipmentSlot.HEAD, "adinferos:textures/models/armor/quartz").setUnlocalizedName("quartz_helmet").setCreativeTab(AdInferosTab.AI_COMBAT);
	public static final Item QUARTZ_CHESTPLATE = new ItemCustomArmor(QuartzArmorMaterial, 0, EntityEquipmentSlot.CHEST, "adinferos:textures/models/armor/quartz").setUnlocalizedName("quartz_chestplate").setCreativeTab(AdInferosTab.AI_COMBAT);
	public static final Item QUARTZ_LEGGINGS = new ItemCustomArmor(QuartzArmorMaterial, 0, EntityEquipmentSlot.LEGS, "adinferos:textures/models/armor/quartz").setUnlocalizedName("quartz_leggings").setCreativeTab(AdInferosTab.AI_COMBAT);
  	public static final Item QUARTZ_BOOTS = new ItemCustomArmor(QuartzArmorMaterial, 0, EntityEquipmentSlot.FEET, "adinferos:textures/models/armor/quartz").setUnlocalizedName("quartz_boots").setCreativeTab(AdInferosTab.AI_COMBAT);
  	
  	public static final Item QUARTZ_STICK = new Item().setUnlocalizedName("quartz_stick").setFull3D().setCreativeTab(AdInferosTab.AI_OTHER);
  	public static final Item NETHERITE_NUGGET = new Item().setUnlocalizedName("netherite_nugget").setCreativeTab(AdInferosTab.AI_OTHER);
  	public static final Item OBSIDIAN_NUGGET = new Item().setUnlocalizedName("obsidian_nugget").setCreativeTab(AdInferosTab.AI_OTHER);
  	public static final Item GOLDEN_BUCKET = new ItemGoldenBucket(Blocks.AIR).setUnlocalizedName("golden_bucket").setCreativeTab(AdInferosTab.AI_TOOLS).setMaxStackSize(24);
  	public static final Item GOLDEN_BUCKET_LAVA = new ItemGoldenBucket(Blocks.FLOWING_LAVA).setUnlocalizedName("golden_bucket_lava").setCreativeTab(AdInferosTab.AI_TOOLS).setContainerItem(GOLDEN_BUCKET);
  	public static final Item GOLDEN_BUCKET_WATER = new ItemGoldenBucket(Blocks.FLOWING_WATER).setUnlocalizedName("golden_bucket_water").setCreativeTab(AdInferosTab.AI_TOOLS).setContainerItem(GOLDEN_BUCKET);
  	public static final Item GOLDEN_BUCKET_ACID = new ItemGoldenBucket(Blocks.FLOWING_WATER).setUnlocalizedName("golden_bucket_acid").setCreativeTab(AdInferosTab.AI_TOOLS).setContainerItem(GOLDEN_BUCKET);
  	public static final Item GOLDEN_BUCKET_BLOOD = new ItemBloodBucket().setUnlocalizedName("golden_bucket_blood").setCreativeTab(AdInferosTab.AI_TOOLS).setContainerItem(GOLDEN_BUCKET);
  	public static final Item COOKED_FLESH = new ItemFood(4, 0.4F, false).setUnlocalizedName("cooked_flesh").setCreativeTab(AdInferosTab.AI_FOOD);
  	public static final Item RECORD_FLAMES = new ItemCustomRecord("flames", AdInferosSounds.RECORDS_FLAMES, AdInferosReference.MOD_ID).setUnlocalizedName("record_flames").setCreativeTab(AdInferosTab.AI_OTHER);
  	public static final Item RECORD_AWAKENED = new ItemCustomRecord("awakened", AdInferosSounds.RECORDS_AWAKENED, AdInferosReference.MOD_ID).setUnlocalizedName("record_awakened").setCreativeTab(AdInferosTab.AI_OTHER);
  	public static final Item RECORD_DARKNESS = new ItemCustomRecord("darkness", AdInferosSounds.RECORDS_DARKNESS, AdInferosReference.MOD_ID).setUnlocalizedName("record_darkness").setCreativeTab(AdInferosTab.AI_OTHER);
  	public static final Item RECORD_UNKNOWN = new ItemCustomRecord("unknown", AdInferosSounds.RECORDS_UNKNOWN, AdInferosReference.MOD_ID).setUnlocalizedName("record_unknown").setCreativeTab(AdInferosTab.AI_OTHER);
  	public static final Item GOLDEN_SHEARS = new ItemShears().setUnlocalizedName("golden_shears").setMaxDamage(79).setCreativeTab(AdInferosTab.AI_TOOLS);
  	public static final Item COOKED_PURPLE_MUSHROOM = new ItemFood(5, 0.4F, false).setPotionEffect(new PotionEffect (MobEffects.FIRE_RESISTANCE, 1200, 1), 1.0F).setAlwaysEdible().setUnlocalizedName("cooked_purple_mushroom").setCreativeTab(AdInferosTab.AI_FOOD);
  	public static final Item DARKCORE = new Item().setUnlocalizedName("unknown").setCreativeTab(AdInferosTab.AI_OTHER);
  	public static final Item DIMSTONE_DUST = new Item().setUnlocalizedName("dimstone_dust").setCreativeTab(AdInferosTab.AI_OTHER);
  	public static final Item ECTOPLASM = new Item().setUnlocalizedName("ectoplasm").setCreativeTab(AdInferosTab.AI_OTHER);
  	public static final Item DIMENSIONAL_DUST = new Item().setUnlocalizedName("dimensional_dust").setCreativeTab(AdInferosTab.AI_OTHER);
  	public static final Item POCKET_WORMHOLE = new ItemWormHole().setUnlocalizedName("pocket_wormhole").setCreativeTab(AdInferosTab.AI_TOOLS);
  	public static final Item INFERNO_DOOR = new ItemDoor(NetherBlocks.INFERNO_DOOR).setUnlocalizedName("inferno_door").setCreativeTab(AdInferosTab.AI_BUILDING);
  	public static final Item MAGMA_DOOR = new ItemDoor(NetherBlocks.MAGMA_DOOR).setUnlocalizedName("magma_door").setCreativeTab(AdInferosTab.AI_BUILDING);
  	public static final Item PHANTOM_DOOR = new ItemDoor(NetherBlocks.PHANTOM_DOOR).setUnlocalizedName("phantom_door").setCreativeTab(AdInferosTab.AI_BUILDING);
  	public static final Item ASH_DOOR = new ItemDoor(NetherBlocks.ASH_DOOR).setUnlocalizedName("ash_door").setCreativeTab(AdInferosTab.AI_BUILDING);
  	public static final Item DARK_BRICK = new Item().setUnlocalizedName("dark_brick").setCreativeTab(AdInferosTab.AI_OTHER);
  	public static final Item AVIS_WING = new Item().setUnlocalizedName("avis_wing").setCreativeTab(AdInferosTab.AI_OTHER);
  	public static final Item WITHER_APPLE = new ItemWitherApple().setUnlocalizedName("wither_apple").setCreativeTab(AdInferosTab.AI_FOOD);
  	public static final ItemAmulet AMULET = new ItemAmulet();
  	public static Item SPAWN_EGG;
  	public static final ItemTribeHeadband TRIBE_HEADBAND = new ItemTribeHeadband();
  	public static final Item INFERNAL_FEATHER = new Item().setUnlocalizedName("infernal_feather").setCreativeTab(AdInferosTab.AI_OTHER);
  	public static final Item INFERNAL_STRING = new Item().setUnlocalizedName("infernal_string").setCreativeTab(AdInferosTab.AI_OTHER);
  	public static final Item INFERNAL_CHICKEN = new ItemFood(3, 0.2F, true).setAlwaysEdible().setPotionEffect(new PotionEffect(MobEffects.FIRE_RESISTANCE, 400, 0), 0.4F).setUnlocalizedName("infernal_chicken").setCreativeTab(AdInferosTab.AI_FOOD);
  	public static final Item COOKED_INFERNAL_CHICKEN = new ItemFood(7, 0.6F, true).setAlwaysEdible().setPotionEffect(new PotionEffect(MobEffects.FIRE_RESISTANCE, 3600, 0), 1.0F).setUnlocalizedName("cooked_infernal_chicken").setCreativeTab(AdInferosTab.AI_FOOD);
  	public static final Item SOUL_FRAGMENT = new Item().setUnlocalizedName("soul_fragment").setCreativeTab(AdInferosTab.AI_OTHER);
  	public static final Item SOUL = new ItemSoul().setUnlocalizedName("soul").setMaxStackSize(1).setCreativeTab(AdInferosTab.AI_OTHER);
  	public static final Item SPAWNER_UPGRADE = new ItemSpawnerUpgrade().setUnlocalizedName("spawner_upgrade").setCreativeTab(AdInferosTab.AI_TOOLS);
  	public static final Item PURPLE_MUSHROOM_SOUP = new ItemFood(8, 0.6F, false) {
  		
  		public ItemStack onItemUseFinish(ItemStack stack, World worldIn, EntityLivingBase entityLiving) {
  			super.onItemUseFinish(stack, worldIn, entityLiving);
  			
  			if(!worldIn.isRemote) {
  				entityLiving.addPotionEffect(new PotionEffect(MobEffects.FIRE_RESISTANCE, 4800, 1));
  				entityLiving.addPotionEffect(new PotionEffect(MobEffects.RESISTANCE, 1200, 0));
  			}
  			
  			return new ItemStack(Items.BOWL);
  		}
  	}.setAlwaysEdible().setUnlocalizedName("purple_mushroom_soup").setMaxStackSize(1).setCreativeTab(AdInferosTab.AI_FOOD);
  	public static final Item PEAT = new Item().setUnlocalizedName("peat").setCreativeTab(AdInferosTab.AI_OTHER);
  	public static final Item INFERNAL_SEEDS = new ItemHellSeeds(0).setUnlocalizedName("infernal_seeds");
  	public static final Item INFERNAL_WHEAT = new Item() {
  	    @SideOnly(Side.CLIENT)
  		public void addInformation(ItemStack stack, EntityPlayer playerIn, List<String> tooltip, boolean advanced) {
  	    	tooltip.add("Gluten Free!");
  		}
  	}.setUnlocalizedName("infernal_wheat").setCreativeTab(AdInferosTab.AI_FOOD);
  	public static final Item INFERNAL_BREAD = new ItemFood(6, 0.5F, false) {
  	    @SideOnly(Side.CLIENT)
  		public void addInformation(ItemStack stack, EntityPlayer playerIn, List<String> tooltip, boolean advanced) {
  	    	tooltip.add("Gluten Free!");
  		}
  	}.setPotionEffect(new PotionEffect(MobEffects.REGENERATION, 90), 0.4F).setUnlocalizedName("infernal_bread").setCreativeTab(AdInferosTab.AI_FOOD);
  	
  	public static void register() {
    
    ((ItemGoldenBucket) GOLDEN_BUCKET_ACID).setLquid(NetherFluids.blockAcid);
    String id = AdInferosReference.MOD_ID;
     
    //TAB BLOCKS
    RegistryHelper.registerItem(INFERNO_DOOR, id);
    RegistryHelper.registerItem(MAGMA_DOOR, id);
    RegistryHelper.registerItem(PHANTOM_DOOR, id);
    RegistryHelper.registerItem(ASH_DOOR, id);
    
    //TAB Combat
  	RegistryHelper.registerItem(QUARTZ_BOW, id);
  	RegistryHelper.registerItem(QUARTZ_ARROW, id);
  	RegistryHelper.registerItem(GLOWSTONE_BOW, id);
  	RegistryHelper.registerItem(GLOWSTONE_ARROW, id);
   	RegistryHelper.registerItem(INFERNO_STAFF, id);
   	RegistryHelper.registerItem(WITHER_STAFF, id);
   	RegistryHelper.registerItem(SCYTHE, id);
  	RegistryHelper.registerItem(OBSIDIAN_BOW, id);
  	RegistryHelper.registerItem(OBSIDIAN_ARROW, id);
  	RegistryHelper.registerItem(NETHERITE_BOW, id);
  	RegistryHelper.registerItem(NETHERITE_ARROW, id);
  	RegistryHelper.registerItem(WITHER_BOW, id);
  	RegistryHelper.registerItem(WITHER_ARROW, id);
   	RegistryHelper.registerItem(QUARTZ_SWORD, id);
   	RegistryHelper.registerItem(GLOWSTONE_SWORD, id);
   	RegistryHelper.registerItem(OBSIDIAN_SWORD, id);
   	RegistryHelper.registerItem(QUARTZ_HELMET, id);
   	RegistryHelper.registerItem(QUARTZ_CHESTPLATE, id);
   	RegistryHelper.registerItem(QUARTZ_LEGGINGS, id);
   	RegistryHelper.registerItem(QUARTZ_BOOTS, id);
   	RegistryHelper.registerItem(GLOWSTONE_HELMET, id);
   	RegistryHelper.registerItem(GLOWSTONE_CHESTPLATE, id);
   	RegistryHelper.registerItem(GLOWSTONE_LEGGINGS, id);
   	RegistryHelper.registerItem(GLOWSTONE_BOOTS, id);
   	RegistryHelper.registerItem(NETHERITE_SWORD, id);
   	RegistryHelper.registerItem(OBSIDIAN_HELMET, id);
   	RegistryHelper.registerItem(OBSIDIAN_CHESTPLATE, id);
   	RegistryHelper.registerItem(OBSIDIAN_LEGGINGS, id);
   	RegistryHelper.registerItem(OBSIDIAN_BOOTS, id);
   	RegistryHelper.registerItem(NETHERITE_HELMET, id);
   	RegistryHelper.registerItem(NETHERITE_CHESTPLATE, id);
   	RegistryHelper.registerItem(NETHERITE_LEGGINGS, id);
   	RegistryHelper.registerItem(NETHERITE_BOOTS, id);
   	RegistryHelper.registerItem(WITHER_SWORD, id);
   	RegistryHelper.registerItem(WITHER_HELMET, id);
   	RegistryHelper.registerItem(WITHER_CHESTPLATE, id);
   	RegistryHelper.registerItem(WITHER_LEGGINGS, id);
   	RegistryHelper.registerItem(WITHER_BOOTS, id);
   	RegistryHelper.registerItem(AMULET, id);
   	RegistryHelper.registerItem(TRIBE_HEADBAND, id);
    
    //TAB Tools
   	RegistryHelper.registerItem(QUARTZ_AXE, id);
   	RegistryHelper.registerItem(QUARTZ_PICKAXE, id);
   	RegistryHelper.registerItem(QUARTZ_SHOVEL, id);
   	RegistryHelper.registerItem(QUARTZ_HOE, id);
   	RegistryHelper.registerItem(GLOWSTONE_AXE, id);
   	RegistryHelper.registerItem(GLOWSTONE_PICKAXE, id);
   	RegistryHelper.registerItem(GLOWSTONE_SHOVEL, id);
   	RegistryHelper.registerItem(GLOWSTONE_HOE, id);
   	RegistryHelper.registerItem(POCKET_WORMHOLE, id);
   	RegistryHelper.registerItem(OBSIDIAN_AXE, id);
   	RegistryHelper.registerItem(OBSIDIAN_PICKAXE, id);
   	RegistryHelper.registerItem(OBSIDIAN_SHOVEL, id);
   	RegistryHelper.registerItem(OBSIDIAN_HOE, id);
   	RegistryHelper.registerItem(NETHERITE_AXE, id);
   	RegistryHelper.registerItem(NETHERITE_PICKAXE, id);
   	RegistryHelper.registerItem(NETHERITE_SHOVEL, id);
   	RegistryHelper.registerItem(NETHERITE_HOE, id);
    RegistryHelper.registerItem(GOLDEN_SHEARS, id);
   	RegistryHelper.registerItem(WITHER_AXE, id);
   	RegistryHelper.registerItem(WITHER_PICKAXE, id);
   	RegistryHelper.registerItem(WITHER_SHOVEL, id);
   	RegistryHelper.registerItem(WITHER_HOE, id);
   	RegistryHelper.registerItem(GOLDEN_BUCKET, id);
    RegistryHelper.registerItem(GOLDEN_BUCKET_WATER, id);
    RegistryHelper.registerItem(GOLDEN_BUCKET_LAVA, id);
    RegistryHelper.registerItem(GOLDEN_BUCKET_ACID, id);
    RegistryHelper.registerItem(GOLDEN_BUCKET_BLOOD, id);
   	RegistryHelper.registerItem(SPAWNER_UPGRADE, id);
    
    //TAB Food
    RegistryHelper.registerItem(COOKED_PURPLE_MUSHROOM, id);
   	RegistryHelper.registerItem(PURPLE_MUSHROOM_SOUP, id);
   	RegistryHelper.registerItem(INFERNAL_CHICKEN, id);
   	RegistryHelper.registerItem(COOKED_INFERNAL_CHICKEN, id);
   	RegistryHelper.registerItem(COOKED_FLESH, id);
   	RegistryHelper.registerItem(WITHER_APPLE, id);
   	RegistryHelper.registerItem(INFERNAL_SEEDS, id);
   	RegistryHelper.registerItem(INFERNAL_WHEAT, id);
   	RegistryHelper.registerItem(INFERNAL_BREAD, id);
    
    //TAB Mobs
   	//SPAWN_EGG registry is handled in NetherEntities.initSpawnEgg()
    
    //TAB Other/Unclassed
   	RegistryHelper.registerItem(RECORD_FLAMES, id);
   	RegistryHelper.registerItem(RECORD_AWAKENED, id);
   	RegistryHelper.registerItem(RECORD_DARKNESS, id);
   	RegistryHelper.registerItem(RECORD_UNKNOWN, id);
   	RegistryHelper.registerItem(QUARTZ_STICK, id);
   	RegistryHelper.registerItem(QUARTZ_CHUNK, id);
   	RegistryHelper.registerItem(GLOWSTONE_CRYSTAL, id);
 	RegistryHelper.registerItem(WITHER_DUST, id);
   	RegistryHelper.registerItem(INFERNAL_FEATHER, id);
   	RegistryHelper.registerItem(INFERNAL_STRING, id);
   	RegistryHelper.registerItem(OBSIDIAN_NUGGET, id);
   	RegistryHelper.registerItem(OBSIDIAN_INGOT, id);
   	RegistryHelper.registerItem(NETHERITE_NUGGET, id);
   	RegistryHelper.registerItem(NETHERITE_INGOT, id);
 	RegistryHelper.registerItem(WITHER_GEM, id);
   	RegistryHelper.registerItem(SOUL_FRAGMENT, id);
   	RegistryHelper.registerItem(SOUL, id);
   	RegistryHelper.registerItem(AVIS_WING, id);
   	RegistryHelper.registerItem(DARKCORE, id);
   	RegistryHelper.registerItem(DARK_BRICK, id);
   	RegistryHelper.registerItem(ECTOPLASM, id);
   	RegistryHelper.registerItem(DIMSTONE_DUST, id);
   	RegistryHelper.registerItem(DIMENSIONAL_DUST, id);
   	RegistryHelper.registerItem(PEAT, id);
   	
    OreDictionary.registerOre("stickQuartz", QUARTZ_STICK);
    OreDictionary.registerOre("chunkQuartz", QUARTZ_CHUNK);
    OreDictionary.registerOre("nuggetNetherite", NETHERITE_NUGGET);
    OreDictionary.registerOre("nuggetObsidian", OBSIDIAN_NUGGET);
    OreDictionary.registerOre("ingotNetherite", NETHERITE_INGOT);
    OreDictionary.registerOre("ingotObsidian", OBSIDIAN_INGOT);
    OreDictionary.registerOre("ingotDarkBrick", DARK_BRICK);
    OreDictionary.registerOre("gemGlowstone", GLOWSTONE_CRYSTAL);
    OreDictionary.registerOre("dustWither", WITHER_DUST);
    OreDictionary.registerOre("gemWither", WITHER_GEM);
    OreDictionary.registerOre("dustDimstone", DIMSTONE_DUST);
    OreDictionary.registerOre("dustDimensional", DIMENSIONAL_DUST);
    OreDictionary.registerOre("ectoplasm",   ECTOPLASM);
    OreDictionary.registerOre("infernal_string",   INFERNAL_STRING);
    OreDictionary.registerOre("string",   INFERNAL_STRING);
    OreDictionary.registerOre("infernal_feather",   INFERNAL_FEATHER);
    OreDictionary.registerOre("feather",   INFERNAL_FEATHER);
    OreDictionary.registerOre("darkcore",   DARKCORE);
    OreDictionary.registerOre("avis_wing",   AVIS_WING);
    OreDictionary.registerOre("record",   RECORD_AWAKENED);
    OreDictionary.registerOre("record",   RECORD_DARKNESS);
    OreDictionary.registerOre("record",   RECORD_FLAMES);
    OreDictionary.registerOre("record",   RECORD_UNKNOWN);
	NetherConfig.printDebugInfo("Registered Items");
   	 
    }
}
