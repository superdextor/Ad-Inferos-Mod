package com.superdextor.adinferos;

import com.superdextor.adinferos.entity.other.EntityNetherArrow;

import net.minecraft.entity.Entity;
import net.minecraft.util.DamageSource;
import net.minecraft.util.EntityDamageSourceIndirect;

public class NetherDamageSource {
	
    public final static DamageSource ACID = (new DamageSource("acid")).setDamageBypassesArmor();
    public final static DamageSource CURSE = (new DamageSource("curse")).setDamageBypassesArmor();
    public final static DamageSource SACRIFICE = (new DamageSource("sacrifice")).setDamageBypassesArmor().setMagicDamage();
    public final static DamageSource DARK_FIRE = (new DamageSource("dark_fire"));
    public final static DamageSource SPIKES = (new DamageSource("spikes"));
    
    public static DamageSource causeArrowDamage(EntityNetherArrow arrow, Entity entity)
    {
        return (new EntityDamageSourceIndirect("nether_arrow", arrow, entity)).setProjectile();
    }

}
