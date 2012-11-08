package entities;


import org.jbox2d.dynamics.World;



public abstract class EntityFactory {

	private Entities entities;
	
	public EntityFactory(Entities entities){
		this.entities=entities;
	}
	
	protected Entities getEntities(){
		return entities;
	}
	

}
