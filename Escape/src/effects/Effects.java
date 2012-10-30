package effects;

import java.awt.Graphics2D;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;

public abstract class Effects {

	private static List <Effects> effects = new LinkedList<>();
	
	public static void render(Graphics2D graphics){

		Iterator<Effects> ite = effects.iterator();
		
		while(ite.hasNext()){
			Effects effect = ite.next();
			effect.renderEffect(graphics);
			if(effect.terminated()){
				ite.remove();
			}
		}
	}
	
	public static void addEffect(Effects effect){
		effects.add(effect);		
	}
	
	
	public abstract void renderEffect(Graphics2D graphics);
	public abstract boolean terminated();
}
