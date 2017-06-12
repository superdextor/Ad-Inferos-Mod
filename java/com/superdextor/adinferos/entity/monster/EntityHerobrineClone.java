package com.superdextor.adinferos.entity.monster;

import com.superdextor.adinferos.entity.NetherMob;

import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.ai.EntityAIAttackMelee;
import net.minecraft.entity.ai.EntityAILookIdle;
import net.minecraft.entity.ai.EntityAISwimming;
import net.minecraft.entity.ai.EntityAIWander;
import net.minecraft.entity.ai.EntityAIWatchClosest;
import net.minecraft.entity.monster.EntityMob;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.SoundEvents;
import net.minecraft.util.DamageSource;
import net.minecraft.util.EnumParticleTypes;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;

public class EntityHerobrineClone extends EntityMob implements NetherMob
{
	private EntityHerobrine master;
    
    public EntityHerobrineClone(World worldIn)
    {
        super(worldIn);
        this.experienceValue = 0;
        this.isImmuneToFire = true;
        this.tasks.addTask(0, new EntityAISwimming(this));
        this.tasks.addTask(1, new EntityAIAttackMelee(this, 1.0D, false));
        this.tasks.addTask(2, new EntityAIWander(this, 1.0D));
        this.tasks.addTask(3, new EntityAIWatchClosest(this, EntityPlayer.class, 8.0F));
        this.tasks.addTask(4, new EntityAILookIdle(this));
    }
    
    public void setOwner(EntityHerobrine herobrine, EntityLivingBase target)
    {
    	this.setAttackTarget(target);
    	this.master = herobrine;
    }

    protected void applyEntityAttributes()
    {
        super.applyEntityAttributes();
        this.getEntityAttribute(SharedMonsterAttributes.MAX_HEALTH).setBaseValue(1.0D);
        this.getEntityAttribute(SharedMonsterAttributes.MOVEMENT_SPEED).setBaseValue(0.43D);
        this.getEntityAttribute(SharedMonsterAttributes.ATTACK_DAMAGE).setBaseValue(5.0D);
    }
    
    public void onLivingUpdate()
    {
    	if(!this.worldObj.isRemote) {
    		
            if(this.getAttackTarget() != null && this.getDistanceSqToEntity(this.getAttackTarget()) > 400.0D) {
            	this.teleportToEntity(this.getAttackTarget());
            }
            
            if(this.master == null || !this.master.isCloaking()) {
            	this.attackEntityFrom(DamageSource.outOfWorld, 1);
            }
    	}
    	
        super.onLivingUpdate();
    }
    
    public boolean teleportRandomly()
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

    protected boolean teleportToEntity(Entity p_70816_1_)
    {
        Vec3d vec3d = new Vec3d(this.posX - p_70816_1_.posX, this.getEntityBoundingBox().minY + (double)(this.height / 2.0F) - p_70816_1_.posY + (double)p_70816_1_.getEyeHeight(), this.posZ - p_70816_1_.posZ);
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
    
    public boolean attackEntityFrom(DamageSource source, float damage)
    {
    	if(source == DamageSource.wither) {
    		return false;
    	}
    	
        if (this.worldObj.isRemote)
        {
            for (int i = 0; i < 20; ++i)
            {
                this.worldObj.spawnParticle(EnumParticleTypes.PORTAL, this.posX + (this.rand.nextDouble() - 0.5D) * (double)this.width, this.posY + this.rand.nextDouble() * (double)this.height + 1.0D, this.posZ + (this.rand.nextDouble() - 0.5D) * (double)this.width, (this.rand.nextDouble() - 0.5D) * 2.0D, -this.rand.nextDouble(), (this.rand.nextDouble() - 0.5D) * 2.0D, new int[0]);
            }
        }
    	
        this.playSound(SoundEvents.ENTITY_ENDERMEN_TELEPORT, 1.0F, 1.0F);
        this.setDead();
        return false;
    }
	
	public void fall(float distance, float damageMultiplier) {}
	
    public boolean isNotColliding()
    {
        return this.worldObj.checkNoEntityCollision(this.getEntityBoundingBox(), this) && this.worldObj.getCollisionBoxes(this, this.getEntityBoundingBox()).isEmpty() && !this.worldObj.containsAnyLiquid(this.getEntityBoundingBox());
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
}