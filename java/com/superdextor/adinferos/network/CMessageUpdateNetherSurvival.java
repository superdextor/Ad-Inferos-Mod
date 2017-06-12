package com.superdextor.adinferos.network;

import com.superdextor.adinferos.AdInferosCore;
import com.superdextor.thinkbigcore.network.AbstractMessageHandler;

import io.netty.buffer.ByteBuf;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraftforge.fml.common.network.ByteBufUtils;
import net.minecraftforge.fml.common.network.simpleimpl.IMessage;
import net.minecraftforge.fml.common.network.simpleimpl.MessageContext;

public class CMessageUpdateNetherSurvival implements IMessage {
	
	 private NBTTagCompound data;
	
	 public CMessageUpdateNetherSurvival() {}
	 
	public CMessageUpdateNetherSurvival(boolean value) {
		this.data = new NBTTagCompound();
		this.data.setBoolean("value", value);
	}
	
	@Override
	public void fromBytes(ByteBuf buffer) {
		this.data = ByteBufUtils.readTag(buffer);
	}
	
	@Override
	public void toBytes(ByteBuf buffer) {
		 ByteBufUtils.writeTag(buffer, data);
	}
	
	public static class Handler extends AbstractMessageHandler<CMessageUpdateNetherSurvival> {
		
		@Override
		public IMessage onMessage(CMessageUpdateNetherSurvival message, MessageContext ctx) {
			
			NBTTagCompound data = message.data;
			AdInferosCore.proxy.setNetherSurvival(data.getBoolean("value"));
			
			return null;
		}
	}
}