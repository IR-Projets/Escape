package entities.weapons;

import hud.Item;

import java.util.Random;

import entities.Entities;
import entities.Entity;
import entities.weapons.Weapon.SourceWeapon;


public class WeaponFactory {

	public enum WeaponType{
		Ball,
		Fireball, 
		Shiboleet, 
		Missile,
		Null;
		public static WeaponType convert(String weaponName){
			switch(weaponName){
			case "Ball": return Ball;
			case "Fireball": return Fireball;
			case "Shiboleet": return Shiboleet;
			case "Missile": return Missile;
			default: return null;
			}
	}
	};
	
	public static Weapon createWeapon(Entities entities, WeaponType type, int x, int y, SourceWeapon sourceWeapon) {
		switch(type){
		case Ball:
			return new Ball(entities, x, y, sourceWeapon);
		case Fireball:
			return new Fireball(entities, x, y, sourceWeapon);
		case Missile:
			return new Ball(entities, x, y, sourceWeapon);
		case Shiboleet:
			return new Ball(entities, x, y, sourceWeapon);
		case Null:
			return null;
		default:
			break;
			
			
		}
		return null;		
	}	
}
