package com.likeapig.build.commands;

import org.bukkit.entity.Player;

import com.likeapig.build.arena.Arena;
import com.likeapig.build.arena.ArenaManager;
import com.likeapig.build.commands.MessageManager.MessageType;

public class Join extends Commands {

	public Join() {
		super("build.default", "Join an arena", "<arena>", new String[] { "j" });
	}

	@Override
    public void onCommand(final Player sender, final String[] args) {
        if (args.length == 0) {
            MessageManager.get().message(sender, "You must specify an arena name!", MessageManager.MessageType.BAD);
            return;
        }
        final Arena a = ArenaManager.get().getArena(sender);
        if (a != null) {
            MessageManager.get().message(sender, "You are already in an arena", MessageManager.MessageType.BAD);
            return;
        }
        final String id = args[0];
        final Arena a2 = ArenaManager.get().getArena(id);
        if (a2 == null) {
            MessageManager.get().message(sender, "Unknown arena", MessageManager.MessageType.BAD);
            return;
        }
        if (a2.getLobby() == null) {
        	MessageManager.get().message(sender, a2.getName() + " doesn't have a lobby set!", MessageType.BAD);
        	return;
        }
        a2.addPlayer(sender);
    }

}
