package ga.matthewtgm.skyblockmod;

import club.sk1er.mods.core.ModCoreInstaller;
import ga.matthewtgm.lib.TGMLib;
import ga.matthewtgm.lib.util.keybindings.KeyBind;
import ga.matthewtgm.lib.util.keybindings.KeyBindManager;
import ga.matthewtgm.skyblockmod.command.SkyBlockModCommand;
import ga.matthewtgm.skyblockmod.features.FeatureManager;
import ga.matthewtgm.skyblockmod.gui.GuiSkyBlockMod;
import ga.matthewtgm.skyblockmod.handlers.FileHandler;
import ga.matthewtgm.skyblockmod.listeners.impl.MobListener;
import ga.matthewtgm.skyblockmod.listeners.impl.PlayerListener;
import ga.matthewtgm.skyblockmod.listeners.impl.RenderListener;
import ga.matthewtgm.skyblockmod.utils.ConflictingModsLoaded;
import ga.matthewtgm.skyblockmod.utils.VersionChecker;
import net.minecraft.client.Minecraft;
import net.minecraftforge.client.ClientCommandHandler;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.lwjgl.input.Keyboard;

@Mod(name = Constants.NAME, version = Constants.VER, modid = Constants.ID)
public class SkyBlockBonus {

    private static final FileHandler FILE_HANDLER = new FileHandler();
    @Mod.Instance(Constants.ID)
    protected static SkyBlockBonus INSTANCE = new SkyBlockBonus();
    private final Logger LOGGER = LogManager.getLogger(Constants.NAME);
    private final FeatureManager FEATURE_MANAGER = new FeatureManager();
    public VersionChecker VERSION_CHECKER = new VersionChecker();
    private boolean latestVersion;

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
        TGMLib.getInstance().setModName(Constants.NAME);

        getFileHandler().init();
        ModCoreInstaller.initializeModCore(Minecraft.getMinecraft().mcDataDir);
        ConflictingModsLoaded.getInstance().setupValues();
    }

    @Mod.EventHandler
    public void initialize(FMLInitializationEvent event) {
        MinecraftForge.EVENT_BUS.register(this.getFeatureManager());
        MinecraftForge.EVENT_BUS.register(new RenderListener());
        MinecraftForge.EVENT_BUS.register(new PlayerListener());
        MinecraftForge.EVENT_BUS.register(new MobListener());
        this.getFeatureManager().initFeatures();
        KeyBindManager.getInstance().addKeyBind(new KeyBind("Open GUI", Keyboard.KEY_J) {
            @Override
            public void onPressed() {
                Minecraft.getMinecraft().displayGuiScreen(new GuiSkyBlockMod());
            }
        });
        KeyBindManager.getInstance().init();
        ClientCommandHandler.instance.registerCommand(new SkyBlockModCommand());
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

}