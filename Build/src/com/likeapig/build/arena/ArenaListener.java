package com.likeapig.build.arena;

import java.util.ArrayList;
import java.util.List;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.Statistic.Type;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.block.BlockPlaceEvent;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.entity.FoodLevelChangeEvent;
import org.bukkit.event.player.AsyncPlayerChatEvent;
import org.bukkit.event.player.PlayerChatEvent;
import org.bukkit.event.player.PlayerDropItemEvent;
import org.bukkit.event.player.PlayerPickupItemEvent;
import org.bukkit.event.player.PlayerQuitEvent;
import org.bukkit.plugin.Plugin;
import org.bukkit.scheduler.BukkitRunnable;

import com.likeapig.build.Build;
import com.likeapig.build.commands.MessageManager;

public class ArenaListener implements Listener {
	private static ArenaListener instance;
	private List<Location> b = new ArrayList<Location>();

	static {
		ArenaListener.instance = new ArenaListener();
	}

	public static ArenaListener get() {
		return ArenaListener.instance;
	}

	public List<Location> getLocation() {
		return b;
	}

	public void addLocation(Location b) {
		this.b.add(b);
	}

	public void removeLocation(Location b) {
		this.b.remove(b);
	}
	
	public void removeBlocks() {
		if (!b.isEmpty()) {
			for (Location loc : getLocation()) {
				new BukkitRunnable() {
					public void run() {
						loc.getBlock().setType(Material.AIR);
					}
				}.runTask(Build.getPlugin(Build.class));
			}
		}
	}

	public void setup() {
		Bukkit.getPluginManager().registerEvents((Listener) this, (Plugin) Build.getInstance());
	}

	@EventHandler
	public void onPlayerLeave(final PlayerQuitEvent e) {
		final Player p = e.getPlayer();
		if (ArenaManager.get().getArena(p) != null) {
			ArenaManager.get().getArena(p).kickPlayer(p);
		}
	}

	@EventHandler
	public void onPlayerPlaceBlock(BlockPlaceEvent e) {
		Player p = e.getPlayer();
		Location l = e.getBlock().getLocation();
		Arena a = ArenaManager.get().getArena(p);
		if (a != null) {
			if (Arena.isBuilder(p)) {
				addLocation(l);
				e.setCancelled(false);
			} else {
				e.setCancelled(true);
			}
		}
	}

	@EventHandler
	public void onPlayerBreakBlock(final BlockBreakEvent e) {
		final Player p = e.getPlayer();
		final Arena a = ArenaManager.get().getArena(p);
		if (a != null) {
			if (!getLocation().contains(e.getBlock().getLocation())) {
				e.setCancelled(true);
			}
			getLocation().remove(e.getBlock().getLocation());
		}
	}

	@EventHandler
	public void onPlayerChat(final AsyncPlayerChatEvent e) {
		final Arena a = ArenaManager.get().getArena(e.getPlayer());
		if (a != null) {
			a.handleChat(e);
		}
	}
	
	@EventHandler
	public void onPlayerBubbleChat(final PlayerChatEvent e) {
		final Arena a = ArenaManager.get().getArena(e.getPlayer());
		if (a != null) {
			a.handleBubbleChat(e);
		}
	}

	@EventHandler
	public void onPlayerDropItem(final PlayerDropItemEvent e) {
		final Player p = e.getPlayer();
		if (ArenaManager.get().getArena(p) != null) {
			e.setCancelled(true);
		}
	}

	@EventHandler
	public void onPlayerPickItem(final PlayerPickupItemEvent e) {
		final Player p = e.getPlayer();
		if (ArenaManager.get().getArena(p) != null) {
			e.setCancelled(true);
		}
	}

	@EventHandler
	public void onPlayerDamaged(final EntityDamageEvent e) {
		if (e.getEntity() instanceof Player) {
			final Player p = (Player) e.getEntity();
			if (ArenaManager.get().getArena(p) != null) {
				e.setCancelled(true);
			}
		}
	}

	@EventHandler
	public void onPlayerHungerChnage(final FoodLevelChangeEvent e) {
		if (e.getEntity() instanceof Player) {
			final Player p = (Player) e.getEntity();
			if (ArenaManager.get().getArena(p) != null) {
				e.setCancelled(true);
			}
		}
	}
}