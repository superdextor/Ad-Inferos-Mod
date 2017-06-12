package com.superdextor.adinferos.render;

import com.superdextor.adinferos.entity.monster.EntitySummoner;
import com.superdextor.adinferos.render.model.ModelSummoner;

import net.minecraft.client.renderer.entity.RenderLiving;
import net.minecraft.client.renderer.entity.RenderManager;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

@SideOnly(Side.CLIENT)
public class RenderSummoner extends RenderLiving<EntitySummoner>
{
    private static final ResourceLocation defaultTextures = new ResourceLocation("textures/entity/skeleton/wither_skeleton.png");
    private static final ResourceLocation skeletonTextures = new ResourceLocation("textures/entity/skeleton/skeleton.png");
    private static final ResourceLocation spiderTextures = new ResourceLocation("adinferos", "textures/entity/black_widow_head.png");
    private static final ResourceLocation glowstoneSkeletonTextures = new ResourceLocation("adinferos", "textures/entity/glowstone_skeleton_head.png");
    private static final ResourceLocation phantomTextures = new ResourceLocation("adinferos", "textures/entity/phantom_head.png");
    private static final ResourceLocation blazeTextures = new ResourceLocation(("textures/entity/blaze.png"));
    private static final ResourceLocation reaperTextures = new ResourceLocation("adinferos", "textures/entity/reaper_head.png");
    private static final ResourceLocation herobrineTextures = new ResourceLocation("adinferos", "textures/entity/herobrine_head.png");
    
    private static final ModelSummoner modelSummoner = new ModelSummoner();

    public RenderSummoner(RenderManager renderer)
    {
        super(renderer, modelSummoner, 0.0F);
    }

    protected ResourceLocation getEntityTexture(EntitySummoner entitysummor)
    {
        switch (entitysummor.getStage())
        {
            case 0:
                return defaultTextures;
            case 1:
                return skeletonTextures;
            case 2:
                return spiderTextures;
            case 3:
                return glowstoneSkeletonTextures;
            case 4:
                return phantomTextures;
            case 5:
                return blazeTextures;
            case 6:
                return reaperTextures;
            case 7:
                return herobrineTextures;
            default:
                return defaultTextures;
        }
    }
}