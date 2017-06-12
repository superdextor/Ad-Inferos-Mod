package com.superdextor.adinferos.entity.monster;

import java.util.ArrayList;

import javax.annotation.Nullable;

import com.superdextor.adinferos.AdInferosSounds;
import com.superdextor.adinferos.config.NetherConfig;
import com.superdextor.adinferos.entity.ISoulEntity;
import com.superdextor.adinferos.entity.NetherMob;
import com.superdextor.adinferos.init.NetherBlocks;
import com.superdextor.adinferos.init.NetherItems;
import com.superdextor.thinkbigcore.entity.IFearedEntity;
import com.superdextor.thinkbigcore.entity.ai.EntityAIBreakblocks;
import com.superdextor.thinkbigcore.items.IEntityUsable;

import net.minecraft.block.Block;
import net.minecraft.block.BlockLiquid;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.enchantment.EnchantmentFrostWalker;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityCreature;
import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.IEntityLivingData;
import net.minecraft.entity.IRangedAttackMob;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.ai.EntityAIAttackMelee;
import net.minecraft.entity.ai.EntityAIAttackRanged;
import net.minecraft.entity.ai.EntityAIHurtByTarget;
import net.minecraft.entity.ai.EntityAINearestAttackableTarget;
import net.minecraft.entity.ai.EntityAISwimming;
import net.minecraft.entity.monster.EntityGolem;
import net.minecraft.entity.monster.EntityMob;
import net.minecraft.entity.passive.EntityVillager;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.init.SoundEvents;
import net.minecraft.inventory.EntityEquipmentSlot;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.network.datasync.DataParameter;
import net.minecraft.network.datasync.DataSerializers;
import net.minecraft.network.datasync.EntityDataManager;
import net.minecraft.pathfinding.PathNavigateGround;
import net.minecraft.pathfinding.PathNodeType;
import net.minecraft.potion.PotionEffect;
import net.minecraft.util.DamageSource;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.SoundEvent;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.DifficultyInstance;
import net.minecraft.world.World;

public class EntityHerobrine extends EntityMob implements IRangedAttackMob, NetherMob, IFearedEntity, ISoulEntity
{
	
    private static final DataParameter<Boolean> IS_CLOAKING = EntityDataManager.<Boolean>createKey(EntityHerobrine.class, DataSerializers.BOOLEAN);
    private static final DataParameter<Boolean> IS_ATTACKING = EntityDataManager.<Boolean>createKey(EntityHerobrine.class, DataSerializers.BOOLEAN);
    private EntityAIAttackRanged aiArrowAttack = new EntityAIAttackRanged(this, 1.25D, 7, 10.0F);
    private EntityAIAttackMelee aiAttackOnCollide = new EntityAIAttackMelee(this, 1.2D, false);
    private int cooldown = 20;
    private int attackUpdateTimer = 0;
    private int specialCooldown = 300;
    private int randomTeleportCooldown = 120;
    private int angerCounter = 0;
    
    public EntityHerobrine(World worldIn)
    {
        super(worldIn);
        ((PathNavigateGround)this.getNavigator()).setBreakDoors(true);
        this.setPathPriority(PathNodeType.WATER, 0.0F);
        this.setPathPriority(PathNodeType.DOOR_WOOD_CLOSED, 0.0F);
        this.setPathPriority(PathNodeType.LAVA, 0.0F);
        this.setPathPriority(PathNodeType.DANGER_FIRE, 0.0F);
        this.setPathPriority(PathNodeType.BLOCKED, 8.0F);
        this.setPathPriority(PathNodeType.FENCE, 0.0F);
        this.experienceValue = 100;
        this.isImmuneToFire = true;
        this.tasks.addTask(0, new EntityAISwimming(this));
        ArrayList<Block> unbreakable = new ArrayList<Block>();
        unbreakable.add(NetherBlocks.HARDENED_LAVA);
        unbreakable.add(Blocks.FROSTED_ICE);
        this.tasks.addTask(1, new EntityAIBreakblocks(this, 80.0F, true,false,true, unbreakable));
        this.targetTasks.addTask(0, new EntityAIHurtByTarget(this, true, new Class[0]));
        this.targetTasks.addTask(1, new EntityAINearestAttackableTarget(this, EntityPlayer.class, true));
        this.targetTasks.addTask(2, new EntityAINearestAttackableTarget(this, EntityGolem.class, true));
        this.targetTasks.addTask(2, new EntityAINearestAttackableTarget(this, EntityVillager.class, true));
    }
    
