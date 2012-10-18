package gestures;

import java.awt.Graphics2D;
import java.awt.Point;
import java.util.LinkedList;
import java.util.List;

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
		for(Point p : trace)
			graphics.drawOval(p.x, p.y, 5, 5);
		
		show = false;
	}
	
	/*
	 * Début de l'event 
	 */
	public void event(MotionEvent event){		
		if(event.getKind()==Kind.ACTION_UP)
			trace =  new LinkedList<>();
		
		trace.add(new Point(event.getX(), event.getY()));
			
		show = true;
		
	}
	

	class Point{		
		public final int x,y;
		public Point(int x, int y){
			this.x=x;
			this.y=y;
		}
	}

}
