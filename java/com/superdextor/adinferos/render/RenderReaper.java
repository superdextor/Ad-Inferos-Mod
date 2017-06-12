package com.superdextor.adinferos.render;

import java.util.Random;

import org.lwjgl.opengl.GL11;

import com.superdextor.adinferos.entity.monster.EntityReaper;
import com.superdextor.adinferos.render.layer.LayerReaperEyes;
import com.superdextor.adinferos.render.model.ModelReaper;

import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.entity.RenderLiving;
import net.minecraft.client.renderer.entity.RenderManager;
import net.minecraft.client.renderer.entity.layers.LayerBipedArmor;
import net.minecraft.client.renderer.entity.layers.LayerHeldItem;
import net.minecraft.util.ResourceLocation;

public class RenderReaper extends RenderLiving<EntityReaper> {
	
    private static final ResourceLocation reaperTextures = new ResourceLocation("adinferos", "textures/entity/reaper.png");
    private final Random rand = new Random();
    
	public RenderReaper(RenderManager renderer) {
		super(renderer, new ModelReaper(), 0.5F);
		this.addLayer(new LayerReaperEyes(this));
        this.addLayer(new LayerHeldItem(this));
        this.addLayer(new LayerBipedArmor(this)
        {
            protected void initArmor()
            {
                this.modelLeggings = new ModelReaper(0.5F, true);
                this.modelArmor = new ModelReaper(1.0F, true);
            }
        });
	}
	
    protected void renderModel(EntityReaper entity, float f1, float f2, float f3, float f4, float f5, float scaleFactor)
    {
        boolean flag = !entity.isInvisible() || this.renderOutlines;
        boolean flag1 = !flag && !entity.isInvisibleToPlayer(Minecraft.getMinecraft().thePlayer);

        if (flag || flag1)
        {
            if (!this.bindEntityTexture(entity))
            {
                return;
            }

            if (flag1)
            {
                GlStateManager.enableBlendProfile(GlStateManager.Profile.TRANSPARENT_MODEL);
            }

            if(entity.getAlpha() < 1.0F || entity.isCloaking()) {
            	float f = entity.getAlpha();
            	
            	if(entity.isCloaking()) {
            		f = 0.1F + this.rand.nextFloat() * 0.3F;
            	}
            	
                GlStateManager.enableBlend();
                GL11.glColor4f(1.0F, 1.0F, 1.0F, f);
                GL11.glEnable(GL11.GL_BLEND);
                GL11.glBlendFunc(GL11.GL_SRC_ALPHA, GL11.GL_ONE_MINUS_SRC_ALPHA);
                GL11.glAlphaFunc(GL11.GL_GREATER, 0.003921569F);
                this.mainModel.render(entity, f1, f2, f3, f4, f5, scaleFactor);
                GL11.glDisable(GL11.GL_BLEND);
                GL11.glAlphaFunc(GL11.GL_GREATER, 0.1F);
                GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
                
            }else {
                this.mainModel.render(entity, f1, f2, f3, f4, f5, scaleFactor);
            }

            if (flag1)
            {
                GlStateManager.disableBlendProfile(GlStateManager.Profile.TRANSPARENT_MODEL);
            }
        }
        
    }

	protected ResourceLocation getEntityTexture(EntityReaper entity) {
		return reaperTextures;
	}
	
}