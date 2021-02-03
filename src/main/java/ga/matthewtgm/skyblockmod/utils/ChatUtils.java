package ga.matthewtgm.skyblockmod.utils;

import ga.matthewtgm.skyblockmod.Constants;
import net.minecraft.client.Minecraft;
import net.minecraft.util.ChatComponentText;
import net.minecraft.util.EnumChatFormatting;

public class ChatUtils {

    private static ChatUtils INSTANCE;
    private final String prefix = EnumChatFormatting.GRAY + "[" + EnumChatFormatting.GOLD + Constants.NAME + EnumChatFormatting.GRAY + "]";

    public static ChatUtils getInstance() {
        if (INSTANCE == null)
            INSTANCE = new ChatUtils();
        return INSTANCE;
    }

    public void sendMessage(String msg) {
        if (msg == null) return;
        if (Minecraft.getMinecraft().thePlayer == null) return;
        Minecraft.getMinecraft().thePlayer.addChatMessage(new ChatComponentText(msg));
    }

    public void sendModMessage(String msg) {
        if (msg == null) return;
        if (Minecraft.getMinecraft().thePlayer == null) return;
        Minecraft.getMinecraft().thePlayer.addChatMessage(new ChatComponentText(prefix + EnumChatFormatting.RESET + " " + msg));
    }

    public void sendModMessage(ChatComponentText text) {
        if (text == null) return;
        if (Minecraft.getMinecraft().thePlayer == null) return;
        ChatComponentText chatComponent = new ChatComponentText(prefix + EnumChatFormatting.RESET + " ");
        chatComponent.appendSibling(text);
        Minecraft.getMinecraft().thePlayer.addChatMessage(chatComponent);
    }

    public String getPrefix() {
        return prefix;
    }

}