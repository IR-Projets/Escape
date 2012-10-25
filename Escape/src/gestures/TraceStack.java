package gestures;

import game.Variables;
import gestures.filters.Filter;

import java.awt.Graphics2D;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;

public class TraceStack {
	private List <Trace> oldTraces;
	private Trace currentTrace;

	public TraceStack(){
		oldTraces = new LinkedList<>();
		currentTrace = new Trace();
	}
	
	public void render(Graphics2D graphics) {
		graphics.setColor(Variables.BLUE);
		currentTrace.render(graphics);
		
		Iterator <Trace> tracesIte = oldTraces.iterator();
		
		while(tracesIte.hasNext()){
			Trace trace = tracesIte.next();
			
			if(trace.isValid()==true)
				graphics.setColor(Variables.GREEN);
			else
				graphics.setColor(Variables.RED);
			
			trace.render(graphics);
			
			trace.removeLastPoint();
			if(trace.isEmpty())
				tracesIte.remove();		//Plus rien a supprimer
		}		
	}

	public boolean isEmpty() {
		return currentTrace.isEmpty() && oldTraces.isEmpty();
	}
	
	public Trace getCurrentTrace(){
		return currentTrace;
	}

	public boolean check(Filter filter){
		return currentTrace.checkTrace(filter);		
	}
	
	/**
	 * Test la trace courante avec une liste de filtre (l'ordre des filtres a une importance)
	 * @param filters
	 */
	public void finishCurrentTrace() {
		oldTraces.add(currentTrace);
		currentTrace = new Trace();
	}
	
	
}
