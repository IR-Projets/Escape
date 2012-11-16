package maps;

import effects.basicEffect;
import effects.Effects;
import game.Environnement;
import game.Ressources;

import java.io.IOException;
import java.util.Random;


public class Jupiter extends Map{
	
	private static final int LOOP_SKIP = 200;
	int loop;
	Random rand;
	
	
	public Jupiter() {
		super(Ressources.getImage("maps/jupiter.png"), 0.2f);
		rand = new Random();
		loop=0;
	}


	@Override
	public void computeMap() {
		loop++;
		if(loop>LOOP_SKIP){
			loop=0;
			
			switch(rand.nextInt(3)){
				case 0:
					Effects.addEffect(3, new basicEffect("maps/asteroide_small.png", 2));//addLayer(new Layer(imageBigCloud, posY - imageBigCloud.getWidth()/2, 2f));
					break;
				case 1:
					Effects.addEffect(2, new basicEffect("maps/asteroide_mid.png", 0.5f));//addLayer(new Layer(imageMidCloud, posY - imageMidCloud.getWidth()/2, 0.5f));
					break;
				case 2:
					Effects.addEffect(1, new basicEffect("maps/asteroide_big.png", 0.1f));//addLayer(new Layer(imageSmallCloud, posY - imageSmallCloud.getWidth()/2, 0.1f));
					break;
			}			
		}	
	}

}
