package entities.ships.enemies;

import java.awt.image.BufferedImage;
import java.util.Random;

import org.jbox2d.dynamics.World;

import entities.ships.Ship;


public class Enemy extends Ship{

	double lastExecution=0;
	Random rand = new Random();

	public Enemy(World world, float x, float y, int life){	
		super(world, getRandomNameImage(), x, y, life);
	}

	private static String getRandomNameImage(){
		Random rand = new Random();
		String imageURL;
		if(rand.nextInt()%2==0)
			imageURL = "images/Ships/ship.png";
		else
			imageURL = "images/Ships/dirtyDick.png";
		return imageURL;
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
	public BufferedImage getImageRender() {
		return getImage();
	}

}
