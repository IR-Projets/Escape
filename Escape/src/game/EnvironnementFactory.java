package game;

import java.io.IOException;
import java.util.LinkedList;
import java.util.List;
import java.util.Random;

import entities.Entity;
import entities.maps.Map;
import entities.ships.Player;
import entities.ships.Ship;
import entities.ships.enemies.Enemy;
import gestures.Gesture;

import org.jbox2d.collision.shapes.PolygonShape;
import org.jbox2d.common.Vec2;
import org.jbox2d.dynamics.Body;
import org.jbox2d.dynamics.BodyDef;
import org.jbox2d.dynamics.BodyType;
import org.jbox2d.dynamics.World;
import org.jbox2d.testbed.tests.EdgeShapes;


public class EnvironnementFactory {
	
	/*
	 * TODO: set the gravity to 0 (set to 10 for test only)
	 */

	private static final boolean DO_SLEEP = false;
	
	public static Environnement WORLD1(World world){
		Environnement env = new Environnement(world);
		Map map;
		Player playerShip;
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
		env.setPlayer(playerShip);
		
		Random rand = new Random();
		for(Entity entity : entityList){
			env.addEntity(entity, rand.nextInt(Variables.SCREEN_WIDTH), rand.nextInt(Variables.SCREEN_HEIGHT));
		}
		
		return env;
	}
	
	
	public static Environnement factory(World world){
		if(world==null){
			world = new World(new Vec2(Variables.WORLD_GRAVITY_X, Variables.WORLD_GRAVITY_Y), DO_SLEEP);
		}
		else{
			world.setGravity(new Vec2(Variables.WORLD_GRAVITY_X, Variables.WORLD_GRAVITY_Y));
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

		float worldWidth = Variables.SCREEN_WIDTH/Variables.WORLD_SCALE;
		float worldHeight = Variables.SCREEN_HEIGHT/Variables.WORLD_SCALE;
		
		PolygonShape shape = new PolygonShape();
		//0,0->width,0
		shape.setAsEdge(new Vec2(0f, 0f), new Vec2(worldWidth, 0.0f));
		ground.createFixture(shape, 0.0f);
		//Width,0->width,height
		shape.setAsEdge(new Vec2(worldWidth, 0f), new Vec2(worldWidth, worldHeight));
		ground.createFixture(shape, 0.0f);
		//width,height->0,height
		shape.setAsEdge(new Vec2(worldWidth, worldHeight), new Vec2(0, worldHeight));
		ground.createFixture(shape, 0.0f);
		//0,height->0,0
		shape.setAsEdge(new Vec2(0, worldHeight), new Vec2(0, 0));
		ground.createFixture(shape, 0.0f);
	}
	
}
