package hud;

import java.awt.Graphics2D;
import java.awt.image.BufferedImage;

import fr.umlv.zen2.MotionEvent;
import fr.umlv.zen2.MotionEvent.Kind;
import game.Ressources;

public abstract class Button {
	private BufferedImage image;
	private int posX;
	private int posY;

	public enum ButtonType{
		SKIP,
		PAUSE,
		PLAY
	}
	private ButtonType type;

	public Button(ButtonType type, int posX, int posY){
		setButtonType(type);
		this.posX=posX;
		this.posY=posY;		
	}

	
	private void setButtonType(ButtonType type){
		this.type=type;
		switch(type){
		case PAUSE:
			this.image = Ressources.getImage("buttons/buttonPause.png");
			break;
		case PLAY:
			this.image = Ressources.getImage("buttons/buttonPlay.png");
			break;
		case SKIP:
			this.image = Ressources.getImage("buttons/buttonSkip.png");
			break;
		}
	}
	
	public void render(Graphics2D graphics){
		graphics.drawImage(image, posX, posY, null);		
	}

	public abstract void pressed();

	public void event(MotionEvent event) {
		if( event.getKind()==Kind.ACTION_DOWN && 
				event.getX()>posX && event.getX()<posX+image.getWidth() &&
				event.getY()>posY && event.getY()<posY+image.getHeight() ){
			
			switch(type){
			case PAUSE:
				setButtonType(ButtonType.PLAY);
				break;	
			case PLAY:
				setButtonType(ButtonType.PAUSE);
				break;	
			default:
				break;
			}
			pressed();
		}
	}
}
