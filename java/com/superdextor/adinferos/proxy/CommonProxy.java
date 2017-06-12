package com.superdextor.adinferos.proxy;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import net.minecraft.entity.player.EntityPlayer;

public class CommonProxy {
	
	protected final static Logger logger = LogManager.getLogger("Ad Inferos [proxy]");
	protected boolean isNetherSurvival = false;
	protected boolean isDarkNether = true;
	
	public void preInit() {}
	public void init() {}
	public void postInit() {}
	
	public boolean isFightingAvis() {return false;}
	public boolean isFightingHerobrine() {return false;}
	
	public void doUpdate() {}
	
	public void updatePlayerModel() {}
	
	public boolean isNetherSurvival() {
		return this.isNetherSurvival;
	}
	
	public boolean isDarkNether() {
		return this.isDarkNether;
	}
	
	public void setNetherSurvival(boolean value) {
		logger.info("Nether Survival = " + value);
		this.isNetherSurvival = value;
	}
	
	public void setDarkNether(boolean value) {
		logger.info("Dark Nether = " + value);
		this.isDarkNether = value;
	}
	
}
