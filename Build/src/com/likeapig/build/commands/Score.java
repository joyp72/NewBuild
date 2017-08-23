package com.likeapig.build.commands;

import org.bukkit.entity.Player;

import com.likeapig.build.arena.Arena;
import com.likeapig.build.arena.ArenaManager;
import com.likeapig.build.arena.Data;
import com.likeapig.build.commands.MessageManager.MessageType;

public class Score extends Commands {

	public Score() {
		super("build.default", "Show your score", "", new String[] { "" });
	}

	@Override
	public void onCommand(final Player sender, final String[] args) {
		Arena a = ArenaManager.get().getArena(sender);
		if (a == null) {
			MessageManager.get().message(sender, "You are not in an arena", MessageType.BAD);
			return;
		} else {
			if (Arena.containsPlayer(sender)) {
				Data d = Arena.getData(sender);
				int score = d.getScore();
				MessageManager.get().message(sender, score + " is your score!");
			}
		}
	}

}
