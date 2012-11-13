package entities.weapons;

import java.util.Random;

import entities.Entities;
import factories.WeaponFactory;
import factories.WeaponFactory.WeaponType;

public class ShiboleetExtension extends Shiboleet{

	private final Weapon [] child;
	private final WeaponFactory weaponFactory;

	public ShiboleetExtension(Entities entities, int x, int y, boolean firedByPlayer, int quantity) {
		super(entities, x, y, firedByPlayer);
		weaponFactory = new WeaponFactory(entities);
		
		child = new Shiboleet[quantity];
		for(int i=0; i<quantity;i++)
			child[i] = weaponFactory.createWeapon(WeaponType.Shiboleet, x, y, firedByPlayer);
	}
	
	
	public void compute(){
		for(Weapon shib : child)
			shib.compute();
	}
	
	@Override
	public void shoot(double angle, int velocity) {
		
		int diffAngle = 10;
		double newAngle = angle-(diffAngle * child.length/2);
		for(int i=0;i<child.length;i++){
			child[i].shoot(newAngle+i*diffAngle, velocity);
		}
	}

}
