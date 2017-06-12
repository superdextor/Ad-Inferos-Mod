package com.superdextor.adinferos.render;

import com.superdextor.adinferos.entity.monster.EntityCurse;

import net.minecraft.client.model.ModelZombie;
import net.minecraft.client.renderer.entity.Render;
import net.minecraft.client.renderer.entity.RenderManager;
import net.minecraft.entity.Entity;
import net.minecraft.util.ResourceLocation;

public class RenderCurse extends Render<EntityCurse> {

	public RenderCurse(RenderManager renderer) {
		super(renderer);
	}

	protected ResourceLocation getEntityTexture(EntityCurse entity) {
		return null;
	}
	
}