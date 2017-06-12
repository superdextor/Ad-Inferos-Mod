package com.superdextor.adinferos.items;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import com.google.common.collect.Maps;
import com.superdextor.adinferos.AdInferosTab;
import com.superdextor.adinferos.config.NetherConfig;
import com.superdextor.adinferos.init.NetherItems;
import com.superdextor.thinkbigcore.helpers.InventoryHelper;

import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.MobEffects;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.potion.PotionEffect;
import net.minecraft.util.ActionResult;
import net.minecraft.util.EnumActionResult;
import net.minecraft.util.EnumHand;
import net.minecraft.util.NonNullList;
import net.minecraft.world.World;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class ItemAmulet extends Item {
	
	public ItemAmulet() {
		this.setMaxStackSize(1);
        this.setHasSubtypes(true);
        this.setUnlocalizedName("amulet");
        this.setCreativeTab(AdInferosTab.AI_COMBAT);
	}
	
    public String getUnlocalizedName(ItemStack stack)
    {
    	ItemAmulet.Type type = ItemAmulet.Type.byItemStack(stack);
        return this.getUnlocalizedName() + "." + type.getUnlocalizedName();
    }
    
    @SideOnly(Side.CLIENT)
    public void getSubItems(Item itemIn, CreativeTabs tab, NonNullList<ItemStack> subItems)
    {
    	ItemAmulet.Type[] afishtype = ItemAmulet.Type.values();
        int i = afishtype.length;

        for (int j = 0; j < i; ++j)
        {
        	ItemAmulet.Type type = afishtype[j];
            subItems.add(new ItemStack(this, 1, type.getMetadata()));
        }
    }
    
    public void onUpdate(ItemStack stack, World worldIn, Entity entityIn, int itemSlot, boolean isSelected) {
    	
    	if(!stack.hasTagCompound()) {
    		stack.setTagCompound(new NBTTagCompound());
    	}
    	
    	boolean isHeld = isSelected;
    	
    	if(entityIn instanceof EntityLivingBase && ((EntityLivingBase)entityIn).getHeldItemOffhand() == stack) {
    		isHeld = ((EntityLivingBase)entityIn).getHeldItemMainhand() == null || !(((EntityLivingBase)entityIn).getHeldItemMainhand().getItem() instanceof ItemAmulet);
    	}
    	
    	if(NetherConfig.amuletEffects && isHeld && entityIn instanceof EntityPlayer) {
    		
    		stack.getTagCompound().setBoolean("IsActive", true);
    		
            switch (stack.getMetadata())
            {
                case 0: {
                    if(entityIn instanceof EntityLivingBase && entityIn.isBurning()) {
                    	((EntityLivingBase) entityIn).addPotionEffect(new PotionEffect(MobEffects.FIRE_RESISTANCE, 30, 1, false, false));
                    	entityIn.extinguish();
                    }
                    break;
                }
                case 1: {
                	if(entityIn instanceof EntityLivingBase)
                	((EntityLivingBase) entityIn).removePotionEffect(MobEffects.WITHER);
                    break;
                }
                case 2: {
                	//fall is handled by NetherEvents
            		break;
                }
                case 3: {
                    if(entityIn instanceof EntityLivingBase) {
                    	EntityLivingBase entitylivingbase = (EntityLivingBase) entityIn; 
                    	if(entitylivingbase.getHealth() <= 4.0F && !entitylivingbase.isPotionActive(MobEffects.REGENERATION)) {
                    		entitylivingbase.addPotionEffect(new PotionEffect(MobEffects.REGENERATION, 149, 0));
                    	}
                    }
                }
            }
    	}
    	else if(!isHeld) {
    		stack.getTagCompound().setBoolean("IsActive", false);
    	}
    }
    
    public int getColor(ItemStack stack) {
    	
    	ItemAmulet.Type type = ItemAmulet.Type.byItemStack(stack);
        return type.getColor();
    }
    
    public boolean hasEffect(ItemStack stack) {
    	
    	if(stack.hasTagCompound()) {
    		return stack.getTagCompound().getBoolean("IsActive");
    	}
    	
    	return super.hasEffect(stack);
    }
    
    public static boolean isActive(ItemStack stack) {
    	if(!stack.hasTagCompound()) {
    		return false;
    	}
    	
    	return stack.getTagCompound().getBoolean("IsActive");
    }
    
    public static enum Type
    {
        FIRE(0, "fire", 0xF0A930),
        WITHER(1, "wither", 0x595763),
        FALL(2, "fall", 0xE8DFD1),
        HEAL(3, "heal", 0xE80909),
        ACID(4, "acid", 0xBB20E6);
        /** Maps an item damage value for an ItemStack to the corresponding Type value. */
        private static final Map META_LOOKUP = Maps.newHashMap();
        /** The item damage value on an ItemStack that represents this fish type */
        private final int meta;
        private final int color;
        /**
         * The value that this fish type uses to replace "XYZ" in: "fish.XYZ.raw" / "fish.XYZ.cooked" for the
         * unlocalized name and "fish_XYZ_raw" / "fish_XYZ_cooked" for the icon string.
         */
        private final String unlocalizedName;

        private Type(int meta, String unlocalizedName, int colorIn)
        {
            this.meta = meta;
            this.unlocalizedName = unlocalizedName;
            this.color = colorIn;
        }

        /**
         * Gets the item damage value on an ItemStack that represents this fish type
         */
        public int getMetadata()
        {
            return this.meta;
        }

        /**
         * Gets the value that this fish type uses to replace "XYZ" in: "fish.XYZ.raw" / "fish.XYZ.cooked" for the
         * unlocalized name and "fish_XYZ_raw" / "fish_XYZ_cooked" for the icon string.
         */
        public String getUnlocalizedName()
        {
            return this.unlocalizedName;
        }
        
        public int getColor() {
        	return this.color;
        }

        /**
         * Gets the corresponding Type value for the given item damage value of an ItemStack, defaulting to COD for
         * unrecognized damage values.
         */
        public static ItemAmulet.Type byMetadata(int meta)
        {
        	ItemAmulet.Type Type = (ItemAmulet.Type)META_LOOKUP.get(Integer.valueOf(meta));
            return Type == null ? FIRE : Type;
        }

        /**
         * Gets the Type that corresponds to the given ItemStack, defaulting to COD if the given ItemStack does not
         * actually contain a fish.
         */
        public static ItemAmulet.Type byItemStack(ItemStack stack)
        {
            return stack.getItem() instanceof ItemAmulet ? byMetadata(stack.getMetadata()) : FIRE;
        }

        static
        {
        	ItemAmulet.Type[] var0 = values();
            int var1 = var0.length;

            for (int var2 = 0; var2 < var1; ++var2)
            {
            	ItemAmulet.Type var3 = var0[var2];
                META_LOOKUP.put(Integer.valueOf(var3.getMetadata()), var3);
            }
        }
    }

}
