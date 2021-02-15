package ga.matthewtgm.skyblockmod;

import club.sk1er.mods.core.ModCoreInstaller;
import club.sk1er.mods.core.util.Multithreading;
import ga.matthewtgm.lib.commands.CommandManager;
import ga.matthewtgm.lib.commands.ModCommand;
import ga.matthewtgm.lib.commands.ModCommandRunnable;
import ga.matthewtgm.lib.util.ForgeUtils;
import ga.matthewtgm.lib.util.GuiScreenUtils;
import ga.matthewtgm.lib.util.keybindings.KeyBind;
import ga.matthewtgm.lib.util.keybindings.KeyBindManager;
import ga.matthewtgm.skyblockmod.features.Feature;
import ga.matthewtgm.skyblockmod.features.FeatureManager;
import ga.matthewtgm.skyblockmod.features.impl.other.FeatureLockSlots;
import ga.matthewtgm.skyblockmod.gui.GuiSkyBlockMod;
import ga.matthewtgm.skyblockmod.handlers.FileHandler;
import ga.matthewtgm.skyblockmod.listeners.impl.MobListener;
import ga.matthewtgm.skyblockmod.listeners.impl.PlayerListener;
import ga.matthewtgm.skyblockmod.listeners.impl.RenderListener;
import ga.matthewtgm.skyblockmod.listeners.impl.TickListener;
import ga.matthewtgm.skyblockmod.utils.*;
import net.minecraft.client.Minecraft;
import net.minecraft.command.CommandException;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.EnumChatFormatting;
import net.minecraftforge.fml.client.registry.ClientRegistry;
import net.minecraftforge.fml.common.Loader;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.ModContainer;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.lwjgl.input.Keyboard;

import java.awt.*;
import java.awt.datatransfer.StringSelection;
import java.util.Collections;
import java.util.List;

@Mod(name = Constants.NAME, version = Constants.VER, modid = Constants.ID)
public class SkyBlockBonus {

    private static final FileHandler FILE_HANDLER = new FileHandler();
    @Mod.Instance(Constants.ID)
    protected static SkyBlockBonus INSTANCE = new SkyBlockBonus();
    private final Logger LOGGER = LogManager.getLogger(Constants.NAME);
    private final FeatureManager FEATURE_MANAGER = new FeatureManager();
    public VersionChecker VERSION_CHECKER = new VersionChecker();
    private boolean latestVersion;

    private TickListener tickListener = new TickListener();
    private InventoryListener invListener = new InventoryListener();
    private LocationListener locListener = new LocationListener();
    private ActionBarParser actionBarParser = new ActionBarParser();
    private SkyBlockUtils skyBlockUtils = new SkyBlockUtils();

    public static SkyBlockBonus getInstance() {
        return INSTANCE;
    }

    public static FileHandler getFileHandler() {
        return FILE_HANDLER;
    }

    public void truPreInitialization() {
        Runtime.getRuntime().addShutdownHook(new Thread() {
            @Override
            public void run() {
                super.run();
                SkyBlockBonus.getInstance().shutdown();
            }
        });
        if (this.VERSION_CHECKER.getEmergencyStatus())
            throw new RuntimeException("PLEASE UPDATE TO THE NEW VERSION OF " + Constants.NAME + "\nTHIS IS AN EMERGENCY!");
        this.latestVersion = this.VERSION_CHECKER.getVersion().equals(Constants.VER);
    }

    @Mod.EventHandler
    public void preInitialize(FMLPreInitializationEvent event) {
        getFileHandler().init();
        ModCoreInstaller.initializeModCore(Minecraft.getMinecraft().mcDataDir);
        ConflictingModsLoaded.getInstance().setupValues();
    }

    @Mod.EventHandler
    public void initialize(FMLInitializationEvent event) {
        Multithreading.runAsync(() -> ForgeUtils.getInstance().registerEventListeners(
                this,
                this.getFeatureManager(),
                new RenderListener(),
                new PlayerListener(),
                new MobListener(),
                this.getTickListener(),
                this.getLocListener(),
                this.getSkyBlockUtils()
        ));
        this.getFeatureManager().initFeatures();
        KeyBindManager.getInstance().addKeyBind(Constants.NAME, new KeyBind("Open GUI", Keyboard.KEY_J) {
            @Override
            public void onPressed() {
                Minecraft.getMinecraft().displayGuiScreen(new GuiSkyBlockMod());
            }
        });
        KeyBindManager.getInstance().init();
        //TODO: ClientRegistry.registerKeyBinding(this.getFeatureManager().getFeature(FeatureLockSlots.class).getLockSlotKeybinding());
        CommandManager.getInstance().register(new ModCommand(new ModCommandRunnable() {
            @Override
            public String getCommandString() {
                return "skyblockbonus";
            }

            @Override
            public String getCommandUsage() {
                return "/" + this.getCommandString();
            }

            @Override
            public int getRequiredPermissionLevel() {
                return -1;
            }

            @Override
            public List<String> getCommandAliases() {
                return Collections.singletonList("sbb");
            }

            @Override
            public void process(EntityPlayer sender, String[] args) throws CommandException {
                if (args.length <= 0) {
                    GuiScreenUtils.getInstance().open(new GuiSkyBlockMod());
                    return;
                }
                switch (args[0].toLowerCase()) {
                    default:
                        ChatUtils.getInstance().sendModMessage("Unrecognized argument.");
                        break;
                    case "reloadfeatures":
                        SkyBlockBonus.getInstance().getFeatureManager().getFeatures().clear();
                        SkyBlockBonus.getInstance().getFeatureManager().initFeatures();
                        break;
                    case "checkver":
                        SkyBlockBonus.getInstance().VERSION_CHECKER.reload();
                        ChatUtils.getInstance().sendModMessage("You are using " + EnumChatFormatting.GREEN + Constants.VER + EnumChatFormatting.WHITE + " and the latest version is currently " + EnumChatFormatting.GREEN + SkyBlockBonus.getInstance().VERSION_CHECKER.getVersion());
                        break;
                    case "info":
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
                        ChatUtils.getInstance().sendModMessage(EnumChatFormatting.GREEN + "Developer info copied to clipboard!");
                }
            }
        }));
    }

    public void shutdown() {
    }

    public boolean isLatestVersion() {
        return latestVersion;
    }

    public Logger getLogger() {
        return LOGGER;
    }

    public FeatureManager getFeatureManager() {
        return FEATURE_MANAGER;
    }

    public TickListener getTickListener() {
        return tickListener;
    }

    public InventoryListener getInvListener() {
        return invListener;
    }

    public LocationListener getLocListener() {
        return locListener;
    }

    public ActionBarParser getActionBarParser() {
        return actionBarParser;
    }

    public SkyBlockUtils getSkyBlockUtils() {
        return skyBlockUtils;
    }

}