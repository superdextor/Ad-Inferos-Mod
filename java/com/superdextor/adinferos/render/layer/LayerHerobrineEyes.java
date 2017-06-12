package com.superdextor.adinferos.render.layer;

import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.entity.RenderBiped;
import net.minecraft.client.renderer.entity.layers.LayerRenderer;
import net.minecraft.entity.monster.EntityMob;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

@SideOnly(Side.CLIENT)
public class LayerHerobrineEyes implements LayerRenderer<EntityMob>
{
    private static final ResourceLocation eyes = new ResourceLocation("adinferos", "textures/entity/herobrine_eyes.png");
    private final RenderBiped renderer;

    public LayerHerobrineEyes(RenderBiped rendererIn)
    {
        this.renderer = rendererIn;
    }

    public void doRenderLayer(EntityMob entitylivingbaseIn, float limbSwing, float limbSwingAmount, float partialTicks, float ageInTicks, float netHeadYaw, float headPitch, float scale)
    {
        this.renderer.bindTexture(eyes);
        GlStateManager.blendFunc(GlStateManager.SourceFactor.ONE, GlStateManager.DestFactor.ONE);

        if (entitylivingbaseIn.isInvisible())
        {
            GlStateManager.depthMask(false);
        }
        else
        {
            GlStateManager.depthMask(true);
        }
            
        this.renderer.getMainModel().render(entitylivingbaseIn, limbSwing, limbSwingAmount, ageInTicks, netHeadYaw, headPitch, scale);
    }

    public boolean shouldCombineTextures()
    {
        return false;
    }
}