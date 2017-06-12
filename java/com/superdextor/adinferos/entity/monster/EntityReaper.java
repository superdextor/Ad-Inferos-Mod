package com.superdextor.adinferos.entity.monster;

import java.util.Calendar;
import java.util.UUID;

import javax.annotation.Nullable;

import com.google.common.base.Predicate;
import com.superdextor.adinferos.AdInferosCore;
import com.superdextor.adinferos.AdInferosSounds;
import com.superdextor.adinferos.config.NetherConfig;
import com.superdextor.adinferos.entity.ISoulEntity;
import com.superdextor.adinferos.entity.NetherMob;
import com.superdextor.adinferos.init.NetherItems;
import com.superdextor.thinkbigcore.entity.IFearedEntity;

import net.minecraft.entity.Entity;
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
import net.minecraft.entity.ai.attributes.AttributeModifier;
import net.minecraft.entity.ai.attributes.IAttributeInstance;
import net.minecraft.entity.monster.EntityIronGolem;
import net.minecraft.entity.monster.EntityMob;
import net.minecraft.entity.passive.EntityAnimal;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.init.MobEffects;
import net.minecraft.init.SoundEvents;
import net.minecraft.inventory.EntityEquipmentSlot;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.network.datasync.DataParameter;
import net.minecraft.network.datasync.DataSerializers;
import net.minecraft.network.datasync.EntityDataManager;
import net.minecraft.potion.PotionEffect;
import net.minecraft.util.DamageSource;
import net.minecraft.util.EnumHand;
import net.minecraft.util.EnumParticleTypes;
import net.minecraft.util.SoundEvent;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.DifficultyInstance;
import net.minecraft.world.World;

public class EntityReaper extends EntityMob implements NetherMob, IFearedEntity, ISoulEntity
{
    private static final UUID attackingSpeedBoostModifierUUID = UUID.fromString("020E0DFB-87AE-4653-9556-831010E291A0");
    private static final AttributeModifier attackingSpeedBoostModifier = (new AttributeModifier(attackingSpeedBoostModifierUUID, "Attacking speed boost", 0.15000000596046448D, 0)).setSaved(false);
    private static final UUID babySpeedBoostUUID = UUID.fromString("B9766B59-9566-4402-BC1F-2EE2A276D836");
    private static final AttributeModifier babySpeedBoostModifier = new AttributeModifier(babySpeedBoostUUID, "Baby speed boost", 0.5D, 1);
    
    private static final DataParameter<Boolean> IS_BABY = EntityDataManager.<Boolean>createKey(EntityReaper.class, DataSerializers.BOOLEAN);
    private static final DataParameter<Boolean> SCREAMING = EntityDataManager.<Boolean>createKey(EntityReaper.class, DataSerializers.BOOLEAN);
    private static final DataParameter<Float> ALPHA = EntityDataManager.<Float>createKey(EntityReaper.class, DataSerializers.FLOAT);
    private static final DataParameter<Integer> EYE_BRIGHTNESS = EntityDataManager.<Integer>createKey(EntityReaper.class, DataSerializers.VARINT);
    private static final DataParameter<Boolean> CLOAKING = EntityDataManager.<Boolean>createKey(EntityReaper.class, DataSerializers.BOOLEAN);
    private int cloakingTimer = 0;
    private EntityLivingBase savedTarget = null;
    private int timeSinceLastTarget = 0;
    
    public EntityReaper(World worldIn)
    {
        super(worldIn);
        this.stepHeight = 1.0F;
        this.experienceValue = 20;
        this.isImmuneToFire = true;
        this.tasks.addTask(0, new EntityAISwimming(this));
        this.tasks.addTask(2, new EntityAIAttackMelee(this, 1.0D, false));
        this.tasks.addTask(7, new EntityAIWander(this, 1.0D));
        this.tasks.addTask(8, new EntityAIWatchClosest(this, EntityPlayer.class, 8.0F));
        this.tasks.addTask(8, new EntityAILookIdle(this));
        this.targetTasks.addTask(0, new EntityAIHurtByTarget(this, false, new Class[0]));
        this.targetTasks.addTask(1, new EntityAINearestAttackableTarget(this, EntityPlayer.class, 10, true, false, new Predicate<EntityPlayer>() { 
        	public boolean apply(final EntityPlayer input)
        	{
        		
        		if(input.isPotionActive(MobEffects.LUCK))
        		{
        			return false;
        		}
        		
                for (EntityReaper reaper : input.worldObj.getEntities(EntityReaper.class, new Predicate<EntityReaper>()
                {
                    public boolean apply(EntityReaper reaper)
                        {
                            return reaper.isEntityAlive() && reaper.getAttackTarget() == input;
                        }
                    }))
                    {
                		return false;
                    }
        		
        		return true;
        	}
        }));
        this.targetTasks.addTask(2, new EntityAINearestAttackableTarget(this, EntityIronGolem.class, true));
        this.targetTasks.addTask(4, new EntityAINearestAttackableTarget(this, EntityAnimal.class, 10, true, false, new Predicate<EntityAnimal>() {
        	
        	public boolean apply(EntityAnimal input) {
        		return !(input instanceof NetherMob);
        	}
        }));
    }

