package listeners;

import listeners.CollisionListener.EntityType;
import entities.Entity;


public interface EntitiesListener {
	public void entityRemoved(EntityType entity);	
}
