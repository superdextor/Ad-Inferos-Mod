package com.superdextor.adinferos;

import com.superdextor.adinferos.config.NetherConfig;
import com.superdextor.thinkbigcore.helpers.RegistryHelper;

import net.minecraft.util.ResourceLocation;
import net.minecraft.util.SoundEvent;

public class AdInferosSounds {
	
	
	public static final SoundEvent AMBIENT_SPOOKY;
	
	public static final SoundEvent MUSIC_VICTORY;
	public static final SoundEvent MUSIC_DEATH;
	public static final SoundEvent MUSIC_INFERNUM_AVIS;
	public static final SoundEvent MUSIC_HEROBRINE;
	public static final SoundEvent MUSIC_ABYSS;
	
	public static final SoundEvent RECORDS_FLAMES;
	public static final SoundEvent RECORDS_AWAKENED;
	public static final SoundEvent RECORDS_DARKNESS;
	public static final SoundEvent RECORDS_UNKNOWN;
	
	public static final SoundEvent ENTITY_SUMMON;
	
	public static final SoundEvent ENTITY_PHANTOM_IDLE;
	public static final SoundEvent ENTITY_PHANTOM_HURT;
	public static final SoundEvent ENTITY_PHANTOM_DEATH;
	
	public static final SoundEvent ENTITY_INFERNUMAVIS_AWAKED;
	public static final SoundEvent ENTITY_INFERNUMAVIS_HURT;
	public static final SoundEvent ENTITY_INFERNUMAVIS_SLEEPING;
	public static final SoundEvent ENTITY_INFERNUMAVIS_ANGRY;
	public static final SoundEvent ENTITY_INFERNUMAVIS_DEATH;
	
	public static final SoundEvent ENTITY_CURSE_IDLE;
	public static final SoundEvent ENTITY_CURSE_HURT;
	public static final SoundEvent ENTITY_CURSE_DEATH;
	
	public static final SoundEvent ENTITY_REAPER_IDLE;
	public static final SoundEvent ENTITY_REAPER_ANGRY;
	public static final SoundEvent ENTITY_REAPER_HURT;
	public static final SoundEvent ENTITY_REAPER_DEATH;
	public static final SoundEvent ENTITY_REAPER_CLOAK;
	public static final SoundEvent ENTITY_REAPER_DODGE;
	
	public static final SoundEvent ENTITY_BLACKWIDOW_IDLE;
	public static final SoundEvent ENTITY_BLACKWIDOW_HURT;
	public static final SoundEvent ENTITY_BLACKWIDOW_DEATH;
	
	public static final SoundEvent ENTITY_GLOWSTONESKELETON_IDLE;
	public static final SoundEvent ENTITY_GLOWSTONESKELETON_HURT;
	public static final SoundEvent ENTITY_GLOWSTONESKELETON_DEATH;
	
	public static final SoundEvent ENTITY_INFERNALCHICKEN_SAY;
	public static final SoundEvent ENTITY_INFERNALCHICKEN_HURT;
	public static final SoundEvent ENTITY_INFERNALCHICKEN_DEATH;
	
	public static final SoundEvent ENTITY_GHOST_SPAWN;
	public static final SoundEvent ENTITY_GHOST_IDLE;
	public static final SoundEvent ENTITY_GHOST_DIVE;
	public static final SoundEvent ENTITY_GHOST_DEATH;
	
	public static final SoundEvent ENTITY_OBSIDIANSHEEPMAN_IDLE;
	public static final SoundEvent ENTITY_OBSIDIANSHEEPMAN_HURT;
	public static final SoundEvent ENTITY_OBSIDIANSHEEPMAN_ANGRY;
	public static final SoundEvent ENTITY_OBSIDIANSHEEPMAN_DEATH;
	
	public static final SoundEvent ABYSSPORTAL_AMBIENT;
	public static final SoundEvent ABYSSPORTAL_TRIGGER;
	public static final SoundEvent ABYSSPORTAL_TRAVEL;
	
	public static void register() {};
	
