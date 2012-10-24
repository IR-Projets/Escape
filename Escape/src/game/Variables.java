package game;

import java.awt.Color;



public class Variables {

	public final static boolean DEBUG = true;
	
	public final static String APPLICATION_NAME = "Escape IR";
	public final static int SCREEN_WIDTH = 500;
	public final static int SCREEN_HEIGHT = 1000;
	
	public final static Color GREEN = new Color(0,255,0);
	public final static Color RED = new Color(255,0,0);
	public final static Color BLUE = new Color(0,0,255);
	
	
	public final static int SPEED_MAIN_SHIP = 30;/* Speed of the main ship */
	
	public final static int TRACE_DELETE_RATE = 10;/*If we increase the value, the delete time of Trace become lower*/
	public final static double TRACE_VARIATION_MAX = 1.5;/* The limit intervalle to accept movement */
	public static final int TRACE_ERROR_MAX = 5;/* Number of division by 0 permitted with coefficent calcul */
	public static final int TRACE_LENGTH_MIN = 4;
	public final static double RATE_ACCEPT_TRACE = 1;/* the pourcentage of Points which can be in the TRACE Variation for being accepted */
	
	
	public final static float WORLD_SCALE = 20;
	public static final float WORLD_GRAVITY_X = 0;
	public static final float WORLD_GRAVITY_Y = 0;


	public static float LINK_FREQUENCY = 0.3f;	//Duretée
	public static float LINK_DAMPING = 0.5f;	//Ressort
	
	
	/*
	 * 
	 */
	
	
}
