package com.likeapig.build.arena;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.entity.Player;
import org.bukkit.event.player.AsyncPlayerChatEvent;
import org.bukkit.event.player.PlayerChatEvent;

import com.likeapig.bubble.Bubble;
import com.likeapig.build.Build;
import com.likeapig.build.Settings;
import com.likeapig.build.commands.MessageManager;
import com.likeapig.build.commands.MessageManager.MessageType;
import com.likeapig.build.commands.RemoveDisguise;
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
	private int id;

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
			Titles.get().addTitle(p, "§6§lYou have joined " + getName().toUpperCase());
			ActionBars.get().addActionBar(p, "§e§lScore: " + d.getScore());
		}
	}

	public void removePlayer(Player p) {
		if (containsPlayer(p)) {
			Data d = getData(p);
			d.restore();
			if (p == builder) {
				builderStopEffect();
				DisguiseClass.disguise(p, p.getName());;
			}
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
		builderStopEffect();
		for (Player p : getPlayers()) {
			DisguiseClass.disguise(p, p.getName());;
		}
		startNewRound();
	}

	public void onTimerTick(String arg, int timer) {
		if (arg.equalsIgnoreCase("endround")) {
			countdown = timer;
			if (countdown < 30) {
				for (Player p : getPlayers()) {
					ActionBars.get().addActionBar(p, "§c§lTime left: " + countdown);
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
			builderEffect(builder);
			DisguiseClass.disguise(builder, "bobthebuiler");
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

	public void builderEffect(Player builder) {
		if (builder != null) {
			id = Bukkit.getServer().getScheduler().scheduleSyncRepeatingTask(Build.getInstance(), new Runnable() {

				@Override
				public void run() {
					Location loc = builder.getLocation().add(0, 2, 0);
					displayColoredParticle(loc, "3232FF");
				}
			}, 0L, 0L);
		}
	}
	
	public void builderStopEffect() {
		Bukkit.getServer().getScheduler().cancelTask(id);
	}

	private void onWordGuessed(Data d) {
		if (!wordGuessed) {
			wordGuessed = true;
			message(ChatColor.GREEN + d.getPlayer().getName() + " Guessed the word!");
			MessageManager.get().message(d.getPlayer(), "You are the first to guess correct! (+3)", MessageType.GOOD);
			d.increaseScore(3);
			MegaData.addGC(d.getPlayer().getName(), 1);
			ActionBars.get().addActionBar(d.getPlayer(), "§e§lScore: " + d.getScore());
			getData(builder).increaseScore(2);
			ActionBars.get().addActionBar(builder, "§e§lScore: " + d.getScore());
			MessageManager.get().message(builder, "Someone guessed correct! (+2)", MessageType.GOOD);
		} else {
			message(ChatColor.GREEN + d.getPlayer().getName() + " Guessed the word!");
			MessageManager.get().message(d.getPlayer(), "You guessed correct! (+1)", MessageType.GOOD);
			d.increaseScore(1);
			MegaData.addGC(d.getPlayer().getName(), 1);
			ActionBars.get().addActionBar(d.getPlayer(), "§e§lScore: " + d.getScore());
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
	
	public void handleBubbleChat(PlayerChatEvent e) {
		String s = e.getMessage().toLowerCase();
		Player p = e.getPlayer();
		if (isStarted()) {
			if (p.equals(builder)) {
				e.setCancelled(true);
				return;
			}
			Data d = getData(p);
			if (d.guessedWord()) {
				e.setCancelled(true);
				return;
			}
			if (s.contains(word)) {
				e.setCancelled(true);
				return;
			} else {
				Bubble bubble = new Bubble(p.getLocation(), s);
				bubble.setCancelTask(Bukkit.getScheduler().runTaskTimer(Build.getInstance(), bubble, 0L, 2L));
			}
		} else {
			Bubble bubble = new Bubble(p.getLocation(), s);
			bubble.setCancelTask(Bukkit.getScheduler().runTaskTimer(Build.getInstance(), bubble, 0L, 2L));
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
					MessageManager.get().message(d.getPlayer(),
							ChatColor.BLUE + "§lYou gained a MegaCoin, check your stats!");
				}
				message(s);
			} else {
				message(ChatColor.GOLD + "Winner: " + winners.get(0).getPlayer().getName() + " ("
						+ winners.get(0).getScore() + ")");
				MegaData.addCoins(winners.get(0).getPlayer().getName());
				MegaData.addGW(winners.get(0).getPlayer().getName(), 1);
				MessageManager.get().message(winners.get(0).getPlayer(),
						ChatColor.BLUE + "§lYou gained a MegaCoin, check your stats!");
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
		Titles.get().addTitle(builder, "§9§lYou are the Builder!");
		Titles.get().addSubTitle(builder, "§6You have to build: " + word.toUpperCase());
		selected.increaseBuild();
		teleportBuilderToSpawn();
		teleportAllPlayers();
		ArenaListener.get().removeBlocks();
		Bukkit.getScheduler().scheduleSyncDelayedTask(Build.getInstance(), new Runnable() {
			public void run() {
				Timer.get().createTimer(getArena(), "endround", 30).startTimer(getArena(), "endround");
			}
		}, 20L);
	}

	public void stop() {
		Timer.get().stopTasks(this);
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
	
	public Arena getArena() {
		return this;
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

	public static void displayColoredParticle(Location loc, ParticleEffect type, String hexVal, float xOffset,
			float yOffset, float zOffset) {
		int R = 0;
		int G = 0;
		int B = 0;

		if (hexVal.length() <= 6) {
			R = Integer.valueOf(hexVal.substring(0, 2), 16);
			G = Integer.valueOf(hexVal.substring(2, 4), 16);
			B = Integer.valueOf(hexVal.substring(4, 6), 16);
			if (R <= 0) {
				R = 1;
			}
		} else if (hexVal.length() <= 7 && hexVal.substring(0, 1).equals("#")) {
			R = Integer.valueOf(hexVal.substring(1, 3), 16);
			G = Integer.valueOf(hexVal.substring(3, 5), 16);
			B = Integer.valueOf(hexVal.substring(5, 7), 16);
			if (R <= 0) {
				R = 1;
			}
		}

		loc.setX(loc.getX() + Math.random() * (xOffset / 2 - -(xOffset / 2)));
		loc.setY(loc.getY() + Math.random() * (yOffset / 2 - -(yOffset / 2)));
		loc.setZ(loc.getZ() + Math.random() * (zOffset / 2 - -(zOffset / 2)));

		if (type == ParticleEffect.RED_DUST || type == ParticleEffect.REDSTONE) {
			ParticleEffect.RED_DUST.display(R, G, B, 0.004F, 0, loc, 255.0);
		} else if (type == ParticleEffect.SPELL_MOB || type == ParticleEffect.MOB_SPELL) {
			ParticleEffect.SPELL_MOB.display((float) 255 - R, (float) 255 - G, (float) 255 - B, 1, 0, loc, 255.0);
		} else if (type == ParticleEffect.SPELL_MOB_AMBIENT || type == ParticleEffect.MOB_SPELL_AMBIENT) {
			ParticleEffect.SPELL_MOB_AMBIENT.display((float) 255 - R, (float) 255 - G, (float) 255 - B, 1, 0, loc,
					255.0);
		} else {
			ParticleEffect.RED_DUST.display(0, 0, 0, 0.004F, 0, loc, 255.0D);
		}
	}

	public static void displayColoredParticle(Location loc, String hexVal) {
		int R = 0;
		int G = 0;
		int B = 0;

		if (hexVal.length() <= 6) {
			R = Integer.valueOf(hexVal.substring(0, 2), 16);
			G = Integer.valueOf(hexVal.substring(2, 4), 16);
			B = Integer.valueOf(hexVal.substring(4, 6), 16);
			if (R <= 0) {
				R = 1;
			}
		} else if (hexVal.length() <= 7 && hexVal.substring(0, 1).equals("#")) {
			R = Integer.valueOf(hexVal.substring(1, 3), 16);
			G = Integer.valueOf(hexVal.substring(3, 5), 16);
			B = Integer.valueOf(hexVal.substring(5, 7), 16);
			if (R <= 0) {
				R = 1;
			}
		}
		ParticleEffect.RED_DUST.display(R, G, B, 0.004F, 0, loc, 257D);
	}

	public static void displayColoredParticle(Location loc, String hexVal, float xOffset, float yOffset,
			float zOffset) {
		int R = 0;
		int G = 0;
		int B = 0;

		if (hexVal.length() <= 6) {
			R = Integer.valueOf(hexVal.substring(0, 2), 16);
			G = Integer.valueOf(hexVal.substring(2, 4), 16);
			B = Integer.valueOf(hexVal.substring(4, 6), 16);
			if (R <= 0) {
				R = 1;
			}
		} else if (hexVal.length() <= 7 && hexVal.substring(0, 1).equals("#")) {
			R = Integer.valueOf(hexVal.substring(1, 3), 16);
			G = Integer.valueOf(hexVal.substring(3, 5), 16);
			B = Integer.valueOf(hexVal.substring(5, 7), 16);
			if (R <= 0) {
				R = 1;
			}
		}

		loc.setX(loc.getX() + Math.random() * (xOffset / 2 - -(xOffset / 2)));
		loc.setY(loc.getY() + Math.random() * (yOffset / 2 - -(yOffset / 2)));
		loc.setZ(loc.getZ() + Math.random() * (zOffset / 2 - -(zOffset / 2)));

		ParticleEffect.RED_DUST.display(R, G, B, 0.004F, 0, loc, 257D);
	}

	public static void displayParticleVector(Location loc, ParticleEffect type, float xTrans, float yTrans,
			float zTrans) {
		if (type == ParticleEffect.FIREWORKS_SPARK) {
			ParticleEffect.FIREWORKS_SPARK.display(xTrans, yTrans, zTrans, 0.09F, 0, loc, 257D);
		} else if (type == ParticleEffect.SMOKE || type == ParticleEffect.SMOKE_NORMAL) {
			ParticleEffect.SMOKE.display(xTrans, yTrans, zTrans, 0.04F, 0, loc, 257D);
		} else if (type == ParticleEffect.LARGE_SMOKE || type == ParticleEffect.SMOKE_LARGE) {
			ParticleEffect.LARGE_SMOKE.display(xTrans, yTrans, zTrans, 0.04F, 0, loc, 257D);
		} else if (type == ParticleEffect.ENCHANTMENT_TABLE) {
			ParticleEffect.ENCHANTMENT_TABLE.display(xTrans, yTrans, zTrans, 0.5F, 0, loc, 257D);
		} else if (type == ParticleEffect.PORTAL) {
			ParticleEffect.PORTAL.display(xTrans, yTrans, zTrans, 0.5F, 0, loc, 257D);
		} else if (type == ParticleEffect.FLAME) {
			ParticleEffect.FLAME.display(xTrans, yTrans, zTrans, 0.04F, 0, loc, 257D);
		} else if (type == ParticleEffect.CLOUD) {
			ParticleEffect.CLOUD.display(xTrans, yTrans, zTrans, 0.04F, 0, loc, 257D);
		} else if (type == ParticleEffect.SNOW_SHOVEL) {
			ParticleEffect.SNOW_SHOVEL.display(xTrans, yTrans, zTrans, 0.2F, 0, loc, 257D);
		} else {
			ParticleEffect.RED_DUST.display(0, 0, 0, 0.004F, 0, loc, 257D);
		}
	}
}