package com.superdextor.adinferos.entity.monster;

import java.util.ArrayList;
import java.util.Map;
import java.util.Random;
import java.util.UUID;

import javax.annotation.Nullable;

import com.google.common.base.Predicate;
import com.google.common.collect.Maps;
import com.superdextor.adinferos.AdInferosSounds;
import com.superdextor.adinferos.blocks.BlockSpawner;
import com.superdextor.adinferos.config.NetherConfig;
import com.superdextor.adinferos.entity.EntityAIObsidianSheepmanTarget;
import com.superdextor.adinferos.entity.ISoulEntity;
import com.superdextor.adinferos.entity.NetherMob;
import com.superdextor.adinferos.init.NetherItems;
import com.superdextor.adinferos.items.ItemTribeHeadband;

import net.minecraft.block.Block;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.IEntityLivingData;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.ai.EntityAIBase;
import net.minecraft.entity.ai.EntityAIHurtByTarget;
import net.minecraft.entity.ai.EntityAITasks;
import net.minecraft.entity.ai.EntityAIZombieAttack;
import net.minecraft.entity.ai.attributes.AttributeModifier;
import net.minecraft.entity.ai.attributes.IAttributeInstance;
import net.minecraft.entity.monster.EntityZombie;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.init.Items;
import net.minecraft.init.SoundEvents;
import net.minecraft.inventory.EntityEquipmentSlot;
import net.minecraft.item.EnumDyeColor;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import net.minecraft.network.datasync.DataParameter;
import net.minecraft.network.datasync.DataSerializers;
import net.minecraft.network.datasync.EntityDataManager;
import net.minecraft.util.DamageSource;
import net.minecraft.util.EnumHand;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.SoundEvent;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.DifficultyInstance;
import net.minecraft.world.EnumDifficulty;
import net.minecraft.world.World;

public class EntityObsidianSheepman extends EntityZombie implements NetherMob, ISoulEntity
{
    private static final UUID ATTACK_SPEED_BOOST_MODIFIER_UUID = UUID.fromString("49455A49-7EC5-45BA-B886-3B90B23A1718");
    private static final AttributeModifier ATTACK_SPEED_BOOST_MODIFIER = (new AttributeModifier(ATTACK_SPEED_BOOST_MODIFIER_UUID, "Attacking speed boost", 0.05D, 0)).setSaved(false);
    private static final DataParameter<Integer> TRIBE_STATE = EntityDataManager.<Integer>createKey(EntityObsidianSheepman.class, DataSerializers.VARINT);
    private static final DataParameter<Integer> TRIBE_COLOR = EntityDataManager.<Integer>createKey(EntityObsidianSheepman.class, DataSerializers.VARINT);
    private ObsidianSheepmenTribe tribe = null;
    private UUID tribeUUID = null;
    private int randomSoundDelay;
    
    public EntityObsidianSheepman(World worldIn)
    {
        super(worldIn);
        this.fixTargetTask();
        this.isImmuneToFire = true;
    }
    
    protected void entityInit()
    {
        super.entityInit();
        this.getDataManager().register(TRIBE_STATE, 0);
        this.getDataManager().register(TRIBE_COLOR, Integer.valueOf(EnumDyeColor.WHITE.getDyeDamage()));
    }
    
    protected void applyEntityAI()
    {
        this.tasks.addTask(3, new EntityObsidianSheepman.AIFollowLeader(this, 1.2D));
        this.targetTasks.addTask(0, new EntityObsidianSheepman.AIHurtByAggressor(this));
        this.targetTasks.addTask(1, new EntityAIObsidianSheepmanTarget(this));
    }

    protected void applyEntityAttributes()
    {
        super.applyEntityAttributes();
        this.getEntityAttribute(SPAWN_REINFORCEMENTS_CHANCE).setBaseValue(0.0D);
        this.getEntityAttribute(SharedMonsterAttributes.MOVEMENT_SPEED).setBaseValue(0.23500000417232513D);
    }
    
