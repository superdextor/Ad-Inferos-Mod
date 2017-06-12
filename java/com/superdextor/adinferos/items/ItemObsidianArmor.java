package com.superdextor.adinferos.items;

import java.util.List;

import com.superdextor.thinkbigcore.items.ItemCustomArmor;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.EntityEquipmentSlot;
import net.minecraft.item.ItemStack;
import net.minecraft.util.text.TextFormatting;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class ItemObsidianArmor extends ItemCustomArmor {
	
	public ItemObsidianArmor(ArmorMaterial material, int renderIndex, EntityEquipmentSlot slot) {
		super(material, renderIndex, slot, "adinferos:textures/models/armor/obsidian");
	}
	
	@SideOnly(Side.CLIENT)
    public void addInformation(ItemStack stack, EntityPlayer playerIn, List toolTip, boolean advanced) 
	{
		toolTip.add(" ");
		toolTip.add("While Worn:");
		toolTip.add(TextFormatting.RED + "Heavy");
	    super.addInformation(stack, playerIn, toolTip, advanced);
	}

}
