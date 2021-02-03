package ga.matthewtgm.skyblockmod.features;

import ga.matthewtgm.skyblockmod.features.impl.discordrpc.FeatureDiscordRPC;
import ga.matthewtgm.skyblockmod.features.impl.playerhider.FeaturePlayerHider;
import net.minecraft.client.Minecraft;
import net.minecraftforge.client.event.RenderGameOverlayEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

import java.util.ArrayList;
import java.util.List;

public class FeatureManager {

    protected final List<Feature> features = new ArrayList<>();

    public void initFeatures() {
        this.getFeatures().add(new FeatureDiscordRPC());
        this.getFeatures().add(new FeaturePlayerHider());
        //this.getFeatures().add(new FeaturePetItemSaver());

        this.getFeatures().forEach(feature -> {
            if (feature.isToggled()) {
                feature.onEnabled();
            } else {
                feature.onDisabled();
            }
        });
    }

    @SubscribeEvent
    protected void onGameOverlayRendered(RenderGameOverlayEvent.Post event) {
        if (event.type == RenderGameOverlayEvent.ElementType.ALL) {
            for (Feature feature : this.getFeatures()) {
                if (feature.isToggled() && Minecraft.getMinecraft().currentScreen == null && Minecraft.getMinecraft().thePlayer != null)
                    feature.onRendered(feature.getRenderedPosition());
            }
        }
    }

    public <T extends Feature> T getFeature(Class<T> featureClass) {
        for (Feature feature : this.getFeatures()) {
            if (featureClass.isAssignableFrom(feature.getClass())) {
                return (T) feature;
            }
        }
        return null;
    }

    public List<Feature> getFeatures() {
        return features;
    }

}