package com.superdextor.adinferos.items;

import com.superdextor.adinferos.world.WorldProviderAbyss;
import com.superdextor.thinkbigcore.items.ItemCustomArmor;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.MobEffects;
import net.minecraft.inventory.EntityEquipmentSlot;
import net.minecraft.item.ItemStack;
import net.minecraft.potion.PotionEffect;
import net.minecraft.world.World;

public class ItemGlowstoneArmor extends ItemCustomArmor {

	public ItemGlowstoneArmor(ArmorMaterial material, int renderIndex, EntityEquipmentSlot slot) {
		super(material, renderIndex, slot, "adinferos:textures/models/armor/glowstone", new PotionEffect(MobEffects.NIGHT_VISION, 14, 0, false, false), true);
	}
	
	public void onArmorTick(World world, EntityPlayer player, ItemStack armor) {

		if(!world.isRemote && !player.isPotionActive(MobEffects.BLINDNESS) && !(world.provider instanceof WorldProviderAbyss))
		{
			super.onArmorTick(world, player, armor);
		}
	}
}