package com.superdextor.adinferos.inventory;

import java.util.ArrayList;

import com.superdextor.adinferos.blocks.BlockSpawner;
import com.superdextor.adinferos.config.NetherConfig;
import com.superdextor.adinferos.entity.monster.EntityBlackWidow;
import com.superdextor.adinferos.entity.monster.EntityCurse;
import com.superdextor.adinferos.entity.monster.EntityGlowstoneSkeleton;
import com.superdextor.adinferos.entity.monster.EntityObsidianSheepman;
import com.superdextor.adinferos.entity.monster.EntityPhantom;
import com.superdextor.adinferos.entity.monster.EntitySkeletonHorse;

import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityList;
import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.monster.EntityBlaze;
import net.minecraft.entity.monster.EntityCaveSpider;
import net.minecraft.entity.monster.EntityCreeper;
import net.minecraft.entity.monster.EntityEnderman;
import net.minecraft.entity.monster.EntityEndermite;
import net.minecraft.entity.monster.EntityGhast;
import net.minecraft.entity.monster.EntityGuardian;
import net.minecraft.entity.monster.EntityHusk;
import net.minecraft.entity.monster.EntityMagmaCube;
import net.minecraft.entity.monster.EntityPigZombie;
import net.minecraft.entity.monster.EntitySilverfish;
import net.minecraft.entity.monster.EntitySkeleton;
import net.minecraft.entity.monster.EntitySlime;
import net.minecraft.entity.monster.EntitySpider;
import net.minecraft.entity.monster.EntityStray;
import net.minecraft.entity.monster.EntityWitch;
import net.minecraft.entity.monster.EntityWitherSkeleton;
import net.minecraft.entity.monster.EntityZombie;
import net.minecraft.entity.passive.EntityBat;
import net.minecraft.entity.passive.EntityChicken;
import net.minecraft.entity.passive.EntityCow;
import net.minecraft.entity.passive.EntityPig;
import net.minecraft.entity.passive.EntityRabbit;
import net.minecraft.entity.passive.EntitySheep;
import net.minecraft.entity.passive.EntitySquid;
import net.minecraft.util.ResourceLocation;

public class ExtractorRecipes
{
    public static final ArrayList<ExtractorRecipes.ExtractorRecipe> recipes = new ArrayList<ExtractorRecipes.ExtractorRecipe>();
		
    public static void register() {
    	
    	addRecipe(EntityCreeper.class, 14, 800, 18);
    	addRecipe(EntitySkeleton.class, 14, 800, 16);
    	addRecipe(EntityWitherSkeleton.class, 14, 800, 18);
    	addRecipe(EntityStray.class, 14, 800, 16);
    	addRecipe(EntitySpider.class, 14, 800, 14);
    	addRecipe(EntityZombie.class, 14, 800, 10);
    	addRecipe(EntityHusk.class, 14, 800, 10);
    	addRecipe(EntitySlime.class, 16, 900, 30);
    	addRecipe(EntityGhast.class, 20, 1400, 90);
    	addRecipe(EntityPigZombie.class, 18, 1200, 26);
    	addRecipe(EntityEnderman.class, 24, 1800, 80);
    	addRecipe(EntityCaveSpider.class, 12, 600, 12);
    	addRecipe(EntitySilverfish.class, 6, 400, 5);
    	addRecipe(EntityBlaze.class, 20, 1800, 80);
    	addRecipe(EntityMagmaCube.class, 24, 1800, 100);
    	addRecipe(EntityBat.class, 6, 400, 5);
    	addRecipe(EntityWitch.class, 20, 1200, 38);
    	addRecipe(EntityEndermite.class, 6, 400, 5);
    	addRecipe(EntityGuardian.class, 20, 1800, 100);
    	addRecipe(EntityPig.class, 16, 1800, 60);
    	addRecipe(EntitySheep.class, 16, 1800, 60);
    	addRecipe(EntityCow.class, 16, 1800, 60);
    	addRecipe(EntityChicken.class, 16, 1800, 60);
    	addRecipe(EntitySquid.class, 12, 900, 40);
    	addRecipe(EntityRabbit.class, 16, 1800, 60);
    	
    	addRecipe(EntityBlackWidow.class, 20, 1200, 26);
    	addRecipe(EntityCurse.class, 24, 1500, 46);
    	addRecipe(EntityGlowstoneSkeleton.class, 18, 1400, 46);
    	addRecipe(EntityObsidianSheepman.class, 26, 1800, 64);
    	addRecipe(EntityPhantom.class, 28, 2400, 140);
    	addRecipe(EntitySkeletonHorse.class, 12, 1200, 58);
		NetherConfig.printDebugInfo("Registered Extractor Recipes");
	}

	public static void addRecipe(Class <? extends Entity > clazz, int soulsRequired, int bloodRequired, int blood4Spawn)
    {
		if(NetherConfig.allowExtractor) {
		    ExtractorRecipe recipe = new ExtractorRecipe(EntityList.func_191306_a(clazz).toString(), soulsRequired, bloodRequired, blood4Spawn);
			recipes.add(recipe);
		}
    }
		
	public static ExtractorRecipe getRecipe(String soulId) {
		
		for(ExtractorRecipe recipe : recipes) {
			if(recipe.soulId.equalsIgnoreCase(soulId)) {
				return recipe;
			}
		}
		
		return null;
	}
	
	public static boolean isValidEntity(Entity entityIn) {
		
		if(entityIn instanceof EntityLiving && entityIn.isNonBoss() && !BlockSpawner.isSpawnerEntity(entityIn)) {
			String s = EntityList.func_191301_a(entityIn).toString();
			
			if(getRecipe(s) != null) {
				return true;
			}
		}
		
		return false;
	}
        
    public static class ExtractorRecipe {
        	
    	public final String soulId;
    	public final int soulsRequired;
    	public final int bloodRequired;
    	public final int bloodForSpawn;
        	
        public ExtractorRecipe(String soulIn, int souls, int blood, int blood4Spawn) {
        	this.soulId = soulIn;
        	this.soulsRequired = souls;
        	this.bloodRequired = blood;
        	this.bloodForSpawn = blood4Spawn;
		}
        	
        public String toString() {
        	return "[" + soulId + "," + soulsRequired + "," + bloodRequired + ", " + bloodForSpawn + "]";
        }
    }
}