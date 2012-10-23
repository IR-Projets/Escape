package game;

import java.awt.Color;



public class Variables {

	public final static boolean DEBUG = true;
	
	public final static String APPLICATION_NAME = "Escape IR";
	public final static int SCREEN_WIDTH = 500;
	public final static int SCREEN_HEIGHT = 1000;
	
	public final static Color GREEN = new Color(0,255,0);
	public final static Color RED = new Color(255,0,0);
	
	public final static int RATE_DELETE_TRACE = 10;/*If we increase the value, the delete time of Trace become lower*/
	public final static int SPEED_MAIN_SHIP = 15;/* Speed of the main ship */
	
	
	public final static float WORLD_SCALE = 20;
	public static final float WORLD_GRAVITY_X = 0;
	public static final float WORLD_GRAVITY_Y = 0;
	
	public static float LINK_FREQUENCY = 0.3f;	//Duretée
	public static float LINK_DAMPING = 0.5f;	//Ressort
	
	
	/*
	 * 
	 */
	
	
}
