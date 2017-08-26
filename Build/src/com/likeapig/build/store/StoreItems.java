package com.likeapig.build.store;

import java.util.ArrayList;

import org.bukkit.Material;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import com.likeapig.build.arena.Menus;

import net.md_5.bungee.api.ChatColor;

public class StoreItems {
	
	public StoreItems() {
		
		ItemStack halo = new ItemStack(Material.RECORD_8);
		{
			ItemMeta meta = halo.getItemMeta();
			meta.setDisplayName(ChatColor.WHITE + "" + ChatColor.BOLD +  "Halo");
			ArrayList<String> lore = new ArrayList<>();
			lore.add(" ");
			lore.add(ChatColor.GRAY + "A circle that appears above your head");
			meta.addItemFlags(ItemFlag.values());
			meta.setLore(lore);
			halo.setItemMeta(meta);
			Menus.getInvStore().setItem(10, halo);
		}
		
	}

}
