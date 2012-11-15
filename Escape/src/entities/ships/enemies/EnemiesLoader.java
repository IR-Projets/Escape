package entities.ships.enemies;

import java.awt.image.BufferedImage;
import java.util.Iterator;
import java.util.List;

import org.jbox2d.common.Vec2;

import entities.Entities;
import factories.ShipFactory;
import game.Variables;

public class EnemiesLoader {

	private final Entities entities;
	
	private int loop, step;

	
	private final List<EnemyDef> enemysDef;
	private final ShipFactory shipFactory;

	public EnemiesLoader(Entities entities, String filename) {
		this.entities=entities;
		loop=step=0;
		enemysDef = new LoaderXml().getEnemysFromXml(entities, filename);
		shipFactory = new ShipFactory(entities);
	}

	public void compute(){
		loop++;
		if(loop>Variables.LOOP_SKIP){
			
			loop=0;
			step++;
			
			Iterator <EnemyDef> it = enemysDef.iterator();
			while(it.hasNext()){
				EnemyDef enemyLoad = it.next();
				if(step > enemyLoad.getTime()){
					if(enemyLoad.isBoss())
						shipFactory.createBoss(enemyLoad.getImage(), enemyLoad.getX(), enemyLoad.getY(), enemyLoad.getLife(), enemyLoad.getBehavior());
					else
						shipFactory.createEnnemy(enemyLoad.getImage(), enemyLoad.getX(), enemyLoad.getY(), enemyLoad.getLife(), enemyLoad.getBehavior());
					it.remove();
				}
			}
		}
	}
	
	
}
