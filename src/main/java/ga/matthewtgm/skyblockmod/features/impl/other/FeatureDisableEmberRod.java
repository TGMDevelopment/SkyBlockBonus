package ga.matthewtgm.skyblockmod.features.impl.other;

import ga.matthewtgm.json.files.JsonReader;
import ga.matthewtgm.skyblockmod.SkyBlockBonus;
import ga.matthewtgm.skyblockmod.events.ModChatMessageSentEvent;
import ga.matthewtgm.skyblockmod.features.Feature;
import ga.matthewtgm.skyblockmod.features.FeatureCategory;
import ga.matthewtgm.skyblockmod.runnables.GuiButtonRunnable;
import ga.matthewtgm.skyblockmod.skyblock.EnumLocation;
import ga.matthewtgm.skyblockmod.utils.ChatUtils;
import ga.matthewtgm.skyblockmod.utils.ItemStackUtils;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.util.EnumChatFormatting;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.entity.player.PlayerInteractEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

public class FeatureDisableEmberRod extends Feature {

    private Boolean onlyBlockOnIsland;

    public FeatureDisableEmberRod() {
        super("Disable Ember Rods", FeatureCategory.OTHER, false);
        this.getAdditionalConfigButtons().add(new GuiButtonRunnable("Only block on island: " + (onlyBlockOnIsland ? EnumChatFormatting.GREEN + "ON" : EnumChatFormatting.RED + "OFF")) {
            @Override
            public int getId() {
                return 100;
            }

            @Override
            public int getX() {
                return mc.currentScreen.width / 2 - 60;
            }

            @Override
            public int getY() {
                return mc.currentScreen.height / 2 - 20;
            }

            @Override
            public int getWidth() {
                return 120;
            }

            @Override
            public int getHeight() {
                return 20;
            }

            @Override
            public void onActionPerformed() {
                onlyBlockOnIsland = !onlyBlockOnIsland;
                text = "Only block on island: " + (onlyBlockOnIsland ? EnumChatFormatting.GREEN + "ON" : EnumChatFormatting.RED + "OFF");
            }
        });
    }

    @Override
    public void onEnabled() {
        MinecraftForge.EVENT_BUS.register(this);
    }

    @Override
    public void onDisabled() {
        MinecraftForge.EVENT_BUS.unregister(this);
    }

    @Override
    public void onSave() {
        super.onSave();
        boolean isConfigNull = JsonReader.readObj(this.getRegistryName(), SkyBlockBonus.getFileHandler().featureDir) == null;

        if (this.onlyBlockOnIsland == null && isConfigNull) this.onlyBlockOnIsland = Boolean.TRUE;
        else if (this.onlyBlockOnIsland != null) this.getConfig().put("only_block_on_island", this.onlyBlockOnIsland);
    }

    @Override
    public void onLoad() {
        super.onLoad();
        this.onlyBlockOnIsland = this.getConfig().get("only_block_on_island", Boolean.class);
    }

    @SubscribeEvent
    protected void onItemInteract(PlayerInteractEvent event) {
        if (event.action == PlayerInteractEvent.Action.RIGHT_CLICK_AIR || event.action == PlayerInteractEvent.Action.RIGHT_CLICK_BLOCK) {
            if (event.entityPlayer == null) return;
            if (event.entityPlayer.getHeldItem() == null) return;
            if (SkyBlockBonus.getInstance().getLocListener().getLocation() == EnumLocation.ISLAND && this.onlyBlockOnIsland)
                return;
            EntityPlayer player = event.entityPlayer;
            ItemStack held = player.getHeldItem();
            String itemId = ItemStackUtils.getInstance().getSkyBlockId(held);
            if (itemId == null) return;
            if (!itemId.contains("EMBER_ROD")) return;
            event.setCanceled(true);
            ChatUtils.getInstance().sendModMessage(ModChatMessageSentEvent.Type.FEATURE, "A feature has stopped you from using this item!");
        }
    }

}