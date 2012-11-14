package entities.ships.enemies;

import java.util.List;

import entities.ships.Ship;
import factories.WeaponFactory.WeaponType;
import game.Variables;


public class EnemyBehavior {

	private int loop, step;

	private final List<Action> actions;
	private final int repeatTime;

	public EnemyBehavior(List<Action> behavior, int repeatTime){
		this.actions = behavior;
		this.repeatTime=repeatTime;
		step=loop=0;
	}

	public void control(Ship ship){
		loop++;

		if(loop>Variables.LOOP_SKIP){

			loop=0;
			step=(step+1)%repeatTime;

			for(Action action : actions){
				if(step >= action.beg && step <= action.end){
					switch(action.type){
					case Fire:
						ship.loadWeapon(WeaponType.convert(action.name), false);
						ship.shootWeapon(action.angle, action.velocity);
						break;

					case Move:
						ship.move(action.angle, action.velocity);
						System.out.println("Set move : "+action.angle);
						break;
					}
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
