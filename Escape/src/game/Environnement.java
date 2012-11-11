package game;

import java.awt.Graphics2D;
import java.util.List;

import maps.Map;

import effects.Effects;
import entities.Entities;
import entities.EntitiesListener;
import entities.Entity.EntityType;
import entities.ships.Player;
import entities.ships.enemies.EnnemyBehavior;
import entities.weapons.Weapon;
import fr.umlv.zen2.MotionEvent;
import gestures.Gesture;
import hud.Hud;


public class Environnement implements EntitiesListener {



	private Map map;				//The background map
	private Entities entities;
	private Gesture gesture;		//Gesture/Event manager
	private Player player;
	private Hud hud;
	private EnnemyBehavior ennemyBehavior;



	/**
	 * Create the environnement with the associated world 
	 * @param world Jbox2d world
	 */
	public Environnement(Map map, Player player, EnnemyBehavior ennemyBehavior, Entities entities, Hud hud){
		this.map = map;
		this.player = player;
		player.addListener(hud);		
		this.ennemyBehavior = ennemyBehavior;		
		this.entities=entities;
		entities.addEntitiesListener(this);
		this.gesture = new Gesture(this);
		this.hud = hud;
		hud = new Hud();
	}

	
	/**
	 * Set the background (the map)
	 * @param map the map to be rendered
	 */
	public void setMap(Map map){

	}
	
	
	public Player getPlayer() {
		return player;
	}

	public List<Weapon> getWeapons(){
		return player.getWeapons();
	}

	public Hud getHud() {
		return hud;
	}

	public Entities getEntities() {
		return entities;
	}
	
	/*
	 * Entities Listener: detect the death of each Entity
	 * @see entities.EntitiesListener#entityRemoved(entities.Entity.EntityType)
	 */
	@Override
	public void entityRemoved(EntityType entity) {
		switch (entity){
		case Boss:
			System.out.println("GAME OVER: WIN !");
			break;
		case Joueur:
			System.out.println("GAME OVER: LOSE !");
			break;
		default:
			System.out.println("Entity Destroyed");
			break;
		}
	}
	
	/**
	 * Render all entities associated
	 * @param graphics draw area
	 */
	public void render(Graphics2D graphics, float interpolation){			
		map.render(graphics);				//The ground (the planet)
		entities.render(graphics);			//All the entities (player too)
		gesture.render(graphics);			//Gesture movements (circle)
		Effects.render(graphics);			//Effect (explosion, cloud, ..)
		hud.render(graphics);				//Health, score, amo
	}

	/**
	 * Send the event to the gesture manager
	 * @param event the event to be handled
	 */
	public void event(MotionEvent event) {
		gesture.event(event);
		hud.event(event);
	}

	
	/**
	 * Methode compute appellée par TestBed: step()
	 */
	public void compute(float timeStep, int velocityIteration, int positionIteration) {
		ennemyBehavior.compute();
		gesture.compute();
		entities.compute();
		entities.step(timeStep, velocityIteration, positionIteration);
		map.compute();
		Effects.compute();
	}
	/**
	 * Methode compute appellée par Game: run()
	 */
	public void compute() {		
		compute(Variables.WORLD_TIME_STEP, Variables.WORLD_VELOCITY_ITERATION, Variables.WORLD_POSITION_ITERATION);		
	}
}




