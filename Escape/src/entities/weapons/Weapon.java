package entities.weapons;

import java.awt.image.BufferedImage;

import entities.Entity;

public abstract class Weapon extends Entity{


	public Weapon(int x, int y){
		body.setBullet(true);
	}
	
	public abstract BufferedImage getIcon();
	
}
