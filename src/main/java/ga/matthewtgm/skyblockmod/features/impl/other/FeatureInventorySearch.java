package ga.matthewtgm.skyblockmod.features.impl.other;

import ga.matthewtgm.json.objects.JsonObject;
import ga.matthewtgm.skyblockmod.SkyBlockBonus;
import ga.matthewtgm.skyblockmod.features.Feature;
import ga.matthewtgm.skyblockmod.features.FeatureCategory;
import ga.matthewtgm.skyblockmod.mixins.gui.GuiContainerAccessor;
import net.minecraft.client.gui.Gui;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.client.gui.GuiTextField;
import net.minecraft.client.gui.inventory.GuiChest;
import net.minecraft.client.gui.inventory.GuiContainer;
import net.minecraft.client.gui.inventory.GuiInventory;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.inventory.Slot;
import net.minecraftforge.client.event.GuiScreenEvent;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import org.lwjgl.input.Keyboard;
import org.lwjgl.input.Mouse;

import java.awt.*;

public class FeatureInventorySearch extends Feature {

    private GuiTextField textField;

    public FeatureInventorySearch() {
        super("Inventory Search", FeatureCategory.OVERLAY, false);
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
    protected void onGuiInit(GuiScreenEvent.InitGuiEvent event) {
        if (isContainer(event.gui)) {
            textField = new GuiTextField(9990, mc.fontRendererObj, 2, event.gui.height - 22, 100, 20);
            textField.setEnabled(true);
            textField.setVisible(true);
            textField.setCanLoseFocus(true);
            textField.setEnableBackgroundDrawing(true);
        }
    }

    @SubscribeEvent
    protected void onGuiDraw(GuiScreenEvent.DrawScreenEvent.Post event) {
        if (isContainer(event.gui)) {
            textField.drawTextBox();

            GuiContainer inventory = (GuiContainer) event.gui;
            for (Slot slot : inventory.inventorySlots.inventorySlots) {
                if (slot != null && textField != null) {
                    String text = textField.getText();
                    if (slot.getStack() != null && slot.getStack().getDisplayName() != null) {
                        if (!text.isEmpty() && slot.getStack().getDisplayName().toLowerCase().contains(text.toLowerCase())) {
                            int x = ((GuiContainerAccessor) inventory).getGuiLeft() + slot.xDisplayPosition;
                            int y = ((GuiContainerAccessor) inventory).getGuiTop() + slot.yDisplayPosition;
                            Gui.drawRect(x, y, x + 16, y + 16, new Color(17, 255, 0, 128).getRGB());
                        }
                    }

                }
            }
        }
    }

    @SubscribeEvent
    protected void onGuiKeyTyped(GuiScreenEvent.KeyboardInputEvent.Pre event) {
        if (isContainer(event.gui) && Keyboard.getEventKeyState() && textField.isFocused()) {
            char eventChar = Keyboard.getEventCharacter();
            int eventCode = Keyboard.getEventKey();

            textField.textboxKeyTyped(eventChar, eventCode);

            event.setCanceled(eventCode != 1);
        }
    }

    @SubscribeEvent
    protected void onGuiMouseClicked(GuiScreenEvent.MouseInputEvent.Pre event) {
        if (isContainer(event.gui) && Mouse.getEventButtonState()) {
            int x = Mouse.getEventX() * event.gui.width / mc.displayWidth;
            int y = event.gui.height - Mouse.getEventY() * event.gui.height / mc.displayHeight - 1;
            int button = Mouse.getEventButton();

            textField.mouseClicked(x, y, button);
        }
    }

    private boolean isContainer(GuiScreen screen) {
        return ((screen.getClass().isAssignableFrom(GuiContainer.class) || screen instanceof GuiContainer) || (screen.getClass().isAssignableFrom(GuiInventory.class) || screen instanceof GuiInventory));
    }

    public GuiTextField getTextField() {
        return textField;
    }

}