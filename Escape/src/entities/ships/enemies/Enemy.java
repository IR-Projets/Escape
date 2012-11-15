package entities.ships.enemies;

import java.awt.image.BufferedImage;
import java.util.Random;

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
	private WeaponType bonusToDrop;

	public Enemy(Entities entities, BufferedImage image, int x, int y, int life, EnemyBehavior behavior){	
		super(entities, EntityShape.Square, image, x, y, life);
		this.behavior=behavior;
		this.entities=entities;
		getBody().setTransform(new Vec2(x,y), (float) Math.toRadians(180));
		getBody().setFixedRotation(true);

		getBody().getFixtureList().getFilterData().categoryBits = 0x0002;
		getBody().getFixtureList().getFilterData().maskBits = 0x0001;
		//getBody().getFixtureList().getFilterData().groupIndex = -1;
	}

	@Override
	public EntityType getEntityType() {
		return EntityType.Enemy;
	}

	private void dropItem(int proba){
		if(rand.nextInt() % proba == 0){
			switch(rand.nextInt()%4){
			case 0:
				bonusToDrop = WeaponType.Fireball;		
				break;
			case 1:
				bonusToDrop = WeaponType.Missile;
				break;
			case 2:
				bonusToDrop = WeaponType.Shuriken;
				break;
			case 3:
				bonusToDrop = WeaponType.ShiboleetExtended;
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
		Vec2 pos = getPositionNormalized();
		if(bonusToDrop!=null){
			entities.addEntity(new Bonus(entities, bonusToDrop, (int)pos.x, (int)pos.y));
			System.out.println("Entity add"+bonusToDrop);
			bonusToDrop=null;
		}
	}

	@Override
	public int getDamage() {
		return 5;
	}

}
