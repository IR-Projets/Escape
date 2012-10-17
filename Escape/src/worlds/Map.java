package worlds;

import game.Variables;

import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

import com.sun.org.apache.xpath.internal.operations.Variable;




	public class Map{

		private final String font = "images\\earth.png";


		private final BufferedImage image;
		private final int width;
		private final int height;
		private int posX;
		private int posY;
		
		

		public Map() throws IOException{			                
			try {                
				image = ImageIO.read(new File(font));
			} catch (IOException ex) {
				throw new IOException("Map initialisation fail: can't open " + font);
			}
			
			width = image.getWidth();
			height = image.getHeight();
			posX=0;
			posY=image.getHeight();
		}


		public void render(Graphics2D graphics){
			
			posY--;
			
			/*
			 * Sort de l'�cran
			 */
			if(posX<=0)
				posX=0;
			if(posY<=0)
				posY=0;
			
			int w = Variables.SCREEN_WIDTH;
			int h = Variables.SCREEN_HEIGHT;
			
			if(w>=width)
				w=width;
			if(h>=height)
				h=height;
			
			try{
				graphics.drawImage(image.getSubimage(posX, posY, w, h), 0, 0, Variables.SCREEN_WIDTH, Variables.SCREEN_HEIGHT, null );
			}
			catch(Exception e){
				System.out.println("bug!");
			}
		}

	}