package com.superdextor.adinferos.blocks;

import java.util.Iterator;
import java.util.Set;
import java.util.function.Predicate;

import net.minecraft.block.Block;
import net.minecraft.block.material.MapColor;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.text.TextComponentString;
import net.minecraft.world.World;

public class BlockNetherSpawn extends Block {

	public BlockNetherSpawn() {
		super(Material.ROCK, MapColor.TNT);
	}
	
    public boolean onBlockActivated(World worldIn, BlockPos pos, IBlockState state, EntityPlayer playerIn,
    		EnumHand hand, EnumFacing side, float hitX, float hitY, float hitZ) 
    {
        if (worldIn.isRemote)
        {
            return true;
        }
        else
        {
        	if(worldIn.provider.getDimension() == -1) {
            	playerIn.setSpawnPoint(null, false);
            	setNetherSpawn(playerIn, pos, !hasNetherSpawn(playerIn));
            	if(hasNetherSpawn(playerIn)) {
            		playerIn.addChatComponentMessage(new TextComponentString("Spawn Point Set"), true);
            	}
            	else {
            		playerIn.addChatComponentMessage(new TextComponentString("Spawn Point Removed"), true);
            	}
        	}
        	else {
        		playerIn.addChatComponentMessage(new TextComponentString("You must be in the Nether to set your Spawn"), true);
        	}
        }
        
		return true;
    }
    
    public static void setNetherSpawn(EntityPlayer playerIn, BlockPos pos, boolean apply) {
    	if(apply) {
    		playerIn.addTag("NetherSpawn=X" + pos.getX() + "Y" + pos.getY() + "Z" + pos.getZ());
    	}
    	else {
    		playerIn.getTags().removeIf(new Predicate<String>() {

				public boolean test(String t) {
					return !t.isEmpty() && t.contains("NetherSpawn=");
				}
    		});
    	}
    }
    
    public static boolean hasNetherSpawn(EntityPlayer playerIn) {
    	
    	Set<String> tags = playerIn.getTags();
    	
    	if(tags.isEmpty()) {
    		return false;
    	}
    	
    	Iterator<String> tags2 = tags.iterator();
    	
    	while(tags2.hasNext()) {
    		String s = tags2.next();
    		if(!s.isEmpty() && s.contains("NetherSpawn=")) {
    			return true;
    		}
    	}
    	
    	return false;
    }
    
    public static BlockPos getNetherSpawn(EntityPlayer playerIn) {
    	Set<String> tags = playerIn.getTags();
    	
    	if(tags.isEmpty()) {
    		return null;
    	}
    	
    	Iterator<String> tags2 = tags.iterator();
    	
    	while(tags2.hasNext()) {
    		String s = tags2.next();
    		if(!s.isEmpty() && s.contains("NetherSpawn=")) {
    			try {
					return parseBlockPos(s);
				} catch (Exception e) {
					e.printStackTrace();
				}
    		}
    	}
    	
    	return null;
    }
    
    public static BlockPos parseBlockPos(String args) throws Exception
    {
    	String input = args.substring(12).toLowerCase();
    	String x = input.substring(1, input.indexOf("y"));
    	String y = input.substring(input.indexOf("y") + 1, input.indexOf("z"));
    	String z = input.substring(input.indexOf("z") + 1);
    	
        return new BlockPos(parseDouble(x, -30000000, 30000000), parseDouble(y, 0, 256), parseDouble(z, -30000000, 30000000));
    }
    
    public static double parseDouble(String input, int min, int max) throws Exception
    {
            double d0 = Double.parseDouble(input);

            if (min != 0 || max != 0)
            {
                if (d0 < (double)min)
                {
                    throw new Exception();
                }

                if (d0 > (double)max)
                {
                    throw new Exception();
                }
            }

            return d0;
    }
}
