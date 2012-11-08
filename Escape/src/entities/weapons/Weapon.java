package entities.weapons;

import java.awt.image.BufferedImage;

import org.jbox2d.common.Vec2;

import effects.Effects;
import effects.Explosion;
import entities.Entities;
import entities.Entity;
import entities.CollisionListener.EntityType;

public abstract class Weapon extends Entity{

	private final BufferedImage image;
	private final boolean damagedPlayer;

	//TODO : LE systeme d'armement, avec l'integration de la classe Item (item extends entity??)
	public Weapon(Entities entities, BufferedImage image, int x, int y, boolean damagedPlayer) {
		super(entities, x, y, image.getWidth(), image.getHeight());
		this.image = image;
		this.damagedPlayer=damagedPlayer;
	}

	@Override
	public BufferedImage getImage(){
		return image;
	}

	@Override
	public EntityType getType() {
		if(damagedPlayer)
			return EntityType.WeaponEnnemy;
		return EntityType.WeaponAllied;
	}

	@Override
	public void collision(Entity entity, EntityType type) {
		if(damagedPlayer==false && type==EntityType.Joueur)
			setSensor(false);
		else{
			Vec2 pos = getScreenPostion();
			Effects.addEffect(new Explosion((int)pos.x, (int)pos.y));
			getEntities().removeEntitie(this);
		}

	}

}
