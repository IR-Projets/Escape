package entities.weapons;

import entities.Entities;
import entities.weapons.WeaponFactory.WeaponType;
import game.Ressources;

public class Missile extends Weapon {

	private boolean resize;
	
	public Missile(Entities entities, int x, int y, boolean firedByPlayer) {
		super(entities, EntityShape.Circle, Ressources.getImage("images/weapons/missile.png"), x, y, firedByPlayer,2, WeaponType.Missile);
		resize = false;
	}

	@Override
	public void compute() {
		if(resize == false){
			setImage(resize(getImage(), 2f));
			resize = true;
		}
	}
	
}
