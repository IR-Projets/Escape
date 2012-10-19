package game;

import java.awt.Graphics2D;
import java.io.IOException;
import Maps.Map;

import ships.Ship;
import worlds.Environnement;


import fr.umlv.zen2.MotionEvent;
import gestures.Gesture;

public class Game {

	Environnement env;
	Map map;
	Ship ship;
	Gesture gesture;
	
	public Game() throws IOException{
		env = Environnement.get();
		map = new Map();
		ship = new Ship();
		gesture = new Gesture();
	}
	
	
	public void init(Graphics2D graphics) {
		ship.init(10, 10);
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
