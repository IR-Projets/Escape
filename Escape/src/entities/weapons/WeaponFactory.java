package entities.weapons;

import hud.Item;

import java.util.Random;

import entities.Entities;
import entities.Entity;

public class WeaponFactory {

	public enum WeaponType{
		Ball,
		Fireball, 
		Shiboleet, 
		Missile,
		Null
	}
	
	public static Weapon createWeapon(Entities entities, WeaponType type, int x, int y) {
		switch(type){
		case Ball:
			return new Ball(entities, x, y);
		case Fireball:
			return new Fireball(entities, x, y);
		case Missile:
			return new Ball(entities, x, y);
		case Shiboleet:
			return new Ball(entities, x, y);
		case Null:
			return null;
		default:
			break;
			
			
		}
		return null;		
	}	
}
