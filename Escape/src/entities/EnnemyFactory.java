package entities;

import java.awt.image.BufferedImage;

import org.jbox2d.dynamics.Body;
import org.jbox2d.dynamics.World;

import entities.ships.Player;
import entities.ships.enemies.Enemy;
import game.Ressources;

public class EnnemyFactory extends Entities {

	public EnnemyFactory(World world) {
		super(world);
		// TODO Auto-generated constructor stub
	}

	
	
	
	
	
	/*
	
	public Entity createEnemy1(String nameImage, int x, int y, int life){
		BufferedImage image = Ressources.getImage(nameImage);
		Body body = createBody(x, y, image.getWidth(), image.getHeight());
		Entity entity = new Enemy(body, image, x, y, life);
	}
*/
	
	public Entity createEnemy2(String nameImage, int x, int y, int life){
		return null;
	}
	
	/*
	 * ...
	 */
	
}
