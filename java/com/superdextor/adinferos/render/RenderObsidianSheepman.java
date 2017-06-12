package com.superdextor.adinferos.render;

import com.superdextor.adinferos.entity.monster.EntityObsidianSheepman;
import com.superdextor.adinferos.render.layer.LayerSheepmanHeadband;

import net.minecraft.client.model.ModelZombie;
import net.minecraft.client.renderer.entity.RenderBiped;
import net.minecraft.client.renderer.entity.RenderManager;
import net.minecraft.client.renderer.entity.layers.LayerBipedArmor;
import net.minecraft.client.renderer.entity.layers.LayerHeldItem;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

@SideOnly(Side.CLIENT)
public class RenderObsidianSheepman extends RenderBiped<EntityObsidianSheepman>
{
    private static final ResourceLocation obsidianSheepmanTextures = new ResourceLocation("adinferos", "textures/entity/obsidian_sheepman/main.png");

    public RenderObsidianSheepman(RenderManager renderer)
    {
        super(renderer, new ModelZombie(), 0.5F);
        this.addLayer(new LayerHeldItem(this));
        LayerBipedArmor layerbipedarmor = new LayerBipedArmor(this)
        {
            protected void initArmor()
            {
                this.modelLeggings = new ModelZombie(0.5F, true);
                this.modelArmor = new ModelZombie(1.0F, true);
            }
        };
        this.addLayer(layerbipedarmor);
        this.addLayer(new LayerSheepmanHeadband(this));
    }
    
	protected ResourceLocation getEntityTexture(EntityObsidianSheepman entity) {
		return obsidianSheepmanTextures;
	}
}