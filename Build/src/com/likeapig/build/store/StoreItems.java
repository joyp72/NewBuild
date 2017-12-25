package com.likeapig.build.store;

import java.util.ArrayList;
import java.util.HashMap;

import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;
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
	// private static Activate activated;

	public static HashMap<Player, Activate> active = new HashMap<Player, Activate>();

	// public Activate getActivated() {
	// return activated;
	// }

	// public void setActivated(Activate a) {
	// activated = a;
	// }

	public static void setActivatedString(Player p, String s) {
		active.get(p).setName(s);
	}

	public static String getActivatedString(Player p) {
		return active.get(p).getName();
	}

	// public Player getActivatedPlayer() {
	// return activated.getPlayer();
	// }

	public StoreItems(Player p) {

		if (active.get(p) == null) {
			active.put(p, new Activate("default", p));
		}

		si = Menus.getStores().get(p);

		ItemStack de = new ItemStack(Material.PAPER);
		{
			ItemMeta meta = de.getItemMeta();
			meta.setDisplayName(ChatColor.WHITE + "" + ChatColor.BOLD + "Default" + ChatColor.RESET + ""
					+ ChatColor.GRAY + " - Particle Effect");
			ArrayList<String> lore = new ArrayList<>();
			if (active.get(p).getName() == "default") {
				lore.add(ChatColor.GRAY + "(Already activated)");
			} else {
				lore.add(ChatColor.GRAY + "(Click to activate)");
			}
			lore.add("");
			lore.add(ChatColor.WHITE + "Desc: " + ChatColor.GRAY + "A dot that appears above your head");
			meta.addItemFlags(ItemFlag.values());
			meta.setLore(lore);
			de.setItemMeta(meta);
			if (active.get(p).getName() == "default") {
				de.addUnsafeEnchantment(Enchantment.ARROW_DAMAGE, 2);
			}
			si.setItem(10, de);
		}

		ItemStack halo = new ItemStack(Material.RECORD_8);
		{
			ItemMeta meta = halo.getItemMeta();
			meta.setDisplayName(ChatColor.WHITE + "" + ChatColor.BOLD + "Halo" + ChatColor.RESET + "" + ChatColor.GRAY
					+ " - Particle Effect");
			ArrayList<String> lore = new ArrayList<>();
			if (!MegaData.getHalo(p.getName())) {
				lore.add(ChatColor.GRAY + "(Click to purchase)");
			}
			if (MegaData.getHalo(p.getName()) && active.get(p).getName() != "halo") {
				lore.add(ChatColor.GRAY + "(Click to activate)");
			}
			if (MegaData.getHalo(p.getName()) && active.get(p).getName() == "halo") {
				lore.add(ChatColor.GRAY + "(Already activated)");
			}
			lore.add(" ");
			lore.add(ChatColor.WHITE + "Cost: " + ChatColor.GRAY + "50 " + ChatColor.YELLOW + "MegaCoins");
			lore.add(ChatColor.WHITE + "Desc: " + ChatColor.GRAY + "A circle that appears above your head");
			meta.addItemFlags(ItemFlag.values());
			meta.setLore(lore);
			halo.setItemMeta(meta);
			if (MegaData.getHalo(p.getName()) && active.get(p).getName() == "halo") {
				halo.addUnsafeEnchantment(Enchantment.ARROW_DAMAGE, 2);
			}
			si.setItem(11, halo);
		}

		ItemStack more = new ItemStack(Material.SIGN);
		{
			ItemMeta meta = more.getItemMeta();
			meta.setDisplayName(ChatColor.WHITE + "" + ChatColor.BOLD + "More..");
			ArrayList<String> lore = new ArrayList<>();
			lore.add(ChatColor.GRAY + "(More coming next update)");
			meta.setLore(lore);
			more.setItemMeta(meta);
			si.setItem(31, more);
		}

		ItemStack fword = new ItemStack(Material.BOOK);
		{
			ItemMeta meta = fword.getItemMeta();
			meta.setDisplayName(ChatColor.WHITE + "" + ChatColor.BOLD + "First letters" + ChatColor.RESET + ""
					+ ChatColor.GRAY + " - Game Mechanic");
			ArrayList<String> lore = new ArrayList<>();
			lore.add(ChatColor.GRAY + "(Work in progress)");
			lore.add(" ");
			lore.add(ChatColor.WHITE + "Cost: " + ChatColor.GRAY + "20 " + ChatColor.YELLOW + "MegaCoins");
			lore.add(ChatColor.WHITE + "Desc: " + ChatColor.GRAY + "A " + ChatColor.RED + "one time use "
					+ ChatColor.GRAY + "ability that will provide");
			// lore.add(ChatColor.GRAY + "A " + ChatColor.RED + "one time use " +
			// ChatColor.GRAY + "ability that will provide");
			lore.add(ChatColor.GRAY + "          you the first letters of the words for");
			lore.add(ChatColor.GRAY + "          an entire game.");
			meta.setLore(lore);
			fword.setItemMeta(meta);
			si.setItem(19, fword);
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

		ItemStack balance = new ItemStack(Material.DOUBLE_PLANT, 1, (short) 0);
		{
			ItemMeta meta = balance.getItemMeta();
			meta.setDisplayName(ChatColor.YELLOW + "MegaCoins: " + ChatColor.GRAY + MegaData.getCoins(p.getName()));
			ArrayList<String> lore = new ArrayList<>();
			lore.clear();
			meta.setLore(lore);
			balance.setItemMeta(meta);
			si.setItem(28, balance);
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
