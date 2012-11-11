package entities.ships.enemies;

import java.awt.image.BufferedImage;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Random;

import org.jbox2d.common.Vec2;
import org.jbox2d.dynamics.World;

import effects.Effects;
import effects.Explosion;
import entities.Entities;
import entities.Entity;
import entities.ships.Ship;
import entities.ships.ShipListener;
import entities.ships.enemies.LoaderBehavior.Behavior;
import entities.ships.enemies.LoaderBehavior.HeadScript.Couple;
import entities.weapons.WeaponFactory;
import entities.weapons.WeaponFactory.WeaponType;
import game.Ressources;
import game.Variables;


public class Enemy extends Ship{

	double lastExecution=0;
	Random rand = new Random();

	private final static int LOOP_SKIP = 64;

	private int indexBehavior, loop, step, sumStep;
	private List<Behavior> listBehavior;
	private final WeaponFactory factoryWeapon;

	public Enemy(Entities entities, BufferedImage image, int x, int y, int life, List<Behavior> listBehavior){	
		super(entities, EntityShape.Square, image, x, y, life);

		getBody().getFixtureList().getFilterData().groupIndex = -1;
		this.listBehavior = new LinkedList<>(listBehavior);
		indexBehavior=loop=step=sumStep=0;
		factoryWeapon = new WeaponFactory(entities);
	}


	public void launchMove(Behavior behavActual){
		switch(behavActual.getMove()){
		case B : 
			setVelocity(0, -behavActual.getSpeed());
			break;
		case L :
			setVelocity(-behavActual.getSpeed(), 0);
			break;
		case LB :
			setVelocity(-behavActual.getSpeed(), -behavActual.getSpeed());
			break;
		case R :
			setVelocity(behavActual.getSpeed(),0);
			break;
		case RB : 
			setVelocity(behavActual.getSpeed(), -behavActual.getSpeed());
			break;
		}
		//System.out.println("movement"+behavActual.getMove()+" et "+((difTime / 1000)%stepMax) + "et le step"+(behavActual.getStep() + stepActual));
	}

	public void launchWeapon(Behavior behavActual){
		WeaponType weaponType = behavActual.getWeaponType();
		int x = (int) getPositionNormalized().x+getImage().getWidth()/2;
		int y = (int) getPositionNormalized().y-getImage().getHeight();
		int speedWeapon = Variables.SPEED_WEAPON;
		addWeapons(factoryWeapon.createWeapon(weaponType, x, y, true));

		switch(behavActual.getMoveWeapon()){
		case B : 
			shoot(0, -speedWeapon);
			break;
		case L :
			shoot(-speedWeapon, 0);
			break;
		case LB :
			shoot(-speedWeapon, -speedWeapon);
			break;
		case R :
			shoot(speedWeapon, 0);
			break;
		case RB : 
			shoot(speedWeapon, -speedWeapon);
			break;
		}
		//System.out.println("movement"+behavActual.getMove()+" et "+((difTime / 1000)%stepMax) + "et le step"+(behavActual.getStep() + stepActual));
	}

	//Ne pas mettre dans compute, sinon concurrentmodification exception -> comme on lance le compute depuis un parcour d'entities et qu'on veut ajouter une nouvelle arme, erreur!!
	public void launchActions() {
		loop++;

		if(loop == 1 && step == 0){
			Behavior behavActual = listBehavior.get(indexBehavior);
			launchMove(behavActual);
			launchWeapon(behavActual);
		}
		if(loop>LOOP_SKIP){
			step++;
			loop=0;
			Behavior behavActual = listBehavior.get(indexBehavior);

			System.out.println("Setp"+step+" et "+behavActual.getStep());
			if(step > behavActual.getStep()+sumStep){
				sumStep+=behavActual.getStep();
				indexBehavior = (indexBehavior+1)%listBehavior.size();
				Behavior newBehavior = listBehavior.get(indexBehavior);
				launchMove(newBehavior);
				launchWeapon(newBehavior);
			}
			return;
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
		// TODO Auto-generated method stub
		
	}

}
