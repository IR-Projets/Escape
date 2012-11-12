package factories;

import java.util.LinkedList;
import java.util.List;

import entities.Entities;
import entities.weapons.Fireball;
import entities.weapons.Missile;
import entities.weapons.Shiboleet;
import entities.weapons.Shuriken;
import entities.weapons.Weapon;


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
	
	public List<Weapon> createWeapon(WeaponType type, int x, int y, boolean damagedPlayer) {
		List<Weapon> listWeapons = new LinkedList<>();
		
		switch(type){
		case Shuriken:
			listWeapons.add(new Shuriken(getEntities(), x, y, damagedPlayer));
			break;
		case Fireball:
			listWeapons.add(new Fireball(getEntities(), x, y, damagedPlayer));
			break;
		case Missile:
			listWeapons.add(new Missile(getEntities(), x, y, damagedPlayer));
			break;
		case Shiboleet:
			for(int i=0;i<3;i++)
			listWeapons.add(new Shiboleet(getEntities(), x, y, damagedPlayer));
			break;
		default:
			break;
		}
		
		for(Weapon weapon : listWeapons)
			getEntities().addEntity(weapon);
		return listWeapons;		
	}	
}
