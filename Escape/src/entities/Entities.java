package entities;

//import Entity;
import game.Variables;

import java.util.Hashtable;

import org.jbox2d.dynamics.Body;
import org.jbox2d.dynamics.World;

public class Entities {
	/*
	 * Sauvegarde une association entre body et entity
	 * Un seul World et une seule liste d'entity
	 */
	
	private final Hashtable<Body, Entitie> entities;
	private final World world;
	
	public Entities(World world){
		this.world = world;
		entities = new Hashtable<>();
	}
	
	public Hashtable<Body, Entitie> getEntities() {
		return entities;
	}
	
	public Entitie getEntitie(Body body){
		return entities.get(body);
	}

	public void addEntitie(Entitie entitie) {
		entities.put(entitie.getBody(), entitie);
	}
	
	public void removeEntitie(Entitie entity){
		world.destroyBody(entity.getBody());
		entities.remove(entity.getBody());
	}
	
	public void step() {
		world.step(Variables.WORLD_TIME_STEP, Variables.WORLD_VELOCITY_ITERATION, Variables.WORLD_POSITION_ITERATION);			
	}
	
}
