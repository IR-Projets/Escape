package entities.ships.enemies;

import java.awt.image.BufferedImage;
import java.util.Random;

import org.jbox2d.dynamics.World;

import entities.Entities;
import entities.ships.Ship;
import game.Ressources;


public class Enemy extends Ship{

	double lastExecution=0;
	Random rand = new Random();

	public Enemy(Entities entities, int x, int y, int life){	
		super(entities, getRandomNameImage(), x, y, life);
	}

	private static BufferedImage getRandomNameImage(){
		Random rand = new Random();
		if(rand.nextInt()%2==0)
			return Ressources.getImage("images/Ships/ship.png");
		else
			return Ressources.getImage("images/Ships/dirtyDick.png");
	}

	@Override
	public void compute() {
		double now = System.currentTimeMillis();
		if(now-lastExecution>1000+rand.nextInt(5000)){
			lastExecution=now;
			setVelocity(rand.nextInt(100)-50, rand.nextInt(100)-50);
		}
	}

	@Override
	public BufferedImage getImage() {
		return getImage();
	}

}
