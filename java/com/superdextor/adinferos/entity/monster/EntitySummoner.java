package com.superdextor.adinferos.entity.monster;

import com.superdextor.adinferos.AdInferosSounds;
import com.superdextor.adinferos.config.NetherConfig;
import com.superdextor.adinferos.entity.NetherMob;
import com.superdextor.thinkbigcore.entity.IFearedEntity;
import com.superdextor.thinkbigcore.entity.ai.EntityAIStare;

import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.EnumCreatureAttribute;
import net.minecraft.entity.IEntityLivingData;
import net.minecraft.entity.monster.EntityBlaze;
import net.minecraft.entity.monster.EntityMagmaCube;
import net.minecraft.entity.monster.EntitySkeleton;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.MobEffects;
import net.minecraft.init.SoundEvents;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.network.datasync.DataParameter;
import net.minecraft.network.datasync.DataSerializers;
import net.minecraft.network.datasync.EntityDataManager;
import net.minecraft.potion.PotionEffect;
import net.minecraft.util.DamageSource;
import net.minecraft.util.SoundEvent;
import net.minecraft.world.DifficultyInstance;
import net.minecraft.world.World;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class EntitySummoner extends EntityLiving implements NetherMob, IFearedEntity {
	
    private static final DataParameter<Integer> STAGE = EntityDataManager.<Integer>createKey(EntitySummoner.class, DataSerializers.VARINT);
	private int cooldown = 500;
	private int waveCounter = 0;

    public EntitySummoner(World worldIn)
    {
        super(worldIn);
        this.tasks.addTask(1, new EntityAIStare(this, EntityPlayer.class, 8.0F));
        this.isImmuneToFire = true;
        this.setSize(0.6F, 1.0F);
    }
    
    protected void entityInit() {
    	super.entityInit();
    	this.getDataManager().register(STAGE, 0);
    }
    
    public IEntityLivingData onInitialSpawn(DifficultyInstance difficulty, IEntityLivingData livingdata) {
		NetherConfig.printDebugInfo(this.getName() + " was spawned at " + this.getPosition());
    	return super.onInitialSpawn(difficulty, livingdata);
    }
    
    public int getTalkInterval() {
    	return 140;
    }
    
    protected SoundEvent getAmbientSound() {
    	return AdInferosSounds.AMBIENT_SPOOKY;
    }
    
    protected SoundEvent getHurtSound() {
    	return SoundEvents.BLOCK_STONE_BREAK;
    }
    
    protected SoundEvent getDeathSound()
    {
    	return SoundEvents.BLOCK_STONE_BREAK;
    }
    
    protected float getSoundVolume() {
    	return 3.0F;
    }
    
    public void readEntityFromNBT(NBTTagCompound tagCompund)
    {
        super.readEntityFromNBT(tagCompund);

        if (tagCompund.hasKey("Cooldown", 99))
        {
            byte b0 = tagCompund.getByte("Cooldown");
            this.cooldown = b0;
        }
        
        if (tagCompund.hasKey("waveCounter", 99))
        {
            byte b0 = tagCompund.getByte("waveCounter");
            this.waveCounter = b0;
        }
    }

    public void writeEntityToNBT(NBTTagCompound tagCompound)
    {
        super.writeEntityToNBT(tagCompound);
        tagCompound.setByte("Cooldown", (byte)this.getCooldown());
        tagCompound.setByte("waveCounter", (byte)this.getwaveCounter());
    }
    
    public int getCooldown() {
    	return this.cooldown;
    }
    
    public int getwaveCounter() {
    	return this.waveCounter;
    }
    
    public int getStage() {
    	return this.getDataManager().get(STAGE);
    }
    
    protected void setStage(int value) {
    	this.getDataManager().set(STAGE, value);
    }
    
    public void resetCooldown() {
        this.cooldown = 450;
    }
    
    public void onLivingUpdate() {
    	
    	if(!worldObj.isRemote) {
    	
    	if(this.cooldown <= 0) {
    		int k = 0;
    		int i = 0;
            this.playSound(AdInferosSounds.ENTITY_SUMMON, 2.0F, 1.3F);
            this.worldObj.newExplosion((Entity)null, this.posX, this.posY + 1, this.posZ, 0.5F, false, false);
    		if(this.waveCounter == 0) {
    			i = rand.nextInt(3) + 3;
    			for(k = 0; k < i; k++) {
    				spawnMob(this.initEntity(new EntitySkeleton(this.worldObj)), 3);
    			}
    		}
    		else if(this.waveCounter == 1) {
    			i = rand.nextInt(3) + 3;
    			for(k = 0; k < i; k++) {
    				spawnMob(this.initEntity(new EntityBlackWidow(this.worldObj)), 4);
    			}
    		}
    		else if(this.waveCounter == 2) {
    			i = rand.nextInt(3) + 2;
    			for(k = 0; k < i; k++) {
    				EntityGlowstoneSkeleton entitySkeleton = new EntityGlowstoneSkeleton(this.worldObj);
    				this.initEntity(entitySkeleton);
    				spawnMob(entitySkeleton, 4);
    			}
    			
    			i = rand.nextInt(2) + 2;
    			for(k = 0; k < i; k++) {
    				spawnMob(this.initEntity(new EntityMagmaCube(this.worldObj)), 4);
    			}
    		}
    		else if(this.waveCounter == 3) {
    			i = rand.nextInt(4) + 3;
    			for(k = 0; k < i; k++) {
    				spawnMob(this.initEntity(new EntityPhantom(this.worldObj)), 5);
    			}
    		}
    		else if(this.waveCounter == 4) {
    			
    			i = rand.nextInt(6) + 4;
    			for(k = 0; k < i; k++) {
    				spawnMob(this.initEntity(new EntityBlaze(this.worldObj)), 5);
    			}
    			
    			if(rand.nextBoolean()) {
        			spawnMob(this.initEntity(new EntitySkeletonHorse(this.worldObj)), 5);
    			}
    		}
    		else if(this.waveCounter == 5) {
                
    			i = rand.nextInt(2) + 2;
    			for(k = 0; k < i; k++) {
    				spawnMob(this.initEntity(new EntityReaper(this.worldObj)), 3);
    			}
    			
    			i = rand.nextInt(2) + 2;
    			for(k = 0; k < i; k++) {
    				spawnMob(this.initEntity(new EntitySkeletonHorse(this.worldObj)), 5);
    			}
    			
    			i = rand.nextInt(2) + 2;
    			for(k = 0; k < i; k++) {
    				spawnMob(this.initEntity(new EntityPhantom(this.worldObj)), 5);
    			}
    			
    			if(rand.nextBoolean()) {
        			spawnMob(this.initEntity(new EntityCurse(this.worldObj)), 7);
    			}
                
    		}
    		else
    		{
                this.worldObj.newExplosion((Entity)null, this.posX, this.posY, this.posZ, 6.0F, false, true);
                EntityHerobrine herobrine = new EntityHerobrine(this.worldObj);
                EntityReaper reaper = new EntityReaper(this.worldObj);
                this.initEntity(herobrine);
                this.initEntity(reaper);
                herobrine.addPotionEffect(new PotionEffect(MobEffects.REGENERATION, 40, 3));
                herobrine.addPotionEffect(new PotionEffect(MobEffects.RESISTANCE, 40, 3));
                reaper.addPotionEffect(new PotionEffect(MobEffects.REGENERATION, 40, 3));
                reaper.addPotionEffect(new PotionEffect(MobEffects.RESISTANCE, 40, 3));
                this.spawnMob(herobrine, 0);
                this.spawnMob(reaper, 2);
                this.setDead();
    		}
    	
      ++this.waveCounter;
        this.resetCooldown();
        this.setStage(0);
    	}
    	else if(this.cooldown == 70) {
    	    this.addPotionEffect(new PotionEffect(MobEffects.RESISTANCE, 80, 1));
    		int i = this.rand.nextInt(3);
    		if(i == 0) {
            this.playSound(SoundEvents.ENTITY_GHAST_HURT, 2.0F, 0.5F);
    		}
    		else if(i == 1) {
                this.playSound(SoundEvents.ENTITY_ENDERMEN_SCREAM, 2.0F, 0.5F);
    		}
    		else
    		{
                this.playSound(SoundEvents.ENTITY_WITHER_AMBIENT, 2.0F, 0.5F);
    		}
    		
    		this.setStage(this.waveCounter + 1);
    		
    	}
    	
    	if(this.cooldown > 0) {
    		--this.cooldown;
    	}
    	
    }
    
        super.onLivingUpdate();
    }
    
    private EntityLiving initEntity(EntityLiving entity) {
    	entity.onInitialSpawn(this.worldObj.getDifficultyForLocation(this.getPosition()), null);
    	return entity;
    }

	private void spawnMob(EntityLiving entity, int radius) {
        entity.setLocationAndAngles(this.posX -(2 + radius) + (double)rand.nextInt(5 + radius), this.posY, this.posZ -(2 + radius) + (double)rand.nextInt(5 + radius), 0, 0.0F);
        
        for(int x = 0; x < 10; x++) {
        	if(entity.isEntityInsideOpaqueBlock())
        		entity.posY++;
        	else
        	break;
        }
        
        this.worldObj.spawnEntityInWorld(entity);
    }
    
    @SideOnly(Side.CLIENT)
    public int getBrightnessForRender(float f)
    {
        return this.rand.nextFloat() < 0.1F ? 0 : 15728880;
    }
    
    protected void collideWithEntity(Entity entityIn) {
    	
    }
    
    public boolean canBePushed()
    {
        return false;
    }
    
    protected boolean canDespawn()
    {
        return false;
    }
    
    public EnumCreatureAttribute getCreatureAttribute()
    {
        return EnumCreatureAttribute.UNDEAD;
    }
    
    public boolean attackEntityFrom(DamageSource source, float amount) {
    	return false;
    }

	public boolean isDarkfireResistant() {
		return true;
	}

	public boolean isCurseResistant() {
		return true;
	}

	public boolean isWitherResistant() {
		return true;
	}

	public boolean isAcidResistant() {
		return true;
	}

	public boolean isSpikeResistant() {
		return true;
	}

	public int getFearDelay() {
		return 14;
	}

	public int getFearAmount() {
		return 6;
	}

	public double getFearRange() {
		return 500.0D;
	}

	public PotionEffect getFearEffect() {
		return null;
	}

	public double getEffectRange() {
		return 0;
	}

	public boolean effectOnTargetOnly() {
		return false;
	}
    
}