    private void fixTargetTask()
    {
    	for(EntityAITasks.EntityAITaskEntry task : this.tasks.taskEntries) {
    		if(task.action instanceof EntityAIZombieAttack) {
    			this.tasks.removeTask(task.action);
    			break;
    		}
    	}
    	
    	this.tasks.addTask(2, new EntityObsidianSheepman.AITargetAggressor(this));
    }

    protected void updateAITasks()
    {
    	if(this.hasTribe() && this.getTribe().isDirty() && this.isLeader())
    	{
    		this.getTribe().initTribe(this.worldObj);
    	}
    		
    	
        if (this.randomSoundDelay > 0 && --this.randomSoundDelay == 0)
        {
            this.playSound(AdInferosSounds.ENTITY_OBSIDIANSHEEPMAN_ANGRY, this.getSoundVolume(), ((this.rand.nextFloat() - this.rand.nextFloat()) * 0.2F + 1.0F) * 1.3F);
        }
    	
        IAttributeInstance iattributeinstance = this.getEntityAttribute(SharedMonsterAttributes.MOVEMENT_SPEED);

        if (this.getAttackTarget() != null)
        {
            if (!this.isChild() && !iattributeinstance.hasModifier(ATTACK_SPEED_BOOST_MODIFIER))
            {
                iattributeinstance.applyModifier(ATTACK_SPEED_BOOST_MODIFIER);
            }
        }
        else if (iattributeinstance.hasModifier(ATTACK_SPEED_BOOST_MODIFIER))
        {
            iattributeinstance.removeModifier(ATTACK_SPEED_BOOST_MODIFIER);
        }
        
        int i = 1200;
        int j = 1200;
        int k = 6000;
        int l = 2;

        if ((this.ticksExisted + this.getEntityId()) % 10 == 0)
        {
            for (EntityPlayerMP entityplayermp : this.worldObj.getPlayers(EntityPlayerMP.class, new Predicate<EntityPlayerMP>()
        {
            public boolean apply(EntityPlayerMP player)
                {
                    return EntityObsidianSheepman.this.canEntityBeSeen(player) && EntityObsidianSheepman.this.getDistanceSqToEntity(player) < 80.0D && player.interactionManager.survivalOrAdventure();
                }
            }))
            {
            	boolean flag = false;
            	
            	if(entityplayermp.getHeldItemMainhand().getItem().equals(Items.MUTTON) || entityplayermp.getHeldItemMainhand().getItem().equals(Items.COOKED_MUTTON)) {
            		flag = true;
            	}
            	
            	if(entityplayermp.getHeldItemOffhand().getItem().equals(Items.MUTTON) || entityplayermp.getHeldItemOffhand().getItem().equals(Items.COOKED_MUTTON)) {
            		flag = true;
            	}
            	
            	if(flag)
            	this.setAttackTarget(entityplayermp);
            }
        }
        
    	if(!this.hasTribe() && (this.ticksExisted + this.getEntityId()) % 100 == 0 && !this.isLeader())
    	{
    		this.joinTribe();
    	}

        super.updateAITasks();
    }
    
    public boolean getCanSpawnHere()
    {
        return this.worldObj.getDifficulty() != EnumDifficulty.PEACEFUL;
    }

    public boolean isNotColliding()
    {
        return this.worldObj.checkNoEntityCollision(this.getEntityBoundingBox(), this) && this.worldObj.getCollisionBoxes(this, this.getEntityBoundingBox()).isEmpty() && !this.worldObj.containsAnyLiquid(this.getEntityBoundingBox());
    }

    public void writeEntityToNBT(NBTTagCompound tagCompound)
    {
        super.writeEntityToNBT(tagCompound);
        tagCompound.setBoolean("IsLeader", this.isLeader());
        
        if(this.hasTribe()) {
        	tagCompound.setUniqueId("TribeUUID", this.getTribe().tribeUUID);
        	
        	if(this.isLeader()) {
        		NBTTagCompound tribeInfo = new NBTTagCompound();
        		this.tribe.writeToNBT(tribeInfo);
        		tagCompound.setTag("TribeInfo", tribeInfo);
        	}
        	
        }
    }
    