	static {
		AMBIENT_SPOOKY = RegistryHelper.registerSound(RegistryHelper.adinferosSoundIds++, new ResourceLocation("adinferos", "ambient.spooky"));
		MUSIC_VICTORY = RegistryHelper.registerSound(RegistryHelper.adinferosSoundIds++, new ResourceLocation("adinferos", "music.victory"));
		MUSIC_DEATH = RegistryHelper.registerSound(RegistryHelper.adinferosSoundIds++, new ResourceLocation("adinferos", "music.death"));
		MUSIC_INFERNUM_AVIS = RegistryHelper.registerSound(RegistryHelper.adinferosSoundIds++, new ResourceLocation("adinferos", "music.infernum_avis"));
		MUSIC_HEROBRINE = RegistryHelper.registerSound(RegistryHelper.adinferosSoundIds++, new ResourceLocation("adinferos", "music.herobrine"));
		MUSIC_ABYSS = RegistryHelper.registerSound(RegistryHelper.adinferosSoundIds++, new ResourceLocation("adinferos", "music.abyss"));
		
		RECORDS_FLAMES = RegistryHelper.registerSound(RegistryHelper.adinferosSoundIds++, new ResourceLocation("adinferos", "records.flames"));
		RECORDS_AWAKENED = RegistryHelper.registerSound(RegistryHelper.adinferosSoundIds++, new ResourceLocation("adinferos", "records.awakened"));
		RECORDS_DARKNESS = RegistryHelper.registerSound(RegistryHelper.adinferosSoundIds++, new ResourceLocation("adinferos", "records.darkness"));
		RECORDS_UNKNOWN = RegistryHelper.registerSound(RegistryHelper.adinferosSoundIds++, new ResourceLocation("adinferos", "records.unknown"));
		
		ENTITY_SUMMON = RegistryHelper.registerSound(RegistryHelper.adinferosSoundIds++, new ResourceLocation("adinferos", "entity.summon"));
		
		ENTITY_PHANTOM_IDLE = RegistryHelper.registerSound(RegistryHelper.adinferosSoundIds++, new ResourceLocation("adinferos", "entity.phantom.idle"));
		ENTITY_PHANTOM_HURT = RegistryHelper.registerSound(RegistryHelper.adinferosSoundIds++, new ResourceLocation("adinferos", "entity.phantom.hurt"));
		ENTITY_PHANTOM_DEATH = RegistryHelper.registerSound(RegistryHelper.adinferosSoundIds++, new ResourceLocation("adinferos", "entity.phantom.death"));
		
		ENTITY_INFERNUMAVIS_AWAKED = RegistryHelper.registerSound(RegistryHelper.adinferosSoundIds++, new ResourceLocation("adinferos", "entity.infernumavis.awaked"));
		ENTITY_INFERNUMAVIS_HURT = RegistryHelper.registerSound(RegistryHelper.adinferosSoundIds++, new ResourceLocation("adinferos", "entity.infernumavis.hurt"));
		ENTITY_INFERNUMAVIS_SLEEPING = RegistryHelper.registerSound(RegistryHelper.adinferosSoundIds++, new ResourceLocation("adinferos", "entity.infernumavis.sleeping"));
		ENTITY_INFERNUMAVIS_ANGRY = RegistryHelper.registerSound(RegistryHelper.adinferosSoundIds++, new ResourceLocation("adinferos", "entity.infernumavis.angry"));
		ENTITY_INFERNUMAVIS_DEATH = RegistryHelper.registerSound(RegistryHelper.adinferosSoundIds++, new ResourceLocation("adinferos", "entity.infernumavis.death"));
		
		ENTITY_CURSE_IDLE = RegistryHelper.registerSound(RegistryHelper.adinferosSoundIds++, new ResourceLocation("adinferos", "entity.curse.idle"));
		ENTITY_CURSE_HURT = RegistryHelper.registerSound(RegistryHelper.adinferosSoundIds++, new ResourceLocation("adinferos", "entity.curse.hurt"));
		ENTITY_CURSE_DEATH = RegistryHelper.registerSound(RegistryHelper.adinferosSoundIds++, new ResourceLocation("adinferos", "entity.curse.death"));
		
		ENTITY_REAPER_IDLE = RegistryHelper.registerSound(RegistryHelper.adinferosSoundIds++, new ResourceLocation("adinferos", "entity.reaper.idle"));
		ENTITY_REAPER_ANGRY = RegistryHelper.registerSound(RegistryHelper.adinferosSoundIds++, new ResourceLocation("adinferos", "entity.reaper.angry"));
		ENTITY_REAPER_HURT = RegistryHelper.registerSound(RegistryHelper.adinferosSoundIds++, new ResourceLocation("adinferos", "entity.reaper.hurt"));
		ENTITY_REAPER_DEATH = RegistryHelper.registerSound(RegistryHelper.adinferosSoundIds++, new ResourceLocation("adinferos", "entity.reaper.death"));
		ENTITY_REAPER_CLOAK = RegistryHelper.registerSound(RegistryHelper.adinferosSoundIds++, new ResourceLocation("adinferos", "entity.reaper.cloak"));
		ENTITY_REAPER_DODGE = RegistryHelper.registerSound(RegistryHelper.adinferosSoundIds++, new ResourceLocation("adinferos", "entity.reaper.dodge"));
		
		ENTITY_BLACKWIDOW_IDLE = RegistryHelper.registerSound(RegistryHelper.adinferosSoundIds++, new ResourceLocation("adinferos", "entity.blackwidow.idle"));
		ENTITY_BLACKWIDOW_HURT = RegistryHelper.registerSound(RegistryHelper.adinferosSoundIds++, new ResourceLocation("adinferos", "entity.blackwidow.hurt"));
		ENTITY_BLACKWIDOW_DEATH = RegistryHelper.registerSound(RegistryHelper.adinferosSoundIds++, new ResourceLocation("adinferos", "entity.blackwidow.death"));
		
		ENTITY_GLOWSTONESKELETON_IDLE = RegistryHelper.registerSound(RegistryHelper.adinferosSoundIds++, new ResourceLocation("adinferos", "entity.glowstoneskeleton.idle"));
		ENTITY_GLOWSTONESKELETON_HURT = RegistryHelper.registerSound(RegistryHelper.adinferosSoundIds++, new ResourceLocation("adinferos", "entity.glowstoneskeleton.hurt"));
		ENTITY_GLOWSTONESKELETON_DEATH = RegistryHelper.registerSound(RegistryHelper.adinferosSoundIds++, new ResourceLocation("adinferos", "entity.glowstoneskeleton.death"));
		
		ENTITY_INFERNALCHICKEN_SAY = RegistryHelper.registerSound(RegistryHelper.adinferosSoundIds++, new ResourceLocation("adinferos", "entity.infernalchicken.say"));
		ENTITY_INFERNALCHICKEN_HURT = RegistryHelper.registerSound(RegistryHelper.adinferosSoundIds++, new ResourceLocation("adinferos", "entity.infernalchicken.hurt"));
		ENTITY_INFERNALCHICKEN_DEATH = RegistryHelper.registerSound(RegistryHelper.adinferosSoundIds++, new ResourceLocation("adinferos", "entity.infernalchicken.death"));
		
		ENTITY_GHOST_SPAWN = RegistryHelper.registerSound(RegistryHelper.adinferosSoundIds++, new ResourceLocation("adinferos", "entity.ghost.spawn"));
		ENTITY_GHOST_IDLE = RegistryHelper.registerSound(RegistryHelper.adinferosSoundIds++, new ResourceLocation("adinferos", "entity.ghost.idle"));
		ENTITY_GHOST_DIVE = RegistryHelper.registerSound(RegistryHelper.adinferosSoundIds++, new ResourceLocation("adinferos", "entity.ghost.dive"));
		ENTITY_GHOST_DEATH = RegistryHelper.registerSound(RegistryHelper.adinferosSoundIds++, new ResourceLocation("adinferos", "entity.ghost.death"));
		
		ENTITY_OBSIDIANSHEEPMAN_IDLE = RegistryHelper.registerSound(RegistryHelper.adinferosSoundIds++, new ResourceLocation("adinferos", "entity.obsidiansheepman.idle"));
		ENTITY_OBSIDIANSHEEPMAN_HURT = RegistryHelper.registerSound(RegistryHelper.adinferosSoundIds++, new ResourceLocation("adinferos", "entity.obsidiansheepman.hurt"));
		ENTITY_OBSIDIANSHEEPMAN_ANGRY = RegistryHelper.registerSound(RegistryHelper.adinferosSoundIds++, new ResourceLocation("adinferos", "entity.obsidiansheepman.angry"));
		ENTITY_OBSIDIANSHEEPMAN_DEATH = RegistryHelper.registerSound(RegistryHelper.adinferosSoundIds++, new ResourceLocation("adinferos", "entity.obsidiansheepman.death"));
		
		ABYSSPORTAL_AMBIENT = RegistryHelper.registerSound(RegistryHelper.adinferosSoundIds++, new ResourceLocation("adinferos", "abyssportal.ambient"));
		ABYSSPORTAL_TRIGGER = RegistryHelper.registerSound(RegistryHelper.adinferosSoundIds++, new ResourceLocation("adinferos", "abyssportal.trigger"));
		ABYSSPORTAL_TRAVEL = RegistryHelper.registerSound(RegistryHelper.adinferosSoundIds++, new ResourceLocation("adinferos", "abyssportal.travel"));
		NetherConfig.printDebugInfo("Registered Sounds");
	}
}
