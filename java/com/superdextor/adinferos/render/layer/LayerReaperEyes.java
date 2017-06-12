package com.superdextor.adinferos.render.layer;

import com.superdextor.adinferos.entity.monster.EntityReaper;
import com.superdextor.adinferos.render.RenderReaper;

import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.OpenGlHelper;
import net.minecraft.client.renderer.entity.layers.LayerRenderer;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

@SideOnly(Side.CLIENT)
public class LayerReaperEyes implements LayerRenderer<EntityReaper>
{
    private static final ResourceLocation eyes = new ResourceLocation("adinferos", "textures/entity/reaper_eyes.png");
    private final RenderReaper reaperRenderer;

    public LayerReaperEyes(RenderReaper p_i46109_1_)
    {
        this.reaperRenderer = p_i46109_1_;
    }

    public void doRenderLayer(EntityReaper entitylivingbaseIn, float limbSwing, float limbSwingAmount, float partialTicks, float ageInTicks, float netHeadYaw, float headPitch, float scale)
    {
    	if(entitylivingbaseIn.getEyeBrightness() > 0) {
            this.reaperRenderer.bindTexture(eyes);
            GlStateManager.blendFunc(GlStateManager.SourceFactor.ONE, GlStateManager.DestFactor.ONE);

            if (entitylivingbaseIn.isInvisible())
            {
                GlStateManager.depthMask(false);
            }
            else
            {
                GlStateManager.depthMask(true);
            }
                
            int i = entitylivingbaseIn.getEyeBrightness();
            int j = i % 65536;
            int k = i / 65536;
            OpenGlHelper.setLightmapTextureCoords(OpenGlHelper.lightmapTexUnit, (float)j, (float)k);
            this.reaperRenderer.getMainModel().render(entitylivingbaseIn, limbSwing, limbSwingAmount, ageInTicks, netHeadYaw, headPitch, scale);
            i = entitylivingbaseIn.getBrightnessForRender(partialTicks);
            j = i % 65536;
            k = i / 65536;
            OpenGlHelper.setLightmapTextureCoords(OpenGlHelper.lightmapTexUnit, (float)j, (float)k);
    	}
    }

    public boolean shouldCombineTextures()
    {
        return false;
    }
}