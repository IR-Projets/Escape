package gestures;

import java.util.List;

import org.jbox2d.common.Vec2;

public class Drift implements Filter{
	public static final int TRACE_DRIFT_BORNES_TOP = 10;/* We refuse affine courbe which increase perpendiculary, with a this bornes*/
	public static final int TRACE_DRIFT_BORNES_BOT = 10;/* We refuse affine courbe which increase perpendiculary, with a this bornes*/
	
	public boolean checkAngle(double angle){
		if((angle >= 90-TRACE_DRIFT_BORNES_TOP && angle <= 90+TRACE_DRIFT_BORNES_TOP )||
				angle -TRACE_DRIFT_BORNES_BOT <= 0 || angle + TRACE_DRIFT_BORNES_BOT >= 180 )
			return false;
		return true;
	}

	@Override
	public boolean checkGesture(List<Vec2> trace){
		if(!trace.isEmpty()){
			double angle = Filters.getAngle(trace);
			if(Filters.isAffine(trace) && checkAngle(angle))
				return true;
			return false;
		}
		return false;
	}
}
