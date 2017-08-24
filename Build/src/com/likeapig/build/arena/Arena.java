package com.likeapig.build.arena;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.entity.Player;
import org.bukkit.event.player.AsyncPlayerChatEvent;

import com.likeapig.build.Settings;
import com.likeapig.build.commands.MessageManager;
import com.likeapig.build.commands.MessageManager.MessageType;
import com.likeapig.build.utils.LocationUtils;

public class Arena {

	private Location spawn;
	private Location lobby;
	private String name;
	private static boolean wordsSetup;
	private static List<String> words;
	private static Player builder;
	private int minPlayers;
	private int maxPlayers;
	private String word;
	private boolean wordGuessed;
	private static List<Data> datas;
	private ArenaState state;
	private int countdown;

	static {
		wordsSetup = false;
	}

	public Arena(String name, Location location) {
		state = ArenaState.STOPPED;
		minPlayers = 2;
		maxPlayers = 6;
		wordGuessed = false;
		datas = new ArrayList<Data>();
		this.name = name;
		spawn = location;
		countdown = 30;
		loadFromConfig();
		saveToConfig();
		checkState();
		if (!Arena.wordsSetup) {
			Arena.words = ConfigManager.WORDS;
			Arena.wordsSetup = true;
		}

	}

	public void setLobby(Location l) {
		lobby = l;
		checkState();
		saveToConfig();
	}

	public Location getLobby() {
		return lobby;
	}

	public void saveToConfig() {
		Settings.getInstance().set("arenas." + this.getName() + ".loc", LocationUtils.locationToString(spawn));
		if (lobby != null) {
			Settings.getInstance().set("arenas." + getName() + ".lobby", LocationUtils.locationToString(lobby));
		}
	}

	public void loadFromConfig() {
		final Settings s = Settings.getInstance();
		if (s.get("arenas." + getName() + ".lobby") != null) {
			String s3 = s.get("arenas." + getName() + ".lobby");
			lobby = LocationUtils.stringToLocation(s3);
		}
		if (s.get("arenas." + this.getName() + ".loc") != null) {
			final String s2 = s.get("arenas." + this.getName() + ".loc");
			spawn = LocationUtils.stringToLocation(s2);
		}
	}

	private void checkState() {
		boolean flag = false;
		if (spawn == null) {
			flag = true;
		}
		if (lobby == null) {
			flag = true;
		}
		if (flag) {
			setState(ArenaState.STOPPED);
			return;
		}
		setState(ArenaState.WAITING);
	}

	public void setState(ArenaState a) {
		state = a;
	}

	public void addPlayer(Player p) {
		if (!containsPlayer(p) && state.canJoin() && getNumberOfPlayer() < maxPlayers) {
			Data d = new Data(p, this);
			datas.add(d);
			d.resetPlayer();
			p.teleport(lobby);
			message(ChatColor.GREEN + p.getName() + " joined the arena!");
			if (state.equals(ArenaState.WAITING) && getNumberOfPlayer() == minPlayers) {
				start();
			}
			Titles.get().addTitle(p, "�6�lYou have joined " + getName().toUpperCase());
			ActionBars.get().addActionBar(p, "�e�lScore: " + d.getScore());
		}
	}

	public void removePlayer(Player p) {
		if (containsPlayer(p)) {
			Data d = getData(p);
			d.restore();
			datas.remove(d);
			if (state.equals(ArenaState.STARTING) && getNumberOfPlayer() < minPlayers) {
				this.setState(ArenaState.WAITING);
			} else if (state.equals(ArenaState.STARTING)
					|| state.equals(ArenaState.STARTED) && getNumberOfPlayer() < minPlayers) {
				stop();
				message(ChatColor.RED + "Players left, stopping game.");
			}
		}
	}

	public void kickAll(boolean b) {
		for (Player p : getPlayers()) {
			if (b) {
				kickPlayer(p);
			} else {
				removePlayer(p);
			}
		}
	}

	public void onTimerEnd(String arg) {
		if (arg.equalsIgnoreCase("endround")) {
			message(ChatColor.RED + "Round has ended.");
			endRound();
		}
	}

	public void endRound() {
		Timer.get().stopTasks(this);
		countdown = 0;
		message(ChatColor.YELLOW + "The word was " + word + ".");
		startNewRound();
	}

	public void onTimerTick(String arg, int timer) {
		if (arg.equalsIgnoreCase("endround")) {
			countdown = timer;
			if (countdown < 30) {
				for (Player p : getPlayers()) {
					ActionBars.get().addActionBar(p, "�c�lTime left: " + countdown);
				}
			}
		}
	}

