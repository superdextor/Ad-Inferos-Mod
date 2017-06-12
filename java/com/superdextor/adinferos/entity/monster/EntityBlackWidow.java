package com.superdextor.adinferos.entity.monster;

import javax.annotation.Nullable;

import com.superdextor.adinferos.AdInferosCore;
import com.superdextor.adinferos.AdInferosSounds;
import com.superdextor.adinferos.config.NetherConfig;
import com.superdextor.adinferos.entity.NetherMob;
import com.superdextor.adinferos.init.NetherItems;

import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.IEntityLivingData;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.ai.EntityAIAttackMelee;
import net.minecraft.entity.monster.EntitySpider;
import net.minecraft.init.Items;
import net.minecraft.init.MobEffects;
import net.minecraft.item.Item;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.network.datasync.DataParameter;
import net.minecraft.network.datasync.DataSerializers;
import net.minecraft.network.datasync.EntityDataManager;
import net.minecraft.potion.PotionEffect;
import net.minecraft.util.DamageSource;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.SoundEvent;
import net.minecraft.world.DifficultyInstance;
import net.minecraft.world.EnumDifficulty;
import net.minecraft.world.World;

public class EntityBlackWidow extends EntitySpider implements NetherMob
{
	
    private static final DataParameter<Integer> SIZE = EntityDataManager.<Integer>createKey(EntityBlackWidow.class, DataSerializers.VARINT);
    
    public EntityBlackWidow(World worldIn)
    {
        super(worldIn);
        this.tasks.addTask(2, new EntityBlackWidow.AIBlackWidowAttack(this));
        this.experienceValue = NetherConfig.blackWidowEXP;
        if(NetherConfig.blackWidowFireResistant) {
            this.isImmuneToFire = true;
        }
    }
    
    protected float getSoundPitch()
    {
    	return super.getSoundPitch() - (0.5F * getSpiderSize());
    }

    protected void applyEntityAttributes()
    {
        super.applyEntityAttributes();
        this.getEntityAttribute(SharedMonsterAttributes.MOVEMENT_SPEED).setBaseValue(NetherConfig.blackWidowSpeed);
        this.getEntityAttribute(SharedMonsterAttributes.ATTACK_DAMAGE).setBaseValue(NetherConfig.blackWidowDamage + 2.0F);
        this.getEntityAttribute(SharedMonsterAttributes.MAX_HEALTH).setBaseValue(NetherConfig.blackWidowHealth);
    }
    
    protected void entityInit()
    {
        super.entityInit();
        this.getDataManager().register(SIZE, 1);
    }
	
    public boolean attackEntityAsMob(Entity target)
    {
        if (super.attackEntityAsMob(target))
        {
            if (NetherConfig.blackWidowDebuffs && target instanceof EntityLivingBase)
            {
            	EntityLivingBase entityLiving = (EntityLivingBase) target;
                int durAmp = 3;

                if (this.worldObj.getDifficulty() == EnumDifficulty.NORMAL)
                {
                	durAmp = 6;
                }
                else if (this.worldObj.getDifficulty() == EnumDifficulty.HARD)
                {
                	durAmp = 12;
                }
                
                PotionEffect confusion = entityLiving.getActivePotionEffect(MobEffects.NAUSEA);
                
                int ampAmp = 0;

                if (this.worldObj.getDifficulty() == EnumDifficulty.NORMAL)
                {
                	ampAmp = 1;
                }
                else if (this.worldObj.getDifficulty() == EnumDifficulty.HARD)
                {
                	ampAmp = 2;
                }
                
                if(confusion != null) {
                	int bitCounter = confusion.getAmplifier();
                	
                	if(bitCounter > 2) {
                		entityLiving.addPotionEffect(new PotionEffect(MobEffects.HUNGER, (durAmp * (getSpiderSize() + 1 / 2)) * 15, 0));
                	}
                	
                	if(bitCounter > 3) {
                		entityLiving.addPotionEffect(new PotionEffect(MobEffects.SLOWNESS, (durAmp * (getSpiderSize() + 1 / 2)) * 15, bitCounter / 5));
                	}
                	
                	if(bitCounter > 4) {
                		entityLiving.addPotionEffect(new PotionEffect(MobEffects.BLINDNESS, (durAmp * (getSpiderSize() + 1 / 2)) * 15, 0));
                	}
                	
                	if(bitCounter >= 100) {
                    	entityLiving.addPotionEffect(new PotionEffect(MobEffects.NAUSEA, 30 + confusion.getDuration(), 100));
                	}else {
                    	entityLiving.addPotionEffect(new PotionEffect(MobEffects.NAUSEA, 30 + confusion.getDuration(), 1 + confusion.getAmplifier()));
                        entityLiving.addPotionEffect(new PotionEffect(MobEffects.WITHER, (durAmp * (getSpiderSize() + 1 / 2)) * 15, bitCounter / 8));
                	}
                	
                }else {
                	entityLiving.addPotionEffect(new PotionEffect(MobEffects.NAUSEA, durAmp * (getSpiderSize() + 1 / 2) * 15, ampAmp));
                    entityLiving.addPotionEffect(new PotionEffect(MobEffects.WITHER, (durAmp * (getSpiderSize() + 1 / 2)) * 15, 0));
                }
            }

            return true;
        }
        else
        {
            return false;
        }
    }
    
