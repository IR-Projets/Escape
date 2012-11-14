package listeners;

import listeners.EntityListener.EntityType;
import entities.Entity;


public interface EntitiesListener {
	public void entityRemoved(EntityType entity);	
}
