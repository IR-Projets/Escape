package factories;

import java.awt.image.BufferedImage;

import entities.Entities;
import entities.ships.Player;
import entities.ships.enemies.Enemy;
import entities.ships.enemies.EnemyBehavior;

public class ShipFactory extends EntityFactory {

	public ShipFactory(Entities entities) {
		super(entities);
	}

	public Player createPlayer(){
		Player player = new Player(getEntities());
		createEntity(player);
		return player;
	}

	
	
	public Enemy createEnnemy(BufferedImage image, int x, int y, int life, EnemyBehavior behavior, boolean isBoss) {
		Enemy enemy = new Enemy(getEntities(), image, x, y, life, behavior, isBoss);
		createEntity(enemy);
		return enemy;
	}
	
	
	/*public static Ship createBoss(Entities entities, String nameImage, int x, int y, int life) {
		Boss boss = new Boss(entities, Ressources.getImage(nameImage), life);
		entities.addEntity(boss);
		return boss;
	}*/

}
