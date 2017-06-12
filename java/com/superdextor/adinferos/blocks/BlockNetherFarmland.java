package com.superdextor.adinferos.blocks;

import java.util.Random;

import javax.annotation.Nullable;

import com.superdextor.adinferos.init.NetherBlocks;
import com.superdextor.thinkbigcore.config.TBCConfig;

import net.minecraft.block.Block;
import net.minecraft.block.BlockFarmland;
import net.minecraft.block.SoundType;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import net.minecraftforge.common.IPlantable;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class BlockNetherFarmland extends BlockFarmland {
	
	@SideOnly(Side.CLIENT)
	private boolean shownTip = false;
	
	public BlockNetherFarmland() {
		this.setSoundType(SoundType.STONE);
	}
	
    @SideOnly(Side.CLIENT)
    public AxisAlignedBB getSelectedBoundingBox(IBlockState state, World worldIn, BlockPos pos)
    {
    	if(!shownTip) {
    		TBCConfig.displayTip("Can sustain Infernal Seeds");
    		shownTip = true;
    	}
    	
    	return super.getSelectedBoundingBox(state, worldIn, pos);
    }
	
    public void updateTick(World worldIn, BlockPos pos, IBlockState state, Random rand)
    {
        int i = ((Integer)state.getValue(MOISTURE)).intValue();

        if (!this.hasLava(worldIn, pos))
        {
            if (i > 0)
            {
                worldIn.setBlockState(pos, state.withProperty(MOISTURE, Integer.valueOf(i - 1)), 2);
            }
            else if (!this.hasCrops(worldIn, pos))
            {
                worldIn.setBlockState(pos, Blocks.NETHERRACK.getDefaultState());
            }
        }
        else if (i < 7)
        {
            worldIn.setBlockState(pos, state.withProperty(MOISTURE, Integer.valueOf(7)), 2);
        }
    }
    
    public void onFallenUpon(World worldIn, BlockPos pos, Entity entityIn, float fallDistance)
    {
        if (!worldIn.isRemote && worldIn.rand.nextFloat() < fallDistance - 0.5F && entityIn instanceof EntityLivingBase && (entityIn instanceof EntityPlayer || worldIn.getGameRules().getBoolean("mobGriefing")) && entityIn.width * entityIn.width * entityIn.height > 0.512F)
        {
            worldIn.setBlockState(pos, Blocks.NETHERRACK.getDefaultState());
        }

        entityIn.fall(fallDistance, 1.0F);
    }
    
    private boolean hasCrops(World worldIn, BlockPos pos)
    {
        Block block = worldIn.getBlockState(pos.up()).getBlock();
        return block instanceof net.minecraftforge.common.IPlantable && canSustainPlant(worldIn.getBlockState(pos), worldIn, pos, net.minecraft.util.EnumFacing.UP, (net.minecraftforge.common.IPlantable)block);
    }
    
    private boolean hasLava(World worldIn, BlockPos pos)
    {
        for (BlockPos.MutableBlockPos blockpos$mutableblockpos : BlockPos.getAllInBoxMutable(pos.add(-4, 0, -4), pos.add(4, 1, 4)))
        {
            if (worldIn.getBlockState(blockpos$mutableblockpos).getMaterial() == Material.LAVA)
            {
                return true;
            }
        }

        return false;
    }
    
    public void neighborChanged(IBlockState state, World worldIn, BlockPos pos, Block blockIn, BlockPos p_189540_5_)
    {
        if (worldIn.getBlockState(pos.up()).getMaterial().isSolid())
        {
            worldIn.setBlockState(pos, Blocks.NETHERRACK.getDefaultState());
        }
    }
    
    @SideOnly(Side.CLIENT)
    public boolean shouldSideBeRendered(IBlockState blockState, IBlockAccess blockAccess, BlockPos pos, EnumFacing side)
    {
        switch (side)
        {
            case UP:
                return true;
            case NORTH:
            case SOUTH:
            case WEST:
            case EAST:
                IBlockState iblockstate = blockAccess.getBlockState(pos.offset(side));
                Block block = iblockstate.getBlock();
                return !iblockstate.isOpaqueCube() && block != NetherBlocks.NETHER_FARMLAND;
            default:
                AxisAlignedBB axisalignedbb = blockState.getBoundingBox(blockAccess, pos);

                switch (side)
                {
                    case DOWN:

                        if (axisalignedbb.minY > 0.0D)
                        {
                            return true;
                        }

                        break;
                    case UP:

                        if (axisalignedbb.maxY < 1.0D)
                        {
                            return true;
                        }

                        break;
                    case NORTH:

                        if (axisalignedbb.minZ > 0.0D)
                        {
                            return true;
                        }

                        break;
                    case SOUTH:

                        if (axisalignedbb.maxZ < 1.0D)
                        {
                            return true;
                        }

                        break;
                    case WEST:

                        if (axisalignedbb.minX > 0.0D)
                        {
                            return true;
                        }

                        break;
                    case EAST:

                        if (axisalignedbb.maxX < 1.0D)
                        {
                            return true;
                        }
                }

                return !blockAccess.getBlockState(pos.offset(side)).doesSideBlockRendering(blockAccess, pos.offset(side), side.getOpposite());
        }
    }
    
    @Nullable
    public Item getItemDropped(IBlockState state, Random rand, int fortune)
    {
        return Item.getItemFromBlock(Blocks.NETHERRACK);
    }
    
    public ItemStack getItem(World worldIn, BlockPos pos, IBlockState state)
    {
        return new ItemStack(Blocks.NETHERRACK);
    }
    
    public Material getMaterial(IBlockState state)
    {
        return Material.ROCK;
    }
    
    public boolean canSustainPlant(IBlockState state, IBlockAccess world, BlockPos pos, EnumFacing direction, IPlantable plantable)
    {
        Block plant = plantable.getPlant(world, pos.offset(direction)).getBlock();
    	if(plant == NetherBlocks.INFERNAL_WHEAT) {
    		return true;
    	}

        return false;
    }
    
    public boolean isFertile(World world, BlockPos pos)
    {
        return ((Integer)world.getBlockState(pos).getValue(BlockFarmland.MOISTURE)) > 0;
    }

}
