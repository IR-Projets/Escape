package entities.weapons;

import java.awt.image.BufferedImage;

import entities.Entities;
import entities.Entity;
import entities.CollisionListener.EntityType;

public abstract class Weapon extends Entity{

	private final BufferedImage image;
	
	//TODO : LE systeme d'armement, avec l'integration de la classe Item (item extends entity??)
	public Weapon(Entities entities, BufferedImage image, int x, int y) {
		super(entities, x, y, image.getWidth(), image.getHeight());
		this.image = image;
		
		//getBody().setActive(true); ???????
	}

	@Override
	public BufferedImage getImage(){
		return image;
	}
	
	
	@Override
	public EntityType getType() {
		return EntityType.Weapon;
	}

	@Override
	public void collision(Entity entity, EntityType type) {
		getEntities().removeEntitie(this);	
	}
	
}
