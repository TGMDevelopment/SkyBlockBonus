package ga.matthewtgm.skyblockmod.features.impl.discordrpc;

import ga.matthewtgm.skyblockmod.features.Feature;
import ga.matthewtgm.skyblockmod.features.FeatureCategory;
import ga.matthewtgm.skyblockmod.utils.ConflictingModsLoaded;

import java.util.ArrayList;

public class FeatureDiscordRPC extends Feature {

    public FeatureDiscordRPC() {
        super("DiscordRPC", FeatureCategory.OTHER, false);
    }

    @Override
    public void onEnabled() {
        //if (ConflictingModsLoaded.getInstance().isSkyBlockAddonsLoaded) return;
        DiscordRPC.INSTANCE.start();
    }

    @Override
    public void onDisabled() {
        //if (ConflictingModsLoaded.getInstance().isSkyBlockAddonsLoaded) return;
        DiscordRPC.INSTANCE.stop();
    }

}