package game;

import java.awt.Graphics2D;
import java.io.IOException;



import entities.maps.Map;
import entities.ships.Ship;
import fr.umlv.zen2.Application;
import fr.umlv.zen2.MotionEvent;
import gestures.Gesture;

public class Game {

	Environnement env;
	int fps;
	private double time = 0;
	
	/*
	 * TODO: C'est ici que va �tre g�rer tout les evenements du jeux (mort, gagn�, ...)
	 */
	
	public Game() throws IOException{		
		env = EnvironnementFactory.factory();
	}
	
	public void init(Graphics2D graphics) {
	}
	

	public void run(Graphics2D graphics) {
		double now = System.nanoTime();
		double fps = 1000000000 / (now-time);
		time = now;
			
		env.step();
		env.compute();
		
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
