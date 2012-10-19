package gestures;

import java.util.List;

public interface Filter {
	public boolean checkGesture(List<Point> trace);
}
