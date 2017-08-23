package com.likeapig.build.commands;

import org.bukkit.entity.Player;

import com.likeapig.build.arena.Arena;
import com.likeapig.build.arena.ArenaManager;

import net.md_5.bungee.api.ChatColor;

public class List extends Commands {

	public List() {
		super("build.default", "List all arenas", "", new String[] { "" });
	}

	@Override
	public void onCommand(final Player sender, final String[] args) {
		if (ArenaManager.get().getArenas().size() > 0) {
			MessageManager.get().message(sender, "Arenas list:");
			for (final Arena a : ArenaManager.get().getArenas()) {
				MessageManager.get().message(sender,
						new StringBuilder().append(ChatColor.YELLOW).append(a.getName()).toString());
			}
		} else {
			MessageManager.get().message(sender, "No arena found");
		}
	}
}