	public void kickPlayer(Player p) {
		kickPlayer(p, "", true);
	}

	public void kickPlayer(Player p, String message, boolean showLeaveMessage) {
		if (message != "") {
			MessageManager.get().message(p, "Kicked for: " + message);
		}
		removePlayer(p);
		if (showLeaveMessage) {
			message(ChatColor.RED + p.getName() + " left the arena!");
			MessageManager.get().message(p, "You left the arena!", MessageType.BAD);
		}
	}

	public void message(String message) {
		for (Player p : getPlayers()) {
			MessageManager.get().message(p, message);
		}
	}

	private void teleportBuilderToSpawn() {
		if (builder != null) {
			builder.teleport(spawn);
		}
	}

	private void teleportAllPlayers() {
		for (Player p : getPlayers()) {
			if (p != builder) {
				p.teleport(lobby);
			}
		}
	}

	private void getNewWord() {
		Random r = new Random();
		word = words.get(r.nextInt(words.size())).toLowerCase();
	}

	public void setMinPlayers(int i) {
		if (i <= maxPlayers && i > 1) {
			minPlayers = i;
		}
	}

	public void setMaxPlayers(int i) {
		if (i >= minPlayers && i > 1 && i < 25) {
			maxPlayers = i;
		}
	}

	public void onRemoved() {
		if (isStarted()) {
			stop();
		} else {
			for (Player p : getPlayers()) {
				removePlayer(p);
			}
		}
		setState(ArenaState.STOPPED);
	}

	private void onWordGuessed(Data d) {
		if (!wordGuessed) {
			wordGuessed = true;
			message(ChatColor.GREEN + d.getPlayer().getName() + " Guessed the word!");
			MessageManager.get().message(d.getPlayer(), "You are the first to guess correct! (+3)", MessageType.GOOD);
			d.increaseScore(3);
			MegaData.addGC(d.getPlayer().getName(), 1);
			ActionBars.get().addActionBar(d.getPlayer(), "�e�lScore: " + d.getScore());
			getData(builder).increaseScore(2);
			ActionBars.get().addActionBar(builder, "�e�lScore: " + d.getScore());
			MessageManager.get().message(builder, "Someone guessed correct! (+2)", MessageType.GOOD);
		} else {
			message(ChatColor.GREEN + d.getPlayer().getName() + " Guessed the word!");
			MessageManager.get().message(d.getPlayer(), "You guessed correct! (+1)", MessageType.GOOD);
			d.increaseScore(1);
			MegaData.addGC(d.getPlayer().getName(), 1);
			ActionBars.get().addActionBar(d.getPlayer(), "�e�lScore: " + d.getScore());
		}
		d.setGuessedWord(true);
		boolean f = true;
		for (Player p : getPlayers()) {
			if (p != builder && !getData(p).guessedWord()) {
				f = false;
			}
		}
		if (f) {
			message(ChatColor.GREEN + "Everyone has guessed the word!");
			endRound();
		}
	}

	public void handleChat(AsyncPlayerChatEvent e) {
		String s = e.getMessage().toLowerCase();
		Player p = e.getPlayer();
		if (isStarted()) {
			if (p.equals(builder)) {
				MessageManager.get().message(p, "You cannot talk while building", MessageType.BAD);
				e.setCancelled(true);
				return;
			}
			Data d = getData(p);
			if (d.guessedWord()) {
				MessageManager.get().message(p, "You have already guessed the word", MessageType.BAD);
				e.setCancelled(true);
				return;
			}
			if (s.contains(word)) {
				e.setCancelled(true);
				onWordGuessed(d);
			} else {
				e.setMessage(ChatColor.GRAY + s);
			}
		} else {
			e.setMessage(ChatColor.GRAY + s);
		}
	}

