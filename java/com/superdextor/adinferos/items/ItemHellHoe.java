package com.superdextor.adinferos.items;

import com.superdextor.adinferos.init.NetherBlocks;
import com.superdextor.adinferos.init.NetherItems;
import com.superdextor.thinkbigcore.config.TBCConfig;

import net.minecraft.block.Block;
import net.minecraft.block.BlockDirt;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.init.SoundEvents;
import net.minecraft.item.ItemHoe;
import net.minecraft.item.ItemStack;
import net.minecraft.util.EnumActionResult;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class ItemHellHoe extends ItemHoe {

	@SideOnly(Side.CLIENT)
	private boolean shownTip = false;
	
	public ItemHellHoe(ToolMaterial material) {
		super(material);
	}
	
    @SideOnly(Side.CLIENT)
    public void onCreated(ItemStack stack, World worldIn, EntityPlayer playerIn) {
    	if(!shownTip)
    	{
    		TBCConfig.displayTip("Can till Netherrack");
    		shownTip = true;
    	}
    }
	
    @SuppressWarnings("incomplete-switch")
    public EnumActionResult onItemUse(EntityPlayer playerIn, World worldIn, BlockPos pos, EnumHand hand, EnumFacing facing, float hitX, float hitY, float hitZ)
    {
    	ItemStack stack = playerIn.getHeldItem(hand);
        if (!playerIn.canPlayerEdit(pos.offset(facing), facing, stack))
        {
            return EnumActionResult.FAIL;
        }
        else
        {
            int hook = net.minecraftforge.event.ForgeEventFactory.onHoeUse(stack, playerIn, worldIn, pos);
            if (hook != 0) return hook > 0 ? EnumActionResult.SUCCESS : EnumActionResult.FAIL;

            IBlockState iblockstate = worldIn.getBlockState(pos);
            Block block = iblockstate.getBlock();

            if (facing != EnumFacing.DOWN && worldIn.isAirBlock(pos.up()))
            {
                if (block == Blocks.GRASS || block == Blocks.GRASS_PATH)
                {
                    this.setBlock(stack, playerIn, worldIn, pos, Blocks.FARMLAND.getDefaultState());
                    return EnumActionResult.SUCCESS;
                }

                if (block == Blocks.DIRT)
                {
                    switch ((BlockDirt.DirtType)iblockstate.getValue(BlockDirt.VARIANT))
                    {
                        case DIRT:
                            this.setBlock(stack, playerIn, worldIn, pos, Blocks.FARMLAND.getDefaultState());
                            return EnumActionResult.SUCCESS;
                        case COARSE_DIRT:
                            this.setBlock(stack, playerIn, worldIn, pos, Blocks.DIRT.getDefaultState().withProperty(BlockDirt.VARIANT, BlockDirt.DirtType.DIRT));
                            return EnumActionResult.SUCCESS;
                    }
                }
                
                if(block == Blocks.NETHERRACK) {
                    this.setBlock(stack, playerIn, worldIn, pos, NetherBlocks.NETHER_FARMLAND.getDefaultState());
                    if(!worldIn.isRemote && playerIn.getRNG().nextFloat() < 0.2F) {
                    	Block.spawnAsEntity(worldIn, pos.up(), new ItemStack(NetherItems.INFERNAL_SEEDS));
                    }
                    return EnumActionResult.SUCCESS;
                }
            }

            return EnumActionResult.PASS;
        }
    }
    
    protected void setBlock(ItemStack stack, EntityPlayer player, World worldIn, BlockPos pos, IBlockState state)
    {
        worldIn.playSound(player, pos, SoundEvents.ITEM_HOE_TILL, SoundCategory.BLOCKS, 1.0F, state.getBlock() == NetherBlocks.NETHER_FARMLAND ? 0.5F : 1.0F);

        if (!worldIn.isRemote)
        {
            worldIn.setBlockState(pos, state, 11);
            stack.damageItem(1, player);
        }
    }

}
