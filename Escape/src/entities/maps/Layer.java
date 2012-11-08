package entities.maps;

import game.Variables;

import java.awt.Graphics2D;
import java.awt.image.BufferedImage;

public class Layer implements Comparable<Layer>{
	
	private BufferedImage image;
	private float posX;
	private float posY;
	private float velocity;
	
	public Layer(BufferedImage image, float posX, float velocity){
		this.image=image;
		this.posX=posX;
		this.posY=-image.getHeight();
		this.velocity=velocity;
	}
	
	
	public void render(Graphics2D graphics){
		graphics.drawImage(image, (int)posX, (int)posY, null);
	}
	
	public void compute(){
		posY+=velocity;
	}
	
	public boolean finished(){
		return posX>Variables.SCREEN_WIDTH || posX<-image.getWidth() ||	posY>Variables.SCREEN_WIDTH+image.getHeight();
	}


	@Override
	public int compareTo(Layer o) {
		return (int) (velocity-o.velocity);
	}
}
