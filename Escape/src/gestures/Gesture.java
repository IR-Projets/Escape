package gestures;

import fr.umlv.zen2.MotionEvent;
import fr.umlv.zen2.MotionEvent.Kind;
import game.Variables;

import java.awt.Graphics2D;
import java.util.LinkedList;
import java.util.List;

import org.jbox2d.common.Vec2;

import ships.Ship;

/*
 * LF:
 * TODO: JAVADOC
 * TODO: Améliorer lisibilité !!
 * 
 * QB:
 * DO : Lisibilite & debut javadoc
 * TODO : Probleme de gravite quand on trace une nouvelle courbe apre une reussie -> le vaisseau tombe, et ne continu pas l'ancienne trajectoire
 *
 *	
 * TODO : Faire un setDebug pour afficher les courbes approximer ou non :) -> pour demain
 * //QB: Pa mal , mai fo voir pour deplacer limage mnt selon un angle en fonction(pour le loop surtout)
 * Gerer l'acceleration
 */


public class Gesture {

	private final List<Trace> traces;
	private Trace traceHandle;
	private final Ship controlledShip;
	private boolean remove;

	public Gesture(Ship controlledShip){
		traces = new LinkedList<>();
		traceHandle = new Trace();
		this.controlledShip = controlledShip;
		remove = false;
	}

	/**
	 * Display all Traces : The current, and all which are not finish to been removed
	 * @param Graphics2D graphics
	 * @see Trace
	 */
	private void displayTraces(Graphics2D graphics){
		graphics.setColor(Variables.BLUE);
		traceHandle.render(graphics);
		for(Trace trace : traces){
			if(trace.getValid()==true)
				graphics.setColor(Variables.GREEN);
			else
				graphics.setColor(Variables.RED);
			trace.render(graphics);
		}
	}



	/**
	 * Display the Gesture, which is a trace of the movement printing by the mouse
	 * @param Graphics2D graphics
	 */
	public void render(Graphics2D graphics){
		displayTraces(graphics);//Display all traces
		if(traces.isEmpty())
			return;

		
		Trace trace = traces.get(0);
		if(trace.getTrace().size()>4 && trace.getValid()){//Do the movement
			float speedX = trace.getTrace().get(0).x-trace.getTrace().get(3).x;
			speedX=(speedX>0)?-Variables.SPEED_MAIN_SHIP:Variables.SPEED_MAIN_SHIP;
			float speedY = trace.getTrace().get(0).y-trace.getTrace().get(3).y;
			speedY=(speedY>0)?Variables.SPEED_MAIN_SHIP:-Variables.SPEED_MAIN_SHIP;
			controlledShip.move(speedX, speedY);
		}

		if(remove){
			Trace traceToRemove = traces.get(0);
			if(traceToRemove.getValid() == false && !traceToRemove.getTrace().isEmpty())
				traceToRemove.getTrace().remove(0);//Remove more fast Trace which aren't been validate
			else
				if(traces.get(0).removeHeadSlowly() == false)//One path is finish, so we remove it from our List
					traces.remove(0);
		}
	}



	/**
	 * The event launched by the mouse, which is described by zen2 Libraries
	 * @param MotionEvent event : the event of the mouse
	 * @see Kind
	 */
	public void event(MotionEvent event){
		switch(event.getKind()){
		case ACTION_UP :
			traceHandle.checkTrace();
			traces.add(traceHandle);
			traceHandle = new Trace();
			remove = true;
			break;

		case ACTION_DOWN :
			remove = false;
			break;

		case ACTION_MOVE :
			traceHandle.getTrace().add(new Vec2(event.getX(), event.getY()));
			break;

		default:
			break;
		}
	}
	

	public void compute() {
		/*
		 * LF:
		 * C'est la qu'on fait tout les calculs! 
		 * render se charge uniquement de l'affichage!!!		
		 */		
	}
}