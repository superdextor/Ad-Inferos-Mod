package com.superdextor.adinferos.config;

import com.superdextor.adinferos.AdInferosReference;

import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.gui.GuiScreen;
import net.minecraftforge.common.config.ConfigElement;
import net.minecraftforge.common.config.Configuration;
import net.minecraftforge.fml.client.config.GuiConfig;

public class GuiNetherConfig extends GuiConfig 
{
    public GuiNetherConfig(GuiScreen parent)
    {
        super(parent,
                new ConfigElement(

                      AdInferosReference.config.getCategory(Configuration.CATEGORY_GENERAL))

                            .getChildElements(),
                AdInferosReference.MOD_ID, 
                false, 
                false, 
                "Ad Inferos 1.11 Configuration");
        titleLine2 = AdInferosReference.config.getConfigFile().getAbsolutePath();
    }
    
    @Override
    public void initGui()
    {
        super.initGui();
    }

    
    @Override
    public void drawScreen(int mouseX, int mouseY, float partialTicks)
    {
        super.drawScreen(mouseX, mouseY, partialTicks);
    }
    
    public void onGuiClosed() {
    	super.onGuiClosed();
    	AdInferosReference.config.save();
    }

    protected void actionPerformed(GuiButton button)
    {
        super.actionPerformed(button);
        
        try {
        	NetherConfig.updateValues();
        }
        catch (Exception e) {
        	
		}
    }
}

