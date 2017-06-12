package com.superdextor.adinferos;

import com.superdextor.adinferos.enchantments.AdInferosEnchantments;
import com.superdextor.adinferos.init.NetherBlocks;
import com.superdextor.adinferos.init.NetherItems;

import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.enchantment.EnchantmentData;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;
import net.minecraft.util.NonNullList;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;


public abstract class AdInferosTab extends CreativeTabs {
	
	public static final AdInferosTab AI_BUILDING = new AdInferosTab("tabAIBlocks")
	{
        @SideOnly(Side.CLIENT)
		public ItemStack getTabIconItem() {
			return new ItemStack(NetherBlocks.LOG);
		}
	};
	public static final AdInferosTab AI_COMBAT = new AdInferosTab("tabAICombat")
	{
        @SideOnly(Side.CLIENT)
		public ItemStack getTabIconItem() {
			return new ItemStack(NetherItems.SCYTHE);
		}
		
		@SideOnly(Side.CLIENT)
	    public void displayAllRelevantItems(NonNullList<ItemStack> itemList)
	    {
	    	super.displayAllRelevantItems(itemList);
	        itemList.add(Items.ENCHANTED_BOOK.getEnchantedItemStack(new EnchantmentData(AdInferosEnchantments.berserk, AdInferosEnchantments.berserk.getMaxLevel())));
	        itemList.add(Items.ENCHANTED_BOOK.getEnchantedItemStack(new EnchantmentData(AdInferosEnchantments.curseProtection, AdInferosEnchantments.curseProtection.getMaxLevel())));
	    }
	};
	
	public static final AdInferosTab AI_TOOLS = new AdInferosTab("tabAITools")
	{
        @SideOnly(Side.CLIENT)
		public ItemStack getTabIconItem() {
			return new ItemStack(NetherItems.NETHERITE_PICKAXE);
		}
		
		@SideOnly(Side.CLIENT)
	    public void displayAllRelevantItems(NonNullList<ItemStack> itemList)
	    {
	    	super.displayAllRelevantItems(itemList);
	    	itemList.add(new ItemStack(NetherBlocks.HIDDEN_LIGHT));
	    	itemList.add(new ItemStack(NetherBlocks.HIDDEN_BLOCK));
	    }
		
	};
	public static final AdInferosTab AI_FOOD = new AdInferosTab("tabAIFood")
	{
        @SideOnly(Side.CLIENT)
		public ItemStack getTabIconItem() {
			return new ItemStack(NetherItems.INFERNAL_WHEAT);
		}
	};
	public static final AdInferosTab AI_MOBS = new AdInferosTab("tabAIMobs")
	{
        @SideOnly(Side.CLIENT)
		public ItemStack getTabIconItem() {
			return new ItemStack(NetherItems.SPAWN_EGG);
		}
	};
	public static final AdInferosTab AI_OTHER = new AdInferosTab("tabAIOther")
	{
        @SideOnly(Side.CLIENT)
		public ItemStack getTabIconItem() {
			return new ItemStack(NetherItems.AVIS_WING);
		}
	};

	public AdInferosTab(String label) {
		super(label);
		this.setBackgroundImageName("adinferos.png");
	}
}
