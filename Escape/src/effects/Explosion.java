package effects;

import game.Variables;

import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

public class Explosion extends Effects {

	private static int SLOW = 5;
	
	private BufferedImage[] images;
	private int currentImage = 0;
	private int currentIte = 0;
	
	private int x;
	private int y;
	
	public Explosion(int x, int y) throws IOException{
		this.x=x;
		this.y=y;
		
		images = new BufferedImage[4];
		
		for(int i=0; i<images.length; i++){
			try {  
				images[i] = ImageIO.read(new File("images/Effects/fire"+i+".png"));				
			} catch (IOException ex) {
				throw new IOException("Ship initialisation fail: can't open images/Effects/fire"+i+".png");
			}
		}
	}
	
	
	@Override
	public void renderEffect(Graphics2D graphics) {
		currentIte = ++currentIte % SLOW;
		if(currentIte==0){
			currentImage++;
		}
		
		graphics.drawImage(images[currentImage], x, y, null);
	}

	@Override
	public boolean terminated() {
		return currentImage==images.length-1 && currentIte==SLOW-1;
	}

}
