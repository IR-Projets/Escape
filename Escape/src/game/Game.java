package game;

import java.awt.Graphics2D;
import java.io.IOException;
import Maps.Map;

import ships.Ship;
import worlds.Environnement;
import worlds.EnvironnementFactory;


import fr.umlv.zen2.Application;
import fr.umlv.zen2.MotionEvent;
import gestures.Gesture;

public class Game {

	Environnement env;
	int fps;
	private double time = 0;
	
	/*
	 * TODO: C'est ici que va être gérer tout les evenements du jeux (mort, gagné, ...)
	 */
	
	public Game() throws IOException{		
		env = EnvironnementFactory.factory();
	}
	
	public void init(Graphics2D graphics) {
		Runnable run = new Runnable(){
			@Override
			public void run() {
				for(;;){
					env.compute();
					try {
						Thread.sleep(10);
					} catch (InterruptedException e) {
						Thread.currentThread().interrupt();
					}
				}
			}
		};
		Thread thread = new Thread(run);
		thread.start();
	}
	

	public void run(Graphics2D graphics) {
		double now = System.nanoTime();
		double fps = 1000000000 / (now-time);
		time = now;
				
		if(Variables.DEBUG){
			graphics.setColor(Variables.RED);
			graphics.scale(2, 2);
			graphics.drawString("fps: " + (int)fps, 10, 10);
			graphics.scale(0.5, 0.5);
		}
		
		env.render(graphics);
	}


	public void event(MotionEvent event) {
		env.event(event);
	}

}
