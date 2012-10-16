package game;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.nio.file.FileSystems;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Random;

import javax.imageio.ImageIO;

import fr.umlv.zen2.Application;
import fr.umlv.zen2.ApplicationCode;
import fr.umlv.zen2.ApplicationContext;
import fr.umlv.zen2.ApplicationRenderCode;

public class TestPerso {
	final static int WIDTH = 400;
	final static int HEIGHT = 600;



	public static void main(String[] args) {

		Application.run("ScrollIt", WIDTH, HEIGHT, new ApplicationCode() {
			@Override
			public void run(ApplicationContext context) {   

				context.render(new ApplicationRenderCode() {  
					@Override
					public void render(Graphics2D graphics) {
						   BufferedImage image = null;
						   try {                
							   image = ImageIO.read(new File("Escape\\img\\earth.png"));
						   } catch (IOException ex) {
							   System.out.println("File not found" + Paths.get("").toAbsolutePath());
						   }
						   
						   graphics.drawImage(image, 0, 0, WIDTH, HEIGHT, null); 						   
					}
				});


				while(true) {
					try {
						Thread.sleep(25);
					} catch (InterruptedException e) {
						Thread.currentThread().interrupt();
					}


					context.render(new ApplicationRenderCode() {
						@Override
						public void render(Graphics2D graphics) {
							graphics.copyArea(0, 5, WIDTH, HEIGHT - 5, 0, -5);
							graphics.fill(new Rectangle(0, HEIGHT - 5, WIDTH, 5));
						}
					});
				}
			}
		});
	}
}
