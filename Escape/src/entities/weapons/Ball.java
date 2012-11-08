package entities.weapons;

import java.awt.image.BufferedImage;

import entities.Entities;
import entities.Entity;
import game.Ressources;

public class Ball extends Weapon{

	
	public Ball(Entities entities, int x, int y, boolean firedByPlayer) {
		super(entities, Ressources.getImage("images/weapons/shiboleet.png"), x, y, firedByPlayer);
	}

	@Override
	public void compute() {
		// TODO Auto-generated method stub
		
	}
	
}
