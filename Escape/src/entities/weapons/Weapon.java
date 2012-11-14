package entities.weapons;

import java.awt.geom.AffineTransform;
import java.awt.image.AffineTransformOp;
import java.awt.image.BufferedImage;

import org.jbox2d.common.Vec2;

import effects.Effects;
import effects.Explosion;
import entities.Entities;
import entities.Entity;
import game.Ressources;

public abstract class Weapon extends Entity{
	


	private BufferedImage image;

	private int damage;

	private boolean isLaunch;
	private final boolean firedByPlayer;

	//TODO : LE systeme d'armement, avec l'integration de la classe Item (item extends entity??)
	public Weapon(Entities entities, EntityShape bodyForm, BufferedImage image, int x, int y, int damage, boolean firedByPlayer) {
		super(entities, bodyForm.get(entities.getWorld(), x, y, image.getWidth(), image.getHeight()));
		this.image = image;
		this.firedByPlayer=firedByPlayer;
		if(firedByPlayer)
			getBody().getFixtureList().getFilterData().groupIndex = -2;
		else
			getBody().getFixtureList().getFilterData().groupIndex = -1;
		this.damage=0;
		isLaunch=false;
		this.damage=damage;
	}

	@Override
	public BufferedImage getImage(){
		return image;
	}

	public void setImage(BufferedImage image) {
		this.image = image;
	}
	
	public boolean isLaunch() {
		return isLaunch;
	}
	
	public void setLaunch(boolean isLaunch) {
		this.isLaunch = isLaunch;
	}
	
	public int getDamage() {
		return damage;
	}

	public void setDamage(int damage) {
		this.damage = damage;
	}
	
	@Override
	public EntityType getType() {
		if(firedByPlayer)
			return EntityType.WeaponEnnemy;
		return EntityType.WeaponPlayer;
	}

	@Override
	public void collision(Entity entity, EntityType type) {
		Vec2 pos = getScreenPostion();
		Effects.addEffect(new Explosion((int)pos.x, (int)pos.y));
		getEntities().removeEntitie(this);
	}
	
	public static BufferedImage resize(BufferedImage image, float coefSize){
		BufferedImage imageRezise = new BufferedImage((int)(image.getWidth()*coefSize), (int)(image.getHeight()*coefSize), image.getType());
		AffineTransform at = new AffineTransform();
		at.scale(coefSize, coefSize);
		AffineTransformOp scaleOp = new AffineTransformOp(at, AffineTransformOp.TYPE_BILINEAR);
		return (scaleOp.filter(image, imageRezise));
	}

	public void shoot(double angle, int velocity) {
		int vitX = (int) (Math.cos(Math.toRadians(angle))*velocity);
		int vitY = (int) (Math.sin(Math.toRadians(angle))*velocity);
		setVelocity(vitX, vitY);
		setLaunch(true);
	}

}
