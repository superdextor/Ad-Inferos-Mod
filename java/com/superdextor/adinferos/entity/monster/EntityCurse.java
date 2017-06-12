package com.superdextor.adinferos.entity.monster;

import javax.annotation.Nullable;

import com.google.common.base.Predicate;
import com.superdextor.adinferos.AdInferosCore;
import com.superdextor.adinferos.AdInferosSounds;
import com.superdextor.adinferos.config.NetherConfig;
import com.superdextor.adinferos.entity.NetherMob;
import com.superdextor.adinferos.init.NetherBlocks;
import com.superdextor.adinferos.world.WorldProviderAbyss;

import net.minecraft.block.material.Material;
import net.minecraft.entity.EnumCreatureAttribute;
import net.minecraft.entity.IEntityLivingData;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.ai.EntityAIAttackMelee;
import net.minecraft.entity.ai.EntityAIHurtByTarget;
import net.minecraft.entity.ai.EntityAINearestAttackableTarget;
import net.minecraft.entity.ai.EntityAISwimming;
import net.minecraft.entity.ai.EntityAIWander;
import net.minecraft.entity.monster.EntityIronGolem;
import net.minecraft.entity.monster.EntityMob;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.MobEffects;
import net.minecraft.item.Item;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.network.datasync.DataParameter;
import net.minecraft.network.datasync.DataSerializers;
import net.minecraft.network.datasync.EntityDataManager;
import net.minecraft.util.DamageSource;
import net.minecraft.util.EnumParticleTypes;
import net.minecraft.util.SoundEvent;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.world.DifficultyInstance;
import net.minecraft.world.World;

public class EntityCurse extends EntityMob implements NetherMob
{
	
    private static final DataParameter<Integer> TYPE = EntityDataManager.<Integer>createKey(EntityCurse.class, DataSerializers.VARINT);

    public EntityCurse(World worldIn)
    {
        super(worldIn);
        this.setSize(0.8F, 1.95F);
        if(NetherConfig.curseFireResistant) {
            this.isImmuneToFire = true;
        }
        this.tasks.addTask(0, new EntityAISwimming(this));
        this.tasks.addTask(2, new EntityAIAttackMelee(this, 1.0D, false));
        this.tasks.addTask(7, new EntityAIWander(this, 1.0D));
        this.targetTasks.addTask(1, new EntityAIHurtByTarget(this, false, new Class[0]));
        this.targetTasks.addTask(2, new EntityAINearestAttackableTarget(this, EntityPlayer.class, 10, true, false, new Predicate<EntityPlayer>() {

			public boolean apply(EntityPlayer input) {
				return !input.isPotionActive(MobEffects.LUCK);
			} 
        	
        }));
        this.targetTasks.addTask(3, new EntityAINearestAttackableTarget(this, EntityIronGolem.class, true));
        this.experienceValue = NetherConfig.curseEXP;
    }

    protected void applyEntityAttributes()
    {
        super.applyEntityAttributes();
        this.getEntityAttribute(SharedMonsterAttributes.MAX_HEALTH).setBaseValue(NetherConfig.curseHealth);
        this.getEntityAttribute(SharedMonsterAttributes.MOVEMENT_SPEED).setBaseValue(NetherConfig.curseSpeed);
        this.getEntityAttribute(SharedMonsterAttributes.ATTACK_DAMAGE).setBaseValue(NetherConfig.curseDamage);
    }
    
    protected void entityInit()
    {
        super.entityInit();
        this.getDataManager().register(TYPE, 0);
    }
    
    public CurseType getType()
    {
        return CurseType.getByOrdinal(((Integer)this.dataManager.get(TYPE)).intValue());
    }
    
    public void setType(CurseType value)
    {
    	this.getDataManager().set(TYPE, value.getId());
    }
    
