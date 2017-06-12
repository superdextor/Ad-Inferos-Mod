package com.superdextor.adinferos.items;

import java.util.List;

import com.superdextor.adinferos.config.NetherConfig;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.init.MobEffects;
import net.minecraft.init.SoundEvents;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.potion.PotionEffect;
import net.minecraft.util.ActionResult;
import net.minecraft.util.DamageSource;
import net.minecraft.util.EnumActionResult;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.EnumParticleTypes;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class ItemWormHole extends Item
{
	
	public ItemWormHole() {
		super();
		this.setMaxDamage(100);
		this.setNoRepair();
		this.setMaxStackSize(1);
	}
	
	public EnumActionResult onItemUse(EntityPlayer playerIn, World worldIn, BlockPos pos, EnumHand hand, EnumFacing facing, float hitX, float hitY, float hitZ) {
		
		ItemStack stack = playerIn.getHeldItem(hand);
		if(playerIn.isSneaking() && NetherConfig.wormholeTravel)
		{
			if(stack.getTagCompound() == null)
			{
				stack.setTagCompound(new NBTTagCompound());
			}
			NBTTagCompound nbt = new NBTTagCompound();
			nbt.setInteger("dimension", playerIn.dimension);
			nbt.setInteger("posX", pos.getX());
			nbt.setInteger("posY", pos.getY() + 1);
			nbt.setInteger("posZ", pos.getZ());
			stack.getTagCompound().setTag("coords", nbt);
            for (int k = 0; k < 15; ++k)
			playerIn.worldObj.spawnParticle(EnumParticleTypes.PORTAL, pos.getX() + 1.4D, pos.getY(), pos.getZ() + 0.5D, ((double)itemRand.nextFloat() - 0.5D) * 0.5D, ((double)itemRand.nextFloat() - 0.5D) * 0.5D, ((double)itemRand.nextFloat() - 0.5D) * 0.5D, new int[0]);
            playerIn.playSound(SoundEvents.ENTITY_ENDERMEN_TELEPORT, 1.0F, 0.4F);
	        return EnumActionResult.SUCCESS;
		}
		
        return EnumActionResult.FAIL;
	}
	
	public ActionResult<ItemStack> onItemRightClick(World worldIn, EntityPlayer playerIn, EnumHand hand) {
		
		ItemStack stack = playerIn.getHeldItem(hand);
		if(playerIn.capabilities.isCreativeMode) {
			playerIn.getCooldownTracker().setCooldown(this, 20);
		}
		else {
			playerIn.getCooldownTracker().setCooldown(this, 180);
		}
		
		if(!playerIn.isSneaking() && NetherConfig.wormholeTravel)
		{
			if(!worldIn.isRemote && stack.getTagCompound() != null)
			{
				NBTTagCompound nbt = (NBTTagCompound) stack.getTagCompound().getTag("coords");
				int dim = nbt.getInteger("dimension");
				int posX = nbt.getInteger("posX");
				int posY = nbt.getInteger("posY");
				int posZ = nbt.getInteger("posZ");
				this.teleportTo(worldIn, playerIn, stack, new BlockPos(posX, posY, posZ), dim);
			}
			
	        return new ActionResult(EnumActionResult.SUCCESS, stack);
		}
		
        return new ActionResult(EnumActionResult.FAIL, stack);
    }
	
	@SideOnly(Side.CLIENT)
    public void addInformation(ItemStack stack, EntityPlayer playerIn, List toolTip, boolean advanced) 
	{
		if(stack.getTagCompound() != null)
		{
			if(stack.getTagCompound().hasKey("coords"))
			{
				NBTTagCompound nbt = (NBTTagCompound) stack.getTagCompound().getTag("coords");
				int dim = nbt.getInteger("dimension");
				double posX = nbt.getInteger("posX");
				double posY = nbt.getInteger("posY");
				double posZ = nbt.getInteger("posZ");
				toolTip.add("Location: X " + posX + " Y " + posY + " Z " + posZ);
			}
		}
		else
			toolTip.add("Sneak Click Ground to set Location");
	}
	
	@SideOnly(Side.CLIENT)
    public boolean hasEffect(ItemStack stack)
    {
		return true;
    }
	
    protected boolean teleportTo(World world, EntityPlayer player, ItemStack stack, BlockPos pos, int dimension)
    {
    	if(player.dimension == dimension) {
        	player.addPotionEffect(new PotionEffect(MobEffects.NAUSEA, 120, 0, false, false));
            player.addPotionEffect(new PotionEffect(MobEffects.BLINDNESS, 30, 0, false, false));
        	stack.damageItem(1, player);
        	
    		BlockPos oldPos = new BlockPos(player.posX, player.posY, player.posZ);
    		NBTTagCompound nbt = new NBTTagCompound();
    		nbt.setInteger("dimension", player.dimension);
    		nbt.setInteger("posX", oldPos.getX());
    		nbt.setInteger("posY", oldPos.getY());
    		nbt.setInteger("posZ", oldPos.getZ());
    		stack.getTagCompound().setTag("coords", nbt);
    		
            if (player instanceof EntityPlayerMP)
            {
                EntityPlayerMP entityplayermp = (EntityPlayerMP)player;

                if (entityplayermp.connection.getNetworkManager().isChannelOpen() && entityplayermp.worldObj == world && !entityplayermp.isPlayerSleeping())
                {
                    net.minecraftforge.event.entity.living.EnderTeleportEvent event = new net.minecraftforge.event.entity.living.EnderTeleportEvent(entityplayermp, pos.getX(), pos.getY(), pos.getZ(), 0.0F);
                    if (!net.minecraftforge.common.MinecraftForge.EVENT_BUS.post(event))
                    {
                    	
    	                player.setPositionAndUpdate(event.getTargetX() + 0.5D, event.getTargetY(), event.getTargetZ() + 0.5D);
    	                player.fallDistance = 0.0F;
    	                player.attackEntityFrom(DamageSource.fall, event.getAttackDamage());
                    }
                }
            }

            world.playSound(null, pos, SoundEvents.ENTITY_ENDERMEN_TELEPORT, SoundCategory.PLAYERS, 1.0F, 0.6F);
            world.playSound(null, oldPos, SoundEvents.ENTITY_ENDERMEN_TELEPORT, SoundCategory.PLAYERS, 1.0F, 0.6F);
    	}
        
        return true;
    }
}
