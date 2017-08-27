package com.likeapig.build.arena;

import java.util.*;

import org.bukkit.configuration.file.FileConfiguration;

import com.likeapig.build.Build;

public class ConfigManager
{
    private static FileConfiguration config;
    public static int TIMEEACHPLAYERBUILD;
    public static int TIMEPERROUND;
    public static int POINTSBUILDERWHENWORDFOUND;
    public static int POINTSWHENWORDFIRSTFOUND;
    public static int POINTSWHENWORDFOUND;
    public static boolean UPDATECHECKER;
    public static boolean ALLOWCREATIVE;
    public static List<String> WORDS;
    
    static {
        ConfigManager.TIMEEACHPLAYERBUILD = 2;
        ConfigManager.TIMEPERROUND = 120;
        ConfigManager.POINTSBUILDERWHENWORDFOUND = 2;
        ConfigManager.POINTSWHENWORDFIRSTFOUND = 3;
        ConfigManager.POINTSWHENWORDFOUND = 1;
        ConfigManager.UPDATECHECKER = true;
        ConfigManager.ALLOWCREATIVE = false;
        ConfigManager.WORDS = new ArrayList<String>() {
            private static final long serialVersionUID = -6790038813350933029L;
            
            {
                this.add("Hippie");
                this.add("Ice Cream Man");
                this.add("Bucket");
                this.add("Giant");
                this.add("Pig");
                this.add("Cow");
                this.add("Portal");
                this.add("Bed");
                this.add("Black Hole");
                this.add("Moon");
                this.add("Water");
                this.add("Sun");
                this.add("Rain");
                this.add("Gun");
                this.add("Fish");
                this.add("House");
                this.add("Trash");
                this.add("Car");
                this.add("Rainbow");
                this.add("Seesaw");
                this.add("Bridge");
                this.add("Cold");
                this.add("Helicopter");
                this.add("Poop");
                this.add("Heart");
                this.add("Mario");
                this.add("Triangle");
                this.add("Church");
                this.add("Balloon");
                this.add("Vacuum");
                this.add("Bike");
                this.add("Boat");
                this.add("Dog");
                this.add("Earth");
                this.add("Window");
                this.add("Tree");
                this.add("Pencil");
                this.add("Computer");
            }
        };
    }
    
    public static void setup() {
        ConfigManager.config = Build.getInstance().getConfig();
        ConfigManager.config.addDefault("words", (Object)ConfigManager.WORDS);
        ConfigManager.WORDS = (List<String>)ConfigManager.config.getList("words");
        Build.getInstance().saveConfig();
    }
}
