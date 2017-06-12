package com.superdextor.adinferos.entity.monster;

import javax.annotation.Nullable;

import com.superdextor.adinferos.config.NetherConfig;
import com.superdextor.adinferos.init.NetherItems;

import net.minecraft.entity.ai.EntityAIAttackRanged;
import net.minecraft.init.Items;
import net.minecraft.inventory.EntityEquipmentSlot;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.world.DifficultyInstance;
import net.minecraft.world.World;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class EntityGlowstoneSkeleton extends EntityNetherSkeleton {
	
    private EntityAIAttackRanged aiShootFlame = new EntityAIAttackRanged(this, 1.25D, 7, 10.0F);
	
	public EntityGlowstoneSkeleton(World worldIn)
	{
		super(worldIn);
	}
	
    public void onLivingUpdate()
    {
        super.onLivingUpdate();
    	
    	if(!this.worldObj.isRemote && this.ticksExisted % 300 == 0) {
    		this.setCombatTask();
    	}
    }
    
    @Nullable
    protected Item getDropItem()
    {
    	return Items.GLOWSTONE_DUST;
    }
    
    protected void setEquipmentBasedOnDifficulty(DifficultyInstance difficulty)
    {
		this.setItemStackToSlot(EntityEquipmentSlot.MAINHAND, new ItemStack(NetherItems.GLOWSTONE_SWORD));
    }
    
    public void setCombatTask()
    {
        this.tasks.removeTask(this.aiAttackOnCollide);
        this.tasks.removeTask(this.aiShootFlame);
        
        boolean ranged = false;
        ItemStack stack = this.getHeldItemMainhand();
        
        if(stack != null)
        {
        	ranged = this.rand.nextBoolean();
        }

        if (ranged)
        {
            this.tasks.addTask(4, this.aiShootFlame);
        }
        else
        {
            this.tasks.addTask(4, this.aiAttackOnCollide);
        }
        
        this.setUsingRange(ranged);
    }
    
    @SideOnly(Side.CLIENT)
    public int getBrightnessForRender(float partialTicks)
    {
        return 15728880;
    }

    public float getBrightness(float partialTicks)
    {
        return 1.0F;
    }
    
	public String getTexture() {
		return "adinferos:textures/entity/glowstone_skeleton/default.png";
	}
	
	public boolean isDarkfireResistant() {
		return NetherConfig.glowstoneSkeletonDarkfireResistant;
	}
	
	public boolean isWitherResistant() {
		return NetherConfig.glowstoneSkeletonWitherResistant;
	}

}
