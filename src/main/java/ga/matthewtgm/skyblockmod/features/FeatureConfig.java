package ga.matthewtgm.skyblockmod.features;

import ga.matthewtgm.json.files.JsonReader;
import ga.matthewtgm.json.files.JsonWriter;
import ga.matthewtgm.json.objects.JsonArray;
import ga.matthewtgm.json.objects.JsonObject;
import ga.matthewtgm.skyblockmod.SkyBlockBonus;

public class FeatureConfig {

    private final Feature feature;
    private final JsonObject configObject;

    public FeatureConfig(Feature feature) {
        this.feature = feature;

        if(JsonReader.readObj(feature.getRegistryName(), SkyBlockBonus.getFileHandler().featureDir) == null) this.configObject = new JsonObject();
        else this.configObject = JsonReader.readObj(feature.getRegistryName(), SkyBlockBonus.getFileHandler().featureDir);
        //System.out.println("Feature: " + feature.getRegistryName() + "\n" + configObject);
    }

    public FeatureConfig put(String key, Object value) {
        this.configObject.add(key, value);
        return this;
    }


    public <T> T get(String key, Class<?> type) {
        //System.out.println("FeatureConfig#get\n" + "Feature: " + this.feature.getRegistryName() + "\nKey: " + key + "\nType: " + type);
        if (!this.configObject.containsKey(key)) {
            Object newVal = new Object();
            if (type.isAssignableFrom(Boolean.class)) newVal = Boolean.FALSE;
            if (type.isAssignableFrom(Double.class)) newVal = 0D;
            if (type.isAssignableFrom(Float.class)) newVal = 0F;
            if (type.isAssignableFrom(Long.class)) newVal = 0L;
            if (type.isAssignableFrom(Integer.class)) newVal = 0;
            if (type.isAssignableFrom(Short.class)) newVal = 0;
            if (type.isAssignableFrom(Character.class)) newVal = 'A';
            if (type.isAssignableFrom(Byte.class)) newVal = 0;
            if (type.isAssignableFrom(JsonObject.class)) newVal = new JsonObject();
            if (type.isAssignableFrom(JsonArray.class)) newVal = new JsonArray();
            this.configObject.put(key, newVal);
            this.save();
        }
        return (T) this.configObject.get(key);
    }

    public void save() {
        //System.out.println("Feature: " + feature.getRegistryName() + "\n" + configObject);
        JsonWriter.write(feature.getRegistryName(), this.configObject, SkyBlockBonus.getFileHandler().featureDir);
    }

    public JsonObject getConfigObject() {
        return configObject;
    }

}