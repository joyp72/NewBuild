package com.likeapig.build.commands;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

import com.likeapig.build.arena.DisguiseClass;

public class Disguise extends Commands {
	
	public Disguise() {
		super("build.default", "Disguise", "", new String[] { "d" });
	}
	
	@Override
    public void onCommand(final Player sender, final String[] args) {
		if (args.length != 2) {
			sender.sendMessage("/b d <name> <name>");
		}
		
		Player p = Bukkit.getServer().getPlayer(args[0]);
        DisguiseClass.disguise(p, args[1]);
        MessageManager.get().message(sender, "round ended");
    }

}
