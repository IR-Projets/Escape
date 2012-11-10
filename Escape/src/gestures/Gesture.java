package gestures;

import entities.ships.Player;
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
import java.util.List;

import org.jbox2d.common.Vec2;
/**
 * The Gesture is the class which manage the Gesture effect, for move the player or launch a weapon.
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
	 * Our TraceStack, which reprents all Trace created by the player during the game
	 */
	private final TraceStack traceStack;
	/**
	 * Represents all filters uses for knows if a trace is correctly set
	 */
	private final List<Filter> filters;
	
	/**
	 * Our environnement, for knows the current player, the HUD for create and launch weapon
	 */
	private final Environnement env;

	/**
	 * An enum for represents the kind of gesture the player wants : He wants to move, or to shoot
	 */
	private enum Action{ MOVE, SHOOT };
	private Action action;


	/**
	 * Public constructor. You have to precised an environnement for instanciate a gesture, for knows entities of the game (player, items...)
	 * @param env
	 */
	public Gesture(Environnement env){
		this.env=env;
		traceStack = new TraceStack();
		filters = initFilters();
	}

	//TODO !!
	public void compute() {
		/*
		 * LF:
		 * C'est la qu'on fait tout les calculs! 
		 * render se charge uniquement de l'affichage!!!		
		 */		
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
	 * Shoot the weapon created by the player, which are in his lists of weapons.
	 */
	public void shootWeapon(){
		List<Vec2> traceWeapon = traceStack.getCurrentTrace().getTrace();
		double angle;
		if(traceWeapon.size() <=1)//we shoot in top by default
			angle = 90;
		else
			angle = Filters.getAngle(traceWeapon);
		int vitX = (int) (Math.cos(Math.toRadians(angle))*Variables.SPEED_WEAPON);
		int vitY = (int) (Math.sin(Math.toRadians(angle))*Variables.SPEED_WEAPON);
		env.getPlayer().shoot(vitX, vitY);
		traceStack.getCurrentTrace().setValid(true);
	}

	/**
	 * Create the Weapon associated with the current Item
	 */
	private void createWeapon() {
		Player player = env.getPlayer();
		Vec2 pos = player.getPositionNormalized();
		int width = player.getImage().getWidth();
		int height = player.getImage().getHeight();
		if(env.getHud().getItemActual().getWeaponType() == WeaponType.Shiboleet)
			env.getWeapons().addAll(env.getHud().createSelectedWeapon(env.getEntities(), (int)pos.x+width/2, (int)pos.y-height/2, false));
		else
			env.getWeapons().addAll(env.getHud().createSelectedWeapon(env.getEntities(), (int)pos.x+width/2, (int)pos.y+height/2, false));
		action = Action.SHOOT;
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
				for(Filter filter : filters)
					if(traceStack.check(filter))
						filter.apply(env.getPlayer());
			}
			else
				if(!env.getWeapons().isEmpty())
					shootWeapon();
			traceStack.finishCurrentTrace();
			break;

		case ACTION_DOWN :
			if(env.getPlayer().isOnSprite(new Vec2(event.getX(), event.getY())) && event.getKind()==Kind.ACTION_DOWN && !env.getHud().getItemList().isEmpty())
				createWeapon();
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