package com.superdextor.adinferos.render.model;

import com.superdextor.adinferos.entity.monster.EntityInfernumAvis;

import net.minecraft.client.model.ModelBase;
import net.minecraft.client.model.ModelRenderer;
import net.minecraft.entity.Entity;
import net.minecraft.util.math.MathHelper;

public class ModelInfernumAvis extends ModelBase
{
  //fields
    ModelRenderer head;
    ModelRenderer body;
    ModelRenderer rightarm;
    ModelRenderer leftarm;
    ModelRenderer rightleg;
    ModelRenderer leftleg;
    ModelRenderer leftHorn;
    ModelRenderer rightHorn;
    ModelRenderer leftWign;
    ModelRenderer rightWign;
  
  public ModelInfernumAvis()
  {
    textureWidth = 68;
    textureHeight = 34;
    
      head = new ModelRenderer(this, 0, 0);
      head.addBox(-4F, -8F, -4F, 8, 8, 8);
      head.setRotationPoint(0F, 0F, 0F);
      head.setTextureSize(64, 32);
      head.mirror = true;
      setRotation(head, 0F, 0F, 0F);
      body = new ModelRenderer(this, 16, 16);
      body.addBox(-4F, 0F, -2F, 8, 12, 4);
      body.setRotationPoint(0F, 0F, 0F);
      body.setTextureSize(64, 32);
      body.mirror = true;
      setRotation(body, 0F, 0F, 0F);
      rightarm = new ModelRenderer(this, 40, 16);
      rightarm.addBox(-3F, -2F, -2F, 3, 14, 4);
      rightarm.setRotationPoint(-4F, 2F, 0F);
      rightarm.setTextureSize(64, 32);
      rightarm.mirror = true;
      setRotation(rightarm, 0F, 0F, 0F);
      leftarm = new ModelRenderer(this, 40, 16);
      leftarm.addBox(-1F, -2F, -2F, 3, 14, 4);
      leftarm.setRotationPoint(5F, 2F, 0F);
      leftarm.setTextureSize(64, 32);
      leftarm.mirror = true;
      setRotation(leftarm, 0F, 0F, 0F);
      rightleg = new ModelRenderer(this, 0, 16);
      rightleg.addBox(-2F, 0F, -2F, 4, 12, 4);
      rightleg.setRotationPoint(-2F, 12F, 0F);
      rightleg.setTextureSize(64, 32);
      rightleg.mirror = true;
      setRotation(rightleg, 0F, 0F, 0F);
      leftleg = new ModelRenderer(this, 0, 16);
      leftleg.addBox(-2F, 0F, -2F, 4, 12, 4);
      leftleg.setRotationPoint(2F, 12F, 0F);
      leftleg.setTextureSize(64, 32);
      leftleg.mirror = true;
      setRotation(leftleg, 0F, 0F, 0F);
      leftHorn = new ModelRenderer(this, 32, 0);
      leftHorn.addBox(0F, 0F, 0F, 1, 6, 1);
      leftHorn.setRotationPoint(4F, -11F, -2F);
      leftHorn.setTextureSize(64, 32);
      leftHorn.mirror = true;
      head.addChild(leftHorn);
      setRotation(leftHorn, 0F, 0F, 0F);
      rightHorn = new ModelRenderer(this, 32, 0);
      rightHorn.addBox(0F, 0F, 0F, 1, 6, 1);
      rightHorn.setRotationPoint(-5F, -11F, -2F);
      rightHorn.setTextureSize(64, 32);
      rightHorn.mirror = true;
      head.addChild(rightHorn);
      setRotation(rightHorn, 0F, 0F, 0F);
      leftWign = new ModelRenderer(this, 36, 0);
      leftWign.addBox(0F, 0F, 0F, 7, 12, 1);
      leftWign.setRotationPoint(-0.5F, 0F, 1F);
      leftWign.setTextureSize(64, 32);
      setRotation(leftWign, 0F, -0.1396263F, -0.3316126F);
      rightWign = new ModelRenderer(this, 52, 0);
      rightWign.addBox(0F, 0F, 0F, 7, 12, 1);
      rightWign.setRotationPoint(0.5F, 0F, 2F);
      rightWign.setTextureSize(64, 32);
      rightWign.mirror = true;
      setRotation(rightWign, 0F, 3.281219F, 0.3141593F);
  }
  
  public void render(Entity entity, float f, float f1, float f2, float f3, float f4, float f5)
  {
    super.render(entity, f, f1, f2, f3, f4, f5);
    setRotationAngles(f, f1, f2, f3, f4, f5, entity);
    head.render(f5);
    body.render(f5);
    rightarm.render(f5);
    leftarm.render(f5);
    rightleg.render(f5);
    leftleg.render(f5);
    leftWign.render(f5);
    rightWign.render(f5);
  }
  
