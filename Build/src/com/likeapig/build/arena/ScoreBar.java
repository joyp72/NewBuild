package com.likeapig.build.arena;

import java.util.ArrayList;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.boss.BarColor;
import org.bukkit.boss.BarFlag;
import org.bukkit.boss.BarStyle;
import org.bukkit.boss.BossBar;
import org.bukkit.entity.Player;

public class ScoreBar {

	static ArrayList<Player> players = new ArrayList<>();
	static ArrayList<BossBar> bars = new ArrayList<>();

	public ScoreBar(Player p) {
		if (p != null) {
			players.add(p);
			int i = Arena.getData(p).getScore();
			BossBar b = Bukkit.createBossBar(" ", BarColor.YELLOW, BarStyle.SOLID, new BarFlag[0]);
			b.addPlayer(p);
			b.setTitle(ChatColor.YELLOW + " " + ChatColor.BOLD + "Score: " + i);
			b.setVisible(true);
			bars.add(b);
		}
	}

	public static void removeScoreBar(Player p) {
		players.remove(p);
		for (BossBar b : bars) {
			b.removePlayer(p);
		}
	}

}
