package com.superdextor.adinferos.items;

import com.superdextor.adinferos.entity.other.EntityNetherArrow;
import com.superdextor.thinkbigcore.items.ItemCustomBow;

import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.projectile.EntityArrow;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;

public class ItemNetherBow extends ItemCustomBow
{
	private final int type;

    public ItemNetherBow(String modelName, int durability, Item ammo, int drawbackSpeed, double damage, float speed, int zoomDelay, float maxZoom, int type)
    {
    	super(modelName, durability, ammo, drawbackSpeed, damage, speed, zoomDelay, maxZoom);
    	this.type = type;
    }

    protected EntityArrow createArrow(World world, ItemStack stack, EntityLivingBase shooter, float pull) {
    	EntityNetherArrow entityarrow = new EntityNetherArrow(world, shooter);
        entityarrow.setArrowType(this.type);
        
    	return entityarrow;
    }
}