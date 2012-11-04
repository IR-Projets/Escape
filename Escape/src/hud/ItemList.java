package hud;

import fr.umlv.zen2.MotionEvent;
import fr.umlv.zen2.MotionEvent.Kind;
import game.Ressources;
import game.Variables;

import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
/**
 * This class represents a list of Item, which contains the main function for show it on a Graphics, and for manage the event with it.
 * 
 * @author Quentin Bernard et Ludovic Feltz
 */


/* <one line to give the program's name and a brief idea of what it does.>
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

public class ItemList {

	private final List<Item> itemList;
	private final BufferedImage cadreSup, cadreInf, cadreBor;

	public ItemList() {
		cadreSup = Ressources.getImage("images/hud/fontWeaponTop.png");
		cadreInf = Ressources.getImage("images/hud/fontWeaponBot.png");
		cadreBor = Ressources.getImage("images/hud/fontWeapon.png");
		itemList = new LinkedList<>();

		//Only for test
		itemList.add(new Item("Fireball", Ressources.getImage("images/weapons/fire.png"),1));
		itemList.add(new Item("Missile", Ressources.getImage("images/weapons/missile.png"),1));
		itemList.add(new Item("Shiboleet", Ressources.getImage("images/weapons/shiboleet.png"),2));
	}


	/**
	 * @return the list of item
	 */
	public List<Item> getItems() {
		return itemList;
	}


	/**
	 * Draw an item, which is compose of a list of item, with a wallpaper specific to the ItemList. 
	 * @param graphics
	 * @param x begin coordonate at x position
	 * @param y begin coordonate at y position
	 * @param item the item to display
	 */
	public void drawItemwithFont(Graphics2D graphics, int x, int y, Item item){
		graphics.drawImage(cadreBor, x, y, cadreBor.getWidth(), cadreBor.getHeight(), null);//the font of the item
		item.drawItem(graphics, x+5, y+1);// the draw of the item
	}


	/**
	 * display the item list of this object on the graphics
	 * The first element displayed is the second, because the first element is already displays on the hud
	 * Drawing a wallpaper behind item, for have a best render
	 * 
	 * @param graphics the graphics2D to print on
	 * @param debX the begin of the drawing of the listItem, at position X
	 * @param debY the begin of the drawing of the listItem, at position Y
	 */
	public void drawItemList(Graphics2D graphics, int debX, int debY){
		int echelleY = cadreBor.getHeight();
		int i=0;

		Iterator<Item> it = itemList.iterator();
		if(it.hasNext())//Don't care about the first element, because he is print on the hud
			it.next();

		if(it.hasNext()){/*Drawing the second item, with the position in Y after the cadreSup*/
			drawItemwithFont(graphics,debX, debY+cadreSup.getHeight(), it.next());
			i++;
		}

		while(it.hasNext())
			drawItemwithFont(graphics,debX, debY+cadreSup.getHeight()+(i++)*echelleY, it.next());

		/* Drawing the border for items*/
		graphics.drawImage(cadreSup, debX, debY, cadreSup.getWidth(), cadreSup.getHeight(), null);
		graphics.drawImage(cadreInf, debX, debY+cadreSup.getHeight()+i*echelleY, cadreInf.getWidth(), cadreInf.getHeight(), null);
		graphics.setColor(Variables.WHITE);
		graphics.drawString("Weapon", debX+22, debY+20);
	}



	/**
	 * The event whose checking we select an item in our item list. Be care, doesn't checks if the Item is displayed! The Hud do this work
	 * 
	 * @see Hud 
	 * @param event The even to check
	 * @param hudRightWidth the Width of the Right Hud
	 * @param hudRightHeight the Height of the Right Hud
	 * @return true if the event is associated with a selection of a weapon, else false.
	 */
	public boolean eventItemList(MotionEvent event, int hudRightWidth, int hudRightHeight){
		int mouseX = event.getX(), mouseY = event.getY();
		int debItemX = Variables.SCREEN_WIDTH-hudRightWidth + hudRightWidth/7;
		int finItemX = debItemX + cadreBor.getWidth();
		int debItemY = 6*hudRightHeight/11+cadreSup.getHeight();
		int echelleY = cadreBor.getHeight();

		if(event.getKind() != Kind.ACTION_DOWN)//Only accept on the down click of the mouse
			return false;
		
		for(int i=1;i<itemList.size();i++)
			if(mouseX >= debItemX && mouseX <= finItemX)
				if(mouseY >= debItemY+echelleY*(i-1) && mouseY <= debItemY+echelleY*i){
					//System.out.println("mise en tete de "+itemList.get(i).getName());
					itemList.add(0,itemList.remove(i));
					return true;
				}
		return false;
	}


}
