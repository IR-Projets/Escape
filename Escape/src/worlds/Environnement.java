package worlds;

import java.awt.Graphics2D;
import java.util.LinkedList;
import java.util.List;

import org.jbox2d.common.Vec2;
import org.jbox2d.dynamics.World;

import fr.umlv.zen2.MotionEvent;
import gestures.Gesture;

import Maps.Map;

public class Environnement {
	
	private static final float TIME_STEP = 1.0f / 60.f;
	private static final int VELOCITY_ITERATION = 10;
	private static final int POSITION_ITERATION = 8;
	
	private World world;			//The Jbox2D world
	private Map map;				//The background map
	private List<Entity> entities;	//All entities
	private Gesture gesture;		//Gesture/Event manager

	
	
	
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
		for(Entity entity : entities)
			entity.render(graphics);
		gesture.render(graphics);
	}

	
	public void step(){
		//First we compute the movement with JBox2d (only for Main lanch, testbed do it alone)
		world.step(TIME_STEP, VELOCITY_ITERATION, POSITION_ITERATION);	
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




