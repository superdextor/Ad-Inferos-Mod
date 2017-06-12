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

public class ContainerExtractor extends Container
{
    private final IInventory extractorInventory;
    private int field_178152_f;
    private int field_178153_g;
    private int field_178154_h;
    private int field_178155_i;

    public ContainerExtractor(InventoryPlayer inventory, IInventory extractorInventoryIn)
    {
        this.extractorInventory = extractorInventoryIn;
        this.addSlotToContainer(new Slot(extractorInventoryIn, 0, 125, 16) {
        	
        	public boolean isItemValid(ItemStack stack) {
        		return ItemSoul.hasSoul(stack);
        	}
        	
        	public int getItemStackLimit(ItemStack stack) {
        		return 1;
        	}
        }); //SWORD
        this.addSlotToContainer(new Slot(extractorInventoryIn, 1, 80, 61) {
        	public boolean isItemValid(ItemStack stack) {
        		return stack.getItem() == NetherItems.SOUL_FRAGMENT;
        	}
        }); //SOUL
        this.addSlotToContainer(new Slot(extractorInventoryIn, 2, 80, 20) {
        	public boolean isItemValid(ItemStack stack) {
        		return false;
        	}
        }); //OUTPUT
        this.addSlotToContainer(new Slot(extractorInventoryIn, 3, 8, 12) {
        	public boolean isItemValid(ItemStack stack) {
        		return ItemBloodBucket.hasBlood(stack);
        	}
        }); //BLOOD
        
        
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
        listener.sendAllWindowProperties(this, this.extractorInventory);
    }

    public void detectAndSendChanges()
    {
        super.detectAndSendChanges();

        for (int i = 0; i < this.listeners.size(); ++i)
        {
            IContainerListener icontainerlistener = (IContainerListener)this.listeners.get(i);

            if (this.field_178152_f != this.extractorInventory.getField(2))
            {
            	icontainerlistener.sendProgressBarUpdate(this, 2, this.extractorInventory.getField(2));
            }

            if (this.field_178154_h != this.extractorInventory.getField(0))
            {
            	icontainerlistener.sendProgressBarUpdate(this, 0, this.extractorInventory.getField(0));
            }

            if (this.field_178155_i != this.extractorInventory.getField(1))
            {
            	icontainerlistener.sendProgressBarUpdate(this, 1, this.extractorInventory.getField(1));
            }

            if (this.field_178153_g != this.extractorInventory.getField(3))
            {
            	icontainerlistener.sendProgressBarUpdate(this, 3, this.extractorInventory.getField(3));
            }
        }

        this.field_178152_f = this.extractorInventory.getField(2);
        this.field_178154_h = this.extractorInventory.getField(0);
        this.field_178155_i = this.extractorInventory.getField(1);
        this.field_178153_g = this.extractorInventory.getField(3);
    }

    @SideOnly(Side.CLIENT)
    public void updateProgressBar(int id, int data)
    {
        this.extractorInventory.setField(id, data);
    }

    public boolean canInteractWith(EntityPlayer playerIn)
    {
        return this.extractorInventory.isUseableByPlayer(playerIn);
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

            if (index == 2)
            {
                if (!this.mergeItemStack(itemstack1, 3, 39, true))
                {
                    return ItemStack.field_190927_a;
                }

                slot.onSlotChange(itemstack1, itemstack);
            }
            else if (index != 0 && index != 1 && index != 3)
            {
                if (ItemSoul.hasSoul(itemstack1))
                {
                    if (!this.mergeItemStack(itemstack1, 0, 1, false))
                    {
                        return ItemStack.field_190927_a;
                    }
                }
                else if (itemstack1.getItem() == NetherItems.SOUL_FRAGMENT)
                {
                    if (!this.mergeItemStack(itemstack1, 1, 2, false))
                    {
                        return ItemStack.field_190927_a;
                    }
                }
                else if (ItemBloodBucket.hasBlood(itemstack1))
                {
                    if (!this.mergeItemStack(itemstack1, 3, 4, false))
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
            else if (!this.mergeItemStack(itemstack1, 4, 39, false))
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