package entities.ships;


import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.List;

import listeners.ShipListener;

import org.jbox2d.common.Vec2;

import effects.Effects;
import effects.Explosion;
import entities.Entities;
import entities.Entity;
import entities.weapons.Weapon;
import factories.WeaponFactory;
import factories.WeaponFactory.WeaponType;



public abstract class Ship extends Entity{
	
	private int life;
	private final BufferedImage image;
	private final List<ShipListener> lifeListener = new ArrayList<ShipListener>();
	private Weapon weapon;
	private final WeaponFactory weaponFactory;
	
	public Ship(Entities entities, EntityShape bodyForm, BufferedImage image, int posX, int posY, int life){
		super(entities, bodyForm.get(entities.getWorld(), posX, posY, image.getWidth(), image.getHeight()));
		this.image= image;
		this.life=life;
		weapon = null;
		weaponFactory = new WeaponFactory(entities);
	}

	public BufferedImage getImage(){
		return image;
	}
	
	public void addListener(ShipListener listen){
		lifeListener.add(listen);
	}
	
	public void setLife(int life){
		this.life = life;
	}
	
	public int getLife(){
		return life;
	}
	
	public void loadWeapon(WeaponType weaponType){
		int posY;
		if(weaponType == WeaponType.Shiboleet || weaponType == WeaponType.ShiboleetExtended)
			posY = (int)(getPositionNormalized().y-getImage().getHeight()/2);
		else
			posY = (int)(getPositionNormalized().y+getImage().getHeight()/2);
		weapon = weaponFactory.createWeapon(weaponType, (int)getPositionNormalized().x+getImage().getWidth()/2, posY, true);
		}
	
	public void shootWeapon(double angle, int velocity){
		if(weapon != null){
			weapon.shoot(angle, velocity);
			weapon = null;
		}
	}
	
	public void explode(){
		Vec2 pos = getScreenPostion();
		Effects.addEffect(new Explosion((int)pos.x, (int)pos.y));
		getEntities().removeEntitie(this, getType());
		for(ShipListener shipDestruct : lifeListener)
			shipDestruct.destroyed();
	}
	
	public void move(double angle, int velocity){
		int vitX = (int) (Math.cos(Math.toRadians(angle))*velocity);
		int vitY = (int) (Math.sin(Math.toRadians(angle))*velocity);
		setVelocity(vitX, vitY);
	}

	
}
