package gestures;

import java.util.List;
import java.util.Objects;

public class Drift implements Filter{

	@Override
	public boolean checkGesture(List<Point> trace){
		Objects.requireNonNull(trace);

		int size=trace.size(), nbPointsValid=0;
		for(int i=1;i<size-1;i++){
			Point pLast = trace.get(i-1);
			Point pActual = trace.get(i);
			Point pNext = trace.get(i+1);
			if(pActual.getX() < pLast.getX() && pActual.getX() > pNext.getX()  ||   /*Left Drift*/
					pActual.getX() > pLast.getX() && pActual.getX() < pNext.getX()	)  /*Right Drift*/
				if(pActual.getY() <= pLast.getY() && pActual.getY() >= pNext.getY())
					nbPointsValid++;
			//System.out.println("Point : ("+pActual.getX()+", "+pActual.getY());
		}

		//System.out.println("Pts valide : "+nbPointsValid+ " sur "+ trace.size());
		if(nbPointsValid>=trace.size()*0.75)
			return true;
		return false;
	}







}
