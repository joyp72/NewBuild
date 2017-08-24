package com.likeapig.build.commands;

import org.bukkit.entity.Player;

import com.likeapig.build.arena.Arena;
import com.likeapig.build.arena.ArenaManager;
import com.likeapig.build.arena.Timer;
import com.likeapig.build.commands.MessageManager.MessageType;

public class Stop extends Commands {
	
	public Stop() {
		super("build.default", "Stop an arena", "", new String[] { "" });
	}
	
	@Override
    public void onCommand(final Player sender, final String[] args) {
        final Arena a = ArenaManager.get().getArena(sender);
        if (a == null) {
            MessageManager.get().message(sender, "You are not in an arena", MessageManager.MessageType.BAD);
            return;
        }
        if (!a.isStarted()) {
        	MessageManager.get().message(sender, "The arena has not been started!", MessageType.BAD);
        	return;
        }
        a.endRound();
        MessageManager.get().message(sender, "round ended");
    }

}
