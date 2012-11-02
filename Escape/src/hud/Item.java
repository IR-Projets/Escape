package hud;

import game.Variables;

import java.awt.Graphics2D;
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
	
	public void drawItem(Graphics2D graphics, int x, int y){
		int widthItem = getImage().getWidth(), heighItem = getImage().getHeight();
		
		graphics.drawImage(getImage(), x, y, widthItem, heighItem, null);//display image of the item
		graphics.setColor(Variables.BLACK);
		graphics.drawRect(x+2, y, widthItem-4, heighItem-1);//border of the item image
		graphics.drawString(getName(), x+26, y+15);//Name of the item
		
		graphics.setColor(Variables.WHITE);
		graphics.drawString(String.valueOf(getNbItem()), x+20, y+23);//display the amount of the item
		
	}
	
	

}
