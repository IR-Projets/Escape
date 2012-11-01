package hud;

import java.awt.image.BufferedImage;

public class Item {
	private final String name;
	private final BufferedImage image;
	private int nbItem;
	
	public Item(String name, BufferedImage image, int nbItem) {
		this.name = name;
		this.image=image;
		this.nbItem=nbItem;
	}

	public String getName() {
		return name;
	}

	public BufferedImage getImage() {
		return image;
	}

	public int getNbItem() {
		return nbItem;
	}
	
	
}
