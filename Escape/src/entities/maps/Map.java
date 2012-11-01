package entities.maps;

import game.Ressources;
import game.Variables;

import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;





	public class Map{

		private final String font = "images/Maps/earth_w500.png";


		private final BufferedImage image;
		private final int width;
		private final int height;
		private int posX;
		private int posY;
		
		

		public Map(){			                               
			image = Ressources.getImage(font, true);
			
			width = image.getWidth();
			height = image.getHeight();
			posX=0;
			posY=image.getHeight();
			
			compute(); //evite les bugs
		}


		public void render(Graphics2D graphics){	
			int screenW = Variables.SCREEN_WIDTH;
			int screenH = Variables.SCREEN_HEIGHT;
			
			//Notre subImage d�passe les bords de notre Image (droite ou bas)
			if(posX+screenW>=width)
				posX=width-screenW-1;
			if(posY+screenH>=height)
				posY=height-screenH-1;
			
			//SubImage d�passe (haut ou gauche)
			if(posX<=0)
				posX=0;
			if(posY<=0)
				posY=0;		
			
			//try{
				graphics.drawImage(image.getSubimage(posX, posY, screenW, screenH), 0, 0, Variables.SCREEN_WIDTH, Variables.SCREEN_HEIGHT, null );
			//}catch(Exception e){
			//	System.out.println("bug!");
			//}
		}


		public void compute() {

			posY--;
			
			/*
			 * Sort de l'�cran
			 */
			int screenW = Variables.SCREEN_WIDTH;
			int screenH = Variables.SCREEN_HEIGHT;
			
			if(screenW>width)
				screenW=width;
			if(screenH>height)
				screenH=height;
			
	
		}

	}