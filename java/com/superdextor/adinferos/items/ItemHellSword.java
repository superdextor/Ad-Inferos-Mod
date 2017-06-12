package com.superdextor.adinferos.items;

import com.superdextor.adinferos.AdInferosSounds;
import com.superdextor.adinferos.NetherDamageSource;
import com.superdextor.adinferos.init.NetherItems;
import com.superdextor.adinferos.inventory.ExtractorRecipes;
import com.superdextor.adinferos.network.SMessageHellSwordAttack;
import com.superdextor.thinkbigcore.entity.EntityCustomThrowable;
import com.superdextor.thinkbigcore.items.ItemCustomSword;
import com.superdextor.thinkbigcore.network.TBCNetwork;

import net.minecraft.client.Minecraft;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.ai.attributes.AttributeModifier;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.init.MobEffects;
import net.minecraft.init.SoundEvents;
import net.minecraft.inventory.EntityEquipmentSlot;
import net.minecraft.item.EnumAction;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.potion.PotionEffect;
import net.minecraft.util.ActionResult;
import net.minecraft.util.EnumActionResult;
import net.minecraft.util.EnumHand;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.RayTraceResult;
import net.minecraft.world.World;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class ItemHellSword extends ItemCustomSword {
	
	public ItemHellSword(ToolMaterial material) {
		super(material);
	}
	
	public ItemHellSword(ToolMaterial material, PotionEffect potionEffectIn) {
		super(material, potionEffectIn);
	}
	
    public boolean hitEntity(ItemStack stack, EntityLivingBase target, EntityLivingBase attacker)
    {
    	if(this.getMaterial().equals(NetherItems.QuartzToolMaterial)) {
    		target.motionY =+ 0.5;
    	}
    	
    	if(ExtractorRecipes.isValidEntity(target) && this.getMaterial().equals(NetherItems.NetheriteToolMaterial) && target.getHealth() <= 0.0F && target.getRNG().nextFloat() < 0.05F) {
    		ItemSoul.createSoulFromEntity(stack, (EntityLiving) target);
			attacker.worldObj.playSound(null, target.getPosition(), AdInferosSounds.ENTITY_GHOST_DEATH, SoundCategory.PLAYERS, 1.0F, 1.3F);
    	}
    	
    	if(this.potionEffect != null) {
    		target.addPotionEffect(new PotionEffect(this.potionEffect));
    	}
    	    stack.damageItem(1, attacker);
    		return true;
    }
    
    public void onUpdate(ItemStack stack, World worldIn, Entity entityIn, int itemSlot, boolean isSelected) {
    	
    	if(!stack.hasTagCompound()) {
    		stack.setTagCompound(new NBTTagCompound());
    	}
    	
    	if(isInHell(stack) != entityIn.worldObj.provider.doesWaterVaporize()) {
    		
        	stack.getTagCompound().setBoolean("InHell", entityIn.worldObj.provider.doesWaterVaporize());
        	
        	
        	if(entityIn.worldObj.provider.doesWaterVaporize()) {
            	stack.addAttributeModifier(SharedMonsterAttributes.ATTACK_DAMAGE.getAttributeUnlocalizedName(), new AttributeModifier(ATTACK_DAMAGE_MODIFIER, "Weapon modifier", (double)(this.getMaterial().getDamageVsEntity() + 5.0F), 0), EntityEquipmentSlot.MAINHAND);
            	stack.addAttributeModifier(SharedMonsterAttributes.ATTACK_SPEED.getAttributeUnlocalizedName(), new AttributeModifier(ATTACK_SPEED_MODIFIER, "Weapon modifier", -2.4000000953674316D, 0), EntityEquipmentSlot.MAINHAND);
        	}
        	else {
        		stack.getTagCompound().removeTag("AttributeModifiers");
        	}
    	}
    }
    
    public EnumAction getItemUseAction(ItemStack stack) {
    	
    	if(this.getMaterial().equals(NetherItems.GlowstoneToolMaterial)) {
    		return EnumAction.BOW;
    	}
    	
    	if(this.getMaterial().equals(NetherItems.ObsidianToolMaterial)) {
    		return EnumAction.BLOCK;
    	}
    	
    	return super.getItemUseAction(stack);
    }
    
    public int getMaxItemUseDuration(ItemStack stack) {
    	
    	if(this.getMaterial().equals(NetherItems.GlowstoneToolMaterial) || this.getMaterial().equals(NetherItems.ObsidianToolMaterial)) {
    		return 72000;
    	}
    	
    	return super.getMaxItemUseDuration(stack);
    }
    
    public ActionResult<ItemStack> onItemRightClick(World worldIn, EntityPlayer playerIn, EnumHand hand) {
    	ItemStack itemStackIn = playerIn.getHeldItem(hand);
    	boolean inHell = isInHell(itemStackIn);
    	boolean hasSheild = false;
    	
    	if(playerIn.getHeldItemOffhand() != null && playerIn.getHeldItemOffhand().getItem().equals(Items.SHIELD)) {
    		hasSheild = true;
    	}
    	
    	if(playerIn.getHeldItemMainhand() != null && playerIn.getHeldItemMainhand().getItem().equals(Items.SHIELD)) {
    		hasSheild = true;
    	}
    	
    	if(this.getMaterial().equals(NetherItems.QuartzToolMaterial) || this.getMaterial().equals(NetherItems.WitherToolMaterial)) {
    		playerIn.swingArm(hand);
    		playerIn.getCooldownTracker().setCooldown(this, inHell ? 40 : 60);
    		
    		if(worldIn.isRemote) {
        		Minecraft mc = Minecraft.getMinecraft();
        		  
        		if (mc.objectMouseOver != null && mc.objectMouseOver.typeOfHit == RayTraceResult.Type.ENTITY && mc.objectMouseOver.entityHit != null && mc.objectMouseOver.entityHit instanceof EntityLivingBase) {
            		playerIn.getCooldownTracker().setCooldown(this, inHell ? 160 : 200);
            		int attackType = this.getMaterial().equals(NetherItems.QuartzToolMaterial) ? 0 : 1;
            		if(attackType == 0) {
                		playerIn.playSound(SoundEvents.ENTITY_ELDER_GUARDIAN_CURSE, 1.0F, 1.3F);
            		}
            		else {
                		playerIn.playSound(SoundEvents.ENTITY_WITHER_AMBIENT, 1.0F, 0.6F);
            		}
        			TBCNetwork.sendToServer(new SMessageHellSwordAttack(playerIn.getUniqueID(), mc.objectMouseOver.entityHit.getUniqueID(), isInHell(itemStackIn), attackType));
        		}
    		}
    		
	        return new ActionResult(EnumActionResult.SUCCESS, itemStackIn);
    	}
    	
    	if((this.getMaterial().equals(NetherItems.GlowstoneToolMaterial) || this.getMaterial().equals(NetherItems.ObsidianToolMaterial)) && !hasSheild) {
    		playerIn.setActiveHand(hand);
            return new ActionResult(EnumActionResult.SUCCESS, itemStackIn);
    	}
    	
    	return super.onItemRightClick(worldIn, playerIn, hand);
    }
    
    public void onUsingTick(ItemStack stack, EntityLivingBase player, int count) {
    	
    	if(this.getMaterial().equals(NetherItems.GlowstoneToolMaterial)) {
        	if(count % 4 == 0) 
        	{
    	    	World world = player.worldObj;
                EntityCustomThrowable flame = new EntityCustomThrowable(world, player);
    	    	
    	        if(!world.isRemote) {
    	        	
    	            flame.setItem(new ItemStack(Items.BLAZE_POWDER));
    	            flame.setHeadingFromThrower(player, player.rotationPitch, player.rotationYaw, 0.0F, 1.5F, 1.0F);
    	            flame.setFireTime(4);
    	            flame.setDamageSource(NetherDamageSource.CURSE);
    	            
    	            if(player.getRNG().nextFloat() < 0.4F) {
    	            	flame.setBlock(Blocks.FIRE.getDefaultState());
    	            }
    	            
    	            world.spawnEntityInWorld(flame);
    	        }
    	    	
    	        player.playSound(SoundEvents.ENTITY_BLAZE_SHOOT, 0.6F, 1.0F);
    		    stack.damageItem(1, player);
        	}
    	}
    	
    	if(this.getMaterial().equals(NetherItems.ObsidianToolMaterial)) {
    		player.addPotionEffect(new PotionEffect(MobEffects.RESISTANCE, 20, 0, false, false));
    	}
    	
    }
    
    @SideOnly(Side.CLIENT)
    public boolean hasEffect(ItemStack stack) {
    	
    	boolean value = isInHell(stack) ? true : super.hasEffect(stack);
    	
    	if(ItemSoul.hasSoul(stack) && itemRand.nextFloat() < 0.17F) {
        	return !value;
    	}
    	else {
        	return value;
    	}
    }
    
    public static boolean isInHell(ItemStack stack) {
    	if(!stack.hasTagCompound() || !stack.getTagCompound().hasKey("InHell")) {
    		return false;
    	}
    	
    	return stack.getTagCompound().getBoolean("InHell");
    }

    public boolean isRangedWeapon() {
    	
    	if(this.getMaterial().equals(NetherItems.GlowstoneToolMaterial)) {
    		return true;
    	}
    	
    	return super.isRangedWeapon();
    }
    
    public boolean onRangedAttack(EntityLiving entity, EntityLivingBase target, ItemStack stack, boolean playSFX) {
    	
    	if(this.getMaterial().equals(NetherItems.GlowstoneToolMaterial)) {
    		World world = entity.worldObj;
            EntityCustomThrowable flame = new EntityCustomThrowable(world, entity);
            flame.setItem(new ItemStack(Items.BLAZE_POWDER));
            double d0 = target.posY + (double)target.getEyeHeight() - 2.100000023841858D;
            double d1 = target.posX - entity.posX;
            double d2 = d0 - flame.posY;
            double d3 = target.posZ - entity.posZ;
            flame.setDamage(2.0F);
            flame.setFireTime(3);
            flame.setDamageSource(NetherDamageSource.CURSE);
            
            if(entity.getRNG().nextFloat() < 0.4F) {
            	flame.setBlock(Blocks.FIRE.getDefaultState());
            }
            
            float f1 = MathHelper.sqrt_double(d1 * d1 + d3 * d3) * 0.2F;
            flame.setThrowableHeading(d1, d2 + (double)f1, d3, 1.6F, 6.0F);
            if(playSFX) {
            	entity.playSound(SoundEvents.ENTITY_BLAZE_SHOOT, 0.6F, 1.0F);
            }
            	
            world.spawnEntityInWorld(flame);
            
            return true;
    	}
    	
    	return super.onRangedAttack(entity, target, stack, playSFX);
    }
    
    public boolean onMeleeAttack(EntityLiving entity, EntityLivingBase target, ItemStack stack) {
    	
    	if(this.getMaterial().equals(NetherItems.QuartzToolMaterial)) {
    		target.motionY =+ 0.7;
    	}
    	
    	return super.onMeleeAttack(entity, target, stack);
    }

}
