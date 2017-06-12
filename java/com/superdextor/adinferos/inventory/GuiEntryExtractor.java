package com.superdextor.adinferos.inventory;

import com.superdextor.thinkbigcore.utility.GuiEntry;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.Container;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;


public class GuiEntryExtractor extends GuiEntry {

	public GuiEntryExtractor() {
		super(102);
	}
	
	@SideOnly(Side.CLIENT)
	public Object getGui(EntityPlayer player, World world, int x, int y, int z) {
        return new GuiExtractor(player.inventory, (TileEntityExtractor) world.getTileEntity(new BlockPos(x, y, z)));
	}

	public Container getContainer(EntityPlayer player, World world, int x, int y, int z) {
        return new ContainerExtractor(player.inventory, (TileEntityExtractor) world.getTileEntity(new BlockPos(x, y, z)));
	}

}
