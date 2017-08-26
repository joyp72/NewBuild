package com.likeapig.build.store;

import java.util.ArrayList;

import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import com.likeapig.build.arena.MegaData;
import com.likeapig.build.arena.Menus;

import net.md_5.bungee.api.ChatColor;

public class StoreItems {

	public Inventory si;

	public StoreItems(Player p) {

		si = Menus.getInvStore();

		ItemStack halo = new ItemStack(Material.RECORD_8);
		{
			ItemMeta meta = halo.getItemMeta();
			meta.setDisplayName(ChatColor.WHITE + "" + ChatColor.BOLD + "Halo" + ChatColor.RESET + "" + ChatColor.GRAY
					+ " - Particle Effect");
			ArrayList<String> lore = new ArrayList<>();
			if (!MegaData.getHalo(p.getName())) {
				lore.add(ChatColor.GRAY + "(Click to purchase)");
			}
			if (MegaData.getHalo(p.getName())) {
				lore.add(ChatColor.GRAY + "(Already purchased)");
			}
			lore.add(" ");
			lore.add(ChatColor.WHITE + "Cost: " + ChatColor.GRAY + "50 " + ChatColor.YELLOW + "MegaCoins");
			lore.add(ChatColor.WHITE + "Description: " + ChatColor.GRAY + "A circle that appears above your head");
			meta.addItemFlags(ItemFlag.values());
			meta.setLore(lore);
			halo.setItemMeta(meta);
			si.setItem(10, halo);
		}

		ItemStack back = new ItemStack(Material.BEDROCK);
		{
			ItemMeta meta = back.getItemMeta();
			meta.setDisplayName(ChatColor.RED + "" + ChatColor.BOLD + "Back to Menu");
			ArrayList<String> lore = new ArrayList<>();
			lore.add(ChatColor.GRAY + "Click to go back to Menu");
			meta.setLore(lore);
			back.setItemMeta(meta);
			si.setItem(34, back);
		}

		ItemStack blank = new ItemStack(Material.STAINED_GLASS_PANE, 1, (short) 15);
		{
			ItemMeta meta = blank.getItemMeta();
			meta.setDisplayName(" ");
			meta.addItemFlags(ItemFlag.values());
			blank.setItemMeta(meta);
			si.setItem(0, blank);
			si.setItem(1, blank);
			si.setItem(2, blank);
			si.setItem(3, blank);
			si.setItem(4, blank);
			si.setItem(5, blank);
			si.setItem(6, blank);
			si.setItem(7, blank);
			si.setItem(8, blank);
			si.setItem(9, blank);
			si.setItem(17, blank);
			si.setItem(18, blank);
			si.setItem(26, blank);
			si.setItem(27, blank);
			si.setItem(35, blank);
			si.setItem(36, blank);
			si.setItem(37, blank);
			si.setItem(38, blank);
			si.setItem(39, blank);
			si.setItem(40, blank);
			si.setItem(41, blank);
			si.setItem(42, blank);
			si.setItem(43, blank);
			si.setItem(44, blank);

		}

	}

}