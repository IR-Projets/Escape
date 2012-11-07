package game;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.GraphicsConfiguration;
import java.awt.GraphicsDevice;
import java.awt.GraphicsEnvironment;
import java.awt.Transparency;
import java.awt.image.BufferedImage;
import java.awt.image.ComponentSampleModel;
import java.awt.image.DataBuffer;
import java.awt.image.DataBufferByte;
import java.awt.image.Raster;
import java.io.ByteArrayOutputStream;
import java.io.IOException;

import javax.imageio.ImageIO;

import fr.umlv.zen2.MotionEvent;

public class Game {

	Environnement env;

	/*
	 * For the double buffered method
	 */
	private BufferedImage offscreen;
	private Graphics2D bufferGraphics; 
	
	
	private final int fps_refreshRate = 20;
	private double fps;
	private double time = 0;
	private int ite = 0;

	private long next_game_tick = System.currentTimeMillis();
	
	
	
	/*
	 * TODO: C'est ici que va �tre g�rer tout les evenements du jeux (mort, gagn�, ...)
	 */
	
	public Game() throws IOException{		
		env = EnvironnementFactory.factory();
		//offscreen = new BufferedImage(Variables.SCREEN_WIDTH, Variables.SCREEN_HEIGHT, BufferedImage.TYPE_INT_ARGB);
		offscreen = GraphicsEnvironment.getLocalGraphicsEnvironment().getDefaultScreenDevice().getDefaultConfiguration().createCompatibleImage(Variables.SCREEN_WIDTH, Variables.SCREEN_HEIGHT, Transparency.OPAQUE);
		bufferGraphics = offscreen.createGraphics();
	}
	
	public void init(Graphics2D graphics) {
	}
	

	public void run(Graphics2D graphics) {				
		bufferGraphics.clearRect(0,0,Variables.SCREEN_WIDTH, Variables.SCREEN_HEIGHT); 
		bufferGraphics.setBackground(new Color(0));

		int loops = 0;
		long currentTime = System.currentTimeMillis();

		while(currentTime > next_game_tick && loops < Variables.MAX_FRAMESKIP) {
			env.compute();

			next_game_tick += Variables.SKIP_TICKS;
			loops++;
		}

		float interpolation = (float) (currentTime + Variables.SKIP_TICKS - next_game_tick) / (float)Variables.SKIP_TICKS;

		//long now = System.currentTimeMillis();
		env.render(bufferGraphics, interpolation);
		//long time = System.currentTimeMillis() - now;
		//System.out.println(time);
		
				
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
