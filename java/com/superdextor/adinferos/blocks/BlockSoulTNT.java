package com.superdextor.adinferos.blocks;

import javax.annotation.Nullable;

import com.superdextor.thinkbigcore.blocks.BlockCustomExplosive;
import com.superdextor.thinkbigcore.config.TBCConfig;
import com.superdextor.thinkbigcore.entity.EntityExplosivePrimed;

import net.minecraft.block.SoundType;
import net.minecraft.block.material.MapColor;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.MobEffects;
import net.minecraft.init.SoundEvents;
import net.minecraft.potion.PotionEffect;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class BlockSoulTNT extends BlockCustomExplosive
{
	
	@SideOnly(Side.CLIENT)
	private boolean shownTip = false;
	
    protected static final AxisAlignedBB TNT_AABB = new AxisAlignedBB(0.01D, 0.01D, 0.01D, 0.99D, 0.99D, 0.99D);
    private static final PotionEffect[] effects = buildEffects();
    
	public BlockSoulTNT()
    {
        super(70, 6.0F);
		this.setHardness(0.0F);
		this.setResistance(0.0F);
        this.setLightLevel(0.3F);
        this.setSoundType(SoundType.GROUND);
    }
	
    @SideOnly(Side.CLIENT)
    public AxisAlignedBB getSelectedBoundingBox(IBlockState state, World worldIn, BlockPos pos)
    {
    	if(!shownTip) {
    		TBCConfig.displayTip("Handle with Extream care");
    		shownTip = true;
    	}
    	
    	return super.getSelectedBoundingBox(state, worldIn, pos);
    }
	
	protected void explode(World worldIn, BlockPos pos, IBlockState state, EntityLivingBase igniter, boolean wasBlownup) {
        if (!worldIn.isRemote)
        {
            if (state == null || ((Boolean)state.getValue(EXPLODE)).booleanValue())
            {
                EntityExplosivePrimed entitytntprimed = new EntityExplosivePrimed(worldIn, (double)((float)pos.getX() + 0.5F), (double)((float)pos.getY() + 0.5F), (double)((float)pos.getZ() + 0.5F), igniter, this);
                entitytntprimed.effects = effects;
                if(wasBlownup)
                entitytntprimed.setFuse(worldIn.rand.nextInt(fuse / 4) + fuse / 8);
                worldIn.spawnEntityInWorld(entitytntprimed);
                if(!wasBlownup)
                worldIn.playSound((EntityPlayer)null, entitytntprimed.posX, entitytntprimed.posY, entitytntprimed.posZ, SoundEvents.ENTITY_TNT_PRIMED, SoundCategory.BLOCKS, 1.0F, 1.0F);
            }
        }
	}
    
    public void onEntityCollidedWithBlock(World world, BlockPos pos, IBlockState state, Entity entity)
    {
        if (!world.isRemote)
        {
        	EntityLivingBase igniter = null;
        	
        	if(entity instanceof EntityLivingBase) {
        		igniter = (EntityLivingBase) entity;
        	}
        	
        	this.explode(world, pos, state.withProperty(EXPLODE, true), igniter, true);
            world.setBlockToAir(pos);
        }
    }
    
    
    @Nullable
    public AxisAlignedBB getCollisionBoundingBox(IBlockState blockState, IBlockAccess worldIn, BlockPos pos) {
        return TNT_AABB;
    }
    
    public MapColor getMapColor(IBlockState state)
    {
        return MapColor.BROWN;
    }
	
	private static PotionEffect[] buildEffects() {
		PotionEffect[] effects = new PotionEffect[1];
		effects[0] = new PotionEffect(MobEffects.WITHER, 130, 1);
		return effects;
	}
}