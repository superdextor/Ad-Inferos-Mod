package com.superdextor.adinferos.entity.monster;

import javax.annotation.Nullable;

import com.superdextor.adinferos.AdInferosAchievements;
import com.superdextor.adinferos.AdInferosSounds;
import com.superdextor.adinferos.config.NetherConfig;
import com.superdextor.adinferos.entity.NetherMob;
import com.superdextor.adinferos.init.NetherItems;
import com.superdextor.thinkbigcore.entity.EntityCustomThrowable;
import com.superdextor.thinkbigcore.entity.IFearedEntity;
import com.superdextor.thinkbigcore.entity.ai.EntityAIBreakblocks;

import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.EnumCreatureAttribute;
import net.minecraft.entity.IEntityLivingData;
import net.minecraft.entity.IRangedAttackMob;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.ai.EntityAIAttackMelee;
import net.minecraft.entity.ai.EntityAIHurtByTarget;
import net.minecraft.entity.ai.EntityAILookIdle;
import net.minecraft.entity.ai.EntityAINearestAttackableTarget;
import net.minecraft.entity.ai.EntityAISwimming;
import net.minecraft.entity.ai.EntityAIWander;
import net.minecraft.entity.monster.EntityGolem;
import net.minecraft.entity.monster.EntityMob;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.projectile.EntityLargeFireball;
import net.minecraft.entity.projectile.EntityWitherSkull;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.init.SoundEvents;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.network.datasync.DataParameter;
import net.minecraft.network.datasync.DataSerializers;
import net.minecraft.network.datasync.EntityDataManager;
import net.minecraft.potion.PotionEffect;
import net.minecraft.util.DamageSource;
import net.minecraft.util.EnumParticleTypes;
import net.minecraft.util.SoundEvent;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.DifficultyInstance;
import net.minecraft.world.World;

public class EntityInfernumAvis extends EntityMob implements NetherMob, IRangedAttackMob, IFearedEntity
{
	
    private static final DataParameter<Boolean> SLEEPING = EntityDataManager.<Boolean>createKey(EntityInfernumAvis.class, DataSerializers.BOOLEAN);
    private static final DataParameter<Integer> WING_ANIMATION = EntityDataManager.<Integer>createKey(EntityInfernumAvis.class, DataSerializers.VARINT);
    
    private EntityAIAttackMelee aiAttack = new EntityAIAttackMelee(this, 1.3D, false);
    private EntityAISwimming AiSwim = new EntityAISwimming(this);
    private EntityAIWander AiWander = new EntityAIWander(this, 1.0D);
    private EntityAILookIdle AiIdle = new EntityAILookIdle(this);
    private int specialtime = 0;
    private int awaketime = 0;
    private int shotDelay = 0;
    private int shotType = 2;
	
    public EntityInfernumAvis(World worldIn)
    {
        super(worldIn);
        this.isImmuneToFire = true;
        this.tasks.addTask(0, new EntityAIBreakblocks(this, 60.0F, true,false,false));
        this.targetTasks.addTask(0, new EntityAIHurtByTarget(this, false));
        this.targetTasks.addTask(1, new EntityAINearestAttackableTarget(this, EntityPlayer.class, true));
        this.targetTasks.addTask(2, new EntityAINearestAttackableTarget(this, EntityGolem.class, true));
        this.experienceValue = 80;
    }
    
    protected void entityInit()
    {
        super.entityInit();
        this.getDataManager().register(SLEEPING, true);
        this.getDataManager().register(WING_ANIMATION, 0);
    }
    
    protected void applyEntityAttributes()
    {
        super.applyEntityAttributes();
        this.getEntityAttribute(SharedMonsterAttributes.FOLLOW_RANGE).setBaseValue(50.0D);
        this.getEntityAttribute(SharedMonsterAttributes.MAX_HEALTH).setBaseValue(200.0D);
        this.getEntityAttribute(SharedMonsterAttributes.MOVEMENT_SPEED).setBaseValue(0.38D);
        this.getEntityAttribute(SharedMonsterAttributes.ATTACK_DAMAGE).setBaseValue(8.0D);
        this.getEntityAttribute(SharedMonsterAttributes.ARMOR).setBaseValue(8.0D);
    }
    