    public void readEntityFromNBT(NBTTagCompound tagCompund)
    {
        super.readEntityFromNBT(tagCompund);
        
        if(tagCompund.getBoolean("IsLeader")) {
        	this.setLeader(true);
        	
        	if(tagCompund.hasKey("TribeInfo")) {
        		NBTTagCompound tribeInfo = tagCompund.getCompoundTag("TribeInfo");
        		this.createTribe(tribeInfo);
        	}else {
        		this.createTribe(null);
        	}
        	
        }
        
        this.tribeUUID = tagCompund.getUniqueId("TribeUUID");
    }
    
    @Nullable
    protected SoundEvent getAmbientSound()
    {
    	return AdInferosSounds.ENTITY_OBSIDIANSHEEPMAN_IDLE;
    }
    
    @Nullable
    protected SoundEvent getHurtSound()
    {
    	return AdInferosSounds.ENTITY_OBSIDIANSHEEPMAN_HURT;
    }

    @Nullable
    protected SoundEvent getDeathSound()
    {
    	return AdInferosSounds.ENTITY_OBSIDIANSHEEPMAN_DEATH;
    }
   
    protected void playStepSound(BlockPos pos, Block block)
    {
        this.playSound(SoundEvents.ENTITY_IRONGOLEM_STEP, 0.15F, 1.0F);
    }
    
    @Nullable
    protected ResourceLocation getLootTable() {
    	return null;
    }

    @Nullable
    protected Item getDropItem() {
    	return NetherItems.OBSIDIAN_NUGGET;
    }
    
    protected void dropFewItems(boolean wasRecentlyHit, int lootingModifier) {
    	super.dropFewItems(wasRecentlyHit, lootingModifier);
    	
        int i = this.rand.nextInt(3);

        if (lootingModifier > 0)
        {
            i += this.rand.nextInt(lootingModifier + 1);
        }

        if(i > 0) {
            this.dropItem(Items.ROTTEN_FLESH, i);
        }
        
        if(wasRecentlyHit && this.rand.nextFloat() < 0.15F) {
        	this.dropItem(NetherItems.OBSIDIAN_INGOT, 1);
        }
    }

    public boolean processInteract(EntityPlayer player, EnumHand p_184645_2_, ItemStack stack)
    {
        return false;
    }

    protected void setEquipmentBasedOnDifficulty(DifficultyInstance difficulty)
    {
    	super.setEquipmentBasedOnDifficulty(difficulty);
        this.setItemStackToSlot(EntityEquipmentSlot.MAINHAND, new ItemStack(NetherItems.OBSIDIAN_SWORD));
        this.fixArmor();
    }
    
    private void fixArmor()
    {
    	ItemStack armor = this.getItemStackFromSlot(EntityEquipmentSlot.HEAD);
    	Item newArmor = null;
    	if(!armor.func_190926_b())
    	{
    		if(armor.equals(Items.DIAMOND_HELMET) || armor.equals(Items.IRON_HELMET))
    		{
    			newArmor = NetherItems.NETHERITE_HELMET;
    		}
    		else
    		{
    			newArmor = NetherItems.OBSIDIAN_HELMET;
    		}
    		
			this.setItemStackToSlot(EntityEquipmentSlot.HEAD, new ItemStack(newArmor));
    	}
    	
    	armor = this.getItemStackFromSlot(EntityEquipmentSlot.CHEST);
    	if(!armor.func_190926_b())
    	{
    		if(armor.equals(Items.DIAMOND_CHESTPLATE) || armor.equals(Items.IRON_CHESTPLATE))
    		{
    			newArmor = NetherItems.NETHERITE_CHESTPLATE;
    		}
    		else
    		{
    			newArmor = NetherItems.OBSIDIAN_CHESTPLATE;
    		}
    		
			this.setItemStackToSlot(EntityEquipmentSlot.CHEST, new ItemStack(newArmor));
    	}
    	
    	armor = this.getItemStackFromSlot(EntityEquipmentSlot.LEGS);
    	if(!armor.func_190926_b())
    	{
    		if(armor.equals(Items.DIAMOND_LEGGINGS) || armor.equals(Items.IRON_LEGGINGS))
    		{
    			newArmor = NetherItems.NETHERITE_LEGGINGS;
    		}
    		else
    		{
    			newArmor = NetherItems.OBSIDIAN_LEGGINGS;
    		}
    		
			this.setItemStackToSlot(EntityEquipmentSlot.LEGS, new ItemStack(newArmor));
    	}
    	
    	armor = this.getItemStackFromSlot(EntityEquipmentSlot.FEET);
    	if(!armor.func_190926_b())
    	{
    		if(armor.equals(Items.DIAMOND_BOOTS) || armor.equals(Items.IRON_BOOTS))
    		{
    			newArmor = NetherItems.NETHERITE_BOOTS;
    		}
    		else
    		{
    			newArmor = NetherItems.OBSIDIAN_BOOTS;
    		}
    		
			this.setItemStackToSlot(EntityEquipmentSlot.FEET, new ItemStack(newArmor));
    	}
    }

