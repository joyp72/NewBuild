package com.likeapig.build.arena;

import org.bukkit.*;
import org.bukkit.configuration.*;
import java.util.*;
import org.bukkit.entity.*;

import com.likeapig.build.Build;
import com.likeapig.build.Settings;
import com.likeapig.build.utils.LocationUtils;

public class ArenaManager {
	public static ArenaManager instance;
	private List<Arena> arenas;

	static {
		ArenaManager.instance = new ArenaManager();
	}

	public static ArenaManager get() {
		return ArenaManager.instance;
	}

	private ArenaManager() {
		this.arenas = new ArrayList<Arena>();
	}

	public void registerArena(final String s, final Location loc) {
		if (this.getArena(s) == null) {
			final Arena a = new Arena(s, loc);
			this.arenas.add(a);
		}
	}

	public void setupArenas() {
		this.arenas.clear();
		if (Settings.getInstance().get("arenas") != null) {
			for (final String s : Settings.getInstance().getConfigSection().getKeys(true)) {
				if (!s.contains(".") && Settings.getInstance().get("arenas." + s + ".loc") != null) {
					final Location loc = LocationUtils
							.stringToLocation(Settings.getInstance().get("arenas." + s + ".loc"));
					if (loc == null) {
						continue;
					}
					try {
						this.registerArena(s, loc);
					} catch (Exception ex) {
						Build.getInstance().getLogger().info("Exception ocurred when loading arena: " + s);
						ex.printStackTrace();
					}
				}
			}
		}
	}

	public Arena getArena(final Player p) {
		for (final Arena a : this.arenas) {
			if (a.containsPlayer(p)) {
				return a;
			}
		}
		return null;
	}

	public Arena getArena(final String name) {
		for (final Arena a : this.arenas) {
			if (a.getName().equalsIgnoreCase(name)) {
				return a;
			}
		}
		return null;
	}

	public void removeArena(final Arena a) {
		if (this.arenas.contains(a)) {
			this.arenas.remove(a);
			a.onRemoved();
		}
	}

	public List<Arena> getArenas() {
		return this.arenas;
	}
}
