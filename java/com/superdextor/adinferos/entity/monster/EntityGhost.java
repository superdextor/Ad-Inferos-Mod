package com.superdextor.adinferos.entity.monster;

import java.util.Random;

import javax.annotation.Nullable;

import com.mojang.authlib.GameProfile;
import com.superdextor.adinferos.AdInferosSounds;
import com.superdextor.adinferos.config.NetherConfig;
import com.superdextor.adinferos.entity.ISoulEntity;
import com.superdextor.adinferos.entity.NetherMob;
import com.superdextor.adinferos.init.NetherItems;

import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityFlying;
import net.minecraft.entity.EntityList;
import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.IEntityLivingData;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.ai.EntityAIBase;
import net.minecraft.entity.ai.EntityAIFindEntityNearestPlayer;
import net.minecraft.entity.ai.EntityMoveHelper;
import net.minecraft.entity.monster.EntitySkeleton;
import net.minecraft.entity.monster.IMob;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.item.ItemAxe;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTUtil;
import net.minecraft.network.datasync.DataParameter;
import net.minecraft.network.datasync.DataSerializers;
import net.minecraft.network.datasync.EntityDataManager;
import net.minecraft.tileentity.TileEntitySkull;
import net.minecraft.util.DamageSource;
import net.minecraft.util.EnumHand;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.SoundEvent;
import net.minecraft.util.math.MathHelper;
import net.minecraft.world.DifficultyInstance;
import net.minecraft.world.EnumDifficulty;
import net.minecraft.world.World;

public class EntityGhost extends EntityFlying implements IMob, NetherMob {
	
	private GameProfile gameProfile = null;
	private int divingDelay = 0;
    private static final DataParameter<String> TEXTURES = EntityDataManager.<String>createKey(EntitySkeleton.class, DataSerializers.STRING);
    
    private boolean isPlayerGhost = false;
    protected String soul = "";
    protected String soulName = "";
    
	public EntityGhost(World worldIn)
	{
		super(worldIn);
        this.setSize(0.6F, 1.95F);
        if(NetherConfig.ghostFireResistant)
        {
            this.isImmuneToFire = true;
        }
        this.experienceValue = NetherConfig.ghostEXP;
        this.noClip = true;
        this.moveHelper = new GhostMoveHelper(this);
	}
	
    protected void initEntityAI()
    {
        this.tasks.addTask(5, new EntityGhost.AIRandomFly(this));
        this.tasks.addTask(7, new EntityGhost.AILookAround(this));
        this.targetTasks.addTask(1, new EntityAIFindEntityNearestPlayer(this));
    }
    
    protected void applyEntityAttributes()
    {
        super.applyEntityAttributes();
        this.getEntityAttribute(SharedMonsterAttributes.MOVEMENT_SPEED).setBaseValue(NetherConfig.curseSpeed);
        this.getEntityAttribute(SharedMonsterAttributes.MAX_HEALTH).setBaseValue(NetherConfig.ghostHealth);
        this.getEntityAttribute(SharedMonsterAttributes.FOLLOW_RANGE).setBaseValue(100.0D);
        this.getAttributeMap().registerAttribute(SharedMonsterAttributes.ATTACK_DAMAGE).setBaseValue(NetherConfig.ghostDamage);
    }
    
    protected void entityInit()
    {
        super.entityInit();
        this.getDataManager().register(TEXTURES, "textures/entity/steve.png");
    }
    
    public void onUpdate()
    {
        super.onUpdate();

        if (!this.worldObj.isRemote && this.worldObj.getDifficulty() == EnumDifficulty.PEACEFUL)
        {
            this.setDead();
        }
    }
    