    public IEntityLivingData onInitialSpawn(DifficultyInstance difficulty, IEntityLivingData livingdata)
    {
        super.onInitialSpawn(difficulty, livingdata);
        this.setBreakDoorsAItask(true);
        
        if(!this.isChild() && ObsidianSheepmenTribe.isColorAvailable() && this.rand.nextFloat() < 0.1F) {
        	this.setLeader(true);
        	this.createTribe(null);
        	this.setHealth(this.getMaxHealth());
            this.setItemStackToSlot(EntityEquipmentSlot.OFFHAND, new ItemStack(NetherItems.OBSIDIAN_SWORD));
            this.setItemStackToSlot(EntityEquipmentSlot.HEAD, ItemStack.field_190927_a);
        }
        
		NetherConfig.printDebugInfo(this.getName() + " was spawned at " + this.getPosition());
        
        return livingdata;
    }
    
    public boolean attackEntityFrom(DamageSource source, float amount)
    {
    	
    	if(source.getEntity() instanceof EntityLivingBase && this.hasTribe())
    	{
    		this.getTribe().blackListEntity((EntityLivingBase) source.getEntity());
    		this.doAngrySound();
    	}
    	
    	return super.attackEntityFrom(source, amount);
    }
    
    public void onDeath(DamageSource cause)
    {
    	if(cause.getEntity() instanceof EntityPlayer)
    	{
    		if(this.canMakeGhost())
    		{
    			EntityGhost.createGhost(this.worldObj, this, this);
    		}
    	}
    	
    	if(this.hasTribe())
    	{
        	if(cause.getEntity() instanceof EntityLivingBase && this.hasTribe())
        	{
        		this.getTribe().blackListEntity((EntityLivingBase) cause.getEntity());
        		
        		if(this.isLeader() && this.hasTribe() && this.getTribe().hasMembers() && cause.getEntity() instanceof EntityPlayer)
        		{
        			ItemStack stack = new ItemStack(NetherItems.TRIBE_HEADBAND);
        			this.getTribe().setPlayerControlled((EntityPlayer) cause.getEntity());
        			ItemTribeHeadband.setTribeIn(stack, this.getTribe());
        			this.entityDropItem(stack, 0.0F);
        		}
        	}
    		
        	this.getTribe().removeMember(this);
    	}
    	
    	super.onDeath(cause);
    }
    
    public void setDead()
    {
    	if(this.hasTribe())
    	{
        	this.getTribe().removeMember(this);
    	}
    	
    	super.setDead();
    }
    
    protected boolean canDespawn()
    {
    	if(this.hasTribe() && this.getTribe().isPlayerControlled())
    	{
    		return false;
    	}
    	
    	return super.canDespawn();
    }
    
    public EnumDyeColor getTribeColor()
    {
        return EnumDyeColor.byDyeDamage(((Integer)this.getDataManager().get(TRIBE_COLOR)).intValue() & 15);
    }

    public void setTribeColor(EnumDyeColor tribecolor)
    {
        this.getDataManager().set(TRIBE_COLOR, Integer.valueOf(tribecolor.getDyeDamage()));
    }
    
    public boolean isLeader() {
    	return this.getDataManager().get(TRIBE_STATE) == 2;
    }
    
