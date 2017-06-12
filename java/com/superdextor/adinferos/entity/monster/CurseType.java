package com.superdextor.adinferos.entity.monster;

import com.superdextor.adinferos.init.NetherBlocks;
import com.superdextor.adinferos.init.NetherItems;

import net.minecraft.block.state.IBlockState;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.util.text.TextComponentTranslation;

public enum CurseType
{
    FLAME("FlameCurse"),
    DECAY("DecayCurse"),
    VENOM("VenomCurse"),
    NAUSEA("NauseaCurse");

    private final TextComponentTranslation name;

    private CurseType(String rawname)
    {
        this.name = new TextComponentTranslation("entity." + rawname + ".name", new Object[0]);
    }

    public int getId()
    {
        return this.ordinal();
    }

    public static CurseType getByOrdinal(int ordinal)
    {
        return values()[ordinal];
    }
    
    public IBlockState getTrail() {
    	switch(this) {
    	
    	case FLAME:
    		return NetherBlocks.CURSE.getStateFromMeta(1);
    		
    	case DECAY:
    		return NetherBlocks.CURSE.getStateFromMeta(2);
    		
    	case VENOM:
    		return NetherBlocks.CURSE.getStateFromMeta(3);
    		
    	default:
    		return NetherBlocks.CURSE.getDefaultState();
    	}
    }
    
    public Item getDropItem() {
    	switch(this) {
	    	case FLAME:
	    		return Items.REDSTONE;
	    		
	    	case DECAY:
	    		return NetherItems.WITHER_DUST;
	    		
	    	case VENOM:
	    		return Items.SPIDER_EYE;
	    		
	    	default:
	    		return Items.GUNPOWDER;
	    }
    }
}