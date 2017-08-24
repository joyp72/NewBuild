package com.likeapig.build.commands;

import org.bukkit.entity.Player;

import com.likeapig.build.arena.DisguiseClass;

public class RemoveDisguise extends Commands {
	
	public RemoveDisguise() {
		super("build.default", "undisguise", "", new String[] { "u" });
	}
	
	@Override
    public void onCommand(final Player sender, final String[] args) {
        DisguiseClass.removeDisguise(sender);
        MessageManager.get().message(sender, "round ended");
    }

}