    public void setLeader(boolean value) {
    	this.getDataManager().set(TRIBE_STATE, value ? 2 : 0);
    	
    	if(value) {
            this.getEntityAttribute(SharedMonsterAttributes.MAX_HEALTH).setBaseValue(60.0D);
            this.getEntityAttribute(SharedMonsterAttributes.ATTACK_DAMAGE).setBaseValue(5.0D);
    	}else {
            this.getEntityAttribute(SharedMonsterAttributes.MAX_HEALTH).setBaseValue(20.0D);
            this.getEntityAttribute(SharedMonsterAttributes.ATTACK_DAMAGE).setBaseValue(3.0D);
    	}
    }
    
    public boolean isMember() {
    	return this.getDataManager().get(TRIBE_STATE) == 1;
    }
    
    public void setMember(boolean value) {
    	this.getDataManager().set(TRIBE_STATE, value ? 1 : 0);
    }
    
    private void joinTribe()
    {
    	if(this.tribeUUID != null) {
    		ObsidianSheepmenTribe tribe = ObsidianSheepmenTribe.getTribeFromUUID(tribeUUID);
    		if(tribe != null) {
    			this.setTribe(tribe);
    		}
    	}
    	else {
            for (EntityObsidianSheepman sheepMan : this.worldObj.getEntities(EntityObsidianSheepman.class, new Predicate<EntityObsidianSheepman>()
            {
                public boolean apply(EntityObsidianSheepman sheepMan)
                    {
                        return sheepMan.isLeader() && sheepMan.hasTribe() && sheepMan.isEntityAlive();
                        }
                }))
                {
                    if(sheepMan.getDistanceSqToEntity(this) < 80.0F) {
                        sheepMan.getTribe().addMember(this);
                        break;
                    }
                }
    	}
    }
    
    private void createTribe(NBTTagCompound data)
    {
    	if(this.hasTribe())
    	{
    		this.getTribe().removeMember(this);
    	}
    	
    	if(data != null)
    	{
        	this.setTribe(ObsidianSheepmenTribe.createFromNBT(this.getUniqueID(), this, data));
    	}
    	else
    	{
        	this.setTribe(new ObsidianSheepmenTribe(this.getUniqueID(), this, ObsidianSheepmenTribe.getRandomColor(this.rand)));
    	}
    	
    }
    
    public boolean hasTribe()
    {
    	return this.tribe != null;
    }
    
    public ObsidianSheepmenTribe getTribe()
    {
    	return this.tribe;
    }
    
    public void setTribe(ObsidianSheepmenTribe tribeIn)
    {
    	if(!this.isLeader()) {
    		this.setMember(tribeIn != null);
    	}
    	
    	this.tribe = tribeIn;
    	
    	if(tribeIn != null) {
    		this.tribeUUID = tribeIn.tribeUUID;
    		this.setTribeColor(tribeIn.tribeColor);
    	}else {
    		this.tribeUUID = null;
    		this.setTribeColor(EnumDyeColor.WHITE);
    	}
    }
    
    public void doAngrySound()
    {
        this.randomSoundDelay = this.rand.nextInt(40);
    }
    
	public boolean canMakeGhost()
	{
		return NetherConfig.ghostSpawnFromEntities && !this.isChild() && !this.isLeader() && !BlockSpawner.isSpawnerEntity(this) && this.rand.nextInt(30) == 0;
	}

	public String getTexture()
	{
		return "adinferos:textures/entity/obsidian_sheepman/main.png";
	}
	
	public void onCreateGhost(EntityGhost ghost) {}

	public boolean isDarkfireResistant()
	{
		return false;
	}

	public boolean isCurseResistant()
	{
		return false;
	}

	public boolean isWitherResistant()
	{
		return false;
	}
	
	public boolean isAcidResistant()
	{
		return true;
	}
	
	public boolean isSpikeResistant()
	{
		return false;
	}
	
	public static class ObsidianSheepmenTribe
	{
		private EntityLivingBase leader;
		private final ArrayList<EntityObsidianSheepman> members;
		private final ArrayList<UUID> blackListedEntities;
		private UUID tribeUUID = null;
		private EnumDyeColor tribeColor = EnumDyeColor.WHITE;
		private static final Map<UUID,ObsidianSheepmenTribe> tribes = Maps.<UUID,ObsidianSheepmenTribe>newHashMap();
		private static final ArrayList<EnumDyeColor> colors = buildColorList();
		private boolean markDirty = false;
		
