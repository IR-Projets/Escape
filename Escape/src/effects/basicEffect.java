package effects;

import game.Ressources;
import game.Variables;

import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.util.Random;

public class basicEffect extends Effects {

	float posX;
	float posY;
	float velocity;
	BufferedImage image;
	
	
	public basicEffect(String fileName, float velocity){
		Random rand = new Random();
		this.velocity = velocity;		
		image = Ressources.getImage(fileName);
		posY = - image.getHeight();
		posX= rand.nextInt(Variables.SCREEN_WIDTH + 10 - image.getWidth()/2) - 10;
	}
	
	@Override
	public void renderEffect(Graphics2D graphics) {
		graphics.drawImage(image, (int)posX, (int)posY, null);
	}


	@Override
	public void computeEffect(){
		posY+=velocity;
		Random rand = new Random();
		int n;
	}

	
	@Override
	public boolean terminated() {
		return posX>Variables.SCREEN_WIDTH || posX<-image.getWidth() ||	posY>Variables.SCREEN_HEIGHT+image.getHeight();
	}

}
