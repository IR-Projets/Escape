package hud;

import fr.umlv.zen2.MotionEvent;
import fr.umlv.zen2.MotionEvent.Kind;
import game.Ressources;
import game.Variables;

import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class Hud implements LifeListener, ItemListener {

	/*
	 * Singleton
	 */
	private static Hud instance = null;
	public static Hud get(){
		if(instance==null)
			instance = new Hud();
		return instance;
	}

	
	/*TODO :
	*Refactoring -> drawItem dans Item avec le nom render()
	*Faire la detection des armes dans le menu
	*
	*/
	

	private BufferedImage hudLeft, hudRight, cadreSup, cadreInf, cadreBor, noWeapon, flecheDown;
	private final List<Item> items;
	private int score;
	private boolean displayItemList;
	
	private int sizeLife;// The total size for display the life
	private final int echelle;// The scale to compare one Point of life into a Percent of the Health Menu

	private Hud(){
		hudLeft = Ressources.getImage("images/hud/hudLeft.png");
		hudRight = Ressources.getImage("images/hud/hudRight.png");
		cadreSup = Ressources.getImage("images/hud/fontWeaponTop.png");
		cadreInf = Ressources.getImage("images/hud/fontWeaponBot.png");
		cadreBor = Ressources.getImage("images/hud/fontWeapon.png");
		noWeapon = Ressources.getImage("images/hud/error.png");
		flecheDown = Ressources.getImage("images/hud/flechedown.png");
		score = 0;
		sizeLife = 4*hudLeft.getWidth()/7;
		echelle = sizeLife/Variables.MAX_LIFE;
		displayItemList = false;
		
		items = new ArrayList<>();

		
		items.add(new Item("Fireball", Ressources.getImage("images/weapons/fire.png"),1));
		items.add(new Item("Missile", Ressources.getImage("images/weapons/missile.png"),1));
		items.add(new Item("Shiboleet", Ressources.getImage("images/weapons/shiboleet.png"),2));
		
	}
	
	
	@Override
	public void itemAdd(Item item) {
		items.add(item);
	}


	@Override
	public void itemRemove(Item item) {
		items.remove(item);
	}

	@Override
	public void lifeChanged(int oldLife, int newLife) {
		if(oldLife == newLife)
			return;
		int diffLife = oldLife-newLife;
		sizeLife -= diffLife*echelle;
		System.out.println("Sizelife"+sizeLife);
	}

	
	
	public void drawLife(Graphics2D graphics){
		graphics.setColor(Variables.GREEN);
		graphics.fillRect(2*hudLeft.getWidth()/7, 6*hudLeft.getHeight()/11, sizeLife, hudLeft.getHeight()/4);
		//graphics.fillPolygon(posX,posY, 4);
		score++;
	}

	public void drawScore(Graphics2D graphics){
		graphics.setColor(Variables.WHITE);
		graphics.drawString("SCORE", hudLeft.getWidth()/6,2*hudLeft.getHeight()/5);
		graphics.drawString(Integer.toString(score), hudLeft.getWidth()/2, 2*hudLeft.getHeight()/5);
	}


	
	public void drawItem(Graphics2D graphics, int x, int y, Item item, boolean printFont){
		int widthItem = item.getImage().getWidth(), heighItem = item.getImage().getHeight();
		
		if(printFont == true)
			graphics.drawImage(cadreBor, x, y, cadreBor.getWidth(), cadreBor.getHeight(), null);//the font
		if(printFont == false)
			graphics.drawImage(flecheDown, x+95, y, flecheDown.getWidth(), flecheDown.getHeight(), null);//the bottom fleche
		
		graphics.drawImage(item.getImage(), x+5, y+1, widthItem, heighItem, null);//image of the item
		
		graphics.setColor(Variables.WHITE);
		graphics.drawString(String.valueOf(item.getNbItem()), x+21, y+25);//Number of the item
		
		graphics.setColor(Variables.BLACK);
		graphics.drawRect(x+7, y+1, widthItem-4, heighItem-1);//border of the item image
		graphics.drawString(item.getName(), x+31, y+15);//Name of the item
		
	}
	
	
	public void drawItemList(Graphics2D graphics){
		int debWeaponX = Variables.SCREEN_WIDTH-hudRight.getWidth() + 4*hudRight.getWidth()/18;
		int debWeaponY = 6*hudRight.getHeight()/11;
		int echelleY = cadreBor.getHeight();
		int i=0;
		
		Iterator<Item> it = items.iterator();
		if(it.hasNext()){/*Drawing the first item, with the position in Y after the cadreSup*/
			drawItem(graphics,debWeaponX, debWeaponY+cadreSup.getHeight(), it.next(), true);
			i++;
		}
		while(it.hasNext())
			drawItem(graphics,debWeaponX, debWeaponY+cadreSup.getHeight()+(i++)*echelleY, it.next(), true);
		
		/* Drawing the border for items*/
		graphics.drawImage(cadreSup, debWeaponX, debWeaponY, cadreSup.getWidth(), cadreSup.getHeight(), null);
		graphics.drawImage(cadreInf, debWeaponX, debWeaponY+cadreSup.getHeight()+i*echelleY, cadreInf.getWidth(), cadreInf.getHeight(), null);
		graphics.setColor(Variables.WHITE);
		graphics.drawString("Weapon", debWeaponX+22, debWeaponY+20);
	}


	public void drawWeapons(Graphics2D graphics){

		int beginLeftHud = Variables.SCREEN_WIDTH-hudRight.getWidth();
		graphics.drawImage(hudRight, beginLeftHud, 0, hudRight.getWidth(), hudRight.getHeight(), null);//Right hud
		
		if(displayItemList == true)//Display menu on click, which is represents by this boolean
			drawItemList(graphics);
		
		/*Drawing actual item in the Right Hud */
		if(items.isEmpty())
			drawItem(graphics, beginLeftHud+30, hudRight.getHeight()/5, new Item("No Weapon", noWeapon, 0), false);
		else
			drawItem(graphics, beginLeftHud+30, hudRight.getHeight()/5, items.get(0), false);
		
		
		//ONLY FOR TEST -> DRAW THE SIZE OF THE EVENT
		graphics.drawRect(beginLeftHud, 10, hudRight.getWidth()-20, hudRight.getHeight()-10);
		
	}
	
	public void event(MotionEvent event) {
		int beginLeftHud = Variables.SCREEN_WIDTH-hudRight.getWidth();
		int mouseX = event.getX(), mouseY = event.getY();
		
		/*Displaying the menu*/
		if(mouseX >= beginLeftHud && mouseX <= (beginLeftHud+hudRight.getWidth()-20))
			if(mouseY >= 10 && mouseY <= hudRight.getHeight()-10)
				if(event.getKind() == Kind.ACTION_DOWN)
					displayItemList=(displayItemList==true)?false:true;
		
		
		/* Choising the arm --- in case*/
		int debItemX = Variables.SCREEN_WIDTH-hudRight.getWidth() + hudRight.getWidth()/7;
		int finItemX = debItemX + cadreBor.getWidth();
		if(displayItemList == false)
			return;
		
		//Iterator<> it = items.iterator();
		/*for(int i=0;i<items.size();i++)
				if(mouseX >= debItemX && mouseX <= finItemX)
					items.add(e);
		}*/
		
		
		
		
		
		//System.out.println(event.getX()+" et "+event.getY());
	}

	public void drawHud(Graphics2D graphics){
		//Draw the life
		drawLife(graphics);

		//Draw the HUD
		graphics.drawImage(hudLeft, 0, 0, hudLeft.getWidth(), hudLeft.getHeight(), null);

		//Draw Weapons
		drawWeapons(graphics);

		//Draw the score
		drawScore(graphics);

	}



	
	/**
	 * Display the HUD, which is compone of several elements : 
	 * @param Graphics2D graphics, which represents the world to draw on
	 */
	public void render(Graphics2D graphics){
		drawHud(graphics);
	}





}
