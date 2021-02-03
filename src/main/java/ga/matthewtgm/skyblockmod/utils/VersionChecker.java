package ga.matthewtgm.skyblockmod.utils;

import ga.matthewtgm.json.objects.JsonObject;
import ga.matthewtgm.json.parsing.JsonParser;
import ga.matthewtgm.skyblockmod.Constants;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URL;

public class VersionChecker {

    public String verJson;
    public JsonObject verOBJ;
    private String version;
    private String download_url;
    private boolean emergency;
    private BufferedReader reader;

    {
        try {
            reader = new BufferedReader(new InputStreamReader(new URL("https://dl.dropboxusercontent.com/s/6545j3us2qech45/version.json").openStream()));
            verJson = reader.readLine();
            verOBJ = JsonParser.parseObj(verJson);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public String getVersion() {
        try {
            JsonObject obj = JsonParser.parseObj(verJson);
            version = String.valueOf(obj.get("ver"));
            return version;
        } catch (Exception e) {
            e.printStackTrace();
            return "";
        }
    }

    public String getDownloadURL() {
        try {
            JsonObject obj = JsonParser.parseObj(verJson);
            download_url = String.valueOf(obj.get("download_url"));
            return download_url;
        } catch (Exception e) {
            e.printStackTrace();
            return "";
        }
    }

    public boolean getEmergencyStatus() {

        try {

            JsonObject obj = JsonParser.parseObj(verJson);

            if (MainUtils.getInstance().isNull(obj.get("emergency_update_" + Constants.VER)))
                emergency = false;
            else emergency = (boolean) obj.get("emergency_update_" + Constants.VER);

            return emergency;

        } catch (Exception e) {

            e.printStackTrace();
            return false;

        }

    }

    public void reload() {
        try {
            reader = new BufferedReader(new InputStreamReader(new URL("https://dl.dropboxusercontent.com/s/6545j3us2qech45/version.json").openStream()));
            verJson = reader.readLine();
            verOBJ = JsonParser.parseObj(verJson);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}