    protected void applyEntityAttributes()
    {
        super.applyEntityAttributes();
        this.getEntityAttribute(SharedMonsterAttributes.MAX_HEALTH).setBaseValue(100.0D);
        this.getEntityAttribute(SharedMonsterAttributes.MOVEMENT_SPEED).setBaseValue(0.30000001192092896D);
        this.getEntityAttribute(SharedMonsterAttributes.FOLLOW_RANGE).setBaseValue(64.0D);
    }

    protected void entityInit()
    {
        super.entityInit();
        this.getDataManager().register(IS_BABY,false);
        this.getDataManager().register(SCREAMING,false);
        this.getDataManager().register(ALPHA,0.0F);
        this.getDataManager().register(EYE_BRIGHTNESS, 0);
        this.getDataManager().register(CLOAKING,false);
    }
    
    public boolean isChild()
    {
    	return this.getDataManager().get(IS_BABY);
    }
    
    public void setChild(boolean child)
    {
    	this.getDataManager().set(IS_BABY, child);
        this.setSize(0.4F, 0.3F);
        
        if (this.worldObj != null && !this.worldObj.isRemote)
        {
            IAttributeInstance iattributeinstance = this.getEntityAttribute(SharedMonsterAttributes.MOVEMENT_SPEED);
            iattributeinstance.removeModifier(babySpeedBoostModifier);

            if (child)
            {
                iattributeinstance.applyModifier(babySpeedBoostModifier);
            }
        }
    }

    /**
     * (abstract) Protected helper method to write subclass entity data to NBT.
     */
    public void writeEntityToNBT(NBTTagCompound tagCompound)
    {
        super.writeEntityToNBT(tagCompound);

        if (this.isChild())
        {
        	tagCompound.setBoolean("IsBaby", true);
        }
    }

    /**
     * (abstract) Protected helper method to read subclass entity data from NBT.
     */
    public void readEntityFromNBT(NBTTagCompound tagCompund)
    {
        super.readEntityFromNBT(tagCompund);
        
        if (tagCompund.getBoolean("IsBaby"))
        {
            this.setChild(true);
        }
    }

    /**
     * Checks to see if this enderman should be attacking this player
     */
    private boolean shouldAttackTarget(EntityLivingBase entity)
    {
        Vec3d vec3 = entity.getLook(1.0F).normalize();
        Vec3d vec31 = new Vec3d(this.posX - entity.posX, this.getEntityBoundingBox().minY + (double)(this.height / 2.0F) - (entity.posY + (double)entity.getEyeHeight()), this.posZ - entity.posZ);
        double d0 = vec31.lengthVector();
        vec31 = vec31.normalize();
        double d1 = vec3.dotProduct(vec31);
        return d1 > 1.0D - 0.025D / d0 ? entity.canEntityBeSeen(this) : false;
    }

    public float getEyeHeight()
    {
        float f = 1.74F;

        if (this.isChild())
        {
            f = (float)((double)f - 0.81D);
        }

        return f;
    }

