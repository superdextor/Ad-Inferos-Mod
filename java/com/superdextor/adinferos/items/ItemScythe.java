package com.superdextor.adinferos.items;

import java.util.Random;

import com.google.common.collect.Multimap;
import com.superdextor.adinferos.AdInferosSounds;
import com.superdextor.adinferos.init.NetherItems;
import com.superdextor.adinferos.inventory.ExtractorRecipes;

import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.ai.attributes.AttributeModifier;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.init.MobEffects;
import net.minecraft.init.SoundEvents;
import net.minecraft.inventory.EntityEquipmentSlot;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.potion.PotionEffect;
import net.minecraft.util.ActionResult;
import net.minecraft.util.DamageSource;
import net.minecraft.util.EnumActionResult;
import net.minecraft.util.EnumHand;
import net.minecraft.util.EnumParticleTypes;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.world.World;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class ItemScythe extends Item
{

    public ItemScythe()
    {
    	this.setMaxStackSize(1);
        this.setMaxDamage(765);
    }

    public boolean hitEntity(ItemStack stack, EntityLivingBase target, EntityLivingBase attacker)
    {
    	if(target.getHealth() <= 0.0F) {
    		if(ExtractorRecipes.isValidEntity(target) && target.getRNG().nextFloat() < 0.08F) {
    			ItemSoul.createSoulFromEntity(stack, (EntityLiving) target);
    			attacker.worldObj.playSound(null, target.getPosition(), AdInferosSounds.ENTITY_GHOST_DEATH, SoundCategory.PLAYERS, 1.0F, 1.3F);
    		}
    	}
    	else {
            target.addPotionEffect(new PotionEffect(MobEffects.NAUSEA, 120, 0));
            target.addPotionEffect(new PotionEffect(MobEffects.BLINDNESS, 15, 0));
    	}
    	
        stack.damageItem(1, attacker);
        return true;
    }

    @SideOnly(Side.CLIENT)
    public boolean isFull3D()
    {
        return true;
    }

    public ActionResult<ItemStack> onItemRightClick(World worldIn, EntityPlayer playerIn, EnumHand hand) 
    {
    	ItemStack itemStackIn = playerIn.getHeldItem(hand);
    	this.tryTeleportPlayer(itemStackIn, worldIn, (EntityPlayer) playerIn);
    	playerIn.swingArm(hand);
    	
    	return new ActionResult(EnumActionResult.SUCCESS, itemStackIn);
    }

    public Multimap<String, AttributeModifier> getAttributeModifiers(EntityEquipmentSlot slot, ItemStack stack)
    {
        Multimap<String, AttributeModifier> multimap = super.getAttributeModifiers(slot, stack);

        if (slot == EntityEquipmentSlot.MAINHAND)
        {
            multimap.put(SharedMonsterAttributes.ATTACK_DAMAGE.getAttributeUnlocalizedName(), new AttributeModifier(ATTACK_DAMAGE_MODIFIER, "Weapon modifier", 10.0F, 0));
            multimap.put(SharedMonsterAttributes.ATTACK_SPEED.getAttributeUnlocalizedName(), new AttributeModifier(ATTACK_SPEED_MODIFIER, "Weapon modifier", -2.4000000953674316D, 0));
        }

        return multimap;
    }
    
    protected void tryTeleportPlayer(ItemStack stack, World world, EntityPlayer entityplayer) {
    	
    	BlockPos currentDestination = entityplayer.getPosition();
    	BlockPos destination = currentDestination;
    	
    	Random rand = entityplayer.getRNG();
    	int range = 16;
    	float x = currentDestination.getX();
    	float y = currentDestination.getY();
    	float z = currentDestination.getZ();
    	float xm = -MathHelper.sin(entityplayer.rotationYaw * 0.017453292F) * MathHelper.cos(entityplayer.rotationPitch * 0.017453292F);
    	float ym = -MathHelper.sin(entityplayer.rotationPitch * 0.017453292F);
    	float zm = MathHelper.cos(entityplayer.rotationYaw * 0.017453292F) * MathHelper.cos(entityplayer.rotationPitch * 0.017453292F);
    	        
    	for(; range > 0; range--) {
    		world.spawnParticle(EnumParticleTypes.DRAGON_BREATH, x, y + 1, z, 0.0D, 0.0D, 0.0D, new int[0]);
    		
    		if(world.getBlockState(new BlockPos(x, y + ym, z)).isOpaqueCube()) {
    			break;
    		}
    		y = y + ym;
    	            
    		if(world.getBlockState(new BlockPos(x + xm, y, z)).isOpaqueCube()) {
    			break;
    		}
    		x = x + xm;
    	            
    		if(world.getBlockState(new BlockPos(x, y, z + zm)).isOpaqueCube()) {
    			break;
    		}
    		z = z + zm;
    	}
    	        
    	destination = new BlockPos(x, y, z);
    			
    	entityplayer.getCooldownTracker().setCooldown(NetherItems.SCYTHE, entityplayer.capabilities.isCreativeMode ? 20 : 120);
    	
    	if(!world.isRemote) {
			stack.damageItem(8, entityplayer);
			world.playSound(null, destination, SoundEvents.ENTITY_ENDERMEN_TELEPORT, SoundCategory.PLAYERS, 0.9F, 1.0F);
	        if (entityplayer instanceof EntityPlayerMP)
	        {
	            EntityPlayerMP entityplayermp = (EntityPlayerMP)entityplayer;

	            if (entityplayermp.connection.getNetworkManager().isChannelOpen() && entityplayermp.worldObj == world && !entityplayermp.isPlayerSleeping())
	            {
	                net.minecraftforge.event.entity.living.EnderTeleportEvent event = new net.minecraftforge.event.entity.living.EnderTeleportEvent(entityplayermp, destination.getX(), destination.getY(), destination.getZ(), 0.0F);
	                if (!net.minecraftforge.common.MinecraftForge.EVENT_BUS.post(event))
	                {

	                	entityplayer.setPositionAndUpdate(event.getTargetX() + 0.5D, event.getTargetY(), event.getTargetZ() + 0.5D);
	                	entityplayer.fallDistance = 0.0F;
	                	entityplayer.attackEntityFrom(DamageSource.fall, event.getAttackDamage());
	                }
	            }
	        }
    	}
    }
    
    @SideOnly(Side.CLIENT)
    public boolean hasEffect(ItemStack stack) {
    	if(ItemSoul.hasSoul(stack) && itemRand.nextFloat() < 0.17F) {
        	return !super.hasEffect(stack);
    	}
    	else {
        	return super.hasEffect(stack);
    	}
    }
}