	public void startNewRound() {
		getNewWord();
		wordGuessed = false;
		boolean flag = builder == null;
		Data selected = null;
		for (Data p : datas) {
			p.setGuessedWord(false);
			p.resetPlayer();
			if (selected != null) {
				if (p.getTimeBuild() >= selected.getTimeBuild()) {
					continue;
				}
				selected = p;
			} else {
				selected = p;
			}
		}
		builder = selected.getPlayer();
		if (selected.getTimeBuild() > 2 - 1) {
			final List<Data> winners = new ArrayList<Data>();
			for (Data p2 : datas) {
				if (winners.isEmpty()) {
					winners.add(p2);
				} else if (p2.getScore() > winners.get(0).getScore()) {
					winners.clear();
					winners.add(p2);
				} else {
					if (p2.getScore() != winners.get(0).getScore()) {
						continue;
					}
					winners.add(p2);
				}
			}
			message(ChatColor.GOLD + "GAME OVER");
			if (winners.size() > 1) {
				String s = ChatColor.GOLD + "Winners: ";
				for (Data d : winners) {
					if (s.equalsIgnoreCase(ChatColor.GOLD + "Winners: ")) {
						s = String.valueOf(s) + d.getPlayer().getName() + " (" + d.getScore() + ")";
					} else {
						s = String.valueOf(s) + ", " + d.getPlayer().getName() + " (" + d.getScore() + ")";
					}
					MegaData.addCoins(d.getPlayer().getName());
					MegaData.addGW(d.getPlayer().getName(), 1);
					MessageManager.get().message(d.getPlayer(), ChatColor.BLUE + "�lYou gained a MegaCoin, check your stats!");
				}
				message(s);
			} else {
				message(ChatColor.GOLD + "Winner: " + winners.get(0).getPlayer().getName() + " (" + winners.get(0).getScore() + ")");
				MegaData.addCoins(winners.get(0).getPlayer().getName());
				MegaData.addGW(winners.get(0).getPlayer().getName(), 1);
				MessageManager.get().message(winners.get(0).getPlayer(), ChatColor.BLUE + "�lYou gained a MegaCoin, check your stats!");
			}
			stop();
			ArenaListener.get().removeBlocks();
			return;
		}
		if (flag) {
			message(ChatColor.GOLD + "Game has Started!");
			message(ChatColor.BLUE + builder.getName() + " is the Builder!");
		} else {
			message(ChatColor.GOLD + "Next round!");
			message(ChatColor.BLUE + builder.getName() + " is the Builder!");
		}
		MessageManager.get().message(builder, ChatColor.GOLD + "You have to build: " + word);
		Titles.get().addTitle(builder, "�9�lYou are the Builder!");
		Titles.get().addSubTitle(builder, "�6You have to build: " + word.toUpperCase());
		selected.increaseBuild();
		teleportBuilderToSpawn();
		teleportAllPlayers();
		ArenaListener.get().removeBlocks();
		Timer.get().createTimer(this, "endround", 40).startTimer(this, "endround");
	}

	public void stop() {
		if (!(getState().equals(ArenaState.WAITING))) {
			Timer.get().stopTasks(this);
		}
		setState(ArenaState.WAITING);
		kickAll(true);
	}

	public boolean isStarted() {
		return state.equals(ArenaState.STARTED);
	}

	public int getNumberOfPlayer() {
		return datas.size();
	}

	public Player getBuilder() {
		return builder;
	}

	public List<Player> getPlayers() {
		final List<Player> players = new ArrayList<Player>();
		for (Data d : datas) {
			players.add(d.getPlayer());
		}
		return players;
	}

	public List<String> getPNames() {
		List<String> names = new ArrayList<String>();
		for (Data d : datas) {
			if (d.getArena() == this) {
				names.add(d.getPlayer().getName());
			}
		}
		return names;
	}

	public static boolean isBuilder(Player p) {
		if (builder == p) {
			return true;
		}
		return false;
	}

	public static boolean containsPlayer(Player p) {
		for (Data d : datas) {
			if (d.getPlayer().equals(p)) {
				return true;
			}
		}
		return false;
	}

	public static Data getData(Player p) {
		for (Data d : datas) {
			if (d.getPlayer().equals(p)) {
				return d;
			}
		}
		return null;
	}

	public void start() {
		for (Data d : datas) {
			if (d.getArena() == this) {
				MegaData.addRW(d.getPlayer().getName(), 1);
			}
		}
		Arena.builder = null;
		Timer.get().stopTasks(this);
		this.setState(ArenaState.STARTED);
		this.startNewRound();
	}

	public String getName() {
		return name;
	}

	public String getStateName() {
		return state.getName();
	}

	public ArenaState getState() {
		return state;
	}

	public enum ArenaState {
		WAITING("WAITING", 0, "WAITING", true), STARTING("STARTING", 1, "STARTING", true), STARTED("STARTED", 2,
				"STARTED", false), STOPPED("STOPPED", 3, "STOPPED", false);

		private boolean allowJoin;
		private String name;

		private ArenaState(final String s, final int n, final String name, final Boolean allowJoin) {
			this.allowJoin = allowJoin;
			this.name = name;
		}

		public boolean canJoin() {
			return this.allowJoin;
		}

		public String getName() {
			return this.name;
		}
	}
}