package effects;

import game.Ressources;
import game.Variables;

import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.util.Random;

public class Cloud extends Effects {

	float posX;
	float posY;
	float velocity;
	BufferedImage image;
	
	public enum CloudType{
		Big,
		Midlle,
		Small		
	}
	public Cloud(CloudType type, float velocity){
		Random rand = new Random();
		this.velocity = velocity;		
		
		switch(type){
		case Big:
			image = Ressources.getImage("maps/bigCloud.png");
			break;			
		case Midlle:
			image = Ressources.getImage("maps/midCloud.png");
			break;
		case Small:
			image = Ressources.getImage("maps/smallCloud.png");
			break;
		}
		posY = - image.getHeight();
		posX= rand.nextInt(Variables.SCREEN_WIDTH) - image.getWidth()/2;
	}
	
	@Override
	public void renderEffect(Graphics2D graphics) {
		graphics.drawImage(image, (int)posX, (int)posY, null);
	}


	@Override
	public void computeEffect(){
		posY+=velocity;
	}

	
	@Override
	public boolean terminated() {
		return posX>Variables.SCREEN_WIDTH || posX<-image.getWidth() ||	posY>Variables.SCREEN_WIDTH+image.getHeight();
	}

}
