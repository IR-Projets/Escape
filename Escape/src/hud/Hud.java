package hud;

import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import javax.imageio.ImageIO;

import entities.weapons.Fireball;
import entities.weapons.Weapon;
import game.Ressources;
import game.Variables;

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


	private BufferedImage hudLeft=null, hudRight=null, cadreSup, cadreInf, cadreBor;
	
	private final List<Item> items;
	private int score;

	private int sizeLife;// The total size for display the life
	private final int echelle;// The scale to compare one Point of life into a Percent of the Health Menu

	private Hud(){
		hudLeft = Ressources.getImage("images/hud/hudLeft.png");
		hudRight = Ressources.getImage("images/hud/hudRight.png");
		cadreSup = Ressources.getImage("images/hud/fontWeaponTop.png");
		cadreInf = Ressources.getImage("images/hud/fontWeaponBot.png");
		cadreBor = Ressources.getImage("images/hud/fontWeapon.png");
		score = 0;
		sizeLife = 4*hudLeft.getWidth()/7;
		echelle = sizeLife/Variables.MAX_LIFE;

		items = new ArrayList<>();

		items.add(new Item("Fireball", Ressources.getImage("images/weapons/fire.png")));
		items.add(new Item("Missile", Ressources.getImage("images/weapons/missile.png")));
	}
	
	
	@Override
	public void itemAdd(Item item) {
		// TODO Auto-generated method stub
		
	}


	@Override
	public void itemRemove(Item item) {
		// TODO Auto-generated method stub
		
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
		//graphics.fillPolygon(posX,posY, 4);
		score++;
	}

	public void drawScore(Graphics2D graphics){
		graphics.setColor(Variables.WHITE);
		graphics.drawString("SCORE", hudLeft.getWidth()/6,2*hudLeft.getHeight()/5);
		graphics.drawString(Integer.toString(score), hudLeft.getWidth()/2, 2*hudLeft.getHeight()/5);
	}


	
	public void drawItem(Graphics2D graphics, int x, int y, Item item){
		int widthItem = item.getImage().getWidth(), heighItem = item.getImage().getHeight();
		
		graphics.drawImage(cadreBor, x, y, cadreBor.getWidth(), cadreBor.getHeight(), null);//the font
		graphics.drawImage(item.getImage(), x+5, y+1, widthItem, heighItem, null);//image
		
		graphics.setColor(Variables.RED);
		graphics.drawRect(x+7, y+1, widthItem-4, heighItem-1);//border of the item image
		graphics.drawString(item.getName(), x+35, y+15);//Name of the item
		
	}


	public void drawWeapons(Graphics2D graphics){

		int debHudX = Variables.SCREEN_WIDTH-hudRight.getWidth();
		int debWeaponX = debHudX + hudRight.getWidth()/7;
		int debWeaponY = 6*hudRight.getHeight()/11;
		int echelleY = cadreBor.getHeight();
		
		graphics.drawImage(hudRight, debHudX, 0, hudRight.getWidth(), hudRight.getHeight(), null);//Right hud
		
		Iterator<Item> it = items.iterator();
		int i=0;
		
		/* Drawing all items */
		if(it.hasNext()){
			drawItem(graphics,debWeaponX, debWeaponY+cadreSup.getHeight(), it.next());
			i++;
		}
		
		while(it.hasNext()){
			drawItem(graphics,debWeaponX, debWeaponY+cadreSup.getHeight()+(i++)*echelleY, it.next());
		}
		
		/* Drawing the border for items*/
		graphics.drawImage(cadreSup, debWeaponX, debWeaponY, cadreSup.getWidth(), cadreSup.getHeight(), null);
		graphics.drawImage(cadreInf, debWeaponX, debWeaponY+cadreSup.getHeight()+i*echelleY, cadreInf.getWidth(), cadreInf.getHeight(), null);
		graphics.setColor(Variables.WHITE);
		graphics.drawString("Weapon", debWeaponX+22, debWeaponY+20);
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
