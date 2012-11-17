package entities.weapons;

import entities.Entities;
import factories.WeaponFactory.WeaponType;

/**
 * This class is used for load an XML files into an EnemyDef class.
 * This is a XML Parser, adapted to our class
 * 
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

public class Fireball extends Weapon {

	private long time;
	private int increase;
	
	public Fireball(Entities entities, int x, int y, boolean firedByPlayer) {
		super(entities, EntityShape.Circle, WeaponType.Fireball.getImage(), x, y, 8,  firedByPlayer);
		time = System.nanoTime()/1000000;
		increase=0;
	}

	
	@Override
	public void compute() {
		long diffTime = (System.nanoTime()/1000000 - time);
		if(isLaunch())
			return;
		if(diffTime > 300 && increase <3){
			setImage(Weapon.resize(getImage(), 1.5f));
			increase++;
			setDamage(getDamage()*2);
			time=System.nanoTime()/1000000;
		}
	}
}
