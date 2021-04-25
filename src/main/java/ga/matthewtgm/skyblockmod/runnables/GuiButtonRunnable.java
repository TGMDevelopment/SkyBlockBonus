package ga.matthewtgm.skyblockmod.runnables;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiScreen;

public abstract class GuiButtonRunnable {
    protected GuiScreen currentScreen = Minecraft.getMinecraft().currentScreen;

    protected String text;

    public GuiButtonRunnable(String text) {
        this.text = text;
    }

    public abstract int getId();
    public abstract int getX();
    public abstract int getY();
    public abstract int getWidth();
    public abstract int getHeight();

    public abstract void onActionPerformed();

    public String getText() {
        return text;
    }
}