package com.superdextor.adinferos.items;

import java.util.List;

import com.superdextor.adinferos.init.NetherItems;

import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.MobEffects;
import net.minecraft.item.ItemBucketMilk;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.potion.PotionEffect;
import net.minecraft.util.ActionResult;
import net.minecraft.util.EnumActionResult;
import net.minecraft.util.EnumHand;
import net.minecraft.world.World;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class ItemBloodBucket extends ItemBucketMilk {

	public ItemBloodBucket() {
        this.setHasSubtypes(true);
	}
	
    public ItemStack onItemUseFinish(ItemStack stack, World worldIn, EntityLivingBase entityLiving)
    {
    	if(stack.getMetadata() > 0) {
    		return stack;
    	}
    	
        if (entityLiving instanceof EntityPlayer && !((EntityPlayer)entityLiving).capabilities.isCreativeMode)
        {
            stack.func_190918_g(1);
        }

        if(hasBlood(stack)) {
        	int d = getBlood(stack);
        	int a = d / 200;
        	
        	if(d > 8000) {
        		d = 8000;
        	}
        	
        	if(a > 4) {
        		a = 4;
        	}
        	
        	entityLiving.addPotionEffect(new PotionEffect(MobEffects.HEALTH_BOOST, d * 3, a));
        }

        return stack.func_190916_E() <= 0 ? new ItemStack(NetherItems.GOLDEN_BUCKET) : stack;
    }
    
    public ActionResult<ItemStack> onItemRightClick(World worldIn, EntityPlayer playerIn, EnumHand hand)
    {
        ItemStack itemstack = playerIn.getHeldItem(hand);
    	if(itemstack.getMetadata() > 0 || hand == EnumHand.OFF_HAND) {
            return new ActionResult(EnumActionResult.PASS, itemstack);
    	}
    	
        playerIn.setActiveHand(hand);
        return new ActionResult(EnumActionResult.SUCCESS, itemstack);
    }
    
    @SideOnly(Side.CLIENT)
    public void addInformation(ItemStack stack, EntityPlayer playerIn, List<String> tooltip, boolean advanced) {
    	
    	String count = "???";
    	
    	if(stack.hasTagCompound() && stack.getTagCompound().hasKey("Blood")) {
    		count = getBlood(stack) + "";
    	}
    	
    	tooltip.add("Blood: " + count);
    	super.addInformation(stack, playerIn, tooltip, advanced);
    }
    
    public void onUpdate(ItemStack stack, World worldIn, Entity entityIn, int itemSlot, boolean isSelected) {
    	if(!worldIn.isRemote && (!stack.hasTagCompound() || !stack.getTagCompound().hasKey("Blood")) && entityIn instanceof EntityPlayer) {
    		setBlood(stack, 79 + itemRand.nextInt(82));
    	}
    }
    
    public static boolean hasBlood(ItemStack stack) {
    	return getBlood(stack) > 0;
    }
    
    public static int getBlood(ItemStack stack) {
    	if(stack.func_190926_b() || !stack.hasTagCompound() || !stack.getTagCompound().hasKey("Blood")) {
    		return 0;
    	}
    	
    	return stack.getTagCompound().getInteger("Blood");
    }
    
    public static void addBlood(ItemStack stack, int value) {
    	if(!stack.hasTagCompound()) {
    		stack.setTagCompound(new NBTTagCompound());
    	}
    	
    	int currentAmount = stack.getTagCompound().getInteger("Blood");
    	stack.getTagCompound().setInteger("Blood", currentAmount + value);
    }
    
    public static void setBlood(ItemStack stack, int value) {
    	if(!stack.hasTagCompound()) {
    		stack.setTagCompound(new NBTTagCompound());
    	}
    	
    	stack.getTagCompound().setInteger("Blood", value);
    }
	
}
