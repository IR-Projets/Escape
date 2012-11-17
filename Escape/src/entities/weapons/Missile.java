package entities.weapons;

import org.jbox2d.common.Vec2;

import entities.Entities;
import factories.WeaponFactory.WeaponType;

public class Missile extends Weapon {

	private boolean resize;
	
	public Missile(Entities entities, int x, int y, boolean firedByPlayer) {
		super(entities, EntityShape.Circle, WeaponType.Missile.getImage(), x, y, 10, firedByPlayer);
		resize = false;
		if(!firedByPlayer)
			getBody().setTransform(new Vec2(x,y), (float) Math.toRadians(180));
	}

	@Override
	public void compute() {
		if(resize == false){
			setImage(resize(getImage(), 2f));
			resize = true;
		}
	}
	
}
