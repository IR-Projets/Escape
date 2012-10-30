package entities.weapons;

import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

public class Fireball extends Weapon {

	public Fireball() throws IOException{
		try {
			currentImage = ImageIO.read(new File("images/weapon/fire.png"));
		} catch (IOException e) {
			throw new IOException("Weapon initialisation fail: can't open images fire.png");
			//e.printStackTrace();
		}
		
	}
}
