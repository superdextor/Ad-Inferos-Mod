package com.superdextor.adinferos.entity.other;

import com.superdextor.adinferos.init.NetherItems;

import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.projectile.EntityArrow;
import net.minecraft.init.MobEffects;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.network.datasync.DataParameter;
import net.minecraft.network.datasync.DataSerializers;
import net.minecraft.network.datasync.EntityDataManager;
import net.minecraft.potion.PotionEffect;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.world.World;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class EntityNetherArrow extends EntityArrow
{
    private static final DataParameter<Integer> TYPE = EntityDataManager.<Integer>createKey(EntityNetherArrow.class, DataSerializers.VARINT);

    public EntityNetherArrow(World worldIn)
    {
        super(worldIn);
    }

    public EntityNetherArrow(World worldIn, double x, double y, double z)
    {
        super(worldIn, x, y, z);
    }

    public EntityNetherArrow(World worldIn, EntityLivingBase shooter)
    {
        super(worldIn, shooter);
    }
    
    protected void entityInit()
    {
        super.entityInit();
        this.getDataManager().register(TYPE, 0);
    }
    
    /**Returns the Arrow's Type, 0 = Quartz 1 = Glowstone 2 = Obsidian 3 = Wither 4 = Netherite **/
    public int getArrowType() {
    	return this.getDataManager().get(TYPE);
    }
    
    /**Sets the Arrow's Type, 0 = Quartz 1 = Glowstone 2 = Obsidian 3 = Wither 4 = Netherite **/
    public void setArrowType(int value) {
        this.getDataManager().set(TYPE, value);
    }

    /**
     * (abstract) Protected helper method to write subclass entity data to NBT.
     */
    public void writeEntityToNBT(NBTTagCompound tagCompound)
    {
    	super.writeEntityToNBT(tagCompound);
        tagCompound.setByte("ArrowType", (byte)this.getArrowType());
    }

    /**
     * (abstract) Protected helper method to read subclass entity data from NBT.
     */
    public void readEntityFromNBT(NBTTagCompound tagCompund)
    {
    	super.readEntityFromNBT(tagCompund);
        
        if (tagCompund.hasKey("ArrowType", 99))
        {
            byte b0 = tagCompund.getByte("ArrowType");
            this.setArrowType(b0);
        }
    }
    
    protected ItemStack getArrowStack()
    {
    	
        Item itemArrow = NetherItems.QUARTZ_ARROW;
        
        switch(getArrowType()) {
        	case 0:
        		itemArrow = NetherItems.QUARTZ_ARROW;
        		break;
        	case 1:
        		itemArrow = NetherItems.GLOWSTONE_ARROW;
        		break;
        	case 2:
        		itemArrow = NetherItems.OBSIDIAN_ARROW;
        		break;
        	case 3:
        		itemArrow = NetherItems.WITHER_ARROW;
        		break;
        	case 4:
        		itemArrow = NetherItems.NETHERITE_ARROW;
        		break;
        }
    	
    	return new ItemStack(itemArrow);
    }
    
    protected void arrowHit(EntityLivingBase living)
    {
        switch(this.getArrowType()) {
	        
	        case 1:
	        	living.addPotionEffect(new PotionEffect(MobEffects.GLOWING, 140, 0));
	        	break;
	        case 2: {
	            double f4 = MathHelper.sqrt_double(this.motionX * this.motionX + this.motionZ * this.motionZ);
	
	            if (f4 > 0.0F)
	            {
	            	living.addVelocity(this.motionX * (double)1.5 * 0.6000000238418579D / (double)f4, 0.1D, this.motionZ * (double)1.5 * 0.6000000238418579D / (double)f4);
	            }
	            
	            break;
	        }
	        
	        case 3: {
	        	living.addPotionEffect(new PotionEffect(MobEffects.WITHER, 120, 1));
	        	break;
	        }
	        
	        case 4: {
	        	
	        	break;
	        }
        }
    }
    
    @SideOnly(Side.CLIENT)
    public int getBrightnessForRender(float partialTicks)
    {
        BlockPos.MutableBlockPos blockpos$mutableblockpos = new BlockPos.MutableBlockPos(MathHelper.floor_double(this.posX), 0, MathHelper.floor_double(this.posZ));

        if (this.worldObj.isBlockLoaded(blockpos$mutableblockpos))
        {
            blockpos$mutableblockpos.setY(MathHelper.floor_double(this.posY + (double)this.getEyeHeight()));
            return this.worldObj.getCombinedLight(blockpos$mutableblockpos, 0);
        }
        else
        {
            return 0;
        }
    }
}