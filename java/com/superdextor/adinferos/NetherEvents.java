package com.superdextor.adinferos;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.UUID;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.google.common.base.Predicate;
import com.superdextor.adinferos.blocks.BlockAcid;
import com.superdextor.adinferos.blocks.BlockNetherSpawn;
import com.superdextor.adinferos.blocks.BlockSpawner;
import com.superdextor.adinferos.config.NetherConfig;
import com.superdextor.adinferos.entity.NetherMob;
import com.superdextor.adinferos.entity.monster.EntityGhost;
import com.superdextor.adinferos.entity.monster.EntityObsidianSheepman;
import com.superdextor.adinferos.entity.monster.EntityObsidianSheepman.ObsidianSheepmenTribe;
import com.superdextor.adinferos.entity.monster.EntityReaper;
import com.superdextor.adinferos.init.NetherBlocks;
import com.superdextor.adinferos.init.NetherItems;
import com.superdextor.adinferos.items.ItemAmulet;
import com.superdextor.adinferos.items.ItemBloodBucket;
import com.superdextor.adinferos.items.ItemNetheriteArmor;
import com.superdextor.adinferos.items.ItemObsidianArmor;
import com.superdextor.adinferos.items.ItemTribeHeadband;
import com.superdextor.adinferos.items.ItemWitherArmor;
import com.superdextor.adinferos.network.CMessageUpdateDarkNether;
import com.superdextor.adinferos.network.CMessageUpdateNetherSurvival;
import com.superdextor.adinferos.world.ChunkProviderNetherSurvival;
import com.superdextor.adinferos.world.TeleporterNS;
import com.superdextor.thinkbigcore.ThinkBigCore;
import com.superdextor.thinkbigcore.helpers.InventoryHelper;
import com.superdextor.thinkbigcore.network.TBCNetwork;

