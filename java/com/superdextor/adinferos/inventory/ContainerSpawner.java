package com.superdextor.adinferos.inventory;

import com.superdextor.adinferos.init.NetherItems;
import com.superdextor.adinferos.items.ItemBloodBucket;
import com.superdextor.adinferos.items.ItemSoul;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.inventory.Container;
import net.minecraft.inventory.IContainerListener;
import net.minecraft.inventory.IInventory;
import net.minecraft.inventory.Slot;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.FurnaceRecipes;
import net.minecraft.tileentity.TileEntityFurnace;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class ContainerSpawner extends Container
{
    private final IInventory spawnerInventory;
    private int field_178154_h;

    public ContainerSpawner(InventoryPlayer inventory, IInventory spawnerInventoryIn)
    {
        this.spawnerInventory = spawnerInventoryIn;
        this.addSlotToContainer(new Slot(spawnerInventoryIn, 0, 80, 20) {
        	
        	public boolean isItemValid(ItemStack stack) {
        		return stack == ItemStack.field_190927_a || stack.getItem() instanceof ItemSoul;
        	}
        }); //SOUL
        this.addSlotToContainer(new Slot(spawnerInventoryIn, 1, 8, 12) {
        	public boolean isItemValid(ItemStack stack) {
        		return ItemBloodBucket.hasBlood(stack);
        	}
        }); //BLOOD
        this.addSlotToContainer(new Slot(spawnerInventoryIn, 2, 62, 55) {
        	
        	public boolean isItemValid(ItemStack stack) {
    			return stack == ItemStack.field_190927_a || (stack.getMetadata() > 0 && stack.getItem() == NetherItems.SPAWNER_UPGRADE);
        	}
        });
        this.addSlotToContainer(new Slot(spawnerInventoryIn, 3, 80, 55) {
        	
        	public boolean isItemValid(ItemStack stack) {
    			return stack == ItemStack.field_190927_a || (stack.getMetadata() > 0 && stack.getItem() == NetherItems.SPAWNER_UPGRADE);
        	}
        });
        this.addSlotToContainer(new Slot(spawnerInventoryIn, 4, 98, 55) {
        	
        	public boolean isItemValid(ItemStack stack) {
    			return stack == ItemStack.field_190927_a || (stack.getMetadata() > 0 && stack.getItem() == NetherItems.SPAWNER_UPGRADE);
        	}
        });
        
        
        int i;

        for (i = 0; i < 3; ++i)
        {
            for (int j = 0; j < 9; ++j)
            {
                this.addSlotToContainer(new Slot(inventory, j + i * 9 + 9, 8 + j * 18, 84 + i * 18));
            }
        }

        for (i = 0; i < 9; ++i)
        {
            this.addSlotToContainer(new Slot(inventory, i, 8 + i * 18, 142));
        }
    }

    public void addListener(IContainerListener listener)
    {
        super.addListener(listener);
        listener.sendAllWindowProperties(this, this.spawnerInventory);
    }

    public void detectAndSendChanges()
    {
        super.detectAndSendChanges();

        for (int i = 0; i < this.listeners.size(); ++i)
        {
            IContainerListener icontainerlistener = (IContainerListener)this.listeners.get(i);

            if (this.field_178154_h != this.spawnerInventory.getField(0))
            {
            	icontainerlistener.sendProgressBarUpdate(this, 0, this.spawnerInventory.getField(0));
            }
        }

        this.field_178154_h = this.spawnerInventory.getField(0);
    }

    @SideOnly(Side.CLIENT)
    public void updateProgressBar(int id, int data)
    {
        this.spawnerInventory.setField(id, data);
    }

    public boolean canInteractWith(EntityPlayer playerIn)
    {
        return this.spawnerInventory.isUseableByPlayer(playerIn);
    }

    /**
     * Take a stack from the specified inventory slot.
     */
    public ItemStack transferStackInSlot(EntityPlayer playerIn, int index)
    {
        ItemStack itemstack = ItemStack.field_190927_a;
        Slot slot = (Slot)this.inventorySlots.get(index);

        if (slot != null && slot.getHasStack())
        {
            ItemStack itemstack1 = slot.getStack();
            itemstack = itemstack1.copy();

            if (index != 0 && index != 1 && index != 2 && index != 3 && index != 4)
            {
                if (itemstack1.getItem() instanceof ItemSoul)
                {
                    if (!this.mergeItemStack(itemstack1, 0, 1, false))
                    {
                        return ItemStack.field_190927_a;
                    }
                }
                else if (ItemBloodBucket.hasBlood(itemstack1))
                {
                    if (!this.mergeItemStack(itemstack1, 1, 2, false))
                    {
                        return ItemStack.field_190927_a;
                    }
                }
                else if (itemstack1.getMetadata() > 0 && itemstack1.getItem() == NetherItems.SPAWNER_UPGRADE)
                {
                    if (!this.mergeItemStack(itemstack1, 2, 4, false))
                    {
                        return ItemStack.field_190927_a;
                    }
                }
                else if (index >= 3 && index < 30)
                {
                    if (!this.mergeItemStack(itemstack1, 30, 39, false))
                    {
                        return ItemStack.field_190927_a;
                    }
                }
                else if (index >= 30 && index < 39 && !this.mergeItemStack(itemstack1, 3, 30, false))
                {
                    return ItemStack.field_190927_a;
                }
            }
            else if (!this.mergeItemStack(itemstack1, 3, 39, false))
            {
                return ItemStack.field_190927_a;
            }

            if (itemstack1.func_190926_b())
            {
                slot.putStack(ItemStack.field_190927_a);
            }
            else
            {
                slot.onSlotChanged();
            }

            if (itemstack1.func_190916_E() == itemstack.func_190916_E())
            {
                return ItemStack.field_190927_a;
            }

            slot.func_190901_a(playerIn, itemstack1);
        }

        return itemstack;
    }
}