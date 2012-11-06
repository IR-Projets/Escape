package entities;

//import Entity;
import game.Variables;

import java.awt.Graphics2D;
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

	public void render(Graphics2D graphics) {
		for(Entitie entitie : entities.values())
			entitie.render(graphics);		
	}


	public void compute() {
		for(Entitie entitie : entities.values())
			entitie.compute();		
	}	
	
}
