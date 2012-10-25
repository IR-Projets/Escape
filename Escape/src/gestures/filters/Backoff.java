package gestures;

import game.Variables;

import java.util.Iterator;
import java.util.List;
import java.util.Objects;

import org.jbox2d.common.Vec2;



public class Backoff implements Filter {

	@Override
	public boolean check(List<Vec2> trace) {
		// TODO Auto-generated method stub
		return false;
	}

/*
	@Override
	public boolean checkGesture(List<Vec2> trace){
		Objects.requireNonNull(trace);
		if(trace.size()<=2)
			return false;
		
		int size=trace.size(), nbPoints=0;
		
		Iterator<Vec2> it = trace.iterator();
		Vec2 pLast=it.next(), pActual=it.next(), pNext=it.next();
		
		while(it.hasNext()){
			if(pActual.y >= pLast.y && pActual.y <= pNext.y)
				nbPoints++;
			pLast = pActual;
			pActual=pNext;
			pNext = it.next();
			//System.out.println("Point : ("+pActual.x+", "+pActual.y);
		}
		
		double difX = trace.get(0).x-trace.get(size-1).x;
		difX=(difX>0)?difX:-difX;
		System.out.println("Pts valide : "+nbPoints+ " sur "+ trace.size());
		if(nbPoints >= Variables.RATE_ACCEPT_TRACE*size && difX < Variables.BORNES_GESTURE_TRACE){
			System.out.println("Backoff");
			return true;
		}
			
		return false;
	}*/

}
