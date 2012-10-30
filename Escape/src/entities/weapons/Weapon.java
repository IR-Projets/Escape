package entities.weapons;

import java.awt.image.BufferedImage;

public abstract class Weapon {
	protected BufferedImage currentImage;

	public BufferedImage getIcon(){
		return currentImage;
	}
}
