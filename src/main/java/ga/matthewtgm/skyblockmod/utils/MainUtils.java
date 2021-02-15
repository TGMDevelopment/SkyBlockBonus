package ga.matthewtgm.skyblockmod.utils;

import net.minecraft.client.Minecraft;
import net.minecraft.scoreboard.ScoreObjective;
import net.minecraft.scoreboard.Scoreboard;
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
        boolean skyblock = false;
        Minecraft mc = Minecraft.getMinecraft();
        if(mc != null && mc.thePlayer != null && mc.theWorld != null && !mc.isSingleplayer() && isPlayerOnHypixel()) {
            Scoreboard scoreboard = mc.theWorld.getScoreboard();
            if(scoreboard != null) {
                ScoreObjective objective = scoreboard.getObjectiveInDisplaySlot(1);
                if(objective != null) {
                    String objectiveName = StringUtils.stripControlCodes(objective.getDisplayName());
                    if(objectiveName.equalsIgnoreCase("skyblock")) skyblock = true;
                }
            }
        }
        return skyblock;
    }

    public boolean isNull(Object obj) {
        return obj == null;
    }

}