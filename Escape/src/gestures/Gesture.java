package gestures;

import java.awt.Graphics2D;
import java.util.LinkedList;
import java.util.List;

import fr.umlv.zen2.MotionEvent;
import fr.umlv.zen2.MotionEvent.Kind;

public class Gesture {
	
	List<Point> trace;
	
	public Gesture(){
		this.trace = new LinkedList<>();
	}
	
	public void event(MotionEvent event, Graphics2D graphics){
		trace.add(new Point(event.getX(), event.getY()));
		for(Point p : trace)
			graphics.drawOval(p.x, p.y, 5, 5);
		
		if(event.getKind()==Kind.ACTION_UP)
			trace =  new LinkedList<>();
	}
	
	class Point{
		
		private final int x,y;
		public Point(int x, int y){
			this.x=x;
			this.y=y;
		}
	}

}
