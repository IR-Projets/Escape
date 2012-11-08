package entities.maps;

import java.awt.image.BufferedImage;
import java.util.Random;

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
		imageBigCloud = Ressources.getImage("images/Maps/bigCloud.png");
		imageMidCloud = Ressources.getImage("images/Maps/midCloud.png");
		imageSmallCloud = Ressources.getImage("images/Maps/smallCloud.png");
		loop=0;
		rand = new Random();
	}

	@Override
	public void computeMap() {
		loop++;
		if(loop>LOOP_SKIP){
			loop=0;
			
			int posY = rand.nextInt(Variables.SCREEN_WIDTH); 
			
			switch(rand.nextInt(3)){
				case 0:
					addLayer(new Layer(imageBigCloud, posY - imageBigCloud.getWidth()/2, 2f));
					break;
				case 1:
					addLayer(new Layer(imageMidCloud, posY - imageMidCloud.getWidth()/2, 0.5f));
					break;
				case 2:
					addLayer(new Layer(imageSmallCloud, posY - imageSmallCloud.getWidth()/2, 0.1f));
					break;
			}
			
			
		}	
	}
	
	
	
}
