package gestures;

import game.Variables;
import gestures.filters.Filter;

import java.awt.Graphics2D;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
/**
 * The TraceStack is the list of Trace, for displays all and manage their validation.
 * @author Quentin Bernard et Ludovic Feltz
 */

/* <This program is an Shoot Them up space game, called Escape-IR, made by IR students.>
 *  Copyright (C) <2012>  <BERNARD Quentin & FELTZ Ludovic>

 *  This program is free software: you can redistribute it and/or modify
 *  it under the terms of the GNU General Public License as published by
 *  the Free Software Foundation, either version 3 of the License, or
 *  (at your option) any later version.

 *  This program is distributed in the hope that it will be useful,
 *  but WITHOUT ANY WARRANTY; without even the implied warranty of
 *  MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 *  GNU General Public License for more details.

 *  You should have received a copy of the GNU General Public License
 *  along with this program.  If not, see <http://www.gnu.org/licenses/>.
 *
 */
public class TraceStack {
	
	/**
	 * We stock a List of trace, which represents all traces who has not finish to been removed
	 */
	private List <Trace> oldTraces;
	
	/**
	 * The currentTrace
	 */
	private Trace currentTrace;

	/**
	 * Initialize the Trace and our list of trace
	 */
	public TraceStack(){
		oldTraces = new LinkedList<>();
		currentTrace = new Trace();
	}
	
	/**
	 * Draw all Traces
	 * @param graphics
	 */
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
				tracesIte.remove();		//Nothing to remove : we remove the trace
		}
	}

	/**
	 * Check if there are no Trace in process
	 * @return
	 */
	public boolean isEmpty() {
		return currentTrace.isEmpty() && oldTraces.isEmpty();
	}
	
	/**
	 * Return the current Trace
	 * @return
	 */
	public Trace getCurrentTrace(){
		return currentTrace;
	}

	/**
	 * Check if the current trace is valid, by checking if the trace is valid for this filter
	 * @param filter
	 * @return true if it's valid, else false
	 */
	public boolean check(Filter filter){
		return currentTrace.checkTrace(filter);
	}
	
	/**
	 * Finish the current Trace
	 */
	public void finishCurrentTrace() {
		oldTraces.add(currentTrace);
		currentTrace = new Trace();
	}
	
	
}
