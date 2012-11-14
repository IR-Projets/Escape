package maps;

import game.Ressources;
import game.Variables;

import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.Collection;
import java.util.Comparator;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.SortedSet;
import java.util.TreeSet;

import javax.imageio.ImageIO;




public abstract class Map{

	private final BufferedImage ground;
	private float posY;
	private int subImgW;
	private int subImgH;
	private float velocity;



	public Map(BufferedImage ground, float velocity){			                               
		this.ground = ground;
		this.velocity=velocity;
		float ratio = 1f; //Doit etre inferieur a 1
		
		subImgW=(int)(Variables.SCREEN_WIDTH * ratio);
		if(subImgW>ground.getWidth())
			subImgW=ground.getWidth();
		
		subImgH=(int)(Variables.SCREEN_HEIGHT * ratio);
		if(subImgH>ground.getHeight())
			subImgH=ground.getHeight();
		
		posY = ground.getHeight() - Variables.SCREEN_HEIGHT;
		if(posY+subImgH>ground.getHeight())
			posY=0;
	}

	
	public void render(Graphics2D graphics){	
		if(posY<0)
			posY=0;

		graphics.drawImage(ground.getSubimage(0, (int)posY, subImgW, subImgH), 0, 0, Variables.SCREEN_WIDTH, Variables.SCREEN_HEIGHT, null );
	}
	

	public void compute() {
		posY-=velocity;
		computeMap();
	}
	
	public abstract void computeMap();
}