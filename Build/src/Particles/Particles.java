package Particles;

import org.bukkit.Bukkit;
import org.bukkit.Effect;
import org.bukkit.Location;
import org.bukkit.entity.Player;
import org.bukkit.util.Vector;

import com.likeapig.build.arena.MegaData;
import com.likeapig.build.arena.ParticleEffect;
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
	Location origin;
	Location location;
	boolean x;
	boolean o;
	boolean[][] shape;
	boolean up;
	float height;

	public Particles() {
	}

	public void addPlayerEffect(Player p) {
		if (p != null) {
			String name = p.getName();
			new StoreItems(p);
			if (MegaData.get().getPurchased(name).contains("halo") && StoreItems.getActivatedString(p) == "halo") {
				PlayerCircleEffect(p);
			} else if (MegaData.get().getPurchased(name).contains("santaHat")
					&& StoreItems.getActivatedString(p) == "santaHat") {
				sHat(p);
			} else if (MegaData.get().getPurchased(name).contains("wings")
					&& StoreItems.getActivatedString(p) == "wings") {
				wings(p, false);
			} else {
				playerSimpleEffect(p);
			}
		}
	}

	public void addBuilderEffect(Player p) {
		if (p != null) {
			String name = p.getName();
			new StoreItems(p);
			if (MegaData.get().getPurchased(name).contains("halo") && StoreItems.getActivatedString(p) == "halo") {
				BuilderCircleEffect(p);
			} else if (MegaData.get().getPurchased(name).contains("santaHat")
					&& StoreItems.getActivatedString(p) == "santaHat") {
				sHat(p);
			} else if (MegaData.get().getPurchased(name).contains("wings")
					&& StoreItems.getActivatedString(p) == "wings") {
				wings(p, true);
			} else {
				builderSimpleEffect(p);
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

	public void sHat(Player player) {
		Location lochat = player.getEyeLocation().add(0.0, 0.25, 0.0);
		for (int i = 0; i < 12; ++i) {
			particle(lochat.clone().add(0.35 * Math.cos(Math.toRadians(30.0 * i)), 0.0,
					0.35 * Math.sin(Math.toRadians(30.0 * i))), false);
			particle(lochat.clone().add(0.25 * Math.cos(Math.toRadians(30.0 * i)), 0.1,
					0.25 * Math.sin(Math.toRadians(30.0 * i))), true);
			particle(lochat.clone().add(0.16 * Math.cos(Math.toRadians(30.0 * i)), 0.2,
					0.16 * Math.sin(Math.toRadians(30.0 * i))), true);
			particle(lochat.clone().add(0.07 * Math.cos(Math.toRadians(30.0 * i)), 0.3,
					0.07 * Math.sin(Math.toRadians(30.0 * i))), true);
			particle(lochat.clone().add(0.07 * Math.cos(Math.toRadians(30.0 * i)), 0.4,
					0.07 * Math.sin(Math.toRadians(30.0 * i))), true);
		}
		for (int i = 0; i < 5; ++i) {
			particle(lochat.clone().add((Math.random() - 0.5) / 10.0, 0.46, (Math.random() - 0.5) / 10.0), false);
		}
	}

	public void particle(final Location loc, final boolean red) {
		loc.getWorld().spigot().playEffect(loc, Effect.COLOURED_DUST, 0, 0, 1.0f, red ? 0.0f : 1.0f, red ? 0.0f : 1.0f,
				1.0f, 0, 128);
	}

	public void wings(final Player player, boolean gold) {
		origin = player.getLocation();
		location = origin.clone();
		x = true;
		o = false;
		shape = new boolean[][] {
				{ this.o, this.o, this.o, this.o, this.o, this.o, this.o, this.o, this.o, this.o, this.o, this.o,
						this.o, this.o, this.o, this.o, this.o, this.o },
				{ this.o, this.x, this.x, this.x, this.x, this.o, this.o, this.o, this.o, this.o, this.o, this.o,
						this.x, this.x, this.x, this.x, this.o, this.o },
				{ this.o, this.o, this.x, this.x, this.x, this.x, this.x, this.o, this.o, this.o, this.x, this.x,
						this.x, this.x, this.x, this.o, this.o, this.o },
				{ this.o, this.o, this.o, this.x, this.x, this.x, this.x, this.x, this.x, this.x, this.x, this.x,
						this.x, this.x, this.o, this.o, this.o, this.o },
				{ this.o, this.o, this.o, this.o, this.x, this.x, this.x, this.x, this.x, this.x, this.x, this.x,
						this.x, this.o, this.o, this.o, this.o, this.o },
				{ this.o, this.o, this.o, this.o, this.x, this.x, this.x, this.x, this.o, this.x, this.x, this.x,
						this.x, this.o, this.o, this.o, this.o, this.o },
				{ this.o, this.o, this.o, this.o, this.o, this.x, this.x, this.x, this.o, this.x, this.x, this.x,
						this.o, this.o, this.o, this.o, this.o, this.o },
				{ this.o, this.o, this.o, this.o, this.o, this.x, this.x, this.o, this.o, this.o, this.x, this.x,
						this.o, this.o, this.o, this.o, this.o, this.o },
				{ this.o, this.o, this.o, this.o, this.x, this.x, this.o, this.o, this.o, this.o, this.o, this.x,
						this.x, this.o, this.o, this.o, this.o, this.o } };
		final double space = 0.2;
		final double defX;
		double x = defX = this.location.getX() - 0.2 * this.shape[0].length / 2.0 + 0.2;
		double y = this.location.clone().getY() + 2.0;
		double angle = -((this.location.getYaw() + 180.0f) / 60.0f);
		angle += ((this.location.getYaw() < -180.0f) ? 3.25 : 2.985);
		boolean[][] shape;
		for (int length = (shape = this.shape).length, i = 0; i < length; ++i) {
			final boolean[] array = shape[i];
			boolean[] array2;
			for (int length2 = (array2 = array).length, j = 0; j < length2; ++j) {
				final boolean anAShape = array2[j];
				if (anAShape) {
					final Location target = this.location.clone();
					target.setX(x);
					target.setY(y);
					Vector v = target.toVector().subtract(this.location.toVector());
					final Vector v2 = getBackVector(this.location);
					v = rotateAroundAxisY(v, angle);
					v2.setY(0).multiply(-0.2);
					this.location.add(v);
					this.location.add(v2);
					for (int k = 0; k < 3; ++k) {
						if (!gold) {
							display(255, 255, 255, this.location);
						} else {
							display(226, 201, 36, this.location);
						}
					}
					this.location.subtract(v2);
					this.location.subtract(v);
				}
				x += 0.2;
			}
			y -= 0.2;
			x = defX;
		}
		if (this.up) {
			if (this.height < 2.0f) {
				this.height += 0.05;
			} else {
				this.up = false;
			}
		} else if (this.height > 0.0f) {
			this.height -= 0.05;
		} else {
			this.up = true;
		}
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
				// displayColoredParticle(loc, "808080");
				display(255, 255, 255, loc);
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
				// displayColoredParticle(loc, "FFD700");
				display(226, 201, 36, loc);
				loc.subtract(x, 2.5, z);
			}
		}
	}

	public void builderSimpleEffect(Player builder) {
		if (builder != null) {
			Location loc = builder.getLocation().add(0, 2.5, 0);
			// displayColoredParticle(loc, "FFD700");
			display(226, 201, 36, loc);
		}
	}

	public void playerSimpleEffect(Player player) {
		if (player != null) {
			Location loc = player.getLocation().add(0, 2.5, 0);
			// displayColoredParticle(loc, "808080");
			display(255, 255, 255, loc);
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

	public static double cos(final double angle) {
		return Math.cos(angle);
	}

	public static final double sin(final double angle) {
		return Math.sin(angle);
	}

	public static final double tan(final double angle) {
		return Math.tan(angle);
	}

	public static Vector rotateFunction(Vector v, final Location loc) {
		final double yawR = loc.getYaw() / 180.0 * 3.141592653589793;
		final double pitchR = loc.getPitch() / 180.0 * 3.141592653589793;
		v = rotateAboutX(v, pitchR);
		v = rotateAboutY(v, -yawR);
		return v;
	}

	public static Vector rotateAboutX(final Vector vect, final double a) {
		final double Y = cos(a) * vect.getY() - sin(a) * vect.getZ();
		final double Z = sin(a) * vect.getY() + cos(a) * vect.getZ();
		return vect.setY(Y).setZ(Z);
	}

	public static Vector rotateAboutY(final Vector vect, final double b) {
		final double X = cos(b) * vect.getX() + sin(b) * vect.getZ();
		final double Z = -sin(b) * vect.getX() + cos(b) * vect.getZ();
		return vect.setX(X).setZ(Z);
	}

	public static Vector rotateAboutZ(final Vector vect, final double c) {
		final double X = cos(c) * vect.getX() - sin(c) * vect.getY();
		final double Y = sin(c) * vect.getX() + cos(c) * vect.getY();
		return vect.setX(X).setY(Y);
	}

	public static Vector rotateAroundAxisY(final Vector v, final double angle) {
		final double cos = Math.cos(angle);
		final double sin = Math.sin(angle);
		final double x = v.getX() * cos + v.getZ() * sin;
		final double z = v.getX() * -sin + v.getZ() * cos;
		return v.setX(x).setZ(z);
	}

	public static Vector getBackVector(final Location loc) {
		final float newZ = (float) (loc.getZ() + 1.0 * Math.sin(Math.toRadians(loc.getYaw() + 90.0f)));
		final float newX = (float) (loc.getX() + 1.0 * Math.cos(Math.toRadians(loc.getYaw() + 90.0f)));
		return new Vector(newX - loc.getX(), 0.0, newZ - loc.getZ());
	}

	public static void display(final com.likeapig.build.utils.Particles effect, final Location location,
			final int amount, final float speed) {
		effect.display(0.0f, 0.0f, 0.0f, speed, amount, location, 128.0);
	}

	public static void display(final com.likeapig.build.utils.Particles effect, final Location location,
			final int amount) {
		effect.display(0.0f, 0.0f, 0.0f, 0.0f, amount, location, 128.0);
	}

	public static void display(final com.likeapig.build.utils.Particles effect, final Location location) {
		display(effect, location, 1);
	}

	public static void display(final com.likeapig.build.utils.Particles effect, final double x, final double y,
			final double z, final Location location, final int amount) {
		effect.display((float) x, (float) y, (float) z, 0.0f, amount, location, 128.0);
	}

	public static void display(final com.likeapig.build.utils.Particles effect, final int red, final int green,
			final int blue, final Location location, final int amount) {
		for (int i = 0; i < amount; ++i) {
			effect.display(new com.likeapig.build.utils.Particles.OrdinaryColor(red, green, blue), location, 128.0);
		}
	}

	public static void display(final int red, final int green, final int blue, final Location location) {
		display(com.likeapig.build.utils.Particles.REDSTONE, red, green, blue, location, 1);
	}

	public static void display(final com.likeapig.build.utils.Particles effect, final int red, final int green,
			final int blue, final Location location) {
		display(effect, red, green, blue, location, 1);
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
