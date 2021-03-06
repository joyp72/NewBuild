package com.likeapig.build.arena;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.inventory.meta.SkullMeta;

import com.likeapig.build.arena.Arena.ArenaState;

import net.md_5.bungee.api.ChatColor;

public class Menus {

	private int size = 9;
	private int slot = 0;
	private String mm = "Main Menu";
	private String sm = "Store";
	private static HashMap<Player, Inventory> menus = new HashMap<Player, Inventory>();
	private static HashMap<Player, Inventory> stores = new HashMap<Player, Inventory>();

	public static HashMap<Player, Inventory> getMenus() {
		return menus;
	}

	public static HashMap<Player, Inventory> getStores() {
		return stores;
	}

	public static void resetInvs(Player p) {
		if (menus.get(p) != null) {
			menus.remove(p);
		}
		if (stores.get(p) != null) {
			stores.remove(p);
		}
	}

	public static boolean containsInv(Inventory i) {
		return menus.containsValue(i) || stores.containsValue(i);
	}

	private final static int CENTER_PX = 34 * 3;

	public Menus(Player p) {
		Inventory mi = Bukkit.createInventory(p, size, mm);
		if (menus.get(p) == null) {
			menus.put(p, mi);
		}
		Inventory si = Bukkit.createInventory(p, 45, sm);
		if (stores.get(p) == null) {
			stores.put(p, si);
		}

		ItemStack store = new ItemStack(Material.CHEST);
		{
			ItemMeta meta = store.getItemMeta();
			meta.setDisplayName(ChatColor.WHITE + "" + ChatColor.BOLD + "Store");
			ArrayList<String> lore = new ArrayList<>();
			lore.add(ChatColor.GRAY + "Click to open store");
			meta.setLore(lore);
			store.setItemMeta(meta);
			mi.setItem(5, store);
		}

		ItemStack head = new ItemStack(Material.SKULL_ITEM, 1, (short) 3);
		{
			SkullMeta hmeta = (SkullMeta) head.getItemMeta();
			hmeta.setOwner(p.getName());
			hmeta.setDisplayName(ChatColor.WHITE + "" + ChatColor.BOLD + "Player Stats");
			ArrayList<String> lore = new ArrayList<>();
			lore.add(" ");
			Arena a = ArenaManager.get().getArena(p);
			if (a != null) {
				if (Arena.containsPlayer(p)) {
					Data d = Arena.getData(p);
					int score = d.getScore();
					lore.add(ChatColor.GREEN + "Score: " + ChatColor.GRAY + score);
				}
			}
			lore.add(ChatColor.GREEN + "Games Played: " + ChatColor.GRAY + MegaData.getRW(p.getName()));
			lore.add(ChatColor.GREEN + "Games Won: " + ChatColor.GRAY + MegaData.getGW(p.getName()));
			lore.add(ChatColor.GREEN + "Guessed Correct: " + ChatColor.GRAY + MegaData.getGC(p.getName()));
			lore.add(" ");
			lore.add(ChatColor.YELLOW + "MegaCoins: " + ChatColor.GRAY + main.MegaData.getMegaCoins(p));
			hmeta.setLore(lore);
			head.setItemMeta(hmeta);
			mi.setItem(1, head);
		}

		ItemStack info = new ItemStack(Material.BOOK_AND_QUILL);
		{
			ItemMeta meta = info.getItemMeta();
			meta.setDisplayName(ChatColor.DARK_AQUA + "" + ChatColor.BOLD + "Mega" + ChatColor.AQUA + ""
					+ ChatColor.BOLD + "Build");
			ArrayList<String> lore = new ArrayList<>();
			lore.add(ChatColor.GRAY + "By like_a_pig");
			lore.add("");
			lore.add(ChatColor.WHITE + "" + ChatColor.BOLD + "Overview: ");
			// lore.add(ChatColor.GRAY + "Once an arena has enough players,");
			// lore.add(ChatColor.GRAY + "a random player in the lobby will become");
			// lore.add(ChatColor.GRAY + "the builder who will be teleported in the");
			// lore.add(ChatColor.GRAY + "middle of the arena and the game will begin.");
			// lore.add(ChatColor.GRAY + "The builder will be given a word to build and");
			// lore.add(ChatColor.GRAY + "the rest of the players must try and guess that");
			// lore.add(ChatColor.GRAY + "word in order to gain points. The player who");
			// lore.add(ChatColor.GRAY + "guesses the word first gains 3 poionts, and any");
			// lore.add(ChatColor.GRAY + "players who guess after that will gain 1 point.");
			// lore.add(ChatColor.GRAY + "On the first time the word is guessed, the
			// builder");
			// lore.add(ChatColor.GRAY + "gains 2 points.");
			String[] l = formatLore(
					"Once an arena has enough players, a random player in the lobby will become the builder who will be teleported in the middle of the arena and the game will begin. The builder will be given a word to build and the rest of the players must try and guess that word in order to gain points. The player who guesses the word first gains 3 points, and any players who guess after that will gain 1 point. On the first time the word is guessed, the builder gains 2 points.",
					30, org.bukkit.ChatColor.GRAY);
			lore.add(l[0]);
			lore.add(l[1]);
			lore.add(l[2]);
			lore.add(l[3]);
			lore.add(l[4]);
			lore.add(l[5]);
			lore.add(l[6]);
			lore.add(l[7]);
			lore.add(l[8]);
			lore.add(l[9]);
			lore.add(l[10]);
			lore.add(l[11]);
			lore.add(l[12]);
			meta.setLore(lore);
			info.setItemMeta(meta);
			mi.setItem(3, info);
		}

		ItemStack blank = new ItemStack(Material.STAINED_GLASS_PANE, 1, (short) 15);
		{
			ItemMeta meta = blank.getItemMeta();
			meta.setDisplayName(" ");
			meta.addItemFlags(ItemFlag.values());
			blank.setItemMeta(meta);
			mi.setItem(0, blank);
			mi.setItem(2, blank);
			mi.setItem(4, blank);
			mi.setItem(6, blank);
			mi.setItem(8, blank);

		}

		if (ArenaManager.get().getArenas().size() > 0) {
			if (ArenaManager.get().getArena(p) == null) {
				if (ArenaManager.get().getArena("Arena") != null) {
					Arena a = ArenaManager.get().getArena("Arena");
					if (a.getState().equals(ArenaState.WAITING)) {
						int num = a.getPlayers().size();
						if (num > 1) {
							ItemStack item = new ItemStack(Material.CONCRETE, num, (byte) 5);
							{
								ItemMeta meta = item.getItemMeta();
								meta.setDisplayName(ChatColor.YELLOW + a.getName());
								ArrayList<String> lore = new ArrayList<>();
								lore.add(ChatColor.GRAY + "(Click to join)");
								lore.add(" ");
								lore.add(ChatColor.GRAY + "Status: " + ChatColor.GREEN + "Online");
								lore.add(ChatColor.GRAY + "Players: " + a.getPlayers().size() + "/12");
								meta.setLore(lore);
								item.setItemMeta(meta);
								mi.setItem(7, item);
							}
						} else {
							ItemStack item = new ItemStack(Material.CONCRETE, 1, (byte) 5);
							{
								ItemMeta meta = item.getItemMeta();
								meta.setDisplayName(ChatColor.YELLOW + a.getName());
								ArrayList<String> lore = new ArrayList<>();
								lore.add(ChatColor.GRAY + "(Click to join)");
								lore.add(" ");
								lore.add(ChatColor.GRAY + "Status: " + ChatColor.GREEN + "Online");
								lore.add(ChatColor.GRAY + "Players: " + a.getPlayers().size() + "/12");
								meta.setLore(lore);
								item.setItemMeta(meta);
							}
							mi.setItem(7, item);
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
								lore.add(" ");
								lore.add(ChatColor.GRAY + "Status: " + ChatColor.RED + "Running");
								lore.add(ChatColor.GRAY + "Players: " + a.getPlayers().size() + "/12");
								meta.setLore(lore);
								item.setItemMeta(meta);
								mi.setItem(7, item);
							}
						} else {
							ItemStack item = new ItemStack(Material.CONCRETE, 1, (byte) 14);
							{
								ItemMeta meta = item.getItemMeta();
								meta.setDisplayName(ChatColor.YELLOW + a.getName());
								ArrayList<String> lore = new ArrayList<>();
								lore.add(" ");
								lore.add(ChatColor.GRAY + "Status: " + ChatColor.RED + "Running");
								lore.add(ChatColor.GRAY + "Players: " + a.getPlayers().size() + "/12");
								meta.setLore(lore);
								item.setItemMeta(meta);
								mi.setItem(7, item);
							}
						}
					}
				}
			} else {
				if (ArenaManager.get().getArenas().size() > 0) {
					ItemStack item = new ItemStack(Material.BEDROCK);
					{
						ItemMeta meta = item.getItemMeta();
						meta.setDisplayName(ChatColor.RED + "" + ChatColor.BOLD + "Leave");
						ArrayList<String> lore = new ArrayList<>();
						Arena a = ArenaManager.get().getArena(p);
						if (a == null) {
							lore.add(ChatColor.GRAY + "You are not in an arena!");
						} else {
							lore.add(ChatColor.GRAY + "Click to leave arena");
						}
						meta.setLore(lore);
						item.setItemMeta(meta);
					}
					mi.setItem(7, item);
				}
			}
		}
	}

	public static String[] formatLore(String text, int size, org.bukkit.ChatColor color) {
		List<String> ret = new ArrayList<String>();

		if (text == null || text.length() == 0)
			return new String[ret.size()];

		String[] words = text.split(" ");
		String rebuild = "";

		int lastAdded = 0;
		for (int i = 0; i < words.length; i++) {
			int wordLen = words[i].length();
			if (rebuild.length() + wordLen > 40 || words[i].contains("\n")
					|| words[i].equals(Character.LINE_SEPARATOR)) {
				lastAdded = i;

				ret.add(color + rebuild);
				rebuild = "";
				if (words[i].equalsIgnoreCase("\n")) {
					words[i] = "";
					continue;
				}

			}
			rebuild = rebuild + " " + words[i];

		}
		if (!rebuild.equalsIgnoreCase(""))
			ret.add(color + rebuild);

		String[] val = new String[ret.size()];
		for (int i = 0; i < ret.size(); i++)
			val[i] = ret.get(i);

		return val;
	}

	public static String sendCenteredMessage(String message) {
		message = ChatColor.translateAlternateColorCodes('&', message);

		int messagePxSize = 0;
		boolean previousCode = false;
		boolean isBold = false;

		for (char c : message.toCharArray()) {
			if (c == '�') {
				previousCode = true;
				continue;
			} else if (previousCode == true) {
				previousCode = false;
				if (c == 'l' || c == 'L') {
					isBold = true;
					continue;
				} else
					isBold = false;
			} else {
				DefaultFontInfo dFI = DefaultFontInfo.getDefaultFontInfo(c);
				messagePxSize += isBold ? dFI.getBoldLength() : dFI.getLength();
				messagePxSize++;
			}
		}

		int halvedMessageSize = messagePxSize / 2;
		int toCompensate = CENTER_PX - halvedMessageSize;
		int spaceLength = DefaultFontInfo.SPACE.getLength() + 1;
		int compensated = 0;
		StringBuilder sb = new StringBuilder();
		while (compensated < toCompensate) {
			sb.append(" ");
			compensated += spaceLength;
		}
		return (sb.toString() + message);
	}

	public enum DefaultFontInfo {

		A('A', 5), a('a', 5), B('B', 5), b('b', 5), C('C', 5), c('c', 5), D('D', 5), d('d', 5), E('E', 5), e('e', 5), F(
				'F', 5), f('f', 4), G('G', 5), g('g', 5), H('H', 5), h('h', 5), I('I', 3), i('i', 1), J('J', 5), j('j',
						5), K('K', 5), k('k', 4), L('L', 5), l('l', 1), M('M', 5), m('m', 5), N('N', 5), n('n', 5), O(
								'O',
								5), o('o', 5), P('P', 5), p('p', 5), Q('Q', 5), q('q', 5), R('R', 5), r('r', 5), S('S',
										5), s('s', 5), T('T', 5), t('t', 4), U('U', 5), u('u', 5), V('V', 5), v('v',
												5), W('W', 5), w('w', 5), X('X', 5), x('x', 5), Y('Y', 5), y('y', 5), Z(
														'Z', 5), z('z', 5), NUM_1('1', 5), NUM_2('2', 5), NUM_3('3',
																5), NUM_4('4', 5), NUM_5('5', 5), NUM_6('6', 5), NUM_7(
																		'7', 5), NUM_8('8', 5), NUM_9('9', 5), NUM_0(
																				'0', 5), EXCLAMATION_POINT('!',
																						1), AT_SYMBOL('@', 6), NUM_SIGN(
																								'#',
																								5), DOLLAR_SIGN('$',
																										5), PERCENT('%',
																												5), UP_ARROW(
																														'^',
																														5), AMPERSAND(
																																'&',
																																5), ASTERISK(
																																		'*',
																																		5), LEFT_PARENTHESIS(
																																				'(',
																																				4), RIGHT_PERENTHESIS(
																																						')',
																																						4), MINUS(
																																								'-',
																																								5), UNDERSCORE(
																																										'_',
																																										5), PLUS_SIGN(
																																												'+',
																																												5), EQUALS_SIGN(
																																														'=',
																																														5), LEFT_CURL_BRACE(
																																																'{',
																																																4), RIGHT_CURL_BRACE(
																																																		'}',
																																																		4), LEFT_BRACKET(
																																																				'[',
																																																				3), RIGHT_BRACKET(
																																																						']',
																																																						3), COLON(
																																																								':',
																																																								1), SEMI_COLON(
																																																										';',
																																																										1), DOUBLE_QUOTE(
																																																												'"',
																																																												3), SINGLE_QUOTE(
																																																														'\'',
																																																														1), LEFT_ARROW(
																																																																'<',
																																																																4), RIGHT_ARROW(
																																																																		'>',
																																																																		4), QUESTION_MARK(
																																																																				'?',
																																																																				5), SLASH(
																																																																						'/',
																																																																						5), BACK_SLASH(
																																																																								'\\',
																																																																								5), LINE(
																																																																										'|',
																																																																										1), TILDE(
																																																																												'~',
																																																																												5), TICK(
																																																																														'`',
																																																																														2), PERIOD(
																																																																																'.',
																																																																																1), COMMA(
																																																																																		',',
																																																																																		1), SPACE(
																																																																																				' ',
																																																																																				3), DEFAULT(
																																																																																						'a',
																																																																																						4);

		private char character;
		private int length;

		DefaultFontInfo(char character, int length) {
			this.character = character;
			this.length = length;
		}

		public char getCharacter() {
			return this.character;
		}

		public int getLength() {
			return this.length;
		}

		public int getBoldLength() {
			if (this == DefaultFontInfo.SPACE)
				return this.getLength();
			return this.length + 1;
		}

		public static DefaultFontInfo getDefaultFontInfo(char c) {
			for (DefaultFontInfo dFI : DefaultFontInfo.values()) {
				if (dFI.getCharacter() == c)
					return dFI;
			}
			return DefaultFontInfo.DEFAULT;
		}
	}
}