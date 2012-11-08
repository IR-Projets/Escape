package entities.weapons;

import java.awt.image.BufferedImage;

import org.jbox2d.dynamics.Body;

import entities.Entities;
import entities.Entity;
import entities.weapons.WeaponFactory.WeaponType;
import game.Ressources;

public class Ball extends Weapon{

	
	public Ball(Entities entities, int x, int y, boolean firedByPlayer) {
		super(entities, EntityShape.Circle, Ressources.getImage("images/weapons/shiboleet.png"), x, y, firedByPlayer,2, WeaponType.Ball);
	}

	@Override
	public void compute() {
		// TODO Auto-generated method stub
		
	}
	
}
