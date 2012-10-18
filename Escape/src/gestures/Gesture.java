package gestures;

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
		Random rand = new Random();
		for(Point p : trace)
			graphics.fillOval(p.x, p.y, 5, 5);
		
		if(show == false && !trace.isEmpty()){
			if(rand.nextInt()%3 == 0)
				trace.remove(0);	
		}
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
	
	public boolean checkTrace(){
		return false;
		
	}

	class Point{		
		public final int x,y;
		public Point(int x, int y){
			this.x=x;
			this.y=y;
		}
	}

}
