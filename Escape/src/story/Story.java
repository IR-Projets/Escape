package story;

import game.Ressources;
import game.Variables;

import java.awt.Graphics2D;
import java.awt.GraphicsEnvironment;
import java.awt.Transparency;
import java.awt.geom.AffineTransform;
import java.awt.geom.Point2D;
import java.awt.image.BufferedImage;

public class Story {


	private static final int TEXT_SCALE = 10;
	private int textY = 200;

	private int textSirudX = 50;
	private int textXaroffX = Variables.SCREEN_WIDTH-textSirudX-150;

	private int TICK_SKIP=50;
	private int clock=0;
	private int totalClock=0;

	private int imagesY = 50;
	private int imagesSize = 100;

	private BufferedImage[] Xaroff;
	private BufferedImage[] Sirud;

	private int imgXaroff;	
	private int imgSirud;

	private int SirudX=50;
	private int XaroffX=Variables.SCREEN_WIDTH-SirudX-imagesSize;


	private boolean finished = false;

	private BufferedImage offscreen;
	private Graphics2D bufferGraphics;
	private long next_game_tick;

	
	public Story(){
		imgSirud=0;
		imgXaroff=0;
		Xaroff = new BufferedImage[2];
		Sirud = new BufferedImage[2];
		Xaroff[0] = Ressources.getImage("story/Xaroff1.png");
		Xaroff[1] = Ressources.getImage("story/Xaroff2.png");
		Sirud[0] = Ressources.getImage("story/Sirud1.png");
		Sirud[1] = Ressources.getImage("story/Sirud2.png");

		offscreen = new BufferedImage(Variables.SCREEN_WIDTH, Variables.SCREEN_HEIGHT, Transparency.OPAQUE);
		bufferGraphics = offscreen.createGraphics();
		next_game_tick = System.currentTimeMillis();
	}


	private void XaroffSpeak(Graphics2D graphics, String string){
		drawText(graphics, string, textXaroffX, textY);
		drawXaroff(graphics, true);
	}

	private void SirudSpeak(Graphics2D graphics, String string){
		drawText(graphics, string, textSirudX, textY);
		drawSirud(graphics, true);
	}

	private void drawText(Graphics2D graphics, String string, int x, int y){
		String [] lines = string.split("\n");
		for(int i=0; i<lines.length; i++){
			graphics.drawString(lines[i], x, y+i*(TEXT_SCALE+10));
		}	
	}

	private void drawXaroff(Graphics2D graphics, boolean speak){
		graphics.drawImage(Xaroff[imgXaroff], XaroffX, imagesY, imagesSize, imagesSize, null);
		if(speak && clock>TICK_SKIP){
			clock=0;
			imgXaroff = (imgXaroff+1) % Xaroff.length;
		}
	}	
	private void drawSirud(Graphics2D graphics, boolean speak){
		graphics.drawImage(Sirud[imgSirud], SirudX, imagesY, imagesSize, imagesSize, null);
		if(speak && clock>TICK_SKIP){
			clock=0;
			imgSirud = (imgSirud+1) % Sirud.length;
		}
	}


	public void render(Graphics2D graphics){
		totalClock++;
		clock++;

		long currentTime = System.currentTimeMillis();
		if(currentTime>next_game_tick){
			next_game_tick += 1;
			
			bufferGraphics.clearRect(0,0,Variables.SCREEN_WIDTH, Variables.SCREEN_HEIGHT); 
			bufferGraphics.setColor(Variables.WHITE);
			if(totalClock<250){
				SirudSpeak(bufferGraphics, "Ou est tu encore passe?\n");
			}
			else if(totalClock<500){
				SirudSpeak(bufferGraphics, "Ta mission suicide sur la planete\nalien c'est bien passee?");
			}
			else if(totalClock<800){
				XaroffSpeak(bufferGraphics, "HaHaHA je l'ai capture!");
				drawSirud(bufferGraphics, false);
			}
			else if(totalClock<1100){
				SirudSpeak(bufferGraphics, "Relache le!");
				drawXaroff(bufferGraphics, false);
			}
			else if(totalClock<1400){
				XaroffSpeak(bufferGraphics, "Non!");
				drawSirud(bufferGraphics, false);
			}
			else if(totalClock<2000){
				SirudSpeak(bufferGraphics, "J'ai bien peur que tu doive te debrouiller tout seul...\nTrouve un vaisseau et enfui toi...");
			}
			else if(totalClock<3300){
				drawText(bufferGraphics, "Les personnages et les situations de ce recit etant moyennement fictifs,\ntoute ressemblance avec des personnes ou des situations existantes\nou ayant existe sont tout sauf fortuite.", 10, 50);
			}
			else{
				finished=true;
			}
		}
		graphics.drawImage(offscreen, 0, 0, null);
	}

	public boolean isFinished(){
		return finished;		
	}

}
