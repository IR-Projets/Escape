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
		getEntities().addEntity(player);
		return player;
	}

	
	public static Enemy createEnnemy(Entities entities, String nameImage, int x, int y, int life) {
		Enemy enemy = new Enemy(entities, Ressources.getImage(nameImage), x, y, life);
		entities.addEntity(enemy);
		return enemy;
	}
	
	
	/*public static Ship createBoss(Entities entities, String nameImage, int x, int y, int life) {
		Boss boss = new Boss(entities, Ressources.getImage(nameImage), life);
		entities.addEntity(boss);
		return boss;
	}*/

}
