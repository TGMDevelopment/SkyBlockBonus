package ga.matthewtgm.skyblockmod.listeners.impl;

import ga.matthewtgm.skyblockmod.SkyBlockBonus;
import ga.matthewtgm.skyblockmod.listeners.SkyBlockModListener;
import net.minecraft.client.Minecraft;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.TickEvent;

public class TickListener extends SkyBlockModListener {

    private int tickTimer = 1;

    @SubscribeEvent
    protected void onClientTick(TickEvent.ClientTickEvent event) {
        if (event.phase == TickEvent.Phase.START) {
            tickTimer++;
            if (tickTimer % 10 == 0) {
                if (Minecraft.getMinecraft().thePlayer != null) {
                    if (Minecraft.getMinecraft().thePlayer.inventory != null) {
                        if (Minecraft.getMinecraft().thePlayer.inventory.mainInventory != null) {
                            SkyBlockBonus.getInstance().getInvListener().getInvDiff(Minecraft.getMinecraft().thePlayer.inventory.mainInventory);
                        }
                    }
                }
            }
        }
    }

}