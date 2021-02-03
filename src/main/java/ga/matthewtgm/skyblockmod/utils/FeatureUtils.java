package ga.matthewtgm.skyblockmod.utils;

import ga.matthewtgm.json.files.JsonReader;
import ga.matthewtgm.json.objects.JsonObject;
import ga.matthewtgm.skyblockmod.SkyBlockBonus;
import ga.matthewtgm.skyblockmod.features.Feature;
import ga.matthewtgm.skyblockmod.features.FeaturePosition;

public class FeatureUtils {

    private static FeatureUtils INSTANCE;

    public static FeatureUtils getInstance() {
        if (INSTANCE == null)
            INSTANCE = new FeatureUtils();
        return INSTANCE;
    }

    public void setupConfigs(Feature feature) {
        final boolean configIsNull = JsonReader.readObj(feature.getRegistryName(), SkyBlockBonus.getFileHandler().featureDir) == null;

        final JsonObject featureObj = JsonReader.readObj(feature.getRegistryName(), SkyBlockBonus.getFileHandler().featureDir);

        JsonObject positionObj = new JsonObject();

        if (!configIsNull) {
            if (featureObj.get("position") != null) positionObj = (JsonObject) featureObj.get("position");
        }

        if (configIsNull) feature.setToggleState(false);
        else feature.setToggleState(Boolean.parseBoolean(String.valueOf(featureObj.get("toggle"))));

        if (feature.isRendered()) {
            if (configIsNull) feature.setPosition(new FeaturePosition(10, 10));
            else if (!positionObj.isEmpty()) feature.setPosition(new FeaturePosition(
                    Integer.parseInt(String.valueOf(positionObj.get("x"))),
                    Integer.parseInt(String.valueOf(positionObj.get("y")))
            ));
        }

    }

}