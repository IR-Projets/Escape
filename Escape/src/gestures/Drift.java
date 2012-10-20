package gestures;

import java.util.List;

public class Drift implements Filter{
	
	@Override
	public boolean checkGesture(List<Point> trace){
		/*int bornes=25;*/
		int size=trace.size(), nbPointsValid=0;
		Point pBegin = trace.get(0);
		Point pEnd = trace.get(size-1);
		
		
		for(int i=0;i<size;i++){
			Point pActual = trace.get(i);
			if(pActual.getX()<pEnd.getX() && pActual.getX()>pBegin.getX())
				if(pActual.getY()<pBegin.getY() && pActual.getY()>pEnd.getY())
					nbPointsValid++;
		}
		System.out.println(nbPointsValid+" et "+trace.size());
		if(nbPointsValid>=trace.size()*0.8)
			return true;
		return false;
		/*
		int x = trace.get(0).getX();
		for(Point p : trace){
			if(p.getX()<x-5)
				return false;
		}
		return true;*/
	}
}
