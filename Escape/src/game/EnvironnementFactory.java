package game;

import java.util.Random;

import org.jbox2d.collision.shapes.PolygonShape;
import org.jbox2d.common.Vec2;
import org.jbox2d.dynamics.Body;
import org.jbox2d.dynamics.BodyDef;
import org.jbox2d.dynamics.World;

import entities.Entities;
import entities.maps.Earth;
import entities.maps.Map;
import entities.ships.Player;
import entities.ships.ShipFactory;
import entities.ships.enemies.Enemy;
import entities.ships.enemies.EnnemyBehavior;
import gestures.Gesture;
import hud.Hud;


public class EnvironnementFactory {
	
	/*
	 * TODO: set the gravity to 0 (set to 10 for test only)
	 */

	private static final boolean DO_SLEEP = false;
	
	public static Environnement WORLD1(World world){
		Entities entities = new Entities(world);		
		EnnemyBehavior ennemyBehavior = new EnnemyBehavior(entities, "script.sir.txt");//= new EnnemyBehavior(entities, "");
		Map map = new Earth();		
		Hud hud = new Hud();
		
		ShipFactory factory = new ShipFactory(entities);
		Player playerShip = factory.createPlayer();
		
		Environnement env = new Environnement(map, playerShip, ennemyBehavior, entities, hud);		
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
	

	
}
