package game;

import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import javax.imageio.ImageIO;

import entities.ships.LifeListener;
import entities.weapons.Fireball;
import entities.weapons.Weapon;

public class Hud implements LifeListener {

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
	private final List<Weapon> weapons;
	private int score;//Position of the life, because it's not a rectangle regular, so we have to update the points when losing life

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

		weapons = new ArrayList<>();
		//Factory???
		/*
		 * LF: C'est pas une bonne idée la liste de weapons
		 * sa rend l'HUD trop dépendant...
		 * le truc sa serai d'avoir une liste de binome <image, nom> ou un truc dans le genre...
		 * 
		weapons.add(new Fireball());
		weapons.add(new Fireball());
		//weapons.add(new Fireball());
		weapons.add(new Fireball());
		*/
		
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


	//TODO

	public void drawWeapons(Graphics2D graphics){
		/*int xDeb =5*Variables.SCREEN_WIDTH/9, xEnd = Variables.SCREEN_WIDTH, nbWeapon = weapons.size();
		int yDeb = Variables.SCREEN_WIDTH/30;
		int echelle = (xEnd-xDeb) / (5*nbWeapon+1), i=1;*/

		int debHudX = Variables.SCREEN_WIDTH-hudRight.getWidth();
		int debWeaponX = debHudX + 1*hudRight.getWidth()/4;
		int debWeaponY = 6*hudRight.getHeight()/11;
		
		int echelleY = cadreBor.getHeight();
		graphics.drawImage(hudRight, debHudX, 0, hudRight.getWidth(), hudRight.getHeight(), null);
		
		
		/* 1er cadre */
		graphics.drawImage(cadreSup, debWeaponX, debWeaponY, cadreSup.getWidth(), cadreSup.getHeight(), null);
		graphics.drawImage(cadreBor, debWeaponX, debWeaponY+echelleY, cadreBor.getWidth(), cadreBor.getHeight(), null);
		//graphics.drawImage(cadreBor, debWeaponX, debWeaponY+2*echelleY, cadreBor.getWidth(), cadreBor.getHeight(), null);
		graphics.drawImage(cadreInf, debWeaponX, debWeaponY+3*echelleY, cadreInf.getWidth(), cadreInf.getHeight(), null);
			
		
		/* rectangle test pour encadrer les differentes images*/
		graphics.setColor(Variables.WHITE);
		graphics.drawRect(debWeaponX, debWeaponY, cadreSup.getWidth(), cadreSup.getHeight());
		graphics.drawRect(debWeaponX, debWeaponY+echelleY, cadreBor.getWidth(), cadreBor.getHeight());
		graphics.drawRect(debWeaponX, debWeaponY+3*echelleY, cadreInf.getWidth(), cadreInf.getHeight());
		
		
		
		
		
		

		/*Iterator<Weapon> it = weapons.iterator();

		while(it.hasNext()){
			graphics.drawImage(it.next().getImage(), xDeb+i*echelle, yDeb, 3*echelle, 3*hudLeft.getHeight()/8, null);
			i+=3;
		}*/
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
