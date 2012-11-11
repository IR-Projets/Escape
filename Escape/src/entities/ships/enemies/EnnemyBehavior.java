package entities.ships.enemies;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;

import entities.Entities;
import entities.Entity;
import entities.ships.Ship;
import entities.ships.boss.Boss;
import entities.ships.enemies.LoaderBehavior.Behavior;
import entities.ships.enemies.LoaderBehavior.HeadScript;
import entities.ships.enemies.LoaderBehavior.HeadScript.Couple;
import game.Ressources;
import game.Variables;


public class EnnemyBehavior {

	private final Entities entities;
	private long timeBegin;
	private HeadScript head;
	private List<Behavior> listBehavior;
	private int stepMax, stepActual, indexBehavior;
	private final List<Ship> enemys;//For move the entity and launch the missile
	private Boss boss;

	public EnnemyBehavior(Entities entities, String filename){
		this.entities=entities;
		timeBegin = System.nanoTime()/1000000;
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

		stepActual = stepMax=0;
		for(Behavior behav : listBehavior)
			stepMax+=behav.getStep();
		indexBehavior=0;
	}

	//TODO: A virer
	boolean bossDejaAffiche = false;
	
	public void compute(){
		long timeActual = System.nanoTime()/1000000;
		long difTime = timeActual-timeBegin;

		Iterator <Couple> it = head.getListAppear().iterator();
		while(it.hasNext()){
			Couple couple = it.next();
			if(couple.getTime()*1000 < difTime){
				enemys.add(EnnemyFactory.createEnnemy(entities, head.getFilename(), couple.getPos(), Variables.SCREEN_HEIGHT+Variables.SCREEN_HEIGHT/20, head.getLife()));
				it.remove();
			}
			//TODO: Ajouter un boss!!!
			else if(10000<difTime && !bossDejaAffiche){
				bossDejaAffiche = true;
				enemys.add(EnnemyFactory.createBoss(entities, "images/ships/boss.png", Variables.SCREEN_HEIGHT/2, Variables.SCREEN_HEIGHT+Variables.SCREEN_HEIGHT/20, 10));
			}

		}

		Behavior behavActual = listBehavior.get(indexBehavior); //Hoooooo le O(n²)
		if( (difTime / 1000) < (behavActual.getStep() + stepActual)){
			for(Entity enemy : enemys){
				switch(behavActual.getMove()){
				case B : 
					enemy.setVelocity(0,0);
					enemy.setVelocity(0, behavActual.getSpeed());
					break;
				case L :
					enemy.setVelocity(0,0);
					enemy.setVelocity(-behavActual.getSpeed(), 0);
					break;
				case LB :
					enemy.setVelocity(0,0);
					enemy.setVelocity(-behavActual.getSpeed(), -behavActual.getSpeed());
					break;
				case R :
					enemy.setVelocity(0,0);
					enemy.setVelocity(behavActual.getSpeed(),0);
					break;
				case RB : 
					enemy.setVelocity(0,0);
					enemy.setVelocity(behavActual.getSpeed(), -behavActual.getSpeed());
					break;
				}
				//System.out.println("movement"+behavActual.getMove()+" et "+((difTime / 1000)%stepMax) + "et le step"+(behavActual.getStep() + stepActual));
			}
		}
		else {
			stepActual += behavActual.getStep() % stepMax;
			indexBehavior = (indexBehavior+1)%listBehavior.size();
		}

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
