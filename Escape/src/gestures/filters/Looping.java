package gestures;


import game.Variables;

import java.util.List;

import org.jbox2d.common.Vec2;


public class Looping implements Filter {
	
	public static final int TRACE_CIRCLE_BORNES = 15;/* bornes of the diameter that we accept */
	
	
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
	
	
	
	
	
	
	
	@Override
	public boolean check(List<Vec2> trace) {
		Vec2 pDeb = trace.get(0), pMid = trace.get(trace.size()/2);
		Vec2 pCenter = pMid.sub(pDeb);
		double rayon = pCenter.normalize();

		for(Vec2 p : trace){
			Vec2 pActual = p.sub(pCenter);
			
			//Filters.getAngle(pCenter, pActual)
			if(pActual.normalize() > rayon+TRACE_CIRCLE_BORNES || pActual.normalize() < rayon-TRACE_CIRCLE_BORNES )
				return false;
		}
		return true;
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		/*int size=trace.size(), nbPoints=0;	
		Vec2 pDebCircleTrace = trace.get(0);
		Vec2 pEndCircleTrace = trace.get(size-1);
		
		Vec2 pMidCircleTrace = trace.get(size/2);
		Vec2 pRealDebCircle = new Vec2((pDebCircleTrace.x+pEndCircleTrace.x)/2, (pDebCircleTrace.y+pEndCircleTrace.y)/2);
		
		Vec2 center = new Vec2((pMidCircleTrace.x+pRealDebCircle.x)/2, (pMidCircleTrace.y+pRealDebCircle.y)/2);
		
		double diameter = Math.sqrt(Math.pow(center.x-pRealDebCircle.x,2)+Math.pow(center.y-pRealDebCircle.y,2));
		for(Vec2 p : trace){
			if(belongToCircle(p, center, diameter*3) && !belongToCircle(p, center, diameter/3))
				nbPoints++;
		}*/
		
		
		
		
		/*System.out.println("Debut Cercle : ("+pMidCircleTrace.getX()+", "+pMidCircleTrace.getY());
		System.out.println("Autre extermite : ("+pRealDebCircle.getX()+", "+pRealDebCircle.getY());
		System.out.println("Center : ("+center.getX()+", "+center.getY());
		System.out.println("Pts valide : "+nbPoints+ " sur "+ trace.size());*/
		/*if(nbPoints>=trace.size()*Variables.RATE_ACCEPT_TRACE){
			System.out.println("Looping");
			return true;
		}*/
			
	}

}
