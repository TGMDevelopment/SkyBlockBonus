package ga.matthewtgm.skyblockmod.features.impl;

import ga.matthewtgm.skyblockmod.features.Feature;
import ga.matthewtgm.skyblockmod.features.FeatureCategory;
import ga.matthewtgm.skyblockmod.features.FeaturePosition;
import net.minecraft.client.Minecraft;

public class FeatureTest extends Feature {

    public FeatureTest() {
        super("Test", FeatureCategory.OTHER, true);
    }

    @Override
    public void onEnabled() {

    }

    @Override
    public void onDisabled() {

    }

    @Override
    public void onRendered(FeaturePosition position) {
        final String a = "[" + Minecraft.getDebugFPS() + "]";
        this.fontRenderer.drawString(a, position.getX(), position.getY(), this.colour.getRGBA());
        this.width = fontRenderer.getStringWidth(a);
        this.height = 10;
    }

}