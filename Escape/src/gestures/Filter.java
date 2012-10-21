package gestures;

import java.util.List;

public interface Filter {
	final int diffLimits=15;/* limit difference for refuse vertical and horizontal movement */
	final double rate_error = 0.75;/* Rate of our accept intervalle (if 0.5, we accept the movement if 50% of points are correctly valids)*/
	public boolean checkGesture(List<Point> trace);
}
