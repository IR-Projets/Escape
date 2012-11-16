package entities.ships.enemies;

import java.awt.image.BufferedImage;

import entities.Entities;
import entities.Entity;

/**
 * The Boss class is used for specify an enemy as a Boss, which is uses for determine the end of a level, with his death.
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

public class Boss extends Enemy{

	/**
	 * Create a boss, which is an extension of an enemy. Be care, A boss enemy is add in the Jbox World by his factory. 
	 * @param entities - class which represents our world
	 * @param image - the image of the boss
	 * @param x - the coordinate associated with x position
	 * @param y - the coordinate associated with y position
	 * @param life - the life of the Boss
	 * @param behavior - the EnemyBehavior of a Boss, which specify his behavior during the game
	 */
	public Boss(Entities entities, BufferedImage image, int x, int y, int life, EnemyBehavior behavior) {
		super(entities, EntityShape.Square, image, x, y, life, behavior);
		setCollisionGroup(EntityType.Boss);
		setVelocity(0, -40);
	}
	
	/**
	 * Return an entityType view as a Boss
	 */
	@Override
	public EntityType getEntityType() {
		return EntityType.Boss;
	}

}
