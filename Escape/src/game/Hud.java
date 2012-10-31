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
	
	
	private BufferedImage hudLeft=null, hudRight=null;
	private final List<Weapon> weapons;
	private int score;//Position of the life, because it's not a rectangle regular, so we have to update the points when losing life

	private int sizeLife;// The total size for display the life
	private final int echelle;// The scale to compare one Point of life into a Percent of the Health Menu
	
	private Hud(){
		try {
			hudLeft = ImageIO.read(new File("images/hud3.png"));
			hudRight = ImageIO.read(new File("images/hud4.png"));
		} catch (IOException e) {
			e.printStackTrace();//("HUD initialisation fail: can't open images hud.png");
			System.exit(0);
		}
		score = 0;
		sizeLife = hudLeft.getWidth()/2;
		echelle = sizeLife/Variables.MAX_LIFE;
		
		weapons = new ArrayList<>();
		//Factory???
		weapons.add(new Fireball());
		weapons.add(new Fireball());
		//weapons.add(new Fireball());
		weapons.add(new Fireball());
		
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
		graphics.fillRect(2*hudLeft.getWidth()/5, hudLeft.getHeight()/3, sizeLife, hudLeft.getHeight()/6);
		//graphics.fillPolygon(posX,posY, 4);
		score++;
	}

	public void drawScore(Graphics2D graphics){
		graphics.setColor(Variables.ORANGE);
		graphics.drawString("SCORE", Variables.SCREEN_WIDTH/23, Variables.SCREEN_HEIGHT/26);
		graphics.drawString(Integer.toString(score), Variables.SCREEN_WIDTH/12, Variables.SCREEN_HEIGHT/15);
	}


	//TODO
	public void drawWeapons(Graphics2D graphics){
		int xDeb =5*Variables.SCREEN_WIDTH/9, xEnd = Variables.SCREEN_WIDTH, nbWeapon = weapons.size();
		int yDeb = Variables.SCREEN_WIDTH/30;
		int echelle = (xEnd-xDeb) / (5*nbWeapon), i=1;
		
		graphics.drawImage(hudRight, xDeb, 0, hudRight.getWidth(), hudRight.getHeight(), null);
		Iterator<Weapon> it = weapons.iterator();

		while(it.hasNext()){
			graphics.drawImage(it.next().getIcon(), xDeb+i*echelle, yDeb, 3*echelle, 3*hudLeft.getHeight()/8, null);
			i+=3;
		}
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
