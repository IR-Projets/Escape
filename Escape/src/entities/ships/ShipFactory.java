package entities.ships;

import java.util.Random;

import org.jbox2d.dynamics.World;

import entities.Entities;
import entities.Entity;
import entities.EntityFactory;
import entities.ships.enemies.Enemy;
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
	
	public Ship createEntity(int posX, int posY, int width, int height){
		Ship ship;
		Random rand = new Random();
		if(rand.nextInt(2)==0)
			ship = new Enemy(getEntities(), Ressources.getImage("images/ships/dirtyDick.png"), rand.nextInt(Variables.SCREEN_WIDTH), rand.nextInt(Variables.SCREEN_HEIGHT*2/3)+Variables.SCREEN_HEIGHT/3, 10);
		else
			ship = new Enemy(getEntities(), Ressources.getImage("images/ships/ship.png"), rand.nextInt(Variables.SCREEN_WIDTH), rand.nextInt(Variables.SCREEN_HEIGHT*2/3)+Variables.SCREEN_HEIGHT/3, 10);
		getEntities().addEntity(ship);
		return ship;
	}

}
