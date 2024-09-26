package me.kimovoid.reskin.config;

import net.minecraftforge.common.config.Configuration;

import java.io.File;

public class Config {

    public final Configuration config;
    public boolean transparency = false;

    public Config(File path) {
        config = new Configuration(path);
        this.sync(true);
    }

    public void sync(boolean load) {
        if (load && !config.isChild) {
            config.load();
        }

        this.transparency = config.getBoolean("transparency", "general", false, "Enables transparency in outer layers");

        if (config.hasChanged()) {
            config.save();
        }
    }
}