import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.ai.attributes.AttributeModifier;
import net.minecraft.entity.ai.attributes.IAttributeInstance;
import net.minecraft.entity.monster.AbstractSkeleton;
import net.minecraft.entity.monster.EntityBlaze;
import net.minecraft.entity.monster.EntityGhast;
import net.minecraft.entity.monster.EntityMagmaCube;
import net.minecraft.entity.monster.EntityPigZombie;
import net.minecraft.entity.monster.EntityWitherSkeleton;
import net.minecraft.entity.monster.EntityZombie;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.init.Enchantments;
import net.minecraft.init.Items;
import net.minecraft.init.MobEffects;
import net.minecraft.init.SoundEvents;
import net.minecraft.inventory.EntityEquipmentSlot;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.ItemWritableBook;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import net.minecraft.nbt.NBTTagString;
import net.minecraft.network.play.server.SPacketSoundEffect;
import net.minecraft.potion.PotionEffect;
import net.minecraft.server.MinecraftServer;
import net.minecraft.stats.StatList;
import net.minecraft.stats.StatisticsManagerServer;
import net.minecraft.util.DamageSource;
import net.minecraft.util.EnumHand;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.text.TextComponentString;
import net.minecraft.util.text.TextFormatting;
import net.minecraft.world.World;
import net.minecraft.world.gen.ChunkProviderServer;
import net.minecraftforge.client.event.EntityViewRenderEvent;
import net.minecraftforge.event.entity.living.LivingDeathEvent;
import net.minecraftforge.event.entity.living.LivingHurtEvent;
import net.minecraftforge.event.world.WorldEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.PlayerEvent;
import net.minecraftforge.fml.common.gameevent.TickEvent;
import net.minecraftforge.fml.common.gameevent.TickEvent.Phase;
import net.minecraftforge.fml.common.gameevent.TickEvent.PlayerTickEvent;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class NetherEvents {
	
	public NetherEvents() {
		NetherConfig.printDebugInfo("Registered Event Hooks");
	}
	
	private static Logger logger = LogManager.getLogger("Ad Inferos [events]");
	private static final UUID customMovementUUID = UUID.fromString("219afd60-fde4-11e9-a4ac-0613237c5a34");
	
	@SubscribeEvent
	public void onRespawn(PlayerEvent.PlayerRespawnEvent e) {
		World world = e.player.worldObj;
		if(!world.isRemote && e.player != null) {
			EntityPlayerMP player = (EntityPlayerMP) e.player;
			StatisticsManagerServer file = player.getStatFile();
			BlockPos pos = player.getBedLocation();
			boolean flag = pos == null ? true : player.getBedSpawnLocation(world, pos, false) == null;
			if(flag && (AdInferosCore.proxy.isNetherSurvival() || BlockNetherSpawn.hasNetherSpawn(player))) {
				if(AdInferosCore.proxy.isNetherSurvival()) {
					this.sendItems(world, player, file.readStat(StatList.DEATHS));
				}
				this.sendToHell(world, player, AdInferosCore.proxy.isNetherSurvival());
			}
		}
	}
	
	@SubscribeEvent
	public void onLogIn(PlayerEvent.PlayerLoggedInEvent e) {
		World world = e.player.worldObj;
		this.updateIsNetherSurvival(world, e.player);
		
		if(!world.isRemote && AdInferosCore.proxy.isNetherSurvival()) {
			EntityPlayerMP player = (EntityPlayerMP) e.player;
			StatisticsManagerServer file = player.getStatFile();
			
			if(!file.hasAchievementUnlocked(AdInferosAchievements.lifeInHell)) {
				player.addStat(AdInferosAchievements.lifeInHell);
				this.sendItems(world, player, 0);
				this.sendToHell(world, player, true);
				player.setSpawnChunk(player.getPosition(), true, -1);
			}
		}
	}
	
	private void sendItems(World worldIn, EntityPlayerMP playerIn, int deathCount) {
		playerIn.addPotionEffect(new PotionEffect(MobEffects.BLINDNESS, 90, 0, true, false));
		
		if(deathCount == 0) {
	        playerIn.addPotionEffect(new PotionEffect(MobEffects.LUCK, 12000, 0));
		}
		else {
	        playerIn.addPotionEffect(new PotionEffect(MobEffects.LUCK, 2400, 0));
		}
		
		if(deathCount < 2) {
			char s = '"';
			NBTTagCompound compound = new NBTTagCompound();
			NBTTagList bookPages = new NBTTagList();
			if(deathCount == 0) {
				compound.setString("title", "Welcome " + playerIn.getName());
				bookPages.appendTag(new NBTTagString(s + "Welcome to your new Home. I cleaned the place up for you. Hope you like it. Oh yes we've already told the locals about your arrival, they'll come to great you soon.. And don't worry about escaping, since you live here, if you die again you'll just spawn back with us.." + s));
				bookPages.appendTag(new NBTTagString(s + "Oh, one more thing, as a result of being an resident here, your DNA has undergone some minor changes... \nHurt by Water/Rain \nGrowth of Horns \nMore Strength \nImmunity to Fire/Lava \nand Luck. Most of the locals will not target you if your 'lucky'" + s));
			}
			else {
				compound.setString("title", "Welcome back " + playerIn.getName());
				bookPages.appendTag(new NBTTagString(s + "Welcome back. I see you've met the locals, Yes troubled fellows they are. Don't worry I'm sure you'll get used to em' soon enough.." + s));
			}
			compound.setTag("pages", bookPages);
			compound.setString("author", "Unknown");
			ItemStack book = new ItemStack(Items.WRITTEN_BOOK);
	        ItemWritableBook.isNBTValid(compound);
			book.setTagCompound(compound);
			playerIn.inventory.addItemStackToInventory(book);
		}
	}
	
    private EntityPlayerMP sendToHell(World worldIn, EntityPlayerMP playerIn, boolean morphDNA)
    {
    	if(morphDNA) {
            playerIn.addPotionEffect(new PotionEffect(MobEffects.FIRE_RESISTANCE, Integer.MAX_VALUE, 1, true, true));
            playerIn.addPotionEffect(new PotionEffect(MobEffects.ABSORPTION, 3600, 0));
            playerIn.addPotionEffect(new PotionEffect(MobEffects.REGENERATION, 600, 1));
            playerIn.getAttributeMap().getAttributeInstance(SharedMonsterAttributes.ATTACK_DAMAGE).setBaseValue(2.5D);
    	}
        
        if(playerIn.dimension != -1) {
            MinecraftServer minecraftserver = playerIn.getServer();
            playerIn.mcServer.getPlayerList().transferPlayerToDimension(playerIn, -1, new TeleporterNS(minecraftserver.worldServerForDimension(-1)));
            playerIn.connection.sendPacket(new SPacketSoundEffect(AdInferosSounds.ENTITY_GHOST_IDLE, SoundCategory.PLAYERS, playerIn.posX, playerIn.posY, playerIn.posZ, 1.0F, 1.0F));
        }
        return playerIn;
    }
	
	@SubscribeEvent
	public void onLoadWorld(WorldEvent.Load e) {
		World world = e.getWorld();
		
		if(world.provider.getDimension() != 0) {
			return;
		}
		
		if(!world.isRemote && world.getChunkProvider() instanceof ChunkProviderServer) {
			ChunkProviderServer providerServer = (ChunkProviderServer) world.getChunkProvider();
			if(providerServer.chunkGenerator instanceof ChunkProviderNetherSurvival) {
				AdInferosCore.proxy.setNetherSurvival(true);
			}
			else {
				AdInferosCore.proxy.setNetherSurvival(false);
			}
		}
	}
	
	@SubscribeEvent
	public void onWorldTick(TickEvent.WorldTickEvent e) {
		
		World world = e.world;
		
		if(world.provider.getDimension() == -1 && e.phase == Phase.START && AdInferosCore.proxy.isNetherSurvival()) {
			
			
			if(world.getGameRules().getBoolean("doDaylightCycle")) {
				world.setWorldTime(world.getWorldTime() + 1);
			}
			
	        int j = this.calculateSkylightSubtracted(world, 1.0F);

	        if (j != world.getSkylightSubtracted())
	        {
	        	world.setSkylightSubtracted(j);
	        }
	        
	        if(AdInferosCore.proxy.isDarkNether() == world.isDaytime()) {
	        	AdInferosCore.proxy.setDarkNether(!world.isDaytime());
	        	TBCNetwork.sendToDimension(new CMessageUpdateDarkNether(!world.isDaytime()), -1);
	        	
	        	
	        	List list = world.getPlayers(EntityPlayerMP.class, new Predicate<EntityPlayerMP>() {
	        		public boolean apply(EntityPlayerMP input) {
	        			return true;
	        		}
	        	});
	        	
	            for (int i = 0; i < list.size(); ++i)
	            {
	            	EntityPlayerMP entityplayer = (EntityPlayerMP)list.get(i);
	            	if(world.isDaytime()) {
	            		entityplayer.addChatComponentMessage(new TextComponentString(TextFormatting.DARK_GREEN + "Light returns.."), true);
	            		entityplayer.playSound(SoundEvents.BLOCK_PORTAL_TRIGGER, 1.0F, 1.0F);
	            	}
	            	else {
	            		entityplayer.addChatComponentMessage(new TextComponentString(TextFormatting.DARK_PURPLE + "Darkness falls.."), true);
	            		entityplayer.playSound(AdInferosSounds.ABYSSPORTAL_TRIGGER, 1.0F, 1.0F);
	            	}
	            }
	        }
		}
	}
	
    private int calculateSkylightSubtracted(World world, float partialTicks)
    {
        float f = this.getSunBrightnessFactor(world, partialTicks);
        f = 1 - f;
        return (int)(f * 11);
    }
    
    private float getSunBrightnessFactor(World world, float partialTicks)
    {
        float f = this.calculateCelestialAngle(world.getWorldInfo().getWorldTime(), partialTicks);;
        float f1 = 1.0F - (MathHelper.cos(f * ((float)Math.PI * 2F)) * 2.0F + 0.5F);
        f1 = MathHelper.clamp_float(f1, 0.0F, 1.0F);
        f1 = 1.0F - f1;
        f1 = (float)((double)f1 * (1.0D - (double)(world.getRainStrength(partialTicks) * 5.0F) / 16.0D));
        f1 = (float)((double)f1 * (1.0D - (double)(world.getThunderStrength(partialTicks) * 5.0F) / 16.0D));
        return f1;
    }
    
    private float calculateCelestialAngle(long worldTime, float partialTicks)
    {
        int i = (int)(worldTime % 24000L);
        float f = ((float)i + partialTicks) / 24000.0F - 0.25F;

        if (f < 0.0F)
        {
            ++f;
        }

        if (f > 1.0F)
        {
            --f;
        }

        float f1 = 1.0F - (float)((Math.cos((double)f * Math.PI) + 1.0D) / 2.0D);
        f = f + (f1 - f) / 3.0F;
        return f;
    }
	
	private void updateIsNetherSurvival(World world, EntityPlayer player) {
		
		if(!world.isRemote) {
			if(AdInferosCore.proxy.isNetherSurvival()) {
				TBCNetwork.sendTo(new CMessageUpdateNetherSurvival(true), (EntityPlayerMP) player);
			}
			else {
				TBCNetwork.sendTo(new CMessageUpdateNetherSurvival(false), (EntityPlayerMP) player);
			}
		}
	}
	
	@SubscribeEvent
	public void onChangedDimension(PlayerEvent.PlayerChangedDimensionEvent e) {
		int i = e.toDim;
		
		if(i == -1) {
			e.player.addStat(AdInferosAchievements.welcome);
		}else if(i == NetherConfig.dimensionIdAbyss) {
			e.player.addStat(AdInferosAchievements.abyss);
		}
	}
	
	@SubscribeEvent
	public void onItemPickup(PlayerEvent.ItemPickupEvent e) {
		
		Item item = e.pickedUp.getEntityItem().getItem();
		
		if(item.equals(NetherItems.WITHER_DUST)) {
			e.player.addStat(AdInferosAchievements.wither);
		}
		
		if(item.equals(NetherItems.SCYTHE)) {
			e.player.addStat(AdInferosAchievements.scythe);
		}
		
		if(item.equals(Items.QUARTZ) || item.equals(NetherItems.QUARTZ_CHUNK)) {
			e.player.addStat(AdInferosAchievements.quartz);
		}
		
		if(item.equals(NetherItems.NETHERITE_INGOT) || item.equals(NetherItems.NETHERITE_NUGGET)) {
			e.player.addStat(AdInferosAchievements.netherite);
		}
	}
	
	@SubscribeEvent
	public void onCraftItem(PlayerEvent.ItemCraftedEvent e) {
		
		Item item = e.crafting.getItem();
		
		if(item.equals(NetherItems.QUARTZ_PICKAXE) || item.equals(NetherItems.GLOWSTONE_PICKAXE)
		|| item.equals(NetherItems.OBSIDIAN_PICKAXE) || item.equals(NetherItems.WITHER_PICKAXE) || item.equals(NetherItems.NETHERITE_PICKAXE)) {
			e.player.addStat(AdInferosAchievements.hellProspector);
		}
		
		if(item == Item.getItemFromBlock(NetherBlocks.ALTAR)) {
			e.player.addStat(AdInferosAchievements.hellAltar);
		}
	}

	@SubscribeEvent
    public void onDamage(LivingHurtEvent event) {
		
    	if(event.getEntity() == null || event.getEntity().worldObj.isRemote) {
    		return;
    	}
		
    	EntityLivingBase entitylivingbase = event.getEntityLiving();
    	DamageSource source = event.getSource();
    	Entity attacker = source.getEntity();
    	
    	if(attacker instanceof EntityPlayer) {
    		EntityPlayer attackerPlayer = (EntityPlayer) attacker;
    		
			ItemStack stack = attackerPlayer.getItemStackFromSlot(EntityEquipmentSlot.HEAD);
			if(stack != null && stack.getItem() instanceof ItemTribeHeadband) {
				ObsidianSheepmenTribe tribe = ItemTribeHeadband.getTribe(stack);
				
				if(tribe != null) {
					tribe.blackListEntity(entitylivingbase);
				}
			}
    	}
    	
    	if(entitylivingbase instanceof EntityPlayer) {
    		EntityPlayer entityPlayer = (EntityPlayer) entitylivingbase;
    		EntityEquipmentSlot[] slots = {EntityEquipmentSlot.HEAD, EntityEquipmentSlot.CHEST, EntityEquipmentSlot.LEGS, EntityEquipmentSlot.FEET};
			ArrayList<ItemStack> stacks;
    		
    		if(event.isCancelable() && source == DamageSource.wither) {
        		boolean flag = false;
    			stacks = InventoryHelper.getItems(entityPlayer.inventory, NetherItems.AMULET, 1);
        		
    			for(int k = 0; k < 4; k++) {
    				if(entityPlayer.getItemStackFromSlot(slots[k]) != null && entityPlayer.getItemStackFromSlot(slots[k]).getItem() instanceof ItemWitherArmor) {
    					flag = true;
    					break;
    				}
    				
    				if(!flag && NetherConfig.amuletEffects) {
    					if(!stacks.isEmpty()) {
    						for(ItemStack stackIn : stacks) {
    							if(ItemAmulet.isActive(stackIn)) {
    								flag = true;
    								break;
    							}
    						}
    					}
    				}
    			}
        		
        		if(flag)
        		{
        			event.setCanceled(true);
        		}
    		}
    		
    		if(event.isCancelable() && source.isFireDamage()) {
    			boolean flag = false;
    			
    			stacks = InventoryHelper.getItems(entityPlayer.inventory, NetherItems.AMULET, 0);
    			
    			if(!stacks.isEmpty()) {
    				for(ItemStack stackIn : stacks) {
    					if(ItemAmulet.isActive(stackIn)) {
    						event.setCanceled(true);
    						break;
    					}
    				}
    			}
    			
        		if(flag)
        		{
        			event.setCanceled(true);
        		}
    		}
			
			ItemStack stack = entityPlayer.getItemStackFromSlot(EntityEquipmentSlot.HEAD);
			if(stack != null && stack.getItem() instanceof ItemTribeHeadband && attacker instanceof EntityLivingBase && !(attacker instanceof EntityPlayer)) {
				ObsidianSheepmenTribe tribe = ItemTribeHeadband.getTribe(stack);
				
				if(tribe != null) {
					tribe.blackListEntity((EntityLivingBase) attacker);
				}
			}
    	}
    }
    
    @SubscribeEvent
    public void onKillEntity(LivingDeathEvent event) {
    	
    	if(event.getEntity() == null || event.getEntity().worldObj.isRemote) {
    		return;
    	}
    	
    	World world = event.getEntity().worldObj;
    	
    	DamageSource source = event.getSource();
    	Entity killer = source.getEntity();
    	Entity killedEntity = event.getEntity();
    	Random random = world.rand;
    	
    	if(killer instanceof EntityLivingBase) {
    		random = ((EntityLivingBase)killer).getRNG();
    	}
    	
    	if(killedEntity instanceof EntityWitherSkeleton) {
    		int l = 0;
    		
    		if(killer instanceof EntityLivingBase) {
    			l = random.nextInt(EnchantmentHelper.getEnchantmentLevel(Enchantments.LOOTING, ((EntityLivingBase)killer).getHeldItemMainhand()) + 1);
    		}
    		
    		killedEntity.entityDropItem(new ItemStack(NetherItems.WITHER_DUST, random.nextInt(3) + l), 0.0F);
    	}
    	
    	if(killer instanceof EntityPlayer) {
    		EntityPlayer killerPlayer = (EntityPlayer) killer;
    		
    		if(killedEntity instanceof EntityLivingBase) {
    			EntityLivingBase killedLiving = (EntityLivingBase) killedEntity;
    			
        		ItemStack theBucket = null;
        		EnumHand hand = null;
        		
        		if(killerPlayer.getHeldItemMainhand() != null && (killerPlayer.getHeldItemMainhand().getItem() == NetherItems.GOLDEN_BUCKET || killerPlayer.getHeldItemMainhand().getItem() == NetherItems.GOLDEN_BUCKET_BLOOD)) {
        			theBucket = killerPlayer.getHeldItemMainhand();
        			hand = EnumHand.MAIN_HAND;
        		}
        		else if(killerPlayer.getHeldItemOffhand() != null && (killerPlayer.getHeldItemOffhand().getItem() == NetherItems.GOLDEN_BUCKET || killerPlayer.getHeldItemOffhand().getItem() == NetherItems.GOLDEN_BUCKET_BLOOD)) {
        			theBucket = killerPlayer.getHeldItemOffhand();
        			hand = EnumHand.OFF_HAND;
        		}
        		
        		if(theBucket != null && !BlockSpawner.isSpawnerEntity(killedEntity)) {
        			killerPlayer.playSound(SoundEvents.ITEM_BOTTLE_FILL, 0.9F, 1.4F);
        			if(theBucket.getItem() == NetherItems.GOLDEN_BUCKET) {
        				theBucket = new ItemStack(NetherItems.GOLDEN_BUCKET_BLOOD);
        				ItemBloodBucket.setBlood(theBucket, (int)killedLiving.getMaxHealth());
        				killerPlayer.setHeldItem(hand, theBucket);

        			}
        			else {
        				ItemBloodBucket.addBlood(theBucket, (int)killedLiving.getMaxHealth());
        			}
        		}
    		}
    	
        	if(killedEntity instanceof NetherMob || killedEntity instanceof EntityPigZombie || killedEntity instanceof EntityBlaze || killedEntity instanceof EntityGhast || killedEntity instanceof EntityMagmaCube)
        		killerPlayer.addStat(AdInferosAchievements.demonSlayer);
            		
            if(killedEntity instanceof EntityObsidianSheepman)
            	killerPlayer.addStat(AdInferosAchievements.obsidiancow);
            
            if(NetherConfig.ghostSpawnFromEntities && !BlockSpawner.isSpawnerEntity(killedEntity)) {
                if(killedEntity instanceof EntityPigZombie && !((EntityPigZombie) killedEntity).isChild() && random.nextInt(30) == 0) {
                	EntityGhost.createGhost(world, (EntityLiving) killedEntity, "textures/entity/zombie_pigman.png");
                }
                
                if(killedEntity instanceof AbstractSkeleton && random.nextInt(30) == (killedEntity instanceof EntityWitherSkeleton ? 20 : 80)) {
                	EntityGhost.createGhost(world, (EntityLiving) killedEntity, (killedEntity instanceof EntityWitherSkeleton ? "textures/entity/skeleton/wither_skeleton.png" : "textures/entity/skeleton/skeleton.png"));
                }
                
                if(killedEntity instanceof EntityZombie && random.nextInt(30) == 80) {
                	EntityGhost.createGhost(world, (EntityLiving) killedEntity, "textures/entity/zombie/zombie.png");
                }
            }
    	}
    	
    	if(NetherConfig.ghostSpawnFromPlayers && killedEntity instanceof EntityPlayer && killer instanceof EntityLiving && !(killer instanceof EntityGhost) && !(killer instanceof EntityReaper)) {
    		EntityGhost.createGhost(world, (EntityPlayer) killedEntity);
    	}
    }
    
    @SubscribeEvent
    public void onPlayerUpdate(PlayerTickEvent event)
    {
    	if(event.side.equals(Side.CLIENT) && event.player.equals(ThinkBigCore.proxy.getClientPlayer())) {
    		AdInferosCore.proxy.doUpdate();
    		AdInferosCore.proxy.updatePlayerModel();
    	}
    	else if(event.side.equals(Side.SERVER) && this.isValid(event.player)) {
    		
    		World world = event.player.worldObj;
    		EntityPlayer player = event.player;
    		
    		if(AdInferosCore.proxy.isNetherSurvival() && !player.isPotionActive(MobEffects.FIRE_RESISTANCE)) {
    			 player.addPotionEffect(new PotionEffect(MobEffects.FIRE_RESISTANCE, Integer.MAX_VALUE, 1, true, true));
    		}
    		
    		if(player.fallDistance > 0.0F && !player.isSneaking() && !player.isInLava() && !player.isOnLadder()) {
        		boolean quartzFlag = false;
    			ItemStack stack = null;
    			
    			ArrayList<ItemStack> stacks = InventoryHelper.getItems(player.inventory, NetherItems.AMULET, 2);
    			
    			if(!stacks.isEmpty()) {
    				for(ItemStack stackIn : stacks) {
    					if(ItemAmulet.isActive(stackIn)) {
    						stack = stackIn;
    						break;
    					}
    				}
    			}
    			
    			if(NetherConfig.amuletEffects && !quartzFlag && stack != null) {
    				quartzFlag = true;
    			}
    			
    			if(quartzFlag) {
    				player.fallDistance = 0.08F;
    			}
    		}
    		
    		if(AdInferosCore.proxy.isNetherSurvival() && player.isWet() && !(player.isInWater() && this.isInAcid(world, player.getEntityBoundingBox().expand(-0.10000000149011612D, -0.4000000059604645D, -0.10000000149011612D)))) {
    			player.attackEntityFrom(DamageSource.drown, 2);
    		}
    		
    		int obsidianCount = 0;
    		EntityEquipmentSlot[] slots = {EntityEquipmentSlot.HEAD, EntityEquipmentSlot.CHEST, EntityEquipmentSlot.LEGS, EntityEquipmentSlot.FEET};
			for(int k = 0; k < 4; k++) {
				if(player.getItemStackFromSlot(slots[k]) != null && player.getItemStackFromSlot(slots[k]).getItem() instanceof ItemObsidianArmor) {
					obsidianCount++;
				}
			}
			
    		int netheriteCount = 0;
			for(int k = 0; k < 4; k++) {
				if(player.getItemStackFromSlot(slots[k]) != null && player.getItemStackFromSlot(slots[k]).getItem() instanceof ItemNetheriteArmor) {
					netheriteCount++;
				}
			}
			
			double value = 0.0F;
			
			double f1 = 0.07D * obsidianCount;
			double f2 = 0.09D * netheriteCount;
			value = f2 - f1;
    		
            if(value != 0.0D) {
            	this.updateMovementSpeed(player, value);
            }
            else {
            	this.removeMovementSpeed(player);
            }
    	}
    }
    
    private boolean isInAcid(World world, AxisAlignedBB bb) {
        int i = MathHelper.floor_double(bb.minX);
        int j = MathHelper.ceiling_double_int(bb.maxX);
        int k = MathHelper.floor_double(bb.minY);
        int l = MathHelper.ceiling_double_int(bb.maxY);
        int i1 = MathHelper.floor_double(bb.minZ);
        int j1 = MathHelper.ceiling_double_int(bb.maxZ);
        BlockPos.PooledMutableBlockPos blockpos$pooledmutableblockpos = BlockPos.PooledMutableBlockPos.retain();

        for (int k1 = i; k1 < j; ++k1)
        {
            for (int l1 = k; l1 < l; ++l1)
            {
                for (int i2 = i1; i2 < j1; ++i2)
                {
                    if (world.getBlockState(blockpos$pooledmutableblockpos.setPos(k1, l1, i2)).getBlock() instanceof BlockAcid)
                    {
                        blockpos$pooledmutableblockpos.release();
                        return true;
                    }
                }
            }
        }

        blockpos$pooledmutableblockpos.release();
        return false;
    }
    
    private boolean isValid(EntityLivingBase entitylivingbase) {
    	AxisAlignedBB bb = entitylivingbase.getEntityBoundingBox().expand(-0.10000000149011612D, -0.4000000059604645D, -0.10000000149011612D);
        int i = MathHelper.floor_double(bb.minX);
        int j = MathHelper.ceiling_double_int(bb.maxX);
        int k = MathHelper.floor_double(bb.minY);
        int l = MathHelper.ceiling_double_int(bb.maxY);
        int i1 = MathHelper.floor_double(bb.minZ);
        int j1 = MathHelper.ceiling_double_int(bb.maxZ);
        BlockPos.PooledMutableBlockPos blockpos$pooledmutableblockpos = BlockPos.PooledMutableBlockPos.retain();
        
        for (int k1 = i; k1 < j; ++k1)
        {
            for (int l1 = k; l1 < l; ++l1)
            {
                for (int i2 = i1; i2 < j1; ++i2)
                {
                    if (!entitylivingbase.worldObj.isBlockLoaded(blockpos$pooledmutableblockpos.setPos(k1, l1, i2)))
                    {
                        blockpos$pooledmutableblockpos.release();
                        return false;
                    }
                }
            }
        }

        blockpos$pooledmutableblockpos.release();
        
        return true;
	}
    
	private void updateMovementSpeed(EntityPlayer player, double value)
	{ 	
		IAttributeInstance atinst = player.getEntityAttribute(SharedMonsterAttributes.MOVEMENT_SPEED); 
		{
			if(atinst.getModifier(customMovementUUID) == null)
			{
				atinst.applyModifier(new AttributeModifier(customMovementUUID,"AFMovementModifier" ,value ,2));
			}
			else if(atinst.getModifier(customMovementUUID).getAmount() != value) {
				this.removeMovementSpeed(player);
				atinst.applyModifier(new AttributeModifier(customMovementUUID,"AFMovementModifier" ,value ,2));
			}
		}
	}

	private void removeMovementSpeed(EntityPlayer player)
	{ 	
		IAttributeInstance atinst = player.getEntityAttribute(SharedMonsterAttributes.MOVEMENT_SPEED); 
		{
			if(atinst.getModifier(customMovementUUID) != null)
			{
				atinst.removeModifier(customMovementUUID);
			}
		}
	}
    
    @SideOnly(Side.CLIENT)
    @SubscribeEvent
    public void updateFog(EntityViewRenderEvent.FogColors event)
    {
    	Entity entity = event.getEntity();
    	if(entity instanceof EntityPlayer && entity.isEntityAlive() && AdInferosCore.proxy.isNetherSurvival() && entity.dimension == -1) {
    		EntityPlayer player = (EntityPlayer) entity;
    		if(!player.isPotionActive(MobEffects.BLINDNESS)) {
        		if(AdInferosCore.proxy.isDarkNether()) {
            		event.setRed(0.0F);
            		event.setGreen(0.0F);
            		event.setBlue(0.0F);
        		}
        		else {
            		event.setRed(0.5F);
            		event.setGreen(0.15F);
            		event.setBlue(0.15F);
        		}
    		}
    	}
    }
}