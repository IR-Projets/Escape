package game;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.util.LinkedList;
import java.util.List;

import javax.imageio.ImageTypeSpecifier;

import org.jbox2d.callbacks.ContactImpulse;
import org.jbox2d.callbacks.ContactListener;
import org.jbox2d.collision.Manifold;
import org.jbox2d.common.Vec2;
import org.jbox2d.dynamics.World;
import org.jbox2d.dynamics.contacts.Contact;


import entities.CollisionListener;
import entities.Entity;
import entities.maps.Map;
import entities.ships.Player;
import entities.ships.Ship;
import fr.umlv.zen2.MotionEvent;
import gestures.Gesture;


public class Environnement {
	

	
	private World world;			//The Jbox2D world
	private Map map;				//The background map
	private List<Entity> entities;	//All entities
	private Gesture gesture;		//Gesture/Event manager
	private Player player;
	private Hud hud;
	
	
	/*
	 * For the double buffered method
	 */
	private BufferedImage offscreen;
	private Graphics2D bufferGraphics; 

	
	
	
	/**
	 * Create the environnement with the associated world 
	 * @param world Jbox2d world
	 */
	public Environnement(World world){
		this.world = world;
		
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
		offscreen = new BufferedImage(Variables.SCREEN_WIDTH, Variables.SCREEN_HEIGHT, BufferedImage.TYPE_INT_ARGB);
		bufferGraphics = offscreen.createGraphics();
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
	
	public void setHud(Hud hud){
		this.hud = hud;
	}
	
	public void setPlayer(Player player){
		this.player = player;
		player.setCollisionListener(new CollisionListener() {
			@Override
			public void collide(Entity entity) {
				playerCollision(entity);
			}
		});
		addEntity(player, 0, 0);
	}
	
	
	
	
	protected void playerCollision(Entity entity) {
		if(entity!=null)
			removeEntity(entity);
	}

	/**
	 * Add an entity to the Environnement at the specified position
	 * @param entity the entity to add
	 * @param x start position x
	 * @param y start position y
	 */
	public void addEntity(Entity entity, int x, int y){
		entity.init(world, x, y);
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
		bufferGraphics.clearRect(0,0,Variables.SCREEN_WIDTH, Variables.SCREEN_HEIGHT); 
		bufferGraphics.setBackground(new Color(0));
		
		//render all: map, entities and the gesture
		map.render(bufferGraphics);
		player.render(bufferGraphics);
		for(Entity entity : entities)
			entity.render(bufferGraphics);
		gesture.render(bufferGraphics);
		
		graphics.drawImage(offscreen, 0, 0, null);
		hud.render(graphics);
	}

	
	public void step(){
		//First we compute the movement with JBox2d (only for Main lanch, testbed do it alone)
		world.step(Variables.WORLD_TIME_STEP, Variables.WORLD_VELOCITY_ITERATION, Variables.WORLD_POSITION_ITERATION);	
	}
	
	/**
	 * Send the event to the gesture manager
	 * @param event the event to be handled
	 */
	public void event(MotionEvent event) {
		gesture.event(event);		
	}

	public void compute() {		
		map.compute();
		for(Entity entity : entities)
			entity.compute();
		gesture.compute();
	}	
}




