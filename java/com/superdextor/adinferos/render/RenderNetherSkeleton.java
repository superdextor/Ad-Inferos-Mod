package com.superdextor.adinferos.render;

import com.superdextor.adinferos.entity.monster.EntityDimstoneSkeleton;
import com.superdextor.adinferos.entity.monster.EntityNetherSkeleton;
import com.superdextor.adinferos.render.model.ModelNetherSkeleton;

import net.minecraft.client.renderer.entity.RenderBiped;
import net.minecraft.client.renderer.entity.RenderManager;
import net.minecraft.client.renderer.entity.layers.LayerBipedArmor;
import net.minecraft.util.ResourceLocation;

public class RenderNetherSkeleton extends RenderBiped<EntityNetherSkeleton> {
	
    private static final ResourceLocation glowstoneTextures = new ResourceLocation("adinferos", "textures/entity/glowstone_skeleton/default.png");
    private static final ResourceLocation dimstoneTextures = new ResourceLocation("adinferos", "textures/entity/glowstone_skeleton/dimstone.png");

	public RenderNetherSkeleton(RenderManager renderer) {
        super(renderer, new ModelNetherSkeleton(), 0.5F);
        this.addLayer(new LayerBipedArmor(this));
	}
	
	protected ResourceLocation getEntityTexture(EntityNetherSkeleton entity) {
		return entity instanceof EntityDimstoneSkeleton ? dimstoneTextures : glowstoneTextures;
	}
	
}	