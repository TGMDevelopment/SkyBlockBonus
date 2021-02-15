package ga.matthewtgm.skyblockmod.utils;

import ga.matthewtgm.skyblockmod.events.InventoryUpdateEvent;
import net.minecraft.item.ItemStack;
import net.minecraft.util.EnumChatFormatting;
import net.minecraftforge.common.MinecraftForge;

import java.util.*;

public class InventoryListener {

    private List<ItemStack> prevInv;

    public void getInvDiff(ItemStack[] currentInv) {
        List<ItemStack> newInv = this.copyInventory(currentInv);
        Map<String, Integer> previousInventoryMap = new HashMap<>();
        Map<String, Integer> newInventoryMap = new HashMap<>();
        Map<String, ItemStack> currentInvMap = new HashMap<>();

        for (ItemStack itemStack : currentInv) {
            if(itemStack != null) {
                currentInvMap.put(itemStack.getDisplayName(), itemStack);
            }
        }

        if (prevInv != null) {
            for(int i = 0; i < newInv.size(); i++) {
                ItemStack prev = prevInv.get(i);
                ItemStack next = newInv.get(i);

                if(prev != null) {
                    int amount = previousInventoryMap.getOrDefault(prev.getDisplayName(), 0) + prev.stackSize;
                    previousInventoryMap.put(prev.getDisplayName(), amount);
                }

                if(next != null) {
                    if (next.getDisplayName().contains(" " + EnumChatFormatting.GRAY + "x")) {
                        String newName = next.getDisplayName().substring(0, next.getDisplayName().lastIndexOf(" "));
                        next.setStackDisplayName(newName);
                    }
                    int amount = newInventoryMap.getOrDefault(next.getDisplayName(), 0) + next.stackSize;
                    newInventoryMap.put(next.getDisplayName(), amount);
                }
            }

            List<InvDiff> invDiffs = new LinkedList<>();
            Set<String> keySet = new HashSet<>(previousInventoryMap.keySet());
            keySet.addAll(newInventoryMap.keySet());

            keySet.forEach(key -> {
                int previousAmount = previousInventoryMap.getOrDefault(key, 0);
                int newAmount = newInventoryMap.getOrDefault(key, 0);
                int diff = newAmount - previousAmount;
                if (diff != 0) {
                    invDiffs.add(new InvDiff(key, currentInvMap.get(key), diff));
                }
            });

            for(InvDiff invDiff : invDiffs) {
                MinecraftForge.EVENT_BUS.post(new InventoryUpdateEvent(invDiff));
            }
        }

        prevInv = newInv;
    }

    private List<ItemStack> copyInventory(ItemStack[] currentInv) {
        List<ItemStack> copy = new ArrayList<>(currentInv.length);
        for (ItemStack item : currentInv) {
            if (item != null) {
                copy.add(ItemStack.copyItemStack(item));
            } else {
                copy.add(null);
            }
        }
        return copy;
    }

}