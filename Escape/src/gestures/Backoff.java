package gestures;

public class Backoff extends Gesture {
import java.util.List;
import java.util.Objects;



public class Backoff implements Filter {


	@Override
	public boolean checkGesture(List<Point> trace){
		Objects.requireNonNull(trace);
		if(trace.size()<=2)
			return false;
		
		int size=trace.size(), nbPoints=0;
		Point pLast=trace.get(0), pActual=trace.get(1), pNext=trace.get(2);
		
		for(int i=2;i<size-1;i++){
			if(pActual.getY() >= pLast.getY() && pActual.getY() <= pNext.getY())
				nbPoints++;
			pLast = pActual;
			pActual=pNext;
			pNext = trace.get(i+1);
			//System.out.println("Point : ("+pActual.getX()+", "+pActual.getY());
		}
		
		int difX = trace.get(0).getX()-trace.get(size-1).getX();
		difX=(difX>0)?difX:-difX;
		if(nbPoints >= rate_error*size && difX < diffLimits)
			return true;
		return false;
	}

}
