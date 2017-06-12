package com.superdextor.adinferos.items;

import java.util.List;
import java.util.Map;

import com.google.common.collect.Maps;

import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.NonNullList;
import net.minecraft.util.text.translation.I18n;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class ItemSpawnerUpgrade extends Item {
	
	public ItemSpawnerUpgrade() {
		this.setHasSubtypes(true);
		this.setMaxStackSize(1);
	}
	
	public String getItemStackDisplayName(ItemStack stack) {
    	String s = ItemSpawnerUpgrade.UpgradeType.byItemStack(stack).getName();
    	
    	if(s == "empty") {
    		return super.getItemStackDisplayName(stack);
    	}
    	else {
    		return super.getItemStackDisplayName(stack) + " " + I18n.translateToLocal("upgrade." + s);
    	}
	}
	
    @SideOnly(Side.CLIENT)
	public void addInformation(ItemStack stack, EntityPlayer playerIn, List<String> tooltip, boolean advanced) {
    	String s = ItemSpawnerUpgrade.UpgradeType.byItemStack(stack).getDesc();
    	
    	if(!s.isEmpty()) {
    		tooltip.add(s);
    	}
	}
    
    @SideOnly(Side.CLIENT)
    public void getSubItems(Item itemIn, CreativeTabs tab, NonNullList<ItemStack> subItems)
    {
    	for (ItemSpawnerUpgrade.UpgradeType upgradeType : ItemSpawnerUpgrade.UpgradeType.values())
    	{
    		if(upgradeType.displayOnTab()) {
        		subItems.add(new ItemStack(this, 1, upgradeType.getMetadata()));
    		}
    	}
    }
    
    public static enum UpgradeType
    {
        EMPTY(0, "empty", 0),
        RADIUS1(1, "radius1", 1, "Increases the spawn radius by 2"),
        RADIUS2(2, "radius2", 2, "Increases the spawn radius by 3"),
        RADIUS3(3, "radius3", 3, "Increases the spawn radius by 5", true),
        SPEED1(4, "speed1", 1, "Decreases the spawn delay by 2 seconds"),
        SPEED2(5, "speed2", 2, "Decreases the spawn delay by 4 seconds"),
        SPEED3(6, "speed3", 3, "Decreases the spawn delay by 6 seconds"),
        SPEED4(7, "speed4", 4, "Decreases the spawn delay by 8 seconds"),
        SPEED5(8, "speed5", 5, "Decreases the spawn delay by 10 seconds", true),
        IGNORESPAWNRULES(9, "ignorespawnrules", 0, "Ignores spawn rules (brightness, ground-block, ect..)", true),
        BLOODFILTER1(10, "bloodfilter1", 1, "Decreases blood use by 15%"),
        BLOODFILTER2(11, "bloodfilter2", 2, "Decreases blood use by 20%"),
        BLOODFILTER3(12, "bloodfilter3", 3, "Decreases blood use by 30%"),
        BLOODFILTER4(13, "bloodfilter4", 4, "Decreases blood use by 45%"),
        BLOODFILTER5(14, "bloodfilter5", 5, "Decreases blood use by 60%", true),
        SPAWNCOUNT1(15, "spawncount1", 1, "Increases spawn count by 1"),
        SPAWNCOUNT2(16, "spawncount2", 2, "Increases spawn count by 2"),
        SPAWNCOUNT3(17, "spawncount3", 3, "Increases spawn count by 3", true),
        MAXCOUNT1(18, "maxcount1", 1, "Increases max nearby entities by 2"),
        MAXCOUNT2(19, "maxcount2", 2, "Increases max nearby entities by 3"),
        MAXCOUNT3(20, "maxcount3", 3, "Increases max nearby entities by 4", true);

        private static final Map<Integer, ItemSpawnerUpgrade.UpgradeType> META_LOOKUP = Maps.<Integer, ItemSpawnerUpgrade.UpgradeType>newHashMap();
        private final int meta;
        private final String name;
        private final int tier;
        private final String desc;
        private final boolean showOnCreative;

        private UpgradeType(int metaIn, String nameIn, int tierIn)
        {
        	this(metaIn, nameIn, tierIn, "");
        }
        
        private UpgradeType(int metaIn, String nameIn, int tierIn, String descIn)
        {
        	this(metaIn, nameIn, tierIn, descIn, false);
        }
        
        private UpgradeType(int metaIn, String nameIn, int tierIn, String descIn, boolean showOnCreativeIn)
        {
            this.meta = metaIn;
            this.name = nameIn;
            this.tier = tierIn;
            this.desc = descIn;
            this.showOnCreative = showOnCreativeIn;
        }

        /**
         * Gets the item damage value on an ItemStack that represents this fish type
         */
        public int getMetadata()
        {
            return this.meta;
        }

        public String getName() {
        	return this.name;
        }
        
        public int getTier() {
        	return this.tier;
        }
        
        public String getDesc() {
        	return this.desc;
        }
        
        public boolean displayOnTab() {
        	return this.showOnCreative;
        }

        /**
         * Gets the corresponding FishType value for the given item damage value of an ItemStack, defaulting to COD for
         * unrecognized damage values.
         */
        public static ItemSpawnerUpgrade.UpgradeType byMetadata(int meta)
        {
        	ItemSpawnerUpgrade.UpgradeType itemfishfood$fishtype = (ItemSpawnerUpgrade.UpgradeType)META_LOOKUP.get(Integer.valueOf(meta));
            return itemfishfood$fishtype == null ? EMPTY : itemfishfood$fishtype;
        }

        /**
         * Gets the FishType that corresponds to the given ItemStack, defaulting to COD if the given ItemStack does not
         * actually contain a fish.
         */
        public static ItemSpawnerUpgrade.UpgradeType byItemStack(ItemStack stack)
        {
            return stack.getItem() instanceof ItemSpawnerUpgrade ? byMetadata(stack.getMetadata()) : EMPTY;
        }

        static
        {
            for (ItemSpawnerUpgrade.UpgradeType itemfishfood$fishtype : values())
            {
                META_LOOKUP.put(Integer.valueOf(itemfishfood$fishtype.getMetadata()), itemfishfood$fishtype);
            }
        }
    }

}
