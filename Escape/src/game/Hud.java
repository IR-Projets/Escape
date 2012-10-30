package game;

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

public class Hud {

	public final static int MAX_LIFE = 100;
	public final static int MAX_SIZE_LIFE = 75;
	BufferedImage hudLeft, hudRight;
	List<Weapon> weapons;


	private int life;// Faudrait mieux le mettre dans Player, mais on fait comment la connexion?pke ia plusieurs moiens et jvoulai en parler avec toi, donc en attendant

	public Hud () throws IOException{
		try {
			hudLeft = ImageIO.read(new File("images/hud.png"));
			hudRight = ImageIO.read(new File("images/hud2.png"));
		} catch (IOException e) {
			throw new IOException("HUD initialisation fail: can't open images hud.png");
			//e.printStackTrace();
		}
		life = MAX_SIZE_LIFE;
		weapons = new ArrayList<>();

		//Factory???
		weapons.add(new Fireball());
		weapons.add(new Fireball());
		
		

	}

	public void renderWeapon(Graphics2D graphics){
		int xDeb = Variables.SCREEN_WIDTH-hudLeft.getWidth(), xEnd = (int) (Variables.SCREEN_WIDTH-Variables.SCREEN_WIDTH*0.05), nbWeapon = weapons.size();
		int echelle = (xEnd-xDeb) / (5*nbWeapon), i=1;
		
		Iterator<Weapon> it = weapons.iterator();
		
		//System.out.println(echelle);
		while(it.hasNext()){
			graphics.drawImage(it.next().getIcon(), xDeb+i*echelle, 5, 4*echelle, 3*hudLeft.getHeight()/5, null);
			i+=5;
		}
		System.out.println(" e i"+i);
		}



	/**
	 * Display the HUD, which is compone of several elements : 
	 * @param Graphics2D graphics, which represents the world to draw on
	 */
	public void render(Graphics2D graphics){


		graphics.drawImage(hudLeft, 0, 0, hudLeft.getWidth(), hudLeft.getHeight(), null);
		graphics.drawImage(hudRight, Variables.SCREEN_WIDTH-hudLeft.getWidth(), 0,  hudLeft.getWidth(), 2*hudLeft.getHeight()/3, null);

		graphics.setColor(Variables.GREEN);

		graphics.fillRect(3*Variables.SCREEN_WIDTH/17, Variables.SCREEN_HEIGHT/21, life, Variables.SCREEN_HEIGHT/40);
		renderWeapon(graphics);

	}
	//life--;

}
