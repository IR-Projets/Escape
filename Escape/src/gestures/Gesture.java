package gestures;

import entities.ships.Player;
import entities.weapons.Weapon;
import entities.weapons.WeaponFactory.WeaponType;
import fr.umlv.zen2.MotionEvent;
import fr.umlv.zen2.MotionEvent.Kind;
import game.Environnement;
import game.Variables;
import gestures.filters.ArrowMovement;
import gestures.filters.Backoff;
import gestures.filters.Drift;
import gestures.filters.Filter;
import gestures.filters.Filters;
import gestures.filters.Looping;

import java.awt.Graphics2D;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

import org.jbox2d.common.Vec2;

public class Gesture {

	private final TraceStack traceStack;
	private final List<Filter> filters;
	private final Environnement env;
	private List<Weapon> weaponsToMove;
	
	
	private enum Action{ MOVE, SHOOT };
	private Action action;


	public Gesture(Environnement env){
		this.env=env;
		traceStack = new TraceStack();
		filters = initFilters();
		weaponsToMove = null;
	}

	/*
	 * Pourait être implémenté dans une factory...
	 * Il faut trouver un moyen d'initialier la liste
	 */
	public List <Filter> initFilters(){
		List<Filter> filtersList = new ArrayList<>();
		filtersList.add(new Backoff());
		filtersList.add(new Drift());
		filtersList.add(new Looping());
		filtersList.add(new ArrowMovement());
		return filtersList;
	}


	/**
	 * Display the Gesture, which is a trace of the movement printing by the mouse
	 * @param Graphics2D graphics
	 */
	public void render(Graphics2D graphics){
		if(traceStack.isEmpty()){
			env.getPlayer().stop();
			return;
		}
		traceStack.render(graphics);
	}



	/**
	 * The event launched by the mouse, which is described by zen2 Libraries
	 * @param MotionEvent event : the event of the mouse
	 * @see Kind
	 */
	public void event(MotionEvent event){
		switch(event.getKind()){	
		case ACTION_UP :
			if(action == Action.MOVE){
				for(Filter filter : filters){
					if(traceStack.check(filter)){
						filter.apply(env.getPlayer());
					}
				}
			}
			else{
				if(weaponsToMove != null && !weaponsToMove.isEmpty()){
					List<Vec2> traceWeapon = traceStack.getCurrentTrace().getTrace();
					double angle;
					if(traceWeapon.size() <=1)
						angle = 90;
					else
						angle = Filters.getAngle(traceWeapon);
					float vitX = (float) (Math.cos(Math.toRadians(angle)))*Variables.SPEED_WEAPON;
					float vitY = (float) (Math.sin(Math.toRadians(angle)))*Variables.SPEED_WEAPON;
					for(Weapon weapon : weaponsToMove)
						weapon.launch(vitX, vitY);
					traceStack.getCurrentTrace().setValid(true);
					weaponsToMove = null;
				}
			}
				traceStack.finishCurrentTrace();
			
			break;

		case ACTION_DOWN :
			Player player = env.getPlayer();
			if(player.isOnSprite(new Vec2(event.getX(), event.getY())) && event.getKind()==Kind.ACTION_DOWN && !env.getHud().getItemList().isEmpty()){
				Vec2 pos = player.getPositionNormalized();
				int width = player.getImage().getWidth();
				int height = player.getImage().getHeight();
				if(env.getHud().getItemActual().getWeaponType() == WeaponType.Shiboleet)
					weaponsToMove = env.getHud().createSelectedWeapon(env.getEntities(), (int)pos.x+width/2, (int)pos.y-height/2, false);
				else
					weaponsToMove = env.getHud().createSelectedWeapon(env.getEntities(), (int)pos.x+width/2, (int)pos.y+height/2, false);
				action = Action.SHOOT;
			}
			else
				action = Action.MOVE;
			break;

		case ACTION_MOVE :
			traceStack.getCurrentTrace().addPoint(new Vec2(event.getX(), event.getY()));
			break;

		default:
			break;
		}
	}


	public void compute() {
		/*
		 * LF:
		 * C'est la qu'on fait tout les calculs! 
		 * render se charge uniquement de l'affichage!!!		
		 */		
	}
}