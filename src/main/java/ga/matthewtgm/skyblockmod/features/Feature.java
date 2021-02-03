package ga.matthewtgm.skyblockmod.features;

import ga.matthewtgm.json.files.JsonReader;
import ga.matthewtgm.json.files.JsonWriter;
import ga.matthewtgm.json.objects.JsonObject;
import ga.matthewtgm.lib.util.LoggingUtils;
import ga.matthewtgm.skyblockmod.Constants;
import ga.matthewtgm.skyblockmod.SkyBlockBonus;
import ga.matthewtgm.skyblockmod.utils.FeatureUtils;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.FontRenderer;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.Random;

public abstract class Feature {

    //FEATURE INFORMATION
    private final String name;
    private final String registryName;
    private final boolean rendered;

    //RENDERED INFORMATION
    public int width;
    public int height;

    //USED VARIABLES
    protected FontRenderer fontRenderer = Minecraft.getMinecraft().fontRendererObj;
    protected Minecraft mc = Minecraft.getMinecraft();
    protected Random random = new Random();
    protected Logger logger;

    //CONFIGURATIONS
    private boolean toggle;
    private FeaturePosition position;

    public Feature(String name, boolean rendered) {
        this.name = name;
        this.registryName = name.toLowerCase().replaceAll(" ", "_");
        this.rendered = rendered;

        this.logger = LoggingUtils.getInstance().createLogger(name);

        this.setupModule();
    }

    private void setupModule() {
        try {
            this.getLogger().info("Setting up " + this.getName());
            this.onSave(new JsonObject());
            this.onSetup();
            this.onLoad();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void onSetup() {
        FeatureUtils.getInstance().setupConfigs(this);
    }

    public abstract void onEnabled();

    public abstract void onDisabled();

    public void onRendered(FeaturePosition position) {
    }

    public void onSave(JsonObject object) {
        object.put("toggle", this.isToggled());
        if (this.isRendered()) {
            JsonObject positionObject = new JsonObject();
            positionObject
                    .add("x", this.getRenderedPosition().getX())
                    .add("y", this.getRenderedPosition().getY());
            object.put("position", positionObject);
        }
        JsonWriter.write(this.getRegistryName(), object, SkyBlockBonus.getFileHandler().featureDir);
    }

    public void onLoad() {
        final JsonObject featureObj = JsonReader.readObj(this.getRegistryName(), SkyBlockBonus.getFileHandler().featureDir);
        this.setToggleState(Boolean.parseBoolean(String.valueOf(featureObj.get("toggle"))));
        if (this.isRendered()) {
            final JsonObject positionObj = (JsonObject) featureObj.get("position");
            this.setPosition(
                    Integer.parseInt(String.valueOf(positionObj.get("x"))),
                    Integer.parseInt(String.valueOf(positionObj.get("y")))
            );
        }
    }

    public String getName() {
        return name;
    }

    public String getRegistryName() {
        return registryName;
    }

    public boolean isToggled() {
        return toggle;
    }

    public boolean isRendered() {
        return rendered;
    }

    public FeaturePosition getRenderedPosition() {
        return position;
    }

    public Logger getLogger() {
        return logger;
    }

    public void setToggleState(boolean toggleState) {
        this.toggle = toggleState;
    }

    public void setPosition(FeaturePosition position) {
        this.position = position;
    }

    public void setPosition(int x, int y) {
        this.position.setX(x);
        this.position.setY(y);
    }

}