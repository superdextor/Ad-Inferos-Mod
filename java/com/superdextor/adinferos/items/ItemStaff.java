package com.superdextor.adinferos.items;

import com.superdextor.thinkbigcore.items.IEntityUsable;

import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.projectile.EntityLargeFireball;
import net.minecraft.entity.projectile.EntityWitherSkull;
import net.minecraft.init.SoundEvents;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ActionResult;
import net.minecraft.util.EnumActionResult;
import net.minecraft.util.EnumHand;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;

public class ItemStaff extends Item implements IEntityUsable
{
	
	private final int type;

    public ItemStaff(int type)
    {
    	this.setMaxStackSize(1);
        this.setMaxDamage(236);
        this.setFull3D();
        this.type = type;
    }

    public ActionResult<ItemStack> onItemRightClick(World worldIn, EntityPlayer playerIn, EnumHand hand) {
    	ItemStack itemStackIn = playerIn.getHeldItem(hand);
    	playerIn.getCooldownTracker().setCooldown(this, 10);
        this.shoot(worldIn, playerIn, itemStackIn, true);
        playerIn.swingArm(hand);
    	
        return new ActionResult(EnumActionResult.SUCCESS, itemStackIn);
    }
	
	protected void shoot(World worldIn, EntityLivingBase entityIn, ItemStack stack, boolean playSound) {
		stack.damageItem(1, entityIn);
		
		if(playSound)
		{
			if(this.type == 1)
			{
				worldIn.playSound(null, entityIn.getPosition(), SoundEvents.ENTITY_WITHER_SHOOT, entityIn.getSoundCategory(), 1.0F, 1.2F);
			}
			else
			{
				worldIn.playSound(null, entityIn.getPosition(), SoundEvents.ENTITY_GHAST_SHOOT, entityIn.getSoundCategory(), 1.0F, 0.8F);
			}
		}
		
		if(!worldIn.isRemote) {
			
			double x = 0;
			double y = 0;
			double z = 0;
            double d1 = 1.0D;
			
			if(entityIn instanceof EntityLiving && ((EntityLiving)entityIn).getAttackTarget() != null) {
				EntityLivingBase target = ((EntityLiving)entityIn).getAttackTarget();
                Vec3d vec3d = entityIn.getLook(1.0F);
                x = target.posX - (entityIn.posX + vec3d.xCoord * d1);
                y = target.getEntityBoundingBox().minY + (double)(target.height / 2.0F) - (0.5D + target.posY + (double)(target.height / 2.0F));
                z = target.posZ - (entityIn.posZ + vec3d.zCoord * d1);
			}
			else
			{
				Vec3d vec3d = entityIn.getLook(1.0F);
				x = vec3d.xCoord * d1;
				y = vec3d.yCoord * d1;
				z = vec3d.zCoord * d1;
			}
			
			if(this.type == 1) {
				EntityWitherSkull witherskull = new EntityWitherSkull(worldIn);
				witherskull.shootingEntity = entityIn;
				witherskull.accelerationX = x;
				witherskull.accelerationY = y;
				witherskull.accelerationZ = z;
				witherskull.posX = entityIn.posX + x * d1;
				witherskull.posY = entityIn.posY + (double)(entityIn.height / 2.0F) + 0.5D;
				witherskull.posZ = entityIn.posZ + z * d1;
				worldIn.spawnEntityInWorld(witherskull);
			}
			else
			{
				EntityLargeFireball largefireball = new EntityLargeFireball(worldIn);
				largefireball.shootingEntity = entityIn;
				largefireball.accelerationX = x;
				largefireball.accelerationY = y;
				largefireball.accelerationZ = z;
				largefireball.posX = entityIn.posX + x * d1;
				largefireball.posY = entityIn.posY + (double)(entityIn.height / 2.0F) + 0.5D;
				largefireball.posZ = entityIn.posZ + z * d1;
				largefireball.explosionPower = 3;
				worldIn.spawnEntityInWorld(largefireball);
			}
		}
	}
	
	public boolean onRangedAttack(EntityLiving entity, EntityLivingBase target, ItemStack stack, boolean playSFX) {
		
		this.shoot(entity.worldObj, entity, stack, playSFX);
		return true;
	}

	public boolean onMeleeAttack(EntityLiving entity, EntityLivingBase target, ItemStack stack) {
		return false;
	}
	
	public boolean isRangedWeapon() {
		return true;
	}
}