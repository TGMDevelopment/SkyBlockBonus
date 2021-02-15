package ga.matthewtgm.skyblockmod.features;

import ga.matthewtgm.json.files.JsonReader;
import ga.matthewtgm.json.objects.JsonObject;
import ga.matthewtgm.lib.util.LoggingUtils;
import ga.matthewtgm.skyblockmod.Constants;
import ga.matthewtgm.skyblockmod.SkyBlockBonus;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.FontRenderer;
import org.apache.logging.log4j.Logger;

import java.util.Random;

public abstract class Feature {

    //FEATURE INFORMATION
    private final String name;
    private final String registryName;
    private final FeatureCategory category;
    private final boolean rendered;
    //CONFIGURATIONS
    private final FeatureConfig config;
    //RENDERED INFORMATION
    public FeatureColour colour;
    public int width;
    public int height;
    //USED VARIABLES
    protected FontRenderer fontRenderer = Minecraft.getMinecraft().fontRendererObj;
    protected Minecraft mc = Minecraft.getMinecraft();
    protected Random random = new Random();
    protected Logger logger;
    private Boolean toggle;
    private FeaturePosition position;

    public Feature(String name, FeatureCategory category, boolean rendered) {
        this.name = name;
        this.registryName = name.toLowerCase().replaceAll(" ", "_");
        this.category = category;
        this.rendered = rendered;

        this.logger = LoggingUtils.getInstance().createLogger(Constants.NAME, name);

        this.config = new FeatureConfig(this);
        this.setupFeature();
    }

    private void setupFeature() {
        try {
            this.onSetup();
            this.onSave();
            this.onLoad();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void onSetup() {
    }

    public abstract void onEnabled();

    public abstract void onDisabled();

    public void onRendered(FeaturePosition position) {
    }

    public void onSave() {
        boolean isConfigNull = JsonReader.readObj(this.getRegistryName(), SkyBlockBonus.getFileHandler().featureDir) == null;

        if (this.isToggled() == null && isConfigNull) this.toggle = Boolean.FALSE;
        else if (this.isToggled() != null) this.config.put("toggle", this.isToggled());

        if (this.isRendered()) {
            if (this.getRenderedPosition() == null && isConfigNull)
                this.position = new FeaturePosition(10, 10);
            config.put("position", new JsonObject()
                    .add("x", this.getRenderedPosition().getX())
                    .add("y", this.getRenderedPosition().getY()));
            if (this.colour == null && isConfigNull)
                this.colour = new FeatureColour(255, 255, 255, 255);
            config.put("colour", new JsonObject()
                    .add("r", this.colour.getR())
                    .add("g", this.colour.getG())
                    .add("b", this.colour.getB()));
        }
        config.save();
    }

    public void onLoad() {
        this.setToggleState(config.get("toggle", Boolean.class));
        if (this.isRendered()) {
            this.setPosition(((JsonObject) config.getConfigObject().get("position")).get("x"), ((JsonObject) config.getConfigObject().get("position")).get("y"));
            this.setColour(((JsonObject) config.getConfigObject().get("colour")).get("r"), ((JsonObject) config.getConfigObject().get("colour")).get("g"), ((JsonObject) config.getConfigObject().get("colour")).get("b"));
        }
    }

    public String getName() {
        return name;
    }

    public String getRegistryName() {
        return registryName;
    }

    public FeatureConfig getConfig() {
        return config;
    }

    public Boolean isToggled() {
        return toggle;
    }

    public FeatureCategory getCategory() {
        return category;
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

    public void setColour(Object r, Object g, Object b) {
        this.colour.setR((Integer) r);
        this.colour.setG((Integer) g);
        this.colour.setB((Integer) b);
    }

    public void setPosition(Object x, Object y) {
        this.position.setX((Integer) x);
        this.position.setY((Integer) y);
    }

}