    public void onLivingUpdate()
    {
    	super.onLivingUpdate();
    	
    	if(!this.worldObj.isRemote && this.isEntityAlive()) {
        	if(this.getAttackTarget() != null) {
        		++this.divingDelay;

        		if(this.divingDelay > 130) {
        			this.divingDelay = -this.rand.nextInt(100);
        			this.playSound(AdInferosSounds.ENTITY_GHOST_DIVE, this.getSoundVolume(), this.getSoundPitch());
        			
        			double d = 0.2D;
        			
        			if(this.worldObj.getDifficulty() == EnumDifficulty.NORMAL) {
        				d = 0.6D;
        			}
        			
        			if(this.worldObj.getDifficulty() == EnumDifficulty.HARD) {
        				d = 0.8D;
        			}
        			
        	        this.getMoveHelper().setMoveTo(this.getAttackTarget().posX, this.getAttackTarget().posY, this.getAttackTarget().posZ, 1.0D + d);
        		}
        	}
        	else {
        		this.divingDelay = 0;
        	}
    	}
    }
    
    public SoundCategory getSoundCategory()
    {
        return SoundCategory.HOSTILE;
    }

    @Nullable
    protected SoundEvent getAmbientSound()
    {
        return AdInferosSounds.ENTITY_GHOST_IDLE;
    }

    @Nullable
    protected SoundEvent getHurtSound()
    {
        return null;
    }

    @Nullable
    protected SoundEvent getDeathSound()
    {
        return AdInferosSounds.ENTITY_GHOST_DEATH;
    }
    
    @Nullable
    protected Item getDropItem()
    {
    	return this.isPlayerGhost ? null : NetherItems.SOUL_FRAGMENT;
    }
    
    protected void dropLoot(boolean playerKill, int looting, DamageSource source)
    {
    	if(NetherConfig.ghostDrops)
    	{
        	super.dropLoot(playerKill, looting, source);
    	}
    }
    
    protected float getSoundVolume()
    {
        return 2.0F;
    }
    
    public boolean getCanSpawnHere()
    {
        return super.getCanSpawnHere() && this.worldObj.getDifficulty() != EnumDifficulty.PEACEFUL;
    }
    
    public int getMaxSpawnedInChunk()
    {
        return 4;
    }
    
    public float getEyeHeight()
    {
        return 1.74F;
    }
    
	protected void doBlockCollisions() {}
	
	protected boolean pushOutOfBlocks(double x, double y, double z)
	{
		return false;
	}
	
	protected void collideWithEntity(Entity entityIn)
	{
		super.collideWithEntity(entityIn);
		
		if(entityIn == this.getAttackTarget()) 
		{
	        this.swingArm(EnumHand.MAIN_HAND);
	        this.attackEntityAsMob(entityIn);
		}
	}
	
	public boolean isEntityInsideOpaqueBlock()
	{
		return false;
	}
	
    public boolean attackEntityAsMob(Entity entityIn)
    {
        float f = (float)this.getEntityAttribute(SharedMonsterAttributes.ATTACK_DAMAGE).getAttributeValue();
        int i = 0;

        if (entityIn instanceof EntityLivingBase)
        {
            f += EnchantmentHelper.getModifierForCreature(this.getHeldItemMainhand(), ((EntityLivingBase)entityIn).getCreatureAttribute());
            i += EnchantmentHelper.getKnockbackModifier(this);
        }

        boolean flag = entityIn.attackEntityFrom(DamageSource.causeMobDamage(this), f);

        if (flag)
        {
            if (i > 0 && entityIn instanceof EntityLivingBase)
            {
                ((EntityLivingBase)entityIn).knockBack(this, (float)i * 0.5F, (double)MathHelper.sin(this.rotationYaw * 0.017453292F), (double)(-MathHelper.cos(this.rotationYaw * 0.017453292F)));
                this.motionX *= 0.6D;
                this.motionZ *= 0.6D;
            }

            int j = EnchantmentHelper.getFireAspectModifier(this);

            if (j > 0)
            {
                entityIn.setFire(j * 4);
            }

            if (entityIn instanceof EntityPlayer)
            {
                EntityPlayer entityplayer = (EntityPlayer)entityIn;
                ItemStack itemstack = this.getHeldItemMainhand();
                ItemStack itemstack1 = entityplayer.isHandActive() ? entityplayer.getActiveItemStack() : null;

                if (itemstack != null && itemstack1 != null && itemstack.getItem() instanceof ItemAxe && itemstack1.getItem() == Items.SHIELD)
                {
                    float f1 = 0.25F + (float)EnchantmentHelper.getEfficiencyModifier(this) * 0.05F;

                    if (this.rand.nextFloat() < f1)
                    {
                        entityplayer.getCooldownTracker().setCooldown(Items.SHIELD, 100);
                        this.worldObj.setEntityState(entityplayer, (byte)30);
                    }
                }
            }

            this.applyEnchantments(this, entityIn);
        }

        return flag;
    }
    
