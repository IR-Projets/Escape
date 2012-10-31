package game;

import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.imageio.ImageIO;

import entities.ships.LifeListener;
import entities.weapons.Fireball;
import entities.weapons.Weapon;

public class Hud implements LifeListener {

/*
 * Singleton a faire
 */
	private final BufferedImage hudLeft, hudRight;
	private final List<Weapon> weapons;
	private int posX[], posY[], score;//Position of the life, because it's not a rectangle regular, so we have to update the points when losing life

	private int sizeLife;// The total size for display the size Polygon

	public Hud () throws IOException{
		try {
			hudLeft = ImageIO.read(new File("images/hud3.png"));
			hudRight = ImageIO.read(new File("images/hud2.png"));
		} catch (IOException e) {
			throw new IOException("HUD initialisation fail: can't open images hud.png");
			//e.printStackTrace();
		}
		score = 0;
		posX = new int[4];
		posY = new int[4];

		weapons = new ArrayList<>();
		initLifeCoordonate();
		//Factory???
		weapons.add(new Fireball());
		weapons.add(new Fireball());

	}

	private void initLifeCoordonate(){
		posX[0]=Variables.SCREEN_WIDTH/6;
		posY[0]=Variables.SCREEN_HEIGHT/10;

		posX[1]=3*Variables.SCREEN_WIDTH/17;
		posY[1]=Variables.SCREEN_HEIGHT/12;

		posX[2]=6*Variables.SCREEN_WIDTH/17;
		posY[2]=Variables.SCREEN_HEIGHT/12;

		posX[3]=6*Variables.SCREEN_WIDTH/18;
		posY[3]=Variables.SCREEN_HEIGHT/10;

		sizeLife=posX[2]-posX[0];
	}


	@Override
	public void lifeChanged(int oldLife, int newLife) {
		if(oldLife == newLife)
			return;
		int diffLife = oldLife-newLife;
		if(posX[1]>=posX[2]){
			if(posX[0]>=posX[3])
				System.out.println("DIE");
			else
				posX[3]-=(diffLife*sizeLife/100);
		}
		else{
			posX[2]-=(diffLife*sizeLife/100);
			if(posX[2] <= posX[3])
				posX[3]=posX[2];
		}
	}

	public void drawLife(Graphics2D graphics){
		graphics.setColor(Variables.GREEN);
		graphics.fillPolygon(posX,posY, 4);
		score++;
	}

	public void drawScore(Graphics2D graphics){
		graphics.setColor(Variables.ORANGE);
		graphics.drawString("SCORE", Variables.SCREEN_WIDTH/23, Variables.SCREEN_HEIGHT/26);
		graphics.drawString(Integer.toString(score), Variables.SCREEN_WIDTH/12, Variables.SCREEN_HEIGHT/15);
	}


	//TODO
	/*public void renderWeapon(Graphics2D graphics){
		int xDeb = Variables.SCREEN_WIDTH-hudLeft.getWidth(), xEnd = (int) (Variables.SCREEN_WIDTH-Variables.SCREEN_WIDTH*0.05), nbWeapon = weapons.size();
		int echelle = (xEnd-xDeb) / (5*nbWeapon), i=1;
		Iterator<Weapon> it = weapons.iterator();

		while(it.hasNext()){
			graphics.drawImage(it.next().getIcon(), xDeb+i*echelle, 5, 4*echelle, 3*hudLeft.getHeight()/5, null);
			i+=5;
		}
	}*/


	public void drawHud(Graphics2D graphics){
		//Draw the HUD
		graphics.drawImage(hudLeft, 0, 0, 2*Variables.SCREEN_WIDTH/5, Variables.SCREEN_HEIGHT/5, null);

		//Draw the score
		drawScore(graphics);

		//Draw the life
		drawLife(graphics);

	}


	/**
	 * Display the HUD, which is compone of several elements : 
	 * @param Graphics2D graphics, which represents the world to draw on
	 */
	public void render(Graphics2D graphics){
		drawHud(graphics);
	}

}