		public ObsidianSheepmenTribe(UUID uuid, EntityObsidianSheepman leaderIn, EnumDyeColor color) {
			this.leader = leaderIn;
			this.members = new ArrayList<EntityObsidianSheepman>();
			this.blackListedEntities = new ArrayList<UUID>();
			this.tribeUUID = uuid;
			this.tribeColor = color;
			this.tribes.put(uuid,this);
			this.colors.remove(color);
		}
		
		public boolean isDirty() {
			return this.markDirty;
		}
		
		public void initTribe(World world) {
			
            for (EntityObsidianSheepman sheepMan : world.getEntities(EntityObsidianSheepman.class, new Predicate<EntityObsidianSheepman>()
            {
                public boolean apply(EntityObsidianSheepman sheepMan)
                    {
                        return sheepMan != null && !sheepMan.isLeader() && !sheepMan.hasTribe() && sheepMan.tribeUUID != null && sheepMan.tribeUUID.equals(ObsidianSheepmenTribe.this.tribeUUID);
                        }
                }))
                {
            	this.addMember(sheepMan);
                }
			
			this.markDirty = false;
		}

		public void addMember(EntityObsidianSheepman sheepMan) {
			this.members.add(sheepMan);
			sheepMan.setTribe(this);
		}
		
		public void removeMember(EntityObsidianSheepman sheepMan) {
			this.members.remove(sheepMan);
			sheepMan.setTribe(null);
			
			if(sheepMan.equals(this.leader)) {
				this.disband();
			}
		}
		
		public void disband() {
			for(EntityObsidianSheepman member : this.members) {
				member.setTribe(null);
			}
			
			if(this.leader instanceof EntityObsidianSheepman) {
				((EntityObsidianSheepman) this.leader).setLeader(false);
			}
			this.leader = null;
			this.members.clear();
			this.blackListedEntities.clear();
			this.tribes.remove(this.tribeUUID);
			this.colors.add(this.tribeColor);
		}
		
		public void blackListEntity(EntityLivingBase entity) {
			
			if(!this.isAlly(entity)) {
				this.blackListedEntities.add(entity.getUniqueID());
			}
		}
		
		public boolean isEntityBlackListed(EntityLivingBase entity) {
			
			if(this.isAlly(entity)) {
				return false;
			}
			
			return this.blackListedEntities.contains(entity.getUniqueID());
		}
		
		public boolean isAlly(EntityLivingBase entity) {
			
			if(this.getLeader() != null && entity != null && entity.getUniqueID() == this.getLeader().getUniqueID())
			{
				return true;
			}
			else if(this.members.contains(entity))
			{
				return true;
			}
			else
			{
				return false;
			}
		}
		
		public void setLeader(EntityLivingBase leader) {
			
			this.leader = leader;
			
			if(this.blackListedEntities.contains(this.leader.getUniqueID())) {
				this.blackListedEntities.remove(this.leader.getUniqueID());
			}
			
			for(EntityObsidianSheepman member : this.members) {
				
				if(leader instanceof EntityPlayer) {
					if(member.isBreakDoorsTaskSet()) {
						member.setBreakDoorsAItask(false);
					}
				}
				else {
					if(!member.isBreakDoorsTaskSet()) {
						member.setBreakDoorsAItask(true);
					}
				}
				
				member.setAttackTarget(null);
			}
		}
		
		public EntityLivingBase getLeader() {
			
			if(this.leader instanceof EntityPlayer) {
				if(this.leader.getItemStackFromSlot(EntityEquipmentSlot.HEAD) != null) {
					ItemStack stack = this.leader.getItemStackFromSlot(EntityEquipmentSlot.HEAD);
					if(!ItemTribeHeadband.getTribeUUID(stack).equals(this.tribeUUID)) {
						return null;
					}
				}else {
					return null;
				}
			}
			
			return this.leader;
		}
		
		public EntityLivingBase theLeader() {
			return this.leader;
		}
		
