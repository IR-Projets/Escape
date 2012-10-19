package gestures;

import java.awt.Color;
import java.awt.Graphics2D;
import java.util.LinkedList;
import java.util.List;
import java.util.Random;

import fr.umlv.zen2.MotionEvent;
import fr.umlv.zen2.MotionEvent.Kind;

public class Gesture {

	private Point position;
	private List<Point> trace;
	private boolean show;


	public Gesture(){
		this.trace = new LinkedList<>();
		show = false;
	}

	/*
	 * Séparer les fonction!
	 * la fonction render se charge de l'affichage uniquement (on peut afficher même quand on ne clique pas... effet de rémanence avec timer par exemple)
	 */
	public void render(Graphics2D graphics){

		if(show == false && !trace.isEmpty()){
			if(validGesture())
				graphics.setColor(new Color(0,255,0));
			else
				graphics.setColor(new Color(255,0,0));
			
			Random rand = new Random();
			if(rand.nextInt()%3 == 0)
				trace.remove(0);	
		}
		
		for(Point p : trace)
			graphics.fillOval(p.getX(), p.getY(), 10, 10);
	}

	/*
	 * Début de l'event 
	 */
	public void event(MotionEvent event){		
		if(event.getKind()==Kind.ACTION_UP)
			show = false;
		else if(event.getKind()==Kind.ACTION_DOWN)
			show = true;
		trace.add(new Point(event.getX(), event.getY()));				
	}


/*Lui il ne pren pa une liste Normalement vu ke c un de ces champ, on f komen la ???*/
	public boolean validGesture() {
		/*
		 * Faire un factory?
		 */
		Filter filter = new Drift();
		return filter.checkGesture(trace);
	}



}
