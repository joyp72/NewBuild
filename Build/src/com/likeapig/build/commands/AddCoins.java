package com.likeapig.build.commands;

import org.bukkit.entity.Player;

import com.likeapig.build.arena.MegaData;

public class AddCoins extends Commands {

	public AddCoins() {
		super("build.default", "Set your coins", "", new String[] { "sc" });
	}

	@Override
	public void onCommand(final Player sender, final String[] args) {
		Particles.Particles.get().PlayerCircleEffect(sender);
		MessageManager.get().message(sender, "Particles spawned.");
	}
}
