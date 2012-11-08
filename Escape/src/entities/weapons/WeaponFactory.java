package entities.weapons;

import entities.Entities;
import entities.EntityFactory;


public class WeaponFactory extends EntityFactory{

	public WeaponFactory(Entities entities) {
		super(entities);
	}

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
	
	public Weapon createWeapon(WeaponType type, int x, int y, boolean damagedPlayer) {
		Weapon weapon=null;
		switch(type){
		case Ball:
			weapon = new Ball(getEntities(), x, y, damagedPlayer);
			break;
		case Fireball:
			weapon = new Fireball(getEntities(), x, y, damagedPlayer);
			break;
		case Missile:
			weapon =  new Ball(getEntities(), x, y, damagedPlayer);
			break;
		case Shiboleet:
			weapon =  new Shiboleet(getEntities(), x, y, damagedPlayer);
			break;
		}
		if(weapon!=null)
			getEntities().addEntity(weapon);
		return weapon;		
	}	
}
