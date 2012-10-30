package entities.ships.enemies;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.Random;

import javax.imageio.ImageIO;

import org.jbox2d.dynamics.Body;

import entities.ships.Ship;


public class Enemy extends Ship{

	BufferedImage image;
	
	double lastExecution=0;
	Random rand = new Random();
	
	public Enemy() throws IOException {
		super();
		
		String imageURL = "images/Ships/dirtyDick.png";
		
		try {                
			image = ImageIO.read(new File(imageURL));
		} catch (IOException ex) {
			throw new IOException("Ship initialisation fail: can't open " + imageURL);
		}		
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
		return image;
	}
}
