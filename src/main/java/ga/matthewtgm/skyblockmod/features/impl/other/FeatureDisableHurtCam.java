package ga.matthewtgm.skyblockmod.features.impl.other;

import ga.matthewtgm.skyblockmod.features.Feature;
import ga.matthewtgm.skyblockmod.features.FeatureCategory;

public class FeatureDisableHurtCam extends Feature {

    public FeatureDisableHurtCam() {
        super("Disable Hurt Cam", FeatureCategory.RENDERING, false);
    }

    @Override
    public void onEnabled() {}

    @Override
    public void onDisabled() {}

}