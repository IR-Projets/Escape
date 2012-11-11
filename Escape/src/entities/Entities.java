package entities;

//import Entity;
import entities.Entity.EntityType;
import game.Variables;

import java.awt.Graphics2D;
import java.util.Hashtable;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import org.jbox2d.callbacks.ContactImpulse;
import org.jbox2d.callbacks.ContactListener;
import org.jbox2d.collision.Manifold;
import org.jbox2d.collision.shapes.PolygonShape;
import org.jbox2d.common.Vec2;
import org.jbox2d.dynamics.Body;
import org.jbox2d.dynamics.BodyDef;
import org.jbox2d.dynamics.World;
import org.jbox2d.dynamics.contacts.Contact;

public class Entities {
	/*
	 * Sauvegarde une association entre body et entity
	 * Un seul World et une seule liste d'entity
	 */	
	private final Map<Body, Entity> entities = new Hashtable<>();
	private final List<Entity> entitiesToDelete;	//All entities
	private final World world;
	private EntitiesListener entityListener;
	
	public Entities(World world){
		entitiesToDelete = new LinkedList<>();
		this.world = world;
		final Body worldLimit = setWorldLimit(world);
		world.setContactListener(new ContactListener() {
			@Override
			public void preSolve(Contact contact, Manifold oldManifold) {
				Body bodyA = contact.getFixtureA().getBody();
				Body bodyB = contact.getFixtureB().getBody();
							
				Entity entityA = getEntitie(bodyA);
				Entity entityB = getEntitie(bodyB);
				
				//Collision avec les bordures
				if(bodyA==worldLimit)
					entityB.collision(null, EntityType.WorldLimit);
				if(bodyB==worldLimit)
					entityA.collision(null, EntityType.WorldLimit);
					
				//Collision de deux entity
				if(entityA!=null && entityB!=null){
					entityA.collision(entityB, entityB.getType());
					entityB.collision(entityA, entityA.getType());
				}
			}			
			@Override
			public void postSolve(Contact contact, ContactImpulse impulse) {
			}			
			@Override
			public void endContact(Contact contact) {
			}			
			@Override
			public void beginContact(Contact contact) {
			}
		});
	}
	
	public World getWorld(){
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
	
	public void removeEntitie(Entity entity, EntityType type){
		entitiesToDelete.add(entity);
		entityListener.entityRemoved(type);
	}

	public void addEntitiesListener(EntitiesListener listener) {
		this.entityListener = listener;
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
		/*
		 * Delete entities that were collided
		 */
		for(Entity entity : entitiesToDelete){
			world.destroyBody(entity.getBody());
			entities.remove(entity.getBody());
		}
		entitiesToDelete.clear();
		
		world.step(timeStep, velocityIteration, positionIteration);
	}
	
	
	
	
	
	
	
	/*
	 * Set the limit of our world
	 */
	private static Body setWorldLimit(World world){
		BodyDef bd = new BodyDef();
		Body ground = world.createBody(bd);

		float worldWidth = Variables.SCREEN_WIDTH/Variables.WORLD_SCALE;
		float worldHeight = Variables.SCREEN_HEIGHT/Variables.WORLD_SCALE;
		float bordure = Variables.WORLD_BORDER/Variables.WORLD_SCALE;
		
		PolygonShape shape = new PolygonShape();
		//0,0->width,0
		shape.setAsEdge(new Vec2(-bordure, -bordure), new Vec2(worldWidth+bordure, -bordure));
		ground.createFixture(shape, 0.0f);
		//Width,0->width,height
		shape.setAsEdge(new Vec2(worldWidth+bordure, -bordure), new Vec2(worldWidth+bordure, worldHeight+bordure));
		ground.createFixture(shape, 0.0f);
		//width,height->0,height
		shape.setAsEdge(new Vec2(worldWidth+bordure, worldHeight+bordure), new Vec2(-bordure, worldHeight+bordure));
		ground.createFixture(shape, 0.0f);
		//0,height->0,0
		shape.setAsEdge(new Vec2(-bordure, worldHeight+bordure), new Vec2(-bordure, -bordure));
		ground.createFixture(shape, 0.0f);
		
		return ground;
	}	
}
