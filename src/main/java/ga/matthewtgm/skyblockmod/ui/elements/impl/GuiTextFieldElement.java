package ga.matthewtgm.skyblockmod.ui.elements.impl;

import ga.matthewtgm.lib.util.RenderUtils;
import ga.matthewtgm.skyblockmod.ui.elements.AbstractGuiElement;
import lombok.Getter;
import lombok.Setter;

import java.awt.*;

public class GuiTextFieldElement extends AbstractGuiElement {

    @Getter private GuiTextFieldElementColour colour;

    @Getter @Setter private boolean focused;
    @Getter @Setter private String text;

    public GuiTextFieldElement(int x, int y, int width, int height, GuiTextFieldElementColour colour) {
        super(x, y, width, height);
        this.colour = colour;
    }

    @Override
    public void render(int mouseX, int mouseY, float partialTicks) {
        RenderUtils.getInstance().drawRoundedRect(getX(), getY(), getX() + getWidth(), getY() + getHeight(), 10, isFocused() ? colour.getFocusedColour() : colour.getColour());
        mc.fontRendererObj.drawString(text, getX(), getY() + (getHeight() - 8) / 2, isFocused() ? colour.getFocusedTextColour().getRGB() : colour.getTextColour().getRGB());
    }

    @Override
    public void keyTyped(char typedChar, int keyCode) {
        text += typedChar;
        System.out.println(text);
    }

    public static abstract class GuiTextFieldElementColour {
        public abstract Color getColour();
        public abstract Color getFocusedColour();
        public abstract Color getTextColour();
        public abstract Color getFocusedTextColour();
        public static class Default extends GuiTextFieldElementColour {
            @Override
            public Color getColour() {
                return new Color(215, 191, 0, 102);
            }
            @Override
            public Color getFocusedColour() {
                return new Color(248, 178, 0, 102);
            }
            @Override
            public Color getTextColour() {
                return new Color(255, 255, 255, 255);
            }
            @Override
            public Color getFocusedTextColour() {
                return new Color(161, 161, 161, 143);
            }
        }
    }

}