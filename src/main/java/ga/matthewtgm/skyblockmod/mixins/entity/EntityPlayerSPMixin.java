package ga.matthewtgm.skyblockmod.mixins.entity;

import ga.matthewtgm.skyblockmod.events.DropItemEvent;
import net.minecraft.client.entity.EntityPlayerSP;
import net.minecraft.entity.item.EntityItem;
import net.minecraftforge.common.MinecraftForge;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin({EntityPlayerSP.class})
public class EntityPlayerSPMixin {

    @Inject(method = "dropOneItem", at = @At("HEAD"), cancellable = true)
    protected void onDropOneItem(boolean dropAll, CallbackInfoReturnable<EntityItem> cir) {
        DropItemEvent.Pre event = new DropItemEvent.Pre(dropAll);
        MinecraftForge.EVENT_BUS.post(event);
        if (event.isCanceled())
            cir.cancel();
    }

    @Inject(method = "dropOneItem", at = @At("TAIL"), cancellable = true)
    protected void onDropOneItem_post(boolean dropAll, CallbackInfoReturnable<EntityItem> cir) {
        DropItemEvent.Post event = new DropItemEvent.Post(dropAll);
        MinecraftForge.EVENT_BUS.post(event);
        if (event.isCanceled())
            cir.cancel();
    }

}