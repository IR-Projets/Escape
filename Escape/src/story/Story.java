package story;

import game.Ressources;
import game.Variables;

import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.util.LinkedList;
import java.util.List;

public class Story {

	/*
	 * Crée un couple temps/render
	 * Sert a l'affichage d'un narrateur
	 */
	public abstract class Couple{
		int time;
		public Couple(int time){ this.time=time; }
		public abstract void render(Graphics2D g);
	}
		

	private static final int TEXT_SCALE = 20;
	private long time, lastTime;
	
	
	
	//La sequence
	List<Couple> sequence;
	
	
	
	//Images
	private BufferedImage[] imgXaroff;
	private BufferedImage[] imgSirud;
	private BufferedImage[] imgHero;
	final Narator sirud;
	final Narator xaroff;
	final Narator hero;


	private boolean finished;
	
	
	
	
	
	public Story(){	
		finished=false;
		time = 0;
		lastTime=0;
		
		/*
		 * Narrator's images 
		 */
		imgXaroff = new BufferedImage[2];
		imgXaroff[0] = Ressources.getImage("story/Xaroff1.png");
		imgXaroff[1] = Ressources.getImage("story/Xaroff2.png");
		
		imgSirud = new BufferedImage[2];
		imgSirud[0] = Ressources.getImage("story/Sirud1.png");
		imgSirud[1] = Ressources.getImage("story/Sirud2.png");
		
		imgHero = new BufferedImage[1];
		imgHero[0] = Ressources.getImage("story/hero.png");
		
		
		/*
		 * Narators( image, posX, posY )
		 */
		sirud = new Narator(imgSirud, 50, 50);
		xaroff = new Narator(imgXaroff, Variables.SCREEN_WIDTH-150, 50);
		hero = new Narator(imgHero, Variables.SCREEN_WIDTH/2-50, 270);
		
		
		/*
		 *	The sequence 
		 */
		sequence = new LinkedList<>();
		sequence.add(new Couple(1){
			@Override
			public void render(Graphics2D g) {
				sirud.speak(g, "Ou est tu encore passé?\n");				
			}			
		});
		sequence.add(new Couple(1){
			@Override
			public void render(Graphics2D g) {
				sirud.speak(g, "Ta mission suicide sur la planète\nalien c'est bien passée?");
				hero.speak(g, "...");				
			}			
		});
		sequence.add(new Couple(1){
			@Override
			public void render(Graphics2D g) {
				xaroff.speak(g, "HaHaHA je l'ai capturé!\n");
				sirud.draw(g);				
			}			
		});
		sequence.add(new Couple(1){
			@Override
			public void render(Graphics2D g) {
				sirud.speak(g, "Relache le!");
				xaroff.draw(g);				
			}			
		});
		sequence.add(new Couple(1){
			@Override
			public void render(Graphics2D g) {
				xaroff.speak(g, "Non!");
				sirud.draw(g);				
			}			
		});
		sequence.add(new Couple(2){
			@Override
			public void render(Graphics2D g) {
				sirud.speak(g, "J'ai bien peur que tu doive te débrouiller tout seul...\nTrouve un vaisseau et enfui toi...");
				//xaroff.draw(g);				
			}			
		});
		sequence.add(new Couple(10){
			float posY=0;
			@Override
			public void render(Graphics2D g) {
				drawText(g, "Les personnages et les situations de ce récit étant moyennement fictifs,\ntoute ressemblance avec des personnes ou des situations existantes\nou ayant existé sont tout sauf fortuite.", 10, posY+=0.1f);			
			}			
		});
	}
	
	
	private void drawText(Graphics2D graphics, String text, float x, float y){
		String [] lines = text.split("\n");
		for(int i=0; i<lines.length; i++){
			graphics.drawString(lines[i], x, y+i*(TEXT_SCALE+10));
		}	
	}
	
	public void render(Graphics2D graphics){		
		if(sequence.size()==0){
			finish();
			return;
		}
		
		Couple couple = sequence.get(0);
		couple.render(graphics);
		
		if(lastTime==0)
			lastTime=System.nanoTime();
		time=System.nanoTime();
		if(couple.time<(time-lastTime)/1000000000){
			lastTime = time;
			sequence.remove(0);
		}
	}
	
	public boolean isFinished(){
		return finished;
	}


	public void finish() {
		finished=true;
	}
	
	
	
	
}