    public boolean attackEntityFrom(DamageSource source, float amount) {
    	
    	if(source == DamageSource.wither) {
    		return false;
    	}
    	
    	if(source == DamageSource.inFire) {
    		return false;
    	}
    	
    	if(source == DamageSource.lava) {
    		return false;
    	}
    	
    	boolean flag = super.attackEntityFrom(source, amount);
    	
    	if(flag) {
            double d0 = this.posX + (double)((this.rand.nextFloat() * 2.0F - 1.0F) * 16.0F);
            double d1 = this.posY + (double)((this.rand.nextFloat() * 2.0F - 1.0F) * 16.0F);
            double d2 = this.posZ + (double)((this.rand.nextFloat() * 2.0F - 1.0F) * 16.0F);
            
            this.getMoveHelper().setMoveTo(d0, d1, d2, 1.0D);
    	}
    	
    	return flag;
    }
    
    public IEntityLivingData onInitialSpawn(DifficultyInstance difficulty, IEntityLivingData livingdata) {
    	IEntityLivingData data = super.onInitialSpawn(difficulty, livingdata);
		this.playSound(AdInferosSounds.ENTITY_GHOST_SPAWN, this.getSoundVolume(), this.getSoundPitch());
    	this.setTextures(this.getRandomTexture());
		
		NetherConfig.printDebugInfo(this.getName() + " was spawned at " + this.getPosition());
		
    	return data;
    }
    
    public String getTextures() {
    	return this.getDataManager().get(TEXTURES);
    }
    
    protected void setTextures(String value) {
    	this.getDataManager().set(TEXTURES, value);
    }
    
    private String getRandomTexture()
    {
    	switch(this.rand.nextInt(8)) {
    		default: {
    			return "textures/entity/steve.png";
    		}
    		
    		case 1: {
    			return "textures/entity/alex.png";
    		}
    		
    		case 2: {
    			return "textures/entity/zombie/zombie.png";
    		}
    		
    		case 3: {
    			return "textures/entity/zombie_pigman.png";
    		}
    		
    		case 4: {
    			return "player:Dragon_Potato";
    		}
    		
    		case 5: {
    			return "adinferos:textures/entity/glowstone_skeleton/default.png";
    		}
    		
    		case 6: {
    			return "adinferos:textures/entity/glowstone_skeleton/dimstone.png";
    		}
    		
    		case 7: {
    			return "adinferos:textures/entity/obsidian_sheepman/main.png";
    		}
    		
    		case 8: {
    			return "adinferos:textures/entity/phantom.png";
    		}
    		
    		case 9: {
    			return "adinferos:textures/entity/reaper.png";
    		}
    	}
    }
    
    public void setSoul(EntityPlayer player) {
    	this.setTextures("player:" + player.getName());
    }
    
    public void setSoul(ISoulEntity soulEntity) {
    	this.setTextures(soulEntity.getTexture());
    }
    
    public void readEntityFromNBT(NBTTagCompound tagCompund) {
    	super.readEntityFromNBT(tagCompund);
    	
    	if(tagCompund.hasKey("Textures") && tagCompund.getString("Textures") != null) {
    		this.setTextures(tagCompund.getString("Textures"));
    	}
    	
    	if(tagCompund.getBoolean("PlayerGhost")) {
    		this.isPlayerGhost = true;
    	}
    	
    	this.soul = tagCompund.getString("Soul");
    	this.soulName = tagCompund.getString("SoulName");
    	
    }
    
