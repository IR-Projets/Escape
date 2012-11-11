package entities.ships.boss;

import java.awt.image.BufferedImage;

import entities.Entities;
import entities.Entity;
import entities.ships.Ship;
import game.Variables;

public class Boss extends Ship{

	public Boss(Entities entities, BufferedImage image, int life) {
		super(entities, EntityShape.Square, image, Variables.SCREEN_WIDTH/2, Variables.SCREEN_HEIGHT, life);
		getBody().setFixedRotation(true);		
		getBody().getFixtureList().getFilterData().groupIndex = -1;
	}

	@Override
	public void collision(Entity entity, EntityType type) {
		if(type==EntityType.WeaponAllied)
			explode();		
	}

	@Override
	public void compute() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public EntityType getType() {
		return EntityType.Boss;
	}

}
