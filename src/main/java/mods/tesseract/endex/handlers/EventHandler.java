package mods.tesseract.endex.handlers;

import mods.tesseract.endex.Reference;
import mods.tesseract.endex.init.BlockInit;
import mods.tesseract.endex.init.ItemInit;
import mods.tesseract.endex.mod.entity.EntityChronologist;
import mods.tesseract.endex.mod.entity.EntityEGuard;
import mods.tesseract.endex.mod.entity.EntityWatcher;
import mods.tesseract.endex.utils.EndForge;
import mods.tesseract.endex.world.TeleporterEnd;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.boss.EntityDragon;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.entity.monster.EntityEnderman;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;
import net.minecraft.server.management.PlayerList;
import net.minecraft.util.EnumActionResult;
import net.minecraft.util.EnumHand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.*;
import net.minecraftforge.common.config.Config;
import net.minecraftforge.common.config.ConfigManager;
import net.minecraftforge.event.entity.living.*;
import net.minecraftforge.event.entity.player.PlayerInteractEvent;
import net.minecraftforge.fml.client.event.ConfigChangedEvent;
import net.minecraftforge.fml.common.Mod.EventBusSubscriber;
import net.minecraftforge.fml.common.eventhandler.EventPriority;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;


@EventBusSubscriber
public class EventHandler {
    @SubscribeEvent
    public void onDragonTick(LivingEvent.LivingUpdateEvent event) {
        EntityLivingBase living = event.getEntityLiving();
        if (living.world.isRemote || !(living instanceof EntityDragon))
            return;
        EntityDragon dragon = (EntityDragon) living;
        if (dragon.deathTicks < 150 || dragon.deathTicks % 10 != 0)
            return;

        for (int i = 0; i < 6; i++) {
            int x = dragon.world.rand.nextInt(256) - 128;
            int z = dragon.world.rand.nextInt(256) - 128;
            BlockPos pos = new BlockPos(x, dragon.world.getHeight(x, z) - 1, z);
            if (!dragon.world.isBlockLoaded(pos))
                continue;
            if (dragon.world.getBlockState(pos.down()).getBlock() != Blocks.END_STONE)
                continue;
            dragon.world.setBlockState(pos, BlockInit.DRAGON_ESSENCE.getDefaultState());
        }
    }

    @SubscribeEvent
    public static void playerInteractEvent(PlayerInteractEvent.RightClickBlock event) {
        ItemStack stack = event.getItemStack();

        EntityPlayer player = event.getEntityPlayer();
        if (event.getHand() == EnumHand.OFF_HAND) {
            ItemStack mainStack = player.getHeldItem(EnumHand.MAIN_HAND);
            if (EndForge.hasAction(event.getWorld(), event.getPos(), mainStack, event.getFace())) {
                event.setCancellationResult(EnumActionResult.SUCCESS);
                event.setCanceled(true);
                return;
            }
        }

        if (EndForge.hasAction(event.getWorld(), event.getPos(), stack, event.getFace())) {
            if (EndForge.performAction(event.getWorld(), event.getPos(), player, stack, event.getFace(), event.getHand())) {
                event.setCancellationResult(EnumActionResult.SUCCESS);
                event.setCanceled(true);
            }
        }
    }

