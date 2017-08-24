package com.likeapig.build;

import java.io.*;
import org.bukkit.plugin.*;
import org.bukkit.configuration.file.*;
import org.bukkit.configuration.*;

public class Settings
{
    private static Settings instance;
    private File arenaFile;
    private FileConfiguration arenaConfig;
    private Plugin plugin;
    
    static {
        Settings.instance = new Settings();
    }
    
    public static Settings getInstance() {
        return Settings.instance;
    }
    
    public void setup(final Plugin p) {
        this.plugin = p;
        if (!p.getDataFolder().exists()) {
            p.getDataFolder().mkdir();
        }
        this.arenaFile = new File(p.getDataFolder() + "/arenas.yml");
        if (!this.arenaFile.exists()) {
            try {
                this.arenaFile.createNewFile();
            }
            catch (Exception e) {
                p.getLogger().info("Failed to generate arena file!");
                e.printStackTrace();
            }
        }
        this.arenaConfig = (FileConfiguration)YamlConfiguration.loadConfiguration(this.arenaFile);
    }
    
    public void set(final String path, final Object value) {
        this.arenaConfig.set(path, value);
        try {
            this.arenaConfig.save(this.arenaFile);
        }
        catch (Exception e) {
            e.printStackTrace();
        }
    }
    
    public <T> T get(final String path) {
        return (T)this.arenaConfig.get(path);
    }
    
    public ConfigurationSection getConfigSection() {
    	return arenaConfig.getConfigurationSection("arenas");
    }
    
    public ConfigurationSection createConfiguration(final String path) {
        final ConfigurationSection s = this.arenaConfig.createSection(path);
        try {
            this.arenaConfig.save(this.arenaFile);
        }
        catch (Exception e) {
            e.printStackTrace();
        }
        return s;
    }
    
    public Plugin getPlugin() {
        return this.plugin;
    }
}
