package ga.matthewtgm.skyblockmod.utils;

import ga.matthewtgm.lib.util.LoggingUtils;
import net.minecraftforge.fml.common.Loader;
import org.apache.logging.log4j.Logger;

import java.util.HashMap;
import java.util.Map;

public class ConflictingModsLoaded {

    private static final ConflictingModsLoaded INSTANCE = new ConflictingModsLoaded();
    private final Logger logger = LoggingUtils.getInstance().createClassLogger(this.getClass());
    public Map<String, Boolean> conflictsMap = new HashMap();
    public boolean isPatcherLoaded;
    public boolean isSkyBlockAddonsLoaded;
    public boolean isNotEnoughUpdatesLoaded;
    public boolean isSkyBlockExtrasLoaded;

    public static ConflictingModsLoaded getInstance() {
        return INSTANCE;
    }

    public void setupValues() {
        logger.info("----------------------------");
        logger.info("Setting values...");
        this.isPatcherLoaded = Loader.isModLoaded("patcher");
        this.isSkyBlockAddonsLoaded = Loader.isModLoaded("skyblockaddons");
        this.isNotEnoughUpdatesLoaded = Loader.isModLoaded("notenoughupdates");
        this.isSkyBlockExtrasLoaded = Loader.isModLoaded("skyblockextras");
        logger.info("Values set!");
        logger.info("----------------------------");
        logger.info("Putting values into a \"java.util.Map\"...");
        conflictsMap.put("patcher", isPatcherLoaded);
        conflictsMap.put("skyblockaddons", isSkyBlockAddonsLoaded);
        conflictsMap.put("notenoughupdates", isNotEnoughUpdatesLoaded);
        conflictsMap.put("skyblockextras", isSkyBlockExtrasLoaded);
        logger.info("All values put into the \"java.util.Map\"!");
        logger.info("----------------------------");
        conflictsMap.forEach((modName, isLoaded) -> {
            logger.info(modName + ": " + isLoaded);
        });
        logger.info("----------------------------");
    }

}