package ga.matthewtgm.skyblockmod.handlers;

import ga.matthewtgm.skyblockmod.Constants;
import net.minecraft.client.Minecraft;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

public class FileHandler {

    public File configDir = new File(Minecraft.getMinecraft().mcDataDir, "config");
    public File tgmDevelopmentDir = new File(configDir, "TGMDevelopment");
    public File modDir = new File(tgmDevelopmentDir, Constants.NAME);
    public File featureDir = new File(modDir, "Features");

    private final List<File> directories = new ArrayList<>();

    public void init() {
        this.getDirectories().add(configDir);
        this.getDirectories().add(tgmDevelopmentDir);
        this.getDirectories().add(modDir);
        this.getDirectories().add(featureDir);

        this.getDirectories().forEach(f -> {
            if(!f.exists()) f.mkdirs();
        });
    }

    public List<File> getDirectories() {
        return directories;
    }

}