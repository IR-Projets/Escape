package gestures;

import java.util.List;

import org.jbox2d.common.Vec2;

public interface Filter {
	public boolean checkGesture(List<Vec2> trace);
}
