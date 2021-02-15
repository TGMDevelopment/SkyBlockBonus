package ga.matthewtgm.skyblockmod.events;

import net.minecraft.network.NetworkManager;
import net.minecraftforge.fml.common.eventhandler.Event;

public class SkyBlockLeftEvent extends Event {
    public final NetworkManager networkManager;
    public SkyBlockLeftEvent(NetworkManager networkManager) {
        this.networkManager = networkManager;
    }
}