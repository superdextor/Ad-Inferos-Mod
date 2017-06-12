package com.superdextor.adinferos.blocks;

import java.util.ArrayList;
import java.util.Random;

import com.superdextor.adinferos.NetherDamageSource;
import com.superdextor.adinferos.entity.NetherMob;
import com.superdextor.adinferos.init.NetherItems;
import com.superdextor.adinferos.items.ItemAmulet;
import com.superdextor.thinkbigcore.helpers.InventoryHelper;

import net.minecraft.block.material.MapColor;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.SoundEvents;
import net.minecraft.item.ItemStack;
import net.minecraft.util.EnumBlockRenderType;
import net.minecraft.util.EnumParticleTypes;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.fluids.BlockFluidClassic;
import net.minecraftforge.fluids.Fluid;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class BlockAcid extends BlockFluidClassic {
	
    public BlockAcid(Fluid fluid, Material material) {
        super(fluid, material);
        this.setHardness(100.0F);
    }
        
    public void onEntityCollidedWithBlock(World worldIn, BlockPos pos, IBlockState state, Entity entity)
    {
       	boolean flag = true;
    		
        if(entity instanceof NetherMob) {
        	NetherMob nethermob = (NetherMob) entity;
        	if(nethermob.isAcidResistant())
        		flag = false;
        }
        	
		if(entity instanceof EntityPlayer) {
				
			ItemStack stack = null;
    		ArrayList<ItemStack> stacks = InventoryHelper.getItems(((EntityPlayer)entity).inventory, NetherItems.AMULET, 4);
    		
			if(!stacks.isEmpty()) {
				for(ItemStack stackIn : stacks) {
					if(ItemAmulet.isActive(stackIn)) {
						stack = stackIn;
						break;
					}
				}
			}
    		
    		if(stack != null) {
    			flag = false;
    		}
		}
        	
        if (entity.isEntityAlive() && flag && entity instanceof EntityLivingBase) {
        	if(entity.hurtResistantTime < 10) {
        		entity.playSound(SoundEvents.BLOCK_FIRE_EXTINGUISH, 1.0F, 0.3F + worldIn.rand.nextFloat());
        	}
            entity.attackEntityFrom(NetherDamageSource.ACID, 3);
       	}
    }
        
    @SideOnly(Side.CLIENT)
    public void randomDisplayTick(IBlockState state, World worldIn, BlockPos pos, Random rand)
    {
        if(rand.nextBoolean() && worldIn.isAirBlock(pos.up())) {
        double d0 = (double)((float)pos.getX() + rand.nextFloat());
        double d1 = (double)((float)pos.getY() + 0.6F + rand.nextFloat());
        double d2 = (double)((float)pos.getZ() + rand.nextFloat());
        worldIn.spawnParticle(EnumParticleTypes.REDSTONE, d0, d1, d2, 0.5D, 0.0D, 1.0D, new int[0]); }
    }
        
    public MapColor getMapColor(IBlockState state)
    {
        return MapColor.PURPLE;
    }
    
    @Override
    public EnumBlockRenderType getRenderType(IBlockState state)
    {
    	return EnumBlockRenderType.MODEL;
    }
}