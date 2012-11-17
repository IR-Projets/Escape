package factories;

import java.awt.image.BufferedImage;

import entities.Entities;
import entities.weapons.Fireball;
import entities.weapons.Missile;
import entities.weapons.Shiboleet;
import entities.weapons.ShiboleetExtended;
import entities.weapons.Shuriken;
import entities.weapons.Weapon;
import game.Ressources;

/**
 * This class is a factory of weapon, and make all weapons of ships.
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

public class WeaponFactory extends EntityFactory{

	/**
	 * 
	 * @param entities
	 */
	public WeaponFactory(Entities entities) {
		super(entities);
	}

	/**
	 * 
	 */
	public enum WeaponType{
		Shuriken,
		Fireball, 
		Shiboleet, 
		ShiboleetExtended, 
		Missile,
		Null;
		/**
		 * 
		 * @param weaponName
		 * @return
		 */
		public static WeaponType convert(String weaponName){
			switch(weaponName){
			case "Shuriken": return Shuriken;
			case "Fireball": return Fireball;
			case "Shiboleet": return Shiboleet;
			case "ShiboleetExtended": return ShiboleetExtended;
			case "Missile": return Missile;
			default: return null;
			}
		}
			
		/**
		 * 
		 * @return
		 */
		public BufferedImage getImage(){
			switch(this){
			case Shuriken: return Ressources.getImage("weapons/shuriken.png");
			case Fireball: return Ressources.getImage("weapons/fire.png");
			case Shiboleet: 
			case ShiboleetExtended: return Ressources.getImage("weapons/shiboleet.png");
			case Missile: return Ressources.getImage("weapons/missile.png");
			case Null : return Ressources.getImage("hud/error.png");
			default: return null;
			}
		}
		
		@Override
		public String toString(){
			switch(this){
			case Shuriken: return "Shuriken";
			case Fireball: return "Fireball";
			case Shiboleet: 
			case ShiboleetExtended: return "Shiboleet";
			case Missile: return "Missile";
			case Null : return "No weapon";
			default: return null;
			}
		}
		
	};
	
	/**
	 * 
	 * @param type
	 * @param x
	 * @param y
	 * @param damagedPlayer
	 * @return
	 */
	public Weapon createWeapon(WeaponType type, int x, int y, boolean damagedPlayer) {
		Weapon weapon = null;
		
		switch(type){
		case Shuriken:
			weapon =new Shuriken(getEntities(), x, y, damagedPlayer);
			break;
		case Fireball:
			weapon = new Fireball(getEntities(), x, y, damagedPlayer);
			break;
		case Missile:
			weapon = new Missile(getEntities(), x, y, damagedPlayer);
			break;
		case Shiboleet:
			weapon = new Shiboleet(getEntities(), x, y, damagedPlayer);
			break;
		case ShiboleetExtended:
			weapon = new ShiboleetExtended(getEntities(), x, y, damagedPlayer,5);
			break;
		default:
			break;
		}
		getEntities().addEntity(weapon);
		return weapon;		
	}	
}
