package ga.matthewtgm.skyblockmod.features.impl.trackers.fishing;

import com.google.gson.internal.LinkedTreeMap;
import ga.matthewtgm.json.objects.JsonArray;
import ga.matthewtgm.json.objects.JsonObject;
import ga.matthewtgm.json.parsing.JsonParser;
import ga.matthewtgm.lib.util.WebUtils;
import ga.matthewtgm.skyblockmod.features.impl.trackers.Tracker;
import ga.matthewtgm.skyblockmod.features.impl.trackers.TrackerItem;
import net.minecraft.util.StringUtils;
import net.minecraftforge.client.event.ClientChatReceivedEvent;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

import java.util.ArrayList;
import java.util.List;

public class TrackerFishing extends Tracker {

    private JsonArray seaCreaturesArray = intializeSeaCreaturesArray();

    public TrackerFishing() {
        super("Fishing Tracker");
    }

    @Override
    public void onEnabled() {
        if (seaCreaturesArray == null)
            seaCreaturesArray = intializeSeaCreaturesArray();
        MinecraftForge.EVENT_BUS.register(this);
    }

    @Override
    public void onDisabled() {
        MinecraftForge.EVENT_BUS.unregister(this);
    }

    @Override
    public List<TrackerItem<?>> trackerItems() {
        List<String> creatures = new ArrayList<>();
        for (Object o : seaCreaturesArray) {
            if (o.getClass().isAssignableFrom(LinkedTreeMap.class)) {
                creatures.add(((LinkedTreeMap) o).get("name").toString());
            }
        }
        List<TrackerItem<?>> trackerItems = new ArrayList<>();
        for (String creature : creatures) {
            trackerItems.add(new TrackerItem<>(creature).setValue(0));
        }
        return trackerItems;
    }

    private JsonArray intializeSeaCreaturesArray() {
        return JsonParser.parseArr(WebUtils.getInstance().getJsonOnline("https://raw.githubusercontent.com/TGMDevelopment/SkyBlock-Bonus-Data/main/skyblock/sea_creatures.json"));
    }

    @SubscribeEvent
    protected void onChatReceived(ClientChatReceivedEvent event) {
        if (event.type == 0 || event.type == 1) {
            seaCreaturesArray.forEach(obj -> {
                if (obj.getClass().isAssignableFrom(LinkedTreeMap.class)) {
                    LinkedTreeMap object = (LinkedTreeMap) obj;
                    String strippedMsg = StringUtils.stripControlCodes(event.message.getUnformattedText());
                    if (strippedMsg.equals(object.get("message"))) {
                        System.out.println("Caught a sea creature!");
                        TrackerItem<Integer> item = new TrackerItem<Integer>((String) object.get("name")).setValue(1);
                        System.out.println(item);
                        if (trackerItems().contains(item)) {
                            int current = item.getValue();
                            trackerItems().add(item.setValue(current + 1));
                        } else trackerItems().add(item);
                        onSave();
                    }
                }
            });
        }
    }

}