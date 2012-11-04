package gestures;

import entities.ships.Player;
import entities.weapons.Fireball;
import entities.weapons.Weapon;
import fr.umlv.zen2.MotionEvent;
import fr.umlv.zen2.MotionEvent.Kind;
import gestures.filters.Backoff;
import gestures.filters.Drift;
import gestures.filters.Filter;
import gestures.filters.Looping;

import java.awt.Graphics2D;
import java.util.ArrayList;
import java.util.List;

import org.jbox2d.common.Vec2;

public class Gesture {

	private TraceStack traceStack;
	private List<Filter> filters;
	private final Player controlledShip;
	

	public Gesture(Player controlledShip){
		this.controlledShip = controlledShip;
		traceStack = new TraceStack();
		filters = initFilters();
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
		return filtersList;
	}
	

	/**
	 * Display the Gesture, which is a trace of the movement printing by the mouse
	 * @param Graphics2D graphics
	 */
	public void render(Graphics2D graphics){
		if(traceStack.isEmpty()){
			controlledShip.setVelocity(0, 0);
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
			for(Filter filter : filters){
				if(traceStack.check(filter)){
					filter.apply(controlledShip);
				}
			}
			traceStack.finishCurrentTrace();
			break;

		case ACTION_DOWN :
			if(controlledShip.isOnSprite(new Vec2(event.getX(), event.getY()))){
				Vec2 pos = controlledShip.getScreenPostion();
				Weapon weapon = new Fireball((int)pos.x+50, (int)pos.y+50);
			}
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