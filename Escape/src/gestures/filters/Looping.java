package gestures.filters;


import java.util.Iterator;
import java.util.List;
import java.util.Objects;

import org.jbox2d.common.Vec2;

import entities.ships.Ship;


public class Looping implements Filter {

	public static final int TRACE_CIRCLE_BORNES = 50;/* bornes of the diameter that we accept */
	public static final double TRACE_CIRCLE_RATE_PERCENTAGE = 0.1;/* bornes of the diameter that we accept */
	private List<Vec2> traceCircle = null;

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

	@Override
	public boolean check(List<Vec2> trace) {
		traceCircle=trace;
		Vec2 pDeb = trace.get(0), pDistMax = vecDistMax(trace, pDeb);
		Vec2 pCenter = pDistMax.sub(pDeb);
		double rayon = Filters.LengthNormalize(pCenter, pDeb)/2;
		int nbErreur=0;

		for(Vec2 p : trace){
			Vec2 pActual = p.sub(pCenter);
			double rayonActual = Filters.LengthNormalize(pCenter, pActual)/2;
			System.out.println("Rayon actual"+rayonActual+" et rayon"+rayon);
			//Filters.getAngle(pCenter, pActual)
			if(rayonActual > rayon+TRACE_CIRCLE_BORNES || rayonActual < rayon-TRACE_CIRCLE_BORNES )
				nbErreur++;

			if(nbErreur>trace.size()*TRACE_CIRCLE_RATE_PERCENTAGE)
				return false;	
		}
		return true;
	}


	@Override
	public void apply(Ship ship) {
		Objects.requireNonNull(traceCircle);
		/*if()
		if(Filters.getAngle(trace.))*/
		// TODO Auto-generated method stub

	}

}
