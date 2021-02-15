package ga.matthewtgm.skyblockmod.features.impl.info;

import ga.matthewtgm.skyblockmod.SkyBlockBonus;
import ga.matthewtgm.skyblockmod.features.Feature;
import ga.matthewtgm.skyblockmod.features.FeatureCategory;
import ga.matthewtgm.skyblockmod.features.FeaturePosition;

public class FeatureDefenseNumber extends Feature {

    public FeatureDefenseNumber() {
        super("Defense Number", FeatureCategory.RENDERING, true);
    }

    @Override
    public void onEnabled() {

    }

    @Override
    public void onDisabled() {

    }

    @Override
    public void onRendered(FeaturePosition position) {
        this.fontRenderer.drawString(SkyBlockBonus.getInstance().getActionBarParser().getDefense(), position.getX(), position.getY(), this.colour.getRGBA());
        this.width = this.fontRenderer.getStringWidth(SkyBlockBonus.getInstance().getActionBarParser().getDefense());
        this.height = 10;
    }

}