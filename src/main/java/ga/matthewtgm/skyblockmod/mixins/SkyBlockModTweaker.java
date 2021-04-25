package ga.matthewtgm.skyblockmod.mixins;

import ga.matthewtgm.lib.util.LoggingUtils;
import ga.matthewtgm.skyblockmod.Constants;
import net.minecraft.launchwrapper.ITweaker;
import net.minecraft.launchwrapper.LaunchClassLoader;
import net.minecraftforge.common.ForgeVersion;
import net.minecraftforge.fml.relauncher.IFMLLoadingPlugin;
import org.apache.logging.log4j.Logger;
import org.spongepowered.asm.launch.MixinBootstrap;
import org.spongepowered.asm.mixin.MixinEnvironment;
import org.spongepowered.asm.mixin.Mixins;

import java.io.File;
import java.util.List;

@IFMLLoadingPlugin.MCVersion(ForgeVersion.mcVersion)
public class SkyBlockModTweaker implements ITweaker {

    private Logger logger = LoggingUtils.getInstance().createLogger(Constants.NAME, "Tweaker");

    @Override
    public void acceptOptions(List<String> args, File gameDir, File assetsDir, String profile) {}

    @Override
    public void injectIntoClassLoader(LaunchClassLoader classLoader) {
        MixinBootstrap.init();
        logger.info("[Mixins] Initializing...");
        MixinEnvironment env = MixinEnvironment.getDefaultEnvironment();
        Mixins.addConfiguration("mixins.sbb.json");

        if (env.getObfuscationContext() == null) {
            logger.info("[Mixins] Setting obfuscation context...");
            env.setObfuscationContext("notch");
        }
        logger.info("[Mixins] Setting mixin environment side...");
        env.setSide(MixinEnvironment.Side.CLIENT);
    }

    @Override
    public String getLaunchTarget() {
        return "net.minecraft.client.main.Main";
    }

    @Override
    public String[] getLaunchArguments() {
        return new String[0];
    }
}