    public void writeEntityToNBT(NBTTagCompound tagCompound) {
    	super.writeEntityToNBT(tagCompound);
    	
    	tagCompound.setString("Textures", this.getTextures());
    	tagCompound.setBoolean("PlayerGhost", this.isPlayerGhost);
    	tagCompound.setString("Soul", this.soul);
    	tagCompound.setString("SoulName", this.soulName);
    }
    
	public GameProfile getGameProfile(String name) {
		
		if(this.gameProfile == null) {
	    	NBTTagCompound compound = new NBTTagCompound();
	    	compound.setString("Name", name);
	    	this.gameProfile = TileEntitySkull.updateGameprofile(NBTUtil.readGameProfileFromNBT(compound));
		}
		
		return this.gameProfile;
	}
	
	public String getSoul() {
		return this.soul;
	}
	
	public String getSoulName() {
		return this.soulName;
	}
    
	public boolean isDarkfireResistant() {
		return NetherConfig.ghostDarkfireResistant;
	}

	public boolean isCurseResistant() {
		return NetherConfig.ghostCurseResistant;
	}

	public boolean isWitherResistant() {
		return NetherConfig.ghostWitherResistant;
	}

	public boolean isAcidResistant() {
		return NetherConfig.ghostAcidResistant;
	}

	public boolean isSpikeResistant() {
		return true;
	}
	
	public static EntityGhost createGhost(World worldIn, EntityLiving entityLiving, String texture) {
		EntityGhost ghost = new EntityGhost(worldIn);
		ghost.setLocationAndAngles(entityLiving.posX, entityLiving.posY, entityLiving.posZ, entityLiving.rotationYaw, entityLiving.rotationPitch);
		ghost.onInitialSpawn(worldIn.getDifficultyForLocation(entityLiving.getPosition()), null);
		ghost.setTextures(texture);
		ghost.soul = EntityList.getEntityString(entityLiving);
		ghost.soulName = entityLiving.getName();
		worldIn.spawnEntityInWorld(ghost);
		return ghost;
	}
	
	public static EntityGhost createGhost(World worldIn, EntityLiving entityLiving, ISoulEntity soulEntity) {
		EntityGhost ghost = new EntityGhost(worldIn);
		ghost.setLocationAndAngles(entityLiving.posX, entityLiving.posY, entityLiving.posZ, entityLiving.rotationYaw, entityLiving.rotationPitch);
		ghost.onInitialSpawn(worldIn.getDifficultyForLocation(entityLiving.getPosition()), null);
		ghost.setSoul(soulEntity);
		worldIn.spawnEntityInWorld(ghost);
		ghost.soul = EntityList.getEntityString(entityLiving);
		ghost.soulName = entityLiving.getName();
		soulEntity.onCreateGhost(ghost);
		return ghost;
	}
	
	public static EntityGhost createGhost(World worldIn, EntityPlayer player) {
		EntityGhost ghost = new EntityGhost(worldIn);
		ghost.setLocationAndAngles(player.posX, player.posY, player.posZ, player.rotationYaw, player.rotationPitch);
		ghost.onInitialSpawn(worldIn.getDifficultyForLocation(player.getPosition()), null);
		ghost.setSoul(player);
		ghost.isPlayerGhost = true;
		worldIn.spawnEntityInWorld(ghost);
		return ghost;
	}
    
    static class AIRandomFly extends EntityAIBase
    {
        private EntityGhost parentEntity;

        public AIRandomFly(EntityGhost ghost)
        {
            this.parentEntity = ghost;
            this.setMutexBits(1);
        }

        /**
         * Returns whether the EntityAIBase should begin execution.
         */
        public boolean shouldExecute()
        {
            EntityMoveHelper entitymovehelper = this.parentEntity.getMoveHelper();

            if (!entitymovehelper.isUpdating())
            {
                return true;
            }
            else
            {
                double d0 = entitymovehelper.getX() - this.parentEntity.posX;
                double d1 = entitymovehelper.getY() - this.parentEntity.posY;
                double d2 = entitymovehelper.getZ() - this.parentEntity.posZ;
                double d3 = d0 * d0 + d1 * d1 + d2 * d2;
                return d3 < 1.0D || d3 > 3600.0D;
            }
        }

