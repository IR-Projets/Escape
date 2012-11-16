package gestures;

import entities.ships.Player;
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
import java.util.List;

import org.jbox2d.common.Vec2;
/**
 * The Gesture is the class which manage the Gesture effect, associated with the player.
 * He manage the control of all gesture launch by the player, and launch the correction action (move or shoot).
 * @author Quentin Bernard et Ludovic Feltz
 */

/* <This program is an Shoot Them up space game, called Escape-IR, made by IR students.>
 *  Copyright (C) <2012>  <BERNARD Quentin & FELTZ Ludovic>

 *  This program is free software: you can redistribute it and/or modify
 *  it under the terms of the GNU General Public License as published by
 *  the Free Software Foundation, either version 3 of the License, or
 *  (at your option) any later version.

 *  This program is distributed in the hope that it will be useful,
 *  but WITHOUT ANY WARRANTY; without even the implied warranty of
 *  MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 *  GNU General Public License for more details.

 *  You should have received a copy of the GNU General Public License
 *  along with this program.  If not, see <http://www.gnu.org/licenses/>.
 *
 */
public class Gesture {

	/**
	 * Our TraceStack, which represents all Trace created by the player during the game.
	 */
	private final TraceStack traceStack;
	
	/**
	 * Represents all filters uses for knows if a trace is correctly set.
	 */
	private final List<Filter> filters;
	
	/**
	 * An enum for represents the kind of gesture the player wants : He wants to move, or to shoot.
	 */
	private enum Action{ MOVE, SHOOT };
	private Action action;

	private final Environnement environnement;
	/**
	 * Public constructor. You have to precised an environnement for instanciate a gesture, for knows entities of the game (player, items...).
	 * @param env
	 */
	public Gesture(Environnement environnement){
		this.environnement = environnement;
		traceStack = new TraceStack();
		filters = initFilters();
	}


	/**
	 * Initialize all filters, which implements Filter Interface.
	 * @return the list of filter initialize with all filters.
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
	 * Display the Gesture, which is a trace of the movement printing by the mouse.
	 * @param Graphics2D graphics.
	 */
	public void render(Graphics2D graphics){
		if(traceStack.isEmpty()){
			environnement.getPlayer().stop();
			return;
		}
		traceStack.render(graphics);
	}


	/**
	 * Shoot the weapon created by the player, which are in his lists of weapons.
	 */
	private void shootWeapon(){
		List<Vec2> traceWeapon = traceStack.getCurrentTrace().getTrace();
		double angle;
		if(traceWeapon.size() <=1)//we shoot in top by default
			angle = 90;
		else
			angle = Filters.getAngle(traceWeapon);
		environnement.getPlayer().shootWeapon(angle, Variables.SHIP_BULLET_VELOCITY);
		
		/*int vitX = (int) (Math.cos(Math.toRadians(angle))*Variables.SPEED_WEAPON);
		int vitY = (int) (Math.sin(Math.toRadians(angle))*Variables.SPEED_WEAPON);*/
		traceStack.getCurrentTrace().setValid(true);
	}

	/**
	 * The event launched by the mouse, which is described by zen2 Libraries.
	 * @param MotionEvent event : the event of the mouse.
	 * @see Kind
	 */
	public void event(MotionEvent event){
		Player player = environnement.getPlayer();
		
		switch(event.getKind()){	
		case ACTION_UP :
			if(action == Action.MOVE){
				for(Filter filter : filters)
					if(traceStack.check(filter))
						filter.apply(player);
			}
			else if (action == Action.SHOOT){
				shootWeapon();
			}
			traceStack.finishCurrentTrace();
			break;

		case ACTION_DOWN :
			if(player.isOnSprite(new Vec2(event.getX(), event.getY())) && !player.getWeapons().isEmpty()){
				player.getWeapons().removeCurrentItem();
				player.loadWeapon();
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
}