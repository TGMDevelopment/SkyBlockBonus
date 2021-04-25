package ga.matthewtgm.skyblockmod.features.impl.other;

import ga.matthewtgm.skyblockmod.features.Feature;
import ga.matthewtgm.skyblockmod.features.FeatureCategory;

public class FeatureDisableBlockParticles extends Feature {

    public FeatureDisableBlockParticles() {
        super("Disable Block Particles", FeatureCategory.RENDERING, false);
    }

    @Override
    public void onEnabled() {}

    @Override
    public void onDisabled() {}

}