package ga.matthewtgm.skyblockmod.utils;

import net.minecraft.client.Minecraft;
import net.minecraft.util.StringUtils;

public class MainUtils {

    private static MainUtils INSTANCE;
    private final Minecraft mc = Minecraft.getMinecraft();

    public static MainUtils getInstance() {
        if (INSTANCE == null)
            INSTANCE = new MainUtils();
        return INSTANCE;
    }

    public boolean isPlayerOnHypixel() {
        if (Minecraft.getMinecraft().getCurrentServerData() != null) {
            return Minecraft.getMinecraft().getCurrentServerData().serverIP.matches("(.*).hypixel.net");
        }
        return false;
    }

    public boolean isPlayerInSkyblock() {
        boolean isInSkyblock = false;
        if(Minecraft.getMinecraft().theWorld != null) {
            if (Minecraft.getMinecraft().theWorld.getScoreboard().getObjectiveInDisplaySlot(1) != null) {
                if (StringUtils.stripControlCodes(Minecraft.getMinecraft().theWorld.getScoreboard().getObjectiveInDisplaySlot(1).getDisplayName()).contains("SKYBLOCK")) {
                    isInSkyblock = true;
                }
            }
        }
        return isInSkyblock;
    }

    public boolean isNull(Object obj) {
        return obj == null;
    }

}