    protected void applyEntityAttributes()
    {
        super.applyEntityAttributes();
        this.getEntityAttribute(SharedMonsterAttributes.MAX_HEALTH).setBaseValue(300.0D);
        this.getEntityAttribute(SharedMonsterAttributes.MOVEMENT_SPEED).setBaseValue(0.46D);
        this.getEntityAttribute(SharedMonsterAttributes.ATTACK_DAMAGE).setBaseValue(5.0D);
        this.getEntityAttribute(SharedMonsterAttributes.FOLLOW_RANGE).setBaseValue(80.0D);
        this.getEntityAttribute(SharedMonsterAttributes.ARMOR).setBaseValue(3.0D);
    }
    
    protected void entityInit()
    {
        super.entityInit();
        this.getDataManager().register(IS_CLOAKING, false);
        this.getDataManager().register(IS_ATTACKING, false);
    }
    
    @Nullable
    protected SoundEvent getAmbientSound() {
    	return null;
    }
    
    @Nullable
    protected SoundEvent getHurtSound()
    {
        return SoundEvents.ENTITY_ELDER_GUARDIAN_HURT;
    }
    
    @Nullable
    protected SoundEvent getDeathSound()
    {
        return SoundEvents.ENTITY_SKELETON_HORSE_DEATH;
    }
    
    protected float getSoundPitch() {
    	return super.getSoundPitch() * 0.7F;
    }
    
    @Nullable
    protected Item getDropItem() {
    	return NetherItems.DARKCORE;
    }
    
    protected void dropFewItems(boolean wasRecentlyHit, int lootingModifier)
    {
    	this.dropItem(NetherItems.DARKCORE, 1);
    	
        int i = 8 + this.rand.nextInt(9);

        if (lootingModifier > 0)
        {
            i += this.rand.nextInt(lootingModifier + 1);
        }

        this.dropItem(NetherItems.DIMENSIONAL_DUST, i);
        
        i = 8 + this.rand.nextInt(9);

        if (lootingModifier > 0)
        {
            i += this.rand.nextInt(lootingModifier + 1);
        }

        this.dropItem(NetherItems.WITHER_DUST, i);
        
        i = 8 + this.rand.nextInt(9);

        if (lootingModifier > 0)
        {
            i += this.rand.nextInt(lootingModifier + 1);
        }

        this.dropItem(NetherItems.NETHERITE_NUGGET, i);
        
        i = 3 + this.rand.nextInt(4);

        if (lootingModifier > 0)
        {
            i += this.rand.nextInt(lootingModifier + 1);
        }

        this.dropItem(Items.DIAMOND, i);
    	
    }
    
    public void setCombatTask()
    {
    	this.setDropChance(EntityEquipmentSlot.MAINHAND, 2.0F);
        this.tasks.removeTask(this.aiAttackOnCollide);
        this.tasks.removeTask(this.aiArrowAttack);
        this.cooldown = 20;
        ItemStack newStack = new ItemStack(Items.STICK);
        
        switch(rand.nextInt(5)) {
        case 0:
        	newStack = new ItemStack(NetherItems.SCYTHE, 1, 0);
            this.tasks.addTask(4, this.aiAttackOnCollide);
        	break;
        	
        case 1:
        	newStack = new ItemStack(NetherItems.INFERNO_STAFF, 1, 0);
            this.tasks.addTask(4, this.aiArrowAttack);
        	break;
        	
        case 2:
        	newStack = new ItemStack(NetherItems.GLOWSTONE_SWORD, 1, 0);
            this.tasks.addTask(4, this.aiArrowAttack);
        	break;
        	
        case 3:
        	newStack = new ItemStack(NetherItems.WITHER_STAFF, 1, 0);
            this.tasks.addTask(4, this.aiArrowAttack);
        	break;
        	
        case 4:
        	newStack = new ItemStack(NetherItems.WITHER_BOW, 1, 0);
            this.tasks.addTask(4, this.aiArrowAttack);
        	break;
        }
        
        
        this.setHeldItem(EnumHand.MAIN_HAND, newStack);
        this.playSound(SoundEvents.ITEM_ARMOR_EQUIP_GENERIC, 2.0F, 0.8F);
    }
    
