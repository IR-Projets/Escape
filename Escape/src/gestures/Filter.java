package gestures;

import java.util.List;
import ships.Ship;

import org.jbox2d.common.Vec2;

public interface Filter {
	public boolean check(List<Vec2> trace);
	public void apply(Ship ship);
}

