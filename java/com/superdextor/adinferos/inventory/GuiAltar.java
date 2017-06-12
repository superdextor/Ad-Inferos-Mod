package com.superdextor.adinferos.inventory;

import java.util.List;

import com.google.common.collect.Lists;
import com.superdextor.adinferos.blocks.BlockAltar;

import net.minecraft.client.gui.FontRenderer;
import net.minecraft.client.gui.inventory.GuiContainer;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.inventory.IInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.text.TextFormatting;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

@SideOnly(Side.CLIENT)
public class GuiAltar extends GuiContainer
{
    private static final ResourceLocation infernumAltarGuiTextures = new ResourceLocation("adinferos", "textures/gui/infernum_altar.png");
    /** The player inventory bound to this GUI. */
    private final InventoryPlayer playerInventory;
    private TileEntityAltar tileAltar;

    public GuiAltar(InventoryPlayer playerInv, TileEntityAltar tileAltar)
    {
        super(new ContainerAltar(playerInv, tileAltar));
        this.playerInventory = playerInv;
        this.tileAltar = tileAltar;
    }

    /**
     * Draw the foreground layer for the GuiContainer (everything in front of the items). Args : mouseX, mouseY
     */
    protected void drawGuiContainerForegroundLayer(int mouseX, int mouseY)
    {
        String s = this.tileAltar.getDisplayName().getUnformattedText();
        this.fontRendererObj.drawString(s, this.xSize / 2 - this.fontRendererObj.getStringWidth(s) / 2, 6, 4210752);
        this.fontRendererObj.drawString(this.playerInventory.getDisplayName().getUnformattedText(), 8, this.ySize - 96 + 2, 4210752);
        
        if(this.isPointInRegion(83, 19, 10, 10, mouseX, mouseY)) {
            List<String> list = Lists.<String>newArrayList();
            if(this.tileAltar.getWorld().getBlockState(this.tileAltar.getPos()).getValue(BlockAltar.OBSIDIAN).booleanValue()) {
                list.add(this.tileAltar.getField(0) + "/1600");
            }
            else {
                list.add("Blood:" + this.tileAltar.getField(0));
            }
            this.drawHoveringText(list, 83, 19, fontRendererObj);
        }
        
    }

    /**
     * Args : renderPartialTicks, mouseX, mouseY
     */
    protected void drawGuiContainerBackgroundLayer(float partialTicks, int mouseX, int mouseY)
    {
        GlStateManager.color(1.0F, 1.0F, 1.0F, 1.0F);
        this.mc.getTextureManager().bindTexture(infernumAltarGuiTextures);
        int k = (this.width - this.xSize) / 2;
        int l = (this.height - this.ySize) / 2;
        this.drawTexturedModalRect(k, l, 0, 0, this.xSize, this.ySize);
        int i1;

        if (tileAltar.getField(0) / tileAltar.getField(1) > 0)
        {
            i1 = tileAltar.getField(0) / tileAltar.getField(1);
            this.drawTexturedModalRect(k + 84, l + 15 + 12 - i1, 176, 7 - i1, 8, i1 + 1);
        }
            i1 = tileAltar.getCookProgressScaled(16);
            this.drawTexturedModalRect(k + 71, l + 79 - i1, 176, 23 - i1, 32, i1 + 1);
    }
}