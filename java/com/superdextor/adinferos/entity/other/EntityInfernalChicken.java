package com.superdextor.adinferos.entity.other;

import javax.annotation.Nullable;

import com.superdextor.adinferos.AdInferosSounds;
import com.superdextor.adinferos.config.NetherConfig;
import com.superdextor.adinferos.entity.NetherMob;
import com.superdextor.adinferos.init.NetherBlocks;
import com.superdextor.adinferos.init.NetherItems;

import net.minecraft.entity.EntityAgeable;
import net.minecraft.entity.IEntityLivingData;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.ai.EntityAITempt;
import net.minecraft.entity.passive.EntityChicken;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.init.SoundEvents;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.SoundEvent;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.world.DifficultyInstance;
import net.minecraft.world.World;

public class EntityInfernalChicken extends EntityChicken implements NetherMob {

	public EntityInfernalChicken(World worldIn) {
		super(worldIn);
        this.tasks.addTask(3, new EntityAITempt(this, 1.0D, NetherItems.INFERNAL_SEEDS, false));
		this.isImmuneToFire = true;
		this.spawnableBlock = Blocks.NETHERRACK;
	}
	
    protected void applyEntityAttributes()
    {
        super.applyEntityAttributes();
        this.getEntityAttribute(SharedMonsterAttributes.MAX_HEALTH).setBaseValue(24.0D);
        this.getEntityAttribute(SharedMonsterAttributes.MOVEMENT_SPEED).setBaseValue(0.25D);
    }
    
    @Nullable
    protected SoundEvent getAmbientSound()
    {
        return AdInferosSounds.ENTITY_INFERNALCHICKEN_SAY;
    }

    @Nullable
    protected SoundEvent getHurtSound()
    {
        return AdInferosSounds.ENTITY_INFERNALCHICKEN_HURT;
    }
    
    @Nullable
    protected SoundEvent getDeathSound()
    {
        return AdInferosSounds.ENTITY_INFERNALCHICKEN_DEATH;
    }
    
    @Nullable
    protected ResourceLocation getLootTable()
    {
    	return null;
    }
    @Nullable
    protected Item getDropItem()
    {
    	return NetherItems.INFERNAL_FEATHER;
    }
    
    protected void dropFewItems(boolean wasRecentlyHit, int lootingModifier)
    {
    	super.dropFewItems(wasRecentlyHit, lootingModifier);
    	this.dropItem(NetherItems.INFERNAL_CHICKEN, 1);
    }
    
    public void onLivingUpdate()
    {
        super.onLivingUpdate();

        if (!this.worldObj.isRemote && !this.isChild() && !this.isChickenJockey() && this.timeUntilNextEgg <= 1)
        {
            this.playSound(SoundEvents.ENTITY_CHICKEN_EGG, 1.0F, (this.rand.nextFloat() - this.rand.nextFloat()) * 0.2F + 1.0F);
            this.dropItem(Items.QUARTZ, 1);
            this.timeUntilNextEgg = this.rand.nextInt(9000) + 9000;
        }
    }
    
    public EntityChicken createChild(EntityAgeable ageable)
    {
        return new EntityInfernalChicken(this.worldObj);
    }
    
    public boolean isBreedingItem(ItemStack stack)
    {
        return stack.getItem().equals(NetherItems.INFERNAL_SEEDS);
    }
    
    public IEntityLivingData onInitialSpawn(DifficultyInstance difficulty, IEntityLivingData livingdata)
    {
		NetherConfig.printDebugInfo(this.getName() + " was spawned at " + this.getPosition());
    	return super.onInitialSpawn(difficulty, livingdata);
    }
    
    public boolean getCanSpawnHere()
    {
        int i = MathHelper.floor_double(this.posX);
        int j = MathHelper.floor_double(this.getEntityBoundingBox().minY);
        int k = MathHelper.floor_double(this.posZ);
        BlockPos blockpos = new BlockPos(i, j, k);
        return this.worldObj.getBlockState(blockpos.down()).getBlock() == this.spawnableBlock && this.getBlockPathWeight(new BlockPos(this.posX, this.getEntityBoundingBox().minY, this.posZ)) >= 0.0F;
    }

	public boolean isDarkfireResistant()
	{
		return true;
	}

	public boolean isCurseResistant()
	{
		return true;
	}

	public boolean isWitherResistant()
	{
		return false;
	}

	public boolean isAcidResistant()
	{
		return false;
	}

	public boolean isSpikeResistant()
	{
		return false;
	}

}
