package gestures;

import java.util.List;
import java.util.Objects;

public class Drift implements Filter{

	@Override
	public boolean checkGesture(List<Point> trace){
		Objects.requireNonNull(trace);
		if(trace.size()<=2)
			return false;

		int size=trace.size(), nbLeft=0, nbRight=0;
		Point pLast=trace.get(0), pActual=trace.get(1), pNext=trace.get(2);

		for(int i=1;i<size-1;i++){
			if(pActual.getY() <= pLast.getY() && pActual.getY() >= pNext.getY()){/*Are we correctly moved up? */
				if(i<10 || (i>10 && nbLeft>= rate_error*i))		/* At 10, we checks in what drift we are, for saving operations*/
					if( pActual.getX() <= pLast.getX() && pActual.getX() >= pNext.getX())  /*Left Drift*/
						nbLeft++;
				if(i<10 || (i>10 && nbRight>= rate_error*i))
					if(	pActual.getX() >= pLast.getX() && pActual.getX() <= pNext.getX())  /*Right Drift*/
						nbRight++;
			}
			/* Don't unified them them or we can have a curbic courbe that can be allow*/

			pLast = pActual;
			pActual=pNext;
			pNext = trace.get(i+1);
			//System.out.println("Point : ("+pActual.getX()+", "+pActual.getY());
		}

		int difX = trace.get(0).getX()-trace.get(size-1).getX();
		int difY = trace.get(0).getY()-trace.get(size-1).getY();
		difX=(difX>0)?difX:-difX;
		difY=(difY>0)?difY:-difY;
		if(difX <diffLimits || difY < diffLimits )/* Don't allow vertical and horizontal movement */
			return false;


		/*A SUPPRIMER une fois move implemente (affiche juste kel drift est reconnu)
		/*System.out.println("Pts valide left: "+nbLeft+ " sur "+ trace.size());
		System.out.println("Pts valide right: "+nbRight+ " sur "+ trace.size());*/
		boolean left;
		if((left = (nbLeft>=trace.size()*rate_error)) || nbRight>=trace.size()*rate_error){
			String posDrift = (left==true)?" left":" right";
			System.out.println("Drift"+posDrift);
			return true;
		}
		return false;

		/* Faudra metre juste ca a la place
		/*if(nbLeft>=trace.size()*rate_error || nbRight>=trace.size()*rate_error)
			return true;
		return false;*/
	}







}
