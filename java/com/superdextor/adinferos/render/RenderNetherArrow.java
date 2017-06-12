package com.superdextor.adinferos.render;

import com.superdextor.adinferos.entity.other.EntityNetherArrow;

import net.minecraft.client.renderer.entity.RenderArrow;
import net.minecraft.client.renderer.entity.RenderManager;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

@SideOnly(Side.CLIENT)
public class RenderNetherArrow extends RenderArrow<EntityNetherArrow>
{
    private static final ResourceLocation quartzTextures = new ResourceLocation("adinferos", "textures/entity/arrow_quartz.png");
    private static final ResourceLocation glowstoneTextures = new ResourceLocation("adinferos", "textures/entity/arrow_glowstone.png");
    private static final ResourceLocation obsidianTextures = new ResourceLocation("adinferos", "textures/entity/arrow_obsidian.png");
    private static final ResourceLocation witherTextures = new ResourceLocation("adinferos", "textures/entity/arrow_wither.png");
    private static final ResourceLocation netheriteTextures = new ResourceLocation("adinferos", "textures/entity/arrow_netherite.png");

    public RenderNetherArrow(RenderManager renderer)
    {
        super(renderer);
    }
    
    protected ResourceLocation getEntityTexture(EntityNetherArrow entity)
    {
    	switch(entity.getArrowType()) {
    	case 0:
    		return quartzTextures;
    	case 1:
    		return glowstoneTextures;
    	case 2:
    		return obsidianTextures;
    	case 3:
    		return witherTextures;
    	case 4:
    		return netheriteTextures;
    	default:
    		return quartzTextures;
    	}
    }
}