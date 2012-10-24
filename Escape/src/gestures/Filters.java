package gestures;

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
	
	public static double getAngle(List<Vec2> trace){
		Vec2 pBegin = trace.get(0);
		Vec2 pEnd = trace.get(trace.size()-1);
		double adj = pEnd.x-pBegin.x;
		double opp = pEnd.y-pBegin.y;
		return Math.atan(opp/adj);
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
			if(i<3)
				return true;

			double coefActual;
			try {
				coefActual = coefDirecteur(pLast, pActual);
				

				if(firstLoop){
					coefMin = coefMax = coefActual;
					firstLoop = false;
				}
				
				//System.out.println("coef min "+coefMin+"et coef Max"+coefMax);
				if(coefActual > coefMax)
					coefMax = coefActual;
				if(coefActual < coefMin)
					coefMin = coefActual;

				//System.out.println("pActual "+pActual.x+"et pLast"+pLast.x);
				if(coefMax - coefMin >= Variables.TRACE_VARIATION_MAX)
					return false;

				pLast = pActual;
				
			} catch (IllegalArgumentException e) {
				if(error++>Variables.TRACE_ERROR_MAX)
					return false;
			}
		}
		
		return true;
	}
	
	
}
