package gestures.filters;

import java.util.List;

import org.jbox2d.common.Vec2;

import entities.ships.Player;
import game.Variables;
/**
 * The Backoff is a back movement, which accept a trace with gradient between 260 and 280 degrees
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


public class Backoff implements Filter {
	
	/**
	 * The vertical bound tilt angle that the drift refuse (180 +- this variable)
	 */
	public static final int TRACE_DRIFT_BORNES_BOT = 10;/* We refuse affine courbe which increase perpendiculary, with this bornes*/
	
	/**
	 * Check if the angle of the Right is correct for backoff
	 * @param angle
	 * @return true if the angle is correct
	 */
	private boolean checkAngle(double angle){
		//System.out.println(angle);
		if((angle >= 270-TRACE_DRIFT_BORNES_BOT && angle <= 270+TRACE_DRIFT_BORNES_BOT ))
			return true;
		return false;
	}
	
	/**
	 * Check if a list of Vec2 is correctly set, with the angle calcul (Point by point)
	 */
	@Override
	public boolean check(List<Vec2> trace){
		if(!trace.isEmpty()){
			double angle = Filters.getAngle(trace);
			if(Filters.isAffine(trace) && checkAngle(angle))
				return true;
			return false;
		}
		return false;
	}

	/**
	 * Move the ship in the good direction, the bottom
	 */
	@Override
	public void apply(Player ship) {
		ship.setVelocity(0, -Variables.SPEED_MAIN_SHIP);
		//System.out.println("Backup OK");
		
	}
	
}
