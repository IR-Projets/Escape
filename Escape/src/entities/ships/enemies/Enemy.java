package entities.ships.enemies;

import java.awt.image.BufferedImage;
import java.util.Random;

import listeners.CollisionListener.EntityType;

import org.jbox2d.common.Vec2;

import entities.Bonus;
import entities.Entities;
import entities.Entity;
import entities.ships.Ship;
import entities.weapons.WeaponItem;
import factories.WeaponFactory.WeaponType;
import game.Variables;


public class Enemy extends Ship{

	private final EnemyBehavior behavior;
	private final Entities entities;
	private Random rand = new Random();
	private WeaponItem bonusToDrop;

	public Enemy(Entities entities, BufferedImage image, int x, int y, int life, EnemyBehavior behavior){	
		super(entities, EntityShape.Square, image, x, y, life);
		this.behavior=behavior;
		this.entities=entities;
		getBody().setTransform(new Vec2(x,y), (float) Math.toRadians(180));
		getBody().setFixedRotation(true);
		addtoCollisionGroup(EntityType.Enemy);
	}

	@Override
	public EntityType getEntityType() {
		return EntityType.Enemy;
	}

	private void dropItem(int proba){
		if(rand.nextInt() % proba == 0){
			int quantity = rand.nextInt(4)+1;
			switch(rand.nextInt()%4){
			case 0:
				bonusToDrop = new WeaponItem(WeaponType.Fireball, quantity);
				break;
			case 1:
				bonusToDrop = new WeaponItem(WeaponType.Missile, quantity);
				break;
			case 2:
				bonusToDrop = new WeaponItem(WeaponType.ShiboleetExtended, quantity);
				break;
			case 3:
				bonusToDrop = new WeaponItem(WeaponType.Shuriken, quantity);
				break;
			}
		}
		
	}
	
	@Override
	public void explode(){
		super.explode();
		dropItem(2);
	}
	
	@Override
	public void collision(Entity entity, EntityType type) {
		switch (type) {
		case WeaponEnnemy:
		case WeaponPlayer:
		case Joueur:
			setLife(getLife()-entity.getDamage());
			if(getLife() <= 0)
				explode();
			break;	
		case WorldLimit:
			Vec2 pos = getScreenPostion();
			if(pos.y+getImage().getHeight()/2>=Variables.SCREEN_HEIGHT)
				explode();
			break;
		default:
			break;
		}
	}

	@Override
	public void compute() {
		behavior.control(this);
		Vec2 pos = getPositionNormalized();
		if(bonusToDrop!=null){
			Bonus bonusTmp = new Bonus(entities, bonusToDrop, (int)pos.x, (int)pos.y);
			entities.addEntity(bonusTmp);
			bonusTmp.setVelocity(0, -Variables.SHIP_BULLET_VELOCITY);
			bonusToDrop=null;
		}
	}

	@Override
	public int getDamage() {
		return 5;
	}

}
