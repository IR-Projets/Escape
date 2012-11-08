package entities.maps;

import java.awt.image.BufferedImage;
import java.util.Random;

import effects.Cloud;
import effects.Cloud.CloudType;
import effects.Effects;

import game.Ressources;
import game.Variables;



public class Earth extends Map {
	
	private static final int LOOP_SKIP = 200;
	private BufferedImage imageBigCloud;
	private BufferedImage imageMidCloud;
	private BufferedImage imageSmallCloud;
	int loop;
	Random rand;

	
	public Earth(){
		super(Ressources.getImage("images/maps/earth.png"), 0.2f);
		imageBigCloud = Ressources.getImage("images/maps/bigCloud.png");
		imageMidCloud = Ressources.getImage("images/maps/midCloud.png");
		imageSmallCloud = Ressources.getImage("images/maps/smallCloud.png");
		loop=0;
		rand = new Random();
	}

	@Override
	public void computeMap() {
		loop++;
		if(loop>LOOP_SKIP){
			loop=0;
			
			switch(rand.nextInt(3)){
				case 0:
					Effects.addEffect(new Cloud(CloudType.Big, 2));//addLayer(new Layer(imageBigCloud, posY - imageBigCloud.getWidth()/2, 2f));
					break;
				case 1:
					Effects.addEffect(new Cloud(CloudType.Midlle, 0.5f));//addLayer(new Layer(imageMidCloud, posY - imageMidCloud.getWidth()/2, 0.5f));
					break;
				case 2:
					Effects.addEffect(new Cloud(CloudType.Small, 0.1f));//addLayer(new Layer(imageSmallCloud, posY - imageSmallCloud.getWidth()/2, 0.1f));
					break;
			}			
		}	
	}
	
	
	
}
