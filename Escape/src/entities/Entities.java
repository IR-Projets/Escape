package entities;

//import Entity;
import game.Variables;

import java.awt.Graphics2D;
import java.util.Hashtable;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import listeners.EntitiesListener;
import listeners.CollisionListener.EntityType;

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
	private final World world;
	private final Body worldLimit;
	private final Map<Body, Entity> entities = new Hashtable<>();
	private final List<Entity> entitiesToDelete;	//All entities
	private final List<Entity> entitiesToAdd;	//All entities
	private EntitiesListener entitiesListener;
	
	public Entities(World world){
		entitiesToDelete = new LinkedList<>();
		entitiesToAdd = new LinkedList<>();
		this.world = world;
		worldLimit = setWorldLimit(world);
		world.setContactListener(new ContactListener() {
			@Override
			public void preSolve(Contact contact, Manifold oldManifold) {
				Body bodyA = contact.getFixtureA().getBody();
				Body bodyB = contact.getFixtureB().getBody();
							
				Entity entityA = getEntitie(bodyA);
				Entity entityB = getEntitie(bodyB);
				
				//Collision avec les bordures
				if(bodyA==worldLimit && entityB!=null)
					entityB.collision(null, EntityType.WorldLimit);
				if(bodyB==worldLimit && entityA!=null)
					entityA.collision(null, EntityType.WorldLimit);
					
				//Collision de deux entity
				if(entityA!=null && entityB!=null){
					entityA.collision(entityB, entityB.getEntityType());
					entityB.collision(entityA, entityA.getEntityType());
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
	
	public Body getWorldLimit(){
		return worldLimit;
	}
	
	
	/*
	 * Methodes sur les collection d'entities 
	 */
	public Entity getEntitie(Body body){
		return entities.get(body);
	}
	
	public void addEntity(Entity entity){
		entitiesToAdd.add(entity);
	}
	
	public void removeEntitie(Entity entity){
		entitiesToDelete.add(entity);
	}

	public void addEntitiesListener(EntitiesListener listener) {
		this.entitiesListener = listener;
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
		for(Entity entity : entitiesToAdd)
			entities.put(entity.getBody(), entity);
		entitiesToAdd.clear();
		
		for(Entity entity : entitiesToDelete){
			world.destroyBody(entity.getBody());
			entities.remove(entity.getBody());
			entitiesListener.entityRemoved(entity.getEntityType());
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

		float worldWidth = Variables.SCREEN_WIDTH;
		float worldHeight = Variables.SCREEN_HEIGHT;
		float bordure = Variables.WORLD_BORDER;
		
		PolygonShape shape = new PolygonShape();
		//0,0->width,0
		shape.setAsEdge(new Vec2(-bordure, -bordure), new Vec2(worldWidth+bordure, -bordure));
		ground.createFixture(shape, 0.0f);
		CollisionGroup.setCollisionGroup(ground, EntityType.WorldLimit);
		//Width,0->width,height
		shape.setAsEdge(new Vec2(worldWidth+bordure, -bordure), new Vec2(worldWidth+bordure, worldHeight+bordure));
		ground.createFixture(shape, 0.0f);
		CollisionGroup.setCollisionGroup(ground, EntityType.WorldLimit);
		//width,height->0,height
		shape.setAsEdge(new Vec2(worldWidth+bordure, worldHeight+bordure), new Vec2(-bordure, worldHeight+bordure));
		ground.createFixture(shape, 0.0f);
		CollisionGroup.setCollisionGroup(ground, EntityType.WorldLimit);
		//0,height->0,0
		shape.setAsEdge(new Vec2(-bordure, worldHeight+bordure), new Vec2(-bordure, -bordure));
		ground.createFixture(shape, 0.0f);
		CollisionGroup.setCollisionGroup(ground, EntityType.WorldLimit);
		
		return ground;
	}	
}
