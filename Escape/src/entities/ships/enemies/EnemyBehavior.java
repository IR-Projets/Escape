package entities.ships.enemies;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import listeners.ShipListener;
import entities.Entities;
import entities.ships.Ship;
import entities.ships.enemies.LoaderBehavior.Behavior;
import entities.ships.enemies.LoaderBehavior.HeadScript;
import entities.ships.enemies.LoaderBehavior.HeadScript.Couple;
import factories.ShipFactory;
import factories.WeaponFactory.WeaponType;
import game.Ressources;
import game.Variables;


public class EnemyBehavior {

	public enum Types{
		Fire,
		Move
	};

	public class Action {
		int beg;
		int end;
		int velocity;
		double angle;
		String name;
		Types type;
		
		
		public Action(){
			beg=-1;
			end=-1;
			velocity=-1;
			angle=-1;
			name=null;
			type=null;
		}
		
	}

	private final static int LOOP_SKIP = 64;
	private int loop, step;

	private final List<Action> behavior;
	private final int repeatTime;

	public EnemyBehavior(List<Action> behavior, int repeatTime){
		this.behavior = behavior;
		this.repeatTime=repeatTime;
	}

	public void control(Ship ship){
		loop++;

		if(loop>LOOP_SKIP){

			loop=0;
			step=(step+1)%repeatTime;

			for(Action action : behavior)
				if(step >= action.beg && step <= action.end){
					switch(action.type){
					case Fire:
						int vitX = (int) (Math.cos(Math.toRadians(action.angle))*action.velocity);
						int vitY = (int) (Math.sin(Math.toRadians(action.angle))*action.velocity);
						ship.shoot(vitX, vitY);
						break;

					case Move:
						ship.move(action.angle, action.velocity);
						break;

					}
				}
		}

	}


	//TODO: A virer
	//boolean bossDejaAffiche = false;


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
