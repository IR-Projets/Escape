package ships;

import game.Variables;

import java.awt.Graphics2D;
import java.awt.geom.AffineTransform;
import java.awt.image.AffineTransformOp;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

import org.jbox2d.dynamics.World;

import worlds.Entity;

public abstract class Ship extends Entity{

	private final BufferedImage image;
	private final int width;
	private final int height;
	private int posX;
	private int posY;
	
	

	public Ship() throws IOException{
		
		try {                
			image = ImageIO.read(new File(getImageURL()));
		} catch (IOException ex) {
			throw new IOException("Ship initialisation fail: can't open " + getImageURL());
		}
		
		width = image.getWidth();
		height = image.getHeight();
	}

	
	public void render(Graphics2D graphics){
		AffineTransform tx = new AffineTransform();
		tx.rotate(getRotate(), image.getWidth()/2, image.getHeight()/2);
		
		AffineTransformOp op = new AffineTransformOp(tx, AffineTransformOp.TYPE_BICUBIC);

		graphics.drawImage(op.filter(image, null), getX(), Variables.SCREEN_HEIGHT-getY(), width, height, null );
	}

	protected abstract String getImageURL();

	@Override
	public int getWidth() {
		return width/2;
	}
	
	@Override
	public int getHeight() {
		return height/2;
	}

}
