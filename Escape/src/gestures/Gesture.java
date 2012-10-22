package gestures;

import java.awt.Color;
import java.awt.Graphics2D;
import java.util.LinkedList;
import java.util.List;
import java.util.Random;

import ships.Ship;

import fr.umlv.zen2.MotionEvent;
import fr.umlv.zen2.MotionEvent.Kind;
import game.Variables;

/*
 * LF:
 * TODO: JAVADOC
 * TODO: Améliorer lisibilité !!
 * 
 * QB:
 * DO : Lisibilite & debut javadoc
 * TODO : Probleme de gravite quand on trace une nouvelle courbe apre une reussie -> le vaisseau tombe, et ne continu pas l'ancienne trajectoire
 */
public class Gesture {

	private List<Point> trace, last;
	private boolean show;
	private boolean check;
	private Color colorCheck, lastcolorCheck;/* For saving the color to display after a movement check*/

	Ship controlledShip;

	public Gesture(Ship controlledShip){
		this.controlledShip = controlledShip;
		trace = new LinkedList<>();
		last = null;
		show = check = false;
		colorCheck = lastcolorCheck = null;
	}


	private void setTraceColor(Graphics2D graphics){
		if(trace.isEmpty() || show == true) // We don't have finished a movement, so we don't have to set the font 
			return;
		if(check == false){// If we haven't check the Trace
			lastcolorCheck=colorCheck;// new Trace -> we save the value of the ancient color
			if(validGesture())
				colorCheck = Variables.GREEN;
			else
				colorCheck = Variables.RED;
			check = true;
		}
		graphics.setColor(colorCheck);
	}


	private void displayTrace(Graphics2D graphics){
		setTraceColor(graphics);
		for(int i=1;i<trace.size();i++)// Print actual movement
			graphics.drawLine(trace.get(i-1).getX(), trace.get(i-1).getY(), trace.get(i).getX(), trace.get(i).getY());
		if(last != null && lastcolorCheck!= null){// Print old movement, if he exists
			graphics.setColor(lastcolorCheck);
			for(int i=1;i<last.size();i++)
				graphics.drawLine(last.get(i-1).getX(), last.get(i-1).getY(), last.get(i).getX(), last.get(i).getY());
		}
		if(show==false && !trace.isEmpty()){// If we have a precedent movement, print it
			Random rand = new Random();// Displaying trace by removing the queue, point by point
			if(rand.nextInt()%Variables.RATE_DELETE_TRACE == 0){/*We remove by order : Last, then trace*/
				if(last!=null && !last.isEmpty())
					last.remove(0);
				else 
					if(!trace.isEmpty())
						trace.remove(0);
			}
		}
	}

	/**
	 * Display the Gesture, which is a trace of the movement printing by the mouse
	 * @param Graphics2D graphics
	 */
	public void render(Graphics2D graphics){

		/*
		 * LF:
		 * TODO: La faut faire sa propre... A toi de voir jle fais juste pour tester...
		 * mais c'est comme sa que l'on doit déplacer un vaisseau...
		 * A voir si on peut refactoriser... le code fait mal au crane...
		 * QB:
		 * Je pense que c'est un bon debut pour le deplacemnt, il faudrait voir pour gerer mieux en fonction d la supresion de liste. Dimanche 22/10, 2h30 du matin
		 */
		/*if(!trace.isEmpty()){
			controlledShip.move(-10, -10);
		}*/
		if(trace.size()>4 && colorCheck==Variables.GREEN && show==false){
			int speedX = trace.get(0).getX()-trace.get(3).getX();
			speedX=(speedX>0)?-Variables.SPEED_MAIN_SHIP:Variables.SPEED_MAIN_SHIP;
			int speedY = trace.get(0).getY()-trace.get(3).getY();
			speedY=(speedY>0)?-Variables.SPEED_MAIN_SHIP:Variables.SPEED_MAIN_SHIP;
			//QB: Pa mal , mai fo voir pour deplacer limage mnt selon un angle en fonction(pour le loop surtout)
			controlledShip.move(speedX, speedY);
		}
		/*if(!trace.isEmpty() && colorCheck==Variables.GREEN)
			controlledShip.move(trace.get(0).getX(), trace.get(0).getY());*/
		displayTrace(graphics);
	}




	/**
	 * The event launched by the mouse, which is described by zen2 Libraries
	 * @param MotionEvent event : the event of the mouse
	 * @see Kind
	 */
	public void event(MotionEvent event){		
		if(event.getKind()==Kind.ACTION_UP)/* We have finished our Movement*/
			show = false;
		else if(event.getKind()==Kind.ACTION_DOWN){/*We begin our Movement */
			if(!trace.isEmpty()){/* Conflict -> we have already a movement, but not finished yet*/
				last = trace;
				trace = new LinkedList<>();
			}
			if(last != null && !last.isEmpty())/* If we have a third movement, the first dissapear, and our last colour become our current for displays the second movement  */
				lastcolorCheck=colorCheck;
			show = true;
			check = false;
		}
		else
			trace.add(new Point(event.getX(), event.getY()));
	}


	/**
	 * Check if a Trace is correctly recognized by our movement, defined by the interface Filter
	 * @see Filter
	 */
	public boolean validGesture() {
		/*
		 * Faire un factory?
		 */
		Filter drift = new Drift();
		Filter backoff = new Backoff();
		Filter looping = new Looping();

		return drift.checkGesture(trace) || backoff.checkGesture(trace) || looping.checkGesture(trace);
	}


	public void compute() {
		/*
		 * LF:
		 * C'est la qu'on fait tout les calculs! 
		 * render se charge uniquement de l'affichage!!!		
		 */
	}



}