  private void setRotation(ModelRenderer model, float x, float y, float z)
  {
    model.rotateAngleX = x;
    model.rotateAngleY = y;
    model.rotateAngleZ = z;
  }
  
  public void setRotationAngles(float f, float f1, float f2, float f3, float f4, float f5, Entity entity)
  {
    super.setRotationAngles(f, f1, f2, f3, f4, f5, entity);
    EntityInfernumAvis entityavis = (EntityInfernumAvis) entity;
    head.rotateAngleY = f3 / (180F / (float)Math.PI);
    head.rotateAngleX = f4 / (180F / (float)Math.PI);
    rightarm.rotateAngleX = MathHelper.cos(f * 0.6662F + (float)Math.PI) * 2.0F * f1 * 0.5F;
    leftarm.rotateAngleX = MathHelper.cos(f * 0.6662F) * 2.0F * f1 * 0.5F;
    rightarm.rotateAngleZ = 0.0F;
    leftarm.rotateAngleZ = 0.0F;
    rightleg.rotateAngleX = MathHelper.cos(f * 0.6662F) * 1.4F * f1;
    leftleg.rotateAngleX = MathHelper.cos(f * 0.6662F + (float)Math.PI) * 1.4F * f1;
    rightleg.rotateAngleY = 0.0F;
    leftleg.rotateAngleY = 0.0F;

    
    
    
    //0 = Idle 1 = Flying 2 = Falling
    int wign = entityavis.getWignAnimation();
    
    
    rightWign.rotateAngleZ = 0.0141593F;
    rightWign.rotateAngleX =  0.0F;
    leftWign.rotateAngleZ = -0.0141593F;
    leftWign.rotateAngleX =  0.0F;
    
    if(wign == 0) {
        rightWign.rotateAngleZ -= MathHelper.cos(f2 * 0.15F) * 0.05F + 0.05F -0.1F;
        rightWign.rotateAngleX -= MathHelper.sin(f2 * 0.067F) * 0.05F +0.2F;
        leftWign.rotateAngleZ -= MathHelper.cos(f2 * 0.15F) * 0.05F + 0.05F +0.1F;
        leftWign.rotateAngleX -= MathHelper.sin(f2 * 0.067F) * 0.05F -0.2F;
    }
    else if(wign == 1) {
        rightWign.rotateAngleZ -= MathHelper.cos(f2 * 0.8F) * 0.4F + -0.3F;
        rightWign.rotateAngleX -= MathHelper.sin(f2 * 0.8F) * 0.8F + 0.8F;
        leftWign.rotateAngleZ -= -MathHelper.cos(f2 * 0.8F) * 0.4F + 0.3F;
        leftWign.rotateAngleX -= -MathHelper.sin(f2 * 0.8F) * 0.8F + -0.8F;
    
    }
    else
    {
    	float falltimer = (float)entity.motionY * 1.6F;
    	
        rightWign.rotateAngleX -= MathHelper.sin(f2 * 0.1F) * 0.05F -falltimer;
        leftWign.rotateAngleX -= -MathHelper.sin(f2 * 0.1F) * 0.05F +falltimer;
        
        rightWign.rotateAngleZ -= -falltimer * 0.3;
        leftWign.rotateAngleZ -= +falltimer * 0.3;
    }
    
    rightarm.rotateAngleZ += MathHelper.cos(f2 * 0.09F) * 0.05F + 0.05F;
    leftarm.rotateAngleZ -= MathHelper.cos(f2 * 0.09F) * 0.05F + 0.05F;
    rightarm.rotateAngleX += MathHelper.sin(f2 * 0.067F) * 0.05F;
    leftarm.rotateAngleX -= MathHelper.sin(f2 * 0.067F) * 0.05F;
    
    if(entityavis.isSleeping()) {
    	
        rightarm.rotateAngleX += -((float)Math.PI / 5F);
        leftarm.rotateAngleX += -((float)Math.PI / 5F);
        rightleg.rotateAngleX = -((float)Math.PI * 2F / 5F);
        leftleg.rotateAngleX = -((float)Math.PI * 2F / 5F);
        rightleg.rotateAngleY = ((float)Math.PI / 10F);
        leftleg.rotateAngleY = -((float)Math.PI / 10F);
        head.rotateAngleX = 20F;
        head.rotateAngleY = 0F;
    }
    
  }
  
  public void postRenderArm(float pos)
  {
      this.rightarm.postRender(pos);
  }
  
  public void setInvisible(boolean invisible)
  {
      head.showModel = invisible;
      body.showModel = invisible;
      rightarm.showModel = invisible;
      leftarm.showModel = invisible;
      rightleg.showModel = invisible;
      leftleg.showModel = invisible;
  }

}
