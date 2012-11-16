package entities.weapons;

import entities.Entities;
import factories.WeaponFactory;
import factories.WeaponFactory.WeaponType;

public class ShiboleetExtended extends Shiboleet{

	private final Weapon [] child;
	private final WeaponFactory weaponFactory;

	public ShiboleetExtended(Entities entities, int x, int y, boolean firedByPlayer, int quantity) {
		super(entities, x, y, firedByPlayer);
		weaponFactory = new WeaponFactory(entities);
		
		child = new Shiboleet[quantity];
		for(int i=0; i<quantity;i++)
			child[i] = weaponFactory.createWeapon(WeaponType.Shiboleet, x, y, firedByPlayer);
	}
	
	
	public void compute(){
		super.compute();
		for(Weapon shib : child)
			shib.compute();
		
	}
	
	@Override
	public void shoot(double angle, int velocity) {
		if(child.length%2 == 1)
			angle-=5;
		super.shoot(angle, velocity);
		int diffAngle = 10;
		double newAngle = angle-(diffAngle * (double)((child.length)/2));
		int offset=0;
		for(int i=0;i<child.length;i++){
			if(i== child.length/2)
				offset++;
			child[i].shoot(newAngle+(i+offset)*diffAngle, velocity);
		}
		
	}

}
