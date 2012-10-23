package gestures;

import game.Variables;

import java.util.Iterator;
import java.util.List;
import java.util.Objects;

import org.jbox2d.common.Vec2;

public class Drift implements Filter{



	@Override
	public boolean checkGesture(List<Vec2> trace){
		Objects.requireNonNull(trace);
		if(trace.size()<2)
			return false;

		int size=trace.size(), nbPoints=0;//The bornes is the borne for accept the drift, to compensate the approximation of the coefficient with 2 points 
		Iterator<Vec2> it = trace.iterator();
		
		Vec2 pBeg = trace.get(0);
		Vec2 pEnd = trace.get(size-1);

		if(pBeg.x==pEnd.x)// Avoid the division by 0
			return false;
		double coef = -((double)(pBeg.y-pEnd.y) / (pBeg.x-pEnd.x));

		/*System.out.println("PointBeg : ("+pBeg.getX()+", "+pBeg.getY());
		System.out.println("PointMid : ("+pEnd.getX()+", "+pEnd.getY());
		System.out.println("coef :"+coef);*/

		while(it.hasNext()){
			Vec2 pActual = it.next();
			Vec2 pActualCenter = new Vec2(pActual.x-pBeg.x, pActual.y-pBeg.y);

			int yNorm = (int) -((pActualCenter.x)*coef);
			//System.out.println(yNorm+" et "+pActualCenter.getY()+" et x "+pActualCenter.getX());
			if(yNorm <= pActualCenter.y+Variables.BORNES_GESTURE_TRACE && yNorm >= pActualCenter.y-Variables.BORNES_GESTURE_TRACE && yNorm<0)
				nbPoints++;
		}
		//System.out.println("Pts valide : "+nbPoints+ " sur "+ trace.size());
		if(nbPoints >= trace.size()*Variables.RATE_ACCEPT_TRACE){
			String posDrift = (coef>0)?" right":" left";
			System.out.println("Drift"+posDrift);
			return true;
		}
		return false;
	}
}
