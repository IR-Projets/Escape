package entities.ships.enemies;

import java.awt.image.BufferedImage;
import java.util.Random;

import org.jbox2d.common.Vec2;

import entities.Entities;
import entities.Entity;
import entities.ships.Ship;
import game.Variables;


public class Enemy extends Ship{

	EnemyBehavior behavior;
	Random rand = new Random();


	public Enemy(Entities entities, BufferedImage image, int x, int y, int life, EnemyBehavior behavior){	
		super(entities, EntityShape.Square, image, x, y, life);
		this.behavior=behavior;
		getBody().getFixtureList().getFilterData().groupIndex = -1;
	}

	@Override
	public EntityType getType() {
		return EntityType.Enemy;
	}

	@Override
	public void collision(Entity entity, EntityType type) {
		switch (type) {
		case WeaponEnnemy:
		case WeaponPlayer:
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

	@Override
	public void compute() {
		behavior.control(this);
	}

}
