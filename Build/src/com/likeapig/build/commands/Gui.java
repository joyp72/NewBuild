package com.likeapig.build.commands;

import org.bukkit.entity.Player;

import com.likeapig.build.arena.Menus;

public class Gui extends Commands {

	public Gui() {
		super("build.default", "Open menu", "", new String[] { "g" });
	}

	@Override
	public void onCommand(Player p, String[] args) {
		new Menus(p);
		p.openInventory(Menus.getMenus().get(p));
	}
}