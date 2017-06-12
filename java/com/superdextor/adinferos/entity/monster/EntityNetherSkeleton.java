package com.superdextor.adinferos.entity.monster;

import java.util.Calendar;
import java.util.UUID;

import javax.annotation.Nullable;

import com.google.common.base.Predicate;
import com.superdextor.adinferos.AdInferosSounds;
import com.superdextor.adinferos.blocks.BlockSpawner;
import com.superdextor.adinferos.config.NetherConfig;
import com.superdextor.adinferos.entity.ISoulEntity;
import com.superdextor.adinferos.entity.NetherMob;
import com.superdextor.adinferos.init.NetherItems;
import com.superdextor.adinferos.world.WorldProviderAbyss;
import com.superdextor.thinkbigcore.helpers.EntityHelper;

import net.minecraft.block.Block;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.EnumCreatureAttribute;
import net.minecraft.entity.IEntityLivingData;
import net.minecraft.entity.IRangedAttackMob;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.ai.EntityAIAttackMelee;
import net.minecraft.entity.ai.EntityAIAttackRanged;
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
import net.minecraft.util.DamageSource;
import net.minecraft.util.SoundEvent;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.DifficultyInstance;
import net.minecraft.world.World;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public abstract class EntityNetherSkeleton extends EntityMob implements IRangedAttackMob, NetherMob, ISoulEntity
{
	
    private static final DataParameter<Boolean> IS_BABY = EntityDataManager.<Boolean>createKey(EntityNetherSkeleton.class, DataSerializers.BOOLEAN);
    private static final DataParameter<Boolean> IS_USING_RANGE = EntityDataManager.<Boolean>createKey(EntityNetherSkeleton.class, DataSerializers.BOOLEAN);
	
    protected EntityAIAttackMelee aiAttackOnCollide = new EntityAIAttackMelee(this, 1.2D, false);
    
    private static final UUID babySpeedBoostUUID = UUID.fromString("B9766B59-9566-4402-BC1F-2EE2A276D836");
    private static final AttributeModifier babySpeedBoostModifier = new AttributeModifier(babySpeedBoostUUID, "Baby speed boost", 0.5D, 1);
    
    public EntityNetherSkeleton(World world)
    {
        super(world);
        if(NetherConfig.glowstoneSkeletonFireResistant)
        {
            this.isImmuneToFire = true;
        }
        this.setSize(0.4F, 1.8F);
        this.experienceValue = NetherConfig.glowstoneSkeletonEXP;
        this.tasks.addTask(1, new EntityAISwimming(this));
        this.tasks.addTask(5, new EntityAIWander(this, 1.0D));
        this.tasks.addTask(6, new EntityAIWatchClosest(this, EntityPlayer.class, 8.0F));
        this.tasks.addTask(6, new EntityAILookIdle(this));
        this.targetTasks.addTask(1, new EntityAIHurtByTarget(this, true));
        this.targetTasks.addTask(2, new EntityAINearestAttackableTarget(this, EntityPlayer.class, 10, true, false, new Predicate<EntityPlayer>() {

			public boolean apply(EntityPlayer input)
			{
				return !input.isPotionActive(MobEffects.LUCK);
			} 
        	
        }));
        this.targetTasks.addTask(3, new EntityAINearestAttackableTarget(this, EntityIronGolem.class, true));

        if (world != null && !world.isRemote)
        {
            this.setCombatTask();
        }
    }

    protected void applyEntityAttributes()
    {
        super.applyEntityAttributes();
        this.getEntityAttribute(SharedMonsterAttributes.MAX_HEALTH).setBaseValue(NetherConfig.glowstoneSkeletonHealth);
        this.getEntityAttribute(SharedMonsterAttributes.ATTACK_DAMAGE).setBaseValue(NetherConfig.glowstoneSkeletonDamage);
        this.getEntityAttribute(SharedMonsterAttributes.MOVEMENT_SPEED).setBaseValue(NetherConfig.glowstoneSkeletonSpeed);
    }

    protected void entityInit()
    {
        super.entityInit();
        this.getDataManager().register(IS_BABY, false);
        this.getDataManager().register(IS_USING_RANGE, false);
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
    
    public double getYOffset()
    {
        return this.isChild() ? 0.0D : -0.35D;
    }
    
    public boolean isChild()
    {
    	return this.getDataManager().get(IS_BABY);
    }
    
    public void setChild(boolean child)
    {
    	this.getDataManager().set(IS_BABY, child);

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
    
    @Nullable
    protected SoundEvent getAmbientSound() 
    {
    	return AdInferosSounds.ENTITY_GLOWSTONESKELETON_IDLE;
    }

    @Nullable
    protected SoundEvent getHurtSound()
    {
    	return AdInferosSounds.ENTITY_GLOWSTONESKELETON_HURT;
    }

    @Nullable
    protected SoundEvent getDeathSound()
    {
    	return AdInferosSounds.ENTITY_GLOWSTONESKELETON_DEATH;
    }
    
    protected void playStepSound(BlockPos pos, Block blockIn) {
    	this.playSound(SoundEvents.ENTITY_SKELETON_STEP, 0.15F, 1.0F);
    }

    public EnumCreatureAttribute getCreatureAttribute()
    {
        return EnumCreatureAttribute.UNDEAD;
    }

    public void onLivingUpdate()
    {
        super.onLivingUpdate();
        
    	if(this.isChild())
    	{
            this.setSize(0.3F, 0.7F);
    	}
    }
    
    @Nullable
    protected abstract Item getDropItem();
    
    protected void dropFewItems(boolean wasRecentlyHit, int lootingModifier)
    {
    	super.dropFewItems(wasRecentlyHit, lootingModifier);
    	
        int i = this.rand.nextInt(3);

        if (lootingModifier > 0)
        {
            i += this.rand.nextInt(lootingModifier + 1);
        }

        if(i > 0)
        {
            this.dropItem(Items.BONE, i);
        }
    	
    }
    
    protected void dropLoot(boolean playerKill, int looting, DamageSource source)
    {
    	if(NetherConfig.glowstoneSkeletonDrops)
    	{
        	super.dropLoot(playerKill, looting, source);
    	}
    }
    
    public IEntityLivingData onInitialSpawn(DifficultyInstance difficulty, IEntityLivingData livingdata)
    {
        livingdata = super.onInitialSpawn(difficulty, livingdata);
        this.setEquipmentBasedOnDifficulty(difficulty);
        this.setEnchantmentBasedOnDifficulty(difficulty);
        
        if(this.rand.nextFloat() < NetherConfig.glowstoneSkeletonBaby) {
        	this.setChild(true);
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

        return livingdata;
    }

    public abstract void setCombatTask();

    public void attackEntityWithRangedAttack(EntityLivingBase target, float f)
    {
    	EntityHelper.tryRangedAttack(this.getHeldItemMainhand(), this, target, true);
    }

    public void readEntityFromNBT(NBTTagCompound compound)
    {
        super.readEntityFromNBT(compound);
        
        if (compound.getBoolean("IsBaby"))
        {
            this.setChild(true);
        }

        this.setCombatTask();
    }

    public void writeEntityToNBT(NBTTagCompound compound)
    {
        super.writeEntityToNBT(compound);
        
        if (this.isChild())
        {
        	compound.setBoolean("IsBaby", true);
        }
    }
    
    public void setItemStackToSlot(EntityEquipmentSlot slotIn, ItemStack stack)
    {
    	super.setItemStackToSlot(slotIn, stack);
    	
        if (!this.worldObj.isRemote && slotIn == EntityEquipmentSlot.MAINHAND)
        {
            this.setCombatTask();
        }
    }
    
    public boolean attackEntityFrom(DamageSource source, float amount)
    {
    	if(source == DamageSource.wither && this.isWitherResistant()) {
    		return false;
    	}
    	
    	if(source.getEntity() instanceof EntityHerobrine) {
    		return false;
    	}
    	
    	return super.attackEntityFrom(source, amount);
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
    
    public void setUsingRange(boolean usingrange)
    {
    	this.getDataManager().set(IS_USING_RANGE, usingrange);
    }
    
    public boolean IsUsingRange()
    {
    	return this.getDataManager().get(IS_USING_RANGE);
    }
    
	public boolean canMakeGhost() {
		return NetherConfig.ghostSpawnFromEntities && !this.isChild() && !BlockSpawner.isSpawnerEntity(this) && this.rand.nextInt(30) == 0;
	}
	
	public void onCreateGhost(EntityGhost ghost) {}

	public boolean isCurseResistant()
	{
		return NetherConfig.glowstoneSkeletonCurseResistant;
	}
	
	public boolean isAcidResistant()
	{
		return NetherConfig.glowstoneSkeletonAcidResistant;
	}
	
	public boolean isSpikeResistant()
	{
		return false;
	}
}