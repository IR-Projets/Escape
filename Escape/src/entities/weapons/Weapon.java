package entities.weapons;

import java.awt.image.BufferedImage;

import org.jbox2d.dynamics.World;

import entities.Entities;
import entities.Entity;

public abstract class Weapon extends Entity{

	//TODO : LE systeme d'armement, avec l'integration de la classe Item (item extends entity??)
	public Weapon(Entities entities, BufferedImage image, int x, int y) {
		super(entities, x, y, image.getWidth(), image.getHeight());
		
		//getBody().setActive(true); ???????
	}

	@Override
	public BufferedImage getImage() {
		return getImage();
	}
	
}
