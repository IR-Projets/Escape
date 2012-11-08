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


public class Enemy extends Ship{

	double lastExecution=0;
	Random rand = new Random();

	public Enemy(Entities entities, BufferedImage image, int x, int y, int life){	
		super(entities, EntityShape.Square, image, x, y, life);
		
		getBody().getFixtureList().getFilterData().groupIndex = -1;
	}

	/*private static BufferedImage getRandomNameImage(){
		Random rand = new Random();
		if(rand.nextInt()%2==0)
			return Ressources.getImage("images/Ships/ship.png");
		else
			return Ressources.getImage("images/Ships/dirtyDick.png");
	}*/

	@Override
	public void compute() {
		double now = System.currentTimeMillis();
		if(now-lastExecution>1000+rand.nextInt(5000)){
			lastExecution=now;
			setVelocity(rand.nextInt(100)-50, rand.nextInt(100)-50);
		}
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
			Vec2 pos = getScreenPostion();
			Effects.addEffect(new Explosion((int)pos.x, (int)pos.y));
			getEntities().removeEntitie(this);
			break;	

		default:
			break;
		}
		
	}

}
