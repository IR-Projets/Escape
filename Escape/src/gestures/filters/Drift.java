package gestures.filters;

import java.util.List;

import org.jbox2d.common.Vec2;

import entities.ships.Player;
import game.Variables;
/**
 * The drift is a movement vertical which goes to the top of the screen, to the right or the left.
 * we refuse the horizontal and verticals movements
 * 
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

public class Drift implements Filter{
	/**
	 * The vertical bound tilt angle that the drift refuse (90 +- this variable)
	 */
	public static final int TRACE_DRIFT_BORNES_TOP = 10;
	/**
	 * The horizontal bound tilt angle that the drift refuse(0 + this variable and 180 - this variable)
	 */
	public static final int TRACE_DRIFT_BORNES_BOT = 10;
	
	private double angle=0;
	
	/**
	 * Check if the angle of the Right is correctly for a left or right drift, but no vertical or horizontal courbe
	 * @param angle
	 * @return true if the angle is correct
	 */
	public boolean checkAngle(double angle){
		if((angle >= 90-TRACE_DRIFT_BORNES_TOP && angle <= 90+TRACE_DRIFT_BORNES_TOP )||
				angle -TRACE_DRIFT_BORNES_BOT <= 0 || angle + TRACE_DRIFT_BORNES_BOT >= 180 )
			return false;
		return true;
	}

	/**
	 * Check if a list of Vec2 is correctly set, with the angle calcul (Point by point)
	 */
	@Override
	public boolean check(List<Vec2> trace){
		if(!trace.isEmpty()){
			angle = Filters.getAngle(trace);
			if(Filters.isAffine(trace) && checkAngle(angle))
				return true;
			return false;
		}
		return false;
	}
	
	
	/**
	 * Move the ship in the good direction, depending on the angle of drift
	 */
	@Override
	public void apply(Player ship) {
		int vitX;
		if(angle < 90)
			vitX = Variables.SPEED_MAIN_SHIP;
		else
			vitX = -Variables.SPEED_MAIN_SHIP;
		ship.setVelocity(vitX, Variables.SPEED_MAIN_SHIP);		
	}
}
