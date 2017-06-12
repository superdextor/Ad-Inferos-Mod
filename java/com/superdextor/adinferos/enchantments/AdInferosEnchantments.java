package com.superdextor.adinferos.enchantments;

import com.superdextor.adinferos.AdInferosReference;
import com.superdextor.adinferos.config.NetherConfig;
import com.superdextor.thinkbigcore.utility.EnchantmentBase;

import net.minecraft.enchantment.Enchantment;
import net.minecraft.util.ResourceLocation;

public class AdInferosEnchantments {
	
    public static final EnchantmentBase berserk = new EnchantmentBerserk();
    public static final EnchantmentBase curseProtection = new EnchantmentCurse();
    
    public static void register() {
    	String s = AdInferosReference.MOD_ID;
    	berserk.register(NetherConfig.enchIdBerserk, s);
    	curseProtection.register(NetherConfig.enchIdCurseProtection, s);
		NetherConfig.printDebugInfo("Registered Enchantments");
    }
}
