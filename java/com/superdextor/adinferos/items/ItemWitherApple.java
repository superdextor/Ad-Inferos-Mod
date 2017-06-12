package com.superdextor.adinferos.items;

import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.MobEffects;
import net.minecraft.item.ItemFood;
import net.minecraft.item.ItemStack;
import net.minecraft.potion.PotionEffect;
import net.minecraft.util.DamageSource;
import net.minecraft.world.World;

public class ItemWitherApple extends ItemFood {

	public ItemWitherApple() {
		super(5, 0.4F, false);
		this.setAlwaysEdible();
		this.setMaxStackSize(8);
	}
	
    protected void onFoodEaten(ItemStack stack, World worldIn, EntityPlayer player)
    {
    	boolean hurtConsumer = true;
    	boolean healConsumer = true;

    	for(ItemStack armor : player.getArmorInventoryList()) {
    		if(armor != null && armor.getItem() instanceof ItemWitherArmor) 
    		{
    			hurtConsumer = false;
    		}
    		else
    		{
    			healConsumer = false;
    		}
    	}
    	
    	if(healConsumer) {
    		player.heal(5.0F);
    	}
    	
    	if(hurtConsumer) {
    		player.addPotionEffect(new PotionEffect(MobEffects.WITHER, 640, 0));
    	    player.attackEntityFrom(DamageSource.wither, 3.0F);
    	}
    }

}
