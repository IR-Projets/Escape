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
import entities.maps.Map;
import entities.ships.Player;
import entities.weapons.Fireball;
import fr.umlv.zen2.MotionEvent;
import fr.umlv.zen2.MotionEvent.Kind;
import gestures.Gesture;
import hud.Hud;


public class Environnement {



	private Map map;				//The background map
	//private List<Entitie> entities;	//All entities
	private final Entities entities;
	private List<Entity> entitiesToDelete;	//All entities
	private Gesture gesture;		//Gesture/Event manager
	private Player player;
	private Hud hud;



	/**
	 * Create the environnement with the associated world 
	 * @param world Jbox2d world
	 */
	public Environnement(final Entities entities){

		this.entities=entities;
		entities.getWorld().setContactListener(new ContactListener() {
			@Override
			public void preSolve(Contact contact, Manifold oldManifold) {
			}			
			@Override
			public void postSolve(Contact contact, ContactImpulse impulse) {
			}			
			@Override
			public void endContact(Contact contact) {
			}			
			@Override
			public void beginContact(Contact contact) {
				if(contact.getFixtureA().getBody() == player.getBody()){
					playerCollision(entities.getEntitie(contact.getFixtureB().getBody()));
				}
				else if(contact.getFixtureB().getBody() == player.getBody()){
					playerCollision(entities.getEntitie(contact.getFixtureA().getBody()));
				}
			}
		});
		//entities = new LinkedList<>();
		entitiesToDelete = new LinkedList<>();
		hud = new Hud();
	}

	
	/**
	 * Set the background (the map)
	 * @param map the map to be rendered
	 */
	public void setMap(Map map){
		this.map = map;
	}

	public void setGesture(Gesture gesture) {
		this.gesture = gesture;		
	}

	/* Bien init le hud avant le player!!!*/
	public void setPlayer(Player player){
		this.player = player;
		player.addListener(hud);
		//entities.addEntity(player);
	}
	
	public Player getPlayer() {
		return player;
	}


	public Hud getHud() {
		return hud;
	}


	protected void playerCollision(Entity entitie) {
		if(entitie!=null){
			Vec2 pos = entitie.getScreenPostion();

			Effects.addEffect(new Explosion((int)pos.x, (int)pos.y));
			//Test
			player.setLife(player.getLife()-20);
			entitiesToDelete.add(entitie);
		}
	}

	/**
	 * Add an entity to the Environnement at the specified position
	 * @param entity the entity to add
	 * @param x start position x
	 * @param y start position y
	 */
	/*public void addEntity(Entitie entitie, int x, int y){
		entity.init(x, y);
		entities.add(entity);
	}*/
	/*
	public void addEntitie(Entity entitie){
		entities.addEntity(entitie);
	}
	 */
	public void removeEntitie(Entity entitie){
		entities.removeEntitie(entitie);
	}



	/*public void removeEntity(Entity entity){
		entities.remove(entity);
		Entity.removeEntity(entity);

	}*/

	/**
	 * Render all entities associated
	 * @param graphics draw area
	 */
	public void render(Graphics2D graphics){			
		//render all: map, entities and the gesture
		map.render(graphics);
		player.render(graphics);
		entities.render(graphics);
		gesture.render(graphics);
		Effects.render(graphics);
		hud.render(graphics);
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

		/*
		 * Delete entities that were collided
		 */
		for(Entity entitie : entitiesToDelete)
			entities.removeEntitie(entitie);
		entitiesToDelete.clear();

		map.compute();
		entities.compute();
		gesture.compute();
	}


	public void compute() {		
		compute(Variables.WORLD_TIME_STEP, Variables.WORLD_VELOCITY_ITERATION, Variables.WORLD_POSITION_ITERATION);		
	}

	public Entities getEntities() {
		return entities;
	}	
}




