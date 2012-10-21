package gestures;

import java.util.List;

public class Looping implements Filter {

	public boolean belongToCircle(Point point, Point center, double diameter){
		
		/*	Equation of circle : (Px-Cx)²+(Py-Cy)²<= (D/2)² 
		 * 				We uses:   eqA,     eqB    and eqC for variable name */
		
		double eqA, eqB, eqC;
		eqA = (Math.pow(point.getX()-center.getX(),2));
		eqB = (Math.pow(point.getY()-center.getY(),2));
		eqC = (Math.pow(diameter/2,2));
		
		if(eqA+eqB <= eqC)
			return true;
		return false;
	}
	
	
	@Override
	public boolean checkGesture(List<Point> trace) {
		int size=trace.size(), nbPoints=0;	
		Point pDebCircleTrace = trace.get(0);
		Point pEndCircleTrace = trace.get(size-1);
		
		Point pMidCircleTrace = trace.get(size/2);
		Point pRealDebCircle = new Point((pDebCircleTrace.getX()+pEndCircleTrace.getX())/2, (pDebCircleTrace.getY()+pEndCircleTrace.getY())/2);
		
		Point center = new Point((pMidCircleTrace.getX()+pRealDebCircle.getX())/2, (pMidCircleTrace.getY()+pRealDebCircle.getY())/2);
		
		double diameter = Math.sqrt(Math.pow(center.getX()-pRealDebCircle.getX(),2)+Math.pow(center.getY()-pRealDebCircle.getY(),2));
		for(Point p : trace){
			if(belongToCircle(p, center, diameter*3) && !belongToCircle(p, center, diameter/3))
				nbPoints++;
		}
		/*System.out.println("Debut Cercle : ("+pMidCircleTrace.getX()+", "+pMidCircleTrace.getY());
		System.out.println("Autre extermite : ("+pRealDebCircle.getX()+", "+pRealDebCircle.getY());
		System.out.println("Center : ("+center.getX()+", "+center.getY());
		System.out.println("Pts valide : "+nbPoints+ " sur "+ trace.size());*/
		if(nbPoints>=trace.size()*rate_error){
			System.out.println("Looping");
			return true;
		}
			
		return false;
	}

}
