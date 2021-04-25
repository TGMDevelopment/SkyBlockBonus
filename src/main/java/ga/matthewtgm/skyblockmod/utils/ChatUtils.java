package ga.matthewtgm.skyblockmod.utils;

import ga.matthewtgm.skyblockmod.Constants;
import ga.matthewtgm.skyblockmod.events.ModChatMessageSentEvent;
import net.minecraft.client.Minecraft;
import net.minecraft.util.ChatComponentText;
import net.minecraft.util.EnumChatFormatting;
import net.minecraftforge.client.event.ClientChatReceivedEvent;
import net.minecraftforge.common.MinecraftForge;

public class ChatUtils {

    private static ChatUtils INSTANCE;
    private final String prefix = EnumChatFormatting.GRAY + "[" + EnumChatFormatting.GOLD + Constants.NAME + EnumChatFormatting.GRAY + "]";

    public static ChatUtils getInstance() {
        if (INSTANCE == null)
            INSTANCE = new ChatUtils();
        return INSTANCE;
    }

    public void sendMessage(ModChatMessageSentEvent.Type type, String msg) {
        if (msg == null) return;
        if (Minecraft.getMinecraft().thePlayer == null) return;
        ModChatMessageSentEvent event = new ModChatMessageSentEvent(type, new ChatComponentText(msg));
        MinecraftForge.EVENT_BUS.post(event);
        if (!event.isCanceled())
            Minecraft.getMinecraft().thePlayer.addChatMessage(new ChatComponentText(msg));
    }

    public void sendModMessage(ModChatMessageSentEvent.Type type, String msg) {
        if (msg == null) return;
        if (Minecraft.getMinecraft().thePlayer == null) return;
        ModChatMessageSentEvent event = new ModChatMessageSentEvent(type, new ChatComponentText(prefix + EnumChatFormatting.RESET + " " + msg));
        MinecraftForge.EVENT_BUS.post(event);
        if (!event.isCanceled())
            Minecraft.getMinecraft().thePlayer.addChatMessage(new ChatComponentText(prefix + EnumChatFormatting.RESET + " " + msg));
    }

    public void sendModMessage(ModChatMessageSentEvent.Type type, ChatComponentText text) {
        if (text == null) return;
        if (Minecraft.getMinecraft().thePlayer == null) return;
        ChatComponentText chatComponent = new ChatComponentText(prefix + EnumChatFormatting.RESET + " ");
        chatComponent.appendSibling(text);
        ModChatMessageSentEvent event = new ModChatMessageSentEvent(type, chatComponent);
        MinecraftForge.EVENT_BUS.post(event);
        if (!event.isCanceled())
            Minecraft.getMinecraft().thePlayer.addChatMessage(chatComponent);
    }

    public String getPrefix() {
        return prefix;
    }

}