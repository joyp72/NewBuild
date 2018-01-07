package com.likeapig.build.commands;

import org.bukkit.entity.Player;

import com.likeapig.build.arena.Arena;
import com.likeapig.build.arena.ArenaManager;
import com.sk89q.worldguard.bukkit.WorldGuardPlugin;
import com.sk89q.worldguard.protection.ApplicableRegionSet;
import com.sk89q.worldguard.protection.managers.RegionManager;
import com.sk89q.worldguard.protection.regions.ProtectedRegion;

public class Test extends Commands {

	public Test() {
		super("build.default", "Test", "", new String[] { "t" });
	}

	@Override
	public void onCommand(final Player sender, final String[] args) {

		sender.sendMessage(Integer.toString(ArenaManager.get().getArenas().get(0).getUsed().size()));
	}

}
