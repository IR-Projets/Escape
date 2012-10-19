package worlds;

import java.io.IOException;

import gestures.Gesture;

import org.jbox2d.collision.shapes.PolygonShape;
import org.jbox2d.common.Vec2;
import org.jbox2d.dynamics.Body;
import org.jbox2d.dynamics.BodyDef;
import org.jbox2d.dynamics.BodyType;
import org.jbox2d.dynamics.World;

import ships.Ship;
import Maps.Map;

public class EnvironnementFactory {
	
	private static final float GRAVITY_X = 10;
	private static final float GRAVITY_Y = 10;
	private static final boolean DO_SLEEP = false;
	private static final float TIME_STEP = 1.0f / 60.f;
	private static final int VELOCITY_ITERATION = 10;
	private static final int POSITION_ITERATION = 8;
	
	private static World createDefaultWorld(){
		World world = new World(new Vec2(GRAVITY_X, GRAVITY_Y), DO_SLEEP);
		world.step(TIME_STEP, VELOCITY_ITERATION, POSITION_ITERATION);
		return world;
	}
	
	private static void setWorldLimit(World world){
		PolygonShape polygon = new PolygonShape();
		polygon.setAsBox(5, 5);
		
		BodyDef bodyDef = new BodyDef();
		bodyDef.type = BodyType.STATIC;
		bodyDef.position.set(0, 0);
		Body body = world.createBody(bodyDef);
		body.createFixture(polygon, 5.0f);
	}
	
	
	public static Environnement WORLD1(World world){
		Environnement env = new Environnement(world);
		Map map;
		Ship ship;
		try {
			map = new Map();
			ship = new Ship();
		} catch (IOException e) {
			throw new IllegalStateException("Impossible de créer le monde 1", e);
		}
		
		env.setMap(map);
		env.setGesture(new Gesture());
		env.addEntity(ship, 1, 1);
		
		//ship.init(1, 1);
		
		
		return env;
	}
	
	
	public static Environnement factory(World world){
		if(world==null){
			world = createDefaultWorld();
		}
		setWorldLimit(world);
		
		/*
		 * Mettre ICI tout les niveaux du jeu!
		 */		
		return WORLD1(world);
	}
	
	public static Environnement factory(){
		return factory(null);
	}
	
}
