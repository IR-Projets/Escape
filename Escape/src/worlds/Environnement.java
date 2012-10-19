package worlds;

import java.util.LinkedList;
import java.util.List;

import org.jbox2d.common.Vec2;
import org.jbox2d.dynamics.Body;
import org.jbox2d.dynamics.BodyDef;
import org.jbox2d.dynamics.BodyType;
import org.jbox2d.dynamics.World;
import org.jbox2d.collision.shapes.PolygonShape;;

public class Environnement {

	
	private static final float GRAVITY_X = 10;
	private static final float GRAVITY_Y = 10;
	private static final boolean DO_SLEEP = false;
	private static final float TIME_STEP = 1.0f / 60.f;
	private static final int VELOCITY_ITERATION = 10;
	private static final int POSITION_ITERATION = 8;
	
	/*
	 * Singleton
	 */
	private static Environnement env=null;
	public static Environnement get(World world){
		if(env==null)
			env = new Environnement(world);
		return env;
	}
	public static Environnement get(){
		if(env==null)
			env = new Environnement();
		return env;
	}
	
	
	
	private World world;
	private List<Entity> entities;
	
	private Environnement(World world){		
		if(world==null){
			this.world = new World(new Vec2(GRAVITY_X, GRAVITY_Y), DO_SLEEP);
			this.world.step(TIME_STEP, VELOCITY_ITERATION, POSITION_ITERATION);
		}
		else{
			this.world = world;
		}
		setWorldLimit();
		entities = new LinkedList<>();
	}
	
	private Environnement(){
		this(null);
	}
	
	private void setWorldLimit(){
		PolygonShape polygon = new PolygonShape();
		polygon.setAsBox(10, 10);
		
		BodyDef bodyDef = new BodyDef();
		bodyDef.type = BodyType.STATIC;
		bodyDef.position.set(0, 0);
		Body body = world.createBody(bodyDef);
		body.createFixture(polygon, 5.0f);
	}
	
	public void addEntity(Entity entity){
		entities.add(entity);
	}
	
	public World getWorld(){
		return world;
	}
	
}
