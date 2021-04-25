package ga.matthewtgm.skyblockmod;

import com.google.gson.Gson;
import ga.matthewtgm.lib.commands.CommandManager;
import ga.matthewtgm.lib.commands.ModCommand;
import ga.matthewtgm.lib.commands.ModCommandRunnable;
import ga.matthewtgm.lib.util.ForgeUtils;
import ga.matthewtgm.lib.util.GuiScreenUtils;
import ga.matthewtgm.lib.util.keybindings.KeyBind;
import ga.matthewtgm.lib.util.keybindings.KeyBindManager;
import ga.matthewtgm.lib.util.threading.ThreadUtils;
import ga.matthewtgm.skyblockmod.custommenus.CustomMenuManager;
import ga.matthewtgm.skyblockmod.events.ModChatMessageSentEvent;
import ga.matthewtgm.skyblockmod.features.Feature;
import ga.matthewtgm.skyblockmod.features.FeatureManager;
import ga.matthewtgm.skyblockmod.gui.GuiSkyBlockMod;
import ga.matthewtgm.skyblockmod.handlers.FileHandler;
import ga.matthewtgm.skyblockmod.listeners.impl.MobListener;
import ga.matthewtgm.skyblockmod.listeners.impl.PlayerListener;
import ga.matthewtgm.skyblockmod.listeners.impl.RenderListener;
import ga.matthewtgm.skyblockmod.listeners.impl.TickListener;
import ga.matthewtgm.skyblockmod.utils.*;
import net.minecraft.client.Minecraft;
import net.minecraft.client.entity.EntityPlayerSP;
import net.minecraft.command.CommandException;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.EnumChatFormatting;
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
import java.util.*;
import java.util.List;

@Mod(name = Constants.NAME, version = Constants.VER, modid = Constants.ID)
public class SkyBlockBonus {

    private static final FileHandler FILE_HANDLER = new FileHandler();
    @Mod.Instance(Constants.ID)
    protected static SkyBlockBonus INSTANCE = new SkyBlockBonus();
    private final Logger LOGGER = LogManager.getLogger(Constants.NAME);
    private final Config config = new Config("config", FILE_HANDLER.modDir);
    private final FeatureManager FEATURE_MANAGER = new FeatureManager();
    private final CustomMenuManager CUSTOM_MENU_MANAGER = new CustomMenuManager();
    public VersionChecker VERSION_CHECKER = new VersionChecker();
    private boolean latestVersion;

    private boolean debug;
    private static String apiKey;

    private final Gson gson = new Gson();
    private final TickListener tickListener = new TickListener();
    private final InventoryListener invListener = new InventoryListener();
    private final LocationListener locListener = new LocationListener();
    private final ActionBarParser actionBarParser = new ActionBarParser();
    private final SkyBlockUtils skyBlockUtils = new SkyBlockUtils();

    public static SkyBlockBonus getInstance() {
        return INSTANCE;
    }

    public static FileHandler getFileHandler() {
        return FILE_HANDLER;
    }

    public void truPreInitialization() {
        Runtime.getRuntime().addShutdownHook(new Thread(() -> SkyBlockBonus.getInstance().shutdown()));

        if (config != null) {
            apiKey = config.get("api_key", String.class);
        }

        if (this.VERSION_CHECKER.getEmergencyStatus())
            throw new RuntimeException("PLEASE UPDATE TO THE NEW VERSION OF " + Constants.NAME + "\nTHIS IS AN EMERGENCY!");
        this.latestVersion = this.VERSION_CHECKER.getVersion().equals(Constants.VER);
    }

    @Mod.EventHandler
    public void preInitialize(FMLPreInitializationEvent event) {
        getFileHandler().init();
        ConflictingModsLoaded.getInstance().setupValues();
    }

