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

import story.Story;
import story.Story2;

import listeners.EnvironnementListener;

import factories.EnvironnementFactory;
import factories.EnvironnementFactory.Level;
import fr.umlv.zen2.Application;
import fr.umlv.zen2.MotionEvent;

public class Game implements EnvironnementListener{

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

	private Story2 story;
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
		story = new Story2();
		paused=false;
		next_game_tick = -1;
	}

	@Override
	public void stateChanged(GameState state) {
		switch(state){
		case Paused:
			paused = paused ? false : true;
			break;
		case Loose:
			System.out.println("Loose !!");
			System.exit(0);
			break;
		case Win:
			System.out.println("Win!!");
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
			environnement.removeListener(this);
			environnement = EnvironnementFactory.factory(level);
			environnement.addListener(this);
			break;
		}

	}


	public void init(Graphics2D graphics) {
		
	}


	public void run(Graphics2D graphics) {				
		bufferGraphics.clearRect(0,0,Variables.SCREEN_WIDTH, Variables.SCREEN_HEIGHT); 
		bufferGraphics.setBackground(new Color(0));

		if(!story.isFinished()){
			story.render(bufferGraphics);
		}
		else{
			if(!paused){
				if(next_game_tick==-1)
					next_game_tick = System.currentTimeMillis();
				
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
		}
		

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
		if(!story.isFinished())
			story.finish();
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
