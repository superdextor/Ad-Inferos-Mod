package com.superdextor.adinferos.inventory;

import com.superdextor.adinferos.AdInferosAchievements;
import com.superdextor.adinferos.init.NetherItems;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.inventory.ClickType;
import net.minecraft.inventory.Container;
import net.minecraft.inventory.IContainerListener;
import net.minecraft.inventory.IInventory;
import net.minecraft.inventory.Slot;
import net.minecraft.item.ItemStack;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class ContainerAltar extends Container
{
    private final IInventory tileAltar;
    private int field_178152_f;
    private int field_178153_g;
    private int field_178154_h;
    private int field_178155_i;

    public ContainerAltar(InventoryPlayer inventory, IInventory altarInventory)
    {
        this.tileAltar = altarInventory;
        this.addSlotToContainer(new Slot(altarInventory, 0, 80, 38));
        this.addSlotToContainer(new Slot(altarInventory, 1, 47, 19));
        this.addSlotToContainer(new Slot(altarInventory, 2, 28, 39));
        this.addSlotToContainer(new Slot(altarInventory, 3, 47, 59));
        this.addSlotToContainer(new Slot(altarInventory, 4, 113, 19));
        this.addSlotToContainer(new Slot(altarInventory, 5, 132, 39));
        this.addSlotToContainer(new Slot(altarInventory, 6, 113, 59));
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
        listener.sendAllWindowProperties(this, this.tileAltar);
    }

    /**
     * Looks for changes made in the container, sends them to every listener.
     */
    public void detectAndSendChanges()
    {
        super.detectAndSendChanges();

        for (int i = 0; i < this.listeners.size(); ++i)
        {
        	IContainerListener icontainerlistener = (IContainerListener)this.listeners.get(i);

            if (this.field_178152_f != this.tileAltar.getField(2))
            {
            	icontainerlistener.sendProgressBarUpdate(this, 2, this.tileAltar.getField(2));
            }

            if (this.field_178154_h != this.tileAltar.getField(0))
            {
            	icontainerlistener.sendProgressBarUpdate(this, 0, this.tileAltar.getField(0));
            }

            if (this.field_178155_i != this.tileAltar.getField(1))
            {
            	icontainerlistener.sendProgressBarUpdate(this, 1, this.tileAltar.getField(1));
            }

            if (this.field_178153_g != this.tileAltar.getField(3))
            {
            	icontainerlistener.sendProgressBarUpdate(this, 3, this.tileAltar.getField(3));
            }
        }

        this.field_178152_f = this.tileAltar.getField(2);
        this.field_178154_h = this.tileAltar.getField(0);
        this.field_178155_i = this.tileAltar.getField(1);
        this.field_178153_g = this.tileAltar.getField(3);
    }

    @SideOnly(Side.CLIENT)
    public void updateProgressBar(int id, int data)
    {
        this.tileAltar.setField(id, data);
    }

    public boolean canInteractWith(EntityPlayer playerIn)
    {
        return this.tileAltar.isUseableByPlayer(playerIn);
    }

    /**
     * Take a stack from the specified inventory slot.
     */
    public ItemStack transferStackInSlot(EntityPlayer playerIn, int index) //TODO make sure this works correctly
    {
        ItemStack itemstack = ItemStack.field_190927_a;
        Slot slot = (Slot)this.inventorySlots.get(index);

        if (slot != null && slot.getHasStack())
        {
            ItemStack itemstack1 = slot.getStack();
            itemstack = itemstack1.copy();

            if (index < 7)
            {
                if (!this.mergeItemStack(itemstack1, 7, this.inventorySlots.size(), true))
                {
                    return ItemStack.field_190927_a;
                }
            }
            else if (!this.mergeItemStack(itemstack1, 0, 7, false))
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
        }

        return itemstack;
    }
    
    public ItemStack slotClick(int slotId, int dragType, ClickType clickTypeIn, EntityPlayer player) {
    	
    	if(slotId > 0) {
        	ItemStack stack = this.getSlot(slotId).getStack();
        	
        	if(stack != ItemStack.field_190927_a) {
                if(stack.getItem().equals(NetherItems.INFERNO_STAFF)) {
                    player.addStat(AdInferosAchievements.flameStaff);
                }
                
                if(stack.getItem().equals(NetherItems.WITHER_STAFF)) {
                    player.addStat(AdInferosAchievements.witherStaff);
                }
                
                if(stack.getItem().equals(NetherItems.WITHER_APPLE)) {
                    player.addStat(AdInferosAchievements.witherApple);
                }
                
                if(stack.getItem().equals(NetherItems.POCKET_WORMHOLE)) {
                    player.addStat(AdInferosAchievements.pocketPortal);
                }
        	}
    	}
    	
    	ItemStack itemstack = super.slotClick(slotId, dragType, clickTypeIn, player);
    	
        return itemstack;
    }
}