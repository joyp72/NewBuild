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
                this.add("Snowflake");
                this.add("Plane");
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
                this.add("Blood");
                this.add("Tent");
                this.add("Clock");
                this.add("Flower");
                this.add("Hotdog");
                this.add("Volcano");
                this.add("Crown");
                this.add("Chair");
                this.add("Shark");
                this.add("Phone");
                this.add("chicken");
                this.add("nether");
                this.add("skyblock");
                this.add("facebook");
                this.add("youtube");
                this.add("twitter");
                this.add("piston");
                this.add("beach");
                this.add("shovel");
                this.add("ocean");
                this.add("sunset");
                this.add("compass");
                this.add("notch");
                this.add("minecraft");
                this.add("tv");
                this.add("ipad");
                this.add("farm");
                this.add("village");
                this.add("trump");
                this.add("mountain");
                this.add("sidewalk");
                this.add("stop sign");
                this.add("emoji");
                this.add("popcorn");
                this.add("lamp");
                this.add("wall");
                this.add("meteor");
                this.add("eclipse");
                this.add("forest");
                this.add("snowman");
                this.add("cloud");
                this.add("lightning");
                this.add("america");
                this.add("money");
                this.add("book");
                this.add("lightning");
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