    @Mod.EventHandler
    public void initialize(FMLInitializationEvent event) {
        ThreadUtils.getInstance().runAsync(() -> ForgeUtils.getInstance().registerEventListeners(
                getInstance(),
                getFeatureManager(),
                getCustomMenuManager(),
                RenderListener.getInstance(),
                PlayerListener.getInstance(),
                new MobListener(),
                getTickListener(),
                getLocListener(),
                getSkyBlockUtils()
        ));
        getFeatureManager().init();
        getCustomMenuManager().init();
        KeyBindManager.getInstance().addKeyBind(Constants.NAME, new KeyBind("Open GUI", Keyboard.KEY_J) {
            @Override
            public void onPressed() {
                Minecraft.getMinecraft().displayGuiScreen(new GuiSkyBlockMod());
            }
        });
        KeyBindManager.getInstance().addKeyBind(Constants.NAME, new KeyBind("Debug", Keyboard.KEY_P) {
            @Override
            public void onPressed() {
                debug = !debug;
                ChatUtils.getInstance().sendModMessage(ModChatMessageSentEvent.Type.SYSTEM, debug ? "Enabled debug mode!" : "Disabled debug mode!");
            }
        });
        KeyBindManager.getInstance().init();
        CommandManager.getInstance().register(new ModCommand(new ModCommandRunnable() {
            @Override
            public String name() {
                return "skyblockbonus";
            }

            @Override
            public String usage() {
                return "/" + name();
            }

            @Override
            public int permissionLevel() {
                return -1;
            }
            @Override
            public List<String> aliases() {
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
                        ChatUtils.getInstance().sendModMessage(ModChatMessageSentEvent.Type.SYSTEM, "Unrecognized argument.");
                        break;
                    case "apikey":
                        if (args.length <= 1) {
                            ChatUtils.getInstance().sendModMessage(ModChatMessageSentEvent.Type.SYSTEM, "Please provide your API key!");
                            return;
                        }
                        /*
                         * Adapted from Skytils under GNU v3.0 license
                         * @link https://github.com/Skytils/SkytilsMod/blob/main/LICENSE
                         * @author SkytilsMod
                         */
                        new Thread(() -> {
                            if (APIUtils.getJSONResponse("https://api.hypixel.net/key?key=" + apiKey).get("success").getAsBoolean()) {
                                config.put("api_key", args[1]);
                                apiKey = args[1];
                                config.save();
                                ChatUtils.getInstance().sendModMessage(ModChatMessageSentEvent.Type.SYSTEM, EnumChatFormatting.GREEN + "API key set!");
                            } else {
                                ChatUtils.getInstance().sendModMessage(ModChatMessageSentEvent.Type.SYSTEM, EnumChatFormatting.RED + "The API key you provided is invalid.");
                            }
                        }).start();
                        break;
                    case "getapikey":
                        Toolkit.getDefaultToolkit().getSystemClipboard().setContents(new StringSelection(apiKey), null);
                        ChatUtils.getInstance().sendModMessage(ModChatMessageSentEvent.Type.SYSTEM, "Your set API key has been copied to the clipboard!");
                        break;
                    case "reloadfeatures":
                        SkyBlockBonus.getInstance().getFeatureManager().reset();
                        break;
                    case "checkver":
                        SkyBlockBonus.getInstance().VERSION_CHECKER.reload();
                        ChatUtils.getInstance().sendModMessage(ModChatMessageSentEvent.Type.SYSTEM, "You are using " + EnumChatFormatting.GREEN + Constants.VER + EnumChatFormatting.WHITE + " and the latest version is currently " + EnumChatFormatting.GREEN + SkyBlockBonus.getInstance().VERSION_CHECKER.getVersion());
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
                        ChatUtils.getInstance().sendModMessage(ModChatMessageSentEvent.Type.SYSTEM, EnumChatFormatting.GREEN + "Developer info copied to clipboard!");
                        break;
                    case "debug":
                        debug = !debug;
                        ChatUtils.getInstance().sendModMessage(ModChatMessageSentEvent.Type.SYSTEM, debug ? "Enabled debug mode!" : "Disabled debug mode!");
                        break;
                    case "givesbitem":
                        if (args.length <= 1) {
                            ChatUtils.getInstance().sendModMessage(ModChatMessageSentEvent.Type.SYSTEM, "Please provide the minecraft id");
                            return;
                        }
                        if (args.length <= 2) {
                            ChatUtils.getInstance().sendModMessage(ModChatMessageSentEvent.Type.SYSTEM, "Please provide the skyblock id");
                        }
                        StringBuilder nbtBuilder = new StringBuilder();
                        nbtBuilder.append("{");
                        if (args[1].equalsIgnoreCase("skull")) {
                            nbtBuilder.append("SkullOwner:");
                            nbtBuilder.append(StringUtils.getInstance().generateRandomAlphabeticString());
                            nbtBuilder.append(",");
                        }
                        nbtBuilder.append("ExtraAttributes:");
                        nbtBuilder.append("{");
                        nbtBuilder.append("id:");
                        nbtBuilder.append(args[2].toUpperCase());
                        nbtBuilder.append("}}");
                        Minecraft.getMinecraft().thePlayer.sendChatMessage("/give " + Minecraft.getMinecraft().thePlayer.getDisplayName().getUnformattedText() + " minecraft:" + args[1] + " 1 3 " + nbtBuilder.toString());
                        break;
                }
            }
            @Override
            public List<String> tabCompleteOptions() {
                return new ArrayList<>(Arrays.asList("reloadfeatures", "checkver", "info", "debug", "givesbitem"));
            }
        }));
        CommandManager.getInstance().register(new ModCommand(new ModCommandRunnable() {
            @Override
            public String name() {
                return "pt";
            }
            @Override
            public String usage() {
                return "/" + name();
            }
            @Override
            public int permissionLevel() {
                return -1;
            }
            @Override
            public void process(EntityPlayer sender, String[] args) throws CommandException {
                if (args.length <= 0) {
                    ((EntityPlayerSP) sender).sendChatMessage("/playtime");
                    return;
                }
                ((EntityPlayerSP) sender).sendChatMessage("/party transfer " + args[0]);
            }
            @Override
            public List<String> tabCompleteOptions() {
                return Collections.emptyList();
            }
        }));
        CommandManager.getInstance().register(new ModCommand(new ModCommandRunnable() {
            @Override
            public String name() {
                return "prtl";
            }
            @Override
            public String usage() {
                return name();
            }

            @Override
            public int permissionLevel() {
                return -1;
            }

            @Override
            public void process(EntityPlayer sender, String[] args) throws CommandException {
                ((EntityPlayerSP) sender).sendChatMessage("/visit prtl");
            }

            @Override
            public List<String> tabCompleteOptions() {
                return Collections.emptyList();
            }
        }));
    }

    public void shutdown() {
    }

    public boolean isDebug() {
        return debug;
    }

    public static String getApiKey() {
        return apiKey;
    }

    public boolean isLatestVersion() {
        return latestVersion;
    }

    public Logger getLogger() {
        return LOGGER;
    }

    public Gson getGson() {
        return gson;
    }

    public FeatureManager getFeatureManager() {
        return FEATURE_MANAGER;
    }

    public CustomMenuManager getCustomMenuManager() {
        return CUSTOM_MENU_MANAGER;
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