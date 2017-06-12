package com.superdextor.adinferos.render;

import com.superdextor.adinferos.entity.monster.EntityInfernumAvis;
import com.superdextor.adinferos.render.model.ModelInfernumAvis;

import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.entity.RenderLiving;
import net.minecraft.client.renderer.entity.RenderManager;
import net.minecraft.client.renderer.entity.layers.LayerHeldItem;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.util.ResourceLocation;

public class RenderInfernumAvis extends RenderLiving<EntityInfernumAvis> {
	
    private static final ResourceLocation textures = new ResourceLocation("adinferos", "textures/entity/infernum_avis.png");

	public RenderInfernumAvis(RenderManager renderer) {
		super(renderer, new ModelInfernumAvis(), 0);
        this.addLayer(new LayerHeldItem(this));
        this.shadowSize = 2.0F;
	}

	protected ResourceLocation getEntityTexture(EntityInfernumAvis entity) {
		return textures;
	}
	
    public void doRender(EntityInfernumAvis entity, double x, double y, double z, float p_76986_8_, float partialTicks)
    {
        this.shadowSize = entity.getHealth() / 160 + 1.0F;
    	
        if (entity.isSleeping())
        {
            y += -0.8F;
        }

        super.doRender(entity, x, y, z, p_76986_8_, partialTicks);
    }
	
    protected void preRenderCallback(EntityInfernumAvis entity, float ticks)
    {
    	float size = entity.getHealth() / 160 + 1.0F;
        if(entity.isChild()) {
        	size *= 0.5;
        }
        GlStateManager.scale(size, size, size);
    }
	
}