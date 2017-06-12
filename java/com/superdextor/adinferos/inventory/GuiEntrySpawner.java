package com.superdextor.adinferos.inventory;

import com.superdextor.thinkbigcore.utility.GuiEntry;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.Container;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;


public class GuiEntrySpawner extends GuiEntry {

	public GuiEntrySpawner() {
		super(103);
	}
	
	@SideOnly(Side.CLIENT)
	public Object getGui(EntityPlayer player, World world, int x, int y, int z) {
        return new GuiSpawner(player.inventory, (TileEntitySpawner) world.getTileEntity(new BlockPos(x, y, z)));
	}

	public Container getContainer(EntityPlayer player, World world, int x, int y, int z) {
        return new ContainerSpawner(player.inventory, (TileEntitySpawner) world.getTileEntity(new BlockPos(x, y, z)));
	}

}
