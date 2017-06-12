package com.superdextor.adinferos.render.layer;

import com.superdextor.adinferos.entity.monster.EntityPhantom;
import com.superdextor.adinferos.render.RenderPhantom;

import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.OpenGlHelper;
import net.minecraft.client.renderer.entity.layers.LayerRenderer;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

@SideOnly(Side.CLIENT)
public class LayerPhantomEyes implements LayerRenderer<EntityPhantom>
{
    private static final ResourceLocation eyes = new ResourceLocation("adinferos", "textures/entity/phantom_eyes.png");
    private final RenderPhantom phantomRenderer;

    public LayerPhantomEyes(RenderPhantom p_i46109_1_)
    {
        this.phantomRenderer = p_i46109_1_;
    }

    public void doRenderLayer(EntityPhantom entitylivingbaseIn, float limbSwing, float limbSwingAmount, float partialTicks, float ageInTicks, float netHeadYaw, float headPitch, float scale)
    {
    	if(entitylivingbaseIn.hasAttackTarget()) {
            this.phantomRenderer.bindTexture(eyes);
            GlStateManager.blendFunc(GlStateManager.SourceFactor.ONE, GlStateManager.DestFactor.ONE);

            if (entitylivingbaseIn.isInvisible())
            {
                GlStateManager.depthMask(false);
            }
            else
            {
                GlStateManager.depthMask(true);
            }
            
            this.phantomRenderer.getMainModel().render(entitylivingbaseIn, limbSwing, limbSwingAmount, ageInTicks, netHeadYaw, headPitch, scale);
    	}
    }

    public boolean shouldCombineTextures()
    {
        return false;
    }
}