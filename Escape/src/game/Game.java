package game;

import java.awt.Graphics2D;
import java.io.IOException;
import Maps.Map;

import ships.Ship;


import fr.umlv.zen2.MotionEvent;
import gestures.Gesture;

public class Game {

	Map map;
	Ship ship;
	Gesture gesture;
	
	public Game() throws IOException{
		map = new Map();
		ship = new Ship();
		gesture = new Gesture();
	}
	
	
	public void init(Graphics2D graphics) {
		ship.init();

	}

	public void run(Graphics2D graphics) {
		map.render(graphics);
		ship.render(graphics);
		gesture.render(graphics);
	}


	public void event(MotionEvent event) {
		gesture.event(event);
	}

}
