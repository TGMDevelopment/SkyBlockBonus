package ga.matthewtgm.skyblockmod.utils;

import ga.matthewtgm.json.files.JsonReader;
import ga.matthewtgm.json.files.JsonWriter;
import ga.matthewtgm.json.objects.JsonArray;
import ga.matthewtgm.json.objects.JsonObject;

import java.io.File;

public class Config {

    private final String name;
    private final File dir;
    private final JsonObject configObject;

    public Config(String name, File dir) {
        this.name = name;
        this.dir = dir;

        if(JsonReader.readObj(name, dir) == null) this.configObject = new JsonObject();
        else this.configObject = JsonReader.readObj(name, dir);
    }

    public Config put(String key, Object value) {
        this.configObject.add(key, value);
        return this;
    }


    public <T> T get(String key, Class<?> type) {
        //System.out.println("FeatureConfig#get\n" + "Feature: " + this.feature.getRegistryName() + "\nKey: " + key + "\nType: " + type);
        if (!this.configObject.containsKey(key)) {
            Object newVal = new Object();
            if (type.isAssignableFrom(String.class)) newVal = "";
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
        JsonWriter.write(name, configObject, dir);
    }

    public JsonObject getConfigObject() {
        return configObject;
    }

}