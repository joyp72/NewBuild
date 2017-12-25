package Particles;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.entity.Player;

import com.likeapig.build.Build;
import com.likeapig.build.arena.MegaData;
import com.likeapig.build.arena.ParticleEffect;
import com.likeapig.build.store.Activate;
import com.likeapig.build.store.StoreItems;

public class Particles {

	public static Particles instance;

	static {
		instance = new Particles();
	}

	public static Particles get() {
		return instance;
	}

	private int id;
	private int id2;
	private int id3;
	private int id4;

	public Particles() {
	}

	public void addPlayerEffect(Player p) {
		if (p != null) {
			new StoreItems(p);
			if (MegaData.getHalo(p.getName()) && StoreItems.getActivatedString(p) == "halo") {
				PlayerCircleEffect(p);
			} else {
				playerSimpleEffect(p);
			}
		}
	}

	public void addBuilderEffect(Player builder) {
		if (builder != null) {
			if (MegaData.getHalo(builder.getName()) && StoreItems.getActivatedString(builder) == "halo") {
				BuilderCircleEffect(builder);
			} else {
				builderSimpleEffect(builder);
			}
		}
	}

	public void removePlayerEffect() {
		Bukkit.getServer().getScheduler().cancelTask(id2);
		Bukkit.getServer().getScheduler().cancelTask(id3);
	}

	public void removeBuilderEffect() {
		Bukkit.getServer().getScheduler().cancelTask(id);
		Bukkit.getServer().getScheduler().cancelTask(id4);
	}

	public void removeAllEffect() {
		Bukkit.getServer().getScheduler().cancelTask(id);
		Bukkit.getServer().getScheduler().cancelTask(id2);
		Bukkit.getServer().getScheduler().cancelTask(id3);
		Bukkit.getServer().getScheduler().cancelTask(id4);
	}

	public void PlayerCircleEffect(Player player) {
		if (player != null) {
			float radius = 0.3f;
			Location loc = player.getLocation().clone();
			for (int i = 0; i < 12; i++) {
				double inc = (2 * Math.PI) / 12;
				float angle = (float) (i * inc);
				float x = (float) (Math.cos(angle) * radius);
				float z = (float) (Math.sin(angle) * radius);
				loc.add(x, 2.5, z);
				displayColoredParticle(loc, "808080");
				loc.subtract(x, 2.5, z);
			}
		}
	}

	public void BuilderCircleEffect(Player builder) {
		if (builder != null) {
			float radius = 0.3f;
			Location loc = builder.getLocation().clone();
			for (int i = 0; i < 12; i++) {
				double inc = (2 * Math.PI) / 12;
				float angle = (float) (i * inc);
				float x = (float) (Math.cos(angle) * radius);
				float z = (float) (Math.sin(angle) * radius);
				loc.add(x, 2.5, z);
				displayColoredParticle(loc, "FFD700");
				loc.subtract(x, 2.5, z);
			}
		}
	}

	public void builderSimpleEffect(Player builder) {
		if (builder != null) {
			Location loc = builder.getLocation().add(0, 2.5, 0);
			displayColoredParticle(loc, "FFD700");
		}
	}

	public void playerSimpleEffect(Player player) {
		if (player != null) {
			Location loc = player.getLocation().add(0, 2.5, 0);
			displayColoredParticle(loc, "808080");
		}
	}

	//

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
