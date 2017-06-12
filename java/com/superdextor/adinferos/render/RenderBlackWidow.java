package com.superdextor.adinferos.render;

import com.google.common.collect.Lists;
import com.superdextor.adinferos.entity.monster.EntityBlackWidow;
import com.superdextor.adinferos.render.layer.LayerBlackWidowEyes;

import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.entity.RenderManager;
import net.minecraft.client.renderer.entity.RenderSpider;
import net.minecraft.client.renderer.entity.layers.LayerRenderer;
import net.minecraft.util.ResourceLocation;

public class RenderBlackWidow extends RenderSpider<EntityBlackWidow> {
	
    private static final ResourceLocation spiderTextures = new ResourceLocation("adinferos", "textures/entity/black_widow.png");

	public RenderBlackWidow(RenderManager renderer) {
		super(renderer);
	    this.layerRenderers = Lists.<LayerRenderer<EntityBlackWidow>>newArrayList();
        this.addLayer(new LayerBlackWidowEyes(this));
	}

	protected ResourceLocation getEntityTexture(EntityBlackWidow entity) {
		return spiderTextures;
	}
	
    public void doRender(EntityBlackWidow entityNetherSpider, double x, double y, double z, float f, float partialTicks)
    {
        this.shadowSize = 0.5F * (entityNetherSpider.getSpiderSize() + 1);
        super.doRender(entityNetherSpider, x, y, z, f, partialTicks);
    }
	
    protected void preRenderCallback(EntityBlackWidow entityNetherSpider, float f)
    {
        float f1 = 0.5F * (entityNetherSpider.getSpiderSize() + 1);
        GlStateManager.scale(f1,f1,f1);
    }
}