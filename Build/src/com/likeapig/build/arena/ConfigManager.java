package com.likeapig.build.arena;

import java.util.*;

import org.bukkit.configuration.file.FileConfiguration;

import com.likeapig.build.Build;

public class ConfigManager {
	public static ConfigManager instance;

	static {
		instance = new ConfigManager();
	}

	public static ConfigManager get() {
		return instance;
	}

	public void addDefaults(List<String> words) {
		words.add("Hippie");
		words.add("Ice Cream Man");
		words.add("Bucket");
		words.add("Giant");
		words.add("Pig");
		words.add("Cow");
		words.add("Portal");
		words.add("Bed");
		words.add("Black Hole");
		words.add("Moon");
		words.add("Water");
		words.add("Sun");
		words.add("Rain");
		words.add("Gun");
		words.add("Fish");
		words.add("House");
		words.add("Trash");
		words.add("Car");
		words.add("Rainbow");
		words.add("Seesaw");
		words.add("Bridge");
		words.add("Snowflake");
		words.add("Plane");
		words.add("Poop");
		words.add("Heart");
		words.add("Mario");
		words.add("Triangle");
		words.add("Church");
		words.add("Balloon");
		words.add("Vacuum");
		words.add("Bike");
		words.add("Boat");
		words.add("Dog");
		words.add("Earth");
		words.add("Window");
		words.add("Tree");
		words.add("Pencil");
		words.add("Computer");
		words.add("Blood");
		words.add("Tent");
		words.add("Clock");
		words.add("Flower");
		words.add("Hotdog");
		words.add("Volcano");
		words.add("Crown");
		words.add("Chair");
		words.add("Shark");
		words.add("Phone");
		words.add("chicken");
		words.add("nether");
		words.add("skyblock");
		words.add("facebook");
		words.add("youtube");
		words.add("twitter");
		words.add("piston");
		words.add("beach");
		words.add("shovel");
		words.add("ocean");
		words.add("sunset");
		words.add("compass");
		words.add("notch");
		words.add("minecraft");
		words.add("tv");
		words.add("ipad");
		words.add("farm");
		words.add("village");
		words.add("trump");
		words.add("mountain");
		words.add("sidewalk");
		words.add("stop sign");
		words.add("emoji");
		words.add("popcorn");
		words.add("lamp");
		words.add("wall");
		words.add("meteor");
		words.add("eclipse");
		words.add("forest");
		words.add("snowman");
		words.add("cloud");
		words.add("lightning");
		words.add("america");
		words.add("money");
		words.add("book");
		words.add("lightning");
	}
}
