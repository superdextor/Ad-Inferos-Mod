package com.superdextor.adinferos.items;

import java.util.UUID;

import com.superdextor.adinferos.AdInferosTab;
import com.superdextor.adinferos.entity.monster.EntityObsidianSheepman.ObsidianSheepmenTribe;
import com.superdextor.thinkbigcore.items.ItemCustomArmor;

import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.SoundEvents;
import net.minecraft.inventory.EntityEquipmentSlot;
import net.minecraft.item.EnumDyeColor;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.world.World;
import net.minecraftforge.common.util.EnumHelper;

public class ItemTribeHeadband extends ItemCustomArmor {
	
	public ItemTribeHeadband() {
		super(EnumHelper.addArmorMaterial("TribeHeadbandMaterial", "adinferos:textures/models/armor/" , 14, new int[]{1,0,0,0}, 0, SoundEvents.ITEM_ARMOR_EQUIP_LEATHER, 0.0F), 0, EntityEquipmentSlot.HEAD, "adinferos:textures/models/armor/tribe_headband");
		this.setUnlocalizedName("tribe_headband");
		this.setCreativeTab(AdInferosTab.AI_COMBAT);
	}
	
	public String getArmorTexture(ItemStack stack, Entity entiy, EntityEquipmentSlot slot, String type)
	{
		
		if(type == "overlay") {
			return this.textures + "_overlay.png";
		}
		
		return this.textures + ".png";
	}
	
	public static void setTribeIn(ItemStack stack, ObsidianSheepmenTribe tribe) {
		NBTTagCompound tagCompound = stack.getTagCompound();
		
		if(!stack.hasTagCompound()) {
			tagCompound = new NBTTagCompound();
			stack.setTagCompound(tagCompound);
		}
		
		if(tribe == null) {
			tagCompound.removeTag("Tribe");
			tagCompound.removeTag("TribeUUID");
			tagCompound.removeTag("Color");
		}else {
			NBTTagCompound tribeData = new NBTTagCompound();
			tribe.writeToNBT(tribeData);
			tagCompound.setTag("Tribe", tribeData);
			tagCompound.setUniqueId("TribeUUID", tribe.getUUID());
			tagCompound.setInteger("Color", tribe.getColor().getMapColor().colorValue);
		}
	}
	
	public static ObsidianSheepmenTribe getTribe(ItemStack stack) {
		NBTTagCompound tagCompound = stack.getTagCompound();
		
		if(stack.hasTagCompound()) {
			return ObsidianSheepmenTribe.getTribeFromUUID(tagCompound.getUniqueId("TribeUUID"));
		}else {
			return null;
		}
	}
	
	public static UUID getTribeUUID(ItemStack stack) {
		NBTTagCompound tagCompound = stack.getTagCompound();
		
		if(stack.hasTagCompound()) {
			return tagCompound.getUniqueId("TribeUUID");
		}else {
			return new UUID(0,0);
		}
	}
	
	public void onArmorTick(World world, EntityPlayer player, ItemStack armor) {
		NBTTagCompound tagCompound = armor.getTagCompound();
		if(!world.isRemote && armor.hasTagCompound() && tagCompound.hasUniqueId("TribeUUID")) {
			ObsidianSheepmenTribe tribe = ObsidianSheepmenTribe.getTribeFromUUID(tagCompound.getUniqueId("TribeUUID"));
			
			if(tribe != null) {
				
				if(tribe.isDirty()) {
					tribe.initTribe(world);
				}
				
				if(!(tribe.theLeader() == player)) {
					tribe.setLeader(player);
				}
				
				if(!tribe.hasMembers()) {
					tribe.disband();
					tagCompound.removeTag("Tribe");
					tagCompound.removeTag("TribeUUID");
					tagCompound.removeTag("Color");
				}
			}
		}
	}
	
	public int getColor(ItemStack stack) {
		
		if(stack.hasTagCompound()) {
			NBTTagCompound tagCompound = stack.getTagCompound();
			
			if(tagCompound.hasKey("Color")) {
				return tagCompound.getInteger("Color");
			}
			
		}
		
		return -1;
	}
	
	public boolean updateItemStackNBT(NBTTagCompound tagCompound) {
		
		if(tagCompound != null && tagCompound.hasUniqueId("TribeUUID") && ObsidianSheepmenTribe.getTribeFromUUID(tagCompound.getUniqueId("TribeUUID")) == null) {
			ObsidianSheepmenTribe.createFromNBT(tagCompound.getUniqueId("TribeUUID"), null, tagCompound.getCompoundTag("Tribe"));
		}
		
		return true;
	}

}
