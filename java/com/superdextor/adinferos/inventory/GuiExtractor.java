package com.superdextor.adinferos.inventory;

import net.minecraft.client.gui.inventory.GuiContainer;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

@SideOnly(Side.CLIENT)
public class GuiExtractor extends GuiContainer
{
    private static final ResourceLocation guiTextures = new ResourceLocation("adinferos", "textures/gui/extractor.png");
    /** The player inventory bound to this GUI. */
    private final InventoryPlayer playerInventory;
    private TileEntityExtractor extractorInventory;

    public GuiExtractor(InventoryPlayer playerInv, TileEntityExtractor furnaceInv)
    {
        super(new ContainerExtractor(playerInv, furnaceInv));
        this.playerInventory = playerInv;
        this.extractorInventory = furnaceInv;
    }

    /**
     * Draw the foreground layer for the GuiContainer (everything in front of the items). Args : mouseX, mouseY
     */
    protected void drawGuiContainerForegroundLayer(int mouseX, int mouseY)
    {
        String s = this.extractorInventory.getDisplayName().getUnformattedText();
        this.fontRendererObj.drawString(s, this.xSize / 2 - this.fontRendererObj.getStringWidth(s) / 2, 6, 4210752);
        this.fontRendererObj.drawString(this.playerInventory.getDisplayName().getUnformattedText(), 8, this.ySize - 96 + 2, 4210752);
    }

    /**
     * Args : renderPartialTicks, mouseX, mouseY
     */
    protected void drawGuiContainerBackgroundLayer(float partialTicks, int mouseX, int mouseY)
    {
        GlStateManager.color(1.0F, 1.0F, 1.0F, 1.0F);
        this.mc.getTextureManager().bindTexture(guiTextures);
        int i = (this.width - this.xSize) / 2;
        int j = (this.height - this.ySize) / 2;
        this.drawTexturedModalRect(i, j, 0, 0, this.xSize, this.ySize);
        
        int value = this.extractorInventory.getField(0) / 75;
        
        if(value > 4350) {
        	value = 4350;
        }
        
        if (value > 0)
        {
            this.drawTexturedModalRect(i + 38, j + 12 + 58 - value, 176, 58 - value, 12, value + 1);
        }
        
        float count = this.extractorInventory.getField(2);
        float required = this.extractorInventory.getField(3);
        float size = 28;
        
        if(count > 0) {
            float f = (count / required) * size;
            value = (int) f;
        }
        else {
        	value = 0;
        }
        
        if (value > 0)
        {
            this.drawTexturedModalRect(i + 127, j + 35 + 28 - value, 188, 28 - value, 12, value + 1);
        }
        
        int m = this.extractorInventory.getField(1) / 10;
        if(m > 0) {
        	value = 13 - m;
        }
        else {
        	value = 0;
        }
        
        if (value > 0)
        {
            this.drawTexturedModalRect(i + 121, j + 64 + 13 - value, 200, 13 - value, 24, value + 1);
        }
    }
}