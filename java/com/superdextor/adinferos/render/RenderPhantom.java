package com.superdextor.adinferos.render;

import java.util.Random;

import org.lwjgl.opengl.GL11;

import com.superdextor.adinferos.entity.monster.EntityPhantom;
import com.superdextor.adinferos.render.layer.LayerPhantomEyes;
import com.superdextor.adinferos.render.layer.LayerPhantomHead;
import com.superdextor.adinferos.render.model.ModelPhantom;

import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.entity.RenderLiving;
import net.minecraft.client.renderer.entity.RenderManager;
import net.minecraft.util.ResourceLocation;

public class RenderPhantom extends RenderLiving<EntityPhantom> {
	
	private static final ResourceLocation textures = new ResourceLocation("adinferos", "textures/entity/phantom.png");
	private final Random rand = new Random();

	public RenderPhantom(RenderManager renderer) {
		super(renderer, new ModelPhantom(), 0.6F);
		this.layerRenderers.add(new LayerPhantomHead(this));
		this.layerRenderers.add(new LayerPhantomEyes(this));
	}
	
    protected void renderModel(EntityPhantom entity, float f1, float f2, float f3, float f4, float f5, float scaleFactor)
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

            float f = 0.2F + rand.nextFloat();
            
            if(f > 1.0F) {
            	f = 1.0F;
            }
            
            if(entity.isSneaking()) {
            	f *= 0.2F;
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

            if (flag1)
            {
                GlStateManager.disableBlendProfile(GlStateManager.Profile.TRANSPARENT_MODEL);
            }
            GlStateManager.disableBlend();
        }
        
    }

	protected ResourceLocation getEntityTexture(EntityPhantom entity) {
		return textures;
	}
	
}