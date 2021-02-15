package ga.matthewtgm.skyblockmod.events;

import net.minecraftforge.fml.common.eventhandler.Event;

public class ProfileChangedEvent extends Event {
    public final String profile;
    public ProfileChangedEvent(String profile) {
        this.profile = profile;
    }
}