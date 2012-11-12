package factories;

import java.util.List;
import java.util.Random;

import org.jbox2d.dynamics.World;

import entities.Entities;
import entities.Entity;
import entities.ships.Player;
import entities.ships.Ship;
import entities.ships.enemies.Boss;
import entities.ships.enemies.Enemy;
import entities.ships.enemies.LoaderBehavior.Behavior;
import game.Ressources;
import game.Variables;

public class ShipFactory extends EntityFactory {

	public ShipFactory(Entities entities) {
		super(entities);
	}

	public Player createPlayer(){
		Player player = new Player(getEntities());
		getEntities().addEntity(player);
		return player;
	}

	
	public static Enemy createEnnemy(Entities entities, String nameImage, int x, int y, int life, List<Behavior> listBehavior) {
		Enemy enemy = new Enemy(entities, Ressources.getImage(nameImage), x, y, life, listBehavior);
		entities.addEntity(enemy);
		return enemy;
	}
	
	public static Ship createBoss(Entities entities, String nameImage, int x, int y, int life) {
		Boss boss = new Boss(entities, Ressources.getImage(nameImage), life);
		entities.addEntity(boss);
		return boss;
	}

}
