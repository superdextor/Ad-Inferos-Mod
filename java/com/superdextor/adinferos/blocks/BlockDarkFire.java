package com.superdextor.adinferos.blocks;

import java.util.Map;
import java.util.Random;

import com.google.common.collect.Maps;
import com.superdextor.adinferos.NetherDamageSource;
import com.superdextor.adinferos.config.NetherConfig;
import com.superdextor.adinferos.entity.NetherMob;
import com.superdextor.adinferos.entity.monster.EntitySummoner;
import com.superdextor.adinferos.init.NetherBlocks;
import com.superdextor.adinferos.init.NetherItems;

import net.minecraft.block.Block;
import net.minecraft.block.BlockFire;
import net.minecraft.block.BlockTNT;
import net.minecraft.block.SoundType;
import net.minecraft.block.material.MapColor;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.init.Blocks;
import net.minecraft.init.MobEffects;
import net.minecraft.init.SoundEvents;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.potion.PotionEffect;
import net.minecraft.util.DamageSource;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumParticleTypes;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class BlockDarkFire extends BlockFire
{
    private final Map<Block, Integer> encouragements = Maps.<Block, Integer>newIdentityHashMap();
    private final Map<Block, Integer> flammabilities = Maps.<Block, Integer>newIdentityHashMap();
    
    public BlockDarkFire()
    {
    	this.setUnlocalizedName("dark_fire");
        this.setSoundType(SoundType.SAND);
    }
    
    public static void initDarkfire()
    {
        NetherBlocks.DARK_FIRE.setFireInfo(NetherBlocks.INFERNO_DOOR, 5, 20, false);
        NetherBlocks.DARK_FIRE.setFireInfo(NetherBlocks.MAGMA_DOOR, 5, 20, false);
        NetherBlocks.DARK_FIRE.setFireInfo(NetherBlocks.PHANTOM_DOOR, 5, 20, false);
        NetherBlocks.DARK_FIRE.setFireInfo(NetherBlocks.ASH_DOOR, 5, 20, false);
        NetherBlocks.DARK_FIRE.setFireInfo(NetherBlocks.PLANKS, 5, 20, false);
        NetherBlocks.DARK_FIRE.setFireInfo(NetherBlocks.DOUBLE_WOODEN_SLAB, 5, 20, false);
        NetherBlocks.DARK_FIRE.setFireInfo(NetherBlocks.WOODEN_SLAB, 5, 20, false);
        NetherBlocks.DARK_FIRE.setFireInfo(NetherBlocks.SOUL_TNT, 15, 100, true);
        NetherBlocks.DARK_FIRE.setFireInfo(NetherBlocks.LOG, 5, 5, false);
        NetherBlocks.DARK_FIRE.setFireInfo(NetherBlocks.LEAVES, 30, 60, false);
        NetherBlocks.DARK_FIRE.setFireInfo(NetherBlocks.INFERNO_STAIRS, 5, 20, false);
        NetherBlocks.DARK_FIRE.setFireInfo(NetherBlocks.MAGMA_STAIRS, 5, 20, false);
        NetherBlocks.DARK_FIRE.setFireInfo(NetherBlocks.PHANTOM_STAIRS, 5, 20, false);
        NetherBlocks.DARK_FIRE.setFireInfo(NetherBlocks.ASH_STAIRS, 5, 20, false);
        NetherBlocks.DARK_FIRE.setFireInfo(NetherBlocks.SAPLING, 60, 100, false);
        NetherBlocks.DARK_FIRE.setFireInfo(NetherBlocks.INFERNO_FENCE, 5, 20, false);
        NetherBlocks.DARK_FIRE.setFireInfo(NetherBlocks.MAGMA_FENCE, 5, 20, false);
        NetherBlocks.DARK_FIRE.setFireInfo(NetherBlocks.PHANTOM_FENCE, 5, 20, false);
        NetherBlocks.DARK_FIRE.setFireInfo(NetherBlocks.ASH_FENCE, 5, 20, false);
        NetherBlocks.DARK_FIRE.setFireInfo(NetherBlocks.INFERNO_FENCE_GATE, 5, 20, false);
        NetherBlocks.DARK_FIRE.setFireInfo(NetherBlocks.MAGMA_FENCE_GATE, 5, 20, false);
        NetherBlocks.DARK_FIRE.setFireInfo(NetherBlocks.PHANTOM_FENCE_GATE, 5, 20, false);
        NetherBlocks.DARK_FIRE.setFireInfo(NetherBlocks.ASH_FENCE_GATE, 5, 20, false);
        NetherBlocks.DARK_FIRE.setFireInfo(NetherBlocks.INFERNO_TRAPDOOR, 5, 20, false);
        NetherBlocks.DARK_FIRE.setFireInfo(NetherBlocks.MAGMA_TRAPDOOR, 5, 20, false);
        NetherBlocks.DARK_FIRE.setFireInfo(NetherBlocks.PHANTOM_TRAPDOOR, 5, 20, false);
        NetherBlocks.DARK_FIRE.setFireInfo(NetherBlocks.ASH_TRAPDOOR, 5, 20, false);
        NetherBlocks.DARK_FIRE.setFireInfo(NetherBlocks.INFERNO_TRAPDOOR_HIDDEN, 5, 20, false);
        NetherBlocks.DARK_FIRE.setFireInfo(NetherBlocks.MAGMA_TRAPDOOR_HIDDEN, 5, 20, false);
        NetherBlocks.DARK_FIRE.setFireInfo(NetherBlocks.PHANTOM_TRAPDOOR_HIDDEN, 5, 20, false);
        NetherBlocks.DARK_FIRE.setFireInfo(NetherBlocks.ASH_TRAPDOOR_HIDDEN, 5, 20, false);
        NetherBlocks.DARK_FIRE.setFireInfo(NetherBlocks.INFERNO_PRESSURE_PLATE, 60, 20, false);
        NetherBlocks.DARK_FIRE.setFireInfo(NetherBlocks.MAGMA_PRESSURE_PLATE, 60, 20, false);
        NetherBlocks.DARK_FIRE.setFireInfo(NetherBlocks.PHANTOM_PRESSURE_PLATE, 60, 20, false);
        NetherBlocks.DARK_FIRE.setFireInfo(NetherBlocks.ASH_PRESSURE_PLATE, 60, 20, false);
        NetherBlocks.DARK_FIRE.setFireInfo(NetherBlocks.CRAFTING_TABLE, 5, 20, false);
    }
    
    @Deprecated
    public void setFireInfo(Block blockIn, int encouragement, int flammability) {}
    
    public void setFireInfo(Block blockIn, int encouragement, int flammability, boolean clasicBurnable)
    {
        if (blockIn == Blocks.AIR) throw new IllegalArgumentException("Tried to set air on fire... This is bad.");
        if(clasicBurnable) {
        	super.setFireInfo(blockIn, encouragement, flammability);
        }
        this.encouragements.put(blockIn, Integer.valueOf(encouragement));
        this.flammabilities.put(blockIn, Integer.valueOf(flammability));
    }
    
    public void onEntityCollidedWithBlock(World worldIn, BlockPos pos, IBlockState state, Entity entity)
    {
    	if(!worldIn.isRemote) {
        	boolean flag = true;
    		
        	if(entity instanceof NetherMob) {
        		NetherMob nethermob = (NetherMob) entity;
        		if(nethermob.isDarkfireResistant())
        			flag = false;
        	}
        	
        	if(flag) {
    	    	if (entity instanceof EntityLivingBase) {
    	    	    ((EntityLivingBase) entity).addPotionEffect(new PotionEffect(MobEffects.WITHER, 60, 1));
    	    	    ((EntityLivingBase) entity).addPotionEffect(new PotionEffect(MobEffects.BLINDNESS, 40, 0));
    	    	}
    	    	
    	    	entity.attackEntityFrom(NetherDamageSource.DARK_FIRE, 1);
        	}
    	}
    }
    
    public boolean isCollidable()
    {
        return true;
    }
    
    @SideOnly(Side.CLIENT)
    public void randomDisplayTick(IBlockState worldIn, World pos, BlockPos state, Random rand) //TODO check to make sure this looks right
    {
        if (rand.nextInt(24) == 0)
        {
            pos.playSound((double)((float)state.getX() + 0.5F), (double)((float)state.getY() + 0.5F), (double)((float)state.getZ() + 0.5F), SoundEvents.BLOCK_FIRE_AMBIENT, SoundCategory.BLOCKS, 1.0F + rand.nextFloat(), rand.nextFloat() * 0.7F + 0.3F, false);
        }

        if (!pos.getBlockState(state.down()).isSideSolid(pos, state.down(), EnumFacing.UP) && !NetherBlocks.DARK_FIRE.canCatchFire(pos, state.down(), EnumFacing.UP))
        {
            if (NetherBlocks.DARK_FIRE.canCatchFire(pos, state.west(), EnumFacing.EAST))
            {
                for (int j = 0; j < 2; ++j)
                {
                    double d3 = (double)state.getX() + rand.nextDouble() * 0.10000000149011612D;
                    double d8 = (double)state.getY() + rand.nextDouble() + 0.5D;
                    double d13 = (double)state.getZ() + rand.nextDouble();
                    pos.spawnParticle(EnumParticleTypes.SUSPENDED_DEPTH, d3, d8, d13, 0.0D, 0.1D, 0.0D, new int[0]);
                }
            }

            if (NetherBlocks.DARK_FIRE.canCatchFire(pos, state.east(), EnumFacing.WEST))
            {
                for (int k = 0; k < 2; ++k)
                {
                    double d4 = (double)(state.getX() + 1) - rand.nextDouble() * 0.10000000149011612D;
                    double d9 = (double)state.getY() + rand.nextDouble() + 0.5D;
                    double d14 = (double)state.getZ() + rand.nextDouble();
                    pos.spawnParticle(EnumParticleTypes.SUSPENDED_DEPTH, d4, d9, d14, 0.0D, 0.1D, 0.0D, new int[0]);
                }
            }

            if (NetherBlocks.DARK_FIRE.canCatchFire(pos, state.north(), EnumFacing.SOUTH))
            {
                for (int l = 0; l < 2; ++l)
                {
                    double d5 = (double)state.getX() + rand.nextDouble();
                    double d10 = (double)state.getY() + rand.nextDouble() + 0.5D;
                    double d15 = (double)state.getZ() + rand.nextDouble() * 0.10000000149011612D;
                    pos.spawnParticle(EnumParticleTypes.SUSPENDED_DEPTH, d5, d10, d15, 0.0D, 0.1D, 0.0D, new int[0]);
                }
            }

            if (NetherBlocks.DARK_FIRE.canCatchFire(pos, state.south(), EnumFacing.NORTH))
            {
                for (int i1 = 0; i1 < 2; ++i1)
                {
                    double d6 = (double)state.getX() + rand.nextDouble();
                    double d11 = (double)state.getY() + rand.nextDouble() + 0.5D;
                    double d16 = (double)(state.getZ() + 1) - rand.nextDouble() * 0.10000000149011612D;
                    pos.spawnParticle(EnumParticleTypes.SUSPENDED_DEPTH, d6, d11, d16, 0.0D, 0.1D, 0.0D, new int[0]);
                }
            }

            if (NetherBlocks.DARK_FIRE.canCatchFire(pos, state.up(), EnumFacing.DOWN))
            {
                for (int j1 = 0; j1 < 2; ++j1)
                {
                    double d7 = (double)state.getX() + rand.nextDouble();
                    double d12 = (double)(state.getY() + 1) - rand.nextDouble() * 0.10000000149011612D + 0.5D;
                    double d17 = (double)state.getZ() + rand.nextDouble();
                    pos.spawnParticle(EnumParticleTypes.SUSPENDED_DEPTH, d7, d12, d17, 0.0D, 0.1D, 0.0D, new int[0]);
                }
            }
        }
        else
        {
            for (int i = 0; i < 3; ++i)
            {
                double d0 = (double)state.getX() + rand.nextDouble();
                double d1 = (double)state.getY() + rand.nextDouble() * 0.5D + 0.5D;
                double d2 = (double)state.getZ() + rand.nextDouble();
                pos.spawnParticle(EnumParticleTypes.SUSPENDED_DEPTH, d0, d1, d2, 0.0D, 0.1D, 0.0D, new int[0]);
            }
        }
    }
    
    public void onBlockAdded(World world, BlockPos pos, IBlockState state)
    {
        if (NetherConfig.abyssPortalBuild) {
            if ((world.provider.getDimension() == 0 || world.provider.getDimension() == NetherConfig.dimensionIdAbyss) && NetherBlocks.ABYSS_PORTAL.trySpawnPortal(world, pos))
            {
                if (!world.getBlockState(pos.down()).isFullBlock())
                {
                    world.setBlockToAir(pos);
                }
            }
        }
    	
    	if(NetherConfig.buildSummoner && world.provider.getDimension() == -1 || world.provider.getDimension() == NetherConfig.dimensionIdAbyss)
        if (world.getBlockState(pos.add(0, -1, 0)).getBlock() == NetherBlocks.WITHER_BLOCK && world.getBlockState(pos.add(1, -1, 0)).getBlock() == Blocks.GOLD_BLOCK && world.getBlockState(pos.add(-1, -1, 0)).getBlock() == Blocks.GOLD_BLOCK && world.getBlockState(pos.add(0, -1, 1)).getBlock() == Blocks.GOLD_BLOCK && world.getBlockState(pos.add(0, -1, -1)).getBlock() == Blocks.GOLD_BLOCK && world.getBlockState(pos.add(1, -1, 1)).getBlock() == Blocks.OBSIDIAN && world.getBlockState(pos.add(-1, -1, 1)).getBlock() == Blocks.OBSIDIAN && world.getBlockState(pos.add(-1, -1, -1)).getBlock() == Blocks.OBSIDIAN && world.getBlockState(pos.add(1, -1, -1)).getBlock() == Blocks.OBSIDIAN && world.getBlockState(pos.add(2, 0, 0)).getBlock() == Blocks.FIRE && world.getBlockState(pos.add(-2, 0, 0)).getBlock() == Blocks.FIRE && world.getBlockState(pos.add(0, 0, 2)).getBlock() == Blocks.FIRE && world.getBlockState(pos.add(0, 0, -2)).getBlock() == Blocks.FIRE && world.getBlockState(pos.add(2, 0, 1)).getBlock() == Blocks.FIRE && world.getBlockState(pos.add(2, 0, -1)).getBlock() == Blocks.FIRE && world.getBlockState(pos.add(-2, 0, 1)).getBlock() == Blocks.FIRE && world.getBlockState(pos.add(-2, 0, -1)).getBlock() == Blocks.FIRE && world.getBlockState(pos.add(1, 0, 2)).getBlock() == Blocks.FIRE && world.getBlockState(pos.add(-1, 0, 2)).getBlock() == Blocks.FIRE && world.getBlockState(pos.add(1, 0, -2)).getBlock() == Blocks.FIRE && world.getBlockState(pos.add(-1, 0, -2)).getBlock() == Blocks.FIRE)
        {
            if(!world.isRemote) {
            	EntitySummoner entity = new EntitySummoner(world);
            entity.setLocationAndAngles(pos.getX() + 0.5, pos.getY() + 3, pos.getZ() + 0.5, 0, 0.0F);
            world.spawnEntityInWorld(entity); 
            entity.playSound(SoundEvents.ENTITY_WITHER_SHOOT, 3.0F, 0.5F);
            }
            world.setBlockToAir(pos);
            world.setBlockState(pos, NetherBlocks.CHISELED_OBSIDIAN.getDefaultState());
            world.setBlockState(pos.add(0, 1, 0), NetherBlocks.CHISELED_OBSIDIAN.getDefaultState());
            world.setBlockState(pos.add(0, -1, 0), NetherBlocks.SMOOTH_NETHERRACK.getDefaultState());
            world.setBlockState(pos.add(1, -1, 0), NetherBlocks.SMOOTH_NETHERRACK.getDefaultState());
            world.setBlockState(pos.add(-1, -1, 0), NetherBlocks.SMOOTH_NETHERRACK.getDefaultState());
            world.setBlockState(pos.add(0, -1, 1), NetherBlocks.SMOOTH_NETHERRACK.getDefaultState());
            world.setBlockState(pos.add(0, -1, -1), NetherBlocks.SMOOTH_NETHERRACK.getDefaultState());
            world.setBlockState(pos.add(1, -1, 1), NetherBlocks.SMOOTH_NETHERRACK.getDefaultState());
            world.setBlockState(pos.add(-1, -1, 1), NetherBlocks.SMOOTH_NETHERRACK.getDefaultState());
            world.setBlockState(pos.add(-1, -1, -1), NetherBlocks.SMOOTH_NETHERRACK.getDefaultState());
            world.setBlockState(pos.add(1, -1, -1), NetherBlocks.SMOOTH_NETHERRACK.getDefaultState());
            world.setBlockState(pos.add(2, 0, 0), NetherBlocks.DARK_FIRE.getDefaultState());
            world.setBlockState(pos.add(-2, 0, 0), NetherBlocks.DARK_FIRE.getDefaultState());
            world.setBlockState(pos.add(0, 0, 2), NetherBlocks.DARK_FIRE.getDefaultState());
            world.setBlockState(pos.add(0, 0, -2), NetherBlocks.DARK_FIRE.getDefaultState());
            world.setBlockState(pos.add(2, 0, 1), NetherBlocks.DARK_FIRE.getDefaultState());
            world.setBlockState(pos.add(2, 0, -1), NetherBlocks.DARK_FIRE.getDefaultState());
            world.setBlockState(pos.add(-2, 0, 1), NetherBlocks.DARK_FIRE.getDefaultState());
            world.setBlockState(pos.add(-2, 0, -1), NetherBlocks.DARK_FIRE.getDefaultState());
            world.setBlockState(pos.add(1, 0, 2), NetherBlocks.DARK_FIRE.getDefaultState());
            world.setBlockState(pos.add(-1, 0, 2), NetherBlocks.DARK_FIRE.getDefaultState());
            world.setBlockState(pos.add(1, 0, -2), NetherBlocks.DARK_FIRE.getDefaultState());
            world.setBlockState(pos.add(-1, 0, -2), NetherBlocks.DARK_FIRE.getDefaultState());
        }
    }
    
    public ItemStack getItem(World worldIn, BlockPos pos, IBlockState state)
    {
        return new ItemStack(Item.getItemById(Item.getIdFromItem(NetherItems.WITHER_DUST)));
    }
    
    public MapColor getMapColor(IBlockState state)
    {
        return MapColor.BLACK;
    }
	
    public void updateTick(World worldIn, BlockPos pos, IBlockState state, Random rand)
    {
        if (worldIn.getGameRules().getBoolean("doFireTick"))
        {
            if (!this.canPlaceBlockAt(worldIn, pos))
            {
                worldIn.setBlockToAir(pos);
            }

            Block block = worldIn.getBlockState(pos.down()).getBlock();
            boolean flag = block.equals(NetherBlocks.DARKSTONE) || block.equals(NetherBlocks.DIMENSIONAL_ORE) || block.equals(NetherBlocks.WITHER_ORE) || block.equals(NetherBlocks.NETHERITE_ORE);

            int i = ((Integer)state.getValue(AGE)).intValue();

            if (!flag && worldIn.isRaining() && this.canDie(worldIn, pos) && rand.nextFloat() < 0.2F + (float)i * 0.03F)
            {
                worldIn.setBlockToAir(pos);
            }
            else
            {
                if (i < 15)
                {
                    state = state.withProperty(AGE, Integer.valueOf(i + rand.nextInt(3) / 2));
                    worldIn.setBlockState(pos, state, 4);
                }

                worldIn.scheduleUpdate(pos, this, this.tickRate(worldIn) + rand.nextInt(10));

                if (!flag)
                {
                    if (!this.canNeighborCatchFire(worldIn, pos))
                    {
                        if (!worldIn.getBlockState(pos.down()).isSideSolid(worldIn, pos.down(), EnumFacing.UP) || i > 3)
                        {
                            worldIn.setBlockToAir(pos);
                        }

                        return;
                    }

                    if (!this.canCatchFire(worldIn, pos.down(), EnumFacing.UP) && i == 15 && rand.nextInt(4) == 0)
                    {
                        worldIn.setBlockToAir(pos);
                        return;
                    }
                }

                boolean flag1 = worldIn.isBlockinHighHumidity(pos);
                int j = 0;

                if (flag1)
                {
                    j = -50;
                }

                this.tryCatchFire(worldIn, pos.east(), 300 + j, rand, i, EnumFacing.WEST);
                this.tryCatchFire(worldIn, pos.west(), 300 + j, rand, i, EnumFacing.EAST);
                this.tryCatchFire(worldIn, pos.down(), 250 + j, rand, i, EnumFacing.UP);
                this.tryCatchFire(worldIn, pos.up(), 250 + j, rand, i, EnumFacing.DOWN);
                this.tryCatchFire(worldIn, pos.north(), 300 + j, rand, i, EnumFacing.SOUTH);
                this.tryCatchFire(worldIn, pos.south(), 300 + j, rand, i, EnumFacing.NORTH);

                for (int k = -1; k <= 1; ++k)
                {
                    for (int l = -1; l <= 1; ++l)
                    {
                        for (int i1 = -1; i1 <= 4; ++i1)
                        {
                            if (k != 0 || i1 != 0 || l != 0)
                            {
                                int j1 = 100;

                                if (i1 > 1)
                                {
                                    j1 += (i1 - 1) * 100;
                                }

                                BlockPos blockpos = pos.add(k, i1, l);
                                int k1 = this.getNeighborEncouragement(worldIn, blockpos);

                                if (k1 > 0)
                                {
                                    int l1 = (k1 + 40 + worldIn.getDifficulty().getDifficultyId() * 7) / (i + 30);

                                    if (flag1)
                                    {
                                        l1 /= 2;
                                    }

                                    if (l1 > 0 && rand.nextInt(j1) <= l1 && (!worldIn.isRaining() || !this.canDie(worldIn, blockpos)))
                                    {
                                        int i2 = i + rand.nextInt(5) / 4;

                                        if (i2 > 15)
                                        {
                                            i2 = 15;
                                        }

                                        worldIn.setBlockState(blockpos, state.withProperty(AGE, Integer.valueOf(i2)), 3);
                                    }
                                }
                            }
                        }
                    }
                }
            }
        }
    }
    
    private void tryCatchFire(World worldIn, BlockPos pos, int chance, Random random, int age, EnumFacing face)
    {
        int i = this.getFlammability(worldIn, worldIn.getBlockState(pos), pos, face);

        if (random.nextInt(chance) < i)
        {
            IBlockState iblockstate = worldIn.getBlockState(pos);

            if (random.nextInt(age + 10) < 5 && !worldIn.isRainingAt(pos))
            {
                int j = age + random.nextInt(5) / 4;

                if (j > 15)
                {
                    j = 15;
                }

                worldIn.setBlockState(pos, this.getDefaultState().withProperty(AGE, Integer.valueOf(j)), 3);
            }
            else
            {
                worldIn.setBlockToAir(pos);
            }

            if (iblockstate.getBlock() == Blocks.TNT)
            {
                Blocks.TNT.onBlockDestroyedByPlayer(worldIn, pos, iblockstate.withProperty(BlockTNT.EXPLODE, Boolean.valueOf(true)));
            }
        }
    }

    private boolean canNeighborCatchFire(World worldIn, BlockPos pos)
    {
        for (EnumFacing enumfacing : EnumFacing.values())
        {
            if (this.canCatchFire(worldIn, pos.offset(enumfacing), enumfacing.getOpposite()))
            {
                return true;
            }
        }

        return false;
    }

    private int getNeighborEncouragement(World worldIn, BlockPos pos)
    {
        if (!worldIn.isAirBlock(pos))
        {
            return 0;
        }
        else
        {
            int i = 0;

            for (EnumFacing enumfacing : EnumFacing.values())
            {
                i = Math.max(this.getFlammability(worldIn, worldIn.getBlockState(pos), pos.offset(enumfacing), enumfacing.getOpposite()), i);
            }

            return i;
        }
    }
    
    public boolean canCatchFire(IBlockAccess world, BlockPos pos, EnumFacing face)
    {
        return this.getFlammability(world, world.getBlockState(pos), pos, face) > 0;
    }
    
    public boolean canPlaceBlockAt(World worldIn, BlockPos pos)
    {
        return worldIn.getBlockState(pos.down()).isFullyOpaque() || this.canNeighborCatchFire(worldIn, pos);
    }

    public void neighborChanged(IBlockState state, World worldIn, BlockPos pos, Block blockIn, BlockPos p_189540_5_)
    {
        if (!worldIn.getBlockState(pos.down()).isFullyOpaque() && !this.canNeighborCatchFire(worldIn, pos))
        {
        	worldIn.setBlockToAir(pos);
        }
    }
    
    public int tickRate(World worldIn)
    {
        return 15;
    }
    
    public int getFlammability(IBlockAccess world, IBlockState state, BlockPos pos, EnumFacing face)
    {
        Integer integer = (Integer)this.flammabilities.get(state.getBlock());
        return integer == null ? state.getBlock().getFlammability(world, pos, face) : integer.intValue();
    }

    public int getEncouragement(IBlockAccess world, IBlockState state, BlockPos pos, EnumFacing face)
    {
        Integer integer = (Integer)this.encouragements.get(state.getBlock());
        return integer == null ? state.getBlock().getFlammability(world, pos, face) : integer.intValue();
    }
    
    public int getFlammability(Block blockIn)
    {
        Integer integer = (Integer)this.flammabilities.get(blockIn);
        return integer == null ? 0 : integer.intValue();
    }

    @Deprecated // Use Block.getFlammability
    public int getEncouragement(Block blockIn)
    {
        Integer integer = (Integer)this.encouragements.get(blockIn);
        return integer == null ? 0 : integer.intValue();
    }
    
    public boolean isPassable(IBlockAccess worldIn, BlockPos pos) {
    	return true;
    }
}
