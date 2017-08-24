package com.likeapig.build.commands;

import org.bukkit.entity.Player;

import com.likeapig.build.arena.MegaData;

public class SetCoins extends Commands {

	public SetCoins() {
		super("build.default", "Set your coins", "", new String[] { "sc" });
	}

	@Override
	public void onCommand(final Player sender, final String[] args) {
		int i = Integer.parseInt(args[0]);
		MegaData.setCoins(sender.getName(), i);
		MessageManager.get().message(sender, "Your coins have been set to " + i + ".");
	}
}
