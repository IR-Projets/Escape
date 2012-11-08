package entities.weapons;

import entities.Entities;
import entities.weapons.WeaponFactory.WeaponType;
import game.Ressources;

public class Shiboleet extends Weapon {

	private final long time;
	private int increase;
	
	public Shiboleet(Entities entities, int x, int y, boolean firedByPlayer) {
		super(entities, EntityShape.Circle, Ressources.getImage("images/weapons/shiboleet.png"), x, y, firedByPlayer, 4, WeaponType.Shiboleet);
		time = System.nanoTime()/1000000;
		increase=0;
	}

	@Override
	public void compute() {
		long diffTime = (System.nanoTime()/1000000 - time)/10;
		System.out.println(diffTime);
		if(isLaunch())
			return;
		if(diffTime % 32 > 29  && increase <3){
			setImage(Weapon.resize(getImage(), 1.5f));
			increase++;
			setDamage(getDamage()*2);
		}
	}


}
