package ga.matthewtgm.skyblockmod.features;

public enum FeatureCategory {

    CHAT("Chat"),
    RENDERING("Rendering"),
    OVERLAY("Overlay"),
    DUNGEONS("Dungeons"),
    MAYOR("Mayor"),
    OTHER("Other");

    private final String properName;

    FeatureCategory(String properName) {
        this.properName = properName;
    }

    public String getProperName() {
        return properName;
    }

}