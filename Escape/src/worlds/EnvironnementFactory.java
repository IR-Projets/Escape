package worlds;

import java.io.IOException;
import java.util.LinkedList;
import java.util.List;
import java.util.Random;

import game.Variables;
import gestures.Gesture;

import org.jbox2d.collision.shapes.PolygonShape;
import org.jbox2d.common.Vec2;
import org.jbox2d.dynamics.Body;
import org.jbox2d.dynamics.BodyDef;
import org.jbox2d.dynamics.BodyType;
import org.jbox2d.dynamics.World;
import org.jbox2d.testbed.tests.EdgeShapes;

import ships.Player;
import ships.Ship;
import ships.enemies.Enemy;
import worlds.Environnement.Lancher;
import Maps.Map;

public class EnvironnementFactory {
	
	/*
	 * TODO: set the gravity to 0 (set to 10 for test only)
	 */
	private static final float GRAVITY_X = 0;
	private static final float GRAVITY_Y = 0;
	private static final boolean DO_SLEEP = false;
	private static Lancher lancher;
	
	public static Environnement WORLD1(World world){
		Environnement env = new Environnement(lancher, world);
		Map map;
		Ship playerShip;
		List<Entity> entityList = new LinkedList<>();
		try {
			map = new Map();
			playerShip = new Player();
			
			for(int i=0;i<10; i++){
				entityList.add(new Enemy());
			}
			
		} catch (IOException e) {
			throw new IllegalStateException("Impossible de créer le monde 1", e);
		}
		
		env.setMap(map);
		env.setGesture(new Gesture(playerShip));
		env.addEntity(playerShip, 75, 86);
		
		Random rand = new Random();
		for(Entity entity : entityList){
			env.addEntity(entity, rand.nextInt(Variables.SCREEN_WIDTH), rand.nextInt(Variables.SCREEN_HEIGHT));
		}
		
		return env;
	}
	
	
	public static Environnement factory(World world){
		if(world==null){
			lancher = Lancher.Main;
			world = new World(new Vec2(GRAVITY_X, GRAVITY_Y), DO_SLEEP);
		}
		else{
			lancher = lancher.Testbed;
			world.setGravity(new Vec2(GRAVITY_X, GRAVITY_Y));
			world.setAllowSleep(DO_SLEEP);
		}

		setWorldLimit(world);
		
		
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