    /**
     * Called frequently so the entity can update its state every tick as required. For example, zombies and skeletons
     * use this to react to sunlight and start to burn.
     */
    public void onLivingUpdate()
    {
    	
    	if(!this.worldObj.isRemote)
    	{
        	if(this.isSneaking())
        	{
        		this.setAlpha(0);
        	    this.setSneaking(false);
        	}
        	
        	if(this.getAlpha() < 1.0F) {
        		this.setAlpha(getAlpha() + 0.03F);
        	}
        	
    	}
    	
        if (this.worldObj.isRemote)
        {
            for (int i = 0; i < 4; ++i)
            {
                this.worldObj.spawnParticle(EnumParticleTypes.SUSPENDED_DEPTH, this.posX + this.rand.nextDouble() - 0.5D, this.posY + this.rand.nextDouble() + this.rand.nextDouble() + 0.05D, this.posZ + this.rand.nextDouble() - 0.5D, (this.rand.nextDouble() - 0.5D) * 2.0D, -this.rand.nextDouble(), (this.rand.nextDouble() - 0.5D) * 2.0D, new int[0]);
            }
        }
        
        if (!this.worldObj.isRemote && this.isEntityAlive())
        {
            if (this.getAttackTarget() != null)
            {
            	this.timeSinceLastTarget = 2000;
            	this.setScreaming(true);
            	if(this.getEyeBrightness() < 12000 && !this.isCloaking()) {
                	this.setEyeBrightness(this.getEyeBrightness() + 30);
            	}
            	
                if (this.shouldAttackTarget(this.getAttackTarget()) && !this.isCloaking())
                {
                    if(this.getEyeBrightness() >= 12000) {
                		this.swingArm(EnumHand.MAIN_HAND);
                		this.swingArm(EnumHand.OFF_HAND);
                		this.getAttackTarget().addPotionEffect(new PotionEffect(MobEffects.BLINDNESS, 15, 0));
                		this.playSound(AdInferosSounds.ENTITY_REAPER_CLOAK, 2.0F, 0.2F);
                		this.cloakingTimer = 160 * this.getDiffculityFactor();
                		this.setCloaking(true);
                	}
                    else if (this.getAttackTarget().getDistanceSqToEntity(this) < 16.0D) {
                    	if(this.rand.nextFloat() < 0.2F * this.getDiffculityFactor()) {
                            this.teleportRandomly();
                    	}else {
                        	this.setEyeBrightness(this.getEyeBrightness() + 40 * this.getDiffculityFactor());
                    	}
                    }
                }
                
                if(this.getDistanceSqToEntity(this.getAttackTarget()) > 500.0D) {
                	this.teleportToEntity(this.getAttackTarget());
                }
            }
            else
            {
            	if(this.timeSinceLastTarget > 1) {
            		--this.timeSinceLastTarget;
            	}
            	
                this.setScreaming(false);
                this.setEyeBrightness(0);
            }
            
            if(this.isCloaking()) {
            	
            	if(this.cloakingTimer > 0) {
                	this.cloakingTimer--;
            	}else {
            		this.setCloaking(false);
            		this.setEyeBrightness(0);
            	}
            }
            
        }

        super.onLivingUpdate();
    }
    
    public boolean attackEntityFrom(DamageSource source, float amount)
    {
    	if((this.getAlpha() < 0.7F || (this.isCloaking() && this.rand.nextFloat() < 0.3F * this.getDiffculityFactor())) && !source.canHarmInCreative() && !source.isCreativePlayer())
    	{
    		this.playSound(AdInferosSounds.ENTITY_REAPER_DODGE, 1.2F, 0.7F);
    		
    		this.cloakingTimer -= 40;
    		
    		if(this.cloakingTimer < 0)
    		{
    			this.cloakingTimer = 0;
    		}
    		
    		return false;
    	}
    	else if(this.isInWater() || this.isInLava() && this.rand.nextBoolean())
    	{
    		this.teleportRandomly();
    	}
    	
    	this.setAlpha(0.4F);
    	
    	return super.attackEntityFrom(source, amount);
    }
    
    protected boolean canDespawn()
    {
    	return this.getAttackTarget() == null && this.timeSinceLastTarget < 1;
    }
    
    protected void dropEquipment(boolean wasRecentlyHit, int lootingModifier)
    {
    	if(wasRecentlyHit)
    	{
    		this.entityDropItem(new ItemStack(NetherItems.GOLDEN_BUCKET_BLOOD, 1, 1), 0.0F);
    	}
    	
    	super.dropEquipment(wasRecentlyHit, lootingModifier);
    }

    protected boolean teleportRandomly()
    {
        double d0 = this.posX + (this.rand.nextDouble() - 0.5D) * 64.0D;
        double d1 = this.posY + (double)(this.rand.nextInt(64) - 32);
        double d2 = this.posZ + (this.rand.nextDouble() - 0.5D) * 64.0D;
        
        if(this.getAttackTarget() != null)
        {
            double k0 = d0 - this.getAttackTarget().posX;
            double k1 = d1 - this.getAttackTarget().posY;
            double k2 = d2 - this.getAttackTarget().posZ;
            
            if(k0 * k0 + k1 * k1 + k2 * k2 > 400.0D)
            {
                return this.teleportToEntity(this.getAttackTarget());
            }
        }
        
        return this.teleportTo(d0, d1, d2);
    }