    public void onLivingUpdate()
    {
    	super.onLivingUpdate();
    	
    	if(!worldObj.isRemote && this.isEntityAlive())
    	{
            double d1;
            double d3;
            double d5;
    		
            if (this.isSleeping())
            {
            	
                if(this.ticksExisted % 40 == 0)
                {
                    this.heal(1.0F);
                }
                
    	    	this.motionX = 0;
    	    	this.motionY = 0;
    	    	
    	        if(this.isInWater() || this.isInLava())
    	        {
        	        this.setSleeping(false);
    	        }
            }
            else
            {
            	if(this.getAttackTarget() == null)
            	{
            		if(this.awaketime > 0)
            		{
            			this.awaketime--;
            			
                        if(this.awaketime < 2)
                        {
                            this.setSleeping(true);
                        }
            		}
            	}
            	else
            	{
                	--this.specialtime;
                	--this.shotDelay;
                	
                	if(this.specialtime < 180)
                	{
                    	this.setWignAnimation(1);
                        Entity target = this.getAttackTarget();

                        if (this.posY < target.posY ||this.posY < target.posY + 5.0D)
                        {
                            if (this.motionY < 0.0D)
                            {
                                this.motionY = 0.0D;
                            }

                            this.motionY += (0.5D - this.motionY) * 0.6000000238418579D;
                        }

                        double d0 = target.posX - this.posX;
                        d1 = target.posZ - this.posZ;
                        d3 = d0 * d0 + d1 * d1;

                        if (d3 > 9.0D)
                        {
                            d5 = (double)MathHelper.sqrt_double(d3);
                            this.motionX += (d0 / d5 * 0.5D - this.motionX) * 0.6000000238418579D;
                            this.motionZ += (d1 / d5 * 0.5D - this.motionZ) * 0.6000000238418579D;
                        }
                        
                        if(this.specialtime < 1)
                        {
                        	this.specialtime = 1200;
                        }
                        
                        if(this.shotDelay < 30 && this.ticksExisted % 4 == 0) {
                        	
                        	if(this.shotType == 2) {
                        		this.shotType = 0;
                        	}
                        	
                            this.shoot(); 
                            this.playSound(SoundEvents.ENTITY_WITHER_SHOOT, 1.0F, 1.0F / (this.getRNG().nextFloat() * 0.4F + 0.8F)); 
                        
                    		if(this.shotDelay < 0) {
                    			this.shotDelay = 80;
                    			this.shotType = this.rand.nextInt(2);
                    		}
                        }
                	}
                	else 
                	{
                		if(this.shotDelay < 20 && this.ticksExisted % 3 == 0) {
                  			this.shotType = 2;
                            this.shoot();
                            this.playSound(SoundEvents.ENTITY_GHAST_SHOOT, 1.0F, 1.0F / (this.getRNG().nextFloat() * 0.4F + 0.8F));
                            
                    		if(this.shotDelay < 0) {
                    			this.shotDelay = 50;
                    		}
                            
                		}
                	}
                }
            }
            
            if (this.isInWater() || this.isInLava() || this.onGround || isSleeping())
            {
            	this.setWignAnimation(0);
            }
            else if(this.motionY < 0.0D)
            {
            	this.setWignAnimation(2);
            }
            else
            {
            	this.setWignAnimation(1);
            }
            
            if(this.specialtime <= 0 && rand.nextFloat() < 0.4F)
            {
            	++this.specialtime;
            	
	            if(this.specialtime > 0) {
	            	this.specialtime = 100 + rand.nextInt(100); 
	            }
            }
    	}
    	
        float width = this.getHealth() / 150 + 0.6F;
        float height = this.getHealth() / 80 + 2.0F; 
        this.setSize(width, height);
        
        for (int i = 0; i < 5; ++i)
        {
            this.worldObj.spawnParticle(EnumParticleTypes.PORTAL, this.posX + (this.rand.nextDouble() - 0.5D) * (double)this.width, this.posY + this.rand.nextDouble() * (double)this.height - 0.25D, this.posZ + (this.rand.nextDouble() - 0.5D) * (double)this.width, (this.rand.nextDouble() - 0.5D) * 2.0D, -this.rand.nextDouble(), (this.rand.nextDouble() - 0.5D) * 2.0D, new int[0]);
        }
    }
    
    @Nullable
    protected SoundEvent getAmbientSound()
    {
        return this.isSleeping() ? AdInferosSounds.ENTITY_INFERNUMAVIS_SLEEPING : AdInferosSounds.ENTITY_INFERNUMAVIS_ANGRY;
    }
    
    @Nullable
    protected SoundEvent getHurtSound()
    {
        return AdInferosSounds.ENTITY_INFERNUMAVIS_HURT;
    }

    @Nullable
    protected SoundEvent getDeathSound()
    {
        return AdInferosSounds.ENTITY_INFERNUMAVIS_DEATH;
    }
    
