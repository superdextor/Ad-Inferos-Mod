package com.superdextor.adinferos.items;

import java.util.List;

import com.superdextor.thinkbigcore.items.ItemCustomArmor;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.MobEffects;
import net.minecraft.inventory.EntityEquipmentSlot;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class ItemWitherArmor extends ItemCustomArmor {

	public ItemWitherArmor(ArmorMaterial material, int renderIndex, EntityEquipmentSlot slot)
	{
		super(material, renderIndex, slot, "adinferos:textures/models/armor/wither");
	}
	
	public void onArmorTick(World world, EntityPlayer player, ItemStack armor) {
		player.removePotionEffect(MobEffects.WITHER);
	}
	
	@SideOnly(Side.CLIENT)
    public void addInformation(ItemStack stack, EntityPlayer playerIn, List toolTip, boolean advanced) 
	{
		toolTip.add(" ");
		toolTip.add("While Worn:");
	    toolTip.add("Wither Immunity");
	    super.addInformation(stack, playerIn, toolTip, advanced);
	}
	
}