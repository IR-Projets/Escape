package factories;

import java.util.Random;

import maps.Earth;
import maps.Moon;
import maps.Jupiter;
import maps.Map;

import org.jbox2d.collision.shapes.PolygonShape;
import org.jbox2d.common.Vec2;
import org.jbox2d.dynamics.Body;
import org.jbox2d.dynamics.BodyDef;
import org.jbox2d.dynamics.World;

import entities.Entities;
import entities.ships.Player;
import entities.ships.enemies.Enemy;
import entities.ships.enemies.EnnemyBehavior;
import game.Environnement;
import game.Variables;
import gestures.Gesture;
import hud.Hud;


public class EnvironnementFactory {
	public enum Level{
		Earth,
		Moon,
		Jupiter
	}
	
	
	private static final boolean DO_SLEEP = false;
	
	private static Environnement Earth(World world){
		Entities entities = new Entities(world);		
		EnnemyBehavior ennemyBehavior = new EnnemyBehavior(entities, "script.sir.txt");//script de la terre
		Map map = new Earth();		
		Hud hud = new Hud();
		
		ShipFactory factory = new ShipFactory(entities);
		Player playerShip = factory.createPlayer();
		
		Environnement env = new Environnement(map, playerShip, ennemyBehavior, entities, hud);		
		return env;
	}
	
	private static Environnement Moon(World world){
		Entities entities = new Entities(world);		
		EnnemyBehavior ennemyBehavior = new EnnemyBehavior(entities, "script.sir.txt");//Script de la lune
		Map map = new Moon();		
		Hud hud = new Hud();
		
		ShipFactory factory = new ShipFactory(entities);
		Player playerShip = factory.createPlayer();
		
		Environnement env = new Environnement(map, playerShip, ennemyBehavior, entities, hud);		
		return env;
	}
	
	private static Environnement Jupiter(World world){
		Entities entities = new Entities(world);		
		EnnemyBehavior ennemyBehavior = new EnnemyBehavior(entities, "script.sir.txt");//Script de la lune
		Map map = new Jupiter();		
		Hud hud = new Hud();
		
		ShipFactory factory = new ShipFactory(entities);
		Player playerShip = factory.createPlayer();
		
		Environnement env = new Environnement(map, playerShip, ennemyBehavior, entities, hud);		
		return env;
	}
	
	/*
	 * Environnement factory principal (celui du main)
	 */
	public static Environnement factory(World world, Level level){
		if(world==null){
			world = new World(new Vec2(Variables.WORLD_GRAVITY_X, Variables.WORLD_GRAVITY_Y), DO_SLEEP);
		}
		else{
			world.setGravity(new Vec2(Variables.WORLD_GRAVITY_X, Variables.WORLD_GRAVITY_Y));
			world.setAllowSleep(DO_SLEEP);
		}		
		
		switch(level){
		case Earth:
			return Earth(world);
		case Moon:
			return Moon(world);
		case Jupiter: 
			return Jupiter(world);
		}
		return null;		
	}
	
	/*
	 * Environnement factory du test JBox2d
	 */
	public static Environnement factory(Level level){
		return factory(null, level);
	}
	

	
}
