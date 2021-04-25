package ga.matthewtgm.skyblockmod.mixins.client;

import com.google.gson.internal.LinkedTreeMap;
import ga.matthewtgm.json.objects.JsonArray;
import ga.matthewtgm.json.parsing.JsonParser;
import ga.matthewtgm.lib.util.WebUtils;
import ga.matthewtgm.skyblockmod.SkyBlockBonus;
import ga.matthewtgm.skyblockmod.features.impl.cosmetics.FeatureColouredNames;
import net.minecraft.client.gui.FontRenderer;
import net.minecraft.util.EnumChatFormatting;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.ModifyVariable;

@Mixin({FontRenderer.class})
public class FontRendererMixin {

    private JsonArray colouredNames = initializeSpecialNameMap();

    @ModifyVariable(method = "drawString(Ljava/lang/String;FFIZ)I", at = @At("HEAD"), argsOnly = true)
    private String merge(String text) {
        if (colouredNames != null && !colouredNames.isEmpty()) {
            for (Object o : colouredNames) {
                if (o.getClass().isAssignableFrom(LinkedTreeMap.class)) {
                    LinkedTreeMap object = (LinkedTreeMap) o;
                    if (object.get("name").equals(text)) {
                        return EnumChatFormatting.getValueByName(object.get("colour").toString()) + text;
                    }
                }
            }
        } else {
            colouredNames = initializeSpecialNameMap();
        }
        return text;
    }

    private JsonArray initializeSpecialNameMap() {
        return JsonParser.parseArr(WebUtils.getInstance().getJsonOnline("https://raw.githubusercontent.com/TGMDevelopment/SkyBlock-Bonus-Data/main/other/chat/custonames.json"));
    }

}