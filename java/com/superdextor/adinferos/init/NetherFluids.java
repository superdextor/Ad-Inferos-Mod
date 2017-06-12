package com.superdextor.adinferos.init;

import java.util.HashSet;
import java.util.Set;

import net.minecraft.block.Block;
import net.minecraft.block.material.MapColor;
import net.minecraft.block.material.Material;
import net.minecraft.block.material.MaterialLiquid;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fluids.Fluid;
import net.minecraftforge.fluids.FluidRegistry;
import net.minecraftforge.fluids.IFluidBlock;
import net.minecraftforge.fml.common.registry.GameRegistry;

import com.superdextor.adinferos.AdInferosReference;
import com.superdextor.adinferos.blocks.BlockAcid;
import com.superdextor.adinferos.config.NetherConfig;
import com.superdextor.thinkbigcore.helpers.RegistryHelper;

public class NetherFluids {

	public static Fluid fluidAcid;
	public static BlockAcid blockAcid;

	public static Set<IFluidBlock> fluidBlocks = new HashSet<IFluidBlock>();

	public static void registerFluids() {
		fluidAcid = createFluid("acid", "blocks/acid", true);
		blockAcid = registerFluidBlock(new BlockAcid(fluidAcid, Material.WATER));
		NetherConfig.printDebugInfo("Registered Fluids");
	}

	private static Fluid createFluid(String name, String textureName, boolean hasFlowIcon) {
		ResourceLocation still = new ResourceLocation("adinferos", textureName + "_still");
		ResourceLocation flowing = hasFlowIcon ? new ResourceLocation("adinferos", textureName + "_flow") : still;

		Fluid fluid = new Fluid(name, still, flowing);
		if (!FluidRegistry.registerFluid(fluid)) {
			throw new IllegalStateException(String.format("Unable to register fluid %s", fluid));
		}

		return fluid;
	}

	private static <T extends Block & IFluidBlock> T registerFluidBlock(T block) {
		String fluidName = block.getFluid().getUnlocalizedName().substring(6);
		block.setUnlocalizedName(fluidName);
		RegistryHelper.registerBlock(block, null, AdInferosReference.MOD_ID, false);

		fluidBlocks.add(block);

		return block;
	}
}
