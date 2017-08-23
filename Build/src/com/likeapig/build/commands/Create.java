package com.likeapig.build.commands;

import org.bukkit.Location;
import org.bukkit.entity.Player;

import com.likeapig.build.arena.Arena;
import com.likeapig.build.arena.ArenaManager;

public class Create extends Commands {
	
	public Create() {
		super("build.admin", "Create an arena", "<name>", new String[] { "c", "createarena" });
	}
	
	@Override
    public void onCommand(final Player sender, final String[] args) {
        if (args.length == 0) {
            MessageManager.get().message(sender, "You must specify an arena name!", MessageManager.MessageType.BAD);
            return;
        }
        final String id = args[0];
        final Arena a = ArenaManager.get().getArena(id);
        if (a != null) {
            MessageManager.get().message(sender, "That arena already exist", MessageManager.MessageType.BAD);
            return;
        }
        Location p1 = sender.getLocation();
        ArenaManager.get().registerArena(id, p1);
        MessageManager.get().message(sender, "Arena created!", MessageManager.MessageType.GOOD);
    }

}
