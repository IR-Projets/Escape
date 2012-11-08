package gestures;

import game.Variables;
import gestures.filters.Filter;

import java.awt.AlphaComposite;
import java.awt.BasicStroke;
import java.awt.Graphics2D;
import java.awt.Shape;
import java.awt.Stroke;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Random;

import org.jbox2d.common.Vec2;

public class Trace {
	private final List<Vec2> trace;
	private boolean valid;

	public Trace() {
		trace = new LinkedList<>();
		valid = false;
	}

	public boolean isValid() {
		return valid;
	}

	public boolean isEmpty(){
		return trace.isEmpty();
	}
	
	public List<Vec2> getTrace() {
		return trace;
	}
	
	public void removeLastPoint(){
		if(trace.isEmpty())
			return;
		Random rand = new Random();// Removing the queue, point by point
		if(rand.nextInt()%Variables.TRACE_DELETE_RATE == 0)//RATE_DELETE_TRACE is the speed Rate for delete the trace
			trace.remove(0);
	}
	
	public void addPoint(Vec2 vec){
		trace.add(vec);
	}
	
	/**
	 * Display a Trace, which represents by a List of Vec2
	 * @see Vec2
	 */
	public void render(Graphics2D graphics){
		Iterator <Vec2> traceIte = trace.iterator();
		
		if(!traceIte.hasNext())
			return;
		
		Vec2 currentPoint = traceIte.next();
		Vec2 lastPoint = null;
		
		graphics.setStroke(new BasicStroke(5));
		while(traceIte.hasNext()){
			lastPoint = currentPoint;
			currentPoint = traceIte.next();
			
			graphics.setStroke(new BasicStroke(4));//The line to trace
			graphics.drawLine((int)lastPoint.x, (int)lastPoint.y, (int)currentPoint.x, (int)currentPoint.y);
			
			graphics.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER,0.1f));//Draw a transparence line for effect
			graphics.setStroke(new BasicStroke(12));
			graphics.drawLine((int)lastPoint.x, (int)lastPoint.y, (int)currentPoint.x, (int)currentPoint.y);
		
			graphics.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 1f));
		}
		
		graphics.setStroke(new BasicStroke(1));
	}

	public void setValid(boolean valid) {//Only uses by the validation of weapon
		this.valid = valid;
	}
	
	/**
	 * Check if a Trace is correctly recognized by our movement, defined by the interface Filter
	 * All trace are managed By Gesture.
	 * @see Filter
	 * @see Gesture
	 */
	
	public boolean checkTrace(Filter filter) {
		if(filter.check(trace)){
			valid = true;
			return true;
		}
		return false;
	}

}
