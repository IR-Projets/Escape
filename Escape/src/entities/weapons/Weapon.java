package entities.weapons;

import java.awt.image.BufferedImage;

import entities.Entity;

public abstract class Weapon extends Entity{


	public void init(int x, int y){
		super.init(x,y);
		body.setBullet(true);
	}
	
	public abstract BufferedImage getIcon();
	
}
