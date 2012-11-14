package entities.weapons;

import java.awt.Graphics2D;

import entities.Entities;
import factories.WeaponFactory.WeaponType;

public class Shuriken extends Weapon{

	private boolean resize;
	
	public Shuriken(Entities entities, int x, int y, boolean firedByPlayer) {
		super(entities, EntityShape.Circle, WeaponType.Shuriken.getImage(), x, y, 10, firedByPlayer);
		resize = false;
	}
	
	@Override
	public void compute() {
		if(resize == false){
			setImage(resize(getImage(), 2f));
			resize = true;
		}
	}
	
	
	@Override
	public void render(Graphics2D graphics) {
		super.render(graphics);
		getBody().setAngularVelocity( 3f);
	}
	
}
