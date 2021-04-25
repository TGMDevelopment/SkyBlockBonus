package ga.matthewtgm.skyblockmod.features.impl.alerts.mines;

import ga.matthewtgm.skyblockmod.features.Feature;
import ga.matthewtgm.skyblockmod.features.FeatureCategory;
import ga.matthewtgm.skyblockmod.listeners.impl.RenderListener;
import net.minecraft.util.StringUtils;
import net.minecraftforge.client.event.ClientChatReceivedEvent;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

import java.util.regex.Pattern;

public class FeatureSorrowDropAlert extends Feature {

    private Pattern sorrowMatcher = Pattern.compile("RARE DROP! Sorrow(?: \\(\\b[0-9]{1,3}\\b% Magic Find\\))?");

    public FeatureSorrowDropAlert() {
        super("Sorrow Drop Alert", FeatureCategory.OVERLAY, false);
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
    protected void onChatReceived(ClientChatReceivedEvent event) {
        if (event.type == 0 || event.type == 1) {
            String stripped = StringUtils.stripControlCodes(event.message.getUnformattedText());
            if (stripped.toLowerCase().contains("rare drop!")) {
                System.out.println(stripped);
                System.out.println(sorrowMatcher.matcher(stripped).matches());
                if (sorrowMatcher.matcher(stripped).matches()) {
                    RenderListener.getInstance().setRenderedTitle("Sorrow!", 100);
                }
            }
        }
    }

}