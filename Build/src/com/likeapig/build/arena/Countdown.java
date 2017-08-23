package com.likeapig.build.arena;

import org.bukkit.scheduler.BukkitRunnable;

import com.likeapig.build.Build;

public class Countdown extends BukkitRunnable {
	
	private int timer;
	private Arena a;
	private boolean done;
	private String arg;
	
	public Countdown(Arena a, String arg, int time) {
		this.timer = time;
		this.arg = arg;
		this.a = a;
	}
	
	public Arena getArena() {
		return a;
	}
	
	public String getString() {
		return arg;
	}
	
	public int getTimer() {
		return timer;
	}
	
	public void run() {
		Timer.get();
		if (Timer.isCanceled(a, arg)) {
			return;
		}
		if (timer <= 0) {
			Timer.onTimerEnd(this);
			a.onTimerEnd(arg);
			return;
		}
		a.onTimerTick(arg, timer);
		--timer;
		Countdown c = new Countdown(a, arg, timer);
		Timer.updateCd(this, c);
		c.runTaskLater(Build.getInstance(), 20L);
	}
	
	public boolean isDone() {
		return done;
	}

}
