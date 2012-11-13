package entities.ships.enemies;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;

import listeners.ShipListener;
import entities.Entities;
import entities.ships.enemies.LoaderBehavior.Behavior;
import entities.ships.enemies.LoaderBehavior.HeadScript;
import entities.ships.enemies.LoaderBehavior.HeadScript.Couple;
import factories.ShipFactory;
import game.Ressources;
import game.Variables;

public class BehaviorFactory {

	private final static int LOOP_SKIP = 64;

	private final Entities entities;

	private List<Behavior> listBehavior;
	private HeadScript head;


	private final EnemyBehavior behavior;//For move the entity and launch the missile

	private int loop, step;
	//private Boss boss;

	public static Behavior BehaviorFactory(Entities entities, String filename){
		return new EnemyBehavior(entities, filename);
		loop=step=0;
	}
	
	

	//TODO: A virer
	//boolean bossDejaAffiche = false;

	
	public void compute(){
		
		loop++;

		if(loop>LOOP_SKIP){
			
			loop=0;
			step++;
			
			Iterator <Couple> it = head.getListAppear().iterator();
			while(it.hasNext()){
				Couple couple = it.next();
				if(step > couple.getTime()){
					final Enemy enemy = ShipFactory.createEnnemy(entities, head.getFilename(), couple.getPos(), Variables.SCREEN_HEIGHT+Variables.SCREEN_HEIGHT/20, head.getLife(), listBehavior);
					enemys.add(enemy);
					enemy.addListener(new ShipListener(){

						@Override
						public void lifeChanged(int oldLife, int newLife) {
						}

						@Override
						public void destroyed() {
							enemys.remove(enemy);
						}
					});
					it.remove();
				}
			}
		}
		for(Enemy enemy : enemys)
			enemy.launchActions();
	}