    @Nullable
    protected ResourceLocation getLootTable()
    {
    	return null;
    }
    
    @Nullable
    protected Item getDropItem()
    {
    	return NetherItems.INFERNAL_STRING;
    }
    
    protected void dropFewItems(boolean wasRecentlyHit, int lootingModifier)
    {
    	switch(this.getSpiderSize()) {
	    	case 0: {
	            int i = this.rand.nextInt(1);

	            if (lootingModifier > 0)
	            {
	                i += this.rand.nextInt(lootingModifier + 1);
	            }
	            
	            if(i > 0) {
		            this.dropItem(NetherItems.INFERNAL_STRING, i);
	            }
	    	}
	    	
	    	case 1: {
	            int i = this.rand.nextInt(3);

	            if (lootingModifier > 0)
	            {
	                i += this.rand.nextInt(lootingModifier + 1);
	            }
	            
	            if(i > 0) {
		            this.dropItem(NetherItems.INFERNAL_STRING, i);
	            }
	            
	            if(wasRecentlyHit && this.rand.nextBoolean()) {
	            	this.dropItem(Items.SPIDER_EYE, 1);
	            }
	    	}
	    	
	    	default: {
	            int i = this.getSpiderSize() + this.rand.nextInt(this.getSpiderSize() + 2);

	            if (lootingModifier > 0)
	            {
	                i += this.rand.nextInt(lootingModifier + 1);
	            }
	            
	            if(i > 0) {
		            this.dropItem(NetherItems.INFERNAL_STRING, i);
	            }
	            
	            if(wasRecentlyHit && this.rand.nextBoolean()) {
	            	this.dropItem(Items.SPIDER_EYE, 1 + this.rand.nextInt(lootingModifier + 1));
	            }
	    	}
    	}
    }
    
    protected void dropLoot(boolean playerKill, int looting, DamageSource source)
    {
    	if(NetherConfig.blackWidowDrops) {
        	super.dropLoot(playerKill, looting, source);
    	}
    }
    
    public boolean attackEntityFrom(DamageSource source, float amount)
    {
    	if(source == DamageSource.wither && this.isWitherResistant()) {
    		return false;
    	}
    	
    	if(source.getEntity() != null && source.getEntity().equals(this.getRidingEntity()))
    	{
    		return false;
    	}
    	
    	return super.attackEntityFrom(source, amount);
    }
    
    public int getSpiderSize()
    {
        return this.getDataManager().get(SIZE);
    }
    
