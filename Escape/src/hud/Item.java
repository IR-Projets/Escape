package hud;

import java.awt.Graphics2D;
import java.awt.image.BufferedImage;

import entities.weapons.WeaponFactory.WeaponType;
import game.Ressources;
import game.Variables;
/**
 * This class represents an item, which contains the main function for initialize it and show it on a Graphics.
 * An item is represents by a name, an image and a number, which represents the number on the inventory.
 * 
 * @author Quentin Bernard et Ludovic Feltz
 */


/* <This program is an Shoot Them up space game, called Escape-IR, made by IR students.>
 *  Copyright (C) <2012>  <BERNARD Quentin & FELTZ Ludovic>

 *  This program is free software: you can redistribute it and/or modify
 *  it under the terms of the GNU General Public License as published by
 *  the Free Software Foundation, either version 3 of the License, or
 *  (at your option) any later version.

 *  This program is distributed in the hope that it will be useful,
 *  but WITHOUT ANY WARRANTY; without even the implied warranty of
 *  MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 *  GNU General Public License for more details.

 *  You should have received a copy of the GNU General Public License
 *  along with this program.  If not, see <http://www.gnu.org/licenses/>.
 *
 */
public class Item {
	private final String name;
	private final BufferedImage image;
	private int nbItem;
	WeaponType type;
	
	public Item(WeaponType type, String name, String nameImage, int nbItem) {
		this.type = type;
		this.name = name;
		this.image=Ressources.getImage(nameImage);
		this.nbItem=nbItem;
	}

	public WeaponType getWeaponType(){
		return type;
	}
	
	/**
	 * Return the name of the Item
	 * @return
	 */
	public String getName() {
		return name;
	}
	
	/**
	 * Return the Image of the Item
	 * @return
	 */
	public BufferedImage getImage() {
		return image;
	}

	/**
	 * Return the number of item
	 * @return
	 */
	public int getNbItem() {
		return nbItem;
	}
	
	public void addItem(int quantity){
		nbItem+=quantity;
	}
	
	public Item removeItem(){
		nbItem--;
		return this;
	}
	/**
	 * Draw the item, at the position x and y. The drawing show : The image of item, with a frame,
	 * the quantity at the bottom right of the frame, and the name of the item, at right of the image.
	 * @param graphics the graphics2D to print on
	 * @param x the begin of the drawing of the item, at position x
	 * @param y the begin of the drawing of the item, at position y
	 */
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
