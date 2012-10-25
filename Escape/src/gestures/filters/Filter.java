package gestures.filters;

import java.util.List;

import org.jbox2d.common.Vec2;

import entities.ships.Ship;

public interface Filter {
	public boolean check(List<Vec2> trace);
	public void apply(Ship ship);
}

