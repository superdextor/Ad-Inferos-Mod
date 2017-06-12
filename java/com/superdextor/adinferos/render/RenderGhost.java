package com.superdextor.adinferos.render;

import java.util.Map;
import java.util.Random;
import java.util.UUID;

import org.lwjgl.opengl.GL11;

import com.mojang.authlib.GameProfile;
import com.mojang.authlib.minecraft.MinecraftProfileTexture;
import com.mojang.authlib.minecraft.MinecraftProfileTexture.Type;
import com.superdextor.adinferos.entity.monster.EntityGhost;
import com.superdextor.adinferos.render.model.ModelGhost;

import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.entity.RenderBiped;
import net.minecraft.client.renderer.entity.RenderManager;
import net.minecraft.client.renderer.entity.layers.LayerBipedArmor;
import net.minecraft.client.renderer.entity.layers.LayerHeldItem;
import net.minecraft.client.resources.DefaultPlayerSkin;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

@SideOnly(Side.CLIENT)
public class RenderGhost extends RenderBiped<EntityGhost>
{
	private final Random rand = new Random();
    
    public RenderGhost(RenderManager renderManagerIn)
    {
        super(renderManagerIn, new ModelGhost(), 0.0F);
        this.addLayer(new LayerHeldItem(this));
        LayerBipedArmor layerbipedarmor = new LayerBipedArmor(this)
        {
            protected void initArmor()
            {
                this.modelLeggings = new ModelGhost(0.5F, true);
                this.modelArmor = new ModelGhost(1.0F, true);
            }
        };
        this.addLayer(layerbipedarmor);
    }
	
    protected void renderModel(EntityGhost entity, float f1, float f2, float f3, float f4, float f5, float scaleFactor)
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

    /**
     * Returns the location of an entity's texture. Doesn't seem to be called unless you call Render.bindEntityTexture.
     */
    protected ResourceLocation getEntityTexture(EntityGhost entity)
    {
    	return new ResourceLocation(entity.getTextures());
    }
    
    private void getPlayerTexture(String name, EntityGhost ghost) {
        ResourceLocation resourcelocation = DefaultPlayerSkin.getDefaultSkinLegacy();
        GameProfile gameProfile = ghost.getGameProfile(name);

        Minecraft minecraft = Minecraft.getMinecraft();
        Map<Type, MinecraftProfileTexture> map = minecraft.getSkinManager().loadSkinFromCache(gameProfile);

        if (map.containsKey(Type.SKIN))
        {
            resourcelocation = minecraft.getSkinManager().loadSkin((MinecraftProfileTexture)map.get(Type.SKIN), Type.SKIN);
        }
        else
        {
            UUID uuid = EntityPlayer.getUUID(gameProfile);
            resourcelocation = DefaultPlayerSkin.getDefaultSkin(uuid);
        }

        this.bindTexture(resourcelocation);
    }
    
    protected boolean bindEntityTexture(EntityGhost entity) {
    	
    	if(entity.getTextures().contains("player")) {
    		this.getPlayerTexture(entity.getTextures().substring(7), entity);
    		return true;
    	}
    	
    	return super.bindEntityTexture(entity);
    }
}