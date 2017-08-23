package com.likeapig.build.commands;

import org.bukkit.entity.Player;

import com.likeapig.build.arena.Arena;
import com.likeapig.build.arena.ArenaManager;

public class SetLobby extends Commands {
	
	public SetLobby() {
        super("bmt.admin", "Set the lobby of an arena", "<arena>", new String[] { "sl" });
    }
    
    @Override
    public void onCommand(final Player sender, final String[] args) {
        if (args.length == 0) {
            MessageManager.get().message(sender, "You must specify an arena name!", MessageManager.MessageType.BAD);
            return;
        }
        final String id = args[0];
        final Arena a = ArenaManager.get().getArena(id);
        if (a == null) {
            MessageManager.get().message(sender, "Unknown arena", MessageManager.MessageType.BAD);
            return;
        }
        a.setLobby(sender.getLocation());
        MessageManager.get().message(sender, "Lobby set for: " + a.getName(), MessageManager.MessageType.GOOD);
    }

}
