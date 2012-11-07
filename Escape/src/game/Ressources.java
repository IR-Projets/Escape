package game;

import java.awt.GraphicsConfiguration;
import java.awt.GraphicsDevice;
import java.awt.GraphicsEnvironment;
import java.awt.Transparency;
import java.awt.image.BufferedImage;
import java.awt.image.DataBufferInt;
import java.awt.image.WritableRaster;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.RandomAccessFile;
import java.util.Hashtable;
import java.util.Map;

import javax.imageio.ImageIO;

import org.jbox2d.dynamics.Body;

import entities.Entity;

public class Ressources {
	
	private static final Map<String, BufferedImage> images = new Hashtable<>();
	
	/**
	 * Load a BufferedImage
	 * @param url Image to be loaded (must be in a package)
	 * @param optimized Set the optimisation, usefull for big image without transparency (default false)
	 * @return BufferedImage
	 */
	public static BufferedImage getImage(String url, boolean optimized){
		BufferedImage image = null;
		
		if(images.containsKey(url)){
			return images.get(url);
		}
		
		ClassLoader classLoader = Thread.currentThread().getContextClassLoader();
		InputStream input = classLoader.getResourceAsStream(url);
		try {
			image = ImageIO.read(input);
		} catch (IllegalArgumentException | IOException e) {
			System.out.println("Can't read file: " + url + System.getProperty("line.separator"));
			e.printStackTrace();
			System.exit(0);
		}
		
		if(optimized)
			image = optimise(image);
		
		images.put(url, image);
		return image;		
	}
	
	/**
	 * Load a BufferedImage (non optimized, for transparent or small images)
	 * @param url Image to be loaded (must be in a package)
	 * @return BufferedImage
	 */
	public static BufferedImage getImage(String url){
		return getImage(url, false);
	}
	
	
	
	
	
	/**
	 * Optimise the Image with device param
	 * @param image to be optimized
	 * @return An optimize image
	 */
	private static BufferedImage optimise(BufferedImage image){
		BufferedImage imageTmp = null;
		
		GraphicsEnvironment env = GraphicsEnvironment.getLocalGraphicsEnvironment();
		GraphicsDevice device = env.getDefaultScreenDevice();
		GraphicsConfiguration config = device.getDefaultConfiguration();
		imageTmp = config.createCompatibleImage(image.getWidth(), image.getHeight(), Transparency.OPAQUE);	
		imageTmp.createGraphics().drawImage(image, 0, 0, null);
		
		return imageTmp;
	}

}
