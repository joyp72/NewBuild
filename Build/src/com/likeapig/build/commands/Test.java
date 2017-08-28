package com.likeapig.build.commands;

import org.bukkit.entity.Player;

import com.likeapig.build.arena.Arena;

public class Test extends Commands {

	public Test() {
		super("build.default", "Test", "", new String[] { "t" });
	}

	@Override
	public void onCommand(final Player sender, final String[] args) {

		// Arena.getData(sender).increaseScore(3);

		if (args.length == 0) {
			for (String s : Arena.usedWords) {
				sender.sendMessage(s);
			}
		}

		if (args.length == 1) {
			if (Arena.usedWords.contains(args[0])) {
				String s = args[0];
				Arena.usedWords.remove(s);
				sender.sendMessage(s + " removed");
			} else {
				sender.sendMessage("not containted boi.");
			}
		}
	}

}
