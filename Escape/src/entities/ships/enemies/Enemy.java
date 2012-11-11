package entities.ships.enemies;

import java.awt.image.BufferedImage;
import java.util.Random;

import org.jbox2d.common.Vec2;
import org.jbox2d.dynamics.World;

import effects.Effects;
import effects.Explosion;
import entities.Entities;
import entities.Entity;
import entities.ships.Ship;
import game.Ressources;
import game.Variables;


public class Enemy extends Ship{

	double lastExecution=0;
	Random rand = new Random();

	public Enemy(Entities entities, BufferedImage image, int x, int y, int life){	
		super(entities, EntityShape.Square, image, x, y, life);
		
		getBody().getFixtureList().getFilterData().groupIndex = -1;
	}

	@Override
	public void compute() {
		/*double now = System.currentTimeMillis();
		if(now-lastExecution>1000+rand.nextInt(5000)){
			lastExecution=now;
			setVelocity(rand.nextInt(100)-50, rand.nextInt(100)-50);
		}*/
	}

	@Override
	public EntityType getType() {
		return EntityType.Enemy;
	}
	
	@Override
	public void collision(Entity entity, EntityType type) {
		switch (type) {
		case WeaponEnnemy:
		case WeaponAllied:
		case Joueur:
			explode();
			break;	
		case WorldLimit:
			Vec2 pos = getScreenPostion();
			if(pos.y+getImage().getHeight()>=Variables.SCREEN_HEIGHT)
				explode();
			break;
		default:
			break;
		}
		
	}

}
