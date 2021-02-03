package ga.matthewtgm.skyblockmod.features.impl.playerhider;

import ga.matthewtgm.skyblockmod.features.Feature;

import java.util.ArrayList;

public class FeaturePlayerHider extends Feature {

    private static boolean shouldHidePlayers;

    public FeaturePlayerHider() {
        super("Player Hider", false);
    }

    public static boolean getShouldHidePlayers() {
        return shouldHidePlayers;
    }

    public static void setShouldHidePlayers(boolean shouldHidePlayers) {
        FeaturePlayerHider.shouldHidePlayers = shouldHidePlayers;
    }

    @Override
    public void onEnabled() {
        setShouldHidePlayers(true);
    }

    @Override
    public void onDisabled() {
        setShouldHidePlayers(false);
    }

}