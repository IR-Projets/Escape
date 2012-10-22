package gestures;

import java.util.Iterator;
import java.util.List;
import java.util.Objects;



public class Backoff implements Filter {


	@Override
	public boolean checkGesture(List<Point> trace){
		Objects.requireNonNull(trace);
		if(trace.size()<=2)
			return false;
		
		
		int size=trace.size(), nbPoints=0;
		
		Iterator<Point> it = trace.iterator();
		Point pLast=it.next(), pActual=it.next(), pNext=it.next();
		
		while(it.hasNext()){
			if(pActual.getY() >= pLast.getY() && pActual.getY() <= pNext.getY())
				nbPoints++;
			pLast = pActual;
			pActual=pNext;
			pNext = it.next();
			//System.out.println("Point : ("+pActual.getX()+", "+pActual.getY());
		}
		
		int difX = trace.get(0).getX()-trace.get(size-1).getX();
		difX=(difX>0)?difX:-difX;
		if(nbPoints >= rate_error*size && difX < diffLimits)
			return true;
		return false;
	}

}
