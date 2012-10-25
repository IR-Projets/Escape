package worlds;

import java.awt.Graphics2D;
import java.util.LinkedList;
import java.util.List;

import org.jbox2d.common.Vec2;
import org.jbox2d.dynamics.World;

import ships.Player;
import ships.Ship;

import fr.umlv.zen2.MotionEvent;
import game.Variables;
import gestures.Gesture;

import Entity.CollisionListener;
import Entity.Entity;
import Maps.Map;

public class Environnement {
	

	
	private World world;			//The Jbox2D world
	private Map map;				//The background map
	private List<Entity> entities;	//All entities
	private Gesture gesture;		//Gesture/Event manager
	private Ship player;

	
	
	
	/**
	 * Create the environnement with the associated world 
	 * @param world Jbox2d world
	 */
	public Environnement(World world){
		this.world = world;
		entities = new LinkedList<>();
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
	
	public void setPlayer(Ship player){
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
		entities.remove(entity);
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
	
	
	
	
	/**
	 * Render all entities associated
	 * @param graphics draw area
	 */
	public void render(Graphics2D graphics){	
		//Then we render all: map, entities and the gesture
		map.render(graphics);
		player.render(graphics);
		for(Entity entity : entities)
			entity.render(graphics);
		gesture.render(graphics);
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




