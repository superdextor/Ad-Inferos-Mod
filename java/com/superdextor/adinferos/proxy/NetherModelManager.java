package com.superdextor.adinferos.proxy;

import net.minecraft.block.Block;
import net.minecraft.block.state.IBlockState;
import net.minecraft.client.renderer.block.model.ModelBakery;
import net.minecraft.client.renderer.block.model.ModelResourceLocation;
import net.minecraft.client.renderer.block.statemap.StateMapperBase;
import net.minecraft.item.Item;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.client.model.ModelLoader;
import net.minecraftforge.fluids.IFluidBlock;

import com.superdextor.adinferos.AdInferosReference;
import com.superdextor.adinferos.init.NetherFluids;


public class NetherModelManager {
	public static final NetherModelManager INSTANCE = new NetherModelManager();

	private static final String FLUID_MODEL_PATH = AdInferosReference.MOD_ID + ":" + "acid";

	private NetherModelManager() {
	}

	public void registerAllModels() {
		registerFluidModels();
	}

	public void registerFluidModels() {
		for (IFluidBlock fluidBlock : NetherFluids.fluidBlocks) {
			registerFluidModel(fluidBlock);
		}
	}

	private void registerFluidModel(IFluidBlock fluidBlock) {
		Item item = Item.getItemFromBlock((Block) fluidBlock);

		final ModelResourceLocation modelResourceLocation = new ModelResourceLocation(FLUID_MODEL_PATH, fluidBlock.getFluid().getName());

		ModelLoader.setCustomModelResourceLocation(item, 0, modelResourceLocation);

		ModelLoader.setCustomStateMapper((Block) fluidBlock, new StateMapperBase() {
			@Override
			protected ModelResourceLocation getModelResourceLocation(IBlockState p_178132_1_) {
				return modelResourceLocation;
			}
		});
	}
}
