package com.likeapig.build.arena;

import org.bukkit.Bukkit;
import org.bukkit.craftbukkit.v1_12_R1.entity.CraftPlayer;
import org.bukkit.entity.Player;

import com.likeapig.build.Build;
import com.mojang.authlib.GameProfile;
import com.mojang.authlib.properties.Property;

public class DisguiseClass {
	
	public static String getUUID(String name) {
		return Bukkit.getOfflinePlayer(name).getUniqueId().toString().replace("-", "");
	}

	public static void disguise(Player p, String args) {
		String name = args;
		
		GameProfile gp = ((CraftPlayer)p).getProfile();
		gp.getProperties().clear();
		Skin skin = new Skin(getUUID(name));
		
		if(skin.getSkinName() != null) {
			gp.getProperties().put(skin.getSkinName(),  new Property(skin.getSkinName(), skin.getSkinValue(), skin.getSkinSignatur()));
		}
		
		Bukkit.getScheduler().runTaskLater(Build.getInstance(), new Runnable() {
			
			@Override
			public void run() {
				for(Player pl : Bukkit.getOnlinePlayers()) {
					pl.hidePlayer((Player)p);
				}
			}
		}, 1);
		
		Bukkit.getScheduler().runTaskLater(Build.getInstance(), new Runnable() {
			
			@Override
			public void run() {
				for (Player pl : Bukkit.getOnlinePlayers()) {
					pl.showPlayer((Player)p);
				}
			}
		}, 20);
	}
	
	public static void removeDisguise(Player p) {
		String name = p.getName();
		
		GameProfile gp = ((CraftPlayer)p).getProfile();
		gp.getProperties().clear();
		Skin sname = new Skin(getUUID(name));
		
		if(sname.getSkinName() != null) {
			gp.getProperties().put(sname.getSkinName(),  new Property(sname.getSkinName(), sname.getSkinValue(), sname.getSkinSignatur()));
		}
		
		Bukkit.getScheduler().runTaskLater(Build.getInstance(), new Runnable() {
			
			@Override
			public void run() {
				for(Player pl : Bukkit.getOnlinePlayers()) {
					pl.hidePlayer((Player)p);
				}
			}
		}, 1);
		
		Bukkit.getScheduler().runTaskLater(Build.getInstance(), new Runnable() {
			
			@Override
			public void run() {
				for (Player pl : Bukkit.getOnlinePlayers()) {
					pl.showPlayer((Player)p);
				}
			}
		}, 20);
		
	}

}