    @SubscribeEvent(receiveCanceled = true)
    public void onEventDrop(LivingDropsEvent event) {
        if (event.getEntity() instanceof EntityDragon) {
            ItemStack itemStackToDrop = new ItemStack(ItemInit.DRAGON_SCALES, 2);
            event.getDrops().add(new EntityItem(event.getEntity().getEntityWorld(), event.getEntity().posX,
                    event.getEntity().posY, event.getEntity().posZ, itemStackToDrop));

        }
        if (event.getEntity() instanceof EntityEnderman) {
            ItemStack itemStackToDrop = new ItemStack(ItemInit.ENDER_FLESH, 1);
            event.getDrops().add(new EntityItem(event.getEntity().getEntityWorld(), event.getEntity().posX,
                    event.getEntity().posY, event.getEntity().posZ, itemStackToDrop));
        }

        if (event.getEntity() instanceof EntityWatcher) {
            ItemStack itemStackToDrop = new ItemStack(ItemInit.ENDER_FLESH, 1);
            event.getDrops().add(new EntityItem(event.getEntity().getEntityWorld(), event.getEntity().posX,
                    event.getEntity().posY, event.getEntity().posZ, itemStackToDrop));
        }

        if (event.getEntity() instanceof EntityWatcher) {
            ItemStack itemStackToDrop = new ItemStack(Items.ENDER_EYE, 1);
            event.getDrops().add(new EntityItem(event.getEntity().getEntityWorld(), event.getEntity().posX,
                    event.getEntity().posY, event.getEntity().posZ, itemStackToDrop));
        }
    }

    @SubscribeEvent
    public static void onLivingSpawn(LivingSpawnEvent event) {
        EntityLivingBase entity = event.getEntityLiving();
        if (entity instanceof EntityEnderman && ConfigsHandler.GENERAL.spawnWatcher) {
            if (entity.world.provider.getDimension() == 1 && entity.world.getDifficulty() != EnumDifficulty.PEACEFUL && !entity.world.isRemote) {
                if (entity.getRNG().nextInt(ConfigsHandler.BALANCE.watcherRare) == 1) {
                    EntityWatcher watch = new EntityWatcher(entity.world);
                    watch.copyLocationAndAnglesFrom(entity);
                    entity.world.spawnEntity(watch);
                    entity.setDead();
                }
            }
        }
        if (entity instanceof EntityEnderman && ConfigsHandler.GENERAL.spawnChronologist) {
            if (entity.world.provider.getDimension() == 0 && entity.world.getDifficulty() != EnumDifficulty.PEACEFUL && !entity.world.isRemote) {
                if (entity.getRNG().nextInt(ConfigsHandler.BALANCE.chronRare) == 1) {
                    EntityChronologist chron = new EntityChronologist(entity.world);
                    chron.copyLocationAndAnglesFrom(entity);
                    entity.world.spawnEntity(chron);
                    entity.setDead();
                }
            }
        }
        if (entity instanceof EntityEnderman && ConfigsHandler.GENERAL.spawnEndGuard) {
            if (entity.world.provider.getDimension() == 1 && entity.world.getDifficulty() != EnumDifficulty.PEACEFUL && !entity.world.isRemote) {
                if (entity.getRNG().nextInt(ConfigsHandler.BALANCE.guardRare) == 1) {
                    EntityEGuard guard = new EntityEGuard(entity.world);
                    guard.copyLocationAndAnglesFrom(entity);
                    entity.world.spawnEntity(guard);
                    entity.setDead();
                }
            }
        }
    }

    @SubscribeEvent
    public static void onPlayerPosition(LivingHurtEvent event) {
        if (event.getEntityLiving() instanceof EntityPlayerMP && event.getEntityLiving().dimension == 1
                && ConfigsHandler.GENERAL.teleporterEnd && event.getEntityLiving().getPosition().getY() <= -6) {
            EntityPlayerMP player = (EntityPlayerMP) event.getEntityLiving();
            PlayerList playerList = player.getEntityWorld().getMinecraftServer().getPlayerList();

            event.setCanceled(true);
            playerList.transferPlayerToDimension(player, 0,
                    new TeleporterEnd((WorldServer) player.getEntityWorld(),
                            player.getPosition().getX(), 250, player.getPosition().getZ()));
        }
    }

    @SubscribeEvent
    public static void onConfigChangedEvent(ConfigChangedEvent.OnConfigChangedEvent event) {
        if (event.getModID().equals(Reference.MODID)) {
            ConfigManager.sync(Reference.MODID, Config.Type.INSTANCE);

        }
    }
}