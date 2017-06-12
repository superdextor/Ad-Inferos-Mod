package com.superdextor.adinferos.render.layer;

import com.superdextor.adinferos.AdInferosCore;
import com.superdextor.adinferos.render.model.ModelDemonPlayer;

import net.minecraft.client.renderer.entity.RenderPlayer;
import net.minecraft.client.renderer.entity.layers.LayerRenderer;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

@SideOnly(Side.CLIENT)
public class LayerDemonPlayer implements LayerRenderer<EntityPlayer>
{
    private static final ResourceLocation textures = new ResourceLocation("adinferos", "textures/entity/player_horns.png");
    private final RenderPlayer renderer;
    private final ModelDemonPlayer modelHorns;

    public LayerDemonPlayer(RenderPlayer rendererIn)
    {
        this.renderer = rendererIn;
        this.modelHorns = new ModelDemonPlayer(rendererIn);
    }

    public void doRenderLayer(EntityPlayer entitylivingbaseIn, float limbSwing, float limbSwingAmount, float partialTicks, float ageInTicks, float netHeadYaw, float headPitch, float scale)
    {
    	if(AdInferosCore.proxy.isNetherSurvival() && !entitylivingbaseIn.isInvisible()) {
            this.renderer.bindTexture(textures);
            this.modelHorns.setRotationAngles(limbSwing, limbSwingAmount, ageInTicks, netHeadYaw, headPitch, scale, entitylivingbaseIn);
            this.modelHorns.render(entitylivingbaseIn, limbSwing, limbSwingAmount, ageInTicks, netHeadYaw, headPitch, scale);
    	}
    }

    public boolean shouldCombineTextures()
    {
        return false;
    }
}