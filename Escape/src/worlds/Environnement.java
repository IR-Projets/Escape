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
	
	private World world;
	private List<Entity> entities;
	private Map map;
	private Gesture gesture;
	
	public Environnement(World world){
		this.world = world;
		entities = new LinkedList<>();
	}
	
	public void addEntity(Entity entity, int x, int y){
		entity.init(world, x, y);
		entities.add(entity);
	}
	
	public World getWorld(){
		return world;
	}
	
	public void setMap(Map map){
		this.map = map;
	}
	
	public void render(Graphics2D graphics){
		world.step(TIME_STEP, VELOCITY_ITERATION, POSITION_ITERATION);
		map.render(graphics);
		for(Entity entity : entities)
			entity.render(graphics);
		gesture.render(graphics);
	}

	public void event(MotionEvent event) {
		gesture.event(event);		
	}

	public void setGesture(Gesture gesture) {
		this.gesture = gesture;		
	}
	
	/*
	public void debug(){
	     world.step(TIME_STEP, VELOCITY_ITERATION, POSITION_ITERATION);
	     Vec2 position = body.getPosition();
	     float angle = body.getAngle();
	     System.out.printf("%4.2f %4.2f %4.2f\n", position.x, position.y, angle)
	}
	*/
	
}
