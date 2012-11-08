package entities.ships;

import hud.LifeListener;

import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.List;

import org.jbox2d.dynamics.Body;
import org.jbox2d.dynamics.World;

import entities.Entities;
import entities.Entity;
import entities.weapons.Weapon;
import entities.weapons.WeaponFactory;



public abstract class Ship extends Entity{
	
	private int life;
	private final BufferedImage image;
	private final List<LifeListener> lifeListener = new ArrayList<LifeListener>();
	
	
	public Ship(Entities entities, EntityShape bodyForm, BufferedImage image, int posX, int posY, int life){
		super(entities, bodyForm.get(entities.getWorld(), posX, posY, image.getWidth(), image.getHeight()));
		this.image= image;
		this.life=life;
	}

	public BufferedImage getImage(){
		return image;
	}
	
	public void addListener(LifeListener listen){
		lifeListener.add(listen);
	}
	
	public void setLife(int life){
		for(LifeListener listen : lifeListener)
			listen.lifeChanged(this.life, life);
		this.life = life;
	}
	
	public int getLife(){
		return life;
	}
}