		public UUID getUUID() {
			return this.tribeUUID;
		}
		
		public EnumDyeColor getColor() {
			return this.tribeColor;
		}
		
		public ArrayList<EntityObsidianSheepman> getMembers() {
			return this.members;
		}
		
		public boolean isPlayerControlled() {
			return this.theLeader() instanceof EntityPlayer;
		}
		
		public boolean hasMembers() {
			return this.members.size() > 0;
		}
		
		public void setPlayerControlled(EntityPlayer leader) {
			this.leader = leader;
			this.tribes.remove(this.tribeUUID);
			this.tribeUUID = leader.getUniqueID();
			this.tribes.put(this.tribeUUID, this);
			
			if(this.blackListedEntities.contains(this.leader.getUniqueID())) {
				this.blackListedEntities.remove(this.tribeUUID);
			}
			
			for(EntityObsidianSheepman member : this.members) {
				member.setAttackTarget(null);
			}
		}
		
		public void writeToNBT(NBTTagCompound tagCompound) {
			
	        tagCompound.setByte("Color", (byte) this.tribeColor.getDyeDamage());
			
			NBTTagList enemies = new NBTTagList();
			
			for(UUID enemy : this.blackListedEntities) {
				NBTTagCompound enemyNBT = new NBTTagCompound();
				enemyNBT.setUniqueId("UUID", enemy);
				enemies.appendTag(enemyNBT);
			}

			tagCompound.setTag("Enemies", enemies);
		}
		
		public static ObsidianSheepmenTribe createFromNBT(UUID uuidIn, EntityObsidianSheepman leader, NBTTagCompound tagCompound) {
			
			EnumDyeColor color = EnumDyeColor.WHITE;
			
	        if (tagCompound.hasKey("Color", 99))
	        {
	        	color = (EnumDyeColor.byDyeDamage(tagCompound.getByte("Color")));
	        }
	        
	        ObsidianSheepmenTribe tribe = new ObsidianSheepmenTribe(uuidIn, leader, color);
	        
	        tribe.markDirty = true;
			
			if(tagCompound.hasKey("Enemies")) {
				NBTTagList enemies = tagCompound.getTagList("Enemies", 10);
				
	            for (int i = 0; i < enemies.tagCount(); ++i)
	            {
	                NBTTagCompound enemy = enemies.getCompoundTagAt(i);
	                UUID uuid = enemy.getUniqueId("UUID");
	                tribe.blackListedEntities.add(uuid);
	            }
			}
			
			return tribe;
			
		}
		
		public static EnumDyeColor getRandomColor(Random rand) {
			
			if(colors.size() < 1) {
				return EnumDyeColor.WHITE;
			}
			
			return colors.get(rand.nextInt(colors.size()));
		}
		
		public static boolean isColorAvailable() {
			
			return colors.size() > 0;
		}
		
		public static ObsidianSheepmenTribe getTribeFromUUID(UUID uuid) {
			return tribes.get(uuid);
		}
		
		public static ObsidianSheepmenTribe getTribeFromEntity(Entity entity) {
			return tribes.get(entity.getUniqueID());
		}
		
		private static ArrayList<EnumDyeColor> buildColorList() {
			ArrayList<EnumDyeColor> list = new ArrayList<EnumDyeColor>();
			
			list.add(EnumDyeColor.ORANGE);
			list.add(EnumDyeColor.MAGENTA);
			list.add(EnumDyeColor.LIGHT_BLUE);
			list.add(EnumDyeColor.YELLOW);
			list.add(EnumDyeColor.LIME);
			list.add(EnumDyeColor.PINK);
			list.add(EnumDyeColor.GRAY);
			list.add(EnumDyeColor.SILVER);
			list.add(EnumDyeColor.CYAN);
			list.add(EnumDyeColor.PURPLE);
			list.add(EnumDyeColor.BLUE);
			list.add(EnumDyeColor.BROWN);
			list.add(EnumDyeColor.GREEN);
			list.add(EnumDyeColor.RED);
			
			return list;
		}
	}
	
	public static class AIFollowLeader extends EntityAIBase
	{
	    /** The child that is following its parent. */
	    EntityObsidianSheepman taskOwner;
	    EntityLivingBase tribeLeader;
	    double moveSpeed;
	    private int delayCounter;

