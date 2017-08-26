package com.likeapig.build.commands;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

import com.likeapig.build.arena.MegaData;

public class Test extends Commands {
	
	public Test() {
		super("build.default", "Test", "", new String[] { "t" });
	}

	@Override
	public void onCommand(final Player sender, final String[] args) {
		
		int i = Integer.parseInt(args[1]);
		Player p = Bukkit.getServer().getPlayer(args[0]);
		MegaData.setCoins(p.getName(), i);
		MessageManager.get().message(sender, "Tested.");
	}

}