    protected boolean teleportToEntity(Entity target)
    {
        Vec3d vec3d = new Vec3d(this.posX - target.posX, this.getEntityBoundingBox().minY + (double)(this.height / 2.0F) - target.posY + (double)target.getEyeHeight(), this.posZ - target.posZ);
        vec3d = vec3d.normalize();
        double d0 = 16.0D;
        double d1 = this.posX + (this.rand.nextDouble() - 0.5D) * 8.0D - vec3d.xCoord * d0;
        double d2 = this.posY + (double)(this.rand.nextInt(16) - 8) - vec3d.yCoord * d0;
        double d3 = this.posZ + (this.rand.nextDouble() - 0.5D) * 8.0D - vec3d.zCoord * d0;
        return this.teleportTo(d1, d2, d3);
    }

    private boolean teleportTo(double x, double y, double z)
    {
    	if(this.cloakingTimer > 0) {
    		this.cloakingTimer -= 60;
    		if(this.cloakingTimer < 0) {
    			this.cloakingTimer = 0;
    		}
    	}
    	double soundX = this.posX;
    	double soundY = this.posY;
    	double soundZ = this.posZ;
    	
        net.minecraftforge.event.entity.living.EnderTeleportEvent event = new net.minecraftforge.event.entity.living.EnderTeleportEvent(this, x, y, z, 0);
        if (net.minecraftforge.common.MinecraftForge.EVENT_BUS.post(event)) return false;
        boolean flag = this.attemptTeleport(event.getTargetX(), event.getTargetY(), event.getTargetZ());

        if (flag)
        {
            this.worldObj.playSound((EntityPlayer)null, soundX, soundY, soundZ, SoundEvents.ENTITY_ENDERMEN_TELEPORT, this.getSoundCategory(), 1.0F, 1.0F);
            this.playSound(SoundEvents.ENTITY_ENDERMEN_TELEPORT, 1.0F, 1.0F);
            this.setAlpha(0.0F);
        }

        return flag;
    }
    
    @Nullable
    protected SoundEvent getAmbientSound()
    {
        return this.isScreaming() ? AdInferosSounds.ENTITY_REAPER_ANGRY : AdInferosSounds.ENTITY_REAPER_IDLE;
    }

    @Nullable
    protected SoundEvent getHurtSound()
    {
        return AdInferosSounds.ENTITY_REAPER_HURT;
    }

    @Nullable
    protected SoundEvent getDeathSound()
    {
        return AdInferosSounds.ENTITY_REAPER_DEATH;
    }

    @Nullable
	protected Item getDropItem() {
		return NetherItems.NETHERITE_NUGGET;
	}
	
	protected void dropFewItems(boolean wasRecentlyHit, int lootingModifier)
	{
        int i = this.rand.nextInt(2);

        if (lootingModifier > 0)
        {
            i += this.rand.nextInt(lootingModifier + 1);
        }

        if(i > 0)
        {
            this.dropItem(Items.ENDER_PEARL, i);
        }
        
        i = 3 + this.rand.nextInt(5);

        if (lootingModifier > 0)
        {
            i += this.rand.nextInt(lootingModifier + 1);
        }

        this.dropItem(NetherItems.NETHERITE_NUGGET, i);
        
        i = 4 + this.rand.nextInt(7);

        if (lootingModifier > 0)
        {
            i += this.rand.nextInt(lootingModifier + 1);
        }

        this.dropItem(NetherItems.WITHER_DUST, i);
	}
    
    /**
     * Makes entity wear random armor based on difficulty
     */
    protected void setEquipmentBasedOnDifficulty(DifficultyInstance difficulty)
    {
        super.setEquipmentBasedOnDifficulty(difficulty);
        this.setItemStackToSlot(EntityEquipmentSlot.MAINHAND, new ItemStack(NetherItems.SCYTHE));
        this.setDropChance(EntityEquipmentSlot.MAINHAND, 0.0F);
    }
    
    public boolean getCanSpawnHere()
    {
    	if(this.worldObj.provider.getDimension() == -1 && !AdInferosCore.proxy.isDarkNether()) {
    		return false;
    	}
    	
    	return super.getCanSpawnHere();
    }
    
