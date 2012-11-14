package entities.ships.enemies;

import java.awt.image.BufferedImage;
import java.util.Random;

import org.jbox2d.common.Vec2;

import entities.Entities;
import entities.Entity;
import entities.ships.Ship;
import factories.WeaponFactory;
import game.Variables;


public class Enemy extends Ship{

	double lastExecution=0;
	Random rand = new Random();


	public Enemy(Entities entities, BufferedImage image, int x, int y, int life){	
		super(entities, EntityShape.Square, image, x, y, life);

		getBody().getFixtureList().getFilterData().groupIndex = -1;
	}




	//Ne pas mettre dans compute, sinon concurrentmodification exception -> comme on lance le compute depuis un parcour d'entities et qu'on veut ajouter une nouvelle arme, erreur!!
	public void launchActions() {
		//loop++;

		//behavior.control(this);
		
		/*if(loop == 1 && step == 0){
			Behavior behavActual = listBehavior.get(indexBehavior);
			launchMove(behavActual);
			launchWeapon(behavActual);
		}
		if(loop>LOOP_SKIP){
			step++;
			loop=0;
			Behavior behavActual = listBehavior.get(indexBehavior);

			if(step > behavActual.getStep()+sumStep){
				sumStep+=behavActual.getStep();
				indexBehavior = (indexBehavior+1)%listBehavior.size();
				Behavior newBehavior = listBehavior.get(indexBehavior);
				launchMove(newBehavior);
				launchWeapon(newBehavior);
			}
			return;
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
		// TODO Auto-generated method stub
		
	}

}
