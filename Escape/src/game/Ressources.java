package game;

import java.awt.image.BufferedImage;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;

import javax.imageio.ImageIO;

public class Ressources {
	
	
	public static BufferedImage getImage(String url){
		BufferedImage image = null;
		ClassLoader classLoader = Thread.currentThread().getContextClassLoader();
		InputStream input = classLoader.getResourceAsStream(url);
		try {
			image = ImageIO.read(input);
		} catch (IllegalArgumentException | IOException e) {
			System.out.println("Can't read file: " + url + System.getProperty("line.separator"));
			e.printStackTrace();
			System.exit(0);
		}
		return image;		
	}

}
