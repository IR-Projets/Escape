package entities.weapons;

import entities.Entities;
import factories.WeaponFactory.WeaponType;

public class Fireball extends Weapon {

	private long time;
	private int increase;
	
	public Fireball(Entities entities, int x, int y, boolean firedByPlayer) {
		super(entities, EntityShape.Circle, WeaponType.Fireball.getImage(), x, y, 3,  firedByPlayer);
		time = System.nanoTime()/1000000;
		increase=0;
	}

	
	@Override
	public void compute() {
		long diffTime = (System.nanoTime()/1000000 - time);
		if(isLaunch())
			return;
		if(diffTime > 300 && increase <3){
			setImage(Weapon.resize(getImage(), 1.5f));
			increase++;
			setDamage(getDamage()*2);
			time=System.nanoTime()/1000000;
		}
	}
	
	
}
