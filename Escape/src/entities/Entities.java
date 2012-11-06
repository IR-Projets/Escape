package entities;

//import Entity;
import entities.ships.Player;
import game.Ressources;
import game.Variables;

import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.util.Hashtable;
import java.util.Map;

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
	private final Map<Body, Entity> entities = new Hashtable<>();
	private final World world;
	
	public Entities(World world){
		this.world = world;
	}
	
	public World getWorld(){
		return world;
	}
	
	
	public enum EntityType{
		Player,
		Ennemy
	}
	
	public Entity createEntity(EntityType entityType){
		Entity entity = null;
		switch(entityType){
			case Player:
				entity = new Player(this);
				break;
			case Ennemy:
				entity = EnnemyFactory.createEntity(this);
				break;
		}
		addEntity(entity);
		return entity;
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
	
	public void step(float timeStep, int velocityIteration, int positionIteration){
		world.step(timeStep, velocityIteration, positionIteration);
	}
	
	
	
	protected Body createBody(int posX, int posY, int width, int height){
		PolygonShape polygonShape = new PolygonShape();
		polygonShape.setAsBox(Entity.toWorldSize(width/2), Entity.toWorldSize(height/2));
		BodyDef bodyDef = new BodyDef();
		bodyDef.type = BodyType.DYNAMIC;
		bodyDef.position.set(Entity.toWorldSize(posX), Entity.toWorldSize(posY));
		bodyDef.allowSleep = false;
		Body body = world.createBody(bodyDef);
		body.createFixture(polygonShape, 1.0f);
		return body;
	}
	
	
}
