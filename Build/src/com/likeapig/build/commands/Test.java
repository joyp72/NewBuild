package com.likeapig.build.commands;

import org.bukkit.entity.Player;

import com.likeapig.build.arena.MegaData;

public class Test extends Commands {
	
	public Test() {
		super("build.default", "Test", "", new String[] { "t" });
	}

	@Override
	public void onCommand(final Player sender, final String[] args) {
		
		int i = Integer.parseInt(args[0]);
		MegaData.setCoins(sender.getName(), i);
		MessageManager.get().message(sender, "Tested.");
	}

}
