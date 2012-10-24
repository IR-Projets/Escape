package gestures;

import java.util.List;

import org.jbox2d.common.Vec2;

public class Drift implements Filter{


	public double coefDirecteur(Vec2 pA, Vec2 pB){
		if(pB.x == pA.x)
			throw new IllegalArgumentException("erreur "+pB.x+" et "+pA.x);
		return -((double)(pB.y-pA.y) / (pB.x-pA.x));
	}

	@Override
	public boolean checkGesture(List<Vec2> trace){
		if(!trace.isEmpty()){
			double angle = Filters.getAngle(trace)*2*Math.PI;
			System.out.println("angle  "+angle);
		}
		return true;
		//if(Filters.isAffine(trace) && angle )
	}
}
