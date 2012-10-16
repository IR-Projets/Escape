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
		private int vectorX;
		private int vectorY;
		
		

		public Map() throws IOException{			                
			try {                
				image = ImageIO.read(new File(font));
			} catch (IOException ex) {
				throw new IOException("Map initialisation fail: can't open " + font);
			}
			
			width = image.getWidth();
			height = image.getHeight();
			vectorX=0;
			vectorY=image.getHeight();
		}


		public void render(Graphics2D graphics){
			if(vectorX>=width)
				vectorX=width-1;
			if(vectorY>=height)
				vectorY=height-1;
			
			int w = vectorX+Variables.SCREEN_WIDTH;
			if(w>=width)
				w=width-1;
			int h = vectorY+Variables.SCREEN_HEIGHT;
			if(h>=height)
				h=height-1;
			
			vectorY--;
			
			try{
				graphics.drawImage(image.getSubimage(vectorX, vectorY, w, h), 0, 0, Variables.SCREEN_WIDTH, Variables.SCREEN_HEIGHT, null );
			}
			catch(Exception e){
				System.out.println("");
			}
		}

	}