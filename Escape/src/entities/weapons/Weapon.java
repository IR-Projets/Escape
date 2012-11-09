package entities.weapons;

import java.awt.geom.AffineTransform;
import java.awt.image.AffineTransformOp;
import java.awt.image.BufferedImage;

import org.jbox2d.common.Vec2;

import effects.Effects;
import effects.Explosion;
import entities.Entities;
import entities.Entity;
import entities.weapons.WeaponFactory.WeaponType;

public abstract class Weapon extends Entity{

	private WeaponType weaponType;
	private BufferedImage image;

	private int damage;

	private boolean isLaunch;
	private final boolean damagedPlayer;

	//TODO : LE systeme d'armement, avec l'integration de la classe Item (item extends entity??)
	public Weapon(Entities entities, EntityShape bodyForm, BufferedImage image, int x, int y, boolean damagedPlayer, int damage, WeaponType weaponType) {
		super(entities, bodyForm.get(entities.getWorld(), x, y, image.getWidth(), image.getHeight()));
		this.image = image;
		this.damagedPlayer=damagedPlayer;
		if(damagedPlayer)
			getBody().getFixtureList().getFilterData().groupIndex = -1;
		else
			getBody().getFixtureList().getFilterData().groupIndex = -2;
		this.damage=0;
		isLaunch=false;
		this.damage=damage;
		this.weaponType=weaponType;
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
	
	public WeaponType getWeaponType() {
		return weaponType;
	}
	
	@Override
	public EntityType getType() {
		if(damagedPlayer)
			return EntityType.WeaponEnnemy;
		return EntityType.WeaponAllied;
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

}
