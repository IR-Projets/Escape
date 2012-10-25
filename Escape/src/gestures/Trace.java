package gestures;

import game.Variables;

import java.awt.Graphics2D;
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
		
		while(traceIte.hasNext()){
			lastPoint = currentPoint;
			currentPoint = traceIte.next();
			graphics.drawLine((int)lastPoint.x, (int)lastPoint.y, (int)currentPoint.x, (int)currentPoint.y);
		}
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
		}
		return false;//|| looping.checkGesture(trace); //|| backoff.checkGesture(trace) || looping.checkGesture(trace);
	}

}
