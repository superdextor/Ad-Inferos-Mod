package com.superdextor.adinferos.enchantments;

import java.util.Random;

import com.superdextor.adinferos.config.NetherConfig;
import com.superdextor.thinkbigcore.utility.EnchantmentBase;

import net.minecraft.enchantment.Enchantment;
import net.minecraft.enchantment.EnchantmentThorns;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.init.MobEffects;
import net.minecraft.init.SoundEvents;
import net.minecraft.inventory.EntityEquipmentSlot;
import net.minecraft.item.ItemStack;
import net.minecraft.item.ItemSword;
import net.minecraft.potion.PotionEffect;

public class EnchantmentBerserk extends EnchantmentBase {
	
	public EnchantmentBerserk() {
		super("berserk", Enchantment.Rarity.RARE, null, new EntityEquipmentSlot[] {EntityEquipmentSlot.HEAD, EntityEquipmentSlot.CHEST, EntityEquipmentSlot.LEGS, EntityEquipmentSlot.FEET});
	}
	
    public int getMinEnchantability(int enchantmentLevel)
    {
        return 10 + 20 * (enchantmentLevel - 1);
    }

    public int getMaxEnchantability(int enchantmentLevel)
    {
        return super.getMinEnchantability(enchantmentLevel) + 50;
    }

    public int getMaxLevel()
    {
        return NetherConfig.enchBerserkMax;
    }

    public void onUserHurt(EntityLivingBase entityliving, Entity entity, int lvl)
    {
        Random random = entityliving.getRNG();

        if (this.shouldActivate(lvl, random) && !entityliving.isPotionActive(MobEffects.STRENGTH))
        {
        	if(lvl > 1)
        	entityliving.addPotionEffect(new PotionEffect(MobEffects.STRENGTH, 120 + (lvl * 30), 0));
        	if(lvl < 2)
        	entityliving.addPotionEffect(new PotionEffect(MobEffects.SPEED, 120 + (lvl * 30), 0));
        	else
            entityliving.addPotionEffect(new PotionEffect(MobEffects.SPEED, 120 + (lvl * 30), 1));
        	entity.playSound(SoundEvents.ENTITY_ENDERMEN_SCREAM, 2.0F, 0.4F);
        }
    }

    private boolean shouldActivate(int lvl, Random random)
    {
        return lvl <= 0 ? false : random.nextFloat() < 0.12F * (float)lvl;
    }
    
    public boolean canApplyTogether(Enchantment ench)
    {
        if (ench instanceof EnchantmentThorns)
        {
        	return false;
        }
        else
        {
            return super.canApplyTogether(ench);
        }
    }
    
    public boolean canApplyAtEnchantingTable(ItemStack stack) {
    	if(stack != null && stack.getItem() instanceof ItemSword) {
    		return true;
    	}
    	else {
    		return false;
    	}
    }

}
