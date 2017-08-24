package com.likeapig.bubble;

import org.bukkit.Bukkit;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerChatEvent;
import org.bukkit.plugin.Plugin;

import com.likeapig.build.Build;

public class BubbleListener implements Listener {
	
	Plugin plugin;
	private static BubbleListener instance;
	
	static {
		instance = new BubbleListener();
	}
	
	public static BubbleListener get() {
		return instance;
	}
	
	public BubbleListener() {
		
	}
	
	public void setup() {
		Bukkit.getPluginManager().registerEvents((Listener) this, (Plugin) Build.getInstance());
	}
	
	@SuppressWarnings("deprecation")
	@EventHandler
	public void playerChat(PlayerChatEvent e) {
		String msg = e.getMessage();
		Bubble bubble = new Bubble(e.getPlayer().getLocation(), e.getMessage());
		bubble.setCancelTask(Bukkit.getScheduler().runTaskTimer(Build.getInstance(), bubble, 0L, 2L));
	}
	

}
