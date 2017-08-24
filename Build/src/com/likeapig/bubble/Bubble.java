package com.likeapig.bubble;

import org.bukkit.Location;
import org.bukkit.entity.ArmorStand;
import org.bukkit.scheduler.BukkitTask;

public class Bubble implements Runnable{
	
	private BukkitTask toCancel;
	private Location loc;
	private Location floc;
	private String msg;
	private ArmorStand AS;

	public Bubble(Location location, String message) {
		loc = location.clone();
		floc = location.clone();
		msg = message;
		
		AS = loc.getWorld().spawn(location, ArmorStand.class);
		AS.setGravity(false);
		AS.setCollidable(false);
		AS.setVisible(false);
		AS.setInvulnerable(true);
		AS.setCustomNameVisible(true);
		AS.setCustomName(msg);
		AS.setSilent(true);
		AS.setGliding(true);

	}


	@Override
	public void run() {
		floc = floc.clone().add(0, 0.1, 0);
		AS.teleport(floc);
		if (floc.getY() > loc.getY() + 10) {
			AS.remove();
			toCancel.cancel();
		}
	}
	
	
	public void setCancelTask(BukkitTask task) {
		toCancel = task;
	}

}
