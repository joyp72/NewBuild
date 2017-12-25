package com.likeapig.build.scoreboard;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.scoreboard.DisplaySlot;
import org.bukkit.scoreboard.Objective;
import org.bukkit.scoreboard.Score;
import org.bukkit.scoreboard.Scoreboard;
import org.bukkit.scoreboard.ScoreboardManager;

import com.likeapig.build.Build;
import com.likeapig.build.arena.Arena;
import com.likeapig.build.arena.ArenaManager;
import com.likeapig.build.arena.Data;

import net.md_5.bungee.api.ChatColor;

public class ScoreBoard {

	public static ScoreBoard instance;
	private int i = 20;

	static {
		instance = new ScoreBoard();
	}

	public static ScoreBoard get() {
		return instance;
	}

	public void removeSB(Player p) {
		p.setScoreboard(Bukkit.getScoreboardManager().getNewScoreboard());
	}

	public void updateSB(Player p) {

		new BukkitRunnable() {

			@Override
			public void run() {
				Arena m = ArenaManager.get().getArena(p);

				if (m != null) {

					ScoreboardManager manager = Bukkit.getScoreboardManager();
					Scoreboard board = manager.getNewScoreboard();

					Objective main = board.registerNewObjective("Trials ", "dummy");
					main.setDisplaySlot(DisplaySlot.SIDEBAR);
					main.setDisplayName(ChatColor.DARK_AQUA + "Mega" + ChatColor.AQUA + "Build");

					Score timer = main.getScore(ChatColor.WHITE + "" + ChatColor.BOLD + "Timer" + ChatColor.RESET + ""
							+ ChatColor.WHITE + ": " + m.getCountdown());
					timer.setScore(i);
					i--;

					Score build = main.getScore(ChatColor.WHITE + "" + ChatColor.BOLD + "Builder" + ChatColor.RESET + ""
							+ ChatColor.WHITE + ": ");
					build.setScore(i);
					i--;

					if (m.getBuilder() != null) {
						Score builder = main.getScore(ChatColor.GRAY + m.getBuilder().getName());
						builder.setScore(i);
						i--;
					}

					Score blank2 = main.getScore("  ");
					blank2.setScore(i);
					i--;

					Score abilities = main.getScore(ChatColor.WHITE + "" + ChatColor.BOLD + "Players" + ChatColor.RESET
							+ "" + ChatColor.WHITE + ":");
					abilities.setScore(i);
					i--;

					for (Data data : m.getDatas()) {
						String name = data.getPlayer().getName();
						int score = data.getScore();
						Score player = main
								.getScore(ChatColor.WHITE + Integer.toString(score) + " " + ChatColor.GRAY + name);
						player.setScore(i);
						i--;
					}

					p.setScoreboard(board);
					i = 20;
				}
			}
		}.runTask(Build.getInstance());
	}

}
