package com.superdextor.adinferos.entity;

import com.google.common.base.Predicate;
import com.superdextor.adinferos.entity.monster.EntityObsidianSheepman;

import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.ai.EntityAINearestAttackableTarget;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.MobEffects;

public class EntityAIObsidianSheepmanTarget extends EntityAINearestAttackableTarget<EntityLivingBase>
{
    private final int targetChance = 10;
    private final EntityObsidianSheepman sheepMan;
	
    public EntityAIObsidianSheepmanTarget(EntityObsidianSheepman entity)
    {
        super(entity, EntityLivingBase.class, 10, true, false, new EntityAIObsidianSheepmanTarget.SheepmanPredicate(entity));
        this.sheepMan = entity;
    }
    
    static class SheepmanPredicate implements Predicate<EntityLivingBase> {
    	
    	private final EntityObsidianSheepman taskOwner;
    	
    	public SheepmanPredicate(EntityObsidianSheepman sheepMan) {
    		this.taskOwner = sheepMan;
		}

		public boolean apply(EntityLivingBase target) 
		{
			
			if(target.isPotionActive(MobEffects.LUCK)) {
				return false;
			}
			
			if(this.taskOwner.hasTribe()) {
				
				if(this.taskOwner.getTribe().isAlly(target)) {
					return false;
				}
				
				if(this.taskOwner.getTribe().isEntityBlackListed(target)) {
					return true;
				}
				
			}
			else if(target instanceof EntityPlayer) {
				return true;
			}
			
			return false;
		}
    }
    
    public void startExecuting()
    {
    	if(this.sheepMan.hasTribe() && this.sheepMan.getTribe().isEntityBlackListed(this.targetEntity)) {
    		this.sheepMan.doAngrySound();
    	}
        super.startExecuting();
    }
}