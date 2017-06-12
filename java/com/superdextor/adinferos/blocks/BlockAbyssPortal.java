package com.superdextor.adinferos.blocks;

import java.util.Random;

import com.google.common.cache.LoadingCache;
import com.superdextor.adinferos.AdInferosSounds;
import com.superdextor.adinferos.AdInferosTab;
import com.superdextor.adinferos.config.NetherConfig;
import com.superdextor.adinferos.entity.monster.EntityPhantom;
import com.superdextor.adinferos.init.NetherBlocks;
import com.superdextor.adinferos.world.TeleporterAbyss;

import net.minecraft.block.Block;
import net.minecraft.block.BlockPortal;
import net.minecraft.block.SoundType;
import net.minecraft.block.material.MapColor;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.BlockWorldState;
import net.minecraft.block.state.IBlockState;
import net.minecraft.block.state.pattern.BlockPattern;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityList;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.init.Blocks;
import net.minecraft.init.MobEffects;
import net.minecraft.item.ItemMonsterPlacer;
import net.minecraft.network.play.server.SPacketSoundEffect;
import net.minecraft.potion.PotionEffect;
import net.minecraft.server.MinecraftServer;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumParticleTypes;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class BlockAbyssPortal extends BlockPortal
{
	public static EnumFacing teleportDirection = EnumFacing.NORTH;
	public static Vec3d lastPortalVec = new Vec3d(0, 0, 0);
	
    public BlockAbyssPortal()
    {
        this.setLightLevel(0.5F);
        this.setUnlocalizedName("abyss_portal");
        this.setCreativeTab(AdInferosTab.AI_OTHER);
        this.setSoundType(SoundType.GLASS);
    }
    
    public void updateTick(World worldIn, BlockPos pos, IBlockState state, Random rand)
    {
        if (worldIn.provider.isSurfaceWorld() && worldIn.getGameRules().getBoolean("doMobSpawning") && rand.nextInt(2000) < worldIn.getDifficulty().getDifficultyId())
        {
            int i = pos.getY();
            BlockPos blockpos;

            for (blockpos = pos; !worldIn.getBlockState(blockpos).isFullyOpaque() && blockpos.getY() > 0; blockpos = blockpos.down())
            {
                ;
            }

            if (i > 0 && !worldIn.getBlockState(blockpos.up()).isNormalCube())
            {
                Entity entity = ItemMonsterPlacer.spawnCreature(worldIn, EntityList.func_191306_a(EntityPhantom.class), (double)blockpos.getX() + 0.5D, (double)blockpos.getY() + 1.1D, (double)blockpos.getZ() + 0.5D);

                if (entity != null)
                {
                    entity.timeUntilPortal = entity.getPortalCooldown();
                }
            }
        }
    }

    public boolean trySpawnPortal(World worldIn, BlockPos pos)
    {
        BlockAbyssPortal.Size blockportal$size = new BlockAbyssPortal.Size(worldIn, pos, EnumFacing.Axis.X);

        if (blockportal$size.isValid() && blockportal$size.portalBlockCount == 0)
        {
            blockportal$size.placePortalBlocks();
            return true;
        }
        else
        {
        	BlockAbyssPortal.Size blockportal$size1 = new BlockAbyssPortal.Size(worldIn, pos, EnumFacing.Axis.Z);

            if (blockportal$size1.isValid() && blockportal$size1.portalBlockCount == 0)
            {
                blockportal$size1.placePortalBlocks();
                return true;
            }
            else
            {
                return false;
            }
        }
    }
    
    public void neighborChanged(IBlockState state, World worldIn, BlockPos pos, Block blockIn, BlockPos p_189540_5_)
    {
    	if(NetherConfig.abyssPortalAnywhere) {
    		return;
    	}
    	
        EnumFacing.Axis enumfacing$axis = (EnumFacing.Axis)state.getValue(AXIS);

        if (enumfacing$axis == EnumFacing.Axis.X)
        {
            BlockAbyssPortal.Size blockportal$size = new BlockAbyssPortal.Size(worldIn, pos, EnumFacing.Axis.X);

            if (!blockportal$size.isValid() || blockportal$size.portalBlockCount < blockportal$size.width * blockportal$size.height)
            {
                worldIn.setBlockState(pos, Blocks.AIR.getDefaultState());
            }
        }
        else if (enumfacing$axis == EnumFacing.Axis.Z)
        {
        	BlockAbyssPortal.Size blockportal$size1 = new BlockAbyssPortal.Size(worldIn, pos, EnumFacing.Axis.Z);

            if (!blockportal$size1.isValid() || blockportal$size1.portalBlockCount < blockportal$size1.width * blockportal$size1.height)
            {
                worldIn.setBlockState(pos, Blocks.AIR.getDefaultState());
            }
        }
    }
    
    public void onEntityCollidedWithBlock(World worldIn, BlockPos pos, IBlockState state, Entity entityIn)
    {
    	if(!NetherConfig.abyssPortalTravel) {
    		return;
    	}
    	
        if (!worldIn.isRemote && entityIn instanceof EntityPlayerMP && !entityIn.isRiding() && !entityIn.isBeingRidden() && entityIn.isNonBoss())
        {
        	this.setInPortal(pos, (EntityPlayerMP) entityIn);
        }
    }
    
    private void setInPortal(BlockPos pos, EntityPlayerMP entityIn) {
		NetherConfig.printDebugInfo("Attempting to send " + entityIn.getName() + " to/from the Abyss");
        MinecraftServer minecraftserver = entityIn.worldObj.getMinecraftServer();

        if (!entityIn.isRiding())
        {
            if (!entityIn.isPotionActive(MobEffects.NAUSEA))
            {
            	entityIn.addPotionEffect(new PotionEffect(MobEffects.NAUSEA, 130, 0, false, false));
                int j;

                if (entityIn.worldObj.provider.getDimensionType().getId() == NetherConfig.dimensionIdAbyss)
                {
                    j = 0;
                }
                else
                {
                    j = NetherConfig.dimensionIdAbyss;
                }

                BlockPattern.PatternHelper blockpattern$patternhelper = NetherBlocks.ABYSS_PORTAL.createPatternHelper(entityIn.worldObj, pos);
                double d0 = blockpattern$patternhelper.getForwards().getAxis() == EnumFacing.Axis.X ? (double)blockpattern$patternhelper.getFrontTopLeft().getZ() : (double)blockpattern$patternhelper.getFrontTopLeft().getX();
                double d1 = blockpattern$patternhelper.getForwards().getAxis() == EnumFacing.Axis.X ? entityIn.posZ : entityIn.posX;
                d1 = Math.abs(MathHelper.pct(d1 - (double)(blockpattern$patternhelper.getForwards().rotateY().getAxisDirection() == EnumFacing.AxisDirection.NEGATIVE ? 1 : 0), d0, d0 - (double)blockpattern$patternhelper.getWidth()));
                double d2 = MathHelper.pct(entityIn.posY - 1.0D, (double)blockpattern$patternhelper.getFrontTopLeft().getY(), (double)(blockpattern$patternhelper.getFrontTopLeft().getY() - blockpattern$patternhelper.getHeight()));
                this.lastPortalVec = new Vec3d(d1, d2, 0.0D);
                this.teleportDirection = blockpattern$patternhelper.getForwards();
                
                this.changeDimension(entityIn.worldObj, entityIn, j);
            }
        }
    }
    
    private EntityPlayerMP changeDimension(World worldIn, EntityPlayerMP playerIn, int dimensionIn)
    {
        if (!net.minecraftforge.common.ForgeHooks.onTravelToDimension(playerIn, dimensionIn)) return playerIn;

        MinecraftServer minecraftserver = playerIn.getServer();
        playerIn.mcServer.getPlayerList().transferPlayerToDimension(playerIn, dimensionIn, new TeleporterAbyss(minecraftserver.worldServerForDimension(dimensionIn)));
        playerIn.connection.sendPacket(new SPacketSoundEffect(AdInferosSounds.ABYSSPORTAL_TRAVEL, SoundCategory.PLAYERS, playerIn.posX, playerIn.posY, playerIn.posZ, 1.0F, 1.0F));
        return playerIn;
    }
    
    @SideOnly(Side.CLIENT)
    public void randomDisplayTick(IBlockState state, World worldIn, BlockPos pos, Random rand)
    {
        if (rand.nextInt(100) == 0)
        {
            worldIn.playSound((double)pos.getX() + 0.5D, (double)pos.getY() + 0.5D, (double)pos.getZ() + 0.5D, AdInferosSounds.ABYSSPORTAL_AMBIENT, SoundCategory.BLOCKS, 0.5F, rand.nextFloat() * 0.4F + 0.8F, false);
        }

        for (int i = 0; i < 4; ++i)
        {
            double d0 = (double)((float)pos.getX() + rand.nextFloat());
            double d1 = (double)((float)pos.getY() + rand.nextFloat());
            double d2 = (double)((float)pos.getZ() + rand.nextFloat());
            double d3 = ((double)rand.nextFloat() - 0.5D) * 0.5D;
            double d4 = ((double)rand.nextFloat() - 0.5D) * 0.5D;
            double d5 = ((double)rand.nextFloat() - 0.5D) * 0.5D;
            int j = rand.nextInt(2) * 2 - 1;

            if (worldIn.getBlockState(pos.west()).getBlock() != this && worldIn.getBlockState(pos.east()).getBlock() != this)
            {
                d0 = (double)pos.getX() + 0.5D + 0.25D * (double)j;
                d3 = (double)(rand.nextFloat() * 2.0F * (float)j);
            }
            else
            {
                d2 = (double)pos.getZ() + 0.5D + 0.25D * (double)j;
                d5 = (double)(rand.nextFloat() * 2.0F * (float)j);
            }

            worldIn.spawnParticle(EnumParticleTypes.SUSPENDED_DEPTH, d0, d1, d2, d3, d4, d5, new int[0]);
        }
    }
    
    public MapColor getMapColor(IBlockState state)
    {
        return MapColor.BLACK;
    }
    
    public BlockPattern.PatternHelper createPatternHelper(World worldIn, BlockPos p_181089_2_)
    {
        EnumFacing.Axis enumfacing$axis = EnumFacing.Axis.Z;
        BlockAbyssPortal.Size BlockAbyssPortal$size = new BlockAbyssPortal.Size(worldIn, p_181089_2_, EnumFacing.Axis.X);
        LoadingCache<BlockPos, BlockWorldState> loadingcache = BlockPattern.createLoadingCache(worldIn, true);

        if (!BlockAbyssPortal$size.isValid())
        {
            enumfacing$axis = EnumFacing.Axis.X;
            BlockAbyssPortal$size = new BlockAbyssPortal.Size(worldIn, p_181089_2_, EnumFacing.Axis.Z);
        }

        if (!BlockAbyssPortal$size.isValid())
        {
            return new BlockPattern.PatternHelper(p_181089_2_, EnumFacing.NORTH, EnumFacing.UP, loadingcache, 1, 1, 1);
        }
        else
        {
            int[] aint = new int[EnumFacing.AxisDirection.values().length];
            EnumFacing enumfacing = BlockAbyssPortal$size.rightDir.rotateYCCW();
            BlockPos blockpos = BlockAbyssPortal$size.bottomLeft.up(BlockAbyssPortal$size.getHeight() - 1);

            for (EnumFacing.AxisDirection enumfacing$axisdirection : EnumFacing.AxisDirection.values())
            {
                BlockPattern.PatternHelper blockpattern$patternhelper = new BlockPattern.PatternHelper(enumfacing.getAxisDirection() == enumfacing$axisdirection ? blockpos : blockpos.offset(BlockAbyssPortal$size.rightDir, BlockAbyssPortal$size.getWidth() - 1), EnumFacing.getFacingFromAxis(enumfacing$axisdirection, enumfacing$axis), EnumFacing.UP, loadingcache, BlockAbyssPortal$size.getWidth(), BlockAbyssPortal$size.getHeight(), 1);

                for (int i = 0; i < BlockAbyssPortal$size.getWidth(); ++i)
                {
                    for (int j = 0; j < BlockAbyssPortal$size.getHeight(); ++j)
                    {
                        BlockWorldState blockworldstate = blockpattern$patternhelper.translateOffset(i, j, 1);

                        if (blockworldstate.getBlockState() != null && blockworldstate.getBlockState().getMaterial() != Material.AIR)
                        {
                            ++aint[enumfacing$axisdirection.ordinal()];
                        }
                    }
                }
            }

            EnumFacing.AxisDirection enumfacing$axisdirection1 = EnumFacing.AxisDirection.POSITIVE;

            for (EnumFacing.AxisDirection enumfacing$axisdirection2 : EnumFacing.AxisDirection.values())
            {
                if (aint[enumfacing$axisdirection2.ordinal()] < aint[enumfacing$axisdirection1.ordinal()])
                {
                    enumfacing$axisdirection1 = enumfacing$axisdirection2;
                }
            }

            return new BlockPattern.PatternHelper(enumfacing.getAxisDirection() == enumfacing$axisdirection1 ? blockpos : blockpos.offset(BlockAbyssPortal$size.rightDir, BlockAbyssPortal$size.getWidth() - 1), EnumFacing.getFacingFromAxis(enumfacing$axisdirection1, enumfacing$axis), EnumFacing.UP, loadingcache, BlockAbyssPortal$size.getWidth(), BlockAbyssPortal$size.getHeight(), 1);
        }
    }
    
    public static class Size
    {
        private final World world;
        private final EnumFacing.Axis axis;
        private final EnumFacing rightDir;
        private final EnumFacing leftDir;
        private int portalBlockCount = 0;
        private BlockPos bottomLeft;
        private int height;
        private int width;

        public Size(World worldIn, BlockPos p_i45694_2_, EnumFacing.Axis p_i45694_3_)
        {
            this.world = worldIn;
            this.axis = p_i45694_3_;

            if (p_i45694_3_ == EnumFacing.Axis.X)
            {
                this.leftDir = EnumFacing.EAST;
                this.rightDir = EnumFacing.WEST;
            }
            else
            {
                this.leftDir = EnumFacing.NORTH;
                this.rightDir = EnumFacing.SOUTH;
            }

            for (BlockPos blockpos = p_i45694_2_; p_i45694_2_.getY() > blockpos.getY() - 21 && p_i45694_2_.getY() > 0 && this.isEmptyBlock(worldIn.getBlockState(p_i45694_2_.down())); p_i45694_2_ = p_i45694_2_.down())
            {
                ;
            }

            int i = this.getDistanceUntilEdge(p_i45694_2_, this.leftDir) - 1;

            if (i >= 0)
            {
                this.bottomLeft = p_i45694_2_.offset(this.leftDir, i);
                this.width = this.getDistanceUntilEdge(this.bottomLeft, this.rightDir);

                if (this.width < 2 || this.width > 21)
                {
                    this.bottomLeft = null;
                    this.width = 0;
                }
            }

            if (this.bottomLeft != null)
            {
                this.height = this.calculatePortalHeight();
            }
        }

        protected int getDistanceUntilEdge(BlockPos p_180120_1_, EnumFacing p_180120_2_)
        {
            int i;

            for (i = 0; i < 22; ++i)
            {
                BlockPos blockpos = p_180120_1_.offset(p_180120_2_, i);

                if (!this.isEmptyBlock(this.world.getBlockState(blockpos)) || this.world.getBlockState(blockpos.down()).getBlock() != NetherBlocks.DARK_BRICKS)
                {
                    break;
                }
            }

            Block block = this.world.getBlockState(p_180120_1_.offset(p_180120_2_, i)).getBlock();
            return block == NetherBlocks.DARK_BRICKS ? i : 0;
        }

        public int getHeight()
        {
            return this.height;
        }

        public int getWidth()
        {
            return this.width;
        }

        protected int calculatePortalHeight()
        {
            label24:

            for (this.height = 0; this.height < 21; ++this.height)
            {
                for (int i = 0; i < this.width; ++i)
                {
                    BlockPos blockpos = this.bottomLeft.offset(this.rightDir, i).up(this.height);
                    Block block = this.world.getBlockState(blockpos).getBlock();

                    if (!this.isEmptyBlock(this.world.getBlockState(blockpos)))
                    {
                        break label24;
                    }

                    if (block == NetherBlocks.ABYSS_PORTAL)
                    {
                        ++this.portalBlockCount;
                    }

                    if (i == 0)
                    {
                        block = this.world.getBlockState(blockpos.offset(this.leftDir)).getBlock();

                        if (block != NetherBlocks.DARK_BRICKS)
                        {
                            break label24;
                        }
                    }
                    else if (i == this.width - 1)
                    {
                        block = this.world.getBlockState(blockpos.offset(this.rightDir)).getBlock();

                        if (block != NetherBlocks.DARK_BRICKS)
                        {
                            break label24;
                        }
                    }
                }
            }

            for (int j = 0; j < this.width; ++j)
            {
                if (this.world.getBlockState(this.bottomLeft.offset(this.rightDir, j).up(this.height)).getBlock() != NetherBlocks.DARK_BRICKS)
                {
                    this.height = 0;
                    break;
                }
            }

            if (this.height <= 21 && this.height >= 3)
            {
                return this.height;
            }
            else
            {
                this.bottomLeft = null;
                this.width = 0;
                this.height = 0;
                return 0;
            }
        }

        protected boolean isEmptyBlock(IBlockState state)
        {
        	Block blockIn = state.getBlock();
            return blockIn.getMaterial(state) == Material.AIR || blockIn == NetherBlocks.DARK_FIRE || blockIn == NetherBlocks.ABYSS_PORTAL;
        }

        public boolean isValid()
        {
            return this.bottomLeft != null && this.width >= 2 && this.width <= 21 && this.height >= 3 && this.height <= 21;
        }

        public void placePortalBlocks()
        {
            for (int i = 0; i < this.width; ++i)
            {
                BlockPos blockpos = this.bottomLeft.offset(this.rightDir, i);

                for (int j = 0; j < this.height; ++j)
                {
                    this.world.setBlockState(blockpos.up(j), NetherBlocks.ABYSS_PORTAL.getDefaultState().withProperty(BlockPortal.AXIS, this.axis), 2);
                }
            }
        }
    }
    
}