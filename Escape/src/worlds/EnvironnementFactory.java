package worlds;

import java.io.IOException;

import game.Variables;
import gestures.Gesture;

import org.jbox2d.collision.shapes.PolygonShape;
import org.jbox2d.common.Vec2;
import org.jbox2d.dynamics.Body;
import org.jbox2d.dynamics.BodyDef;
import org.jbox2d.dynamics.BodyType;
import org.jbox2d.dynamics.World;
import org.jbox2d.testbed.tests.EdgeShapes;

import ships.Ship;
import Maps.Map;

public class EnvironnementFactory {
	
	private static final float GRAVITY_X = 10;
	private static final float GRAVITY_Y = 10;
	private static final boolean DO_SLEEP = true;

	
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
		env.setGesture(new Gesture(ship));
		env.addEntity(ship, 1, 1);
		
		return env;
	}
	
	
	public static Environnement factory(World world){
		if(world==null){
			world = createDefaultWorld();
		}
		
		/*
		 * TODO: Mettre ICI tout les niveaux du jeu!
		 * 
		 * if(lvl1)
		 * 		return world1()
		 * ...
		 *  
		 */		
		return WORLD1(world);
	}
	
	public static Environnement factory(){
		return factory(null);
	}
	
	
	
	
	
	/*
	 * Create a default world with no gravity
	 */
	private static World createDefaultWorld(){
		World world = new World(new Vec2(GRAVITY_X, GRAVITY_Y), DO_SLEEP);
		setWorldLimit(world);
		return world;
	}
	/*
	 * Set the limit of our world
	 */
	private static void setWorldLimit(World world){
		BodyDef bd = new BodyDef();
		Body ground = world.createBody(bd);

		PolygonShape shape = new PolygonShape();
		//0,0->width,0
		shape.setAsEdge(new Vec2(0f, 0f), new Vec2(Variables.SCREEN_WIDTH, 0.0f));
		ground.createFixture(shape, 0.0f);
		//Width,0->width,height
		shape.setAsEdge(new Vec2(Variables.SCREEN_WIDTH, 0f), new Vec2(Variables.SCREEN_WIDTH, Variables.SCREEN_HEIGHT));
		ground.createFixture(shape, 0.0f);
		//width,height->0,height
		shape.setAsEdge(new Vec2(Variables.SCREEN_WIDTH, Variables.SCREEN_HEIGHT), new Vec2(0, Variables.SCREEN_HEIGHT));
		ground.createFixture(shape, 0.0f);
		//0,height->0,0
		shape.setAsEdge(new Vec2(0, Variables.SCREEN_HEIGHT), new Vec2(0, 0));
		ground.createFixture(shape, 0.0f);
	}
	
}
