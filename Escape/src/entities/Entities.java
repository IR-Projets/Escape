package entities;

//import Entity;
import entities.ships.Player;
import game.Ressources;
import game.Variables;

import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.util.Hashtable;

import org.jbox2d.collision.shapes.PolygonShape;
import org.jbox2d.dynamics.Body;
import org.jbox2d.dynamics.BodyDef;
import org.jbox2d.dynamics.BodyType;
import org.jbox2d.dynamics.World;

public class Entities {
	/*
	 * Sauvegarde une association entre body et entity
	 * Un seul World et une seule liste d'entity
	 */	
	private final Hashtable<Body, Entity> entities = new Hashtable<>();
	private final World world;
	
	public Entities(World world){
		this.world = world;
	}
	
	
	
	World getWorld(){
		return world;
	}
	
	/*
	 * Methodes sur les collection d'entities 
	 */
	public Entity getEntitie(Body body){
		return entities.get(body);
	}
	
	public void addEntity(Entity entity){
		entities.put(entity.getBody(), entity);				
	}
	
	public void removeEntitie(Entity entity){
		world.destroyBody(entity.getBody());
		entities.remove(entity.getBody());
	}

	
	
	/*
	 * Methodes de rendu et de calculs
	 */
	public void render(Graphics2D graphics) {
		for(Entity entitie : entities.values())
			entitie.render(graphics);		
	}

	public void compute() {
		for(Entity entitie : entities.values())
			entitie.compute();		
	}	
	
	
}
