package com.superdextor.adinferos.items;

import java.util.List;
import java.util.Random;

import com.superdextor.thinkbigcore.items.ItemCustomArmor;

import net.minecraft.client.renderer.entity.layers.LayerBipedArmor;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.MobEffects;
import net.minecraft.inventory.EntityEquipmentSlot;
import net.minecraft.item.ItemStack;
import net.minecraft.potion.PotionEffect;
import net.minecraft.world.World;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class ItemNetheriteArmor extends ItemCustomArmor {

	public ItemNetheriteArmor(ArmorMaterial material, int renderIndex, EntityEquipmentSlot slot) {
		super(material, renderIndex, slot, "adinferos:textures/models/armor/netherite");
	}
	
	@SideOnly(Side.CLIENT)
    public void addInformation(ItemStack stack, EntityPlayer playerIn, List toolTip, boolean advanced) 
	{
		toolTip.add(" ");
	    toolTip.add("While Worn:");
	    toolTip.add("Agility");
	    super.addInformation(stack, playerIn, toolTip, advanced);
	}
	
}