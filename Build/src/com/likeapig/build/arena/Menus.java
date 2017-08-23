package com.likeapig.build.arena;

import java.util.ArrayList;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.inventory.meta.SkullMeta;

import com.likeapig.build.arena.Arena.ArenaState;

import net.md_5.bungee.api.ChatColor;

public class Menus {

	private static Inventory ai;
	private static Inventory mi;
	private int size = 9;
	private int slot = 0;
	private String bm = "Arenas";
	private String mm = "Main Menu";

	public static Inventory getInvArenas() {
		return ai;
	}

	public static Inventory getInvMain() {
		return mi;
	}

	public Menus(Player p) {
		ai = Bukkit.createInventory(p, size, bm);
		mi = Bukkit.createInventory(p, size, mm);

		ItemStack arenas = new ItemStack(Material.WORKBENCH);
		{
			ItemMeta meta = arenas.getItemMeta();
			meta.setDisplayName(ChatColor.GOLD + bm);
			ArrayList<String> lore = new ArrayList<>();
			lore.add(ChatColor.GRAY + "Click to view arenas!");
			meta.setLore(lore);
			arenas.setItemMeta(meta);
			mi.setItem(6, arenas);
		}
		
		ItemStack head = new ItemStack(Material.SKULL_ITEM, 1, (short) 3);
		{
			SkullMeta hmeta = (SkullMeta) head.getItemMeta();
			hmeta.setOwner(p.getName());
			hmeta.setDisplayName(ChatColor.GOLD + "Player Stats");
			ArrayList<String> lore = new ArrayList<>();
			lore.add(" ");
			lore.add(ChatColor.GRAY + "=-=-=-=-=-=-=-=-=");
			lore.add(" ");
			lore.add(ChatColor.AQUA + "Name: " + p.getName());
			lore.add(ChatColor.AQUA + "more stats...");
			lore.add(" ");
			lore.add(ChatColor.GRAY + "=-=-=-=-=-=-=-=-=");
			hmeta.setLore(lore);
			head.setItemMeta(hmeta);
			mi.setItem(2, head);
		}

		if (ArenaManager.get().getArenas().size() > 0) {
			for (Arena a : ArenaManager.get().getArenas()) {
				if (slot < 9) {
					if (a.getState().equals(ArenaState.WAITING)) {
						int num = a.getPlayers().size();
						if (num > 1) {
							ItemStack item = new ItemStack(Material.CONCRETE, num, (byte) 5);
							{
								ItemMeta meta = item.getItemMeta();
								meta.setDisplayName(ChatColor.YELLOW + a.getName());
								ArrayList<String> lore = new ArrayList<>();
								lore.add(ChatColor.GREEN + "Click to join!");
								lore.add(ChatColor.GRAY + "Players: " + a.getPNames().toString());
								meta.setLore(lore);
								item.setItemMeta(meta);
								ai.setItem(slot, item);
								slot++;
							}
						} else {
							ItemStack item = new ItemStack(Material.CONCRETE, 1, (byte) 5);
							{
								ItemMeta meta = item.getItemMeta();
								meta.setDisplayName(ChatColor.YELLOW + a.getName());
								ArrayList<String> lore = new ArrayList<>();
								lore.add(ChatColor.GREEN + "Click to join!");
								lore.add(ChatColor.GRAY + "Players: " + a.getPNames().toString());
								meta.setLore(lore);
								item.setItemMeta(meta);
							}
							ai.setItem(slot, item);
							slot++;
						}
					}
					if (a.isStarted()) {
						int num = a.getPlayers().size();
						if (num > 1) {
							ItemStack item = new ItemStack(Material.CONCRETE, num, (byte) 14);
							{
								ItemMeta meta = item.getItemMeta();
								meta.setDisplayName(ChatColor.YELLOW + a.getName());
								ArrayList<String> lore = new ArrayList<>();
								lore.add(ChatColor.RED + "Arena has already started!");
								lore.add(ChatColor.GRAY + "Players: " + a.getPNames().toString());
								meta.setLore(lore);
								item.setItemMeta(meta);
								ai.setItem(slot, item);
								slot++;
							}
						} else {
							ItemStack item = new ItemStack(Material.CONCRETE, 1, (byte) 14);
							{
								ItemMeta meta = item.getItemMeta();
								meta.setDisplayName(ChatColor.YELLOW + a.getName());
								ArrayList<String> lore = new ArrayList<>();
								lore.add(ChatColor.RED + "Arena has already started!");
								lore.add(ChatColor.GRAY + "Players: " + a.getPNames().toString());
								meta.setLore(lore);
								item.setItemMeta(meta);
								ai.setItem(slot, item);
								slot++;
							}
						}
					}
				}
			}
		}

		Arena pa = ArenaManager.get().getArena(p);
		
		if (pa == null) {
			ItemStack back = new ItemStack(Material.BEDROCK);
			{
				ItemMeta meta  = back.getItemMeta();
				meta.setDisplayName(ChatColor.RED + "Back to Menu");
				ArrayList<String> lore = new ArrayList<>();
				lore.add(ChatColor.GRAY + "Click to go back to Menu!");
				meta.setLore(lore);
				back.setItemMeta(meta);
			}
			ai.setItem(8, back);
		}
		
		if (pa != null) {
			if (ArenaManager.get().getArenas().size() > 0) {
				ItemStack item = new ItemStack(Material.BEDROCK);
				{
					ItemMeta meta = item.getItemMeta();
					meta.setDisplayName(ChatColor.RED + "Leave");
					ArrayList<String> lore = new ArrayList<>();
					Arena a = ArenaManager.get().getArena(p);
					if (a == null) {
						lore.add(ChatColor.GRAY + "You are not in an arena!");
					} else {
						lore.add(ChatColor.GRAY + "Click to leave arena!");
					}
					meta.setLore(lore);
					item.setItemMeta(meta);
				}
				ai.setItem(8, item);
			}
		}

	}
}