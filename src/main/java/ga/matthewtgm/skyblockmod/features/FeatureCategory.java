package ga.matthewtgm.skyblockmod.features;

public enum FeatureCategory {

    CHAT("Chat"),
    GUI("Gui"),
    RENDERING("Rendering"),
    OVERLAY("Overlay"),
    DUNGEONS("Dungeons"),
    TRACKING("Tracking"),
    COSMETICS("Cosmetics"),
    OTHER("Other");

    private final String properName;

    FeatureCategory(String properName) {
        this.properName = properName;
    }

    public String getProperName() {
        return properName;
    }

}