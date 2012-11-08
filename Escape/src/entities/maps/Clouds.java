package entities.maps;

import game.Ressources;

import java.awt.Graphics2D;
import java.awt.image.BufferedImage;

public class Clouds{
	
	BufferedImage imageBigCloud;
	BufferedImage imageMidCloud;
	BufferedImage imageSmallCloud;
	
	public Clouds(){
		imageBigCloud = Ressources.getImage("images/Maps/bigCloud.png");
		imageMidCloud = Ressources.getImage("images/Maps/midCloud.png");
		imageSmallCloud = Ressources.getImage("images/Maps/smallCloud.png");
	}
	
	
	public void render(Graphics2D graphics){
		
		graphics.drawImage(imageSmallCloud, 100, 100, null);
		
	}
	

}
