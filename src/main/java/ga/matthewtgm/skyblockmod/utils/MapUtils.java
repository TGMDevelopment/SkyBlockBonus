package ga.matthewtgm.skyblockmod.utils;

import ga.matthewtgm.json.objects.JsonArray;
import ga.matthewtgm.json.objects.JsonObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class MapUtils {

    private static MapUtils INSTANCE;

    public static MapUtils getInstance() {
        if (INSTANCE == null)
            INSTANCE = new MapUtils();
        return INSTANCE;
    }

    public <T> T get(Map map, String key, Class<?> type) {
        if (!map.containsKey(key)) {
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
            if (type.isAssignableFrom(List.class)) newVal = new ArrayList<>();
            if (type.isAssignableFrom(Map.class)) newVal = new HashMap<>();
            map.put(key, newVal);
        }
        return (T) map.get(key);
    }

}