package entities.weapons;

import java.awt.image.BufferedImage;

import org.jbox2d.dynamics.World;

public class Fireball extends Weapon {

	protected BufferedImage image;
	
	
	
	//TODO: Entity doit afficher lui même ses entitées !!!!!!!!!!!!!!!!!!!!!!!
	//Renomer Entity en Entities !!!!!!!!!!!!!!!!!!
	
	
	public Fireball(World world, String nameImage, float x, float y) {
		super(world,nameImage,x,y);
	}
	
	/*public Fireball(int x, int y){
		image = Ressources.getImage("images/weapons/fire.png");
		init(x,y);
	}

	@Override
	public BufferedImage getImage() {
		return image;
	}

	@Override
	public BufferedImage getIcon() {
		return image;
	}*/
	
	@Override
	public void compute() {
		// TODO Auto-generated method stub
		
	}

	//la il faudra ajouter l'effet d'augmentation de la boule
	/*
	@Override
	public BufferedImage getImageRender() {
		
		return null;
	}*/
	
	
	
}
