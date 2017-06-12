package com.superdextor.adinferos.inventory;

import java.util.Random;

import com.superdextor.adinferos.init.NetherItems;
import com.superdextor.adinferos.inventory.ExtractorRecipes.ExtractorRecipe;
import com.superdextor.adinferos.items.ItemBloodBucket;
import com.superdextor.adinferos.items.ItemSoul;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.init.SoundEvents;
import net.minecraft.inventory.Container;
import net.minecraft.inventory.IInventory;
import net.minecraft.inventory.ISidedInventory;
import net.minecraft.inventory.ItemStackHelper;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntityLockable;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.ITickable;
import net.minecraft.util.NonNullList;
import net.minecraft.util.SoundCategory;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class TileEntityExtractor extends TileEntityLockable implements ITickable, ISidedInventory
{
    private static final int[] slotsTop = new int[] {3};
    private static final int[] slotsBottom = new int[] {3, 1};
    private static final int[] slotsSides = new int[] {0};
    private NonNullList<ItemStack> extractorItemStacks = NonNullList.<ItemStack>func_191197_a(4, ItemStack.field_190927_a);
    private int extractorBlood;
    private int burnTime;
    private int burnedSoulCount;
    private int requiredSoulCount;
    private String extractorCustomName;
    private final Random rand = new Random();

    public int getSizeInventory()
    {
        return this.extractorItemStacks.size();
    }
    
    public boolean func_191420_l()
    {
        for (ItemStack itemstack : this.extractorItemStacks)
        {
            if (!itemstack.func_190926_b())
            {
                return false;
            }
        }

        return true;
    }
    
    public ItemStack getStackInSlot(int index)
    {
        return (ItemStack)this.extractorItemStacks.get(index);
    }

    public ItemStack decrStackSize(int index, int count)
    {
        return ItemStackHelper.getAndSplit(this.extractorItemStacks, index, count);
    }

    public ItemStack removeStackFromSlot(int index)
    {
        return ItemStackHelper.getAndRemove(this.extractorItemStacks, index);
    }

    public void setInventorySlotContents(int index, ItemStack stack)
    {
        ItemStack itemstack = (ItemStack)this.extractorItemStacks.get(index);
        boolean flag = !stack.func_190926_b() && stack.isItemEqual(itemstack) && ItemStack.areItemStackTagsEqual(stack, itemstack);
        this.extractorItemStacks.set(index, stack);

        if (stack.func_190916_E() > this.getInventoryStackLimit())
        {
            stack.func_190920_e(this.getInventoryStackLimit());
        }

        if (index == 0 && !flag)
        {
            this.requiredSoulCount = ItemSoul.getSoulRepairAmount(this.extractorItemStacks.get(0));
            this.burnTime = 0;
            this.markDirty();
        }
    }

    public String getName()
    {
        return this.hasCustomName() ? this.extractorCustomName : "container.extractor";
    }

    public boolean hasCustomName()
    {
        return this.extractorCustomName != null && !this.extractorCustomName.isEmpty();
    }

    public void setCustomInventoryName(String value)
    {
        this.extractorCustomName = value;
    }

    public void readFromNBT(NBTTagCompound compound)
    {
        super.readFromNBT(compound);
        this.extractorItemStacks = NonNullList.<ItemStack>func_191197_a(this.getSizeInventory(), ItemStack.field_190927_a);
        ItemStackHelper.func_191283_b(compound, this.extractorItemStacks);
        this.extractorBlood = compound.getInteger("Blood");
        this.burnTime = compound.getInteger("BurnTime");
        this.burnedSoulCount = compound.getInteger("BurnedSoulCount");
        this.requiredSoulCount = ItemSoul.getSoulRepairAmount(this.extractorItemStacks.get(0));

        if (compound.hasKey("CustomName", 8))
        {
            this.extractorCustomName = compound.getString("CustomName");
        }
    }

    public NBTTagCompound writeToNBT(NBTTagCompound compound)
    {
        super.writeToNBT(compound);
        compound.setInteger("Blood", this.extractorBlood);
        compound.setInteger("BurnTime", this.burnTime);
        compound.setInteger("BurnedSoulCount", this.burnedSoulCount);
        ItemStackHelper.func_191282_a(compound, this.extractorItemStacks);

        if (this.hasCustomName())
        {
            compound.setString("CustomName", this.extractorCustomName);
        }
        
        return compound;
    }

    public int getInventoryStackLimit()
    {
        return 64;
    }

    public boolean isBurning()
    {
        return this.burnTime > 0;
    }
    
    public int getRequiredBlood(ItemStack stack)
    {
    	
    	if(stack == null || !ItemSoul.hasSoul(stack)) {
    		return 999;
    	}
    	
    	ExtractorRecipe recipe = ExtractorRecipes.getRecipe(ItemSoul.getSoulID(stack).toString());
    	
    	if(recipe != null) {
    		return recipe.bloodRequired;
    	}
    	
        return 75;
    }

    @SideOnly(Side.CLIENT)
    public static boolean isBurning(IInventory inventory)
    {
        return inventory.getField(1) > 0;
    }

    public void update()
    {
        boolean shouldUpdate = false;

        if (this.isBurning())
        {
            --this.burnTime;
        }

        if (!this.worldObj.isRemote)
        {
        	
        	if(this.isBurning() && this.rand.nextDouble() < 0.06D) {
        		this.worldObj.playSound(null, this.getPos(), SoundEvents.BLOCK_FIRE_EXTINGUISH, SoundCategory.BLOCKS, 0.7F, 1.2F);
        	}
        	
        	if(ItemBloodBucket.hasBlood(this.extractorItemStacks.get(3))) {
        		shouldUpdate = true;
        		this.extractorBlood += ItemBloodBucket.getBlood(this.extractorItemStacks.get(3));
        		ItemBloodBucket.setBlood(this.extractorItemStacks.get(3), 0);
        		
        		if(this.extractorItemStacks.get(3).getItem().equals(NetherItems.GOLDEN_BUCKET_BLOOD)) {
        			if(this.extractorItemStacks.get(3).getMetadata() > 0) {
            			this.extractorItemStacks.set(3, ItemStack.field_190927_a);
        			}else {
            			this.extractorItemStacks.set(3, new ItemStack(NetherItems.GOLDEN_BUCKET));
        			}
        		}
        		
        		this.worldObj.playSound(null, this.getPos(), SoundEvents.BLOCK_BREWING_STAND_BREW, SoundCategory.BLOCKS, 1.4F, 0.7F);
        	}
        	
        	
            if (this.canExtract())
            {
            	if(!this.isBurning()) {
            		
            		if(this.burnedSoulCount >= this.requiredSoulCount) {
            			shouldUpdate = true;
            			this.extractSoul();
            		}
            		else if(this.hasSouls()) {
                		shouldUpdate = true;
                		this.burnTime = 130;
                		
                        if (!this.extractorItemStacks.get(1).func_190926_b())
                        {
                            this.extractorItemStacks.get(1).func_190918_g(1);
                        }
            		}
            	}
            	else if(this.burnTime == 1) {
            		++this.burnedSoulCount;
            		if(this.burnedSoulCount == this.requiredSoulCount) {
            			this.burnTime = 65;
            		}
            	}
            }
            else if(!this.isBurning() && this.burnedSoulCount > 0) {
            	--this.burnedSoulCount;
            }
        }

        if (shouldUpdate)
        {
            this.markDirty();
        }
    }
    
    private boolean canExtract() {
    	
    	if(this.extractorItemStacks.get(0).func_190926_b() || !ItemSoul.hasSoul(this.extractorItemStacks.get(0)) || !this.extractorItemStacks.get(2).func_190926_b()) {
    		return false;
    	}
    	
    	if(!this.isBurning() && !this.hasSouls()) {
    		return false;
    	}
    	
    	if(this.extractorBlood < this.getRequiredBlood(this.extractorItemStacks.get(0))) {
    		return false;
    	}
    		
    	return true;
    }
    
    private boolean hasSouls() {
    	return this.extractorItemStacks.get(1).getItem() == NetherItems.SOUL_FRAGMENT;
    }

    private void extractSoul() {
    	this.burnedSoulCount = 0;
    	this.extractorBlood -= this.getRequiredBlood(this.extractorItemStacks.get(0));
    	ItemStack soul = ItemSoul.extractSoulFromItem(this.extractorItemStacks.get(0));
    	this.extractorItemStacks.set(2, soul);
    }

    /**
     * Do not make give this method the name canInteractWith because it clashes with Container
     */
    public boolean isUseableByPlayer(EntityPlayer player)
    {
        return this.worldObj.getTileEntity(this.pos) != this ? false : player.getDistanceSq((double)this.pos.getX() + 0.5D, (double)this.pos.getY() + 0.5D, (double)this.pos.getZ() + 0.5D) <= 64.0D;
    }

    public void openInventory(EntityPlayer player)
    {
    }

    public void closeInventory(EntityPlayer player)
    {
    }

    /**
     * Returns true if automation is allowed to insert the given stack (ignoring stack size) into the given slot.
     */
    public boolean isItemValidForSlot(int index, ItemStack stack)
    {
        if (index == 0 && ItemSoul.hasSoul(stack))
        {
            return true;
        }
        else if (index == 1 && stack != null && stack.getItem() == NetherItems.SOUL_FRAGMENT)
        {
            return true;
        }
        else if(index == 3 && ItemBloodBucket.hasBlood(stack)) {
        	return true;
        }
        else
        {
        	return false;
        }
    }

    public int[] getSlotsForFace(EnumFacing side)
    {
        return side == EnumFacing.DOWN ? slotsBottom : (side == EnumFacing.UP ? slotsTop : slotsSides);
    }

    /**
     * Returns true if automation can insert the given item in the given slot from the given side.
     *  
     * @param index The slot index to test insertion into
     * @param itemStackIn The item to test insertion of
     * @param direction The direction to test insertion from
     */
    public boolean canInsertItem(int index, ItemStack itemStackIn, EnumFacing direction)
    {
        return this.isItemValidForSlot(index, itemStackIn);
    }

    /**
     * Returns true if automation can extract the given item in the given slot from the given side.
     *  
     * @param index The slot index to test extraction from
     * @param stack The item to try to extract
     * @param direction The direction to extract from
     */
    public boolean canExtractItem(int index, ItemStack stack, EnumFacing direction)
    {
		if(index == 3 && ItemBloodBucket.hasBlood(stack)) {
			return false;
		}
    	
        return true;
    }

    public String getGuiID()
    {
        return "adinferos:extractor";
    }

    public Container createContainer(InventoryPlayer playerInventory, EntityPlayer playerIn)
    {
        return new ContainerExtractor(playerInventory, this);
    }

    public int getField(int id)
    {
        switch (id)
        {
            case 0:
                return this.extractorBlood;
            case 1:
                return this.burnTime;
            case 2:
                return this.burnedSoulCount > this.requiredSoulCount ? 0 : this.burnedSoulCount;
            case 3:
                return this.requiredSoulCount;
            default:
                return 0;
        }
    }

    public void setField(int id, int value)
    {
        switch (id)
        {
            case 0:
                this.extractorBlood = value;
                break;
            case 1:
                this.burnTime = value;
                break;
            case 2:
                this.burnedSoulCount = value;
                break;
            case 3:
            	this.requiredSoulCount = value;
            	
        }
    }

    public int getFieldCount()
    {
        return 4;
    }

    public void clear()
    {
    	this.extractorItemStacks.clear();
    }

    net.minecraftforge.items.IItemHandler handlerTop = new net.minecraftforge.items.wrapper.SidedInvWrapper(this, net.minecraft.util.EnumFacing.UP);
    net.minecraftforge.items.IItemHandler handlerBottom = new net.minecraftforge.items.wrapper.SidedInvWrapper(this, net.minecraft.util.EnumFacing.DOWN);
    net.minecraftforge.items.IItemHandler handlerSide = new net.minecraftforge.items.wrapper.SidedInvWrapper(this, net.minecraft.util.EnumFacing.WEST);

    @SuppressWarnings("unchecked")
    @Override
    public <T> T getCapability(net.minecraftforge.common.capabilities.Capability<T> capability, net.minecraft.util.EnumFacing facing)
    {
        if (facing != null && capability == net.minecraftforge.items.CapabilityItemHandler.ITEM_HANDLER_CAPABILITY)
            if (facing == EnumFacing.DOWN)
                return (T) handlerBottom;
            else if (facing == EnumFacing.UP)
                return (T) handlerTop;
            else
                return (T) handlerSide;
        return super.getCapability(capability, facing);
    }
}