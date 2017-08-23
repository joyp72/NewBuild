package com.likeapig.build.commands;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import com.likeapig.build.Build;
import com.likeapig.build.arena.Menus;
import com.likeapig.build.commands.MessageManager.MessageType;

public class CommandsManager implements CommandExecutor {

	private List<Commands> cmds;
	private static CommandsManager instance;
	private boolean gui;

	static {
		instance = new CommandsManager();
	}

	public static CommandsManager get() {
		return instance;
	}

	private CommandsManager() {
		cmds = new ArrayList<Commands>();
	}

	public void setup() {
		Build.getInstance().getCommand("b").setExecutor(this);
		cmds.add(new Create());
		cmds.add(new Join());
		cmds.add(new Leave());
		cmds.add(new com.likeapig.build.commands.List());
		cmds.add(new Start());
		cmds.add(new Stop());
		cmds.add(new Score());
		cmds.add(new SetLobby());
		cmds.add(new Gui());
		gui = true;
	}

	public boolean onCommand(CommandSender sender, Command cmd, String arg2, String[] args) {
		if (!(sender instanceof Player)) {
			return true;
		}
		Player p = (Player) sender;
		if (!cmd.getName().equalsIgnoreCase("b")) {
			return true;
		}
		if (gui && args.length == 0) {
			new Menus(p);
			p.openInventory(Menus.getInvMain());
		}
		if (args.length == 0 && !gui) {
			MessageManager.get().message(p, "Commands List:");
			for (Commands c : cmds) {
				if (!c.noIndex()) {
					MessageManager.get().message(p,
							"/b" + " " + c.getClass().getSimpleName().toLowerCase() + " " + c.getUsage());
				}
			}
			return true;
		}
		if (args.length != 0) {
			Commands c = getCommand(args[0]);
			if (c != null) {
				final List<String> a = new ArrayList<String>(Arrays.asList(args));
				a.remove(0);
				args = a.toArray(new String[a.size()]);
				c.commandPreprocess(p, args);
			}
		}
		return true;
	}

	private Commands getCommand(final String name) {
		for (final Commands c : this.cmds) {
			if (c.getClass().getSimpleName().trim().equalsIgnoreCase(name.trim())) {
				return c;
			}
			String[] aliases;
			for (int length = (aliases = c.getAliases()).length, i = 0; i < length; ++i) {
				final String s = aliases[i];
				if (s.trim().equalsIgnoreCase(name.trim())) {
					return c;
				}
			}
		}
		return null;
	}
}