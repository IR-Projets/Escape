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

import fr.umlv.zen2.Application;
import fr.umlv.zen2.MotionEvent;
import game.EnvironnementFactory.Level;

public class Game implements GameStateListener{

	Environnement environnement;

	/*
	 * For the double buffered method
	 */
	private BufferedImage offscreen;
	private Graphics2D bufferGraphics; 


	private final int fps_refreshRate = 20;
	private double fps;
	private double time = 0;
	private int ite = 0;
	private long next_game_tick;
	private boolean paused;

	private Level level;
	/*
	 * TODO: C'est ici que va ï¿½tre gï¿½rer tout les evenements du jeux (mort, gagnï¿½, ...)
	 */

	public Game() throws IOException{		
		this.level = Level.Jupiter;
		environnement = EnvironnementFactory.factory(level);
		environnement.addListener(this);
		//offscreen = new BufferedImage(Variables.SCREEN_WIDTH, Variables.SCREEN_HEIGHT, BufferedImage.TYPE_INT_ARGB);
		offscreen = GraphicsEnvironment.getLocalGraphicsEnvironment().getDefaultScreenDevice().getDefaultConfiguration().createCompatibleImage(Variables.SCREEN_WIDTH, Variables.SCREEN_HEIGHT, Transparency.OPAQUE);
		bufferGraphics = offscreen.createGraphics();
		paused=false;
	}

	@Override
	public void stateChanged(GameState state) {
		switch(state){
		case Paused:
			paused = paused ? false : true;
			break;
		case Loose:
			System.out.println("Perdu !!");
			System.exit(0);
			break;
		case Win:
			//On lance le niveau suivant
			switch(level){
			case Jupiter:
				level = Level.Moon;
				break;
			case Moon:
				level = Level.Earth;
				break;
			case Earth:
				System.out.println("Jeu fini!! pas encore implémenté");
				break;
			}			
			environnement = EnvironnementFactory.factory(level);
			environnement.addListener(this);
			break;
		}

	}


	public void init(Graphics2D graphics) {
		next_game_tick = System.currentTimeMillis();
	}


	public void run(Graphics2D graphics) {				
		bufferGraphics.clearRect(0,0,Variables.SCREEN_WIDTH, Variables.SCREEN_HEIGHT); 
		bufferGraphics.setBackground(new Color(0));

		if(!paused){
			int loops = 0;
			long currentTime = System.currentTimeMillis();
			while(currentTime > next_game_tick && loops < Variables.MAX_FRAMESKIP) {
				environnement.compute();
				next_game_tick += Variables.SKIP_TICKS;
				loops++;
			}
		}

		//float interpolation = (float) (currentTime + Variables.SKIP_TICKS - next_game_tick) / (float)Variables.SKIP_TICKS;
		environnement.render(bufferGraphics);

		if(Variables.DEBUG){
			drawFPS(bufferGraphics);
		}

		graphics.drawImage(offscreen, 0, 0, null);
	}



	public void drawFPS(Graphics2D graphics){
		ite++;
		if(ite>fps_refreshRate){	//1er image: On récupère le temps
			ite=0;
			time = System.nanoTime();
		}
		else if(ite==1){			//2e image: On calcul le temps écoulé depuis la 1er image
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
		environnement.event(event);
	}



	long now;
	public void beginTime(){
		now = System.currentTimeMillis();
	}
	public void endTime(String description){
		System.out.println(description + ": " + (System.currentTimeMillis() - now + "ms"));
	}
}