        /**
         * Returns whether an in-progress EntityAIBase should continue executing
         */
        public boolean continueExecuting()
        {
            return false;
        }

        /**
         * Execute a one shot task or start executing a continuous task
         */
        public void startExecuting()
        {
            Random random = this.parentEntity.getRNG();
            double d0 = this.parentEntity.posX + (double)((random.nextFloat() * 2.0F - 1.0F) * 16.0F);
            double d1 = this.parentEntity.posY + (double)((random.nextFloat() * 2.0F - 1.0F) * 16.0F);
            double d2 = this.parentEntity.posZ + (double)((random.nextFloat() * 2.0F - 1.0F) * 16.0F);
            
            if(this.parentEntity.getAttackTarget() != null) {
                d0 = this.parentEntity.getAttackTarget().posX + (double)((random.nextFloat() * 2.0F - 1.0F) * 16.0F);
                d1 = this.parentEntity.getAttackTarget().posY + (double)((random.nextFloat() * 2.0F - 1.0F) * 16.0F);
                d2 = this.parentEntity.getAttackTarget().posZ + (double)((random.nextFloat() * 2.0F - 1.0F) * 16.0F);
            }
            
            this.parentEntity.getMoveHelper().setMoveTo(d0, d1, d2, 1.0D);
        }
    }
    
    static class AILookAround extends EntityAIBase
    {
        private EntityGhost parentEntity;

        public AILookAround(EntityGhost ghost)
        {
            this.parentEntity = ghost;
            this.setMutexBits(2);
        }

        /**
         * Returns whether the EntityAIBase should begin execution.
         */
        public boolean shouldExecute()
        {
            return true;
        }

        /**
         * Updates the task
         */
        public void updateTask()
        {
            if (this.parentEntity.getAttackTarget() == null)
            {
                this.parentEntity.renderYawOffset = this.parentEntity.rotationYaw = -((float)MathHelper.atan2(this.parentEntity.motionX, this.parentEntity.motionZ)) * (180F / (float)Math.PI);
            }
            else
            {
                EntityLivingBase entitylivingbase = this.parentEntity.getAttackTarget();
                double d0 = 64.0D;

                if (entitylivingbase.getDistanceSqToEntity(this.parentEntity) < d0 * d0)
                {
                    double d1 = entitylivingbase.posX - this.parentEntity.posX;
                    double d2 = entitylivingbase.posZ - this.parentEntity.posZ;
                    this.parentEntity.renderYawOffset = this.parentEntity.rotationYaw = -((float)MathHelper.atan2(d1, d2)) * (180F / (float)Math.PI);
                }
            }
        }
    }
    
    static class GhostMoveHelper extends EntityMoveHelper
    {
        private EntityGhost parentEntity;
        private int courseChangeCooldown;

        public GhostMoveHelper(EntityGhost ghost)
        {
            super(ghost);
            this.parentEntity = ghost;
        }

        public void onUpdateMoveHelper()
        {
            if (this.action == EntityMoveHelper.Action.MOVE_TO)
            {
                this.parentEntity.setAIMoveSpeed((float)(this.speed * this.parentEntity.getEntityAttribute(SharedMonsterAttributes.MOVEMENT_SPEED).getAttributeValue()));
                double d0 = this.posX - this.parentEntity.posX;
                double d1 = this.posY - this.parentEntity.posY;
                double d2 = this.posZ - this.parentEntity.posZ;
                double d3 = d0 * d0 + d1 * d1 + d2 * d2;

                if (this.courseChangeCooldown-- <= 0)
                {
                    this.courseChangeCooldown += this.parentEntity.getRNG().nextInt(5) + 2;
                    d3 = (double)MathHelper.sqrt_double(d3);
                    this.parentEntity.motionX += (d0 / d3 * 0.1D) * this.speed;
                    this.parentEntity.motionY += (d1 / d3 * 0.1D) * this.speed;
                    this.parentEntity.motionZ += (d2 / d3 * 0.1D) * this.speed;
                }
            }
        }
    }
}
