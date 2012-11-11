package entities.ships.enemies;

import java.util.List;

import org.jbox2d.dynamics.World;

import entities.Entities;
import entities.Entity;
import entities.ships.Ship;
import entities.ships.boss.Boss;
import entities.ships.enemies.LoaderBehavior.Behavior;
import entities.weapons.Weapon;
import entities.weapons.WeaponFactory;
import entities.weapons.WeaponFactory.WeaponType;
import game.Ressources;
import hud.Item;

public class EnnemyFactory extends Entities {

	public EnnemyFactory(World world) {
		super(world);
		// TODO Auto-generated constructor stub
	}

	
	
	public static Enemy createEnnemy(Entities entities, String nameImage, int x, int y, int life, List<Behavior> listBehavior) {
		//Random rand = new Random();
		Enemy enemy = new Enemy(entities, Ressources.getImage(nameImage), x, y, life, listBehavior);
		entities.addEntity(enemy);
		/*WeaponFactory factory = new WeaponFactory(entities);
		Weapon tmp = factory.createWeapon(WeaponType.Fireball,  x, y, true);
		tmp.setVelocity(0, -100);
		tmp.setLaunch(true);*/
		
		
		return enemy;
	}
	
	public static Ship createBoss(Entities entities, String nameImage, int x, int y, int life) {
		//Random rand = new Random();
		Boss boss = new Boss(entities, Ressources.getImage(nameImage), life);
		entities.addEntity(boss);
		/*WeaponFactory factory = new WeaponFactory(entities);
		Weapon tmp = factory.createWeapon(WeaponType.Fireball,  x, y, true);
		tmp.setVelocity(0, -100);
		tmp.setLaunch(true);*/
		
		
		return boss;
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
