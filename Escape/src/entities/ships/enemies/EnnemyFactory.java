package entities.ships.enemies;

import org.jbox2d.dynamics.World;

import entities.Entities;
import entities.Entity;
import game.Ressources;

public class EnnemyFactory extends Entities {

	public EnnemyFactory(World world) {
		super(world);
		// TODO Auto-generated constructor stub
	}

	
	
	public static Entity createEntity(Entities entities, String nameImage, int x, int y, int life) {
		//Random rand = new Random();
		Enemy enemy = new Enemy(entities, Ressources.getImage(nameImage), x, y, life);
		entities.addEntity(enemy);
		return enemy;
	}
	/*rand.nextInt(Variables.SCREEN_WIDTH), rand.nextInt(Variables.SCREEN_HEIGHT*2/3)+Variables.SCREEN_HEIGHT/3*/
	
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
