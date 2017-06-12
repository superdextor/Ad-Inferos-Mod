package com.superdextor.adinferos.enchantments;

import com.superdextor.adinferos.NetherDamageSource;
import com.superdextor.adinferos.config.NetherConfig;
import com.superdextor.thinkbigcore.utility.EnchantmentBase;

import net.minecraft.enchantment.Enchantment;
import net.minecraft.enchantment.EnchantmentProtection;
import net.minecraft.inventory.EntityEquipmentSlot;
import net.minecraft.item.ItemArmor;
import net.minecraft.item.ItemStack;
import net.minecraft.util.DamageSource;
import net.minecraft.util.math.MathHelper;

public class EnchantmentCurse extends EnchantmentBase
{

    public EnchantmentCurse()
    {
        super("curse_protection", Enchantment.Rarity.UNCOMMON, null, new EntityEquipmentSlot[] {EntityEquipmentSlot.HEAD, EntityEquipmentSlot.CHEST, EntityEquipmentSlot.LEGS, EntityEquipmentSlot.FEET});
    }

    public int getMinEnchantability(int enchantmentLevel)
    {
        return enchantmentLevel * 10;
    }
    
    public int getMaxEnchantability(int enchantmentLevel)
    {
        return this.getMinEnchantability(enchantmentLevel) + 15;
    }

    public int getMaxLevel()
    {
        return NetherConfig.enchCurseProtectionMax;
    }

    public boolean canApplyTogether(Enchantment ench)
    {
        if (ench instanceof EnchantmentProtection)
        {
        	return false;
        }
        else
        {
            return super.canApplyTogether(ench);
        }
    }
    
    public int calcModifierDamage(int level, DamageSource source)
    {
        float f = (float)(6 + level * level) / 3.0F;
        return source == NetherDamageSource.CURSE ? MathHelper.floor_float(f * 0.75F) : super.calcModifierDamage(level, source);
    }
    
    public boolean canApplyAtEnchantingTable(ItemStack stack) {
    	if(stack != null && stack.getItem() instanceof ItemArmor) {
    		return true;
    	}
    	else {
    		return false;
    	}
    }
}