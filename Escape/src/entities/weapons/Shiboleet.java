package entities.weapons;

import java.awt.Graphics2D;
import java.awt.image.BufferedImage;

import entities.Entities;
import factories.WeaponFactory;
import factories.WeaponFactory.WeaponType;

public class Shiboleet extends Weapon {

	private long time;
	private int increase;
	
	public Shiboleet(Entities entities, int x, int y, boolean firedByPlayer) {
		super(entities, EntityShape.Circle, WeaponType.Shiboleet.getImage(), x, y, 8, firedByPlayer);
		time = System.nanoTime()/1000000;
		increase=0;
	}

	@Override
	public void compute() {
		long diffTime = (System.nanoTime()/1000000 - time);
		if(isLaunch())
			return;
		if(diffTime > 300 && increase <2){
			BufferedImage image = Weapon.resize(getImage(), 1.5f);
			setImage(image);	
			increase++;
			int damage = getDamage()*2;
			setDamage(damage);			
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
