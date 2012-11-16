package story;

import game.Ressources;
import game.Variables;

import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.util.LinkedList;
import java.util.List;

public class Story2 {

	/*
	 * Cree un couple temps/render
	 * Sert a l'affichage d'un narrateur
	 */
	public abstract class Couple{
		int time;
		public Couple(int time){ this.time=time; }
		public abstract void render(Graphics2D g);
	}
		

	private static final int TEXT_SCALE = 20;
	private static long TIME_SKIP = 1000;
	private long time, lastTime;
	
	
	
	//La sequence
	List<Couple> sequence;
	
	
	
	//Images
	private BufferedImage[] Xaroff;
	private BufferedImage[] Sirud;
	final Narator sirud;
	final Narator xaroff;


	private boolean finished;
	
	
	
	
	
	public Story2(){	
		finished=false;
		time = 0;
		lastTime=0;
		
		Xaroff = new BufferedImage[2];
		Xaroff[0] = Ressources.getImage("story/Xaroff1.png");
		Xaroff[1] = Ressources.getImage("story/Xaroff2.png");
		
		Sirud = new BufferedImage[2];
		Sirud[0] = Ressources.getImage("story/Sirud1.png");
		Sirud[1] = Ressources.getImage("story/Sirud2.png");
		
		sirud = new Narator(Sirud, 50, 50);
		xaroff = new Narator(Xaroff, Variables.SCREEN_WIDTH-150, 50);
		
		
		sequence = new LinkedList<>();
		sequence.add(new Couple(1){
			@Override
			public void render(Graphics2D g) {
				sirud.speak(g, "Ou est tu encore passe?\n");
				xaroff.draw(g);				
			}			
		});
		sequence.add(new Couple(1){
			@Override
			public void render(Graphics2D g) {
				sirud.speak(g, "Ta mission suicide sur la planete\nalien c'est bien passee?");
				xaroff.draw(g);				
			}			
		});
		sequence.add(new Couple(1){
			@Override
			public void render(Graphics2D g) {
				xaroff.speak(g, "HaHaHA je l'ai capture!\n");
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
				sirud.speak(g, "J'ai bien peur que tu doive te debrouiller tout seul...\nTrouve un vaisseau et enfui toi...");
				//xaroff.draw(g);				
			}			
		});
		sequence.add(new Couple(10){
			float posY=0;
			@Override
			public void render(Graphics2D g) {
				drawText(g, "Les personnages et les situations de ce recit etant moyennement fictifs,\ntoute ressemblance avec des personnes ou des situations existantes\nou ayant existe sont tout sauf fortuite.", 10, posY+=0.1f);			
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
