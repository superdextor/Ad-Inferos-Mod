package com.superdextor.adinferos.render;

import net.minecraft.client.renderer.entity.RenderChicken;
import net.minecraft.client.renderer.entity.RenderManager;
import net.minecraft.entity.passive.EntityChicken;
import net.minecraft.util.ResourceLocation;

public class RenderInfernalChicken extends RenderChicken {

    private static final ResourceLocation chickenTextures = new ResourceLocation("adinferos", "textures/entity/infernal_chicken.png");
	
	public RenderInfernalChicken(RenderManager renderManagerIn) {
		super(renderManagerIn);
	}

    protected ResourceLocation getEntityTexture(EntityChicken entity)
    {
        return chickenTextures;
    }
	
}
