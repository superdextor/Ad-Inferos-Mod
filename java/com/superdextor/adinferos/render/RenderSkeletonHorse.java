package com.superdextor.adinferos.render;

import com.superdextor.adinferos.entity.monster.EntitySkeletonHorse;
import com.superdextor.adinferos.render.layer.LayerSkeletonHorseEyes;
import com.superdextor.adinferos.render.model.ModelSkeletonHorse;

import net.minecraft.client.renderer.entity.RenderLiving;
import net.minecraft.client.renderer.entity.RenderManager;
import net.minecraft.entity.Entity;
import net.minecraft.util.ResourceLocation;

public class RenderSkeletonHorse extends RenderLiving<EntitySkeletonHorse> {
	
    private static final ResourceLocation skeletonTextures = new ResourceLocation("minecraft", "textures/entity/horse/horse_skeleton.png");
    private static final ResourceLocation witherSkeletonTextures = new ResourceLocation("adinferos", "textures/entity/wither_skeleton_horse.png");

	public RenderSkeletonHorse(RenderManager renderer) {
		super(renderer, new ModelSkeletonHorse(), 0.9F);
        this.addLayer(new LayerSkeletonHorseEyes(this));
	}
	
    protected ResourceLocation getEntityTexture(EntitySkeletonHorse entity)
    {
        return entity.isWither() ? witherSkeletonTextures : skeletonTextures;
    }
	
}