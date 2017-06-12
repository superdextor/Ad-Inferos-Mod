package com.superdextor.adinferos.inventory;

import com.superdextor.thinkbigcore.utility.GuiEntry;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.Container;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;


public class GuiEntryAltar extends GuiEntry {

	public GuiEntryAltar() {
		super(100);
	}
	
	@SideOnly(Side.CLIENT)
	public Object getGui(EntityPlayer player, World world, int x, int y, int z) {
        return new GuiAltar(player.inventory, (TileEntityAltar) world.getTileEntity(new BlockPos(x, y, z)));
	}

	public Container getContainer(EntityPlayer player, World world, int x, int y, int z) {
        return new ContainerAltar(player.inventory, (TileEntityAltar) world.getTileEntity(new BlockPos(x, y, z)));
	}

}
