package factories;

import java.awt.image.BufferedImage;

import entities.Entities;
import entities.weapons.Fireball;
import entities.weapons.Missile;
import entities.weapons.Shiboleet;
import entities.weapons.ShiboleetExtension;
import entities.weapons.Shuriken;
import entities.weapons.Weapon;
import game.Ressources;


public class WeaponFactory extends EntityFactory{

	
	public WeaponFactory(Entities entities) {
		super(entities);
	}

	public enum WeaponType{
		Shuriken,
		Fireball, 
		Shiboleet, 
		ShiboleetExtended, 
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
	};
	
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
			weapon = new ShiboleetExtension(getEntities(), x, y, damagedPlayer,3);
			break;
		default:
			break;
		}
		getEntities().addEntity(weapon);
		return weapon;		
	}	
}
