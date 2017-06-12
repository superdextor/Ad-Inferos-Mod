package com.superdextor.adinferos.inventory;

import javax.annotation.Nullable;

import com.superdextor.adinferos.init.NetherBlocks;
import com.superdextor.adinferos.init.NetherItems;
import com.superdextor.adinferos.items.ItemBloodBucket;
import com.superdextor.adinferos.items.ItemSoul;

import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityList;
import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.IEntityLivingData;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.init.SoundEvents;
import net.minecraft.inventory.Container;
import net.minecraft.inventory.ISidedInventory;
import net.minecraft.inventory.ItemStackHelper;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import net.minecraft.network.Packet;
import net.minecraft.network.play.server.SPacketUpdateTileEntity;
import net.minecraft.tileentity.MobSpawnerBaseLogic;
import net.minecraft.tileentity.TileEntityMobSpawner;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumParticleTypes;
import net.minecraft.util.NonNullList;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.StringUtils;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.TextComponentString;
import net.minecraft.util.text.TextComponentTranslation;
import net.minecraft.world.IInteractionObject;
import net.minecraft.world.World;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class TileEntitySpawner extends TileEntityMobSpawner implements IInteractionObject, ISidedInventory
{
    private final MobSpawnerLogic spawnerLogic = new MobSpawnerLogic(this);
    
    private static final int[] slotsTop = new int[] {1};
    private static final int[] slotsBottom = new int[] {1, 1};
    private static final int[] slotsSides = new int[] {0};
    private int spawnerBlood;
    private NonNullList<ItemStack> spawnerItemStacks = NonNullList.<ItemStack>func_191197_a(5, ItemStack.field_190927_a);
    private String spawnerCustomName;

    public void readFromNBT(NBTTagCompound compound)
    {
        super.readFromNBT(compound);
        this.spawnerLogic.readFromNBT(compound);
        this.spawnerItemStacks = NonNullList.<ItemStack>func_191197_a(this.getSizeInventory(), ItemStack.field_190927_a);
        ItemStackHelper.func_191283_b(compound, this.spawnerItemStacks);
        
        this.spawnerBlood = compound.getInteger("Blood");
        
        if (compound.hasKey("CustomName", 8))
        {
            this.spawnerCustomName = compound.getString("CustomName");
        }
        
        this.updateUpgrades();
    }
    
    public void setWorldObj(World worldIn) {
    	super.setWorldObj(worldIn);
        this.updateUpgrades();
    }

    public NBTTagCompound writeToNBT(NBTTagCompound compound)
    {
        super.writeToNBT(compound);
        this.spawnerLogic.writeToNBT(compound);
        compound.setInteger("Blood", this.spawnerBlood);
        ItemStackHelper.func_191282_a(compound, this.spawnerItemStacks);
        
        if (this.hasCustomName())
        {
            compound.setString("CustomName", this.spawnerCustomName);
        }
        
        return compound;
    }

    /**
     * Like the old updateEntity(), except more generic.
     */
    public void update()
    {
        boolean shouldUpdate = false;
    	
    	if(!this.worldObj.isRemote) {
        	if(ItemBloodBucket.hasBlood(this.spawnerItemStacks.get(1))) {
        		shouldUpdate = true;
        		this.spawnerBlood += ItemBloodBucket.getBlood(this.spawnerItemStacks.get(1));
        		ItemBloodBucket.setBlood(this.spawnerItemStacks.get(1), 0);
        		
        		if(this.spawnerItemStacks.get(1).getItem().equals(NetherItems.GOLDEN_BUCKET_BLOOD)) {
        			if(this.spawnerItemStacks.get(1).getMetadata() > 0) {
            			this.spawnerItemStacks.set(1, ItemStack.field_190927_a);
        			}else {
            			this.spawnerItemStacks.set(1, new ItemStack(NetherItems.GOLDEN_BUCKET));
        			}
        		}
        		
        		this.worldObj.playSound(null, this.getPos(), SoundEvents.BLOCK_BREWING_STAND_BREW, SoundCategory.BLOCKS, 1.4F, 0.7F);
        	}
    	}
    	
        if (shouldUpdate)
        {
            this.markDirty();
        }
    	
        this.spawnerLogic.updateSpawner();
    }
    
    private void updateUpgrades() {
    	if(this.worldObj != null && !this.worldObj.isRemote) {
    		
    		this.spawnerLogic.updateEntityName();
    		
    		for(int i = 0; i < 3; i++) {
    			this.spawnerLogic.updateUpgrade(this.spawnerItemStacks.get(2 + i));
    		}
    	}
    	else if(this.worldObj != null) {
    		this.spawnerLogic.updateEntityName();
    	}
    }

    public Packet<?> getDescriptionPacket()
    {
        NBTTagCompound nbttagcompound = new NBTTagCompound();
        this.writeToNBT(nbttagcompound);
        nbttagcompound.removeTag("SpawnPotentials");
        return new SPacketUpdateTileEntity(this.pos, 1, nbttagcompound);
    }

    public boolean receiveClientEvent(int id, int type)
    {
        return this.spawnerLogic.setDelayToMin(id) ? true : false;
    }

    public boolean func_183000_F()
    {
        return true;
    }

    public MobSpawnerBaseLogic getSpawnerBaseLogic()
    {
        return this.spawnerLogic;
    }

	public String getName() {
        return this.hasCustomName() ? this.spawnerCustomName : "container.spawner";
	}

	public boolean hasCustomName() {
        return this.spawnerCustomName != null && !this.spawnerCustomName.isEmpty();
	}

	public ITextComponent getDisplayName() {
        return (ITextComponent)(this.hasCustomName() ? new TextComponentString(this.getName()) : new TextComponentTranslation(this.getName(), new Object[0]));
	}

	public int getSizeInventory() {
		return this.spawnerItemStacks.size();
	}
	
    public boolean func_191420_l()
    {
        for (ItemStack itemstack : this.spawnerItemStacks)
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
        return (ItemStack)this.spawnerItemStacks.get(index);
    }

    public ItemStack decrStackSize(int index, int count)
    {
        return ItemStackHelper.getAndSplit(this.spawnerItemStacks, index, count);
    }

    public ItemStack removeStackFromSlot(int index)
    {
        return ItemStackHelper.getAndRemove(this.spawnerItemStacks, index);
    }

    public void setInventorySlotContents(int index, ItemStack stack)
    {
        ItemStack itemstack = (ItemStack)this.spawnerItemStacks.get(index);
        boolean flag = !stack.func_190926_b() && stack.isItemEqual(itemstack) && ItemStack.areItemStackTagsEqual(stack, itemstack);
        this.spawnerItemStacks.set(index, stack);

        if (stack.func_190916_E() > this.getInventoryStackLimit())
        {
            stack.func_190920_e(this.getInventoryStackLimit());
        }

        if(index != 1) {
            this.updateUpgrades();
        }
    }

	public int getInventoryStackLimit() {
        return 1;
	}

	public boolean isUseableByPlayer(EntityPlayer player) {
        return this.worldObj.getTileEntity(this.pos) != this ? false : player.getDistanceSq((double)this.pos.getX() + 0.5D, (double)this.pos.getY() + 0.5D, (double)this.pos.getZ() + 0.5D) <= 64.0D;
	}

	public void openInventory(EntityPlayer player) {}

	public void closeInventory(EntityPlayer player) {}

	public boolean isItemValidForSlot(int index, ItemStack stack) {
		if(index == 0) {
    		return stack.getItem() instanceof ItemSoul;
		}
		else if(index == 1) {
    		return ItemBloodBucket.hasBlood(stack);
		}
		else {
			return stack == null || (stack.getMetadata() > 0 && stack.getItem() == NetherItems.SPAWNER_UPGRADE);
		}
	}

	public int getField(int id) {
		return this.spawnerBlood;
	}

	public void setField(int id, int value) {
		this.spawnerBlood = value;
	}

	public int getFieldCount() {
		return 1;
	}

	public void clear() {
		this.spawnerItemStacks.clear();
	}

	public int[] getSlotsForFace(EnumFacing side) {
        return side == EnumFacing.DOWN ? slotsBottom : (side == EnumFacing.UP ? slotsTop : slotsSides);
	}

	public boolean canInsertItem(int index, ItemStack itemStackIn, EnumFacing direction) {
        return this.isItemValidForSlot(index, itemStackIn);
	}

	public boolean canExtractItem(int index, ItemStack stack, EnumFacing direction) {
		if(index == 1 && ItemBloodBucket.hasBlood(stack)) {
			return false;
		}
		
        return true;
	}

	public Container createContainer(InventoryPlayer playerInventory, EntityPlayer playerIn) {
		return new ContainerSpawner(playerInventory, this);
	}

	public String getGuiID() {
		return "adinferos:spawner";
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
    
    public static class MobSpawnerLogic extends MobSpawnerBaseLogic {
    	
    	private final TileEntitySpawner tileEntitySpawner;
    	
        private double mobRotation = 0.0D;
        private double prevMobRotation = 0.0D;
        private int spawnDelay = 20;
        private int minSpawnDelay = 300;
        private int maxSpawnDelay = 900;
    	
        private final int spawnCountBase = 3;
        private final int spawnRangeBase = 4;
        private final int maxNearbyEntitiesBase = 5;
        
        protected int activatingRangeFromPlayer = 24;
        protected int spawnCount = this.spawnCountBase;
        protected int spawnRange = this.spawnRangeBase;
        protected int spawnDelayOffset = 0;
        protected int maxNearbyEntities = this.maxNearbyEntitiesBase;
        protected boolean ignoreSpawnRules = false;
        protected float bloodDecrease = 1.0F;
        
        protected Entity displayEntity = null;
        protected int bloodPerSpawn = 0;
        protected ResourceLocation entityName = null;
        
    	public MobSpawnerLogic(TileEntitySpawner spawnerIn) {
    		this.tileEntitySpawner = spawnerIn;
		}
    	
    	private void applyDefaults() {
    		this.spawnCount = this.spawnCountBase;
    		this.spawnRange = this.spawnRangeBase;
    		this.spawnDelayOffset = 0;
    		this.maxNearbyEntities = this.maxNearbyEntitiesBase;
    		this.ignoreSpawnRules = false;
    		this.bloodDecrease = 1.0F;
    	}
    	
        private void updateUpgrade(ItemStack stack) {
        	
        	if(stack == null || stack.getMetadata() == 0 || stack.getItem() != NetherItems.SPAWNER_UPGRADE) {
        		return;
        	}
        	
        	int i = stack.getMetadata();
        	
        	switch(i) {
        		default: {
        			break;
        		}
        		
        		case 1: {
        			this.spawnRange = this.spawnRangeBase + 2;
        			break;
        		}
        		
        		case 2: {
        			this.spawnRange = this.spawnRangeBase + 3;
        			break;
        		}
        		
        		case 3: {
        			this.spawnRange = this.spawnRangeBase + 5;
        			break;
        		}
        		
        		case 4: {
        			this.spawnDelayOffset = 40;
        			break;
        		}
        		
        		case 5: {
        			this.spawnDelayOffset = 80;
        			break;
        		}
        		
        		case 6: {
        			this.spawnDelayOffset = 120;
        			break;
        		}
        		
        		case 7: {
        			this.spawnDelayOffset = 160;
        			break;
        		}
        		
        		case 8: {
        			this.spawnDelayOffset = 200;
        			break;
        		}
        		
        		case 9: {
        			this.ignoreSpawnRules = true;
        			break;
        		}
        		
        		case 10: {
        			this.bloodDecrease = 0.85F;
        			break;
        		}
        		
        		case 11: {
        			this.bloodDecrease = 0.80F;
        			break;
        		}
        		
        		case 12: {
        			this.bloodDecrease = 0.70F;
        			break;
        		}
        		
        		case 13: {
        			this.bloodDecrease = 0.65F;
        			break;
        		}
        		
        		case 14: {
        			this.bloodDecrease = 0.40F;
        			break;
        		}
        		
        		case 15: {
        			this.spawnCount = this.spawnCountBase + 1;
        			break;
        		}
        		
        		case 16: {
        			this.spawnCount = this.spawnCountBase + 2;
        			break;
        		}
        		
        		case 17: {
        			this.spawnCount = this.spawnCountBase + 3;
        			break;
        		}
        		
        		case 18: {
        			this.maxNearbyEntities = this.maxNearbyEntitiesBase + 2;
        			break;
        		}
        		
        		case 19: {
        			this.maxNearbyEntities = this.maxNearbyEntitiesBase + 3;
        			break;
        		}
        		
        		case 20: {
        			this.maxNearbyEntities = this.maxNearbyEntitiesBase + 4;
        			break;
        		}
        	}
        }
        
        private boolean isActivated()
        {
            BlockPos blockpos = this.getSpawnerPosition();
            return this.getSpawnerWorld().isAnyPlayerWithinRangeAt((double)blockpos.getX() + 0.5D, (double)blockpos.getY() + 0.5D, (double)blockpos.getZ() + 0.5D, (double)this.activatingRangeFromPlayer);
        }
        
        public void updateSpawner()
        {
            if (!this.isActivated())
            {
                this.prevMobRotation = this.mobRotation;
            }
            else
            {
                BlockPos blockpos = this.getSpawnerPosition();

                if (this.getSpawnerWorld().isRemote)
                {
                    double d3 = (double)((float)blockpos.getX() + this.getSpawnerWorld().rand.nextFloat());
                    double d4 = (double)((float)blockpos.getY() + this.getSpawnerWorld().rand.nextFloat());
                    double d5 = (double)((float)blockpos.getZ() + this.getSpawnerWorld().rand.nextFloat());
                    this.getSpawnerWorld().spawnParticle(EnumParticleTypes.SMOKE_NORMAL, d3, d4, d5, 0.0D, 0.0D, 0.0D, new int[0]);
                    this.getSpawnerWorld().spawnParticle(EnumParticleTypes.FLAME, d3, d4, d5, 0.0D, 0.0D, 0.0D, new int[0]);

                    if (this.spawnDelay > 0)
                    {
                        --this.spawnDelay;
                    }

                    this.prevMobRotation = this.mobRotation;
                    this.mobRotation = (this.mobRotation + (double)(1000.0F / ((float)this.spawnDelay + 200.0F))) % 360.0D;
                }
                else
                {
                    if (this.spawnDelay == -1)
                    {
                        this.resetTimer();
                    }

                    if (this.spawnDelay > 0)
                    {
                        --this.spawnDelay;
                        return;
                    }
                    
                    if(this.entityName == null) {
                    	return;
                    }
                    
                    int b = (int) (this.bloodPerSpawn * this.bloodDecrease);

                    boolean flag = false;

                    for (int i = 0; i < this.spawnCount && b <= this.tileEntitySpawner.spawnerBlood; ++i)
                    {
                    	World world = this.getSpawnerWorld();
                    	Entity entity = EntityList.createEntityByIDFromName(this.entityName, world);

                        if (entity == null)
                        {
                            return;
                        }

                        int k = world.getEntitiesWithinAABB(entity.getClass(), (new AxisAlignedBB((double)blockpos.getX(), (double)blockpos.getY(), (double)blockpos.getZ(), (double)(blockpos.getX() + 1), (double)(blockpos.getY() + 1), (double)(blockpos.getZ() + 1))).expandXyz((double)this.spawnRange)).size();

                        if (k >= this.maxNearbyEntities)
                        {
                            this.resetTimer();
                            return;
                        }

                        EntityLiving entityliving = entity instanceof EntityLiving ? (EntityLiving)entity : null;
                        double d0 = (double)blockpos.getX() + (world.rand.nextDouble() - world.rand.nextDouble()) * (double)this.spawnRange + 0.5D;
                        double d1 = (double)(blockpos.getY() + world.rand.nextInt(3) - 1);
                        double d2 = (double)blockpos.getZ() + (world.rand.nextDouble() - world.rand.nextDouble()) * (double)this.spawnRange + 0.5D;
                        entity.setLocationAndAngles(d0, d1, d2, world.rand.nextFloat() * 360.0F, 0.0F);
                        entity.addTag("AdSpawnerEntity");
                        
                        if (entityliving == null || (entityliving.getCanSpawnHere() || this.ignoreSpawnRules) && entityliving.isNotColliding())
                        {
                            if (entityliving != null)
                            {
                            	entityliving.onInitialSpawn(world.getDifficultyForLocation(new BlockPos(entity)), (IEntityLivingData)null);
                            }

                            this.tileEntitySpawner.spawnerBlood -= b;
                            world.spawnEntityInWorld(entity);
                            world.playEvent(2004, blockpos, 0);

                            if (entityliving != null)
                            {
                                entityliving.spawnExplosionParticle();
                            }

                            flag = true;
                        }
                    }

                    if (flag)
                    {
                        this.resetTimer();
                    }
                }
            }
        }
        
        private void resetTimer()
        {
            if (this.maxSpawnDelay <= this.minSpawnDelay)
            {
                this.spawnDelay = this.minSpawnDelay;
            }
            else
            {
                int i = this.maxSpawnDelay - this.minSpawnDelay;
                this.spawnDelay = this.minSpawnDelay + this.getSpawnerWorld().rand.nextInt(i) - this.spawnDelayOffset;
            }

            this.broadcastEvent(1);
        }
        
        public void readFromNBT(NBTTagCompound nbt)
        {
            this.spawnDelay = nbt.getShort("Delay");
            this.updateEntityName();

            if (nbt.hasKey("MinSpawnDelay", 99))
            {
                this.minSpawnDelay = nbt.getShort("MinSpawnDelay");
                this.maxSpawnDelay = nbt.getShort("MaxSpawnDelay");
                this.spawnCount = nbt.getShort("SpawnCount");
            }

            if (nbt.hasKey("MaxNearbyEntities", 99))
            {
                this.maxNearbyEntities = nbt.getShort("MaxNearbyEntities");
                this.activatingRangeFromPlayer = nbt.getShort("RequiredPlayerRange");
            }

            if (nbt.hasKey("SpawnRange", 99))
            {
                this.spawnRange = nbt.getShort("SpawnRange");
            }
        }
        
        protected void updateEntityName() {
        	if(ItemSoul.hasSoul(this.tileEntitySpawner.spawnerItemStacks.get(0))) {
        		ResourceLocation s = ItemSoul.getSoulID(this.tileEntitySpawner.spawnerItemStacks.get(0));
        		this.entityName = s;
        		ExtractorRecipes.ExtractorRecipe recipe = ExtractorRecipes.getRecipe(s.toString()); 
        		if(recipe == null) {
        			this.bloodPerSpawn = 50;
        		}
        		else {
            		this.bloodPerSpawn = recipe.bloodForSpawn;
        		}
        	}
        	else {
        		this.bloodPerSpawn = 0;
        		this.entityName = null;
        	}
        }

        public NBTTagCompound writeToNBT(NBTTagCompound nbt)
        {
        	ResourceLocation s = this.entityName;

            if (s != null)
            {
                nbt.setShort("Delay", (short)this.spawnDelay);
                nbt.setShort("MinSpawnDelay", (short)this.minSpawnDelay);
                nbt.setShort("MaxSpawnDelay", (short)this.maxSpawnDelay);
                nbt.setShort("SpawnCount", (short)this.spawnCount);
                nbt.setShort("MaxNearbyEntities", (short)this.maxNearbyEntities);
                nbt.setShort("RequiredPlayerRange", (short)this.activatingRangeFromPlayer);
                nbt.setShort("SpawnRange", (short)this.spawnRange);
            }
            
            return nbt;
        }
        
        public boolean setDelayToMin(int delay)
        {
            if (delay == 1 && this.getSpawnerWorld().isRemote)
            {
                this.spawnDelay = this.minSpawnDelay;
                return true;
            }
            else
            {
                return false;
            }
        }
    	
        public void broadcastEvent(int id)
        {
        	this.tileEntitySpawner.worldObj.addBlockEvent(this.tileEntitySpawner.pos, NetherBlocks.SPAWNER, id, 0);
        }
        
        public World getSpawnerWorld()
        {
            return this.tileEntitySpawner.worldObj;
        }
        
        public BlockPos getSpawnerPosition()
        {
            return this.tileEntitySpawner.pos;
        }
        
        @SideOnly(Side.CLIENT)
        public Entity getCachedEntity()
        {
        	if(this.entityName == null) {
        		if(this.displayEntity != null) {
        			this.displayEntity = null;
        		}
        	}
        	else {
        		boolean flag = false;
        		if(this.displayEntity == null) {
        			flag = true;
        		}
        		else if(EntityList.func_191301_a(this.displayEntity) != this.entityName) {
        			flag = true;
        		}
        		
        		if(flag) {
        			this.displayEntity = EntityList.createEntityByIDFromName(this.entityName, this.getSpawnerWorld());
        			if(this.displayEntity instanceof EntityLiving) {
        				((EntityLiving) this.displayEntity).onInitialSpawn(this.getSpawnerWorld().getDifficultyForLocation(this.getSpawnerPosition()), null);
        			}
        		}
        	}
        	
        	return this.displayEntity;
        }
        
        @SideOnly(Side.CLIENT)
        public double getMobRotation()
        {
            return this.mobRotation;
        }

        @SideOnly(Side.CLIENT)
        public double getPrevMobRotation()
        {
            return this.prevMobRotation;
        }
    }
}