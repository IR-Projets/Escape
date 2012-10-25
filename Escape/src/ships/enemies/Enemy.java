package ships.enemies;

import java.io.IOException;
import java.util.Random;

import org.jbox2d.dynamics.Body;

import ships.Ship;

public class Enemy extends Ship{

	double lastExecution=0;
	Random rand = new Random();
	
	public Enemy() throws IOException {
		super();
	}

	@Override
	protected String getImageURL() {
		return "images/dirtyDick.png";
	}

	@Override
	public void compute() {
		double now = System.currentTimeMillis();
		if(now-lastExecution>1000+rand.nextInt(5000)){
			lastExecution=now;
			move(rand.nextInt(100)-50, rand.nextInt(100)-50);
		}
		
	}
}