	    public AIFollowLeader(EntityObsidianSheepman entityIn, double speed)
	    {
	        this.taskOwner = entityIn;
	        this.moveSpeed = speed;
	    }

	    /**
	     * Returns whether the EntityAIBase should begin execution.
	     */
	    public boolean shouldExecute()
	    {
	        if (!this.taskOwner.hasTribe())
	        {
	            return false;
	        }
	        else if(this.taskOwner.getTribe().getLeader() == null)
	        {
	        	return false;
	        }
	        else
	        {
	        	this.tribeLeader = this.taskOwner.getTribe().getLeader();
	        	double d0 = this.taskOwner.getDistanceSqToEntity(this.tribeLeader);
	            if (d0 < 140.0D || this.taskOwner.getAttackTarget() != null || !this.taskOwner.getNavigator().noPath())
	            {
	                return false;
	            }
	            else
	            {
	                return true;
	            }
	        }
	    }

	    /**
	     * Returns whether an in-progress EntityAIBase should continue executing
	     */
	    public boolean continueExecuting()
	    {
	        if (!this.taskOwner.hasTribe())
	        {
	            return false;
	        }
	        else if (!this.tribeLeader.isEntityAlive())
	        {
	            return false;
	        }
	        else if(this.taskOwner.getDistanceSqToEntity(this.tribeLeader) < 80.0D)
	        {
	            this.taskOwner.getNavigator().setPath(null, 0.0D);
	            return false;
	        }
	        else
	        {
	        	return true;
	        }
	    }

	    /**
	     * Execute a one shot task or start executing a continuous task
	     */
	    public void startExecuting()
	    {
	        this.delayCounter = 0;
	    }

	    /**
	     * Resets the task
	     */
	    public void resetTask()
	    {
	        this.tribeLeader = null;
	    }

	    /**
	     * Updates the task
	     */
	    public void updateTask()
	    {
	        if (--this.delayCounter <= 0)
	        {
	            this.delayCounter = 10;
	            this.taskOwner.getNavigator().tryMoveToEntityLiving(this.tribeLeader, this.moveSpeed);
	        }
	    }
	}
	
	static class AITargetAggressor extends EntityAIZombieAttack
	{
		private final EntityObsidianSheepman sheepMan;
		
		public AITargetAggressor(EntityObsidianSheepman entity) {
			super(entity, 1.0D, false);
			this.sheepMan = entity;
		}
		
		public boolean shouldExecute() {
			
			if(this.sheepMan.getAttackTarget() == null)
			{
				return false;
			}
			else if(this.sheepMan.hasTribe() && this.sheepMan.getTribe().isAlly(this.sheepMan.getAttackTarget()))
			{
				return false;
			}
			
			return super.shouldExecute();
		}
		
        public boolean continueExecuting()
        {
            if (this.sheepMan.hasTribe() && this.sheepMan.getTribe().isAlly(this.sheepMan.getAttackTarget()))
            {
            	this.sheepMan.attackingPlayer = (EntityPlayer)null;
            	this.sheepMan.setLastAttacker((EntityLivingBase)null);
            	this.sheepMan.setRevengeTarget((EntityLivingBase)null);
	            this.sheepMan.getNavigator().setPath(null, 0.0D);
                this.sheepMan.setAttackTarget((EntityLivingBase)null);
                return false;
            }
            else
            {
                return super.continueExecuting();
            }
        }
		
	}
	
    static class AIHurtByAggressor extends EntityAIHurtByTarget
    {
    	private final EntityObsidianSheepman entitySheepman;
        public AIHurtByAggressor(EntityObsidianSheepman entity)
        {
            super(entity, false, new Class[0]);
            this.entitySheepman = entity;
        }
        
        protected boolean isSuitableTarget(EntityLivingBase target, boolean includeInvincibles) {
        	
        	if(this.entitySheepman.hasTribe() && this.entitySheepman.getTribe().isAlly(target)) {
        		return false;
        	}
        	
        	return super.isSuitableTarget(target, includeInvincibles);
        }
    }
}