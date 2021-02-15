package ga.matthewtgm.skyblockmod.features.impl.other;

import ga.matthewtgm.skyblockmod.features.Feature;
import ga.matthewtgm.skyblockmod.features.FeatureCategory;
import net.minecraft.entity.item.EntityArmorStand;
import net.minecraft.init.Blocks;
import net.minecraft.item.Item;
import net.minecraftforge.client.event.RenderLivingEvent;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

public class FeatureMidasStaffParticles extends Feature {

    public FeatureMidasStaffParticles(String name, FeatureCategory category, boolean rendered) {
        super("Hide Midas Staff Particles", FeatureCategory.OTHER, false);
    }

    @Override
    public void onEnabled() {
        MinecraftForge.EVENT_BUS.register(this);
    }

    @Override
    public void onDisabled() {
        MinecraftForge.EVENT_BUS.unregister(this);
    }

    @SubscribeEvent
    protected void onEntityRender(RenderLivingEvent<EntityArmorStand> event) {
        if(!(event.entity instanceof EntityArmorStand)) return;
        final EntityArmorStand armorStand = (EntityArmorStand) event.entity;
        if (armorStand.getCurrentArmor(3).getItem() == Item.getItemFromBlock(Blocks.gold_block)) {
            event.setCanceled(true);
        }
    }

}