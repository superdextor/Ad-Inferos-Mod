package com.superdextor.adinferos.render.layer;

import com.superdextor.adinferos.entity.monster.EntityBlackWidow;
import com.superdextor.adinferos.render.RenderBlackWidow;

import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.OpenGlHelper;
import net.minecraft.client.renderer.entity.RenderSpider;
import net.minecraft.client.renderer.entity.layers.LayerRenderer;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

@SideOnly(Side.CLIENT)
public class LayerBlackWidowEyes<T extends EntityBlackWidow> implements LayerRenderer<T>
{
    private static final ResourceLocation SpiderEyes = new ResourceLocation("adinferos", "textures/entity/black_widow_eyes.png");
    private final RenderBlackWidow spiderRenderer;

    public LayerBlackWidowEyes(RenderBlackWidow renderer)
    {
        this.spiderRenderer = renderer;
    }

    public void doRenderLayer(T entitylivingbaseIn, float limbSwing, float limbSwingAmount, float partialTicks, float ageInTicks, float netHeadYaw, float headPitch, float scale)
    {
        this.spiderRenderer.bindTexture(SpiderEyes);
        GlStateManager.enableBlend();
        GlStateManager.disableAlpha();
        GlStateManager.blendFunc(GlStateManager.SourceFactor.ONE, GlStateManager.DestFactor.ONE);

        if (entitylivingbaseIn.isInvisible())
        {
            GlStateManager.depthMask(false);
        }
        else
        {
            GlStateManager.depthMask(true);
        }

        int i = 15728880;
        int j = i % 65536;
        int k = i / 65536;
        OpenGlHelper.setLightmapTextureCoords(OpenGlHelper.lightmapTexUnit, (float)j, (float)k);
        GlStateManager.color(1.0F, 1.0F, 1.0F, 1.0F);
        this.spiderRenderer.getMainModel().render(entitylivingbaseIn, limbSwing, limbSwingAmount, ageInTicks, netHeadYaw, headPitch, scale);
        i = entitylivingbaseIn.getBrightnessForRender(partialTicks);
        j = i % 65536;
        k = i / 65536;
        OpenGlHelper.setLightmapTextureCoords(OpenGlHelper.lightmapTexUnit, (float)j, (float)k);
        this.spiderRenderer.setLightmap(entitylivingbaseIn, partialTicks);
        GlStateManager.disableBlend();
        GlStateManager.enableAlpha();
    }

    public boolean shouldCombineTextures()
    {
        return false;
    }
}