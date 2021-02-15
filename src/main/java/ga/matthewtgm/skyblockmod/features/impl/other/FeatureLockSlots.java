package ga.matthewtgm.skyblockmod.features.impl.other;

import com.google.gson.internal.LinkedTreeMap;
import ga.matthewtgm.json.objects.JsonArray;
import ga.matthewtgm.skyblockmod.Constants;
import ga.matthewtgm.skyblockmod.SkyBlockBonus;
import ga.matthewtgm.skyblockmod.events.ProfileChangedEvent;
import ga.matthewtgm.skyblockmod.features.Feature;
import ga.matthewtgm.skyblockmod.features.FeatureCategory;
import net.minecraft.client.settings.KeyBinding;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import org.lwjgl.input.Keyboard;

import java.util.*;


//TODO: Fix locked slot loading.
public class FeatureLockSlots extends Feature {

    private Map<String, Set<Integer>> profileLockedSlots;
    private final KeyBinding lockSlotKeybinding = new KeyBinding("Lock Slot", Keyboard.KEY_LMENU, Constants.NAME);

    public FeatureLockSlots() {
        super("Lock Slots", FeatureCategory.OTHER, false);
    }

    @Override
    public void onSave() {
        super.onSave();
        try {
            this.profileLockedSlots = new HashMap<>();
            final LinkedTreeMap<String, Object> slots = new LinkedTreeMap<>();
            this.profileLockedSlots.entrySet().forEach(profile -> {
                JsonArray slotArray = new JsonArray();
                profile.getValue().forEach(theSlot -> {
                    slotArray.plus(theSlot);
                });
                slots.put(profile.getKey(), slotArray);
            });
            this.getConfig().getConfigObject().put("slots", slots);
        } catch (Exception ignored) {
        }
    }

    @Override
    public void onLoad() {
        super.onLoad();
        try {
            LinkedTreeMap profileObject = (LinkedTreeMap) this.getConfig().getConfigObject().get("slots");
            Set<Integer> lockedSlots = new HashSet<>();
            ((List) profileObject.get(SkyBlockBonus.getInstance().getSkyBlockUtils().getProfileName())).forEach(theSlot -> {
                if (theSlot.getClass().isAssignableFrom(Integer.class)) {
                    System.out.println((int) Math.round((Integer) theSlot));
                    lockedSlots.add((int) Math.round((Integer) theSlot));
                } else if (theSlot.getClass().isAssignableFrom(Double.class)) {
                    System.out.println((int) Math.round((Double) theSlot));
                    lockedSlots.add((int) Math.round((Double) theSlot));
                }
            });
            System.out.println(SkyBlockBonus.getInstance().getSkyBlockUtils().getProfileName());
            System.out.println(profileObject);
            System.out.println(lockedSlots);
            System.out.println(this.profileLockedSlots);
            System.out.println(this.getProfileLockedSlots());
            profileLockedSlots.put(SkyBlockBonus.getInstance().getSkyBlockUtils().getProfileName(), lockedSlots);
            System.out.println(this.profileLockedSlots);
            System.out.println(this.getProfileLockedSlots());
        } catch (Exception e) {
            e.printStackTrace();
        }
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
    protected void onProfileChanged(ProfileChangedEvent event) {
        this.onSave();
        this.onLoad();
    }

    public Set<Integer> getProfileLockedSlots() {
        try {
            String profile = SkyBlockBonus.getInstance().getSkyBlockUtils().getProfileName();
            if (profile != null) {
                if (!this.profileLockedSlots.containsKey(profile)) {
                    this.profileLockedSlots.put(profile, new HashSet<>());
                }
                return this.profileLockedSlots.get(profile);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public KeyBinding getLockSlotKeybinding() {
        return lockSlotKeybinding;
    }

}