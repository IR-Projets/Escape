package game;

import java.awt.Graphics2D;
import java.io.IOException;
import Maps.Map;

import ships.Ship;
import worlds.Environnement;
import worlds.EnvironnementFactory;


import fr.umlv.zen2.MotionEvent;
import gestures.Gesture;

public class Game {

	Environnement env;
	
	public Game() throws IOException{		
		env = EnvironnementFactory.factory();
	}
	
	
	public void init(Graphics2D graphics) {
		
	}

	public void run(Graphics2D graphics) {
		env.render(graphics);
	}


	public void event(MotionEvent event) {
		env.event(event);
	}

}
