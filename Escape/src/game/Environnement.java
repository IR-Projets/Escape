package game;

import java.awt.Graphics2D;
import java.util.LinkedList;
import java.util.List;

import listeners.EntitiesListener;
import listeners.EntityListener.EntityType;
import listeners.EnvironnementListener;
import listeners.EnvironnementListener.GameState;
import maps.Map;
import effects.Effects;
import entities.Entities;
import entities.ships.Player;
import fr.umlv.zen2.MotionEvent;
import gestures.Gesture;


public class Environnement implements EntitiesListener {



	private Map map;				//The background map
	private Entities entities;
	private Gesture gesture;		//Gesture/Event manager
	private Player player;
	private Hud hud;
	private List<EnvironnementListener> gameListener;


	/**
	 * Create the environnement with the associated world 
	 * @param world Jbox2d world
	 */
	public Environnement(Entities entities, Map map, Player player){
		this.gameListener = new LinkedList<>();
		this.map = map;
		this.player = player;
		this.entities=entities;
		this.gesture = new Gesture(this);
		this.hud = new Hud(player);
		
		entities.addEntitiesListener(this);
	}


	public void addListener(EnvironnementListener listener) {
		this.gameListener.add(listener);		
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
	
	/*
	 * Entities Listener: detect the death of each Entity
	 * @see entities.EntitiesListener#entityRemoved(entities.Entity.EntityType)
	 */
	@Override
	public void entityRemoved(EntityType entity) {
		GameState newState = null;
		switch (entity){
		case Boss:
			newState = GameState.Win;
			break;
		case Joueur:
			newState = GameState.Loose;
			break;
		default:
			break;
		}
		if(newState!=null){
			for(EnvironnementListener listener : gameListener){
				listener.stateChanged(newState);
			}
		}
	}
	
	/**
	 * Render all entities associated
	 * @param graphics draw area
	 */
	public void render(Graphics2D graphics){			
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




