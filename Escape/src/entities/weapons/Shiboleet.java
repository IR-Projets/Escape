package entities.weapons;

import java.awt.Graphics2D;

import entities.Entities;
import entities.weapons.WeaponFactory.WeaponType;
import game.Ressources;

public class Shiboleet extends Weapon {

	private long time;
	private int increase;
	
	public Shiboleet(Entities entities, int x, int y, boolean firedByPlayer) {
		super(entities, EntityShape.Circle, Ressources.getImage("images/weapons/shiboleet.png"), x, y, firedByPlayer, 4, WeaponType.Shiboleet);
		time = System.nanoTime()/1000000;
		increase=0;
	}

	@Override
	public void compute() {
		long diffTime = (System.nanoTime()/1000000 - time);
		if(isLaunch())
			return;
		if(diffTime > 300 && increase <2){
			setImage(Weapon.resize(getImage(), 1.5f));
			increase++;
			setDamage(getDamage()*2);
			time=System.nanoTime()/1000000;
		}
	}
	
	@Override
	public void render(Graphics2D graphics) {
		super.render(graphics);
		if(isLaunch())
			getBody().setAngularVelocity( 3f);
	}
	


}
