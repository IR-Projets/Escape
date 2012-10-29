package game;

import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

public class Hud {

	public final static int MAX_LIFE = 100;
	public final static int MAX_SIZE_LIFE = 75;
	BufferedImage hud;
	private int life;// Faudrait mieux le mettre dans Player, mais on fait comment la connexion?pke ia plusieurs moiens et jvoulai en parler avec toi, donc en attendant
	
	public Hud () throws IOException{
		try {
			hud = ImageIO.read(new File("images/hud.png"));
		} catch (IOException e) {
			throw new IOException("HUD initialisation fail: can't open images hud.png");
			//e.printStackTrace();
		}
		life = MAX_SIZE_LIFE;
	}
	
	/**
	 * Display the HUD, which is compone of several elements : 
	 * @param Graphics2D graphics, which represents the world to draw on
	 */
	public void render(Graphics2D graphics){
		graphics.drawImage(hud, 0, 0, hud.getWidth(), hud.getHeight(), null);
		graphics.setColor(Variables.GREEN);
		
		graphics.fillRect(2*Variables.SCREEN_WIDTH/8, Variables.SCREEN_HEIGHT/10, life, Variables.SCREEN_HEIGHT/40);
		//life--;
		}
	
	
	
	
}
