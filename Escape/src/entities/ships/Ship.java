package entities.ships;

import hud.LifeListener;

import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;

import entities.Entities;
import entities.Entity;
import entities.weapons.Weapon;



public abstract class Ship extends Entity{
	
	private int life;
	private final BufferedImage image;
	private final List<LifeListener> lifeListener = new ArrayList<LifeListener>();
	private final List<Weapon> weapons;
	
	public Ship(Entities entities, EntityShape bodyForm, BufferedImage image, int posX, int posY, int life){
		super(entities, bodyForm.get(entities.getWorld(), posX, posY, image.getWidth(), image.getHeight()));
		this.image= image;
		this.life=life;
		weapons = new LinkedList<>();
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

	public List<Weapon> getWeapons() {
		return weapons;
	}
	
	public void shoot(int vitX, int vitY){
		Iterator<Weapon> it = weapons.iterator();
		while(it.hasNext()){
			Weapon weapon = it.next();
			weapon.shoot(vitX, vitY);
			it.remove();
		}
	}
}
