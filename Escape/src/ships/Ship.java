package ships;

import game.Variables;

import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

import org.jbox2d.dynamics.World;

import worlds.Entity;

public class Ship extends Entity{

	private final String font = "images\\ship.png";


	private final BufferedImage image;
	private final int width;
	private final int height;
	private int posX;
	private int posY;
	
	

	public Ship() throws IOException{
		
		try {                
			image = ImageIO.read(new File(font));
		} catch (IOException ex) {
			throw new IOException("Map initialisation fail: can't open " + font);
		}
		
		width = image.getWidth();
		height = image.getHeight();
		posX = 250;
		posY = 300;
	}

	
	public void render(Graphics2D graphics){
		debug();
		graphics.drawImage(image, getX(), getY(), width, height, null );
	}


	@Override
	public int getWidth() {
		return width;
	}
	
	@Override
	public int getHeight() {
		return height;
	}

}
