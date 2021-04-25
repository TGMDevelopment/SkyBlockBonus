package ga.matthewtgm.skyblockmod.features;

import ga.matthewtgm.skyblockmod.Constants;
import ga.matthewtgm.skyblockmod.features.impl.alerts.mines.FeatureSorrowDropAlert;
import ga.matthewtgm.skyblockmod.features.impl.cosmetics.FeatureColouredNames;
import ga.matthewtgm.skyblockmod.features.impl.discordrpc.FeatureDiscordRPC;
import ga.matthewtgm.skyblockmod.features.impl.dungeons.FeatureChecksStatsOnJoin;
import ga.matthewtgm.skyblockmod.features.impl.other.*;
import ga.matthewtgm.skyblockmod.features.impl.other.FeaturePetItemSaver;
import ga.matthewtgm.skyblockmod.features.impl.playerhider.FeaturePlayerHider;
import ga.matthewtgm.skyblockmod.features.impl.raredropmessage.FeatureRareDropMessage;
import ga.matthewtgm.skyblockmod.features.impl.trackers.revstonetracker.FeatureReviveStoneTracker;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraftforge.client.event.RenderGameOverlayEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.ArrayList;
import java.util.List;

public class FeatureManager {

    private final Logger logger = LogManager.getLogger(Constants.NAME + " (" + getClass().getSimpleName() + ")");
    protected final List<Feature> features = new ArrayList<>();

    public void init() {

        /* Features */
        registerFeature(new FeatureDiscordRPC());
        registerFeature(new FeaturePlayerHider());
        registerFeature(new FeaturePetItemSaver());
        registerFeature(new FeatureRareDropMessage());
        registerFeature(new FeatureDisableEmberRod());
        registerFeature(new FeatureDisableAOTD());
        registerFeature(new FeatureReviveStoneTracker());
        registerFeature(new FeatureSkyBlockId());
        registerFeature(new FeatureDisableHurtCam());
        registerFeature(new FeatureInventorySearch());
        registerFeature(new FeatureHideCreeperVeil());
        registerFeature(new FeatureHidePlayersNearNPCs());
        registerFeature(new FeatureChecksStatsOnJoin());
        // TODO: FIX BUGS  |  registerFeature(new FeatureDoubleTapToDrop());
        //registerFeature(new FeatureColouredNames());
        registerFeature(new FeatureSorrowDropAlert());
        registerFeature(new FeatureTransparentSkinRendering());
        registerFeature(new FeatureSkinRenderingFix());
        registerFeature(new FeatureBetterMagicalWaterBucket());
        registerFeature(new FeatureDisableBlockParticles());

        /* Trackers */
        // TODO: BETTER MOB DETECTION  |   registerFeature(new TrackerFishing());

        getFeatures().forEach(feature -> {
            if (feature.isToggled()) {
                feature.onEnabled();
            } else {
                feature.onDisabled();
            }
        });
    }

    public void reset() {
        features.clear();
        init();
    }

    public void registerFeature(Feature feature) {
        logger.info(String.format("Registering feature: %s{%s, %s, %s}", feature.getName(), feature.getRegistryName(), feature.getCategory(), feature.getConfig().getConfigObject()));
        features.add(feature);
    }

    public int getFeatureCount(boolean enabledOnly) {
        if (!enabledOnly)
            return features.size();
        else
            return (int) features.stream().filter(Feature::isToggled).count();
    }

    @SubscribeEvent
    protected void onGameOverlayRendered(RenderGameOverlayEvent.Post event) {
        if (event.type == RenderGameOverlayEvent.ElementType.ALL) {
            for (Feature feature : this.getFeatures()) {
                if (feature.isToggled() && Minecraft.getMinecraft().currentScreen == null && Minecraft.getMinecraft().thePlayer != null) {
                    GlStateManager.color(1, 1, 1);
                    feature.onRendered(feature.getRenderedPosition());
                }
            }
            GlStateManager.color(1, 1, 1);
        }
    }

    public <T extends Feature> T getFeature(Class<T> featureClass) {
        for (Feature feature : getFeatures()) {
            if (featureClass.isAssignableFrom(feature.getClass())) {
                return (T) feature;
            }
        }
        return null;
    }

    public <T extends Feature> boolean getFeatureToggle(Class<T> featureClass) {
        for (Feature feature : getFeatures()) {
            if (featureClass.isAssignableFrom(feature.getClass())) {
                return ((T) feature).isToggled();
            }
        }
        return false;
    }

    public <T extends Feature> boolean isFeatureRegistered(Class<T> featureClass) {
        boolean registered = false;
        for (Feature feature : getFeatures()) {
            if (feature.getClass().isAssignableFrom(featureClass)) registered = true;
        }
        return registered;
    }

    public List<Feature> getFeatures() {
        return features;
    }

}