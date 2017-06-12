package com.superdextor.adinferos.world;

import java.util.Random;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.superdextor.adinferos.blocks.BlockNetherSpawn;
import com.superdextor.adinferos.init.NetherBlocks;
import com.superdextor.adinferos.init.NetherItems;
import com.superdextor.thinkbigcore.worldgen.WorldUtilities;

import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.init.Enchantments;
import net.minecraft.item.ItemStack;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.Teleporter;
import net.minecraft.world.WorldServer;

public class TeleporterNS extends Teleporter
{
	private static Logger logger = LogManager.getLogger("Ad Inferos (teleporter)");
    private final WorldServer worldServerInstance;
    private final Random random;
    
    public TeleporterNS(WorldServer worldIn)
    {
    	super(worldIn);
    	this.worldServerInstance = worldIn;
        this.random = new Random(worldIn.getSeed());
    }

    public void placeInPortal(Entity entityIn, float rotationYaw)
    {
    	if(!(entityIn instanceof EntityPlayer)) {
    		return;
    	}
    	
    	EntityPlayer player = (EntityPlayer) entityIn;
    	BlockPos entityPos = entityIn.getPosition();
    	BlockPos pos = new BlockPos((entityPos.getX() / 16) * 16 + 8.5D, entityPos.getY(), (entityPos.getY() / 16) * 16 + 8.5D);
    	boolean spawnBlock = false;
    	
    	if(BlockNetherSpawn.hasNetherSpawn(player)) {
    		BlockPos pos2 = BlockNetherSpawn.getNetherSpawn(player);
    		if(pos2 != null) {
        		pos = pos2;
        		spawnBlock = true;
    		}
    	}
    	
    	//MOVES OUT OF BLOCK
    	if(!spawnBlock) {
        	if(this.worldServerInstance.isBlockFullCube(pos) || this.worldServerInstance.isBlockFullCube(pos.up())) {
        		int y = 110;
        		boolean stat = true;
        		
        		for(; this.worldServerInstance.isBlockFullCube(new BlockPos(pos.getX(), y, pos.getZ())) || this.worldServerInstance.isBlockFullCube(new BlockPos(pos.getX(), y + 1, pos.getZ())); y--) {
        			if(y < 10) {
        				stat = false;
        				break;
        			}
        		}
        		
        		if(stat) {
            		pos = new BlockPos(pos.getX(), y, pos.getZ());
        			entityIn.setLocationAndAngles(pos.getX(), pos.getY(), pos.getZ(), entityIn.rotationYaw, entityIn.rotationPitch);
        		}
        	}
        	
        	//MOVES TO GROUND
        	if(!this.worldServerInstance.isBlockFullCube(pos.down())) {
        		int y = pos.getY();
        		boolean stat = true;
        		
        		for(; !this.worldServerInstance.isBlockFullCube(new BlockPos(pos.getX(), y - 1, pos.getZ())); y--) {
        			if(y < 10) {
        				stat = false;
        				break;
        			}
        		}
        		
        		if(stat) {
            		pos = new BlockPos(pos.getX(), y, pos.getZ());
        			entityIn.setLocationAndAngles(pos.getX(), pos.getY(), pos.getZ(), entityIn.rotationYaw, entityIn.rotationPitch);
        		}
        	}
        	
        	//MOVES OUT OF ACID/LAVA/WATER
        	if(this.worldServerInstance.getBlockState(pos).getMaterial().isLiquid() || this.worldServerInstance.getBlockState(pos.up()).getMaterial().isLiquid()) {
        		int y = pos.getY();
        		boolean stat = true;
        		for(; this.worldServerInstance.getBlockState(new BlockPos(pos.getX(), y, pos.getZ())).getMaterial().isLiquid() || this.worldServerInstance.getBlockState(new BlockPos(pos.getX(), y + 1, pos.getZ())).getMaterial().isLiquid(); y++) {
        			if(y > 110) {
        				stat = false;
        				break;
        			}
        			
        			if(this.worldServerInstance.isAirBlock(new BlockPos(pos.getX(), y + 2, pos.getZ())) && this.worldServerInstance.isAirBlock(new BlockPos(pos.getX(), y + 3, pos.getZ()))) {
        				y += 2;
        				break;
        			}
        		}
        		
        		if(stat) {
            		pos = new BlockPos(pos.getX(), y, pos.getZ());
        			entityIn.setLocationAndAngles(pos.getX(), pos.getY(), pos.getZ(), entityIn.rotationYaw, entityIn.rotationPitch);
        		}
        	}
        	
        	if(this.worldServerInstance.isBlockFullCube(pos) || this.worldServerInstance.isBlockFullCube(pos.up())) {
            	ItemStack stack = new ItemStack(NetherItems.QUARTZ_PICKAXE);
            	stack.addEnchantment(Enchantments.UNBREAKING, 2);
        		player.inventory.addItemStackToInventory(stack);
        		WorldUtilities.fill(this.worldServerInstance, pos.add(-2, 0, -2), Blocks.AIR, 5, 3, 5);
        	}
        	
        	if(!this.worldServerInstance.isBlockFullCube(pos.down())) {
        		WorldUtilities.fill(this.worldServerInstance, pos.add(-2, -1, -2), NetherBlocks.SMOOTH_OBSIDIAN, 5, 1, 5);
        	}
    	}
    	
		entityIn.setLocationAndAngles(pos.getX(), pos.getY(), pos.getZ(), entityIn.rotationYaw, entityIn.rotationPitch);
        entityIn.motionX = entityIn.motionY = entityIn.motionZ = 0.0D;
    }

    public boolean placeInExistingPortal(Entity entityIn, float rotationYaw)
    {
        return true;
    }

    public boolean makePortal(Entity entityIn)
    {
    	return true;
    }

    public void removeStalePortalLocations(long worldTime)
    {
    	
    }
}