package com.superdextor.adinferos.items;

import org.apache.logging.log4j.LogManager;

import com.superdextor.adinferos.entity.monster.EntityGhost;
import com.superdextor.adinferos.init.NetherItems;
import com.superdextor.adinferos.inventory.ExtractorRecipes;
import com.superdextor.adinferos.inventory.ExtractorRecipes.ExtractorRecipe;

import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityList;
import net.minecraft.entity.EntityLiving;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

public class ItemSoul extends Item {
	
	public ItemSoul() {
		
	}
	
	public String getItemStackDisplayName(ItemStack stack) {
		
		String s = getSoulName(stack);
		
		if(s.isEmpty()) {
			return super.getItemStackDisplayName(stack);
		}
		else {
			return super.getItemStackDisplayName(stack) + " of " + s;
		}
	}
	
	public static int getSoulRepairAmount(ItemStack stack) {
		
		if(stack != null && stack.hasTagCompound()) {
			return stack.getTagCompound().getInteger("SoulRepairAmount");
		}
		else {
			return 999;
		}
	}
	
	public static boolean hasSoul(ItemStack stack) {
		
		if(stack != null && stack.hasTagCompound() && !stack.getTagCompound().getString("Soul").isEmpty()) {
			return true;
		}
		else {
			return false;
		}
	}
	
	public static ResourceLocation getSoulID(ItemStack stack) {
		if(stack.hasTagCompound()) {
			return new ResourceLocation(stack.getTagCompound().getString("Soul"));
		}
		else {
			return null;
		}
	}
	
	public static String getSoulName(ItemStack stack) {
		if(stack.hasTagCompound()) {
			return stack.getTagCompound().getString("SoulName");
		}
		else {
			return "";
		}
	}

	public static ItemStack removeSoul(ItemStack stack) {
		
		NBTTagCompound compound = stack.getTagCompound();
		
		if(compound == null) {
			return stack;
		}
		compound.setString("Soul", "");
		compound.setString("SoulName", "");
		
		return stack;
	}
	
	public static ItemStack extractSoulFromItem(ItemStack stack) {
		
		if(hasSoul(stack)) {
			ItemStack soul = new ItemStack(NetherItems.SOUL);
			NBTTagCompound compound = new NBTTagCompound();
			compound.setString("Soul", stack.getTagCompound().getString("Soul"));
			compound.setString("SoulName", stack.getTagCompound().getString("SoulName"));
			soul.setTagCompound(compound);
			
			removeSoul(stack);
			
			return soul;
		}
		
		return ItemStack.field_190927_a;
	}

	public static ItemStack createSoulFromEntity(ItemStack stack, EntityLiving entityIn) {
		
		if(!stack.hasTagCompound()) {
			stack.setTagCompound(new NBTTagCompound());
		}
		
		NBTTagCompound compound = stack.getTagCompound();
		String soulId = null;
		
		if(entityIn.getClass() == EntityGhost.class) {
			soulId = ((EntityGhost) entityIn).getSoul();
			compound.setString("Soul", soulId);
			compound.setString("SoulName", ((EntityGhost) entityIn).getSoulName());
		}
		else {
			soulId = EntityList.func_191301_a(entityIn).toString();
			compound.setString("Soul", soulId);
			compound.setString("SoulName", entityIn.getName());
		}
		
		ExtractorRecipe extractorRecipes = ExtractorRecipes.getRecipe(soulId);
		
		if(extractorRecipes == null) {
			LogManager.getLogger().warn("Failed to find recipe for " + soulId);
			compound.setInteger("SoulRepairAmount", 6);
		}
		else {
			compound.setInteger("SoulRepairAmount", extractorRecipes.soulsRequired);
		}
		
		return stack;
	}
	
	public static ItemStack createSoulFromEntity(EntityLiving entityIn) {
		ItemStack stack = new ItemStack(NetherItems.SOUL);
		NBTTagCompound compound = new NBTTagCompound();
		if(entityIn.getClass() == EntityGhost.class) {
			compound.setString("Soul", ((EntityGhost) entityIn).getSoul());
			compound.setString("SoulName", ((EntityGhost) entityIn).getSoulName());
		}
		else {
			compound.setString("Soul", EntityList.func_191301_a(entityIn).toString());
			compound.setString("SoulName", entityIn.getName());
		}
		stack.setTagCompound(compound);
		return stack;
	}
	
	public static EntityLiving createEntityFromSoul(ItemStack stack, World world, BlockPos pos) {
		EntityLiving entityLiving = null;
		
		if(stack.hasTagCompound()) {
			NBTTagCompound compound = stack.getTagCompound();
			String s = compound.getString("Soul");
			String s1 = compound.getString("SoulName");
			
			if(!s.isEmpty()) {
				Entity entity1 = EntityList.createEntityByIDFromName(new ResourceLocation(s), world);
				
				if(entity1 instanceof EntityLiving) {
					entityLiving = (EntityLiving) entity1;
					entityLiving.setLocationAndAngles(pos.getX(), pos.getY(), pos.getZ(), 0.0F, 0.0F);
					if(!s1.isEmpty() && s1 != entityLiving.getName()) {
						entityLiving.setCustomNameTag(s1);
					}
					
					entityLiving.onInitialSpawn(world.getDifficultyForLocation(pos), null);
					world.spawnEntityInWorld(entityLiving);
				}
			}
			
		}
		
		return entityLiving;
	}

}