    public void onLivingUpdate()
    {
    	if(!worldObj.isRemote) {
        	ItemStack heldItem = this.getHeldItemMainhand();
            if(this.getAttackTarget() != null) {
            	
                if (this.attackUpdateTimer <= 0)
                {
                    this.setCombatTask();
                    this.attackUpdateTimer = 100 + this.rand.nextInt(70);
                }
                else
                {
                	--this.attackUpdateTimer;
                }
                	
                if(this.cooldown > 0) {
                    --this.cooldown;
                }
                
                if(heldItem != null && this.cooldown == 50)
                {
                    this.playSound(heldItem.getItem() == NetherItems.WITHER_STAFF ? SoundEvents.ENTITY_WITHER_AMBIENT : SoundEvents.ENTITY_GHAST_HURT, 1.3F, 0.9F);
                }
                    
                if(this.specialCooldown < 1)
                {
                    this.specialAttack(this.rand.nextInt(2));
                }
                else
                {
                    --this.specialCooldown;
                }
                
                if(this.getDistanceSqToEntity(this.getAttackTarget()) > 400.0D)
                {
                	this.teleportToEntity(this.getAttackTarget());
                }
                
                if(!this.isCloaking())
                {
                	--this.randomTeleportCooldown;
                	
                	if(this.randomTeleportCooldown < 0) {
                		this.randomTeleportCooldown = 230 + this.rand.nextInt(201);
                		this.teleportRandomly();
                	}
                }
            }
            else
            {
            	if(this.angerCounter > 0)
            	{
            		--this.angerCounter;
            		
            		if(this.angerCounter < 1)
            		{
            			this.angerCounter = 0;
            			this.setAttacking(false);
            		}
            	}
            	
            	if(heldItem != null)
            	{
                	this.setHeldItem(EnumHand.MAIN_HAND, null);
                	this.setCloaking(false);
                	this.cooldown = 20;
                	this.attackUpdateTimer = 0;
            		this.specialCooldown = 400 + rand.nextInt(200);
            		this.randomTeleportCooldown = 120;
                }
            }
    	}
        
        super.onLivingUpdate();
    }
    
    public boolean isNotColliding()
    {
        return this.worldObj.checkNoEntityCollision(this.getEntityBoundingBox(), this) && this.worldObj.getCollisionBoxes(this, this.getEntityBoundingBox()).isEmpty() && !this.worldObj.containsAnyLiquid(this.getEntityBoundingBox());
    }
    
    protected void specialAttack(int attackID)
    {
    	switch(attackID) {
	    	default:
	    	{
	    		EntityHerobrineClone clone = null;
	        	int k = this.rand.nextInt(5) + 7;
	        	for(int x = 0; x < k; x++) {
	        		EntityHerobrineClone entityclone = new EntityHerobrineClone(this.worldObj);
	        		entityclone.setOwner(this, this.getAttackTarget());
	        		entityclone.setHeldItem(EnumHand.MAIN_HAND, this.getHeldItemMainhand());
	                this.spawnMob(entityclone, 5);
	                clone = entityclone;
	                entityclone.teleportRandomly();
	        	}
	        	
	        	if(this.getAttackTarget() instanceof EntityLiving) {
	        		((EntityLiving) this.getAttackTarget()).setAttackTarget(clone);
	        	}
	        	
	            this.playSound(AdInferosSounds.ENTITY_REAPER_CLOAK, 1.6F, 0.8F);
	            this.playSound(SoundEvents.ENTITY_ENDERMEN_TELEPORT, 1.6F, 1.3F);
	            this.setCloaking(true);
	            this.teleportRandomly();
	    		break;
	    	}
	    		
	    	case 1:
	    	{
	    		EntityDimstoneSkeleton dimstoneSkeleton = new EntityDimstoneSkeleton(this.worldObj);
	    		dimstoneSkeleton.setAttackTarget(this.getAttackTarget());
	    		dimstoneSkeleton.setHeldItem(EnumHand.MAIN_HAND, new ItemStack(NetherItems.WITHER_STAFF));
	    		dimstoneSkeleton.setItemStackToSlot(EntityEquipmentSlot.HEAD, new ItemStack(NetherItems.WITHER_HELMET));
	    		dimstoneSkeleton.setItemStackToSlot(EntityEquipmentSlot.CHEST, new ItemStack(NetherItems.WITHER_CHESTPLATE));
	    		dimstoneSkeleton.setItemStackToSlot(EntityEquipmentSlot.LEGS, new ItemStack(NetherItems.WITHER_LEGGINGS));
	    		dimstoneSkeleton.setItemStackToSlot(EntityEquipmentSlot.FEET, new ItemStack(NetherItems.WITHER_BOOTS));
	    		this.spawnMob(dimstoneSkeleton, 2);
                this.playSound(SoundEvents.ENTITY_BLAZE_AMBIENT, 1.6F, 1.0F);
	    		break;
	    	}
	    }
    	
		this.specialCooldown = 800 + this.rand.nextInt(200);
	}
    
