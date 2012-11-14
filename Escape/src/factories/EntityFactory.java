package factories;


import org.jbox2d.dynamics.World;

import entities.Entities;
import entities.Entity;



public abstract class EntityFactory {

	private Entities entities;
	
	public EntityFactory(Entities entities){
		this.entities=entities;
	}
	
	protected Entities getEntities(){
		return entities;
	}
	
	protected void createEntity(Entity entity){
		entities.addEntity(entity);
	}

}
