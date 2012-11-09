package entities.weapons;

import entities.Entities;
import entities.EntityFactory;


public class WeaponFactory extends EntityFactory{

	public WeaponFactory(Entities entities) {
		super(entities);
	}

	public enum WeaponType{
		Shuriken,
		Fireball, 
		Shiboleet, 
		Missile,
		Null;
		public static WeaponType convert(String weaponName){
			switch(weaponName){
			case "Shuriken": return Shuriken;
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
		case Shuriken:
			weapon = new Shuriken(getEntities(), x, y, damagedPlayer);
			break;
		case Fireball:
			weapon = new Fireball(getEntities(), x, y, damagedPlayer);
			break;
		case Missile:
			weapon =  new Missile(getEntities(), x, y, damagedPlayer);
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
