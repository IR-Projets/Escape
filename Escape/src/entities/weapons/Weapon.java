package entities.weapons;

import java.awt.image.BufferedImage;

import org.jbox2d.dynamics.World;

import entities.Entitie;

public abstract class Weapon extends Entitie{

	//TODO : LE systeme d'armement, avec l'integration de la classe Item (item extends entity??)
	public Weapon(World world, String nameImage, float x, float y) {
		super(world, nameImage, x, y);
		getBody().setActive(true);
	}

	@Override
	public BufferedImage getImageRender() {
		return getImage();
	}
	
}
