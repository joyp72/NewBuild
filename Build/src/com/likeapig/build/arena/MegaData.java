package com.likeapig.build.arena;

import java.io.File;

import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.plugin.Plugin;

public class MegaData {

	public static int megacoins;
	private static MegaData instance;
	private static File coinsFile;
	public static FileConfiguration coinsConfig;
	private Plugin plugin;
	public static String coinspath;
	public static String guessedcorrect;
	public static String roundswon;
	public static String gameswon;
	public static String halo;

	static {
		instance = new MegaData();
	}

	public static MegaData get() {
		return instance;
	}

	public void setup(final Plugin p) {
		this.plugin = p;
		coinspath = ".coins";
		guessedcorrect = ".guessedcorrect";
		roundswon = ".roundswon";
		gameswon = ".gameswon";
		halo = ".halo";
		if (!p.getDataFolder().exists()) {
			p.getDataFolder().mkdir();
		}
		coinsFile = new File(p.getDataFolder() + "/data.yml");
		if (!coinsFile.exists()) {
			try {
				coinsFile.createNewFile();
			} catch (Exception e) {
				p.getLogger().info("Failed to generate coins file!");
				e.printStackTrace();
			}
		}
		coinsConfig = (FileConfiguration) YamlConfiguration.loadConfiguration(coinsFile);
	}

	public MegaData() {
	}
	
	//coins

	public static int getCoins(String name) {

		if (!containsCoinsPlayer(name)) {
			setCoinsData(name, 0);
		}

		int coins = getCoinsData(name);
		return coins;
	}

	public static void setCoins(String name, int i) {
		int coins = getCoins(name);
		coins = i;
		setCoinsData(name, coins);
	}

	public static void addCoins(String name) {
		int coins = getCoins(name);
		int fcoins = coins + 1;
		setCoinsData(name, fcoins);
	}

	public static int getCoinsData(String path) {
		return coinsConfig.getInt(path + coinspath);
	}

	public static void setCoinsData(final String path, final int value) {
		MegaData.coinsConfig.set(path + coinspath, value);
		try {
			MegaData.coinsConfig.save(coinsFile);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public static boolean containsCoinsPlayer(String name) {
		return coinsConfig.contains(name + coinspath);
	}

	public Plugin getPlugin() {
		return plugin;
	}
	
	//words guessed

	public static int getGC(String name) {

		if (!containsGCPlayer(name)) {
			setGCData(name, 0);
		}

		int gc = getGCData(name);
		return gc;
	}

	public static void setGC(String name, int i) {
		int gc = getGCData(name);
		gc = i;
		setGCData(name, gc);
	}

	public static void addGC(String name, int i) {
		int gc = getGCData(name);
		gc = gc + i;
		setGCData(name, gc);
	}

	public static int getGCData(String path) {
		return coinsConfig.getInt(path + guessedcorrect);
	}

	public static void setGCData(final String path, final Object value) {
		MegaData.coinsConfig.set(path + guessedcorrect, value);
		try {
			MegaData.coinsConfig.save(coinsFile);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public static boolean containsGCPlayer(String name) {
		return coinsConfig.contains(name + guessedcorrect);
	}
	
	//games played
	
	public static int getRW(String name) {

		if (!containsRWPlayer(name)) {
			setRWData(name, 0);
		}

		int rw = getRWData(name);
		return rw;
	}

	public static void setRW(String name, int i) {
		int rw = getRWData(name);
		rw = i;
		setRWData(name, rw);
	}

	public static void addRW(String name, int i) {
		int rw = getRWData(name);
		rw = rw + i;
		setRWData(name, rw);
	}

	public static int getRWData(String path) {
		return coinsConfig.getInt(path + roundswon);
	}

	public static void setRWData(final String path, final Object value) {
		MegaData.coinsConfig.set(path + roundswon, value);
		try {
			MegaData.coinsConfig.save(coinsFile);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public static boolean containsRWPlayer(String name) {
		return coinsConfig.contains(name + roundswon);
	}
	
	//games won
	
	public static int getGW(String name) {

		if (!containsGWPlayer(name)) {
			setGWData(name, 0);
		}

		int gw = getGWData(name);
		return gw;
	}

	public static void setGW(String name, int i) {
		int gw = getGWData(name);
		gw = i;
		setGWData(name, gw);
	}

	public static void addGW(String name, int i) {
		int gw = getGWData(name);
		gw = gw + i;
		setGWData(name, gw);
	}

	public static int getGWData(String path) {
		return coinsConfig.getInt(path + gameswon);
	}

	public static void setGWData(final String path, final Object value) {
		MegaData.coinsConfig.set(path + gameswon, value);
		try {
			MegaData.coinsConfig.save(coinsFile);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public static boolean containsGWPlayer(String name) {
		return coinsConfig.contains(name + gameswon);
	}
	
	//halo
	
	public static boolean getHalo(String name) {
		if (!(containsHaloPlayer(name))) {
			setHaloData(name, false);
		}
		
		boolean bo = getHaloData(name);
		return bo;
	}
	
	public static void setHalo(String name, boolean b) {
		boolean bo = getHaloData(name);
		bo = b;
		setHaloData(name, bo);
	}
	
	public static boolean getHaloData(String path) {
		return MegaData.coinsConfig.getBoolean(path + halo);
	}
	
	public static void setHaloData(String path, Object value) {
		MegaData.coinsConfig.set(path + halo, value);
		try {
			MegaData.coinsConfig.save(coinsFile);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public static boolean containsHaloPlayer(String name) {
		return coinsConfig.contains(name + halo);
	}

}
