package game;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.io.IOException;



import entities.maps.Map;
import entities.ships.Ship;
import fr.umlv.zen2.Application;
import fr.umlv.zen2.MotionEvent;
import gestures.Gesture;

public class Game {

	Environnement env;

	/*
	 * For the double buffered method
	 */
	private BufferedImage offscreen;
	private Graphics2D bufferGraphics; 
	
	
	int fps;
	private double time = 0;
	
	
	
	
	
	/*
	 * TODO: C'est ici que va �tre g�rer tout les evenements du jeux (mort, gagn�, ...)
	 */
	
	public Game() throws IOException{		
		env = EnvironnementFactory.factory();
		offscreen = new BufferedImage(Variables.SCREEN_WIDTH, Variables.SCREEN_HEIGHT, BufferedImage.TYPE_INT_ARGB);
		bufferGraphics = offscreen.createGraphics();
	}
	
	public void init(Graphics2D graphics) {
	}
	

	public void run(Graphics2D graphics) {		
		bufferGraphics.clearRect(0,0,Variables.SCREEN_WIDTH, Variables.SCREEN_HEIGHT); 
		bufferGraphics.setBackground(new Color(0));
		
		
		env.step();
		env.compute();		
		
		if(Variables.DEBUG){
			drawFPS(bufferGraphics);
		}
		
		env.render(bufferGraphics);		
		
		graphics.drawImage(offscreen, 0, 0, null);
	}

	
	
	public void drawFPS(Graphics2D graphics){
		double now = System.nanoTime();
		double fps = 1000000000 / (now-time);
		time = now;
		graphics.setColor(Variables.RED);
		graphics.scale(2, 2);
		graphics.drawString("fps: " + (int)fps, 10, 10);
		graphics.scale(0.5, 0.5);		
	}

	public void event(MotionEvent event) {
		env.event(event);
	}

}
