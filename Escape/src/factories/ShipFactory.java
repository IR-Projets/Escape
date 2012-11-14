package factories;

import java.awt.image.BufferedImage;

import entities.Entities;
import entities.ships.Player;
import entities.ships.enemies.Boss;
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

	
	
	public Enemy createEnnemy(BufferedImage image, int x, int y, int life, EnemyBehavior behavior) {
		Enemy enemy = new Enemy(getEntities(), image, x, y, life, behavior);
		createEntity(enemy);
		return enemy;
	}
	
	
	public Boss createBoss(BufferedImage image, int x, int y, int life, EnemyBehavior behavior) {
		Boss boss = new Boss(getEntities(), image, x, y, life, behavior);
		getEntities().addEntity(boss);
		return boss;
	}

}
