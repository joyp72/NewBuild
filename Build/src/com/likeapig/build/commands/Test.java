package com.likeapig.build.commands;

import org.bukkit.entity.Player;

import com.likeapig.build.arena.Arena;

public class Test extends Commands {
	
	public Test() {
		super("build.default", "Test", "", new String[] { "t" });
	}

	@Override
	public void onCommand(final Player sender, final String[] args) {
		
		Arena.getData(sender).increaseScore(3);
		MessageManager.get().message(sender, "Tested.");
	}

}
