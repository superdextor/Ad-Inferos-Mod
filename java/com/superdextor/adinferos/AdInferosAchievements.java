package com.superdextor.adinferos;

import com.superdextor.adinferos.config.NetherConfig;
import com.superdextor.adinferos.init.NetherBlocks;
import com.superdextor.adinferos.init.NetherItems;
import com.superdextor.thinkbigcore.helpers.RegistryHelper;

import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;
import net.minecraft.stats.Achievement;
import net.minecraft.util.text.TextFormatting;
import net.minecraftforge.common.AchievementPage;

public class AdInferosAchievements {

    public static Achievement welcome;
    public static Achievement quartz;
    public static Achievement demonSlayer;
    public static Achievement scythe;
    public static Achievement obsidiancow;
    public static Achievement hellProspector;
    public static Achievement wither;
    public static Achievement witherApple;
    public static Achievement netherite;
    public static Achievement wakeAvis;
    public static Achievement hellAltar;
    public static Achievement summoner;
    public static Achievement witherStaff;
    public static Achievement flameStaff;
    public static Achievement abyss;
    public static Achievement pocketPortal;
    
    public static Achievement lifeInHell;
    
	public static void register() {
		
		welcome = RegistryHelper.addAchievement("welcome", 0, 0, new ItemStack(Blocks.NETHERRACK));
		quartz = RegistryHelper.addAchievement("quartz", 2, 1, new ItemStack(Items.QUARTZ), welcome);
		demonSlayer = RegistryHelper.addAchievement("demonSlayer", 4, 1, new ItemStack(NetherItems.QUARTZ_SWORD), quartz);
		scythe = RegistryHelper.addAchievement("scythe", 4, -1, new ItemStack(NetherItems.SCYTHE), demonSlayer, true);
		obsidiancow = RegistryHelper.addAchievement("obsidiancow", 5, 0, new ItemStack(NetherItems.OBSIDIAN_NUGGET), demonSlayer);
		hellProspector = RegistryHelper.addAchievement("hellProspector", 2, 3, new ItemStack(NetherItems.QUARTZ_PICKAXE), quartz);
		wither = RegistryHelper.addAchievement("wither", 0, 4, new ItemStack(NetherItems.WITHER_DUST), hellProspector);
		witherApple = RegistryHelper.addAchievement("witherApple", -1, 2, new ItemStack(NetherItems.WITHER_APPLE), wither);
		netherite = RegistryHelper.addAchievement("netherite", 1, 6, new ItemStack(NetherItems.NETHERITE_INGOT), wither);
		wakeAvis = RegistryHelper.addAchievement("wakeAvis", 0, 8, new ItemStack(NetherItems.AVIS_WING), netherite);
		summoner = RegistryHelper.addAchievement("summoner", 3, 7, new ItemStack(NetherBlocks.DARK_FIRE), netherite, true);
		hellAltar = RegistryHelper.addAchievement("hellAltar", 5, 8, new ItemStack(NetherBlocks.ALTAR), summoner);
		witherStaff = RegistryHelper.addAchievement("witherstaff", 4, 6, new ItemStack(NetherItems.WITHER_STAFF), hellAltar, true);
		flameStaff = RegistryHelper.addAchievement("flamestaff", 6, 6, new ItemStack(NetherItems.INFERNO_STAFF), hellAltar, true);
		abyss = RegistryHelper.addAchievement("abyss", 4, 10, new ItemStack(NetherBlocks.DARK_BRICKS), hellAltar, true);
		pocketPortal = RegistryHelper.addAchievement("pocketPortal", 2, 10, new ItemStack(NetherItems.POCKET_WORMHOLE), abyss, true);
		lifeInHell = RegistryHelper.addAchievement("lifeInHell", 0, -2, new ItemStack(NetherItems.COOKED_FLESH), null, true);
        
        AchievementPage.registerAchievementPage(new AchievementPage(TextFormatting.DARK_RED + "Ad Inferos", new Achievement[]{welcome, quartz, demonSlayer, obsidiancow, hellProspector, wither, witherApple, witherStaff, scythe, flameStaff, summoner, netherite, wakeAvis, hellAltar, abyss, pocketPortal, lifeInHell}));
		NetherConfig.printDebugInfo("Registered Achievements");
	}
}
