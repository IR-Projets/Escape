package factories;

import entities.Entities;
import entities.ships.Player;
import entities.ships.enemies.Enemy;
import game.Ressources;

public class ShipFactory extends EntityFactory {

	public ShipFactory(Entities entities) {
		super(entities);
	}

	public Player createPlayer(){
		Player player = new Player(getEntities());
		createEntity(player);
		return player;
	}

	
	public Enemy createEnnemy(String nameImage, int x, int y, int life) {
		Enemy enemy = new Enemy(getEntities(), Ressources.getImage(nameImage), x, y, life);
		createEntity(enemy);
		return enemy;
	}
	
	
	/*public static Ship createBoss(Entities entities, String nameImage, int x, int y, int life) {
		Boss boss = new Boss(entities, Ressources.getImage(nameImage), life);
		entities.addEntity(boss);
		return boss;
	}*/

}