    protected float getSoundVolume()
    {
    	return 1.4F;
    }
    
    public void fall(float distance, float damageMultiplier) {}
    
    public boolean attackEntityFrom(DamageSource source, float damage)
    {
    	if(source == DamageSource.wither) {
    		return false;
    	}
    	
    	if(this.isSleeping()) {
        	this.setSleeping(false);
        	if(source.getEntity() != null && source.getEntity() instanceof EntityPlayer) {
            	((EntityPlayer)source.getEntity()).addStat(AdInferosAchievements.wakeAvis);
        	}
    	}
    	else if(source.getEntity() instanceof EntityLivingBase && !(source.getEntity() instanceof EntityPlayer && ((EntityPlayer)source.getEntity()).isCreative()) && this.rand.nextBoolean()) {
    		this.setAttackTarget((EntityLivingBase) source.getEntity());
    	}
    	
        return super.attackEntityFrom(source, damage);
    }
    
    public void onDeath(DamageSource cause) {
    	
    	this.playSound(AdInferosSounds.MUSIC_VICTORY, 1.0F, 1.0F);
    	
    	super.onDeath(cause);
    }
    
    @Nullable
    protected Item getDropItem()
    {
    	return NetherItems.AVIS_WING;
    }
    
    @Nullable
    protected void dropFewItems(boolean wasRecentlyHit, int lootingModifier)
    {
    	this.dropItem(NetherItems.AVIS_WING, 2);
        this.dropItem(Item.getItemFromBlock(Blocks.GOLD_BLOCK), 2 + this.rand.nextInt(3));
    	
        int i = 8 + this.rand.nextInt(9);

        if (lootingModifier > 0)
        {
            i += this.rand.nextInt(lootingModifier + 1);
        }

        this.dropItem(NetherItems.COOKED_FLESH, i);
        
        i = 8 + this.rand.nextInt(9);

        if (lootingModifier > 0)
        {
            i += this.rand.nextInt(lootingModifier + 1);
        }

        this.dropItem(NetherItems.WITHER_DUST, i);
    }
    
    protected void shoot()
    {
	    this.getLookHelper().setLookPositionWithEntity(this.getAttackTarget(), 30.0F, 30.0F);
	    this.attackEntityWithRangedAttack(this.getAttackTarget(), 0.0F); 
    }
    
    protected boolean canDespawn()
    {
        return false;
    }
    
    public IEntityLivingData onInitialSpawn(DifficultyInstance difficulty, IEntityLivingData livingdata)
    {
		NetherConfig.printDebugInfo(this.getName() + " was spawned at " + this.getPosition());
    	return super.onInitialSpawn(difficulty, livingdata);
    }
    
    public void attackEntityWithRangedAttack(EntityLivingBase target, float var)
    {
    	switch(this.shotType) {
    	
	    	case 0: {
	            double d1 = this.width;
	            Vec3d vec3d = this.getLook(1.0F);
	            double d2 = target.posX - (this.posX + vec3d.xCoord * d1);
	            double d3 = target.getEntityBoundingBox().minY + (double)(target.height / 2.0F) - (0.5D + this.posY + (double)(this.height / 2.0F));
	            double d4 = target.posZ - (this.posZ + vec3d.zCoord * d1);
	            this.worldObj.playEvent((EntityPlayer)null, 1016, new BlockPos(this), 0);
	            EntityLargeFireball entitylargefireball = new EntityLargeFireball(this.worldObj, this, d2, d3, d4);
	            entitylargefireball.posX = this.posX + vec3d.xCoord * d1;
	            entitylargefireball.posY = this.posY + (double)(this.height / 2.0F) + 0.5D;
	            entitylargefireball.posZ = this.posZ + vec3d.zCoord * d1;
	            this.worldObj.spawnEntityInWorld(entitylargefireball);
	    		break;
	    	}
    		
	    	case 1: {
	            double d1 = this.width;
	            Vec3d vec3d = this.getLook(1.0F);
	            double d2 = target.posX - (this.posX + vec3d.xCoord * d1);
	            double d3 = target.getEntityBoundingBox().minY + (double)(target.height / 2.0F) - (0.5D + this.posY + (double)(this.height / 2.0F));
	            double d4 = target.posZ - (this.posZ + vec3d.zCoord * d1);
	            this.worldObj.playEvent((EntityPlayer)null, 1016, new BlockPos(this), 0);
	            EntityWitherSkull entityWitherSkull = new EntityWitherSkull(this.worldObj, this, d2, d3, d4);
	            entityWitherSkull.posX = this.posX + vec3d.xCoord * d1;
	            entityWitherSkull.posY = this.posY + (double)(this.height / 2.0F) + 0.5D;
	            entityWitherSkull.posZ = this.posZ + vec3d.zCoord * d1;
	            this.worldObj.spawnEntityInWorld(entityWitherSkull);
	    		break;
	    	}
    		
	    	default: {
	    		EntityCustomThrowable customThrowable = new EntityCustomThrowable(this.worldObj, this);
	    		customThrowable.setItem(new ItemStack(Items.BLAZE_POWDER));
	    		customThrowable.setBlock(Blocks.FIRE.getDefaultState());
	    		customThrowable.setFireTime(3);
	    		customThrowable.setDamageSource(DamageSource.inFire);
	            double d0 = target.posY + (double)target.getEyeHeight() - 1.100000023841858D;
	            double d1 = target.posX - this.posX;
	            double d2 = d0 - customThrowable.posY;
	            double d3 = target.posZ - this.posZ;
	            float f1 = MathHelper.sqrt_double(d1 * d1 + d3 * d3) * 0.2F;
	            customThrowable.setThrowableHeading(d1, d2 + (double)f1, d3, 1.6F, 12.0F);
	            double d4 = this.width;
	            Vec3d vec3d = this.getLook(1.0F);
	            customThrowable.posX = this.posX + vec3d.xCoord * d4;
	            customThrowable.posY = this.posY + (double)(this.height / 2.0F) + 0.5D;
	            customThrowable.posZ = this.posZ + vec3d.zCoord * d4;
	            this.worldObj.spawnEntityInWorld(customThrowable);
	    		break;
	    	}
    	}
    }
    
