package game;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.io.IOException;

import fr.umlv.zen2.MotionEvent;

public class Game {

	Environnement env;

	/*
	 * For the double buffered method
	 */
	private BufferedImage offscreen;
	private Graphics2D bufferGraphics; 
	
	
	private final int fps_refreshRate = 10;
	private double fps;
	private double time = 0;
	private int ite = 0;
	
	
	
	
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
	
				
		env.render(bufferGraphics);		
		env.compute();
		
		if(Variables.DEBUG){
			drawFPS(bufferGraphics);
		}
		
		graphics.drawImage(offscreen, 0, 0, null);
	}

	
	
	public void drawFPS(Graphics2D graphics){
		ite++;
		if(ite>fps_refreshRate){	//1er image: On r�cup�re le temps
			ite=0;
			time = System.nanoTime();
		}
		else if(ite==1){			//2e image: On calcul le temps �coul� depuis la 1er image
			double now = System.nanoTime();
			fps = 1000000000 / (now-time);
			time = now;
		}	
		
		graphics.setColor(Variables.RED);
		graphics.scale(2, 2);
		graphics.drawString("fps: " + (int)fps, 10, 10);
		graphics.scale(0.5, 0.5);	
	}

	public void event(MotionEvent event) {
		env.event(event);
	}

}
