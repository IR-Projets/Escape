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


public class EnnemyBehavior {

	private final static int LOOP_SKIP = 64;

	private final Entities entities;

	private List<Behavior> listBehavior;
	private HeadScript head;


	private final List<Enemy> enemys;//For move the entity and launch the missile

	private int loop, step;
	//private Boss boss;

	public EnnemyBehavior(Entities entities, String filename){
		this.entities=entities;
		enemys = new LinkedList<>();

		BufferedReader bufIn = null;
		bufIn = new BufferedReader (new InputStreamReader(Ressources.getFile(filename)));
		try {
			head = LoaderBehavior.initHead(bufIn);
			listBehavior = LoaderBehavior.initBody(bufIn);
			bufIn.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
		loop=step=0;
	}

	//TODO: A virer
	//boolean bossDejaAffiche = false;

	
	public void compute(){
		loop++;

		if(loop>LOOP_SKIP){
			step++;
			loop=0;
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
	
	/*public static void main(String[] args) {
		loadEnnemyFromScript("../script.sir.txt");
		System.out.println("Apres chargement : ");

		System.out.println("HEAD");
		System.out.println("life "+life);
		System.out.println("sprite "+filename);
		System.out.println("begin "+listAppear.get(0).pos + " et "+listAppear.get(0).time);
		System.out.println("begin "+listAppear.get(1).pos + " et "+listAppear.get(1).time);

		System.out.println("BODY");
		Behavior behav = listBehavior.get(0);
		System.out.println("step :"+behav.step);
		System.out.println("speed :"+behav.speed);
		System.out.println("move :"+behav.move);
		System.out.println("shoot :"+behav.weaponType);
		System.out.println("moveshoot :"+behav.moveWeapon);

		Behavior behav2 = listBehavior.get(1);
		System.out.println("step :"+behav2.step);
		System.out.println("speed :"+behav2.speed);
		System.out.println("move :"+behav2.move);
		System.out.println("shoot :"+behav2.weaponType);
		System.out.println("moveshoot :"+behav2.moveWeapon);

	}*/
}
