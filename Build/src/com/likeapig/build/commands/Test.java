package com.likeapig.build.commands;

import org.bukkit.entity.Player;

import com.likeapig.build.arena.Arena;
import com.likeapig.build.arena.ArenaManager;

public class Test extends Commands {

	public Test() {
		super("build.default", "Test", "", new String[] { "t" });
	}

	@Override
	public void onCommand(final Player sender, final String[] args) {

		Arena a = ArenaManager.get().getArena(sender);
		
		if (a != null) {
			MessageManager.get().message(sender, a.getWord());
		}
	}

}
