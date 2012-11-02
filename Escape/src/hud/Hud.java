package hud;

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
public class Hud implements LifeListener, ItemListener {

	/*
	 * Singleton
	 */
	private static Hud instance = null;
	public static Hud get(){
		if(instance==null)
			instance = new Hud();
		return instance;
	}

	private final BufferedImage hudLeft, hudRight, noWeapon;
	private final ItemList itemList;
	private int score;
	private boolean displayItemList;//Boolean for know if we have to display the ItemList
	private final Item itemEmpty;//Only for show an empty item if we ItemList is empty.
	
	private int sizeLife;// The total size for display the life
	private final int echelle;// The scale to compare one Point of life into a Percent of the Health Menu

	private Hud(){
		hudLeft = Ressources.getImage("images/hud/hudLeft.png");
		hudRight = Ressources.getImage("images/hud/hudRight.png");
		noWeapon = Ressources.getImage("images/hud/error.png");
		
		score = 0;
		sizeLife = 4*hudLeft.getWidth()/7;
		echelle = sizeLife/Variables.MAX_LIFE;
		displayItemList = false;
		
		itemEmpty = new Item("No Weapon", noWeapon, 0);
		itemList = new ItemList();
	}

	@Override
	public void itemAdd(Item item) {
		itemList.getItems().add(item);
	}

	@Override
	public void itemRemove(Item item) {
		itemList.getItems().remove(item);
	}

	@Override
	public void lifeChanged(int oldLife, int newLife) {
		if(oldLife == newLife)
			return;
		int diffLife = oldLife-newLife;
		sizeLife -= diffLife*echelle;
		System.out.println("Sizelife"+sizeLife);
	}

	public void drawLife(Graphics2D graphics){
		graphics.setColor(Variables.GREEN);
		graphics.fillRect(2*hudLeft.getWidth()/7, 6*hudLeft.getHeight()/11, sizeLife, hudLeft.getHeight()/4);
		score++;
	}

	public void drawScore(Graphics2D graphics){
		graphics.setColor(Variables.WHITE);
		graphics.drawString("SCORE", hudLeft.getWidth()/6,2*hudLeft.getHeight()/5);
		graphics.drawString(Integer.toString(score), hudLeft.getWidth()/2, 2*hudLeft.getHeight()/5);
	}

	public void drawWeapons(Graphics2D graphics){

		int beginLeftHud = Variables.SCREEN_WIDTH-hudRight.getWidth();
		graphics.drawImage(hudRight, beginLeftHud, 0, hudRight.getWidth(), hudRight.getHeight(), null);//Right hud

		if(displayItemList == true)//Display menu on click, which is represents by this boolean
			itemList.drawItemList(graphics, Variables.SCREEN_WIDTH-hudRight.getWidth() + 4*hudRight.getWidth()/18, 6*hudRight.getHeight()/11);

		/*Drawing actual item in the Right Hud */
		if(itemList.getItems().isEmpty())
			itemEmpty.drawItem(graphics, beginLeftHud+30, hudRight.getHeight()/5);
		else
			itemList.getItems().get(0).drawItem(graphics, beginLeftHud+30, hudRight.getHeight()/5);
		//ONLY FOR TEST -> DRAW THE SIZE OF THE EVENT
		//graphics.drawRect(beginLeftHud, 10, hudRight.getWidth()-20, hudRight.getHeight()-10);
	}

	
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


	public void drawHud(Graphics2D graphics){
		//Draw the life
		drawLife(graphics);

		//Draw the HUD
		graphics.drawImage(hudLeft, 0, 0, hudLeft.getWidth(), hudLeft.getHeight(), null);

		//Draw Weapons
		drawWeapons(graphics);

		//Draw the score
		drawScore(graphics);

	}

	/**
	 * Display the HUD, which is compone of several elements : 
	 * @param Graphics2D graphics, which represents the world to draw on
	 */
	public void render(Graphics2D graphics){
		drawHud(graphics);
	}

}
