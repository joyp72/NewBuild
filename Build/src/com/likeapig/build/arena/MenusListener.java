package com.likeapig.build.arena;

import java.util.List;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.player.PlayerJoinEvent;

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
	public void onJoin(PlayerJoinEvent e) {
		Player p = e.getPlayer();
		int coins = MegaData.getCoins(p.getName());
		if (coins > 0) {
			main.MegaData.addMegaCoins(p, coins);
			MegaData.setCoins(p.getName(), 0);
			return;
		}
	}

	@EventHandler
	public void onClick(InventoryClickEvent e) {

		// Main Menu
		if (Menus.getMenus() != null) {
			if (Menus.getMenus().containsValue(e.getInventory())) {
				if (e.getCurrentItem() == null) {
					return;
				}
				if (e.getCurrentItem().getType() == Material.CONCRETE) {
					e.setCancelled(true);
					String name = ChatColor.stripColor(e.getCurrentItem().getItemMeta().getDisplayName());
					final String id = name;
					final Arena a2 = ArenaManager.get().getArena(id);
					if (a2 != null) {
						if (e.getWhoClicked() instanceof Player) {
							Player p = (Player) e.getWhoClicked();
							p.closeInventory();
							final Arena a = ArenaManager.get().getArena(p);
							if (a != null) {
								MessageManager.get().message(p, "You are already in an arena",
										MessageManager.MessageType.BAD);
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
				if (e.getCurrentItem().getType() == Material.BEDROCK) {
					e.setCancelled(true);
					if (ChatColor.stripColor(e.getCurrentItem().getItemMeta().getDisplayName())
							.equalsIgnoreCase("Leave")) {
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
							}
						}
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
					Menus.resetInvs(p);
					new Menus(p);
					new StoreItems(p);
					Bukkit.getScheduler().scheduleSyncDelayedTask(Build.getInstance(), new Runnable() {
						public void run() {
							p.openInventory(Menus.getStores().get(p));
						}
					}, 1L);
				}
			}
		}

		// Store
		if (Menus.getStores() != null) {
			if (Menus.getStores().containsValue(e.getInventory())) {
				if (e.getCurrentItem() == null) {
					return;
				}
				if (e.getWhoClicked() instanceof Player) {
					Player p = (Player) e.getWhoClicked();
					String name = p.getName();
					int coins = main.MegaData.getMegaCoins(p);

					if (e.getCurrentItem().getType() == Material.PAPER) {
						e.setCancelled(true);
						if (StoreItems.getActivatedString(p) != "default") {
							StoreItems.setActivatedString(p, "default");
							p.getOpenInventory().close();
							new StoreItems(p);
							p.openInventory(Menus.getStores().get(p));
						}
					}

					if (e.getCurrentItem().getType() == Material.RECORD_8) {
						e.setCancelled(true);
						List<String> purchased = MegaData.get().getPurchased(p.getName());
						if (coins >= 20 && !purchased.contains("halo")) {
							MegaData.get().addPurchased(p.getName(), "halo");
							main.MegaData.spendMegaCoins(p, 20);
							MessageManager.get().message(p, "Successfully bought Halo effect!", MessageType.GOOD);
							StoreItems.setActivatedString(p, "halo");
							p.getOpenInventory().close();
							new StoreItems(p);
							p.openInventory(Menus.getStores().get(p));
						}
						if (coins < 20 && !purchased.contains("halo")) {
							MessageManager.get().message(p, "You do not have enough MegaCoins to afford this item!",
									MessageType.BAD);
						}
						if (purchased.contains("halo") && StoreItems.getActivatedString(p) != "halo") {
							StoreItems.setActivatedString(p, "halo");
							p.getOpenInventory().close();
							new StoreItems(p);
							p.openInventory(Menus.getStores().get(p));
						}
					}
					if (e.getCurrentItem().getType() == Material.REDSTONE) {
						e.setCancelled(true);
						List<String> purchased = MegaData.get().getPurchased(p.getName());
						if (coins >= 35 && !purchased.contains("santaHat")) {
							MegaData.get().addPurchased(p.getName(), "santaHat");
							main.MegaData.spendMegaCoins(p, 35);
							MessageManager.get().message(p, "Successfully bought Santa Hat effect!", MessageType.GOOD);
							StoreItems.setActivatedString(p, "santaHat");
							p.getOpenInventory().close();
							new StoreItems(p);
							p.openInventory(Menus.getStores().get(p));
						}
						if (coins < 35 && !purchased.contains("santaHat")) {
							MessageManager.get().message(p, "You do not have enough MegaCoins to afford this item!",
									MessageType.BAD);
						}
						if (purchased.contains("santaHat") && StoreItems.getActivatedString(p) != "santaHat") {
							StoreItems.setActivatedString(p, "santaHat");
							p.getOpenInventory().close();
							new StoreItems(p);
							p.openInventory(Menus.getStores().get(p));
						}
					}
					if (e.getCurrentItem().getType() == Material.FEATHER) {
						e.setCancelled(true);
						List<String> purchased = MegaData.get().getPurchased(p.getName());
						if (coins >= 50 && !purchased.contains("wings")) {
							MegaData.get().addPurchased(p.getName(), "wings");
							main.MegaData.spendMegaCoins(p, 50);
							MessageManager.get().message(p, "Successfully bought Wings effect!", MessageType.GOOD);
							StoreItems.setActivatedString(p, "wings");
							p.getOpenInventory().close();
							new StoreItems(p);
							p.openInventory(Menus.getStores().get(p));
						}
						if (coins < 50 && !purchased.contains("wings")) {
							MessageManager.get().message(p, "You do not have enough MegaCoins to afford this item!",
									MessageType.BAD);
						}
						if (purchased.contains("wings") && StoreItems.getActivatedString(p) != "wings") {
							StoreItems.setActivatedString(p, "wings");
							p.getOpenInventory().close();
							new StoreItems(p);
							p.openInventory(Menus.getStores().get(p));
						}
					}
					if (e.getCurrentItem().getType() == Material.BEDROCK) {
						e.setCancelled(true);
						Menus.resetInvs(p);
						new Menus(p);
						new StoreItems(p);
						Bukkit.getScheduler().scheduleSyncDelayedTask(Build.getInstance(), new Runnable() {
							public void run() {
								p.openInventory(Menus.getMenus().get(p));
							}
						}, 1L);
					}
					if (e.getCurrentItem().getType() == Material.STAINED_GLASS_PANE
							|| e.getCurrentItem().getType() == Material.BOOK
							|| e.getCurrentItem().getType() == Material.DOUBLE_PLANT
							|| e.getCurrentItem().getType() == Material.SIGN) {
						e.setCancelled(true);
					}
				}
			}
		}
	}
}