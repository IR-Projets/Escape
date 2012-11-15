package effects;

import game.Ressources;
import game.Variables;

import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.util.LinkedList;
import java.util.List;

public class Story2 {

	private abstract class Couple{
		int time;
		public abstract void render(Graphics2D graphics);
	}
	List<Couple> sequence;
	
	
	//Images
	private BufferedImage[] Xaroff;
	private BufferedImage[] Sirud;
	//Position de depart des textes
	private int textSirudX = 50;
	private int textXaroffX = Variables.SCREEN_WIDTH-textSirudX-150;
	
	
	
	
	
	
	public Story2(){
		sequence = new LinkedList<>();
		
		//imgSirud=0;
		//imgXaroff=0;
		Xaroff = new BufferedImage[2];
		Sirud = new BufferedImage[2];
		Xaroff[0] = Ressources.getImage("story/Xaroff1.png");
		Xaroff[1] = Ressources.getImage("story/Xaroff2.png");
		Sirud[0] = Ressources.getImage("story/Sirud1.png");
		Sirud[1] = Ressources.getImage("story/Sirud2.png");
		
	}
	
	
	
	
	private void XaroffSpeak(Graphics2D graphics, String string){
		//drawText(graphics, string, textXaroffX, textY);
		//drawXaroff(graphics, true);
	}

	private void SirudSpeak(Graphics2D graphics, String string){
		//drawText(graphics, string, textSirudX, textY);
		//drawSirud(graphics, true);
	}
	
	
}
