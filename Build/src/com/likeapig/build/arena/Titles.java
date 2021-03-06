package com.likeapig.build.arena;

import org.bukkit.craftbukkit.v1_12_R1.entity.CraftPlayer;
import org.bukkit.entity.Player;

import net.minecraft.server.v1_12_R1.IChatBaseComponent.ChatSerializer;
import net.minecraft.server.v1_12_R1.PacketPlayOutTitle;
import net.minecraft.server.v1_12_R1.PacketPlayOutTitle.EnumTitleAction;

public class Titles {
	
	public static Titles instance;
	
	static {
		Titles.instance = new Titles();
	}
	
	public static Titles get() {
		return instance;
	}
	
	private Titles() {}
	
	public void addTitle(Player p, String msg) {
		StringBuilder message = new StringBuilder();
		message.append(msg + "");
		
		PacketPlayOutTitle title = new PacketPlayOutTitle(EnumTitleAction.TITLE, ChatSerializer.a("{\"text\": \"" + message + "\"}"));
		
		((CraftPlayer) p).getHandle().playerConnection.sendPacket(title);
	}
	
	public void addSubTitle(Player p, String msg) {
		StringBuilder message = new StringBuilder();
		message.append(msg + "");
		
		PacketPlayOutTitle subtitle = new PacketPlayOutTitle(EnumTitleAction.SUBTITLE, ChatSerializer.a("{\"text\": \"" + message + "\"}"));
		PacketPlayOutTitle length = new PacketPlayOutTitle(5, 110, 5);
		
		((CraftPlayer) p).getHandle().playerConnection.sendPacket(subtitle);
		((CraftPlayer) p).getHandle().playerConnection.sendPacket(length);
	}

}
