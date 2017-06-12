package com.superdextor.adinferos.blocks;

import java.util.Random;

import javax.annotation.Nullable;

import com.superdextor.adinferos.NetherDamageSource;
import com.superdextor.adinferos.inventory.TileEntityAltar;

import net.minecraft.block.BlockSoulSand;
import net.minecraft.block.SoundType;
import net.minecraft.block.properties.IProperty;
import net.minecraft.block.properties.PropertyInteger;
import net.minecraft.block.state.BlockStateContainer;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.RayTraceResult;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;

public class BlockBloodHolder extends BlockSoulSand {
	
    public static final PropertyInteger BLOOD = PropertyInteger.create("blood", 0, 9);
    protected static final AxisAlignedBB SOUL_SAND_AABB = new AxisAlignedBB(0.0D, 0.0D, 0.0D, 1.0D, 0.475D, 1.0D);
	
	public BlockBloodHolder() {
        this.setDefaultState(this.blockState.getBaseState().withProperty(BLOOD, 0));
		this.setHardness(0.8F);
		this.setSoundType(SoundType.SAND);
        this.setCreativeTab(null);
	}
	
    @Nullable
    public AxisAlignedBB getCollisionBoundingBox(IBlockState blockState, IBlockAccess worldIn, BlockPos pos)
    {
        return SOUL_SAND_AABB;
    }
	
	public Item getItemDropped(IBlockState state, Random rand, int fortune) {
		return Item.getItemFromBlock(Blocks.SOUL_SAND);
	}
	
	public ItemStack getPickBlock(IBlockState state, RayTraceResult target, World world, BlockPos pos, EntityPlayer player) {
		return new ItemStack(Blocks.SOUL_SAND);
	}
	
	public void onEntityCollidedWithBlock(World worldIn, BlockPos pos, IBlockState state, Entity entityIn) {
		
		entityIn.motionX *= 0.2D;
		entityIn.motionZ *= 0.2D;
		if(entityIn.motionY < 0.0D) {
			entityIn.motionY = -0.002D;
		}
		
		if(!worldIn.isRemote) {
			
			if(entityIn.ticksExisted % 10 != 0) {
				return;
			}
			
			if(!(entityIn instanceof EntityLivingBase)) {
				return;
			}
			
			TileEntity tileentity = worldIn.getTileEntity(pos.add(2, 1, 0));
			
			if(tileentity instanceof TileEntityAltar && ((TileEntityAltar) tileentity).isActive()) {
				this.doSacrifice((TileEntityAltar) tileentity, worldIn, pos, entityIn);
				return;
			}
			
			tileentity = worldIn.getTileEntity(pos.add(0, 1, 2));
			
			if(tileentity instanceof TileEntityAltar && ((TileEntityAltar) tileentity).isActive()) {
				this.doSacrifice((TileEntityAltar) tileentity, worldIn, pos, entityIn);
				return;
			}
			
			tileentity = worldIn.getTileEntity(pos.add(-2, 1, 0));
			
			if(tileentity instanceof TileEntityAltar && ((TileEntityAltar) tileentity).isActive()) {
				this.doSacrifice((TileEntityAltar) tileentity, worldIn, pos, entityIn);
				return;
			}
			
			tileentity = worldIn.getTileEntity(pos.add(0, 1, -2));
			
			if(tileentity instanceof TileEntityAltar && ((TileEntityAltar) tileentity).isActive()) {
				this.doSacrifice((TileEntityAltar) tileentity, worldIn, pos, entityIn);
				return;
			}
		}
	}
	
	private void doSacrifice(TileEntityAltar tileentity, World world, BlockPos pos, Entity entity) {
		tileentity.blood += 1;
		entity.attackEntityFrom(NetherDamageSource.SACRIFICE, 1);
	}
	
    public IBlockState getStateFromMeta(int meta)
    {
        return this.getDefaultState().withProperty(BLOOD, meta);
    }

    public int getMetaFromState(IBlockState state)
    {
        return state.getValue(BLOOD);
    }

    protected BlockStateContainer createBlockState()
    {
        return new BlockStateContainer(this, new IProperty[] {BLOOD});
    }
}
