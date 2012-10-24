package gestures;

import game.Variables;

import java.awt.Graphics2D;
import java.util.LinkedList;
import java.util.List;
import java.util.Random;

import org.jbox2d.common.Vec2;

public class Trace {
	private final List<Vec2> trace;
	private Boolean valid;

	public Trace() {
		trace = new LinkedList<>();
		valid = null;
	}

	public Boolean getValid() {
		return valid;
	}
	
	public List<Vec2> getTrace() {
		return trace;
	}
	
	public boolean removeHeadSlowly(){
		if(trace.isEmpty())
			return false;
		Random rand = new Random();// Removing the queue, point by point
		if(rand.nextInt()%Variables.TRACE_DELETE_RATE == 0)//RATE_DELETE_TRACE is the speed Rate for delete the trace
			trace.remove(0);
		return true;
	}
	
	
	/**
	 * Display a Trace, which represents by a List of Vec2
	 * @see Vec2
	 */
	public void render(Graphics2D graphics){
		for(int i=1;i<trace.size();i++)// Print a Movement reprents by the trace
			graphics.drawLine((int)trace.get(i-1).x, (int)trace.get(i-1).y, (int)trace.get(i).x, (int)trace.get(i).y);
	}

	/**
	 * Check if a Trace is correctly recognized by our movement, defined by the interface Filter
	 * All trace are managed By Gesture.
	 * @see Filter
	 * @see Gesture
	 */
	
	public void checkTrace() {
		/*
		 * Faire un factory?
		 */
		Filter drift = new Drift();
		Filter backoff = new Backoff();
		//new Looping();
		valid = drift.checkGesture(trace); //|| backoff.checkGesture(trace) /*|| looping.checkGesture(trace)*/;
	}

}
