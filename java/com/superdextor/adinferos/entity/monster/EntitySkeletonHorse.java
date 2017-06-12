package com.superdextor.adinferos.entity.monster;

import javax.annotation.Nullable;

import com.google.common.base.Predicate;
import com.superdextor.adinferos.config.NetherConfig;
import com.superdextor.adinferos.entity.NetherMob;
import com.superdextor.adinferos.init.NetherItems;
import com.superdextor.adinferos.world.WorldProviderAbyss;

import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.EnumCreatureAttribute;
import net.minecraft.entity.IEntityLivingData;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.ai.EntityAIAttackMelee;
import net.minecraft.entity.ai.EntityAIHurtByTarget;
import net.minecraft.entity.ai.EntityAILookIdle;
import net.minecraft.entity.ai.EntityAIMoveTowardsRestriction;
import net.minecraft.entity.ai.EntityAINearestAttackableTarget;
import net.minecraft.entity.ai.EntityAISwimming;
import net.minecraft.entity.ai.EntityAIWander;
import net.minecraft.entity.ai.EntityAIWatchClosest;
import net.minecraft.entity.monster.EntityIronGolem;
import net.minecraft.entity.monster.EntityMob;
import net.minecraft.entity.monster.EntitySkeleton;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Items;
import net.minecraft.init.MobEffects;
import net.minecraft.init.SoundEvents;
import net.minecraft.item.Item;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.network.datasync.DataParameter;
import net.minecraft.network.datasync.DataSerializers;
import net.minecraft.network.datasync.EntityDataManager;
import net.minecraft.pathfinding.PathNodeType;
import net.minecraft.potion.PotionEffect;
import net.minecraft.util.DamageSource;
import net.minecraft.util.SoundEvent;
import net.minecraft.util.math.MathHelper;
import net.minecraft.world.DifficultyInstance;
import net.minecraft.world.World;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class EntitySkeletonHorse extends EntityMob implements NetherMob
{
    private static final DataParameter<Boolean> IS_WITHER = EntityDataManager.<Boolean>createKey(EntitySkeletonHorse.class, DataSerializers.BOOLEAN);
    private static final DataParameter<Boolean> IS_REARING = EntityDataManager.<Boolean>createKey(EntitySkeletonHorse.class, DataSerializers.BOOLEAN);
    private int jumpRearingCounter;
    private float rearingAmount;
    private float prevRearingAmount;
    private float mouthOpenness;
    private float prevMouthOpenness;
    
	public EntitySkeletonHorse(World worldIn)
    {
        super(worldIn);
        this.setSize(1.4F, 1.6F);
        this.isImmuneToFire = true;
        this.setPathPriority(PathNodeType.WATER, -1.0F);
        this.tasks.addTask(0, new EntityAISwimming(this));
        this.tasks.addTask(2, new EntityAIAttackMelee(this, 1.0D, false));
        this.tasks.addTask(5, new EntityAIMoveTowardsRestriction(this, 1.0D));
        this.tasks.addTask(7, new EntityAIWander(this, 1.0D));
        this.tasks.addTask(8, new EntityAIWatchClosest(this, EntityPlayer.class, 8.0F));
        this.tasks.addTask(8, new EntityAILookIdle(this));
        this.targetTasks.addTask(1, new EntityAIHurtByTarget(this, true));
        this.targetTasks.addTask(2, new EntityAINearestAttackableTarget(this, EntityPlayer.class, 10, true, false, new Predicate<EntityPlayer>() {

			public boolean apply(EntityPlayer input)
			{
				return !input.isPotionActive(MobEffects.LUCK);
			} 
        	
        }));
        this.targetTasks.addTask(3, new EntityAINearestAttackableTarget(this, EntityIronGolem.class, true));
        this.experienceValue = 7;
    }

    public float getEyeHeight()
    {
        return 1.9F;
    }

    protected void applyEntityAttributes()
    {
        super.applyEntityAttributes();
        this.getEntityAttribute(SharedMonsterAttributes.MAX_HEALTH).setBaseValue(24.0D);
        this.getEntityAttribute(SharedMonsterAttributes.MOVEMENT_SPEED).setBaseValue(0.34D);
        this.getEntityAttribute(SharedMonsterAttributes.ATTACK_DAMAGE).setBaseValue(3.0D);
    }
    
    protected void entityInit()
    {
        super.entityInit();
        this.getDataManager().register(IS_WITHER, false);
        this.getDataManager().register(IS_REARING, false);
    }
    
    public boolean isWither()
    {
        return this.getDataManager().get(IS_WITHER);
    }

    public void setWither(boolean wither)
    {
        this.getDataManager().set(IS_WITHER, wither);
    }
    
    @Nullable
    protected SoundEvent getAmbientSound()
    {
        if (this.rand.nextInt(10) == 0 && !this.isMovementBlocked())
        {
            this.setRearing(true);
        }

        return SoundEvents.ENTITY_SKELETON_HORSE_AMBIENT;
    }
    
    @Nullable
    protected SoundEvent getHurtSound()
    {
        if (this.rand.nextInt(3) == 0)
        {
            this.setRearing(true);
        }

        return SoundEvents.ENTITY_SKELETON_HORSE_HURT;
    }
    
    @Nullable
    protected SoundEvent getDeathSound()
    {
    	return SoundEvents.ENTITY_SKELETON_HORSE_DEATH;
    }
    
    @Nullable
    protected Item getDropItem()
    {
    	return Items.BONE;
    }
    
    protected void dropFewItems(boolean wasRecentlyHit, int lootingModifier)
    {
    	super.dropFewItems(wasRecentlyHit, lootingModifier);
    	
    	if(this.isWither()) {
            int i = this.rand.nextInt(3);

            if (lootingModifier > 0)
            {
                i += this.rand.nextInt(lootingModifier + 1);
            }

            if(i > 0) {
                this.dropItem(NetherItems.WITHER_DUST, i);
            }
    	}
    }
    
    public IEntityLivingData onInitialSpawn(DifficultyInstance difficulty, IEntityLivingData livingdata)
    {
    	IEntityLivingData data = super.onInitialSpawn(difficulty, livingdata);
    	
        if (this.worldObj.provider instanceof WorldProviderAbyss && this.getRNG().nextInt(5) > 0)
        {
        	this.setWither(true);
            this.getEntityAttribute(SharedMonsterAttributes.MAX_HEALTH).setBaseValue(30.0D);
            this.getEntityAttribute(SharedMonsterAttributes.MOVEMENT_SPEED).setBaseValue(0.4D);
            this.getEntityAttribute(SharedMonsterAttributes.ATTACK_DAMAGE).setBaseValue(5.0D);
        }
        
        EntityLiving riderIn = null;

        if (this.worldObj.rand.nextInt(7) == 0)
        {
            riderIn = new EntitySkeleton(this.worldObj);
        }
        else if(this.worldObj.rand.nextInt(10) == 0)
        {
            riderIn = new EntityGlowstoneSkeleton(this.worldObj);
        }
        else if(this.worldObj.rand.nextInt(30) == 0)
        {
        	riderIn = new EntityReaper(this.worldObj);
        }
        else if(this.worldObj.rand.nextInt(10) == 0)
        {
        	riderIn = new EntityObsidianSheepman(this.worldObj);
        	NBTTagCompound NBTTag = new NBTTagCompound();
        	NBTTag.setShort("Anger", (short) 400);
        	riderIn.writeEntityToNBT(NBTTag);
        }
        
        if(riderIn != null) {
        	riderIn.setLocationAndAngles(this.posX, this.posY, this.posZ, this.rotationYaw, 0.0F);
        	riderIn.onInitialSpawn(this.worldObj.getDifficultyForLocation(this.getPosition()), null);
            this.worldObj.spawnEntityInWorld(riderIn);
            riderIn.startRiding(this);
        }
    	
		NetherConfig.printDebugInfo(this.getName() + " was spawned at " + this.getPosition());
        
    	return data;
    }
    
    public void onUpdate()
    {
        if (this.canPassengerSteer() && this.jumpRearingCounter > 0 && ++this.jumpRearingCounter > 20)
        {
            this.jumpRearingCounter = 0;
            this.setRearing(false);
        }
    	
        this.prevRearingAmount = this.rearingAmount;

        if (this.isRearing())
        {
            this.rearingAmount += (1.0F - this.rearingAmount) * 0.4F + 0.05F;

            if (this.rearingAmount > 1.0F)
            {
                this.rearingAmount = 1.0F;
            }
        }
        else
        {
            this.rearingAmount += (0.8F * this.rearingAmount * this.rearingAmount * this.rearingAmount - this.rearingAmount) * 0.6F - 0.05F;

            if (this.rearingAmount < 0.0F)
            {
                this.rearingAmount = 0.0F;
            }
        }
    	
    	super.onUpdate();
    }
    
    public boolean attackEntityFrom(DamageSource source, float amount)
    {
    	if(this.isWither() && source == DamageSource.wither) {
    		return false;
    	}
    	
        if (this.isEntityInvulnerable(source))
        {
            return false;
        }
        else if (super.attackEntityFrom(source, amount))
        {
            Entity entity = source.getEntity();
            return this.isBeingRidden() && this.getRidingEntity() != entity ? true : true;
        }
        else
        {
            return false;
        }
    }
    
    public boolean attackEntityAsMob(Entity entity)
    {
        if (super.attackEntityAsMob(entity))
        {
            if (this.isWither() && entity instanceof EntityLivingBase)
            {
                ((EntityLivingBase)entity).addPotionEffect(new PotionEffect(MobEffects.WITHER, 200));
            }

            return true;
        }
        else
        {
            return false;
        }
    }
    

    /**
     * (abstract) Protected helper method to read subclass entity data from NBT.
     */
    public void readEntityFromNBT(NBTTagCompound tagCompund)
    {
        super.readEntityFromNBT(tagCompund);
        this.setWither(tagCompund.getBoolean("Wither"));
    }

    /**
     * (abstract) Protected helper method to write subclass entity data to NBT.
     */
    public void writeEntityToNBT(NBTTagCompound tagCompound)
    {
        super.writeEntityToNBT(tagCompound);
        tagCompound.setBoolean("Wither", this.isWither());
    }
    
    /**
     * Get this Entity's EnumCreatureAttribute
     */
    public EnumCreatureAttribute getCreatureAttribute()
    {
        return EnumCreatureAttribute.UNDEAD;
    }
    
    public void updatePassenger(Entity passenger)
    {
        super.updatePassenger(passenger);

        if (passenger instanceof EntityLiving)
        {
            EntityLiving entityliving = (EntityLiving)passenger;
            this.renderYawOffset = entityliving.renderYawOffset;
        }

        if (this.prevRearingAmount > 0.0F)
        {
            float f3 = MathHelper.sin(this.renderYawOffset * 0.017453292F);
            float f = MathHelper.cos(this.renderYawOffset * 0.017453292F);
            float f1 = 0.7F * this.prevRearingAmount;
            float f2 = 0.15F * this.prevRearingAmount;
            passenger.setPosition(this.posX + (double)(f1 * f3), this.posY + this.getMountedYOffset() + passenger.getYOffset() + (double)f2, this.posZ - (double)(f1 * f));

            if (passenger instanceof EntityLivingBase)
            {
                ((EntityLivingBase)passenger).renderYawOffset = this.renderYawOffset;
            }
        }
    }
    
    public void setRearing(boolean rearing)
    {
    	if(rearing) {
    	    this.jumpRearingCounter = 1;
    	}
    	
        this.getDataManager().set(IS_REARING, rearing);
    }
    
    public boolean isRearing()
    {
        return this.getDataManager().get(IS_REARING);
    }
    
    @SideOnly(Side.CLIENT)
    public float getRearingAmount(float amount)
    {
        return this.prevRearingAmount + (this.rearingAmount - this.prevRearingAmount) * amount;
    }

	public boolean isDarkfireResistant()
	{
		return this.isWither();
	}

	public boolean isCurseResistant()
	{
		return this.isWither();
	}

	public boolean isWitherResistant()
	{
		return this.isWither();
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