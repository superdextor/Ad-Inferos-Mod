package com.superdextor.adinferos.render;

import java.util.Random;

import org.lwjgl.opengl.GL11;

import com.superdextor.adinferos.entity.monster.EntityHerobrine;
import com.superdextor.adinferos.entity.monster.EntityReaper;
import com.superdextor.adinferos.render.layer.LayerHerobrineEyes;

import net.minecraft.client.Minecraft;
import net.minecraft.client.model.ModelBiped;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.entity.RenderBiped;
import net.minecraft.client.renderer.entity.RenderManager;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.util.ResourceLocation;

public class RenderHerobrine extends RenderBiped<EntityHerobrine> {
	
    private static final ResourceLocation textures = new ResourceLocation("adinferos", "textures/entity/herobrine.png");
    private final Random rand = new Random();
    
	public RenderHerobrine(RenderManager renderer) {
        super(renderer, new ModelBiped(0.0F, 0.0F, 64, 64), 0.5F);
        this.addLayer(new LayerHerobrineEyes(this));
	}

	protected ResourceLocation getEntityTexture(EntityHerobrine entity) {
		return textures;
	}
	
    protected void renderModel(EntityHerobrine entity, float f1, float f2, float f3, float f4, float f5, float scaleFactor)
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

            if(entity.isCloaking()) {
            	float f = 0.1F + this.rand.nextFloat() * 0.3F;
            	
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
	
}	