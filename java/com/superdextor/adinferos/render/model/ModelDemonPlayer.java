package com.superdextor.adinferos.render.model;

import net.minecraft.client.model.ModelBase;
import net.minecraft.client.model.ModelRenderer;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.entity.RenderPlayer;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

@SideOnly(Side.CLIENT)
public class ModelDemonPlayer extends ModelBase
{
	private final RenderPlayer renderer;
    private ModelRenderer rightHorn;
    private ModelRenderer leftHorn;

    public ModelDemonPlayer(RenderPlayer rendererIn)
    {
    	this.renderer = rendererIn;
        this.textureWidth = 8;
        this.textureHeight = 8;
        this.rightHorn = new ModelRenderer(this, 0, 0);
        this.rightHorn.addBox(0F, 0F, 0F, 1, 4, 1);
        this.rightHorn.setRotationPoint(4F, -11F, -1F);
        this.leftHorn = new ModelRenderer(this, 0, 0);
        this.leftHorn.addBox(0F, 0F, 0F, 1, 4, 1);
        this.leftHorn.setRotationPoint(-5F, -11F, -1F);
    }

    /**
     * Sets the models various rotation angles then renders the model.
     */
    public void render(Entity entityIn, float p_78088_2_, float limbSwing, float ageInTicks, float netHeadYaw, float headPitch, float scale)
    {
    	ModelRenderer base = this.renderer.getMainModel().bipedHead;
    	
        if (entityIn.isSneaking())
        {
            GlStateManager.translate(0.0F, 0.2F, 0.0F);
        }
    	
    	GlStateManager.translate(base.offsetX, base.offsetY, base.offsetZ);

        if (base.rotateAngleX == 0.0F && base.rotateAngleY == 0.0F && base.rotateAngleZ == 0.0F)
        {
            if (base.rotationPointX == 0.0F && base.rotationPointY == 0.0F && base.rotationPointZ == 0.0F)
            {

            	this.rightHorn.render(scale);
            	this.leftHorn.render(scale);
            }
            else
            {
                GlStateManager.translate(base.rotationPointX * scale, base.rotationPointY * scale, base.rotationPointZ * scale);

            	this.rightHorn.render(scale);
            	this.leftHorn.render(scale);

                GlStateManager.translate(-base.rotationPointX * scale, -base.rotationPointY * scale, -base.rotationPointZ * scale);
            }
        }
        else
        {
            GlStateManager.pushMatrix();
            GlStateManager.translate(base.rotationPointX * scale, base.rotationPointY * scale, base.rotationPointZ * scale);

            if (base.rotateAngleZ != 0.0F)
            {
                GlStateManager.rotate(base.rotateAngleZ * (180F / (float)Math.PI), 0.0F, 0.0F, 1.0F);
            }

            if (base.rotateAngleY != 0.0F)
            {
                GlStateManager.rotate(base.rotateAngleY * (180F / (float)Math.PI), 0.0F, 1.0F, 0.0F);
            }

            if (base.rotateAngleX != 0.0F)
            {
                GlStateManager.rotate(base.rotateAngleX * (180F / (float)Math.PI), 1.0F, 0.0F, 0.0F);
            }

        	this.rightHorn.render(scale);
        	this.leftHorn.render(scale);

            GlStateManager.popMatrix();
        }

        GlStateManager.translate(-base.offsetX, -base.offsetY, -base.offsetZ);
    }

    /**
     * Sets the model's various rotation angles. For bipeds, par1 and par2 are used for animating the movement of arms
     * and legs, where par1 represents the time(so that arms and legs swing back and forth) and par2 represents how
     * "far" arms and legs can swing at most.
     */
    public void setRotationAngles(float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw, float headPitch, float scaleFactor, Entity entityIn)
    {
    	super.setRotationAngles(limbSwing, limbSwingAmount, ageInTicks, netHeadYaw, headPitch, scaleFactor, entityIn);
    	/**
    	ModelRenderer renderer = this.renderer.getMainModel().bipedHead;
    	copyModelAngles(renderer, this.rightHorn);
    	copyModelAngles(renderer, this.leftHorn);
        this.rightHorn.rotationPointX = renderer.rotationPointX + 4.0F;
        this.leftHorn.rotationPointX = renderer.rotationPointX - 5.0F;
        this.rightHorn.rotationPointY = renderer.rotationPointY - 11.0F;
        this.leftHorn.rotationPointY = renderer.rotationPointY - 11.0F;
        this.rightHorn.rotationPointZ = renderer.rotationPointZ - 1.0F;
        this.leftHorn.rotationPointZ = renderer.rotationPointZ - 1.0F;
        **/
        
    }

    /**
     * Used for easily adding entity-dependent animations. The second and third float params here are the same second
     * and third as in the setRotationAngles method.
     */
    public void setLivingAnimations(EntityLivingBase entitylivingbaseIn, float p_78086_2_, float p_78086_3_, float partialTickTime)
    {
        super.setLivingAnimations(entitylivingbaseIn, p_78086_2_, p_78086_3_, partialTickTime);
    }
}