    public IEntityLivingData onInitialSpawn(DifficultyInstance difficulty, IEntityLivingData idata)
    {
    	idata = super.onInitialSpawn(difficulty, idata);
        {
            this.setEquipmentBasedOnDifficulty(difficulty);
            this.setEnchantmentBasedOnDifficulty(difficulty);
            
            if(this.rand.nextFloat() < 0.01F) {
            	this.setChild(true);
            }
        }

        this.setCanPickUpLoot(this.rand.nextFloat() < 0.55F * difficulty.getClampedAdditionalDifficulty());

        if (this.getItemStackFromSlot(EntityEquipmentSlot.HEAD) == null)
        {
            Calendar calendar = this.worldObj.getCurrentDate();

            if (calendar.get(2) + 1 == 10 && calendar.get(5) == 31 && this.rand.nextFloat() < 0.25F)
            {
                this.setItemStackToSlot(EntityEquipmentSlot.HEAD, new ItemStack(this.rand.nextFloat() < 0.1F ? Blocks.LIT_PUMPKIN : Blocks.PUMPKIN));
                this.inventoryArmorDropChances[EntityEquipmentSlot.HEAD.getIndex()] = 0.0F;
            }
        }
        
		NetherConfig.printDebugInfo(this.getName() + " was spawned at " + this.getPosition());

        return idata;
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

    public boolean isScreaming()
    {
    	return this.getDataManager().get(SCREAMING);
    }

    public void setScreaming(boolean screaming)
    {
    	this.getDataManager().set(SCREAMING, screaming);
    }
    
    public float getAlpha()
    {
    	return this.getDataManager().get(ALPHA);
    }
    
    public void setAlpha(float value)
    {
    	this.getDataManager().set(ALPHA, value);
    }
    
    public int getEyeBrightness()
    {
    	return this.getDataManager().get(EYE_BRIGHTNESS);
    }
    
    public void setEyeBrightness(int brightness)
    {
    	this.getDataManager().set(EYE_BRIGHTNESS, brightness);
    }
    
    public boolean isCloaking()
    {
    	return this.getDataManager().get(CLOAKING);
    }
    
    public void setCloaking(boolean cloaking)
    {
    	this.getDataManager().set(CLOAKING, cloaking);
    }
    
    public EnumCreatureAttribute getCreatureAttribute()
    {
        return EnumCreatureAttribute.UNDEAD;
    }
    
    public boolean attackEntityAsMob(Entity target)
    {
        if (super.attackEntityAsMob(target))
        {
            if (target instanceof EntityLivingBase)
            {
            	((EntityLivingBase) target).addPotionEffect(new PotionEffect(MobEffects.BLINDNESS, 12, 0));
            }

            return true;
        }
        else
        {
            return false;
        }
    }
    
    @Nullable
    public EntityLivingBase getAttackTarget()
    {
    	return super.getAttackTarget() == null ? this.savedTarget : super.getAttackTarget();
    }
    
    public void setAttackTarget(EntityLivingBase entitylivingbaseIn) {
    	
    	if(this.getAttackTarget() != null && entitylivingbaseIn == null) {
    		if(this.getAttackTarget().isEntityAlive()) {
    			this.savedTarget = this.getAttackTarget();
    		}else {
    			this.savedTarget = entitylivingbaseIn;
    		}
    	}else if(entitylivingbaseIn != null){
    		this.savedTarget = entitylivingbaseIn;
    	}
    	
    	super.setAttackTarget(entitylivingbaseIn);
    }
    
	public boolean canMakeGhost()
	{
		return NetherConfig.ghostSpawnFromEntities && !this.isChild() && this.rand.nextBoolean();
	}

	public String getTexture()
	{
		return "adinferos:textures/entity/reaper.png";
	}

	public void onCreateGhost(EntityGhost ghost)
	{
		ghost.setHeldItem(EnumHand.MAIN_HAND, this.getHeldItemMainhand());
		ghost.setDropChance(EntityEquipmentSlot.MAINHAND, 1.0F);
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
	
	private int getDiffculityFactor()
	{
		
		switch(this.worldObj.getDifficulty())
		{
			default:
				return 0;
			case EASY:
				return 1;
				
			case NORMAL:
				return 2;
				
			case HARD:
				return 3;
		}
	}

	public int getFearDelay()
	{
		return 10;
	}

	public int getFearAmount()
	{
		return 13;
	}

	public double getFearRange()
	{
		return 900.0D;
	}

	public PotionEffect getFearEffect()
	{
		return new PotionEffect(MobEffects.NAUSEA, 90, 0, false, false);
	}

	public double getEffectRange()
	{
		return 80.0D;
	}

	public boolean effectOnTargetOnly()
	{
		return true;
	}
}