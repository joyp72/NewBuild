package com.likeapig.build.store;

import org.bukkit.entity.Player;

public class Activate {

	public String name;
	public Player player;

	public Activate(String s, Player p) {
		name = s;
		player = p;
	}

	public String getName() {
		return name;
	}
	
	public void setName(String s) {
		name = s;
	}
	
	public Player getPlayer() {
		return player;
	}
}

