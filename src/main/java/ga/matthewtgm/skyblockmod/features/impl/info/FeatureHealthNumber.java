package ga.matthewtgm.skyblockmod.features.impl.info;

import ga.matthewtgm.skyblockmod.SkyBlockBonus;
import ga.matthewtgm.skyblockmod.features.Feature;
import ga.matthewtgm.skyblockmod.features.FeatureCategory;
import ga.matthewtgm.skyblockmod.features.FeaturePosition;

public class FeatureHealthNumber extends Feature {

    public FeatureHealthNumber() {
        super("Health Number", FeatureCategory.RENDERING, true);
    }

    @Override
    public void onEnabled() {

    }

    @Override
    public void onDisabled() {

    }

    @Override
    public void onRendered(FeaturePosition position) {
        String rendered = SkyBlockBonus.getInstance().getActionBarParser().getHealth() + "/" + SkyBlockBonus.getInstance().getActionBarParser().getMaxHealth();
        this.fontRenderer.drawString(rendered, position.getX(), position.getY(), this.colour.getRGBA());
        this.width = this.fontRenderer.getStringWidth(rendered);
        this.height = 10;
    }

}