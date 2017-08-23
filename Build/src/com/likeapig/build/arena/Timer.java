package com.likeapig.build.arena;


import java.util.ArrayList;
import java.util.List;

public class Timer {
	
	private static Timer instance;
	private static List<Countdown> cd;
	
	static {
		instance = new Timer();
	}
	
	public static Timer get() {
		return instance;
	}
	
	private Timer() {
		cd = new ArrayList<Countdown>();
	}
	
	public Timer createTimer(Arena a, String arg, int time) {
		cd.add(new Countdown(a, arg, time));
		return this;
	}
	
	public void startTimer(Arena a, String arg) {
		if (cd.size() > 0) {
			for (Countdown c : cd) {
				if (c.getArena().equals(a) && c.getString().equals(arg)) {
					c.run();
				}
			}
		}
	}
	
	public void stopTasks(Arena a) {
		try {
			if (cd.size() > 0) {
				List<Countdown> clone = new ArrayList<Countdown>();
				for (Countdown c : cd) {
					clone.add(c);
				}
				for (Countdown c : clone) {
					if (c.getArena().equals(a)) {
						cd.remove(c);
					}
				}
				
			}
		}
		catch (Exception ex) {}
	}
	
	public static boolean isCanceled(Arena a, String arg) {
		if (cd.size() > 0) {
			for (Countdown c : cd) {
				if (c.getArena().equals(a) && c.getString().equals(arg)) {
					return false;
				}
			}
		}
		return true;
	}
	
	public static void onTimerEnd(Countdown c) {
		if (cd.contains(c)) {
			cd.remove(c);
		}
	}
	
	public static void updateCd(Countdown o, Countdown n) {
		if (cd.contains(o)) {
			cd.remove(o);
			cd.add(n);
		}
	}
}
