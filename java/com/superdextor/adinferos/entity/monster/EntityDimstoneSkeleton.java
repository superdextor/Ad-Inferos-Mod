package com.superdextor.adinferos.entity.monster;

import javax.annotation.Nullable;

import com.superdextor.adinferos.config.NetherConfig;
import com.superdextor.adinferos.init.NetherItems;

import net.minecraft.entity.ai.EntityAIAttackRanged;
import net.minecraft.inventory.EntityEquipmentSlot;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.world.DifficultyInstance;
import net.minecraft.world.World;

public class EntityDimstoneSkeleton extends EntityNetherSkeleton {
	
    private EntityAIAttackRanged aiShootSkull = new EntityAIAttackRanged(this, 1.25D, 50, 10.0F);
	
	public EntityDimstoneSkeleton(World worldIn)
	{
		super(worldIn);
	}
	
    @Nullable
    protected Item getDropItem()
    {
    	return NetherItems.DIMSTONE_DUST;
    }
    
    protected void setEquipmentBasedOnDifficulty(DifficultyInstance difficulty)
    {
    	if(this.rand.nextBoolean())
    	{
    		this.setItemStackToSlot(EntityEquipmentSlot.MAINHAND, new ItemStack(NetherItems.WITHER_STAFF));
    	}
    	else
    	{
    		this.setItemStackToSlot(EntityEquipmentSlot.MAINHAND, new ItemStack(NetherItems.WITHER_SWORD));
    	}
    }
    
    public void setCombatTask()
    {
        this.tasks.removeTask(this.aiAttackOnCollide);
        this.tasks.removeTask(this.aiShootSkull);
        
        boolean ranged = false;
        ItemStack stack = this.getHeldItemMainhand();
        
        if(stack == null)
        {
        	ranged = false;
        }
        else {
        	if(stack.getItem() == NetherItems.WITHER_STAFF)
        	{
        		ranged = true;
        	}
        	else
        	{
        		ranged = false;
        	}
        }

        if (ranged)
        {
            this.tasks.addTask(4, this.aiShootSkull);
        }
        else
        {
            this.tasks.addTask(4, this.aiAttackOnCollide);
        }
        
        this.setUsingRange(ranged);
    }
    
	public String getTexture() {
		return "adinferos:textures/entity/glowstone_skeleton/dimstone.png";
	}
	
	public boolean isDarkfireResistant() {
		return true;
	}
	
	public boolean isWitherResistant() {
		return true;
	}
}
