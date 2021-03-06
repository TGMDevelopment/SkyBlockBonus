package ga.matthewtgm.skyblockmod.listeners.impl;

import club.sk1er.mods.core.util.Multithreading;
import ga.matthewtgm.skyblockmod.Constants;
import ga.matthewtgm.skyblockmod.SkyBlockBonus;
import ga.matthewtgm.skyblockmod.events.SkyBlockJoinedEvent;
import ga.matthewtgm.skyblockmod.events.SkyBlockLeftEvent;
import ga.matthewtgm.skyblockmod.listeners.SkyBlockModListener;
import ga.matthewtgm.skyblockmod.utils.ActionBarParser;
import ga.matthewtgm.skyblockmod.utils.MainUtils;
import net.minecraft.client.Minecraft;
import net.minecraft.client.entity.EntityPlayerSP;
import net.minecraft.event.ClickEvent;
import net.minecraft.util.ChatComponentText;
import net.minecraft.util.EnumChatFormatting;
import net.minecraftforge.client.event.ClientChatReceivedEvent;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.entity.EntityJoinWorldEvent;
import net.minecraftforge.event.entity.player.PlayerInteractEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.network.FMLNetworkEvent;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class PlayerListener extends SkyBlockModListener {

    private final ActionBarParser actionBarParser = new ActionBarParser();

    private final double sinceLastIceSprayUse = 0.1;
    private boolean hasCheckedForUpdate = false;

    public void setHasCheckedForUpdate(boolean hasCheckedForUpdate) {
        this.hasCheckedForUpdate = hasCheckedForUpdate;
    }

    @SubscribeEvent
    public void onChatReceived(ClientChatReceivedEvent event) {
        if (MainUtils.getInstance().isPlayerOnHypixel() && MainUtils.getInstance().isPlayerInSkyblock() && event.message.getUnformattedText().equalsIgnoreCase("welcome to hypixel skyblock!")) {
            final Logger logger = LogManager.getLogger(Constants.NAME + " (SkyBlockJoinedEvent)");

            MinecraftForge.EVENT_BUS.post(new SkyBlockJoinedEvent());
            logger.info("The player has joined SkyBlock!");
        }

        if(event.type == 2) {
            String rest = SkyBlockBonus.getInstance().getActionBarParser().parse(event.message.getUnformattedText());
            if(rest.trim().length() == 0) event.setCanceled(true);
        }
    }

    @SubscribeEvent
    public void onServerLeave(FMLNetworkEvent.ClientDisconnectionFromServerEvent event) {
        if (MainUtils.getInstance().isPlayerOnHypixel() && MainUtils.getInstance().isPlayerInSkyblock()) {
            final Logger logger = LogManager.getLogger(Constants.NAME + " (SkyBlockLeftEvent)");

            MinecraftForge.EVENT_BUS.post(new SkyBlockLeftEvent(event.manager));
            logger.info("The player has left SkyBlock!");
        }
    }

    @SubscribeEvent
    public void onInteract(PlayerInteractEvent event) {

    }

    @SubscribeEvent
    public void onJoin(EntityJoinWorldEvent event) {
        if (!hasCheckedForUpdate) {
            this.setHasCheckedForUpdate(true);
            Multithreading.runAsync(() -> {
                EntityPlayerSP player;
                if (Constants.VER.equals(SkyBlockBonus.getInstance().VERSION_CHECKER.getVersion())) {
                    //Minecraft.getMinecraft().thePlayer.addChatMessage(new ChatComponentText(EnumChatFormatting.YELLOW + "You have the latest version of " + EnumChatFormatting.GREEN + Constants.NAME + EnumChatFormatting.YELLOW + "!"));
                    return;
                }
                ChatComponentText updateMessage = new ChatComponentText(EnumChatFormatting.GOLD + "" + EnumChatFormatting.BOLD + "[" + SkyBlockBonus.getInstance().VERSION_CHECKER.getVersion() + "]");
                updateMessage.setChatStyle(updateMessage.getChatStyle().setChatClickEvent(new ClickEvent(ClickEvent.Action.OPEN_URL, SkyBlockBonus.getInstance().VERSION_CHECKER.getDownloadURL())));
                try {
                    Thread.sleep(10000);
                } catch (Exception e) {
                    e.printStackTrace();
                }
                player = Minecraft.getMinecraft().thePlayer;
                player.addChatMessage(new ChatComponentText(EnumChatFormatting.YELLOW + "Your version of " + EnumChatFormatting.GREEN + Constants.NAME + EnumChatFormatting.YELLOW + " is out of date!\n" + EnumChatFormatting.RED + "Please update: ").appendSibling(updateMessage));
            });
        }
    }

}