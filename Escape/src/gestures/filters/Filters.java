package gestures.filters;

import game.Variables;

import java.util.Iterator;
import java.util.List;
import java.util.Objects;

import org.jbox2d.common.Vec2;

public class Filters{
	
	public static double coefDirecteur(Vec2 pA, Vec2 pB){
		if(pB.x == pA.x)
			throw new IllegalArgumentException("erreur "+pB.x+" et "+pA.x);
		return -((double)(pB.y-pA.y) / (pB.x-pA.x));
	}
	
	public static double getAngle(Vec2 pBegin, Vec2 pEnd){
		Vec2 pReal = pEnd.sub(pBegin);
		double angle = Math.atan2(pReal.y, pReal.x);
		if(angle < 0)
			angle = Math.abs(angle);
		else
			angle = 2*Math.PI - angle;
		
		//System.out.println(Math.toDegrees(angle));
		return Math.toDegrees(angle);
	}
	
	public static double getAngle(List<Vec2> trace){
		Vec2 pBegin = trace.get(0), pEnd = trace.get(trace.size()-1);
		Vec2 pReal = pEnd.sub(pBegin);
		double angle = Math.atan2(pReal.y, pReal.x);
		if(angle < 0)
			angle = Math.abs(angle);
		else
			angle = 2*Math.PI - angle;
		
		//System.out.println(Math.toDegrees(angle));
		return Math.toDegrees(angle);
	}
	
	public static boolean isAffine(List<Vec2> trace){
		Objects.requireNonNull(trace);
		if(trace.size()<Variables.TRACE_LENGTH_MIN)
			return false;

		Iterator<Vec2> it = trace.iterator();
		boolean firstLoop = true;
		double coefMin = 0, coefMax = 0;
		int error = 0;
		Vec2 pActual = null, pLast = it.next();

		while(it.hasNext()){
			int i;
			for(i=0; i<3 && it.hasNext(); i++){
				pActual = it.next();
			}
			/*if(i<3)
				return true;*/

			double coefActual;
			try {
				coefActual = coefDirecteur(pLast, pActual);
				System.out.println("Coef actual"+coefActual);

			} catch (IllegalArgumentException e) {
				if(error++>Variables.TRACE_ERROR_MAX)
					return false;
				coefActual = coefMin;
			}
			if(firstLoop){
				coefMin = coefMax = coefActual;
					firstLoop = false;
				}
				
				System.out.println("coef min "+coefMin+"et coef Max"+coefMax);
				if(coefActual > coefMax)
					coefMax = coefActual;
				if(coefActual < coefMin)
					coefMin = coefActual;

				//System.out.println("pActual "+pActual.x+"et pLast"+pLast.x);
				if(coefMax - coefMin >= Variables.TRACE_VARIATION_MAX)
					return false;
				pLast = pActual;

		}
		if(coefMax>0 && coefMin<0)
			return false;
		return true;
	}	
}
