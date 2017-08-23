package com.likeapig.build.utils;

import org.bukkit.entity.*;
import org.bukkit.*;
import org.bukkit.inventory.*;
import org.bukkit.potion.*;
import java.util.*;

public class PlayerUtils
{
    public static void clearPlayer(final Player p) {
        p.setHealth(20.0);
        p.setMaxHealth(20.0);
        p.setFoodLevel(20);
        p.setExp(0.0f);
        p.setLevel(0);
        if (p.getGameMode() != GameMode.CREATIVE) {
            p.setGameMode(GameMode.CREATIVE);
        }
        p.getInventory().clear();
        clearArmor(p);
        p.setItemInHand(new ItemStack(Material.AIR, 1));
        p.updateInventory();
        for (final PotionEffect effect : p.getActivePotionEffects()) {
            p.removePotionEffect(effect.getType());
        }
    }
    
    public static void clearArmor(final Player p) {
        p.getInventory().setHelmet(new ItemStack(Material.AIR, 1));
        p.getInventory().setChestplate(new ItemStack(Material.AIR, 1));
        p.getInventory().setLeggings(new ItemStack(Material.AIR, 1));
        p.getInventory().setBoots(new ItemStack(Material.AIR, 1));
    }
}
