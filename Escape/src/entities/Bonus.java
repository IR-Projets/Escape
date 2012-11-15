package entities;

import java.awt.Graphics2D;
import java.awt.image.BufferedImage;

import entities.weapons.WeaponItem;
import factories.WeaponFactory.WeaponType;
import game.Variables;

public class Bonus extends Entity {

	private final BufferedImage imageItem;
	private final WeaponItem weaponItem;
	
	
	public Bonus(Entities entities, WeaponItem weaponItem, int posX, int posY) {
		super(entities, getSquareBody(entities, posX, posY, weaponItem.getImage().getWidth(), weaponItem.getImage().getHeight()));
		this.weaponItem = weaponItem;
		
		BufferedImage imageTmp = weaponItem.getImage();
		imageItem =  new BufferedImage(imageTmp.getWidth(), imageTmp.getHeight(), BufferedImage.TYPE_INT_ARGB);
		Graphics2D graphics = imageItem.createGraphics();
		
		graphics.setColor(Variables.BLACK);
		graphics.fillOval(0, 0, imageItem.getWidth(), imageItem.getHeight());
		graphics.drawImage(imageTmp, 0, 0, null);
		
		graphics.setColor(Variables.WHITE);
		graphics.drawString(String.valueOf(getQuantity()), 20, 23);//display the amount of the item
		addtoCollisionGroup(EntityType.Bonus);
	}

	@Override
	public EntityType getEntityType() {
		return EntityType.Bonus;
	}

	@Override
	public int getDamage() {
		return 0;
	}

	@Override
	public void collision(Entity entity, EntityType type) {
	}

	@Override
	public BufferedImage getImage() {
		return imageItem;
	}

	@Override
	public void compute() {
	}

	public WeaponType getWeaponType() {
		return weaponItem.getWeaponType();
	}

	public int getQuantity() {
		return weaponItem.getQuantity();
	}

}
