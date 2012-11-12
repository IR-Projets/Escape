package listeners;

import entities.Entity;
import entities.Entity.EntityType;


public interface EntityListener {	
	public EntityType getType();
	public void collision(Entity entity, EntityType type);
}
