package gestures;

import java.util.List;
import java.util.Objects;

public class Drift implements Filter{

	@Override
	public boolean checkGesture(List<Point> trace){
		Objects.requireNonNull(trace);

		int size=trace.size(), nbLeft=0, nbRight=0;
		int diffLimits=25;
		double rate_error = (size>15)?0.75:0.5;/*Better rate of error if less points, because less precisions*/
		
		
		for(int i=1;i<size-1;i++){
			Point pLast = trace.get(i-1);
			Point pActual = trace.get(i);
			Point pNext = trace.get(i+1);
			if(pActual.getX() <= pLast.getX() && pActual.getX() >= pNext.getX())  /*Left Drift*/
				if(pActual.getY() <= pLast.getY() && pActual.getY() >= pNext.getY())
					nbLeft++;
			if(	pActual.getX() >= pLast.getX() && pActual.getX() <= pNext.getX())  /*Right Drift*/
				if(pActual.getY() <= pLast.getY() && pActual.getY() >= pNext.getY())
					nbRight++;
			/* Don't unified them them or we can have a curbic courbe that can be allow*/
			
			//System.out.println("Point : ("+pActual.getX()+", "+pActual.getY());
		}

		int difX = trace.get(0).getX()-trace.get(size-1).getX();
		int difY = trace.get(0).getY()-trace.get(size-1).getY();
		difX=(difX>0)?difX:-difX;
		difY=(difY>0)?difY:-difY;
		if(difX <diffLimits || difY < diffLimits)/* Don't allow vertical and horizontal movement */
			return false;
		
		/*System.out.println("Pts valide left: "+nbLeft+ " sur "+ trace.size());
		System.out.println("Pts valide right: "+nbRight+ " sur "+ trace.size());*/
		if(nbLeft>=trace.size()*rate_error || nbRight>=trace.size()*rate_error)
			return true;
		return false;
	}







}
