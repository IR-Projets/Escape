package gestures.filters;

import java.util.List;

import org.jbox2d.common.Vec2;

import entities.ships.Player;
import entities.ships.Ship;

public interface Filter {
	/**
	 * Check if a list of Vec2 is correctly positioned, depends on what kind on filter we want to apply
	 * @param trace
	 * @return boolean
	 */
	public boolean check(List<Vec2> trace);
	
	/**
	 * Apply the movement of the Filter at the Ship
	 * @param ship
	 * @see Ship
	 */
	public void apply(Player ship);
}

