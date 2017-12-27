package com.likeapig.build.arena;

import java.util.*;
import org.bukkit.inventory.*;
import org.bukkit.scoreboard.*;

import com.likeapig.build.utils.PlayerUtils;

import org.bukkit.entity.*;
import org.bukkit.*;

public class Data
{
    private UUID id;
    private ItemStack[] content;
    private ItemStack[] armorContents;
    private Location location;
    private GameMode gamemode;
    private int hunger;
    private int level;
    private double life;
    private Location spawn;
    private float exp;
    private Scoreboard sc;
    private final Arena arena;
    private boolean guessedWord;
    private int score;
    private int hasBuild;
    
    protected Data(final Player p, final Arena a) {
        this.guessedWord = false;
        this.score = 0;
        this.hasBuild = 0;
        this.location = p.getLocation();
        this.id = p.getUniqueId();
        this.content = p.getInventory().getContents();
        this.armorContents = p.getInventory().getArmorContents();
        this.gamemode = p.getGameMode();
        this.life = p.getHealth();
        this.hunger = p.getFoodLevel();
        this.spawn = p.getBedSpawnLocation();
        this.level = p.getLevel();
        this.exp = p.getExp();
        this.arena = a;
        this.sc = p.getScoreboard();
    }
    
    public void increaseScore(final int i) {
        this.score += i;
    }
    
    public int getTimeBuild() {
        return this.hasBuild;
    }
    
    protected void increaseBuild() {
        ++this.hasBuild;
    }
    
    public void setHasBuild(final int i) {
        this.hasBuild = i;
    }
    
    public int getScore() {
        return this.score;
    }
    
    protected void setScore(final int i) {
        this.score = i;
    }
    
    protected void setGuessedWord(final boolean b) {
        this.guessedWord = b;
    }
    
    public boolean guessedWord() {
        return this.guessedWord;
    }
    
    protected void resetPlayer() {
        if (Bukkit.getPlayer(this.id) != null) {
            final Player p = Bukkit.getPlayer(this.id);
            PlayerUtils.clearPlayer(p);
        }
    }
    
    protected void setRespawnLoc(final Location l) {
        this.location = l;
    }
    
    protected void restore(final boolean b) {
        final Player p = Bukkit.getPlayer(this.id);
        PlayerUtils.clearPlayer(p);
        p.getInventory().setContents(this.content);
        p.getInventory().setArmorContents(this.armorContents);
        if (p.getVehicle() != null) {
            p.getVehicle().eject();
        }
        if (b) {
            p.teleport(this.location);
        }
        p.setGameMode(this.gamemode);
        p.setHealth(this.life);
        p.setFoodLevel(this.hunger);
        p.setBedSpawnLocation(this.spawn);
        p.setLevel(this.level);
        p.setExp(this.exp);
        p.setScoreboard(this.sc);
    }
    
    protected void restore() {
        this.restore(true);
    }
    
    public Arena getArena() {
        return this.arena;
    }
    
    public boolean isForPlayer(final Player p) {
        return p.isOnline() && this.id == p.getUniqueId();
    }
    
    public Player getPlayer() {
        return Bukkit.getPlayer(this.id);
    }
    
    public Location getLocation() {
        return this.location;
    }
}
