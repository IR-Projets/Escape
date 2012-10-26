package gestures.filters;


import java.util.Iterator;
import java.util.List;

import org.jbox2d.common.Vec2;

import entities.ships.Ship;


public class Looping implements Filter {

	public static final int TRACE_CIRCLE_BORNES = 15;/* bornes of the diameter that we accept */

	public Vec2 vecDistMax(List<Vec2> trace, Vec2 point){
		Iterator<Vec2> it = trace.iterator();
		Vec2 vecMax = null;
		double lengthMax = 0;
		while(it.hasNext()){
			Vec2 vecActual = it.next();
			if(vecMax == null)
				vecMax = vecActual;
			if(Filters.LengthNormalize(vecActual, vecMax) >= lengthMax){
				lengthMax = Filters.LengthNormalize(vecActual, vecMax);
				vecMax=vecActual;
			}
		}
		return vecMax;
	}


	public boolean belongToCircle(Vec2 point, Vec2 center, double diameter){

		/*	Equation of circle : (Px-Cx)²+(Py-Cy)²<= (D/2)² 
		 * 				We uses:   eqA,     eqB    and eqC for variable name */

		double eqA, eqB, eqC;
		eqA = (Math.pow(point.x-center.x,2));
		eqB = (Math.pow(point.y-center.y,2));
		eqC = (Math.pow(diameter/2,2));

		if(eqA+eqB <= eqC)
			return true;
		return false;
	}


/*
	@Override
	public boolean check(List<Vec2> trace) {
		
		Vec2 pDeb = trace.get(0), pDia = trace.get(trace.size()/2);
		Vec2 pCenter = pMid.sub(pDeb);
		double rayon = pCenter.normalize();

		for(Vec2 p : trace){
			Vec2 pActual = p.sub(pCenter);


			//Filters.getAngle(pCenter, pActual)
			if(pActual.normalize() > rayon+TRACE_CIRCLE_BORNES || pActual.normalize() < rayon-TRACE_CIRCLE_BORNES )
				return false;
		}
		return true;
	}
*/






	@Override
	public void apply(Ship ship) {
		// TODO Auto-generated method stub

	}


	@Override
	public boolean check(List<Vec2> trace) {
		// TODO Auto-generated method stub
		return false;
	}

}
