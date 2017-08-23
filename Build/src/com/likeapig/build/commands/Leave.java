package com.likeapig.build.commands;

import org.bukkit.entity.Player;

import com.likeapig.build.arena.Arena;
import com.likeapig.build.arena.ArenaManager;

public class Leave extends Commands {
	
	public Leave() {
		super("build.default", "Leave an arena", "", new String[] { "l", "quit", "lobby", "hub"});
	}

	@Override
    public void onCommand(final Player sender, final String[] args) {
        final Arena a = ArenaManager.get().getArena(sender);
        if (a == null) {
            MessageManager.get().message(sender, "You are not in an arena", MessageManager.MessageType.BAD);
            return;
        }
        a.kickPlayer(sender);
    }
}
