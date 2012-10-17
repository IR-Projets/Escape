package game;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.nio.file.FileSystems;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Random;

import javax.imageio.ImageIO;

import ships.Ship;

import worlds.Map;

import fr.umlv.zen2.Application;
import fr.umlv.zen2.ApplicationCode;
import fr.umlv.zen2.ApplicationContext;
import fr.umlv.zen2.ApplicationRenderCode;
import fr.umlv.zen2.MotionEvent;

public class Game {

	Map map;
	Ship ship;
	
	public Game() throws IOException{
		map = new Map();
		ship = new Ship();
	}
	
	
	public void init(Graphics2D graphics) {
		//map.init(graphics);

	}

	public void run(Graphics2D graphics) {
		map.render(graphics);
		ship.render(graphics);
	}


	public void event(MotionEvent event) {
				
	}

}
