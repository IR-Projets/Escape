package gestures;

import java.util.List;

public class Drift implements Filter{
	
	@Override
	public boolean checkGesture(List<Point> trace){
		int x = trace.get(0).getX();
		for(Point p : trace){
			if(p.getX()<x-5)
				return false;
		}
		return true;
	}
}