    public void setSpiderSize(int size, boolean fillHealth)
    {
        this.getDataManager().set(SIZE, size);
        double f = (size * 5) + 15;
        this.getEntityAttribute(SharedMonsterAttributes.MAX_HEALTH).setBaseValue(f);
        this.getEntityAttribute(SharedMonsterAttributes.ATTACK_DAMAGE).setBaseValue(NetherConfig.blackWidowDamage + (size * 2));
        if(fillHealth)
        this.setHealth(this.getMaxHealth());
    }
    
    public void notifyDataManagerChange(DataParameter<?> key)
    {
        if (SIZE.equals(key))
        {
            float f = 0.5F * (getSpiderSize() + 1);
            this.setSize(1.4F * f, 0.9F * f);
            this.rotationYaw = this.rotationYawHead;
            this.renderYawOffset = this.rotationYawHead;
        }

        super.notifyDataManagerChange(key);
    }
    
    public void writeEntityToNBT(NBTTagCompound tagCompound)
    {
        super.writeEntityToNBT(tagCompound);
        tagCompound.setInteger("Size", this.getSpiderSize());
    }

    public void readEntityFromNBT(NBTTagCompound tagCompund)
    {
        super.readEntityFromNBT(tagCompund);

        if (tagCompund.hasKey("Size", 99)) {
            this.setSpiderSize(tagCompund.getInteger("Size"), false);
        }
    }
    
    public IEntityLivingData onInitialSpawn(DifficultyInstance difficulty, IEntityLivingData livingdata)
    {
    	livingdata = super.onInitialSpawn(difficulty, livingdata);
    
    	this.setSpiderSize(rand.nextInt(NetherConfig.blackWidowMaxsize + 1),true);
    	
    	if(this.getSpiderSize() > 1) {
    		int i = rand.nextInt(2) + 2;
    		
    		for(int x = 0; x < i; x++) {
    			EntityBlackWidow spider = new EntityBlackWidow(this.worldObj);
    			spider.setLocationAndAngles(posX, posY, posZ, rotationYaw, 0.0F);
    			spider.setSpiderSize(0, true);
    			this.worldObj.spawnEntityInWorld(spider);
    		}
    	}
    	
		NetherConfig.printDebugInfo(this.getName() + " was spawned at " + this.getPosition());
    	
        return livingdata;
    }
    
    @Nullable
    protected SoundEvent getAmbientSound()
    {
        return AdInferosSounds.ENTITY_BLACKWIDOW_IDLE;
    }

    @Nullable
    protected SoundEvent getHurtSound()
    {
        return AdInferosSounds.ENTITY_BLACKWIDOW_HURT;
    }

    @Nullable
    protected SoundEvent getDeathSound()
    {
        return AdInferosSounds.ENTITY_BLACKWIDOW_DEATH;
    }
    
    public boolean getCanSpawnHere()
    {
    	if(this.worldObj.provider.getDimension() == -1 && !AdInferosCore.proxy.isDarkNether())
    	{
    		return false;
    	}
    	
    	return super.getCanSpawnHere();
    }
    
	public boolean isDarkfireResistant()
	{
		return NetherConfig.blackWidowDarkfireResistant;
	}
	
	public boolean isCurseResistant(){
		
		return NetherConfig.blackWidowCurseResistant;
	}
	
	public boolean isWitherResistant()
	{
		return NetherConfig.blackWidowWitherResistant;
	}
	
	public boolean isAcidResistant()
	{
		return NetherConfig.blackWidowAcidResistant;
	}
	
	public boolean isSpikeResistant()
	{
		return false;
	}
	
    public float getEyeHeight()
    {
        return this.height * 0.6F;
    }
    
    static class AIBlackWidowAttack extends EntityAIAttackMelee
    {
    	private final EntityBlackWidow blackWidow;
    	
        public AIBlackWidowAttack(EntityBlackWidow blackWidow)
        {
            super(blackWidow, 1.0D, true);
            this.blackWidow = blackWidow;
        }

        protected double getAttackReachSqr(EntityLivingBase attackTarget)
        {
            return (double)(2.0F + (this.blackWidow.getSpiderSize() * 2.0F) + attackTarget.width);
        }
    }
}