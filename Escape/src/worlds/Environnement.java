package worlds;

import java.awt.Graphics2D;
import java.util.LinkedList;
import java.util.List;

import org.jbox2d.dynamics.World;

import fr.umlv.zen2.MotionEvent;
import gestures.Gesture;

import Maps.Map;

public class Environnement {
	
	private World world;
	private List<Entity> entities;
	private Map map;
	private Gesture gesture;
	
	public Environnement(World world){
		this.world = world;
		entities = new LinkedList<>();
	}
	

	
	public void addEntity(Entity entity){
		entity.setWorld(world);
		entities.add(entity);
	}
	
	public World getWorld(){
		return world;
	}
	
	public void setMap(Map map){
		this.map = map;
	}
	
	public void render(Graphics2D graphics){
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
	
}
