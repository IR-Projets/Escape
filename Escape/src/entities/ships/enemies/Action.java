package entities.ships.enemies;

/**
 * the Action class is used to determined what kind of action that an Enemy can do. 
 * In our case, we have only Two actions possible, Shoot and Move.
 * Moreover this class keep the angle, velocity and the step of begin and end of action and eventually the name for recognize a weapon.
 *  * @author Quentin Bernard et Ludovic Feltz
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

public class Action {
	/**
	 * Enum type, for knows what kind of actions we have
	 * @author kiouby
	 *
	 */
	public enum ActionType{
		Fire,
		Move
	};

	private int beg;
	private int end;
	private int velocity;
	private double angle;
	private ActionType type;
	private String name;

	/**
	 * Construct an Action, with parameter initialize with wrong value 
	 * (begin and end time to -1)
	 */
	public Action(){
		setBeg(-1);
		setEnd(-1);
		setVelocity(Integer.MAX_VALUE);
		setAngle(Integer.MAX_VALUE);
		setType(null);
		setName(null);
	}

	public int getBeg() {
		return beg;
	}

	public void setBeg(int beg) {
		this.beg = beg;
	}

	public int getEnd() {
		return end;
	}

	public void setEnd(int end) {
		this.end = end;
	}

	public int getVelocity() {
		return velocity;
	}

	public void setVelocity(int velocity) {
		this.velocity = velocity;
	}

	public double getAngle() {
		return angle;
	}

	public void setAngle(double angle) {
		this.angle = angle;
	}

	public ActionType getType() {
		return type;
	}

	public void setType(ActionType type) {
		this.type = type;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
}