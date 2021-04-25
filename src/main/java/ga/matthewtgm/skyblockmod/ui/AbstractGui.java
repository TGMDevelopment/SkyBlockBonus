package ga.matthewtgm.skyblockmod.ui;

import ga.matthewtgm.skyblockmod.ui.elements.AbstractGuiElement;
import lombok.Getter;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiScreen;
import org.lwjgl.input.Mouse;

import java.io.IOException;
import java.util.ArrayList;
import java.util.ConcurrentModificationException;
import java.util.List;

public abstract class AbstractGui extends GuiScreen {

    @Getter private List<AbstractGuiElement> elements = new ArrayList<>();

    private int eventButton;
    private long lastMouseEvent;

    public AbstractGui() {
        elements.clear();
    }

    @Override
    public abstract void initGui();

    @Override
    public abstract boolean doesGuiPauseGame();

    @Override
    public void handleMouseInput() {
        try {
            int mouseX = Mouse.getEventX() * this.width / this.mc.displayWidth;
            int mouseY = this.height - Mouse.getEventY() * this.height / this.mc.displayHeight - 1;
            int button = Mouse.getEventButton();
            if (getGuiRunnable() != null)
                getGuiRunnable().onMouseInput(button, mouseX, mouseY);
            if (Mouse.getEventButtonState()) {
                this.eventButton = button;
                this.lastMouseEvent = Minecraft.getSystemTime();

                if (getGuiRunnable() != null)
                    getGuiRunnable().onMouseClicked(button, mouseX, mouseY);

                for (AbstractGuiElement element : elements)
                    if (element.isMouseInBounds(mouseX, mouseY))
                        element.mouseClick(button, mouseX, mouseY);
            } else if (button != -1) {
                this.eventButton = -1;

                if (getGuiRunnable() != null)
                    getGuiRunnable().onMouseReleased(button, mouseX, mouseY);

                for (AbstractGuiElement element : elements)
                    if (element.isMouseInBounds(mouseX, mouseY))
                        element.mouseRelease(button, mouseX, mouseY);
            } else if (this.eventButton != -1 && this.lastMouseEvent > 0L) {
                long l = Minecraft.getSystemTime() - this.lastMouseEvent;

                if (getGuiRunnable() != null)
                    getGuiRunnable().onMouseDragged(button, mouseX, mouseY);

                for (AbstractGuiElement element : elements)
                    if (element.isMouseInBounds(mouseX, mouseY))
                        element.mouseDrag(button, mouseX, mouseY);
            }
        } catch (Exception e) {
            if (e instanceof ConcurrentModificationException)
                return;
            e.printStackTrace();
        }
    }

    @Override
    protected void keyTyped(char typedChar, int keyCode) throws IOException {
        try {
            for (AbstractGuiElement element : elements)
                element.keyTyped(typedChar, keyCode);

            super.keyTyped(typedChar, keyCode);
        } catch (Exception e) {
            if (e instanceof ConcurrentModificationException)
                return;
            e.printStackTrace();
        }
    }

    @Override
    public void drawScreen(int mouseX, int mouseY, float partialTicks) {
        try {
            drawDefaultBackground();
            if (getGuiRunnable() != null) {
                getGuiRunnable().onScreenDrawn(mouseX, mouseY, partialTicks);
                if (getGuiRunnable().drawScreenCanceled())
                    return;
            }
            for (AbstractGuiElement element : elements)
                element.render(mouseX, mouseY, partialTicks);
        } catch (Exception e) {
            if (e instanceof ConcurrentModificationException)
                return;
            e.printStackTrace();
        }
    }

    @Override
    public void setWorldAndResolution(Minecraft mc, int width, int height) {
        elements.clear();
        super.setWorldAndResolution(mc, width, height);
    }

    public AbstractGuiRunnable getGuiRunnable() {
        return null;
    }

    public static abstract class AbstractGuiRunnable {
        @Getter private final AbstractGui parent;
        public AbstractGuiRunnable(AbstractGui parent) {
            this.parent = parent;
        }
        public boolean drawScreenCanceled() {
            return false;
        }
        public void onScreenDrawn(int mouseX, int mouseY, float partialTicks) {}
        public void onMouseInput(int button, int mouseX, int mouseY) {}
        public void onMouseClicked(int button, int mouseX, int mouseY) {}
        public void onMouseReleased(int button, int mouseX, int mouseY) {}
        public void onMouseDragged(int button, int mouseX, int mouseY) {}
    }

}