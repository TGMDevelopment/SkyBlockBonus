package ga.matthewtgm.skyblockmod.command;

import ga.matthewtgm.lib.util.GuiScreenUtils;
import ga.matthewtgm.skyblockmod.Constants;
import ga.matthewtgm.skyblockmod.SkyBlockBonus;
import ga.matthewtgm.skyblockmod.features.Feature;
import ga.matthewtgm.skyblockmod.gui.GuiSkyBlockMod;
import ga.matthewtgm.skyblockmod.utils.ChatUtils;
import net.minecraft.command.CommandBase;
import net.minecraft.command.ICommandSender;
import net.minecraft.util.EnumChatFormatting;
import net.minecraftforge.fml.common.Loader;
import net.minecraftforge.fml.common.ModContainer;

import java.awt.*;
import java.awt.datatransfer.StringSelection;

public class SkyBlockModCommand extends CommandBase {

    @Override
    public String getCommandName() {
        return "sbb";
    }

    @Override
    public String getCommandUsage(ICommandSender sender) {
        return "/" + this.getCommandName();
    }

    @Override
    public int getRequiredPermissionLevel() {
        return -1;
    }

    @Override
    public void processCommand(ICommandSender sender, String[] args) {
        final PossibleArguments arguments = new PossibleArguments();
        arguments.noArgs(args);
        arguments.reloadFeatures(args);
        arguments.checkVersion(args);
        arguments.info(args);
    }

    private static class PossibleArguments {

        public void noArgs(String[] args) {
            if(args.length <= 0) {
                GuiScreenUtils.getInstance().open(new GuiSkyBlockMod());
                return;
            }
        }

        public void reloadFeatures(String[] args) {
            if(args[0].equalsIgnoreCase("reloadfeatures")) {
                SkyBlockBonus.getInstance().getFeatureManager().getFeatures().clear();
                SkyBlockBonus.getInstance().getFeatureManager().initFeatures();
                return;
            }
        }

        public void checkVersion(String[] args) {
            if(args[0].equalsIgnoreCase("checkver") || args[0].equalsIgnoreCase("vercheck")) {
                SkyBlockBonus.getInstance().VERSION_CHECKER.reload();
                ChatUtils.getInstance().sendModMessage("You are using " + EnumChatFormatting.GREEN + Constants.VER + EnumChatFormatting.WHITE + " and the latest version is currently " + EnumChatFormatting.GREEN + SkyBlockBonus.getInstance().VERSION_CHECKER.getVersion());
            }
        }

        public void info(String[] args) {
            if(args[0].equalsIgnoreCase("info")) {
                StringBuilder builder = new StringBuilder();
                builder.append("```md\n");
                builder.append("# Features\n");
                for (Feature feature : SkyBlockBonus.getInstance().getFeatureManager().getFeatures()) {
                    builder.append("[")
                            .append(feature.getName())
                            .append("][").append("(toggle:")
                            .append(feature.isToggled())
                            .append(")")
                            .append("]")
                            .append("\n");
                }
                if (Loader.instance().getActiveModList().size() <= 15) {
                    builder.append("\n# Mods Loaded").append("\n");
                    for (ModContainer modContainer : Loader.instance().getActiveModList()) {
                        builder.append("[").append(modContainer.getName()).append("]")
                                .append("[").append(modContainer.getSource().getName()).append("]\n");
                    }
                }
                builder.append("```");
                StringSelection clipboard = new StringSelection(builder.toString());
                Toolkit.getDefaultToolkit().getSystemClipboard().setContents(clipboard, clipboard);
                ChatUtils.getInstance().sendModMessage(EnumChatFormatting.GOLD + "Developer info copied to clipboard!");
                return;
            }
        }

    }

}