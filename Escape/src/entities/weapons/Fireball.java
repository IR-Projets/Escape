package entities.weapons;

import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

public class Fireball extends Weapon {

	public Fireball(){
		try {
			currentImage = ImageIO.read(new File("images/weapon/fire.png"));
		} catch (IOException e) {
			e.printStackTrace();
			System.exit(0);
		}
		
	}
}