    public void onLivingUpdate()
    {
        super.onLivingUpdate();

        if (!this.worldObj.isRemote && NetherConfig.curseTrail) {
            
            for (int l = 0; l < 4; ++l)
            {
                int i = MathHelper.floor_double(this.posX + (double)((float)(l % 2 * 2 - 1) * 0.25F));
                int j = MathHelper.floor_double(this.posY);
                int k = MathHelper.floor_double(this.posZ + (double)((float)(l / 2 % 2 * 2 - 1) * 0.25F));
                BlockPos blockpos = new BlockPos(i, j, k);

                if (this.worldObj.getBlockState(blockpos).getMaterial() == Material.AIR && NetherBlocks.CURSE.canPlaceBlockAt(this.worldObj, blockpos))
                {
                    this.worldObj.setBlockState(blockpos, this.getType().getTrail());
                }
            }
        }
        else if(this.rand.nextFloat() < 0.4F)
        {
        	switch(this.getType())
        	{
	    		case FLAME:
	    		{
	    	        double d0 = (double)((float)posX + rand.nextFloat() - 0.5D);
	    	        double d1 = (double)((float)posY);
	    	        double d2 = (double)((float)posZ + rand.nextFloat() - 0.5D);
	    	        this.worldObj.spawnParticle(EnumParticleTypes.FLAME, d0, d1, d2, 0.0D, 0.03D, 0.0D, new int[0]);
	    	        break;
	    		}
	    		
	    		case DECAY:
	    		{
	    	        double d0 = (double)((float)posX + rand.nextFloat() - 0.5D);
	    	        double d1 = (double)((float)posY);
	    	        double d2 = (double)((float)posZ + rand.nextFloat() - 0.5D);
	    	        this.worldObj.spawnParticle(EnumParticleTypes.SPELL_MOB, d0, d1, d2, 0.1D, 0.1D, 0.1D, new int[0]);
	    	        break;
	    		}
	    		
	    		case VENOM:
	    		{
	    	        double d0 = (double)((float)posX + rand.nextFloat() - 0.5D);
	    	        double d1 = (double)((float)posY);
	    	        double d2 = (double)((float)posZ + rand.nextFloat() - 0.5D);
	    	        this.worldObj.spawnParticle(EnumParticleTypes.SPELL_MOB, d0, d1, d2, 0.1D, 0.5D, 0.0D, new int[0]);
	    	        break;
	    		}
	    		
	    		default:
	    		{
	    	        double d0 = (double)((float)posX + rand.nextFloat() - 0.5D);
	    	        double d1 = (double)((float)posY);
	    	        double d2 = (double)((float)posZ + rand.nextFloat() - 0.5D);
	    	        this.worldObj.spawnParticle(EnumParticleTypes.SPELL_MOB, d0, d1, d2, 0.5D, 0.0D, 1.0D, new int[0]);
	    	        break;
	    		}
        	}
        }
    }
    
    @Nullable
    protected SoundEvent getAmbientSound()
    {
		return AdInferosSounds.ENTITY_CURSE_IDLE;
    }

    @Nullable
    protected SoundEvent getHurtSound()
    {
		return AdInferosSounds.ENTITY_CURSE_HURT;
    }

    @Nullable
    protected SoundEvent getDeathSound()
    {
		return AdInferosSounds.ENTITY_CURSE_DEATH;
    }
    
    public boolean getCanSpawnHere()
    {
    	if(this.worldObj.provider.getDimension() == -1 && !AdInferosCore.proxy.isDarkNether()) {
    		return false;
    	}
    	
    	return super.getCanSpawnHere();
    }
    
    protected boolean isValidLightLevel()
    {
    	return true;
    }
    
    @Nullable
    protected Item getDropItem()
    {
    	return this.getType().getDropItem();
    }
    
    protected void dropLoot(boolean playerKill, int looting, DamageSource source)
    {
    	if(NetherConfig.curseDrops) {
        	super.dropLoot(playerKill, looting, source);
    	}
    }
    
    protected float getSoundPitch()
    {
    	return super.getSoundPitch() * 0.7F;
    }
    
    public void readEntityFromNBT(NBTTagCompound tagCompund)
    {
    	super.readEntityFromNBT(tagCompund);
    	
    	this.setType(CurseType.getByOrdinal(tagCompund.getInteger("Type")));
    }
    
    public void writeEntityToNBT(NBTTagCompound tagCompound)
    {
    	super.writeEntityToNBT(tagCompound);
    	
    	tagCompound.setInteger("Type", this.getType().getId());
    }
    
    public IEntityLivingData onInitialSpawn(DifficultyInstance difficulty, IEntityLivingData livingdata)
    {
    	IEntityLivingData data = super.onInitialSpawn(difficulty, livingdata);
    	
    	if(this.worldObj.provider instanceof WorldProviderAbyss) {
    		if(this.rand.nextBoolean()) {
    			this.setType(CurseType.DECAY);
    		}
    		else 
    		{
    			this.setType(CurseType.NAUSEA);
    		}
    	}
    	else {
    		if(this.rand.nextBoolean()) {
    			this.setType(CurseType.FLAME);
    		}else {
    			this.setType(CurseType.VENOM);
    		}
    	}
    	
		NetherConfig.printDebugInfo(this.getName() + " was spawned at " + this.getPosition());
    	
    	return data;
    }
    
    public EnumCreatureAttribute getCreatureAttribute()
    {
        return EnumCreatureAttribute.UNDEAD;
    }

	public boolean isDarkfireResistant() {
		return NetherConfig.curseDarkfireResistant;
	}

	public boolean isCurseResistant() {
		return NetherConfig.curseCurseResistant;
	}

	public boolean isWitherResistant() {
		return NetherConfig.curseWitherResistant;
	}

	public boolean isAcidResistant() {
		return NetherConfig.curseAcidResistant;
	}

	public boolean isSpikeResistant() {
		return false;
	}
    

}