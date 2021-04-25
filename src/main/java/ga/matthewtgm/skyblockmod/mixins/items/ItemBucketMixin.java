package ga.matthewtgm.skyblockmod.mixins.items;

import ga.matthewtgm.skyblockmod.SkyBlockBonus;
import ga.matthewtgm.skyblockmod.features.impl.other.FeatureBetterMagicalWaterBucket;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemBucket;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.world.World;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Redirect;

@Mixin({ItemBucket.class})
public class ItemBucketMixin {

    @Redirect(method = "onItemRightClick", at = @At(value = "NEW", target = "net/minecraft/item/ItemStack"))
    protected ItemStack betterMagicalWaterBucket(Item item, ItemStack itemStack, World world, EntityPlayer player) {
        if (SkyBlockBonus.getInstance().getFeatureManager().getFeatureToggle(FeatureBetterMagicalWaterBucket.class) && itemStack.hasTagCompound()) {
            NBTTagCompound extra = itemStack.getTagCompound().getCompoundTag("ExtraAttributes");
            String id = extra.getString("id");

            if (id.equals("MAGICAL_WATER_BUCKET")) {
                return itemStack;
            }
        }
        return new ItemStack(item);
    }

}