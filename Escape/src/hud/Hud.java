package hud;

import entities.Entities;
import entities.weapons.Weapon;
import entities.weapons.WeaponFactory;
import entities.weapons.WeaponFactory.WeaponType;
import fr.umlv.zen2.MotionEvent;
import fr.umlv.zen2.MotionEvent.Kind;
import game.Ressources;
import game.Variables;

import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
/**
 * This class represents our Head Up Display, which manage the elements associated with the game (life, weapon).
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
public class Hud implements LifeListener{


	private final BufferedImage hudLeft, hudRight;
	private final ItemList itemList;

	private int score;
	private boolean displayItemList;//Boolean for know if we have to display the ItemList
	private final Item itemEmpty;//Only for show an empty item if we ItemList is empty.

	private int sizeLife;// The total size for display the life
	private final int echelle;// The scale to compare one Point of life into a Percent of the Health Menu

	public Hud(){
		hudLeft = Ressources.getImage("images/hud/hudLeft.png");
		hudRight = Ressources.getImage("images/hud/hudRight.png");

		score = 0;
		sizeLife = 4*hudLeft.getWidth()/7;
		echelle = sizeLife/Variables.MAX_LIFE;
		displayItemList = false;

		itemEmpty = new Item(WeaponType.Null, "No Weapon", "images/hud/error.png", 0);
		itemList = new ItemList();
	}

	
	public ItemList getItemList() {
		return itemList;
	}

	public Item getItemActual() {
		return itemList.getItems().get(0);
	}
	
	public Weapon createSelectedWeapon(Entities entities, int x, int y, boolean damagedPlayer){
		WeaponFactory factory = new WeaponFactory(entities);
		return factory.createWeapon(itemList.removeCurrentItem().getWeaponType(), x, y, damagedPlayer);
	}
	
	@Override
	public void lifeChanged(int oldLife, int newLife) {
		if(oldLife == newLife)
			return;
		int diffLife = oldLife-newLife;
		sizeLife -= diffLife*echelle;
	}
	
	/**
	 * Draw the life of the player
	 * @param graphics the graphics2D to print on
	 */
	public void drawLife(Graphics2D graphics){
		graphics.setColor(Variables.GREEN);
		graphics.fillRect(2*hudLeft.getWidth()/7, 6*hudLeft.getHeight()/11, sizeLife, hudLeft.getHeight()/4);
		score++;
	}

	/**
	 * Draw the score of the player
	 * @param graphics the graphics2D to print on
	 */
	public void drawScore(Graphics2D graphics){
		graphics.setColor(Variables.WHITE);
		graphics.drawString("SCORE", hudLeft.getWidth()/6,2*hudLeft.getHeight()/5);
		graphics.drawString(Integer.toString(score), hudLeft.getWidth()/2, 2*hudLeft.getHeight()/5);
	}

	/**
	 * Draw the right hud, with the weapon associated. When click on the hud, display the weapon list
	 * @param graphics the graphics2D to print on
	 */
	public void drawWeapons(Graphics2D graphics){
		int beginLeftHud = Variables.SCREEN_WIDTH-hudRight.getWidth();
		graphics.drawImage(hudRight, beginLeftHud, 0, hudRight.getWidth(), hudRight.getHeight(), null);//Right hud

		if(displayItemList == true)//Display menu on click, which is represents by this boolean
			itemList.drawItemList(graphics, Variables.SCREEN_WIDTH-hudRight.getWidth() + 2*hudRight.getWidth()/9, 6*hudRight.getHeight()/11);

		if(itemList.isEmpty())//Drawing actual item in the Right Hud 
			itemEmpty.drawItem(graphics, beginLeftHud+hudRight.getWidth()/4, hudRight.getHeight()/4);
		else
			itemList.getItems().get(0).drawItem(graphics, beginLeftHud+hudRight.getWidth()/4, hudRight.getHeight()/4);
		//ONLY FOR TEST -> DRAW THE SIZE OF THE EVENT
		//graphics.drawRect(beginLeftHud, 10, hudRight.getWidth()-20, hudRight.getHeight()-10);
	}

	
	/**
	 * Display the menu of weapon when click on the right hud, and launch the eventItemList for manage the selection of weapon
	 * @param event the MotionEvent which reprensents the event of the mouse
	 */
	public void event(MotionEvent event) {
		int beginLeftHud = Variables.SCREEN_WIDTH-hudRight.getWidth();
		int mouseX = event.getX(), mouseY = event.getY();

		/* Check the event of the list of weapon*/
		if(displayItemList == true && itemList.eventItemList(event, hudRight.getWidth(), hudRight.getHeight()) == true)
			displayItemList=false;

		/*Displaying the menu*/
		if(mouseX >= beginLeftHud && mouseX <= (beginLeftHud+hudRight.getWidth()-20))
			if(mouseY >= 10 && mouseY <= hudRight.getHeight()-10)
				if(event.getKind() == Kind.ACTION_DOWN)
					displayItemList=(displayItemList==true)?false:true;
	}
	
	
	/**
	 * Display the HUD, which is compone of several elements : the left hud with the life and score, the right hud with the weapon.
	 * @param graphics the graphics2D to print on
	 */
	public void render(Graphics2D graphics){
		drawLife(graphics);
		graphics.drawImage(hudLeft, 0, 0, hudLeft.getWidth(), hudLeft.getHeight(), null);//Draw the right HUD
		drawWeapons(graphics);
		drawScore(graphics);
	}

}
