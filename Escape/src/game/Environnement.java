package game;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.LinkedList;
import java.util.List;
import java.util.Objects;

import javax.imageio.ImageTypeSpecifier;

import org.jbox2d.callbacks.ContactImpulse;
import org.jbox2d.callbacks.ContactListener;
import org.jbox2d.collision.Manifold;
import org.jbox2d.common.Vec2;
import org.jbox2d.dynamics.World;
import org.jbox2d.dynamics.contacts.Contact;


import effects.Effects;
import effects.Explosion;
import entities.CollisionListener;
import entities.Entity;
import entities.maps.Map;
import entities.ships.Player;
import entities.ships.Ship;
import fr.umlv.zen2.MotionEvent;
import gestures.Gesture;
import hud.Hud;


public class Environnement {
	

	
	//private World world;			//The Jbox2D world
	private Map map;				//The background map
	private List<Entity> entities;	//All entities
	private List<Entity> entitiesToDelete;	//All entities
	private Gesture gesture;		//Gesture/Event manager
	private Player player;

	
	
	/**
	 * Create the environnement with the associated world 
	 * @param world Jbox2d world
	 */
	public Environnement(World world){
		Entity.setWorld(world);
		
		world.setContactListener(new ContactListener() {
			@Override
			public void preSolve(Contact contact, Manifold oldManifold) {
			}			
			@Override
			public void postSolve(Contact contact, ContactImpulse impulse) {
			}			
			@Override
			public void endContact(Contact contact) {
			}			
			@Override
			public void beginContact(Contact contact) {
				if(contact.getFixtureA().getBody() == player.getBody()){
					playerCollision(Entity.getEntity(contact.getFixtureB().getBody()));
				}
				else if(contact.getFixtureB().getBody() == player.getBody()){
					playerCollision(Entity.getEntity(contact.getFixtureA().getBody()));
				}
			}
		});
		
		entities = new LinkedList<>();
		entitiesToDelete = new LinkedList<>();
	}
	
	/**
	 * Set the background (the map)
	 * @param map the map to be rendered
	 */
	public void setMap(Map map){
		this.map = map;
	}
	
	public void setGesture(Gesture gesture) {
		this.gesture = gesture;		
	}
	
	/* Bien init le hud avant le player!!!*/
	public void setPlayer(Player player){
		this.player = player;
		player.addListener(Hud.get());
		addEntity(player, 0, 0);
	}
	
	
	
	
	protected void playerCollision(Entity entity) {
		if(entity!=null){
			Vec2 pos = entity.getScreenPostion();
			
			Effects.addEffect(new Explosion((int)pos.x, (int)pos.y));
			//Test
			player.setLife(player.getLife()-20);
			entitiesToDelete.add(entity);
		}
	}

	/**
	 * Add an entity to the Environnement at the specified position
	 * @param entity the entity to add
	 * @param x start position x
	 * @param y start position y
	 */
	public void addEntity(Entity entity, int x, int y){
		entity.init(x, y);
		entities.add(entity);
	}
	
	public void removeEntity(Entity entity){
		entities.remove(entity);
		Entity.removeEntity(entity);
		
	}
	
	/**
	 * Render all entities associated
	 * @param graphics draw area
	 */
	public void render(Graphics2D graphics){			
		//render all: map, entities and the gesture
		map.render(graphics);
		player.render(graphics);
		for(Entity entity : entities)
			entity.render(graphics);
		gesture.render(graphics);
		Effects.render(graphics);
		Hud.get().render(graphics);
	}

	
	public void step(){	
		//First we compute the movement with JBox2d (only for Main lanch, testbed do it alone)
		Entity.step();
	}
	
	/**
	 * Send the event to the gesture manager
	 * @param event the event to be handled
	 */
	public void event(MotionEvent event) {
		gesture.event(event);
		Hud.get().event(event);
	}

	public void compute() {		
		
		/*
		 * Delete entities that were collided
		 */
		for(Entity entity : entitiesToDelete)
			removeEntity(entity);
		entitiesToDelete.clear();
		
		map.compute();
		for(Entity entity : entities)
			entity.compute();
		gesture.compute();
	}	
}




