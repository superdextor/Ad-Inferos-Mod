package com.superdextor.adinferos.items;

import com.superdextor.adinferos.blocks.BlockAcid;
import com.superdextor.adinferos.init.NetherItems;
import com.superdextor.thinkbigcore.config.TBCConfig;

import net.minecraft.block.Block;
import net.minecraft.block.BlockLiquid;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.init.SoundEvents;
import net.minecraft.item.Item;
import net.minecraft.item.ItemBucket;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ActionResult;
import net.minecraft.util.EnumActionResult;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.SoundEvent;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.RayTraceResult;
import net.minecraft.world.World;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class ItemGoldenBucket extends ItemBucket
{
	
	@SideOnly(Side.CLIENT)
	private boolean shownTip = false;
	
	private Block lquid;

    public ItemGoldenBucket(Block block)
    {
    	super(block);
        this.maxStackSize = 1;
        this.lquid = block;
    }
    
    public void setLquid(Block lquidIn) {
    	this.lquid = lquidIn;
    }
    
    @SideOnly(Side.CLIENT)
    public void onCreated(ItemStack stack, World worldIn, EntityPlayer playerIn) {
    	if(!shownTip)
    	{
    		TBCConfig.displayTip("Can hold Lava, Water Acid & Blood");
    		shownTip = true;
    	}
    }

    public ActionResult<ItemStack> onItemRightClick(World worldIn, EntityPlayer playerIn, EnumHand hand)
    {
    	ItemStack itemstack = playerIn.getHeldItem(hand);
        boolean flag = this.lquid == Blocks.AIR;
        RayTraceResult raytraceresult = this.rayTrace(worldIn, playerIn, flag);
        ActionResult<ItemStack> ret = net.minecraftforge.event.ForgeEventFactory.onBucketUse(playerIn, worldIn, itemstack, raytraceresult);
        if (ret != null) return ret;

        if (raytraceresult == null)
        {
            return new ActionResult(EnumActionResult.PASS, itemstack);
        }
        else if (raytraceresult.typeOfHit != RayTraceResult.Type.BLOCK)
        {
            return new ActionResult(EnumActionResult.PASS, itemstack);
        }
        else
        {
            BlockPos blockpos = raytraceresult.getBlockPos();

            if (!worldIn.isBlockModifiable(playerIn, blockpos))
            {
                return new ActionResult(EnumActionResult.FAIL, itemstack);
            }
            else if (flag)
            {
                if (!playerIn.canPlayerEdit(blockpos.offset(raytraceresult.sideHit), raytraceresult.sideHit, itemstack))
                {
                    return new ActionResult(EnumActionResult.FAIL, itemstack);
                }
                else
                {
                    IBlockState iblockstate = worldIn.getBlockState(blockpos);
                    Material material = iblockstate.getMaterial();
                    Block block = iblockstate.getBlock();

                    if (material == Material.WATER && ((Integer)iblockstate.getValue(BlockLiquid.LEVEL)).intValue() == 0)
                    {
                        worldIn.setBlockState(blockpos, Blocks.AIR.getDefaultState(), 11);
                        playerIn.playSound(SoundEvents.ITEM_BUCKET_FILL, 1.0F, 1.0F);
                        return new ActionResult(EnumActionResult.SUCCESS, this.fillBucket(itemstack, playerIn, block instanceof BlockAcid ? NetherItems.GOLDEN_BUCKET_ACID : NetherItems.GOLDEN_BUCKET_WATER));
                    }
                    else if (material == Material.LAVA && ((Integer)iblockstate.getValue(BlockLiquid.LEVEL)).intValue() == 0)
                    {
                        playerIn.playSound(SoundEvents.ITEM_BUCKET_FILL_LAVA, 1.0F, 1.0F);
                        worldIn.setBlockState(blockpos, Blocks.AIR.getDefaultState(), 11);
                        return new ActionResult(EnumActionResult.SUCCESS, this.fillBucket(itemstack, playerIn, NetherItems.GOLDEN_BUCKET_LAVA));
                    }
                    else
                    {
                        return new ActionResult(EnumActionResult.FAIL, itemstack);
                    }
                }
            }
            else
            {
                boolean flag1 = worldIn.getBlockState(blockpos).getBlock().isReplaceable(worldIn, blockpos);
                BlockPos blockpos1 = flag1 && raytraceresult.sideHit == EnumFacing.UP ? blockpos : blockpos.offset(raytraceresult.sideHit);

                if (!playerIn.canPlayerEdit(blockpos1, raytraceresult.sideHit, itemstack))
                {
                    return new ActionResult(EnumActionResult.FAIL, itemstack);
                }
                else if (this.tryPlaceContainedLiquid(playerIn, worldIn, blockpos1))
                {
                    return !playerIn.capabilities.isCreativeMode ? new ActionResult(EnumActionResult.SUCCESS, new ItemStack(NetherItems.GOLDEN_BUCKET)) : new ActionResult(EnumActionResult.SUCCESS, itemstack);
                }
                else
                {
                    return new ActionResult(EnumActionResult.FAIL, itemstack);
                }
            }
        }
    }

    private ItemStack fillBucket(ItemStack emptyBuckets, EntityPlayer player, Item fullBucket)
    {
        if (player.capabilities.isCreativeMode)
        {
            return emptyBuckets;
        }
        else
        {
            emptyBuckets.func_190918_g(1);

            if (emptyBuckets.func_190926_b())
            {
                return new ItemStack(fullBucket);
            }
            else
            {
                if (!player.inventory.addItemStackToInventory(new ItemStack(fullBucket)))
                {
                    player.dropItem(new ItemStack(fullBucket), false);
                }

                return emptyBuckets;
            }
        }
    }

    public boolean tryPlaceContainedLiquid(EntityPlayer worldIn, World pos, BlockPos p_180616_3_)
    {
        if (this.lquid == Blocks.AIR)
        {
            return false;
        }
        else
        {
            IBlockState iblockstate = pos.getBlockState(p_180616_3_);
            Material material = iblockstate.getMaterial();
            boolean flag = !material.isSolid();
            boolean flag1 = iblockstate.getBlock().isReplaceable(pos, p_180616_3_);

            if (!pos.isAirBlock(p_180616_3_) && !flag && !flag1)
            {
                return false;
            }
            else
            {
                if (!pos.isRemote && (flag || flag1) && !material.isLiquid())
                {
                    pos.destroyBlock(p_180616_3_, true);
                }

                SoundEvent soundevent = this.lquid == Blocks.FLOWING_LAVA ? SoundEvents.ITEM_BUCKET_EMPTY_LAVA : SoundEvents.ITEM_BUCKET_EMPTY;
                pos.playSound(worldIn, p_180616_3_, soundevent, SoundCategory.BLOCKS, 1.0F, 1.0F);
                pos.setBlockState(p_180616_3_, this.lquid.getDefaultState(), 11);

                return true;
            }
        }
    }
}