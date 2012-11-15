package entities.ships.enemies;

import java.awt.image.BufferedImage;

import org.jbox2d.common.Vec2;

public class EnemyDef {
	private final BufferedImage image;
	private final EnemyBehavior behavior;
	private final int x, y, life;
	private final int time;
	private final Vec2 posAppear;
	private final boolean isBoss;

	public EnemyDef(BufferedImage image, EnemyBehavior behavior, int x, int y, int life, int time, Vec2 posAppear, boolean isBoss){
		this.image = image;
		this.behavior = behavior;
		this.x = x;
		this.y = y;
		this.life = life;
		this.time = time;
		this.posAppear = posAppear;
		this.isBoss = isBoss;
	}

	public BufferedImage getImage() {
		return image;
	}

	public EnemyBehavior getBehavior() {
		return behavior;
	}

	public int getX() {
		return x;
	}

	public int getY() {
		return y;
	}

	public int getLife() {
		return life;
	}

	public int getTime() {
		return time;
	}

	public Vec2 getPosAppear() {
		return posAppear;
	}

	public boolean isBoss(){
		return isBoss;
	}
}
