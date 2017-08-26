package com.likeapig.build.arena;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;

import com.likeapig.build.Build;
import com.likeapig.build.commands.MessageManager;
import com.likeapig.build.commands.MessageManager.MessageType;
import com.likeapig.build.store.StoreItems;

import net.md_5.bungee.api.ChatColor;

public class MenusListener implements Listener {

	private static MenusListener instance;

	static {
		instance = new MenusListener();
	}

	public static MenusListener get() {
		return instance;
	}

	public void setup() {
		Bukkit.getPluginManager().registerEvents(this, Build.getInstance());
	}

	@EventHandler
	public void onClick(InventoryClickEvent e) {

		// Main Menu
		if (e.getInventory().getName().equalsIgnoreCase(Menus.getInvMain().getName())) {
			if (e.getCurrentItem() == null) {
				return;
			}
			if (e.getCurrentItem().getType() == Material.WORKBENCH) {
				e.setCancelled(true);
				if (e.getWhoClicked() instanceof Player) {
					Player p = (Player) e.getWhoClicked();
					p.openInventory(Menus.getInvArenas());
				}
			}
			if (e.getCurrentItem().getType() == Material.SKULL_ITEM
					|| e.getCurrentItem().getType() == Material.BOOK_AND_QUILL
					|| e.getCurrentItem().getType() == Material.STAINED_GLASS_PANE) {
				e.setCancelled(true);
			}

			if (e.getCurrentItem().getType() == Material.CHEST) {
				e.setCancelled(true);
				Player p = (Player) e.getWhoClicked();
				p.openInventory(Menus.getInvStore());

			}
		}

		// Store
		if (e.getInventory().getName().equalsIgnoreCase(Menus.getInvStore().getName())) {
			if (e.getCurrentItem() == null) {
				return;
			}
			if (e.getWhoClicked() instanceof Player) {
				Player p = (Player) e.getWhoClicked();
				String name = p.getName();
				int coins = MegaData.getCoins(name);
				
				if (e.getCurrentItem().getType() == Material.PAPER) {
					e.setCancelled(true);
					if (StoreItems.getActivatedString() != "default") {
						StoreItems.setActivatedString("default");
						p.getOpenInventory().close();
						new StoreItems(p);
						p.openInventory(Menus.getInvStore());
					}
				}
				
				if (e.getCurrentItem().getType() == Material.RECORD_8) {
					e.setCancelled(true);
					if (coins >= 50 && !MegaData.getHalo(name)) {
						MegaData.setHalo(name, true);
						MegaData.setCoins(name, coins - 50);
						MessageManager.get().message(p, "Successfully bought Halo effect!", MessageType.GOOD);
					}
					if (coins < 50 && !MegaData.getHalo(name)) {
						MessageManager.get().message(p, "You do not have enough MegaCoins to afford this item!", MessageType.BAD);
					}
					if (MegaData.getHalo(name) && StoreItems.getActivatedString() != "halo") {
						StoreItems.setActivatedString("halo");
						p.getOpenInventory().close();
						new StoreItems(p);
						p.openInventory(Menus.getInvStore());
					}
				}
				if (e.getCurrentItem().getType() == Material.BEDROCK) {
					e.setCancelled(true);
					p.openInventory(Menus.getInvMain());
				}
				if (e.getCurrentItem().getType() == Material.DOUBLE_PLANT) {
					e.setCancelled(true);
				}
				if (e.getCurrentItem().getType() == Material.STAINED_GLASS_PANE) {
					e.setCancelled(true);
				}
			}
		}

		// Arenas
		if (e.getInventory().getName().equalsIgnoreCase(Menus.getInvArenas().getName())) {
			if (e.getCurrentItem() == null) {
				return;
			}
			if (e.getCurrentItem().getType() == Material.CONCRETE) {
				for (Arena an : ArenaManager.get().getArenas()) {
					if (ChatColor.stripColor(e.getCurrentItem().getItemMeta().getDisplayName())
							.equalsIgnoreCase(an.getName())) {
						e.setCancelled(true);
						String name = ChatColor.stripColor(e.getCurrentItem().getItemMeta().getDisplayName());

						if (e.getWhoClicked() instanceof Player) {
							Player p = (Player) e.getWhoClicked();
							p.closeInventory();
							final Arena a = ArenaManager.get().getArena(p);
							if (a != null) {
								MessageManager.get().message(p, "You are already in an arena",
										MessageManager.MessageType.BAD);
								return;
							}
							final String id = name;
							final Arena a2 = ArenaManager.get().getArena(id);
							if (a2 == null) {
								MessageManager.get().message(p, "Unknown arena", MessageManager.MessageType.BAD);
								return;
							}
							if (a2.getLobby() == null) {
								MessageManager.get().message(p, a2.getName() + " doesn't have a lobby set!",
										MessageType.BAD);
								return;
							}
							if (a2.isStarted()) {
								MessageManager.get().message(p, "Arena has started!", MessageType.BAD);
								return;
							}
							a2.addPlayer(p);

						}
					}
				}
			}
			if (e.getCurrentItem().getType() == Material.BEDROCK) {
				if (ChatColor.stripColor(e.getCurrentItem().getItemMeta().getDisplayName()).equalsIgnoreCase("Leave")) {
					String dName = ChatColor.stripColor(e.getCurrentItem().getItemMeta().getDisplayName());
					if (dName.equalsIgnoreCase("leave")) {
						if (e.getWhoClicked() instanceof Player) {
							Player p = (Player) e.getWhoClicked();
							p.closeInventory();
							final Arena a = ArenaManager.get().getArena(p);
							if (a == null) {
								MessageManager.get().message(p, "You are not in an arena!",
										MessageManager.MessageType.BAD);
								return;
							}
							a.kickPlayer(p);
							MessageManager.get().message(p, "You left the arena!");
						}
					}
				}
				if (ChatColor.stripColor(e.getCurrentItem().getItemMeta().getDisplayName())
						.equalsIgnoreCase("Back to Menu")) {
					String dName = ChatColor.stripColor(e.getCurrentItem().getItemMeta().getDisplayName());
					if (dName.equalsIgnoreCase("back to menu")) {
						if (e.getWhoClicked() instanceof Player) {
							Player p = (Player) e.getWhoClicked();
							p.openInventory(Menus.getInvMain());
						}
					}
				}
			}
		}
	}
}