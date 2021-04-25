package ga.matthewtgm.skyblockmod.custommenus;

import ga.matthewtgm.skyblockmod.ui.AbstractGui;
import net.minecraft.client.Minecraft;
import net.minecraft.network.play.client.C0DPacketCloseWindow;

public abstract class CustomMenu extends AbstractGui {

    @Override
    public abstract void initGui();

    public abstract String parentName();

    @Override
    public void onGuiClosed() {
        Minecraft.getMinecraft().thePlayer.sendQueue.addToSendQueue(new C0DPacketCloseWindow());
    }

    @Override
    public boolean doesGuiPauseGame() {
        return false;
    }

}