package ga.matthewtgm.skyblockmod.events;

import ga.matthewtgm.skyblockmod.utils.InvDiff;
import net.minecraftforge.fml.common.eventhandler.Event;

public class InventoryUpdateEvent extends Event {
    public final InvDiff diff;
    public InventoryUpdateEvent(InvDiff diff) {
        this.diff = diff;
    }
}