    public void updateRidden()
    {
        super.updateRidden();

        if (this.getRidingEntity() instanceof EntityCreature)
        {
            EntityCreature entitycreature = (EntityCreature)this.getRidingEntity();
            this.renderYawOffset = entitycreature.renderYawOffset;
        }
    }

	private void spawnMob(EntityLiving entity, int radius)
	{
        entity.setLocationAndAngles(this.posX -(2 + radius) + (double)rand.nextInt(5 + radius), this.posY, this.posZ -(2 + radius) + (double)rand.nextInt(5 + radius), 0, 0.0F);
        
        for(int x = 0; x < 10; x++) {
        	
        	if(entity.isEntityInsideOpaqueBlock()) {
        		entity.posY++;
        	}
        	else {
            	break;
        	}
        }
        
        this.worldObj.spawnEntityInWorld(entity);
    }
	
    protected boolean teleportRandomly()
    {
        double d0 = this.posX + (this.rand.nextDouble() - 0.5D) * 64.0D;
        double d1 = this.posY + (double)(this.rand.nextInt(64) - 32);
        double d2 = this.posZ + (this.rand.nextDouble() - 0.5D) * 64.0D;
        
        if(this.getAttackTarget() != null) {
            double k0 = d0 - this.getAttackTarget().posX;
            double k1 = d1 - this.getAttackTarget().posY;
            double k2 = d2 - this.getAttackTarget().posZ;
            
            if(k0 * k0 + k1 * k1 + k2 * k2 > 400.0D) {
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
        }

        return flag;
    }
    
    protected boolean canDespawn()
    {
    	return !this.isAttacking();
    }
    
    public boolean canAttackClass(Class<? extends EntityLivingBase> cls)
    {
    	return cls != EntityDimstoneSkeleton.class && super.canAttackClass(cls);
    }
    
	
    public void setAttackTarget(EntityLivingBase entitylivingbaseIn)
    {
        super.setAttackTarget(entitylivingbaseIn);

        if (entitylivingbaseIn != null)
        {
            this.setAttacking(true);
            this.angerCounter = 120;
        }
    }
    
    public boolean attackEntityFrom(DamageSource source, float damage)
    {
    	if(source == DamageSource.wither) {
    		return false;
    	}
    	
    	if(source.getEntity() != null && source.getEntity().equals(this)) {
    		return false;
    	}
    	
    	if(source.getEntity() instanceof EntityLivingBase) {
        	this.setCloaking(false);
    	}
    	
        return super.attackEntityFrom(source, damage);
    }
    
    public void onDeath(DamageSource cause) {
    	
    	this.playSound(AdInferosSounds.MUSIC_VICTORY, 1.0F, 1.0F);
    	
    	if(cause.getEntity() instanceof EntityPlayer) {
    		if(this.canMakeGhost()) {
    			EntityGhost.createGhost(this.worldObj, this, this);
    		}
    	}
    	
    	super.onDeath(cause);
    }

	public boolean attackEntityAsMob(Entity entity)
    {
        if (super.attackEntityAsMob(entity))
        {
        	ItemStack heldItem = this.getHeldItem(EnumHand.MAIN_HAND);
        	
        	if(entity instanceof EntityLivingBase && this.getAttackTarget() != null && heldItem != null 
        			&& heldItem.getItem() instanceof IEntityUsable && entity instanceof EntityLivingBase) {
        		((IEntityUsable) heldItem.getItem()).onMeleeAttack(this, (EntityLivingBase)entity, heldItem);
        	}
        	
            return true;
        }
        
        return false;
    }
    
    public void attackEntityWithRangedAttack(EntityLivingBase entity, float f)
    {
		ItemStack heldItem = this.getHeldItem(EnumHand.MAIN_HAND);
		Item item = null;
    	boolean flag = false;
    	
    	if(heldItem != null) {
    		item = heldItem.getItem();
    		
    		flag = true;
    		
			if(item.equals(NetherItems.WITHER_BOW)) {
				flag = this.cooldown < 1;
			}
			
			if(item.equals(NetherItems.INFERNO_STAFF)) {
				flag = this.cooldown < 40;
			}
			
			if(item.equals(NetherItems.WITHER_STAFF)) {
				flag = this.cooldown < 30;
			}
    	}
    	
    	if(flag && heldItem.getItem() instanceof IEntityUsable) {
    		((IEntityUsable) item).onRangedAttack(this, entity, heldItem, true);
    			
    		if(this.cooldown < 1) {
        		if(item == NetherItems.INFERNO_STAFF) {
        			this.cooldown = 120;
        		}
        		else if(item == NetherItems.WITHER_STAFF) {
        			this.cooldown = 120;
        		}
        		if(item == NetherItems.QUARTZ_BOW) {
        			this.cooldown = 8;
        		}
    		}
    	}
    }
    
    /**
     * (abstract) Protected helper method to write subclass entity data to NBT.
     */
    public void writeEntityToNBT(NBTTagCompound tagCompound)
    {
        super.writeEntityToNBT(tagCompound);
        tagCompound.setBoolean("isCloaking", this.isCloaking());
    }

    /**
     * (abstract) Protected helper method to read subclass entity data from NBT.
     */
    public void readEntityFromNBT(NBTTagCompound tagCompund)
    {
        super.readEntityFromNBT(tagCompund);

        if (tagCompund.hasKey("isCloaking", 1))
        {
            this.setCloaking(tagCompund.getBoolean("isCloaking"));
        }
    }
    
    public void moveEntityWithHeading(float strafe, float forward)
    {
    	super.moveEntityWithHeading(strafe, forward);
    	
    	hardenLava(this, this.worldObj, this.getPosition(), 2);
    	EnchantmentFrostWalker.freezeNearby(this, this.worldObj, this.getPosition(), 2);
    }
    
    public IEntityLivingData onInitialSpawn(DifficultyInstance difficulty, IEntityLivingData livingdata)
    {
		NetherConfig.printDebugInfo(this.getName() + " was spawned at " + this.getPosition());
    	return super.onInitialSpawn(difficulty, livingdata);
    }
    
    public static void hardenLava(EntityLivingBase entityIn, World worldIn, BlockPos posIn, int range)
    {
        if (entityIn.onGround)
        {
            float f = (float)Math.min(16, 2 + range);
            BlockPos.MutableBlockPos blockpos$mutableblockpos = new BlockPos.MutableBlockPos(0, 0, 0);

            for (BlockPos.MutableBlockPos blockpos$mutableblockpos1 : BlockPos.getAllInBoxMutable(posIn.add((double)(-f), -1.0D, (double)(-f)), posIn.add((double)f, -1.0D, (double)f)))
            {
                if (blockpos$mutableblockpos1.distanceSqToCenter(entityIn.posX, entityIn.posY, entityIn.posZ) <= (double)(f * f))
                {
                    blockpos$mutableblockpos.setPos(blockpos$mutableblockpos1.getX(), blockpos$mutableblockpos1.getY() + 1, blockpos$mutableblockpos1.getZ());
                    IBlockState iblockstate = worldIn.getBlockState(blockpos$mutableblockpos);

                    if (iblockstate.getBlock() == Blocks.AIR)
                    {
                        IBlockState iblockstate1 = worldIn.getBlockState(blockpos$mutableblockpos1);

                        if (iblockstate1.getMaterial() == Material.LAVA && ((Integer)iblockstate1.getValue(BlockLiquid.LEVEL)).intValue() == 0 && worldIn.func_190527_a(NetherBlocks.HARDENED_LAVA, blockpos$mutableblockpos1, false, EnumFacing.DOWN, (Entity)null))
                        {
                        	worldIn.setBlockState(blockpos$mutableblockpos1, NetherBlocks.HARDENED_LAVA.getStateFromMeta(1));
                            worldIn.scheduleUpdate(blockpos$mutableblockpos1.toImmutable(), NetherBlocks.HARDENED_LAVA, MathHelper.getRandomIntegerInRange(entityIn.getRNG(), 60, 120));
                        }
                    }
                }
            }
        }
    }
    
    public boolean isCloaking()
    {
    	return this.getDataManager().get(IS_CLOAKING);
    }
    
    protected void setCloaking(boolean cloaking)
    {
    	this.getDataManager().set(IS_CLOAKING, cloaking);
    }
    
    public boolean isAttacking()
    {
    	return this.getDataManager().get(IS_ATTACKING);
    }
    
    public void setAttacking(boolean attacking)
    {
    	this.getDataManager().set(IS_ATTACKING, attacking);
    }
    
    public boolean isNonBoss()
    {
    	return false;
    }
    
    public int getMaxSpawnedInChunk()
    {
    	return 1;
    }
    
	public boolean canMakeGhost()
	{
		return NetherConfig.ghostSpawnFromEntities;
	}

	public String getTexture()
	{
		return "adinferos:textures/entity/herobrine.png";
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
		return true;
	}

	public boolean isSpikeResistant()
	{
		return true;
	}

	public int getFearDelay()
	{
		return 7;
	}

	public int getFearAmount()
	{
		return 15;
	}

	public double getFearRange()
	{
		return 900.0D;
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