package com.superdextor.adinferos.render.layer;

import com.superdextor.adinferos.entity.monster.EntitySkeletonHorse;
import com.superdextor.adinferos.render.RenderSkeletonHorse;

import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.OpenGlHelper;
import net.minecraft.client.renderer.entity.layers.LayerRenderer;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

@SideOnly(Side.CLIENT)
public class LayerSkeletonHorseEyes implements LayerRenderer<EntitySkeletonHorse>
{
    private static final ResourceLocation eyes = new ResourceLocation("adinferos", "textures/entity/skeleton_horse_eyes.png");
    private final RenderSkeletonHorse horseRenderer;

    public LayerSkeletonHorseEyes(RenderSkeletonHorse p_i46109_1_)
    {
        this.horseRenderer = p_i46109_1_;
    }

    public void doRenderLayer(EntitySkeletonHorse entitylivingbaseIn, float limbSwing, float limbSwingAmount, float partialTicks, float ageInTicks, float netHeadYaw, float headPitch, float scale)
    {
        this.horseRenderer.bindTexture(eyes);
        GlStateManager.enableBlend();
        GlStateManager.blendFunc(GlStateManager.SourceFactor.ONE, GlStateManager.DestFactor.ONE);

        if (entitylivingbaseIn.isInvisible())
        {
            GlStateManager.depthMask(false);
        }
        else
        {
            GlStateManager.depthMask(true);
        }

        int i = 61680;
        int j = i % 65536;
        int k = i / 65536;
        OpenGlHelper.setLightmapTextureCoords(OpenGlHelper.lightmapTexUnit, (float)j, (float)k);
        GlStateManager.color(1.0F, 1.0F, 1.0F, 1.0F);
        this.horseRenderer.getMainModel().render(entitylivingbaseIn, limbSwing, limbSwingAmount, ageInTicks, netHeadYaw, headPitch, scale);
        i = entitylivingbaseIn.getBrightnessForRender(partialTicks);
        j = i % 65536;
        k = i / 65536;
        OpenGlHelper.setLightmapTextureCoords(OpenGlHelper.lightmapTexUnit, (float)j, (float)k);
        this.horseRenderer.setLightmap(entitylivingbaseIn, partialTicks);
        GlStateManager.disableBlend();
    }

    public boolean shouldCombineTextures()
    {
        return false;
    }
}