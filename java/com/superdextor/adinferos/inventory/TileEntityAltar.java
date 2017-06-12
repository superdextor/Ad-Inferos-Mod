package com.superdextor.adinferos.inventory;

import java.util.Random;

import com.google.common.base.Predicate;
import com.superdextor.adinferos.blocks.BlockChiseledNetherrack;
import com.superdextor.adinferos.entity.monster.EntityInfernumAvis;
import com.superdextor.adinferos.init.NetherBlocks;
import com.superdextor.adinferos.init.NetherItems;
import com.superdextor.adinferos.inventory.AltarRecipes.AltarRecipe;
import com.superdextor.adinferos.items.ItemBloodBucket;

import net.minecraft.block.Block;
import net.minecraft.block.BlockSoulSand;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.init.SoundEvents;
import net.minecraft.inventory.Container;
import net.minecraft.inventory.ISidedInventory;
import net.minecraft.inventory.ItemStackHelper;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import net.minecraft.tileentity.TileEntityLockable;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumParticleTypes;
import net.minecraft.util.ITickable;
import net.minecraft.util.NonNullList;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.text.TextComponentString;
import net.minecraft.util.text.TextFormatting;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class TileEntityAltar extends TileEntityLockable implements ITickable, ISidedInventory
{
    private static final int[] slotsTop = new int[] {0};
    private static final int[] slotsBottom = new int[] {2, 1};
    private static final int[] slotsSides = new int[] {1};
    /** The ItemStacks that hold the items currently being used in the furnace */
    private NonNullList<ItemStack> altarItemStacks = NonNullList.<ItemStack>func_191197_a(7, ItemStack.field_190927_a);
    /** The number of ticks that a fresh copy of the currently-burning item would keep the furnace burning for */
	public int age;
    private int cookTime;
    private String customName;
    private static Random rand = new Random();
	private boolean isActive = false;
	public int blood = 0;
	private int bloodForBar = 600;
	private int summonCountdown = -1;
	
    private float recipeDuration = 0.0F;
    private boolean isObsidian = false;;
    
    public void setObsidian() {
    	this.isObsidian = true;
        this.bloodForBar = 200;
    }
    
    public boolean func_191420_l()
    {
        for (ItemStack itemstack : this.altarItemStacks)
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
        return (ItemStack)this.altarItemStacks.get(index);
    }
    
    public ItemStack decrStackSize(int index, int count)
    {
        return ItemStackHelper.getAndSplit(this.altarItemStacks, index, count);
    }
    
    public ItemStack removeStackFromSlot(int index)
    {
        return ItemStackHelper.getAndRemove(this.altarItemStacks, index);
    }
    
    public void setInventorySlotContents(int index, ItemStack stack)
    {
        ItemStack itemstack = (ItemStack)this.altarItemStacks.get(index);
        boolean flag = !stack.func_190926_b() && stack.isItemEqual(itemstack) && ItemStack.areItemStackTagsEqual(stack, itemstack);
        this.altarItemStacks.set(index, stack);

        if (stack.func_190916_E() > this.getInventoryStackLimit())
        {
            stack.func_190920_e(this.getInventoryStackLimit());
        }

        if (index == 0 && !flag)
        {
        	this.recipeDuration = 0.0F;
            this.cookTime = 0;
            this.markDirty();
        }
    }
    
    public String getName()
    {
        return this.hasCustomName() ? this.customName : this.isObsidian ? "container.obsidian_altar" : "container.infernum_altar";
    }
    
    public boolean hasCustomName()
    {
        return this.customName != null && this.customName.length() > 0;
    }

    public void setCustomInventoryName(String name)
    {
        this.customName = name;
    }

    public void readFromNBT(NBTTagCompound compound)
    {
        super.readFromNBT(compound);
        NBTTagList nbttaglist = compound.getTagList("Items", 10);
        this.altarItemStacks = NonNullList.<ItemStack>func_191197_a(this.getSizeInventory(), ItemStack.field_190927_a);
        ItemStackHelper.func_191283_b(compound, this.altarItemStacks);
        this.cookTime = compound.getShort("CookTime");
        this.blood = compound.getInteger("Blood");

        if (compound.hasKey("CustomName", 8))
        {
            this.customName = compound.getString("CustomName");
        }
        
        if(compound.getBoolean("IsObsidian")) {
        	this.setObsidian();
        }
    }

    public NBTTagCompound writeToNBT(NBTTagCompound compound)
    {
        super.writeToNBT(compound);
        compound.setShort("CookTime", (short)this.cookTime);
        compound.setInteger("Blood", this.blood);
        ItemStackHelper.func_191282_a(compound, this.altarItemStacks);

        if (this.hasCustomName())
        {
            compound.setString("CustomName", this.customName);
        }
        
        if(this.isObsidian) {
        	compound.setBoolean("IsObsidian", true);
        }
        
        return compound;
    }

    public int getInventoryStackLimit()
    {
        return 1;
    }
    
    public void update()
    {
    	this.age++;

        if (!this.worldObj.isRemote)
        {
        	
        	if(this.isObsidian) {
        		
        		if(this.summonCountdown > -1) {
            		--this.summonCountdown;
            		
            		if(this.summonCountdown < 1) {
            			this.summonAvis();
            		}
        		}
        		else if(this.blood >= 1600) {
                	this.startSummon();
                }
        	}
        	
            if (!this.altarItemStacks.get(0).func_190926_b() && this.canAlter())
            {
                if(this.rand.nextFloat() < this.recipeDuration) {

	                if (this.canAlter())
	                {
	                    ++this.cookTime;
	
	                    if (this.cookTime == 200)
	                    {
	                        this.cookTime = 0;
	                        this.alterItem();
	                    }
	                }
	                else
	                {
	                    this.cookTime = 0;
	                }
                }
            }
            else if(this.cookTime > 0)
            	--this.cookTime;
            
            if(this.age % 20 == 0) {
            	boolean wasActive = this.isActive;
            	if(this.checkIsActive()) {
            			
            		if(!wasActive) {
                			
            			if(!this.isObsidian) {
                			IBlockState block = NetherBlocks.CHISELED_NETHERRACK.getStateFromMeta(1);
                    		
                    		this.worldObj.setBlockState(this.getPos().add(2, 1, 2), block);
                    		this.worldObj.setBlockState(this.getPos().add(-2, 1, 2), block);
                    		this.worldObj.setBlockState(this.getPos().add(-2, 1, -2), block);
                    		this.worldObj.setBlockState(this.getPos().add(2, 1, -2), block);
            			}
                		
                        this.updateBloodHolders();
            		}
            	}
            	else 
            	{
            		if(wasActive) {
            			this.deactivateBlocks();
            		}
            	}
            }
            
        }
        else {
        	
        	if(this.age % 20 == 0) {
        		this.isActive = this.checkIsActive();
        	}
        	
        	if(this.isActive) {
            	if(this.age % 30 == 0) {
            		this.worldObj.spawnParticle(EnumParticleTypes.FLAME, (double)pos.getX() + 0.5D, (double)pos.getY() + 0.5D, (double)pos.getZ() + 0.5D, 0.0D, 0.07D, 0.0D, new int[0]);
            	}
            	
            	if(this.age % 3 == 0) {
            		
                	int i = Block.getIdFromBlock(Blocks.FLOWING_LAVA);
                	
                    this.worldObj.spawnParticle(EnumParticleTypes.BLOCK_DUST, (double)pos.getX() + 2.0D + this.rand.nextDouble(), (double)pos.getY() + 0.0D, (double)pos.getZ() + 0.0D + this.rand.nextDouble(), 0.0D, 0.05D, 0.0D, new int[] {i});
                    this.worldObj.spawnParticle(EnumParticleTypes.BLOCK_DUST, (double)pos.getX() + 0.0D + this.rand.nextDouble(), (double)pos.getY() + 0.0D, (double)pos.getZ() + 2.0D + this.rand.nextDouble(), 0.0D, 0.05D, 0.0D, new int[] {i});
                    this.worldObj.spawnParticle(EnumParticleTypes.BLOCK_DUST, (double)pos.getX() - 2.0D + this.rand.nextDouble(), (double)pos.getY() + 0.0D, (double)pos.getZ() + 0.0D + this.rand.nextDouble(), 0.0D, 0.05D, 0.0D, new int[] {i});
                    this.worldObj.spawnParticle(EnumParticleTypes.BLOCK_DUST, (double)pos.getX() + 0.0D + this.rand.nextDouble(), (double)pos.getY() + 0.0D, (double)pos.getZ() - 2.0D + this.rand.nextDouble(), 0.0D, 0.05D, 0.0D, new int[] {i});
            	}
        	}
        }
    }
    
    private boolean checkIsActive() {
    	this.isActive = false;
    	
    	if(!(this.worldObj.getBlockState(this.getPos().add(2, -1, 0)).getBlock() instanceof BlockSoulSand)) return false;
    	if(!(this.worldObj.getBlockState(this.getPos().add(-2, -1, 0)).getBlock() instanceof BlockSoulSand)) return false;
    	if(!(this.worldObj.getBlockState(this.getPos().add(0, -1, -2)).getBlock() instanceof BlockSoulSand)) return false;
    	if(!(this.worldObj.getBlockState(this.getPos().add(0, -1, 2)).getBlock() instanceof BlockSoulSand)) return false;
    	
    	if(this.isObsidian) {
        	if(this.worldObj.getBlockState(this.getPos().add(2, 0, 2)).getBlock() != NetherBlocks.SMOOTH_OBSIDIAN) return false;
        	if(this.worldObj.getBlockState(this.getPos().add(2, 0, -2)).getBlock() != NetherBlocks.SMOOTH_OBSIDIAN) return false;
        	if(this.worldObj.getBlockState(this.getPos().add(-2, 0, -2)).getBlock() != NetherBlocks.SMOOTH_OBSIDIAN) return false;
        	if(this.worldObj.getBlockState(this.getPos().add(-2, 0, 2)).getBlock() != NetherBlocks.SMOOTH_OBSIDIAN) return false;
        	
        	if(this.worldObj.getBlockState(this.getPos().add(2, 1, 2)).getBlock() != NetherBlocks.SMOOTH_OBSIDIAN) return false;
        	if(this.worldObj.getBlockState(this.getPos().add(2, 1, -2)).getBlock() != NetherBlocks.SMOOTH_OBSIDIAN) return false;
        	if(this.worldObj.getBlockState(this.getPos().add(-2, 1, -2)).getBlock() != NetherBlocks.SMOOTH_OBSIDIAN) return false;
        	if(this.worldObj.getBlockState(this.getPos().add(-2, 1, 2)).getBlock() != NetherBlocks.SMOOTH_OBSIDIAN) return false;
    	}
    	else {
        	if(this.worldObj.getBlockState(this.getPos().add(2, 0, 2)).getBlock() != NetherBlocks.SMOOTH_NETHERRACK) return false;
        	if(this.worldObj.getBlockState(this.getPos().add(2, 0, -2)).getBlock() != NetherBlocks.SMOOTH_NETHERRACK) return false;
        	if(this.worldObj.getBlockState(this.getPos().add(-2, 0, -2)).getBlock() != NetherBlocks.SMOOTH_NETHERRACK) return false;
        	if(this.worldObj.getBlockState(this.getPos().add(-2, 0, 2)).getBlock() != NetherBlocks.SMOOTH_NETHERRACK) return false;
        	
        	if(!(this.worldObj.getBlockState(this.getPos().add(2, 1, 2)).getBlock() instanceof BlockChiseledNetherrack)) return false;
        	if(!(this.worldObj.getBlockState(this.getPos().add(2, 1, -2)).getBlock() instanceof BlockChiseledNetherrack)) return false;
        	if(!(this.worldObj.getBlockState(this.getPos().add(-2, 1, -2)).getBlock() instanceof BlockChiseledNetherrack)) return false;
        	if(!(this.worldObj.getBlockState(this.getPos().add(-2, 1, 2)).getBlock() instanceof BlockChiseledNetherrack)) return false;
    	}
    	
    	this.isActive = true;
    	return true;
    }
    
    private void deactivateBlocks() {
    	this.blood = 0;
		IBlockState block = Blocks.SOUL_SAND.getDefaultState();
		
		if(this.worldObj.getBlockState(this.getPos().add(2, -1, 0)).getBlock().equals(NetherBlocks.BLOOD_HOLDER)) {
			this.worldObj.setBlockState(this.getPos().add(2, -1, 0), block);
		}
			
		if(this.worldObj.getBlockState(this.getPos().add(-2, -1, 0)).getBlock().equals(NetherBlocks.BLOOD_HOLDER)) {
			this.worldObj.setBlockState(this.getPos().add(-2, -1, 0), block);
		}
			
		if(this.worldObj.getBlockState(this.getPos().add(0, -1, -2)).getBlock().equals(NetherBlocks.BLOOD_HOLDER)) {
			this.worldObj.setBlockState(this.getPos().add(0, -1, -2), block);
		}
			
		if(this.worldObj.getBlockState(this.getPos().add(0, -1, 2)).getBlock().equals(NetherBlocks.BLOOD_HOLDER)) {
			this.worldObj.setBlockState(this.getPos().add(0, -1, 2), block);
		}
		
		block = NetherBlocks.CHISELED_NETHERRACK.getDefaultState();
		
		if(this.worldObj.getBlockState(this.getPos().add(2, 1, 2)).getBlock().equals(NetherBlocks.CHISELED_NETHERRACK)) {
			this.worldObj.setBlockState(this.getPos().add(2, 1, 2), block);
		}
			
		if(this.worldObj.getBlockState(this.getPos().add(-2, 1, 2)).getBlock().equals(NetherBlocks.CHISELED_NETHERRACK)) {
			this.worldObj.setBlockState(this.getPos().add(-2, 1, 2), block);
		}
			
		if(this.worldObj.getBlockState(this.getPos().add(2, 1, -2)).getBlock().equals(NetherBlocks.CHISELED_NETHERRACK)) {
			this.worldObj.setBlockState(this.getPos().add(2, 1, -2), block);
		}
			
		if(this.worldObj.getBlockState(this.getPos().add(-2, 1, -2)).getBlock().equals(NetherBlocks.CHISELED_NETHERRACK)) {
			this.worldObj.setBlockState(this.getPos().add(-2, 1, -2), block);
		}
    }
    
    private void updateBloodHolders() {
    	int i = this.getBlood();
    	
    	if(i > 9) {
    		i = 9;
    	}
    	
		IBlockState block = NetherBlocks.BLOOD_HOLDER.getStateFromMeta(i);
		
		this.worldObj.setBlockState(this.getPos().add(2, -1, 0), block);
		this.worldObj.setBlockState(this.getPos().add(-2, -1, 0), block);
		this.worldObj.setBlockState(this.getPos().add(0, -1, -2), block);
		this.worldObj.setBlockState(this.getPos().add(0, -1, 2), block);
    }
    
    private void startSummon() {
    	this.summonCountdown = 800;
    	this.worldObj.playSound(null, this.getPos().getX(), this.getPos().getY(), this.getPos().getZ(), SoundEvents.ENTITY_GHAST_HURT, SoundCategory.BLOCKS, 1.0F, 0.6F);
        for (EntityPlayerMP entityplayermp : this.worldObj.getPlayers(EntityPlayerMP.class, new Predicate<EntityPlayerMP>()
    {
        public boolean apply(EntityPlayerMP player)
            {
                return TileEntityAltar.this.getDistanceSqToEntity(player) < 240.0D;
            }
        }))
        {
        	entityplayermp.addChatMessage(new TextComponentString(TextFormatting.DARK_PURPLE + "You! You will pay for waking me.."));
        }
    }
    
    private void summonAvis() {
    	
    	this.summonCountdown = -1;
    	this.blood = 0;
    	EntityInfernumAvis infernumAvis = new EntityInfernumAvis(this.worldObj);
    	this.worldObj.createExplosion(infernumAvis, this.getPos().getX(), this.getPos().getY(), this.getPos().getZ(), 3.0F, true);
    	infernumAvis.setLocationAndAngles(this.getPos().getX() - 0.5D, this.getPos().getY(), this.getPos().getZ() - 0.5D, 0, 0);
    	this.worldObj.spawnEntityInWorld(infernumAvis);
    	infernumAvis.setSleeping(false);
    	
    	this.worldObj.setBlockState(this.getPos(), Blocks.AIR.getDefaultState(), 3);
    }
    
    public void onBreak() {
    	this.deactivateBlocks();
    }

    public int getBlood()
    {
        return this.blood / this.bloodForBar;
    }

    private boolean canAlter()
    {
        if (this.altarItemStacks.get(0).func_190926_b()) return false;
        
        if(this.isObsidian && this.blood >= 1600) {
        	return false;
        }
        
        if(this.altarItemStacks.get(0).getItem().equals(NetherItems.GOLDEN_BUCKET) || ItemBloodBucket.getBlood(this.altarItemStacks.get(0)) > 0) {
        	this.recipeDuration = 1.0F;
        	return true;
        }
        
        if(this.isObsidian) {
        	return false;
        }
        
        AltarRecipe recipe = AltarRecipes.getRecipe(this.altarItemStacks);
        	
        if(recipe == null) return false;
        if(recipe.blood > this.blood) return false;
        	
        this.recipeDuration = recipe.duration;
        
        return true;
    }

	/**
     * Turn one item from the furnace source stack into the appropriate smelted item in the furnace result stack
     */
    public void alterItem()
    {
        if (this.canAlter())
        {
        	if(ItemBloodBucket.getBlood(this.altarItemStacks.get(0)) > 0) {
        		this.blood += ItemBloodBucket.getBlood(this.altarItemStacks.get(0));
        		ItemBloodBucket.setBlood(this.altarItemStacks.get(0), 0);
        		
        		if(this.altarItemStacks.get(0).getItem().equals(NetherItems.GOLDEN_BUCKET_BLOOD)) {
        			if(this.altarItemStacks.get(0).getMetadata() > 0) {
            			this.altarItemStacks.set(0, ItemStack.field_190927_a);
        			}else {
            			this.altarItemStacks.set(0, new ItemStack(NetherItems.GOLDEN_BUCKET));
        			}
        		}
        		
        		this.worldObj.playSound(null, this.getPos(), SoundEvents.BLOCK_BREWING_STAND_BREW, SoundCategory.BLOCKS, 1.4F, 0.7F);
                this.updateBloodHolders();
                
        		return;
        	}
        	
        	if(this.altarItemStacks.get(0).getItem().equals(NetherItems.GOLDEN_BUCKET)) {
        		ItemStack bloodBucket = new ItemStack(NetherItems.GOLDEN_BUCKET_BLOOD);
        		ItemBloodBucket.setBlood(bloodBucket, this.blood);
        		this.blood = 0;
        		this.altarItemStacks.set(0, bloodBucket);
        		this.worldObj.playSound(null, this.getPos(), SoundEvents.ITEM_BUCKET_FILL_LAVA, SoundCategory.BLOCKS, 1.4F, 0.7F);
                this.updateBloodHolders();
        		return;
        	}
        	
        	if(this.isObsidian) {
        		return;
        	}
        	
            AltarRecipe recipe = AltarRecipes.getRecipe(this.altarItemStacks);
    		
        	if(recipe.input1 != null)
        		this.altarItemStacks.set(1, ItemStack.field_190927_a);
            
        	if(recipe.input2 != null)
        		this.altarItemStacks.set(2, ItemStack.field_190927_a);
            
        	if(recipe.input3 != null)
        		this.altarItemStacks.set(3, ItemStack.field_190927_a);
            
        	if(recipe.input4 != null)
        		this.altarItemStacks.set(4, ItemStack.field_190927_a);
            
        	if(recipe.input5 != null)
        		this.altarItemStacks.set(5, ItemStack.field_190927_a);
            
        	if(recipe.input6 != null)
        		this.altarItemStacks.set(6, ItemStack.field_190927_a);
            
            ItemStack itemstack = recipe.output;

            if (itemstack != null)
            {
                this.altarItemStacks.set(0, itemstack.copy());
            }
            
            this.blood -= recipe.blood;
            
            this.worldObj.playSound(null, this.getPos(), SoundEvents.ENTITY_GENERIC_SPLASH, SoundCategory.BLOCKS, 1.4F, 0.7F);
            this.updateBloodHolders();
        }
    }

    public boolean isUseableByPlayer(EntityPlayer player)
    {
        return worldObj.getTileEntity(this.pos) != this ? false : player.getDistanceSq((double)this.pos.getX() + 0.5D, (double)this.pos.getY() + 0.5D, (double)this.pos.getZ() + 0.5D) <= 64.0D;
    }

    public void openInventory(EntityPlayer player) {}

    public void closeInventory(EntityPlayer player) {}

    public boolean isItemValidForSlot(int index, ItemStack stack)
    {
        return true;
    }

    public int[] getSlotsForFace(EnumFacing side)
    {
        return side == EnumFacing.DOWN ? slotsBottom : (side == EnumFacing.UP ? slotsTop : slotsSides);
    }

    public boolean canInsertItem(int index, ItemStack itemStackIn, EnumFacing direction)
    {
        return this.isItemValidForSlot(index, itemStackIn);
    }

    public boolean canExtractItem(int index, ItemStack stack, EnumFacing direction)
    {
        return true;
    }

    public String getGuiID()
    {
        return "adinferos:infernum_alter";
    }

    public Container createContainer(InventoryPlayer playerInventory, EntityPlayer playerIn)
    {
        return new ContainerAltar(playerInventory, this);
    }

    public int getField(int id)
    {
        switch (id)
        {
	    	case 0:
	        	return this.blood;
        	case 1:
            	return this.bloodForBar;
            case 2:
                return this.cookTime;
            default:
                return 0;
        }
    }

    public void setField(int id, int value)
    {
        switch (id)
        {
        	case 0: {
        		this.blood = value;
            	break;
        	}
        	
        	case 1: {
        		this.bloodForBar = value;
            	break;
        	}
        
            case 2: {
                this.cookTime = value;
                break;
            }
        }
    }

    public int getFieldCount()
    {
        return 2;
    }
    
    public void clear()
    {
        this.altarItemStacks.clear();
    }
    
    public double getDistanceSqToEntity(Entity entityIn)
    {
        double d0 = this.getPos().getX() - entityIn.posX;
        double d1 = this.getPos().getY() - entityIn.posY;
        double d2 = this.getPos().getZ() - entityIn.posZ;
        return d0 * d0 + d1 * d1 + d2 * d2;
    }

    @SideOnly(Side.CLIENT)
    public int getCookProgressScaled(int time)
    {
        return this.cookTime * time / 200;
    }
    
    public boolean isActive() {
    	return this.isActive;
    }

	public int getSizeInventory() {
		return this.altarItemStacks.size();
	}
}