package entities;

import java.awt.Graphics2D;
import java.awt.GraphicsEnvironment;
import java.awt.Transparency;
import java.awt.image.BufferedImage;

import javax.imageio.ImageTypeSpecifier;

import org.jbox2d.dynamics.Body;

import entities.weapons.WeaponItem;
import factories.WeaponFactory.WeaponType;
import game.Variables;

public class Bonus extends Entity {

	private final BufferedImage image;
	WeaponType type;
	
	
	public Bonus(Entities entities, WeaponType type, int posX, int posY) {
		super(entities, getSquareBody(entities, posX, posY, type.getImage().getWidth(), type.getImage().getHeight()));
		this.type = type;
		
		BufferedImage imageTmp = type.getImage();
		image =  new BufferedImage(imageTmp.getWidth(), imageTmp.getHeight(), BufferedImage.TYPE_INT_ARGB);
		Graphics2D graphics = image.createGraphics();
		
		graphics.setColor(Variables.GREEN);
		graphics.fillOval(0, 0, image.getWidth(), image.getHeight());
		graphics.drawImage(imageTmp, 0, 0, null);
		
		getBody().getFixtureList().getFilterData().categoryBits = 0x04;
		getBody().getFixtureList().getFilterData().maskBits = 0x04;

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
		return image;
	}

	@Override
	public void compute() {
	}

}
