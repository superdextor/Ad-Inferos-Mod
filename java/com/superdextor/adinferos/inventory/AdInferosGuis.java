package com.superdextor.adinferos.inventory;

import com.superdextor.adinferos.config.NetherConfig;
import com.superdextor.thinkbigcore.helpers.GuiHelper;

public class AdInferosGuis {
	
	public static final GuiEntryAltar altar = new GuiEntryAltar();
	public static final GuiEntryNetherrackFurnace netherrackFurnace = new GuiEntryNetherrackFurnace();
	public static final GuiEntryExtractor extractor = new GuiEntryExtractor();
	public static final GuiEntrySpawner spawner = new GuiEntrySpawner();
	
	public static void register() {
		GuiHelper.registerGui(altar);
		GuiHelper.registerGui(netherrackFurnace);
		GuiHelper.registerGui(extractor);
		GuiHelper.registerGui(spawner);
		NetherConfig.printDebugInfo("Registered GUIs");
	}

}
