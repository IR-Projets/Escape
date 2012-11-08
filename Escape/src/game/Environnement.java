package game;

import java.awt.Graphics2D;
import java.util.LinkedList;
import java.util.List;

import org.jbox2d.callbacks.ContactImpulse;
import org.jbox2d.callbacks.ContactListener;
import org.jbox2d.collision.Manifold;
import org.jbox2d.common.Vec2;
import org.jbox2d.dynamics.World;
import org.jbox2d.dynamics.contacts.Contact;

import effects.Effects;
import effects.Explosion;
import entities.Entity;
import entities.Entities;
import entities.maps.Clouds;
import entities.maps.Map;
import entities.ships.Player;
import entities.weapons.Fireball;
import fr.umlv.zen2.MotionEvent;
import fr.umlv.zen2.MotionEvent.Kind;
import gestures.Gesture;
import hud.Hud;


public class Environnement {



	private Map map;				//The background map
	private final Entities entities;
	private Gesture gesture;		//Gesture/Event manager
	private Player player;
	private Hud hud;



	/**
	 * Create the environnement with the associated world 
	 * @param world Jbox2d world
	 */
	public Environnement(Map map, Player player, Entities entities, Hud hud){
		this.entities=entities;
		hud = new Hud();
		ennemyBehavior = new EnnemyBehavior(entities, "../script.sir.txt");
	}

	
	/**
	 * Set the background (the map)
	 * @param map the map to be rendered
	 */
	public void setMap(Map map){
		this.map = map;
		this.player = player;
		player.addListener(hud);
		this.entities=entities;
		this.gesture = new Gesture(this);
		this.hud = hud;
	}
	
	
	public Player getPlayer() {
		return player;
	}


	public Hud getHud() {
		return hud;
	}

	public Entities getEntities() {
		return entities;
	}
	
	
	/**
	 * Render all entities associated
	 * @param graphics draw area
	 */
	public void render(Graphics2D graphics, float interpolation){			
		map.render_groundLayer(graphics);	//The ground (the planet)
		
		entities.render(graphics);			//All the entities (player too)
		gesture.render(graphics);			//Gesture movements (circle)
		Effects.render(graphics);			//Effect (explosion)
		
		map.render_earthLayer(graphics);	//the earth (cloud)
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

	public void compute(float timeStep, int velocityIteration, int positionIteration) {
		entities.step(timeStep, velocityIteration, positionIteration);
		entities.compute();
		map.compute();
		gesture.compute();
		ennemyBehavior.compute();
	}


	public void compute() {		
		compute(Variables.WORLD_TIME_STEP, Variables.WORLD_VELOCITY_ITERATION, Variables.WORLD_POSITION_ITERATION);		
	}
}




