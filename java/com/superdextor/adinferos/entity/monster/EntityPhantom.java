package com.superdextor.adinferos.entity.monster;

import javax.annotation.Nullable;

import com.superdextor.adinferos.AdInferosSounds;
import com.superdextor.adinferos.blocks.BlockSpawner;
import com.superdextor.adinferos.config.NetherConfig;
import com.superdextor.adinferos.entity.ISoulEntity;
import com.superdextor.adinferos.entity.NetherMob;
import com.superdextor.adinferos.init.NetherItems;
import com.superdextor.thinkbigcore.entity.ai.EntityAIStare;

import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.EnumCreatureAttribute;
import net.minecraft.entity.IEntityLivingData;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.ai.EntityAIAttackMelee;
import net.minecraft.entity.ai.EntityAIHurtByTarget;
import net.minecraft.entity.ai.EntityAILookIdle;
import net.minecraft.entity.ai.EntityAINearestAttackableTarget;
import net.minecraft.entity.ai.EntityAISwimming;
import net.minecraft.entity.ai.EntityAIWander;
import net.minecraft.entity.ai.EntityAIWatchClosest;
import net.minecraft.entity.monster.EntityIronGolem;
import net.minecraft.entity.monster.EntityMob;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.MobEffects;
import net.minecraft.init.SoundEvents;
import net.minecraft.item.Item;
import net.minecraft.network.datasync.DataParameter;
import net.minecraft.network.datasync.DataSerializers;
import net.minecraft.network.datasync.EntityDataManager;
import net.minecraft.potion.PotionEffect;
import net.minecraft.util.DamageSource;
import net.minecraft.util.EnumParticleTypes;
import net.minecraft.util.SoundEvent;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.DifficultyInstance;
import net.minecraft.world.World;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class EntityPhantom extends EntityMob implements NetherMob, ISoulEntity {
	
	private final EntityAIAttackMelee AIAttackMelee = new EntityAIAttackMelee(this, 1.0D, false); 
	private final EntityAILookIdle AILookIdle = new EntityAILookIdle(this);
	private final EntityAIWatchClosest AIWatchPlayer = new EntityAIWatchClosest(this, EntityPlayer.class, 8.0F);
	private final EntityAIStare AIStateAtPlayer = new EntityAIStare(this, EntityPlayer.class, 16.0F);
	private final EntityAIWander AIWanderNormal = new EntityAIWander(this, 0.8D);
	private final EntityAIWander AIWanderWhileWatched = new EntityAIWander(this, 0.2D);
	
	private int watchTimer = 0;
	private boolean noFallDamage = false;
	private int watchCooldown = 0;
	private boolean disrupteWatch = false;
	
    private static final DataParameter<Boolean> IS_BURNING = EntityDataManager.<Boolean>createKey(EntityPhantom.class, DataSerializers.BOOLEAN);
    private static final DataParameter<Boolean> HAS_TARGET = EntityDataManager.<Boolean>createKey(EntityPhantom.class, DataSerializers.BOOLEAN);

	public EntityPhantom(World worldIn)
	{
		super(worldIn);
        this.setSize(0.6F, 1.9F);
        this.tasks.addTask(1, new EntityAISwimming(this));
        this.tasks.addTask(2, this.AIAttackMelee);
        this.tasks.addTask(3, this.AIWanderNormal);
        this.tasks.addTask(4, this.AIWatchPlayer);
        this.tasks.addTask(5, this.AILookIdle);
        this.targetTasks.addTask(1, new EntityAIHurtByTarget(this, true));
        this.targetTasks.addTask(2, new EntityAINearestAttackableTarget(this, EntityPlayer.class, true));
        this.targetTasks.addTask(3, new EntityAINearestAttackableTarget(this, EntityIronGolem.class, true));
        this.isImmuneToFire = true;
        this.experienceValue = 12;
	}
	
	protected void entityInit()
	{
		super.entityInit();
		this.getDataManager().register(IS_BURNING, false);
		this.getDataManager().register(HAS_TARGET, false);
	}
	
    protected void applyEntityAttributes()
    {
        super.applyEntityAttributes();
        this.getEntityAttribute(SharedMonsterAttributes.MAX_HEALTH).setBaseValue(40.0D);
        this.getEntityAttribute(SharedMonsterAttributes.MOVEMENT_SPEED).setBaseValue(0.5D);
        this.getEntityAttribute(SharedMonsterAttributes.ATTACK_DAMAGE).setBaseValue(7.0D);
    }
    
    @Nullable
    protected SoundEvent getAmbientSound()
    {
    	return this.isSneaking() ? null : AdInferosSounds.ENTITY_PHANTOM_IDLE;
    }

    @Nullable
    protected SoundEvent getHurtSound()
    {
    	return AdInferosSounds.ENTITY_PHANTOM_HURT;
    }

    @Nullable
    protected SoundEvent getDeathSound()
    {
    	return AdInferosSounds.ENTITY_PHANTOM_DEATH;
    }
    
    @SideOnly(Side.CLIENT)
    public int getBrightnessForRender(float f)
    {
        return 15728880;
    }
    
    private boolean isBeingWatchedByPlayer(EntityLivingBase entity)
    {
        Vec3d vec3 = entity.getLook(1.0F).normalize();
        Vec3d vec31 = new Vec3d(this.posX - entity.posX, this.getEntityBoundingBox().minY + (double)(this.height / 2.0F) - (entity.posY + (double)entity.getEyeHeight()), this.posZ - entity.posZ);
        double d0 = vec31.lengthVector();
        vec31 = vec31.normalize();
        double d1 = vec3.dotProduct(vec31);
        return d1 > 1.0D - 0.100D / d0 ? entity.canEntityBeSeen(this) : false;
    }
    
    public void onUpdate()
    {
    	if(!this.worldObj.isRemote)
    	{
        	if(this.getAttackTarget() != null)
        	{
        		EntityLivingBase target = this.getAttackTarget();
        	    if(this.isBeingWatchedByPlayer(target) && this.watchCooldown == 0 && !this.disrupteWatch) {
        	    	
        	    	if(this.worldObj.canSeeSky(this.getPosition()) && this.getBrightness(1.0F) > 0.5F && !this.worldObj.provider.doesWaterVaporize())
        	    	{
        	    		this.getDataManager().set(IS_BURNING, true);
        	    	}else
        	    	{
        	    		this.getDataManager().set(IS_BURNING, false);
        	    	}
        	    	
        	    	this.watchTimer++;
        	    	if(!this.isSneaking())
        	    	{
        	    		this.setSneaking(true);
        	            this.tasks.removeTask(this.AIAttackMelee);
        	            this.tasks.removeTask(this.AIWanderNormal);
        	            this.tasks.removeTask(this.AIWatchPlayer);
        	            this.tasks.removeTask(this.AILookIdle);
                        this.tasks.addTask(3, this.AIStateAtPlayer);
                        this.tasks.addTask(4, this.AIWanderWhileWatched);
        	    	}
        	    	else if(this.watchTimer == 10) 
        	    	{
    	    			this.playSound(SoundEvents.BLOCK_FIRE_EXTINGUISH, 1.0F, 1.4F);
        	    	}
        	    	else if(this.watchTimer > 10 && this.isBurning())
        	    	{
        	    		this.worldObj.removeEntity(this);
        	    	}
        	    	else if(this.watchTimer == 200)
        	    	{
    	    			this.playSound(SoundEvents.BLOCK_PORTAL_TRIGGER, 1.0F, 1.0F);
        	    	}
        	    	else if(this.watchTimer >= 250)
        	    	{
        	    		int l = 0;
        	    		
        	    		if(this.watchTimer > 500)
        	    		{
        	    			l = 6;
        	    		} else if(this.watchTimer > 460)
        	    		{
        	    			l = 5;
        	    		}else if(this.watchTimer > 430)
        	    		{
        	    			l = 4;
        	    		}else if(this.watchTimer > 400)
        	    		{
        	    			l = 3;
        	    		}else if(this.watchTimer > 360)
        	    		{
        	    			l = 2;
        	    		}else if(this.watchTimer > 300)
        	    		{
        	    			l = 1;
        	    		}
        	    		
        	    		this.addPotionEffect(new PotionEffect(MobEffects.LEVITATION, 4, l, false, false));
        	    		target.addPotionEffect(new PotionEffect(MobEffects.LEVITATION, 4, l, false, false));
        	    		target.addPotionEffect(new PotionEffect(MobEffects.NAUSEA, 130, 0, false, false));
        	    		this.noFallDamage = true;
        	    	}
        	    }
        	    else
        	    {
    	    		this.getDataManager().set(IS_BURNING, false);
        	    	this.disrupteWatch = false;
        	    	if(this.isSneaking())
        	    	{
                		this.setSneaking(false);
        	            this.tasks.removeTask(this.AIStateAtPlayer);
                        this.tasks.addTask(2, this.AIAttackMelee);
                        this.tasks.addTask(3, this.AIWanderNormal);
                        this.tasks.addTask(4, this.AIWatchPlayer);
                        this.tasks.addTask(5, this.AILookIdle);
                        this.watchCooldown = this.watchTimer;
                        
                        if(this.watchTimer > 10)
                        {
                            this.watchCooldown = 80;
                        }
                        if(this.watchTimer > 280)
                        {
                        	this.playSound(SoundEvents.ENTITY_ELDER_GUARDIAN_CURSE, 1.0F, 1.5F);
                        	target.addPotionEffect(new PotionEffect(MobEffects.BLINDNESS, 15, 0, false, false));
            	    		this.watchTimer = 0;
                            this.watchCooldown = 200;
                        }else if(this.watchTimer > 40)
                        {
        	    			this.playSound(SoundEvents.ENTITY_CAT_HISS, 1.0F, 0.3F);
                        }
        	    	}
        	    	
        	    	if(this.watchTimer > 0)
        	    	{
        	    		this.watchTimer--;
        	    	}
        	    	
        	    	if(this.watchCooldown > 0)
        	    	{
        	    		this.watchCooldown--;
        	    	}
        	    	
        	    }
        	}else if(this.isSneaking())
        	{
	    		this.getDataManager().set(IS_BURNING, false);
        		this.setSneaking(false);
	            this.tasks.removeTask(this.AIStateAtPlayer);
                this.tasks.addTask(2, this.AIAttackMelee);
                this.tasks.addTask(3, this.AIWanderNormal);
                this.tasks.addTask(4, this.AIWatchPlayer);
                this.tasks.addTask(5, this.AILookIdle);
        	}
    	}else if(this.isBurning())
    	{
    		for(int i = 0; i < 3; i++)
    		{
	    		this.worldObj.spawnParticle(EnumParticleTypes.SMOKE_LARGE, this.posX - 0.5D + this.rand.nextDouble(), this.posY, this.posZ - 0.5D + this.rand.nextDouble(), 0.0D, 0.3D, 0.0D, new int[] {});
    		}
    	}
    	
    	super.onUpdate();
    }
    
    public boolean isBurning()
    {
    	return this.getDataManager().get(IS_BURNING);
    }
    
    public void setAttackTarget(EntityLivingBase entitylivingbaseIn)
    {
    	super.setAttackTarget(entitylivingbaseIn);
    	
    	this.getDataManager().set(HAS_TARGET, entitylivingbaseIn != null);
    	
    }
    
    public boolean hasAttackTarget()
    {
    	return this.getDataManager().get(HAS_TARGET);
    }
    
    public void fall(float distance, float damageMultiplier)
    {
    	if(!this.noFallDamage)
    	{
        	super.fall(distance, damageMultiplier);
    	}
    	else
    	{
    		this.noFallDamage = false;
    	}
    }
    
    protected Item getDropItem()
    {
    	return NetherItems.ECTOPLASM;
    }
    
    /**
     * Get this Entity's EnumCreatureAttribute
     */
    public EnumCreatureAttribute getCreatureAttribute()
    {
        return EnumCreatureAttribute.UNDEAD;
    }
    
    public boolean attackEntityFrom(DamageSource source, float amount)
    {
    	if(source == DamageSource.wither)
    	{
    		return false;
    	}
    	
    	if(this.watchTimer > 12)
    	{
    		this.disrupteWatch = true;
    	}
    	
    	return super.attackEntityFrom(source, amount);
    }
    
    public IEntityLivingData onInitialSpawn(DifficultyInstance difficulty, IEntityLivingData livingdata)
    {
		NetherConfig.printDebugInfo(this.getName() + " was spawned at " + this.getPosition());
    	return super.onInitialSpawn(difficulty, livingdata);
    }
    
    public void onDeath(DamageSource cause)
    {
    	if(cause.getEntity() instanceof EntityPlayer) {
    		if(this.canMakeGhost()) {
    			EntityGhost.createGhost(this.worldObj, this, this);
    		}
    	}
    	
    	super.onDeath(cause);
    }

	public boolean canMakeGhost()
	{
		return NetherConfig.ghostSpawnFromEntities && !BlockSpawner.isSpawnerEntity(this) && this.rand.nextInt(10) == 0;
	}

	public String getTexture()
	{
		return "adinferos:textures/entity/phantom.png";
	}

	public void onCreateGhost(EntityGhost ghost) {}

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
		return false;
	}
	
	public boolean isSpikeResistant()
	{
		return true;
	}
}
