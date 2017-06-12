package com.superdextor.adinferos.blocks;

import java.util.Random;

import javax.annotation.Nullable;

import com.superdextor.adinferos.init.NetherItems;
import com.superdextor.thinkbigcore.config.TBCConfig;

import net.minecraft.block.Block;
import net.minecraft.block.material.EnumPushReaction;
import net.minecraft.block.material.MapColor;
import net.minecraft.block.material.Material;
import net.minecraft.block.properties.IProperty;
import net.minecraft.block.properties.PropertyInteger;
import net.minecraft.block.state.BlockStateContainer;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.init.MobEffects;
import net.minecraft.inventory.EntityEquipmentSlot;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class BlockHardenedLava extends Block
{
	protected static final AxisAlignedBB HARDENED_LAVA_AABB = new AxisAlignedBB(0.0D, 0.0D, 0.0D, 1.0D, 0.875D, 1.0D);
    public static final PropertyInteger AGE = PropertyInteger.create("age", 0, 4);
	
	@SideOnly(Side.CLIENT)
	private boolean shownTip = false;
    
    public BlockHardenedLava()
    {
        super(Material.ROCK);
        this.setDefaultState(this.blockState.getBaseState().withProperty(AGE, Integer.valueOf(0)));
        this.setTickRandomly(true);
        this.setHarvestLevel("pickaxe", 2);
    }
    
    @SideOnly(Side.CLIENT)
    public AxisAlignedBB getSelectedBoundingBox(IBlockState state, World worldIn, BlockPos pos)
    {
    	if(!shownTip) {
    		TBCConfig.displayTip("Good source of Obsidian, can burn your Feet");
    		shownTip = true;
    	}
    	
    	return super.getSelectedBoundingBox(state, worldIn, pos);
    }
    
    @Nullable
    public AxisAlignedBB getCollisionBoundingBox(IBlockState blockState, IBlockAccess worldIn, BlockPos pos)
    {
        return HARDENED_LAVA_AABB;
    }
    
    public int getMetaFromState(IBlockState state)
    {
        return ((Integer)state.getValue(AGE)).intValue();
    }

    public IBlockState getStateFromMeta(int meta)
    {
        return this.getDefaultState().withProperty(AGE, Integer.valueOf(MathHelper.clamp_int(meta, 0, 3)));
    }
    
    public void updateTick(World worldIn, BlockPos pos, IBlockState state, Random rand)
    {
        if (state.getValue(AGE).intValue() > 0 && (rand.nextInt(3) == 0 || this.getSolidLavaBlockCount(worldIn, pos) < 4) && worldIn.getLightFromNeighbors(pos) > 11 - ((Integer)state.getValue(AGE)).intValue() - state.getLightOpacity())
        {
            this.doAge(worldIn, pos, state, rand, true);
        }
        else
        {
            worldIn.scheduleUpdate(pos, this, MathHelper.getRandomIntegerInRange(rand, 20, 40));
        }
    }
    
    public void neighborChanged(IBlockState state, World worldIn, BlockPos pos, Block blockIn, BlockPos value)
    {
        if (state.getValue(AGE).intValue() > 0 && blockIn == this)
        {
            int i = this.getSolidLavaBlockCount(worldIn, pos);

            if (i < 2)
            {
                this.makeLava(worldIn, pos, state);
            }
        }
    }
    
    private int getSolidLavaBlockCount(World worldIn, BlockPos pos)
    {
        int amount = 0;

        for (EnumFacing enumfacing : EnumFacing.values())
        {
            if (worldIn.getBlockState(pos.offset(enumfacing)).getBlock() == this)
            {
                ++amount;

                if (amount >= 4)
                {
                    return amount;
                }
            }
        }

        return amount;
    }
    
    protected void doAge(World worldIn, BlockPos pos, IBlockState state, Random rand, boolean isFirstcall)
    {
        int i = ((Integer)state.getValue(AGE)).intValue();

        if (i < 4)
        {
        	worldIn.setBlockState(pos, state.withProperty(AGE, Integer.valueOf(i + 1)), 2);
        	worldIn.scheduleUpdate(pos, this, MathHelper.getRandomIntegerInRange(rand, 20, 40));
        }
        else
        {
            this.makeLava(worldIn, pos, state);

            if (isFirstcall)
            {
                for (EnumFacing enumfacing : EnumFacing.values())
                {
                    BlockPos blockpos = pos.offset(enumfacing);
                    IBlockState iblockstate = worldIn.getBlockState(blockpos);

                    if (iblockstate.getBlock() == this)
                    {
                        this.doAge(worldIn, blockpos, iblockstate, rand, false);
                    }
                }
            }
        }
    }

    protected BlockStateContainer createBlockState()
    {
        return new BlockStateContainer(this, new IProperty[] {AGE});
    }

    public ItemStack getItem(World worldIn, BlockPos pos, IBlockState state)
    {
        return ItemStack.field_190927_a;
    }
    
    public void dropBlockAsItemWithChance(World worldIn, BlockPos pos, IBlockState state, float chance, int fortune)
    {
        if (!worldIn.isRemote && !worldIn.restoringBlockSnapshots) // do not drop items while restoring blockstates, prevents item dupe
        {
        	this.makeLava(worldIn, pos, state);
        }
    }

    public void harvestBlock(World worldIn, EntityPlayer player, BlockPos pos, IBlockState state, TileEntity te, ItemStack stack)
    {
        player.addExhaustion(0.025F);
        this.makeLava(worldIn, pos, state);
    }

    public int quantityDropped(Random random)
    {
        return 0;
    }

    protected void makeLava(World worldIn, BlockPos pos, IBlockState state)
    {
    	if(state.getValue(AGE) > 0 || worldIn.rand.nextInt(17) == 0) {
        	worldIn.setBlockState(pos, Blocks.FLOWING_LAVA.getDefaultState());
    	}
    	else {
    		spawnAsEntity(worldIn, pos, new ItemStack(NetherItems.OBSIDIAN_NUGGET, 2 + worldIn.rand.nextInt(5)));
    	}
    }

    public EnumPushReaction getMobilityFlag(IBlockState state)
    {
        return EnumPushReaction.NORMAL;
    }
    
    public MapColor getMapColor(IBlockState state) {
    	return MapColor.TNT;
    }
    
    public void onEntityCollidedWithBlock(World worldIn, BlockPos pos, IBlockState state, Entity entityIn) {
    	if(entityIn instanceof EntityLivingBase) {
    		EntityLivingBase entitylivingbase = (EntityLivingBase) entityIn;
    		
    		if(!entityIn.isImmuneToFire() && !entitylivingbase.isPotionActive(MobEffects.FIRE_RESISTANCE)) {
        		ItemStack stack = entitylivingbase.getItemStackFromSlot(EntityEquipmentSlot.FEET);
        		if(stack.func_190926_b()) {
                	entityIn.setFire(3);
        		}
        		else if(entitylivingbase.getRNG().nextInt(12) == 0){
        			stack.damageItem(1, entitylivingbase);
        		}
    		}
    	}
    }
}