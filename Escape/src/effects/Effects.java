package effects;

import java.awt.Graphics2D;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;

public abstract class Effects {

	private static final int MAX_LAYERS = 10;
	private static List <Effects> layers[] = new ArrayList[MAX_LAYERS];;
	
	public static void render(Graphics2D graphics){
		for(List<Effects> effects : layers){
			if(effects!=null){
				for(Effects effect : effects){
					effect.renderEffect(graphics);
				}
			}
		}
	}
	
	public static void compute(){
		for(List<Effects> effects : layers){
			if(effects!=null){
				Iterator<Effects> ite = effects.iterator();		
				while(ite.hasNext()){
					Effects effect = ite.next();
					effect.computeEffect();
					if(effect.terminated()){
						ite.remove();
					}
				}
			}
		}
	}
	
	public static void addEffect(int layer, Effects effect){
		if(layers[layer]==null)
			layers[layer] = new ArrayList<>();
		layers[layer].add(effect);		
	}
	public static void addEffect(Effects effect){
		addEffect(0, effect);		
	}
	
	
	public abstract void renderEffect(Graphics2D graphics);
	public abstract void computeEffect();
	public abstract boolean terminated();
}
