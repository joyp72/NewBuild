package com.likeapig.build.commands;

import org.bukkit.entity.Player;

import com.likeapig.build.arena.Arena;
import com.likeapig.build.arena.ArenaManager;
import com.likeapig.build.commands.MessageManager.MessageType;

public class Start extends Commands {
	
	public Start() {
		super("build.default", "Start an arena", "", new String[] { "s" });
	}
	
	@Override
    public void onCommand(final Player sender, final String[] args) {
        final Arena a = ArenaManager.get().getArena(sender);
        if (a == null) {
            MessageManager.get().message(sender, "You are not in an arena", MessageManager.MessageType.BAD);
            return;
        }
        if (a.isStarted()) {
        	MessageManager.get().message(sender, "The arena has already been started!", MessageType.BAD);
        	return;
        }
        a.start();
    }

}
