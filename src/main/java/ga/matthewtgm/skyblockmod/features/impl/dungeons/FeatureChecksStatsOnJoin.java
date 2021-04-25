package ga.matthewtgm.skyblockmod.features.impl.dungeons;

import com.google.gson.JsonObject;
import ga.matthewtgm.lib.util.threading.ThreadUtils;
import ga.matthewtgm.skyblockmod.SkyBlockBonus;
import ga.matthewtgm.skyblockmod.events.ModChatMessageSentEvent;
import ga.matthewtgm.skyblockmod.features.Feature;
import ga.matthewtgm.skyblockmod.features.FeatureCategory;
import ga.matthewtgm.skyblockmod.runnables.GuiButtonRunnable;
import ga.matthewtgm.skyblockmod.utils.APIUtils;
import ga.matthewtgm.skyblockmod.utils.ChatUtils;
import net.minecraft.util.EnumChatFormatting;
import net.minecraft.util.StringUtils;
import net.minecraftforge.client.event.ClientChatReceivedEvent;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

import java.text.SimpleDateFormat;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class FeatureChecksStatsOnJoin extends Feature {

    private DungeonFloor floor = DungeonFloor.ONE;

    public FeatureChecksStatsOnJoin() {
        super("Check Stats On Join", FeatureCategory.DUNGEONS, false);
        getAdditionalConfigButtons().add(new GuiButtonRunnable("Dungeon Floor: " + floor.num) {
            @Override
            public int getId() {
                return 1000;
            }
            @Override
            public int getX() {
                return mc.currentScreen.width / 2 - 50;
            }
            @Override
            public int getY() {
                return mc.currentScreen.height / 2 - 20;
            }
            @Override
            public int getWidth() {
                return 100;
            }
            @Override
            public int getHeight() {
                return 20;
            }
            @Override
            public void onActionPerformed() {
                floor = floor.getNext(floor);
                text = "Dungeon Floor: " + floor.num;
                text = "Dungeon Floor: " + floor.num;
                text = "Dungeon Floor: " + floor.num;
            }
        });
    }

    @Override
    public void onEnabled() {
        MinecraftForge.EVENT_BUS.register(this);
    }

    @Override
    public void onDisabled() {
        MinecraftForge.EVENT_BUS.unregister(this);
    }

    @SubscribeEvent
    protected void onChatMessageReceived(ClientChatReceivedEvent event) {
        if (event.type == 0 || event.type == 1) {
            String strippedMsg = StringUtils.stripControlCodes(event.message.getUnformattedText());
            System.out.println(strippedMsg);
            if (strippedMsg.contains("Dungeon Finder")) {
                Matcher matcher = getMatcher(strippedMsg);
                if (matcher.matches()) {
                    System.out.println(matcher.group(1));
                    ThreadUtils.getInstance().runAsync(() -> {
                        String divider = "------------------------";;
                        ChatUtils.getInstance().sendMessage(ModChatMessageSentEvent.Type.FEATURE, String.format("%s\n%s\n%s", EnumChatFormatting.BLUE + divider, getFloorStats(floor, getPlayerStats(matcher.group(1))), EnumChatFormatting.BLUE + divider));
                    });
                }
            }
        }
    }

    private JsonObject getPlayerStats(String username) {
        if (SkyBlockBonus.getApiKey() != null) {
            final JsonObject profile = APIUtils.getJSONResponse("https://api.hypixel.net/skyblock/profile?key=" + SkyBlockBonus.getApiKey() + "&profile=" + APIUtils.getLatestProfileID(APIUtils.getUUID(username), SkyBlockBonus.getApiKey()));
            boolean success = profile.get("success").getAsBoolean();
            if (success) {
                JsonObject obj = profile.getAsJsonObject("profile").getAsJsonObject("members").getAsJsonObject(APIUtils.getUUID(username)).getAsJsonObject("dungeons").getAsJsonObject("dungeon_types").getAsJsonObject("catacombs");
                return obj;
            } else {
                ChatUtils.getInstance().sendModMessage(ModChatMessageSentEvent.Type.FEATURE, "Error fetching dungeon stats for " + username);
                return new JsonObject();
            }
        } else {
            ChatUtils.getInstance().sendModMessage(ModChatMessageSentEvent.Type.FEATURE, "Please enter your API key using /sbb apikey");
            return new JsonObject();
        }
    }

    private Matcher getMatcher(String msg) {
        return Pattern.compile("Dungeon Finder > (\\w+) joined the dungeon group! \\((\\w+) Level (\\w+)\\)").matcher(msg);
    }

    private String getFloorStats(DungeonFloor floor, JsonObject playerStats) {
        return String.format("%s" +
                "\nTimes played: %s" +
                "\nBest score: %s" +
                "\nFastest S: %s" +
                "\nFastest S+: %s" +
                "\n" +
                EnumChatFormatting.BOLD + "PLEASE IGNORE THE FASTEST S AND S+ VALUES, THEY ARE CURRENTLY " + EnumChatFormatting.UNDERLINE + "BROKEN",
                EnumChatFormatting.BOLD + floor.name.toUpperCase(), playerStats.getAsJsonObject("times_played").get(String.valueOf(floor.getNum())).getAsInt(), playerStats.getAsJsonObject("best_score").get(String.valueOf(floor.getNum())).getAsInt(), new SimpleDateFormat("HH:mm:ss").format(playerStats.getAsJsonObject("fastest_time_s").get(String.valueOf(floor.getNum())).getAsInt()), new SimpleDateFormat("HH:mm:ss").format(playerStats.getAsJsonObject("fastest_time_s_plus").get(String.valueOf(floor.getNum())).getAsInt()));
    }

    private enum DungeonFloor {

        ONE(1, "Floor One"),
        TWO(2, "Floor Two"),
        THREE(3, "Floor Three"),
        FOUR(4, "Floor Four"),
        FIVE(5, "Flour Five"),
        SIX(6, "Floor Six"),
        SEVEN(7, "Floor Seven");

        private int num;
        private String name;

        DungeonFloor(int num, String name) {
            this.num = num;
            this.name = name;
        }

        public int getNum() {
            return num;
        }

        public String getName() {
            return name;
        }

        public DungeonFloor getNext(DungeonFloor floor) {
            return values()[(floor.ordinal() + 1) % values().length];
        }

    }

}