    protected void collideWithEntity(Entity entityIn)
    {
    	
    	if(entityIn instanceof EntityPlayer && !((EntityPlayer) entityIn).capabilities.isCreativeMode) {
    		this.attackEntityAsMob(entityIn);
        	this.setSleeping(false);
    	}
    }
    
    public boolean isSleeping()
    {
        return this.getDataManager().get(SLEEPING);
    }
    
    public void setSleeping(boolean sleeping)
    {
    	if(this.isSleeping() != sleeping) {
        	if(sleeping) {
                this.tasks.removeTask(this.aiAttack);
                this.tasks.removeTask(this.AiSwim);
                this.tasks.removeTask(this.AiWander);
                this.tasks.removeTask(this.AiIdle);
                this.awaketime = 0;
                this.shotDelay = 0;
                this.specialtime = 0;
        	}
        	else
        	{
                this.tasks.addTask(0, this.AiSwim);
                this.tasks.addTask(1, this.aiAttack);
                this.tasks.addTask(2, this.AiWander);
                this.tasks.addTask(3, this.AiIdle);
                this.playSound(AdInferosSounds.ENTITY_INFERNUMAVIS_AWAKED, 1.4F, 0.8F);
                this.awaketime = 2000;
                this.shotDelay = 30;
                this.specialtime = 300;
        	}
    	}
    	this.getDataManager().set(SLEEPING, sleeping);
    }

    public int getWignAnimation()
    {
    	return this.getDataManager().get(WING_ANIMATION);
    }
    
    public void setWignAnimation(int animation)
    {
    	
    	if(!this.worldObj.isRemote && this.getWignAnimation() != animation)
    		this.getDataManager().set(WING_ANIMATION, animation);
    }
    
    public EnumCreatureAttribute getCreatureAttribute()
    {
        return EnumCreatureAttribute.UNDEAD;
    }
	
	public void readEntityFromNBT(NBTTagCompound tagCompund)
	{
		super.readEntityFromNBT(tagCompund);
		
        if (tagCompund.hasKey("IsSleeping", 1))
        {
            this.setSleeping(tagCompund.getBoolean("IsSleeping"));
        }
	}
	
	public void writeEntityToNBT(NBTTagCompound tagCompound)
	{
		super.writeEntityToNBT(tagCompound);
        tagCompound.setBoolean("IsSleeping", this.isSleeping());
	}
	
    public boolean isNonBoss()
    {
    	return false;
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
		return true;
	}

	public boolean isAcidResistant()
	{
		return true;
	}

	public boolean isSpikeResistant()
	{
		return true;
	}

	public int getFearDelay()
	{
		return 9;
	}

	public int getFearAmount()
	{
		return 12;
	}

	public double getFearRange()
	{
		return 500.0D;
	}

	public PotionEffect getFearEffect()
	{
		return null;
	}

	public double getEffectRange()
	{
		return 0;
	}

	public boolean effectOnTargetOnly()
	{
		return false;
	}
}