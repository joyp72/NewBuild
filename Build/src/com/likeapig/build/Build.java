package com.likeapig.build;

import org.bukkit.plugin.java.JavaPlugin;

import com.likeapig.bubble.BubbleListener;
import com.likeapig.build.arena.Arena;
import com.likeapig.build.arena.ArenaListener;
import com.likeapig.build.arena.ArenaManager;
import com.likeapig.build.arena.ConfigManager;
import com.likeapig.build.arena.MegaData;
import com.likeapig.build.arena.MenusListener;
import com.likeapig.build.arena.Timer;
import com.likeapig.build.commands.CommandsManager;

public class Build extends JavaPlugin {
	
	public static Build instance;
	
	public void onEnable() {
		getLogger().info("Enabled!");
		instance = this;
		Timer.get();
		Settings.getInstance().setup(this);
		ConfigManager.setup();
		ArenaManager.get().setupArenas();
		ArenaListener.get().setup();
		MenusListener.get().setup();
		CommandsManager.get().setup();
		MegaData.get().setup(this);
		//BubbleListener.get().setup();
		
	}
	
	public void onDisable() {
		getLogger().info("Disabled!");
		for (Arena a : ArenaManager.get().getArenas()) {
			if (a.isStarted() || a.getStateName().equals("WAITING")) {
				a.stop();
			}
		}
	}
	
	public static Build getInstance() {
		return instance;
	}

}
