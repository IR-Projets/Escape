package hud;

/**
 * This interface is a Listener, for impact the changment of the life from the player to the HUD, and display it
 *
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

public interface LifeListener {
	/**
	 * The main methode of the listener : send the difference between the old life and the new life, for does some action
	 * @param oldLife
	 * @param newLife
	 */
	void lifeChanged(int oldLife, int newLife);
}
