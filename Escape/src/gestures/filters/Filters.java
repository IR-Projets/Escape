package gestures.filters;

import game.Variables;

import java.util.Iterator;
import java.util.List;
import java.util.Objects;

import org.jbox2d.common.Vec2;

public class Filters{

	/** 
	 * Return the length of the droite represents by the two points pA AND Pb
	 * @param pA
	 * @param pB
	 * @return double value
	 */
	public static double LengthNormalize(Vec2 pA, Vec2 pB){
		return Math.sqrt(Math.pow(pA.x-pB.x, 2)+Math.pow(pA.y-pB.y, 2));
	}

	/**
	 * Return the angle of a Trace
	 * @param trace
	 * @return return the double value in degree (0-360) where 0 is a right to the right, and 180 a right ro the left 
	 * @see Trace
	 */
	public static double getAngle(List<Vec2> trace){
		return getAngle(trace.get(0), trace.get(trace.size()-1));
	}

	/**
	 * The same Method, with two points
	 * @param pBegin
	 * @param pEnd
	 * @return double
	 */
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

	/**
	 * Check if a right is affine, with a calcul between the difference angle does by the trace
	 * @param trace
	 * @return boolean 
	 */
	public static boolean isAffine(List<Vec2> trace){
		Objects.requireNonNull(trace);
		if(trace.size()<Variables.TRACE_LENGTH_MIN)
			return false;

		Iterator<Vec2> it = trace.iterator();
		boolean firstLoop = true;
		double angleMin =0, angleMax = 0 ;
		Vec2 pActual = null, pLast = it.next();
		while(it.hasNext()){
			int i;
			for(i=0; i<3 && it.hasNext(); i++){
				pActual = it.next();
			}
			double angleActual = getAngle(pLast, pActual);
			if(firstLoop){
				angleMin = angleMax = angleActual;
				firstLoop = false;
			}
			
			if(angleActual > angleMax)
				angleMax = angleActual;
			if(angleActual < angleMin)
				angleMin = angleActual;
			//System.out.println("Variation" + (angleMax-angleMin ));
			if(angleMax - angleMin>Variables.TRACE_VARIATION_MAX)
				return false;
		}
		return true;
	}	
}
