package ga.matthewtgm.skyblockmod.utils;

import ga.matthewtgm.skyblockmod.events.ProfileChangedEvent;
import net.minecraft.client.Minecraft;
import net.minecraft.util.StringUtils;
import net.minecraftforge.client.event.ClientChatReceivedEvent;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class SkyBlockUtils {


    private String profileName = "Unknown";

    @SubscribeEvent
    protected void onChatReceived(ClientChatReceivedEvent event) {
        if (event.type != 0) return;
        if (event.message.getUnformattedText().contains("profile")) {
            String msg = StringUtils.stripControlCodes(event.message.getUnformattedText());
            Matcher profMatcher;
            if (msg.contains("You are playing on profile:")) {
                profMatcher = Pattern.compile("^You are playing on profile: (\\w+)(?: \\(Co-op\\))?$").matcher(msg);
                if (profMatcher.matches()) {
                    this.profileName = profMatcher.group(1);
                    MinecraftForge.EVENT_BUS.register(new ProfileChangedEvent(this.profileName));
                }
            } else if (msg.contains("Your profile was changed to:")) {
                profMatcher = Pattern.compile("^Your profile was changed to: (\\w+)(?: \\(Co-op\\))?$").matcher(msg);
                if (profMatcher.matches()) {
                    this.profileName = profMatcher.group(1);
                    MinecraftForge.EVENT_BUS.register(new ProfileChangedEvent(this.profileName));
                }
            }
        }
    }

    public String getProfileName() {
        return profileName;
    }

}