package ga.matthewtgm.skyblockmod.custommenus;

import ga.matthewtgm.skyblockmod.custommenus.impl.CustomSkyBlockMenu;
import lombok.Getter;
import net.minecraft.client.gui.inventory.GuiContainer;
import net.minecraft.inventory.ContainerChest;
import net.minecraftforge.client.event.GuiOpenEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

import java.util.ArrayList;
import java.util.List;

public class CustomMenuManager {

    @Getter private final List<CustomMenu> customMenus = new ArrayList<>();

    public void init() {
        customMenus.add(new CustomSkyBlockMenu());
    }

    @SubscribeEvent
    protected void onGuiOpen(GuiOpenEvent event) {
        if (event.gui instanceof GuiContainer) {
            GuiContainer container = (GuiContainer) event.gui;
            System.out.println(container);
            if (container.inventorySlots instanceof ContainerChest) {
                ContainerChest chest = (ContainerChest) container.inventorySlots;
                String name = chest.getLowerChestInventory().getDisplayName().getUnformattedText();
                String lowerName = name.toLowerCase();
                System.out.println(lowerName);
                for (CustomMenu menu : customMenus)
                    if (lowerName.equalsIgnoreCase(menu.parentName()))
                        event.gui = menu;
            }
        }
    }

}