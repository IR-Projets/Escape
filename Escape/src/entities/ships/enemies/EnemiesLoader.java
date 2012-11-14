package entities.ships.enemies;

import java.awt.image.BufferedImage;
import java.util.Iterator;
import java.util.List;

import org.jbox2d.common.Vec2;

import entities.Entities;
import factories.ShipFactory;
import game.Variables;

public class EnemiesLoader {

	private final Entities entities;
	
	private int loop, step;

	
	private final List<EnemyApparitionTime> appearList;
	private final ShipFactory shipFactory;

	public static class EnemyApparitionTime {
		private BufferedImage image;
		private EnemyBehavior behavior;
		private int x, y, life;
		private int time;
		Vec2 posAppear;
		boolean isBoss;
		
		public EnemyApparitionTime(BufferedImage image, EnemyBehavior behavior, int x, int y, int life, int time, Vec2 posAppear, boolean isBoss){
			this.image = image;
			this.behavior = behavior;
			this.x = x;
			this.y = y;
			this.life = life;
			this.time = time;
			this.posAppear = posAppear;
			this.isBoss = isBoss;
		}
		
		public BufferedImage getImage() {
			return image;
		}

		public EnemyBehavior getBehavior() {
			return behavior;
		}

		public int getX() {
			return x;
		}

		public int getY() {
			return y;
		}

		public int getLife() {
			return life;
		}

		public int getTime() {
			return time;
		}

		public Vec2 getPosAppear() {
			return posAppear;
		}
		
		public boolean isBoss(){
			return isBoss;
		}
	}
	
	
	public EnemiesLoader(Entities entities, String filename) {
		this.entities=entities;
		loop=step=0;
		appearList = new LoaderXml().getEnemysFromXml(entities, filename);
		shipFactory = new ShipFactory(entities);
	}

	public void compute(){
		loop++;
		if(loop>Variables.LOOP_SKIP){
			
			loop=0;
			step++;
			
			Iterator <EnemyApparitionTime> it = appearList.iterator();
			while(it.hasNext()){
				EnemyApparitionTime enemyLoad = it.next();
				if(step > enemyLoad.getTime()){
					if(enemyLoad.isBoss())
						shipFactory.createBoss(enemyLoad.getImage(), enemyLoad.getX(), enemyLoad.getY(), enemyLoad.getLife(), enemyLoad.getBehavior());
					else
						shipFactory.createEnnemy(enemyLoad.getImage(), enemyLoad.getX(), enemyLoad.getY(), enemyLoad.getLife(), enemyLoad.getBehavior());
					it.remove();
				}
			}
		}
	}
	
	
}
