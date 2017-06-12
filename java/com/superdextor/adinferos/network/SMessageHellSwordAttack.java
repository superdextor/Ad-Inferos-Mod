package com.superdextor.adinferos.network;

import java.util.UUID;

import com.superdextor.adinferos.init.NetherItems;
import com.superdextor.thinkbigcore.ThinkBigCore;
import com.superdextor.thinkbigcore.helpers.EntityHelper;
import com.superdextor.thinkbigcore.network.AbstractMessageHandler;

import io.netty.buffer.ByteBuf;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.MobEffects;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.potion.PotionEffect;
import net.minecraft.world.World;
import net.minecraftforge.fml.common.network.ByteBufUtils;
import net.minecraftforge.fml.common.network.simpleimpl.IMessage;
import net.minecraftforge.fml.common.network.simpleimpl.MessageContext;

public class SMessageHellSwordAttack implements IMessage {
	
	 private NBTTagCompound data;
	 
	 public SMessageHellSwordAttack() {}
	
	public SMessageHellSwordAttack(UUID player, UUID target, boolean inHell, int attackType) {
		this.data = new NBTTagCompound();
		this.data.setUniqueId("Player", player);
		this.data.setUniqueId("Target", target);
		this.data.setBoolean("InHell", inHell);
		this.data.setInteger("Type", attackType);
	}
	
	@Override
	public void fromBytes(ByteBuf buffer) {
		this.data = ByteBufUtils.readTag(buffer);
	}
	
	@Override
	public void toBytes(ByteBuf buffer) {
		 ByteBufUtils.writeTag(buffer, data);
	}
	
	public static class Handler extends AbstractMessageHandler<SMessageHellSwordAttack> {
		
		@Override
		public IMessage onMessage(SMessageHellSwordAttack message, MessageContext ctx) {
			
			NBTTagCompound data = message.data;
			World world = ThinkBigCore.proxy.getPlayerEntity(ctx).worldObj;
			EntityPlayer player = world.getPlayerEntityByUUID(data.getUniqueId("Player"));
			Entity target = EntityHelper.getEntityByUUID(world, data.getUniqueId("Target"));
			boolean inHell = data.getBoolean("InHell");
			int attackType = data.getInteger("Type");
			
			if(player == null || target == null || !(target instanceof EntityLivingBase)) {
				return null;
			}
			
			Item theItem = NetherItems.QUARTZ_SWORD;
			
			if(attackType == 1) {
				theItem = NetherItems.WITHER_SWORD;
			}
			
			ItemStack stack = player.getHeldItemMainhand();
			
			if(stack == null || stack.getItem() != theItem) {
				stack = player.getHeldItemOffhand();
			}
			
			if(stack == null || stack.getItem() != theItem) {
				return null;
			}
			
			stack.damageItem(1, player);
			player.getCooldownTracker().setCooldown(theItem, inHell ? 160 : 200);
			
			if(target instanceof EntityLiving) {
				((EntityLiving)target).setRevengeTarget(player);
			}
			
			if(attackType == 0) {
				int a = inHell ? 3 : 2;
				((EntityLivingBase)target).addPotionEffect(new PotionEffect(MobEffects.LEVITATION, 58, a));
			}
			else {
				int a = inHell ? 5 : 3;
				
				((EntityLivingBase)target).addPotionEffect(new PotionEffect(MobEffects.BLINDNESS, 20, a));
				((EntityLivingBase)target).addPotionEffect(new PotionEffect(MobEffects.WITHER, 70, a));
			}
			
			return null;
		}
	}
}