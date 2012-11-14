package factories;

import entities.Entities;
import entities.ships.Player;
import entities.ships.enemies.EnemiesLoader;
import game.Environnement;
import game.Hud;
import game.Variables;
import maps.Earth;
import maps.Jupiter;
import maps.Map;
import maps.Moon;

import org.jbox2d.common.Vec2;
import org.jbox2d.dynamics.World;


public class EnvironnementFactory {
	public enum Level{
		Earth,
		Moon,
		Jupiter
	}


	private static final boolean DO_SLEEP = false;

	private static Environnement Earth(World world){
		Entities entities = new Entities(world);		
		EnemiesLoader ennemyloader = new EnemiesLoader(entities, "scripts/EnemiesEarth.xml");//xml of ennemies of the earth

		ShipFactory factory = new ShipFactory(entities);
		Player playerShip = factory.createPlayer();

		Map map = new Earth();
		Environnement env = new Environnement(entities, map, playerShip, ennemyloader);		
		return env;
	}

	private static Environnement Moon(World world){
		Entities entities = new Entities(world);		
		EnemiesLoader ennemyloader = new EnemiesLoader(entities, "scripts/EnemiesEarth.xml");//xml of ennemies of the moon

		ShipFactory factory = new ShipFactory(entities);
		Player playerShip = factory.createPlayer();

		Map map = new Moon();		
		Environnement env = new Environnement(entities, map, playerShip, ennemyloader);		
		return env;
	}

	private static Environnement Jupiter(World world){
		Entities entities = new Entities(world);		
		EnemiesLoader ennemyloader = new EnemiesLoader(entities, "scripts/EnemiesEarth.xml");//xml of ennemies of the jupiter

		ShipFactory factory = new ShipFactory(entities);
		Player playerShip = factory.createPlayer();

		Map map = new Jupiter();		
		Environnement env = new Environnement(entities, map, playerShip, ennemyloader);		
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
