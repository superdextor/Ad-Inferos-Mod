package com.superdextor.adinferos.render.layer;

import com.superdextor.adinferos.entity.monster.EntityObsidianSheepman;
import com.superdextor.adinferos.render.RenderObsidianSheepman;

import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.entity.layers.LayerRenderer;
import net.minecraft.entity.passive.EntitySheep;
import net.minecraft.item.EnumDyeColor;
import net.minecraft.util.ResourceLocation;

public class LayerSheepmanHeadband implements LayerRenderer<EntityObsidianSheepman> {

    private static final ResourceLocation headbandTexture = new ResourceLocation("adinferos", "textures/entity/obsidian_sheepman/headband.png");
    private static final ResourceLocation leaderTextures = new ResourceLocation("adinferos", "textures/entity/obsidian_sheepman/leader_mark.png");
    private final RenderObsidianSheepman sheepmanRenderer;

    public LayerSheepmanHeadband(RenderObsidianSheepman renderer)
    {
        this.sheepmanRenderer = renderer;
    }

    public void doRenderLayer(EntityObsidianSheepman entitylivingbaseIn, float limbSwing, float limbSwingAmount, float partialTicks, float ageInTicks, float netHeadYaw, float headPitch, float scale)
    {
    	if((entitylivingbaseIn.isMember() || entitylivingbaseIn.isLeader()) && !entitylivingbaseIn.isInvisible()) {
    		
    		
    		if(entitylivingbaseIn.isLeader()) {
    			this.sheepmanRenderer.bindTexture(leaderTextures);
                this.sheepmanRenderer.getMainModel().render(entitylivingbaseIn, limbSwing, limbSwingAmount, ageInTicks, netHeadYaw, headPitch, scale);
    		}
    		
            this.sheepmanRenderer.bindTexture(headbandTexture);
            EnumDyeColor enumdyecolor = entitylivingbaseIn.getTribeColor();
            float[] afloat = EntitySheep.getDyeRgb(enumdyecolor);
            GlStateManager.color(afloat[0], afloat[1], afloat[2]);
            this.sheepmanRenderer.getMainModel().render(entitylivingbaseIn, limbSwing, limbSwingAmount, ageInTicks, netHeadYaw, headPitch, scale);
    	}
    }

    public boolean shouldCombineTextures()
    {
        return false;
    }

}
