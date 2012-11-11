package entities;

import entities.Entity.EntityType;


public interface CollisionListener {	
	public EntityType getType();
	public void collision(Entity entity, EntityType type);
}
