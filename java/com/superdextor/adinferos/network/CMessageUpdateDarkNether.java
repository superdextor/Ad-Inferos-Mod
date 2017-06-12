package com.superdextor.adinferos.network;

import com.superdextor.adinferos.AdInferosCore;
import com.superdextor.thinkbigcore.network.AbstractMessageHandler;

import io.netty.buffer.ByteBuf;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraftforge.fml.common.network.ByteBufUtils;
import net.minecraftforge.fml.common.network.simpleimpl.IMessage;
import net.minecraftforge.fml.common.network.simpleimpl.MessageContext;

public class CMessageUpdateDarkNether implements IMessage {
	
	 private NBTTagCompound data;
	
	 public CMessageUpdateDarkNether() {}
	 
	public CMessageUpdateDarkNether(boolean value) {
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
	
	public static class Handler extends AbstractMessageHandler<CMessageUpdateDarkNether> {
		
		@Override
		public IMessage onMessage(CMessageUpdateDarkNether message, MessageContext ctx) {
			
			NBTTagCompound data = message.data;
			AdInferosCore.proxy.setDarkNether(data.getBoolean("value"));
			
			return null;
		}
	}
}