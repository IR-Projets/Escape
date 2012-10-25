package ships;

import game.Variables;

import java.awt.Graphics2D;
import java.awt.geom.AffineTransform;
import java.awt.image.AffineTransformOp;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

import org.jbox2d.common.Vec2;
import org.jbox2d.dynamics.Body;
import org.jbox2d.dynamics.BodyDef;
import org.jbox2d.dynamics.World;
import org.jbox2d.dynamics.joints.DistanceJointDef;
import org.jbox2d.dynamics.joints.Joint;

import Entity.Entity;


public abstract class Ship extends Entity{

	protected final BufferedImage image;
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

	
	protected abstract String getImageURL();

	@Override
	public BufferedImage getImage(){
		return image;
	}

}
