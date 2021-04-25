package ga.matthewtgm.skyblockmod.utils;

import ga.matthewtgm.skyblockmod.events.GetLocationEvent;
import ga.matthewtgm.skyblockmod.skyblock.EnumLocation;
import net.minecraft.client.Minecraft;
import net.minecraft.scoreboard.Score;
import net.minecraft.scoreboard.ScorePlayerTeam;
import net.minecraft.scoreboard.Scoreboard;
import net.minecraft.util.StringUtils;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.TickEvent;

import java.util.Collection;

public class LocationListener {

    private int tickTimer = 1;
    private EnumLocation location = EnumLocation.UNKNOWN;

    @SubscribeEvent
    protected void onClientTick(TickEvent.ClientTickEvent event) {
        if (event.phase == TickEvent.Phase.START) {
            tickTimer++;
            if (tickTimer % 15 == 0) {
                GetLocationEvent event1 = new GetLocationEvent(location);
                if (!event1.isCanceled()) {
                    this.checkLocation();
                    MinecraftForge.EVENT_BUS.post(event1);
                }
            }
        }
    }

    private void checkLocation() {
        if (Minecraft.getMinecraft().theWorld != null) {
            Scoreboard scoreboard = Minecraft.getMinecraft().theWorld.getScoreboard();
            if (scoreboard != null) {
                Collection<Score> scores = scoreboard.getSortedScores(scoreboard.getObjectiveInDisplaySlot(1));
                for (Score line : scores) {
                    ScorePlayerTeam scorePlayerTeam = scoreboard.getPlayersTeam(line.getPlayerName());
                    this.parseLocation(scorePlayerTeam, line);
                }
            }
        }
    }

    private void parseLocation(ScorePlayerTeam scorePlayerTeam, Score score) {
        String strippedLine = TextUtils.keepScoreboardCharacters(StringUtils.stripControlCodes(ScorePlayerTeam.formatPlayerName(scorePlayerTeam, score.getPlayerName()))).trim();
        for (EnumLocation loopLocation : EnumLocation.values()) {
            if (strippedLine.contains(loopLocation.getScoreboardName())) {
                if (location != loopLocation) {
                    location = loopLocation;
                }
            }
        }
    }

    public EnumLocation getLocation() {
        return location;
    }

}