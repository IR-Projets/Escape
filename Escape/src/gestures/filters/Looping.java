package gestures.filters;


import entities.ships.Player;
import game.Variables;

import java.util.Iterator;
import java.util.List;

import org.jbox2d.common.Vec2;


public class Looping implements Filter {

	//public static final int TRACE_CIRCLE_BORNES = 35;/* bornes of the diameter that we accept */
	
	public static final int TRACE_CIRCLE_BORNES_MAX = 125;/* bornes of the diameter Max */
	public static final int TRACE_CIRCLE_BORNES_MIN = 30;/* bornes of the diameter Min */
	
	public static final double TRACE_CIRCLE_RATE_PERCENTAGE = 0.1;/* rate of error -> accept approximely of circle */
	public static final double TRACE_CIRCLE_RATE_CLOSED = 40;/* number of coordate of difference we accept between the begin and end of the circle */
	private enum Direction {LEFT, RIGHT};
	private Direction direction;

	public Direction getDirection(List<Vec2> trace){
		if(trace.size() < 1)
			return null;
		double angle = Filters.getAngle(trace.get(0), trace.get(1));
		if(angle <= 90 || angle >= 270)
			return Direction.RIGHT;
		else
			return Direction.LEFT;
	}
	
	public Vec2 vecDistMax(List<Vec2> trace){
		Iterator<Vec2> it = trace.iterator();
		Vec2 vecBegin = null, vecMax = null;
		if(it.hasNext())
			vecBegin = it.next();
		
		double lengthMax = 0;
		while(it.hasNext()){
			Vec2 vecActual = it.next();
			if(vecMax == null)
				vecMax = vecActual;
			if(Filters.LengthNormalize(vecBegin, vecActual) >= lengthMax){
				lengthMax = Filters.LengthNormalize(vecBegin, vecActual);
				vecMax=vecActual;
			}
		}
		return vecMax;
	}

	@Override
	public boolean check(List<Vec2> trace) {
		if(trace.size() < Variables.TRACE_LENGTH_MIN)
			return false;
		
		Vec2 pDeb = trace.get(0), pDistMax = vecDistMax(trace);
		
		Vec2 pCenterOrigin = new Vec2((pDistMax.x+pDeb.x)/2, (pDistMax.y+pDeb.y)/2);//pDistMax.sub(pDeb);
		//double rayon = Filters.LengthNormalize(pCenterOrigin, pDeb);
		int nbErreur=0;

		Iterator<Vec2> it = trace.iterator();

		while(it.hasNext()){
			//Vec2 pActual = p.sub(pCenterOrigin);
			Vec2 vecActual = it.next();
			double rayonActual = Filters.LengthNormalize(pCenterOrigin, vecActual);
			//System.out.println("Rayon actual"+rayonActual+" et rayon"+rayon);

			if(rayonActual > TRACE_CIRCLE_BORNES_MAX || rayonActual < TRACE_CIRCLE_BORNES_MIN )
				nbErreur++;
			
			if(nbErreur>trace.size()*TRACE_CIRCLE_RATE_PERCENTAGE){
				//System.out.println(nbErreur+" sur  "+trace.size()+ "donc "+(trace.size()*TRACE_CIRCLE_RATE_PERCENTAGE));
				return false;
			}
			if(!it.hasNext())
				if(Filters.LengthNormalize(pDeb,vecActual) >= TRACE_CIRCLE_RATE_CLOSED)// If we don"t finish properly the circle
					return false;
			
		}
		if(getDirection(trace)==Direction.LEFT)
			direction = Direction.LEFT;
		else
			direction = Direction.RIGHT;
		return true;
	}

/*
 * TODO : le deplacement en fonction du rotate :) avec le rotate de limage bien sur!
 */
	@Override
	public void apply(Player ship) {
		switch(direction){
		case LEFT:
			ship.looping(Player.Looping.LEFT);
			break;
		case RIGHT:
			ship.looping(Player.Looping.RIGHT);
			break;
			
		}
		System.out.println("loop"+direction);
	}

}
