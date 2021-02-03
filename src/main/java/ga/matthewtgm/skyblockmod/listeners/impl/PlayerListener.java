package ga.matthewtgm.skyblockmod.listeners.impl;

import ga.matthewtgm.skyblockmod.Constants;
import ga.matthewtgm.skyblockmod.SkyBlockBonus;
import ga.matthewtgm.skyblockmod.events.SkyBlockJoinedEvent;
import ga.matthewtgm.skyblockmod.events.SkyBlockLeftEvent;
import ga.matthewtgm.skyblockmod.listeners.SkyBlockModListener;
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

public class PlayerListener extends SkyBlockModListener {

    private boolean hasCheckedForUpdate = false;
    private final double sinceLastIceSprayUse = 0.1;

    public void setHasCheckedForUpdate(boolean hasCheckedForUpdate) {
        this.hasCheckedForUpdate = hasCheckedForUpdate;
    }

    @SubscribeEvent
    public void onServerJoin(FMLNetworkEvent.ClientConnectedToServerEvent event) {
        if(MainUtils.getInstance().isPlayerOnHypixel() && MainUtils.getInstance().isPlayerInSkyblock()) MinecraftForge.EVENT_BUS.post(new SkyBlockJoinedEvent());
    }

    @SubscribeEvent
    public void onServerLeave(FMLNetworkEvent.ClientDisconnectionFromServerEvent event) {
        if(MainUtils.getInstance().isPlayerOnHypixel() && MainUtils.getInstance().isPlayerInSkyblock()) MinecraftForge.EVENT_BUS.post(new SkyBlockLeftEvent());
    }

    @SubscribeEvent
    public void onInteract(PlayerInteractEvent event) {

    }

    @SubscribeEvent
    public void onChatReceived(ClientChatReceivedEvent event) {
    }

    @SubscribeEvent
    public void onJoin(EntityJoinWorldEvent event) {
        if (!hasCheckedForUpdate) {
            this.setHasCheckedForUpdate(true);
            Thread updateCheckThread = new Thread(() -> {
                EntityPlayerSP player;
                if (Constants.VER.equals(SkyBlockBonus.getInstance().VERSION_CHECKER.getVersion())) {
                    Minecraft.getMinecraft().thePlayer.addChatMessage(new ChatComponentText(EnumChatFormatting.YELLOW + "You have the latest version of " + EnumChatFormatting.GREEN + Constants.NAME + EnumChatFormatting.YELLOW + "!"));
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
            updateCheckThread.start();
        }
    }

}