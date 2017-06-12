package com.superdextor.adinferos.inventory;

import com.superdextor.thinkbigcore.utility.GuiEntry;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.Container;
import net.minecraft.inventory.ContainerFurnace;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;


public class GuiEntryNetherrackFurnace extends GuiEntry {

	public GuiEntryNetherrackFurnace() {
		super(101);
	}
	
	@SideOnly(Side.CLIENT)
	public Object getGui(EntityPlayer player, World world, int x, int y, int z) {
        return new GuiNetherFurnace(player.inventory, (TileEntityNetherFurnace) world.getTileEntity(new BlockPos(x, y, z)));
	}

	public Container getContainer(EntityPlayer player, World world, int x, int y, int z) {
        return new ContainerFurnace(player.inventory, (TileEntityNetherFurnace) world.getTileEntity(new BlockPos(x, y, z)));
	}

}
