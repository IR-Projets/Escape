package gestures;

import java.awt.Color;
import java.awt.Graphics2D;
import java.util.LinkedList;
import java.util.List;
import java.util.Random;

import ships.Ship;

import fr.umlv.zen2.MotionEvent;
import fr.umlv.zen2.MotionEvent.Kind;

public class Gesture {

	private List<Point> trace, last;
	private boolean show;
	private boolean check;
	private Color colorCheck, lastcolorCheck;/* For saving the color to display after a movement check*/
	Ship ship;
	
	public Gesture(Ship controlledShip){
		this.ship = controlledShip;
		trace = new LinkedList<>();
		last = null;
		show = check = false;
		colorCheck = lastcolorCheck = null;
	}

	/*
	 * Séparer les fonction!
	 * la fonction render se charge de l'affichage uniquement (on peut afficher même quand on ne clique pas... effet de rémanence avec timer par exemple)
	 */
	public void render(Graphics2D graphics){

		/*
		 * La faut faire sa plus propre... A toi de voir jle fais juste pour tester...
		 * mais c'est comme sa que l'on doit déplacer un vaisseau...
		 * A voir si on peut refactoriser... le code fait mal au cranes...
		 */
		if(!trace.isEmpty()){
			ship.move(-10, -10);
		}
		
		if(show == false && !trace.isEmpty()){/* We have finished a movement */
			if(check == false){/* So we checked it */
				if(validGesture())
					colorCheck = new Color(0,255,0);
				else
					colorCheck = new Color(255,0,0);
				check = true;
			}
			if(lastcolorCheck == null)/* we save the value */
				lastcolorCheck=colorCheck;
			
			graphics.setColor(colorCheck);
			Random rand = new Random();
			
			if(rand.nextInt()%3 == 0){/*We remove by order : Last, then trace*/
				if(last!=null && !last.isEmpty())
					last.remove(0);
				else {
						if(!trace.isEmpty())
							trace.remove(0);
						lastcolorCheck=null;
				}
			}
		}

		for(int i=1;i<trace.size();i++)
			graphics.drawLine(trace.get(i-1).getX(), trace.get(i-1).getY(), trace.get(i).getX(), trace.get(i).getY());

		if(last != null && lastcolorCheck!= null){
			graphics.setColor(lastcolorCheck);
			for(int i=1;i<last.size();i++)
				graphics.drawLine(last.get(i-1).getX(), last.get(i-1).getY(), last.get(i).getX(), last.get(i).getY());
		}
		/*for(Point p : trace)
			graphics.fillOval(p.getX(), p.getY(), 10, 10);*/
	}

	/*
	 * Début de l'event 
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


	/*Lui il ne pren pa une liste Normalement vu ke c un de ces champ, on f komen la ???*/
	public boolean validGesture() {
		/*
		 * Faire un factory?
		 */
		Filter filter = new Drift();
		return filter.checkGesture(trace);
	}



}
