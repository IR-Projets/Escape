package entities.ships.enemies;

import java.awt.image.BufferedImage;

import listeners.CollisionListener.EntityType;

import entities.Entities;
import entities.Entity;

public class Boss extends Enemy{

	public Boss(Entities entities, BufferedImage image, int x, int y, int life, EnemyBehavior behavior) {
		super(entities, image, x, y, life, behavior);
		setCollisionGroup(EntityType.Boss);
	}

	@Override
	public void collision(Entity entity, EntityType entityType) {
		if(entityType==EntityType.WeaponPlayer){
			setLife(getLife()-entity.getDamage());
			if(getLife() <= 0)
				explode();
		}
	}

	@Override
	public EntityType getEntityType() {
		return EntityType.Boss;
	}

}
