package entities.ships;


import java.awt.image.BufferedImage;

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

	public void setLife(int life){
		this.life = life;
	}

	public int getLife(){
		return life;
	}

	public void loadWeapon(WeaponType weaponType, boolean firedByPlayer){
		double angleRadian = getBody().getAngle();
		int normX = getImage().getWidth()/2;
		int normY = getImage().getHeight()/2;
		float posX, posY;

		if(weaponType == WeaponType.Shiboleet || weaponType == WeaponType.ShiboleetExtended){
			posX = (float) (getPositionNormalized().x+normX);
			posY = (float) (getPositionNormalized().y-normY);
		}
		else{
			posX = (float) (getPositionNormalized().x+normX+Math.cos(angleRadian+Math.PI/2)*normX);
			posY = (float) (getPositionNormalized().y-normY+Math.sin(angleRadian+Math.PI/2)*normY);
		}
		weapon = weaponFactory.createWeapon(weaponType,(int) posX,(int) posY, firedByPlayer);
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
		getEntities().removeEntitie(this);
	}

	public void move(double angle, int velocity){
		int vitX = (int) (Math.cos(Math.toRadians(angle))*velocity);
		int vitY = (int) (Math.sin(Math.toRadians(angle))*velocity);
		setVelocity(vitX, vitY);
	}


}
